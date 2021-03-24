package trOptimizer;

/**
 * <p>Title: Produzent_Box</p>
 * <p>Description: Diese Klasse stellt das Dialog-Fenster zur Eingabe von Produzenten-Daten dar.</p>
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

public class Produzent_Box extends JDialog implements ActionListener {

  //Klassenattribute
  XYLayout xYLayout1 = new XYLayout();
  JLabel ueberschrift = new JLabel();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  JLabel name = new JLabel();
  JLabel lieferMenge = new JLabel();
  JTextField lieferMengeTF = new JTextField();
  JTextField nameTF = new JTextField();

  private TransopController controller;
  private int objektId;
  private Produzent produzent;

  /**
   * Konstruktor
   *
   * @param c gueltiger controller
   * @param id des zugehoerigen empfaengers
   * @throws HeadlessException
   */

  public Produzent_Box( TransopController c, int id ){//Headless

    controller = c;
    objektId = id;

    //hol den richtigen Produzent raus
    for( int i = 0; i < controller.getProduzenten().size(); i++ ) {
      if( ( ( Produzent )controller.getProduzenten().get( i ) ).getId() == objektId ) {
        produzent = ( Produzent )controller.getProduzenten().get( i );
      }
    }

    this.setTitle( "Produzent Daten" );
    try {
      jbInit();
    }
    catch( Exception e ) {
      e.printStackTrace();
    }
  }

  /**
   * Die Init-Methode dient zur Initialisierung saemtlicher Klassenattribute.
   *
   * @throws Exception
   */

  private void jbInit() throws Exception {
    ueberschrift.setFont( new java.awt.Font( "Dialog", 1, 22 ) );
    ueberschrift.setHorizontalAlignment( SwingConstants.LEFT );
    ueberschrift.setHorizontalTextPosition( SwingConstants.LEFT );
    ueberschrift.setText( "Produzent Daten" );
    this.getContentPane().setLayout( xYLayout1 );
    okButton.setText( "OK" );
    okButton.addActionListener( this );
    cancelButton.setText( "Cancel" );
    cancelButton.addActionListener( this );
    name.setText( "Name" );
    lieferMenge.setText( "Liefermenge" );
    lieferMengeTF.setText( String.valueOf( produzent.getLieferMenge() ) );
    nameTF.setText( produzent.getName() );
    xYLayout1.setWidth( 309 );
    xYLayout1.setHeight( 249 );
    this.getContentPane().setBackground( new Color( 233, 233, 233 ) );
    this.getContentPane().add( ueberschrift, new XYConstraints( 25, 0, 228, 40 ) );
    this.getContentPane().add( lieferMenge, new XYConstraints( 25, 120, 120, 25 ) );
    this.getContentPane().add( name, new XYConstraints( 25, 70, 123, 25 ) );
    this.getContentPane().add( cancelButton,  new XYConstraints(189, 177, 80, 23) );
    this.getContentPane().add( okButton, new XYConstraints( 114, 177, 53, 23 ) );
    this.getContentPane().add( lieferMengeTF, new XYConstraints( 140, 122, 120, 20 ) );
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

    for( int i = 0; i < controller.getProduzenten().size(); i++ ) {
      if( ( ( Produzent )controller.getProduzenten().get( i ) ).getId() == objektId ) {
        if( nameTF.getText() != null ) {
          ( ( Produzent )controller.getProduzenten().get( i ) ).setName( nameTF.getText() );
        }
        else {
          ( ( Produzent )controller.getProduzenten().get( i ) ).setName( "" );
        }
        if( lieferMengeTF.getText() != null ) {
          ( ( Produzent )controller.getProduzenten().get( i ) ).setLieferMenge( Integer.parseInt( lieferMengeTF.getText() ) );
        }
        else {
          ( ( Produzent )controller.getProduzenten().get( i ) ).setLieferMenge( 0 );
        }
      }
    }
    controller.getZeichenPane().setProduzenten( controller.getProduzenten() );
    controller.getMainFrame().repaint();
    this.dispose();
  }

}