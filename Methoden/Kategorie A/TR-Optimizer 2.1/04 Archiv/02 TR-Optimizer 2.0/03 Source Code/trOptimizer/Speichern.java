package trOptimizer;

/**
 * <p>Title: Speichern</p>
 * <p>Description: Diese Klasse dient zum speichern des aktuellen Modells.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Speichern {

  //Klassenattribute
  MainFrame mainFrame;

  /**
   * Der Konstruktor erwartet ein MainFrame-Objekt.
   *
   * @param mF ein MainFrame-Objekt
   */
  public Speichern( MainFrame mF ) {

    mainFrame = mF;
  }

  /**
   * Diese Methode dient zum speichern von Modellen. Sie erwartet einen Datei-Pfad, wo sich die zu
   * speichernde Datei befinden soll.
   *
   * @param path - wo sich die zu speichernde Datei befinden soll
   */

  public void speichernObjekt( File path ) {

    try {
      if( path != null ) {
        FileOutputStream file = new FileOutputStream( path );
        ObjectOutputStream out = new ObjectOutputStream( file );
        out.writeObject( mainFrame.getController().getEmpfaenger() );
        out.writeObject( mainFrame.getController().getProduzenten() );
        out.writeObject( mainFrame.getController().getStrecken() );
        out.writeObject( mainFrame.getController().getTransporter() );
        out.flush();
        out.close();
      }
    }
    catch( IOException ex ) {
      ex.printStackTrace();
    }

  }

}