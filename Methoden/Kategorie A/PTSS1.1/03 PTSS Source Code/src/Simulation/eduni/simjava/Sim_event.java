/* Sim_event.java
 */

package Simulation.eduni.simjava;

/**
 * This class represents events which are passed between the entities
 * in the simulation.
 * @see         Sim_system
 * @version     1.0, 4 September 1996
 * @author      Ross McNab
 */

public class Sim_event implements Cloneable {
  // Private data members
  private int etype;      // internal event type
  private double time;    // sim time event should occur
  private int ent_src;    // id of entity who scheduled event
  private int ent_dst;    // id of entity event will be sent to
  private int tag;        // the user defined type of the event
  private Object data;    // any data the event is carrying

  //
  // Public library interface
  //

  // Internal event types
  static final int ENULL = 0;
  static final int SEND = 1;
  static final int HOLD_DONE = 2;
  static final int CREATE = 3;

  // Constructors
  /** Contructor, create a blank event. Usefull for fetching events
   * using methods such as <tt>Sim_entity.sim_wait(ev)</tt>.
   * @return A blank instance of the class Sim_event
   */
  public Sim_event() {
    etype = ENULL;
    this.time = -1.0;
    ent_src = -1;
    ent_dst = -1;
    this.tag = -1;
    data = null;
  }

  // Package level constructors
  Sim_event(int evtype, double time, int src,
                   int dest, int tag, Object edata) {
    etype = evtype;
    this.time = time;
    ent_src = src;
    ent_dst = dest;
    this.tag = tag;
    data = edata;
  }
  Sim_event(int evtype, double time, int src) {
    etype = evtype;
    this.time = time;
    ent_src = src;
    ent_dst = -1;
    this.tag = -1;
    data = null;
  }

  // Public access methods
  /** Get the unique id number of the entity which recieved this event.
   * @return the id number
   */
  public int get_dest() { return ent_dst; }
  /** Get the unique id number of the entity which scheduled this event.
   * @return the id number
   */
  public int get_src() { return ent_src; }
  /** Get the simulation time that this event was scheduled.
   * @return The simulation time
   */
  public double event_time() { return time; }
  /** Get the user-defined tag in this event
   * @return The tag
   */
  public int type() { return tag; }             // The user defined type
  /** Get the unique id number of the entity which scheduled this event.
   * @return the id number
   */
  public int scheduled_by() { return ent_src; }
  /** Get the user-defined tag in this event.
   * @return The tag
   */
  public int get_tag() { return tag; }
  /** Get the data passed in this event.
   * @return A reference to the data
   */
  public Object get_data() { return data; }
  /** Determine if the event was sent from a given port.
   * @param p The port to test
   * @return <tt>true</tt> if the event was scheduled through the port
   */
  public boolean from_port(Sim_port p) { return (get_src()==p.get_dest()); }
  // Public modifying methods
  /** Create an exact copy of this event.
   * @return A reference to the copy
   */
  public Object clone() {
    return new Sim_event(etype, time, ent_src, ent_dst, tag, data);
  }
  /** Set the source entity of this event.
   * @param s The unique id number of the entity
   */
  public void set_src(int s) { ent_src = s; }
  /** Set the destination entity of this event.
   * @param d The unique id number of the entity
   */
  public void set_dest(int d) { ent_dst = d; }

  //
  // Package level methods
  //

  int get_type() { return etype; }       // The internal type
  void copy(Sim_event ev) {
    ent_dst = ev.get_dest();
    ent_src = ev.get_src();
    time = ev.event_time();
    etype = ev.get_type();
    tag = ev.get_tag();
    data = ev.get_data();
  }
}
