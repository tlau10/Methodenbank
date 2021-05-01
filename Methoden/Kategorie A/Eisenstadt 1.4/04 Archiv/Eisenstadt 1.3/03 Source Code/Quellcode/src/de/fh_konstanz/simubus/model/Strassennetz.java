package de.fh_konstanz.simubus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.fh_konstanz.simubus.util.Logger;

/**
 * Die Klasse <code>Strassennetz</code> beinhaltet alle Listen mit den
 * Eigenschaften des Virtual Grid.
 * 
 * @author Michael Franz
 * 
 */
public class Strassennetz implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5773417806187776141L;

	/**
	 * Liste aller Haltestellen
	 */
	private Set<Haltestelle> haltestellen = null;

	/**
	 * Liste mit Planquadraten, die die Eigenschaft Strasse haben
	 */
	private ArrayList<Planquadrat> strassenliste = null;

	/**
	 * Liste aller Ziele
	 */
	private ArrayList<Ziel> zielliste = null;

	/**
	 * Liste aller Planquadrate, die die Eigenschaft Feld gesperrt haben
	 */
	private ArrayList<Planquadrat> feldGesperrtListe = null;

	/**
	 * Lister aller Buslinien
	 */
	private ArrayList<Linie> linien = null;

	/**
	 * Liste aller für Haltestellen gesperrter Felder
	 * 
	 * author Philipp Hofmann
	 */
	private ArrayList<GesperrteHaltestelle> gesperrteHaltestellenliste = null;

	/**
	 * Instanz des Strassennetz
	 */
	private static transient Strassennetz instance = null;

	/**
	 * Konstruktor, hier werden sämtliche Listen angelegt
	 * 
	 */
	private Strassennetz() {
		haltestellen = new HashSet<Haltestelle>();
		strassenliste = new ArrayList<Planquadrat>();
		zielliste = new ArrayList<Ziel>();
		feldGesperrtListe = new ArrayList<Planquadrat>();
		linien = new ArrayList<Linie>();
		gesperrteHaltestellenliste = new ArrayList<GesperrteHaltestelle>();
	}

	/**
	 * 
	 * @return liefert die Instanz des Strassennetz
	 */
	public static synchronized Strassennetz getInstance() {
		if (instance == null) {
			instance = new Strassennetz();
		}

		return instance;
	}

	/**
	 * 
	 * @param h
	 *            fügt der Liste aller Haltestellen eine hinzu
	 */
	public void addHaltestelle(Haltestelle h) {
		haltestellen.add(h);
	}

	/**
	 * 
	 * @param h
	 *            löscht die Haltestelle aus der Liste
	 */
	public void removeHaltestelle(Haltestelle haltestelle) {

		
		Set<Haltestelle> neuesSet = new HashSet<Haltestelle>();
		
		Iterator<Haltestelle> iterator = haltestellen.iterator();
		while (iterator.hasNext()){
			neuesSet.add(iterator.next());
		}
		
			
		iterator = neuesSet.iterator();
		while (iterator.hasNext()){
			Haltestelle aHaltestelle = (Haltestelle)iterator.next();
			
			if (aHaltestelle.getPixelXCoordinate() == haltestelle.getPixelXCoordinate() && aHaltestelle.getPixelYCoordinate() == haltestelle.getPixelYCoordinate()){
//				System.out.println("haltestellesize vorher:" + haltestellen.size());
				haltestellen.remove(aHaltestelle);
//				System.out.println("haltestellesize nachher:" + haltestellen.size());
			}
			
		}
		
			
	}

	/**
	 * 
	 * @return liefert alle Haltestellen
	 */
	public Collection<Haltestelle> getAlleHaltestellen() {
		return haltestellen;
	}

	/**
	 * 
	 * @param strasse
	 *            fügt ein Planquadrat der Liste hinzu, welches die Eigenschaft
	 *            Strasse hat
	 */
	public void addStrasse(Planquadrat strasse) {
		strassenliste.add(strasse);
	}

	/**
	 * 
	 * @param strasse
	 *            löscht das Planquadrat aus der Strassenliste
	 */
	public void removeStrasse(Planquadrat strasse) {
		strassenliste.remove(strasse);
	}

	/**
	 * 
	 * @param feldGesperrt
	 *            fügt dieses Planquadrat der Liste hinzu, welches die
	 *            Eigenschaft Feld gesperrt hat
	 */
	public void addFeldGesperrt(Planquadrat feldGesperrt) {
		feldGesperrtListe.add(feldGesperrt);
	}

	/**
	 * 
	 * @param feldGesperrt
	 *            löscht das Planquadrat aus der Liste der gesperrten
	 *            Planquadrate
	 */
	public void removeFeldGesperrt(Planquadrat feldGesperrt) {
		feldGesperrtListe.remove(feldGesperrt);
	}

	/**
	 * 
	 * @param ziel
	 *            fügt ein Ziel der Liste hinzu
	 */
	public void addZiel(Ziel ziel) {
//		System.out.println("füge ziel hinzu: ID " + ziel.getId());
//		System.out.println(" X-Value: " + ziel.getX());
//		System.out.println(" y-Value: " + ziel.getY());
		zielliste.add(ziel);
	}

	/**
	 * 
	 * @param ziel
	 *            löscht ein Ziel aus der entsprechenden Liste
	 */
	
	public void removeZiel(Ziel ziel) {
//		System.out.println("LÖSCHE ziel hinfort: ID " + ziel.getId());
//		System.out.println(" X-Value: " + ziel.getX());
//		System.out.println(" y-Value: " + ziel.getY());
		
		//for (Ziel aZiel: zielliste){
		for(int i=0; i< zielliste.size(); i++){
			Ziel aZiel = zielliste.get(i);
			if (aZiel.getX() == ziel.getX() && aZiel.getY() == ziel.getY()){
//				System.out.println("Lösche x(" + ziel.getX() + ") und Y(" + ziel.getY() + ")" );
//				System.out.println("ARRAYSIZE vorher:" + zielliste.size());
				zielliste.remove(i);
//				System.out.println("ARRAYSIZE nachher:" + zielliste.size());
			}			
		}
		
	}

	/**
	 * 
	 * @return liefert die Liste mit den Strassen
	 */
	public ArrayList<Planquadrat> getStrassenListePlanquadrat() {
		return strassenliste;
	}

	/**
	 * 
	 * @return liefert die Liste mit den Zielen
	 */
	public ArrayList<Ziel> getZiele() {
		return zielliste;
	}

	/**
	 * Leert alle Listen (Haltestellen, Strassen, Ziele, gesperrte Felder,
	 * Linien)
	 */
	public void reset() {
		// instance = new Strassennetz();

		haltestellen.clear();
		strassenliste.clear();
		zielliste.clear();
		feldGesperrtListe.clear();
		linien.clear();
		gesperrteHaltestellenliste.clear();
	}

	/**
	 * löscht die Liste mit den Haltestellen
	 * 
	 */
	public void resetHaltestellen() {
		// Haltestellen aus Strassenfelder loeschen
		for (int i = 0; i < strassenliste.size(); i++) {
			// Haltestelle entfernen
			strassenliste.get(i).setStreet();
		}

		haltestellen.clear();
	}

	/**
	 * prüft ob die Strassenliste und die Zielliste leer ist.
	 * 
	 * @return ja oder nein
	 */
	public boolean isEmpty() {
		boolean result = false;

		if (strassenliste.isEmpty() == true || zielliste.isEmpty() == true) {
			result = true;
		}

		return result;
	}

	/**
	 * 
	 * @return liefert die Liste alle Buslinien
	 */
	public ArrayList<Linie> getArrayBuslinie() {

		return this.linien;
	}

	/**
	 * 
	 * @param aLinie
	 *            fügt die Linie, der entsprechenden Liste hinzu
	 */
	public void addLinie(Linie aLinie) {
		Logger.getInstance().log("linie added");
		this.linien.add(aLinie);
	}

	/**
	 * 
	 * @param aLinie
	 *            löscht die Linie aus der Linienliste
	 */
	public void removeLinie(Linie aLinie) {
		Logger.getInstance().log("linie removed");
		linien.remove(aLinie);

	}

	/**
	 * 
	 * @param feldGesperrtListe
	 *            setzt die Liste der gesperrten Felder
	 */
	public void setFeldGesperrtListe(ArrayList<Planquadrat> feldGesperrtListe) {
		Logger.getInstance().log("gesperrteFelder added");
		this.feldGesperrtListe = feldGesperrtListe;
	}

	/**
	 * 
	 * @param haltestellen
	 *            setzt die Liste der Haltestellen
	 */
	public void setHaltestellen(Set<Haltestelle> haltestellen) {
		Logger.getInstance().log("haltestellen added");
		this.haltestellen = haltestellen;
	}

	/**
	 * 
	 * @param linien
	 *            setzt die Liste der Linien
	 */
	public void setLinien(ArrayList<Linie> linien) {
		Logger.getInstance().log("linien added");
		this.linien = linien;
	}

	/**
	 * 
	 * @param strassenListeNeu
	 *            setzt die Liste aller Strassen
	 */
	public void setStrassenListeNeu(ArrayList<Planquadrat> strassenListeNeu) {
		Logger.getInstance().log("strassenliste resetted");
		this.strassenliste = strassenListeNeu;
	}

	/**
	 * 
	 * @param zielliste
	 * 
	 * setzt die Liste aller Ziele
	 */
	public void setZielliste(ArrayList<Ziel> zielliste) {
		this.zielliste = zielliste;
	}

	/**
	 * 
	 * @return liefert die Liste aller gesperrten Felder
	 */
	public ArrayList<Planquadrat> getFeldGesperrtListe() {
		return feldGesperrtListe;
	}

	/**
	 * 
	 * @return liefert die Liste aller gesperrten Haltestellen
	 */
	public ArrayList<GesperrteHaltestelle> getAlleGesperrteHaltestellenliste() {
		return this.gesperrteHaltestellenliste;
		
	}

	/**
	 * Setzt die gesperrten Haltestellen
	 * 
	 * @param gesperrteHaltestellenliste
	 */
	public void setGesperrteHaltestellenliste(
			ArrayList<GesperrteHaltestelle> gesperrteHaltestellenliste) {
		this.gesperrteHaltestellenliste = gesperrteHaltestellenliste;
	}

	/**
	 * Fügt eine gesperrte Haltestelle dazu
	 * 
	 * @param gesperrteHaltestelle
	 */
	public void addGesperrteHaltestelle(
			GesperrteHaltestelle gesperrteHaltestelle) {
		this.gesperrteHaltestellenliste.add(gesperrteHaltestelle);
	}

	/**
	 * Löscht eine gesperrte Haltestelle
	 * 
	 * @param gesperrteHaltestelle
	 */
	public void removeGesperrteHaltestelle(
			GesperrteHaltestelle gesperrteHaltestelle) {
		for(int i= 0; i<gesperrteHaltestellenliste.size(); i++){
			if(gesperrteHaltestellenliste.get(i).getId() == gesperrteHaltestelle.getId() ){
				gesperrteHaltestellenliste.remove(i);
			}
		}
	}
}
