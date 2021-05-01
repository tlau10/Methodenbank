/**
 * <p>Title: Strecken_Box</p>
 * <p>Description: Diese Klasse stellt das Dialog-Fenster zur Eingabe von Strecken-Daten dar.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;

public class Strecken_Box extends JDialog implements ActionListener {

  //Klassenattribute
  XYLayout xYLayout1 = new XYLayout();
  JLabel ueberschrift = new JLabel();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JLabel entfernung = new JLabel();
  JTextField entfernungTF = new JTextField();

  private TransopController controller;
  private int objektId;
  private Strecke strecke;

  /**
   * Konstruktor
   *
   * @param c gueltiger controller
   * @param id des zugehörigen empfaengers
   * @throws HeadlessException
   */

  public Strecken_Box( TransopController c, int id ) throws HeadlessException {

    controller = c;
    objektId = id;

    //hol die richtige Strecke raus
    for( int i = 0; i < controller.getStrecken().size(); i++ ) {
      if( ( ( Strecke )controller.getStrecken().get( i ) ).getId() == objektId ) {
        strecke = ( Strecke )controller.getStrecken().get( i );
      }
    }

    this.setTitle( "Strecken Daten" );
    try {
      jbInit();
    }
    catch( Exception e ) {
      e.printStackTrace();
    }

  }

  /**
   * Die Init-Methode dient zur Initialisierung sämtlicher Klassenattribute.
   *
   * @throws Exception
   */

  private void jbInit() throws Exception {
    ueberschrift.setFont( new java.awt.Font( "Dialog", 1, 22 ) );
    ueberschrift.setHorizontalAlignment( SwingConstants.LEFT );
    ueberschrift.setHorizontalTextPosition( SwingConstants.LEFT );
    ueberschrift.setText( "Daten für Strecke" );
    this.getContentPane().setLayout( xYLayout1 );
    okButton.setText( "OK" );
    okButton.addActionListener( this );
    cancelButton.setText( "Cancel" );
    cancelButton.addActionListener( this );

    entfernung.setText( "Entfernung in km" );
    entfernungTF.setText( String.valueOf( strecke.getEntfernung() ) );
    xYLayout1.setWidth( 300 );
    xYLayout1.setHeight( 150 );

    this.getContentPane().setBackground( new Color( 233, 233, 233 ) );
    this.getContentPane().add( ueberschrift, new XYConstraints( 25, 0, 228, 40 ) );
    this.getContentPane().add( entfernung, new XYConstraints( 25, 60, 123, 25 ) );
    this.getContentPane().add( entfernungTF, new XYConstraints( 140, 62, 120, 20 ) );
    this.getContentPane().add( cancelButton,  new XYConstraints(165, 100, 80, 23) );
    this.getContentPane().add( okButton, new XYConstraints( 90, 100, 53, 23 ) );
  }

  /**
   * Diese Methode wird überschrieben (ActionListener) um Aktionen auf Button-Klicks auszufuehren.
   * Beim Buttonklick wird überprüft, ob es sich um einen OK-Klick oder Abbrechen-Klick handelt und die
   * jeweilige Methode aufgerufen.
   *
   * @param e aktuelles actionEvent
   */

  public void actionPerformed( ActionEvent e ) {

    if( e.getActionCommand().equals( cancelButton.getText() ) ) {
      this.cancelButton_actionPerformed();
    }
    else {
      this.okButton_actionPerformed();
    }
  }

  /**
   * Hier wird die Aktion die beim druecken des "cancel-buttons" ausgeführt wird aufgerufen.
   * Das Dialog-Fenster wird geschlossen.
   */

  private void cancelButton_actionPerformed() {
    this.dispose();
    controller.getMainFrame().repaint();
  }

  /**
   * Hier wird die Aktion die beim druecken des "ok-buttons" ausgeführt wird aufgerufen.
   * Die Daten die eingegeben wurden werden dem ausgewählten Objekt zugewiesen und gespeichert.
   */

  private void okButton_actionPerformed() {

    for( int i = 0; i < controller.getStrecken().size(); i++ ) {
      if( ( ( Strecke )controller.getStrecken().get( i ) ).getId() == objektId ) {
        if( entfernungTF.getText() != null ) {
          ( ( Strecke )controller.getStrecken().get( i ) ).setEntfernung( Integer.parseInt( entfernungTF.getText() ) );
        }
        else {
          ( ( Strecke )controller.getStrecken().get( i ) ).setEntfernung( 0 );
        }
      }
    }
    controller.getZeichenPane().setStrecken( controller.getStrecken() );
    this.dispose();
    controller.getMainFrame().repaint();
  }

}