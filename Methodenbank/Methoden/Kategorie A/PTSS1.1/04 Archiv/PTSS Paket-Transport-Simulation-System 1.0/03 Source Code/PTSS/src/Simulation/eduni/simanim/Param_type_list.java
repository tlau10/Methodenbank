package Simulation.eduni.simanim;

import java.util.Vector;

/**
 * A list of all distinct parameter types
 * @version     1.0, 19 June 1997
 * @author      Fred Howell
 */

class Param_type_list {
  Vector ptypes;
  Param_type_list() {   ptypes = new Vector();  }
  public void reset() { ptypes.removeAllElements(); }
  public void add(Param_type p) {
    System.out.println("Adding "+p.getType());
    boolean is_distinct = true;
    for (int i=0; (i<ptypes.size()) && is_distinct; i++) {
      Param_type pt = (Param_type)ptypes.elementAt(i);
      if (pt.getType().equals(p.getType()))
	  is_distinct = false;
    }
    if (is_distinct) {
      ptypes.addElement(p);
      System.out.println("Really Adding "+p.getType());
    }
  }
  public Vector getV() { return ptypes; }
}
