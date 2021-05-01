package absf1;

import java.lang.String;
import java.io.*;
import java.util.*;
import javax.swing.table.TableModel;
import javax.swing.*;

/**
 * <p>Title: Solver Interface</p>
 * <p>Description:Interface  Schnittstelle für die Solverklassen</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Florian Puetz
 * @version 1.0
 */

public class LPSolve implements Solver{

  private String zielFunktion;
  private String Restriktion;
  private ArrayList ergebnis;
  private String Bedingung;
  private boolean isBatch;

  /**
   * Konstruktor der Klasse LPSolve
   */
  public LPSolve() {
    ergebnis = new ArrayList();
    isBatch = false;
  }

  /**
   * Bereitet das Eingabe-File für den Solver vor
   * @param inputList
   * @param pGebinde
   */
  public void writeToInFile(ArrayList inputList,TableModel pGebinde) {

    //Zusammensetzung der Zielfunktion
    Restriktion = "";
    zielFunktion = "min:";
    if(inputList.isEmpty() == false)
    {
    zielFunktion += "1 x1"; //erster Wert ohne Pluszeichen
    for(int i=2; i<=inputList.size(); i++)
      zielFunktion += " + 1 x"+i;
    zielFunktion += ";";
    }
    //Aufbau der Restriktionen
   for(int j=1; j < pGebinde.getRowCount()+1; j++)
   {
     Restriktion += "R"+j+":\t";
     int[] temp2 = (int[])inputList.get(0);
     Restriktion += temp2[j]+" x1";
     for(int k=1; k < inputList.size()-1; k++)
     {
       int[] temp = (int[])inputList.get(k);
       if(temp[j] != 0)
          Restriktion += " + "+temp[j]+" x"+(k+1);
     }
     //letzter Teil der Restriktion
     int[] temp1 = (int[])inputList.get(inputList.size()-1);
     if(temp1[j] != 0)
      Restriktion += " + " +temp1[j]+" x"+(inputList.size());
      Restriktion += " >= "+pGebinde.getValueAt(j-1, 5)+"; \n";
   }

   //Aufbau der Bedingung, das Zahl ganzzahlig sein muss
   if(inputList.isEmpty() == false)
    {
    Bedingung = "int x1"; //erster Wert ohne Pluszeichen
    for(int i=2; i<=inputList.size(); i++)
      Bedingung += ",x"+i;
    Bedingung += ";";
    }

  }

  /**
   * Ruft den Solver auf
   * @param directory
   * @return
   * @throws IOException
   */
  public int executeSolver(String directory)
    throws IOException {
    boolean isfinished = false;
    int exitValue = 0;
    String output ="";
    //Überprüfen ob der übergebene Pfad eien Batchdatei ist
    try{
      if(directory.endsWith(".bat")){
        isBatch = true;
        PrintWriter myOutFile = new PrintWriter(new BufferedWriter(new FileWriter("C:/Temp/Solver.in")));
        myOutFile.println(zielFunktion);
        myOutFile.println();
        myOutFile.println(Restriktion);
        myOutFile.println();
        myOutFile.println(Bedingung);
        myOutFile.close();
      }

      //Aufruf des Solvers
    Runtime myRuntime = Runtime.getRuntime();
    Process myProcess = myRuntime.exec(directory);

    //Übergabepfad war keine Batchdatei
    if(isBatch == false) {
    PrintStream eingabe = new PrintStream(new BufferedOutputStream(myProcess.getOutputStream()));
    eingabe.println(zielFunktion);
    eingabe.println();
    eingabe.println(Restriktion);
    eingabe.println();
    eingabe.println(Bedingung);

    eingabe.flush();
    eingabe.close();
    }

    try{

      myProcess.waitFor();
    }
    catch(InterruptedException eth) {
      eth.printStackTrace();
    }

    try {
      exitValue= myProcess.exitValue();
    }
    catch(IllegalThreadStateException e)
    {
      e.printStackTrace();
    }
    if(isBatch == false) {
    BufferedReader rueckgabe = new BufferedReader(new InputStreamReader(myProcess.getInputStream()));
    while((output = rueckgabe.readLine()) != null )
    {
       //0 nicht mit in Ergebnisliste mit aufnehmen
       if(!output.endsWith(" 0"))
       {
         ergebnis.add((String)output);
       }
       if(output.endsWith("This problem is infeasible")){
         return 3;
       }
    }
    }
    else {
      System.out.println("schreibe nach c-temp");
    BufferedReader myInFile = new BufferedReader(new FileReader("C:/temp/solver.out"));
    int hasOutput = 0;
    while((output = myInFile.readLine()) != null )
    {
      hasOutput++;
       //0 nicht mit in Ergebnisliste mit aufnehmen
       if(!output.endsWith(" 0"))
       {
         ergebnis.add((String)output);
       }
       if(output.endsWith("This problem is infeasible")){
         return 3;
       }
    }
    if (hasOutput == 0) {
JOptionPane.showMessageDialog(null, "Problem konnte nicht geloest werden. Eingaben ueberpruefen?", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
    }

    }



    if(exitValue==0)
      return 0;
    else
      return 1;
    }
   catch(IOException eio)
   {
      JOptionPane.showMessageDialog(null, "Solverpfad nicht gefunden, bitte ändern Sie die Einstellung", "Fehler", JOptionPane.ERROR_MESSAGE);
      return 2;
    }
  }

  /**
   * Liefert das Ergebnis zurück
   * @return
   */
  public ArrayList getOutput() {
    // to do
    return ergebnis;
  }

}