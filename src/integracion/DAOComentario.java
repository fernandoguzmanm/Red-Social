package integracion;

import java.util.List;

import negocio.Comentario;
import negocio.InfoComentario;

public interface DAOComentario{
	public List<Comentario> buscarComentario();
	public Comentario buscarComentario(int id);
	public void crearComentario(InfoComentario comentario);
	public void actualizarComentario (InfoComentario comentario);
	public void eliminarComentario (InfoComentario comentario);
}
