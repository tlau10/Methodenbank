package trOptimizer;

/**
 * <p>Title: MainFrame</p>
 * <p>Description: Die Klasse MainFrame stellt die Haupt-GUI der Anwendung dar. Sie enthält alle Komponenten zum Zeichen, etc.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.help.JHelpIndexNavigator;
import javax.help.JHelpNavigator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class MainFrame extends JFrame implements ActionListener {

  //Klassenattribute
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem oeffnenMenue = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem ueberMenue = new JMenuItem();
  XYLayout xYLayout1 = new XYLayout();
  //JPanel mainPane = new JPanel();
  XYLayout xYLayout2 = new XYLayout();
  ZeichenPane zeichenPane = new ZeichenPane();
  SymbolPane symbolPane = new SymbolPane();
  XYLayout xYLayout5 = new XYLayout();
  JPanel ergebnisPane = new JPanel();
  XYLayout xYLayout6 = new XYLayout();
  Border border1;
  boolean bereitsGespeichert = false;
  File selFile;

  // Eigene Variablen

  TransopController controller;
  XYLayout xYLayout3 = new XYLayout();
  TransporterPane transporterPane = new TransporterPane();
  XYLayout xYLayout4 = new XYLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea ergebnisTA = new JTextArea();
  JButton berechneButton = new JButton();
  JTextField gesamtTF = new JTextField();
  JLabel ergebnis = new JLabel();
  JLabel gesamt = new JLabel();
  private JMenuItem speichernMenue = new JMenuItem();
  private JMenuItem beendenMenue = new JMenuItem();
  private JMenuItem speichernUnter = new JMenuItem();
  private JMenuItem neuMenue = new JMenuItem();
  private JMenu jMenuSolver = new JMenu();
  private JCheckBoxMenuItem jCheckBoxMenuItem1 = new JCheckBoxMenuItem();
  private JCheckBoxMenuItem jCheckBoxMenuItem2 = new JCheckBoxMenuItem();
  private JCheckBoxMenuItem jCheckBoxMenuItem3 = new JCheckBoxMenuItem();
  private int solvertyp=1;

  /**
   * Konstruktor der Klasse
   */
  public MainFrame() {
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

    contentPane = ( JPanel )this.getContentPane();
    contentPane.setLayout( xYLayout1 );
    this.setResizable( false );
    this.setSize( new Dimension( 600, 685 ) );
    this.setTitle( "TR-Optimizer" );
    jMenuFile.setBackground( Color.white );
    jMenuFile.setText( "Datei" );
    oeffnenMenue.setBackground( Color.white );
    oeffnenMenue.setText( "Oeffnen" );
    oeffnenMenue.addActionListener( this );

    jMenuHelp.setBackground( Color.white );
    jMenuHelp.setText( "Hilfe" );

    ueberMenue.setBackground( Color.white );
    ueberMenue.setText( "Über" );
    ueberMenue.addActionListener( this );

    contentPane.setBackground( new Color( 233, 233, 233 ) );
    contentPane.setAlignmentY( ( float )0.5 );
    contentPane.setBorder( BorderFactory.createEtchedBorder() );

    //contentPane.setPreferredSize(new Dimension(3, 15));
    //mainPane.setLayout(xYLayout2);
    zeichenPane.setBackground( Color.red );
    zeichenPane.setDebugGraphicsOptions( 0 );
    zeichenPane.setLayout( xYLayout3 );
    zeichenPane.addMouseListener( new ZeichenAdapter( this ) );

    symbolPane.setBackground( Color.blue );
    symbolPane.setForeground( Color.black );
    symbolPane.addMouseListener( new SymbolAdapter( this ) );
    symbolPane.setLayout( xYLayout5 );
    ergebnisPane.setBackground(new Color(192, 192, 217) );
    ergebnisPane.setLayout( xYLayout6 );
    //mainPane.setBackground(SystemColor.control);
    transporterPane.setBackground( new Color( 160, 160, 166 ) );
    transporterPane.setLayout( xYLayout4 );
    transporterPane.addMouseListener( new TransporterAdapter( this ) );

    jMenuBar1.setBackground( Color.white );
    ergebnisTA.setFont( new java.awt.Font( "SansSerif", 1, 10 ) );
    ergebnisTA.setEditable( false );
    ergebnisTA.setText( "" );
    berechneButton.setBackground( Color.white );
    berechneButton.setText( "los" );
    berechneButton.addActionListener(new MainFrame_berechneButton_actionAdapter(this));
    berechneButton.addActionListener( this );
    gesamtTF.setFont( new java.awt.Font( "SansSerif", 1, 10 ) );
    gesamtTF.setPreferredSize( new Dimension( 57, 21 ) );
    gesamtTF.setText( "" );
    gesamtTF.setEditable( false );
    ergebnis.setText( "Ergebnis" );
    gesamt.setText( "Gesamt" );
    speichernMenue.setBackground( Color.white );
    speichernMenue.setText( "Speichern" );
    speichernMenue.addActionListener( this );
    speichernMenue.setEnabled( false );
    beendenMenue.setBackground( Color.white );
    beendenMenue.setText( "Beenden" );
    beendenMenue.addActionListener( this );
    speichernUnter.setBackground( Color.white );
    speichernUnter.setText( "Speichern unter" );
    speichernUnter.addActionListener( this );
    neuMenue.setBackground( Color.white );
    neuMenue.setText( "Neu" );
    neuMenue.addActionListener( this );
    jMenuSolver.setBackground(Color.white);
    jMenuSolver.setText("Solver");
    jCheckBoxMenuItem1.setBackground(Color.white);
    jCheckBoxMenuItem1.setSelected(true);
    jCheckBoxMenuItem1.setText("LP-Solve");
    jCheckBoxMenuItem1.addItemListener(new MainFrame_jCheckBoxMenuItem1_itemAdapter(this));
    jCheckBoxMenuItem2.setBackground(Color.white);
    jCheckBoxMenuItem2.setText("Weidenauer");
    jCheckBoxMenuItem2.addItemListener(new MainFrame_jCheckBoxMenuItem2_itemAdapter(this));
    jCheckBoxMenuItem3.setBackground(Color.white);
    jCheckBoxMenuItem3.setEnabled(true);
    jCheckBoxMenuItem3.setText("MOPS");
    jCheckBoxMenuItem3.addItemListener(new MainFrame_jCheckBoxMenuItem3_itemAdapter(this));
    jMenuFile.add( neuMenue );
    jMenuFile.add( oeffnenMenue );
    jMenuFile.add( speichernUnter );
    //jMenuFile.add(speichernMenue);
    jMenuFile.add( beendenMenue );
    jMenuHelp.add( ueberMenue );
    jMenuBar1.add( jMenuFile );
    jMenuBar1.add(jMenuSolver);
    jMenuBar1.add( jMenuHelp );

    contentPane.add( zeichenPane, new XYConstraints( 5, 0, 515, 400 ) );
    contentPane.add( symbolPane, new XYConstraints( 520, 0, 65, 400 ) ); //mainPane.add(ergebnisPane, new XYConstraints(0, 500, 600, 100));
    contentPane.add( transporterPane, new XYConstraints( 5, 400, 515, 70 ) );
    contentPane.add( ergebnisPane, new XYConstraints( 5, 479, 580, 148 ) );
    ergebnisPane.add( jScrollPane1, new XYConstraints( 4, 17, 505, 126 ) );
    ergebnisPane.add( gesamtTF, new XYConstraints( 515, 18, 63, 20 ) );
    ergebnisPane.add( ergebnis, new XYConstraints( 5, 0, 97, 15 ) );
    ergebnisPane.add( gesamt, new XYConstraints( 515, 0, 62, -1 ) );
    contentPane.add( berechneButton, new XYConstraints( 522, 450, 65, 20 ) );
    jScrollPane1.getViewport().add( ergebnisTA, null );
    jMenuSolver.add(jCheckBoxMenuItem1);
    jMenuSolver.add(jCheckBoxMenuItem2);
    jMenuSolver.add(jCheckBoxMenuItem3);
    this.setJMenuBar( jMenuBar1 );

    // eigene Methodenaufrufe
    controller = new TransopController( this );
    controller.setZeichenPane( zeichenPane );

    controller.setTransporterPane( transporterPane );

  }

  /**
   * Diese Methode dient dazu, um Events des Menues abzufangen und die dazugehörigen Aktion ausführen.
   *
   * @param e zugehöriges actionEvent
   */
  public void actionPerformed( ActionEvent e ) {

    if( bereitsGespeichert ) {
      speichernMenue.setEnabled( true );
    }
    else {
      speichernMenue.setEnabled( false );
    }

    // was wurde im menue ausgewaehlt?
    if( e.getActionCommand().equalsIgnoreCase( "Ueber" ) ) {
      this.ueberMenue_actionPerformed( e );
    }
    else if( e.getActionCommand().equalsIgnoreCase( "Speichern" ) ) {
      this.speichernMenue_actionPerformed( e );
    }
    else if( e.getActionCommand().equalsIgnoreCase( "Beenden" ) ) {
      this.beendenMenue_actionPerformed( e );
    }
    else if( e.getActionCommand().equalsIgnoreCase( "Oeffnen" ) ) {
      this.oeffnenMenue_actionPerformed( e );
    }
    else if( e.getActionCommand().equalsIgnoreCase( "los" ) ) {
      this.berechneButton_actionPerformed( e );
    }
    else if( e.getActionCommand().equalsIgnoreCase( "Speichern unter" ) ) {
      this.speichernUnterMenue_actionPerformed( e );
    }
    else if( e.getActionCommand().equalsIgnoreCase( "neu" ) ) {
      this.neuMenue_actionPerformed( e );
    }
    else if( e.getActionCommand().equalsIgnoreCase( "Über" ) ) {
      this.ueberMenue_actionPerformed( e );
    }

    jMenuFile.updateUI();
  }

  /**
   * Diese Methode wird aufgerufen, wenn im Menue beenden ausgewählt wurde.
   * Sie beendet die Anwendung.
   *
   * @param e
   */
  public void beendenMenue_actionPerformed( ActionEvent e ) {
    System.exit( 0 );
  }

  /**
   * Diese Methode wird aufgerufen, wenn im Menue hilfe->ueber ausgewaehlt wurde.
   * Sie veranlasst das Erzeugen und erscheinen der About-Box.
   * @param e
   */
  public void ueberMenue_actionPerformed( ActionEvent e ) {

		JHelp helpViewer = null;
		try {
			// Hauptfenster in der nächsten Zeile ersetzen durch aktuellen
			// Klassennamen
			ClassLoader cl = MainFrame.class.getClassLoader();
			URL url = HelpSet.findHelpSet(cl, "jhelpset.hs");
			helpViewer = new JHelp(new HelpSet(cl, url));


			Enumeration eNavigators = helpViewer.getHelpNavigators();
			while (eNavigators.hasMoreElements()) {
				JHelpNavigator nav = (JHelpNavigator) eNavigators.nextElement();
				if (nav instanceof JHelpIndexNavigator) {
					helpViewer.removeHelpNavigator(nav);
				}
			}
		} catch (Exception ex) {
			System.err.println("API Help Set not found");
		}

			JFrame frame = new JFrame();
			frame.setTitle("Hilfe zu Ernährungsplaner 1.0");
			frame.setSize(800, 600);
			frame.getContentPane().add(helpViewer);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
  }

  /**
   * Diese Methode wird aufgerufen, wenn im Menue speichern ausgewaehlt wurde.
   * Sie erzeugt dazu ein neues Speichern-Objekt und speichert die aktuelle Einstellungen in einer Datei.
   * @param e
   */
  public void speichernMenue_actionPerformed( ActionEvent e ) {
    Speichern sp = new Speichern( this );
    sp.speichernObjekt( selFile );
  }

  /**
   * Diese Methode wird aufgerufen, wenn im Menue oeffnen ausgewaehlt wurde.
   * Sie erzeugt ein neues Oeffnen-Objekt und öffnet die die vom Benutzer ausgewählte Datei. (*.transop)
   *
   * @param e
   */
  public void oeffnenMenue_actionPerformed( ActionEvent e ) {
    Oeffnen of = new Oeffnen(this);

//    String filename = File.separator + "transop";
    JFileChooser fc = new JFileChooser(new File("Daten"));
    //fc.addChoosableFileFilter(new MyFilter());

    // Show open dialog; this method does not return until the dialog is closed
    fc.showOpenDialog( new JFrame() );
    File oeffneFile = fc.getSelectedFile();

    of.oeffnenObjekte(oeffneFile );
  }

  /**
   * Diese Methode wird aufgerufen, wenn auf der Arbeitsflaeche der berechnen-Button gedrueckt wurde.
   * Sie veranlasst, die Berechnung der Ergebnisse.
   *
   * @param e
   */
  public void berechneButton_actionPerformed( ActionEvent e ) {

    // bereits angelegte virtuelle empfaenger loeschen
    for( int i = 0; i < controller.getEmpfaenger().size(); i++ ) {
      if( ( ( Empfaenger )controller.getEmpfaenger().get( i ) ).getId() == 0 ) {
        controller.getEmpfaenger().remove( i );
      }
    }

    // bereits angelegte virtuelle produzenten loeschen
    for( int i = 0; i < controller.getProduzenten().size(); i++ ) {
      if( ( ( Produzent )controller.getProduzenten().get( i ) ).getId()== 0 ) {
        controller.getProduzenten().remove( i );
      }
    }

    // bereits angelegte virtuelle strecken loeschen
    for( int i = 0; i < controller.getStrecken().size(); i++ ) {
      if( ( ( Strecke )controller.getStrecken().get( i ) ).getId() == 0 ) {
        controller.getStrecken().remove( i );
      }
    }

    if(jCheckBoxMenuItem1.isSelected())
    {solvertyp=1;}
    if(jCheckBoxMenuItem2.isSelected())
    {solvertyp=2;}
    if(jCheckBoxMenuItem3.isSelected())
    {solvertyp=3;}

    Solve solver = new Solve( solvertyp, controller.getEmpfaenger(), controller.getProduzenten(), controller.getStrecken(),
        controller.getTransporter() );

    String ausgabe = controller.parseErg( solver.getErgebnis() );
    String gesamt = controller.berechneGesamt( solver.getErgebnis() );
    ergebnisTA.setText( ausgabe );
    gesamtTF.setText( gesamt );

  }

  /**
   * Diese Methode wird aufgerufen, wenn im Menue "speichern unter" ausgewaehlt wurde.
   * Sie erzeugt dazu ein neues Speichern-Objekt und speichert die aktuelle Einstellungen in einer Datei.
   * @param e
   */
  public void speichernUnterMenue_actionPerformed( ActionEvent e ) {
    bereitsGespeichert = true;
    String filename = File.separator + "transop";
    JFileChooser fc = new JFileChooser( new File( filename ) );


    // Show save dialog; this method does not return until the dialog is closed
    fc.showSaveDialog( new JFrame() );
    selFile = fc.getSelectedFile();
    String tmp = selFile.getAbsolutePath() + ".transop";
    selFile = new File( tmp );
    Speichern sp = new Speichern( this );
    sp.speichernObjekt( selFile );
  }

  /**
   * Diese Methode wird aufgerufen, wenn im Menue neu gewaehlt wurde.
   * Sie veranlasst das löschen der aktuellen Objekte und das Neuzeichnen einer leeren Arbeitsfläche.
   * @param e
   */
  public void neuMenue_actionPerformed( ActionEvent e ) {
    controller.leereAlleVectoren();
    controller.setTransportPos( 520 );
    bereitsGespeichert = false;
  }

  /**
   * Diese Methode wird aufgerufen, wenn schliessen oben rechts gedrueckt wurde.
   * Sie veranlasst das Beenden der Anwendung.
   * @param e
   */
  protected void processWindowEvent( WindowEvent e ) {
    super.processWindowEvent( e );
    this.repaint();
    if( e.getID() == WindowEvent.WINDOW_CLOSING ) {
      beendenMenue_actionPerformed( null );
    }
  }

  /**
   * Diese Methode liefert den aktuell gültigen TransopController zurück.
   *
   * @return gueltiger controller der Anwendung.
   */
  public TransopController getController() {
    return controller;
  }




  void jCheckBoxMenuItem1_itemStateChanged(ItemEvent e) {
    if(jCheckBoxMenuItem1.isSelected())
    {
      jCheckBoxMenuItem2.setSelected(false);
      jCheckBoxMenuItem3.setSelected(false);
      jCheckBoxMenuItem1.setSelected(true);
    }
    else if(jCheckBoxMenuItem2.isSelected() || jCheckBoxMenuItem3.isSelected())
    {
      jCheckBoxMenuItem1.setSelected(false);
    }
    else
    {
      jCheckBoxMenuItem1.setSelected(true);
    }
  }

  void jCheckBoxMenuItem2_itemStateChanged(ItemEvent e) {
    if(jCheckBoxMenuItem2.isSelected())
    {
      jCheckBoxMenuItem1.setSelected(false);
      jCheckBoxMenuItem3.setSelected(false);
      jCheckBoxMenuItem2.setSelected(true);
    }
    else if(jCheckBoxMenuItem1.isSelected() || jCheckBoxMenuItem3.isSelected())
    {
      jCheckBoxMenuItem2.setSelected(false);
    }
    else
    {
      jCheckBoxMenuItem2.setSelected(true);
    }
  }

  void jCheckBoxMenuItem3_itemStateChanged(ItemEvent e) {
    if(jCheckBoxMenuItem3.isSelected())
    {
      jCheckBoxMenuItem1.setSelected(false);
      jCheckBoxMenuItem2.setSelected(false);
      jCheckBoxMenuItem3.setSelected(true);
    }
    else if(jCheckBoxMenuItem1.isSelected() || jCheckBoxMenuItem2.isSelected())
    {
      jCheckBoxMenuItem3.setSelected(false);
    }
    else
    {
      jCheckBoxMenuItem3.setSelected(true);
    }
  }
}

class MainFrame_berechneButton_actionAdapter implements java.awt.event.ActionListener {
  private MainFrame adaptee;

  MainFrame_berechneButton_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.berechneButton_actionPerformed(e);
  }
}

class MainFrame_jCheckBoxMenuItem1_itemAdapter implements java.awt.event.ItemListener {
  private MainFrame adaptee;

  MainFrame_jCheckBoxMenuItem1_itemAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.jCheckBoxMenuItem1_itemStateChanged(e);
  }
}

class MainFrame_jCheckBoxMenuItem2_itemAdapter implements java.awt.event.ItemListener {
  private MainFrame adaptee;

  MainFrame_jCheckBoxMenuItem2_itemAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.jCheckBoxMenuItem2_itemStateChanged(e);
  }
}

class MainFrame_jCheckBoxMenuItem3_itemAdapter implements java.awt.event.ItemListener {
  private MainFrame adaptee;

  MainFrame_jCheckBoxMenuItem3_itemAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.jCheckBoxMenuItem3_itemStateChanged(e);
  }
}
