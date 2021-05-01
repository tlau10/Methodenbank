package portfolio.view.images;

/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */
import javax.swing.ImageIcon;


public class ImageLoader {

  public ImageLoader() {
  }

  /** Returns an ImageIcon, or null if the path was invalid. */
  public static ImageIcon createImageIcon(String path, String description)
  {
    java.net.URL imgURL = ImageLoader.class.getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
  }


}