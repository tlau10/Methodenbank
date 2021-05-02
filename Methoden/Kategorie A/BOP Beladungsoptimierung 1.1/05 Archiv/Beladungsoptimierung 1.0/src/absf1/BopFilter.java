package absf1;

import java.io.FileFilter;
import java.io.File;

/**
 * <p>Title: BopFilter</p>
 * <p>Description: Klasse zur Filterung der Dateien beim Speichern- und �ffnendialog. Nur .BOP Dateien und Verzeichnisse werden zugelassen </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FH Konstanz</p>
 * @author J�rgen Kambeitz
 * @version 1.0
 */

public class BopFilter extends javax.swing.filechooser.FileFilter  {

  /**
   * Leerer Konstruktor der Klasse BopFilter
   */
  public BopFilter() {
  }

  /**
   * Hier wird sichergestellt, dass nur Verzeichnisse und .BOP Dateien im FileChooser sichtbar sind.
   * @param pathname Es wird das File und der zugeh�rige Pfad �bergeben.
   * @return
   */
  public boolean accept(File pathname) {
    if(pathname.getName().endsWith(".bop") || pathname.isDirectory()) {
      return true;
    }
    else
    {
      return false;
    }
   }

   /**
    * Liefert den Text f�r einen Eintrag in einer Combo-Box im FileChooser.
    * @return
    */
   public String getDescription() {
      return ".bop Dateien";
    }


}