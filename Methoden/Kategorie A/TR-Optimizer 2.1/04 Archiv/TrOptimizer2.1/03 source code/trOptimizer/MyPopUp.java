package trOptimizer;

/**
 * <p>Title: MyPopUp</p>
 * <p>Description: Diese Klasse stellt ein PopUp-Men� dar, das aufgerufen wird, bei Rechtklick auf ein Objekt in der Zeichnungsfl�che.</p>
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


public class MyPopUp extends JPopupMenu implements ActionListener {

  //Klassenattribute
  JMenuItem streckeZeichnen = new JMenuItem();
  JMenuItem objektLoeschen = new JMenuItem();
  JMenuItem daten = new JMenuItem();
  private int objektId = 0;
  private TransopController controller;
  private Empfaenger_Box empfaenger_Box;
  private Produzent_Box produzent_Box;
  private StreckenAdapter streckenAdapter;
  private int objektX, objektY;

  /**
   * Der Konstruktor setzt den aktuellen und gueltigen controller.
   * @param c den aktuellen TransopController
   */
  public MyPopUp( TransopController c ) {
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
    streckeZeichnen.setText( "Strecke zeichnen" );
    objektLoeschen.setText( "Objekt loeschen" );
    daten.setText( "Daten anlegen/aendern" );
    streckeZeichnen.addActionListener( this );
    objektLoeschen.addActionListener( this );
    daten.addActionListener( this );

    this.add( streckeZeichnen );
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
    if( e.getActionCommand().equalsIgnoreCase( streckeZeichnen.getText() ) ) {

      // strecke zeichnen
      streckenAdapter = new StreckenAdapter( this );
      controller.getZeichenPane().addMouseMotionListener( streckenAdapter );
      controller.getZeichenPane().addMouseListener( streckenAdapter );

    }
    else if( e.getActionCommand().equalsIgnoreCase( objektLoeschen.getText() ) ) {
      // Objekt loeschen
      controller.removeObjekt( objektId );

    }
    else if( e.getActionCommand().equalsIgnoreCase( daten.getText() ) ) {
      // Daten eingeben
      if( objektId <= 999 ) { // popUp vom empfaenger

        empfaenger_Box = new Empfaenger_Box( controller, objektId );

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = controller.getMainFrame().getSize();
        if( frameSize.height > screenSize.height ) {
          frameSize.height = screenSize.height;
        }
        if( frameSize.width > screenSize.width ) {
          frameSize.width = screenSize.width;
        }
        empfaenger_Box.setLocation( ( screenSize.width - frameSize.width ) / 2,
            ( screenSize.height - frameSize.height ) / 2 );
        empfaenger_Box.pack();

        empfaenger_Box.setVisible( true );

      }
      else if( objektId >= 1000 && objektId <= 1999 ) { // popUp vom produzent

        produzent_Box = new Produzent_Box( controller, objektId );

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = controller.getMainFrame().getSize();
        if( frameSize.height > screenSize.height ) {
          frameSize.height = screenSize.height;
        }
        if( frameSize.width > screenSize.width ) {
          frameSize.width = screenSize.width;
        }
        produzent_Box.setLocation( ( screenSize.width - frameSize.width ) / 2,
            ( screenSize.height - frameSize.height ) / 2 );
        produzent_Box.pack();
        produzent_Box.setVisible( true );
      }
    }
  }

  /**
       * Diese Methode zeichnet die Strecke von einem zum anderen Objekt, also z.B. von einem Produzenten zu einem Empfaenger.
   * Es merkt sich dabei die Quell-Koordinate (also von wo aus die Strecke gezeichnet werden soll) und bekommt nur
   * noch die Ziel-Koordinate uebergeben.
   *
   * @param x - Koordinate des Streckenendes
   * @param y - Koordinate des Streckenendes
   */
  public void zeichneStrecke( int x, int y ) {

    // strecke zeichnen, falls anfangs und schlusspunkt o.k.!!
    controller.printLine( x, y, objektId );
    controller.getZeichenPane().removeMouseMotionListener( streckenAdapter );
    controller.getZeichenPane().removeMouseListener( streckenAdapter );

  }

  /**
   * Diese Methode wird vom streckenAdapter aufgerufen um bei mouseMoved temporaere linien zu zeichnen.
   * Der Benutzer sieht so staendig eine Linie mit seiner Maus mitbewegen.
   *
   * @param x2 - Koordinate des Streckenendes
   * @param y2 - Koordinate des Streckenendes
   */
  public void printLine( int x2, int y2 ) {
    controller.printTempLine( x2, y2, objektId );
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

  /**
   * Diese Methode liefert die Id des ausgwaehlten Objekts zurueck.
   *
   * @return
   */
  public int getObjektId() {
    return objektId;
  }

}