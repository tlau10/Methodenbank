package de.fh_konstanz.simubus.model;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * Die Klasse <code>Planquadrat</code> beinhaltet Informationen, welche
 * Eigenschaft dieses Planquadrat hat, die Grösse, welche ID sie besitzt. Sie
 * ist essentieller Anteil der Optimierung.
 * 
 * @author Michael Franz
 * @version 1.0 25.06.2006
 */

public class Planquadrat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7810261235256070274L;

	/**
	 * Die Werte f,g,h und father dienen zur Berechnung des kürzesten Weges durch
	 * den PathfinderOR
	 */

	/**
	 * f sind die gesamten Kosten des Weges, die sich aus g+h zusammensetzen
	 */
	private int f;

	/**
	 * g sind die bisherigen Kosten vom Start bis zu einem bestimmten Punkt
	 */
	private int g;

	/**
	 * h sind die geschätzten Kosten von einem bestimmten Punkt bis zum Ziel
	 */
	private int h;

	/**
	 * gibt an, welches Planquadrat der Vorgänger war, um den Weg schlussendlich
	 * zu ermitteln
	 */
	public Planquadrat father;

	/**
	 * X-Koordinate des Planquadrats im Virtual Grid
	 */
	private int x;

	/**
	 * Y-Koordinate des Planquadrats im Virtual Grid
	 */
	private int y;

	/**
	 * Anzahl der Pixel, wie gross ein Planquadrat ist
	 */
	private int sizeOfField = SimuKonfiguration.getInstance()
			.getFeldElementGroesse();

	/**
	 * boolean, ob Planquadrat eine Strasse ist
	 */
	public boolean isStreet = false;

	/**
	 * boolean, ob Planquadrat ein Ziel ist
	 */
	public boolean isZiel = false;

	/**
	 * boolean, ob Planquadrat gesperrt ist
	 */
	public boolean isGesperrt = false;

	/**
	 * boolean, ob Planquadrat eine Haltestelle ist
	 */
	public boolean isHaltestelle = false;

	/**
	 * boolean, ob Planquadrat eine gesperrte Haltestelle ist
	 */
	public boolean isLockedHaltestelle = false;

	/**
	 * Wert zur Bestimmung, ob der vorherige Zustand eine Haltestelle war
	 */
	public final static int IS_HALTESTELLE = 2;

	/**
	 * Wert zur Bestimmung, ob der vorherige Zustand eine Strasse war
	 */
	public final static int IS_STREET = 1;

	/**
	 * Wert zur Bestimmung, ob der vorherige Zustand eine leer war
	 */
	public final static int IS_EMPTY = 0;

	/**
	 * vorheriger Zustand eines Planquadrates
	 */
	private int before = IS_EMPTY;

	/**
	 * ID des Planquadrates
	 */
	private int id;
	
	/**
	 * x end point of a street
	 */
	private int xEnd;
	
	/**
	 * y end point of a street
	 */
	private int yEnd;
	/**
	 * Konstruktor, Planquadrat wird mit X- und Y-Koordinaten im VirtualGrid
	 * angelegt
	 * 
	 * @param x
	 *           X-Koordinate im Virtual Grid
	 * @param y
	 *           Y-Koordinate im Virtual Grid
	 */
	
	public Planquadrat(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * liefert die ID des Planquadrates zurück
	 * 
	 * @return ID des Planquadrates
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * setzt die ID des Planquadrates
	 * 
	 * @param s
	 *           s ist die neue ID
	 */
	public void setID(int s) {
		this.id = s;
	}

	/**
	 * setzt die Werte f,g,h eines Planquadrates, die zur Berechnung des
	 * kürzesten Weges dienen. Die Werte werden vom PathfinderOR verwendet.
	 * 
	 * @param f
	 *           f-Wert
	 * @param g
	 *           g-Wert
	 * @param h
	 *           h-Wert
	 */
	public void setValues(int f, int g, int h) {
		this.f = f;
		this.g = g;
		this.h = h;
	}

	/**
	 * liefert den F-Wert des Planquadrates zurück
	 * 
	 * @return F-Wert, Gesamtkosten
	 */
	public int getF() {
		return f;
	}

	/**
	 * liefert den G-Wert des Planquadrates zurück
	 * 
	 * @return G-Wert, bisherige Kosten
	 */
	public int getG() {
		return g;
	}

	/**
	 * liefert den H-Wert des Planquadrates zurück
	 * 
	 * @return H-Wert, geschätzte Kosten
	 */
	public int getH() {
		return h;
	}

	/**
	 * setzt das Planquadrat als Strasse
	 * 
	 */
	public void setStreet() {
		if (isHaltestelle == true) {
			before = IS_HALTESTELLE;
		} else {
			before = IS_EMPTY;
		}

		isStreet = true;
		isZiel = false;
		isGesperrt = false;

	}

	/**
	 * setzt das Planquadrat leer
	 * 
	 */
	public void setEmpty() {
		if (before == IS_STREET) {
			isStreet = true;
			isHaltestelle = false;
		} else if (before == IS_HALTESTELLE) {
			isHaltestelle = true;
			isStreet = false;
		} else {
			isStreet = false;
			isHaltestelle = false;
		}

		isZiel = false;
		isGesperrt = false;
		isLockedHaltestelle = false;

	}

	/**
	 * setzt das Planquadrat als gesperrtes Feld
	 * 
	 */
	public void setGesperrt() {
		isStreet = false;
		isZiel = false;
		isGesperrt = true;
		isHaltestelle = false;
		isLockedHaltestelle = false;
	}

	/**
	 * setzt das Planquadrat als Ziel
	 * 
	 */
	public void setZiel() {
		isStreet = false;
		isZiel = true;
		isGesperrt = false;
		isHaltestelle = false;
		isLockedHaltestelle = false;

	}

	/**
	 * setzt das Planquadrat als gesperrte Haltestelle
	 * 
	 */
	public void isLockedHaltestelle() {
		isZiel = false;
		isGesperrt = false;
		isHaltestelle = false;
		isStreet = false;
		isLockedHaltestelle = true;
	}

	/**
	 * setzt das Planquadrat als Haltestelle
	 * 
	 */
	public void setHaltestelle() {
		if (isStreet == false) {
			isStreet = true;
			before = IS_EMPTY;
			Strassennetz.getInstance().addStrasse(this);
		} else {
			before = IS_STREET;
		}

		isZiel = false;
		isGesperrt = false;
		isHaltestelle = true;
		isLockedHaltestelle = false;

	}

	/**
	 * erzeugt ein Quadrat mit Pixel-Koordinaten und deren Grösse, welches zur
	 * Bestimmung der Eigenschaften eines Planquadrates benötigt wird.
	 * 
	 * @return liefert das erzeugte Planquadrat
	 */
	public Rectangle2D.Double getBounds() {
		Rectangle2D.Double r = new Rectangle2D.Double();
		r.x = getPixelX();
		r.y = getPixelY();
		r.height = sizeOfField;
		r.width = sizeOfField;
		return r;
	}

	/**
	 * liefert die X-Koordinate des Planquadrates zurück, an welcher Position
	 * sich das Planquadrat im VirtualGrid befindet
	 * 
	 * @return liefert die X-Koordinate des Planquadrates
	 */
	public int getPlanquadratX() {
		return x;
	}

	/**
	 * liefert die Y-Koordinate des Planquadrates zurück, an welcher Position
	 * sich das Planquadrat im VirtualGrid befindet
	 * 
	 * @return liefert die Y-Koordinate des Planquadrates
	 */
	public int getPlanquadratY() {
		return y;
	}

	/**
	 * liefert die Pixel-X-Koordinate des Planquadrates zurück, an welcher
	 * Position sich das Planquadrat im VirtualGrid befindet
	 * 
	 * @return liefert die vom Planquadrat umgerechnete Pixel-X-Koordinate
	 */
	public int getPixelX() {
		return sizeOfField * x;
	}

	/**
	 * liefert die Pixel-Y-Koordinate des Planquadrates zurück, an welcher
	 * Position sich das Planquadrat im VirtualGrid befindet
	 * 
	 * @return liefert die vom Planquadrat umgerechnete Pixel-Y-Koordinate
	 */
	public int getPixelY() {
		return sizeOfField * y;
	}

	/**
	 * liefert die Planquadrat Koordinaten als Point
	 * 
	 * @return Planquadrat Koordinaten
	 */
	public Point getPlanquadratCoordinates() {
		Point p = new Point(x, y);
		return p;
	}

	/**
	 * liefert die Pixel Koordianten als Point, die zuvor von den Planquadrat
	 * Koordinaten umgerechnet wurden.
	 * 
	 * @return Pixel Koordinaten
	 */
	public Point getPixelCoordinates() {
		Point p = new Point(getPixelX(), getPixelY());
		return p;
	}

	@Override
	public String toString() {
		return "Planquadrat [" + x + ", " + y + "]";
	}

	@Override
	public int hashCode() {
		return getPlanquadratCoordinates().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (obj instanceof Planquadrat) {
			if (((Planquadrat) obj).getPlanquadratCoordinates().equals(
					getPlanquadratCoordinates()) == true) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * liefert den Zustand des Planquadrates, vor der neu gesetzten Eigenschaft
	 * 
	 * @return Zustand des Planquadrates, vor der neu gesetzten Eigenschaft
	 */
	public int getBefore() {
		return before;
	}

	/**
	 * liefert den Vorgänger
	 * 
	 * @return
	 */
	public Planquadrat getFather() {
		return father;
	}

	//Utilities
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setSizeOfField(int size) {
		this.sizeOfField = size;
	}

	public int getSizeOfField() {
		return this.sizeOfField;
	}

	public int getXEnd() {
		return xEnd;
	}

	public void setXEnd(int end) {
		xEnd = end;
	}

	public int getYEnd() {
		return yEnd;
	}

	public void setYEnd(int end) {
		yEnd = end;
	}


}
