package mx.com.qtx.core.service;

import mx.com.qtx.api.dto.CategoriaDTO;
import mx.com.qtx.api.dto.ProductoDTO;
import mx.com.qtx.core.model.Categoria;
import mx.com.qtx.infrastructure.persistence.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatalogoService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaDTO> obtenerTodasCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::mapearACategoriaDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoriaDTO> obtenerCategoriaConProductos(Integer id) {
        return categoriaRepository.findByIdWithProductos(id)
                .map(this::mapearACategoriaDTOCompleta);
    }

    private CategoriaDTO mapearACategoriaDTO(Categoria categoria) {
        return new CategoriaDTO(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }

    private CategoriaDTO mapearACategoriaDTOCompleta(Categoria categoria) {
        List<ProductoDTO> productosDto = null;
        if (categoria.getProductos() != null) {
            productosDto = categoria.getProductos().stream()
                    .map(p -> new ProductoDTO(p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getCategoria().getId()))
                    .collect(Collectors.toList());
        }
        return new CategoriaDTO(categoria.getId(), categoria.getNombre(), categoria.getDescripcion(), productosDto);
    }
}
