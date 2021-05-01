package hotelbelegung;

import java.io.*;

public class DateiLaden {
	public String Laden(String dateiname) // Die Lade-Methode
	  {
		String ausgabe="";
		File datei = new File(dateiname);
		
	    if (datei != null)
	    {
	    	try // try-catch Prozedur um eventl fehler abzufangen
	        {
	    		FileReader eingabestrom = new FileReader(datei);
	    		int gelesen;
	    		StringBuffer text = new StringBuffer(100);
	    		boolean ende = false;

	    		// lese Zeichen, bis Dateiende erreicht ist
	    		while(!ende)
	    		{
	    			gelesen = eingabestrom.read();

	    			if(gelesen == -1){	
	    				ende = true;}
	    			else{
	    				text.append( (char) gelesen);}
	    		}

	    		ausgabe = (text.toString()); //Ausgabe des eingelesenen Strings
	    		eingabestrom.close(); // Schlieﬂen des Lesestroms (Datei wird dadurch wieder "frei")
	        }
	        catch(FileNotFoundException f) // F‰ngt Fehler beim ÷ffnen ab
	        {
	        	System.err.println(dateiname + " konnte nicht unter " + 
	        			System.getProperty("user.dir") + " gefunden werden.");
	        }
	        catch(IOException f) // F‰ngt Fehler beim Einlesen ab
	        {
	        	System.err.println("Fehler beim Einlesen der Datei " + dateiname 
	        			+ " von " + System.getProperty("user.dir"));
	        }
	    }
	    return ausgabe; // return des Ausgabestrings

	  }
}
