/* Sim_any_p.java
 */

package Simulation.eduni.simjava;

/**
 * A predicate which will match any event on the deferred event queue.
 * There is a publicly accessible instance of this predicate in the
 * Sim_system class, called Sim_system.SIM_ANY, so the user does
 * not need to create any new instances.
 * @see         eduni.simjava.Sim_predicate
 * @see         eduni.simjava.Sim_system
 * @version     1.0, 4 September 1996
 * @author      Ross McNab
 */

public class Sim_any_p extends Sim_predicate {
  /** Constructor.
   * @return A new instance of the class.
   */
  public Sim_any_p()  {};
  /** The match function called by Sim_system.sim_select(),
   * not used directly by the user
   */
  public boolean match(Sim_event ev) { return true; }
}
