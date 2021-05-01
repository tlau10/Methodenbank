package planer;
import java.io.*;

public class LP_Solve implements Solver {

    String tempVerzeichnis;
    String lpSolvVerzeichnis;
    String enter;

    /** Creates a new instance of LP_Solve */
    public LP_Solve()
    {
      tempVerzeichnis = new String("c:\\temp\\");
      lpSolvVerzeichnis = new String(System.getProperty("user.dir") + "\\Solver\\");
      enter = System.getProperty("line.separator");
    }

     public double[] calcModel(Matrix lpModell)
     {
       return calcModel(lpModell,new Matrix(1000,4,1000,4));
     }

    public double[] calcModel(Matrix lpModell, Matrix grenzen)
    {

        // Pfade von Oberfläche holen
        int anzSpalten = lpModell.getAnzahlSpalten();
        int anzZeilen = lpModell.getAnzahlZeilen();

        String solver_input = new String();

        // Zielfunktionszeile
        solver_input = solver_input.concat(lpModell.getElement(0,anzSpalten-1));
        solver_input = solver_input.concat(": ");

        for(int i=0;i<anzSpalten;i++) {
            if(i<anzSpalten-3) {
                solver_input = solver_input.concat(lpModell.getElement(0,i)+"x"+(i+1)+" + ");
            }
            else if(i == anzSpalten-3) {
                solver_input = solver_input.concat(lpModell.getElement(0,i)+"x"+(i+1)+";");
            }
        }
        solver_input = solver_input.concat(enter);

        // Restriktionen
        for(int i=1;i<anzZeilen;i++) {
            solver_input = solver_input.concat(enter + "R"+i+": ");
            for(int j=0;j<anzSpalten;j++) {
                if(j<anzSpalten-3) {
                    solver_input = solver_input.concat(lpModell.getElement(i,j)+" x"+(j+1)+" + ");
                }
                else if (j<anzSpalten-2) {
                    solver_input = solver_input.concat(lpModell.getElement(i,j)+" x"+(j+1));
                }
                else if (j<anzSpalten-1){
                    solver_input = solver_input.concat(" "+lpModell.getElement(i,j));
                }
                else {
                    solver_input = solver_input.concat(" "+lpModell.getElement(i,j)+";");
                }
            }
        }

        // Ganzzahligkeit und Variableneinschraenkungen
        solver_input = solver_input.concat(enter);
        for(int x=0; x<anzSpalten-2; x++) {
            if(grenzen.getElementInt(x,0) >0)
            {
                solver_input = solver_input.concat("x"+(x+1)+">="+grenzen.getElement(x,0)+";"+enter);
            }
             if(grenzen.getElementInt(x,1) >0)
             {
                solver_input = solver_input.concat("x"+(x+1)+"<="+grenzen.getElement(x,1)+";" +enter);
             }
        }

        boolean first = true;
        for(int x=0; x<anzSpalten-2; x++) {
            if(grenzen.getElement(x,2).equalsIgnoreCase("ja"))
            {
                if (first)
                {
                  solver_input = solver_input.concat(enter+ "int ");
                  solver_input = solver_input.concat("x" + (x + 1));
                  first = false;
                }
                else
                  solver_input = solver_input.concat(",x"+(x+1));

            }
        }
        if(first == false)
        solver_input = solver_input.concat(";");

        //System.out.println(enter+ "LP-Modell:\n"+solver_input+"\n");

        // schreiben der Datei
        try {

            File neueDatei = new File(this.getTempVerzeichnis()+"diaet.lp");
            FileWriter writeOut = new FileWriter(neueDatei);
            writeOut.write(solver_input);
            writeOut.flush();
            File neueBatDatei = new File(this.getTempVerzeichnis()+"diaet.bat");
            FileWriter writeBatOut = new FileWriter(neueBatDatei);
            writeBatOut.write("\"" + this.getLpSolvVerzeichnis()+"lp_solve.exe\" -p <"+this.getTempVerzeichnis()+"diaet.lp >"+this.getTempVerzeichnis()+"diaet.out");
            writeBatOut.write(enter +"exit");
            writeBatOut.flush();

            writeOut.close();
            writeBatOut.close();


            // Ausfuehren des Solvers
            try {
            	// DEBUG AUSGABE
				//System.out.println("Es wird die Datei: \""+this.getTempVerzeichnis()+"hotel.bat\" ausgeführt !!!");
				//System.out.println("cmd /C start "+this.getTempVerzeichnis()+"hotel.bat");


                synchronized(this) {
                /////////////////////////
                //
                /// Hier wird der Solver aufgerufen
                //
                /////////////////////////


                Runtime rt = Runtime.getRuntime();
             	Process p = rt.exec("cmd /C start "+this.getTempVerzeichnis()+"diaet.bat");
				//System.out.println("Auf das Ende der Solverberechnungen warten");
             	p.waitFor();

				//System.out.println("2 Sekunden warten");
				Thread.sleep(3000);
                }

             	// Die Solverinputdatei löschen
             	//neueDatei.delete();
            }
            catch(Exception e) {
              System.out.print("Fehler beim Ausfuehren des Solvers\n"); e.printStackTrace();
            }
        } catch(java.io.IOException e) { System.out.println("Fehler beim Schreiben der Solverinput Datei"); }




        // lesen der Ergebnisdatei
        double[] ergebnis = new double[anzSpalten-1];

        ///////////////////////////////////////
        //
        /// Es wird versucht die vom Solver erzeugte Lösungsdatei zu lesen.
        /// Falls dies nicht gelingt wird 2 Sekunden gewartet und es wird erneut versucht,
        /// bis maximal 5 Versuche vergangej sind.
        //
        //////////////////////////////////////
        int versuche = 1;
        boolean erfolgreich = false;

        while(!erfolgreich && versuche <= 10) {
        versuche++;

        try {
			//System.out.println("Lese ErgebnisDatei...");
            BufferedReader in = new BufferedReader(new FileReader(this.getTempVerzeichnis()+"diaet.out"));
			//System.out.println("Lesen der ErgebnisDatei beendet");

			try {
				// Zielfunktionswert
                                String ersteZeile = in.readLine();
                                // Testen ob Lösung berechenbar ist, sonst gleich 0 als Ergebnis zurückgeben
                                // "This problem is infeasible"
                                // "This problem is unbounded"
                                if (ersteZeile.substring(0,4).equalsIgnoreCase("This"))
                                {
                                  return ergebnis;
                                }

				ergebnis[0]=Double.valueOf(ersteZeile.substring(28).trim()).doubleValue();
				String tmp;
				for(int i=0;i<anzSpalten-2;i++) {
					tmp = in.readLine();
					ergebnis[Integer.valueOf(tmp.substring(1,4).trim()).intValue()]=Double.valueOf(tmp.substring(5).trim()).doubleValue();
				}

				//System.out.println("\nErgebnis:");

				for(int i=0;i<anzSpalten-1;i++) {
					//System.out.println("X"+(i+1)+": "+ergebnis[i]);
				}

				erfolgreich = true;
			}
			catch(Exception e) {
			  System.out.println("Warten bis Ergebnisdatei fertig ist...." + enter);
			  //e.printStackTrace();
			  //JOptionPane.showMessageDialog(null, "Bitte eingestellte Pfade bzw. Verbindungen überprüfen.", "Fehler beim Solveraufruf", 0);

			  erfolgreich = false;
			}
        }
        catch(Exception e) {
			System.out.println("Fehler beim Einlesen der Ergebnisdatei" +enter);
			//e.printStackTrace();

			erfolgreich = false;
        }

        // Falls dieser Versuch die Datei zu parsen nicht geklappt hat, warten...
        if(!erfolgreich) {
        	try {
        		Thread.sleep(3000);
        	}
        	catch(Exception e) {
        		System.out.println("Fehler bei Sleep");
        	}
        }

        }
        //double[] ergebnis = new double[1];
        return ergebnis;
    }

    /** Getter for property tempVerzeichnis.
     * @return Value of property tempVerzeichnis.
     */
    public java.lang.String getTempVerzeichnis() {
        return tempVerzeichnis;
    }

    /** Setter for property tempVerzeichnis.
     * @param tempVerzeichnis New value of property tempVerzeichnis.
     */
    public void setTempVerzeichnis(java.lang.String tempVerzeichnis) {
        this.tempVerzeichnis = tempVerzeichnis;
    }

    /** Getter for property lpSolvVerzeichnis.
     * @return Value of property lpSolvVerzeichnis.
     */
    public java.lang.String getLpSolvVerzeichnis() {
        return lpSolvVerzeichnis;
    }

    /** Setter for property lpSolvVerzeichnis.
     * @param lpSolvVerzeichnis New value of property lpSolvVerzeichnis.
     */
    public void setLpSolvVerzeichnis(java.lang.String lpSolvVerzeichnis) {
        this.lpSolvVerzeichnis = lpSolvVerzeichnis;
    }
}
