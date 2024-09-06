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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Users.Usuario;
import launcher.control.Controller;
import negocio.InfoUsuario;

public class CambiarNombreView  extends JFrame{

	private InfoUsuario _usuario;
	private Frame _parent;
	private Controller _ctrl;

	public CambiarNombreView(Controller ctrl, Frame parent, InfoUsuario u) {
		super("CAMBIO DE NOMBRE");
		_ctrl = ctrl;
		_parent = parent;
		_usuario = u;
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
		
		// Label nueva password
		JLabel password = new JLabel("Nuevo username:");
		password.setFont(new Font("Tahoma", Font.PLAIN, 14));
		password.setBounds(25, 50, 130, 15);
		contentPane.add(password);
		
		// Password Field nueva password
		JTextField nuevoUsername = new JTextField();
		nuevoUsername.setBackground(Color.LIGHT_GRAY);
		nuevoUsername.setBounds(140, 50, 150, 20);
		contentPane.add(nuevoUsername);
		
		// Boton aceptar
		JButton aceptar = new JButton("ACEPTAR");
		aceptar.setBounds(125, 165, 100, 50);
		aceptar.addActionListener((e) -> {
			String username = nuevoUsername.getText();
			
			if(username.isBlank())
				ViewUtils.showErrorMsg("Completa todos los campos");
			else {
				// hay que buscar si el nombre existe
				Usuario user = _ctrl.buscarUsuario(username, "nombre");
				if(user != null	)
					ViewUtils.showErrorMsg(this, "El nombre ya esta en uso");
				else {
					_ctrl.actualizarUsuario(_usuario.get_correo(), username, "nombre");
					setVisible(false);
					_parent.setVisible(true);
				}
			}
		});
		contentPane.add(aceptar);
	
	}
}
