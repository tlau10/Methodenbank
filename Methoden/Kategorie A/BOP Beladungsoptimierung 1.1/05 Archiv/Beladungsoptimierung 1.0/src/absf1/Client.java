package absf1;

import javax.swing.UIManager;
import java.awt.*;

/**
 * <p>Title: Client </p>
 * <p>Description: Ausgangspunkt der Applikation. Von hier aus wird das Fenster HauptFrame angelegt.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FH Konstanz</p>
 * @author Jürgen Kambeitz
 * @version 1.0
 */

public class Client {
  private boolean packFrame = false;

  /**
   * Im Konstruktor der Klasse Client wird das Hauptfenster der Applikation BOP erzeugt und gestartet.
   */
  public Client() {
    HauptFrame frame = new HauptFrame();

    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Zentriert das Fenster
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
   * Main-Funktion der Applikation
   * @param args
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new Client();
  }
}