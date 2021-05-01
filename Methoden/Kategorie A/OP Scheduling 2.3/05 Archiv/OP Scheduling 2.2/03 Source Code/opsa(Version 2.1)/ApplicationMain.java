package opsa;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

/*
 * Title: OP-Scheduling
 * erweitert Juni 2003
 * @author Nina Bruch, Katharina Dammeier
 * @version 2.0
 */

public class ApplicationMain {
	/** Main method */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new ApplicationMain();
	}

	boolean packFrame = false;

	/** Construct the application */
	public ApplicationMain() {
		FmOpSa frame = new FmOpSa();
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
}