package lpSolver;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
//Klasse wurde von den Studierenden erstellt
public class SolverPath {

	public String getLPSolverPath() throws IOException {

		String solverPath = "";

		Properties prop = new Properties();

		try {

			prop.load(new FileReader("..\\Umladeplanung_v0.3.8\\up.properties"));

			solverPath = (String) prop.get("lpSolvePath");

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return solverPath;
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
