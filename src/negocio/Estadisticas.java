package negocio;


public class Estadisticas {
	
    private int num_likes;
    private int num_comentarios;
    private int num_publicaciones;

	public Estadisticas(int likes, int publicaciones, int comentarios) {
		num_likes = likes;
		num_comentarios = comentarios;
		num_publicaciones = publicaciones;
	}

	public int get_numLikes() {
		return num_likes;
	}

	public void set_likes(int likes) {
		num_likes = likes;
	}

	public int get_numComentairos() {
		return num_comentarios;
	}
	
	public void set_comentarios(int comentarios) {
		num_comentarios = comentarios;
	}
	
	public int get_numPublicaciones() {
		return num_publicaciones;
	}
	
	public void set_publicaciones(int publicaciones) {
		num_publicaciones = publicaciones;
	}

}
