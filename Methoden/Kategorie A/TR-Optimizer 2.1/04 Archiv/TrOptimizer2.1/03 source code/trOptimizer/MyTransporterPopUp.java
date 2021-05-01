package trOptimizer;

/**
 * <p>Title: MyTransporterPopUp</p>
 * <p>Description: Diese Klasse dient als PopUp-Men� der vorhandenen Transporter.</p>
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

public class MyTransporterPopUp extends JPopupMenu implements ActionListener {

  //Klassenattribute
  JMenuItem objektLoeschen = new JMenuItem();
  JMenuItem daten = new JMenuItem();
  private int objektId = 0;
  private TransopController controller;
  private Transporter_Box transporter_Box;

  /**
   * Der Konstruktor setzt den aktuellen und gueltigen controller.
   * @param c den aktuellen TransopController
   */

  public MyTransporterPopUp( TransopController c ) {

    controller = c;

    try {
      jbInit();
    }
    catch( Exception e ) {
      e.printStackTrace();
    }
  }

  /**
   * Diese Methode dient zur Initialisierung saemtlicher Komponenten.
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
   * Diese Methode dient zur Aktion eines Mausklicks. Es wird ueberprueft, welcher Menuepunkt auferufen wurde und
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

      transporter_Box = new Transporter_Box( controller, objektId );

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = controller.getMainFrame().getSize();
      if( frameSize.height > screenSize.height ) {
        frameSize.height = screenSize.height;
      }
      if( frameSize.width > screenSize.width ) {
        frameSize.width = screenSize.width;
      }
      transporter_Box.setLocation( ( screenSize.width - frameSize.width ) / 2,
          ( screenSize.height - frameSize.height ) / 2 );
      transporter_Box.pack();

      transporter_Box.setVisible( true );

    }
    //controller.getMainFrame().repaint();
  }

  /**
   * Diese Methode setzt die Id des aktuell ausgewaehlten Objekts. Sie wird vom controller gesetzt,
   * sobald auf ein Objekt mit rechts geklickt wurde und das popUp aufgerufen wurde ist immer die id,
   * des gerade gewaehlten Objekts.
   *
   * @param id des ausgewaehlten Objekts
   */

  public void setObjektId( int id ) {
    objektId = id;
  }

  }