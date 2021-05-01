package SolverCaller;
import java.io.*;
import java.util.*;

/**
 * Ruft den LP Solver auf. Kommunikation erfolgt über Input Files und
 * Output Files.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public class LPSolver extends BatchSolver
{
  private final static String BATCH_FILE  = "start_lpsolve.bat";
  private final static String ERROR_FILE  = "lpsolve_batchErrorStream.txt";
  private final static String OUTPUT_FILE = "lpsolve_batchOutStream.txt";
  private String _errMsg;

  /**
   * Konstruktor. Erzeugt ein Objekt vom Typ LPSolver.
   * @param inFileName Name der Eingabedatei(enthält den LP-Ansatz).
   * @param outFileName Name der Ausgabedatei(enthält bei Erfolg das Ergebnis).
   * @param arbeitsVerzeichnis Hier werden Ein- u. Ausgabedateien erzeugt und
   * Fehler-Logdateien.
   * @param solverPfad Verzeichnis in dem der Solver sich befindet.
   * @param solverName Name des Solvers(ausführbare Datei).
   * @param parser Parser der die Ausgabedatei auswertet.
   */
  public LPSolver( String inFileName,
                   String outFileName,
                   String arbeitsVerzeichnis,
                   String solverPfad,
                   String solverName,
                   Parser parser )
  {
    super(inFileName,outFileName,arbeitsVerzeichnis,solverPfad,solverName,parser);
    _errMsg = "";
  }

  /**
  * Fügt ein vom User definiertes LP Model in den Eingabepuffer ein. Um den Puffer in die
  * Eingabedatei zu schreiben muss Methode writeLPModel() verwendet werden.
  * Diese Methode kann vom User bearbeitet werden. Sie soll es dem 
  * Softwareentwickler ermoeglichen seinen eigenes def. lineares Programmier Model
  * gezielt in das vom Solver verlangte Model umzuwandel und einzufuegen.
  * Diese Aufgabe ist vom Anwender des SolverCaller Packets selbst zu programmieren!
  * @param lpmodel Das User definierte LPModel
  * @param anzVar  Die Anzahl der Variablen im LPModel (x0,x1,..,xn)
  * sein.
  */
  public void addLPModel(String lpmodel[], int anzVar)
  {

        addLPZeile("min: " + lpmodel[0]+";");
	
	String tmpStr="";
	// BOUNDS
	for (int j=0; j<anzVar; j++)
	{
		tmpStr = "B" + (j+1) + ": x"+(j+1)+" >= 0;";
		addLPZeile(tmpStr);
	}
	// CONSTRAINTS
	for (int i=1; i<lpmodel.length; i++)
	{
		//lpmodel[i] = lpmodel[i].replace('.', ',');
		addLPZeile("C" + i + ": " + lpmodel[i] + ";");
	}     
	
	// Nicht Negativ
	tmpStr = "int ";
	for (int j=0; j<anzVar; j++)
	{
		tmpStr += "x"+(j+1)+",";
	}
	   
	char[] c = tmpStr.toCharArray();
	c[c.length-1] = ';';
	tmpStr = String.copyValueOf(c);
	
	addLPZeile(tmpStr);
  }
  
  /**
  * Kam es zu einem Fehler, kann mit dieser Methode der Fehlertext ausgelesen werden.
  * @return Der Fehlertext
  */
  public String getErrorMsg() { return _errMsg;  }

  /**
  * Leitet die Berechnung des Linearen Gleichungssystems ein.
  * @return Fehlernummer<br>
  * gleich  0  - kein Fehler<br>
  * kleiner 0  - Fehler! Fehlertext kann ermittelt werden.<br>
  * größer  0:<br>
  * Falls Fehlercode 1 -> infeasable problem<br>
  * Falls Fehlercode 2 -> unbounded solution<br>
  *
  * @see IErrorMessage#getErrorMsg()
  */
  public int solve()
  {
    String outFilePfad;
    String inFilePfad;
    String solverPath;
    String buffer = "";
    int errorcode = 0;

    // Pfade und Parameter basteln..
    if (getWorkingDir().length()>0)
    { outFilePfad = getWorkingDir()+ "\\" + getOutFileName();
      inFilePfad  = getWorkingDir()+ "\\" + getInFileName();
    }
    else
    { outFilePfad = getOutFileName();
      inFilePfad = getInFileName();
    }

    if (getExecPath().length()>0)
      solverPath = getExecPath() + "\\" + getSolverName();
    else
      solverPath = getSolverName();

    String parameter1 = "<" + inFilePfad + ">";
    String parameter2 = outFilePfad;

    // Korrekturen..

    // Eingabedaten Einlesen aus Eingabedatei..
    FileReader fileReader = new FileReader(inFilePfad);
    errorcode = fileReader.read();
    String data = fileReader.getData();
    // Eingabedatei konnte nicht gelesen werden ?
    if (errorcode < 0)
    {
      _errMsg = fileReader.getErrorMsg();
      _errMsg+= "\r\n->Sind die Pfade richtig gesetzt?";
      _errMsg+= "\r\n->Besitzten sie genügen Schreib- u. Leserechte?";
      return errorcode;
    }

    // Executive Strings erstellen..
    String executeBatch  = getWorkingDir()+ "\\" + BATCH_FILE;
    String executeSolver = solverPath + " " + parameter1 + " " + parameter2;

    try
    { // Stapeldatei erzeugen für Solver Aufruf, da direkter Aufruf nicht
      // funktioniert..!
      FileOutputStream fileOutStream = new FileOutputStream(executeBatch);
      PrintWriter out = new PrintWriter(fileOutStream);
      out.println("@echo of");
      out.println("echo Automatisch erzeugte Stapeldatei startet den LP-Solver");
      out.println(executeSolver);
      out.println(parameter2);
      out.println("exit");
      out.close();
    }
    catch (Exception e)
    {
      _errMsg = "Konnte Start.bat zum starten des Sovers nicht schreiben";
      return -1;
    }

    // Den Solver Ausführen! (Geht leider nur über eine Batchdatei(?))
    Executor solver = new Executor();

    errorcode = solver.starteProcess( executeBatch,
                                       getWorkingDir()+"\\" + ERROR_FILE,
                                       getWorkingDir()+"\\" + OUTPUT_FILE
                                     );

    if (errorcode < 0)
    {
      _errMsg = solver.getErrorMsg();
      return errorcode;
    }

    // Ausgabefile parsen
    errorcode = parseOutFile();
    _errMsg = this.getErrorMsg();

    return errorcode;
  }

// --------------------------------------------------------------------------

  private int parseOutFile()
  {
    double optimum;
    int    errorcode;

    // Ausgabedatei auslesen..
    String outFilePfad = getWorkingDir()+"\\"+getOutFileName();
    FileReader fileReader = new FileReader(outFilePfad);
    errorcode = fileReader.read();
    if (errorcode < 0)
    {
      _errMsg = fileReader.getErrorMsg();
      _errMsg += "\r\n-> Überprüfen sie ob alle Pfade richtig gesetzt wurden.";
      _errMsg += "\r\n-> Überprüfen die die Eingabe auf Syntaxfehler.";

      return errorcode;
    }

    // Daten in String-Puffer transferieren..
    String data = fileReader.getData();
    getParser().setParseString(data);

    // getParser() = new LPSolveParser(data);
    if (getParser() != null)
    {
      errorcode = getParser().parse();
      _errMsg = getParser().getErrorMsg();
    } else
    {
      errorcode = -1;
      _errMsg =  "Parser ist nicht gesetzt!\n";
      _errMsg += "-> Methode setParser() ist vor dem Aufruf des Solvers zu verwenden.";
    }
    return errorcode;
  }
} /* class */
