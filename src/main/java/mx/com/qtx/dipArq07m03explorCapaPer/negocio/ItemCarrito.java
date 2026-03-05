package mx.com.qtx.dipArq07m03explorCapaPer.negocio;

import mx.com.qtx.dipArq07m03explorCapaPer.persistencia.jpa.Producto;
import java.math.BigDecimal;

public class ItemCarrito {

    private Producto producto;
    private int cantidad;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        if (producto != null && producto.getPrecio() != null && producto.getPrecio().compareTo(BigDecimal.ZERO) > 0) {
            return producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        }
        return BigDecimal.ZERO;
    }
}
