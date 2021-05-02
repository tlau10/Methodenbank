package de.fh_konstanz.simubus.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.jdom.JDOMException;

import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.View;

/**
 * Die Klasse <code>DateiManager</code> stellt Methoden zum laden und speichern
 * zur Verfuegung.
 * 
 * @author unkonwn
 * @version 1.0
 *
 */
public final class DateiManager {
	/**
	 * speichert ein Optimierungs-/Simulationsmodell
	 * 
	 * @param datei Datei, in die gespeichert werden soll
	 * @throws IOException wenn beim Speichern ein Fehler auftritt
	 */
	public static void speichern(File datei) throws IOException {

		//proof if file exists
		if (datei.exists() == true) {
			if (JOptionPane
					.showOptionDialog(
							View.getInstance(),
							"Die Datei existiert bereits. Wollen Sie die Datei überschreiben?",
							"Datei speichern", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {
				datei.delete();
				write(datei);
			}
		} else {
			write(datei);
		}

	}

	/**
	 * speichert die Umgebung als XML ab
	 * 
	 * @param datei Datei, in die gespeichert werden soll
	 * @throws FileNotFoundException wenn <code>datei</code> ein Verzeichnis ist
	 * @throws IOException wenn beim Speichern ein Fehler auftritt
	 */
	private static void write(File datei)throws FileNotFoundException, IOException {
		
		// init the XMLFileManager
		XMLFileManager xmlFileManager = XMLFileManager.getInstance();
		xmlFileManager.saveAsXML(datei);
	}

	/**
	 * laedt ein Optimierungs-/Simulationsmodell
	 * @param datei Datei, aus der gelesen werden soll
	 * @throws IOException 
	 * @throws IOException wenn beim Laden ein Fehler auftritt
	 * @throws ClassNotFoundException 
	 * @throws ClassNotFoundException wenn beim Deserialieren ein Fehler auftritt
	 * @throws JDOMException 
	 * @throws JDOMException 
	 */
	public static void laden(File datei) throws IOException,ClassNotFoundException, JDOMException {
		
		XMLFileLoader xmlLoader = new XMLFileLoader(datei);

		SimuPanel sp = SimuPanel.getInstance();
		//TODO FileLoader Methode in XMLFileLoader -> auslagern
		//load Haltestelle
		xmlLoader.initSimuPanel(sp);

	}
}
