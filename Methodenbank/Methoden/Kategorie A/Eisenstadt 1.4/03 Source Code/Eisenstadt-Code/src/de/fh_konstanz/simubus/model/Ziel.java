package de.fh_konstanz.simubus.model;

import java.awt.Point;
import java.io.Serializable;

/**
 * Die Klasse <code>Ziel</code> stellt ein Ziel mit deren Informationen auf der
 * Oberfläche dar.
 * 
 * @author Ingo Kroh, Michael Franz, Daniel Weber
 * 
 * @version 1.0 (05.06.2006)
 * 
 */
public class Ziel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1826931499974537423L;

	/**
	 * Point Koordinaten des Ziels
	 */
	private Point point;

	/**
	 * Name des Ziels
	 */
	private String zielName = null;

	/** ID des Ziel */
	private int id = -1;

	/**
	 * Konstruktor, legt ein Ziel mit Koordinaten an
	 * 
	 * @param point
	 */
	public Ziel(Point point, int id) {
		this.point = point;
		this.id = id;
	}

	/**
	 * liefert die Koordinaten des Ziels
	 * 
	 * @return Ziel-Koordinaten
	 */
	public Point getZiel() {
		return point;
	}

	/**
	 * liefert die X-Koordinate des Ziels
	 * 
	 * @return X-Koordinate
	 */
	public int getX() {
		return point.x;
	}

	/**
	 * liefert die Y-Koordinate des Ziels
	 * 
	 * @return Y-Koordinate
	 */
	public int getY() {
		return point.y;
	}

	@Override
	public String toString() {
		if (zielName == null)
			return "Ziel [" + point.x + ", " + point.y + "]";

		return zielName;
	}

	public void setName(String name) {
		this.zielName = name;
	}

	@Override
	public int hashCode() {
		return point.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (obj instanceof Ziel) {
			if (((Ziel) obj).point.equals(point) == true) {
				result = true;
			}
		}
		return result;
	}

	public int getId() {
		return id;
	}
}
