package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import sun.awt.image.ToolkitImage;
import sun.awt.image.URLImageSource;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.model.SimpleDate;
import de.fh_konstanz.simubus.util.ImageUtil;

/**
 * Die Klasse <code>BuslinieDetail<code> zeigt Informationen zu einer Buslinie
 * in einem Fenster.
 * 
 * @author unknown
 * @version 1.0
 *
 */
public class BuslinieDetail extends JFrame
{

   /**
	 * 
	 */
	private static final long serialVersionUID = -4288545297113866572L;

/** Linie, die angezeigt wird */
   private Linie      linie;

   /** Schriftart */
   private final Font labelFont = new Font( "Arial", Font.PLAIN, 10 );

   /** Panel fuer Informationen */
   private JPanel     main;

   /** zeigt Format fuer Frequenz */
   private JLabel     lFrequenzInfo;

   /** Ueberschrift fuer Haltestellen-Liste */
   private JLabel     haltestellen;

   /** Liste mit Haltestellen */
   private JList      lHaltestellen;

   /** Beschriftung fuer Frequenz */
   private JLabel     lFrequenz;

   /** Eingabefeld fuer Frequenz */
   private JTextField tFrequenz;

   /** Beschriftung fuer Anzahl Passagiere */
   private JLabel     lAnzahlPass;

   /** Eingabefeld fuer Anzahl Passagiere */
   private JTextField tAnzahlPass;

   /** Beschriftung fuer Linienname */
   private JLabel     lName;

   /** Eingabefeld fuer Linienname */
   private JTextField tName;

   /** Beschriftung fuer Farbe der Linie */
   private JLabel     lFarbe;

   /** Button zum Oeffnen des Farbdialog */
   private JButton    bFarbe;

   /** Button zum Speichern */
   private JButton    bSave;

   /** Button zum Abbrechen */
   private JButton    bOK;

   /**
    * Konstruktor
    * 
    * @param aLinie
    *           Linie, die angezeigt werden soll
    */
   public BuslinieDetail( Linie aLinie )
   {
      super( "Buslinie bearbeiten" );
      this
            .setIconImage( new ToolkitImage( new URLImageSource( ImageUtil.getImageUrl( "haltestelle.png" ) ) ) );
      linie = aLinie;

      getContentPane().setLayout( null );

      main = new JPanel();
      main.setLayout( null );
      main.setMinimumSize( new Dimension( 600, 600 ) );
      main.setPreferredSize( new Dimension( 600, 600 ) );
      main.setBounds( 0, 0, 600, 600 );

      haltestellen = new JLabel( "Haltestellen" );
      haltestellen.setBounds( 30, 15, 150, 16 );
      main.add( haltestellen );

      lHaltestellen = new JList();
      lHaltestellen.setName( "haltestellen" );
      lHaltestellen.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
      lHaltestellen.setDragEnabled( true );

      JScrollPane scrollPaneHaltestellen = new JScrollPane( lHaltestellen );
      scrollPaneHaltestellen.setBorder( BorderFactory.createLineBorder( Color.GRAY, 1 ) );
      scrollPaneHaltestellen.setBounds( 30, 35, 150, 450 );
      main.add( scrollPaneHaltestellen );

      lFrequenz = new JLabel( "Frequenz der Buslinie" );
      lFrequenz.setBounds( 260, 15, 350, 16 );
      main.add( lFrequenz );

      tFrequenz = new JTextField();
      tFrequenz.setBounds( 260, 35, 50, 22 );
      main.add( tFrequenz );

      lFrequenzInfo = new JLabel( "(Angabe in HH:MM)" );
      lFrequenzInfo.setBounds( 320, 35, 250, 22 );
      lFrequenzInfo.setFont( labelFont );
      main.add( lFrequenzInfo );

      lAnzahlPass = new JLabel( "Maximale Anzahl der Passagiere pro Bus" );
      lAnzahlPass.setBounds( 260, 75, 350, 16 );
      main.add( lAnzahlPass );

      tAnzahlPass = new JTextField();
      tAnzahlPass.setBounds( 260, 95, 50, 22 );
      main.add( tAnzahlPass );

      lName = new JLabel( "Name der Busline" );
      lName.setBounds( 260, 135, 350, 16 );
      main.add( lName );

      tName = new JTextField();
      tName.setBounds( 260, 155, 120, 22 );
      main.add( tName );

      lFarbe = new JLabel( "Buslinenfarbe" );
      lFarbe.setBounds( 260, 195, 350, 16 );
      main.add( lFarbe );

      bFarbe = new JButton( "" );
      bFarbe.setName( "farbe" );
      bFarbe.setBounds( 260, 215, 20, 20 );
      bFarbe.setBackground( linie.getLinienfarbe() );
      bFarbe.addMouseListener( new BusButtonListener() );
      main.add( bFarbe );

      bSave = new JButton( "Speichern" );
      bSave.setName( "speichern" );
      bSave.setBounds( 360, 490, 100, 24 );
      bSave.addMouseListener( new BusButtonListener() );
      main.add( bSave );

      bOK = new JButton( "OK" );
      bOK.setName( "OK" );
      bOK.setBounds( 260, 490, 100, 24 );
      bOK.addMouseListener( new BusButtonListener() );
      main.add( bOK );

      initialize();
      getContentPane().add( main );
   }

   /**
    * initialisiert die Eingabefelder
    * 
    */
   private void initialize()
   {
      tName.setText( linie.getId() );

      List<Haltestelle> haltestellen = linie.getHaltestellen();

      DefaultListModel defModel = new DefaultListModel();

      for ( int i = 0; i < haltestellen.size(); i++ )
      {
         defModel.addElement( haltestellen.get( i ) );
      }

      lHaltestellen.setModel( defModel );

      // tFrequenz.setText(String.valueOf( linie.getFrequenz() ) );
      tFrequenz.setText( new SimpleDate( linie.getFrequenz() ).toString() );
      tAnzahlPass.setText( String.valueOf( linie.getMaxKapBusPassagiere() ) );

   }

   /**
    * speichert die Liniendaten
    * 
    */
   private void saveLinienDaten()
   {

      try
      {
         // wenn keine Wiederkehrzeit eingegeben wird gibt es keine und wir
         // speichern den Wert 0
         if ( !tFrequenz.getText().equals( "0.0" ) )
         {
            linie.setFrequenz( Double.valueOf( getSimtime( tFrequenz.getText() ) ) );
            linie.setMaxKapBusPassagiere( Integer.valueOf( tAnzahlPass.getText() ) );
         }
         else
         {
            linie.setMaxKapBusPassagiere( Integer.valueOf( tAnzahlPass.getText() ) );
            linie.setFrequenz( 0 );
         }

         if ( !tName.getText().trim().equals( "" ) )
            linie.setId( tName.getText().trim() );
      }
      catch ( NumberFormatException e )
      {
         JOptionPane.showMessageDialog( this, "Fehler bei Eingabe!", "Bus Simulation",
               JOptionPane.ERROR_MESSAGE );
      }
      catch ( Exception ex )
      {
         JOptionPane.showMessageDialog( this, "Fehler bei Zeiteingabe!", "Bus Simulation",
               JOptionPane.ERROR_MESSAGE );
      }

   }

   /**
    * formattiert Frequenz um
    * 
    * @param num Frequenz
    * @return formattierte Frequenz
    * @throws Exception falls beim Formattieren ein Fehler auftritt
    */
   private String getSimtime( String num ) throws Exception
   {
      int delim = num.indexOf( ":" );
      if ( delim == -1 || ( num.length() - delim ) != 3 )
         throw new Exception();

      String tmp = num.substring( 0, delim );
      int result = Integer.parseInt( tmp );
      if ( result < 0 || result > 23 )
         throw new Exception();
      result = result * 60;

      tmp = num.substring( delim + 1, num.length() );
      int min = Integer.parseInt( tmp );
      if ( min < 0 || min > 59 )
         throw new Exception();
      result += min;

      return String.valueOf( result );
   }

   /**
    * Listner fuer Buttons
    * 
    * @author ???
    * 
    */
   private class BusButtonListener extends MouseAdapter
   {
      @Override
      public void mouseClicked( MouseEvent evt )
      {
         if ( evt.getSource() instanceof JButton )
         {
            JButton btn = (JButton) evt.getSource();
            if ( btn.getName().equals( "speichern" ) )
            {
               // speichere Eingabedaten Wiederkehrzeit und
               // maxAnzahlPassagiereImBus
               saveLinienDaten();
               setVisible( false );
               dispose();
            }
            else if ( btn.getName().equals( "farbe" ) )
            {
               Color color = JColorChooser.showDialog( bFarbe, "Buslinienfarbe", linie.getLinienfarbe() );

               if ( color != null )
               {
                  bFarbe.setBackground( color );
                  linie.setLinienfarbe( color );
                  SimuPanel.getInstance().setColorSelectedLinie( color );
               }
            }
            else if ( btn.getName().equals( "OK" ) )
            {
               setVisible( false );
               dispose();
            }
         }
      }
   }
}
