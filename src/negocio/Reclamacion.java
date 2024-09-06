package negocio;


public class Reclamacion extends Observacion {
    public String motivo;

	public Reclamacion(String motivo) {
		this.motivo = motivo;
	}
    
    @Override
    public String get_extraInfo() {
    	return motivo;
    }

}
