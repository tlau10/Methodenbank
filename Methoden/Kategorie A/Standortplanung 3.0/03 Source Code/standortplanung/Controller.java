package standortplanung;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * <p>
 * Überschrift: Standortplanung
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author Manuel Thoma, Markus Klemens
 * @version 3.0
 */

public class Controller {
	private boolean packFrame = false;
	public HauptFrame_new frame;
	String filename = null;

	// Die Anwendung konstruieren
	public Controller() {
		frame = new HauptFrame_new(this);
		// Frames überprüfen, die voreingestellte Größe haben
		// Frames packen, die nutzbare bevorzugte Größeninformationen enthalten,
		// z.B. aus ihrem Layout
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}
		// Das Fenster zentrieren
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

	public String starteSolve(Daten matrix, int solverTyp, int distanz) {
		String result = "Verwendeter Solver: ";
		Solver solve = null;
		if (solverTyp == 1) {
			solve = new LP_Solve();
			result = result + "LP";
		}
		if (solverTyp == 2) {
			solve = new XA();
			result = result + "XA";
		}
		if (solverTyp == 3) {
			solve = new MOPS();
			result = result + "MOPS";
		}

		solve.starteSolver(matrix, distanz);

		String[] ergebnis = solve.getErgebnis();
		result = result + "\n\n" + "Typ: Minimierung";
		result = result + "\n" + "Zielfunktionswert: "
				+ solve.getZielfunktionswert() + "\n\n";
		result = result + "Entfernung: " + distanz + "\n\n";
		result = result + "Empfohlene(r) Standort(e): " + "\n";
		for (int i = 0; i < ergebnis.length; i++)
			result = result + ergebnis[i] + "\n";

		return result;
	}

	public Daten dateiOeffnen(ActionEvent e) throws Exception {
		Daten matrix = null;
		JTextField jTextField = new JTextField();
		jTextField.setText("." + Variablen.fileExtension);

		JFileChooser fc = new JFileChooser("Daten");
		FileFilterStandort myFilter = new FileFilterStandort(
				Variablen.fileExtension);
		fc.addChoosableFileFilter(myFilter);

		int returnVal = fc.showOpenDialog(null); // ok=1, sonst=0

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String path = file.getAbsolutePath();
			jTextField.setText(path);
		}
		if (returnVal != 1) // if(datei öffnen)
		{
			String filename = jTextField.getText();
			try {
				matrix = new FileHandler().readData(filename);
				this.filename = filename;
				return matrix;
			} catch (Exception eOeffnen) {
				throw eOeffnen;
			}
		}

		else
			// abbrechen des Filechoosers
			return null;
	}

	public boolean dateiSpeichern(ActionEvent e, Daten matrix, int distanz) {
		if (filename == null) {
			if (!this.dateiSpeichernUnter(e, matrix, distanz))
				// if(! rufe dateiSpeichernUnter) {speichert filename in
				// this.filename und ruft dateiSpeichern() nochmal auf}
				return false;// quit
		} else// ist filename ok
		{
			try {
				new FileHandler()
						.writeData(matrix, filename, distanz);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Fehlerhafte Datei!",
						"Fehler", JOptionPane.WARNING_MESSAGE);
			}
		}
		return true;
	}

	public boolean dateiSpeichernUnter(ActionEvent e, Daten matrix,
			int distanz) {
		JTextField jTextField = new JTextField("." + Variablen.fileExtension);

		JFileChooser fc = new JFileChooser();
		FileFilterStandort myFilter = new FileFilterStandort(
				Variablen.fileExtension);
		fc.addChoosableFileFilter(myFilter);

		boolean fileElected = false;

		while (!fileElected)// solange keine Datei ausgewählt
		{
			int returnVal = fc.showSaveDialog(null); // File chooser öffnen,
			// 1==abbrechen,
			// 0==speichern

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String path = file.getAbsolutePath();
				jTextField.setText(path);
			}

			if (returnVal == 0)// speichern
			{
				String filename = jTextField.getText(); // holt Filename
				if (!this.isFilename(filename))
					filename = filename + "." + Variablen.fileExtension; // Dateityp
				// .csv
				// anhängen

				if (new File(filename).exists())// falls File existiert
				{
					//if (frame.jOptionPane_DateiUeberschreiben())// if(file
						// overwrite)
						// 0==ja, 1==no
						fileElected = true;// fileElected = true --> while
					// verlassen
					this.filename = filename;
				} else// falls Datei nicht existiert
				{
					this.filename = filename;
					fileElected = true; // fileElected = true --> while
					// verlassen
				}
			} else
				// speichern verlassen
				return false;
		}

		this.dateiSpeichern(e, matrix, distanz); // ruft
		// dateiSpeichern
		// (filename in
		// Controller.filename
		// speichern)

		return true;
	}

	private boolean isFilename(String filename) {
		if (filename.substring(filename.length() - 2, filename.length() - 1)
				.equals("."))
			return true;
		else if (filename.substring(filename.length() - 3,
				filename.length() - 2).equals("."))
			return true;
		else if (filename.substring(filename.length() - 4,
				filename.length() - 3).equals("."))
			return true;
		else if (filename.substring(filename.length() - 5,
				filename.length() - 4).equals("."))
			return true;
		else
			return false;
	}
}
