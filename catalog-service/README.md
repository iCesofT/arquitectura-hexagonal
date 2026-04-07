# Catalog Service

<p align="center">
    <a href="https://github.com/icesoft/arquitectura-hexagonal/commits/" title="Last Commit"><img src="https://img.shields.io/github/last-commit/icesoft/arquitectura-hexagonal?style=flat"></a>
    <a href="https://github.com/icesoft/arquitectura-hexagonal/issues" title="Open Issues"><img src="https://img.shields.io/github/issues/icesoft/arquitectura-hexagonal?style=flat"></a>
    <a href="https://github.com/icesoft/arquitectura-hexagonal/blob/master/LICENSE" title="License"><img src="https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg?style=flat"></a>
</p>

![Java](https://img.shields.io/badge/Java-25-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen?logo=spring-boot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.1-brightgreen?logo=spring&logoColor=white)
![gRPC](https://img.shields.io/badge/gRPC-1.80.0-orange?logo=grpc&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql&logoColor=white)
![Build](https://img.shields.io/badge/Build-Maven-red?logo=apache-maven&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-Hexagonal-yellow)

Un microservicio empresarial diseñado con **Arquiectura Hexagonal** (Ports & Adapters) que proporciona un catálogo de entidades geográficas españolas. Este proyecto implementa las mejores prácticas de desarrollo, con soporte para API REST y gRPC, y está diseñado para ser altamente escalable y mantenible.

## 🎯 Objetivos del Proyecto

- **Demostrar Arquitectura Hexagonal**: Implementación limpia de la arquitectura de puertos y adaptadores
- **Microservicios Modernos**: Uso de las últimas versiones de Spring Boot y Java 25
- **Doble Protocolo**: Soporte simultáneo para API REST y gRPC
- **Alta Performance**: Implementación con Virtual Threads y optimizaciones avanzadas
- **Calidad de Código**: Cobertura completa de tests con Cucumber y JUnit
- **DevOps Ready**: Containerización con Docker y orquestación con Docker Compose

## 🏗️ Arquitectura

El proyecto sigue los principios de **Arquitectura Hexagonal**, garantizando:

```
┌──────────────────────────────────────────┐
│             Infrastructure              │
├─────────────────┬────────────────────────┤
│   API REST      │    API gRPC           │
│   JPA/Hibernate │    Redis Cache        │
└─────────────────┼────────────────────────┘
                  │
┌─────────────────▼────────────────────────┐
│             Application                  │
│   (Use Cases & Application Services)     │
└─────────────────┬────────────────────────┘
                  │
┌─────────────────▼────────────────────────┐
│              Domain                      │
│   (Entities, Value Objects, Rules)      │
└──────────────────────────────────────────┘
```

### Módulos

- **`catalog-domain`**: Lógica de negocio y entidades del dominio
- **`catalog-application`**: Casos de uso y servicios de aplicación
- **`catalog-infrastructure`**: Adaptadores externos (REST, gRPC, JPA)
  - `catalog-infrastructure-api-rest`: API REST con OpenAPI 3.0
  - `catalog-infrastructure-api-grpc`: API gRPC con Protocol Buffers
  - `catalog-infrastructure-api-common`: Componentes compartidos
  - `catalog-infrastructure-persistence-jpa`: Persistencia con PostgreSQL
- **`catalog-bootstrap`**: Configuración de Spring Boot y punto de entrada
- **`catalog-doc`**: Documentación generada con AsciiDoctor

## 📊 Entidades del Dominio

El servicio gestiona las siguientes entidades geográficas siguiendo estándares oficiales:

- **País**: Entidades con códigos ISO 3166-1 (alfa-2 y alfa-3)
- **Comunidad Autónoma**: Divisiones administrativas españolas
- **Provincia**: Subdivisiones provinciales
- **Localidad**: Municipios y localidades
- **Tipo Vía**: Clasificación de vías urbanas

## 🚀 Tecnologías y Versiones

### Core Framework
- **Java**: 25 (Project Loom - Virtual Threads)
- **Spring Boot**: 4.0.5
- **Spring Cloud**: 2025.1.1
- **Spring Data**: 2025.1.4

### API & Communication
- **gRPC**: 1.80.0 con Spring gRPC 1.0.2
- **OpenAPI**: 3.0.2 con SpringDoc
- **Protocol Buffers**: 4.34.1

### Database & Cache
- **PostgreSQL**: 16+ (Alpine)
- **Hibernate**: 7.3.0.Final
- **Redis**: Para caché distribuido
- **Liquibase**: 5.0.2 para migraciones

### Build & Quality
- **Maven**: 3.8.0+
- **Lombok**: 1.18.44
- **MapStruct**: 1.6.3 para mapeo de objetos
- **Spotless**: 3.3.0 para formateo de código
- **SpotBugs**: 4.9.8.2 para análisis estático

### Testing
- **JUnit**: 5+ con Platform Suite
- **Cucumber**: 7.34.3 para BDD
- **AssertJ**: Para assertions fluidas

### Observabilidad
- **Spring Boot Actuator**: Métricas y health checks
- **Micrometer**: Métricas de aplicación

## ⚡ Funcionalidades Destacadas

- **Doble API**: Expone tanto REST como gRPC simultáneamente
- **Virtual Threads**: Máxima escalabilidad con Project Loom
- **Cache Distribuido**: Redis para mejorar rendimiento
- **Paginación Inteligente**: Configuración centralizada y validación
- **Health Checks**: Monitorización completa del estado
- **Jackson Blackbird**: Serialización JSON optimizada
- **Connection Pooling**: HikariCP configurado para alta concurrencia

## 🛠️ Requisitos del Sistema

### Desarrollo
- **Java 25+** (recomendado: Eclipse Temurin)
- **Maven 3.8.0+**
- **Docker y Docker Compose**
- **Git 2.30+**

### Producción
- **8GB RAM mínimo** (recomendado: 16GB)
- **4 CPU cores** (recomendado: 8 cores)
- **PostgreSQL 16+**
- **Redis 7.0+**

## 🚀 Inicio Rápido

### 1. Clonar el Repositorio

```bash
git clone https://github.com/icesoft/arquitectura-hexagonal.git
cd catalog-service
```

### 2. Compilación

```bash
./mvnw clean compile
```

### 3. Ejecución con Docker

```bash
docker-compose up -d
```

### 4. Verificar Funcionamiento

```bash
# Health Check
curl http://localhost:8080/actuator/health

# API REST - Países
curl http://localhost:8080/api/v1/paises

# Swagger UI
open http://localhost:8080/openapi/ui.html
```

## 📋 API Endpoints

### REST API (Puerto 8080)

- **GET** `/api/v1/paises` - Lista paginada de países
- **GET** `/api/v1/paises/{id}` - Obtener país por ID
- **GET** `/api/v1/comunidades-autonomas` - Lista de comunidades autónomas
- **GET** `/api/v1/provincias` - Lista de provincias
- **GET** `/api/v1/localidades` - Lista de localidades
- **GET** `/actuator/health` - Health check
- **GET** `/openapi/ui.html` - Documentación interactiva

### gRPC API (Puerto 9090)

- `CatalogService.GetPaises()` - Lista países
- `CatalogService.GetPaisById()` - País por ID
- `CatalogService.GetComunidadesAutonomas()` - Comunidades autónomas
- Ver archivos `.proto` en `catalog-infrastructure-api-grpc`

## 🧪 Ejecución de Tests

```bash
# Tests unitarios
./mvnw test

# Tests de integración (requiere Docker)
./mvnw verify

# Tests Cucumber (BDD)
./mvnw test -Dtest="*CucumberTest"

# Cobertura de código
./mvnw jacoco:report
```

## 🔧 Configuración

### Variables de Entorno

```env
# Base de datos
DATABASE_URL=jdbc:postgresql://localhost:5432/catalog
DATABASE_USERNAME=catalog
DATABASE_PASSWORD=catalog

# Cache Redis
REDIS_HOST=localhost
REDIS_PORT=6379

# Puertos
SERVER_PORT=8080
GRPC_SERVER_PORT=9090

# Optimizaciones
VIRTUAL_THREADS_ENABLED=true
JACKSON_BLACKBIRD_ENABLED=true
```

### Perfiles de Spring

- **`default`**: Desarrollo local
- **`docker`**: Ejecución con Docker Compose
- **`prod`**: Producción

## 📖 Documentación

- **API REST**: Disponible en `/swagger-ui.html`
- **Arquitectura**: Documentos en `catalog-doc/`
- **Cucumber Reports**: Tests BDD en formato HTML

## 🤝 Contribución

1. **Fork** el proyecto
2. **Crea** una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** tus cambios (`git commit -m 'Add: AmazingFeature'`)
4. **Push** a la rama (`git push origin feature/AmazingFeature`)
5. **Abre** un Pull Request

### Estándares de Código

- Código formateado con **Spotless**
- Análisis estático con **SpotBugs**
- Tests con cobertura mínima del **80%**
- Documentación con **Javadoc** completo

## 📄 Licencia

Este proyecto está bajo la licencia GPL-3.0. Ver el archivo [LICENSE](LICENSE) para más detalles.

## 👥 Autores

- **Francisco Javier Ahijado** - *Desarrollo inicial* - [@icesoft](https://github.com/icesoft)

## 🔗 Enlaces Útiles

- [Spring Boot 4.0 Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [gRPC Java Documentation](https://grpc.io/docs/languages/java/)
- [Hexagonal Architecture Guide](https://alistair.cockburn.us/hexagonal-architecture/)
- [Java 25 Documentation](https://docs.oracle.com/en/java/javase/25/)

---

**¿Preguntas o problemas?** Abre un [issue](https://github.com/icesoft/arquitectura-hexagonal/issues) en GitHub.
