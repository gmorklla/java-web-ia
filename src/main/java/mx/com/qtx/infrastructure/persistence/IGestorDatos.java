package mx.com.qtx.infrastructure.persistence;

import java.util.List;

import mx.com.qtx.api.dto.CategoriaDTO;
import mx.com.qtx.api.dto.CategoriaProductoDTO;

public interface IGestorDatos {
	CategoriaDTO leerCategoriaXID(int id) throws Exception;
	List<CategoriaProductoDTO> leerProductosConCategorias(int id) throws Exception;
}
