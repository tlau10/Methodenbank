package de.fh_konstanz.simubus.model;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Die Klasse <code>Teilstrecke</code> ist Bestandteil der Buslinie und hält
 * Informationen zur Teilstrecke.
 * 
 * @author Michael Franz
 * 
 * @version 1.0 (05.06.2006)
 * 
 */
public class Teilstrecke implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2593910533108929872L;

	/**
	 * ID der Teilstrecke
	 */
	private int id;

	/**
	 * Startpunkt der Teilstrecke
	 */
	private Point2D start;

	/**
	 * Endpunkt der Teilstrecke
	 */
	private Point2D ende;

	/**
	 * Geschwindigkeit mit der auf der Teilstrecke gefahren werden kann.
	 */
	private int geschwindigkeit;

	/**
	 * Fahrbahnbreite der Teilstrecke
	 */
	private int breite;

	/**
	 * Name der Teilstrecke
	 */
	private String name;

	/**
	 * Länge der Teilstrecke
	 */
	private double laenge;

	/**
	 * Konstruktor, jede Teilstrecke benötigt eine ID, einen Namen, einen Start-
	 * und Endpunkt, sowie die Geschwindigkeit, die Breite und die Länge der
	 * Teilstrecke.
	 * 
	 * @param id
	 *            Id der Teilstrecke
	 * @param name
	 *            Name der Teilstrecke
	 * @param start
	 *            Startpunkt der Teilstrecke
	 * @param ende
	 *            Endpunkt der Teilstrecke
	 * @param geschwindigkeit
	 *            Geschwindigkeit mit der Teilstrecke gefahren werden kann
	 * @param breite
	 *            Breite der Teilstrecke
	 */
	public Teilstrecke(int id, String name, Point2D start, Point2D ende, int geschwindigkeit, int breite) {
		this.id = id;
		this.name = name;
		this.start = start;
		this.ende = ende;
		this.geschwindigkeit = geschwindigkeit;
		this.breite = breite;
		this.laenge = Math.sqrt(quadrat(ende.getY() - start.getY()) + quadrat(ende.getX() - start.getX()));
	}

	/**
	 * setzt den Namen der Teilstrecke
	 * 
	 * @param name
	 *            Name der Teilstrecke
	 */
	public Teilstrecke(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + " - " + start.getX() + ";" + start.getY() + "/" + ende.getX() + ";" + ende.getY() + " " + laenge;
	}

	/**
	 * liefert das Quadrat
	 * 
	 * @param a
	 *            Zahl die quadriert werden soll.
	 * 
	 * @return Quadrat des Übergabeparameters
	 */
	public double quadrat(double a) {
		return a * a;
	}

	/**
	 * @return liefert die Breite der Teilstrecke
	 */
	public int getBreite() {
		return breite;
	}

	/**
	 * @param breite
	 *            setzt die Breite der Strasse
	 */
	public void setBreite(int breite) {
		this.breite = breite;
	}

	/**
	 * liefert die Geschwindigkeit, mit der auf der Teilstrecke gefahren werden
	 * kann.
	 * 
	 * @return liefert die Geschwindigkeit
	 */
	public int getGeschwindigkeit() {
		return geschwindigkeit;
	}

	/**
	 * @param geschwindigkeit
	 *            setzt die Geschwindigkeit mit der auf der Teilstrecke gefahren
	 *            werden kann.
	 */
	public void setGeschwindigkeit(int geschwindigkeit) {
		this.geschwindigkeit = geschwindigkeit;
	}

	/**
	 * @return liefert die ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            setzt die Id der Teilstrecke
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return liefert den Startpunkt der Teilstrecke
	 */
	public Point2D getStart() {
		return start;
	}

	/**
	 * @return liefert den Endpunkt der Teilstrecke
	 */
	public Point2D getEnde() {
		return ende;
	}

	/**
	 * @param ende
	 *            setzt den Endpunkt der Teilstrecke
	 */
	public void setEnde(Point2D ende) {
		this.ende = ende;
	}

	/**
	 * @param start
	 *            setzt den Startpunkt der Teilstrecke
	 */
	public void setStart(Point2D start) {
		this.start = start;
	}

	/**
	 * @return liefert die Länge der Teilstrecke
	 */
	public double getLaenge() {
		return this.laenge;
	}
}