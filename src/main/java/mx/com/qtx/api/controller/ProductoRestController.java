package mx.com.qtx.api.controller;

import mx.com.qtx.api.dto.ProductoDTO;
import mx.com.qtx.core.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // Permite pruebas desde clientes externos/navegadores
@Tag(name = "Productos", description = "API para la gestión de productos")
public class ProductoRestController {

    private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class);
    private final ProductoService productoService;

    @Autowired
    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Recupera una lista de todos los productos disponibles en el catálogo.")
    public List<ProductoDTO> listarTodos() {
        log.info("REST: Listando todos los productos");
        return productoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Recupera los detalles de un producto específico dado su ID.")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable String id) {
        log.info("REST: Consultando producto con id: {}", id);
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Registra un nuevo producto en el sistema.")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO dto) {
        log.info("REST: Creando nuevo producto: {}", dto.getId());
        try {
            ProductoDTO guardado = productoService.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            log.error("REST: Error al crear producto", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable String id, @RequestBody ProductoDTO dto) {
        log.info("REST: Actualizando producto: {}", id);
        try {
            // Aseguramos que el ID del path coincida con el del DTO si es necesario, 
            // o simplemente usamos el del DTO segun la logica del servicio.
            ProductoDTO actualizado = productoService.actualizar(dto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            log.error("REST: Error al actualizar producto", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        log.info("REST: Eliminando producto: {}", id);
        try {
            productoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("REST: Error al eliminar producto", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
