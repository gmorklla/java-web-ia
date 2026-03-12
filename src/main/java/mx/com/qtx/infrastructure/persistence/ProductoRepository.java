package mx.com.qtx.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.com.qtx.core.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {

}
