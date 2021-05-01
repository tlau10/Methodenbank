
package zuordnungsplanung;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JOptionPane;


/**
 * <p>Title: Zuordnungsplanung V 1.0</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Patrick Badent
 * @version 1.0
 */



public class Variables
{
  //public static final String tool
  public static final String toolName = "Zuordnungsplanung";
  public static final String toolVersion = "V 1.0";
  public static final String toolCopyright = "(C) Copyright 2004";
  public static final String license = "FH-Konstanz";
  public static final String toolAuthors[] = {"Patrick Badent", "Elmar Erhart", "Bettina Pfeiffer"};

  public static final int dataRowsMax = 18;
  public static final int dataColumnsMax = 18;

  public static final int frameMainSizeX = 800;
  public static final int frameMainSizeY = 600;

  public static final int startWithRows = 3;
  public static final int startWithColumns = 3;

  public static final String solverDirectory = "c:\\temp";
  public static final String copy = "copy ";

  public static final String fileExtension = "csv";
  public static final int statusBarShowTextTime = 5000; //ms
  public static final String helpFile = "helpfile.htm";

  //ERROR_MESSAGE(x)  INFORMATION_MESSAGE(i) WARNING_MESSAGE(!)  QUESTION_MESSAGE(?)  PLAIN_MESSAGE(ohne)
  public static void jOptionPane_IntergerWarning(String value)
  {
    JOptionPane.showMessageDialog(null,
         "'" + value.toString() + "' ist keine Integer Zahl.\nBitte nur Integer Zahlen eingeben!",
                                  "Fehler!", JOptionPane.WARNING_MESSAGE);
  }

  public static void jOptionPane_SolverWaehlen()
  {
  JOptionPane.showMessageDialog(null,"Sie haben kein Sovler gewählt. Zuerst Solver wählen!","Solver nicht gewählt", JOptionPane.WARNING_MESSAGE);
  }


  public static String jOptionPane_AssistentZahl()
  {
    return JOptionPane.showInputDialog(
         "Bitte Zahl eingeben, mit der die leeren" + "\n" +
         "Werte vervollständigt werden sollen:");
  }


  public static boolean jOptionPane_AssistentStarten()
  {
    Object[] options =
         {
         "Ja, Assistent starten.", "Nein, Daten selbst eingeben."};
    int wahl = JOptionPane.showOptionDialog(null,
         "Die Daten wurden nicht vollständig eingegeben." + "\n" +
         "Soll der Assistent zur Vervollständigung der Daten gestartet werden?",
                                            "Daten nicht vollständig",
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.WARNING_MESSAGE, null,
                                            options, options[0]);
    if (wahl == 0)//if(OK-gedrückt)
      return true;
    else//else(Abrechen gedrückt)
      return false;
  }


  public static boolean jOptionPane_DateiUeberschreiben()
  {
    Object[] options =
         {
         "Ja, überschreiben.", "Nein, abbrechen."};
    int wahl = JOptionPane.showOptionDialog(null,
                                            "Diese Datei exisiert schon." +
                                            "\n" +
         "Soll die Datei überschrieben werden?",
         "Speichern unter",
         JOptionPane.DEFAULT_OPTION,
         JOptionPane.WARNING_MESSAGE, null,
         options, options[0]);
    if (wahl == 0) //if(ja-gedrückt)
      return true;
    else //else(Abrechen gedrückt)
      return false;
  }

  public static boolean jOptionPane_DateiSpeichern()
  {
    Object[] options ={"Ja.", "Nein."};
    int wahl = JOptionPane.showOptionDialog(null,
                                            "Soll die Datei vor dem Beenden gespeichert werden?",
         "Speichern",
         JOptionPane.DEFAULT_OPTION,
         JOptionPane.WARNING_MESSAGE, null,
         options, options[0]);
    if (wahl == 0) //if(ja-gedrückt)
      return true;
    else //else(Abrechen gedrückt)
      return false;
  }


  private static String readFile(String filename)
  {
    String str = "";
    File myFile = new File(filename);
    byte buffer[] = new byte[(int)myFile.length()];
    FileInputStream inStream = null;

    try
    {
      inStream = new FileInputStream(myFile);
      int len = inStream.read( buffer, 0, (int)myFile.length() );
      str = new String( buffer, 0, len );
    }
    catch ( Exception e )
    {
        ExceptionDialog.showExceptionDialog("Fehler beim lesen der Hilfe-Datei.",e.getMessage(),e);
    }
    finally
    {
      try
      {
        if ( inStream != null )
          inStream.close();
      }
      catch (Exception e)
      {
        ExceptionDialog.showExceptionDialog("Fehler beim lesen der Hilfe-Datei.",e.getMessage(),e);
      }
    }
    return(str);
  }
}