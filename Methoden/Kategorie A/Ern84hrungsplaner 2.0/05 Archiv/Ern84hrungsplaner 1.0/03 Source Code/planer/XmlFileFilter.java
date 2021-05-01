package planer;
import javax.swing.filechooser.*;
import java.io.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unbekannt
 * @version 1.0
 */

public class XmlFileFilter extends javax.swing.filechooser.FileFilter {
  /**
   * Leerer Konstruktor der Klasse XmlFileFilter
   */
  public XmlFileFilter() {
  }

  /**
   * Hier wird sichergestellt, dass nur Verzeichnisse und .xml Dateien im FileChooser sichtbar sind.
   * @param pathname Es wird das File und der zugehörige Pfad übergeben.
   * @return
   */
    public boolean accept(File pathname) {
    if(pathname.getName().endsWith(".xml") || pathname.isDirectory()) {
      return true;
    }
    else
    {
      return false;
    }
   }

   /**
    * Liefert den Text für einen Eintrag in einer Combo-Box im FileChooser.
    * @return
    */
   public String getDescription() {
      return ".xml Dateien";
   }


}
