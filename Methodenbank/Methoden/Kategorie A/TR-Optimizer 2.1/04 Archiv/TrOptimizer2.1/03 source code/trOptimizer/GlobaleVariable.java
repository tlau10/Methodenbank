package trOptimizer;

import java.io.File;

public class GlobaleVariable {

	public static boolean autosaveSTATUS = false;
	public static int solvertyp = 1;

	/*
	 * Speichern Funktion nach der Speicher unter Funktion oder Nach dem Datei
	 * Oeffnen? Dient zum Auswahl der benoetigten File-Variable zum Speichern
	 * Bedeutung: 0 = Programmstart oder das Menue "Neu" wurde gewaehlt 1 = Eine
	 * Datei wurde gespeichert durch die Funktion "Speichern unter" 2 = Eine
	 * Datei wurde mit der Funktion "oeffnen" geoeffnet
	 */
	public static int speichernach = 0;
	public static int autosaveSECONDS = 30;
	public static File selFile;
	public static File oeffneFile;
}
