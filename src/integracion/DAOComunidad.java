package integracion;

import java.util.List;

import negocio.Comunidad;
import negocio.InfoComunidad;

public interface DAOComunidad {
	public List<Comunidad> buscarComunidades();
	public Comunidad buscarComunidad(String id);
	public void crearComunidad(InfoComunidad comunidad);
	public void actualizarComunidad (String whereSet, String whatSet, int id);
	public void eliminarComunidad (int id);

}
