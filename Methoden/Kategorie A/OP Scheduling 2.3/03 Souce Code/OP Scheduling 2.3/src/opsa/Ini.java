package opsa;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Title: Operationssleplanung Description: Das Prototyp wurde von Liwei Lu &
 * Arne Bittermann ertellt. Copyright: Copyright (c) WI8 SS2002 Company: FHKN
 *
 * @author Arne Bittermann, Liwei Lu
 * @version 1.0
 */

public class Ini {

	int defaultPeriode = 60;

	String LPsolvePath = "";
	String LPsolvePath2 = "";
	String CheckedPath = "";
	String s1, s2, s3, s4;
	public Ini() {
		readIni();
	}

	public int getDefauldPeriode() {
		return defaultPeriode;
	}

	public String getLPsolvePath() {
		return LPsolvePath;
	}
	
	public String getLPsolvePath2() {
		return LPsolvePath2;
	}
	public String getCheckedPath() {
		return CheckedPath;
	}

	public boolean readIni() {
		try {
			File inFile = new File("ini.txt");
			FileReader theReader = new FileReader(inFile);
			BufferedReader in = new BufferedReader(theReader);
			if (in.ready()) {
				s1 = in.readLine();
				s2 = in.readLine();
				s3 = in.readLine();
				s4 = in.readLine();
			}
			defaultPeriode = Integer.parseInt(s1.substring(s1.indexOf("=") + 1));
			LPsolvePath = s2.substring(s2.indexOf("=") + 1);
			LPsolvePath2 = s3.substring(s3.indexOf("=") + 1);
			CheckedPath = s4.substring(s4.indexOf("=") + 1);
			in.close();
		} catch (Exception e3) {
			System.out.println(e3.toString());
			return false;
		}
		return true;
	}

	public boolean writeIni(String lpPath, String lpPath2, String checkedPath) {
		try {
			File inFile = new File("ini.txt");
			FileWriter theWriter = new FileWriter(inFile);
			BufferedWriter out = new BufferedWriter(theWriter);
			out.write("defauldPeriode=" + defaultPeriode + "\r");
			out.write("LPsolvePath=" + lpPath + "\r");
			out.write("LPsolvePath2=" + lpPath2+ "\r");
			out.write("checkedPath=" + checkedPath);
			out.close();
		} catch (Exception e3) {
			System.out.println(e3.toString());
			return false;
		}
		return true;
	}
}