package negocio;

import java.util.ArrayList;
import java.util.List;

import Users.Usuario;

public class Colaboracion {
    public List<Usuario> usuarios = new ArrayList<> ();

	public Colaboracion() {
		usuarios = new ArrayList<>();
	}
    
	public void add_user (Usuario user) {
		this.usuarios.add(user);
	}
	
	public void remove_user(Usuario user) {
		this.usuarios.remove(user);
	}

}
