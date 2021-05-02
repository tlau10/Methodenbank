package de.fh_konstanz.simubus.model;

import java.io.Serializable;

/**
 * Ein Passagier erhaelt bei Angabe seines Endziels von seiner Haltestelle
 * die Information welche Linie er als naechstes bis zu welcher Haltestelle
 * nehmen muss. Diese Information ist in dieser Klasse gekapselt.
 */
public class Fahranweisung implements Serializable{
	private Buslinie linie;
	private Haltestelle aussteigeHaltestelle;
	
	public Fahranweisung(Buslinie linie, Haltestelle aussteigeHaltestelle) {
		this.linie = linie;
		this.aussteigeHaltestelle = aussteigeHaltestelle;
	}
	
	public Haltestelle getAussteigeHaltestelle() {
		return aussteigeHaltestelle;
	}
	
	public Buslinie getLinie() {
		return linie;
	}
}
