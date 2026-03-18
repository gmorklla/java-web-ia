package mx.com.qtx.core.service;

import mx.com.qtx.api.dto.ProductoDTO;
import mx.com.qtx.core.model.Categoria;
import mx.com.qtx.core.model.Producto;
import mx.com.qtx.core.repository.IProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    private final IProductoRepository productoRepository;

    @Autowired
    public ProductoService(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::mapearAProductoDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductoDTO> obtenerPorId(String id) {
        return productoRepository.findById(id).map(this::mapearAProductoDTO);
    }

    public ProductoDTO guardar(ProductoDTO dto) {
        log.info("Iniciando guardado de producto con id: {}", dto.getId());
        Producto producto = mapearAProducto(dto);
        Producto guardado = productoRepository.save(producto);
        log.info("Guardado exitoso de producto: {}", guardado.getId());
        return mapearAProductoDTO(guardado);
    }

    public ProductoDTO actualizar(ProductoDTO dto) {
        log.info("Iniciando actualizacion de producto con id: {}", dto.getId());
        if (productoRepository.findById(dto.getId()).isEmpty()) {
            throw new RuntimeException("El producto con ID " + dto.getId() + " no existe.");
        }
        Producto producto = mapearAProducto(dto);
        Producto actualizado = productoRepository.save(producto);
        log.info("Actualizacion exitosa de producto: {}", actualizado.getId());
        return mapearAProductoDTO(actualizado);
    }

    public void eliminar(String id) {
        log.info("Eliminando producto con id: {}", id);
        productoRepository.deleteById(id);
    }

    private ProductoDTO mapearAProductoDTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCategoria() != null ? producto.getCategoria().getId() : null
        );
    }

    private Producto mapearAProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());

        if (dto.getIdCategoria() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getIdCategoria());
            producto.setCategoria(categoria);
        }

        return producto;
    }
}
