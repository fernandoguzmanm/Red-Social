package launcher.view;

import java.awt.Frame;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import launcher.control.Controller;
import negocio.InfoPublicacion;

public class EstadisticasView extends JFrame{
	
	int likes = 0;
	int numPublicaciones = 0;
	List<InfoPublicacion> _listaPublicaciones;
	
	private Controller _ctrl;
	Frame _parent;
	
	EstadisticasView(Controller ctrl, List<InfoPublicacion> lista, Frame parent){
		super("[ESTADISTICAS]");
		_ctrl = ctrl;
		_listaPublicaciones = lista;
		_parent = parent;
		initGUI();
	}

	private void initGUI() {
		
		setVisible(true);
		setBounds(75, 75, 350, 300);
		
		// Calculo de likes, views, etc
		numPublicaciones = _listaPublicaciones.size();
		for (InfoPublicacion p : _listaPublicaciones) {
			likes += p.get_likes();
		}
		
		// Main Panel
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		
		// Menu Bar
		JMenuBar menuBar = new JMenuBar();
		//menuBar.setForeground(new Color(220, 220, 220));
		//menuBar.setBackground(new Color(238, 232, 170));
		menuBar.add(Box.createHorizontalGlue());
		setJMenuBar(menuBar);
		
		// Boton retroceder
		JButton botonRetroceder = new JButton(new ImageIcon("icons/flecha.png"));
		botonRetroceder.addActionListener((e) -> {
			setVisible(false);
		});
		menuBar.add(botonRetroceder);
		
		// Numero de likes
		contentPane.add(new JLabel("Likes: " + likes));
		
		// Total publicaciones
		contentPane.add(new JLabel("Publicaciones: " + numPublicaciones));
	}
}
