package launcher.view;

import javax.swing.JFrame;

import Users.Estandar;
import Users.Usuario;
import Users.Usuario.Tipo;
import launcher.control.Controller;

public class MainWindow extends JFrame {

	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
	}

	
	private void initGUI() {
		LogInView vista1 = new LogInView(_ctrl);

//		AdminView vista12 = new AdminView(_ctrl, null);
	}
}
