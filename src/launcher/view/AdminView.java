package launcher.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Users.Administrador;
import launcher.control.Controller;
import negocio.Comunidad;
import negocio.InfoComunidad;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;
import negocio.Observacion;

public class AdminView extends JFrame {

	private Controller _ctrl;

	private JPanel _contenidoPanel;
	private JPanel _centralPanel;
	private JPanel _comboBoxPanel;
	private DefaultComboBoxModel<String> _ambito;

	private Administrador _usuarioViendo;

	private JButton eliminarComunidadButton;

	private JButton agregarComunidadButton;

	public AdminView(Controller ctrl, Administrador admin) {
		super("ADMIN");
		_ctrl = ctrl;
		_usuarioViendo = admin;
		initGUI();
	}

	private void initGUI() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 509);

		// Content Pane
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(78, 143, 163));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel del titulo
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(78, 143, 163));
		contentPane.add(titlePanel, BorderLayout.NORTH);

		JLabel titulo = new JLabel("Conectado como ADMIN");
		titulo.setFont(new Font("Tahoma", Font.BOLD, 19));
		titlePanel.add(titulo);

		// Panel central
		_centralPanel = new JPanel();
		_centralPanel.setBackground(new Color(78, 143, 163));
		contentPane.add(_centralPanel, BorderLayout.CENTER);
		_centralPanel.setLayout(new BorderLayout(0, 0));

		_comboBoxPanel = new JPanel();
		_comboBoxPanel.setBackground(new Color(78, 143, 163));
		_centralPanel.add(_comboBoxPanel, BorderLayout.NORTH);

		JLabel text = new JLabel("Escoja ambito : ");
		text.setFont(new Font("Tahoma", Font.BOLD, 15));
		_comboBoxPanel.add(text);

		// Combo box de tipos de ambito
		init_comboBoxes();
		JComboBox<String> ambito = new JComboBox<>(_ambito);
		ambito.setForeground(new Color(18, 18, 237));
		ambito.setToolTipText("Observaciones que puedes realizar\r\n");
		ambito.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ambito.setBounds(214, 82, 107, 21);
		ambito.addActionListener((e) -> show_textArea(ambito.getSelectedIndex()));
		_comboBoxPanel.add(ambito);

		// Boton eliminar comunidad
		JSeparator separator1 = new JSeparator();
		_comboBoxPanel.add(separator1);

		eliminarComunidadButton = new JButton(new ImageIcon("icons/eliminar.png"));
		eliminarComunidadButton.addActionListener((e) -> {
		});
		eliminarComunidadButton.setPreferredSize(new Dimension(32, 32));
		_comboBoxPanel.add(eliminarComunidadButton);

		JSeparator separator2 = new JSeparator();
		_comboBoxPanel.add(separator2);

		// Boton agregar comunidad
		agregarComunidadButton = new JButton(new ImageIcon("icons/agregar.png"));
		agregarComunidadButton.addActionListener((e) -> {
			setVisible(false);
			new AgregarComunidad(_ctrl, this, _usuarioViendo);
		});
		agregarComunidadButton.setPreferredSize(new Dimension(32, 32));
		_comboBoxPanel.add(agregarComunidadButton);
		
		agregarComunidadButton.setVisible(false);
		eliminarComunidadButton.setVisible(false);

		// Panel de contenido
		_contenidoPanel = new JPanel();
		_contenidoPanel.setBackground(new Color(78, 143, 163));
		_contenidoPanel.setLayout(new BorderLayout());
		_centralPanel.add(_contenidoPanel);

	}

	private void init_comboBoxes() {
		this._ambito = new DefaultComboBoxModel<String>();
		String[] ambito = { "Usuarios", "Publicaciones", "Comunidades", "Observaciones" };

		for (int i = 0; i < ambito.length; i++)
			this._ambito.addElement(ambito[i]);
	}

	private void show_textArea(int index) {
		_contenidoPanel.removeAll();

		switch (index) {
		case 0: crearUsuarioPanel(); break;
		case 1: crearPublicacionPanel(); break;
		case 2: crearComunidadPanel(); break;
		case 3: crearObservacionPanel(); break;
		default: break;
		}
	}

	private void crearUsuarioPanel() {
		agregarComunidadButton.setVisible(false);
		eliminarComunidadButton.setVisible(false);
		
		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		_contenidoPanel.add(contenido, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(contenido);
		_contenidoPanel.add(scroll, BorderLayout.CENTER);
		
		List<InfoUsuario> usuarios = _ctrl.buscarUsuarios();
		for(InfoUsuario u: usuarios) {
			// panel para meter el boton del usuario y el de eliminar usuario
			JPanel panelBotones = new JPanel();
			panelBotones.setBackground(new Color(78, 143, 163));
			contenido.add(panelBotones);
			
			// Boton del user
			JButton user = new JButton(u.get_username());
			user.setPreferredSize(new Dimension(300,40));
			user.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			user.addActionListener((e)-> {
				setVisible(false);
				new PerfilAjenoView(_ctrl, this, u, _usuarioViendo);				
			});
			
			// Boton para eliminar el user
			JButton eliminarUser = new JButton(new ImageIcon("icons/eliminar.png"));
			eliminarUser.addActionListener((e)-> {
				setVisible(false);
				new ConfirmacionView(_ctrl, this, u);
			});
			
			panelBotones.add(user);
			panelBotones.add(eliminarUser);
			contenido.add(panelBotones);
		}

	}

	private void crearPublicacionPanel() {
		agregarComunidadButton.setVisible(false);
		eliminarComunidadButton.setVisible(false);
		
		JPanel publicacionesPanel = new JPanel();
		publicacionesPanel.setLayout(new GridLayout(4, 3, 2, 2));
		_contenidoPanel.add(publicacionesPanel, BorderLayout.CENTER);
		
		List<InfoPublicacion> publicaciones = _ctrl.buscaPublicaciones();
		for (InfoPublicacion p : publicaciones) {

			JButton publi = new JButton(new ImageIcon(p.get_url()));
			publi.addActionListener((e) -> new PublicacionView(_ctrl, this, _usuarioViendo, p));
			publi.setPreferredSize(new Dimension(128, 128));
			publicacionesPanel.add(publi);
		}

	}

	private void crearComunidadPanel() {
		agregarComunidadButton.setVisible(true);
		eliminarComunidadButton.setVisible(true);
		
		JPanel comunidadesPanel = new JPanel();
		comunidadesPanel.setBackground(new Color(216, 191, 216));
		comunidadesPanel.setBounds(10, 90, 398, 350);
		
		_contenidoPanel.add(comunidadesPanel, BorderLayout.CENTER);
		comunidadesPanel.setLayout(new BoxLayout(comunidadesPanel, BoxLayout.Y_AXIS));
		init_comunidades(comunidadesPanel);
	}

	private void init_comunidades(JPanel comunidadesPanel) {
		forEach(comunidadesPanel);
	}
	
	private void forEach(JPanel comunidadesPanel) {

		List<Comunidad> _listaComunidades = _ctrl.getComunidades();
		List<InfoComunidad> _lista = new ArrayList<>(_listaComunidades);
		for (InfoComunidad c : _lista) {

			// Panel comunidad
			JPanel panel = new JPanel(new BorderLayout(10, 10));
			panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			panel.setBackground(new Color(216, 191, 216));
			comunidadesPanel.add(panel);

			// Boton imagen (para entrar en la comunidad)
			JButton botonComunidad = new JButton(new ImageIcon(c.get_url()));
			botonComunidad.setSize(100, 70);
			botonComunidad.addActionListener((e) -> {
				setVisible(false);
				new ComunidadView(_ctrl, this, c, _usuarioViendo);
			});
			panel.add(botonComunidad, BorderLayout.WEST);

			// Panel con info de la comunidad
			JPanel infoComunidad = new JPanel();
			infoComunidad.setBackground(new Color(216, 191, 216));

			panel.add(infoComunidad, BorderLayout.CENTER);
			infoComunidad.setLayout(new BoxLayout(infoComunidad, BoxLayout.Y_AXIS));

			// Label nombre
			JLabel nombreComunidad = new JLabel(c.get_nombre());
			nombreComunidad.setFont(new Font("Tempus Sans ITC", Font.BOLD, 21));
			infoComunidad.add(nombreComunidad);

			// Label topico
			JLabel topico = new JLabel("Topico: " + c.get_topico());
			topico.setFont(new Font("Tahoma", Font.PLAIN, 13));
			infoComunidad.add(topico);

			// Label descripcion
			JLabel descripcion = new JLabel("<html><p>" + c.get_desc() + "</p></html>");
			descripcion.setFont(new Font("Sylfaen", Font.ITALIC, 13));
			infoComunidad.add(descripcion);
		}
	}

	private void crearObservacionPanel() {
		agregarComunidadButton.setVisible(false);
		eliminarComunidadButton.setVisible(false);
		
		JPanel observacionesPanel = new JPanel();
		observacionesPanel.setLayout(new BoxLayout(observacionesPanel, BoxLayout.Y_AXIS));
		_contenidoPanel.add(observacionesPanel, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(observacionesPanel);
		_contenidoPanel.add(scroll, BorderLayout.CENTER);
		
		for(int i = 0 ; i < 30;i++) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			panel.setBackground(new Color(216, 191, 216));
			observacionesPanel.add(panel);
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(216, 191, 216));
			panel.add(panel_1, BorderLayout.CENTER);
			panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
			JLabel type = new JLabel("Resena");
			type.setFont(new Font("Tahoma", Font.PLAIN, 13));
			panel_1.add(type);
			JLabel texto = new JLabel("Muy bonita la aplicacion");
			texto.setFont(new Font("Tahoma", Font.PLAIN, 13));
			panel_1.add(texto);	
		}
		//List<Observacion> observaciones = _ctrl.buscarObservaciones();
		
		//for(Observacion o: observaciones) {
		//	JButton publi = new JButton(new ImageIcon(o.get_url()));
		//	publi.addActionListener((e) -> new PublicacionView(_ctrl, this, _usuarioViendo, p));
		//	publi.setPreferredSize(new Dimension(128, 128));
		//	observacionesPanel.add(publi);
		//}
	}
}
