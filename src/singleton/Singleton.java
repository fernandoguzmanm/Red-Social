package singleton;

public class Singleton {
	
	private static Singleton instance;
	private Conexion conexion;

	private Singleton() {
		conexion = new Conexion();
	}
	
	public static Singleton getInstance() {
		if (instance == null)
			instance = new Singleton();
		return instance;
	}
	
	public Conexion getConexion() {
		return conexion;
	}
}
