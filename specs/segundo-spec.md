**Contexto:**
Necesito implementar un sistema de monitoreo técnico para auditar el comportamiento de la aplicación bajo condiciones de concurrencia y gestión de sesiones.

**Objetivo:**
Implementar filtros y listeners que permitan visualizar en los logs: qué hilos están atendiendo qué peticiones, la duración de las mismas, y el conteo exacto de sesiones de usuario activas.

**Tareas requeridas:**

1.  **Configuración de Logs (MDC):**
    *   Modifica `application.properties` para definir un patrón de log que incluya variables de MDC (Mapped Diagnostic Context): `requestId` y `threadName`.

2.  **Filtro de Monitoreo de Concurrencia (Request Level):**
    *   Crea una clase `ConcurrencyTrackingFilter` que extienda de `OncePerRequestFilter`.
    *   Debe generar un `UUID` corto como `requestId` al inicio de cada petición.
    *   Debe inyectar en el MDC el `requestId` y el nombre del hilo actual (`Thread.currentThread().getName()`).
    *   Debe registrar (log) el inicio de la petición (URI y Método) y el final, calculando el tiempo de ejecución en milisegundos.
    *   Asegúrate de limpiar el MDC en un bloque `finally`.

3.  **Listener de Sesiones HTTP (Session Level):**
    *   Crea una clase `SessionMonitoringListener` que implemente `HttpSessionListener`.
    *   Utiliza un `AtomicInteger` para llevar el conteo de sesiones activas de forma thread-safe.
    *   Registra en logs cada vez que una sesión es creada o destruida, mostrando el ID de la sesión y el número total de usuarios concurrentes.

4.  **Auditoría del Ciclo de Vida del Carrito:**
    *   En la clase `CarritoCompraService` (que es `@SessionScope`), añade métodos anotados con `@PostConstruct` y `@PreDestroy`.
    *   Añade logs informativos en estos métodos para identificar exactamente cuándo Spring instancia y destruye el carrito de un usuario.

5.  **Organización de Archivos:**
    *   Ubica los componentes de monitoreo en un nuevo paquete llamado `mx.com.qtx.dipArq07m03explorCapaPer.monitoring`.

**Restricciones técnicas:**
*   Usa `jakarta.servlet` (no `javax.servlet`).
*   Asegúrate de que los componentes sean detectados por Spring (usando `@Component`).
*   El código debe ser thread-safe.

***
