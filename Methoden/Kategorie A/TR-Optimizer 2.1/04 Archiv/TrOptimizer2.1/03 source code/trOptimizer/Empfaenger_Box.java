package trOptimizer;

/**
 * <p>Title: Empfaenger_Box</p>
 * <p>Description: Diese Klasse stellt das Dialog-Fenster zur Eingabe von Empfaenger-Daten dar.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;


public class Empfaenger_Box extends JDialog implements ActionListener {

  //Klassenattribute
  private XYLayout xYLayout1 = new XYLayout();
  private JLabel ueberschrift = new JLabel();
  private JButton okButton = new JButton();
  private JButton cancelButton = new JButton();
  private JLabel name = new JLabel();
  private JLabel nachfrageMenge = new JLabel();
  private JTextField nachfrageMengeTF = new JTextField();
  private JTextField nameTF = new JTextField();

  private TransopController controller;
  private int objektId;
  private Empfaenger empfaenger;

  /**
   * Konstruktor
   *
   * @param c gueltiger controller
   * @param id des zugehoerigen empfaengers
   * @throws HeadlessException
   */
  public Empfaenger_Box( TransopController c, int id )  {//Headless

    controller = c;
    objektId = id;

    //hol den richtigen Empfaenger raus
    for( int i = 0; i < controller.getEmpfaenger().size(); i++ ) {
      if( ( ( Empfaenger )controller.getEmpfaenger().get( i ) ).getId() == objektId ) {
        empfaenger = ( Empfaenger )controller.getEmpfaenger().get( i );
      }
    }

    this.setTitle( "Empfaenger Daten" );
    try {
      jbInit();
    }
    catch( Exception e ) {
      e.printStackTrace();
    }

  }

  /**
   * Die Init-Methode dient zur Initialisierung der Klassenattribute.
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    ueberschrift.setFont( new java.awt.Font( "Dialog", 1, 22 ) );
    ueberschrift.setHorizontalAlignment( SwingConstants.LEFT );
    ueberschrift.setHorizontalTextPosition( SwingConstants.LEFT );
    ueberschrift.setText( "Empfaenger Daten" );
    this.getContentPane().setLayout( xYLayout1 );
    okButton.setText( "OK" );
    okButton.addActionListener( this );
    cancelButton.setText( "Cancel" );
    cancelButton.addActionListener( this );
    name.setText( "Name" );
    nachfrageMenge.setText( "nachgefragte Menge" );
    nachfrageMengeTF.setText( String.valueOf( empfaenger.getBenoetigteMenge() ) );
    nameTF.setText( empfaenger.getName() );
    xYLayout1.setWidth( 309 );
    xYLayout1.setHeight( 249 );
    this.getContentPane().setBackground( new Color( 233, 233, 233 ) );
    this.getContentPane().add( ueberschrift, new XYConstraints( 25, 0, 238, 45 ) );
    this.getContentPane().add( nachfrageMenge, new XYConstraints( 25, 120, 120, 25 ) );
    this.getContentPane().add( name, new XYConstraints( 25, 70, 123, 25 ) );
    this.getContentPane().add( cancelButton,    new XYConstraints(189, 177, 80, 23) );
    this.getContentPane().add( okButton, new XYConstraints( 114, 177, 53, 23 ) );
    this.getContentPane().add( nachfrageMengeTF, new XYConstraints( 140, 122, 120, 20 ) );
    this.getContentPane().add( nameTF, new XYConstraints( 140, 72, 120, 20 ) );
  }

  /**
   * Diese Methode wird ueberschrieben (ActionListener) um Aktionen auf Button-Klicks auszufuehren.
   * Beim Buttonklick wird ueberprueft, ob es sich um einen OK-Klick oder Abbrechen-Klick handelt und die
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
   * Hier wird die Aktion die beim druecken des "cancel-buttons" ausgefuehrt wird aufgerufen.
   * Das Dialog-Fenster wird geschlossen.
   */

  private void cancelButton_actionPerformed() {
    this.dispose();
    controller.getMainFrame().repaint();
  }

  /**
   * Hier wird die Aktion die beim druecken des "ok-buttons" ausgefuehrt wird aufgerufen.
   * Die Daten die eingegeben wurden werden dem ausgewaehlten Objekt zugewiesen und gespeichert.
   */
  private void okButton_actionPerformed() {

    // setzen der vom user eingegebenen daten in das ausgew�hlte objekt
    for( int i = 0; i < controller.getEmpfaenger().size(); i++ ) {
      if( ( ( Empfaenger )controller.getEmpfaenger().get( i ) ).getId() == objektId ) {
        if( nameTF.getText() != null ) {
          ( ( Empfaenger )controller.getEmpfaenger().get( i ) ).setName( nameTF.getText() );
        }
        else {
          ( ( Empfaenger )controller.getEmpfaenger().get( i ) ).setName( "" );
        }
        if( nachfrageMengeTF.getText() != null ) {
          ( ( Empfaenger )controller.getEmpfaenger().get( i ) ).setBenoetigteMenge( Integer.parseInt( nachfrageMengeTF.
              getText() ) );
        }
        else {
          ( ( Empfaenger )controller.getEmpfaenger().get( i ) ).setBenoetigteMenge( 0 );
        }
      }
    }
    // neu gefuellten vector uebergeben
    controller.getZeichenPane().setEmpfaenger( controller.getEmpfaenger() );
    controller.getMainFrame().repaint();
    this.dispose();
  }

}