package trOptimizer;

/**
 * <p>Title: Strecke</p>
 * <p>Description: Diese Klasse stellt eine Strecke dar. Also die Verbindung zwischen Produzenten und Empf�ngern.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.io.Serializable;

public class Strecke implements Serializable {

  //Klassenattribute
  private int streckenId;
  private int quelle; // id vom produzenten
  private int ziel; // id vom empf�nger
  private int entfernung;
  private int x1Koordinate, y1Koordinate, x2Koordinate, y2Koordinate;

  /**
   * Diese Konstruktor erwartet die Id des Objekts, die vom Controller �bergeben wird.
   * @param id des Objekts
   */
  public Strecke( int id ) {
    streckenId = id;
  }

  /**
   * Diese Konstruktor erwartet sowohl die Id als auch die Koordinate des Objekts, die vom Controller �bergeben werden.
   *
   * @param x1 - Koordinate
   * @param y1 - Koordinate
   * @param x2 - Koordinate
   * @param y2 - Koordinate
   * @param id des Objekts
   */
  public Strecke( int x1, int y1, int x2, int y2, int id ) {

    x1Koordinate = x1;
    y1Koordinate = y1;
    x2Koordinate = x2;
    y2Koordinate = y2;
    streckenId = id;

  }

  /***********************************
   * setter - Methoden
   ************************************/

  /**
   * Diese Methode setzt den Ursprung einer Strecke.
   *
   * @param idProduzent id des zugeh�rigen produzenten
   */
  public void setQuelle( int idProduzent ) {
    quelle = idProduzent;
  }

  /**
   * Diese Methode setzt das Ziel einer Strecke.
   *
   * @param idEmpfaenger id des zugeh�rigen empfaengers
   */
  public void setZiel( int idEmpfaenger ) {
    ziel = idEmpfaenger;
  }

  /**
   * Diese Methode setzt die Entfernung zwischen zwei Objekten.
   *
   * @param _entfernung zwischen den verbundenen objekten
   */
  public void setEntfernung( int _entfernung ) {
    entfernung = _entfernung;
  }

  /**
   * Diese Methode setzt die Id des Objekts.
   *
   * @param _id der strecke
   */
  public void setId( int _id ) {
    streckenId = _id;
  }

  /**
   * Diese Methode setzt die erste X-Koordinate des Objekts.
   *
   * @param x1 x1Koordinate der strecke auf dem zeichenFeld
   */
  public void setX1Koordinate( int x1 ) {
    x1Koordinate = x1;
  }

  /**
   * Diese Methode setzt die zweite X-Koordinate des Objekts.
   *
   * @param x2 x2Koordinate der strecke auf dem zeichenFeld
   */
  public void setX2Koordinate( int x2 ) {
    x2Koordinate = x2;
  }

  /**
   * Diese Methode setzt die erste Y-Koordinate des Objekts.
   *
   * @param y1 y1Koordinate der strecke auf dem zeichenFeld
   */
  public void setY1Koordinate( int y1 ) {
    y1Koordinate = y1;
  }

  /**
   * Diese Methode setzt die zweite Y-Koordinate des Objekts.
   *
   * @param y2 y2Koordinate der strecke auf dem zeichenFeld
   */
  public void setY2Koordinate( int y2 ) {
    y2Koordinate = y2;
  }

  /***********************************
   * getter - Methoden
   ***********************************/

  /**
   * Diese Methode liefert die Quelle des Objekts zur�ck.
   *
   * @return quelle der strecke
   */
  public int getQuelle() {
    return quelle;
  }

  /**
   * Diese Methode liefert das Ziel des Objekts zur�ck.
   *
   * @return ziel der strecke
   */
  public int getZiel() {
    return ziel;
  }

  /**
   * Diese Methode liefert die Entfernung zweier verbundener Objekte zur�ck.
   *
   * @return entfernung zwischen den zwei verbundenen objekten
   */
  public int getEntfernung() {
    return entfernung;
  }

  /**
   * Diese Methode liefert die Id des Objekt zur�ck.
   *
   * @return id der strecke
   */
  public int getId() {
    return streckenId;
  }

  /**
   * Diese Methode liefert die erste X-Koordinate des Objekts zur�ck.
   *
   * @return x1Koordinate der strecke auf dem zeichenFeld
   */
  public int getX1Koordinate() {
    return x1Koordinate;
  }

  /**
   * Diese Methode liefert die zweite X-Koordinate des Objekts zur�ck.
   *
   * @return x2Koordinate der strecke auf dem zeichenFeld
   */
  public int getX2Koordinate() {
    return x2Koordinate;
  }

  /**
   * Diese Methode liefert die erste Y-Koordinate des Objekts zur�ck.
   *
   * @return y1Koordinate der strecke auf dem zeichenFeld
   */
  public int getY1Koordinate() {
    return y1Koordinate;
  }

  /**
   * Diese Methode liefert die zweite Y-Koordinate des Objekts zur�ck.
   *
   * @return y2Koordinate der strecke auf dem zeichenFeld
   */
  public int getY2Koordinate() {
    return y2Koordinate;
  }
}