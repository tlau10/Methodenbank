package zuordnungsplanung;
/**
 * <p>Title: SolveEnumeration</p>
 * <p>Description: Finds a solution by using enumeration.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Bettina Pfeiffer + Patrick Badent
 * @version 1.0
 */

public class SolveEnumeration implements Solver{
  String[] ergebnis;
  int zielfunktionswert;
  DataModel dataModel_;
  int rows;
  int columns;
  int loesung[];
  int zaehler;
  boolean columnsLaenger;
  long SolverTime;

  public SolveEnumeration(){
    ergebnis = new String[0];
  }

  @Override
public void starteSolver(DataModel dataModel)
  {
    dataModel_ = dataModel;
    rows = dataModel_.getRows()-1;
    columns = dataModel_.getColumns()-1;
    Object columnVector[] = dataModel.getColumnVector();
    Object rowVector[] = dataModel.getRowVector();

    if((dataModel_.getRows()-1) * (dataModel_.getColumns()-1) > 64)
    {
      ergebnis = new String[2];
      ergebnis[0] = "Das Problem ist zu gro� f�r die vollst�ndige Enumeration.";
      ergebnis[1] = "Bitte probieren Sie es mit einem anderem Solver!";
      return;
    }

    if(dataModel_.getMaximize()==false)
      zielfunktionswert = Integer.MAX_VALUE;
    int x;
    int y;
    if(rows<columns)
    {
      y=columns;
      x=rows;
      columnsLaenger = true;
    }
    else
    {
      x = columns;
      y = rows;
      columnsLaenger = false;
    }
    loesung = new int[x];
    zaehler = 0;
    int m�glichkeiten[] = new int[x];
    SolverTime = System.currentTimeMillis();
    enumrek(x, x, y, m�glichkeiten);
    SolverTime = System.currentTimeMillis()-SolverTime;
    ergebnis = new String[x];
    for(int i = 0; i< loesung.length; i++)
      if (columnsLaenger == true)
        ergebnis[i] = rowVector[loesung.length - i] + " -> " + columnVector[loesung[i] + 1];
      else
        ergebnis[i] = rowVector[loesung[i] + 1] + " -> " + columnVector[loesung.length - i];
    return;
  }

  public void enumrek(int tiefe, int x, int y, int m�glichkeiten[])
  {
    if (tiefe==0)
    {
      boolean gueltig = true;
      for (int i = 0; i< m�glichkeiten.length; i++)
      {
        for (int j = i+1; j< m�glichkeiten.length; j++)
        {
          if (m�glichkeiten[i] == m�glichkeiten[j])
          {
            gueltig = false;
          }

        }
      }
      if(gueltig == true)
      {
        findeMaxWert(m�glichkeiten);
        zaehler++;
      }
      return;
    }
    else
    {
        for(int i=0; i<y; i++)
        {
          m�glichkeiten[tiefe-1] = i;
          enumrek(tiefe - 1, x, y, m�glichkeiten);
        }
    }

  }

  private void findeMaxWert(int m�glichkeiten[])
  {
    int zielwertTemp = 0;
    for (int i = 0; i < m�glichkeiten.length; i++)
    {
      if(columnsLaenger == true)
        zielwertTemp = zielwertTemp + Integer.parseInt((String) dataModel_.getValueAt((i+1), m�glichkeiten[m�glichkeiten.length -i -1]+1));
      else
        zielwertTemp = zielwertTemp + Integer.parseInt((String) dataModel_.getValueAt((m�glichkeiten[m�glichkeiten.length -i -1]+1),(i+1)));
    }
    if (dataModel_.getMaximize() == true){
      if (zielfunktionswert < zielwertTemp)
      {
        zielfunktionswert = zielwertTemp;
        loesung = new int[m�glichkeiten.length];
        for (int i=0; i<m�glichkeiten.length; i++)
          loesung[i] = m�glichkeiten[i];
      }
    }
    else{
      if (zielfunktionswert > zielwertTemp)
      {
        zielfunktionswert = zielwertTemp;
        loesung = new int[m�glichkeiten.length];
        for (int i=0; i<m�glichkeiten.length; i++)
          loesung[i] = m�glichkeiten[i];
      }
    }
  }

  @Override
public String[] getErgebnis(){
    return ergebnis;
  }
  @Override
public String getZielfunktionswert(){
    return zielfunktionswert + "";
  }
  @Override
public long getZeit(){
    return SolverTime;
  }
}