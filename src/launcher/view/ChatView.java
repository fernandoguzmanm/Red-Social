package launcher.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Users.Usuario;
import launcher.control.Controller;
import negocio.Chat;
import negocio.Chat.Info;
import negocio.InfoUsuario;

public class ChatView extends JFrame{

	
	private Frame _parent;
	private Controller _ctrl;
	private Chat _chat;
	private JPanel panelMensajes;
	private InfoUsuario _emisor;
	private InfoUsuario _receptor;

	public ChatView (Controller ctrl, Frame parent, InfoUsuario emisor, InfoUsuario receptor) {
		super("CHAT");
		_ctrl = ctrl;
		_parent = parent;
		_chat = new Chat(emisor, receptor);
		_emisor = emisor;
		_receptor = receptor;
		initGUI();
		init_chat();
	}

	private void initGUI() {
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(252, 182, 86));
		
		menuBar.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setJMenuBar(menuBar);

//		// Menu 3 puntos
//		JMenu tresPuntos = new JMenu("");
//		tresPuntos.setHorizontalAlignment(SwingConstants.CENTER);
//		tresPuntos.setForeground(Color.BLACK);
//		tresPuntos.setBackground(new Color(124, 231, 140));
//		tresPuntos.setFont(new Font("Segoe UI", Font.PLAIN, 20));
//		tresPuntos.setIcon(new ImageIcon("icons/tresPuntos.png"));
//		menuBar.add(tresPuntos);
//
//		// Boton observacion
//		JButton realizarObservacion = new JButton();
//		realizarObservacion.setText("Realizar obervacion");
//		realizarObservacion.addActionListener((e) -> {
////							setVisible(false);
////							new ObservacionView(_ctrl);
//		});
//		tresPuntos.add(realizarObservacion);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(252, 182, 86));
		separator.setBackground(new Color(252, 182, 86));
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

		JLabel textoCabecera = new JLabel("@" +_receptor.get_nombre());
		textoCabecera.setHorizontalAlignment(SwingConstants.CENTER);
		textoCabecera.setFont(new Font("SansSerif", Font.BOLD, 17));
		menuBar.add(textoCabecera);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(252, 182, 86));
		separator_1.setBackground(new Color(252, 182, 86));
		menuBar.add(separator_1);
		menuBar.add(botonRetroceder);

		setBounds(100, 100, 439, 509);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(252, 182, 86));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelMensajes = new JPanel();
		panelMensajes.setBackground(new Color(253, 202, 134));
		contentPane.add(panelMensajes, BorderLayout.CENTER);
		panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(300, 50));
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton botonEnviarMensaje = new JButton("Enviar");
		botonEnviarMensaje.addActionListener((e)->{
			String textoMensaje = textArea.getText();
			textArea.setText("");
			if(!textoMensaje.isEmpty()) {
				_chat.addMensaje(_emisor.get_nombre(), _receptor.get_nombre(), textoMensaje);
				init_chat();
				_chat.actualizarFichero();
			}
		});
		scrollPane.setRowHeaderView(botonEnviarMensaje);
		
	}
	
	private void init_chat() {
		List<Info> mensajes = _chat.get_mensajes();
		panelMensajes.removeAll();
		
		for(Info i : mensajes) {
			JLabel nombreEmisor = new JLabel(" @"+ i.nombreUser());
			nombreEmisor.setFont(new Font("Tahoma", Font.BOLD, 12));
			panelMensajes.add(nombreEmisor);
			
			JLabel mensaje = new JLabel("<html><p>" + i.mensaje()+ "</p></html>");
			mensaje.setFont(new Font("Tahoma", Font.PLAIN, 14));
			
			panelMensajes.add(mensaje);
		}
	}
}
