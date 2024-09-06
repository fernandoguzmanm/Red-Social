package launcher.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Users.Administrador;
import Users.Usuario;
import launcher.control.Controller;
import singleton.Conexion;
import singleton.Singleton;

public class LogInView extends JFrame {

	private Controller _ctrl;

	public LogInView(Controller ctrl) {
		super("LAGGY");
		initGUI();
		_ctrl = ctrl;
	}

	private JPanel contentPane;
	private JPasswordField textoPassword;
	private JTextField textoUsuario;

	private void initGUI() {
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 509);
		
		// Content Pane
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Label correo
		JLabel correo = new JLabel("Correo:");
		correo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		correo.setBounds(98, 91, 68, 13);
		contentPane.add(correo);
		
		// Text Field para correo
		textoUsuario = new JTextField();
		textoUsuario.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		textoUsuario.setBackground(Color.LIGHT_GRAY);
		textoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textoUsuario.setBounds(173, 88, 164, 19);
		contentPane.add(textoUsuario);
		textoUsuario.setColumns(10);
		
		// Label peticion de correo
		JLabel correoRequest = new JLabel("Introduzca su correo");
		correoRequest.setFont(new Font("Tahoma", Font.PLAIN, 13));
		correoRequest.setBounds(127, 65, 281, 13);
		contentPane.add(correoRequest);
		
		// Label password
		JLabel password = new JLabel("Password:");
		password.setFont(new Font("Tahoma", Font.PLAIN, 14));
		password.setBounds(98, 174, 95, 13);
		contentPane.add(password);
		
		// Password Field para password
		textoPassword = new JPasswordField();
		textoPassword.setBorder(new LineBorder(Color.DARK_GRAY, 2));
		textoPassword.setBackground(Color.LIGHT_GRAY);
		textoPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textoPassword.setBounds(175, 174, 126, 19);
		contentPane.add(textoPassword);
		
		// Label peticion de password
		JLabel passwordRequest = new JLabel("Introduzca su password");
		passwordRequest.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordRequest.setBounds(127, 151, 210, 13);
		contentPane.add(passwordRequest);
		
		// Boton LogIn
		JButton login = new JButton("Log in");
		login.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		login.setBackground(SystemColor.inactiveCaption);
		login.setForeground(Color.BLACK);
		login.setFont(new Font("Tahoma", Font.PLAIN, 22));
		login.addActionListener((e) -> {
			logIn();
		});
		login.setBounds(142, 231, 126, 41);
		contentPane.add(login);
		
		// Boton registro
		JButton registro = new JButton("Registrarse");
		registro.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		registro.setBackground(SystemColor.inactiveCaption);
		registro.setFont(new Font("Tahoma", Font.PLAIN, 21));
		registro.setBounds(10, 399, 164, 41);
		registro.addActionListener((e) -> {
			setVisible(false);			
			new RegisterView(_ctrl, this);
		});
		contentPane.add(registro);

		// Label registro
		JLabel textoCrearCuenta = new JLabel("Create una cuenta");
		textoCrearCuenta.setHorizontalAlignment(SwingConstants.CENTER);
		textoCrearCuenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textoCrearCuenta.setBounds(24, 361, 136, 28);
		contentPane.add(textoCrearCuenta);
		
		// Label cambio de password
		JLabel textoOlvidarContrasenha = new JLabel("He olvidado mi password");
		textoOlvidarContrasenha.setHorizontalAlignment(SwingConstants.CENTER);
		textoOlvidarContrasenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textoOlvidarContrasenha.setBounds(214, 366, 181, 18);
		contentPane.add(textoOlvidarContrasenha);
		
		// Boton cambio de password
		JButton cambiarPassword = new JButton("Cambiar password");
		cambiarPassword.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		cambiarPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cambiarPassword.setBackground(SystemColor.inactiveCaption);
		cambiarPassword.setBounds(223, 399, 164, 41);
		cambiarPassword.addActionListener((e)->{
			setVisible(false);
			new CambioPasswordView(_ctrl, this);
		});
		contentPane.add(cambiarPassword);

	}

	private void logIn() {
		String correo = textoUsuario.getText();
		String password = new String(textoPassword.getPassword());

		if (correo.isEmpty() || password.isEmpty()) {
			ViewUtils.showErrorMsg(this,"Completa todos los campos");
		} 
		else {
			if (correo.equals(Administrador.TOKEN)) {
				try {
					Integer id = Integer.parseInt(password);
					Conexion c = Singleton.getInstance().getConexion();
					PreparedStatement admin = c.getConexion().prepareStatement
							("select * from administradores a where a.id = ?");
					admin.setInt(1, id);
					ResultSet result = admin.executeQuery();
					if (result.next()) {
						setVisible(false);
						new AdminView(_ctrl, new Administrador(result));
					}
					else 
						ViewUtils.showErrorMsg("no existe admin con ese id");
						
				} catch (NumberFormatException nfe) {
					ViewUtils.showErrorMsg("id de admin invalido");
				} catch (SQLException sqle) {
					ViewUtils.showErrorMsg("error en la base de datos");
				}
			}
			else {
				Usuario usuario = _ctrl.buscarUsuario(correo, "correo");
				if (usuario != null) {
					if (password.equals(usuario.get_password())) {
						setVisible(false);
						new PrincipalView(_ctrl, null, usuario);
					} 
					else
						ViewUtils.showErrorMsg("Password incorrecta");
				} 
				else
					ViewUtils.showErrorMsg("Usuario no encontrado");
			}
		}
	}
}