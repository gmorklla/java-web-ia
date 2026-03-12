package mx.com.qtx.infrastructure.persistence;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.qtx.api.dto.CategoriaDTO;
import mx.com.qtx.api.dto.CategoriaProductoDTO;

@Repository	
public class GestorVentasDAO implements IGestorDatos {
	@Autowired
	DataSource ds;
	
	private static Logger log = LoggerFactory.getLogger(GestorVentasDAO.class);
	
	public CategoriaDTO leerCategoriaXID(int id) throws SQLException {
		
		String sqlConsulta = "SELECT cat_id_categoria, cat_nombre, cat_descripcion "
							+ "FROM cat_categoria "
							+ "WHERE cat_id_categoria = ?";
		
		try(Connection con = this.ds.getConnection()){
			try( PreparedStatement pst= con.prepareStatement(sqlConsulta)){
				pst.setInt(1, id);
				try(ResultSet rs = pst.executeQuery()){
					if(rs.next()) {
						String nombre = rs.getString("cat_nombre");
						String descripcion = rs.getString("cat_descripcion");
						return new CategoriaDTO(id, nombre, descripcion);
					}
				}
				
			}
		}
		
		return null;
	}
	
	public List<CategoriaProductoDTO> leerProductosConCategorias(int id) throws SQLException{
		List<CategoriaProductoDTO> lstCatsYprods = new ArrayList<>();
		String sqlConsulta = "SELECT cat_id_categoria, cat_nombre, cat_descripcion, "
				                  + "prd_nombre, prd_descripcion, prd_precio "
				           + "FROM cat_categoria "
				           + "JOIN prd_producto ON prd_id_categoria = cat_id_categoria "
				           + "WHERE cat_id_categoria = ?";
		
		try(Connection con = this.ds.getConnection()){
			try( PreparedStatement pst= con.prepareStatement(sqlConsulta)){
				pst.setInt(1, id);
				try(ResultSet rs = pst.executeQuery()){
					while(rs.next()) {
						String catNombre = rs.getString("cat_nombre");
						String catDireccion = rs.getString("cat_descripcion");
						String prdNombre = rs.getString("prd_nombre");
						String prdDireccion = rs.getString("prd_descripcion");
						BigDecimal prdPrecio = rs.getBigDecimal("prd_precio");
						
						lstCatsYprods.add(new CategoriaProductoDTO(id, catNombre, catDireccion, prdNombre, prdDireccion, prdPrecio));
					}
				}
				
			}
		}		
		return lstCatsYprods;
	}
	
	public void probarJdbc() throws SQLException {
		String charsetActivo = Charset.defaultCharset().toString();
		log.info("charset usado:" + charsetActivo);
		log.info("Funcionando");
		String sqlConsulta = "SELECT * FROM cat_categoria";
		try( Connection con = this.ds.getConnection() ){
			if(con != null) {
				log.info("Se obtuvo la conexion a la BD a " + con.getCatalog());
			}
			try (Statement stmt = con.createStatement()){
				stmt.execute(sqlConsulta);
				try( ResultSet rs = stmt.getResultSet()){
					while(rs.next()) {
						int catId = rs.getInt("cat_id_categoria");
						String catNombre = rs.getString("cat_nombre");
						log.info(catId + ": " + catNombre);
					}
				}
			}
		}
	}
	
	public int crearCategoria(CategoriaDTO categoria) throws SQLException {

	    String sql = """
	        INSERT INTO cat_categoria (cat_nombre, cat_descripcion)
	        VALUES (?, ?)
	    """;

	    try (Connection con = ds.getConnection();
	         PreparedStatement pst = con.prepareStatement(
	                 sql, Statement.RETURN_GENERATED_KEYS)) {

	        pst.setString(1, categoria.nombre());
	        pst.setString(2, categoria.descripcion());

	        pst.executeUpdate();

	        try (ResultSet rs = pst.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	        }
	    }
	    //TODO Mejorar estartegia de manejo de error ¿Que pasa si hay un error originado por otra causa?
	    throw new SQLException("No se pudo generar el id de la categoría");
	}
	
	public void actualizarCategoria(CategoriaDTO categoria) throws SQLException {

	    String sql = """
	        UPDATE cat_categoria
	        SET cat_nombre = ?, cat_descripcion = ?
	        WHERE cat_id_categoria = ?
	    """;

	    try (Connection con = ds.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setString(1, categoria.nombre());
	        pst.setString(2, categoria.descripcion());
	        pst.setInt(3, categoria.id());

	        int filas = pst.executeUpdate();

	        if (filas != 1) {
	            throw new SQLException(
	                "Actualización fallida. Categoría inexistente o múltiple."
	            );
	        }
	    }
	}
	
	public void eliminarCategoria(int idCategoria) throws SQLException {

	    String sql = "DELETE FROM cat_categoria WHERE cat_id_categoria = ?";

	    try (Connection con = ds.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setInt(1, idCategoria);
	        pst.executeUpdate();
	    }
	}

	public List<CategoriaDTO> listarCategorias() throws SQLException {

	    List<CategoriaDTO> categorias = new ArrayList<>();

	    String sql = """
	        SELECT cat_id_categoria, cat_nombre, cat_descripcion
	        FROM cat_categoria
	        ORDER BY cat_nombre
	    """;

	    try (Connection con = ds.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql);
	         ResultSet rs = pst.executeQuery()) {

	        while (rs.next()) {
	            categorias.add(new CategoriaDTO(
	                rs.getInt("cat_id_categoria"),
	                rs.getString("cat_nombre"),
	                rs.getString("cat_descripcion")
	            ));
	        }
	    }

	    return categorias;
	}


}
