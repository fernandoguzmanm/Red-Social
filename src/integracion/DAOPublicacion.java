package integracion;

import java.util.List;

import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Publicacion;

public interface DAOPublicacion {
	public List<InfoPublicacion> buscarPublicaciones();
	public Publicacion buscarPublicacion(String id);
	public void crearPublicacion (InfoPublicacion publicacion);
	public void actualizarPublicacion (Publicacion publicacion);
	public void eliminarPublicacion (InfoPublicacion publicacion);
	public boolean buscarLike(InfoUsuario _usuarioViendo, InfoPublicacion _publicacion);
	public void setLike(InfoUsuario _usuarioViendo, InfoPublicacion _publicacion, boolean like, int n_likes);
}
