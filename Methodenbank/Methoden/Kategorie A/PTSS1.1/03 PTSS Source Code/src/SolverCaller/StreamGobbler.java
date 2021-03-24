package SolverCaller;
import java.util.*;
import java.io.*;

/**
 * Liest die Eingabeströme von externen Sub-Prozessen aus. Ermöglicht dadurch
 * das Erstellen von Log-Dateien.
 *
 * Copyright:     Copyright (c) 2001
 * Organisation:  FH-Konstanz
 * @author Helmut Lindinger
 * @version 1.0
 */
public class StreamGobbler extends Thread implements IErrorMessage
{
    private InputStream  _inStream;
    private String       _type;
    private OutputStream _outStream;
    private PrintWriter  _printWriter;
    private String       _ausgabe;
    private String       _errMsg;

    /**
    * Kam es zu einem Fehler, kann mit dieser Methode der Fehlertext ausgelesen werden.
    * @return Fehlertext
    */
    public String getErrorMsg() { return _errMsg; }

    /**
    * Konstruktor.
    * @param is InputStream des externen Programms. Die Daten des Eingabestroms
    * werden auf dem Bildschirm ausgegeben (stdout).
    * @param type String der vor jeder Ausgabe steht. Dadurch lassen sich
    * Ausgaben verschiedener Eingabeströme unterscheiden.
    */
    public StreamGobbler(InputStream is, String type)
    {
        this(is, type, null);
    }

    /**
    * Konstruktor.
    * @param is InputStream des externen Programms. Die Daten des Eingabestroms
    * werden auf dem Bildschirm ausgegeben (stdout).
    * @param type String der vor jeder Ausgabe steht. Dadurch lassen sich
    * Ausgaben verschiedener Eingabeströme unterscheiden.
    * @param redirect Kann verwendet werden um den InputSream des externen
    * Programms umzuleiten; z.B. in einen FileOutputStream um Log-Dateien zu
    * erzeugen.
    */
    public StreamGobbler(InputStream is, String type, OutputStream redirect)
    {
        _inStream  = is;
        _type      = type;
        _outStream = redirect;
        _printWriter = null;
        _ausgabe = "";
        _errMsg  = "";
        if (_outStream != null) _printWriter = new PrintWriter(_outStream);
    }

    /**
    * Framework-Methode, beinhaltet den StreamGobbler-Thread.
    */
    public void run()
    {
        InputStreamReader isReader = new InputStreamReader(_inStream);
        BufferedReader bufReader = new BufferedReader(isReader);

        String line="";
//        System.out.println("Gobbler "+ this.getName() +" gestartet");
//        Thread myThread; // mje
  //      myThread = Thread.currentThread(); //mje
        try
        {
            while ( (line = bufReader.readLine()) != null)
            {
         //     while(myThread == this.currentThread()){ // mje
                if (_printWriter != null)
                {
                  _printWriter.println(_type + ">" + line);
                  _printWriter.flush();
                }
                else System.out.println(_type + ">" + line);
        //      }
            }
        } catch (Exception e)
        {
          _errMsg = "Fehler bei Gobbler-Thread!\r\n";
        }
//        System.out.println("Gobbler "+ this.getName() +" beendet");
    } /* run */

} /* class */