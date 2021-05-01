/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.fh_konstanz.simubus.model;

import java.awt.Dimension;
import java.io.Serializable;


/**
 * @author Robert Audersetz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SimuKonfiguration implements Serializable{

	private static SimuKonfiguration instance;

	private final int RES_1024x768 = 0;
	private final int RES_1280x1024 = 75;
	private int res_Actual;
	
	private int feldElementGroesse;
	private String mapLocation;
	
	private final Dimension einstellungenFrame = new Dimension(800, 600);

	private int simulationsgeschwindigkeit = 10;

	private double feldElementGroesseInM = 10;
	private double busGeschwindigkeitInKmH = 40;
	private double gehGeschwindigkeitInKmH = 6;
	
	private double startzeit = 0; // TODO einstellbar
	private double endezeit = 100; // TODO
	
	private SimuKonfiguration() {
		res_Actual = RES_1280x1024;
		feldElementGroesse = 12;
		mapLocation = "Planueb.jpg";
	}
	
	public static SimuKonfiguration getInstance() {
		
		if (instance == null)
			instance = new SimuKonfiguration();
		
		return instance;
	}

	/**
	 * Setzt die instance-Variable neu
	 */
	public static void setInstance(SimuKonfiguration instance) {
		SimuKonfiguration.instance = instance;
	}
	
	public int getActiveResolutionForCombo() {
		if (res_Actual == RES_1024x768)
			return 0;

		return 1;		
	}

	public void setActiveResolutionFromCombo(int index) {
		if (index == 0)
			res_Actual = RES_1024x768;
		else if (index == 1)
			res_Actual = RES_1280x1024;;		
	}

	public int getResWidth() {
		if (res_Actual == RES_1024x768)
			return 1020;

		return 1220;
	}
	
	public int getResHeight() {
		if (res_Actual == RES_1024x768)
			return 700;

		return 900;
	}

	public int getResBoxAddition() {
		return res_Actual;
	}

	public int getResPanel() {
		if (res_Actual == RES_1024x768)
			return 640;

		return 840;
	}

	public int getFeldElementGroesse() {
		return feldElementGroesse;
	}

	public void setFeldElementGroesse(int feldElementGroesse) {
		this.feldElementGroesse = feldElementGroesse;
	}

	public Dimension getEinstellungenFrameDimension() {
		return einstellungenFrame;
	}

	/**
	 * Gibt die Geschwindigkeit zurueck, mit der die Simulation auf dem
	 * Bildschirm dargestellt wird.
	 * @return simulationsgeschwindigkeit 1 bedeutet Echtzeit, 10 bedeutet 10
	 * 		mal so schnell wie Echtzeit, usw.
	 */
	public int getSimulationsgeschwindigkeit() {
		return simulationsgeschwindigkeit;
	}

	/**
	 * Legt die Geschwindigkeit fest, mit der die Simulation auf dem
	 * Bildschirm dargestellt wird.
	 * @param simulationsgeschwindigkeit 1 bedeutet Echtzeit, 10 bedeutet 10 mal
	 * 		so schnell wie Echtzeit, usw.
	 */
	public void setSimulationsgeschwindigkeit(int simulationsgeschwindigkeit) {
		this.simulationsgeschwindigkeit = simulationsgeschwindigkeit;
	}

	public String getMapLocation() {
		return mapLocation;
	}

	public void setMapLocation(String mapLocation) {
		this.mapLocation = mapLocation;
	}

	public double getBusGeschwindigkeitInKmH() {
		return busGeschwindigkeitInKmH;
	}

	public void setBusGeschwindigkeitInKmH(double busGeschwindigkeitInKmH) {
		this.busGeschwindigkeitInKmH = busGeschwindigkeitInKmH;
	}

	public double getFeldElementGroesseInM() {
		return feldElementGroesseInM;
	}

	public void setFeldElementGroesseInM(double feldElementGroesseInM) {
		this.feldElementGroesseInM = feldElementGroesseInM;
	}

	public double getGehGeschwindigkeitInKmH() {
		return gehGeschwindigkeitInKmH;
	}

	public double getGehGeschwindigkeitInMSec() {
		return (gehGeschwindigkeitInKmH/3.6)+60;
	}

	public void setGehGeschwindigkeitInKmH(double gehGeschwindigkeitInKmH) {
		this.gehGeschwindigkeitInKmH = gehGeschwindigkeitInKmH;
	}

	public double getStartzeit() {
		return startzeit;
	}

	public void setStartzeit(double linienstartzeit) {
		this.startzeit = linienstartzeit;
	}

	public double getEndezeit() {
		return endezeit;
	}

	public void setEndezeit(double linienendezeit) {
		this.endezeit = linienendezeit;
	}

}
