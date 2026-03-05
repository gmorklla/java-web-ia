1. ¿Cómo funciona la sesión que se implementó?
En una aplicación web tradicional, los servidores no tienen estado (son stateless). Esto significa que el servidor no recuerda quién eres entre una petición (clic) y otra.

Para resolver esto, usamos la anotación @SessionScope. Lo que hace internamente Spring Boot es lo siguiente:

Instancias únicas por usuario: Cada vez que un nuevo navegador (usuario) entra a la tienda, Spring Boot crea una nueva instancia exclusiva de la clase 

CarritoCompraService
 solo para ese usuario.
El Proxy Mágico: Aunque nuestro 

CarritoController
 es un Singleton (solo existe uno en todo el servidor para atender a todos los usuarios simultáneamente), Spring inyecta un "Proxy" (un intermediario falso) en lugar del servicio real.
Enrutamiento automático: Cuando un usuario hace una petición (ej. POST /carrito/agregar), ese Proxy detecta automáticamente el ID de sesión (JSESSIONID) que viene en las cookies del navegador del usuario y redirige la llamada a la instancia exacta de 

CarritoCompraService
 que le pertenece a esa persona.
Así logramos que si Juan agrega unos tenis y María agrega una camisa al mismo tiempo, las listas de items en memoria no se mezclen.

2. ¿Por qué se usó @Service y no @Component?
Técnicamente hablando, funcionarían exactamente igual. En Spring framework, @Service es simplemente una especialización (un estereotipo) de la anotación genérica @Component. Si vas al código fuente de Spring, verás que @Service está anotada internamente con @Component.

Sin embargo, decidimos usar @Service por tres motivos principales de Diseño y Arquitectura de Software:

Semántica y Claridad (Domain-Driven Design): @Service le dice inmediatamente a cualquier otro programador que lea el código: "Esta clase contiene la Lógica de Negocio de la aplicación". Un carrito de compras no es solo una utilidad genérica de infraestructura (donde encajaría mejor @Component), sino que define operaciones vitales del dominio del negocio (agregar productos, calcular subtotales, reglas de descuentos, etc).
Patrón de Capas: Mantiene limpia la arquitectura de capas típica de Spring:
Capa de Presentación: Controladores (@Controller)
Capa de Lógica de Negocio: Servicios (@Service)
Capa de Acceso a Datos: Repositorios (@Repository)
Evolución futura: Si en el futuro quisieras agregar transaccionalidad al carrito (por ejemplo, con @Transactional al momento de convertir el carrito en un pedido en la base de datos), o intercepción de métodos (AOP), Spring está optimizado para aplicar este tipo de aspectos a las clases en la capa de Servicio.
En resumen: Usamos @SessionScope para aislar los datos cliente por cliente, y usamos @Service (en lugar de @Component) para darle un significado arquitectónico correcto, dejando claro que ahí vive la inteligencia del negocio.
