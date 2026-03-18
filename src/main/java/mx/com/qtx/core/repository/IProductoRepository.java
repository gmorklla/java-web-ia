package mx.com.qtx.core.repository;

import java.util.List;
import java.util.Optional;

import mx.com.qtx.core.model.Producto;

public interface IProductoRepository {
    Producto save(Producto producto);
    Optional<Producto> findById(String id);
    List<Producto> findAll();
    void deleteById(String id);
}
