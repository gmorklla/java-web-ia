package mx.com.qtx.api.dto;

import java.math.BigDecimal;

public record ProductoDTO(String id, String nombre, String descripcion, BigDecimal precio, Integer idCategoria) {
    public String getId() { return id(); }
    public String getNombre() { return nombre(); }
    public String getDescripcion() { return descripcion(); }
    public BigDecimal getPrecio() { return precio(); }
    public Integer getIdCategoria() { return idCategoria(); }
}
