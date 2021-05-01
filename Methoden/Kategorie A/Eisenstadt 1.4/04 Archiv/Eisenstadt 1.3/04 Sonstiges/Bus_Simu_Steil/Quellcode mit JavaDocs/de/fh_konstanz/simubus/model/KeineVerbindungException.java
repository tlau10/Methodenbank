package de.fh_konstanz.simubus.model;

/**
 * Wird geworfen, wenn es keine Möglichkeit gibt von der Quellhaltestelle
 * zur Zielhaltestelle zu gelangen, auch nicht mit Umsteigen
 */
public class KeineVerbindungException extends Exception {
	private Haltestelle quellhaltestelle;
	private Haltestelle zielhaltestelle;
	
	public KeineVerbindungException(Haltestelle quellhaltestelle,
			Haltestelle zielhaltestelle) {
		this.quellhaltestelle = quellhaltestelle;
		this.zielhaltestelle = zielhaltestelle;
	}

	public Haltestelle getQuellhaltestelle() {
		return quellhaltestelle;
	}

	public Haltestelle getZielhaltestelle() {
		return zielhaltestelle;
	}
}
