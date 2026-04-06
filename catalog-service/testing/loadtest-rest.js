import http from 'k6/http';
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
        http_req_duration: ['p(95)<500'],
        http_req_failed: ['rate<0.05'],
    },
};

const BASE_URL = 'http://localhost:8080/api/v1/localidades';
const PAGE_SIZE = Number(__ENV.PAGE_SIZE ?? 50);
const SLEEP_BETWEEN_PAGES_MS = Number(__ENV.SLEEP_BETWEEN_PAGES_MS ?? 0);

export default function () {

    let first = http.get(`${BASE_URL}?page=0&size=${PAGE_SIZE}`);

    check(first, { 'status 200': (r) => r.status === 200 });

    let body = first.json();
    let totalItems = body.totalItems || 0;
    let totalPages = Math.ceil(totalItems / PAGE_SIZE);

    for (let page = 1; page < totalPages; page++) {
        let res = http.get(`${BASE_URL}?page=${page}&size=${PAGE_SIZE}`);
        check(res, { 'status 200': (r) => r.status === 200 });

        sleep(SLEEP_BETWEEN_PAGES_MS / 1000);
    }
}