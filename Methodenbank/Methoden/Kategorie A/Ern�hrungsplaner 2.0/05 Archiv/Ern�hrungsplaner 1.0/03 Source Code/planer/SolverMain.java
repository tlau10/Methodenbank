package planer;


/**
 * <p>Title: Solver Beispielklass</p>
 * <p>Description: Diese Klasse soll zeigen wie unser Solver Aufruf funktioniert und wie die Matritzen auszusehen haben</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Steffen Kreidler
 * @version 1.0
 */

public class SolverMain
{

  public SolverMain()
  {
  }
/*
  public static void main(String[] args)
  {
   // System.out.println("Properties:");
   // System.getProperties().list(System.out);
   try
   {
     Matrix model = new Matrix(4, 4, 1000, 1000);
     Matrix grenzen = new Matrix(3, 4, 3, 4);
     //Modelmatrix wird initialisiert, siehe model.jpg zur Verdeutlichung
     //erste Zeile -> Zielfunktion
     model.setElement(0, 0, 1);
     model.setElement(0, 1, 2);
     model.setElement(0, 3, "max");
     //zweite Zeile
     model.setElement(1, 0, -2.1);
     model.setElement(1, 1, 1);
     model.setElement(1, 2, "<");
     model.setElement(1, 3, "3");
     //dritte Zeile
     model.setElement(2, 0, "1");
     model.setElement(2, 1, ".2");
     model.setElement(2, 2, "<");
     model.setElement(2, 3, 8);
     //vierte Zeile
     model.setElement(3, 0, 4);
     model.setElement(3, 1, -1.5);
     model.setElement(3, 2, "<");
     // "0" wird als Standard in Matrix gesetzt
     //model.setElement(3,3,0);

     // Grenzenmatrix wird initialisiert, nur Grenzen die benutzt werden sollen müssen gesetzt werden
     // siehe grenzen.jpg
     grenzen.setElement(0, 0, 1);
     grenzen.setElement(0, 1, 5);
     // Ganzzahligkeit: alles ausser "ja" wird als nein interpretiert
     grenzen.setElement(1, 2, "ja");

     Solver mySolver = new LP_Solve();
     // Aufruf ohne Grenzen
     // double[] ergebnis = mySolver.calcModel(model);
     // Aufruf mit Grenzen
     double[] ergebnis = mySolver.calcModel(model, grenzen);
     System.out.println("\nZielfunktionswert: " + ergebnis[0]);
     for (int i = 1; i < ergebnis.length; i++)
       System.out.println("x" + i + " = " + ergebnis[i]);
   }
   catch(DiaetplanerException e)
   {
     System.out.println(e.getMessage());
   }

  }
*/
}