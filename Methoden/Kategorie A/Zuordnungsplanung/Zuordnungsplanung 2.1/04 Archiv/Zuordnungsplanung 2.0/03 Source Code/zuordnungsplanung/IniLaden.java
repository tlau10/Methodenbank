package zuordnungsplanung;

import java.io.*;
import java.util.StringTokenizer;

/**
 * <p>Überschrift: Zuordnungsplanung 2004</p>
 * <p>Beschreibung: Hinzufuegen einer Ini-Datei zur leichten Pfadanpassung</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Organisation: HTWG-Konstanz</p>
 * <p>Komplett neue Klasse um per INI-Datei Daten einlesen zu können</p>
 * @author Angelina Bader,, Benedikt Woelfle
 * @version 2.0
 */


public class IniLaden {



  public static String laden(String dateiname) // Die Lade-Methode
          {
                String ausgabe="";
                File datei = new File(dateiname);

            if (datei != null)
            {
                    try // try-catch Prozedur um eventl fehler abzufangen
                {
                            FileReader eingabestrom = new FileReader(datei);
                            int gelesen;
                            StringBuffer text = new StringBuffer(100);
                            boolean ende = false;

                            // lese Zeichen, bis Dateiende erreicht ist
                            while(!ende)
                            {
                                    gelesen = eingabestrom.read();

                                    if(gelesen == -1){
                                            ende = true;}
                                    else{
                                            text.append( (char) gelesen);}
                            }

                            ausgabe = (text.toString()); //Ausgabe des eingelesenen Strings
                            eingabestrom.close(); // Schließen des Lesestroms (Datei wird dadurch wieder "frei")
                }
                catch(FileNotFoundException f) // Fängt Fehler beim Öffnen ab
                {
                        System.err.println(dateiname + " konnte nicht unter " +
                                        System.getProperty("user.dir") + " gefunden werden.");
                }
                catch(IOException f) // Fängt Fehler beim Einlesen ab
                {
                        System.err.println("Fehler beim Einlesen der Datei " + dateiname
                                        + " von " + System.getProperty("user.dir"));
                }
            }
            return ausgabe; // return des Ausgabestrings

          }

          public static String getLpSolvePath(){
            //Beispiel für das Ini-File: lpsolve=solver/lp_solve.exe
            String ausgabe = "solver/lp_solve.exe";
            String iniPath = "path.ini";
            String input = laden(iniPath);
            String output = tokenize(input,"lpsolve");

            if(output != null){
              ausgabe = output;
            }
            return ausgabe;
          }

          public static String getXA(){
            //Beispiel für das Ini-File: xa=solver/xa.exe
            String ausgabe = "solver/xa.exe";
            String iniPath = "path.ini";
            String input = laden(iniPath);
            String output = tokenize(input,"xa");

            if(output != null){
              ausgabe = output;
            }
            return ausgabe;
          }

          public static String getMOPS(){
            //Beispiel für das Ini-File: mops=L:\\BESF\\Solver\\MOPS 7.06\\Exec\\mops.exe
            String ausgabe = "solver/mops.exe";
            String iniPath = "path.ini";
            String input = laden(iniPath);
            String output = tokenize(input,"mops");

            if(output != null){
              ausgabe = output;
            }
            return ausgabe;
          }

          public static String getSolverPIF(){
            //Beispiel für das Ini-File: solverPIF=SOLVER.PIF
            String ausgabe = "SOLVER.pif";
            String iniPath = "path.ini";
            String input = laden(iniPath);
            String output = tokenize(input,"solverPIF");

            if(output != null){
              ausgabe = output;
            }
            return ausgabe;
          }

          public static String getTemp(){
            //Beispiel für das Ini-File: temp=C:/temp
            String ausgabe = "c:/temp";
            String iniPath = "path.ini";
            String input = laden(iniPath);
            String output = tokenize(input,"temp");

            if(output != null){
              ausgabe = output;
            }
            return ausgabe;
          }



          private static String tokenize(String input, String solverName){
            String ausgabe = null;

            if(!input.equals("")){
              StringTokenizer st = new StringTokenizer(input,";");

              while(st.hasMoreElements()){
                String s = st.nextToken();
                s = s.trim();//Leerzeichen entfernen

                if (s.startsWith(solverName)){
                  int position = s.indexOf("=");

                  if(position > 0){ //Pfadname ausschneiden
                    ausgabe = s.substring(position+1);
                    ausgabe.trim();
                  }
                  else{
                    System.err.println("Konnte keinen Substring finden");
                  }
                }
              }
            }
            System.out.println(ausgabe);
            return ausgabe;
          }
}
