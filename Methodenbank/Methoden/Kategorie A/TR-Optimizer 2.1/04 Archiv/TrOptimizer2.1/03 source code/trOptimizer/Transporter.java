package trOptimizer;


/**
 * <p>Title: Transporter</p>
 * <p>Description: Die Klasse Transporter stellt einen Transporter der zwischen Produzenten und Empf�nger liefert dar.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.io.Serializable;

public class Transporter extends java.awt.geom.Rectangle2D.Double implements Serializable {

  //Klassenattribute
  private double breite = 25;
  private double hoehe = 22;
  private int kapazitaet;
  private String name;
  private double kosten_pro_km;
  private int id;

  private double x, y;

  /**
   * Der Konstruktor setzt die xKoordinate, die yKoordinate und die id des Transporters
   *
   * @param xKoordinate
   * @param yKoordinate
   * @param id
   */
  public Transporter( double xKoordinate, double yKoordinate, int id ) {
    x = xKoordinate;
    y = yKoordinate;
    this.setId( id );
    this.setRect( x, y, breite, hoehe );

  }

  /*************************************************
   *  setter - Methoden
   *************************************************/
  /**
   * Diese Methode setzt die X-Koordinate des Transporters
   *
   * @param xKoordinate des transporters auf dem zeichenFeld
   */
  public void setX( double xKoordinate ) {
    x = xKoordinate;
  }

  /**
   * Diese Methode setzt die Y-Koordinate des Transporters
   *
   * @param yKoordinate des transporters auf dem zeichenFeld
   */
  public void setY( double yKoordinate ) {
    y = yKoordinate;
  }

  /**
   *
   * Diese Methode zeichnet den Transporter als Rechteck mit den uebergebenen Koordinaten bzw. setzt die Koordinaten
   *
   * @param xKoordinate
   * @param yKoordinate
   */
  public void setRect( double xKoordinate, double yKoordinate ) {
    super.setRect( xKoordinate, yKoordinate, breite, hoehe );
  }

  /**
   * Diese Methode setzt die Kapazitaet des Transporters
   *
   * @param kap kapazitaet des transporters
   */
  public void setKapazitaet( int kap ) {
    kapazitaet = kap;
  }

  /**
   * Diese Methode setzt den Namen des Transporters
   *
   * @param _name des transporters
   */
  public void setName( String _name ) {
    name = _name;
  }

  /**
   * Diese Methode setzt die Kosten pro km des Transporters
   *
   * @param kosten pro km des transporters
   */
  public void setKosten_pro_km( double kosten ) {
    kosten_pro_km = kosten;
  }

  /**
   * Diese Methode setzt die Id des Transporters
   * @param _id des transporters
   */
  public void setId( int _id ) {
    id = _id;
  }

  /*****************************************************
   * getter - Methoden
   ****************************************************/

  /**
   * Diese Methode liefert die Kapazitaet des Transporters zurueck.
   *
   * @return kapazitaet des transporters
   */
  public int getKapazitaet() {
    return kapazitaet;
  }

  /**
   *  Diese Methode liefert den Namen des Transporters zurueck.
   *
   * @return name des transporters
   */
  public String getName() {
    return name;
  }

  /**
   * Diese Methode liefert die Kosten pro km zurueck.
   * @return kosten_pro_km des transporters
   */
  public double getKosten_pro_km() {
    return kosten_pro_km;
  }

  /**
   * Diese Methode liefert die Id des Transporters zurueck.
   * @return id des transporters
   */
  public int getId() {
    return id;
  }

  /**
   * Diese Methode liefert die Breite des Objekts zurueck.
   * @return breite des transporters auf dem zeichenFeld
   */
  public double getBreite() {
    return breite;
  }

  /**
   * Diese Methode liefert die Hoehe des Objekt zurueck.
   * @return hoehe des transporters auf dem zeichenFeld
   */
  public double getHoehe() {
    return hoehe;
  }

  /**
   * Diese Methode liefert die X-Koordinate des Objekts zurueck.
   * @return xKoordinate des transporters auf dem zeichenFeld
   */
  public double getX() {
    return x;
  }

  /**
   * Diese Methode liefert die Y-Koordinate des Ojekts zurueck.
   * @return yKoordinate des transporters auf dem zeichenFeld
   */
  public double getY() {
    return y;
  }
}