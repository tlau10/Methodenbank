package mischungsplaner.model.solverCaller;
import java.util.*;

/**
 * Implementiert den Parser für LP_SOLVE.EXE, wobei hier nur Primallösungen
 * geparst werden können. Der Aufruf darf keine Optionsparameter enthalten,
 * da sonst das Ergebnis nicht richtig geparst wird.
 *
 * Aufruf: lp_solve <in-file> out-file
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public class LPSolveParser extends Parser
{
  private final static String HEADER_SUCCESS    = "Value of objective function:";
  private final static String HEADER_INFEASABLE = "This problem is infeasible";
  private final static String HEADER_UNBOUND    = "This problem is unbounded";

  private final static  String COMMON_ERROR_MSG1 =
    "-> Struktur der Ausgabedatei ist inkorrekt bzw. korrumpiert.\r\n"+
    "\r\nHinweis: Vergleiche Fehlerdatei im Arbeitsverzeichnis.\r\n";

  private Vector _resultVec;
  private double _optimum;
  private char _token;
  private String _errMsg;

 /**
 * Default-Konstruktor.
 */
  public LPSolveParser()
  {
    this("");
  }

  /**
  * Konstruktor.
  * @param parseStr Der String der geparst werden soll.
  */
  public LPSolveParser(String parseStr)
  {
    super(parseStr);
    _resultVec = new Vector();
    _optimum = -1;
  }

  /**
  * Kam es zu einem Fehler, kann mit dieser Methode der Fehlertext ausgelesen werden.
  * @return Der Fehlertext
  */
  public String getErrorMsg() { return _errMsg; }

  /**
  * Gibt Optimumswert zurück. Voraussetzung dafür ist, dass eine Lösung
  * existiert, d.h. der Solver gestartet wurde und es zu keinem Fehler kam.
  * @return Optimum
  */
  public double getOptimum()
  {
    return _optimum;
  }

 /**
  * Ermittelt (Variable=Wert) Tupel vom Objekttyp String (Primallösungen).
  * Voraussetzung dafür ist, dass eine Lösung existiert, d.h. der Solver
  * gestartet wurde und es zu keinem Fehler kam.
  * @return Ergebnisvektor
  */
  public Vector getPrimalResults()
  {
     return _resultVec;
  }

 /**
  * Ermittelt (Variable=Wert) Tupel vom Objekttyp String (Dualergebnisse).
  * Voraussetzung dafür ist, dass eine Lösung existiert, d.h. der Solver
  * gestartet wurde und es zu keinem Fehler kam.
  * @return Ergebnisvektor
  */
  public Vector getDualResults()
  {
     return null;
  }

  /**
  * Parst die Ausgabedatei und ermittelt so die Ergebnisse.
  * @return Fehlernummer<br>
  * gleich  0  - kein Fehler<br>
  * kleiner 0  - Fehler! Fehlertext kann ermittelt werden.<br>
  * größer  0 <br>
  * Falls Fehlercode 1 -> infeasable problem<br>
  * Falls Fehlercode 2 -> unbounded solution<br>
  *
  * @see IErrorMessage#getErrorMsg()
  */
  public int parse()
  {
    if (!finished())
    {
      _errMsg = this.getErrorMsg();
      return setztErgebnisVektoren();
    }
    else
    {
      _errMsg  = "Ausgabedatei ist leer!\r\n";
      _errMsg += "Vermutlich enthält die Eingabedatei Syntaxfehler,";
      _errMsg += "so dass keine Ausgabedatei erzeugt werden konnte.\r\n";
      _errMsg += "->Überprüfen Sie die Eingabesyntax.\r\n";
      _errMsg += "Weitere Fehlerquellen:\r\n";
      _errMsg += "-> Pfad zum Solver falsch gesetzt.\r\n";
      _errMsg += "-> Arbeitsverzeichnis nicht vorhanden.\r\n";
      _errMsg += "-> Schreib- u. Leserechte für das Arbeitsverzeichnis fehlen\r\n";
      return -1;
    }
  }

  // ---------------------------------------------------------------------
  // Private Parser Routinen
  // ---------------------------------------------------------------------

  private int setztErgebnisVektoren()
  {
    // Prüfen ob Header da...
    String[] helpStr = new String[1]; helpStr[0] = "";

    _token = getElement();
    while (!isZiffer() && !isMinus() && !isPlus())
    {
      helpStr[0] += _token;
      if (!finished()) _token = getNextElement();
      else break;
    }

    int success     = helpStr[0].indexOf(HEADER_SUCCESS);
    int infeasable  = helpStr[0].indexOf(HEADER_INFEASABLE);
    int unbound     = helpStr[0].indexOf(HEADER_UNBOUND);

    // Header nicht gefunden..
    if ( success<0 && infeasable<0 && unbound<0)
    {
      _errMsg  = "Ausgabedatei hat falschen Header!\r\n";
      _errMsg += "-> Falscher Solver verwendet?\r\n";
      _errMsg += "-> Falsche Solverversion verwendet?\r\n";
      return -2;
    }
    // Problemstellung unbound oder infeasable(aber kein Fehler im LP-Ansatz)!
    if (infeasable>=0)
    {
      _errMsg = "infeasable problem";
      return 1;
    }
    if (unbound>=0)
    {
      _errMsg = "unbounded solution";
      return 2;
    }

    // Prüfen ob Optimum da..
    int errorcode = ermittleZahl(helpStr);
    try
    {
      _optimum = Double.parseDouble(helpStr[0]);
    }
    catch (Exception e)
    {
      _errMsg = "Optimum ist kein gültiger Double-Wert!\r\n";
      _errMsg += COMMON_ERROR_MSG1;
      return -3;
    }
    return macheAuswertung();
  }

  private int ermittleZahl(String[] doubleStr)
  {
    String helpStr = "";

    // Ein "-" Zeichen da ?
    if (isMinus()||isPlus()||isZiffer())
    {
      helpStr += _token;
      if (!finished()) _token = getNextElement();
    }
    while (isZiffer())
    {
      helpStr += _token;
      if (!finished()) _token = getNextElement();
      else break;
    }

    // Ist es eine Kommazahl?
    if (_token=='.')
    {
      helpStr += _token;
      if (!finished()) _token = getNextElement();
      while (isZiffer())
      {
        helpStr += _token;
        if (!finished()) _token = getNextElement();
        else break;
      }
    }

    if ( (helpStr == "") ||
         (helpStr.compareTo("+")==0) ||
         (helpStr.compareTo("-")==0))
    {
      doubleStr[0] = "";
      _errMsg = "Kein gültiger Double oder Integer Wert!\r\n";
      _errMsg += COMMON_ERROR_MSG1;
      return -3;
    }

    doubleStr[0] = helpStr;
    _errMsg = "Kein Fehler";
    return 0;
  }

  private int macheAuswertung()
  {
    String variableStr;
    String[] ergebnisStr = new String[1];

    while (!finished())
    {
      variableStr = "";

      // Ist eine Variable da ?
      while (_token==CR || _token==LF || _token==SPACE || _token == TAB)
      {
        if (!finished()) _token = getNextElement();
        else break;
      }

      if (!finished() && !isBuchstabe())
      {
        _errMsg = "Falscher Variablenbenennung!\r\n";
        _errMsg = "-> Syntax:   <Variable>::=[Buchstabe]{Ziffer}\r\n";
        _errMsg += COMMON_ERROR_MSG1;
        return -1;
      } else
      while (isBuchstabe())
      {
        variableStr += _token;
        if (!finished())_token = getNextElement();
        else break;
      }
      while (isZiffer())
      {
        variableStr += _token;
        if (!finished()) _token = getNextElement();
        else break;
      }

      // Ist eine Zahl da ?
      while (_token==SPACE || _token==TAB)
      {
        if (!finished()) _token = getNextElement();
        else break;
      }

      if (finished())
      {
        _errMsg = "kein Fehler";
        return 0; // Fertig!!!
      }

      int errorcode = ermittleZahl(ergebnisStr);
      if (errorcode<0) return errorcode;

      //Wenn Variable und Ergebniss vorhanden -> Füge Ergebnistupel in Vector ein!
      if (ergebnisStr[0]!="" && variableStr!="")
      {
        _resultVec.add(variableStr+"="+ergebnisStr[0]);
      }
      else
      {
        if (ergebnisStr[0] == "")
        {
          _errMsg = "Kein gültiges Ergebnis!\r\n";
          _errMsg += COMMON_ERROR_MSG1;
          return -4;
        }
        if (variableStr == "")
        {
          _errMsg = "Keine gültige Variable!\r\n";
          _errMsg += "-> Syntax:  <Variable>::=[Buchstabe]{Ziffer}\r\n";
          _errMsg += COMMON_ERROR_MSG1;
          return -5;
        }
      }
    }
    return 0;
  }
} /*class*/