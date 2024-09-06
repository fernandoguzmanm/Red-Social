package Users;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import negocio.InfoPublicacion;
import negocio.Publicacion;

public class Empresa extends Usuario {
	
	private String _nombreEmpresa;
	private List<InfoPublicacion> _publicaciones;
	
    public Empresa(ResultSet result, List<InfoPublicacion> publicaciones) throws SQLException {
		super(result, Tipo.EMPRESA);
		_publicaciones = publicaciones;
		_nombreEmpresa = result.getString("nombreEmpresa");
	}

	@Override
	public void add_publicacion(Publicacion p) {
		_publicaciones.add(p);
	}

	@Override
	public List<InfoPublicacion> get_publicaciones() {
    	return _publicaciones;
	}
	
	@Override
	public String get_nombreEmpresa() {
		return _nombreEmpresa;
	}

}
