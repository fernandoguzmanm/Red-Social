package Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Users.Usuario.Tipo;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Publicacion;

public class Administrador implements InfoUsuario{
	
	// IMPORTANTE
	// Para usar el usuario admin es preciso introducir el token de abajo
	// en el campo correo y el id en el campo password de la ventana LogIn
	
	// No permitimos que se creen nuevos administradores, se insertan en la bd
	
	// El administrador que hay creado tiene id 1
	
	public static final String TOKEN = "soyADMIN";
	
    public int _id;
    public String _nombre;
    
    public Administrador(ResultSet result) throws SQLException {
    	_id = result.getInt("id");
    	_nombre = result.getString("nombre");
    }

	@Override
	public int get_id() {
		return _id;
	}

	@Override
	public int get_numero() {
		return 0;
	}

	@Override
	public int get_edad() {
		return 0;
	}

	@Override
	public int get_strikes() {
		return 0;
	}

	@Override
	public String get_nombre() {
		return _nombre;
	}

	@Override
	public String get_username() {
		return "";
	}

	@Override
	public String get_password() {
		return "";
	}

	@Override
	public String get_correo() {
		return "";
	}

	@Override
	public String get_pais() {
		return "";
	}

	@Override
	public String get_descripcion() {
		return "";
	}

	@Override
	public String get_url() {
		return "";
	}

	@Override
	public String get_nombreEmpresa() {
		return "";
	}

	@Override
	public Tipo get_tipo() {
		return null;
	}

	@Override
	public List<InfoPublicacion> get_publicaciones() {
		return new ArrayList<>();
	}

	@Override
	public void change_pfp(String url) {}

	@Override
	public void add_publicacion(Publicacion p) {}

    
}
