package trOptimizer;

import jtoolbox.*;

/**
 * <p>
 * Application: Tr-Optimizer 2.1
 * </p>
 * <p>
 * Class: IniPaths
 * </p>
 * <p>
 * Description: Auslesen und ueberschreiben der Solver-Pfade aus einer INI-Datei
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Nicolas Tedjadharma, Markus Rommelfanger
 * @version 1.0
 */

public class IniPaths {

	private static final String iniPath = "C:\\Methodenbank\\Methoden\\Kategorie A\\TR Optimizer\\TrOptimizer2.1\\01 programm\\paths.ini";
	private static final INIDatei paths = new INIDatei(iniPath);

	//lp_solve.exe Dateipfad von der paths.ini Datei nehmen
	public static String getLpSolvePath() {
		String result = "c:\\methodenbank\\solver\\lp_solve\\exec\\lp_solve.exe";
		String lpSolverPath = paths.leseString("LPSolve", "Path");
		if (lpSolverPath != null) {
			result = lpSolverPath;
		}
		return result;
	}

	//Zum Schreiben neuen lp_solve.exe Dateipfad in der paths.ini Datei 
	public static void setLpSolvePath(String newLpSolvePath) {
		if (newLpSolvePath != null) {
			paths.setzeString("LPSolve", "Path", newLpSolvePath);
			paths.schreibeINIDatei(iniPath, true);
		}
	}

	//MOPS Dateipfad von der paths.ini Datei nehmen
	public static String getMOPSPath() {
		String result = "C:\\Methodenbank\\Solver\\MOPS 7.06\\Exec\\mops.exe";
		String cplexPath = paths.leseString("MOPS", "Path");
		if (cplexPath != null) {
			result = cplexPath;
		}
		return result;
	}

	//Zum Schreiben neuen MOPS Dateipfad in der paths.ini Datei 
	public static void setMOPSPath(String newMOPSPath) {
		if (newMOPSPath != null) {
			paths.setzeString("MOPS", "Path", newMOPSPath);
			paths.schreibeINIDatei(iniPath, true);
		}
	}

	//Weidenauer Dateipfad von der paths.ini Datei nehmen
	public static String getWeidenauerPath() {
		String result = "c:\\methodenbank\\solver\\ilog\\cplex.exe";
		String cplexPath = paths.leseString("Weidenauer", "Path");
		if (cplexPath != null) {
			result = cplexPath;
		}
		return result;
	}

	//Zum Schreiben neuen Weidenauer Dateipfad in der paths.ini Datei 
	public static void setWeidenauerPath(String newWeidenauerPath) {
		if (newWeidenauerPath != null) {
			paths.setzeString("Weidenauer", "Path", newWeidenauerPath);
			paths.schreibeINIDatei(iniPath, true);
		}
	}
	
	//Weidenauer Dateipfad von der paths.ini Datei nehmen
	public static String getTempPath() {
		String result = "c:\\temp\\";
		String tempPath = paths.leseString("Temp", "Path");
		if (tempPath != null) {
			result = tempPath;
		}
		return result;
	}

	//Zum Schreiben neuen Weidenauer Dateipfad in der paths.ini Datei 
	public static void setTempPath(String newTempPath) {
		if (newTempPath != null) {
			paths.setzeString("Temp", "Path", newTempPath);
			paths.schreibeINIDatei(iniPath, true);
		}
	}
}
