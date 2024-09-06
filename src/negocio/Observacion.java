package negocio;

public class Observacion implements InfoObservacion{

	public String _comentario;
	public String _tipo;
	public String extra_info;

	// Constructor vacio
	public Observacion() {
	}

	// Constructor con parámetros
	public Observacion(String comentario, String tipo) {
		_comentario = comentario;
		_tipo = tipo;
		extra_info = "";
	}

	// Info observacion
	
	@Override
	public String get_comentario() {
		return _comentario;
	}
	@Override
	public String get_extraInfo() {
		return extra_info;
	}
	
	@Override
	public String get_tipo() {
		return _tipo;
	}
}