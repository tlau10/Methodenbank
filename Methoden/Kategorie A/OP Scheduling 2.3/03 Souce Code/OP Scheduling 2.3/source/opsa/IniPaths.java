package opsa;

import jtoolbox.*;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: IniPaths
 * </p>
 * <p>
 * Description: Auslesen und Überschreiben der Solver-Pfade aus einer INI-Datei
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 * 
 * Übernommen aus Ernährungsplaner
 */

public class IniPaths {

	private static final String iniPath = "paths.ini";
	private static final INIDatei paths = new INIDatei(iniPath);

	public static String getLpSolvePath() {
		String result = "C:\\methodenbank\\solver\\LP_Solve\\Exec\\lp_solve.exe";
		String lpSolverPath = paths.leseString("LPSolve", "Path");
		if (lpSolverPath != null) {
			result = lpSolverPath;
		}
		return result;
	}

	public static void setLpSolvePath(String newLpSolvePath) {
		if (newLpSolvePath != null) {
			paths.setzeString("LPSolve", "Path", newLpSolvePath);
			paths.schreibeINIDatei(iniPath, true);
		}
	}
/*
	public static String getCplexPath() {
		String result = "c:\\methodenbank\\solver\\ilog\\cplex.exe";
		String cplexPath = paths.leseString("Cplex", "Path");
		if (cplexPath != null) {
			result = cplexPath;
		}
		return result;
	}

	public static void setCplexePath(String newCplexPath) {
		if (newCplexPath != null) {
			paths.setzeString("Cplex", "Path", newCplexPath);
			paths.schreibeINIDatei(iniPath, true);
		}
	}
*/
	public static String getTempPath() {
		String result = "c:\\temp\\";
		String tempPath = paths.leseString("Temp", "Path");
		if (tempPath != null) {
			result = tempPath;
		}
		return result;
	}

	public static void setTempPath(String newTempPath) {
		if (newTempPath != null) {
			paths.setzeString("Temp", "Path", newTempPath);
			paths.schreibeINIDatei(iniPath, true);
		}
	}
}
