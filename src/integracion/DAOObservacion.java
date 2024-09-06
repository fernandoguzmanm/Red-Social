package integracion;

import java.util.List;

import negocio.InfoObservacion;
import negocio.Observacion;

public interface DAOObservacion {
	public List<Observacion> buscarObservaciones();
	public Observacion buscarObservacion(int id, String tipo);
	public void crearObservacion(InfoObservacion observacion);
	public void actualizarObservacion (InfoObservacion observacion);
	public void eliminarObservacion (InfoObservacion observacion);
}
