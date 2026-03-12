package mx.com.qtx.core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tie_tienda")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tie_id_tienda")
    private Integer id;

    @Column(name = "tie_razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(name = "tie_direccion", nullable = false, length = 255)
    private String direccion;

    @Column(name = "tie_telefono", length = 20)
    private String telefono;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tie_id_empleado_encargado", nullable = false)
    private Empleado encargado;
}
