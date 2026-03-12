package mx.com.qtx.core.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ped_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ped_id_pedido")
    private Long id;

    @Column(name = "ped_fecha_pedido", nullable = false)
    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "ped_id_proveedor", nullable = false)
    private Proveedor proveedor;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles = new ArrayList<>();
}
