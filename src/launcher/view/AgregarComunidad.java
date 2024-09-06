package launcher.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Users.Administrador;
import launcher.control.Controller;
import negocio.Comunidad;
import negocio.InfoUsuario;

public class AgregarComunidad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField _textoNombre;
	private JTextField _textoTopico;
	private JTextArea _textoDescripcion;
	private JTextField _textoIntegrantes;
	private JTextField _textoURL;
	
	private Controller _ctrl;
	private Frame _parent;
	private Administrador _admin;

	public AgregarComunidad(Controller ctrl, Frame parent, Administrador admin) {
		super("ADMIN");
		initGUI();
		_ctrl = ctrl;
		_parent = parent;
		_admin = admin;
	}
	
	private void initGUI() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 509);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(78, 143, 163));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		// Label textoIni
		JLabel textoIni = new JLabel("Creacion de Comunidad");
		textoIni.setHorizontalAlignment(SwingConstants.CENTER);
		textoIni.setFont(new Font("Tahoma", Font.BOLD, 20));
		textoIni.setBounds(79, 32, 250, 20);
		contentPane.add(textoIni);
		
		// Label nombre
		JLabel nombre = new JLabel("Nombre de la Comunidad:");
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		nombre.setBounds(35, 75, 176, 18);
		contentPane.add(nombre);

		// Text Field para nombre
		_textoNombre = new JTextField();
		_textoNombre.setForeground(Color.BLACK);
		_textoNombre.setBackground(Color.WHITE);
		_textoNombre.setBounds(232, 76, 144, 19);
		contentPane.add(_textoNombre);
		_textoNombre.setColumns(10);

		// Label topico
		JLabel topico = new JLabel("Topico de la Comunidad: ");
		topico.setFont(new Font("Tahoma", Font.PLAIN, 15));
		topico.setBounds(35, 104, 176, 18);
		contentPane.add(topico);

		// Text Field para topico
		_textoTopico = new JTextField();
		_textoTopico.setBackground(Color.WHITE);
		_textoTopico.setBounds(232, 105, 144, 19);
		contentPane.add(_textoTopico);
		
		// Label descripcion
		JLabel descripcion = new JLabel("Descripcion de la Comunidad:");
		descripcion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		descripcion.setBounds(35, 139, 198, 18);
		contentPane.add(descripcion);
		
		// ScrollPane para descripcion
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 168, 341, 94);
		contentPane.add(scrollPane);
		
		// Text Area para descripcion
		_textoDescripcion = new JTextArea();
		scrollPane.setViewportView(_textoDescripcion);
		_textoDescripcion.setLineWrap(true);
		_textoDescripcion.setWrapStyleWord(true);
		_textoDescripcion.setColumns(10);
		
		// Label numIntegrantes
		JLabel numIntegrantes = new JLabel("Numero de integrantes de la Comunidad:");
		numIntegrantes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		numIntegrantes.setBounds(35, 287, 272, 18);
		contentPane.add(numIntegrantes);
		
		// Text Field para descripcion
		_textoIntegrantes = new JTextField();
		_textoIntegrantes.setBounds(326, 288, 50, 19);
		contentPane.add(_textoIntegrantes);
		_textoIntegrantes.setColumns(10);
		
		// Label URL
		JLabel URL = new JLabel("URL icon de la Comunidad:");
		URL.setFont(new Font("Tahoma", Font.PLAIN, 15));
		URL.setBounds(35, 329, 183, 18);
		contentPane.add(URL);
		
		// Text Field para URL
		_textoURL = new JTextField();
		_textoURL.setBounds(35, 358, 341, 19);
		contentPane.add(_textoURL);
		_textoURL.setColumns(10);
		
		// Button OK
		JButton okButton = new JButton("OK");
		okButton.addActionListener( (e)-> comprobarDatos());
		okButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		okButton.setBounds(65, 411, 89, 23);
		contentPane.add(okButton);
		
		// Button Cancel
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {
			if (_parent != null) {
				setVisible(false);
				_parent.setVisible(true);
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cancelButton.setBounds(257, 411, 89, 23);
		contentPane.add(cancelButton);
	}
	private void comprobarDatos() {
		String nombre = _textoNombre.getText();
		String topico = _textoTopico.getText();
		String descripcion = _textoDescripcion.getText();
		String numIntegrantes = _textoIntegrantes.getText();
		String URL = _textoURL.getText();
		if (nombre.isBlank() || topico.isBlank() || descripcion.isBlank() 
				|| numIntegrantes.isBlank() || URL.isBlank()) {
			ViewUtils.showErrorMsg("Rellene todos los campos");
		} 
		else {
			Comunidad comunidad = _ctrl.buscarComunidad(nombre);

			try {
				int numInt = Integer.parseInt(numIntegrantes);
				
				if (comunidad != null) {
					ViewUtils.showErrorMsg("Ya existe esta comunidad");
					setVisible(false);
					_parent.setVisible(true);
				} 
				else if (numInt <= 0) {
					ViewUtils.showErrorMsg("El numero de integrantes debe ser mayor que 0");
				}
				else {
					Comunidad c = new Comunidad(nombre,topico,numInt,descripcion,URL);
					_ctrl.crearComunidad(c);
					setVisible(false);
					new ComunidadView(_ctrl, null, c, _admin);
				}
			} catch (NumberFormatException nfe) {
				ViewUtils.showErrorMsg("El numero de integrantes debe ser un caracter numerico");
			}
		}
	}

}
