import grpc from 'k6/net/grpc';
import { check, sleep } from 'k6';

const SHORT_RUN = (__ENV.SHORT_RUN ?? 'false') === 'true';
const STAGES = SHORT_RUN
    ? [
        { duration: '10s', target: Number(__ENV.TARGET_RATE_1 ?? 10) },
        { duration: '10s', target: 0 },
    ]
    : [
        { duration: '1m', target: Number(__ENV.TARGET_RATE_1 ?? 150) },
        { duration: '2m', target: Number(__ENV.TARGET_RATE_2 ?? 300) },
        { duration: '2m', target: Number(__ENV.TARGET_RATE_3 ?? 500) },
        { duration: '1m', target: Number(__ENV.TARGET_RATE_4 ?? 600) },
        { duration: '30s', target: 0 },
    ];

export const options = {
    scenarios: {
        browse: {
            executor: 'ramping-arrival-rate',
            startRate: Number(__ENV.START_RATE ?? 50),
            timeUnit: '1s',
            preAllocatedVUs: Number(__ENV.PREALLOCATED_VUS ?? 100),
            maxVUs: Number(__ENV.MAX_VUS ?? 400),
            stages: STAGES,
        },
    },
    thresholds: {
        grpc_req_duration: ['p(95)<500'],
        // Note: grpc_req_failed metric is not available in k6 for gRPC
        // Error validation is done through checks instead
        checks: ['rate>0.95'], // 95% of checks should pass
    },
};

const GRPC_HOST = __ENV.GRPC_HOST ?? 'localhost:9090';
const PAGE_SIZE = Number(__ENV.PAGE_SIZE ?? 50);
const SLEEP_BETWEEN_PAGES_MS = Number(__ENV.SLEEP_BETWEEN_PAGES_MS ?? 0);
const LANG = __ENV.LANG ?? 'es'; // Idioma para las consultas

// Crear cliente gRPC
const client = new grpc.Client();

// Cargar el archivo proto en el contexto de inicialización
client.load(['./catalog-infrastructure/catalog-infrastructure-api-grpc/src/main/spec'], 'localidad.proto');

export function setup() {
    // Setup function can be used for other initialization tasks if needed
    return {};
}

export default function () {
    // Conectar al servidor gRPC con configuración mejorada
    client.connect(GRPC_HOST, {
        plaintext: true, // Usar conexión no segura para testing local
        timeout: '30s', // Timeout de conexión
        reflect: true,  // Habilitar reflection si está disponible
    });

    try {
        // Hacer la primera llamada para obtener información de paginación
        const firstRequest = {
            lang: LANG,
            page: 0,
            size: PAGE_SIZE,
            q: '' // Sin filtro de búsqueda
        };

        console.log(`Attempting to call gRPC service at ${GRPC_HOST}...`);
        const firstResponse = client.invoke('io.github.icesoft.catalog.infrastructure.api.grpc.LocalidadService/ListLocalidades', firstRequest);
        console.log(`Response status: ${firstResponse.status}`);

        check(firstResponse, {
            'first request status is OK': (r) => {
                if (r.status !== grpc.StatusOK) {
                    console.error(`First request failed with status: ${r.status}, message: ${r.error}`);
                }
                return r.status === grpc.StatusOK;
            },
            'first response has items': (r) => r.message && r.message.items && r.message.items.length >= 0,
        });

        let totalItems = 0;
        let totalPages = 1;

        if (firstResponse.status === grpc.StatusOK && firstResponse.message) {
            totalItems = Number(firstResponse.message.totalItems || 0);
            totalPages = Math.ceil(totalItems / PAGE_SIZE);

            console.log(`Total items: ${totalItems}, Total pages: ${totalPages}, Page size: ${PAGE_SIZE}`);
        }

        // Iterar a través de todas las páginas
        for (let page = 1; page < totalPages; page++) {
            const request = {
                lang: LANG,
                page: page,
                size: PAGE_SIZE,
                q: ''
            };

            const response = client.invoke('io.github.icesoft.catalog.infrastructure.api.grpc.LocalidadService/ListLocalidades', request);

            check(response, {
                [`page ${page} status is OK`]: (r) => r.status === grpc.StatusOK,
                [`page ${page} has items`]: (r) => r.message && r.message.items && r.message.items.length >= 0,
            });

            // Opcional: validar que el número de página coincide
            check(response, {
                [`page ${page} number matches`]: (r) => r.message && Number(r.message.page) === page,
            });

            if (SLEEP_BETWEEN_PAGES_MS > 0) {
                sleep(SLEEP_BETWEEN_PAGES_MS / 1000);
            }
        }

    } catch (error) {
        console.error('gRPC error:', error);
    } finally {
        // Cerrar la conexión gRPC
        client.close();
    }
}

export function teardown() {
    // Cleanup si es necesario
}
