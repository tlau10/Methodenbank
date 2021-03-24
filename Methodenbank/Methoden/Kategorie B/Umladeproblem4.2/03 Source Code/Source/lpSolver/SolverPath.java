package lpSolver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import jtoolbox.INIDatei;
//Klasse wurde von den Studierenden erstellt
public class SolverPath {
	private static final String iniPath = "C://Methodenbank//Methoden//Kategorie B//Umladeproblem 4.0//01 Programm//paths.ini";
	private static final INIDatei paths = new INIDatei(iniPath);
	private String datei = new String();

	
	public String getLPSolverPath() throws IOException {

		String result = "C:\\Methodenbank\\Solver\\LP_Solve\\Exec\\LP_Solve.exe";
    

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

	
	
	public String getWebsolverPath() throws IOException {

		String solverPath = "";

		Properties prop = new Properties();

		try {

			prop.load(new FileReader("..\\Umladeplanung_v1.2\\up.properties"));

			solverPath = (String) prop.get("webSolverPath");

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return solverPath;
	}

}
