package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.UIManager;

import model.Cplex;
import model.LP_Solve;
import model.SolverInterface;
import model.XmlListHandler;

import org.jdom.Element;

import view.ErrorMessages;
import view.MainFrame;

/**
 * <p>
 * Title: Application Diaetplaner
 * </p>
 * <p>
 * Description: Anwendung mit Main-Methode für Programm Diätplaner
 * </p>
 * <p>
 * Copyright: Matthias Siegert Copyright (c) 2003,  (Refactoring 2015 by Julien Hespeler, Dusan Spasic)
 * </p>
 * <p>
 * Company:FH Konstanz
 * </p>
 * 
 * @author Matthias Siegert, Julien Hespeler, Dusan Spasic
 * @version 2.0
 */

public class ApplicationDiaetplaner implements DiaetplanerInterface {
	private boolean packFrame = false;
	private boolean printMatrix_ = true;

	private SolverInterface mySolver = new LP_Solve();

	// Enthält alle verfügbaren Lebensmittel
	private Vector<Object> allEatables;

	// Enthält alle ausgewählten Lebensmittel
	private Vector<Object> menu;
	private XmlListHandler myListHandler;

	// Enthält Restriktionen und Zielfunktion
	private Matrix myMatrix;

	// Matrix zur Zuordnung der Lebensmittel zu den Gruppen
	private Matrix borderMatrix;

	// Array enthält Zusammenhang Gruppen ID und Gruppenname
	private String[] groupName;
	private double menuCalories = 0;
	private String xmlPath = "Lebensmittel.xml";

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Fehler in der Erzeugung des UIManagers");
		}
		new ApplicationDiaetplaner();
	}

	public Vector<Object> getAllEatables() {
		return allEatables;
	}

	public double getMenuCalories() {
		return menuCalories;
	}

	public ApplicationDiaetplaner() {
		// Verfügbare Lebensmittel werden eingelesen
		readFromXmlFile(xmlPath);

		// Zuordnung GruppenNummer zu Gruppenname herstellen
		groupName = new String[11];
		groupName[0] = "Milchprodukte";
		groupName[1] = "Cerealien";
		groupName[2] = "Backware_fruehstueck";
		groupName[3] = "Getraenk_fruehstück";
		groupName[4] = "Hauptgericht";
		groupName[5] = "Beilage";
		groupName[6] = "Dessert";
		groupName[7] = "Getränk_mittagessen";
		groupName[8] = "Brotbelag";
		groupName[9] = "Backwaren_abendessen";
		groupName[10] = "Getraenk_abendessen";

		MainFrame frame = new MainFrame(allEatables, this);
		ErrorMessages.setMainFrame(frame);

		if (packFrame)
			frame.pack();
		else
			frame.validate();

		// Das Fenster wird auf dem Birldschirm zentriert
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();

		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;

		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public Vector<Object> executeCalculation(Vector<Object> choosenEatables, int calories) {
		// Matrix erzeugen
		int numberOfFoodGroups = 11;

		int numberOfMatrixRows = numberOfFoodGroups + 2;
		int numberOfMatrixColumns = allEatables.size() + 2;
		int firstSelectedFood = numberOfFoodGroups + 2;
		int countSelectedFoods = firstSelectedFood;

		myMatrix = new Matrix(numberOfMatrixRows, numberOfMatrixColumns, 1000,
				1000);
		borderMatrix = new Matrix(allEatables.size(), 3, allEatables.size(), 3);

		// Alle Lebensmittel durchlaufen
		for (int i = 0; i < allEatables.size(); i++) {
			Element myElement = (Element) allEatables.get(i);

			// Zielfunktion in Matrix schreiben
			myMatrix.setElement(0, i, myElement.getChild("Kalorien").getText());

			// Tagesbedarf erstellen
			myMatrix.setElement(1, i, myElement.getChild("Kalorien").getText());

			// Zugehörigkeiten zu Lebensmittelgruppen erstellen
			// Zeile der Matrix ist Gruppennummer + 2 (Zielfunktion und
			// Tagesbedarf)
			int countFoodToGroups = Integer.parseInt(myElement
					.getChild("GruppenID").getText()) + 2;
			myMatrix.setElement(countFoodToGroups, i, "1");

			// Jedes ausgewaehlte Lebensmittel muss einmal vorkommen
			if (choosenEatables.get(i) != null) {
				myMatrix.setElement(countSelectedFoods, i, "1");
				countSelectedFoods++;
			}

			// Grenzenmatrix fuellen Alle Anzahlen muessen ganzzahlig sein und
			// zwischen 0 und 1 liegen !
			borderMatrix.setElement(i, 0, 0);
			borderMatrix.setElement(i, 1, 1);
			borderMatrix.setElement(i, 2, "ja");
		}

		// Zeichen und rechte Seite der Matrix setzen

		// Zielfunktion
		myMatrix.setElement(0, (allEatables.size() + 1), "min");

		// Tagesbedarf
		myMatrix.setElement(1, allEatables.size(), ">=");
		myMatrix.setElement(1, (allEatables.size()) + 1, calories);

		// Zugehörigkeiten zu Lebensmittelgruppen
		for (int j = 2; j < (numberOfFoodGroups + 2); j++) {
			myMatrix.setElement(j, allEatables.size(), ">=");
			myMatrix.setElement(j, (allEatables.size() + 1), "1");
		}

		// Jedes ausgewaehlte Lebensmittel muss einmal vorkommen
		for (int m = firstSelectedFood; m < countSelectedFoods; m++) {
			myMatrix.setElement(m, allEatables.size(), "=");
			myMatrix.setElement(m, (allEatables.size() + 1), "1");
		}

		// Solver aufrufen
		double[] result = mySolver.calcModel(myMatrix, borderMatrix);

		menu = (Vector<Object>) allEatables.clone();

		for (int i = 1; i < result.length; i++) {
			System.out.println("x" + i + " = " + result[i]);

			// Alle Nahrungsmittel, die nicht im Menü vorkommen
			// werden rausgeschmissen
			if (result[i] == 0.0)
				menu.setElementAt(null, i - 1);
		}

		// Ausgerechnete Kalorien setzen
		menuCalories = result[0];

		// Aus Ergebnis Vector menu zurückgeben
		if (printMatrix_ == true)
			printmatrix(myMatrix);

		return menu;
	}

	public void setSolverToCalculate(String solverName) {
		switch (solverName) {
		case "LP_Solve":
			mySolver = new LP_Solve();
			break;
		case "Cplex":
			mySolver = new Cplex();
			break;
		default:
			mySolver = new LP_Solve();
			break;
		}
	}

	public Element newEatable(String name, int groupID, String amount,
			String calories) {

		// Erstes Element aus Liste wird kopiert
		Element myElement = (Element) myListHandler.getList().get(0);
		Element myNewElement = (Element) myElement.clone();

		// Neue Parameter werden gesetzt
		myNewElement.getChild("Name").setText(name);
		myNewElement.getChild("GruppenID").setText(String.valueOf(groupID));
		myNewElement.getChild("Menge").setText(amount);
		myNewElement.getChild("Kalorien").setText(calories);
		myNewElement.getChild("Gruppe").setText(groupName[groupID]);

		return myNewElement;
	}

	public void addEatable(Element newEatable) {
		// Neues Lebensmittel in die Liste einfuegen
		myListHandler.addElement(newEatable);

		// Neues Lebensmittel dem Vector hinzufügen
		updateAllEatablesVector();
	}

	public boolean deleteEatable(Element pEatable) {

		String groupNumber = String.valueOf(pEatable.getChild("GruppenID")
				.getText());
		String name = String.valueOf(pEatable.getChild("Name").getText());
		boolean found = false;

		for (int i = 0; i < allEatables.size(); i++) {
			// Vector allEatables wird nach entsprechendem Element durchsucht
			Element temp = (Element) allEatables.get(i);

			// Wenn Name und GruppenID stimmen, darf Element geloescht werden
			if (temp.getChild("GruppenID").getText().equals(groupNumber)
					&& !temp.getChild("Name").getText().equals(name)) {
				found = true;
				break;
			}
		}

		if (found) {
			// Löscht ein Element aus der Lebensmittelliste
			myListHandler.deleteElement(pEatable);

			// Updaten des AllEatables Vector
			updateAllEatablesVector();

			return true;
		}

		return false;
	}

	public String[] getGruppenName() {
		return groupName;
	}

	public void setPrintMatrix(boolean print) {
		printMatrix_ = print;
	}

	public void saveToXmlFile(String filename) {
		// Geänderten Vector ins XML-File schreiben
		try {
			myListHandler.saveXmlList(filename);
		} catch (Exception e) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Speichern der Nahrungsmittel XML-Datei");
		}
	}

	public void readFromXmlFile(String filename) {
		// Vector allEatables initialisieren
		myListHandler = new XmlListHandler(filename);

		// Der Vector muss so gross sein wie die Liste
		allEatables = new Vector<Object>(myListHandler.getList().size());

		// Elemente hinzufuegen
		updateAllEatablesVector();
	}

	private void updateAllEatablesVector() {
		// Erst wird der alte Stand komplett rausgeschmissen
		allEatables.removeAllElements();

		// Anschließend wird der Vector mit der neuen Liste gefuellt
		for (int i = 0; i < myListHandler.getList().size(); i++) {
			allEatables.add(myListHandler.getList().get(i));
		}
	}

	private void printmatrix(Matrix myMatrix) {
		try {
			String enter = System.getProperty("line.separator");
			File matrixFile = new File("c:\\temp\\matrix.txt");
			FileWriter matrixFileOut = new FileWriter(matrixFile);

			for (int i = 0; i < myMatrix.getNumberOfRows(); i++) {
				for (int j = 0; j < myMatrix.getNumberOfColumns(); j++) {
					matrixFileOut.write(myMatrix.getElement(i, j) + " ");
				}

				if (i > 1 && i < 13)
					matrixFileOut.write("  (" + groupName[i - 2] + ")");

				matrixFileOut.write(enter);
			}

			matrixFileOut.write(enter + enter);

			Element myElement;
			for (int i = 0; i < allEatables.size(); i++) {
				myElement = (Element) allEatables.get(i);
				matrixFileOut.write("x" + (i + 1) + " = "
						+ myElement.getChild("Name").getText() + enter);
			}

			matrixFileOut.flush();
			matrixFileOut.close();
			Runtime rt = Runtime.getRuntime();
			rt.exec("notepad.exe c:\\temp\\matrix.txt");
		} catch (Exception e) {
			ErrorMessages.throwErrorMessage("Fehler beim Anzeigen der Matrix");
		}
	}
}