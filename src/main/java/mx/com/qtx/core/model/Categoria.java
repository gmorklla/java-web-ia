package mx.com.qtx.core.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "cat_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id_categoria")
    private Integer id;

    @Column(name = "cat_nombre", nullable = false)
    private String nombre;

    @Column(name = "cat_descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<Producto> productos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", productos="
				+ productos + "]";
	}
    
    
}
