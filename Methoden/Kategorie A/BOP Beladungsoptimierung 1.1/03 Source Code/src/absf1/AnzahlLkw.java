package absf1;

/**
 * <p>Title: AnzahlLkw</p>
 * <p>Description: Bildet den Mengenaspekt bezueglich der verfuegbaren LKWs ab</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FH Konstanz </p>
 * @author Juergen Kambeitz
 * @version 1.0
 */

public class AnzahlLkw {

  private int anzahl;
  private Lkw myLkw;

  /**
   * Konstruktor der Klasse AnzahlLkw
   * @param pLkw Parameter für die Übergabe des gewünschten LKWs
   * @param pAnzahl Parameter für die Übergabe der gewünschten Anzahl
   */
  public AnzahlLkw(Lkw pLkw, int pAnzahl) {
    this.setMyLkw(pLkw);
    this.setAnzahl(pAnzahl);
  }

  /**
   * Set-Methode für die Membervariable Anzahl
   * @param pAnzahl
   */
  public void setAnzahl(int pAnzahl)
  {
    anzahl = pAnzahl;
  }

  /**
   * Get-Methode für die Membervariable Anzahl
   * @return Liefert die Anzahl der LKWs zurück
   */
  public int getAnzahl()
  {
    return anzahl;
  }

  /**
   * Set-Methode für die Membervariable myLkw
   * @param pMyLkw
   */
  public void setMyLkw(Lkw pMyLkw)
  {
    myLkw = pMyLkw;
  }

  /**
   * Get-Methode für die Membervariable myLkw
   * @return Liefert einen LKW zurück
   */
  public Lkw getMyLkw()
  {
    return myLkw;
  }
}

