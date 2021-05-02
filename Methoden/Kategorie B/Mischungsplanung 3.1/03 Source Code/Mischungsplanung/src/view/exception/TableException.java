/*
 * EingabeException.java
 *
 * Created on 16. Juni 2004, 12:14
 */

package view.exception;

/**
 * Diese Exception wird geworfen, wenn die Eingaben in der Anforderung und Sorten Tabelle
 * fehlerhaft sind.
 * @author  hmaass
 */
public class TableException extends Exception{
    
    /** Creates a new instance of EingabeException */
    public TableException() {
    }
    public TableException(String msg) {
        super(msg);
    }
    
}
