package de.fh_konstanz.simubus.model;

import desmoj.core.simulator.SimTime;

public class RingBetretenEvent {
	private Buslinie buslinie;
	private SimTime time;
	
	public RingBetretenEvent(SimTime t, Buslinie b) {
		if (b.getHaltestellen().size() < 2) {
			throw new RuntimeException(
					"Linie mit weniger als zwei Haltestellen ist sinnlos");
		} else {
			time=t;
			buslinie=b;
		}
	}
	/**
	 * @return Returns the Buslinie.
	 */
	public Buslinie getBuslinie() {
		return buslinie;
	}
	
	/**
	 * @return Returns the haltestelle.
	 */
	public Haltestelle getHaltestelle() {
		return buslinie.getHaltestellen().get(0);
	}

	/**
	 * @return Returns the time.
	 */
	public SimTime getTime() {
		return time;
	}

	

}
