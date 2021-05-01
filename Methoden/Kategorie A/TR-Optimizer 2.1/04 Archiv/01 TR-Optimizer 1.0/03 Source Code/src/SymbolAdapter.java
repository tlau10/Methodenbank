/**
 * <p>Title: SymbolAdapter</p>
 * <p>Description: Die Klasse SymbolAdapter dient als Listener, der auf bestimmte MouseEvents reagieren soll.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.awt.event.*;

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
   * Diese Methode wird überschrieben und aufgerufen, wenn die Maus geclickt wird.
   * @param e
   */
  public void mouseClicked( MouseEvent e ) {
    this.symbolPane_mouseClicked( e );
  }

  /**
   * Diese Methode dient dazu, um von der Symbolfläche Objekt zu erzeugen. Mit doppelklick auf den jeweiligen -hier festgelegten Bereich-
   * werden neue Objekte erzeugt und in der Zeichenfläche dargestellt.
   *
   * @param e
   */

  public void symbolPane_mouseClicked( MouseEvent e ) {

    //wars ein doppelklick?
    if( e.getClickCount() == 2 ) {
      if( e.getX() >= 20 && e.getX() <= 45 && e.getY() >= 45 && e.getY() <= 65 ) {
        adaptee.getController().addEmpfaenger();
        // hier das Eingabe-Fenster für Empfänger aufrufen

        adaptee.repaint();
      }
      else if( e.getX() >= 20 && e.getX() <= 45 && e.getY() >= 135 && e.getY() <= 155 ) {
        adaptee.getController().addProduzent();
        // hier das Eingabe-Fenster für Produzenten aufrufen

        adaptee.repaint();
      }
      else if( e.getX() >= 17 && e.getX() <= 47 && e.getY() >= 225 && e.getY() <= 245 ) {
        adaptee.getController().addTransporter();
        // hier das Eingabe-Fenster für Transporter aufrufen

        adaptee.repaint();
      }
    }
  }

}