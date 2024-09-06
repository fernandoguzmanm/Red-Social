package launcher.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import Users.Usuario;
import launcher.control.Controller;

public class ObservacionView extends JFrame{
	
	private Controller _ctrl;
	private Usuario _usuario;

	private JPanel _textAreaPanel;
	private JPanel _calificacionPanel;
	
	private DefaultComboBoxModel<String> _observacion;
	private DefaultComboBoxModel<Integer> _calificacion;
	private JTextArea textArea;
	
	
	public ObservacionView(Controller ctrl) {
		super("REALIZAR UNA OBSERVACION");
		initGUI();
		_ctrl = ctrl;
	}

	/**
	 * 
	 */
	private void initGUI() {
		this.setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 509);
		
		// Content Pane
		JPanel	contentPane = new JPanel();
		contentPane.setBackground(new Color(60, 179, 113));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Label principal
		JLabel principal = new JLabel("Aqui puedes hacer tu observacion");
		principal.setFont(new Font("Tahoma", Font.PLAIN, 19));
		principal.setBounds(59, 22, 329, 26);
		contentPane.add(principal);
		
		// Label de opciones
		JLabel opciones = new JLabel("Elige una de las opciones:");
		opciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		opciones.setBounds(32, 79, 193, 26);
		contentPane.add(opciones);
		
		// Combo box de tipos de observacion
		init_comboBoxes();
		JComboBox<String> observacion = new JComboBox<>(_observacion);
		observacion.setForeground(new Color(18, 18, 237));
		observacion.setToolTipText("Observaciones que puedes realizar\r\n");
		observacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		observacion.setBounds(214, 82, 107, 21);
		observacion.addActionListener((e)-> show_textArea(observacion.getSelectedIndex()));
		contentPane.add(observacion);
		
		_textAreaPanel = new JPanel();
		_textAreaPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(46, 139, 87), null, null, null));
		_textAreaPanel.setBackground(new Color(46, 139, 87));
		_textAreaPanel.setBounds(42, 183, 329, 186);
		_textAreaPanel.setLayout(new BorderLayout(0, 0));
		_textAreaPanel.setVisible(false);
		contentPane.add(_textAreaPanel);
		
		// Label explicacion
		JLabel problema = new JLabel("Explique aqui su problema");
		problema.setBackground(Color.WHITE);
		problema.setFont(new Font("Tahoma", Font.BOLD, 13));
		_textAreaPanel.add(problema, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		_textAreaPanel.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(new Color(46, 139, 87));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
		_calificacionPanel = new JPanel();
		_calificacionPanel.setBackground(new Color(60, 179, 113));
		_calificacionPanel.setBounds(80, 140, 246, 33);
		_calificacionPanel.setVisible(false);
		contentPane.add(_calificacionPanel);
		_calificacionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// Label de valoracion
		JLabel lblNewLabel_3 = new JLabel("Valoracion de la aplicacion:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		_calificacionPanel.add(lblNewLabel_3);
		
		JComboBox<Integer> calificacion = new JComboBox<>(this._calificacion);
		calificacion.setForeground(Color.BLUE);
		calificacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		_calificacionPanel.add(calificacion);
		
		// Boton de enviar
		JButton aceptar = new JButton("Enviar");
		aceptar.setBackground(new Color(46, 139, 87));
		aceptar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		aceptar.setBounds(224, 404, 135, 40);
		aceptar.addActionListener((e)-> {
			get_text();
			setVisible(false);
		});
		contentPane.add(aceptar);
		
		// Boton de cancelar
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(new Color(46, 139, 87));
		cancelar.setFont(new Font("Tahoma", Font.PLAIN, 22));
		cancelar.setBounds(59, 404, 135, 40);
		cancelar.addActionListener((e)-> {
			setVisible(false);
		});
		contentPane.add(cancelar);
	}
	
	private void show_textArea(int index) {
		if(index == 0) _calificacionPanel.setVisible(true);
		else _calificacionPanel.setVisible(false);
		_textAreaPanel.setVisible(true);
	}
	
	private void init_comboBoxes() {
		this._calificacion = new DefaultComboBoxModel<Integer>();
		this._observacion = new DefaultComboBoxModel<String>();
		String[] observaciones= {"Resenha", "Peticion de cambio", "Reclamacion"};
		int[] valoraciones= {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		for(int i =0 ; i < observaciones.length; i++) {
			this._observacion.addElement(observaciones[i]);
		}
		
		for(int i =0 ; i < valoraciones.length; i++) {
			this._calificacion.addElement(valoraciones[i]);
		}
	}
	
	private void get_text() {
		String observacion = _observacion.getSelectedItem().toString();
		if(observacion.equalsIgnoreCase("Resenha")) {
			int nota = Integer.parseInt(_calificacion.getSelectedItem().toString());
			String texto = this.textArea.getText();
			// _ctrl.crearObservacion("Resenha", texto, nota);
		}else if(observacion.equalsIgnoreCase("Peticion de cambio")) {
			String texto = this.textArea.getText();
//			_ctrl.crearObservacion("Cambio", texto, null);
		}else if(observacion.equalsIgnoreCase("Reclamacion")) {
			String texto = this.textArea.getText();
//			_ctrl.crearObservacion("Reclamacion", texto, null);
		}
	}
}
