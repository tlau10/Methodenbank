package de.fh_konstanz.simubus.model.optimierung;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Die Klasse <code>Result</code> entspricht einer Loesung der Optimierung.
 * Sie enthaelt eine Basisloesung, Informationen zu den Zielen der Loesung, die
 * Gesamtzeit fuer alle Ziele zu den entsprechenden Haltestellen und die
 * Durchschnittszeit fuer alle Ziele zu den Haltestellen.
 * 
 * @author Ingo Kroh, Erkan Erpolat
 * @version 1.0 (25.06.2006)
 */

public class Result implements Comparable<Result> {
	/** Basisloesung des Optimierungsergebnis */
	private BasicSolution basicSolution = null;

	/** Informationen zum Ziel der Loesung */
	private List<TargetDetails> targetDetails = null;

	/** Gesamtzeit fuer alle Ziele zu den Haltestellen */
	private double totalTime = 0;

	/** Durchschnittszeit fuer alle Ziele zu den Haltestellen */
	private double averageTime = 0;

	/** ID der Loesung */
	private String id = null;

	/** Uhrzeit und Datum ausgeben */
	private GregorianCalendar dateAndTime;

	/**
	 * Konstruktor
	 * 
	 * @param aBasicSolution
	 *            Basisloesung fuer das Ergebnis
	 */
	public Result(BasicSolution aBasicSolution) {
		dateAndTime = new GregorianCalendar();
		basicSolution = aBasicSolution;
		targetDetails = new ArrayList<TargetDetails>();

	}

	/**
	 * liefert die Basisloesung fuer das Ergebnis
	 * 
	 * @return Basisloesung fuer das Ergebnis
	 */
	public BasicSolution getBasicSolution() {
		return basicSolution;
	}

	/**
	 * liefert Informationen zu den Zielen der Loesung
	 * 
	 * @return Informationen zu den Zielen der Loesung
	 */
	public TargetDetails[] getTargetDetails() {
		return targetDetails.toArray(new TargetDetails[targetDetails.size()]);
	}

	/**
	 * liefert die Gesamtzeit fuer alle Ziele zu den Haltestellen
	 * 
	 * @return Gesamtzeit fuer alle Ziele zu den Haltestellen
	 */
	public double getTotalTime() {
		return totalTime;
	}

	/**
	 * fuegt der Loesung Informationen zu einem Ziel hinzu
	 * 
	 * @param aTargetDetails
	 *            Informationen zu einem Ziel hinzu
	 */
	public void addTargetDetails(TargetDetails aTargetDetails) {
		targetDetails.add(aTargetDetails);
	}

	/**
	 * setzt die Gesamtzeit fuer alle Ziele zu den Haltestellen
	 * 
	 * @param aTotalTime
	 *            Gesamtzeit fuer alle Ziele zu den Haltestellen
	 */
	public void setTotalTime(double aTotalTime) {
		this.totalTime = Math.round(aTotalTime * 100.) / 100.;
	}

	@Override
	public String toString() {
		return id;
	}

	/**
	 * gibt an ob die uebergebene Loesung eine kleinere, gleiche oder groessere
	 * Gesamtzeit hat.
	 * 
	 * @param o
	 *            die zu vergleichende Loesung
	 * @return negative Zahl, falls <code>o</code> groesser ist, 0 bei
	 *         Gleichheit oder eine positive Zahl falls <code>o</code> kleiner
	 *         ist.
	 */
	public int compareTo(Result o) {
		int result = 0;

		double diff = totalTime - o.totalTime;

		if (diff < 0) {
			result = -1;
		} else if (diff > 0) {
			result = 1;
		}

		return result;
	}

	/**
	 * liefert die Durchschnittszeit fuer alle Ziele zu den Haltestellen
	 * 
	 * @return Durchschnittszeit fuer alle Ziele zu den Haltestellen
	 */
	public double getAverageTime() {
		return averageTime;
	}

	/**
	 * setzt die Durchschnittszeit fuer alle Ziele zu den Haltestellen
	 * 
	 * @param averageTime
	 *            Durchschnittszeit fuer alle Ziele zu den Haltestellen
	 */
	public void setAverageTime(double averageTime) {
		this.averageTime = Math.round(averageTime * 100.) / 100.;
	}

	/**
	 * liefert die ID der Loesung
	 * 
	 * @return ID der Loesung
	 */
	public String getId() {
		return id;
	}

	/**
	 * setzt die ID der Loesung
	 * 
	 * @param id
	 *            ID der Loesung
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Lifert die Zeit der Lösung
	 * 
	 * @return Zeit der Loesung
	 */
	public GregorianCalendar getDateAndTime() {
		return dateAndTime;
	}

}