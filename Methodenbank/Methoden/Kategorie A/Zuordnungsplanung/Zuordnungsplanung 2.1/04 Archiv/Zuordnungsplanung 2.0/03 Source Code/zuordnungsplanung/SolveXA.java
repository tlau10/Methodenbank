

package zuordnungsplanung;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>Title: Zuordnungsplanung V 1.0</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Bettina Pfeiffer
 * @version 1.0
 */

public class SolveXA extends Thread implements Solver {
  String[] ergebnis;
  String zielfunktionswert;
  private Process solver;
  long SolverTime;

  public SolveXA()
  {
    ergebnis = new String[0];
  }

  public void starteSolver(DataModel dataModel)
  {
    Object columnVector[] = dataModel.getColumnVector();
    Object rowVector[] = dataModel.getRowVector();
    boolean maximierung = dataModel.getMaximize();

    try{
      //BatchDatei und InputFile für Solver erstellen
      PrintWriter myXASolveBatch = new PrintWriter(new BufferedWriter(new FileWriter(Variables.solverDirectory+"/solver.bat")));
      PrintWriter myXASolveIn = new PrintWriter(new BufferedWriter(new FileWriter(Variables.solverDirectory+"/xa_solve.in")));
      PrintWriter myXASolveCopyBatch = new PrintWriter(new BufferedWriter(new FileWriter(Variables.solverDirectory+"/solverCopy.bat")));
      //Batchdateien schreiben

      //XA-Solver nach c:\temp kopieren
      File SolverXA = new File ("solver/xa.exe " + Variables.solverDirectory);
      myXASolveCopyBatch.println(Variables.copy + SolverXA);
      //Sovler.pif nach c:\temp kopieren
      File SolverPif = new File ("SOLVER.pif " + Variables.solverDirectory);
      myXASolveCopyBatch.println(Variables.copy + SolverPif);
      myXASolveCopyBatch.close();

      //Batch-Datei für Solver-Start
      myXASolveBatch.println("@echo off");
      myXASolveBatch.println("REM Solver: XA");
      myXASolveBatch.println("set Oldpath=%path%");
      File Solver = new File(Variables.solverDirectory + "/xa");
      myXASolveBatch.println("path " + Solver + ";%path%");
      myXASolveBatch.println("xa xa_solve.in > xa_solve.out");
      myXASolveBatch.println("path %Oldpath%");
      myXASolveBatch.println("set Oldpath=");
      myXASolveBatch.close();

      //Maximierung oder Minimierung
      String maxMin;
      if (maximierung == true)
        maxMin = "MAXIMIZE";
      else
        maxMin = "MINIMIZE";

      myXASolveIn.println("..TITLE");
      myXASolveIn.println("  Zuordnungsplanung");
      myXASolveIn.println("..OBJECTIVE " + maxMin);
      /**********************Zielfunktion bilden*************/
      //Ganzzahligkeit
      myXASolveIn.println("  [");
      String Zielfunktion = "   + ";
      int i = 0;
      int j = 0;
      int t = 0;
      for (i = 1; i < rowVector.length; i++){
        for (j = 1; j < columnVector.length; j++){
          if (t == 25){
                Zielfunktion = Zielfunktion.substring(0, Zielfunktion.length()-3);
                myXASolveIn.println(Zielfunktion);
                Zielfunktion = "   + ";
            t = 0;
          }
          t++;
            if(i <= 9 && j <= 9)
              Zielfunktion = Zielfunktion + dataModel.getValueAt(i,j) + " x0" + (i) + "0" + (j) + " + ";
            else if(i <= 9)
              Zielfunktion = Zielfunktion + dataModel.getValueAt(i,j) + " x0" + (i) + (j) + " + ";
            else if (j <= 9)
              Zielfunktion = Zielfunktion + dataModel.getValueAt(i,j) + " x" + (i) + "0" + (j) + " + ";
            else
              Zielfunktion = Zielfunktion + dataModel.getValueAt(i,j) + " x" + (i) + (j) + " + ";
            }
        }
      //letztes ' + ' oder ' - ' muss gelöscht werden und ein abschließendes Semikolon wird hinzugefügt

      Zielfunktion = Zielfunktion.substring(0, Zielfunktion.length()-3);
      myXASolveIn.println(Zielfunktion);
      //Ganzzahligkeit
      myXASolveIn.println("  ]");
      myXASolveIn.println("..BOUNDS");

      //Bounds

      for (i = 1; i < rowVector.length; i++){
        for (j = 1; j < columnVector.length; j++){
            if(i <= 9 && j <= 9){
              myXASolveIn.println(" x0" + (i) + "0" + (j) + ">=0");
              myXASolveIn.println(" x0" + (i) + "0" + (j) + "<=1");
              }
            else if(i <= 9){
              myXASolveIn.println(" x0" + (i) + (j) + ">=0");
              myXASolveIn.println(" x0" + (i) + (j) + "<=1");
              }
            else if (j <= 9){
              myXASolveIn.println(" x" + (i) + "0" + (j) + ">=0");
              myXASolveIn.println(" x" + (i) + "0" + (j) + "<=1");
              }
            else{
             myXASolveIn.println(" x" + (i) + (j) + ">=0");
             myXASolveIn.println(" x" + (i) + (j) + "<=1");
             }
          }
      }

      /**************Restriktionen bilden*******************/
      myXASolveIn.println("..CONSTRAINTS");
      int k = 0;
      int l = 0;

      //Wenn die Matrix nicht auf beiden Seiten gleich lang ist muss die Restriktion geändert werden
      String vergleich1 = " = 1";
      String vergleich2 = " = 1";
      if (rowVector.length < columnVector.length)
        vergleich2 = " <=1";
      else if (rowVector.length > columnVector.length)
        vergleich1 = " <=1";
      String Restriktion = "";

      //Restriktionen für Bewerber
      for (k = 1; k < rowVector.length; k++){
        Restriktion = "  R" + (k) + ": ";
        for (l = 1; l < columnVector.length; l++){
          if(k<=9 && l<=9)
            Restriktion += "x0" + (k) + "0" + (l) + " + ";
          else if(k<=9)
            Restriktion += "x0" + (k) + (l) + " + ";
          else if(l<=9)
            Restriktion += "x" + (k) + "0"+ (l) + " + ";
          else
            Restriktion += "x" + (k) + (l) + " + ";
          }
        Restriktion =Restriktion.substring(0, Restriktion.length()-3) + vergleich1;
        myXASolveIn.println(Restriktion);
      }

      int m = 0;
      int n = 0;

      //Restriktionen für Stellen
      for (m = 1; m < columnVector.length; m++){
        Restriktion = "  R" + (k) + ": ";
        k++;
        for (n = 1; n < rowVector.length; n++){
          if (n <= 9 && m<=9)
            Restriktion += "x0" + (n) + "0" + (m) + " + ";
          else if (n <=9)
            Restriktion += "x0" + (n) + (m) + " + ";
          else if (m <=9)
            Restriktion += "x" + (n) + "0" + (m) + " + ";
          else
            Restriktion += "x" + (n) + (m) + " + ";
          }
        Restriktion =Restriktion.substring(0, Restriktion.length()-3) + vergleich2;
        myXASolveIn.println(Restriktion);
      }
      myXASolveIn.close();
      /******************XA-Solver starten******************/
      solver = Runtime.getRuntime().exec(Variables.solverDirectory+"/solverCopy.bat");
      try{
        solver.waitFor();
      }
      catch(InterruptedException eth) {
        ExceptionDialog.showExceptionDialog("Fehler beim Solver XA",eth.getMessage(),eth);
      }
      SolverTime = System.currentTimeMillis();
      solver = Runtime.getRuntime().exec(Variables.solverDirectory+"/SOLVER.pif");
      try{
        solver.waitFor();
      }
      catch(InterruptedException eth) {
        ExceptionDialog.showExceptionDialog("Fehler beim Solver XA",eth.getMessage(),eth);
      }
      SolverTime =  System.currentTimeMillis() - SolverTime;
      /****************Auslesen aus dem Solver**************/
      File xa_SolveOut = new File (Variables.solverDirectory+"/xa_solve.out");
      BufferedReader outputDatei = new BufferedReader(new FileReader(xa_SolveOut));
      //Zielfunktionswert
      String Ausgabe = "";
      String Solution = "";
      while(Solution.compareTo("SOLUTION")!= 0){
        Ausgabe = outputDatei.readLine();
        if(Ausgabe.length() >= 8)
          Solution = Ausgabe.substring(0,8);
      }
      zielfunktionswert = Ausgabe.substring(21,27).trim();
      /**************************Ergebnis*************************************/
      String Ergebniswert = "";
      for (int x = 0; x<4; x++)
        Ergebniswert = outputDatei.readLine();
      String wert;
      if (rowVector.length < columnVector.length)
      {
        ergebnis = new String[rowVector.length-1];
      }
      else{
        ergebnis = new String[columnVector.length-1];
      }
      int index =0;
      boolean gehtnichtsmehr = false;
      while (gehtnichtsmehr == false){
        if (Ergebniswert.substring(17,18).compareTo("1") == 0 || Ergebniswert.substring(17,18).compareTo("0") == 0){
       for( int y = 0; y < 6; y++){
          if(Ergebniswert.length()>18)
          {
            wert = Ergebniswert.substring(17,18);
            if(wert.compareTo("1") == 0){
              ergebnis[index] = Ergebniswert.substring(6,10);
              index++;
              }
          }
          if(Ergebniswert.length()>57){
            wert = Ergebniswert.substring(56,57);
            if(wert.compareTo("1") == 0){
              ergebnis[index] = Ergebniswert.substring(45,49);
              index++;
              }
            }
          for(int u = 0; u < 3; u++)
            Ergebniswert = outputDatei.readLine();
          }
          for(int x = 0; x < 7; x++)
            Ergebniswert = outputDatei.readLine();
          }
          else
            gehtnichtsmehr = true;
      }
      //Ergebnisfeld zusammensetzen
      for (int p = 0; p < ergebnis.length; p++){
        ergebnis[p] = rowVector[Integer.parseInt(ergebnis[p].substring(0,2))] + " -> " + columnVector[Integer.parseInt(ergebnis[p].substring(2,4))];
      }

      outputDatei.close();
    }
    catch(IOException eio)
    {
        ExceptionDialog.showExceptionDialog("Fehler im Solver XA.",eio.getMessage(),eio);
    }
    catch ( Exception ex )
    {
       ExceptionDialog.showExceptionDialog("Fehler im Solver XA.",ex.getMessage(),ex);
    }
    return;
  }

  public String[] getErgebnis(){
    return ergebnis;
  }

  public String getZielfunktionswert(){
    return zielfunktionswert;
  }
  public long getZeit(){
    return SolverTime;
  }
}