/* Evqueue.java
 */

package Simulation.eduni.simjava;

import java.util.Vector;
import java.util.Enumeration;

/**
 * This class implements an event queue used internally by the Sim_system to
 * manage
 * the list of future and deferred Sim_events. It should not be needed in
 * a user simulation. It works like a normal FIFO
 * queue, but during insertion events are kept in order from the smallest time
 * stamp to the largest. This means the next event to occur will be at the top
 * of the queue. <P>
 * The current implementation
 * is uses a Vector to store the queue and is inefficient for popping
 * and inserting elements because the rest of the array has to be
 * moved down one space. A better method would be to use a circular array.
 * @see		eduni.simjava.Sim_system
 * @version     0.1, 25 June 1995
 * @author      Ross McNab
 */

public class Evqueue extends Vector {
  // Constructors
  /**
   * Allocates a new Evqueue object.
   */
  public Evqueue() {
    super();
  }

  /**
   * Allocates a new Evqueue object, with an initial capacity.
   * @param initialCapacity	The initial capacity of the queue.
   */
  public Evqueue(int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Remove and return the event at the top of the queue.
   * @return           The next event.
   */
  public Sim_event pop() {
    Sim_event event = (Sim_event)firstElement();
    removeElementAt(0);
    return event;
  }

  /**
   * Return the event at the top of the queue, without removing it.
   * @return	The next event.
   */
  public Sim_event top() {
    return (Sim_event)firstElement();
  }

  /**
   * Add a new event to the queue, preserving the temporal order of the
   * events in the queue.
   * @param new_event	The event to be put on the queue.
   */
  public void add(Sim_event new_event) {
    int i;
    Enumeration e;
    Sim_event event;

    i = -1;
    for(e = elements(); e.hasMoreElements() && (i == -1);) {
      event = (Sim_event)e.nextElement();
      if(event.event_time() > new_event.event_time())
        i = indexOf(event);
    }

    if(i == -1)
      addElement((Object)new_event);
    else
      insertElementAt((Object)new_event, i);
  }
}
