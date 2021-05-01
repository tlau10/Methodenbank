package Dakin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JOptionPane;

//////////////////////////////////////////////////
// NEU SS13 ==> Solverpfad wird bei jedem Programmstart
// frisch aus einer außerhalb liegenden Textdatei eingelesen.
// Bei Umzug des Solvers --> Problemlose Änderung außerhalb 
// des Codes!
//////////////////////////////////////////////////
public class PathReader {
	private String pfad = "";

	public PathReader() throws Exception {
		readFile();
	}

	public void readFile() throws Exception {

		BufferedReader leser = new BufferedReader(new
		// FileReader("C:\\Users\\BJ\\Documents\\FH\\Semester_6\\Lineare Programmierung\\pfad.txt"));
		// FileReader("Z:\\Semester_6\\Lineare Programmierung\\pfad.txt"));
				FileReader("pfad.txt"));
		String zeile = null;
		
		if ((zeile = leser.readLine()) != null) {
			setPfad(zeile);
		}
		
		String testPfad = getPfad() + "lp_solve.exe";
		File file = new File(testPfad);
		if (file.exists() != true) {
			//System.out.println("Datei existiert nicht!");
			throw new Exception("Datei existiert nicht am angegebenen PFad!");
		}
		leser.close();

	}

	public String getPfad() {
		return pfad;
	}

	public void setPfad(String pfad) {
		this.pfad = pfad;
	}

}
