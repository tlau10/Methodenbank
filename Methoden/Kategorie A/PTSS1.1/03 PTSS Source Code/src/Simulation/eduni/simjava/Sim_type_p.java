/* Sim_type_p.java
 */

package Simulation.eduni.simjava;

/**
 * A predicate to select events with specific tags from the deferred
 * event queue.
 * @see         eduni.simjava.Sim_predicate
 * @version     1.0, 4 September 1996
 * @author      Ross McNab
 */

public class Sim_type_p extends Sim_predicate {
  private int tag1,tag2,tag3;
  private int ntags;

  /** Constructor.
   * @param t1   An event tag value
   * @return A predicate which selects events with the tag value <tt>t1</tt>
   */
  public Sim_type_p(int t1) { tag1 = t1; ntags = 1; }
  /** Constructor.
   * @param t1   An event tag value
   * @param t2   An event tag value
   * @return A predicate which selects events with the tag values <tt>t1</tt>
   *         or <tt>t2</tt>
   */
  public Sim_type_p(int t1, int t2) { tag1 = t1; tag2 = t2; ntags = 2; }
  /** Constructor.
   * @param t1   An event tag value
   * @param t2   An event tag value
   * @param t3   An event tag value
   * @return A predicate which selects events with the tag value <tt>t1</tt>,
   *         <tt>t2</tt>, or <tt>t3</tt>
   */
  public Sim_type_p(int t1, int t2, int t3) {
    tag1 = t1; tag2 = t2; tag3 = t3;
    ntags = 3;
  }
  /** The match function called by Sim_system.sim_select(),
   * not used directly by the user
   */
  public boolean match(Sim_event ev) {
    switch (ntags) {
    case 1: return (ev.get_tag()==tag1);
    case 2: return (ev.get_tag()==tag1) || (ev.get_tag()==tag2);
    case 3: return (ev.get_tag()==tag1) || (ev.get_tag()==tag2) ||
                   (ev.get_tag()==tag3);
    }
    return false;
  }
}
