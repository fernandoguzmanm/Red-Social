package negocio;

public interface InteraccionPublicacion {
	public void add_like();
	public void substract_like();
	public void add_comentario(Comentario c);
	public void add_visita();
	public void add_colab(Colaboracion colab);
	public void remove_colab(Colaboracion colab);
}
