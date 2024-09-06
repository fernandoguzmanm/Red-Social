package launcher.control;


import java.util.List;

import Users.Usuario;
import integracion.DAOComentario;
import integracion.DAOComentarioImpl;
import integracion.DAOComunidad;
import integracion.DAOComunidadImpl;
import integracion.DAOObservacion;
import integracion.DAOPublicacion;
import integracion.DAOPublicacionImpl;
import integracion.DAOUsuario;
import integracion.DAOUsuarioImpl;
import negocio.Comentario;
import negocio.Comunidad;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Observacion;
import negocio.Publicacion;

public class Controller {

	private DAOUsuario daoUsuario; 
	private DAOComunidad daoComunidad;
	private DAOComentario daoComentario;
	private DAOPublicacion daoPublicacion;
	private DAOObservacion daoObservacion;
	
	public Controller() {
		daoUsuario = new DAOUsuarioImpl();
		daoComunidad = new DAOComunidadImpl();
		daoComentario = new DAOComentarioImpl();
		daoPublicacion = new DAOPublicacionImpl();
	}

	public Usuario buscarUsuario(int what, String where) {
		return daoUsuario.buscarUsuario(what, where);
	}
	
	public Usuario buscarUsuario(String what, String where) {
		return daoUsuario.buscarUsuario(what, where);
	}
	
	public void crearUsuario(Usuario usuario) {
		daoUsuario.crearUsuario(usuario);
	}
	
	public void eliminarUsuario(int id){
		daoUsuario.eliminarUsuario(id);
	}
	
	public void actualizarUsuario(String correo, String set, String whereSet) {
		daoUsuario.actualizarUsuario(correo, set, whereSet);
	}

	public List<Comunidad> getComunidades() {
		return daoComunidad.buscarComunidades();
	}
	public Comunidad buscarComunidad(String id) {
		return daoComunidad.buscarComunidad(id);
	}
	public void crearComunidad(Comunidad comunidad) {
		daoComunidad.crearComunidad(comunidad);
	}

	public void crearComentario(Comentario comentario) {
		daoComentario.crearComentario(comentario);
	}
	
	public void crearPublicacion(Publicacion p) {
		daoPublicacion.crearPublicacion(p);	
	}
	
	public List<String> get_mensajes(){
			
		
		return null;
	}

	public int buscaSeguidores(int id) {
		return daoUsuario.buscarSeg(id, "idseguido");
	}

	public int buscaSeguidos(int id) {
		return daoUsuario.buscarSeg(id, "idseguidor");
	}

	public boolean hayLike(InfoUsuario _usuarioViendo, InfoPublicacion _publicacion) {
		return daoPublicacion.buscarLike(_usuarioViendo, _publicacion);
	}

	public void darQuitarLike(InfoUsuario _usuarioViendo, InfoPublicacion _publicacion, boolean like, int n_likes) {
		daoPublicacion.setLike(_usuarioViendo, _publicacion, like, n_likes);
		
	}

	public List<InfoPublicacion> buscaPublicaciones() {
		return daoPublicacion.buscarPublicaciones();
	}

	public List<InfoUsuario> buscarUsuarios() {
		
		return daoUsuario.buscarUsuarios();
	}

	public List<Observacion> buscarObservaciones() {
		return daoObservacion.buscarObservaciones();
	}

	public boolean seguidorDe(InfoUsuario idPerfilAjeno, InfoUsuario idViewer) {
		return daoUsuario.seguidorDe(idViewer, "idSeguido", idPerfilAjeno);
	}

	public void empezarDejarDeSeguir(InfoUsuario usuarioPerfilAjeno, InfoUsuario usuarioViendo, boolean siguiendo) {
		daoUsuario.empezarDejarSeguir(usuarioPerfilAjeno, usuarioViendo, siguiendo);
	}	
	
//	public void abrirVista(String vista) {
//		switch(vista) {
//		case "principal": vistaActual.setVisible(false); vistaActual= new PrincipalView(this, null, vistaActual); 	//PrincipalView
//		case "register":																							//RegisterView
//		case "login":																								//LogInView
//		case "observacion":																							//ObservacionView
//		}
//	}
//	public void actualizarVista(JFrame vista) {
//		vistaActual = vista;
//	}

}
