/*
 * XMLFileFilter.java
 *
 * Created on 12. Juni 2004, 21:29
 */

package view.fileFilter;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Diese Klasse ist ein Filter für den Swing FileChooser (Öffnen / Speichern Dialog) <br>
 * Damit werden nur Verzeichnisse angezeigt.
 * @author  hmaass
 */
public class DirectoryFileFilter extends FileFilter {
    private String getExtension(File f) {
            String ext = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 &&  i < s.length() - 1) {
                ext = s.substring(i+1).toLowerCase();
            }
            return ext;
        }

    // Akzeptiere nur XML Dateien
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    //The description of this filter
    public String getDescription() {
        return "Verzeichnisse";
    }
}


