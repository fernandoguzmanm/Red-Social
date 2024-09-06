package launcher.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Users.Usuario;
import Users.Usuario.Tipo;
import launcher.control.Controller;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;

public class PerfilPropioView extends JFrame {

	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	private InfoUsuario _usuario;
	private Frame _parent;

	private JFileChooser _fc;

	private JPanel contentPane;

	public PerfilPropioView(Controller ctrl, Frame parent, InfoUsuario usuario) {
		super("PERFIL PROPIO");
		_ctrl = ctrl;
		_usuario = usuario;
		_parent = parent;
		initGUI();
	}

	private void initGUI() {

		this.setVisible(true);
		setBounds(100, 100, 432, 509);

		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(new Color(220, 220, 220));
		menuBar.setBackground(new Color(238, 232, 170));
		menuBar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setJMenuBar(menuBar);

		// Content Pane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Menu 3 puntos
		JMenu tresPuntos = new JMenu("");
		tresPuntos.setHorizontalAlignment(SwingConstants.CENTER);
		tresPuntos.setForeground(Color.BLACK);
		tresPuntos.setBackground(new Color(147, 112, 219));
		tresPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		tresPuntos.setIcon(new ImageIcon("icons/tresPuntos.png"));
		menuBar.add(tresPuntos);

		// Opcion observacion
		JButton realizarObservacion = new JButton("Realizar Observacion");
		realizarObservacion.addActionListener((e) -> {
			new ObservacionView(_ctrl);
		});
		tresPuntos.add(realizarObservacion);

		// Opcion cerrar sesion
		JButton cerrarSesion = new JButton("Cerrar sesion");
		cerrarSesion.addActionListener((e) -> {
			setVisible(false);
			new LogInView(_ctrl);
		});
		tresPuntos.add(cerrarSesion);

		// Opcion eliminar cuenta permanentemente
		JButton eliminarCuenta = new JButton("Eliminar cuenta");
		eliminarCuenta.addActionListener((e) -> {
			new ConfirmacionView(_ctrl, this, _usuario);
		});
		tresPuntos.add(eliminarCuenta);

		JSeparator separator = new JSeparator();
		menuBar.add(separator);

		// Boton retroceder
		JButton botonRetroceder = new JButton(new ImageIcon("icons/flecha.png"));
		botonRetroceder.addActionListener((e) -> {
			if (_parent != null) {
				setVisible(false);
				_parent.setVisible(true);
			}
		});
		menuBar.add(botonRetroceder);

		// Icono usuario
		String url = _usuario.get_url();
		url = url.isBlank() ? "icons/perfil/perfil.png" : url;

		JLabel icono = new JLabel(new ImageIcon(url));
		icono.setForeground(new Color(0, 0, 205));
		icono.setBackground(new Color(128, 128, 128));
		icono.setBounds(63, 10, 130, 130);
		contentPane.add(icono);

		// Label de username
		JLabel username = new JLabel("@" + _usuario.get_username());
		username.setFont(new Font("SimSun", Font.BOLD, 19));
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setBounds(49, 134, 190, 31);
		contentPane.add(username);

		// Label nombre empresa
		if (_usuario.get_tipo().equals(Tipo.EMPRESA)) {
			JLabel nombre_empresa = new JLabel("Empresa: " + _usuario.get_nombreEmpresa());
			nombre_empresa.setFont(new Font("SimSun", Font.BOLD, 19));
			nombre_empresa.setHorizontalAlignment(SwingConstants.CENTER);
			nombre_empresa.setBounds(40, 160, 350, 25);
			contentPane.add(nombre_empresa);

			seguidores_seguidos(175);

		} else { // Si no es user empresa
			seguidores_seguidos(150);
		}

		// Boton de cambio de foto de perfil
		JButton cambiarPerfil = new JButton("Cambiar foto de perfil");
		cambiarPerfil.setBounds(200, 29, 160, 31);
		cambiarPerfil.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		cambiarPerfil.addActionListener((e) -> {
			int sod = _fc.showOpenDialog(ViewUtils.getWindow(this));

			if (sod == JFileChooser.APPROVE_OPTION) {
				File urlFile = _fc.getSelectedFile();
				String _url = "./icons/perfil/" + urlFile.getName();
				icono.setIcon(new ImageIcon(_url));
				_usuario.change_pfp(_url);
				_ctrl.actualizarUsuario(_usuario.get_correo(), _url, "url");
			}
		});
		contentPane.add(cambiarPerfil);

		_fc = new JFileChooser();
		_fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "/icons/perfil"));

		// Boton de cambio de username
		JButton cambiarNombre = new JButton("Cambiar username");
		cambiarNombre.setBounds(200, 89, 150, 31);
		cambiarNombre.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		cambiarNombre.addActionListener((e) -> new CambiarNombreView(_ctrl, this, _usuario));
		contentPane.add(cambiarNombre);

		List<InfoPublicacion> _lista = _usuario.get_publicaciones();
		if (!_lista.isEmpty()) {
			JButton verEstadisticas = new JButton("Ver Estadisticas ");
			verEstadisticas.setFont(new Font("Dialog", Font.PLAIN, 14));
			verEstadisticas.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			verEstadisticas.setBounds(31, 284, 167, 49);
			verEstadisticas.addActionListener((e) -> {
				new EstadisticasView(_ctrl, _usuario.get_publicaciones(), this);
			});
			contentPane.add(verEstadisticas);

			JButton verPublicaciones = new JButton("Ver lista de publicaciones");
			verPublicaciones.setFont(new Font("Dialog", Font.PLAIN, 14));
			verPublicaciones.setBounds(31, 339, 224, 49);
			verPublicaciones.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			verPublicaciones.addActionListener((e)-> new ListadoPublicacionesView(_ctrl, this, _usuario, _usuario.get_publicaciones()));
			contentPane.add(verPublicaciones);
		}
	}

	private void seguidores_seguidos(int y) {
		int num_seguidores = _ctrl.buscaSeguidores(_usuario.get_id());
		int num_seguidos = _ctrl.buscaSeguidos(_usuario.get_id());

		// Label de seguidores
		JLabel seguidores = new JLabel("Seguidores: " + num_seguidores);
		seguidores.setFont(new Font("SimSun", Font.BOLD, 19));
		seguidores.setHorizontalAlignment(SwingConstants.CENTER);
		seguidores.setBounds(40, y, 150, 50);
		contentPane.add(seguidores);

		// Label de seguidos
		JLabel seguidos = new JLabel("Seguidos: " + num_seguidos);
		seguidos.setFont(new Font("SimSun", Font.BOLD, 19));
		seguidos.setHorizontalAlignment(SwingConstants.CENTER);
		seguidos.setBounds(190, y, 150, 50);
		contentPane.add(seguidos);
	}
}
