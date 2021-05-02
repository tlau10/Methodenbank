/*
 * Bernd Haertenstein
 * 
 * Copyright (c) 2011-2015 University of Applied Science Konstanz. All Rights Reserved.
 * 
 * Version 	1.0	WS10/11
 * Author	Bernd Haertenstein
 * 
 */
package jobshop;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * Refine all files without .job-files. Preparing file for FileChooser.
 */
public class JobShopFileFilter extends FileFilter{
    public final static String job = "job";

    /* Accept all directories and all job files. */
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

    /* Get the extension of a file. */
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /* The description of this filter. Will be shown the user during loading process of a file. */
    public String getDescription() {
        return "JobShop Dateien (*.job)";
    }
}