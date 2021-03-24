
package Simulation.eduni.simdiag;

import java.awt.*;
import java.util.EventObject;
import java.util.*;

/**
 * Graph event listener interface. <p>
 * Implemented by graph drawing routine.
 * @see eduni.simdiag.GraphDiagram
 */

public interface GraphListener extends EventListener {
  /** Responds to a single graph event */
  void handleGraph(GraphEventObject teo);
}
