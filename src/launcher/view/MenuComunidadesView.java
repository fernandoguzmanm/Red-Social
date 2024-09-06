package launcher.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Users.Usuario;
import launcher.control.Controller;
import negocio.Comunidad;
import negocio.InfoUsuario;

public class MenuComunidadesView extends JFrame{

	private Controller _ctrl;
	private InfoUsuario _usuario;
	private Frame _parent;
	private JPanel comunidadesPanel;

	public MenuComunidadesView(Controller ctrl,Frame parent, InfoUsuario usuario) {
		super("COMUNIDADES");
		_ctrl = ctrl;
		_usuario = usuario;
		_parent = parent;
		initGUI();
		init_comunidades();
	}

	private void initGUI() {

		this.setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 509);
		
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(218, 112, 214));
		setJMenuBar(menuBar);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(218, 112, 214));
		menuBar.add(separator);
		
		// Boton Retroceder
		JButton botonRetroceder = new JButton(new ImageIcon("icons/flecha.png"));
		botonRetroceder.setBackground(new Color(218, 112, 214));
		botonRetroceder.addActionListener((e) -> {
			if (_parent != null) {
				setVisible(false);
				_parent.setVisible(true);
			}
		});
		menuBar.add(botonRetroceder);
		
		// Content Pane
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(216, 191, 216));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(216, 191, 216));
		panel.setBounds(0, 0, 418, 91);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel textoCabecera = new JLabel("<html><p>Explora las comunidades activas, chatea con los demas usuarios...</p></html>");
		textoCabecera.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textoCabecera.setVerticalAlignment(SwingConstants.TOP);
		panel.add(textoCabecera, BorderLayout.CENTER);
		
		comunidadesPanel = new JPanel();
		comunidadesPanel.setBackground(new Color(216, 191, 216));
		comunidadesPanel.setBounds(10, 90, 398, 350);
		contentPane.add(comunidadesPanel);
		comunidadesPanel.setLayout(new BoxLayout(comunidadesPanel, BoxLayout.Y_AXIS));


	}
	
	private void init_comunidades() {

		forEach();
		//bucleFor();
	}
	
	private void forEach() {
		
		List<Comunidad> _listaComunidades = _ctrl.getComunidades();
		for(Comunidad c: _listaComunidades) {
			
			// Panel comunidad
			JPanel panel = new JPanel(new BorderLayout(10, 10));
			panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			panel.setBackground(new Color(216, 191, 216));
			comunidadesPanel.add(panel);
			
			// Boton imagen (para entrar en la comunidad)
			JButton botonComunidad = new JButton(new ImageIcon(c.get_url()));
			botonComunidad.setSize(100,70);
			botonComunidad.addActionListener((e) -> {
				setVisible(false);
				new ComunidadView(_ctrl, this, c, _usuario);
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
			JLabel descripcion = new JLabel("<html><p>" +c.get_desc() + "</p></html>");
			descripcion.setFont(new Font("Sylfaen", Font.ITALIC, 13));
			infoComunidad.add(descripcion);
		}
	}
	
	private void bucleFor() {
		// Comunidades por defecto
		for(int i = 0; i < 4; i ++) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			panel.setBackground(new Color(216, 191, 216));
			comunidadesPanel.add(panel);
			
			JButton iconoComunidad = new JButton(new ImageIcon("icons/cocina.png"));
			iconoComunidad.addActionListener((e)->{
				setVisible(false);
//				new ComunidadView(_ctrl, this, comunidad);
			});
			panel.add(iconoComunidad, BorderLayout.WEST);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBackground(new Color(216, 191, 216));

			panel.add(panel_3, BorderLayout.CENTER);
			panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
			
			JLabel lblNewLabel_2 = new JLabel("COCINA PARA TODOS");
			lblNewLabel_2.setFont(new Font("Tempus Sans ITC", Font.BOLD, 21));
			panel_3.add(lblNewLabel_2);
			
			JLabel lblNewLabel_1 = new JLabel("Topico: Cocina");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
			panel_3.add(lblNewLabel_1);
			
			JLabel lblNewLabel_3 = new JLabel("<html><p> Aqui podras aprender nuevas recetas, consejos y trucos en la cocina. Unete, chatea, haz amigos!!</p></html>");
			lblNewLabel_3.setFont(new Font("Sylfaen", Font.ITALIC, 13));
			panel_3.add(lblNewLabel_3);
		}
	}
}
