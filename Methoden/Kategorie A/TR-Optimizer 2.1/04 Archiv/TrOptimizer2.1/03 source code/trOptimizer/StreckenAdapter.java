package trOptimizer;

/**
 * <p>Title: StreckenAdapter</p>
 * <p>Description: Die Klasse StreckenAdapter dient als Listener, der auf bestimmte MouseEvents reagieren soll.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class StreckenAdapter extends MouseAdapter implements MouseMotionListener {

  //Klassenattribute
  private MyPopUp popUp;

  /**
   * Der Konstruktor erwartet ein MyPopUp-Objekt.
   *
   * @param pop ein MyPopUp-Objekt
   */
  public StreckenAdapter( MyPopUp pop ) {
    popUp = pop;
  }

  /**
   * Diese Methode wird ueberschrieben und aufgerufen, wenn die Maus bewegt wird.
   *
   * @param e
   */

  public void mouseMoved( MouseEvent e ) {
    popUp.printLine( e.getX(), e.getY() );
  }

  /**
   * Diese Methode wird ueberschrieben und aufgerufen, wenn die Maus gedraggt also gezogen wird.
   * @param e
   */
  public void mouseDragged( MouseEvent e ) {}

  /**
   * Diese Methode wird ueberschrieben und aufgerufen, wenn die Maus geclickt wird.
   * @param e
   */
  public void mouseClicked( MouseEvent e ) {
    popUp.zeichneStrecke( e.getX(), e.getY() );
  }

}