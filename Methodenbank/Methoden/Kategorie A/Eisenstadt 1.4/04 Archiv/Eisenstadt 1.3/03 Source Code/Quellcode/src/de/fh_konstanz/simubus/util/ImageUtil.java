package de.fh_konstanz.simubus.util;

import java.net.URL;

/**
 * Die Klasse <code>ImageUtil</code> stellt Hilfsmethoden fuer Bilder zur Verfuegung.
 * 
 * @author Ingo Kroh
 * @version 1.0 (27.06.2006)
 */

public class ImageUtil
{
   /** System-Classloader */
   private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

   private ImageUtil()
   {
      // keine Instanziierung
   }

   /**
    * liefert zu einem Bildname die entsprechenden <code>URL</code>
    * 
    * @param aImageName Name des Bildes
    * @return <code>URL</code> zum Bild
    */
   public static URL getImageUrl( String aImageName )
   {
      return classLoader.getResource( aImageName );

   }
}
