package launcher;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

import launcher.control.Controller;
import launcher.view.MainWindow;

public class Main {
	public static void main(String[] args) {
		Controller controlador = new Controller();
		
		try {
			SwingUtilities.invokeAndWait(() -> new MainWindow(controlador));
			
		} catch (InvocationTargetException | InterruptedException e ) {
			e.printStackTrace();
		}
	}
}