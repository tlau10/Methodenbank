/*
 * ErfuellungAnforderung.java
 *
 * Created on 15. Mai 2004, 17:40
 */

package mischungsplaner.model.eingabe;

/**
 *
 * @author  hmaass
 */
public class ErfuellungAnforderung {
    private int anforderungId;
    private double wert;
    public ErfuellungAnforderung() {
        
    }
    /** Creates a new instance of ErfuellungAnforderung */
    public ErfuellungAnforderung(int anforderungId, double wert) {
        this.anforderungId  = anforderungId;
        this.wert           = wert;
    }
    
    /**
     * Getter for property anforderungId.
     * @return Value of property anforderungId.
     */
    public int getAnforderungId() {
        return anforderungId;
    }
    
    /**
     * Getter for property wert.
     * @return Value of property wert.
     */
    public double getWert() {
        return wert;
    }
    
    /**
     * Setter for property anforderungId.
     * @param anforderungId New value of property anforderungId.
     */
    public void setAnforderungId(int anforderungId) {
        this.anforderungId = anforderungId;
    }    

    /**
     * Setter for property wert.
     * @param wert New value of property wert.
     */
    public void setWert(double wert) {
        this.wert = wert;
    }
    
}
