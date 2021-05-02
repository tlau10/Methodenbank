/*
 * lpBuilderLPSolve.java
 *
 * Created on 22. Juni 2004, 09:51
 */

package mischungsplaner.model.lpBuilder;
import java.util.*;
import mischungsplaner.model.eingabe.*;
import mischungsplaner.model.solverCaller.*;
/**
 *
 * @author  hmaass
 */
public class LPBuilderLPSolve implements LPBuilder{

    
    public String[] createLPModell(Eingabe eingabe, boolean ganzzahlig) {
              Vector lpModell = new Vector();
        String zielFunktion = "";
        int nPos = 0;
        int nAnzahlAnforderungen = eingabe.getAnforderungenSize();
        int nAnzahlSorten        = eingabe.getSortenSize();
        String[] restriktionen = new String[nAnzahlAnforderungen];
        for (int i = 0; i < restriktionen.length; i++) {
            restriktionen[i] = "";
        }
        Iterator itSorten = eingabe.getSorten();
        while (itSorten.hasNext()) {
            nPos++;
            Sorte sort = (Sorte)itSorten.next();
            
            Iterator itErfuellungAnforderung = sort.getErfuellungAnforderungen();
            while (itErfuellungAnforderung.hasNext()) {
                ErfuellungAnforderung ea = (ErfuellungAnforderung)itErfuellungAnforderung.next();
                // getAnforderungId - 1, da die AnforderungId mit 1 beginnt!!!
                restriktionen[ea.getAnforderungId() - 1] += ea.getWert() + " x" + nPos + " + ";
            }
            zielFunktion += sort.getPreis() + " x" + nPos + " + ";
        }
        
        // Minimierung der Kosten
        zielFunktion = "min: " + zielFunktion.substring(0, zielFunktion.lastIndexOf('+') - 1) + ";";
        
        String[] returnModell = null;
        if (ganzzahlig) {
            returnModell = new String[nAnzahlAnforderungen + 1 + nAnzahlSorten];
        } else {
            returnModell = new String[nAnzahlAnforderungen + 1];
        }
        returnModell[0] = zielFunktion;
        
        Iterator itAnforderungen = eingabe.getAnforderungen();
        while (itAnforderungen.hasNext()) {
            Anforderung af = (Anforderung)itAnforderungen.next();
            String restriktion = "R" + af.getId() + ": " + restriktionen[af.getId() - 1];
            
            returnModell[af.getId()] = restriktion.substring(0, restriktion.lastIndexOf('+') - 1)
            + " >= " + af.getWert() + ";";
        }
        if (ganzzahlig) {
            for (int i=0;i<nAnzahlSorten;i++) {
                returnModell[nAnzahlAnforderungen + i +1] = "int x" + (i+1) + ";";
            }
        }
        
        lpModell.addElement(zielFunktion);
        /*// Zielfunktion
        lpAnsatz[0] = "max: 1 x1 + 2 x2;";
        // Restriktionen
        lpAnsatz[1] = "R1: 3 x1 + 2 x2 <= 12;";
        lpAnsatz[2] = "R2: 1 x1 + 3x2 <= 9;";
         */
        return returnModell;
    }
    
}
