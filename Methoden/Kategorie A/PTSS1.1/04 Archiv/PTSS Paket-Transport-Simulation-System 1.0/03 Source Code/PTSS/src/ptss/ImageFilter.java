package ptss;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;


public class ImageFilter extends FileFilter {

    // Akzeptiert alle directories und alle gif, jpg, odedr tiff dateien.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String extension = Utils.getExtension(f);
	if (extension != null) {
            if (extension.equals(Utils.tiff) ||
                extension.equals(Utils.tif) ||
                extension.equals(Utils.gif) ||
                extension.equals(Utils.jpeg) ||
                extension.equals(Utils.jpg)) {
                    return true;
            } else {
                return false;
            }
    	}

        return false;
    }

    // The description of this filter
    public String getDescription() {
        return "Nur Bilder";
    }
}
