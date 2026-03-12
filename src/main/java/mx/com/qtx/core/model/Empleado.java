package mx.com.qtx.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "emp_empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id_empleado")
    private Long id;

    @Column(name = "emp_nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "emp_apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "emp_apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(name = "emp_telefono", length = 20)
    private String telefono;

    @Column(name = "emp_fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;

    @Column(name = "emp_fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "emp_salario", nullable = false, precision = 12, scale = 2)
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    @Column(name = "emp_tipo_empleado", nullable = false)
    private TipoEmpleado tipoEmpleado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "emp_id_rol", nullable = false)
    private Rol rol;
}
