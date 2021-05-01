package ptss;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;


class ModellFilter extends FileFilter{


    // Akzeptiert alle modell daten .xml
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.xml) )
                 {
                    return true;
            } else {
                return false;
            }
            }

        return false;
    }

    // The description of this filter
    public String getDescription() {
        return "Nur Modell Daten";
    }





}