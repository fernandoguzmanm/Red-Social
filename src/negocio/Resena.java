package negocio;


public class Resena extends Observacion {
	
	public int valoracion;
	
	public Resena(int valoracion) {
		this.valoracion = valoracion;
	}

    public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	

}
