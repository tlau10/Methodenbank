package trOptimizer;

/**
 * <p>Title: SymbolAdapter</p>
 * <p>Description: Die Klasse SymbolAdapter dient als Listener, der auf bestimmte MouseEvents reagieren soll.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.awt.event.MouseEvent;

public class SymbolAdapter extends java.awt.event.MouseAdapter {

  //Klassenattribute
  MainFrame adaptee;

  /**
   * Der Konstruktor erwartet ein MainFrame-Objekt.
   *
   * @param m
   */
  SymbolAdapter( MainFrame m ) {
    adaptee = m;
  }

  /**
   * Diese Methode wird ueberschrieben und aufgerufen, wenn die Maus geclickt wird.
   * @param e
   */
  public void mouseClicked( MouseEvent e ) {
    this.symbolPane_mouseClicked( e );
  }

  /**
   * Diese Methode dient dazu, um von der Symbolflaeche Objekt zu erzeugen. Mit doppelklick auf den jeweiligen -hier festgelegten Bereich-
   * werden neue Objekte erzeugt und in der Zeichenflaeche dargestellt.
   *
   * @param e
   */

  public void symbolPane_mouseClicked( MouseEvent e ) {

    //wars ein doppelklick?
    if( e.getClickCount() == 1) {
      if( e.getX() >= 20 && e.getX() <= 45 && e.getY() >= 45 && e.getY() <= 65 ) {
        adaptee.getController().addEmpfaenger();
        // hier das Eingabe-Fenster fuer Empfaenger aufrufen

        adaptee.repaint();
      }
      else if( e.getX() >= 20 && e.getX() <= 45 && e.getY() >= 135 && e.getY() <= 155 ) {
        adaptee.getController().addProduzent();
        // hier das Eingabe-Fenster fuer Produzenten aufrufen

        adaptee.repaint();
      }
      else if( e.getX() >= 17 && e.getX() <= 47 && e.getY() >= 225 && e.getY() <= 245 ) {
        adaptee.getController().addTransporter();
        // hier das Eingabe-Fenster fuer Transporter aufrufen

        adaptee.repaint();
      }
    }
  }

}