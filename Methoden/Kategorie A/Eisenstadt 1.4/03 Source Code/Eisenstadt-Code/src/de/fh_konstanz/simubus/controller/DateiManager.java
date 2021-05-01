package de.fh_konstanz.simubus.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.jdom.JDOMException;

import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.View;

/**
 * Die Klasse <code>DateiManager</code> stellt Methoden zum laden und speichern von Modellen
 * zur Verfuegung.
 * 
 * @author Daniel Weber, Ingo Kroh
 * @version 1.0 (22.06.2006)
 */
public final class DateiManager {

	public static void speichern(File datei) throws IOException {

		if (datei.exists() == true) {
			if (JOptionPane.showOptionDialog(View.getInstance(),
					"Die Datei existiert bereits. Wollen Sie die Datei überschreiben?", "Datei speichern",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
					null) == JOptionPane.OK_OPTION) {
				datei.delete();
				write(datei);
			}
		} else {
			write(datei);
		}
	}

	private static void write(File datei) throws FileNotFoundException, IOException {
		XMLFileManager xmlFileManager = XMLFileManager.getInstance();
		xmlFileManager.saveAsXML(datei);
	}

	public static void laden(File datei) throws IOException, ClassNotFoundException, JDOMException {

		XMLFileLoader xmlLoader = new XMLFileLoader(datei);

		SimuPanel sp = SimuPanel.getInstance();
		xmlLoader.initSimuPanel(sp);

	}
}
