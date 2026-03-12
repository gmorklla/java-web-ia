package mx.com.qtx.core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ppp_proveedor_primario_prod")
public class ProveedorPrimarioProd {

    @EmbeddedId
    private ProveedorPrimarioProdId id;

    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name = "ppp_id_producto")
    private Producto producto;

    @ManyToOne
    @MapsId("idProveedor")
    @JoinColumn(name = "ppp_id_proveedor")
    private Proveedor proveedor;

    @Column(name = "ppp_prioridad", nullable = false)
    private Integer prioridad;

    @Column(name = "ppp_condiciones")
    private String condiciones;
}
