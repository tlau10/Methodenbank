package trOptimizer;

/**
 * <p>Title: Öffnen</p>
 * <p>Description: Diese Klasse dient zum Oeffnen von bereits vorhandenen Modellen.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

public class Oeffnen {

  //Klassenattribute
  MainFrame mainFrame;

  /**
   * Konstruktor erwartet ein Mainframe-Objekt.
   *
   * @param mf Mainframe-Objekt
   */
  public Oeffnen(MainFrame mf) {
    mainFrame = mf;
  }

  /**
   * Diese Methode dient zum Oeffenen bereits gespeicherter Modelle. Sie erwartet einen Datei-Pfad, wo sich die zu
   * ladende Datei befindet.
   *
   * @param path - wo sich die zu oeffnende Datei befindet.
   */
  public void oeffnenObjekte( File path ) {

    try {
      if( path != null ) {
        FileInputStream file = new FileInputStream(path);
        ObjectInputStream input = new ObjectInputStream(file);
        Vector tmpEmpfaenger = new Vector();
        System.out.println( path );
        tmpEmpfaenger = ( Vector )input.readObject();
        Vector tmpProduzent = new Vector();
        tmpProduzent = ( Vector )input.readObject();
        Vector tmpStrecken = new Vector();
        tmpStrecken = ( Vector )input.readObject();
        Vector tmpTransporter = new Vector();
        tmpTransporter = ( Vector )input.readObject();
        int tmpEmpfaengerId = 1;
        int tmpProduzentId = 1000;
        int tmpStreckenId = 3000;
        int tmpTransporterId = 2000;

        // die ausgelesenen vectoren an den controller uebergeben, damit dieser wieder mit den gespeicherten
        // Daten arbeiten kann
        mainFrame.getController().setEmpfaenger( tmpEmpfaenger );
        mainFrame.getController().setProduzenten( tmpProduzent );
        mainFrame.getController().setStrecken( tmpStrecken );
        mainFrame.getController().setTransporter( tmpTransporter );

        // position der transporter neu setzen, damit diese direkt hinter die bestehenden gezeichnet werden
        mainFrame.getController().setTransportPos( 520 - ( 60 * ( mainFrame.getController().getTransporter().size() ) ) );

        // die empfaengerId setzen, damit nachfolgender empfaenger eine richtige Id bekommen
        for( int i = 0; i < tmpEmpfaenger.size(); i++ ) {
          tmpEmpfaengerId++;
        }
        mainFrame.getController().setEmpfaengerId( tmpEmpfaengerId );

        // die produzentId setzen, damit nachfolgender produzent eine richtige Id bekommen
        for( int i = 0; i < tmpProduzent.size(); i++ ) {
          tmpProduzentId++;
        }
        mainFrame.getController().setProduzentId( tmpProduzentId );

        // die streckenId setzen, damit nachfolgende strecke eine richtige Id bekommen
        for( int i = 0; i < tmpStrecken.size(); i++ ) {
          tmpStreckenId++;
        }
        mainFrame.getController().setStreckenId( tmpStreckenId );

        // die transpoertId setzen, damit nachfolgender transporter eine richtige Id bekommen
        for( int i = 0; i < tmpTransporter.size(); i++ ) {
          tmpTransporterId++;
        }
        mainFrame.getController().setTransporterId( tmpTransporterId );

        input.close();

      }
    }
    catch( IOException ex ) {
      ex.printStackTrace();

    }
    catch( ClassNotFoundException ex ) {
      ex.printStackTrace();

    }

  }

}