package absf1;

/**
 * <p>Title: AnsichtLkw</p>
 * <p>Description: Repraesentiert ein Array von LKWs mit ihren Eigenschaften im GUI</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Juergen Kambeitz
 * @version 1.0
 */

public class AnsichtLkw {

  private AnzahlLkw[] lkwArray;
  private AnzahlLkw[][] lkwTabelle;
  private String[] colheads;

  public AnsichtLkw() {

  }

  public void setLkwArray(AnzahlLkw[] pLkwArray)
  {
    lkwArray = pLkwArray;
  }

  public AnzahlLkw[] getLkwArray()
  {
    return lkwArray;
  }

  public void setColheads(String[] pColheads)
  {
    colheads = pColheads;
  }

  public String[] getColheads()
  {
    return colheads;
  }

 /* public void setLkwTabelle(AnzahlLkw[][] pLkwTabelle)
  {
    lkwTabelle = pLkwTabelle;
  }

  public AnzahlLkw[][] getLkwTabelle()
  {
    return lkwTabelle;
  }*/


}