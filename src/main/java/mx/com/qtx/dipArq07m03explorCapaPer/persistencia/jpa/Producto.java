package mx.com.qtx.dipArq07m03explorCapaPer.persistencia.jpa;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "prd_producto")
public class Producto {

    @Id
    @Column(name = "prd_id_producto", length = 50)
    private String id;

    @Column(name = "prd_nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "prd_descripcion", length = 255)
    private String descripcion;

    @Column(name = "prd_precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prd_id_categoria", nullable = false)
    private Categoria categoria;

    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private InventarioProducto inventario;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public InventarioProducto getInventario() {
		return inventario;
	}

	public void setInventario(InventarioProducto inventario) {
		this.inventario = inventario;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", categoria=" + categoria.getId() + ", inventario=" + inventario + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
    
    
}
