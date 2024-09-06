package launcher.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import launcher.control.Controller;
import negocio.Comentario;
import negocio.InfoComunidad;
import negocio.InfoUsuario;

public class ComentarComunidad extends JDialog {

	private Controller _ctrl;
	private Frame parent;
	private JTextArea textArea;
	private InfoComunidad _comunidad;
	private InfoUsuario _usuarioViendo;
	
	public ComentarComunidad(Controller ctrl, Frame parent, InfoComunidad c, InfoUsuario viewer) {
		super((Frame) null, true);
		_ctrl = ctrl;
		this.parent = parent;
		_comunidad = c;
		_usuarioViendo = viewer;
		initGUI();
	}

	private void initGUI() {
		if (parent != null) {
			setLocation(parent.getLocation().x + parent.getWidth() / 2 - getWidth() / 2,
					parent.getLocation().y + parent.getHeight() / 2 - getHeight() / 2);
		}
		setBounds(100, 100, 415, 260);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225), 2));
		panel.setBackground(new Color(127, 255, 212));
		contentPane.add(panel, BorderLayout.SOUTH);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setBorder(new LineBorder(new Color(65, 105, 225), 2, true));
		cancelar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cancelar.addActionListener((e) -> this.setVisible(false));
		panel.add(cancelar);

		JButton enviar = new JButton("Enviar");
		enviar.setBorder(new LineBorder(new Color(65, 105, 225), 2, true));
		enviar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		enviar.addActionListener((e) -> {
			String mensaje = textArea.getText();
			Comentario comentario = new Comentario(mensaje, _usuarioViendo.get_nombre());
//			_ctrl.crearComentario(comentario, "comunidad", _comunidad.get_id());
		});
		panel.add(enviar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(65, 105, 225), 2));
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		scrollPane.setViewportView(textArea);

		JLabel textoCabecera_1 = new JLabel("Escribe un comentario:");
		textoCabecera_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setColumnHeaderView(textoCabecera_1);

	}
}
