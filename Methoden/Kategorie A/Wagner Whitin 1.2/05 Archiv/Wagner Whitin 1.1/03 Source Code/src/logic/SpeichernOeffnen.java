package logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.csvreader.CsvReader;

public class SpeichernOeffnen {
	//Methode um Pfad auszuwählen
    public static String fileChooserDialog(String String) {
    	//Variable dekl./init.
    	String pfadAuswahl = null;   
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
    	if (String == "speichern") {
            // Dialog zum speichern von Dateien anzeigen
            chooser.showSaveDialog(null);
            //gewählten Pfadnamen in Variable speichern
            pfadAuswahl = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("der gewählte Pfad ist:" + pfadAuswahl);	
		} else {
            // Dialog zum Oeffnen von Dateien anzeigen
            chooser.showOpenDialog(null);
            //gewählten Pfadnamen in Variable speichern
            pfadAuswahl = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("der gewählte Pfad ist:" + pfadAuswahl);	
		}
		return pfadAuswahl;
    }
    
    
  //Methode zum speichern eines Modells

    public static void save(Object[][] tabellenWerte,String bestellKostenSatz, String periodenZahl, String Pfad) {// Übergibt den Tabelleninhalt, den Rüst-/Bestellkostensatz, die Anzahl der Perioden und den Speicherpfad
    	try {

    	    BufferedWriter writer = new BufferedWriter(new FileWriter(Pfad + ".wwa"));  // Pfad variabel + Dateiendung
    	    	writer.write(bestellKostenSatz);//+++++testweise ob bestellkosten reingeschrieben werden
    	    	writer.newLine(); 
    	    	writer.write(periodenZahl); //AnzahlPerioden in Datei schreiben
    	    	writer.newLine();
    	    	//ab hier werden die Daten aus der Tabelle eingelesen
    	    	for (int i = 0; i < tabellenWerte.length; i++) {
    	             for (int j = 0; j < tabellenWerte[i].length; j++) {
    	                  writer.write(tabellenWerte[i][j] + ";");   //mit Semikolon getrennte Werte (Spaltentrennung)
    	              }
    	              writer.newLine(); // Zeilentrennung
    	              System.out.println(i);
    	         }
    	         writer.close();
    	}
    	catch (IOException e) {
    	e.printStackTrace();
    	}
    }
   

    
    
// Methode zum Laden einer .wwa Datei 
    

	public static String[][] read(String file) {
        List<String[]> list = new ArrayList<String[]>();
        int zeile; 
        zeile = 0;//Initialisierung der Perioden
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));
            String in = read.readLine();
           
            while (in != null) {
            	//sonderbehandlung erste Zeile da hier der Bestellkostensatz stehen wird
            	if (zeile == 0) {
                	try {
                        logic.WagWhit.setRuestkosten(Double.parseDouble(in)); //Rüstkosten-/Bestellkostensatz wird eingelesen
                    	in = read.readLine();
                        logic.WagWhit.setAnzahlPerioden(Integer.parseInt(in));//entspricht PeriodenAnzahl die eingelesen und für die Berechnung benötigt wird 
                        in = read.readLine();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(new JFrame(), "Fehler beim Einlesen, Bestellkosten-Wert ist keine Zahl (Double) ", "Fehlerhafte Datei",
                                JOptionPane.ERROR_MESSAGE);
                    }
				} else {
					String[] line = in.split(";");
	                list.add(line);
	                in = read.readLine();
				}

                zeile += 1;
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String[][] tmp = new String[list.size()][];    

		return list.toArray(tmp);
    }   
    
}



  