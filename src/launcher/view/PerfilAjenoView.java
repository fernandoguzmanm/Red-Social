package launcher.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Users.Usuario.Tipo;
import launcher.control.Controller;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Publicacion;

public class PerfilAjenoView extends JFrame {

	private Controller _ctrl;
	private Frame _parent;
	private InfoUsuario _usuarioPerfil;
	private JPanel contentPane;
	private InfoUsuario _usuarioViendo;
	
	private boolean siguiendo;
	private int num_seguidores;
	private JLabel seguidores;

	public PerfilAjenoView(Controller ctrl, Frame parent, InfoUsuario perfil, InfoUsuario viewer) {
		super("PERFIL AJENO");
		_ctrl = ctrl;
		_parent = parent;
		_usuarioPerfil = perfil;
		_usuarioViendo = viewer;
		initGUI();
	}

	private void initGUI() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		String url = _usuarioPerfil.get_url();
		url = url.isBlank() ? "icons/perfil/perfil.png" : url;

		JLabel icono = new JLabel(new ImageIcon(url));
		icono.setForeground(new Color(0, 0, 205));
		icono.setBackground(new Color(128, 128, 128));
		icono.setBounds(63, 10, 130, 130);
		contentPane.add(icono);

		// Label de username
		JLabel username = new JLabel("@" + _usuarioPerfil.get_username());
		username.setFont(new Font("SimSun", Font.BOLD, 19));
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setBounds(49, 134, 190, 31);
		contentPane.add(username);

		// Label nombre empresa
		if (_usuarioPerfil.get_tipo().equals(Tipo.EMPRESA)) {
			JLabel nombre_empresa = new JLabel("Empresa: " + _usuarioPerfil.get_nombreEmpresa());
			nombre_empresa.setFont(new Font("SimSun", Font.BOLD, 19));
			nombre_empresa.setHorizontalAlignment(SwingConstants.CENTER);
			nombre_empresa.setBounds(40, 160, 350, 25);
			contentPane.add(nombre_empresa);

			seguidores_seguidos(175);

		} else { // Si no es user empresa
			seguidores_seguidos(150);
		}

		List<InfoPublicacion> _lista = _usuarioPerfil.get_publicaciones();
		if (!_lista.isEmpty()) {
			JButton verPublicaciones = new JButton("Ver lista de publicaciones");
			verPublicaciones.setFont(new Font("Dialog", Font.PLAIN, 14));
			verPublicaciones.setBounds(31, 339, 224, 49);
			verPublicaciones.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			verPublicaciones.addActionListener((e) -> {
				setVisible(false);
				new ListadoPublicacionesView(_ctrl, this, _usuarioViendo, _lista);
			});
			contentPane.add(verPublicaciones);
		}

		// boton para seguir/dejar de seguir
		JButton botonSeguir = new JButton();
		botonSeguir.setFont(new Font("Tahoma", Font.PLAIN, 20));
		siguiendo = _ctrl.seguidorDe(_usuarioPerfil, _usuarioViendo);
		if(siguiendo)
			botonSeguir.setText("Siguiendo");
		else
			botonSeguir.setText("Seguir");
		
		botonSeguir.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		botonSeguir.setBounds(222, 38, 156, 49);
		botonSeguir.addActionListener((e)->{
			siguiendo = !siguiendo;
			if(siguiendo) {
				botonSeguir.setText("Siguiendo");
				num_seguidores++;
				
			}
			else {
				botonSeguir.setText("Seguir");
				num_seguidores--;
			}
			seguidores.setText("Seguidores: " + num_seguidores);
			_ctrl.empezarDejarDeSeguir(_usuarioPerfil, _usuarioViendo, siguiendo);
		});
		contentPane.add(botonSeguir);

		JButton botonChatear = new JButton("Empezar chat");
		botonChatear.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		botonChatear.setFont(new Font("Dialog", Font.PLAIN, 17));
		botonChatear.setBounds(33, 263, 160, 40);
		botonChatear.addActionListener((e) -> new ChatView(_ctrl, this, _usuarioViendo, _usuarioPerfil));
		contentPane.add(botonChatear);

	}

	private void seguidores_seguidos(int y) {
		num_seguidores = _ctrl.buscaSeguidores(_usuarioPerfil.get_id());
		int num_seguidos = _ctrl.buscaSeguidos(_usuarioPerfil.get_id());

		// Label de seguidores
		seguidores = new JLabel("Seguidores: " + num_seguidores);
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
