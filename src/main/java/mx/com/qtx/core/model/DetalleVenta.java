package mx.com.qtx.core.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "dtv_detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dtv_num_detalle")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dtv_num_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "dtv_id_producto", nullable = false)
    private Producto producto;

    @Column(name = "dtv_cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "dtv_precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    public BigDecimal calcularImporte() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
