package standortplanung;

/**
 * <p>
 * Überschrift: Standortplanung
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author Manuel Thoma, Markus Klemens
 * @version 3.0
 */

public interface Solver {

	public void starteSolver(Daten matrix, int distanz);

	public String[] getErgebnis();

	public String getZielfunktionswert();

}