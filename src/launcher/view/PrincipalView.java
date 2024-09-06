package launcher.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import Users.CreadorContenido;
import Users.Usuario;
import Users.Usuario.Tipo;
import launcher.control.Controller;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Publicacion;

public class PrincipalView extends JFrame {

	private Controller _ctrl;
	private InfoUsuario _usuario;
	private Frame _parent;
	
	private JFileChooser _fc; // fileChooser para subir contenido
	
	private List<InfoPublicacion> _publicacionesBD;
	InfoPublicacion p;
	private int cont = 0;

	public PrincipalView(Controller ctrl, Frame parent, Usuario usuario) {
		_ctrl = ctrl;
		_usuario = usuario;
		_parent = parent;
		_publicacionesBD = _ctrl.buscaPublicaciones();
		initGUI();
	}

	private JPanel publicacionPanel;

	private void initGUI() {

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 439, 509);

		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(147, 112, 219));
		menuBar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setJMenuBar(menuBar);

		// Menu 3 puntos
		JMenu tresPuntos = new JMenu("");
		tresPuntos.setHorizontalAlignment(SwingConstants.CENTER);
		tresPuntos.setForeground(Color.BLACK);
		tresPuntos.setBackground(new Color(147, 112, 219));
		tresPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		tresPuntos.setIcon(new ImageIcon("icons/tresPuntos.png"));
		menuBar.add(tresPuntos);

		// Opcion cerrar sesion
		JButton cerrarSesion = new JButton("Cerrar sesion");
		cerrarSesion.addActionListener((e) ->{
			setVisible(false);
			new LogInView(_ctrl);
		});
		tresPuntos.add(cerrarSesion);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(147, 112, 219));
		separator.setBackground(new Color(147, 112, 219));
		menuBar.add(separator);

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
		contentPane.setBackground(new Color(216, 191, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel Menu (panel inferior)
		JPanel panel = new JPanel();
		panel.setBackground(new Color(147, 112, 219));
		panel.setBounds(0, 340, 440, 121);
		contentPane.add(panel);
		panel.setLayout(null);

		// Boton perfil
		JButton botonPerfil = new JButton();
		botonPerfil.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		botonPerfil.setBackground(new Color(216, 191, 216));
		botonPerfil.setBounds(120, 11, 80, 80);
		botonPerfil.setIcon(new ImageIcon("./icons/perfil.png"));
		botonPerfil.addActionListener((e) -> {
			setVisible(false);
			new PerfilPropioView(_ctrl, this, _usuario);
		});
		panel.add(botonPerfil);

		// Boton de chats
		JButton botonChats = new JButton();
		botonChats.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		botonChats.setBackground(new Color(216, 191, 216));
		botonChats.setIcon(new ImageIcon("icons/chats.png"));
		botonChats.setBounds(320, 11, 80, 80);
		botonChats.addActionListener((e) -> {
			setVisible(false);
			new ChatsView(_ctrl, this, _usuario);
		});
		panel.add(botonChats);

		// Boton de comunidades
		JButton botonComunidades = new JButton();
		botonComunidades.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		botonComunidades.setBackground(new Color(216, 191, 216));
		botonComunidades.setBounds(20, 11, 80, 80);
		botonComunidades.setIcon(new ImageIcon("icons/comunidades.png"));
		botonComunidades.addActionListener((e) -> {
			setVisible(false);
			new MenuComunidadesView(_ctrl, this, _usuario);
		});
		panel.add(botonComunidades);

		// Boton subir contenido
		JButton botonSubirContenido = new JButton(new ImageIcon("icons/contenido.png"));
		botonSubirContenido.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		botonSubirContenido.setBackground(new Color(216, 191, 216));
		botonSubirContenido.setBounds(220, 11, 80, 80);
		botonSubirContenido.addActionListener((e) -> {
			int sod = _fc.showOpenDialog(ViewUtils.getWindow(this));

			if (sod == JFileChooser.APPROVE_OPTION) {
				File urlFile = _fc.getSelectedFile();
				String url = "./icons/publicaciones/" + urlFile.getName();
				Publicacion p = new Publicacion(_usuario.get_id(), url);
				if (_usuario.get_tipo().equals(Tipo.ESTANDAR)) {
					_usuario = new CreadorContenido(_usuario);
					_ctrl.actualizarUsuario(_usuario.get_correo(), "creador_contenido", "tipo");
				}					
				_usuario.add_publicacion(p);
				_ctrl.crearPublicacion(p);
			}
			;
		});
		panel.add(botonSubirContenido);
		
		_fc = new JFileChooser();
		_fc.setCurrentDirectory(new File(System.getProperty("user.dir") + "./icons/publicaciones"));

		publicacionPanel = new JPanel();
		publicacionPanel.setBorder(new CompoundBorder(
				new EtchedBorder(EtchedBorder.RAISED, new Color(148, 0, 211), new Color(128, 0, 128)),
				new EtchedBorder(EtchedBorder.RAISED, new Color(106, 90, 205), new Color(192, 192, 192))));
		publicacionPanel.setBounds(84, 74, 256, 256);
		contentPane.add(publicacionPanel);
		publicacionPanel.setLayout(new CardLayout(0, 0));

		p = _publicacionesBD.get(cont);
		
		// Publicacion
		JButton publicacion = new JButton(new ImageIcon(p.get_url()));
		publicacion.addActionListener((e) -> {
			setVisible(false);
			new PublicacionView(_ctrl, this,_usuario, p);			
		});

		publicacionPanel.add(publicacion);

		// Publicacion anterior
		JButton retrocederPubli = new JButton(new ImageIcon("icons/flechaIzquierda.png"));
		retrocederPubli.setBounds(21, 202, 50, 50);
		retrocederPubli.addActionListener((e) -> {
			cont--;
			if (cont < 0) {
				cont = 0;
				ViewUtils.showErrorMsg(this, "Esta es la primera publicacion");
			}
			p = _publicacionesBD.get(cont);
			publicacion.setIcon(new ImageIcon(p.get_url()));
		});
		contentPane.add(retrocederPubli);

		// Publicacion siguiente
		JButton avanzarPubli = new JButton(new ImageIcon("icons/flechaDerecha.png"));
		avanzarPubli.setBounds(350, 202, 50, 50);
		avanzarPubli.addActionListener((e) -> {
			cont++;
			if (cont == _publicacionesBD.size()) {
				cont--;
				ViewUtils.showErrorMsg(this, "Esta es la ultima publicacion");
			}
			p = _publicacionesBD.get(cont);
			publicacion.setIcon(new ImageIcon(p.get_url()));
		});
		contentPane.add(avanzarPubli);

	}

}
