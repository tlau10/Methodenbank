package absf1;

/**
 * <p>Title: Gebinde</p>
 * <p>Description: Bildet ein Gebinde (Palette, Gitterbox o. ae. ab</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FH Konstanz</p>
 * @author Juergen Kambeitz
 * @version 1.0
 */

public class Gebinde {

  private int laenge;
  private int breite;
  private int hoehe;
  private int gewicht;
  //private boolean stapelbar;

  /**
   * ImKonstruktor der Klasse Gebinde werden die einzelnen Member-Variablen gesetzt. Parameter "pStapelbar" wurde auskommentiert, da diese Funktionalität im Augenblick noch nicht implementiert werden konnte.
   * @param pLaenge
   * @param pBreite
   * @param pHoehe
   * @param pGewicht
   */
  public Gebinde(int pLaenge, int pBreite, int pHoehe, int pGewicht/*, boolean pStapelbar*/) {

    this.setLaenge(pLaenge);
    this.setBreite(pBreite);
    this.setHoehe(pHoehe);
    this.setGewicht(pGewicht);
    //this.setStapelbar(pStapelbar);
  }

  /**
   * Set-Methode für die Membervariable Laenge
   * @param pLaenge
   */
  public void setLaenge(int pLaenge)
  {
    laenge = pLaenge;
  }

  /**
   * Get-Methode für die Membervariable Laenge
   * @return
   */
  public int getLaenge()
  {
    return laenge;
  }

  /**
   * Set-Methode für die Membervariable Breite
   * @param pBreite
   */
  public void setBreite(int pBreite)
  {
    breite = pBreite;
  }

  /**
   * Get-Methode für die Membervariable Breite
   * @return
   */
  public int getBreite()
  {
    return breite;
  }

  /**
   * Set-Methode für die Membervariable Hoehe
   * @param pHoehe
   */
  public void setHoehe(int pHoehe)
  {
    hoehe = pHoehe;
  }

  /**
   * Get-Methode für die Membervariable Hoehe
   * @return
   */
  public int getHoehe()
  {
    return hoehe;
  }

  /**
   * Set-Methode für die Membervariable Gewicht
   * @param pGewicht
   */
  public void setGewicht(int pGewicht)
  {
    gewicht = pGewicht;
  }

  /**
   * Get-Methode für die Membervariable Gewicht
   * @return
   */
  public int getGewicht()
  {
    return gewicht;
  }

  /*public void setStapelbar(boolean pStapelbar)
  {
    stapelbar = pStapelbar;
  }

  public boolean isStapelbar()
  {
    return stapelbar;
  }*/
}