package de.fh_konstanz.simubus.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import de.fh_konstanz.simubus.model.SimuKonfiguration;

/**
 * Die Klasse <code>Main</code> ist der Einstiegspunkt fuer das Programm. Sie 
 * enthaelt die Main-Methode.
 * 
 * @author ???
 *
 */
public class Main
{

   public static void main( String[] args )
   {
      SimuKonfiguration config = SimuKonfiguration.getInstance();

      Dimension screen_res = Toolkit.getDefaultToolkit().getScreenSize();

      if ( screen_res.height < 1000 )
      {
         config.setActiveResolutionFromCombo( 0 );
      }
      else
      {
         config.setActiveResolutionFromCombo( 1 );
      }

      View mainFrame = View.getInstance();
      mainFrame.setSize( config.getResWidth(), config.getResHeight() );
      mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      mainFrame.setExtendedState( JFrame.MAXIMIZED_BOTH );
      mainFrame.setVisible( true );
   }
}
