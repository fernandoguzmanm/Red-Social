package launcher.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Users.Estandar;
import Users.Usuario;
import launcher.control.Controller;

public class RegisterView extends JFrame {
	private Controller _ctrl;
	private Frame _parent;

	private JTextField textoPais;
	private JTextField textoTelefono;
	private JTextField textoCorreo;
	private JPasswordField repetirPassword;
	private JPasswordField textoPassword;
	private JTextField textoUsuario;
	private JTextField textoNombre;
	private JTextField textoDescripcion;

	private JTextField edad;

	public RegisterView(Controller ctrl, Frame parent) {
		super("REGISTRO");
		_ctrl = ctrl;
		_parent = parent;
		initGUI();
	}

	private void initGUI() {

		this.setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 509);

		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		setJMenuBar(menuBar);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2)));

		// Boton retroceder
		JButton botonRetroceder = new JButton();
		botonRetroceder.setIcon(new ImageIcon("icons/flecha.png"));
		botonRetroceder.addActionListener((e) -> {
			if (_parent != null) {
				setVisible(false);
				_parent.setVisible(true);
			}
		});
		menuBar.add(botonRetroceder);

		// Content Pane
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Label nombre
		JLabel nombre = new JLabel("Nombre:");
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nombre.setBounds(70, 15, 100, 15);
		contentPane.add(nombre);

		// Text Field para nombre
		textoNombre = new JTextField();
		textoNombre.setForeground(Color.BLACK);
		textoNombre.setBackground(Color.LIGHT_GRAY);
		textoNombre.setColumns(15);
		textoNombre.setBounds(150, 15, 140, 18);
		contentPane.add(textoNombre);
		
		// Label usuario
		JLabel usuario = new JLabel("Nombre de usuario:");
		usuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		usuario.setBounds(35, 50, 131, 13);
		contentPane.add(usuario);

		// Text Field para usuario
		textoUsuario = new JTextField();
		textoUsuario.setForeground(Color.BLACK);
		textoUsuario.setBackground(Color.LIGHT_GRAY);
		textoUsuario.setBounds(172, 47, 144, 19);
		contentPane.add(textoUsuario);
		textoUsuario.setColumns(10);

		// Label password
		JLabel password = new JLabel("Password: ");
		password.setFont(new Font("Tahoma", Font.PLAIN, 13));
		password.setBounds(75, 90, 95, 13);
		contentPane.add(password);

		// Password Field para password
		textoPassword = new JPasswordField();
		textoPassword.setBackground(Color.LIGHT_GRAY);
		textoPassword.setBounds(172, 90, 144, 16);
		contentPane.add(textoPassword);

		// Label repetir password
		JLabel repitePassword = new JLabel("Repita la password: ");
		repitePassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		repitePassword.setBounds(44, 127, 131, 13);
		contentPane.add(repitePassword);

		// Password Field para repetir password
		repetirPassword = new JPasswordField();
		repetirPassword.setBackground(Color.LIGHT_GRAY);
		repetirPassword.setBounds(172, 127, 144, 16);
		contentPane.add(repetirPassword);

		// Label correo
		JLabel correo = new JLabel("Correo:");
		correo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		correo.setBounds(44, 188, 70, 13);
		contentPane.add(correo);

		// Text Field para correo
		textoCorreo = new JTextField();
		textoCorreo.setBackground(Color.LIGHT_GRAY);
		textoCorreo.setBounds(90, 186, 236, 19);
		contentPane.add(textoCorreo);
		textoCorreo.setColumns(10);

		// Label Numero Tlfn
		JLabel telefono = new JLabel("Num Telefono:");
		telefono.setFont(new Font("Tahoma", Font.PLAIN, 12));
		telefono.setBounds(25, 317, 156, 13);
		contentPane.add(telefono);

		// Text Field para numero tlfn
		textoTelefono = new JTextField();
		textoTelefono.setBackground(Color.LIGHT_GRAY);
		textoTelefono.setBounds(108, 317, 96, 19);
		contentPane.add(textoTelefono);
		textoTelefono.setColumns(10);

		// Label pais
		JLabel pais = new JLabel("Pais:");
		pais.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pais.setBounds(257, 317, 59, 13);
		contentPane.add(pais);

		// Text Field para pais
		textoPais = new JTextField();
		textoPais.setBackground(Color.LIGHT_GRAY);
		textoPais.setBounds(288, 317, 88, 19);
		contentPane.add(textoPais);
		textoPais.setColumns(10);

		// Label Edad
		JLabel fechaNacimiento = new JLabel("Edad:");
		fechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fechaNacimiento.setBounds(44, 240, 163, 13);
		contentPane.add(fechaNacimiento);

		// Text Field para edad
		edad = new JTextField();
		edad.setBackground(Color.LIGHT_GRAY);
		edad.setBounds(83, 236, 110, 20);
		contentPane.add(edad);
		edad.setColumns(10);
		
		// Label para descripcion
		JLabel descripcion = new JLabel("Descripcion:");
		descripcion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		descripcion.setBounds(32, 270, 80, 30);
		contentPane.add(descripcion);
		
		// Text Field para descripcion
		textoDescripcion = new JTextField();
		textoDescripcion.setBackground(Color.LIGHT_GRAY);
		textoDescripcion.setColumns(40);
		textoDescripcion.setBounds(100, 277, 275, 19);
		contentPane.add(textoDescripcion);
		
		// Red Label advertencia
		JLabel warning = new JLabel("Asegurese de que todos los datos estan correctos");
		warning.setForeground(Color.RED);
		warning.setFont(new Font("Tahoma", Font.PLAIN, 12));
		warning.setBounds(75, 383, 333, 13);
		contentPane.add(warning);

		// Boton registrarse
		JButton registrarse = new JButton("Registrarse");
		registrarse.setBackground(SystemColor.inactiveCaption);
		registrarse.setFont(new Font("Tahoma", Font.PLAIN, 16));
		registrarse.setBounds(118, 400, 173, 33);
		registrarse.addActionListener((e) -> comprobarDatos());
		contentPane.add(registrarse);
	}

	private void comprobarDatos() {
		String user = textoUsuario.getText();
		String password1 = new String(textoPassword.getPassword());
		String password2 = new String(repetirPassword.getPassword());
		String correo = textoCorreo.getText();
		String pais = textoPais.getText();
		String textoTlfn = textoTelefono.getText();
		String edadTexto = edad.getText();
		String desc = textoDescripcion.getText();
		String name = textoNombre.getText();

		if (user.isBlank() || password1.isBlank() || password2.isBlank() 
				|| correo.isBlank() || edadTexto.isBlank() || name.isBlank()) {
			ViewUtils.showErrorMsg("Rellene todos los campos");
		} 
		else {
			Usuario usuario = _ctrl.buscarUsuario(correo, "correo");
			Usuario user_name = _ctrl.buscarUsuario(user, "username");

			try {
				int edad = Integer.parseInt(edadTexto);
				int telefono = Integer.parseInt(textoTlfn);
				
				if (usuario != null) {
					ViewUtils.showErrorMsg("El correo ya esta asociado a una cuenta, inicia sesion");
					setVisible(false);
					_parent.setVisible(true);
				} 
				else if (user_name != null) {
					ViewUtils.showErrorMsg("Ya existe un usuario con ese nombre de usuario, cambialo por otro");
				} 
				else if (!password1.equalsIgnoreCase(password2)) {
					ViewUtils.showErrorMsg("Las passwords deben de coincidir");
				} 
				else if (edad < 13) {
					ViewUtils.showErrorMsg("Debes ser mayor de 13 para usar la red");
				}
				else {
					Usuario u = new Estandar(correo, password1, user, name, pais, telefono, edad, desc, "");
					_ctrl.crearUsuario(u);
					setVisible(false);
					new PrincipalView(_ctrl, null, u);
				}
			} catch (NumberFormatException nfe) {
				ViewUtils.showErrorMsg("La edad/numero deben ser numericos");
			}
		}
	}
}