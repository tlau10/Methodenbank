package zuordnungsplanung;

import javax.swing.UIManager;


/**
 * <p>Title: Zuordnungsplanung V 1.0</p>
 * <p>Description: This is the main class, which is starting the application.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Patrick Badent
 * @version 1.0
 */

public class Application
{

  //Constructor
  public Application()
  {
  }

  //Main method
  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e)
    {
      ExceptionDialog.showExceptionDialog(
                      "Fehler  beim Programmstart.","Es konnten nicht alle Dateien geladen werden." + e.getMessage(), e);
    }
    java.io.File tempDirectory = new java.io.File(Variables.solverDirectory);
    if (!tempDirectory.exists())
    {
      try
      {
        tempDirectory.mkdir();
      }
      catch (Exception ex)
      {
        ExceptionDialog.showExceptionDialog("Fehler beim Programmstart. Es wird Schreibzugriff auf das Verzeichnis "+ Variables.solverDirectory+ "benötigt" , ex.getMessage(), ex);
      }
    }
    ApplicationControll applicationControll = new ApplicationControll();
  }
}
