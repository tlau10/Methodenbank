/*
 * eigenschaft.java
 *
 * Created on 15. Mai 2004, 12:00
 */

package model.eingabe;

/**
 * Eine Anforderung besteht aus der id, den Namen und den Wert.
 * Beispiel Anforderung:
 * Magnesiumgehalt (in mg): 5.0
 *      
 * @author  hmaass
 */
public class Anforderung {
    private int id;
    private String name;
    private double wert;
    
    
    /** Creates a new instance of eigenschaft */
    public Anforderung() {
    }
    
    /**
     * Getter for property anforderung.
     * @return Value of property anforderung.
     */
    public double getWert() {
        return wert;
    }
    
    /**
     * Setter for property anforderung.
     * @param anforderung New value of property anforderung.
     */
    public void setWert(double wert) {
        this.wert = wert;
    }
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName() {
        return name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(java.lang.String name) {
        this.name = name;
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
    
}
