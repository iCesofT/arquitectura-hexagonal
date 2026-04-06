# Arquitectura Hexagonal - Catálogo Geográfico

<p align="center">
    <a href="https://github.com/icesoft/arquitectura-hexagonal/commits/" title="Last Commit"><img src="https://img.shields.io/github/last-commit/icesoft/arquitectura-hexagonal?style=flat"></a>
    <a href="https://github.com/icesoft/arquitectura-hexagonal/issues" title="Open Issues"><img src="https://img.shields.io/github/issues/icesoft/arquitectura-hexagonal?style=flat"></a>
    <a href="https://github.com/icesoft/arquitectura-hexagonal/blob/master/LICENSE" title="License"><img src="https://img.shields.io/badge/License-GPL%203.0-brightgreen.svg?style=flat"></a>
</p>

<p align="center">
    <img src="https://img.shields.io/badge/Java-25-blue?logo=java&logoColor=white" alt="Java 25" />
    <img src="https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen?logo=spring-boot&logoColor=white" alt="Spring Boot" />
    <img src="https://img.shields.io/badge/Architecture-Hexagonal-yellow" alt="Hexagonal Architecture" />
    <img src="https://img.shields.io/badge/gRPC-1.80.0-orange?logo=grpc&logoColor=white" alt="gRPC" />
    <img src="https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql&logoColor=white" alt="PostgreSQL" />
</p>

## 📖 Descripción

**Proyecto de demostración** que implementa la **Arquitectura Hexagonal** (Ports & Adapters) mediante un microservicio de catálogo geográfico para entidades españolas. Esta implementación sirve como ejemplo de buenas prácticas en el desarrollo de microservicios modernos con Spring Boot y Java.

### 🎯 Propósito Educativo

Este proyecto está diseñado para demostrar:

- **Arquitectura Limpia**: Separación clara entre dominio, aplicación e infraestructura
- **Principios SOLID**: Implementación práctica de buenos patrones de diseño
- **API Dual**: Exposición simultánea mediante REST y gRPC
- **Testing Avanzado**: Estrategias de testing con Cucumber, JUnit y Testcontainers
- **DevOps**: Containerización y orquestación para despliegue

## 🏗️ Arquitectura del Sistema

El proyecto sigue estrictamente los principios de **Arquitectura Hexagonal**:

```
┌─────────────────────────────────────────────────────────┐
│                    INFRASTRUCTURE                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │
│  │   REST API  │  │  gRPC API   │  │  JPA Repository │  │
│  │   (8080)    │  │   (9090)    │  │   PostgreSQL    │  │
│  └─────┬───────┘  └─────┬───────┘  └─────┬───────────┘  │
│        │                │                │               │
└────────┼────────────────┼────────────────┼───────────────┘
         │                │                │
┌────────┼────────────────┼────────────────┼───────────────┐
│        │     APPLICATION LAYER           │               │
│        │  ┌─────────────────────────────┐ │               │
│        │  │      Use Cases &            │ │               │
│        └──┤   Application Services      ├─┘               │
│           │    (Orchestration)          │                 │
│           └─────────────┬───────────────┘                 │
└─────────────────────────┼─────────────────────────────────┘
                         │
┌─────────────────────────┼─────────────────────────────────┐
│                 DOMAIN LAYER                             │
│           ┌─────────────┴───────────────┐                 │
│           │     Business Logic          │                 │
│           │   • Entities                │                 │
│           │   • Value Objects           │                 │
│           │   • Domain Services         │                 │
│           │   • Business Rules          │                 │
│           └─────────────────────────────┘                 │
└─────────────────────────────────────────────────────────┘
```

## 📁 Estructura del Proyecto

```
arquitectura-hexagonal/
│
├── LICENSE                           # Licencia GPL v3
├── README.md                         # Este archivo
│
└── catalog-service/                  # 🏠 Microservicio Principal
    ├── catalog-domain/               # 🎯 Lógica de Negocio
    │   └── src/main/java/...         # Entidades, Value Objects, Rules
    │
    ├── catalog-application/          # ⚙️ Casos de Uso
    │   └── src/main/java/...         # Application Services
    │   └── src/test/resources/       # 🥒 Cucumber Features (BDD)
    │
    ├── catalog-infrastructure/       # 🔌 Adaptadores
    │   ├── catalog-infrastructure-api-rest/      # REST API
    │   ├── catalog-infrastructure-api-grpc/      # gRPC API
    │   ├── catalog-infrastructure-api-common/    # Shared Components
    │   └── catalog-infrastructure-persistence-jpa/  # PostgreSQL JPA
    │
    ├── catalog-bootstrap/            # 🚀 Spring Boot Entry Point
    │   └── src/main/resources/       # Configuration (application.yaml)
    │
    ├── catalog-doc/                  # 📚 Documentación AsciiDoc
    │
    ├── testing/                      # 🧪 Scripts de Carga
    │   ├── loadtest-grpc.js          # Test de carga gRPC
    │   └── loadtest-rest.js          # Test de carga REST
    │
    ├── docker-compose.yml            # 🐳 Orquestación de servicios
    ├── Dockerfile                    # 📦 Imagen de contenedor
    └── pom.xml                       # 🔧 Configuración Maven
```

## 🌍 Dominio de Negocio

El sistema gestiona un **catálogo geográfico** de entidades españolas:

| Entidad | Descripción | Estándares |
|---------|-------------|------------|
| **País** | Países con códigos ISO | ISO 3166-1 (alfa-2, alfa-3) |
| **Comunidad Autónoma** | Divisiones administrativas | División territorial española |
| **Provincia** | Subdivisiones provinciales | 50 provincias oficiales |
| **Localidad** | Municipios y localidades | Registro municipal |
| **Tipo Vía** | Clasificación de vías urbanas | Nomenclatura oficial |

### Relaciones del Dominio

```
País (1) ──┐
           ├── ComunidadAutonoma (*)
           │   └── Provincia (*)
           │       └── Localidad (*)
           │           └── TipoVia (*)
           └── Provincia (*) ──┘
```

## 🚀 Stack Tecnológico

### Core Framework
- **☕ Java 25** - Project Loom (Virtual Threads)
- **🍃 Spring Boot 4.0.5** - Framework principal
- **☁️ Spring Cloud 2025.1.1** - Microservicios
- **📊 Spring Data 2025.1.4** - Acceso a datos

### APIs y Comunicación
- **🌐 REST API** - OpenAPI 3.0, SpringDoc
- **⚡ gRPC 1.80.0** - Protocol Buffers
- **🔄 Spring Web MVC** - JSON / Content negotiation

### Base de Datos y Cache
- **🐘 PostgreSQL 16** - Base de datos principal
- **🔴 Redis 7.0** - Caché distribuido
- **🗄️ Hibernate 7.3.0** - ORM
- **📋 Liquibase 5.0.2** - Migraciones

### Testing y Calidad
- **🧪 JUnit 5** - Unit testing
- **🥒 Cucumber 7.34.3** - Behavior Driven Development
- **🐳 Testcontainers 1.21.0** - Integration testing
- **🔍 AssertJ** - Fluent assertions

### Build y DevOps
- **🔨 Maven 3.8+** - Build tool
- **🐳 Docker & Docker Compose** - Containerización
- **☁️ Virtual Threads** - Escalabilidad
- **📈 Spring Actuator** - Observabilidad

## ⚡ Inicio Rápido

### Pre-requisitos

```bash
# Verificar versiones
java --version     # Java 25+
mvn --version      # Maven 3.8+
docker --version   # Docker 20+
```

### 1️⃣ Clonar y Navegar

```bash
git clone https://github.com/icesoft/arquitectura-hexagonal.git
cd arquitectura-hexagonal/catalog-service
```

### 2️⃣ Compilar el Proyecto

```bash
# Compilación completa
./mvnw clean compile

# Ejecutar tests unitarios
./mvnw test
```

### 3️⃣ Levantar Servicios

```bash
# Iniciar PostgreSQL y Redis
docker-compose up -d postgres redis

# Ejecutar la aplicación
./mvnw spring-boot:run -pl catalog-bootstrap
```

### 4️⃣ Verificar Funcionamiento

```bash
# Health Check
curl http://localhost:8080/api/v1/actuator/health

# Listar países (REST)
curl http://localhost:8080/api/v1/paises

# Swagger UI
open http://localhost:8080/openapi/ui.html
```

## 📡 APIs Disponibles

### 🌐 REST API (Puerto 8080)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/paises` | Lista paginada de países |
| GET | `/api/v1/paises/{id}` | Obtener país por ID |
| GET | `/api/v1/comunidades-autonomas` | Comunidades autónomas |
| GET | `/api/v1/provincias` | Lista de provincias |
| GET | `/api/v1/localidades` | Lista de localidades |
| GET | `/actuator/health` | Health check |
| GET | `/openapi/ui.html` | Documentación Swagger |

### ⚡ gRPC API (Puerto 9090)

| Service | Method | Descripción |
|---------|--------|-------------|
| `CatalogService` | `GetPaises()` | Lista países |
| `CatalogService` | `GetPaisById()` | País por ID |
| `CatalogService` | `GetComunidadesAutonomas()` | Comunidades |

> 📝 Ver definiciones completas en archivos `.proto` dentro de `catalog-infrastructure-api-grpc`

## 🧪 Testing y Calidad

### Testing Strategy

```bash
# Tests unitarios (rápidos)
./mvnw test

# Tests de integración (requiere Docker)
./mvnw verify

# BDD con Cucumber
./mvnw test -Dtest="*CucumberTest"

# Cobertura de código
./mvnw jacoco:report
```

### 🥒 Behavior Driven Development

Los tests BDD están ubicados en:
- **Features**: `catalog-application/src/test/resources/features/`
- **Step Definitions**: `catalog-application/src/test/java/.../steps/`

### 📊 Tests de Carga

```bash
# Test de carga REST
cd testing && ./run-loadtest-rest.sh

# Test de carga gRPC  
cd testing && ./run-loadtest-grpc.sh
```

## 🐳 Containerización

### Docker Compose Completo

```bash
# Todos los servicios (app + infraestructura)
docker-compose up -d

# Solo infraestructura
docker-compose up -d postgres redis
```

### Build de Imagen Docker

```bash
# Construir imagen
docker build -t catalog-service:latest .

# Ejecutar contenedor
docker run -p 8080:8080 catalog-service:latest
```

## 📈 Monitorización

### Endpoints de Actuator

| Endpoint | Descripción |
|----------|-------------|
| `/actuator/health` | Estado del servicio |
| `/actuator/info` | Información de la app |
| `/actuator/metrics` | Métricas de rendimiento |
| `/actuator/prometheus` | Métricas para Prometheus |

### Configuración de Logs

Los logs se pueden configurar en `application.yaml` descomentando la sección de logging.

## 🔧 Configuración

### Variables de Entorno

| Variable | Descripción | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Puerto del servidor | `8080` |
| `REDIS_HOST` | Host de Redis | `localhost` |
| `TOMCAT_MAX_THREADS` | Threads máximos | `400` |

### Perfiles de Spring

- **default**: Configuración local
- **docker**: Para contenedores
- **test**: Para testing

## 🤝 Contribuir

1. **Fork** el repositorio
2. **Crea** una branch para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. **Commit** tus cambios (`git commit -am 'Añadir nueva funcionalidad'`)
4. **Push** a la branch (`git push origin feature/nueva-funcionalidad`)
5. **Crea** un Pull Request

### Estándares de Código

- Usar **Spotless** para formateo automático
- **SpotBugs** para análisis estático
- Cobertura de tests > 80%
- Documentación JavaDoc en métodos públicos

## 📚 Documentación Adicional

- **[README Catalog Service](catalog-service/README.md)** - Documentación detallada del microservicio
- **[Documentación AsciiDoc](catalog-service/catalog-doc/)** - Generación de documentación
- **[API gRPC](catalog-service/catalog-infrastructure/catalog-infrastructure-api-grpc/)** - Definiciones Protocol Buffers

## 📄 Licencia

Este proyecto está licenciado bajo **GNU General Public License v3.0** - ver archivo [LICENSE](LICENSE) para más detalles.

## ✨ Características Destacadas

- ✅ **Arquitectura Hexagonal** completa e implementada correctamente
- ✅ **Java 25** con Virtual Threads para máximo rendimiento
- ✅ **Dual API** REST y gRPC funcionando simultáneamente  
- ✅ **BDD Testing** con Cucumber para especificaciones vivientes
- ✅ **Testcontainers** para testing de integración realista
- ✅ **Cache Distribuido** con Redis para optimización
- ✅ **Observabilidad** completa con Spring Actuator
- ✅ **Docker Ready** para despliegue en cualquier entorno

---

<p align="center">
    <strong>🏗️ Desarrollado como ejemplo de Arquitectura Hexagonal con Spring Boot y Java 25 🏗️</strong>
</p>