package mx.com.qtx.api.controller;

import mx.com.qtx.core.service.CarritoCompraService;
import mx.com.qtx.infrastructure.persistence.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarritoController {

    @Autowired
    private CarritoCompraService carritoService;

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@RequestParam String productoId, @RequestParam int cantidad) {
        productoRepository.findById(productoId).ifPresent(producto -> {
            carritoService.agregarProducto(producto, cantidad);
        });
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model) {
        model.addAttribute("items", carritoService.getItems());
        model.addAttribute("total", carritoService.getTotal());
        return "carrito";
    }
}
