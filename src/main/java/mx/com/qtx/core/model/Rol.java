package mx.com.qtx.core.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "rol_rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id_rol")
    private Integer id;

    @Column(name = "rol_nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "rol_descripcion", length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "rol")
    private List<Empleado> empleados = new ArrayList<>();
}
