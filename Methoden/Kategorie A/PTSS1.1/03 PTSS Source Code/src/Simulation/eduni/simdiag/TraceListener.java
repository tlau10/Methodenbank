
package Simulation.eduni.simdiag;

import java.awt.*;
import java.util.EventObject;
import java.util.*;

/**
 * Timing diagram event listener interface
 */


public interface TraceListener extends EventListener {
  /** Processes the given trace event object.
   */
  void handleTrace(TraceEventObject teo);
}
