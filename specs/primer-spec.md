Actúa como un Desarrollador Senior de Java y Spring Boot. Tengo un proyecto existente en Java 21 con Spring Boot que actualmente explora la capa de persistencia usando JPA y JDBC. 

El objetivo es extender este proyecto para agregar una interfaz web MVC básica que permita a un usuario:
1. Ver un catálogo de Categorías.
2. Hacer clic en una categoría para ver los Productos asociados.
3. Ver el detalle de un Producto específico.
4. Agregar ese producto a un "Carrito de Compras" en memoria (sesión).

Por favor, genera el código necesario siguiendo estos pasos de forma iterativa:

**PASO 1: Dependencias**
Indícame qué dependencias debo agregar al `pom.xml` para habilitar Spring Web y Thymeleaf.

**PASO 2: Capa de Repositorios (Spring Data JPA)**
Actualmente uso un `EntityManager` manual (`GestorVentasJPAmanual`), pero dado que ya tengo `spring-boot-starter-data-jpa` en el pom, quiero crear interfaces de Spring Data JPA para hacer esto más ágil:
- Crea `CategoriaRepository` y `ProductoRepository`.

**PASO 3: Lógica del Carrito de Compras**
Crea una clase `CarritoCompraService` y un modelo `ItemCarrito`.
- El servicio debe estar anotado con `@SessionScope` para mantener el estado por usuario.
- Debe tener métodos para: agregar producto (recibiendo el ID del producto y cantidad), ver ítems, y calcular el total.

**PASO 4: Capa de Controladores (Spring MVC)**
Crea un `CatalogoController` que maneje las siguientes rutas:
- `GET /` o `GET /categorias`: Muestra la lista de categorías.
- `GET /categorias/{id}`: Muestra los productos de esa categoría.
- `GET /productos/{id}`: Muestra el detalle de un producto con un formulario para agregarlo al carrito.
Crea un `CarritoController`:
- `POST /carrito/agregar`: Procesa el formulario y agrega al carrito.
- `GET /carrito`: Muestra el contenido del carrito.

**PASO 5: Vistas (Thymeleaf)**
Genera el código HTML básico usando Thymeleaf (con clases de Bootstrap por CDN para que se vea decente) para las siguientes vistas en `src/main/resources/templates/`:
1. `categorias.html`
2. `productos-por-categoria.html`
3. `detalle-producto.html`
4. `carrito.html`

**Consideraciones importantes:**
- Utiliza las entidades JPA existentes (`Categoria`, `Producto`).
- El proyecto actualmente usa un `CommandLineRunner` (`ProbadorCapaDatosJPA`). Indícame cómo desactivarlo (por ejemplo, comentando la anotación `@Component`) para que no interfiera con el arranque web.
- Maneja correctamente la carga perezosa (Lazy Loading) ya que `Categoria` tiene `@OneToMany(fetch = FetchType.LAZY) private List<Producto> productos;`.

Dame el código archivo por archivo con su ruta respectiva.

Para que tengas un entendimiento exacto del flujo arquitectónico y de navegación que espero, guíate estrictamente por los siguientes diagramas UML en formato Mermaid:

```
sequenceDiagram
    actor Usuario
    participant CatalogoCtrl as CatalogoController
    participant CarritoCtrl as CarritoController
    participant Repos as Repositories (JPA)
    participant CarritoSvc as CarritoCompraService (@SessionScope)
    participant Vistas as Vistas (Thymeleaf)

    %% 1. Ver Catálogo de Categorías
    Usuario->>CatalogoCtrl: GET /categorias (o /)
    CatalogoCtrl->>Repos: findAll() categorias
    Repos-->>CatalogoCtrl: List<Categoria>
    CatalogoCtrl->>Vistas: render(categorias.html)
    Vistas-->>Usuario: Muestra listado de Categorías

    %% 2. Ver Productos de una Categoría
    Usuario->>CatalogoCtrl: GET /categorias/{id}
    CatalogoCtrl->>Repos: findByIdWithProductos(id)
    Repos-->>CatalogoCtrl: Categoria + List<Producto>
    CatalogoCtrl->>Vistas: render(productos-por-categoria.html)
    Vistas-->>Usuario: Muestra listado de Productos

    %% 3. Ver Detalle del Producto
    Usuario->>CatalogoCtrl: GET /productos/{id}
    CatalogoCtrl->>Repos: findById(id)
    Repos-->>CatalogoCtrl: Producto
    CatalogoCtrl->>Vistas: render(detalle-producto.html)
    Vistas-->>Usuario: Muestra Detalle y Formulario 'Agregar'

    %% 4. Agregar al Carrito
    Usuario->>CarritoCtrl: POST /carrito/agregar (productoId, cantidad)
    CarritoCtrl->>Repos: findById(productoId)
    Repos-->>CarritoCtrl: Producto
    CarritoCtrl->>CarritoSvc: agregarProducto(Producto, cantidad)
    CarritoSvc-->>CarritoCtrl: Actualiza estado en Sesión
    CarritoCtrl->>Usuario: REDIRECT /carrito

    %% 5. Ver Carrito
    Usuario->>CarritoCtrl: GET /carrito
    CarritoCtrl->>CarritoSvc: getItems(), getTotal()
    CarritoSvc-->>CarritoCtrl: List<ItemCarrito>, BigDecimal
    CarritoCtrl->>Vistas: render(carrito.html)
    Vistas-->>Usuario: Muestra Carrito de Compras

```

```

graph TD
    classDef page fill:#e1f5fe,stroke:#01579b,stroke-width:2px;
    classDef action fill:#fff3e0,stroke:#e65100,stroke-width:2px,stroke-dasharray: 5 5;

    A["🏠 Pantalla: Categorías<br/>(GET /categorias)"]:::page
    B["📦 Pantalla: Productos por Categoría<br/>(GET /categorias/{id})"]:::page
    C["🔍 Pantalla: Detalle de Producto<br/>(GET /productos/{id})"]:::page
    D{"⚙️ Acción: Procesar Formulario<br/>(POST /carrito/agregar)"}:::action
    E["🛒 Pantalla: Carrito de Compras<br/>(GET /carrito)"]:::page

    A -->|Clic en una Categoría| B
    B -->|Clic en un Producto| C
    B -->|Volver| A
    C -->|Llenar cantidad y enviar form| D
    C -->|Volver| B
    D -->|Redirección automática 302| E
    E -->|Seguir comprando| A
```
