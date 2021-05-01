/**
 * <p>Title: TransporterAdapter</p>
 * <p>Description: Die Klasse TransporterAdapter dient als Listener, der auf bestimmte MouseEvents reagieren soll.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.event.*;

public class TransporterAdapter extends MouseAdapter {

  //Klassenattribute
  MainFrame adaptee;
  private boolean isObject = false;
  MyTransporterPopUp popUp;

  /**
   * Der Konstruktor erwartet ein MainFrame-Objekt
   * @param m
   */
  public TransporterAdapter( MainFrame m ) {
    adaptee = m;
  }

  /**
   * Diese Methode wird überschrieben und aufgerufen, wenn die Maus geclickt wird.
   * @param e
   */

  public void mouseClicked( MouseEvent e ) {
    this.transporterPane_mouseClicked( e );
  }

  /**
   * Diese Methode überprüft, ob es sich um ein Rechtsklick gehandlet hat.
   * Falls ja, wird das Transporter-PopUp-Menü aufgerfen.
   *
   * @param e
   */
  public void transporterPane_mouseClicked( MouseEvent e ) {
    if( /*(e.getButton() == e.BUTTON3) ||*/ ( e.getClickCount() == 2 ) ) {
      if( adaptee.getController().isTransporterInPos( e.getX(), e.getY() ) ) {
        adaptee.getController().popUpTransporter( e.getX(), ( e.getY() + 400 ) );
      }
    }
  }
}