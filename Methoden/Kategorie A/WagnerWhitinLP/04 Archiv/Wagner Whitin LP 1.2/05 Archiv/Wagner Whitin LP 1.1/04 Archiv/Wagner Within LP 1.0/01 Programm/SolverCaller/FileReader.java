package SolverCaller;
import java.io.*;

/**
 * Die Klasse ermöglicht das Lesen von Daten aus einer Datei. Die Daten werden
 * im String-Format abgelegt.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public class FileReader extends Files
{
  private String _inFileBuffer;
  private String _errMsg;

  /**
  * Konstruktor.
  * @param pfad Pfad zur Datei (mit Namensangabe).
  */
  public FileReader(String pfad)
  {
    super(pfad);
    _inFileBuffer = "";
    _errMsg = "";
  }

  /**
  * Kam es zu einem Fehler, kann mit dieser Methode der Fehlertext ausgelesen werden.
  * @return Der Fehlertext
  */
  public String getErrorMsg()  { return _errMsg;  }

  /**
  * Liest die Datei in einen Stringpuffer ein.
  * @return Fehlernummer <br>
  * gleich  0 - kein Fehler<br>
  * kleiner 0 - Fehler<br>
  *
  * @see IErrorMessage#getErrorMsg()
  */
  public int read()
  {
    final int READ_BUF_SIZE = 5;
    int gelesen = 0;
    char[] cbuf = new char[READ_BUF_SIZE];

    try
    {
      java.io.FileReader fr = new java.io.FileReader(getFilePath());

      while (fr.ready())
      {
        gelesen = fr.read(cbuf);
        _inFileBuffer += String.valueOf(cbuf,0,gelesen);
      }
      fr.close();
    } catch (Exception e)
    {
      _errMsg = "Konnte Datei \""+ getFilePath() +"\" nicht lesen";
      return -1;
    }
    _errMsg = "Keine Fehler";
    return 0;
  }

  /**
  * Ermittelt den Inhalt des eingelesenen Datenpuffers.
  * @return Datenpuffer im Stringformat.
  */
  public String getData()
  {
    return _inFileBuffer;
  }

}