package jobshop;

import java.io.File;
import javax.swing.filechooser.*;

/*
 * Filtert alle nicht .job-Dateien für den FileChooser heraus.
 */
public class JobShopFileFilter extends FileFilter{

    public final static String job = "job";

    //Accept all directories and all job files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = this.getExtension(f);
        if (extension != null) {
            if (extension.equals(job)) {
		return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /*
     * Get the extension of a file.
     */
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    //The description of this filter
    public String getDescription() {
        return "JobShop Dateien (*.job)";
    }
}