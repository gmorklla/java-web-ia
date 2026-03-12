package mx.com.qtx.api.dto;

import java.math.BigDecimal;

public record CategoriaProductoDTO(int catIdCategoria, String catNombre, String catDescripcion, 
						String prdNombre, String prdDescripcion, BigDecimal prdPrecio) {

}
