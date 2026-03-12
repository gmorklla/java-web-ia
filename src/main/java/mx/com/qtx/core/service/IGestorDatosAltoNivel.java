package mx.com.qtx.core.service;

import mx.com.qtx.core.model.Categoria;

public interface IGestorDatosAltoNivel {

	Categoria leerCategoriaXID(int id);

	Categoria leerCategoriaXID_conProductos(int id);

}
