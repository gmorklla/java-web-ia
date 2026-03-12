package mx.com.qtx.test.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import mx.com.qtx.core.model.Categoria;
import mx.com.qtx.core.service.IGestorDatosAltoNivel;

// @Component
public class ProbadorCapaDatosJPA implements CommandLineRunner{
	private static Logger log = LoggerFactory.getLogger(ProbadorCapaDatosJPA.class);
	
	@Autowired
	IGestorDatosAltoNivel gestorDatos;

	@Override
	public void run(String... args) throws Exception {
		Categoria cat = this.gestorDatos.leerCategoriaXID_conProductos(5);
		log.info(cat.toString());
	}

}
