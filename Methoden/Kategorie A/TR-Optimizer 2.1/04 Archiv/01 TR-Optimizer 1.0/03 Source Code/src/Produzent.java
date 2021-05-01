/**
 * <p>Title: Produzent</p>
 * <p>Description: Diese Klasse stellt einen Produzenten dar.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;

public class Produzent extends java.awt.geom.Ellipse2D.Double implements Serializable {

  //Klassenattribute
  private double breite = 20;
  private double hoehe = 20;
  private String name;
  private int lieferMenge;
  private int id;

  private double x, y;

  /**
   * Konstruktor
   *
   * @param xKoordinate des produzenten auf dem zeichenFeld
   * @param yKoordinate des produzenten auf dem zeichenFeld
   * @param id des produzenten
   */
  public Produzent( double xKoordinate, double yKoordinate, int id ) {
    x = xKoordinate;
    y = yKoordinate;
    this.setFrame( x, y, breite, hoehe );
    this.setId( id );
  }

  /***********************************
   * setter - Methoden
   ***********************************/

  /**
   * Diese Methode setzt den Namen des Objekts.
   *
   * @param _name des produzenten
   */
  public void setName( String _name ) {
    name = _name;
  }

  /**
   * Diese Methode setzt die Liefermenge des Objekts.
   *
   * @param lieferM lieferMenge des produzenten
   */
  public void setLieferMenge( int lieferM ) {
    lieferMenge = lieferM;
  }

  /**
   * Diese Methode setzt die Id des Objekts.
   *
   * @param _id des produzenten
   */
  public void setId( int _id ) {
    id = _id;
  }

  /**
   * Diese Methode setzt die X-Koordinate des Objekts.
   *
   * @param xKoordinate des produzenten auf dem zeichenFeld
   */
  public void setX( double xKoordinate ) {
    x = xKoordinate;
  }

  /**
   * Diese Methode setzt die Y-Koordinate des Objekts.
   *
   * @param yKoordinate des produzenten auf dem zeichenFeld
   */
  public void setY( double yKoordinate ) {
    y = yKoordinate;
  }

  /***********************************
   * getter - Methoden
   ***********************************/

  /**
   *  Diese Methode liefert den Namen des Objekts zurück.
   *
   * @return name des produzenten
   */
  public String getName() {
    return name;
  }

  /**
   * Diese Methode liefert die Liefermenge des Objekts zurück.
   *
   * @return lieferMenge des produzenten
   */
  public int getLieferMenge() {
    return lieferMenge;
  }

  /**
   * Diese Methode liefert die Id des Objekts zurück.
   *
   * @return id des produzenten
   */
  public int getId() {
    return id;
  }

  /**
   * Diese Methode liefert die Breite des Objekts zurück.
   *
   * @return breite des produzenten auf dem zeichenFeld
   */
  public double getBreite() {
    return breite;
  }

  /**
   * Diese Methode liefert die Höhe des Objekts zurück.
   *
   * @return hoehe des produzenten auf dem zeichenFeld
   */
  public double getHoehe() {
    return hoehe;
  }

  /**
   * Diese Methode liefert die X-Koordinate des Objekts zurück.
   *
   * @return xKoordinate des produzenten auf dem zeichenFeld
   */
  public double getX() {
    return x;
  }

  /**
   * Diese Methode liefert die Y-Koordinate des Objekts zurück.
   *
   * @return yKoordinate des produzenten auf dem zeichenFeld
   */
  public double getY() {
    return y;
  }

}