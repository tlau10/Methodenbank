package hotelbelegung;

import java.io.*;
import javax.swing.*;

/*
 * LP_Solve.java
 *
 * Created on 2. Januar 2003, 16:21
 */

/**
 *
 * @author  Florian Raiber
 */

public class LP_Solve implements Solver {

    Frame1 frame;
    String tempVerzeichnis;
    String lpSolvVerzeichnis;

    /** Creates a new instance of LP_Solve */
    public LP_Solve() {
    }

    public double[] calcModel(Frame1 frame, String[][] lp_modell, int anzZeilen, int anzSpalten, String[][] restriktionen) {

        // Pfade von Oberfläche holen
        tempVerzeichnis = frame.jTextFieldArbeit.getText();
        lpSolvVerzeichnis = frame.jTextFieldSolver.getText() + "Lp_solve\\exec\\";//"l:\\Besf\\Solver\\Lp_solve\\exec\\";

        String solver_input = new String();

        // Zielfunktionszeile
        solver_input = solver_input.concat(lp_modell[0][anzSpalten-1].toString());
        solver_input = solver_input.concat(": ");

        for(int i=0;i<anzSpalten;i++) {
            if(i<anzSpalten-3) {
                solver_input = solver_input.concat(lp_modell[0][i]+"x"+(i+1)+" + ");
            }
            else if(i == anzSpalten-3) {
                solver_input = solver_input.concat(lp_modell[0][i]+"x"+(i+1)+";");
            }
        }
        solver_input = solver_input.concat("\n");

        // Restriktionen
        for(int i=1;i<anzZeilen;i++) {
            solver_input = solver_input.concat("\nR"+i+": ");
            for(int j=0;j<anzSpalten;j++) {
                if(j<anzSpalten-3) {
                    solver_input = solver_input.concat(lp_modell[i][j]+" x"+(j+1)+" + ");
                }
                else if (j<anzSpalten-2) {
                    solver_input = solver_input.concat(lp_modell[i][j]+" x"+(j+1));
                }
                else if (j<anzSpalten-1){
                    solver_input = solver_input.concat(" "+lp_modell[i][j]);
                }
                else {
                    solver_input = solver_input.concat(" "+lp_modell[i][j]+";");
                }
            }
        }

        // Ganzzahligkeit und Variableneinschraenkungen
        solver_input = solver_input.concat("\n");
        for(int x=0; x<anzSpalten-2; x++) {
            if(Integer.valueOf(restriktionen[x][0]).intValue()>0) {
                solver_input = solver_input.concat("x"+(x+1)+">="+restriktionen[x][0]+";\n");
            }
            solver_input = solver_input.concat("x"+(x+1)+"<="+restriktionen[x][1]+";\n");
        }
        solver_input = solver_input.concat("\nint ");
        for(int x=0; x<anzSpalten-2; x++) {
            if(restriktionen[x][2].equalsIgnoreCase("ja")) {
                solver_input = solver_input.concat("x"+(x+1));
                if(x == anzSpalten-3) {
                    solver_input = solver_input.concat(";");
                }
                else {
                    solver_input = solver_input.concat(",");
                }
            }
        }

        System.out.println("\nLP-Modell:\n"+solver_input+"\n");

        // schreiben der Datei
        try {

            File neueDatei = new File(this.getTempVerzeichnis()+"hotel.lp");
            FileWriter writeOut = new FileWriter(neueDatei);
            writeOut.write(solver_input);
            writeOut.flush();
            File neueBatDatei = new File(this.getTempVerzeichnis()+"hotel.bat");
            FileWriter writeBatOut = new FileWriter(neueBatDatei);
            writeBatOut.write(this.getLpSolvVerzeichnis()+"lp_solve -p <"+this.getTempVerzeichnis()+"hotel.lp >"+this.getTempVerzeichnis()+"hotel.out");
            writeBatOut.flush();

            writeOut.close();
            writeBatOut.close();


            // Ausfuehren des Solvers
            try {
                System.out.println("Jetzt manuell die bat Datei klicken... :/");
                Thread.sleep(10000);
             //   Process p = Runtime.getRuntime().exec(this.getTempVerzeichnis()+"hotel.bat");
             //   p.waitFor();
             //   neueDatei.delete();
            }
            catch(Exception e) {
              System.out.println("Fehler beim Ausfuehren des Solvers:\n"); e.printStackTrace();
            }
        } catch(java.io.IOException e) { System.out.println("Fehler beim Schreiben der Solverinput Datei"); }


        // lesen der Ergebnisdatei
        double[] ergebnis = new double[anzSpalten-1];
        try {
            BufferedReader in = new BufferedReader(new FileReader(this.getTempVerzeichnis()+"hotel.out"));

            // Zielfunktionswert
            ergebnis[anzSpalten-2]=Double.valueOf(in.readLine().substring(28).trim()).doubleValue();
            String tmp;
            for(int i=0;i<anzSpalten-2;i++) {
                tmp = in.readLine();
                ergebnis[Integer.valueOf(tmp.substring(1,4).trim()).intValue()-1]=Double.valueOf(tmp.substring(5).trim()).doubleValue();
            }

            System.out.println("\nErgebnis:");

            for(int i=0;i<anzSpalten-1;i++) {
                System.out.println("X"+(i+1)+": "+ergebnis[i]);
            }


        }
        catch(Exception e) {
          System.out.println("Fehler beim Einlesen oder Parsen der Ergebnisdatei:\n"); e.printStackTrace();
          JOptionPane.showMessageDialog(null, "Bitte eingestellte Pfade bzw. Verbindungen überprüfen.", "Fehler beim Solveraufruf", 0);
        }

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
