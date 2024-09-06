package negocio;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Publicacion implements InteraccionPublicacion, InfoPublicacion{
	
    private String _id;
    private int _idUsuario;
    private int num_likes;
    private int num_visitas;
    
    private String _url;
    
    private List<Colaboracion> colaboraciones = new ArrayList<Colaboracion> ();
    private List<Comentario> comentarios = new ArrayList<Comentario> ();
    
	public Publicacion() {}
    
    public Publicacion(int idUsuario, String url) {
    	_idUsuario = idUsuario;
//      _id = 0;
    	File imagen = new File(url);
    	try {
			byte[] data = Files.readAllBytes(imagen.toPath());
			byte[] hash = MessageDigest.getInstance("MD5").digest(data);
			_id = new BigInteger(1, hash).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		num_likes = 0;
		num_visitas = 0;
		
		_url = url;
		
		colaboraciones = new ArrayList<>();
		comentarios = new ArrayList<>();
	}

    public Publicacion(ResultSet result, List<Comentario> listaComentario) throws SQLException {
		_id = result.getString("idpublicacion");
		_idUsuario = result.getInt("idusuario");
		num_likes = result.getInt("num_likes");
		num_visitas = 0;
		_url = result.getString("url");
		
		comentarios = listaComentario;
	}
        
    // Info de una publicacion
    
	@Override
	public String get_id() {
		return _id;
	}
	
	@Override
	public int get_user_id() {
		return _idUsuario;
	}

    @Override
	public int get_likes() {
		return num_likes;
	}

    @Override
	public int get_visitas() {
		return num_visitas;
	}	
	
    @Override
	public String get_url() {
		return _url;
	}
	
	// Interaccion con publicacion
	
	@Override
	public void add_like() {
		this.num_likes++;
	}
	
	@Override
	public void substract_like() {
		this.num_likes--;
	}

	@Override
	public void add_visita() {
		this.num_visitas++;
	}
	
	@Override
	public void add_colab(Colaboracion colab) {
		this.colaboraciones.add(colab);
	}
	
	@Override
	public void remove_colab(Colaboracion colab) {
		this.colaboraciones.remove(colab);
	}
	
	@Override
	public void add_comentario(Comentario comentario) {
		this.comentarios.add(comentario);
	}
}