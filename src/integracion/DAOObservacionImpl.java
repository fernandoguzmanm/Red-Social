package integracion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import launcher.view.ViewUtils;

import java.io.File;
import java.io.FileWriter;

import negocio.InfoObservacion;
import negocio.Observacion;
import singleton.Conexion;
import singleton.Singleton;

public class DAOObservacionImpl implements DAOObservacion {

	@Override
	public List<Observacion> buscarObservaciones() {
		List<Observacion> lista = null;
		Conexion c = Singleton.getInstance().getConexion();
		
		try {
			PreparedStatement consulta = c.getConexion().prepareStatement("SELECT * FROM Observaciones");
			
			ResultSet resultado = consulta.executeQuery();
			
			lista = new ArrayList<>();
			
			while(resultado.next()) 
				lista.add(buscarObservacion(resultado.getInt("idObservacion"), resultado.getString("tipo")));
			
			resultado.close();
			consulta.close();
		} catch (Exception e) {
			ViewUtils.showErrorMsg("No se pudo encontrar el lista de las observaciones"+ "\n" + e);
		}
		return lista;
	}

	@Override
	public Observacion buscarObservacion(int id, String tipo) {
		Conexion c = Singleton.getInstance().getConexion();
		Observacion o = null;

		try {
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("SELECT * FROM Observaciones WHERE tipo = ? and idObservacion = ?");
			consulta.setInt(0, id);
			consulta.setString(1, tipo);
			
			ResultSet resultado = consulta.executeQuery();
			
			if(resultado.next()) {
				o = new Observacion(resultado.getString("comentario"), resultado.getString("extra"));
			}
			consulta.close();
			resultado.close();
		} catch (Exception e) {
			ViewUtils.showErrorMsg("No se pudo econtrar la " + tipo + " " + id + " \n" + e);
		}
		return o;
	}

	@Override
	public void crearObservacion(InfoObservacion observacion) {
		Conexion c = Singleton.getInstance().getConexion();
		
		try {
			File obs = new File("./src/observaciones/" + String.valueOf(observacion.hashCode()) + ".txt");
			FileWriter wrt = new FileWriter(obs);
			wrt.write(observacion.get_comentario());
			wrt.close();
			
			PreparedStatement consulta = c.getConexion()
					.prepareStatement("INSERT INTO Observaciones VALUES (?, ?)");
			consulta.setInt(1, observacion.hashCode());
			consulta.setString(2, observacion.get_tipo());
			
			consulta.executeUpdate();			
			consulta.close();
			
		} catch (Exception e) {
			System.out.println("An error ocurred.");
			e.printStackTrace();
			
		}
	}

	@Override
	public void actualizarObservacion(InfoObservacion observacion) {
		try {
			File obs = new File("./src/observaciones/" + String.valueOf(observacion.hashCode()) + ".txt");
			FileWriter wrt = new FileWriter(obs);
			wrt.write(observacion.get_comentario());
			wrt.close();
		} catch (Exception e) {
			System.out.println("An error ocurred.");
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarObservacion(InfoObservacion observacion) {
		try {
			File obs = new File("./src/observaciones/" + String.valueOf(observacion.hashCode()) + ".txt");
			obs.delete();
		} catch (NullPointerException npe) {
			System.out.println("An error ocurred.");
			System.out.println("File doesn't exist.");
		}
	}
}
