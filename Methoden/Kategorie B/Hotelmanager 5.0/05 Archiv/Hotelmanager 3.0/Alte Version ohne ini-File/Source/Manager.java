package hotelbelegung;

import java.util.*;
import java.text.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Oliver Schraag
 * @version 2.0 Oliver B�hler, Kilian Thiel Juni 2003 
 */

public class Manager {
	public static final int kategorien = 3; // es gibt 3 Zimmerkategorien
  // Anlage von Datumsformaten f�r die weitere Verarbeitung
  private DateFormat outFormat = new SimpleDateFormat("dd.MM.yyyy");
  private DateFormat compareFormat = new SimpleDateFormat("yyyyMMdd");
  private Frame1 mainFrame;
  private Solver mySolver;
  private Belegung myBelegung;
  private String[][] model = new String[1000][1000];
  private String[][] restriktion = new String[1000][3];
  private Date aktDatum = new Date();
  private Date anfangDatum;
  //private Date altDatum;
  private int dauer;
  private int zeitraum;
  
  private int anzSchalter;
  private int MWertL = -1000;
  private int altSchalter;
  private int anzAlternativen = 0;
  private int anzZeilen;
  private int anzSpalten;

  	public Manager(Frame1 frame, Solver solver, Belegung belegung) {
		this.mainFrame = frame;
      	this.mySolver = solver;
      	this.myBelegung = belegung;
      	myBelegung.setManager(this);
  	}

	// wird von der GUI angestossen
	public void generateModel() {
	dauer = mainFrame.getBuchungsDauer();
    anfangDatum = setDatum(mainFrame.getBuchungsDatum(), -dauer);
    
    int zaehler;
    int MWertR = MWertL + dauer;
    int ausgelastet = 0;
    boolean checkSchalter;

    zeitraum = dauer * 3;
    anzSchalter = (zeitraum-dauer+1)*kategorien;
    anzZeilen = anzSchalter + 3; 
    anzSpalten = zeitraum*kategorien + anzSchalter + 2;

    int[] belegung = new int[zeitraum*kategorien]; // Zimmerbelegung innerhalb des Betrachtungszeitraumes
    int[] varSchalter = new int[1000];

    // Falls Alternativberechnung vor dem heutigen Datum liegt wird der Berechnungsanfang auf "morgen" verlegt
    int vglAnfangDatum = Integer.parseInt(compareFormat.format(anfangDatum));
    int vglAktDatum = Integer.parseInt(compareFormat.format(aktDatum));
    if (vglAnfangDatum <= vglAktDatum) {
      anfangDatum = setDatum(aktDatum, 1);
    }
    
    // Zufallszahlen im entsprechenden Zeitraum erstellen
	myBelegung.berechneBelegung(anfangDatum, zeitraum);
	myBelegung.berechneDeckungsbeitrag(mainFrame.getBuchungsDatum(), dauer, mainFrame.getZimmerKategorie());

	int[] zimmerAnzahl = new int[4];
	zimmerAnzahl[1] = mainFrame.getZimmerAnzahlKat1();
	zimmerAnzahl[2] = mainFrame.getZimmerAnzahlKat2();
	zimmerAnzahl[3] = mainFrame.getZimmerAnzahlKat3();
	Date tmpDatum;
    // Die Werte f�r Belegung werden ausgelesen
    for (int kat=1; kat<=kategorien; kat++) {
		tmpDatum = anfangDatum;
	    for (int i=0; i < zeitraum; i++) {
	      	belegung[(kat-1)*zeitraum+i] = myBelegung.getBelegung(tmpDatum,kat);
	      	if (belegung[(kat-1)*zeitraum+i] >= zimmerAnzahl[kat]) {
	        	ausgelastet++;
	      	}
	      	tmpDatum = setDatum(tmpDatum, 1);
	    }
    }

    // Pr�fung ob s�mtliche Alternativtage gr��er/gleich die maximale Hotelbelegung ist
    if (zeitraum*kategorien == ausgelastet) {
      JOptionPane.showMessageDialog(null, "Berechnung nicht m�glich, maximale Hotelbelegung zu niedrig angesetzt - bitte anpassen!", "Fehler", 0);
      mainFrame.getRegisterJTabbedPane().setSelectedComponent(mainFrame.getEinstellungenJPanel());
      mainFrame.getRegisterJTabbedPane().addNotify();
    }
    
	// Schema mit korrekten Solver-Daten f�llen (Als zweidim. Array)
	// Generierung der Zielfunktion = Zeile 0
	Vector v1 = myBelegung.getDBZimmerKat1V();
	Vector v2 = myBelegung.getDBZimmerKat2V();
	Vector v3 = myBelegung.getDBZimmerKat3V();
	Vector v4 = new Vector();
	v4.addAll(v1);
	v4.addAll(v2);
	v4.addAll(v3);
	for (int j=0; j < anzSpalten; j++) {
		// Deckungsbeitrag f�r Variable in Zielfunktion eintragen
		if (j < zeitraum*kategorien) {
			double db = ((Double)(v4.elementAt(j))).doubleValue();
			model[0][j] = new Long(Math.round(db*100)).toString();
		}
		else {
			if (j == anzSpalten-2) {
				model[0][j] = " -->";
			}
		   	// Maximierung
		   	else if (j == anzSpalten-1) {
				model[0][j] = "max";
		   	}
		   	// Schaltervariablen werden mit 0 in ZF bewertet
		   	else {
				model[0][j] = "0";
		   	}
		}
	}
	
	// Generierung der Tagesvariablen-Restriktion = Zeile 1
	for (int j=0; j < anzSpalten; j++) {
		if (j < zeitraum*kategorien) {
			model[1][j] = "1";
		}
		else {
			if (j == anzSpalten-2) {
				model[1][j] = "=";
			}
			else if (j == anzSpalten-1) {
				model[1][j] = String.valueOf(dauer);
			}
			else {
				model[1][j] = "0";
			}
		}
	}
	
	// Generierung der Schaltervariablen-Restriktion = Zeile 2
	for (int j=0; j < anzSpalten; j++) {
		if (j < zeitraum*kategorien) {
			model[2][j] = "0";
		}
		else {
			if (j == anzSpalten-2) {
				model[2][j] = "=";
			}
			else if (j == anzSpalten-1) {
				model[2][j] = "1";
			}
			else {
				model[2][j] = "1";
			}
			//////////////////////////
			// Ausschl�sse beachten
			// Restbelegung wird separat eingetragen (G�ltigkeit der Alternativ-Perioden �ber Schalter)
			//////////////////////////
		}
	}
	
	// m�gliche Buchungsperioden eintragen
	for (int i=3; i < anzZeilen; i++) {
		int kategorie = (i-3)/(zeitraum-dauer+1)+1;
    	int startTag;
    	if (dauer == 1) {
    		startTag = i-3; // In Zeile 3 geht Restriktion f�r Tag 1 los
    	}
    	else {
			startTag = (i-3)+kategorie-1; 
    	}
		//System.out.println("Zeile: " + i + " startTag: " + startTag + " kat:" + kategorie);
    	int tmpDauer = 1;
      	checkSchalter = true;
      
      	for (int j=0; j < anzSpalten; j++) {
		// Generierung der restlichen Alternativ-Restriktionen
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
			  		if (belegung[j] >= zimmerAnzahl[kategorie]) {
		  				checkSchalter = false;
			  		}
              		model[i][j] = "1";
              	}
              	else {
                	model[i][j] = "0";
                	// Schaltervariable
                	if (j == i+zeitraum*kategorien-3)  {
                  		model[i][j] = Integer.toString(MWertL);
                	}
              	}
            }
      	} // end of for j
	} // end of for i

/*      // Eintrag der Schaltervariablen in separates Array (zur �berpr�fung der max. Belegung)
      if (i > 1) {
		if (checkSchalter == true) {
			varSchalter[i-1] = 1;
		}
		else {
			varSchalter[i-1] = 0;
		}
      }
    //} // end of for i*/

    // Erstellung des Schemas f�r die zus�tzlichen Restriktionen (x1<1 ...)
    for (int i=0; i < zeitraum*kategorien+anzSchalter; i++) {
        restriktion[i][0] = "0";
        restriktion[i][1] = "1";
        restriktion[i][2] = "ja";
    }

    // Aufruf der Methode zur Alternativenberechnung
    getAlternative();
  }

  // �ndert das Modell indem die abgelehnte Alternative ausgeschlossen wird (Schalter auf 0 setzen)
  public void changeModel() {
    model[2][altSchalter] = "0";
    getAlternative();
  }

 

  // Aufruf zur Berechnung der optimalen Alternative und Auswertung des Ergebnisses
  public void getAlternative() {
    double[] alternative = mySolver.calcModel(mainFrame, model, anzZeilen, anzSpalten, restriktion);
    int altAnfang;		// Anfangsdatum der Alternativbuchung
	Date altDatum;
	int altKategorie;	// Zimmerkategorie der Alternativbuchung
    int preis;
    int zaehler = 0;
    altSchalter = 0;
    anzAlternativen++;

    Vector gesetzteVariablen = new Vector();
    for (int i=0; i < alternative.length-1; i++) {
        // Schalter der optimalen Alternative suchen
        if (Math.round((float)alternative[i]) == 1) {
			gesetzteVariablen.add(new Integer(i));
        }
        else {
          zaehler++;
        }
    }
    // Kontrolle auf leere R�ckgabe (kein Ergebnis vom Solver)
    if (zaehler < alternative.length-1) {
      // Anfangsdatum der optimalen Alternative suchen
	  altSchalter = ((Integer)gesetzteVariablen.lastElement()).intValue();
	  int altVariable = ((Integer)gesetzteVariablen.firstElement()).intValue();
      altKategorie = altVariable/zeitraum+1;
	  altDatum = setDatum(anfangDatum, (altVariable%zeitraum));
	  preis = myBelegung.berechnePreis(altDatum, altKategorie);
      setAlternative(altDatum, dauer, preis, altKategorie);
    }
  }

  // Funktion zur Datums�nderung (Z�hler +Tage und -Tage)
  public Date setDatum(Date datum, int tage) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(datum);
    cal.add(Calendar.DATE, tage);
    Date neuesDatum = new Date();
    neuesDatum = cal.getTime();
    return neuesDatum;
  }

  // Funktion f�r den GUI-Aufruf zur Alternativenberechnung
  public void setAlternative(Date optDatum, int tage, int preis, int kategorie) {
    int tagesPreis = preis;
    int gesPreis = tagesPreis * tage;
    int maximum1 = mainFrame.getZimmerAnzahlKat1();
	int maximum2 = mainFrame.getZimmerAnzahlKat2();
	int maximum3 = mainFrame.getZimmerAnzahlKat3();
    int vglWunschDatum = Integer.parseInt(compareFormat.format(mainFrame.getBuchungsDatum()));
    int vglAltDatum = Integer.parseInt(compareFormat.format(optDatum));

    // Chart-Graphik wird aktualisiert
    mainFrame.getRegisterJTabbedPane().remove(mainFrame.getBelegungJPanel());
	mainFrame.getBelegungJPanel().removeAll();
	ServiceLevelPanel Kategorie1ServiceLevelPanel;
	ServiceLevelPanel Kategorie2ServiceLevelPanel;
	ServiceLevelPanel Kategorie3ServiceLevelPanel;
	switch (kategorie) {
		case 1:
			Kategorie1ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum1, anfangDatum, optDatum, tage, 1);
			Kategorie2ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum2, anfangDatum, zeitraum ,2);
			Kategorie3ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum3, anfangDatum, zeitraum ,3);
			break;
		case 2:
			Kategorie1ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum1, anfangDatum, zeitraum ,1);
			Kategorie2ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum2, anfangDatum, optDatum, tage, 2);
			Kategorie3ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum3, anfangDatum, zeitraum ,3);
			break;
		case 3:
			Kategorie1ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum1, anfangDatum, zeitraum ,1);
			Kategorie2ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum2, anfangDatum, zeitraum ,2);
			Kategorie3ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum3, anfangDatum, optDatum, tage, 3);
			break;
		default:
			Kategorie1ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum1, anfangDatum, zeitraum ,1);
			Kategorie2ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum2, anfangDatum, zeitraum ,2);
			Kategorie3ServiceLevelPanel = new ServiceLevelPanel(this, myBelegung, maximum3, anfangDatum, zeitraum ,3);	
	}
	JTablePanel aJTP = new JTablePanel(this, myBelegung);
    mainFrame.getBelegungJPanel().add(Kategorie1ServiceLevelPanel);
	mainFrame.getBelegungJPanel().add(Kategorie2ServiceLevelPanel);
	mainFrame.getBelegungJPanel().add(Kategorie3ServiceLevelPanel);
	mainFrame.getBelegungJPanel().add(aJTP);
    mainFrame.getRegisterJTabbedPane().add(mainFrame.getBelegungJPanel(), "Belegung", 1);

    // Sofortige Reservierung wenn Wunschbuchung gleich Alternativbuchung
    if((vglWunschDatum == vglAltDatum) && (kategorie == mainFrame.getZimmerKategorie())) {
      JOptionPane.showMessageDialog(null, "Check-in am " + outFormat.format(optDatum) + "  -  Aufenthaltsdauer " + tage + " Tag(e)  -  Gesamtpreis � " + gesPreis, "Gew�nschte Reservierung vorgenommen", 1);
      mainFrame.getRegisterJTabbedPane().setSelectedComponent(mainFrame.getBelegungJPanel());
      mainFrame.getRegisterJTabbedPane().addNotify();
      Date tmpDatum = optDatum;

      for(int i=0; i < tage; i++) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(tmpDatum);
        myBelegung.setBuchung(calendar,1);
        tmpDatum = setDatum(tmpDatum, 1);
      }
    }
    // Fenster FrameAlternative wird aufgerufen
    else {
      FrameAlternative frame = new FrameAlternative(mainFrame, this, myBelegung, mainFrame.getBuchungsDatum(), optDatum, tage, tagesPreis, kategorie);

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
/**
 * 
 * @return
 */
public int getZeitraum() {
	return zeitraum;
}

/**
 * 
 * @return
 */
public int getDauer() {
	return dauer;
}

/**
 * 
 * @return
 */
public Date getAnfangDatum() {
	return anfangDatum;
}

}