package mischungsplaner.model.solverCaller;
import java.util.*;

/**
 * Implementiert den Parser für XA.EXE wobei hier nur Primallösungen
 * geparst werden können. Der Aufruf darf keine Optionsparameter enthalten,
 * da sonst das Ergebnis nicht richtig geparst wird.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public class XAParser extends Parser
{
  private final static  String UNBOUNDED  = "U N B O U N D E D   V A R I A B L E";
  private final static  String INFEASABLE = "N O    F E A S I B L E    S O L U T I O N";
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
  public XAParser()
  {
    this("");
  }

  /**
  * Konstruktor.
  * @param parseStr Der String der geparst werden soll.
  */
  public XAParser(String parseStr)
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
     int startPos;
     String parseStr = this.getParseString();

     // Unfeasable abfragen
     if (parseStr.indexOf(INFEASABLE)>=0)
     {
        _errMsg = "no feasable solution";
        return 1;
     }
     if (parseStr.indexOf(UNBOUNDED)>=0)
     {
        _errMsg = "variables are unbounded";
        return 2;
     }
     startPos = parseStr.indexOf("SOLUTION (Maximized):");
     if (startPos<0) startPos = parseStr.indexOf("SOLUTION (Minimized):");

     if (startPos<0)
     { // Optimum nicht gefunden ODER UNBOUND / INFEASABLE
        _errMsg = "->Eingabedatei hat falsches Format.\r\n";
        return -1;
     }
     else
     {  // Optimum auslesen...
        setPosition(startPos);
        if (!finished())
        {
          while (!isZiffer())
          {
            if (!finished()) _token = getNextElement();
            else break;
          }
          String[] s = new String[1];
          int errorcode = ermittleZahl(s);
          if (errorcode<0) return errorcode;
          // Komma mit Punkt vertauschen
          String optimumStr = s[0];
          optimumStr = optimumStr.replace(',','.');
          try
          {
            _optimum = Double.parseDouble(optimumStr);
          }
          catch (Exception e)
          {
            _errMsg = "Optimum ist kein gültiger Double-Wert!\r\n";
            _errMsg += COMMON_ERROR_MSG1;
            return -1;
          }
          // nach x<Zahl> Variablen suchen und Vektoren setzten..
          errorcode = macheAuswertung();
          if (errorcode<0) return errorcode;
        }
     }
    _errMsg = "Kein Fehler";
    return 0;
  }

//-------------------------------------------------------------------------
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
    if (_token==',')
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
      return -1;
    }

    doubleStr[0] = helpStr;
    _errMsg = "Kein Fehler";
    return 0;
  }
  //----------------------------------------------------------------------
  private int macheAuswertung()
  {
    String helpStr;
    double dwert;

    while (!finished())
    {
      helpStr = ""; _token = getElement();
      while (_token != 'x')
      {
        if (!finished()) _token = getNextElement();
        else break;
      }

      if (_token == ENDE_ZEICHEN) break;

      helpStr += _token; _token = getNextElement();
      while (isZiffer())
      {
        helpStr += _token;
        if (!finished()) _token = getNextElement();
        else break;
      }
      helpStr += "=";
      // weiter bis Ziffer auftaucht..
      while (!isZiffer())
      {
        if (!finished()) _token = getNextElement();
        else break;
      }
      // eine Zahl ?
      String[] wertStr = new String[1];
      int errorcode = ermittleZahl(wertStr);
      if (errorcode<0) return errorcode;
      // Komma mit Punkt vertauschen
      wertStr[0] = wertStr[0].replace(',','.');
      // Bisher kein Fehler -> Variablen - Wert Tupel eintragen..
      helpStr += wertStr[0];
      // Doppelt da?
      if (!_resultVec.contains(helpStr))  _resultVec.addElement(helpStr);
      else break;
    }
    _errMsg = "kein Fehler";
    return 0;

  }
} /*class*/