package mx.com.qtx.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.com.qtx.core.model.Producto;

import mx.com.qtx.core.repository.IProductoRepository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String>, IProductoRepository {
    @Override
    Optional<Producto> findById(String id);
}
