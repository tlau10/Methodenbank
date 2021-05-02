package SolverCaller;
import java.io.*;

/**
 * Ruft den XA Solver auf. Kommunikation erfolgt ueber Input Files und
 * Output Files.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public class XASolver extends BatchSolver
{
  private final static String BATCH_FILE    = "start_xa.bat";
  private final static String COMMAND_FILE  = "xa.clp";
  private final static String ERROR_FILE    = "xa_batchErrorStream.txt";
  private final static String OUTPUT_FILE   = "xa_batchOutStream.txt";
  private final static String XA_OUT        = "xa_output.txt";

  private String _errMsg;

  /**
   * Konstruktor. Erzeugt ein Objekt vom Typ XASolver.
   * @param inFileName Name der Eingabedatei(enthaelt den LP-Ansatz).
   * @param outFileName Name der Ausgabedatei(enthaelt bei Erfolg das Ergebnis).
   * @param arbeitsVerzeichnis Hier werden Ein- u. Ausgabedateien erzeugt und
   * Fehler-Logdateien.
   * @param solverPfad Verzeichnis in dem der Solver sich befindet.
   * @param solverName Name des Solvers(ausfuehrbare Datei).
   * @param parser Parser der die Ausgabedatei auswertet.
   */
  public XASolver( String inFileName,
                   String outFileName,
                   String arbeitsVerzeichnis,
                   String solverPfad,
                   String solverName,
                   Parser parser )
 {
    super(inFileName,outFileName,arbeitsVerzeichnis,solverPfad,solverName,parser);
 }

  public void addLPModel(String lpmodel[], int anzVar)
  {

	addLPZeile("..TITLE");
	addLPZeile("  PROG_GENERATED");
	addLPZeile("..OBJECTIVE MINIMIZE");
	lpmodel[0] = lpmodel[0].replace('.', ',');
	addLPZeile(lpmodel[0]);
	addLPZeile("..BOUNDS");

	String tmpStr;
	for (int j=0; j<anzVar; j++)
	{
		tmpStr = new String("x"+(j+1)+" >= 0");
		addLPZeile(tmpStr);
	}
	addLPZeile("..CONSTRAINTS");

	for (int i=1; i<lpmodel.length; i++)
	{
		lpmodel[i] = lpmodel[i].replace('.', ',');
		addLPZeile(lpmodel[i]);
	}
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
  * groesser  0:<br>
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

    // XA funktioniert nur, wenn XA.EXE und die Steuerdateien im selben
    // Verzeichnis sind!!!!! (XA ist das Arschloch unter den Solvern!!)
    if (getWorkingDir().compareTo(getExecPath()) != 0)
    {
      _errMsg = "Arbeitsverzeichnis und Solververzeichnis muessen beim XA Solver gleich sein!\r\n";
      return -1;
    }


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

    try
    { // Stapeldatei erzeugen fuer Solver Aufruf, da direkter Aufruf nicht
      // funktioniert..!
      FileOutputStream fileOutStream = new FileOutputStream(executeBatch);
      PrintWriter out = new PrintWriter(fileOutStream);
      out.println("@echo of");
      out.println("echo Automatisch erzeugte Stapeldatei startet den XA-Solver");
      out.println("cd "+getWorkingDir());
      out.println(getSolverName() + " " +COMMAND_FILE+ " >" + XA_OUT);
      out.println("exit");
      out.close();
    }
    catch (Exception e)
    {
      _errMsg = "Konnte Stapeldatei zum starten des Solvers nicht schreiben";
      return -1;
    }

    try
    { // Kommandodatei erzeugen fuer Solver Aufruf
      FileOutputStream fileOutStream = new FileOutputStream(getWorkingDir()+"\\"+COMMAND_FILE);
      PrintWriter out = new PrintWriter(fileOutStream);
      out.println(getInFileName() + " LISTINPUT  YES");
      out.println("            OUTPUT  "+ getOutFileName() +" PAGESIZE  24");
      out.println("            LINESIZE  79 TMARGINS   0");
      out.println("            BMARGINS   0  FIELDSIZE  11");
      out.println("            DECIMALS   5  EUROPEAN  YES");
      out.println("            LMARGINS   0  COPIES     1");
      out.println("            WAIT      NO  MUTE  NO");
      out.println("            LISTINPUT NO WARNING YES");
      out.println("            SOLUTION  YES CONSTRAINTS  NO");
      out.println("            COSTANALYSIS  NO MARGINANALYSIS  NO");
      out.println("            MATLIST  NO   DEFAULTS NO");
      out.close();
    }
    catch (Exception e)
    {
      _errMsg = "Konnte Kommandodatei zur Steuerung des Solvers nicht schreiben";
      return -1;
    }

    // Den Solver ueber sStapeldatei ausfuehren
    Executor solver = new Executor();

    errorcode = solver.starteProcess(  executeBatch,
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
      _errMsg += "\r\n-> überprüfen sie ob alle Pfade u. Dateinamen richtig gesetzt wurden.";
      _errMsg += "\r\n-> überprüfen die die Eingabe auf Syntaxfehler.";
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
