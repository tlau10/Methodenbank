package trOptimizer;

/**
 * <p>Title: MyStreckenPopUp</p>
 * <p>Description: Diese Klasse dient als PopUp-Menü der gezeichneten Strecken.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MyStreckenPopUp extends JPopupMenu implements ActionListener {

  //Klassenattribute
  JMenuItem objektLoeschen = new JMenuItem();
  JMenuItem daten = new JMenuItem();
  private int objektId = 0;
  private TransopController controller;
  private Strecken_Box strecken_Box;

  /**
   * Der Konstruktor setzt den aktuellen und gueltigen controller.
   * @param c den aktuellen TransopController
   */

  public MyStreckenPopUp( TransopController c ) {

    controller = c;

    try {
      jbInit();
    }
    catch( Exception e ) {
      e.printStackTrace();
    }
  }

  /**
   * Diese Methode dient zur Initialisierung sämtlicher Komponenten.
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    objektLoeschen.setText( "Objekt loeschen" );
    daten.setText( "Daten anlegen/aendern" );
    objektLoeschen.addActionListener( this );
    daten.addActionListener( this );

    this.add( daten );
    this.add( objektLoeschen );

  }

  /**
   * Diese Methode dient zur Aktion eines Mausklicks. Es wird überprüft, welcher Menüpunkt auferufen wurde und
   * veranlasst die entsprechende weitere Methodenaufrufe bzw. Objekterzeugungen.
   *
   * @param e
   */

  public void actionPerformed( ActionEvent e ) {

    if( e.getActionCommand().equalsIgnoreCase( objektLoeschen.getText() ) ) {
      // Objekt loeschen
      controller.removeObjekt( objektId );

    }
    else {
      // Daten eingeben

      strecken_Box = new Strecken_Box( controller, objektId );

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = controller.getMainFrame().getSize();

      if( frameSize.height > screenSize.height ) {
        frameSize.height = screenSize.height;
      }
      if( frameSize.width > screenSize.width ) {
        frameSize.width = screenSize.width;
      }

      strecken_Box.setLocation( ( screenSize.width - frameSize.width ) / 2,
          ( screenSize.height - frameSize.height ) / 2 );
      strecken_Box.pack();
      strecken_Box.setVisible( true );

    }
  }

  /**
   * Diese Methode setzt die Id des aktuell ausgewählten Objekts. Sie wird vom controller gesetzt,
   * sobald auf ein Objekt mit rechts geklickt wurde und das popUp aufgerufen wurde ist immer die id,
   * des gerade gewählten Objekts.
   *
   * @param id des ausgewählten Objekts
   */

  public void setObjektId( int id ) {
    objektId = id;
  }

}