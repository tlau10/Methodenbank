package absf1;

/**
 * <p>Title: Lkw</p>
 * <p>Description: Bildet einen Lkw ab</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FH Konstanz</p>
 * @author Juergen Kambeitz
 * @version 1.0
 */

public class Lkw {

  private int laenge;
  private int breite;
  private int hoehe;
  private int zuladung;
  private int anzahl;

  /**
   * Konstruktor der Klasse Lkw. Hier werden die übergebenen Werte für die Membervariablen gesetzt.
   * @param pLaenge
   * @param pBreite
   * @param pHoehe
   * @param pZuladung
   */
  public Lkw(int pLaenge, int pBreite, int pHoehe, int pZuladung) {
    this.setLaenge(pLaenge);
    this.setBreite(pBreite);
    this.setHoehe(pHoehe);
    this.setZuladung(pZuladung);

  }

  /**
   * Set-Methode der Membervariablen Laenge
   * @param pLaenge
   */
  public void setLaenge(int pLaenge)
  {
    laenge = pLaenge;
  }

  /**
   * Get-Methode der Membervariablen Laenge
   * @return
   */
  public int getLaenge()
  {
    return laenge;
  }

  /**
   * Set-Methode der Membervariablen Breite
   * @param pBreite
   */
  public void setBreite(int pBreite)
  {
    breite = pBreite;
  }

  /**
   * Get-Methode der Membervariablen Breite
   * @return
   */
  public int getBreite()
  {
    return breite;
  }

  /**
   * Set-Methode der Membervariablen Hoehe
   * @param pHoehe
   */
  public void setHoehe(int pHoehe)
  {
    hoehe = pHoehe;
  }

  /**
   * get-Methode der Membervariablen Hoehe
   * @return
   */
  public int getHoehe()
  {
    return hoehe;
  }

  /**
   * Set-Methode der Membervariablen Zuladung
   * @param pZuladung
   */
  public void setZuladung(int pZuladung)
  {
    zuladung = pZuladung;
  }

  /**
   * Get-Methode der Membervariablen Zuladung
   * @return
   */
  public int getZuladung()
  {
    return zuladung;
  }

}