package de.fh_konstanz.simubus.model;

import java.awt.Dimension;
import java.io.Serializable;

import de.fh_konstanz.simubus.util.Logger;

/**
 * Die Klasse <code>SimuKonfiguration</code> stellt Default-Einstellungen fuer
 * die Simulation und Optimierung zur Verfuegung.
 * 
 * @author Robert Audersetz, Ingo Kroh, Michael Franz, Daniel Weber
 * @version 1.0 (10.05.2005)
 * @version 1.1 (02.07.2006)
 * 
 */
public class SimuKonfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 444081520886035282L;

	/**
	 * Instanz der SimuKonfiguration
	 */
	private static SimuKonfiguration instance = null;

	/**
	 * Wert bei bestimmter Auflösung
	 */
	private final int RES_1024x768 = 0;

	/**
	 * Wert bei bestimmter Auflösung
	 */
	private final int RES_1280x1024 = 75;

	/**
	 * aktuelle Auflösung
	 */
	private int res_Actual = RES_1280x1024;

	/**
	 * Grösse eines Planquadrates
	 */
	private final int feldElementGroesse = 13;

	/**
	 * Grösse des Fenster Einstellungen
	 */
	private final Dimension einstellungenFrame = new Dimension(330, 400);

	/**
	 * Maßstab, wieviel Meter ein Planquadrat im Reellen darstellt
	 */
	private double feldElementGroesseInM = 65;

	/**
	 * durchschnittliche Busgeschwindigkeit
	 */
	private double busGeschwindigkeitInKmH = 40;

	/**
	 * Gehgeschwindigkeit eines Fußgängers, wenn ein Weg zu Fuß zurück gelegt
	 * wird.
	 */
	private double gehGeschwindigkeitInKmH = 6;

	private boolean logToFile = false;

	private boolean logToConsole = false;

	private boolean soundCheck = true;

	/**
	 * Konstruktor
	 * 
	 */
	private SimuKonfiguration() {

		// Logger anwerfen
		Logger logger = Logger.getInstance();
		logger.setLogToConsole(logToConsole);
		logger.setLogToFile(logToFile);
	}

	/**
	 * 
	 * @return liefert die Instanz der Simukonfiguration
	 */
	public static SimuKonfiguration getInstance() {
		if (instance == null) {
			instance = new SimuKonfiguration();
		}

		return instance;
	}

	/**
	 * 
	 * @param instance
	 *            Setzt die instance-Variable neu
	 */
	public static void setInstance(SimuKonfiguration instance) {
		SimuKonfiguration.instance = instance;
	}

	/**
	 * 
	 * @return liefert den Index für die aktuelle Auflösung
	 */
	public int getActiveResolutionForCombo() {
		if (res_Actual == RES_1024x768)
			return 0;

		return 1;
	}

	/**
	 * 
	 * @param index
	 *            setzt den Index für die aktuelle Auflösung
	 */
	public void setActiveResolutionFromCombo(int index) {
		if (index == 0)
			res_Actual = RES_1024x768;
		else if (index == 1)
			res_Actual = RES_1280x1024;
		;
	}

	/**
	 * 
	 * @return liefert die Breite für die gewählte Auflösung
	 */
	public int getResWidth() {
		if (res_Actual == RES_1024x768)
			return 1024;

		return 1220;
	}

	/**
	 * 
	 * @return liefert die Höhe für die gewählte Auflösung
	 */
	public int getResHeight() {
		if (res_Actual == RES_1024x768)
			return 768;

		return 960;
	}

	/**
	 * 
	 * @return liefert die Grösse des Virtual Grid für die gewählte Auflösung
	 */
	public int getResPanel() {
		if (res_Actual == RES_1024x768)
			return 640;

		return 840;
	}

	/**
	 * 
	 * @return liefert die Grösse in Pixel für jedes Planquadrat
	 */
	public int getFeldElementGroesse() {
		return feldElementGroesse;
	}

	/**
	 * 
	 * @return liefert die Grösse in Pixel des Einstellungsfenster
	 */
	public Dimension getEinstellungenFrameDimension() {
		return einstellungenFrame;
	}

	/**
	 * 
	 * @return liefert die Busgeschwindigkeit
	 */
	public double getBusGeschwindigkeitInKmH() {
		return busGeschwindigkeitInKmH;
	}

	/**
	 * 
	 * @param busGeschwindigkeitInKmH
	 *            setzt die Busgeschwindigkeit
	 */
	public void setBusGeschwindigkeitInKmH(double busGeschwindigkeitInKmH) {
		this.busGeschwindigkeitInKmH = busGeschwindigkeitInKmH;
	}

	/**
	 * 
	 * @return liefert den Maßstab im Reellen für ein Planquadrat
	 */
	public double getFeldElementGroesseInM() {
		return feldElementGroesseInM;
	}

	/**
	 * 
	 * @param feldElementGroesseInM
	 *            setzt den Maßstab für ein Planquadrat im Reellen neu
	 */
	public void setFeldElementGroesseInM(double feldElementGroesseInM) {
		this.feldElementGroesseInM = feldElementGroesseInM;
	}

	/**
	 * 
	 * @return liefert die Gehgeschwindigkeit
	 */
	public double getGehGeschwindigkeitInKmH() {
		return gehGeschwindigkeitInKmH;
	}

	/**
	 * 
	 * @param gehGeschwindigkeitInKmH
	 *            setzt die Gehgeschwindigkeit
	 */
	public void setGehGeschwindigkeitInKmH(double gehGeschwindigkeitInKmH) {
		this.gehGeschwindigkeitInKmH = gehGeschwindigkeitInKmH;
	}

	public boolean isLogToConsole() {
		return logToConsole;
	}

	public void setLogToConsole(boolean logToConsole) {
		this.logToConsole = logToConsole;
		Logger.getInstance().setLogToConsole(logToConsole);
	}

	public boolean isLogToFile() {
		return logToFile;
	}

	public void setLogToFile(boolean logToFile) {
		this.logToFile = logToFile;
		Logger.getInstance().setLogToFile(logToFile);
	}

	public void setCurrentLogLevel(String loglevel) {
		int newloglevel = 99;

		if (loglevel == "Keine Ausgaben")
			newloglevel = 99;
		else if (loglevel == "Fatale Fehler")
			newloglevel = Logger.LEVEL_FATALERROR;
		else if (loglevel == "Fehler")
			newloglevel = Logger.LEVEL_ERROR;
		else if (loglevel == "Debug")
			newloglevel = Logger.LEVEL_DEBUG;

		Logger.getInstance().setCurrentLoglevel(newloglevel);
	}

	public String getCurrentLoglevel() {
		int loglevel = Logger.getInstance().getCurrentLoglevel();

		if (loglevel == 99)
			return "Keine Ausgaben";
		else if (loglevel == Logger.LEVEL_FATALERROR)
			return "Fatale Fehler";
		else if (loglevel == Logger.LEVEL_ERROR)
			return "Fehler";
		else if (loglevel == Logger.LEVEL_DEBUG)
			return "Debug";

		return "";
	}

	public void setSound(Object selectedItem) {
		Logger.getInstance().log("Sound: " + selectedItem.toString());
		if (selectedItem.toString().equals("Nein")) {
			this.soundCheck = false;
		} else {
			this.soundCheck = true;
		}
	}

	public boolean checkSound() {
		return this.soundCheck;
	}

}
