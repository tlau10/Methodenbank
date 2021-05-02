
package zuordnungsplanung;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.filechooser.FileFilter;



/**
 * <p>Title: FileFilterZuo</p>
 * <p>Description: The filefilter is used from JFileChooser, to display only csv-files.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Patrick Badent
 * @version 1.0
 */


public class FileFilterZuo extends FileFilter
  {
    private static String TYPE_UNKNOWN = "Type Unknown";
    private static String HIDDEN_FILE = "Hidden File";

    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    public FileFilterZuo(String extension)
    {
        this(extension,null);
    }

    public FileFilterZuo(String[] filters)
    {
        this(filters, null);
    }

    public FileFilterZuo() {
        this.filters = new Hashtable();
    }

    public FileFilterZuo(String extension, String description) {
        this();
        if(extension!=null) addExtension(extension);
         if(description!=null) setDescription(description);
    }

     public void addExtension(String extension) {
        if(filters == null) {
            filters = new Hashtable(5);
        }
        filters.put(extension.toLowerCase(), this);
        fullDescription = null;
    }

     @Override
	public String getDescription() {
        if(fullDescription == null) {
            if(description == null || isExtensionListInDescription()) {
                 fullDescription = description==null ? "(" : description + " (";
                // build the description from the extension list
                Enumeration extensions = filters.keys();
                if(extensions != null) {
                    fullDescription += "." + (String) extensions.nextElement();
                    while (extensions.hasMoreElements()) {
                        fullDescription += ", ." + (String) extensions.nextElement();
                    }
                }
                fullDescription += ")";
            } else {
                fullDescription = description;
            }
        }
        return fullDescription;
    }

    public void setDescription(String description)
    {
        this.description = description;
        fullDescription = null;
    }

    public FileFilterZuo(String[] filters, String description) {
        this();
        for (int i = 0; i < filters.length; i++) {
            // add filters one by one
            addExtension(filters[i]);
        }
         if(description!=null) setDescription(description);
    }

    @Override
	public boolean accept(File f) {
        if(f != null) {
            if(f.isDirectory()) {
                return true;
            }
            String extension = getExtension(f);
            if(extension != null && filters.get(getExtension(f)) != null) {
                return true;
            };
        }
        return false;
    }

    public String getExtension(File f) {
        if(f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
                return filename.substring(i+1).toLowerCase();
            };
        }
        return null;
    }

    public void setExtensionListInDescription(boolean b) {
        useExtensionsInDescription = b;
        fullDescription = null;
    }

    public boolean isExtensionListInDescription() {
        return useExtensionsInDescription;
    }


  }
