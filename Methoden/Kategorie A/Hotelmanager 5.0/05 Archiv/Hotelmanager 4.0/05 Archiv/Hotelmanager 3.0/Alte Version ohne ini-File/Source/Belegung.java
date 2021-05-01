/*
 * Belegung.java
 *
 * Created on 7. Januar 2003, 13:35
 */

package hotelbelegung;
import java.util.*;
import java.text.*;

/**
 *
 * @author  Florian Raiber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003 
 */
public class Belegung {
	private Frame1 mainFrame;
	private Manager manager;
    private DateFormat outFormat = new SimpleDateFormat("dd.MM.yyyy");
    private DateFormat compareFormat = new SimpleDateFormat("yyyyMMdd");
	private IFZufallszahlen zufallsZahlZimmerKat1;
	private IFZufallszahlen zufallsZahlZimmerKat2;
	private IFZufallszahlen zufallsZahlZimmerKat3;
    private Hashtable belegungZimmerKat1; // Anzahl belegte Zimmer
	private Hashtable belegungZimmerKat2;
	private Hashtable belegungZimmerKat3;
	private Hashtable ueberbuchungZimmerKat1; // Wahrscheinlichkeit für Überbuchung
	private Hashtable ueberbuchungZimmerKat2;
	private Hashtable ueberbuchungZimmerKat3;
	private Hashtable DBZimmerKat1; // Deckungsbeiträge
	private Hashtable DBZimmerKat2;
	private Hashtable DBZimmerKat3;
	private Vector DBZimmerKat1V; // Deckungsbeiträge
	private Vector DBZimmerKat2V;
	private Vector DBZimmerKat3V;

	private Date aktDatum = new Date();

    /** Creates a new instance of Belegung */
    public Belegung(Frame1 frame) {
        this.mainFrame = frame;
        this.berechneBelegung(aktDatum, 10);
    }

	/** Initialisiert neue Zufallszahlen für die Zimmerbelegung und die Überbuchungswahrscheinlichkeit*/
    public void berechneBelegung(Date startDate, int anzahlTage) {
        // für anzahlTage nach startDate Belegung bestimmen
        Calendar tempDate = new GregorianCalendar();
		
        // Belegung Zimmerkategorie 1
		tempDate.setTime(startDate); 
		belegungZimmerKat1 = new Hashtable();
		ueberbuchungZimmerKat1 = new Hashtable();
		int zimmerAnzahlKat1 = mainFrame.getZimmerAnzahlKat1();
		int zimmerBelegungKat1 = mainFrame.getZimmerBelegungKat1();	
		int spontanbucher1 = mainFrame.getSpontanBuchungenKat1();	
		double sigmaZimmerKat1 = zimmerAnzahlKat1*0.1; // 10% der Zimmeranzahl
		double mueZimmerKat1 = zimmerBelegungKat1*zimmerAnzahlKat1/100;
		zufallsZahlZimmerKat1 = new Normalverteilung(sigmaZimmerKat1,mueZimmerKat1);
        for (int i=0; i<anzahlTage; i++) {
            // Belegung ermitteln
            int belegung = new Double(zufallsZahlZimmerKat1.nextDouble()).intValue();
            if (belegung > zimmerAnzahlKat1)
            {
				belegung = zimmerAnzahlKat1;
            }
			belegungZimmerKat1.put(outFormat.format(tempDate.getTime()),new Integer(belegung));
			// Überbuchungswahrscheinlichkeit ermitteln
			double ueberbuchung = berechneUeberbuchung(zimmerAnzahlKat1, belegung, spontanbucher1);
			ueberbuchungZimmerKat1.put(outFormat.format(tempDate.getTime()), new Double(ueberbuchung));
            tempDate.add(Calendar.DATE,1);
        }
		
		// Belegung Zimmerkategorie 2
		tempDate.setTime(startDate); 
		belegungZimmerKat2 = new Hashtable();
		ueberbuchungZimmerKat2 = new Hashtable();
		int zimmerAnzahlKat2 = mainFrame.getZimmerAnzahlKat2();
		int zimmerBelegungKat2 = mainFrame.getZimmerBelegungKat2();
		int spontanbucher2 = mainFrame.getSpontanBuchungenKat2();		
		double sigmaZimmerKat2 = zimmerAnzahlKat2*0.1; // 10% der Zimmeranzahl
		double mueZimmerKat2 = zimmerBelegungKat2*zimmerAnzahlKat2/100;
		zufallsZahlZimmerKat2 = new Normalverteilung(sigmaZimmerKat2,mueZimmerKat2);
		for (int i=0; i<anzahlTage; i++) {
			// Belegung ermitteln
			int belegung = new Double(zufallsZahlZimmerKat2.nextDouble()).intValue();
			if (belegung > zimmerAnzahlKat2)
			{
				belegung = zimmerAnzahlKat2;
			}
			belegungZimmerKat2.put(outFormat.format(tempDate.getTime()),new Integer(belegung));
			// Überbuchungswahrscheinlichkeit ermitteln
			double ueberbuchung = berechneUeberbuchung(zimmerAnzahlKat2, belegung, spontanbucher2);
			ueberbuchungZimmerKat2.put(outFormat.format(tempDate.getTime()), new Double(ueberbuchung));
			tempDate.add(Calendar.DATE,1);
		}
		
		// Belegung Zimmerkategorie 3
		tempDate.setTime(startDate); 
		belegungZimmerKat3 = new Hashtable();
		ueberbuchungZimmerKat3 = new Hashtable();
		int zimmerAnzahlKat3 = mainFrame.getZimmerAnzahlKat3();
		int zimmerBelegungKat3 = mainFrame.getZimmerBelegungKat3();
		int spontanbucher3 = mainFrame.getSpontanBuchungenKat3();		
		double sigmaZimmerKat3 = zimmerAnzahlKat3*0.1; // 10% der Zimmeranzahl
		double mueZimmerKat3 = zimmerBelegungKat3*zimmerAnzahlKat3/100;
		zufallsZahlZimmerKat3 = new Normalverteilung(sigmaZimmerKat3,mueZimmerKat3);
		for (int i=0; i<anzahlTage; i++) {
			// Belegung ermitteln
			int belegung = new Double(zufallsZahlZimmerKat3.nextDouble()).intValue();
			if (belegung > zimmerAnzahlKat3)
			{
				belegung = zimmerAnzahlKat3;
			}
			belegungZimmerKat3.put(outFormat.format(tempDate.getTime()),new Integer(belegung));
			// Überbuchungswahrscheinlichkeit ermitteln
			double ueberbuchung = berechneUeberbuchung(zimmerAnzahlKat3, belegung, spontanbucher3);
			ueberbuchungZimmerKat3.put(outFormat.format(tempDate.getTime()), new Double(ueberbuchung));
			tempDate.add(Calendar.DATE,1);
		}
    }	
    
	/** Errechnet die Überbuchungswahrscheinlichkeit */
	public double berechneUeberbuchung(int zimmeranzahl, int belegung, int spontanbucher) {
		
		double wahrscheinlichkeit = 0;
		
		int freieZimmer = zimmeranzahl-belegung;
		int spontanbuchungen = (int)zimmeranzahl*spontanbucher/100;
		int maxBuchungen = (int)spontanbuchungen+(zimmeranzahl*10/100);
		int minBuchungen = (int)spontanbuchungen-(zimmeranzahl*10/100);
		
		// Wenn weniger freie Zimmer vorhanden sind als die linke Grenze
		// dann 0 zurückgeben
		if(freieZimmer <= minBuchungen) {
			return 1;	
		}
		// Wenn mehr freie Zimmer vorhanden sind als die rechte Grenze
		// dann 1 zurückgeben
		if(freieZimmer >= maxBuchungen) {
			return 0;	
		}		
		
		int dreieckHoehe = spontanbuchungen-minBuchungen;
		double dreieckFlaeche = dreieckHoehe*dreieckHoehe;
		
		// Wenn die Freien Zimmer weniger sind als die Spontanbuchungen
		// von links nähern ...
		if(freieZimmer <= spontanbuchungen) {
			double kleineFlaeche = (freieZimmer-minBuchungen)*(freieZimmer-minBuchungen)/2;
			wahrscheinlichkeit = 1-(kleineFlaeche/dreieckFlaeche);
		}
		// Wenn die Freien Zimmer mehr sind als die Spontanbuchungen
		// von rechts nähern ...
		else {
			double kleineFlaeche = (maxBuchungen-freieZimmer)*(maxBuchungen-freieZimmer)/2;
			wahrscheinlichkeit = kleineFlaeche/dreieckFlaeche;
		}
			
		return wahrscheinlichkeit;
	}    
    

	/** Funktion zur dynamischen Berechnung des Zimmerpreises zur jeweiligen Alternative */
	public int berechnePreis(Date altDatum, int altKategorie) {
	   Date datum = mainFrame.getBuchungsDatum(); // ursprüngliches Buchungsdatum
	   int kategorie = mainFrame.getZimmerKategorie(); // ursprüngliche Zimmerkategorie
	   double calcPreis = 0;
	   GregorianCalendar date1 = new GregorianCalendar();
	   GregorianCalendar date2 = new GregorianCalendar();
	   
	   // Berücksichtigung eines Kategorienwechsels (+/- 10%)
	   int kategorieDifferenz = Math.abs(kategorie - altKategorie);
	   switch (altKategorie) {
		   case 1: calcPreis = mainFrame.getZimmerPreisKat1()*(1-kategorieDifferenz*0.1);
				break;
		   case 2: calcPreis = mainFrame.getZimmerPreisKat2()*(1-kategorieDifferenz*0.1);
				break;
		   case 3: calcPreis = mainFrame.getZimmerPreisKat3()*(1-kategorieDifferenz*0.1);
				break;
	   }
    
	   	// Berücksichtigung des zeitlichen Abstandes (0-20%)
	   	date1.setTime(datum);
		date2.setTime(altDatum);
	  	long differenzMilliSek = date1.getTime().getTime() - date2.getTime().getTime(); 
	  	long differenzTage = Math.abs(Math.round((double)differenzMilliSek/(24*60*60*1000)));  
	   	int alternativen = manager.getZeitraum()-manager.getDauer()+1;
	   	calcPreis = calcPreis*(1-((double)differenzTage/alternativen)*0.2);
		return (new Double(calcPreis).intValue());
	}
		

	/** errechnet die erwarteten Deckungsbeiträge der verschiedenen Zimmer bei vorliegender Buchung */ 
	public void berechneDeckungsbeitrag (Date datum, int dauer, int kategorie) {
		this.manager = mainFrame.getMyManager();
		Date tempDate; 
		
		// Kategorie 1
		DBZimmerKat1 = new Hashtable();
		DBZimmerKat1V = new Vector();
		tempDate = manager.getAnfangDatum();
		int preis;
		double db=0;
		for (int tag=0; tag<ueberbuchungZimmerKat1.size(); tag++) {
			preis = this.berechnePreis(tempDate, 1);
			db = mainFrame.getDeckungsBeitragKat1();
			db *= (1-((Double)ueberbuchungZimmerKat1.get(outFormat.format(tempDate))).doubleValue());
			db += preis - mainFrame.getZimmerPreisKat1();
			DBZimmerKat1.put(outFormat.format(tempDate), new Double(db));
			DBZimmerKat1V.add(new Double(db));
			tempDate = manager.setDatum(tempDate,1);
		}
		
		// Kategorie 2
		DBZimmerKat2 = new Hashtable();
		DBZimmerKat2V = new Vector();
		tempDate = manager.getAnfangDatum();
		for (int tag=0; tag<ueberbuchungZimmerKat2.size(); tag++) {
			preis = this.berechnePreis(tempDate, 2);
			db = mainFrame.getDeckungsBeitragKat2();
			db *= (1-((Double)ueberbuchungZimmerKat2.get(outFormat.format(tempDate))).doubleValue());
			db += preis - mainFrame.getZimmerPreisKat2();
			DBZimmerKat2.put(outFormat.format(tempDate), new Double(db));
			DBZimmerKat2V.add(new Double(db));
			tempDate = manager.setDatum(tempDate,1);
		}
		
		// Kategorie 3
		DBZimmerKat3 = new Hashtable();
		DBZimmerKat3V = new Vector();
		tempDate = manager.getAnfangDatum();
		for (int tag=0; tag<ueberbuchungZimmerKat3.size(); tag++) {
			preis = this.berechnePreis(tempDate, 3);
			db = mainFrame.getDeckungsBeitragKat3();
			db *= (1-((Double)ueberbuchungZimmerKat3.get(outFormat.format(tempDate))).doubleValue());
			db += preis - mainFrame.getZimmerPreisKat3();
			DBZimmerKat3.put(outFormat.format(tempDate), new Double(db));
			DBZimmerKat3V.add(new Double(db));
			tempDate = manager.setDatum(tempDate,1);
		}
	}
	
	
	/** Gibt die Belegung am entsprechenden Datum/Zimmerkategorie zurück */
    public int getBelegung(Date date, int kategorie) {
    	switch (kategorie) {
			case 1: return ((Integer)belegungZimmerKat1.get(outFormat.format(date))).intValue();
			case 2: return ((Integer)belegungZimmerKat2.get(outFormat.format(date))).intValue();
			case 3: return ((Integer)belegungZimmerKat3.get(outFormat.format(date))).intValue();
			default: return 0;
    	}
    }
    
    
	/** Gibt die Überbuchungswahrscheinlichkeit am entsprechenden Datum/Zimmerkategorie zurück */
	public double getUeberbuchung(Date date, int kategorie) {
		switch (kategorie) {
			case 1: return ((Double)ueberbuchungZimmerKat1.get(outFormat.format(date))).doubleValue();
			case 2: return ((Double)ueberbuchungZimmerKat2.get(outFormat.format(date))).doubleValue();
			case 3: return ((Double)ueberbuchungZimmerKat3.get(outFormat.format(date))).doubleValue();
			default: return 0;
		}
	}
    
    
	/** Gibt den Deckungsbeitrag am entsprechenden Datum/Zimmerkategorie zurück */
	public double getDeckungsbeitrag(Date date, int kategorie) {
		switch (kategorie) {
			case 1: return ((Double)DBZimmerKat1.get(outFormat.format(date))).doubleValue();
			case 2: return ((Double)DBZimmerKat2.get(outFormat.format(date))).doubleValue();
			case 3: return ((Double)DBZimmerKat3.get(outFormat.format(date))).doubleValue();
			default: return 0;
		}
	}
	
	/** Bucht ein Zimmer am entsprechenden Datum */
    public void setBuchung(Calendar date, int kategorie) {
    	int belegung;
    	switch (kategorie) {
    		case 1: belegung = ((Integer)belegungZimmerKat1.get(outFormat.format(date.getTime()))).intValue();
    				belegung++;
					belegungZimmerKat1.put(outFormat.format(date.getTime()), new Integer(belegung));
					break;
			case 2: belegung = ((Integer)belegungZimmerKat2.get(outFormat.format(date.getTime()))).intValue();
					belegung++;
					belegungZimmerKat2.put(outFormat.format(date.getTime()), new Integer(belegung));
					break;
			case 3: belegung = ((Integer)belegungZimmerKat3.get(outFormat.format(date.getTime()))).intValue();
					belegung++;
					belegungZimmerKat3.put(outFormat.format(date.getTime()), new Integer(belegung));
					break;		
    	}
    }
	/**
	 * 
	 * @return
	 */
	public Manager getManager() {
		return manager;
	}

	/**
	 * 
	 * @param manager
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	/**
	 * 
	 * @return
	 */
	public Hashtable getDBZimmerKat1() {
		return DBZimmerKat1;
	}

	/**
	 * 
	 * @return
	 */
	public Vector getDBZimmerKat1V() {
		return DBZimmerKat1V;
	}

	/**
	 * 
	 * @return
	 */
	public Hashtable getDBZimmerKat2() {
		return DBZimmerKat2;
	}

	/**
	 * 
	 * @return
	 */
	public Vector getDBZimmerKat2V() {
		return DBZimmerKat2V;
	}

	/**
	 * 
	 * @return
	 */
	public Hashtable getDBZimmerKat3() {
		return DBZimmerKat3;
	}

	/**
	 * 
	 * @return
	 */
	public Vector getDBZimmerKat3V() {
		return DBZimmerKat3V;
	}

}
