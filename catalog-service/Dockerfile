# ---------- STAGE 1: Builder ----------
FROM eclipse-temurin:25-jre-alpine AS builder

WORKDIR /app

COPY catalog-bootstrap/target/catalog-bootstrap-*.jar app.jar

RUN java -Djarmode=layertools -jar app.jar extract && \
    rm app.jar

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:25-jre-alpine

LABEL maintainer="iCesofT" \
      description="Catalog Service - Hexagonal Architecture" \
      version="0.0.1"

# Create non-root user
RUN adduser -D appuser && \
    apk add --no-cache ca-certificates

WORKDIR /app

ENV SERVER_PORT=8080

# Copy layers from builder - order matters for caching
COPY --from=builder --chown=appuser:appuser /app/dependencies/ ./
COPY --from=builder --chown=appuser:appuser /app/snapshot-dependencies/ ./
COPY --from=builder --chown=appuser:appuser /app/spring-boot-loader/ ./
COPY --from=builder --chown=appuser:appuser /app/application/ ./

# Set JVM options for container
# - UseContainerSupport: JVM respects cgroup CPU/memory limits automatically
# - InitialRAMPercentage=50 / MaxRAMPercentage=75: leaves headroom for metaspace and native memory
# - MaxMetaspaceSize=256m: Spring Boot apps typically need 200-300m
ENV JAVA_OPTS="-XX:+UseContainerSupport \
               -XX:InitialRAMPercentage=50 \
               -XX:MaxRAMPercentage=75 \
               -XX:+UseZGC \
               -XX:MaxMetaspaceSize=256m \
               -XX:+UseStringDeduplication \
               -XshowSettings:system \
               -XX:+ExitOnOutOfMemoryError"

EXPOSE ${SERVER_PORT}
EXPOSE ${GRPC_SERVER_PORT:-9090}

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
    CMD wget -qO- http://localhost:${SERVER_PORT}/actuator/health || exit 1

USER appuser

# exec replaces the shell with the JVM process so SIGTERM is forwarded correctly (graceful shutdown)
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]
