/* Sim_from_p.java
 */

package Simulation.eduni.simjava;

/**
 * A predicate which selects events from a specific entity, out of the
 * deferred event queue.
 * @see         eduni.simjava.Sim_predicate
 * @version     1.0, 4 September
 * @author      Ross McNab
 */

public class Sim_from_p extends Sim_predicate {
  private int src_e;
  /** Constructor.
   * @param source_ent The id number of the source entity to look for
   * @returns A new predicate which selects events sent from the entity
   *          <tt>source_ent</tt>
   */
  public Sim_from_p(int source_ent) { src_e = source_ent; }
  /** The match function called by Sim_system.sim_select(),
   * not used directly by the user.
   */
  public boolean match(Sim_event ev) { return (ev.get_src() == src_e); }
}
