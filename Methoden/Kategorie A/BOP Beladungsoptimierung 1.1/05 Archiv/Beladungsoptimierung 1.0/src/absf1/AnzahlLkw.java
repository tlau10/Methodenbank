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
   * @param pLkw Parameter f�r die �bergabe des gew�nschten LKWs
   * @param pAnzahl Parameter f�r die �bergabe der gew�nschten Anzahl
   */
  public AnzahlLkw(Lkw pLkw, int pAnzahl) {
    this.setMyLkw(pLkw);
    this.setAnzahl(pAnzahl);
  }

  /**
   * Set-Methode f�r die Membervariable Anzahl
   * @param pAnzahl
   */
  public void setAnzahl(int pAnzahl)
  {
    anzahl = pAnzahl;
  }

  /**
   * Get-Methode f�r die Membervariable Anzahl
   * @return Liefert die Anzahl der LKWs zur�ck
   */
  public int getAnzahl()
  {
    return anzahl;
  }

  /**
   * Set-Methode f�r die Membervariable myLkw
   * @param pMyLkw
   */
  public void setMyLkw(Lkw pMyLkw)
  {
    myLkw = pMyLkw;
  }

  /**
   * Get-Methode f�r die Membervariable myLkw
   * @return Liefert einen LKW zur�ck
   */
  public Lkw getMyLkw()
  {
    return myLkw;
  }
}

