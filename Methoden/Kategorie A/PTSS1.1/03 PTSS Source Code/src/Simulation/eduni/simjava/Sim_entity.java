 /* Sim_entity.java
 */

package Simulation.eduni.simjava;

import Simulation.eduni.simanim.*;
import java.util.Vector;
import java.util.Enumeration;

/**
 * This class represents entities, or processes, running in the system.
 * To create a new type of entity, it should be <tt>extended</tt> and
 * a definition for the <tt>body()</tt> method given. The <tt>body()</tt>
 * method is called by the <tt>Sim_system</tt> and defines the behaviour of
 * the entity during the simulation. <p>
 * The methods with names starting with the prefix <tt>sim_</tt> are
 * runtime methods, and should only be called from within the entity's
 * <tt>body()</tt> method.
 * @see         Sim_system
 * @version     0.1, 25 June 1995
 * @author      Ross McNab
 */

public class Sim_entity extends Thread {
  // Private data members
  private String name;       // The entities name
  private int me;            // Unique id
  private Sim_event evbuf;   // For incoming events
  private int state;         // Our current state from list below
  private Semaphore restart; // Used by Sim_system to schedule us
  private Vector ports;      // Our outgoing ports
  private Anim_entity aent;  // Hacky Anim_entity pointer

  //
  // Public library interface
  //

  // Public constructor
  /** The standard constructor.
   * @param name The name to be associated with this entity
   * @return A new instance of the class Sim_entity
   */
  public Sim_entity(String name) {
    this.name = name;
    me = -1;
    state = RUNNABLE;
    restart = new Semaphore(0);
    ports = new Vector();
    // Now a hacky setDaemon so that if entities are the only active
    // threads in a simulation then the runtime will exit.
    // Saves having to explicitly kill still active entity threads
    // at the end of the simulation
    //this.setDaemon(true); FIXED
    aent = null;

    // Adding this to Sim_system automatically
    Sim_system.add(this);
  }
  // Anim constructor
  /** The constructor for use with the eduni.simanim animation package.
   * @param name The name to be associated with this entity
   * @param image_name The name of the gif image file for this entity's
   *                   icon, (without the .gif extension).
   * @param x The X co-ordinate at which the entity should be drawn
   * @param y The Y co-ordinate at which the entity should be drawn
   */
  public Sim_entity(String name, String image_name, int x, int y) {
    this.name = name;
    me = -1;
    state = RUNNABLE;
    restart = new Semaphore(0);
    ports = new Vector();
    // Adding this to Sim_system automatically
    Sim_system.add(this);
    // Now a hacky setDaemon so that if entities are the only active
    // threads in a simulation then the runtime will exit.
    // Saves having to explicitly kill still active entity threads
    // at the end of the simulation
    //this.setDaemon(true); FIXED
    // Now anim stuff
    aent = new Anim_entity(name, image_name);
    aent.set_position(x, y);
    ((Sim_anim)Sim_system.get_trcout()).add_entity(aent);
  }

  /** Make entity icon invisible
   */
  public void set_invisible(boolean b) {
    if (aent!=null) { aent.set_invisible(b); }
  }

  // Public access methods
  /** Get the name of this entity
   * @return The entity's name
   */
  public String get_name() { return name; }
  /** Get the unique id number assigned to this entity
   * @return The id number
   */
  public int get_id() { return me; }
  // Search for the port that the event came from
  /** Search through this entity's ports, for the one which sent this event.
   * @param ev The event
   * @return A reference to the port which sent the event, or null if
   *         it could not be found
   */
  public Sim_port get_port(Sim_event ev) {
    Sim_port found = null, curr;
    Enumeration e;
    for (e = ports.elements(); e.hasMoreElements();) {
      curr = (Sim_port)e.nextElement();
      if(ev.get_src() == curr.get_dest())
        found = curr;
    }
    return found;
  }
  // Search for a port by name
  /** Search through this entity's ports, for one called <tt>name</tt>.
   * @param name The name of the port to search for
   * @return A reference to the port, or null if it could not be found
   */
  public Sim_port get_port(String name) {
    Sim_port found = null, curr;
    Enumeration e;
    for (e = ports.elements(); e.hasMoreElements();) {
      curr = (Sim_port)e.nextElement();
      if(name.compareTo(curr.get_pname()) == 0)
        found = curr;
    }
    if(found == null)
      System.out.println("Sim_entity: could not find port "+name+
                         " on entity "+this.name);
    return found;
  }

  // Public update methods
  /** Add a port to this entity.
   * @param port A reference to the port to add
   */
  public void add_port(Sim_port port) {
    Anim_port aport;
    ports.addElement(port);
    port.set_src(this.me);
    if(((aport = port.get_aport()) != null) && (aent != null)) {
      aent.add_port(aport);
    }
  }
  /** Add a parameter to this entity.
   * Used with the eduni.simanim package for animation.
   * @param param A reference to the parameter to add
   */
  public void add_param(Anim_param param) {
    aent.add_param(param);
  }
  // The body function which should be overidden
  /** The method which defines the behavior of the entity. This method
   * should be overidden in subclasses of Sim_entity.
   */
  public void body() {
    System.out.println("Entity "+name+" has no body().");
  }

  // Runtime methods
  /** Causes the entity to hold for <tt>delay</tt> units of simulation time.
   * @param delay The amount of time to hold
   */
  public void sim_hold(double delay) {
    Sim_system.hold(me,delay);
    /* Pause me now */
    Sim_system.paused();
    restart.p();
  }
  /** An interruptable hold.
    * Causes the entity to hold for <tt>delay</tt> units of simulation time.
    * @param delay The amount of time to hold
    * @param ev Returns the event if hold interrupted
    * @return The amount of time left on the hold (0.0 if no interruptions)
    */
  public double sim_hold_for(double delay, Sim_event ev) {
    double start_t = Sim_system.sim_clock();
    sim_schedule(me, delay, 9999); // Send self 'hold done' msg
    // Sim_event ev2 = new Sim_event();
    sim_wait_for(Sim_system.SIM_ANY,ev);
    if (ev.get_tag() == 9999) {
      return 0.0;
    } else { // interrupted
      Sim_type_p p = new Sim_type_p(9999);
      double time_left = delay - (ev.event_time() - start_t);
      int success = sim_cancel(p,null);
      return time_left;
    }
  }

  /** Write a trace message.
   * @param level The level at which the trace should be printed, used
   *              with <tt>Sim_system.set_trc_level()</tt> to control
   *              what traces are printed
   * @param msg The message to be printed
   */
  public void sim_trace(int level, String msg) {
    if((level & Sim_system.get_trc_level()) != 0) {
      Sim_system.trace(me, msg);
    }
  }

  // The schedule functions
  /** Send an event to another entity, by id number with data.
   * @param dest The unique id number of the destination entity
   * @param delay How long from the current simulation time the event
   *              should be sent
   * @param tag An user-defined number representing the type of event.
   * @param data A reference to data to be sent with the event.
   */
  public void sim_schedule(int dest, double delay, int tag, Object data) {
    Sim_system.send(me, dest, delay, tag, data);
  }
  /** Send an event to another entity, by id number and with <b>no</b> data.
   * @param dest The unique id number of the destination entity
   * @param delay How long from the current simulation time the event
   *              should be sent
   * @param tag An user-defined number representing the type of event.
   */
  public void sim_schedule(int dest, double delay, int tag){
    Sim_system.send(me, dest, delay, tag, null);
  }
  /** Send an event to another entity, by a port reference with data.
   * @param dest A reference to the port to send the event out of
   * @param delay How long from the current simulation time the event
   *              should be sent
   * @param tag An user-defined number representing the type of event.
   * @param data A reference to data to be sent with the event.
   */
  public void sim_schedule(Sim_port dest, double delay, int tag, Object data) {
    Sim_system.send(me, dest.get_dest(), delay, tag, data);
  }
  /** Send an event to another entity, by a port reference with <b>no</b> data.
   * @param dest A reference to the port to send the event out of
   * @param delay How long from the current simulation time the event
   *              should be sent
   * @param tag An user-defined number representing the type of event.
   */
  public void sim_schedule(Sim_port dest, double delay, int tag) {
    Sim_system.send(me, dest.get_dest(), delay, tag, null);
  }
  /** Send an event to another entity, by a port name with data.
   * @param dest The name of the port to send the event out of
   * @param delay How long from the current simulation time the event
   *              should be sent
   * @param tag An user-defined number representing the type of event.
   * @param data A reference to data to be sent with the event.
   */
  public void sim_schedule(String dest, double delay, int tag, Object data) {
    Sim_system.send(me, get_port(dest).get_dest(), delay, tag, data);
  }
  /** Send an event to another entity, by a port name with <b>no</b> data.
   * @param dest The name of the port to send the event out of
   * @param delay How long from the current simulation time the event
   *              should be sent
   * @param tag An user-defined number representing the type of event.
   */
  public void sim_schedule(String dest, double delay, int tag) {
    Sim_system.send(me, get_port(dest).get_dest(), delay, tag, null);
  }


  /** Hold until the entity receives an event.
   * @param ev   The event received is copied into <tt>ev</tt> if
   *             it points to an blank event, or discarded if <tt>ev</tt> is
   *             <tt>null</tt>
   */
  public void sim_wait(Sim_event ev) {
    do{
        Sim_system.wait(me);
        Sim_system.paused();
        restart.p();
    } while (evbuf==null); //Hacky indeed. Null messages now ignored ADA MSI
    // I think this bit is fairly non-standard and hacky:
    // If they passed us a null event ref then just drop the new event
    // Otherwise copy the new event's values into the one they passed us
    if((ev != null) && (evbuf != null)) ev.copy(evbuf);
    evbuf = null;       // ADA MSI.
  }
  /** Count how many events matching a predicate are waiting
   * for this entity on the deferred queue.
   * @param p The event selection predicate
   * @return The count of matching events
   */
  public int sim_waiting(Sim_predicate p) { return Sim_system.waiting(me, p); }
  /** Count how many events are waiting of this entity on the deferred queue
   * @return The count of events
   */
  public int sim_waiting() {
    return Sim_system.waiting(me, Sim_system.SIM_ANY);
  }
  /** Extract the first event waiting for this entity on the deferred
   * queue, matched by the predicate <tt>p</tt>.
   * @param p An event selection predicate
   * @param ev   The event matched is copied into <tt>ev</tt> if
   *             it points to a blank event, or discarded if <tt>ev</tt> is
   *             <tt>null</tt>
   */
  public void sim_select(Sim_predicate p, Sim_event ev) {
    Sim_system.select(me, p);
    if((ev != null) && (evbuf != null)) ev.copy(evbuf);
    evbuf = null;       // ADA MSI
  }
  /** Cancel the first event waiting for this entity on the future
   * queue, matched by the predicate <tt>p</tt>. Returns the
   * number of events cancelled (0 or 1).
   * @param p An event selection predicate
   * @param ev   The event matched is copied into <tt>ev</tt> if
   *             it points to a blank event, or discarded if <tt>ev</tt> is
   *             <tt>null</tt>
   */
  public int sim_cancel(Sim_predicate p, Sim_event ev) {
    Sim_system.cancel(me, p);
    if((ev != null) && (evbuf != null)) ev.copy(evbuf);
    if (evbuf != null) { return 1;}  else { return 0; }
  }


  /** Repeatedly <tt>sim_wait()</tt> until the entity receives an event
   * matched by a predicate, all other received events
   * are discarded.
   * @param p The event selection predicate
   * @param ev   The event matched is copied into <tt>ev</tt> if
   *             it points to a blank event, or discarded if <tt>ev</tt> is
   *             <tt>null</tt>
   */
  public void sim_wait_for(Sim_predicate p, Sim_event ev) {
    boolean matched = false;
    while(!matched) {
        sim_wait(ev);
        if (p.match(ev)) matched = true;
        else sim_putback(ev);
    }
  }

  /** Put an event back on the deferred queue.
   * @param ev The event to reinsert
   */
  public void sim_putback(Sim_event ev) { Sim_system.putback((Sim_event)ev.clone()); }

  /** Get the first event matching a predicate from the deferred queue,
   * or, if none match, wait for a matching event to arrive.
   * @param p The predicate to match
   * @param ev   The event matched is copied into <tt>ev</tt> if
   *             it points to a blank event, or discarded if <tt>ev</tt> is
   *             <tt>null</tt>
   */
  public void sim_get_next(Sim_predicate p, Sim_event ev) {
    if (sim_waiting(p) > 0)
      sim_select(p, ev);
    else
      sim_wait_for(p,ev);
  }
  /** Get the first event from the deferred queue waiting on the entity,
   * or, if there are none, wait for an event to arrive.
   * @param ev   The event matched is copied into <tt>ev</tt> if
   *             it points to a blank event, or discarded if <tt>ev</tt> is
   *             <tt>null</tt>
   */
  public void sim_get_next(Sim_event ev) {
    sim_get_next(Sim_system.SIM_ANY, ev);
  }
  /** Get the id of the currently running entity
   * @return A unique entity id number
   */
  public int sim_current() {
    return this.get_id();
  }
  /** Send on an event to an other entity, through a port.
   * @param ev A reference to the event to send
   * @param p A reference to the port through which to send
   */
  public void send_on(Sim_event ev, Sim_port p) {
    sim_schedule(p.get_dest(), 0.0, ev.type(), ev.get_data());
  }

  //
  // Package level methods
  //

  // Package access methods
  int get_state() { return state; }
  Sim_event get_evbuf() { return evbuf; }

  // The states
  static final int RUNNABLE = 0;
  static final int WAITING  = 1;
  static final int HOLDING  = 2;
  static final int FINISHED = 3;

  // Package update methods
  void restart() { restart.v(); }
  void set_going() { restart.v(); } // FIXME same as restart, both needed?
  void set_state(int state) { this.state = state; }
  void set_id(int id) { me = id; }
  void set_evbuf(Sim_event e) { evbuf = e; }
  void poison() {
                    //restart.v(); ADA MSI
      // this.stop(); FWH deprecated
  }

  /** Internal method - don't overide */
  public final void run() {
    // Connect all our ports to their destination entities
    //Sim_port curr;
    //Enumeration e;
    //for (e = ports.elements(); e.hasMoreElements();) {
    //  ((Sim_port)e.nextElement()).connect(me);
    //}
    Sim_system.paused(); // Tell the system we're up and running
    restart.p(); // initially we pause 'till we get the go ahead from system
    body();
    state = FINISHED;
    Sim_system.paused();
  }


}
