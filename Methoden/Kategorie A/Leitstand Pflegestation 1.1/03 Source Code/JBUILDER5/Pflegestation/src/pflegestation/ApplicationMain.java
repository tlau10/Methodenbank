package pflegestation;

import javax.swing.UIManager;
import java.awt.*;

/**
 * <p>Title: Leitstand Pflegestation</p>
 * <p>Description: Optimiert die Pflegevorgänge einer Krankenhausstation</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: FH Konstanz</p>
 * @author Sebastian Hagen, Jonathan Feuerstein, Birgit Engler
 * @version 1.0
 */

public class ApplicationMain {
    boolean packFrame = false;

    //Construct the application
    public ApplicationMain() {
        FrameMain frame = new FrameMain();
        //Validate frames that have preset sizes
        //Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        }
        else {
            frame.validate();
        }
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }
    //Main method
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        new ApplicationMain();
    }
}