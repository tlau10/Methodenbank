/**
 * <p>Title: Transporter_Box</p>
 * <p>Description: Diese Klasse stellt das Dialog-Fenster zur Eingabe von Transporter-Daten dar.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;

public class Transporter_Box extends JDialog implements ActionListener {

  //Klassenattribute
  XYLayout xYLayout1 = new XYLayout();
  JLabel ueberschrift = new JLabel();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JLabel name = new JLabel();
  JLabel kapazitaetsMenge = new JLabel();
  JLabel kostenProKm = new JLabel();
  JTextField kapazitaetsMengeTF = new JTextField();
  JTextField nameTF = new JTextField();
  JTextField kostenProKmTF = new JTextField();

  private TransopController controller;
  private int objektId;
  private Transporter transporter;

  /**
   * Konstruktor
   *
   * @param c gueltiger controller
   * @param id des zugehörigen empfaengers
   * @throws HeadlessException
   */

  public Transporter_Box( TransopController c, int id ) throws HeadlessException {

    controller = c;
    objektId = id;

    //hol den richtigen Empfaenger raus
    for( int i = 0; i < controller.getTransporter().size(); i++ ) {
      if( ( ( Transporter )controller.getTransporter().get( i ) ).getId() == objektId ) {
        transporter = ( Transporter )controller.getTransporter().get( i );
      }
    }

    this.setTitle( "Transporter Daten" );
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
    ueberschrift.setText( "Daten für Transporter" );
    this.getContentPane().setLayout( xYLayout1 );
    okButton.setText( "OK" );
    okButton.addActionListener( this );
    cancelButton.setText( "Cancel" );
    cancelButton.addActionListener( this );
    name.setText( "Name" );
    kapazitaetsMenge.setText( "Kapazität" );
    kapazitaetsMengeTF.setText( String.valueOf( transporter.getKapazitaet() ) );
    nameTF.setText( transporter.getName() );
    xYLayout1.setWidth( 309 );
    xYLayout1.setHeight( 249 );
    kostenProKmTF.setText( String.valueOf( transporter.getKosten_pro_km() ) );
    kostenProKm.setText( "Kosten pro Km" );
    this.getContentPane().setBackground( new Color( 233, 233, 233 ) );
    this.getContentPane().add( ueberschrift, new XYConstraints( 25, 0, 228, 40 ) );
    this.getContentPane().add( name, new XYConstraints( 25, 70, 123, 25 ) );
    this.getContentPane().add( nameTF, new XYConstraints( 140, 72, 120, 20 ) );
    this.getContentPane().add( cancelButton,  new XYConstraints(165, 205, 80, 23) );
    this.getContentPane().add( okButton, new XYConstraints( 90, 205, 53, 23 ) );
    this.getContentPane().add( kapazitaetsMengeTF, new XYConstraints( 140, 104, 120, 20 ) );
    this.getContentPane().add( kapazitaetsMenge, new XYConstraints( 25, 102, 120, 25 ) );
    this.getContentPane().add( kostenProKmTF, new XYConstraints( 140, 136, 120, 20 ) );
    this.getContentPane().add( kostenProKm, new XYConstraints( 25, 134, 123, 25 ) );
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

    for( int i = 0; i < controller.getTransporter().size(); i++ ) {
      if( ( ( Transporter )controller.getTransporter().get( i ) ).getId() == objektId ) {
        if( nameTF.getText() != null ) {
          ( ( Transporter )controller.getTransporter().get( i ) ).setName( nameTF.getText() );
        }
        else {
          ( ( Transporter )controller.getTransporter().get( i ) ).setName( "" );
        }
        if( kapazitaetsMengeTF.getText() != null ) {
          ( ( Transporter )controller.getTransporter().get( i ) ).setKapazitaet( Integer.parseInt( kapazitaetsMengeTF.
              getText() ) );
        }
        else {
          ( ( Transporter )controller.getTransporter().get( i ) ).setKapazitaet( 0 );
        }
        if( kostenProKmTF.getText() != null ) {
          ( ( Transporter )controller.getTransporter().get( i ) ).setKosten_pro_km( Double.parseDouble( kostenProKmTF.
              getText() ) );
        }
        else {
          ( ( Transporter )controller.getTransporter().get( i ) ).setKosten_pro_km( 0 );
        }

      }
    }
    controller.getTransporterPane().setTransporter( controller.getTransporter() );
    controller.getMainFrame().repaint();
    this.dispose();
  }

}