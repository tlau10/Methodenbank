package absf1;

/**
 * <p>Title: AnsichtGebinde</p>
 * <p>Description: Repraesentiert ein Array von Gebinden mit Eigenschaften in der GUI-Ansicht</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Juergen Kambeitz
 * @version 1.0
 */

public class AnsichtGebinde {

  private AnzahlGebinde[] gebindeArray;
  private String[] colheads;

  public AnsichtGebinde() {
  }

  public void setGebindeArray(AnzahlGebinde[] pGebindeArray)
  {
    gebindeArray = pGebindeArray;
  }

  public AnzahlGebinde[] getGebindeArray()
  {
    return gebindeArray;
  }

  public void setColheads(String[] pColheads)
  {
    colheads = pColheads;
  }

  public String[] getColheads()
  {
    return colheads;
  }
}