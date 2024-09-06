package Users;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Publicacion;

public class CreadorContenido extends Usuario {

	private List<InfoPublicacion> _publicaciones;
    
    // Constructor para cambiar un usuario de Estandar a Creador de Contenido
    public CreadorContenido(InfoUsuario usuario){
    	super(usuario.get_nombre(), usuario.get_correo(), usuario.get_username(), usuario.get_password(),
    			usuario.get_edad(), usuario.get_numero(), usuario.get_pais(), Tipo.CREADOR_CONTENIDO,
    			usuario.get_descripcion(), usuario.get_url(), usuario.get_strikes());
    	_publicaciones = new ArrayList<>();
    }
    
    public CreadorContenido(ResultSet result, List<InfoPublicacion> publicaciones) throws SQLException {
		super(result, Tipo.CREADOR_CONTENIDO);
		_publicaciones = publicaciones;
	}

	@Override
    public void add_publicacion(Publicacion p) {
    	_publicaciones.add(p);
    }
    
    @Override
    public List<InfoPublicacion> get_publicaciones(){
    	return _publicaciones;
    }
}
