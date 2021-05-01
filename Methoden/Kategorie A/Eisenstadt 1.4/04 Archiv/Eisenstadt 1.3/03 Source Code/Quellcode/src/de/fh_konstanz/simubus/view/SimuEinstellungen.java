package de.fh_konstanz.simubus.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.awt.image.ToolkitImage;
import sun.awt.image.URLImageSource;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.util.ImageUtil;
import de.fh_konstanz.simubus.util.Logger;

/**
 * Die Klasse <code>SimuEinstellungen</code> ist ein Fenster mit allgemeinen Einstellungen.
 * 
 * @author ???
 * @version 1.1 (04.07.2006)
 *
 */
public class SimuEinstellungen extends JFrame
{

   /**
	 * 
	 */
	private static final long serialVersionUID = 2815453319681263234L;

/** Default Einstellungen */
   private SimuKonfiguration config;

   /** Schriftart */
   private final Font        fLabel         = new Font( "Arial", Font.BOLD, 12 );

   /** Container fuer Label, etc. */
   private JPanel            main           = null;

   /** Beschriftung fuer Aufloesung */
   private JLabel            lAufloesung    = null;

   /** ComboBox fuer Aufloesung */
   private JComboBox         cAufloesung    = null;
   
   /** ComboBox fuer den Signalton */
   private JComboBox 		 cSignal 		= null;

   /** Beschriftung fuer Busgeschwindigkeit */
   private JLabel            lBusgeschw     = null;

   /** Eingabefeld fuer Busgeschwindigkeit */
   private JTextField        tBusgeschw     = null;

   /** Beschriftung fuer Anzahl Passagiere */
   private JLabel            lPassgeschw    = null;

   /** Eingabefeld fuer Anzahl Passagiere */
   private JTextField        tPassgeschw    = null;

   /** Beschriftung fuer Planquadratgroesse */
   private JLabel            lRealSizeField = null;
   
   /** Beschriftung fuer Signal Optimierung */
   private JLabel			 lSignalLabel   = null;

   /** Eingabefeld fuer Planquadratgroesse */
   private JTextField        tRealSizeField = null;
   
   /** Label für das Loggen in eine Datei */
   private JLabel            lLogToFile = null;

   /** Checkbox für das Loggen in eine Datei **/
   private JCheckBox		 cLogToFile	    = null;
   
   /** Label für das Loggen auf die Console */
   private JLabel            lLogToConsole = null;

   /** Checkbox für das Loggen auf die Konsole **/
   private JCheckBox		 cLogToConsole  = null;
   
   /** Beschriftung fuer Debuglevel */
   private JLabel            lDebugLevel    = null;

   /** ComboBox fuer Debuglevel */
   private JComboBox         cDebugLevel    = null;
   
   /** Button zum Speichern der Einstellungen */
   private JButton           bSave          = null;

   /**
    * Konstruktor
    *
    */
   public SimuEinstellungen()
   {
      super( "Einstellungen" );
      this
            .setIconImage( new ToolkitImage( new URLImageSource( ImageUtil.getImageUrl( "haltestelle.png" ) ) ) );
      config = SimuKonfiguration.getInstance();

      getContentPane().setLayout( null );

      Dimension mainDimension = config.getEinstellungenFrameDimension();
      main = new JPanel();
      main.setLayout( null );

      main.setMinimumSize( mainDimension );
      main.setPreferredSize( mainDimension );
      main.setBounds( 0, 0, mainDimension.width, mainDimension.height );

      lAufloesung = new JLabel( "Bildschirmauflösung" );
      lAufloesung.setFont( fLabel );
      lAufloesung.setBounds( 20, 20, 125, 20 );
      main.add( lAufloesung );

      cAufloesung = new JComboBox();
      cAufloesung.addItem( "1024x768" );
      cAufloesung.addItem( "1280x1024" );
      cAufloesung.setSelectedIndex( config.getActiveResolutionForCombo() );
      cAufloesung.setBounds( 140, 18, 110, 24 );
      cAufloesung.setEnabled( false );
      main.add( cAufloesung );

      lBusgeschw = new JLabel( "Busgeschwindigkeit (km/h)" );
      lBusgeschw.setFont( fLabel );
      lBusgeschw.setBounds( 20, 80, 175, 20 );
      main.add( lBusgeschw );

      tBusgeschw = new JTextField( String.valueOf( config.getBusGeschwindigkeitInKmH() ) );
      tBusgeschw.setBounds( 190, 78, 50, 24 );
      main.add( tBusgeschw );

      lPassgeschw = new JLabel( "Gehgeschwindigkeit (km/h)" );
      lPassgeschw.setFont( fLabel );
      lPassgeschw.setBounds( 20, 120, 175, 20 );
      main.add( lPassgeschw );

      tPassgeschw = new JTextField( String.valueOf( config.getGehGeschwindigkeitInKmH() ) );
      tPassgeschw.setBounds( 190, 118, 50, 24 );
      main.add( tPassgeschw );

      lRealSizeField = new JLabel( "Planquadratgrösse (m)" );
      lRealSizeField.setFont( fLabel );
      lRealSizeField.setBounds( 20, 160, 175, 20 );
      lRealSizeField.setToolTipText( "Realgrösse eines Planquadrats in Meter" );
      main.add( lRealSizeField );

      tRealSizeField = new JTextField( String.valueOf( config.getFeldElementGroesseInM() ) );
      tRealSizeField.setBounds( 190, 158, 50, 24 );
      tRealSizeField.setToolTipText( "Realgrösse eines Planquadrats in Meter" );
      main.add( tRealSizeField );
      
      lLogToConsole = new JLabel( "Auf die Konsole loggen" );
      lLogToConsole.setFont( fLabel );
      lLogToConsole.setBounds( 20, 188, 150, 24 );
      main.add( lLogToConsole );
      
      cLogToConsole = new JCheckBox("", config.isLogToConsole());
      cLogToConsole.setBounds( 188, 188, 50, 24 );
      main.add(cLogToConsole);
      
      lLogToFile = new JLabel( "In eine Datei loggen" );
      lLogToFile.setFont( fLabel );
      lLogToFile.setBounds( 20, 218, 150, 24 );
      main.add( lLogToFile );
      
      cLogToFile = new JCheckBox("", config.isLogToFile());
      cLogToFile.setBounds( 188, 218, 50, 24 );
      main.add(cLogToFile);
      
      
      lDebugLevel = new JLabel( "Logging-Granularität" );
      lDebugLevel.setFont( fLabel );
      lDebugLevel.setBounds( 20, 258, 125, 20 );
      main.add( lDebugLevel );
      
      lSignalLabel = new JLabel("Sound zur Optimierung: ");
      lSignalLabel.setFont(fLabel);
      lSignalLabel.setBounds(20,295,150,20);
      main.add(lSignalLabel);
      
      cSignal = new JComboBox();
      cSignal.addItem( "Ja" );
      cSignal.addItem( "Nein" );
      cSignal.setBounds( 190, 295, 60, 24 );
      if(config.checkSound()){
    	  cSignal.setSelectedIndex(0);
      }
      else{
    	  cSignal.setSelectedIndex(1);
      }
      cSignal.setEnabled( true );
      main.add(cSignal);

      cDebugLevel = new JComboBox();
      cDebugLevel.addItem( "Keine Ausgaben" );
      cDebugLevel.addItem( "Fatale Fehler" );
      cDebugLevel.addItem( "Fehler" );
      cDebugLevel.addItem( "Debug" );
      cDebugLevel.setSelectedItem( config.getCurrentLoglevel() );
      cDebugLevel.setBounds( 140, 258, 110, 24 );
      cDebugLevel.setEnabled( true );
      main.add( cDebugLevel );
      

      bSave = new JButton( "Speichern" );
      bSave.setActionCommand( "saveEinstellungen" );
      bSave.setBounds( 150, 330, 100, 24 );
      bSave.addMouseListener( new BusButtonListener() );
      main.add( bSave );

      getContentPane().add( main );
   }

   /**
    * speichert die Einstellungen
    *
    */
   public void save()
   {
      config.setActiveResolutionFromCombo( cAufloesung.getSelectedIndex() );

      try
      {
         config.setBusGeschwindigkeitInKmH( Double.parseDouble( tBusgeschw.getText() ) );
         config.setGehGeschwindigkeitInKmH( Double.parseDouble( tPassgeschw.getText() ) );
         config.setFeldElementGroesseInM( Double.parseDouble( tRealSizeField.getText() ) );
         config.setLogToConsole(cLogToConsole.isSelected());
         config.setLogToFile(cLogToFile.isSelected());
         config.setSound(cSignal.getSelectedItem());
         config.setCurrentLogLevel((String)cDebugLevel.getSelectedItem());
      }
      catch ( NumberFormatException e )
      {
         JOptionPane.showMessageDialog( this, "Fehler bei Zahleneingaben!", "Bus Simulation",
               JOptionPane.ERROR_MESSAGE );
      }
   }

   /**
    * Listener fuer Speichern Button
    * 
    * @author ???
    *
    */
   private class BusButtonListener extends MouseAdapter
   {
      @Override
      public void mouseClicked( MouseEvent evt )
      {
         save();

         setVisible( false );
         dispose();
      }
   }
}
