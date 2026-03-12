package mx.com.qtx.core.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "prv_proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prv_id_proveedor")
    private Long id;

    @Column(name = "prv_nombre", nullable = false)
    private String nombre;

    @Column(name = "prv_telefono")
    private String telefono;

    @OneToMany(mappedBy = "proveedor")
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "proveedor")
    private List<ProveedorPrimarioProd> productos = new ArrayList<>();
}
