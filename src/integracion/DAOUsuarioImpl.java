package integracion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Users.CreadorContenido;
import Users.Empresa;
import Users.Estandar;
import Users.Usuario;
import Users.Usuario.Tipo;
import launcher.view.ViewUtils;
import negocio.Comentario;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Publicacion;
import singleton.Conexion;
import singleton.Singleton;

public class DAOUsuarioImpl implements DAOUsuario {

	@Override
	public List<InfoUsuario> buscarUsuarios() {
		List<InfoUsuario> lista = null;
		Conexion conexion = Singleton.getInstance().getConexion();

		try {
			PreparedStatement consulta = conexion.getConexion().prepareStatement("SELECT * FROM Usuarios");
			ResultSet resultado = consulta.executeQuery();

			List<Usuario> listado = new ArrayList<>();
			while (resultado.next())
				listado.add(buscarUsuario(resultado.getString("idUsuario"), "idusuario"));

			lista = new ArrayList<>(listado);

			resultado.close();
			consulta.close();

		} catch (Exception e) {
			ViewUtils.showErrorMsg("No se pudo encontrar el listado de las publicaciones");
		}
		return lista;
	}

	@Override
	public Usuario buscarUsuario(int what, String where) {
		Conexion c = Singleton.getInstance().getConexion();
		Usuario user = null;

		try {
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("SELECT * FROM usuarios WHERE " + where + "=?");

			consulta.setInt(1, what);
			ResultSet result = consulta.executeQuery();

			user = cargarUsuario(result, c);

			result.close();
			consulta.close();

		} catch (Exception e) {
		}

		return user;
	}

	private Usuario cargarUsuario(ResultSet result, Conexion c) throws SQLException {
		Usuario user = null;
		List<Publicacion> publicaciones = new ArrayList<>();

		if (result.next()) {
			PreparedStatement consulta2 = c.getConexion().prepareStatement(
					"SELECT * FROM usuarios u JOIN publicaciones p ON p.idusuario = u.idusuario WHERE u.idusuario = ?;");

			consulta2.setInt(1, result.getInt("idusuario"));
			ResultSet result2 = consulta2.executeQuery();

			// cogemos las publicaciones de un usuario
			while (result2.next()) {
				List<Comentario> comentarios = new ArrayList<>();

				PreparedStatement consulta3 = c.getConexion()
						.prepareStatement("SELECT * FROM comentarios NATURAL JOIN publicaciones "
								+ "WHERE publicaciones.idpublicacion = ?");

				consulta3.setString(1, result2.getString("idpublicacion"));
				ResultSet result3 = consulta3.executeQuery();

				while (result3.next())
					comentarios.add(new Comentario(result3));

				consulta3.close();
				result3.close();

				publicaciones.add(new Publicacion(result2, comentarios));
			}

			consulta2.close();
			result2.close();
			
			List<InfoPublicacion> p = new ArrayList<>(publicaciones);

			if (result.getBoolean("empresa"))
				user = new Empresa(result, p);
			else {
				if (publicaciones.isEmpty())
					user = new Estandar(result);
				else
					user = new CreadorContenido(result, p);
			}
		}
		return user;
	}

	@Override
	public Usuario buscarUsuario(String what, String where) {
		Conexion c = Singleton.getInstance().getConexion();
		Usuario user = null;

		try {
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("SELECT * FROM usuarios WHERE " + where + "= ?");

			consulta.setString(1, what);

			ResultSet result = consulta.executeQuery();

			user = cargarUsuario(result, c);

			result.close();
			consulta.close();

		} catch (Exception e) {
		}

		return user;
	}

	@Override
	public void crearUsuario(InfoUsuario usuario) {
		Singleton s = Singleton.getInstance();
		Conexion conexion = s.getConexion();

		try {
			PreparedStatement consulta = conexion.getConexion()
					.prepareStatement("INSERT INTO Usuarios VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

			consulta.setInt(1, usuario.get_id());
			consulta.setString(2, usuario.get_nombre());
			consulta.setString(3, usuario.get_username());
			consulta.setString(4, usuario.get_correo());
			consulta.setString(5, usuario.get_password());
			consulta.setString(6, usuario.get_pais());
			consulta.setInt(7, usuario.get_numero());
			consulta.setString(8, usuario.get_descripcion());
			consulta.setBoolean(9, usuario.get_tipo() == Tipo.EMPRESA ? true : false);
			consulta.setInt(10, 0);
			consulta.setInt(11, usuario.get_edad());
			consulta.setString(12, usuario.get_url());
			consulta.setString(13, usuario.get_nombreEmpresa());

			consulta.executeUpdate();
			consulta.close();

		} catch (Exception e) {
			ViewUtils.showErrorMsg("No se pudo registrar al usuario");
		}
	}

	@Override
	public void actualizarUsuario(String correo, String set, String whereSet) {
		Conexion conexion = Singleton.getInstance().getConexion();

		try {
			PreparedStatement consulta = conexion.getConexion()
					.prepareStatement("UPDATE Usuarios SET " + whereSet + " = ? WHERE correo = ?");

			consulta.setString(1, set);
			consulta.setString(2, correo);

			consulta.executeUpdate();
			consulta.close();

		} catch (Exception e) {
			ViewUtils.showErrorMsg("No se encontro al usuario");
		}
	}

	@Override
	public void eliminarUsuario(int id) {
		Conexion conexion = Singleton.getInstance().getConexion();

		try {

			// Se borran seguidores y seguidos
			PreparedStatement consulta2 = conexion.getConexion().prepareStatement
					("delete FROM seguidos WHERE idseguidor = ?");
			consulta2.setInt(1, id);
			consulta2.executeUpdate();
			consulta2.close();
			
			PreparedStatement consulta3 = conexion.getConexion().prepareStatement
					("delete FROM seguidos WHERE idseguido = ?");
			consulta3.setInt(1, id);
			consulta3.executeUpdate();
			consulta3.close();
			
			// Se borran los likes que hubiera dado
			PreparedStatement consulta4 = conexion.getConexion().prepareStatement
					("delete from likes where idusuario = ?");
			consulta4.setInt(1, id);
			consulta4.executeUpdate();
			consulta4.close();
			
			// Buscamos todas las publicaciones que tuviera
			PreparedStatement consulta5 = conexion.getConexion().prepareStatement
					("select * from publicaciones p where idusuario = ?");
			consulta5.setInt(1, id);
			ResultSet result = consulta5.executeQuery();
//			consulta5.close();
			
			// Se borran comentarios y likes de las publicaciones
			while (result.next()) {
				String idPublicacion = result.getString("idpublicacion");
				
				PreparedStatement consulta6 = conexion.getConexion().prepareStatement
						("delete from comentarios c where c.idpublicacion = ?");
				consulta6.setString(1, idPublicacion);
				consulta6.executeUpdate();
				consulta6.close();
				
				PreparedStatement consulta7 = conexion.getConexion().prepareStatement
						("delete from likes l where l.idpublicacion = ?");
				consulta7.setString(1, idPublicacion);
				consulta7.executeUpdate();
				consulta7.close();
			}
			
			// Se borran todas las publicaciones
			PreparedStatement consulta8 = conexion.getConexion().prepareStatement
					("delete from publicaciones p where p.idusuario = ?");
			consulta8.setInt(1, id);
			consulta8.executeUpdate();
			consulta8.close();
			
			// Se borra al usuario
			PreparedStatement consulta = conexion.getConexion()
					.prepareStatement("DELETE FROM Usuarios WHERE idUsuario = ?");

			consulta.setInt(1, id);
			consulta.executeUpdate();
			consulta.close();
			
		} catch (Exception e) {
			ViewUtils.showErrorMsg("Error al borrar el usuario");
		}
	}

	@Override
	public int buscarSeg(int id, String where) {
		Conexion c = Singleton.getInstance().getConexion();

		try {
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("SELECT count(*) FROM usuarios JOIN seguidos ON usuarios.idusuario = seguidos."
							+ where + " WHERE usuarios.idusuario = ?");

			consulta.setInt(1, id);

			ResultSet result = consulta.executeQuery();

			if (result.next())
				return result.getInt(1);
		} catch (Exception e) {

		}
		return 0;
	}

	@Override
	public boolean seguidorDe(InfoUsuario idPerfilAjeno, String where, InfoUsuario idViewer) {
		Conexion c = Singleton.getInstance().getConexion();
		try {
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("SELECT * FROM seguidos WHERE idseguidor = ? and idseguido = ?");

			consulta.setInt(1, idViewer.get_id());
			consulta.setInt(2, idPerfilAjeno.get_id());

			ResultSet result = consulta.executeQuery();
			if (result.next()) {
				consulta.close();
				result.close();
				return true;
			}
			consulta.close();
			result.close();

		} catch (Exception e) {

		}
		return false;
	}

	@Override
	public void empezarDejarSeguir(InfoUsuario usuarioPerfilAjeno, InfoUsuario usuarioViendo, boolean siguiendo) {
		Conexion c = Singleton.getInstance().getConexion();
		try {

			PreparedStatement consulta;

			if (siguiendo) {
				consulta = c.getConexion().prepareStatement("INSERT INTO seguidos VALUES (?,?)");
			} 
			else {
				consulta = c.getConexion().prepareStatement("DELETE FROM seguidos WHERE idSeguidor = ? and idSeguido = ?");

			}

			consulta.setInt(1, usuarioViendo.get_id());
			consulta.setInt(2, usuarioPerfilAjeno.get_id());
			consulta.executeUpdate();
			consulta.close();
		} catch (Exception e) {

		}

	}

}
