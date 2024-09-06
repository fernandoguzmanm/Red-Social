package Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Publicacion;

public abstract class Usuario implements InfoUsuario{
	
    private int _idusuario;
    private int _numero;
    private int _numStrikes;
    private int _edad;
    
    private String _nombre;
    private String _correo;
    private String _username;
    private String _password;
    private String _pais;
    private String _desc;
    private String _url;
  
    private Tipo _tipo;
    
    public enum Tipo{ESTANDAR, CREADOR_CONTENIDO, EMPRESA};

	public Usuario(String nombre, String correo, String username, String password, int edad,
			int numero, String pais, Tipo tipo, String desc, String url, int numStrikes) {
		_nombre = nombre;
		_correo = correo;
		_username = username;
		_password = password;
		_pais = pais;
		_desc = desc;
		_url = url;
		
		_idusuario = _username.hashCode();	
		_edad = edad;
		_numero = numero;
		_numStrikes = numStrikes;
		
		_tipo = tipo;
	}

	public Usuario(ResultSet result, Tipo tipo) throws SQLException {		
		_idusuario = result.getInt("idusuario");
		_numero = result.getInt("numero");
		_numStrikes = result.getInt("numStrikes");
		_edad = result.getInt("edad");
		
		_nombre = result.getString("nombre");
		_correo = result.getString("correo");
		_username = result.getString("username");
		_password = result.getString("password");
		_pais = result.getString("pais");
		_desc = result.getString("descripcion");
		_url = result.getString("url");
	
		_tipo = tipo;
	}
	
	public abstract void add_publicacion(Publicacion p);
	public abstract List<InfoPublicacion> get_publicaciones();
	
	public void change_pfp(String url) {
		_url = url;
	}
	
	// Info de un usuario
	
	@Override
	public int get_id() {
		return _idusuario;
	}

	@Override
	public String get_nombre() {
		return _nombre;
	}

	@Override
	public String get_username() {
		return _username;
	}
	
	@Override
	public String get_password() {
		return _password;
	}

	@Override
	public String get_correo() {
		return _correo;
	}

	public void setCorreo(String correo) {
		_correo = correo;
	} 
	
	@Override
	public String get_pais() {
		return _pais;
	}
	
	@Override
	public int get_numero() {
		return _numero;
	}
	
	@Override
	public int get_edad() {
		return _edad;
	}
	
	@Override
	public String get_descripcion() {
		return _desc;
	}
	
	@Override
	public Tipo get_tipo() {
		return _tipo;
	}
	
	@Override
	public int get_strikes() {
		return _numStrikes;
	}
	
	@Override
	public String get_url() {
		return _url;
	}
	
	@Override
	public String get_nombreEmpresa() {
		return "";
	}
}