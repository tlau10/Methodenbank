
package Simulation.eduni.simdiag;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.applet.Applet;
import java.awt.event.*;
import java.lang.Math;


/**
 * Generates sin/cos graph from equation.
 * The output format is a stream of
 * GraphEventObjects which can be read and displayed by
 * a GraphDiagram.
 * This class may be useful as an example of how to
 * generate a graph from user simulations:
 * see the <a href="../../eduni/simdiag/GraphEqn.java">source code</a>
 */

public class GraphEqn implements Runnable {

  transient Thread thread;

  /** Creates a graph generator (Sine, Cos) */
  public GraphEqn() {
  }

  /** Called after output has been wired */
  public void startRunning() {
    thread = new Thread(this);
    thread.start();
  }

  /** Generates the graph */
  public void run() {
    double delta = 0.1;
    // while (true) {
    forwardGraph( new GraphClearObject(this) );

      forwardGraph( new GraphSetAxes(this,"X","Y") );

      for (double x = 0.0; x<20; x+=0.1) {
	forwardGraph( new GraphData(this,"sin",x,Math.sin(x+delta)) );
	forwardGraph( new GraphData(this,"cos",x,Math.cos(x+delta)) );

	forwardGraph( new GraphDisplay(this) );
	try { thread.sleep(100); } catch (Exception e) {}
      }

      forwardGraph( new GraphDisplay(this) );
      delta += 0.1;
      // try { thread.sleep(1000); } catch (Exception e) {}
      //  }
  }

  /** Javabeans graph event output: List of
   * event listeners.
   */
  private Vector graphListeners = new Vector();
  /** Adds a graph listener */
  public synchronized void addGraphListener(GraphListener l) {
    graphListeners.addElement(l);
  }
  /** Removes a graph listener */
  public synchronized void removeGraphListener(GraphListener l) {
    graphListeners.removeElement(l);
  }
  /** Forwards a graph event to anyone listening.
   */
  public void forwardGraph(GraphEventObject e) {
    Vector l;
    // GraphEventObject weo = new GraphEventObject(this,e);
    synchronized(this) { l = (Vector)graphListeners.clone(); }
    for (int i=0; i<l.size(); i++) {
      GraphListener wl = (GraphListener) l.elementAt(i);
      wl.handleGraph(e);
    }
  }
}
