/*
 * lpBuilder.java
 *
 * Created on 22. Juni 2004, 09:48
 */

package model.lpBuilder;
import model.eingabe.*;
/**
 *
 * @author  hmaass
 */
public interface LPBuilder {
    public String[] createLPModell(Eingabe eingabe, boolean ganzzahlig);
}
