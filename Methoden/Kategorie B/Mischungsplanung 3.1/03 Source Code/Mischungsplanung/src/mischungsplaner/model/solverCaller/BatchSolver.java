package mischungsplaner.model.solverCaller;

import java.io.*;
import java.util.*;

/**
 * Abstrakte Klasse. Bietet grundlegende Methoden an, um Pfade auf
 * Informationsdateien zu setzten (Eingabe/Ausgabe), Datein in die Eingabedatei
 * zu schreiben und um die Solver Ergebnisse auszulesen.
 *
 * Zwingt zur Implementation
 * der Solver - Methode:
 *
 *     public  int solve();
 *
 * Copyright: Copyright (c) 2001
 * Organisation:  FH - Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public abstract class BatchSolver implements IErrorMessage
{
  // Private Variablen
  private FileWriter     _lpInputFile;
  private String _outFileName;
  private String _inFileName;
  private String _execName;
  private String _execPath;
  private String _workingDir;
  private String _solverName;
  private String _errMsg;
  private Parser _derParser;

  /**
   * Konstruktor.
   * @param inFileName Name der Eingabedatei(enthält den LP-Ansatz).
   * @param outFileName Name der Ausgabedatei(enthält bei Erfolg das Ergebnis).
   * @param arbeitsVerzeichnis Hier werden Ein- u. Ausgabedateien erzeugt und
   * Fehler-Logdateien.
   * @param solverPfad Verzeichnis in dem der Solver sich befindet.
   * @param solverName Name des Solvers(ausführbare Datei).
   * @param parser Parser der die Ausgabedatei auswertet.
   */
  public BatchSolver( String inFileName,
                      String outFileName,
                      String arbeitsVerzeichnis,
                      String solverPfad,
                      String solverName,
                      Parser parser )
  {
    _workingDir  = arbeitsVerzeichnis;
    _execPath    = solverPfad;
    _solverName  = solverName;
    _inFileName  = inFileName;
    _outFileName = outFileName;
    _derParser = parser;
    _lpInputFile = new FileWriter(_inFileName);
  }

  /**
  * Stößt die berechnung des Linearen Gleichungssystems an.
  * @return Fehlercode
  * @see IErrorMessage#getErrorMsg()
  */
  public abstract int solve();

  /**
  * Ermittelt das Primalergebnis.
  * @return Zwei dimensionales Vektor-Feld. Der erste Vektor beinhaltet
  * die Variablennamen. Der zweite Vektor beinhaltet die zugehörigen Werte.
  */
  public Vector[] getPrimalResults()
  { Vector[] resultTupel = new Vector[2];
    // Variablen / Lösungen Tupel auslesen...
    if (getParser() != null)
    {
      resultTupel[0] = new Vector();
      resultTupel[1] = new Vector();

      Vector result = getParser().getPrimalResults();

      int firstPos = result.indexOf(result.firstElement());
      int lastPos  = result.indexOf(result.lastElement());

      for (int i=firstPos; i<=lastPos; i++)
      {
        Object obj = result.get(i);
        if (obj instanceof String)
        {
          String s = (String) obj;
          // Wie ermittle ich die Variable?
          String variable = s.substring(0, s.indexOf("="));
          resultTupel[0].add(variable);

          // Wie mache ich Zahlen draus ?
          String zahlStr = s.substring(s.indexOf("=")+1, s.length());
          Double d = Double.valueOf(zahlStr);
          resultTupel[1].add(d);
        } /* if */
      } /* for */
    } /* if */
    else
    {
      resultTupel[0] = null;
      resultTupel[1] = null;
      _errMsg = "Es liegen keine Ergebnisse vor";
      return resultTupel;
    }

    _errMsg = "Kein Fehler";
    return resultTupel;
  }

  /**
  * Ermittelt das Dualergebnis.
  * @return Zwei dimensionales Vektor-Feld. Der erste Vektor beinhaltet
  * die Variablennamen. Der zweite Vektor beinhaltet die zugehörigen Werte.
  */
  public Vector[] getDualResults()
  {
    Vector[] resultTupel = new Vector[2];
    resultTupel[0] = null;
    resultTupel[1] = null;
    _errMsg ="Methode nicht implementiert";
    return null;
  }

  /**
  * Ermittelt das Optimum, falls vorhanden.
  * @return Optimum
  */
  public double getOptimum()
  { if (getParser() != null)
    {
      _errMsg = getParser().getErrorMsg();
      return getParser().getOptimum();
    }
    else
    {
      _errMsg = "Es liegen keine Ergebnisse vor";
      return -1;
    }
  }

  /**
  * Fügt eine neue LP Zeile in den Eingabepuffer ein. Um den Puffer in die
  * Eingabedatei zu schreiben muss Methode writeLPModel() verwendet werden.
  * @param lpzeile Eine LP-Zeile kann z.B. einen Restriktion oder Zielfunktion
  * sein.
  */
  public void addLPZeile(String lpzeile)
  {
    _lpInputFile.addData(lpzeile+"\r\n");
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
  public abstract void addLPModel(String lpmodel[], int anzVar);
  
  /**
  * Fügt ein solverspezifisches LP Model in den Eingabepuffer ein. Um den Puffer in die
  * Eingabedatei zu schreiben muss Methode writeLPModel() verwendet werden.
  * Diese Methode fuegt das Model wie es uebergeben wurde Zeile fuer Zeile ein. 
  * @param lpmodel Das solverspezifisches LPModel
  * sein.
  */
  public void addLPModel(String specificLPModel[])
  {
      for (int i=0; i<specificLPModel.length;i++) addLPZeile(specificLPModel[i]);
  }    
 
  /**
  * Schreibt den Eingabepuffer in die Eingabedatei.
  * @return Fehlernummer
  * gleich  0  - kein Fehler<br>
  * kleiner 0  - Fehler! Fehlertext kann ermittelt werden.<br>
  * größer  0  - Falls Fehlercode 1 -> infeasable problem<br>
  *              Falls Fehlercode 2 -> unbounded solution<br>
  *
  * @see IErrorMessage#getErrorMsg()
  */
  public int writeLPModel()
  {
    // Ältere Ausgagedatei löschen falls vorhanden..
    File f = new File(getWorkingDir()+"\\"+getOutFileName());
    f.delete();
    // Eingabedatei erzeugen..
    _lpInputFile.setFilePath(getWorkingDir()+"\\"+getInFileName());
    int errorcode = _lpInputFile.write();
    if (errorcode<0) _errMsg = _lpInputFile.getErrorMsg();
    return errorcode;
  }

  /**
  * Setzt den Parser der die Ausgabedatei auswertet.
  * @param p Parser-Objekt
  */
  public void setParser(Parser p)           { _derParser = p; }

  /**
  * Setzt das Arbeitswerzeichnis. Hier werden diverse Log-Files abgelegt.
  * Außerdem werden die Ein- und Ausgabedateien hier gespeichert.
  * @param workDir Pfad zum Arbeitsverzeichnis.
  */
  public void setWorkingDir(String workDir) { _workingDir = workDir; }

  /**
  * Setzt den Pfad zum Solver.
  * @param execPath Pfad zum Solver
  */
  public void setExecPath(String execPath)  { _execPath = execPath;  }

  /**
  * Setzt den Name des Solver (executable file)
  * @param name Name des Solvers
  */
  public void setSolverName(String name)    { _solverName = name; }

  /**
  * Setzt den Name der Eingabedatei
  * @param name Name der Eingabedatei
  */
  public void setInFileName(String name)    { _inFileName = name; }

  /**
  * Setzt den Name der Ausgabedatei
  * @param name Name der Ausgabedatei
  */
  public void setOutFileName(String name)   { _outFileName = name; }

  /**
  * Ermittelt den Namen den Ausgabedatei
  * @return Name der Ausgabedatei
  */
  public String getOutFileName()            { return _outFileName; }

  /**
  * Ermittelt den Namen den Eingabedatei
  * @return Name der Eingabedatei
  */
  public String getInFileName()             { return _inFileName; }

  /**
  * Ermittelt gesetzten Pfad zum Arbeitsverzeichnis
  * @return Pfad zum Arbeitsverzeichnis
  */
  public String getWorkingDir()             { return _workingDir; }

  /**
  * Ermittelt gesetzten Pfad zum Solver.
  * @return Pfad zum Solver
  */
  public String getExecPath()               { return _execPath; }

  /**
  * Ermittelt Name des Solvers
  * @return Name des Solvers
  */
  public String getSolverName()             { return _solverName; }

  /**
  * Ermittelt den gesetzten Parser.
  * @return Objekt eines Untertyps der abstrakten Klasse Parser
  */
  public Parser getParser()                 { return _derParser; }

}