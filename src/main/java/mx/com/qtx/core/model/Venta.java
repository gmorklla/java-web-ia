package mx.com.qtx.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "ven_venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ven_num_venta")
    private Long id;

    @Column(name = "ven_fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "ven_direccion_entrega", length = 255)
    private String direccionEntrega;

    @Column(name = "ven_telefono", length = 20)
    private String telefono;

    @Column(name = "ven_saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ven_id_empleado_vendedor", nullable = false)
    private Empleado vendedor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ven_id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();
}
