/**
 * GraphLoader.java
 * Converts graph from URL to GraphEventListener format
 */

package Simulation.eduni.simdiag;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.applet.Applet;
import java.awt.event.*;

public class GraphLoader implements Runnable {

  transient Thread thread;
  String urlName;


  /** Create a graph loader with the given url */
  public GraphLoader(String urlName) {
    this.urlName = urlName;
    thread = new Thread(this);
  }

  /** Called after output has been wired */
  public void startLoading() {
    thread.start();
  }

  protected String nextLine(BufferedReader r) {
    try {
      String l = r.readLine();
      return l;
    } catch (Exception e) {
      return null;
    }
  }

  public void run() {
    URL u;
    try {
      u = new URL(urlName);
    } catch (Exception e) {
      System.out.println("Can't open URL "+urlName);
      return;
    }

    DataInputStream file;
    try {
      file = new DataInputStream(u.openStream());
    } catch (IOException ioe) {
      System.out.println("Couldn't open URL\n");
      return;
    }
    BufferedReader in = new BufferedReader(new InputStreamReader(file));
    String l;

    int numevents = 0;

    forwardGraph( new GraphClearObject(this) );
    // File fmt:
    // axis names, data.
    // mem speed\n time\n sys1 10 123.4 etc
    String xax = nextLine(in);
    String yax = nextLine(in);
    forwardGraph( new GraphSetAxes(this,xax,yax) );
    while ((l = nextLine(in))!=null) {
      forwardGraph( new GraphData(this,l) );
      numevents += 1;
    }

    forwardGraph( new GraphDisplay(this) );

    System.out.println("Read in "+numevents+" lines");
  }

  /* Javabeans graph event output */
  private Vector graphListeners = new Vector();
  public synchronized void addGraphListener(GraphListener l) {
    graphListeners.addElement(l);
  }
  public synchronized void removeGraphListener(GraphListener l) {
    graphListeners.removeElement(l);
  }
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
