package mx.com.qtx.api.controller;

import mx.com.qtx.api.dto.ProductoDTO;
import mx.com.qtx.core.service.ProductoService;
import mx.com.qtx.infrastructure.persistence.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productos")
public class AdminProductoController {

    private final ProductoService productoService;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public AdminProductoController(ProductoService productoService, CategoriaRepository categoriaRepository) {
        this.productoService = productoService;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "admin/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new ProductoDTO("", "", "", null, null));
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") String id, Model model) {
        ProductoDTO producto = productoService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de producto invalido:" + id));
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "admin/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") ProductoDTO producto) {
        if (productoService.obtenerPorId(producto.getId()).isPresent()) {
            productoService.actualizar(producto);
        } else {
            productoService.guardar(producto);
        }
        return "redirect:/admin/productos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") String id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Producto eliminado correctamente.");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar el producto porque está siendo referenciado en otros registros (ej. pedidos, ventas o historial).");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ocurrió un error inesperado al intentar eliminar el producto.");
        }
        return "redirect:/admin/productos";
    }
}
