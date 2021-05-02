package absf1;

/**
 * <p>Title: AnzahlGebinde</p>
 * <p>Description: Bildet eine Zuordnung eines einzelnen Gebindes zu einer Anzahl und einer evtl. Sammelsendung ab</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Juergen Kambeitz
 * @version 1.0
 */

public class AnzahlGebinde {

  private int anzahl;
  //Sammelsendung wurde auskommentiert, da diese Funktionalität mit dem momentanen
  //Prototypen noch nicht realisiert wurde.
  /*private int sammelsendung;*/
  private Gebinde myGebinde;

  /**
   * Konstruktor der Klasse AnzahlGebinde. Es werden die Parameter unten übergeben und anschliessend gesetzt.
   * @param pAnzahl Die Anzahl der Gebinde
   * @param pGebinde Das zugehörige Gebinde zur Anzahl
   */
  public AnzahlGebinde(int pAnzahl,/*int pSammelsendung,*/ Gebinde pGebinde) {

    this.setAnzahl(pAnzahl);
    //this.setSammelsendung(pSammelsendung);
    this.setMyGebinde(pGebinde);
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
   * @return Liefert die Anzahl Gebinde zurück
   */
  public int getAnzahl()
  {
    return anzahl;
  }

  /*public void setSammelsendung(int pSammelsendung)
  {
    sammelsendung = pSammelsendung;
  }

  public int getSammelsendung()
  {
    return sammelsendung;
  }*/

  /**
   * Set-Methode für die Membervariable MyGebinde
   * @param pMyGebinde
   */
  public void setMyGebinde(Gebinde pMyGebinde)
  {
    myGebinde = pMyGebinde;
  }

  /**
   * Get--Methode für die Membervariable MyGebinde
   * @return Liefert ein Gebinde zurück
   */
  public Gebinde getMyGebinde()
  {
    return myGebinde;
  }
}