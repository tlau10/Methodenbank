package Simulation.eduni.simanim;

import java.awt.*;
import java.applet.Applet;

/**
 * An animation parameter type. Stores an array of strings
 * representing the different states of a parameter, e.g.
 * "Idle", "Busy" etc. <p>
 * You need to specify a parameter type in order to get a parameter
 * to display on a timing diagram.<p>
 * Example code to add a parameter type from a user's
 * entity constructor is below.
 * <pre>
 *   String[] wstate = {"idle","busy"};
 *   add_param(new Anim_param( "State",
 *                          Anim_param.STATE,
 *                           new Param_type("wstate", wstate)
 *                           ));
 * <pre>
 * @version     1.0, 19 June 1997
 * @author      Fred Howell
 */

public class Param_type {
  private String typename;
  private String[] vals;
  public Param_type(String tp, String[] vals) {
    typename = tp;
    this.vals = vals;
  }
  /** Returns the array of strings */
  public String[] getVals() { return vals; }
  /** Returns the type name */
  public String getType() { return typename; }
  /** Returns the state index given a state name
    * (or -1 if the state name isn't found)
    */
  public  int getIndex(String s) {
    for(int i=0; i<vals.length; i++)
      if (vals[i].equals(s)) return i;
    return(-1);
  }
  /** Returns type spec in form <type> <val1> <val2> etc */
  public StringBuffer getSpec() {
    StringBuffer sb = new StringBuffer(typename);
    for (int i=0; i<vals.length; i++)
      sb.append(" "+vals[i]);
    return sb;
  }
}
