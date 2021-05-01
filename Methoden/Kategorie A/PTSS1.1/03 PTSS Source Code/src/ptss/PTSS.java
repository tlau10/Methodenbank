package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:Applikation Steuerklasse</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Thomas Geldner, Mathias Jehle
 * @version 1.0
 */

import javax.swing.UIManager;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;
import java.awt.*;

public class PTSS {
  boolean packFrame = false;

  /**
   * @directed
   */
  private HauptFrame lnkHauptFrame;

  /**Die Anwendung konstruieren*/
  public PTSS() {

    HauptFrame frame = new HauptFrame();

     if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Das Fenster zentrieren
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

  /**Main-Methode*/
  public static void main(String[] args) {
    try {
  UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    new PTSS();
  }
}