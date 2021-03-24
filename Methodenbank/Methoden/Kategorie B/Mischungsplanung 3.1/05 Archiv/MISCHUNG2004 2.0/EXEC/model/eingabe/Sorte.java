/*
 * sorte.java
 *
 * Created on 15. Mai 2004, 12:00
 */

package model.eingabe;
import model.eingabe.*;
import java.util.*;
/**
 * Eine Sorte besteht aus einer id, einem Preis. Zusätzlich
 * enthält eine Sorte eine Sammlung von erfuellungAnforderung Objekten,
 * die bestimmen, welche Anforderungen in welchen Umfang erfüllt werden.
 * @author  hmaass
 */
public class Sorte {
    private int    id;
    private double preis;
    private Collection erfuellungAnforderungen;
    
    /** Creates a new instance of sorte */
    public Sorte() {
        erfuellungAnforderungen = new Vector();
    }
    public void addErfuellungAnforderung(ErfuellungAnforderung ea) {
        erfuellungAnforderungen.add(ea);
    }
    
    /**
     * Getter for property erfuellungAnforderungen.
     * @return Value of property erfuellungAnforderungen.
     */
    public Iterator getErfuellungAnforderungen() {
        return erfuellungAnforderungen.iterator();
    }    
 
    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public int getId() {
        return id;
    }    
    
    /**
     * Setter for property id.
     * @param id New value of property id.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Getter for property preis.
     * @return Value of property preis.
     */
    public double getPreis() {
        return preis;
    }
    
    /**
     * Setter for property preis.
     * @param preis New value of property preis.
     */
    public void setPreis(double preis) {
        this.preis = preis;
    }
    
}
