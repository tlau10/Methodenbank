/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Wiebke Bang, Frank Mayer
 * @version 1.0
 */
package portfolio.business;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.filechooser.*;


/**
 * Zeigt nur csv Dateien an.
 */
public class PortfolioFileFilter extends FileFilter
{

    /**
     * Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *
     * Files that begin with "." are ignored.
     *
     * @see #getExtension
     * @see FileFilter#accepts
     */
    public boolean accept(File f) {
        if(f != null)
        {

            if(f.isDirectory())
            {
            return true;
            }

            String extension = getExtension(f);
            if(extension != null && extension.equalsIgnoreCase("csv"))
            {
                return true;
            }

        }
        return false;
    }


    public String getDescription() {
        return "csv";
    }

     /**
     * Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see FileFilter#accept
     */
    public String getExtension(File f)
    {
	    if(f != null)
        {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
            return filename.substring(i+1).toLowerCase();
	    }
	}
	return null;
    }

}