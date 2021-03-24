package trOptimizer;

/**
 * <p>Title: Ergebnis</p>
 * <p>Description: Diese Klasse stellt das Ergebnis einer Berechnung dar.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */

public class Ergebnis {


  //Klassenattribute
  private String transporter;
  private String ladung;
  private int quelle;
  private int ziel;
  private double kosten;

  /**
   * Standard-Konstruktor
   */
  public Ergebnis() {}


  /**
   * Diese Methode liefert den Namen des Transporters zurueck.
   * @return Name des Transporters
   */
  public String getTransporter() {
    return transporter;
  }

  /**
   * Diese Methode setzt den Namen des Ergebnis-Transporters.
   * @param transporter - Name
   */
  public void setTransporter(String transporter) {
    this.transporter = transporter;
  }

  /**
   * Diese Methode liefert den Ladung zurueck.
   *
   * @return Ladung als String
   */
  public String getLadung() {
    return ladung;
  }

  /**
   * Diese Methode setzt die Ladung.
   * @param ladung
   */
  public void setLadung(String ladung) {
    this.ladung = ladung;
  }

  /**
   * Diese Methode liefert die Id der Quelle zurueck.
   * @return Id der Quelle
   */
  public int getQuelle() {
    return quelle;
  }

  /**
   * Diese Methode setzt die Id der Quelle.
   *
   * @param quelle
   */
  public void setQuelle(int quelle) {
    this.quelle = quelle;
  }

  /**
   * Diese Methode liefert die Id des Ziels zurueck.
   *
   * @return Id des Ziels
   */
  public int getZiel() {
    return ziel;
  }

  /**
   * Diese Methode setzt die Id des Ziels.
   *
   * @param ziel Id des Ziels
   */
  public void setZiel(int ziel) {
    this.ziel = ziel;
  }

  /**
   * Diese Methode liefert die Kosten zurueck.
   *
   * @return Kosten der Lieferung.
   */
  public double getKosten() {
    return kosten;
  }

  /**
   * Diese Methode setzt die Kosten der Lieferung.
   *
   * @param kosten der Lieferung
   */
  public void setKosten(double kosten) {
    this.kosten = kosten;
  }

}