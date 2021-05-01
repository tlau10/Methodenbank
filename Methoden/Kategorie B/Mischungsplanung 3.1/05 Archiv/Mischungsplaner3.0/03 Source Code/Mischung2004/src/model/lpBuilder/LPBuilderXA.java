/*
 * lpBuilderXA.java
 *
 * Created on 22. Juni 2004, 09:49
 */

package model.lpBuilder;
import java.util.*;
import model.eingabe.*;
import model.solverCaller.*;
/**
 *
 * @author  hmaass, choefel
 */
public class LPBuilderXA implements LPBuilder {
    
    public String[] createLPModell(model.eingabe.Eingabe eingabe, boolean ganzzahlig) {
                Vector xaModell = new Vector();
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
        zielFunktion = zielFunktion.substring(0, zielFunktion.lastIndexOf('+') - 1);
        
        String[] returnModell = new String[nAnzahlAnforderungen + 1];
        returnModell[0] = zielFunktion;
        
        Iterator itAnforderungen = eingabe.getAnforderungen();
        while (itAnforderungen.hasNext()) {
            Anforderung af = (Anforderung)itAnforderungen.next();
            String restriktion = af.getId() + ": " + restriktionen[af.getId() - 1];
            
            returnModell[af.getId()] = restriktion.substring(0, restriktion.lastIndexOf('+') - 1)
            + " >= " + af.getWert();
        }

        
        xaModell.addElement(zielFunktion);
        /*// Zielfunktion
        lpAnsatz[0] = "   + 1 x1 + 2 x2"
         // Restriktionen
        lpAnsatz[1] = " R1: 1.5 x1 + 2.1 x2 >= 5";
        lpAnsatz[2] = " R2: 2.1 x1 + 2.1 x2 >= 2";
         */
        return returnModell;
    }
    
}
