package de.fh_konstanz.simubus.model.statistik;

import java.util.HashMap;
import java.util.Map;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;

/**
 * Enthält Auswertungen über eine Buslinie
 */
public class Linienstatistik {
	private Buslinie linie;
	
	private Map<Haltestelle, Werte> passagiereAufStreckenabschnitt = new HashMap<Haltestelle, Werte>();

	private Map<Haltestelle, Integer> erfasstePassagiereAufStreckenabschnitt = new HashMap<Haltestelle, Integer>();

	public Linienstatistik(Buslinie linie) {
		for (Haltestelle hs : linie.getHaltestellen()) {
			erfasstePassagiereAufStreckenabschnitt.put(hs, 0);
			passagiereAufStreckenabschnitt.put(hs, new Werte());
		}
	}
	
	public void aktualisierePassagiereAufStreckenabschnitt(Haltestelle hs,
			int anzPassagiere) {
		// neuen Durchschnitt berechnen
		int erfasste = erfasstePassagiereAufStreckenabschnitt.get(hs);
		double alterDurchschnitt = passagiereAufStreckenabschnitt.get(hs)
				.getDurchschnitt();
		double Gewichtung = erfasste + 1;
		double neuerDurchschnitt = ((erfasste * alterDurchschnitt) + anzPassagiere)
				/ (erfasste + 1);
		passagiereAufStreckenabschnitt.get(hs).setDurchschnitt(
				neuerDurchschnitt);
		
		// bei Bedarf neues Maximum erfassen
		if (anzPassagiere
				> passagiereAufStreckenabschnitt.get(hs).getMaximum()) {
			passagiereAufStreckenabschnitt.get(hs).setMaximum(anzPassagiere);
		}
		
		// bei Bedarf neues Minimum erfassen
		if (anzPassagiere
				< passagiereAufStreckenabschnitt.get(hs).getMinimum()) {
			passagiereAufStreckenabschnitt.get(hs).setMinimum(anzPassagiere);
		}
		
		erfasstePassagiereAufStreckenabschnitt.put(hs,
				erfasstePassagiereAufStreckenabschnitt.get(hs) + 1);
	}
	
	public double getDurschnittPassagiereAufStreckenabschnitt(Haltestelle hs) {
		return passagiereAufStreckenabschnitt.get(hs).getDurchschnitt();
	}
	
	public int getMaxPassagiereAufStreckenabschnitt(Haltestelle hs) {
		return passagiereAufStreckenabschnitt.get(hs).getMaximum();
	}
	
	public int getMinPassagiereAufStreckenabschnitt(Haltestelle hs) {
		return passagiereAufStreckenabschnitt.get(hs).getMinimum();
	}
}
