package mx.com.qtx.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "cli_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id_cliente")
    private Long id;

    @Column(name = "cli_nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "cli_email", length = 150)
    private String email;

    @Column(name = "cli_telefono", length = 20)
    private String telefono;

    @Column(name = "cli_direccion_fiscal", length = 255)
    private String direccionFiscal;

    @Column(name = "cli_direccion_entrega", length = 255)
    private String direccionEntrega;

    @Column(name = "cli_saldo", nullable = false, precision = 12, scale = 2)
    private BigDecimal saldo;

    @Column(name = "cli_limite_credito", precision = 12, scale = 2)
    private BigDecimal limiteCredito;

    @OneToMany(mappedBy = "cliente")
    private List<Venta> ventas = new ArrayList<>();
}
