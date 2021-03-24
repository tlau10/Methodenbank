/**
 * <p>Title: ZeichenAdapter</p>
 * <p>Description: Die Klasse ZeichenAdapter dient als Listener, der auf bestimmte MouseEvents reagieren soll.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.event.*;

public class ZeichenAdapter extends MouseAdapter {

  //Klassenattribute
  MainFrame adaptee;
  private boolean isObject = false;
  MyPopUp popUp;

  /**
   * Der Konstruktor erwartet ein MainFrame-Objekt
   * @param m
   */
  public ZeichenAdapter( MainFrame m ) {
    adaptee = m;
  }

  /**
   * Diese Methode wird überschrieben und aufgerufen, wenn die Maus geclickt wird.
   * @param e
   */

  public void mouseClicked( MouseEvent e ) {
    this.zeichenPane_mouseClicked( e );

  }

  /**
   * Diese Methode wird überschrieben und aufgerufen, wenn die Maus gedrückt gehalten wird.
   * @param e
   */

  public void mousePressed( MouseEvent e ) {
    this.zeichenPane_mousePressed( e );

  }

  /**
   * Diese Methode wird überschrieben und aufgerufen, wenn die Maus losgelassen wird.
   * @param e
   */

  public void mouseReleased( MouseEvent e ) {
    this.zeichenPane_mouseReleased( e );
  }

  /**
   * Diese Methode überprüft, ob es sich um ein doppelklick gehandlet hat.
   * Falls ja, wird das PopUp-Menü aufgerufen.
   *
   * @param e
   */
  public void zeichenPane_mouseClicked( MouseEvent e ) {

    if( /*(e.getButton() == e.BUTTON3) ||*/ (e.getClickCount() == 2)) {
      if( adaptee.getController().isInPos( e.getX(), e.getY() ) ) {
        adaptee.getController().popMeUp( e.getX(), e.getY() );
      }
    }
  }

  /**
   * Diese Methode überprüft, ob innerhalb der Zeichenfläche die Maus gedrückt gehalten wird.
   *
   * @param e
   */

  public void zeichenPane_mousePressed( MouseEvent e ) {
    if( adaptee.getController().isInPos( e.getX(), e.getY() ) ) {
      isObject = true;
    }
  }

  /**
   * Diese Methode überprüft, ob innerhalb der Zeichenfläche die Maus losgelassen wurde.
   *
   * @param e
   */
  public void zeichenPane_mouseReleased( MouseEvent e ) {
    if( isObject ) {
      // controller aufrufen, um Objekte an der neuen Stelle zu zeichnen
      adaptee.getController().move( e.getX() - 10, e.getY() - 10 );
      adaptee.repaint();
    }
    isObject = false;
  }
}