/**
 * The simjava package is a support library for discrete
 * event simulations.
 * @version     1.2, 4 August 1997
 * @author      Ross McNab, Fred Howell
 */

package Simulation.eduni.simjava;

import Simulation.eduni.simanim.*;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * This is the system class which manages the simulation. All of
 * the members of this class are static, so there is no need to
 * create an instance of this class.
 * @version     1.0, 4 September 1996
 * @version     1.2, 14 July 1997
 * @author      Ross McNab, Fred Howell
 */


public class Sim_system {
  // Private data members
  static private Vector entities;  // The current entity list
  static private Evqueue future;   // The future event queue
  static private Evqueue deferred; // The deferred event queue
  static private double clock;     // Holds the current global sim time
  static private boolean running;  // Has the run() member been called yet
  static private Semaphore onestopped;
  static private Sim_output trcout; // The output object for trace messages
  static private int trc_level;
  static private boolean auto_trace; // Should we print auto trace messages?
  static private boolean animation;  // Are we running as an animation applet?
  static private Thread simThread = null;
  static private NumberFormat nf;

  //
  // Public library interface
  //

  // Initialise system
  /** Initialise the system, this function does the job of a
   * constructor, and should be called at the start of any simulation
   * program. It comes in several flavours depending on what context
   * the simulation is running.<P>
   * This is the simplest, and should be used for standalone simulations
   * It sets up trace output to a file in the current directory called
   * `tracefile'
   */
  static public void initialise() {
    initialise(new Sim_outfile(), null);
  }
  /** This version of initialise() is used by the standard animation
   * package <tt>eduni.simanim</tt>
   */
  static public void initialise(Sim_anim out, Thread sim) {
    animation = true;
    initialise(((Sim_output)out), sim);
  }
  /** This version of initialise() is for experienced users who
   * want to use the trace output to draw a graph or an animation
   * as part of the application.
   * @param out The object to be used for trace output
   * @param sim The thread the simulation is running under, (set to
   *            <tt>null</tt> if not applicable
   */
  static public void initialise(Sim_output out, Thread sim) {
//    System.out.println("SimJava V1.2 - Ross McNab and Fred Howell");
    entities = new Vector();
    future = new Evqueue();
    deferred = new Evqueue();
    clock = 0.0;
    running = false;
    onestopped = new Semaphore(0);
    trcout = out;
    trcout.initialise();
    trc_level = 0xff;
    auto_trace = true;
    simThread = sim;

    // Set the default number format
    nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(4);
    nf.setMinimumFractionDigits(2);

  }

  /** Returns the number format used for generating
   * times in trace lines
   */
  static public NumberFormat getNumberFormat() { return nf; }

  // The two standard predicates
  /** A standard predicate that matches any event. */
  static public Sim_any_p SIM_ANY = new Sim_any_p();
  /** A standard predicate that does not match any events. */
  static public Sim_none_p SIM_NONE = new Sim_none_p();

  // Public access methods
  /** Get the current simulation time.
   * @return The simulation time
   */
  static public double clock() { return clock; }
  /** A different name for <tt>Sim_system.clock()</tt>.
   * @return The current simulation time
   */
  static public double sim_clock() { return clock; }
  /** Get the current number of entities in the simulation
   * @return A count of entities
   */
  static public int get_num_entities() { return entities.size(); }
  /** Get the current trace level (initially <tt>0xff</tt>), which
   * controls trace output.
   * @return The trace level flags
   */
  static public int get_trc_level() { return trc_level; }
  /** Find an entity by its id number.
   * @param id The entity's unique id number
   * @return A reference to the entity, or null if it could not be found
   */
  static public Sim_entity get_entity(int id) {
    return (Sim_entity)entities.elementAt(id);
  }
  /** Find an entity by its name.
   * @param name The entity's name
   * @return A reference to the entity, or null if it could not be found
   */
  static public Sim_entity get_entity(String name) {
    Enumeration e;
    Sim_entity  ent, found = null;

    for (e = entities.elements(); e.hasMoreElements();) {
      ent = (Sim_entity)e.nextElement();
      if(name.compareTo(ent.get_name()) == 0)
        found = ent;
    }
    if(found == null)
      System.out.println("Sim_system: could not find entity "+name);
    return found;
  }
  /** Find out an entities unique id number from its name.
   * @param name The entity's name
   * @return The entity's unique id number, or -1 if it could not be found
   */
  static public int get_entity_id(String name) {
    return entities.indexOf(get_entity(name));
  }

  /** Find the currently running entity.
   * @return A reference to the entity, or null if none are running
   */
  static public Sim_entity current_ent() {
    return (Sim_entity)Sim_entity.currentThread();
    //Sim_entity ent, found = null;
    //Enumeration e;
    //for (e = entities.elements(); e.hasMoreElements();) {
    //  ent = (Sim_entity)e.nextElement();
    //  if(ent.is_running())
    //    found = ent;
    //return found;
  }

  // Public update methods
  /** Set the trace level flags which control trace output.
   * @param level The new flags
   */
  static public void set_trc_level(int level) { trc_level = level; }
  /** Switch the auto trace messages on and off.
   * @param on If <tt>true</tt> then the messages are switched on
   */
  static public void set_auto_trace(boolean on) { auto_trace = on; }
  /** Add a new entity to the simulation.
   * This is now done automatically in the Sim_entity constructor,
   * so there is no need to call this explicitly.
   * @param e A reference to the new entity
   */
  static public void add(Sim_entity e) {
    Sim_event evt;
    if (running()) {
      /* Post an event to make this entity */
      evt = new Sim_event(Sim_event.CREATE,clock,current_ent().get_id(),0,0, e);
      future.add(evt);
    } else {
      if (e.get_id()==-1) { // Only add once!
	e.set_id(entities.size());
	entities.addElement(e);
      }
    }
  }
  /** Add a new entity to the simulation, when the simulation is running.
   * Note this is an internal method and should not be called from
   * user simulations. Use <tt>add()</tt> instead to add entities
   * on the fly.
   * @param e A reference to the new entity
   */
  static synchronized void add_entity_dynamically(Sim_entity e) {
    e.set_id(entities.size());
    if (e==null) { System.out.println("Adding null entity :("); }
    else { System.out.println("Adding: "+e.get_name()); }
    entities.addElement(e);
    e.start();
    onestopped.p();
  }
  /** Link the ports the ports on two entities, so that events secheduled
   * from one port are sent to the other.
   * @param ent1 The name of the first entity
   * @param port1 The name of the port on the first entity
   * @param ent2 The name of the second entity
   * @param port2 The name of the port on the second entity
   */
  static public void link_ports(String ent1, String port1, String ent2, String port2) {
    Sim_port p1,p2;
    Sim_entity e1, e2;
    e1 = get_entity(ent1);    e2 = get_entity(ent2);
    if (e1==null) {
      System.out.println("Sim_system: "+ent1+" not found"); }
    else if (e2==null) { System.out.println("Sim_system: "+ent2+" not found"); }
    else {
      p1 = e1.get_port(port1);  p2 = e2.get_port(port2);
      if (p1==null) { System.out.println("Sim_system: "+port1+" not found"); }
      else if (p2==null) { System.out.println("Sim_system: "+port1+" not found"); }
      else {
	p1.connect(e2);           p2.connect(e1);
	if(animation) {
	  ((Sim_anim)trcout).link_ports(ent1, port1, ent2, port2);
	}
      }
    }
  }

  /** Start the simulation running. This should be called after
   * all the entities have been setup and added, and their ports linked
   * Phase 1.
   */
  static public void run_start() {
    Enumeration e;
    Sim_entity  ent;
    int i;

    running = true;
    // Start all the entities' threads
    System.out.println("Sim_system: Starting entities");
    for (e = entities.elements(); e.hasMoreElements();) {
      ent = (Sim_entity)e.nextElement();
      ent.start();
    }
    System.out.println("Sim_system: Waiting for entities to startup");
    // Wait 'till they're all up and ready
    for(i=0; i<entities.size(); i++) {
      onestopped.p();
    }
  }

  /** Run one tick of the simulation.
   */
  static public boolean run_tick() {

 //   while(simThread != null) { // ist Thread gestoppt ?


    Enumeration e;
    Sim_entity  ent;
    int i, num_started;



    num_started = 0;
    for (e = entities.elements(); e.hasMoreElements();) {
      ent = (Sim_entity)e.nextElement();
      if (ent.get_state() == Sim_entity.RUNNABLE) {
	ent.restart();
	num_started++;
      }
    }

    // Wait for them all to halt
    //System.out.println("Sim_system: Waiting for "+num_started+" entities to halt");
    for(i=0; i<num_started; i++) {
      onestopped.p();
    }

/*    // Give everything else a chance
    if (simThread != null) {
      try { simThread.sleep(5); }
      catch(InterruptedException except) {}
    }
*/
    // If there are more future events then deal with them
    //System.out.println("Sim_system: Looking for future event");
    Thread myThread = Thread.currentThread();
    if ((future.size() > 0) ){
      Sim_event first = future.pop();
      process_event(first);

      // Check if next events are at same time...
      boolean trymore = (future.size()>0);
      while (trymore) {
	Sim_event next = future.top();
	if (next.event_time() == first.event_time()) {
	  process_event(future.pop());
	  trymore = (future.size()>0);
	} else trymore = false;
      }
    } else {
      running = false;
      System.out.println("Sim_system: No more future events");
    }
//   }

    return running;

  //  return false; // mje
  }


  /** Stop the simulation
   */
  static public void run_stop() {
    Enumeration e;
    Sim_entity  ent;
    // Attempt to kiiiillll all the entity threads
    for (e = entities.elements(); e.hasMoreElements();) {
      ent = (Sim_entity)e.nextElement();
      ent.poison();
    }
    System.out.println("Exiting Sim_system.run()");
    trcout.close();
  }

  /** Start the simulation running. This should be called after
   * all the entities have been setup and added, and their ports linked
   */
  static public void run() {
    // Now the main loop
    System.out.println("Sim_system: Entering main loop");
    run_start();
    while(run_tick()) { }
    run_stop();
  }

  //
  // Package level methods
  //

  static boolean running() { return running; }
  static public Sim_output get_trcout() { return trcout; }

  // Entity service methods

  // Called by an entity just before it become non-RUNNABLE
  static void paused() { onestopped.v(); }

  static synchronized void hold(int src, double delay) {
    Sim_event e = new Sim_event(Sim_event.HOLD_DONE,clock+delay,src);
    future.add(e);
    ((Sim_entity)entities.elementAt(src)).set_state(Sim_entity.HOLDING);
    if (auto_trace) {
      trace(src,"sim_hold starting");
    }
  }

  static synchronized void send(int src, int dest, double delay, int tag, Object data) {
    Sim_event e = new Sim_event(Sim_event.SEND, clock+delay, src, dest, tag, data);
    if (auto_trace) {
      trace(src, "scheduling event type "+tag+" for "+
                 ((Sim_entity)entities.elementAt(dest)).get_name()+
                 " with delay "+delay);
    }
    future.add(e);
  }
  static synchronized void wait(int src) {
    ((Sim_entity)entities.elementAt(src)).set_state(Sim_entity.WAITING);
    if (auto_trace) {
      trace(src,"waiting for an event");
    }
  }
  static synchronized void trace(int src, String msg) {
    trcout.println("u:"+((Sim_entity)entities.elementAt(src)).get_name()+
            " at "+clock+": "+msg);
  }
  static synchronized int waiting(int d, Sim_predicate p ) {
    int w = 0;
    Enumeration e;
    Sim_event ev;

    for (e = deferred.elements(); e.hasMoreElements();) {
      ev = (Sim_event)e.nextElement();
      if(ev.get_dest() == d) {
	//	trcout.println("t: in waiting, - event from "+ev.get_src()+" to "+
	//	       ev.get_dest() + " type:"+ev.type()+" time:"+ev.event_time());

        if(p.match(ev))
          w++;
      }
    }
    return w;
  }
  static synchronized void select(int src, Sim_predicate p) {
    Enumeration e;
    Sim_event ev=null;
    boolean found = false;

    // retrieve + remove event with dest == src
    for (e = deferred.elements(); (e.hasMoreElements()) && !found;) {
      ev = (Sim_event)e.nextElement();
      if(ev.get_dest() == src) {
        if(p.match(ev)) {
          deferred.removeElement(ev);
          found = true;
        }
      }
    }
    if(found) {
      ((Sim_entity)entities.elementAt(src)).set_evbuf((Sim_event)ev.clone());
      if (auto_trace) {
        trace(src,"sim_select returning (event time was "+ev.event_time()+")");
      }
    } else {
      ((Sim_entity)entities.elementAt(src)).set_evbuf(null);
      if (auto_trace) {
        trace(src,"sim_select returning (no event found)");
      }
    }
  }
  static synchronized void cancel(int src, Sim_predicate p) {
    Enumeration e;
    Sim_event ev=null;
    boolean found = false;

    // retrieve + remove event with dest == src
    for (e = future.elements(); (e.hasMoreElements()) && !found;) {
      ev = (Sim_event)e.nextElement();
      if(ev.get_src() == src) {
        if(p.match(ev)) {
          future.removeElement(ev);
          found = true;
        }
      }
    }
    if(found) {
      ((Sim_entity)entities.elementAt(src)).set_evbuf((Sim_event)ev.clone());
      if (auto_trace) {
        trace(src,"sim_cancel returning (event time was "+ev.event_time()+")");
      }
    } else {
      ((Sim_entity)entities.elementAt(src)).set_evbuf(null);
      if (auto_trace) {
        trace(src,"sim_cancel returning (no event found)");
      }
    }
  }
  static synchronized void putback(Sim_event ev) { deferred.add(ev); }

  //
  // Private internal methods
  //

  static private void process_event(Sim_event e) {
    int dest, src;
    Sim_entity dest_ent;

    //System.out.println("Sim_system: Processing event");
    // Update the system's clock
    if (e.event_time() < clock) {
      System.out.println("Sim_system: Error - past event detected!");
      System.out.println("Time: "+clock+", event time: "+e.event_time()+
                         ", event type: "+e.get_type());
    }
    clock = e.event_time();

    // Ok now process it
    switch(e.get_type()) {
      case(Sim_event.ENULL):
        System.out.println("Sim_system: Error - event has a null type");
        break;
      case(Sim_event.CREATE):
        Sim_entity newe = (Sim_entity)e.get_data();
        add_entity_dynamically(newe);
        break;
      case(Sim_event.SEND):
        // Check for matching wait
        dest = e.get_dest();
        if (dest<0) System.out.println("Sim_system: Error - attempt to send to a null entity");
        else {
          dest_ent = (Sim_entity)entities.elementAt(dest);
          if (dest_ent.get_state() == Sim_entity.WAITING) {
            dest_ent.set_evbuf((Sim_event)e.clone());
            dest_ent.set_state(Sim_entity.RUNNABLE);
          } else {
            deferred.add(e);
          }
        }
        break;
      case(Sim_event.HOLD_DONE):
        src = e.get_src();
        if (src<0)
          System.out.println("Sim_system: Error - NULL entity holding");
        else
          ((Sim_entity)entities.elementAt(src)).set_state(Sim_entity.RUNNABLE);
        break;
    }
  }


}
