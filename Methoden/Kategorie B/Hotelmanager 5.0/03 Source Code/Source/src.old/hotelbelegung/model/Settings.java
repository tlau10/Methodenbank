package hotelbelegung.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Properties;

public class Settings implements Serializable {

    private static final long serialVersionUID = 5255364196831292150L;
    private static final String FILENAME = "hotel.settings";

    public String lpPath;
    public String tempPath;

    public Settings() {
        Properties props = new Properties();
        InputStream is = null;

        // First try loading from the current directory
        try {
            File f = new File(FILENAME);
            is = new FileInputStream(f);
        } catch (Exception e) {
            is = null;
        }

        try {
            if (is == null) {
                // Try loading from classpath
                is = getClass().getResourceAsStream(FILENAME);
            }

            // Try loading properties from the file (if found)
            props.load(is);
        } catch (Exception e) {
        }

        lpPath = props.getProperty("LP_Path", "C:\\Methodendatenbank\\Solver\\LP_Solve\\Exec\\");
        tempPath = props.getProperty("TemporaryPath", "C:\\Temp\\");
        // newIntProp = new Integer(props.getProperty("ServerPort", "8080"));
    }

    public void saveSettings() {
        try {
            Properties props = new Properties();
            props.setProperty("LP_Path", lpPath);
            props.setProperty("TemporaryPath", tempPath);
            File f = new File(FILENAME);
            OutputStream out = new FileOutputStream(f);
            props.store(out, "This is an optional header comment string");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
