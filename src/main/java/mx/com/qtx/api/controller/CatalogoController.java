package mx.com.qtx.api.controller;

import mx.com.qtx.api.dto.CategoriaDTO;
import mx.com.qtx.core.service.CatalogoService;
import mx.com.qtx.infrastructure.persistence.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    // Temporalmente dejado para no romper el detalle de producto, aunque debería ir al service en un mundo ideal
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping({"/", "/categorias"})
    public String mostrarCategorias(Model model) {
        model.addAttribute("categorias", catalogoService.obtenerTodasCategorias());
        return "categorias";
    }

    @GetMapping("/categorias/{id}")
    public String mostrarProductosPorCategoria(@PathVariable Integer id, Model model) {
        Optional<CategoriaDTO> categoriaOpt = catalogoService.obtenerCategoriaConProductos(id);
        if (categoriaOpt.isPresent()) {
            model.addAttribute("categoria", categoriaOpt.get());
            model.addAttribute("productos", categoriaOpt.get().productos());
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
