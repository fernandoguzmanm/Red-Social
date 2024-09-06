package negocio;


public class PeticionCambio extends Observacion {
    public String area_afectada;

    
    public PeticionCambio(String area_afectada) {
		this.area_afectada = area_afectada;
	}

	@Override 
    public String get_extraInfo() {
    	return area_afectada;
    }
}
