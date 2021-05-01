package de.fh_konstanz.simubus.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fh_konstanz.simubus.util.ImageUtil;

/**
 * Die Klasse <code>Info</code> ist ein Fenster das Informationen( Autoren, usw.)
 * zum Programm anzeigt.
 * 
 * @author ???
 * @version 1.1
 *
 */
public class Info extends JFrame
{

   /**
	 * 
	 */
	private static final long serialVersionUID = 4792473612874487941L;

/** Panel mit Informationen */
   private JPanel main;

   /** Label fuer Informationen */
   private JLabel text;

   /** Label fuer Copyright */
   private JLabel copyright;

   /** Label fuer Bild */
   private JLabel help;

   /**
    * Konstruktor
    *
    */
   public Info()
   {
      super( "Bussimulation 2007" );

      main = new JPanel();
      main.setLayout( null );
      main.setBounds( 0, 0, getWidth(), getHeight() );

      help = new JLabel();
      help.setIcon( new ImageIcon( ImageUtil.getImageUrl( "fhcampus.png" ) ) );
      help.setBounds( 235, 20, 350, 245 );
      main.add( help );

      text = new JLabel();
      text.setText( "<html><b>Anschrift</b><br>" + "<br>Prof. Dr. Ulrich Hedtstück"
            + "<br>Prof. Dr. Michael Grütz" + "<br><br>HTWG Konstanz" + "<br>Braunegger Str. 55"
            + "<br>78467 Konstanz" + "<br><br>hdstueck@htwg-konstanz.de" + "<br>gruetz@htwg-konstanz.de"
            + "<br><br><b>Programmierer</b><br><br>Version 1.0:<br>" + "<br>Tobias Lott, Robert Audersetz, "
            + "<br>Daniel Prinz, Daniel Speicher" + "<br>Christian Steil, Ruping Hua"
            + "<br><br>Version 1.1:<br><br>Ingo Kroh, Michael Franz," + "<br>Daniel Weber, Stefan Müller"
            + "<br>Tobias Klein, Philipp Stader"
            );

      text.setBounds( 30, 4, 225, 420 );
      main.add( text );

      copyright = new JLabel();
      copyright.setText( "<html><div align=center>Version 1.4</div></html>");
      copyright.setBounds( 360, 270, 225, 30 );
      main.add( copyright );
      JLabel weiter = new JLabel();
      weiter.setText( "<html><u>Version 1.3:</u><br><br>Erkan Erpolat, Johannes Frei," + "<br>Stefan Idler, Dominik Heller,"
              + "<br>Markus Kaiser, Michel Litera, <br>Daniel Merkle, Stefan Meyer</div></html>");
      weiter.setBounds( 235, 300, 225, 100 );
      main.add( weiter );
      
      JLabel version14 = new JLabel();
      version14.setText( "<html><u>Version 1.4:</u><br><br>Alexander Akkus," + "<br>Guangyu Lu</div></html>");
      version14.setBounds(430, 300, 130, 70);
      main.add(version14);

      getContentPane().add( main );
      pack();
   }
}
