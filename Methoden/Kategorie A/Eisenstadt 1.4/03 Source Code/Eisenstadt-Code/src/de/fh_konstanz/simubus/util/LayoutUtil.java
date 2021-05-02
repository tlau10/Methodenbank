package de.fh_konstanz.simubus.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Die Klasse <code>LayoutUtil</code> stellt Hilsmethoden zum Arbeiten mit
 * Layouts zur Verfuegung.
 * 
 * @author Ingo Kroh
 * @version 1.0 (22.06.2006)
 */

public class LayoutUtil
{
   private LayoutUtil()
   {
      // keine Instanziierung
   }

   /**
    * fuegt einem <code>Container</code> mit <code>GridBagLayout</code> eine
    * Komponente hinzu. <code>fill</code> wird auf
    * <code>GridBagConstraints.BOTH</code> gesetzt.
    * 
    * @param cont
    *           der Container
    * @param gbl
    *           das GridBagLayout
    * @param c
    *           die Komponente
    * @param x
    *           X-Position im Layout
    * @param y
    *           Y-Position im Layout
    * @param width
    *           Anzahl von Zellen, die die Komponente horizontzal einnehmen soll
    * @param height
    *           Anzahl von Zellen, die die Komponente vertikal einnehmen soll
    * @param weightx
    *           gibt an, wie der freie horizontale Platz verteilt wird
    * @param weighty
    *           gibt an, wie der freie vertikale Platz verteilt wird
    */
   public static void addComponent( Container cont, GridBagLayout gbl, Component c, int x, int y, int width,
         int height, double weightx, double weighty )
   {
      addComponent( cont, gbl, c, x, y, width, height, weightx, weighty, GridBagConstraints.BOTH );
   }

   /**
    * fuegt einem <code>Container</code> mit <code>GridBagLayout</code> eine
    * Komponente hinzu.
    * 
    * @param cont
    *           der Container
    * @param gbl
    *           das GridBagLayout
    * @param c
    *           die Komponente
    * @param x
    *           X-Position im Layout
    * @param y
    *           Y-Position im Layout
    * @param width
    *           Anzahl von Zellen, die die Komponente horizontzal einnehmen soll
    * @param height
    *           Anzahl von Zellen, die die Komponente vertikal einnehmen soll
    * @param weightx
    *           gibt an, wie der freie horizontale Platz verteilt wird
    * @param weighty
    *           gibt an, wie der freie vertikale Platz verteilt wird
    * @param fill
    *           gibt an, ob der Bereich fuer die Komponente variabel ist
    */
   public static void addComponent( Container cont, GridBagLayout gbl, Component c, int x, int y, int width,
         int height, double weightx, double weighty, int fill )
   {
      addComponent( cont, gbl, c, x, y, width, height, weightx, weighty, fill, GridBagConstraints.CENTER );
   }

   /**
    * fuegt einem <code>Container</code> mit <code>GridBagLayout</code> eine
    * Komponente hinzu.
    * 
    * @param cont
    *           der Container
    * @param gbl
    *           das GridBagLayout
    * @param c
    *           die Komponente
    * @param x
    *           X-Position im Layout
    * @param y
    *           Y-Position im Layout
    * @param width
    *           Anzahl von Zellen, die die Komponente horizontzal einnehmen soll
    * @param height
    *           Anzahl von Zellen, die die Komponente vertikal einnehmen soll
    * @param weightx
    *           gibt an, wie der freie horizontale Platz verteilt wird
    * @param weighty
    *           gibt an, wie der freie vertikale Platz verteilt wird
    * @param fill
    *           gibt an, ob der Bereich fuer die Komponente variabel ist
    * @param anchor
    *           Anker fuer Komponente
    */
   public static void addComponent( Container cont, GridBagLayout gbl, Component c, int x, int y, int width,
         int height, double weightx, double weighty, int fill, int anchor )
   {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = fill;
      gbc.anchor = anchor;
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = width;
      gbc.gridheight = height;
      gbc.weightx = weightx;
      gbc.weighty = weighty;
      gbc.insets = new Insets( 5, 5, 5, 5 );
      gbl.setConstraints( c, gbc );
      cont.add( c );
   }

}
