package de.fh_konstanz.simubus.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.fh_konstanz.simubus.model.statistik.Haltestellenstatistik;
import de.fh_konstanz.simubus.model.statistik.Linienstatistik;
import desmoj.core.simulator.Queue;

/**
 * Enthält Auswertungen für einen Simulationsdurchlauf. Wird während der
 * Simulation laufend aktualisiert.
 */
public class Statistik {
	private static Statistik instance;
	
	private int registrierteReisezeiten = 0;
	private double durchschnittlicheReisezeit = 0; 
	private Map<Buslinie, Linienstatistik> linienstatistiken = new HashMap<Buslinie, Linienstatistik>();
	private Map<Haltestelle, Haltestellenstatistik> haltestellenstatistiken = new HashMap<Haltestelle, Haltestellenstatistik>();
	
	private Statistik() {
		
	}
	
	public static synchronized Statistik getInstance() {
		if (instance == null) {
			instance = new Statistik();
		}	
		return instance;
	}

	/**
	 * Gibt die durchschnittliche Reisezeit der Passagiere zurück. Deise setzt
	 * sich zusammen aus Wartezeiten und Fahrzeiten mit dem Bus. Die Zeit, die
	 * die Passagiere zu Fuß gehen um ihr Ziel zu erreichen, wird nicht
	 * berücksichtigt.
	 * @return durchschnittlicheReisezeit
	 */
	public double getDurchschnittlicheReisezeit() {
		return durchschnittlicheReisezeit;
	}

	/**
	 * Wenn ein Passagier am Ziel angekommen ist, registriert er seine Reisezeit
	 * mit dieser Funktion bei der Statistik
	 * @param reisezeit
	 */
	public void registriereReisezeit(double reisezeit) {
		double gewichtung = 1.0 / (registrierteReisezeiten + 1);
		durchschnittlicheReisezeit
				= ((registrierteReisezeiten * durchschnittlicheReisezeit)
						+ reisezeit) / (registrierteReisezeiten + 1);
		registrierteReisezeiten++;
	}

	public static void init() {
		instance = new Statistik();
		for (Buslinie linie : Gesamtfahrplan.getInstance().getBuslinien()) {
			instance.linienstatistiken.put(linie, new Linienstatistik(linie));
		}
		for (Haltestelle hs : Strassennetz.getInstance().getAlleHaltestellen()){
			instance.haltestellenstatistiken.put(
					hs, new Haltestellenstatistik(hs));
		}
	}
	
	/**
	 * Gibt die Statistik für eine bestimmte Buslinie zurück
	 * @param linie
	 * @return linienstatistik
	 */
	public Linienstatistik getLinienstatistik(Buslinie linie) {
		return linienstatistiken.get(linie);
	}
	
	/**
	 * Gibt Statistiken für alle Buslinien zurück
	 * @return linienstatistiken
	 */
	public Collection<Linienstatistik> getLinienstatistiken() {
		return linienstatistiken.values();
	}
	
	/**
	 * Gibt die Statistik für eine bestimmte Haltestelle zurück
	 * @param hs Haltestelle
	 * @return haltestellenstatistik
	 */
	public Haltestellenstatistik getHaltestellenstatistik(Haltestelle hs) {
		return haltestellenstatistiken.get(hs);
	}
	
	/**
	 * Gibt die Statistiken für alle Haltestellen zurück
	 * @return haltestellenstatistiken
	 */
	public Collection<Haltestellenstatistik> getHaltestellenstatistiken() {
		return haltestellenstatistiken.values();
	}
}
