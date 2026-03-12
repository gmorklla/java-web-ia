package mx.com.qtx.core.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "inv_inventario_producto")
public class InventarioProducto {

    @Id
    @Column(name = "inv_id_producto", length = 50)
    private String id;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "inv_id_producto", nullable = false)
    private Producto producto;

    @Column(name = "inv_existencia", nullable = false)
    private Integer existencia;

    @Column(name = "inv_existencia_minima", nullable = false)
    private Integer existenciaMinima;

    @Column(name = "inv_existencia_maxima", nullable = false)
    private Integer existenciaMaxima;

    @Column(name = "inv_punto_reorden", nullable = false)
    private Integer puntoReorden;

    @Column(name = "inv_fecha_actualizacion", nullable = false)
    private LocalDate fechaActualizacion;
}
