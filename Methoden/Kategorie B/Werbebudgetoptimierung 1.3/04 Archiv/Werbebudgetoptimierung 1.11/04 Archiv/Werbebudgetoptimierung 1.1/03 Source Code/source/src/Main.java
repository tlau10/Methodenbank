/*
    Klasse Main

    Diese Klasse enthaelt eine statische main()-Methode um
    das Porgramm zu starten und stellt den Frame dar
 */

import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
	boolean packFrame = false;

	// Die Anwendung konstruieren
	public Main() {
		WerbeBudgetApplication frame = new WerbeBudgetApplication();

		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}

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

	// Main-Methode
	public static void main(String[] args) {
		new Main();
	}
}
