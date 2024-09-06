package integracion;

import java.util.List;

import Users.Usuario;
import negocio.InfoUsuario;

public interface DAOUsuario {
	public List<InfoUsuario> buscarUsuarios();
	public Usuario buscarUsuario(int what, String where);
	public Usuario buscarUsuario(String what, String where);
	public void crearUsuario(InfoUsuario usuario);
	public void actualizarUsuario(String correo, String set, String whereSet);
	public void eliminarUsuario(int id);
	public int buscarSeg(int id, String where);
	public boolean seguidorDe(InfoUsuario idPerfil, String where, InfoUsuario idViewer);
	public void empezarDejarSeguir(InfoUsuario usuarioPerfilAjeno, InfoUsuario usuarioViendo, boolean siguiendo);
}
