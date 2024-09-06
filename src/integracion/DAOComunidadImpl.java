package integracion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import launcher.view.ViewUtils;
import negocio.Comunidad;
import negocio.InfoComunidad;
import singleton.Conexion;
import singleton.Singleton;

public class DAOComunidadImpl implements DAOComunidad {

	@Override
	public List<Comunidad> buscarComunidades() {
		List<Comunidad> comunidades = new ArrayList<>();
		Conexion c = Singleton.getInstance().getConexion();
		
		try {
			PreparedStatement consulta = c.getConexion().prepareStatement("SELECT * FROM Comunidades");
			
			ResultSet resultado = consulta.executeQuery();
			while(resultado.next()) {
				comunidades.add(new Comunidad(resultado));
			}
			
		} catch (Exception e) {
			ViewUtils.showErrorMsg("No se pudo obtener el listado de las comunidades");
		}	
		return comunidades;
	}

	@Override
	public Comunidad buscarComunidad(String id) {
		Conexion c = Singleton.getInstance().getConexion();
		Comunidad comunidad = null;
		
		try {
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("SELECT * FROM Comunidades WHERE idComunidad = ?)");
			consulta.setString(0, id);

			ResultSet resultado = consulta.executeQuery();

			if (resultado.next()) {
				comunidad = new Comunidad(resultado);
				// faltarian los chats de la comunidad;
			}

			resultado.close();
			consulta.close();

		} catch (Exception e) {
			System.out.println("La comunidad con id" + String.valueOf(id) + " no se pudo encontrar" + " \n" + e);
		}
		return comunidad;
	}

	@Override
	public void crearComunidad(InfoComunidad comunidad) {
		Conexion c = Singleton.getInstance().getConexion();

		try {
			PreparedStatement consulta = c.getConexion().prepareStatement("INSERT INTO Comunidades VALUES(?,?,?,?,?,?,?)");
			consulta.setInt(1, comunidad.get_id());
			consulta.setInt(2, comunidad.get_num_integrantes());
			consulta.setInt(3, comunidad.get_max_integrantes());
			consulta.setString(4, comunidad.get_topico());
			consulta.setString(5, comunidad.get_nombre());
			consulta.setString(6, comunidad.get_desc());
			consulta.setString(7, comunidad.get_url());

			consulta.executeUpdate();

			consulta.close();

		} catch (Exception e) {
			System.out.println("No se pudo crear la comunidad" + comunidad.get_nombre() + " \n" + e);
		}
	}

	@Override
	public void actualizarComunidad(String whereSet, String whatSet, int id) {
		Conexion c = Singleton.getInstance().getConexion();

		try {
			PreparedStatement consulta = c.getConexion().prepareStatement(
					"update Comunidades set" + whereSet + "= ? where idcomunidad = ?");
			consulta.setString(1, whatSet);
			consulta.setInt(2, id);
			
			consulta.executeUpdate();
			consulta.close();
			
		} catch (Exception e) {
			System.out.println("No se pudo actualizar la comunidad ");
		}

	}

	@Override
	public void eliminarComunidad(int id) {
		Conexion c = Singleton.getInstance().getConexion();

		try {
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("DELETE FROM Comentarios WHERE idcomunidad = ?");
			consulta.setInt(1, id);

			consulta.executeUpdate();
			consulta.close();

		} catch (Exception e) {
			System.out.println("No se pudo eliminar la comunidad ");
		}
	}
}
