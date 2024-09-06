package negocio;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comentario implements InfoComentario{
	
	private int _id;
	private int _idPublicacion;
	private int _idUsuario;
	
	private String _texto;
	private String _autor;
	
	public Comentario(String contenido, String username) {
		_id = contenido.hashCode();
		_texto = contenido;
		_autor = username;
	}

	public Comentario(ResultSet result) throws SQLException {
		_id = result.getInt("idcomentario");
		_idPublicacion = result.getInt("idpublicacion");
		_idUsuario = result.getInt("idusuario");
		_texto = result.getString("texto");
	}

	// Info comentario
	
	@Override
	public int get_id() {
		return _id;
	}

	@Override
	public String get_texto() {
		return _texto;
	}

	@Override
	public int get_user_id() {
		return _idUsuario;
	}

	@Override
	public int get_publicacion_id() {
		return _idPublicacion;
	}
	
	@Override
	public String get_autor() {
		return _autor;
	}
}
