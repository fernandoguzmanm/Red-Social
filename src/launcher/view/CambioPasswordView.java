package launcher.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import launcher.control.Controller;
import negocio.InfoUsuario;

public class CambioPasswordView extends JFrame {

	private Controller _ctrl;
	private Frame _parent;

	public CambioPasswordView(Controller ctrl, Frame parent) {
		super("CAMBIO DE PASSWORD");
		_ctrl = ctrl;
		_parent = parent;
		initGUI();

	}

	private void initGUI() {
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 300);

		// Content Pane
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		setJMenuBar(menuBar);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2)));

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

		// Label correo
		JLabel correo = new JLabel("Correo:");
		correo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		correo.setBounds(80, 20, 60, 15);
		contentPane.add(correo);

		// Text Field correo
		JTextField correoTF = new JTextField();
		correoTF.setBackground(Color.LIGHT_GRAY);
		correoTF.setBounds(135, 20, 150, 20);
		contentPane.add(correoTF);

		// Label nueva password
		JLabel password = new JLabel("Nueva password:");
		password.setFont(new Font("Tahoma", Font.PLAIN, 14));
		password.setBounds(25, 50, 130, 15);
		contentPane.add(password);

		// Password Field nueva password
		JPasswordField passwordPF = new JPasswordField();
		passwordPF.setBackground(Color.LIGHT_GRAY);
		passwordPF.setBounds(140, 50, 150, 20);
		contentPane.add(passwordPF);

		// Boton aceptar
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.setBounds(125, 165, 100, 50);
		aceptar.addActionListener((e) -> {
			String nuevaPassword = new String(passwordPF.getPassword());
			String correoUsuario = correoTF.getText();

			if (nuevaPassword.isBlank() || correoUsuario.isBlank())
				ViewUtils.showErrorMsg("Completa todos los campos");
			else {
				_ctrl.actualizarUsuario(correoUsuario, nuevaPassword, "`password`");
				setVisible(false);
				_parent.setVisible(true);
			}
		});
		contentPane.add(aceptar);
	}
}
