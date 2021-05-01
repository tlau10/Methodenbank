package mischungsplaner.model.solverCaller;

import java.util.*;

/**
 * Die Klasse bietet grundlegende Operationen die zum parsen verwendet werden können.
 * Zwing zur implementierung der abstrakten Methoden:
 *
 *    public abstract Vector getPrimalResults();
 *    public abstract Vector getDualResults();
 *    public abstract double getOptimum();
 *    public abstract int parse();
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public abstract class Parser implements IErrorMessage
{
  public final static char ENDE_ZEICHEN = '#';

  public final static char SPACE= ' ';
  public final static char CR= '\r';
  public final static char LF= '\n';
  public final static char TAB= '\t';

  private String _parseString;
  private int _position;

 /**
 * Default-Konstruktor.
 */
  public Parser() { this(""); }

 /**
  * Ermittelt (Variable=Wert) Tupel vom Objekttyp String (Primallösungen).
  * Voraussetzung dafür ist, dass eine Lösung existiert, d.h. der Solver
  * gestartet wurde und es zu keinem Fehler kam.
  * @return Ergebnisvektor
  */
  public abstract Vector getPrimalResults();

  /**
  * Ermittelt (Variablem=Wert) Tupel vom Objekttyp String (Dualergebnisse).
  * Voraussetzung dafür ist, dass eine Lösung existiert, d.h. der Solver
  * gestartet wurde und es zu keinem Fehler kam.
  * @return Ergebnisvektor
  */
  public abstract Vector getDualResults();

  /**
  * Gibt Optimumswert zurück. Voraussetzung dafür ist, dass eine Lösung
  * existiert, d.h. der Solver gestartet wurde und es zu keinem Fehler kam.
  * @return Optimum
  */
  public abstract double getOptimum();

  /**
  * Parst die Ausgabedatei und ermittelt so die Ergebnisse.
  * @return Fehlernummer<br>
  * gleich 0  - kein Fehler<br>
  * kleiner 0 - Fehler! Fehlertext kann ermittelt werden.<br>
  * größer 0 <br>
  * Falls Fehlercode 1 -> infeasable problem<br>
  * Falls Fehlercode 2 -> unbounded solution<br>
  *
  * @see IErrorMessage#getErrorMsg()
  */
  public abstract int parse();

  /**
  * Konstruktor.
  * @param parseStr Der String der geparst werden soll.
  */
  public Parser(String parseStr)
  {
    _parseString = parseStr + ENDE_ZEICHEN;
    _position = 0;
  }

  /**
  * Prüft ob aktuelles zeichen eine Ziffer ist.
  * @return true falls Ziffer ansonsten false.
  */
  public boolean isZiffer()
  {
    int von = (int) '0';
    int bis = (int) '9';

    int i = (int) _parseString.charAt(_position);

    if ( (i>=von)&&(i<=bis)) return true;
    else
      return false;
  }

  /**
  * Prüft ob aktuelles zeichen eine Buchstabe ist.
  * @return true falls Buchstabe ansonsten false.
  */
  public boolean isBuchstabe()
  {
    int vonGross = (int) 'A';
    int bisGross = (int) 'Z';
    int vonKlein = (int) 'a';
    int bisKlein = (int) 'z';

    int i = (int) _parseString.charAt(_position);

    if ( ((i>=vonGross)&&(i<=bisGross)) ||
         ((i>=vonKlein)&&(i<=bisKlein)) )
      return true;
    else
      return false;
  }

  /**
  * Prüft ob aktuelles zeichen ein Additionszeichen(+) ist.
  * @return true falls Additionszeichen ansonsten false.
  */
  public boolean isPlus()
  {
    return (_parseString.charAt(_position) == '+');
  }

  /**
  * Prüft ob aktuelles zeichen ein Subtraktionszeichen(-) ist.
  * @return true falls Subtraktionszeichen ansonsten false.
  */
  public boolean isMinus()
  {
    return (_parseString.charAt(_position) == '-');
  }

  /**
  * Holt das nächste Zeichen aus dem Puffer und erhöht den Pufferzeiger.
  * @return Zeichen
  */
  public char getNextElement()
  {
    return _parseString.charAt(++_position);
  }
  /**
  * Holt das nächste Zeichen aus dem Puffer.
  * @return Zeichen
  */
  public char getElement()
  {
    return _parseString.charAt(_position);
  }

  /**
  * Prüft ob das Ende des Parsestring erreicht ist.
  * @return True falls das Ende des Parsestrings erreciht ist, ansonsten false.
  */
  public boolean finished()
  {
    if ( (_position == _parseString.length()-1) ||
         (_parseString.charAt(_position) == ENDE_ZEICHEN) )
      return true;
    else
      return false;

  }

  /**
  * Setzt den String der geparst werden soll.
  */
  protected void setParseString(String parseStr)
  {
    _parseString = parseStr + ENDE_ZEICHEN;
    _position = 0;
  }

  /**
  * Liest den den String der geparst werden soll.
  */
  protected String getParseString()
  {
    return _parseString;
  }

  /**
  * Setzt Lesezeiger auf eine Position innerhalb des Parse-Strings.
  * @param pos Position innerhalb des Parse-Strings.
  */
  protected void setPosition(int pos)
  {
    if (pos > _parseString.length())
      _position = _parseString.length()-1;
    else
      _position = pos;
  }

}