package mx.com.qtx.core.model;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class ProveedorPrimarioProdId implements Serializable {

    @Column(name = "ppp_id_producto")
    private String idProducto;

    @Column(name = "ppp_id_proveedor")
    private Long idProveedor;

    // equals() y hashCode() obligatorios
}
