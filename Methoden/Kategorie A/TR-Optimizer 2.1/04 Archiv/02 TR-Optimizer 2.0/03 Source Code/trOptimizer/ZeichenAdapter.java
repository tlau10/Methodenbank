package trOptimizer;

/**
 * <p>Title: ZeichenAdapter</p>
 * <p>Description: Die Klasse ZeichenAdapter dient als Listener, der auf bestimmte MouseEvents reagieren soll.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class ZeichenAdapter extends MouseAdapter {

  //Klassenattribute
   private Graphics2D a2;
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
   * Diese Methode wird �berschrieben und aufgerufen, wenn die Maus geclickt wird.
   * @param e
   */

  public void mouseClicked( MouseEvent e ) {
    this.zeichenPane_mouseClicked( e );

  }

  /**
   * Diese Methode wird �berschrieben und aufgerufen, wenn die Maus gedr�ckt gehalten wird.
   * @param e
   */

  public void mousePressed( MouseEvent e ) {
    this.zeichenPane_mousePressed( e );

  }

  /**
   * Diese Methode wird �berschrieben und aufgerufen, wenn die Maus losgelassen wird.
   * @param e
   */

  public void mouseReleased( MouseEvent e ) {
    this.zeichenPane_mouseReleased( e );
  }

  /**
   * Diese Methode �berpr�ft, ob es sich um ein doppelklick gehandlet hat.
   * Falls ja, wird das PopUp-Men� aufgerufen.
   *
   * @param e
   */
  public void zeichenPane_mouseClicked( MouseEvent e ) {

    if(e.isMetaDown()) {
      if( adaptee.getController().isInPos( e.getX(), e.getY() ) ) {
        adaptee.getController().popMeUp( e.getX(), e.getY() );
      }
    }
  }

  /**
   * Diese Methode �berpr�ft, ob innerhalb der Zeichenfl�che die Maus gedr�ckt gehalten wird.
   *
   * @param e
   */

  public void zeichenPane_mousePressed( MouseEvent e ) {
    if( adaptee.getController().isInPos( e.getX(), e.getY() ) ) {
      isObject = true;

      Point lastpoint; //Ursprung des letzten Rechtecks
      Point drawoffset; //Offset zur Mausdragposition


    }
  }


  public void mouseDragged(MouseEvent e)
  {
    if( isObject )
    {
      adaptee.getController().move( e.getX() - 10, e.getY() - 10 );
      adaptee.repaint();
    }
  }


  /**
   * Diese Methode �berpr�ft, ob innerhalb der Zeichenfl�che die Maus losgelassen wurde.
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