package trOptimizer;
/**
 * <p>Title: MainFrame_AboutBox</p>
 * <p>Description: Die Klasse dient als About-Box. Sie enth�lt lediglich Informationen �ber die Anwendung (Die Verantwortlichen bzw. Betreuer).</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class MainFrame_AboutBox extends JDialog implements ActionListener {

  //Klassenattribute
  JPanel panel1 = new JPanel();
  JButton button1 = new JButton();
  JLabel imageLabel = new JLabel();
  ImageIcon image1 = new ImageIcon();
  String product = "";
  String version = "1.0";
  String copyright = "Copyright (c) 2003";
  String comments = "";
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  private JLabel jLabel9 = new JLabel();
  private JLabel jLabel10 = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private JLabel jLabel12 = new JLabel();
  private JLabel jLabel13 = new JLabel();
  private JLabel jLabel14 = new JLabel();
  private JLabel jLabel15 = new JLabel();
  public MainFrame_AboutBox( Frame parent ) {
    super( parent );
    enableEvents( AWTEvent.WINDOW_EVENT_MASK );
    try {
      jbInit();
    }
    catch( Exception e ) {
      e.printStackTrace();
    }
  }

  /**
   * Diese Methode dient zur Initialisierung der Komponenten.
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    image1 = new ImageIcon( MainFrame.class.getResource( "about.png" ) );
    imageLabel.setIcon( image1 );
    this.getContentPane().setBackground( new Color( 233, 233, 233 ) );
    this.setTitle( "Über" );
    this.getContentPane().setLayout( xYLayout5 );
    panel1.setLayout( xYLayout1 );
    button1.setBackground( Color.white );
    button1.setText( "Ok" );
    button1.addActionListener( this );
    panel1.setBackground( new Color( 233, 233, 233 ) );
    jLabel1.setText( "erstellt:" );
    jLabel2.setText( "Oliver Baumann" );
    jLabel3.setText( "Gunther Koch" );
    jLabel4.setText( "Ekkehard Will" );
    jLabel5.setText( "betreut:" );
    jLabel6.setText( "Prof. Dr. Michael Grütz" );
    jLabel7.setText( "v1.0, WS 2002/2003, Wirtschaftsinformatik (SE)" );
    jLabel8.setBackground( new Color( 212, 208, 255 ) );
    jLabel8.setFont( new java.awt.Font( "Dialog", 1, 14 ) );
    jLabel8.setText( "TransOp 2" );
    xYLayout5.setWidth(264 );
    xYLayout5.setHeight(358 );
    jLabel9.setText("überarbeitet:");
    jLabel10.setText("betreut:");
    jLabel11.setText("v2.0, WS 2003/2004, Wirtschaftsinformatik");
    jLabel12.setText("________________________________________");
    jLabel13.setText("Stefanie Brauchle");
    jLabel14.setText("Konstanze Czierpka");
    jLabel15.setText("Prof. Dr. Michael Grütz");
    panel1.add( jLabel1, new XYConstraints( 15, 53, 49, 26 ) );
    panel1.add( jLabel5, new XYConstraints( 15, 124, 63, 17 ) );
    panel1.add( jLabel6, new XYConstraints( 82, 123, 136, 19 ) );
    panel1.add( jLabel4,  new XYConstraints(83, 95, 117, 25) );
    panel1.add( jLabel3,  new XYConstraints(83, 78, 113, 17) );
    panel1.add( jLabel2,  new XYConstraints(83, 53, 110, 26) );
    panel1.add( jLabel8, new XYConstraints( 85, 11, 121, 28 ) );
    panel1.add( imageLabel, new XYConstraints( 15, 11, -1, -1 ) );

    panel1.add(button1, new XYConstraints(104, 324, -1, -1));
    panel1.add(jLabel9, new XYConstraints(15, 209, 74, 26));
    panel1.add(jLabel12,new XYConstraints(15, 181, 240, 25));
    panel1.add(jLabel13,new XYConstraints(87, 209, 110, 26));
    panel1.add(jLabel14,new XYConstraints(87, 230, 110, 26));
    panel1.add(jLabel15,new XYConstraints(87, 262, 136, 19));
    panel1.add(jLabel10,new XYConstraints(15, 263, 63, -1));
    panel1.add(jLabel7, new XYConstraints(15, 145, 240, 25));
    panel1.add(jLabel11, new XYConstraints(15, 284, 240, 25));
    this.getContentPane().add( panel1,  new XYConstraints(0, 0, 263, 361) );
    setResizable( true );
  }

  /**
   * Ueberschriebene Methode um die Informations- bzw. Dialog-Box zu schliessen.
   *
   * @param e ein WindowEvent
   */
  protected void processWindowEvent( WindowEvent e ) {
    if( e.getID() == WindowEvent.WINDOW_CLOSING ) {
      cancel();
    }
    super.processWindowEvent( e );
  }

  /**
   * Diese Methode ist fuer das Schliessen der Dialog-Box zustaendig.
   *
   */
  void cancel() {
    dispose();
  }

  /**
   * Ueberschriebene Methode um auf Ereignisse zu reagieren. Z.B. um Fenster zu schliessen bei OK-Buttonklick.
   *
   * @param e ein ActionEvent
   */
  public void actionPerformed( ActionEvent e ) {
    if( e.getSource() == button1 ) {
      cancel();
    }
  }
}
