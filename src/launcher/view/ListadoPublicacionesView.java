package launcher.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
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

import launcher.control.Controller;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;

public class ListadoPublicacionesView extends JFrame{

	private InfoUsuario _usuario;
	private Frame _parent;
	private Controller _ctrl;
	private JPanel contentPane;
	private JPanel panelPublicaciones;
	List<InfoPublicacion> publicaciones;

	public ListadoPublicacionesView(Controller ctrl, Frame parent, InfoUsuario viewer) {
		super("LISTA DE PUBLICACIONES");
		_ctrl = ctrl;
		_parent = parent;
		_usuario = viewer;
		publicaciones = _ctrl.buscaPublicaciones();
		initGUI();
		init_publicaciones();
	}
	
	public ListadoPublicacionesView(Controller ctrl, Frame parent, InfoUsuario viewer, List<InfoPublicacion> lista) {
		_ctrl = ctrl;
		_parent = parent;
		_usuario = viewer;
		publicaciones = lista;
		initGUI();
		init_publicaciones();
	}

	private void initGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		setBounds(100, 100, 432, 509);
		
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(new Color(220, 220, 220));
		menuBar.setBackground(new Color(224, 255, 255));
		menuBar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setJMenuBar(menuBar);
		
		// Content Pane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel textoCabecera = new JLabel("Lista de publicaciones de @");
		textoCabecera.setFont(new Font("Tahoma", Font.BOLD, 17));
		textoCabecera.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(textoCabecera, BorderLayout.NORTH);
		
		panelPublicaciones = new JPanel();
		panelPublicaciones.setBackground(new Color(127, 255, 212));
		FlowLayout flowLayout = (FlowLayout) panelPublicaciones.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelPublicaciones, BorderLayout.CENTER);		
		
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
		separator.setBackground(new Color(224, 255, 255));
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
	}
	
	private void init_publicaciones() {
		for(InfoPublicacion p : publicaciones) {
			
			JButton publi = new JButton(new ImageIcon(p.get_url()));
			publi.addActionListener((e)-> {
				setVisible(false);
				new PublicacionView(_ctrl, this, _usuario, p);
			});
			publi.setPreferredSize(new Dimension(128, 128));
			panelPublicaciones.add(publi);
		}
	}
}
