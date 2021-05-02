package de.fh_konstanz.simubus.model;
/**
 * @author Tobias Lott
 *
 * Klassen, die dieses Interface implementieren, können sich bei einem Bus
 * als Observer anmelden
 */
public interface BusObserver {
	/**
	 * Wird immer dann ausgeführt wenn ein Bus an einer Haltestelle abfaehrt
	 */
	public void busFaehrtLos(Bus bus);
	
	/**
	 * Wird immer dann ausgeführt, wenn ein Bus an seiner letzten Haltestelle angekommen ist
	 * und das System verlässt.
	 * @param bus
	 */
	public void busVerschwindet(Bus bus);
}
