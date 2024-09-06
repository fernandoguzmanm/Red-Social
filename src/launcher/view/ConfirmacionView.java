package launcher.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import launcher.control.Controller;
import negocio.InfoUsuario;

public class ConfirmacionView extends JFrame{
	
	private Controller _ctrl;
	private Frame _parent;
	private InfoUsuario _usuario;

	public ConfirmacionView(Controller ctrl, Frame parent, InfoUsuario usuario) {
		_ctrl = ctrl;
		_parent = parent;
		_parent.setEnabled(false);
		_usuario = usuario;
		initGui();
	}

	private void initGui() {
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 393, 166);
		
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Label textoAviso
		JLabel textoAviso1 = new JLabel("Usted esta a punto de borrar su cuenta");
		textoAviso1.setFont(new Font("Tahoma", Font.BOLD, 12));
		textoAviso1.setBounds(16, 11, 253, 14);
		contentPane.add(textoAviso1);
		
		JLabel textoAviso2 = new JLabel("<html><p> Aviso! En caso de darle a Aceptar perdera toda la informacion y contenido de la cuenta</p></html>");
		textoAviso2.setFont(new Font("Tahoma", Font.BOLD, 11));
		textoAviso2.setHorizontalAlignment(SwingConstants.RIGHT);
		textoAviso2.setVerticalAlignment(SwingConstants.TOP);
		textoAviso2.setBounds(16, 37, 266, 28);
		contentPane.add(textoAviso2);
		
		// Label aceptar
		JLabel textoAceptar = new JLabel("Pulse Aceptar en caso de estar seguro");
		textoAceptar.setForeground(new Color(0, 98, 24));
		textoAceptar.setBounds(16, 76, 200, 14);
		contentPane.add(textoAceptar);
				
		// Boton aceptar
		JButton aceptar = new JButton("Aceptar");
		aceptar.setForeground(new Color(0, 98, 24));
		aceptar.setBounds(211, 93, 90, 23);
		aceptar.addActionListener((e) -> {
			_ctrl.eliminarUsuario(_usuario.get_id());
			setVisible(false);
			new LogInView(_ctrl);
		});
		contentPane.add(aceptar);
		
		// Label cancelar
		JLabel textoCancelar = new JLabel("Pulse Cancelar en caso contrario");
		textoCancelar.setForeground(new Color(91, 0, 0));
		textoCancelar.setBounds(16, 97, 170, 14);
		contentPane.add(textoCancelar);
		
		// Boton cancelar
		JButton cancelar = new JButton("Cancelar");
		cancelar.setForeground(new Color(100, 0, 0));
		cancelar.setBounds(292, 93, 90, 23);
		cancelar.addActionListener((e) -> {
			setVisible(false);
			_parent.setEnabled(true);
		});
		contentPane.add(cancelar);
	}
}
