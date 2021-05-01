package de.fh_konstanz.simubus.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.fh_konstanz.simubus.view.Editor;
import de.fh_konstanz.simubus.view.Field;
import desmoj.core.simulator.Model;

public class Strassennetz implements Serializable {
	private Set<Haltestelle> netz = new HashSet<Haltestelle>();
	private ArrayList<Point> strassenliste = new ArrayList<Point>();
	private ArrayList<Field> strassenListe = new ArrayList<Field>();
	private ArrayList<Ziel> zielliste = new ArrayList<Ziel>();
	private Map<Point, Haltestelle> zuordnungZielZuHaltestelle
			= new HashMap<Point, Haltestelle>();
	private static Strassennetz instance;
	
	private Strassennetz() {
	}
	
	public static synchronized Strassennetz getInstance() {
		if (instance == null)
			instance = new Strassennetz();
		
		return instance;
	}
	
	public void addHaltestelle(Haltestelle h) {
		netz.add(h);
	}
	
	public void removeHaltestelle(Haltestelle h) {
		netz.remove(h);
	}
	
	public Collection<Haltestelle> getAlleHaltestellen() {		
		return netz;
	}

	/**
	 * Berechnet im Voraus für jede Start-/Zielhaltestellenkombination welchen
	 * Bus ein Passagier bis zu welcher Haltestelle nehmen muss.
	 * @throws KeineVerbindungException 
	 *
	 */
	public void initHaltestellen(Model model) throws KeineVerbindungException {
		for (Haltestelle haltestelle : getAlleHaltestellen()) {
			haltestelle.init(model);
		}
	}
	
	public void addStrasse(Point strasse) {
		strassenliste.add(strasse);
	}

	public void removeStrasse(Point strasse) {
		strassenliste.remove(strasse);
	}

	public void addStrasse(Field strasse) {
		strassenListe.add(strasse);
	}

	public void removeStrasse(Field strasse) {
		strassenListe.remove(strasse);
	}

	public void addZiel(Ziel ziel) {
		zielliste.add(ziel);
	}

	public void removeZiel(Ziel ziel) {
		zielliste.remove(ziel);
	}

	public ArrayList<Point> getStrasse() {
		return strassenliste;
	}
	
	public ArrayList<Field> getStrassenListe() {
		return strassenListe;
	}
	
	public ArrayList<Ziel> getZiele() {
		return zielliste;
	}

	/**
	 * Setzt die instance-Variable des Singletons auf null
	 */
	public static void reset() {
		instance = null;
	}
	
	/**
	 * Setzt die instance-Variable neu
	 */
	public static void setInstance(Strassennetz instance) {
		Strassennetz.instance = instance;
	}
	
	/**
	 * Gibt die nächstgelegene Haltestelle für ein Ziel auf der Karte zurück.
	 * @param ziel
	 * @return haltestelle
	 */
	public Haltestelle getHaltestelleFuerZiel(Point ziel) {
		// Falls die nächstgelegene Haltestelle noch nicht ermittelt worden ist
		// wird dies hier getan
		if (zuordnungZielZuHaltestelle.get(ziel) == null){
			double minDist = Double.MAX_VALUE;
			Haltestelle naechstgelegeneHaltestelle = null;
			for (Haltestelle hs : netz) {
				double dist = hs.diagonalDistanz(ziel);
				if (dist < minDist) {
					minDist = dist;
					naechstgelegeneHaltestelle = hs;
				}
			}
			zuordnungZielZuHaltestelle.put(ziel, naechstgelegeneHaltestelle);
		}
		return zuordnungZielZuHaltestelle.get(ziel);
	}
	
	public void setStrassenListe(ArrayList<Field> neu) {
		this.strassenListe = neu;
	}
	
	public void resetHaltestellen() {
		// Haltestellen aus Strassenfelder löschen
		for (int i=0; i< strassenListe.size(); i++) {
			// Haltestelle entfernen
			strassenListe.get(i).setStreet();
		}
		
		netz.clear();
	}
}
