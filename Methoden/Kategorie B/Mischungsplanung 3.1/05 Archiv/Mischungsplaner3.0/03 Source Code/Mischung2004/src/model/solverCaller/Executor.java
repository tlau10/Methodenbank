package model.solverCaller;

import java.util.*;
import java.io.*;

/**
 * Ist zuständig für das Ausführen von externen Programmen.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public class Executor implements IErrorMessage
{
  private String _errMsg;
  /**
   * Default-Konstruktor
   */
  public Executor() {}

  /**
  * Kam es zu einem Fehler, kann mit dieser Methode der Fehlertext ausgelesen werden.
  * @return Fehlertext
  */
  public String getErrorMsg() { return _errMsg; }
  public void setErrorMsg(String errMsg) { _errMsg = errMsg;  }

  /**
  * Startet ein externes Programm in einem eigenen Thread. Dabei können
  * ErrorStream und OutputStream ausgegeben oder in eine Log-Datei
  * geschrieben werden.
  * @param execPath Pfad zum externen Programm.
  * @param errFile Name der Fehler-Log-Datei. Fall hier null angegeben ist,
  * werden potentielle Warnungen und Fehlerströme des externen Programms an stdout
  * (Bildschirmausgabe) geschickt.
  * @param outFile Name der Ausgabe-Log-Datei. Fall hier null angegeben ist,
  * wird der Ausgabestrom des externen Programms an stdout (Bildschirmausgabe)
  * geschickt.
  * @return Fehlernummer
  * gleich  0  :  kein Fehler<br>
  * kleiner 0  :  Fehler. Fehlertext kann ermittelt werden.<br>
  * größer  0<br>
  * Falls Code 1 -> infeasable problem<br>
  * Falls Code 2 -> unbounded solution<br>
  *
  * @see IErrorMessage#getErrorMsg()
  */
  public int starteProcess(String execPath, String errFile, String outFile)
  {
    FileOutputStream fileOutStream;
    FileOutputStream fileErrStream;
    boolean redirectOn;

    // Pfad zum Solver MUSS da sein!
    _errMsg="Pfadangabe zum Solver ist leer!";
    if (execPath.length() <= 0) return -1;

    // Wenn errorStream oder outStream null sind Ausgabeumleitung deaktivieren!
    if (errFile == null || outFile == null)
      redirectOn = false;
    else
      redirectOn = true;

    try
    {
      if (!redirectOn)
      { // Keine Log-dateien erzeugen -> Ausgabe erfolgt auf dem Bildschirm!
        fileOutStream = null;
        fileErrStream = null;
      } else
      { // Ja, Log-Dateien erzeugen -> Stream-Redirection !
        fileOutStream = new FileOutputStream(outFile);
        fileErrStream = new FileOutputStream(errFile);
      }

      Runtime rt = Runtime.getRuntime();
      Process proc = rt.exec(execPath);
      // any error message?
      StreamGobbler errorGobbler = new
      StreamGobbler(proc.getErrorStream(), "ERR",fileErrStream);
      errorGobbler.setName("ErrorGobbler");

      // any output?
      StreamGobbler outputGobbler = new
      StreamGobbler(proc.getInputStream(), "OUT", fileOutStream);
      outputGobbler.setName("OutputGobbler");

      // Wichtig (!)
      // Threads müssen beendet sein, erst dann ist das File Handle
      // zu schließen.(Sonst wie evt. nicht alles geschrieben ;-)
      // Die Thread sollten möglichts nacheinander abgearbeitet werden,
      // wenn auch Ausgabe and stdout geschickt werden sollen. Ansonsten
      // vermischen sich die Ausgabezeilen, was beim Schreiben in Dateien
      // wegen der verschiedenen Filehandles nicht zutrifft ;-))
      //
      // Bemerkung (!)
      //
      // Bei Verwendung von JBuilder und anderen Entwicklerumgebungen,
      // kann es sein, das das Programm nicht endet bzw. in der
      // while(outputGobbler.isAlive()) - Schleife festhängt, so dass
      // die restlichen Aktionen nicht ausgeführt werden :-(
      // (bei manuellem Starten funktioniert (zum Glück) das Programm
      // wunschgemäß (Beim Arbeiten mit JBuilder usw. diese Zeile aus-
      // kommentieren und stattdessen -> join() - Methode verwenden).
      //
      // Alternative mit join - Methode statt While(..);
      //
      // Warte max XXX ms, dann mache weiter mit Programmablauf!
      // (arbeitet sowohl mit JBuilder als auch auf Kommandoebene,
      // Nachteilig wenn die OutputStream extrem langsam sind, könnten nicht
      // alle Meldungen abgefangen werden. Hat aber denn Vorteil das das
      // Programm in endlicher Zeit terminiert :->

      // kick them off
      errorGobbler.start();
      //while(errorGobbler.isAlive());  // Warten bis Thread-Ende(gefährlich!)
      errorGobbler.join(3000);        // max. 4 Sek warten, dann aber weiter..

      outputGobbler.start();
      //while(outputGobbler.isAlive());  // Warten bis Thread-Ende(gefährlich!)
      outputGobbler.join(3000);    // max. 3 Sek. warten, dann ist gut..

      // any error???
      int exitVal = proc.waitFor();
      System.out.println("runtime process exitvalue: " + exitVal);

      if (fileOutStream != null) fileOutStream.close();
      if (fileErrStream != null) fileErrStream.close();

    } catch (Exception e)
    {
      _errMsg="Konnte externes Programm nicht ausgeführen!\r\n";
      return -1000;
    }

    _errMsg="Kein Fehler";
    return 0;
  }

} /*class */
