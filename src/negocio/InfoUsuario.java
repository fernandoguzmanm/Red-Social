package negocio;

import java.util.List;

import Users.Usuario.Tipo;

public interface InfoUsuario {
	public int get_id();
	public int get_numero();
	public int get_edad();
	public int get_strikes();
	
	public String get_nombre();
	public String get_username();
	public String get_password();
	public String get_correo();
	public String get_pais();
	public String get_descripcion();
	public String get_url();
	public String get_nombreEmpresa();
	
	public Tipo get_tipo();
	
	public List<InfoPublicacion> get_publicaciones();
	
	public void change_pfp(String url);
	public void add_publicacion(Publicacion p);
}
