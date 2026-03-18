# Guía de Configuración del Proyecto

Este documento describe los pasos necesarios para configurar y ejecutar el proyecto **dipArq07m03explorCapaPer-1** localmente.

## Requisitos Previos

Asegúrate de tener instalados los siguientes componentes:

- **Java JDK 17** o superior (Recomendado JDK 17 o 21).
- **MySQL Server** (u otro cliente compatible con MySQL).
- **Maven** (opcional, el proyecto incluye un wrapper `mvnw`).

## Configuración de la Base de Datos

1. **Crear la Base de Datos**:
   El proyecto está configurado para conectarse a una base de datos llamada `sis_ventas`.
   ```sql
   CREATE DATABASE sis_ventas;
   ```

2. **Verificar Credenciales**:
   Asegúrate de que el usuario y la contraseña en `src/main/resources/application.properties` coincidan con tu configuración local de MySQL.
   ```properties
   spring.datasource.username=root
   spring.datasource.password=TUN_PASSWORD_AQUI
   ```

3. **Inicialización de Tablas**:
   El proyecto utiliza Hibernate para validar el esquema (`spring.jpa.hibernate.ddl-auto=validate`). Asegúrate de que las tablas necesarias existan antes de ejecutar la aplicación.

## Ejecución de la Aplicación

Puedes ejecutar la aplicación utilizando el Maven Wrapper incluido:

### Windows (PowerShell/CMD)
```powershell
./mvnw.cmd spring-boot:run
```

### Linux/macOS
```bash
./mvnw spring-boot:run
```

La aplicación se iniciará en el puerto **8081** por defecto.

## Endpoints Principales

### Vistas Web (Thymeleaf)
- **Catálogo de Categorías**: [http://localhost:8081/categorias](http://localhost:8081/categorias)
- **Carrito de Compras**: [http://localhost:8081/carrito](http://localhost:8081/carrito)
- **Administración de Productos**: [http://localhost:8081/admin/productos](http://localhost:8081/admin/productos)

### APIs REST
- **API de Productos**: [http://localhost:8081/api/productos](http://localhost:8081/api/productos)

## Documentación de API (OpenAPI / Swagger)

El proyecto cuenta con documentación interactiva de la API accesible una vez que el servidor está en funcionamiento:

- **Swagger UI**: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- **Definición OpenAPI (JSON)**: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

## Errores Comunes

### Error de Versión de Java (UnsupportedClassVersionError)
Si recibes un error indicando que la clase fue compilada por una versión más reciente de Java (ej. `class file version 65.0`) y tu entorno solo reconoce versiones anteriores (ej. `up to 61.0`):

1. **Causa**: El proyecto está configurado para **Java 17** en el `pom.xml`, pero el entorno de ejecución es inferior.
2. **Solución**: Instala el JDK 17 o 21 y asegúrate de que tu variable `JAVA_HOME` apunte a la instalación correcta.
3. **Limpieza**: Después de cambiar la versión, se recomienda limpiar los archivos compilados:
   ```powershell
   ./mvnw.cmd clean spring-boot:run
   ```

## Monitoreo y Logs

El proyecto incluye configuraciones de monitoreo técnico (MDC):
- Los logs mostrarán el `threadName` y `requestId` para facilitar el rastreo de peticiones concurrentes.
- Se audita la creación y destrucción de sesiones en la consola.
