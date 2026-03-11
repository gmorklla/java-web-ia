# Guía de Configuración del Proyecto

Este documento describe los pasos necesarios para configurar y ejecutar el proyecto **dipArq07m03explorCapaPer-1** localmente.

## Requisitos Previos

Asegúrate de tener instalados los siguientes componentes:

- **Java JDK 21** o superior.
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

Una vez que la aplicación esté en funcionamiento, puedes acceder a las siguientes rutas:

- **Catálogo de Categorías**: [http://localhost:8081/categorias](http://localhost:8081/categorias)
- **Carrito de Compras**: [http://localhost:8081/carrito](http://localhost:8081/carrito)

## Errores Comunes

### Error de Versión de Java (UnsupportedClassVersionError)
Si recibes un error indicando que la clase fue compilada por una versión más reciente de Java (ej. `class file version 65.0`) y tu entorno solo reconoce versiones anteriores (ej. `up to 61.0`):

1. **Causa**: El proyecto requiere **Java 21**, pero estás usando **Java 17**.
2. **Solución Recomendada**: Instala el JDK 21 y asegúrate de que tu variable `JAVA_HOME` apunte a la nueva instalación.
3. **Solución Alternativa (Downgrade)**: Si no puedes instalar Java 21, puedes bajar la versión en el archivo `pom.xml`:
   - Cambia `<java.version>21</java.version>` por `<java.version>17</java.version>`.
   - **IMPORTANTE**: Después de cambiar la versión, debes limpiar los archivos compilados anteriormente ejecutando:
     ```powershell
     ./mvnw.cmd clean spring-boot:run
     ```
   - *Nota: Esto solo funcionará si el proyecto no utiliza características específicas de Java 21.*

## Monitoreo y Logs

El proyecto incluye configuraciones de monitoreo técnico (MDC):
- Los logs mostrarán el `threadName` y `requestId` para facilitar el rastreo de peticiones concurrentes.
- Se audita la creación y destrucción de sesiones en la consola.
