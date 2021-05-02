package SolverCaller;
import java.io.*;

/**
 * Die Klasse schreibt einen String in eine Datei.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public class FileWriter extends Files
{
  private FileOutputStream _fileOutStream;
  private String _outFileBuffer;
  private String _errMsg;

  /**
  * Konstruktor.
  */
  public FileWriter()
  {
    this("");
  }

  /**
  * Konstruktor.
  * @param pfad Pfad zur Datei (mit Namensangabe).
  */
   public FileWriter(String pfad)
  {
    super(pfad);
    _outFileBuffer = "";
  }

  /**
  * Kam es zu einem Fehler, kann mit dieser Methode der Fehlertext ausgelesen werden.
  * @return Der Fehlertext
  */
  public String getErrorMsg() { return _errMsg; }

  /**
  * Schreibt den Inhalt des Stringpuffers in die Datei.
  * @return Fehlercode
  * gleich  0  - kein Fehler<br>
  * kleiner 0  - Fehler aufgetreten<br>
  *
  * @see IErrorMessage#getErrorMsg()
  */
  public int write()
  {
    try
    {
      String temp = getFilePath();
      _fileOutStream = new FileOutputStream(getFilePath());
      PrintWriter out = new PrintWriter(_fileOutStream);
      out.print(_outFileBuffer);
      out.close();
    } catch (Exception e)
    {
      _errMsg = "Konnte in Datei \"" + getFilePath() + "\" nicht schreiben";
      return -1;
    }
    _errMsg = "Kein Fehler";
    return 0;
  }

  /**
  * Setzt den Inhalt des Datenpuffers.
  * @param data Damit wird der Puffer beschrieben.
  */
  public void setData(String data)
  {
    _outFileBuffer = data;
  }

  /**
  * Fügt Daten ans Ende des Datenpuffers ein.
  * @param data Diese Daten werden eingefügt.
  */
  public void addData(String data)
  {
    _outFileBuffer += data;
  }

  /**
  * Ermittelt den Inhalt des gesetzten Datenpuffers.
  * @return Datenpuffer im Stringformat.
  */
  public String getData()
  {
    return _outFileBuffer;
  }

}