package Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.InfoPublicacion;
import negocio.Publicacion;

public class Estandar extends Usuario {

	public Estandar(String correo, String password, String username, String nombre, 
			String pais, int telefono, int edad, String desc, String url) {
		super(nombre, correo, username, password, edad, telefono, pais, Tipo.ESTANDAR, desc, url, 0);
	}

	public Estandar(ResultSet result) throws SQLException {
		super(result, Tipo.ESTANDAR);
	}

	@Override
	public void add_publicacion(Publicacion p) {}

	@Override
	public List<InfoPublicacion> get_publicaciones() {
		return new ArrayList<>();
	}
}
