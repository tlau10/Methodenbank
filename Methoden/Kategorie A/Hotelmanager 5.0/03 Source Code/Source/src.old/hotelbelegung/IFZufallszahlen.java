package hotelbelegung;

/**
 * Überschrift: Hotelbelegung Beschreibung: Copyright: Copyleft (c) 2014
 * Organisation: HTWG
 * 
 * @author Florian Raiber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003
 * @Version 2.0.1 Christian Gruhler SS08
 * @Version 4.0 Vitaliy Davats, Dominique Lebert, Manuel Falkenstein WS2013/14
 */

public interface IFZufallszahlen {
	/**
	 * Gibt die nächste Pseudo-Zufallszahl auf dem Intervall [0,1] zurück.
	 * 
	 * @return double : Zufallszahl (double) auf dem Intervall [0,1]
	 */
	public double nextDouble();

}