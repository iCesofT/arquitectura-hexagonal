# Liquibase Database Migration

Este módulo utiliza Liquibase para gestionar las migraciones de base de datos de PostgreSQL.

## Estructura de archivos

```
src/main/liquibase/
├── db.changelog-master.xml          # Archivo maestro que incluye todos los changesets
├── liquibase.properties            # Configuración para conexión real a PostgreSQL
├── liquibase-offline.properties    # Configuración para generar SQL sin BD (RECOMENDADO)
├── data/                           # Archivos de datos para carga inicial
│   ├── comunidades-autonomas.csv  # Datos en CSV
│   ├── provincias.sql             # Datos en SQL
│   └── tipos-via.csv              # Datos de tipos de vía en CSV
└── changelog/
    ├── 001-ddl-create-comunidad-autonoma-table.xml
    ├── 002-ddl-create-provincia-table.xml
    ├── 003-ddl-create-localidad-table.xml
    ├── 004-ddl-create-tipo-via-table.xml
    ├── 101-dml-load-comunidad-autonoma-data.xml # Carga datos desde CSV
    ├── 102-dml-load-provincia-data.xml        # Carga datos desde SQL
    └── 104-dml-load-tipo-via-data.xml        # Carga datos desde CSV
```

## Comandos Maven disponibles

### **GENERAR SCRIPTS SQL (SIN EJECUTAR EN BD) - Recomendado**

```bash
# OPCIÓN 1: Desde el directorio raíz del proyecto
cd /ruta/al/proyecto/catalog-service
mvn liquibase:updateSQL -pl infrastructure/infrastructure-persistence-jpa

# OPCIÓN 2: Desde el directorio del módulo  
cd infrastructure/infrastructure-persistence-jpa
mvn liquibase:updateSQL

# Generar SQL de rollback
mvn liquibase:rollbackSQL -Dliquibase.rollbackCount=1

# Validar formato de changesets
mvn liquibase:validate
```

### **Comandos que requieren conexión a BD real**

```bash
# Para usar conexión real, especifica el otro archivo de propiedades:
mvn liquibase:status -Dliquibase.propertyFile=src/main/liquibase/liquibase.properties

# Aplicar cambios a la BD (requiere BD funcionando)
mvn liquibase:update -Dliquibase.propertyFile=src/main/liquibase/liquibase.properties

# Rollback de cambios (requiere BD funcionando)
mvn liquibase:rollback -Dliquibase.propertyFile=src/main/liquibase/liquibase.properties -Dliquibase.rollbackCount=1
```

## Configuración de base de datos

Asegúrate de tener PostgreSQL ejecutándose con:
- Base de datos: `catalog_db`
- Usuario: `catalog_user`
- Contraseña: `catalog_password`
- Puerto: `5432`

## Tablas creadas

1. **COMUNIDAD_AUTONOMA**
   - ID: CHAR(2) PRIMARY KEY
   - DENOMINACION: VARCHAR(50) NOT NULL
   - RCP: CHAR(2)
   - DIR2: CHAR(2)

2. **PROVINCIA**
   - ID: CHAR(2) PRIMARY KEY  
   - DENOMINACION: VARCHAR(50) NOT NULL
   - CA_ID: CHAR(2) NOT NULL (FK → COMUNIDAD_AUTONOMA.ID)

3. **LOCALIDAD**
   - ID: CHAR(4) PRIMARY KEY
   - DENOMINACION: VARCHAR(50) NOT NULL
   - PROVINCIA_ID: CHAR(2) NOT NULL (FK → PROVINCIA.ID)
   - ÍNDICE ÚNICO: (PROVINCIA_ID, DENOMINACION)

4. **TIPO_VIA**  
   - ID: CHAR(2) PRIMARY KEY
   - CLAVE: VARCHAR(50) NOT NULL
   - DENOMINACION: VARCHAR(50)

## Archivos generados

Los scripts SQL se generarán en:
- `target/liquibase/migration.sql` - SQL para aplicar cambios (CREATE TABLE, etc.)
- `target/liquibase/rollback.sql` - SQL para deshacer cambios (DROP TABLE, etc.)

**Importante:** Estos archivos se generan sin necesidad de tener PostgreSQL instalado o funcionando, usando el archivo `liquibase-offline.properties`.

## Métodos de carga de datos

Liquibase ofrece varios métodos para cargar datos iniciales:

### 1. **Usando `<insert>` (Recomendado para pocos registros)**
```xml
<insert tableName="TIPO_VIA">
    <column name="ID" value="CL"/>
    <column name="CLAVE" value="CALLE"/>
</insert>
```

### 2. **Usando `<loadData>` desde archivos CSV**
```xml
<loadData file="data/comunidades-autonomas.csv" 
          tableName="COMUNIDAD_AUTONOMA"
          separator=",">
    <column name="ID" type="STRING"/>
</loadData>
```

### 3. **Usando `<sqlFile>` desde archivos SQL**
```xml
<sqlFile path="data/provincias.sql" encoding="UTF-8"/>
```

### 4. **Usando `<sql>` inline**
```xml
<sql>
    INSERT INTO TABLA VALUES ('valor1', 'valor2');
</sql>
```

**Ventajas por método:**
- **`<insert>`**: Más control, rollback automático, mejor para validaciones
- **`<loadData>`**: Perfecto para grandes volúmenes, formato estándar (usado para TIPO_VIA y COMUNIDAD_AUTONOMA)
- **`<sqlFile>`**: Flexibilidad total, reutilización de scripts existentes (usado para PROVINCIA)
- **`<sql>`**: Rápido para consultas simples

**Datos incluidos:**
- **42 tipos de vía** completos (alameda, calle, avenida, plaza, etc.)
- **19 comunidades autónomas** de España
- **30 provincias** de ejemplo