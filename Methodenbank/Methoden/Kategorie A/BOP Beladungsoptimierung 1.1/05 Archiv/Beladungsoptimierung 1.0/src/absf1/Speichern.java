package absf1;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.io.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.beans.XMLEncoder;


public class Speichern {

  //Klassenattribute
  TableModel LkwModell;
  TableModel GebindeModell;


  public Speichern(TableModel pLkwModell, TableModel pGebindeModell) {

    LkwModell = pLkwModell;
    GebindeModell = pGebindeModell;
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

        // Ein Array von Strings erstellen in das wir die Daten aus der LKW-Tabelle speichern können
        String[][] valuesLKW = new String[LkwModell.getRowCount()][LkwModell.getColumnCount()];

        //die LKW-Tabelle durchlaufen und die Werte in das Array übertragen
        for (int i = 0; i < LkwModell.getRowCount(); i++) {
          for (int j = 0; j < LkwModell.getColumnCount(); j++) {
            valuesLKW[i][j] = (String) LkwModell.getValueAt(i, j);
          }
        }

        //das ganze nochmal für die Gebinde
        String[][] valuesGebinde = new String[GebindeModell.getRowCount()][GebindeModell.getColumnCount()];
        for (int i = 0; i < GebindeModell.getRowCount(); i++) {
          for (int j = 0; j < GebindeModell.getColumnCount(); j++) {
            valuesGebinde[i][j] = (String) GebindeModell.getValueAt(i, j);
          }
        }

        // gespeichert werden serialisierte Objekte
        FileOutputStream file = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(file);

        //Information speichern: wieviele Zeilen waren in der LKW-Tabelle
        out.writeInt(LkwModell.getRowCount());
        // Zeilen Gebinde-Tabelle
        out.writeInt(GebindeModell.getRowCount());
        // Daten rausschreiben
        out.writeObject(valuesLKW);
        out.writeObject(valuesGebinde);
        out.flush();
        out.close();
      }
    }
    catch( IOException ex ) {
      ex.printStackTrace();
    }

  }

}