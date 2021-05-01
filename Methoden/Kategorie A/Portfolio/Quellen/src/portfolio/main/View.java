package portfolio.main;

import javax.swing.UIManager;
import java.awt.*;

import portfolio.view.*;


public class View {
    boolean packFrame = false;


    /**
     * Construct the application
     */
    public View() {
        Hauptfenster frame = new Hauptfenster();
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


    /**
     * Main method
     */
    public static void main(String[] args) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//      UIManager.setLookAndFeel( "javax.swing.plaf.metal.MetalLookAndFeel" );
//      UIManager.setLookAndFeel( "com.sun.java.swing.plaf.motif.MotifLookAndFeel" );
//      UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
        View mainFrame = new View();
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }
}