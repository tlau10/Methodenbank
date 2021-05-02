package trOptimizer;

/**
 * <p>Title: Empfaenger</p>
 * <p>Description: Die Klasse Empfaenger stellt einen Empfaenger von z.B. Gütern dar. Sie bilden das Gegenstück zu Produzenten.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.io.Serializable;

public class Empfaenger extends java.awt.geom.Rectangle2D.Double implements Serializable {

  //Klassenattribute
  private int breite = 22;
  private int hoehe = 22;
  private int x, y;
  private int id;
  private String name;
  private int benoetigteMenge;

  /**
   * Der Konstruktor setzt die Koordinaten und die eigene Id des jeweiligen Objekts.
   *
   * @param xKoordinate des empfaengers auf dem zeichenFeld
   * @param yKoordinate des empfaengers auf dem zeichenFeld
   * @param id des empfaengers
   */
  public Empfaenger( int xKoordinate, int yKoordinate, int id ) {
    x = xKoordinate;
    y = yKoordinate;
    this.setId( id );

  }

  /***********************************
   * setter - Methoden
   ***********************************/

  /**
   * Diese Methode setzt die X-Koordinate des Objekts.
   *
   * @param xKoordinate des empfaengers auf dem zeichenFeld
   */
  public void setX( int xKoordinate ) {
    x = xKoordinate;
  }

  /**
   * Diese Methode setzt die Y-Koordinate des Objekts.
   *
   * @param yKoordinate des empfaengers auf dem zeichenFeld
   */
  public void setY( int yKoordinate ) {
    y = yKoordinate;
  }

  /**
   * Diese Methode setzt den Namen des Objekts.
   *
   * @param _name des empfaengers
   */
  public void setName( String _name ) {
    name = _name;
  }

  /**
   * Diese Methode setzt die benötigte Menge des Objekts.
   *
   * @param menge benoetigte menge des empfaengers
   */
  public void setBenoetigteMenge( int menge ) {
    benoetigteMenge = menge;
  }

  /**
   * Diese Methode setzt die Id des Objekts.
   *
   * @param _id des empfaengers
   */
  public void setId( int _id ) {
    id = _id;
  }

  /***********************************
   * getter - Methoden
   ***********************************/

  /**
   * Diese Methode liefert den Namen des Objekts zurück.
   *
   * @return name des empfaengers
   */
  public String getName() {
    return name;
  }

  /**
   * Diese Methode liefert die benötigte Menge des Objekts zurück.
   *
   * @return benoetigte Menge des empfaengers
   */
  public int getBenoetigteMenge() {
    return benoetigteMenge;
  }

  /**
   * Diese Methode liefert die Id des Objekts zurück.
   *
   * @return id des empfaengers
   */
  public int getId() {
    return id;
  }

  /**
   * Diese Methode liefert die Breite des Objekts zurück.
   *
   * @return breite des empfaenger auf dem zeichenfeld
   */
  public int getBreite() {
    return breite;
  }

  /**
   * Diese Methode liefert die Höhe des Objekts zurück.
   *
   * @return hoehe des empfaenger auf dem zeichenfeld
   */
  public int getHoehe() {
    return hoehe;
  }

  /**
   * Diese Methode liefert die X-Koordinate des Objekts zurück.
   *
   * @return xKoordinate des empfaenger auf dem zeichenfeld
   */
  public double getX() {
    return x;
  }

  /**
   * Diese Methode liefert die Y-Koordinate des Objekts zurück.
   *
   * @return yKoordinate des empfaenger auf dem zeichenfeld
   */
  public double getY() {
    return y;
  }

}