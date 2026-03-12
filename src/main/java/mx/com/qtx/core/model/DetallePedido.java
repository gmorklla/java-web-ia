package mx.com.qtx.core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dtp_detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dtp_num_detalle")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dtp_id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "dtp_id_producto", nullable = false)
    private Producto producto;

    @Column(name = "dtp_cantidad", nullable = false)
    private Integer cantidad;
}
