package mx.com.qtx.api.dto;

import java.util.List;

public record CategoriaDTO(int id, String nombre, String descripcion, List<ProductoDTO> productos) {
    
    public CategoriaDTO(int id, String nombre, String descripcion) {
        this(id, nombre, descripcion, null);
    }
    
    public String getNombre() { return nombre(); }
    public String getDescripcion() { return descripcion(); }
    public Integer getId() { return id(); }
    public List<ProductoDTO> getProductos() { return productos(); }
}
