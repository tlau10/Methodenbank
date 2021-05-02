package hotelbelegung;

import java.lang.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.beans.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Oliver Schraag
 * @version 1.0
 */

public class Manager {
  // Anlage von Datumsformaten für die weitere Verarbeitung
  private DateFormat outFormat = new SimpleDateFormat("dd.MM.yyyy");
  private DateFormat compareFormat = new SimpleDateFormat("yyyyMMdd");
  private Frame1 mainFrame;
  private String[][] model = new String[1000][1000];
  private String[][] restriktion = new String[1000][3];
  private Date aktDatum = new Date();
  private Date wunschDatum;
  private Date anfangDatum;
  private Date altDatum;
  private int dauer;
  private int zeitraum;
  private int anzSchalter;
  private int MWertL = -1000;
  private int altSchalter;
  private int anzAlternativen = 0;
  private int zimmerPreis;
  private int maxBelegung;
  private int anzZeilen;
  private int anzSpalten;
  private Solver mySolver;
  private Belegung myBelegung;

  public Manager() {
  }

  public Manager(Solver solver, Belegung belegung) {
      this.mySolver = solver;
      this.myBelegung = belegung;
  }

  // Start-Funktion wird mit Übergabe von Buchungsdatum und Dauer von der GUI angestossen
  public void setBelegung(Frame1 frame, Date startDatum, int anzTage, int basispreis, int auslastung) {
    mainFrame = frame;
    wunschDatum = startDatum;
    dauer = anzTage;
    zimmerPreis = basispreis;
    maxBelegung = auslastung;

    generateModel();
  }


  public void generateModel() {
    anfangDatum = setDatum(wunschDatum, -dauer);
    int zaehler;
    int startTag = 0;
    int tmpDauer;
    int MWertR = MWertL + dauer;
    int tmpJ = 1;
    int ausgelastet = 0;
    boolean checkSchalter;

    zeitraum = dauer * 3;
    anzSchalter = dauer * 2 + 1;
    anzZeilen = anzSchalter + 2;
    anzSpalten = zeitraum + anzSchalter + 2;

    int[] belegung = new int[zeitraum];
    int[] tmpErfahrungswert = new int[zeitraum];
    int[] erfahrungswert = new int[1000];
    int[] varSchalter = new int[1000];

    // Falls Alternativberechnung vor dem heutigen Datum liegt wird der Berechnungsanfang auf "morgen" verlegt
    int vglAnfangDatum = Integer.parseInt(compareFormat.format(anfangDatum));
    int vglAktDatum = Integer.parseInt(compareFormat.format(aktDatum));

    if (vglAnfangDatum <= vglAktDatum) {
      anfangDatum = setDatum(aktDatum, 1);
    }

    Date tmpAnfangDatum = anfangDatum;

    // Die Werte für Belegung und Erfahrungswerte werden ausgelesen
    for (int i=0; i < zeitraum; i++) {
      belegung[i] = myBelegung.getBelegung(tmpAnfangDatum);
      if (belegung[i] >= maxBelegung) {
        ausgelastet++;
      }
      tmpErfahrungswert[i] = myBelegung.getErfahrungswert(tmpAnfangDatum);
      tmpAnfangDatum = setDatum(tmpAnfangDatum, 1);
    }

    // Prüfung ob sämtliche Alternativtage größer/gleich die maximale Hotelbelegung ist
    if (zeitraum == ausgelastet) {
      JOptionPane.showMessageDialog(null, "Berechnung nicht möglich, maximale Hotelbelegung zu niedrig angesetzt - bitte anpassen!", "Fehler", 0);
      mainFrame.jTabbedPane1.setSelectedComponent(mainFrame.Einstellungen);
      mainFrame.jTabbedPane1.addNotify();
    }
    else {  // Schema mit korrekten Solver-Daten füllen (Als zweidim. Array)
     for (int i=0; i < anzZeilen; i++) {
      startTag = i - 2;
      tmpDauer = 1;
      checkSchalter = true;
      for (int j=0; j < anzSpalten; j++) {
	// Generierung der Zielfunktion
        if (i == 0) {
          if (j < zeitraum) {
            model[i][j] = Integer.toString(belegung[j]);
          }
          else {
            if (j == anzSpalten-2) {
              model[i][j] = " -->";
            }
            else if (j == anzSpalten-1) {
              model[i][j] = "min";
            }
            // Restbelegung wird separat eingetragen (Erfahrungswert für Schaltervariablen)
          }
        }
	// Generierung der Schaltervariablen-Restriktion
        else if (i == 1) {
          if (j < zeitraum) {
            model[i][j] = "0";
          }
          else if (j == anzSpalten-2) {
              model[i][j] = "=";
          }
          else if (j == anzSpalten-1) {
              model[i][j] = "1";
          }
          // Restbelegung wird separat eingetragen (Gültigkeit der Alternativ-Perioden über Schalter)
        }
	// Generierung der restlichen Alternativ-Restriktionen
        else {
            if (j == anzSpalten-2) {
              model[i][j] = ">=";
            }
            else if (j == anzSpalten-1) {
              model[i][j] = Integer.toString(MWertR);
            }
            else {
              if (startTag == j) {
                if (tmpDauer < dauer) {
                  startTag++;
                  tmpDauer++;
                }
		if (belegung[j] >= maxBelegung) {
		  checkSchalter = false;
		}
                model[i][j] = "1";
                // Eintrag der aufsummierten Erfahrungswerte der einzelnen Perioden in ein Array
                erfahrungswert[i-1] = erfahrungswert[i-1] + tmpErfahrungswert[j];
              }
              else {
                model[i][j] = "0";
                if (j == i+zeitraum-2)  {
                  model[i][j] = Integer.toString(MWertL);
                }
              }
            }
        }
      }

      // Eintrag der Schaltervariablen in separates Array (zur Überprüfung der max. Belegung)
      if (i > 1) {
        if (checkSchalter == true) {
	  varSchalter[i-1] = 1;
        }
        else {
	  varSchalter[i-1] = 0;
        }
      }
    }

    // durchschnittliche Erfahrungswerte in Zielfunktion einfügen und Ausschluss der Alternative (Schalter) bei max. Zimmerbelegungen
    for (int j=zeitraum; j < anzSpalten-2; j++) {
      model[0][j] = Integer.toString((erfahrungswert[tmpJ] / dauer) / 2);
      model[1][j] = Integer.toString(varSchalter[tmpJ]);
      tmpJ++;
    }

    // Erstellung des Schemas für die zusätzlichen Restriktionen
    for (int i=0; i < zeitraum+anzSchalter; i++) {
        restriktion[i][0] = "0";
        restriktion[i][1] = "1";
        restriktion[i][2] = "ja";
    }

    // Aufruf der Methode zur Alternativenberechnung
    getAlternative();
    }
  }

  // ändert das Modell indem die abgelehnte Alternative ausgeschlossen wird (Schalter auf 0 setzen)
  public void changeModel() {
    model[1][altSchalter] = "0";
    getAlternative();
  }

  // Funktion zur dynamischen Berechnung der Zimmerpreise zur jeweiligen Alternative
  public int getPreis() {
    Date tmpAnfangDatum = anfangDatum;
    int calcPreis = zimmerPreis;
    int wunschTag = 0;
    int altTag = 0;
    int vglWunschDatum = Integer.parseInt(compareFormat.format(wunschDatum));
    int vglAltDatum = Integer.parseInt(compareFormat.format(altDatum));

    // 1.Berechnungsvorschrift des Splittingmodells: Zeitabstand
    for (int i=0; i < zeitraum; i++) {
      tmpAnfangDatum = setDatum(tmpAnfangDatum, 1);
      int vglAnfangDatum = Integer.parseInt(compareFormat.format(tmpAnfangDatum));
      if (vglAnfangDatum == vglWunschDatum) {
        wunschTag = i + 1;
      }
      if (vglAnfangDatum == vglAltDatum) {
        altTag = i + 1;
      }
    }
    int abstand = altTag - wunschTag;

    if (abstand == 0) {
      abstand = 0;
    }
    else if (abstand < 0) {
      abstand = abstand * (-1) + 1;
    }
    else {
      abstand = abstand + 1;
    }
    // Preisabstufung vornehmen
    calcPreis = calcPreis - (zimmerPreis * ((20 / (anzSchalter-1)) * abstand) / 100);

    // 2.Berechnungsvorschrift des Splittingmodells: Alternativdurchlauf
    if (vglAltDatum != vglWunschDatum) {
       calcPreis = calcPreis - ((zimmerPreis * (10 / (anzAlternativen + 1)) / 100));
    }

    return calcPreis;
  }

  // Aufruf zur Berechnung der optimalen Alternative und Auswertung des Ergebnisses
  public void getAlternative() {
    double[] alternative = mySolver.calcModel(mainFrame, model, anzZeilen, anzSpalten, restriktion);
    int altAnfang;
    int preis;
    int zaehler = 0;
    altSchalter = 0;
    anzAlternativen++;

    for (int i=0; i < alternative.length-1; i++) {
        // Schalter der optimalen Alternative suchen
        if (Math.round((float)alternative[i]) == 1) {
                altSchalter = i;
        }
        else {
          zaehler++;
        }
    }
    // Kontrolle auf leere Rückgabe (kein Ergebnis vom Solver)
    if (zaehler < alternative.length-1) {
      // Anfangsdatum der optimalen Alternative suchen
      altAnfang = altSchalter-zeitraum;//(((alternative.length-1)/2)+1);
      altDatum = setDatum(anfangDatum, altAnfang);
      preis = getPreis();
      setAlternative(altDatum, dauer, preis);
    }
  }

  // Funktion zur Datumsänderung (Zähler +Tage und -Tage)
  public Date setDatum(Date datum, int tage) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(datum);
    cal.add(Calendar.DATE, tage);
    Date neuesDatum = new Date();
    neuesDatum = cal.getTime();
    return neuesDatum;
  }

  // Funktion für den GUI-Aufruf zur Alternativenberechnung
  public void setAlternative(Date optDatum, int tage, int preis) {
    Date anfang = optDatum;
    int buchung = tage;
    int tagesPreis = preis;
    int gesPreis = tagesPreis * buchung;
    String maximum = Integer.toString(maxBelegung);
    int vglWunschDatum = Integer.parseInt(compareFormat.format(wunschDatum));
    int vglAltDatum = Integer.parseInt(compareFormat.format(anfang));

    // Chart-Graphik wird aktualisiert
    mainFrame.jTabbedPane1.remove(mainFrame.Belegung);
    mainFrame.Belegung = new ServiceLevelPanel(this, myBelegung, maximum, anfangDatum, anfang, buchung);
    mainFrame.jTabbedPane1.add(mainFrame.Belegung, "Belegung", 1);

    // Sofortige Reservierung wenn Wunschbuchung gleich Alternativbuchung
    if(vglWunschDatum == vglAltDatum) {
      JOptionPane.showMessageDialog(null, "Check-in am " + outFormat.format(anfang) + "  -  Aufenthaltsdauer " + buchung + " Tag(e)  -  Gesamtpreis € " + gesPreis, "Gewünschte Reservierung vorgenommen", 1);
      mainFrame.jTabbedPane1.setSelectedComponent(mainFrame.Belegung);
      mainFrame.jTabbedPane1.addNotify();
      Date tmpDatum = anfang;

      for(int i=0; i < buchung; i++) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(tmpDatum);
        myBelegung.setBuchung(calendar);
        tmpDatum = setDatum(tmpDatum, 1);
      }
    }
    // Fenster FrameAlternative wird aufgerufen
    else {
      FrameAlternative frame = new FrameAlternative(mainFrame, this, myBelegung, wunschDatum, anfang, buchung, tagesPreis);

      // Das Fenster zentrieren
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = frame.getSize();
      if (frameSize.height > screenSize.height) {
        frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
        frameSize.width = screenSize.width;
      }
      frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      frame.setVisible(true);
    }
  }
}