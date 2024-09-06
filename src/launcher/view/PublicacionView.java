package launcher.view;

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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Users.Usuario;
import launcher.control.Controller;
import negocio.InfoPublicacion;
import negocio.InfoUsuario;

public class PublicacionView extends JFrame {

	private Frame _parent;
	private Controller _ctrl;
	private InfoUsuario _usuarioViendo;
	private InfoUsuario _usuarioPublicacion;
	private InfoPublicacion _publicacion;
	
	private int n_likes;
	private JLabel numLikes;
	private boolean like;
	private String urlLike;

	public PublicacionView(Controller ctrl, Frame parent, InfoUsuario usuarioViendo, InfoPublicacion p) {
		super("PUBLICACION");
		_parent = parent;
		_ctrl = ctrl;
		_usuarioViendo = usuarioViendo;
		_publicacion = p;
		_usuarioPublicacion = _ctrl.buscarUsuario(_publicacion.get_user_id(), "idusuario");
		initGUI();
	}

	private void initGUI() {
		setVisible(true);
		setLocation(_parent.getLocation().x + _parent.getWidth() / 2 - getWidth() / 2,
				_parent.getLocation().y + _parent.getHeight() / 2 - getHeight() / 2);

		setBounds(100, 100, 439, 509);

		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(147, 112, 219));
		menuBar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setJMenuBar(menuBar);

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
		
		// Content pane
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(216, 191, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
//		contentPane.setLayout(null);

		// Scroll pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		scrollPane.setBounds(10, 304, 392, 135);
//		contentPane.add(scrollPane);

		// Panel
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		like = _ctrl.hayLike(_usuarioViendo, _publicacion);
		urlLike = like ? "icons/corazonLleno.png" : "icons/corazonVacio.png";
		n_likes = _publicacion.get_likes();
		
		// Boton likes
		JButton darQuitarLike = new JButton();
		darQuitarLike.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		darQuitarLike.setIcon(new ImageIcon(urlLike));
		darQuitarLike.setBounds(329, 204, 40, 40);
		darQuitarLike.addActionListener((e) -> {
			like = !like;
			urlLike = like ? "icons/corazonLleno.png" : "icons/corazonVacio.png";
			darQuitarLike.setIcon(new ImageIcon(urlLike));
			
			if (like) n_likes++;
			else n_likes--;
			numLikes.setText("" + n_likes);
			_ctrl.darQuitarLike(_usuarioViendo, _publicacion, like, n_likes);
		});
		contentPane.add(darQuitarLike);	

		// Label numero likes
		numLikes = new JLabel("" + n_likes);
		numLikes.setHorizontalAlignment(SwingConstants.CENTER);
		numLikes.setFont(new Font("Tahoma", Font.BOLD, 14));
		numLikes.setBounds(371, 215, 20, 19);
		contentPane.add(numLikes);

		// Boton imagen de perfil
		JButton imagenPerfil = new JButton(new ImageIcon(_usuarioPublicacion.get_url()));
		imagenPerfil.setBounds(305, 49, 130, 130);
		imagenPerfil.addActionListener((e) -> {
			setVisible(false);
			new PerfilAjenoView(_ctrl, this, _usuarioPublicacion, _usuarioViendo);
		});
		contentPane.add(imagenPerfil);

		// Label username
		JLabel username = new JLabel("@" + _usuarioPublicacion.get_username());
		username.setForeground(new Color(0, 0, 255));
		username.setFont(new Font("Sylfaen", Font.PLAIN, 16));
		username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setBounds(294, 132, 108, 24);
		contentPane.add(username);

		// Label publicacion
		JLabel publicacion = new JLabel(new ImageIcon(_publicacion.get_url()));
		publicacion.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		publicacion.setBounds(28, 24, 256, 256);
		contentPane.add(publicacion);

		// Boton para comentar
		JButton comentar = new JButton("Comentar");
		comentar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comentar.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		comentar.addActionListener((e) -> abrirPanelComentar());
		comentar.setBounds(10, 284, 85, 21);
		contentPane.add(comentar);

//		for (int i = 0; i < 4; i++) {
//			// Label username 1
//			JLabel username1 = new JLabel("  @Anonimo_1");
//			username1.setFont(new Font("Tahoma", Font.PLAIN, 12));
//			panel.add(username1);
//
//			JLabel lblNewLabel_1 = new JLabel("Que divertida es la programacion!! Como me gusta!!");
//			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
//			lblNewLabel_1.setForeground(new Color(0, 0, 139));
//
//			panel.add(lblNewLabel_1);
//
//			// Label username 2
//			JLabel username2 = new JLabel("  @Anonimo_2");
//			username2.setFont(new Font("Tahoma", Font.PLAIN, 12));
//			panel.add(username2);
//
//			JLabel lblNewLabel_11 = new JLabel(
//					"A mi tambien me encanta programar, sobre todo para la asignata IS2, es superdivertido");
//			lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));
//			lblNewLabel_11.setForeground(new Color(0, 0, 139));
//			panel.add(lblNewLabel_11);
//		}
	}

	private void abrirPanelComentar() {
		JDialog comentarioView = new ComentarPublicacion(_ctrl, null, _publicacion, _usuarioViendo);
		comentarioView.setVisible(true);

	}

}
