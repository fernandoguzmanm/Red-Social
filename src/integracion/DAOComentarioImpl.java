package integracion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import negocio.Comentario;
import negocio.InfoComentario;
import singleton.Conexion;
import singleton.Singleton;

public class DAOComentarioImpl implements DAOComentario {

	@Override
	public List<Comentario> buscarComentario() {
		List<Comentario> comentarios = new ArrayList<>();
		Conexion c = Singleton.getInstance().getConexion();
		
		try {
			PreparedStatement consulta = c.getConexion().prepareStatement("SELECT * FROM Comentarios");
			ResultSet resultado = consulta.executeQuery();
			
			while(resultado.next()) 
				comentarios.add(buscarComentario(resultado.getInt("idComentario")));
			
			resultado.close();
			consulta.close();

		} catch (Exception e) {
			System.out.println("No hay ningun comentario...");
		}
		return null;
	}

	@Override
	public Comentario buscarComentario(int id) {
		Conexion c = Singleton.getInstance().getConexion();
		Comentario comentario = null;
		try {
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("SELECT * FROM Comentarios WHERE idComentario = ?)");
			consulta.setInt(1, id);
			
			ResultSet resultado = consulta.executeQuery();
			
			if(resultado.next()) {
				 comentario = new Comentario(resultado);
			}
			
			resultado.close();
			consulta.close();

		} catch (Exception e) {
			System.out.println("El comentario "+ id + " no se pudo encontrar" + " \n" + e);
		}
		return comentario;
	}

	@Override
	public void crearComentario(InfoComentario comentario) {
		Conexion c = Singleton.getInstance().getConexion();
		
		try {
			PreparedStatement consulta = c.getConexion().prepareStatement("INSERT INTO Comentarios VALUES(?,?)");
			consulta.setInt(1, comentario.get_id());
			consulta.setString(2, comentario.get_texto());
			
			consulta.executeUpdate();
			
			consulta.close();

		} catch (Exception e) {
			System.out.println("No se pudo crear el comentario" + " \n" + e);
		}
	}

	@Override
	public void actualizarComentario(InfoComentario comentario) {
		System.out.println("No es posible modificar un comentario");
	}

	@Override
	public void eliminarComentario(InfoComentario comentario) {
		Conexion c = Singleton.getInstance().getConexion();
		
		try {
			PreparedStatement consulta = c.getConexion().prepareStatement("DELETE FROM Comentarios WHERE idComentario = ?");
			consulta.setInt(1, comentario.get_id());
			
			consulta.executeUpdate();			
			consulta.close();

		} catch (Exception e) {
			System.out.println("No se pudo eliminar el comentario"+ " \n" + e);
		}
	}

}
