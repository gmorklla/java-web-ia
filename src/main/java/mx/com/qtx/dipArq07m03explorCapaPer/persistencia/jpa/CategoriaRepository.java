package mx.com.qtx.dipArq07m03explorCapaPer.persistencia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Query("SELECT c FROM Categoria c JOIN FETCH c.productos WHERE c.id = :id")
    Optional<Categoria> findByIdWithProductos(@Param("id") Integer id);
}
