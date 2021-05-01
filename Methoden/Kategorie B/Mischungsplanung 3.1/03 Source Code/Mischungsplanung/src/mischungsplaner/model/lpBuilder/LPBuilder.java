/*
 * lpBuilder.java
 *
 * Created on 22. Juni 2004, 09:48
 */

package mischungsplaner.model.lpBuilder;
import mischungsplaner.model.eingabe.*;
/**
 *
 * @author  hmaass
 */
public interface LPBuilder {
    public String[] createLPModell(Eingabe eingabe, boolean ganzzahlig);
}
