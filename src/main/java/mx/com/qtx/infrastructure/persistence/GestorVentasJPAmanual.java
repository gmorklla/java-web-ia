package mx.com.qtx.infrastructure.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import mx.com.qtx.core.service.IGestorDatosAltoNivel;
import mx.com.qtx.core.model.Categoria;


@Repository
public class GestorVentasJPAmanual implements IGestorDatosAltoNivel {
	private static Logger log = LoggerFactory.getLogger(GestorVentasJPAmanual.class);
	
	@Autowired
	EntityManager em;

	@Override
	public Categoria leerCategoriaXID(int id)  {
		Categoria categoria = this.em.find(Categoria.class, id);
		log.info("Se ha leido con éxito la categoria " + categoria.getNombre());
		return categoria;
	}
	
	@Override
	@Transactional
	public Categoria leerCategoriaXID_conProductos(int id)  {
		log.info("La Implementacion del entity manager es :" + this.em.getClass().getName());
		Categoria categoria = this.em.find(Categoria.class, id);
		log.info("Se ha leido con éxito la categoria " + categoria.getNombre());
		if(em.isOpen()) {
			log.info("Entity manager esta abierto");
			categoria.getProductos().size();
		}
		else {
			log.warn("Entity manager esta cerrado!");
		}
		return categoria;
	}


}
