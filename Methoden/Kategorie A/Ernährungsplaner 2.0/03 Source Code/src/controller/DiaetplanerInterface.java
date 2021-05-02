package controller;

import java.util.Vector;
import org.jdom.Element;

/**
 * <p>
 * Title: Diaeplaner Interface
 * </p>
 * <p>
 * Description: Interface, verwendet zum kollaborativen Programmieren
 * </p>
 * <p>
 * Copyright: Matthias Siegert Copyright (c) 2003 (Refactoring 2015 by Julien Hespeler, Dusan Spasic)
 * </p>
 * <p>
 * Company: FH Konstanz
 * </p>
 * 
 * @author Matthias Siegert, Julien Hespeler, Dusan Spasic
 * @version 2.0
 */

public interface DiaetplanerInterface {
	// Matrix erzeugen
	// Solver aufrufen
	// Aus Ergebnis Vector menu zurückgeben
	public Vector<Object> executeCalculation(Vector<Object> choosenEatables, int calories);

	// Solver auswaehlen
	public void setSolverToCalculate(String solverName);

	// Neues Lebensmittel dem Vector hinzufügen und in XMLFile abspeichern
	public void addEatable(Element newEatable); 

	// Die folgende Methode generiert ein neues Element aus Uebergabeparametern
	// (Name, Lebensmittelgruppe, Menge, Kalorien)
	public Element newEatable(String name, int gruppenID, String amount,
			String calories);

	// Löscht ein Element aus dem Vector und dem XML-File
	public boolean deleteEatable(Element Eatable);

	// Diese Methode liefert nach der Berechnung des Menues die ausgerechneten
	// Kalorien zurück
	public double getMenuCalories();

	// Diese Methode liefert das Array zurueck, das den Zusammenhang zwischen
	// GruppenID und Gruppenname liefert
	public String[] getGruppenName();
}