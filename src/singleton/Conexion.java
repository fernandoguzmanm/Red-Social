package singleton;

import java.sql.*;

public class Conexion {
	//grupog-is2
	private String nombreBD = ""; // inserta el nombre de la base de datos aqui
	private String usuario = "root"; // inserta el usuario de mySQL aqui, root por defecto
	private String password = ""; // inserta la password de mySQL aqui
	private String url = "jdbc:mysql://localhost:3306/" + nombreBD + "?useUnicode=true&use"
			+ "JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	Connection conn = null;
	
	public Conexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, usuario, password);
			
			System.out.println("Conexion establecida");
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.out.println("Error al crear la conexion");
			e.printStackTrace();
		}
	}
	
	public Connection getConexion() {
		return conn;
	}
	
	public void desconectar() {
		conn = null;
	}
}