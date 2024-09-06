package launcher.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import Users.Usuario;
import launcher.control.Controller;
import negocio.Comunidad;
import negocio.InfoComunidad;
import negocio.InfoUsuario;

public class ComunidadView extends JFrame{

	private Controller _ctrl;
	private JPanel panelComentarios;
	private InfoComunidad _comunidad;
	private Frame _parent;
	private InfoUsuario _usuarioViendo;

	public ComunidadView(Controller ctrl, Frame parent, InfoComunidad comunidad, InfoUsuario viewer) {
		super("COMUNIDAD");
		_ctrl = ctrl;
		_parent = parent;
		_comunidad = comunidad;
		_usuarioViendo = viewer;
		initGUI();
		init_comentarios();
	}

	private void initGUI() {

		this.setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 439, 509);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0, 0, 128));
		setJMenuBar(menuBar);
		
		JSeparator separator = new JSeparator();
		menuBar.add(separator);
		
		JButton botonRetroceder = new JButton(new ImageIcon("icons/flecha.png"));
		botonRetroceder.addActionListener((e)-> {
			if (_parent != null) {
				setVisible(false);
				_parent.setVisible(true);
			}			
		});
		menuBar.add(botonRetroceder);
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(72, 209, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCabecera = new JPanel();
		panelCabecera.setBackground(new Color(100, 149, 237));
		panelCabecera.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		contentPane.add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(new BorderLayout(0, 0));
		
		JLabel iconoComunidad = new JLabel(new ImageIcon(_comunidad.get_url()));
		iconoComunidad.setBackground(new Color(135, 206, 235));
		iconoComunidad.setBorder(new MatteBorder(0, 0, 0, 2, (Color) new Color(0, 0, 0)));
		panelCabecera.add(iconoComunidad, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(135, 206, 235));
		panelCabecera.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JLabel nombreComunidad = new JLabel(_comunidad.get_nombre());
		nombreComunidad.setFont(new Font("Tempus Sans ITC", Font.BOLD, 18));
		panel_2.add(nombreComunidad);
		
		JLabel seguidoresLabel = new JLabel("Seguidores de la comunidad: " + _comunidad.get_num_integrantes());
		seguidoresLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_2.add(seguidoresLabel);
		
		JButton botonSeguir = new JButton("Dejar de seguir");
		botonSeguir.addActionListener((e) -> {
			
		});
		botonSeguir.setBorder(new MatteBorder(0, 2, 0, 0, (Color) new Color(0, 0, 0)));
		botonSeguir.setBackground(new Color(100, 149, 237));
		
		panelCabecera.add(botonSeguir, BorderLayout.EAST);
		
		panelComentarios = new JPanel();
		panelComentarios.setBackground(new Color(175, 238, 238));
		panelComentarios.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), new Color(192, 192, 192)), new EtchedBorder(EtchedBorder.LOWERED, new Color(192, 192, 192), new Color(128, 128, 128))));
		contentPane.add(panelComentarios, BorderLayout.CENTER);
		panelComentarios.setLayout(new BoxLayout(panelComentarios, BoxLayout.Y_AXIS));
		
		JLabel foroMensajes = new JLabel("Foro de mensajes ");
		foroMensajes.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		foroMensajes.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		panelComentarios.add(foroMensajes);
		
		JPanel panelComentar = new JPanel();
		contentPane.add(panelComentar, BorderLayout.SOUTH);
		panelComentar.setLayout(new CardLayout(0, 0));
		
		JButton comentar = new JButton("COMENTAR");
		comentar.addActionListener((e)-> abrirPanelComentar());
		panelComentar.add(comentar);
			
	}

	private void init_comentarios() {
		JLabel user = new JLabel("   @Anonimo");
		user.setForeground(new Color(255, 0, 0));
		user.setFont(new Font("Verdana Pro Black", Font.BOLD, 12));
		JLabel comentario = new JLabel("<html><p>Este sera el ejemplo de la vista del chat de una comunidad</p></html>");
		panelComentarios.add(user);
		panelComentarios.add(comentario);
		
		JLabel nombre1 = new JLabel("   @Anonimo_2");
		nombre1.setForeground(new Color(255, 0, 0));
		nombre1.setFont(new Font("Verdana Pro Black", Font.BOLD, 12));
		JLabel comentario1 = new JLabel("<html><p>Me encanta esta red social</p></html>");
		panelComentarios.add(nombre1);
		panelComentarios.add(comentario1);
		
	}
	
	private void abrirPanelComentar() {
		JDialog comentarioView = new ComentarComunidad(_ctrl, this, _comunidad, _usuarioViendo);
		comentarioView.setVisible(true);
		
	}

	
}
