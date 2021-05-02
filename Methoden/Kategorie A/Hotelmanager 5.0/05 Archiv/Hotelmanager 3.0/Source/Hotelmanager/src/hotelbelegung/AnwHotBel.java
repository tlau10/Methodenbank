package hotelbelegung;


import javax.swing.UIManager;

/**
 * Überschrift:   Hotelbelegung
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author Volker Wohlleber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003 
 */

public class AnwHotBel {
  boolean packFrame = false;

  /**Die Anwendung konstruieren*/
  public AnwHotBel() {
    Frame1 frame = new Frame1();
    frame.setVisible(true);
  }
  /**Main-Methode*/
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new AnwHotBel();
  }
}