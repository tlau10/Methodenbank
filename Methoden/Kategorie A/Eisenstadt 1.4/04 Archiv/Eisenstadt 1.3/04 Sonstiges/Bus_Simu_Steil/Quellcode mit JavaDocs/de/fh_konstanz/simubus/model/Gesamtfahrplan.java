
package de.fh_konstanz.simubus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import desmoj.core.simulator.SimTime;


/**
 * Singleton.
 * Enthält die Fahrpläne für alle Buslinien.
 *
 * Funktionsbeschreibung
 */
public class Gesamtfahrplan implements Serializable{
	private static Gesamtfahrplan instance;
	
	private Set<Buslinie> linien;

	private transient List<RingBetretenEvent> ringevents;

	private transient Iterator<RingBetretenEvent> aktuellerZeiger;
	
	/**Initialisiert den Gesamtfahrplan
	 * darf nur von getInstance() aufgerufen werden
	 *
	 */
	private Gesamtfahrplan() {
		ringevents = new ArrayList<RingBetretenEvent>();
		linien = new HashSet<Buslinie>();
		prepare();
	}
	
	/**
	 * Diese Methode muß aufgerufen werden, nachdem die Daten eingespeist wurden.
	 * Sie kann auch aufgerufen werden, um die Zeit für die Busankunft zurückzusetzen
	 *
	 */
	public void prepare() {
		aktuellerZeiger = ringevents.iterator();
		Collections.sort(ringevents, new RingEventComparator());
	}

	/**
	 * Singleton-Factory Methode
	 * @return die Instanz des Gesamtfahrplans
	 */
	public static synchronized Gesamtfahrplan getInstance() {
		if (instance == null) {
			instance = new Gesamtfahrplan();
		}
		return instance;
	}

	/**Das nächste Ankunftsereignis für den Ring.
	 * 
	 * @return Das nächste Ankunftsereignis für den Ring
	 */
	public RingBetretenEvent getNextRingEntryEvent() {
		return aktuellerZeiger.next();
	}

	/**Prüft, ob noch ein Ankunftsereignis vorhanden ist
	 * 
	 * @return true, wenn noch ein Ankunftsereignis vorhanden iust 
	 */
	public boolean hasNextRingEntryEvent() {
		return aktuellerZeiger.hasNext();
	}

	/**
	 * Fügt einen neuen Linienfahrplan dem Gesamt hinzu
	 * @param linie die Linie
	 */
	public void addLinie(Buslinie linie) {
		linien.add(linie);
	}

	/**
	 * Entfernt die übergebene Buslinie
	 * @param linie die Linie
	 */
	public void removeLinie(Buslinie linie) {
		linien.remove(linie);
	}

	public void removeHaltestellenAusBuslinien() {
		Iterator<Buslinie> it = linien.iterator(); 
		while (it.hasNext()) {
			Buslinie bus = it.next(); 
			bus.clearPath();
			bus.deleteHaltestellen();
		}
	}
	
	
	/**
	 * Fügt einen neuen Beginn eines Linienzyklusses dem Gesamtfahrplan hinzu
	 * @param linie die Linie
	 * @param zeit die Zeit, zu der die Linie seinen Dienst beginnen soll
	 */
	public void addLinienStart(Buslinie linie, SimTime zeit) {
		ringevents.add(new RingBetretenEvent(zeit, linie));		
	}
	
	/**
	 * @param haltestelle
	 * @return alle Buslinien, die die Haltestelle anfahren
	 */
	public Set<Buslinie> getLinien(Haltestelle haltestelle) {
		Set<Buslinie> ergebnisSet = new HashSet<Buslinie>();		
		for (Buslinie linie : linien) {
			if (linie.getHaltestellen().contains(haltestelle)) {
				ergebnisSet.add(linie);
			}
		}
		return ergebnisSet;
	}
	
	/**
	 * @return alle Buslinien
	 */
	public Set<Buslinie> getBuslinien() {
		return linien;
	}
	
	/**
	 * Setzt die instance-Variable auf null
	 */
	public static void reset() {
		instance = null;
	}
	
	public int getGesamtanzahlPassagiereZiel(Ziel ziel) {
		
		int summe = 0;
		Iterator<Buslinie> it = linien.iterator();
		Buslinie linie;
		while (it.hasNext()) {
			linie = it.next();
			summe += linie.getZielWahrscheinlichkeit(ziel)*linie.getPassagiereProTag();
		}
		
		return summe;
	}
	
	/**
	 * Setzt die instance-Variable neu
	 */
	public static void setInstance(Gesamtfahrplan instance) {
		Gesamtfahrplan.instance = instance;
	}
	
	/**
	 * Vergleicht zwei Ankunftsereignisse und sortiert das frühere zuerst ein
	 * @author gott
	 *
	 */
	private class RingEventComparator implements Comparator {
		public int compare(Object arg0, Object arg1) {
			double first = ((RingBetretenEvent) arg0).getTime().getTimeValue();
			double last = ((RingBetretenEvent) arg1).getTime().getTimeValue();
			if (first < last)
				return -1;
			if (first == last)
				return 0;
			return 1;

		}
	}
}
