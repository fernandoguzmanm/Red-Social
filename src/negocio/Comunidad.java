package negocio;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comunidad implements InfoComunidad{
	
	private String _topico;
	private String _nombre;
	private String _descripcion;
	
	private String _url;
	
	private int _idcomunidad;
	private int _numIntegrantes;
	private int _maxIntegrantes;
	
	private Chat _chats;

	public Comunidad(String nombre, String topico, int max_integrantes, String descripcion, String url)	{
		_nombre = nombre;
		_topico = topico;
		_descripcion = descripcion;
		
		_url = url;
		
		_maxIntegrantes = max_integrantes;
		_numIntegrantes = 0;
		
		_chats = new Chat();
	}
	
	public Comunidad(ResultSet resultado) throws SQLException {
		_nombre = resultado.getString("nombre");
		_topico = resultado.getString("topico");
		_descripcion = resultado.getString("descripcion");
		
		_idcomunidad = _nombre.hashCode();
		
		_url = resultado.getString("url");
		
		_maxIntegrantes = resultado.getInt("maxIntegrantes");
		_numIntegrantes = resultado.getInt("numIntegrantes");
		
		_chats = new Chat();
	}
	
	// Info comunidad
	@Override
	public String get_topico() {
		return _topico;
	}

	@Override
	public int get_num_integrantes() {
		return _numIntegrantes;
	}

	public void add_integrante() {
		_numIntegrantes++;
	}

	@Override
	public String get_nombre() {
		return _nombre;
	}

	@Override
	public int get_max_integrantes() {
		return _maxIntegrantes;
	}

	@Override
	public String get_url() {
		return _url;
	}

	@Override
	public String get_desc() {
		return _descripcion;
	}

	@Override
	public int get_id() {
		return _idcomunidad;
	}	
}
