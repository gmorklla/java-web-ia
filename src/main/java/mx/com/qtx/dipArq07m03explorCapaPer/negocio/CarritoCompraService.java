package mx.com.qtx.dipArq07m03explorCapaPer.negocio;

import mx.com.qtx.dipArq07m03explorCapaPer.persistencia.jpa.Producto;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@SessionScope
public class CarritoCompraService {

    private static final Logger log = LoggerFactory.getLogger(CarritoCompraService.class);

    private List<ItemCarrito> items = new ArrayList<>();

    @PostConstruct
    public void onInit() {
        log.info("*** CARRITO CREADO: Bean instanciado temporalmente en memoria para este usuario ***");
    }

    @PreDestroy
    public void onDestroy() {
        log.info("*** CARRITO DESTRUIDO: Memoria liberada. El usuario {} perdio su sesion. ***", this.hashCode());
    }

    public void agregarProducto(Producto producto, int cantidad) {
        Optional<ItemCarrito> itemExistente = items.stream()
                .filter(item -> item.getProducto().getId().equals(producto.getId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            items.add(new ItemCarrito(producto, cantidad));
        }
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void vaciarCarrito() {
    	items.clear();
    }
}
