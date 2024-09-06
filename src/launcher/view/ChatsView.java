package launcher.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import Users.Estandar;
import Users.Usuario;
import launcher.control.Controller;
import negocio.InfoUsuario;

public class ChatsView extends JFrame {

	private Frame _parent;
	private Controller _ctrl;
	private JPanel panelChats;
	private InfoUsuario _usuarioViendo;

	public ChatsView(Controller ctrl, Frame parent, InfoUsuario u) {
		super("CHATS");
		_ctrl = ctrl;
		_parent = parent;
		_usuarioViendo = u;
		initGUI();
		init_chats();
	}

	private void initGUI() {
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(124, 231, 140));
		menuBar.setForeground(new Color(124, 231, 140));
		menuBar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setJMenuBar(menuBar);

		// Menu 3 puntos
		JMenu tresPuntos = new JMenu("");
		tresPuntos.setHorizontalAlignment(SwingConstants.CENTER);
		tresPuntos.setForeground(Color.BLACK);
		tresPuntos.setBackground(new Color(124, 231, 140));
		tresPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		tresPuntos.setIcon(new ImageIcon("icons/tresPuntos.png"));
		menuBar.add(tresPuntos);

		// Boton observacion
		JButton realizarObservacion = new JButton();
		realizarObservacion.setText("Realizar obervacion");
		realizarObservacion.addActionListener((e) -> {
			setVisible(false);
			new ObservacionView(_ctrl);
		});
		tresPuntos.add(realizarObservacion);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(124, 231, 140));
		separator.setBackground(new Color(124, 231, 140));
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

		JLabel textoCabecera = new JLabel("Tu lista de chats");
		textoCabecera.setPreferredSize(new Dimension(400, 30));
		textoCabecera.setHorizontalAlignment(SwingConstants.CENTER);
		textoCabecera.setFont(new Font("SansSerif", Font.BOLD, 17));
		menuBar.add(textoCabecera);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(124, 231, 140));
		separator_1.setBackground(new Color(124, 231, 140));
		menuBar.add(separator_1);
		menuBar.add(botonRetroceder);

		setBounds(100, 100, 432, 509);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(124, 231, 140));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		panelChats = new JPanel();
		panelChats.setLayout(new BoxLayout(panelChats, BoxLayout.Y_AXIS));

		scrollPane.setViewportView(panelChats);
	}

	private void init_chats() {

		try {
			// cargo el archivo a un JSON
			InputStream archivoMensajes = new FileInputStream(new File("mensajes/mensajes.json"));
			JSONObject json = new JSONObject(new JSONTokener(archivoMensajes));

			JSONArray chats_usuarios = json.optJSONArray("chats");
			List<String> nombreChats = new ArrayList<>();

			if (chats_usuarios != null) {
				for (int i = 0; i < chats_usuarios.length(); i++) {
					JSONObject chat = chats_usuarios.getJSONObject(i);
					String emisor = chat.optString("emisor");
					String receptor = chat.optString("receptor");

					String nombreUserChat = null;
					if (emisor.equalsIgnoreCase(_usuarioViendo.get_nombre())) {
						nombreUserChat = receptor;
					} else if (receptor.equalsIgnoreCase(_usuarioViendo.get_nombre())) {
						nombreUserChat = emisor;
					}

					if (!nombreChats.contains(nombreUserChat)) {
						nombreChats.add(nombreUserChat);

						JPanel chatPanel = new JPanel();

						chatPanel.setBackground(new Color(152, 251, 152));
						chatPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
						chatPanel.setPreferredSize(new Dimension(320, 40));
						panelChats.add(chatPanel);

						chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.X_AXIS));

						JLabel nombreChatUser = new JLabel(" @" + nombreUserChat);
						nombreChatUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
						chatPanel.add(nombreChatUser);

						JSeparator separator1 = new JSeparator();
						separator1.setForeground(new Color(124, 231, 140));
						separator1.setBackground(new Color(152, 251, 152));
						chatPanel.add(separator1);

						JLabel textoEntrarChat = new JLabel("Entra al chat      ");
						textoEntrarChat.setFont(new Font("Tahoma", Font.PLAIN, 12));
						chatPanel.add(textoEntrarChat);

						JButton botonChat = new JButton(new ImageIcon("icons/entrarChat.png"));
						botonChat.addActionListener((e) -> {
							setVisible(false);

//						new ChatView(_ctrl, this, _usuarioViendo, _ctrl.buscarUsuario(receptor, "nombre"));
							Usuario u = new Estandar("correo1234", "contra", "jorge123", "Jorge", "Turquia", 999111555,
									17, "", "");
							new ChatView(_ctrl, this, _usuarioViendo, u);
						});
						botonChat.setPreferredSize(new Dimension(35, 35));
						chatPanel.add(botonChat);
					}
				}
			}

		} catch (FileNotFoundException e) {
			ViewUtils.showErrorMsg(this, "No se pudo encontrar el archivo con los mensajes");
		}

	}

}
