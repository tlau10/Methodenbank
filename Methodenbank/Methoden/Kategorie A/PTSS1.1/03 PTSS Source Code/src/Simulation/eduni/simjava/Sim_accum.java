/* Sim_accum.java
 */

package Simulation.eduni.simjava;

import java.util.Vector;
import java.util.Enumeration;

/**
 * A class for collecting statistical data during simulations.
 * @version     1.0, 4 September 1996
 * @version     1.1, 29 May 1997 fwh corrected update() bug.
 * @author      Ross McNab
 */

public class Sim_accum {
  private Vector intervals;
  private Vector values;

  /**
   * Constructor.
   * @return    A new instance of the class
   */
  public Sim_accum() {
    intervals = new Vector();
    values = new Vector();
  }

  /**
   * Constructor, with name for SIM++ compatability.
   * @param name The name to be associated with the instance,
   *             (currently ignored).
   * @return    A new instance of the class
   */
  public Sim_accum(String name) {     // For SIM++ compatibility
    new Sim_accum();
  }

  /**
   * Add a new record to the statistics collected so far.
   * @param interval How long the value held
   * @param value The value to record
   */
  public void update(double interval, double value) {
    intervals.addElement(new Double(interval));
    values.addElement(new Double(value));
  }

  /**
   * Find the minimum value recorded so far
   * @return The minimum value recorded so far, or 0.0
   *         if no values have been recorded
   */
  public double min(){
    double ret, val;
    Enumeration e;

    if (intervals.size() == 0)
      ret = 0.0;
    else {
      ret = ((Double)values.elementAt(0)).doubleValue();
      for (e = values.elements(); e.hasMoreElements();) {
        val = ((Double)e.nextElement()).doubleValue();
        if (val<ret) ret = val;
      }
    }
    return ret;
  }


  /**
   * Find the maximum value recorded so far
   * @return The maximum value recorded so far, or 0.0
   *         if no values have been recorded
   */
  public double max() {
    double ret, val;
    Enumeration e;

    if (intervals.size() == 0)
      ret = 0.0;
    else {
      ret = ((Double)values.elementAt(0)).doubleValue();
      for (e = values.elements(); e.hasMoreElements();) {
        val = ((Double)e.nextElement()).doubleValue();
        if (val>ret) ret = val;
      }
    }
    return ret;
  }

  /**
   * Calculates the average value held over the total
   * interval recorded.
   * i.e. <tt>Sum_for_all_i(value[i]*interval[i])/total_interval</tt>
   * @return The average value
   */
  public double avg() {
    double ret, val, inter;
    Enumeration i, v;

    if (intervals.size() == 0)
      ret = 0.0;
    else {
      ret = 0.0;
      i = intervals.elements();
      v = values.elements();
      while(i.hasMoreElements()) {
        val = ((Double)v.nextElement()).doubleValue();
        inter = ((Double)i.nextElement()).doubleValue();
        ret += val * inter;
      }
      ret = ret / interval_sum();
    }
    return ret;
  }

  /**
   * Sums all the intervals recorded so far.
   * @return The sum of the intervals
   */
  public double interval_sum() {
    double ret, val;
    Enumeration e;

    if (intervals.size() == 0)
      ret = 0.0;
    else {
      ret = 0.0;
      for (e = intervals.elements(); e.hasMoreElements();) {
        val = ((Double)e.nextElement()).doubleValue();
        ret += val;
      }
    }
    return ret;
  }
}

