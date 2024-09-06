package integracion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import singleton.Conexion;
import singleton.Singleton;
import launcher.view.ViewUtils;
import negocio.Comentario;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Publicacion;

public class DAOPublicacionImpl implements DAOPublicacion {

	@Override
	public List<InfoPublicacion> buscarPublicaciones() {
		Conexion conexion = Singleton.getInstance().getConexion();
		List<InfoPublicacion> lista = null;
		try {
			PreparedStatement consulta = conexion.getConexion().prepareStatement("SELECT * FROM Publicaciones");
			ResultSet resultado = consulta.executeQuery();
			
			List<Publicacion> listado = new ArrayList<>();
			while (resultado.next()) 
				listado.add(buscarPublicacion(resultado.getString("idPublicacion")));
			
			lista = new ArrayList<>(listado);
			resultado.close();
			consulta.close();

		} catch (Exception e) {
			System.out.println("No hay publicaciones ..." + " \n" + e);
		}
		return lista;
	}

	@Override
	public Publicacion buscarPublicacion(String id) {
		Conexion conexion = Singleton.getInstance().getConexion();
		
		List<Comentario> listaComentario = new ArrayList<>();
		Publicacion publicacion = null;

		try {
			PreparedStatement consulta = conexion.getConexion()
					.prepareStatement("select * from Publicaciones where idpublicacion = ?");
			
			consulta.setString(1, id);
			
			ResultSet resultado = consulta.executeQuery();
			if (resultado.next()) {
				try {
					PreparedStatement consulta2 = conexion.getConexion()
							.prepareStatement("SELECT * FROM comentarios WHERE idPublicacion = ?");
					
					consulta2.setString(1, resultado.getString("idpublicacion"));
					
					ResultSet resultado2 = consulta2.executeQuery();
					
					while (resultado2.next())
						listaComentario.add(new Comentario(resultado2));
					
					resultado2.close();
					consulta2.close();
					
				} catch (Exception e) {
					ViewUtils.showErrorMsg("error cargando comentarios");
				}
				
				publicacion = new Publicacion(resultado, listaComentario);
			}

			resultado.close();
			consulta.close();
		} catch (Exception e) {
			ViewUtils.showErrorMsg("error al cargar la publicacion");
		}
		
		return publicacion;
	}

	@Override
	public void crearPublicacion(InfoPublicacion publicacion) {
		Conexion conexion = Singleton.getInstance().getConexion();
		
		try {
			PreparedStatement consulta = conexion.getConexion()
					.prepareStatement("insert into Publicaciones values(?,?,?,?)");
			
			consulta.setString(1, publicacion.get_id());
			consulta.setInt(2, publicacion.get_user_id());
			consulta.setInt(3, publicacion.get_likes());
			consulta.setString(4, publicacion.get_url());
			
			consulta.executeUpdate();
			consulta.close();

		} catch (Exception e) {
			System.out.println("No se pudo registrar la publicacion" + publicacion.get_id() + "\n" + e);
		}
	}

	@Override
	public void actualizarPublicacion(Publicacion publicacion) {
		ViewUtils.showErrorMsg("No es posible actualizar una publicacion");
	}

	@Override
	public void eliminarPublicacion(InfoPublicacion publicacion) {
		Conexion conexion = Singleton.getInstance().getConexion();
		int rows = 0;
		try {
			PreparedStatement consulta = conexion.getConexion()
					.prepareStatement("DELETE FROM Publicaciones WHERE idPublicacion = ?");

			consulta.setString(1, publicacion.get_id());

			rows = consulta.executeUpdate();

			if (rows != 0)
				System.out.println("Se ha eliminado la publicacion " + publicacion.get_id() + " correctamente");
			consulta.close();

		} catch (Exception e) {
			System.out.println("No se pudo eliminar la publicacion " + publicacion.get_id() + "\n" + e);
		}

	}

	@Override
	public boolean buscarLike(InfoUsuario _usuarioViendo, InfoPublicacion _publicacion) {
		Conexion conexion = Singleton.getInstance().getConexion();
		
		try {
			PreparedStatement consulta = conexion.getConexion().prepareStatement
					("SELECT * FROM likes WHERE idusuario = ? and idpublicacion = ?");
			
			consulta.setInt(1, _usuarioViendo.get_id());
			consulta.setString(2, _publicacion.get_id());
			
			ResultSet result = consulta.executeQuery();
			if (result.next())
				return true;
			
			result.close();
			consulta.close();
			
		} catch (Exception e) {
			ViewUtils.showErrorMsg("error buscando si hay like");
		}
		return false;
	}

	@Override
	public void setLike(InfoUsuario _usuarioViendo, InfoPublicacion _publicacion, boolean like, int n_likes) {
		Conexion conexion = Singleton.getInstance().getConexion();
		
		try {
			PreparedStatement consulta = conexion.getConexion().prepareStatement
					("UPDATE publicaciones SET num_likes = ? WHERE idpublicacion = ?");
			
			consulta.setInt(1, n_likes);
			consulta.setString(2, _publicacion.get_id());
			consulta.executeUpdate();
			consulta.close();
			
			if (like) { // crear fila usuario-like
				PreparedStatement consulta2 = conexion.getConexion().prepareStatement
						("INSERT INTO likes VALUES(?, ?)");
				
				consulta2.setInt(1, _usuarioViendo.get_id());
				consulta2.setString(2, _publicacion.get_id());
				consulta2.executeUpdate();
				consulta2.close();
			}
			else { // eliminar fila usuario-like
				PreparedStatement consulta3 = conexion.getConexion().prepareStatement
						("DELETE FROM likes WHERE idusuario = ? and idpublicacion = ?");
				
				consulta3.setInt(1, _usuarioViendo.get_id());
				consulta3.setString(2, _publicacion.get_id());
				consulta3.executeUpdate();
				consulta3.close();
			}
		} catch (Exception e) {
			ViewUtils.showErrorMsg("error actualizando los likes");
		}
	}
}
