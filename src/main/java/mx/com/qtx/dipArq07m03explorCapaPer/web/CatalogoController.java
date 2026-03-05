package mx.com.qtx.dipArq07m03explorCapaPer.web;

import mx.com.qtx.dipArq07m03explorCapaPer.persistencia.jpa.Categoria;
import mx.com.qtx.dipArq07m03explorCapaPer.persistencia.jpa.CategoriaRepository;
import mx.com.qtx.dipArq07m03explorCapaPer.persistencia.jpa.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class CatalogoController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping({"/", "/categorias"})
    public String mostrarCategorias(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categorias";
    }

    @GetMapping("/categorias/{id}")
    public String mostrarProductosPorCategoria(@PathVariable Integer id, Model model) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findByIdWithProductos(id);
        if (categoriaOpt.isPresent()) {
            model.addAttribute("categoria", categoriaOpt.get());
            model.addAttribute("productos", categoriaOpt.get().getProductos());
            return "productos-por-categoria";
        }
        return "redirect:/categorias";
    }

    @GetMapping("/productos/{id}")
    public String mostrarDetalleProducto(@PathVariable String id, Model model) {
        productoRepository.findById(id).ifPresent(producto -> model.addAttribute("producto", producto));
        return "detalle-producto";
    }
}
