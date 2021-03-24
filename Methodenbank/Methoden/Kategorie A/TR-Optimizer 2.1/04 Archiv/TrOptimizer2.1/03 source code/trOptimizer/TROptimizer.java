package trOptimizer;

/**
 * <p>Title: TR-Optimizer</p>
 * <p>Description: TR-Optimizer ist eine Weiterentwicklung von TransOp</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

import java.util.Timer;

public class TROptimizer {
	public boolean packFrame = false;
	public MainFrame frame;

	/**
	 * Der Konstruktor erzeugt einen MainFrame und damit die eigentliche
	 * Anwendung.
	 */
	public TROptimizer() {
		frame = new MainFrame();
		// Validate frames that have preset sizes
		// Pack frames that have useful preferred size info, e.g. from their
		// layout
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}
		// Center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
	}

	/**
	 * Main-Methode und damit Einstieg ins Programm
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TROptimizer to;
		GlobaleVariable.autosaveSTATUS = false;
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel(
			// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );

		} catch (Exception e) {
			e.printStackTrace();
		}
		to = new TROptimizer();

		while (true) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException ex) {
			}
			to.frame.repaint();
		}

	}

}