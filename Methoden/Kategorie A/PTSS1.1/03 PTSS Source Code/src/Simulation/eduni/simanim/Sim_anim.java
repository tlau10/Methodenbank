package Simulation.eduni.simanim;

import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.text.NumberFormat;
import java.net.*;
import java.io.*;
import java.applet.*;
import java.awt.*;
import Simulation.eduni.simjava.*;
import Simulation.eduni.simdiag.*;

/**
 * The trace output class for animations. Do not use this class
 * directly, instead use Anim_applet.
 * @version     1.0, 4 September
 * @version     1.1, 24 March 1997 - updates for JDK 1.1
 * @author      Ross McNab
 */


public class Sim_anim extends Panel implements Sim_output, Traceable {
  private int width, height;    // Our width and height
  private int traceCount;       // How many traces we've seen
  private Vector events;
  private Vector entities;
  private Param_type_list ptypes;
  private String msgString;
  private Image staticImage;       // Off screen buffer for drawing graphics
  private Graphics staticGraphics; // and it's graphics region
  static private NumberFormat nf;
  private long lastsimtime;	// Time for last sim step (ms)
  private Vector sends = new Vector(); // Stores list of send events for messages

  /* Javabeans trace event output */
  private Vector traceListeners = new Vector();
  private TraceEventObject lastTraceEvent;
  public synchronized void addTraceListener(TraceListener l) {
    traceListeners.addElement(l);
  }
  public synchronized void removeTraceListener(TraceListener l) {
    traceListeners.removeElement(l);
  }
  public void forwardTrace(TraceEventObject e) {
    Vector l;
    TraceEventObject weo = new TraceEventObject(this,e);
    synchronized(this) { l = (Vector)traceListeners.clone(); }
    for (int i=0; i<l.size(); i++) {
      TraceListener wl = (TraceListener) l.elementAt(i);
      wl.handleTrace(weo);
    }
  }


  // One time initialisation
  /** Do not use this method directly */
  public Sim_anim(Anim_applet applet)  {
    super();
    entities = new Vector();
    ptypes = new Param_type_list();
    msgString = new String();
    staticImage = null;  staticGraphics = null;
    this.applet = applet;
    System.gc(); // Now is a good time to tidy up
    // Set the default number format
    nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(4);
    nf.setMinimumFractionDigits(2);
    lastsimtime = 0;
  }

  /** Do not use this method directly */
  public void link_ports(String e1, String p1, String e2, String p2) {
    Anim_port port1, port2;
    Anim_entity ent1 = find_entity(e1);
    Anim_entity ent2 = find_entity(e2);
    if (ent1==null) {
	//      System.out.println("Couldn't find anim ent: "+e1);
    } else if (ent2==null) {
	//      System.out.println("Couldn't find anim ent: "+e2);
    } else {
      port1 = ent1.find_port(p1);
      port2 = ent2.find_port(p2);
      if (port1==null) {
	System.out.println("Couldn't find anim port: "+p1);
      } else if (port2==null) {
	System.out.println("Couldn't find anim port: "+p2);
      } else {
	port1.link_port(port2, true);  port2.link_port(port1, false);
      }
    }
  }

  /** Do not use this method directly */
  public void add_entity(Anim_entity ent) { entities.addElement(ent); }

  //
  // Some global image management stuff
  //
  private static Vector images = new Vector();    // Image files we have loaded
  private static Vector img_names = new Vector(); // And the names of the files
  static Anim_applet applet;
  static Image get_image(String name) {
    int index;
    MediaTracker track;
    Image image = null;

    index = img_names.indexOf(name);
    if(index == -1) {
	System.out.println("Loading image :"+name+".gif");
  //    URL cb = applet.getCodeBase();
  //    if (cb == null) {
//	  try { cb=new URL("file:./"); } catch (Exception e ) { System.out.println(e); }
   //   }
      //      System.out.println("Code base "+cb+" Applet "+applet);
  //    image = applet.getImage(cb, "bitmaps/"+name+".gif");
      track = new MediaTracker(applet);
      track.addImage(image,0);
      try { track.waitForID(0); }
      catch(InterruptedException e) { System.out.println("Image loaded"); }
      if(image == null)
        System.out.println("Failed to load image :"+name+".gif");
      img_names.addElement(name);
      images.addElement(image);
      return(image);
    } else {
      return((Image)images.elementAt(index));
    }
  }


  // Initialisation for each anim run
  /** Do not use this method directly */
  public void initialise() {
    traceCount = 0;
    events = new Vector();
    entities = new Vector();
    ptypes.reset();
    setup_static();
    update_msgString("Initialising");
  }

  void setup_static() {
    Dimension s = this.getSize();
    width = s.width;
    height = s.height;
 //   System.out.println("Panel size is "+width+" "+height);
    if (width < 300) width = 300;
    if (height < 300) height = 300;
//    System.out.println("Panel size is now"+width+" "+height);
    staticImage = createImage(width, height);
    staticGraphics = staticImage.getGraphics();
    staticGraphics.setFont(new Font("TimesRoman", Font.PLAIN, 12));
  }

  /** Register param type
   *  Called from Anim_entity whenever add_param() called.
   */
  void add_param_type(Param_type pt) {  ptypes.add(pt); }

  /** Output trace header */
  public void genTraceHeader() {
    TraceEventObject t = new TraceEventObject(this,LAYOUT);
    forwardTrace( t );
    t.set("$types");   forwardTrace( t );
    // Get from ptypes...
    Vector pv = ptypes.getV();
    for (int i=0; i<pv.size(); i++) {
      Param_type pt = (Param_type)pv.elementAt(i);
      String s = new String(pt.getSpec());
      t.set(s); forwardTrace( t );
    }
    t.set("$bars");    forwardTrace( t );
    // From entity list...
    for (int i=0; i<entities.size(); i++) {
      Anim_entity ae = (Anim_entity)entities.elementAt(i);
      String s = ae.get_bar_string();
      if (s != null){
	t.set(ae.get_bar_string()); forwardTrace( t );
      }
    }
    t.set("$events");  forwardTrace( t );
  }
  public void genTraceTail() {
    forwardTrace( new TraceEventObject(this,DISPLAY) );
  }
  public void dispTrace() {
    forwardTrace( new TraceEventObject(this,DISPLAY) );
  }



  // Accept a trace line from the simulation, and store it
  /** Do not use this method directly */
  public void println(String msg) {
    int old_capacity;

    // System.out.println("Doing line "+msg);
    long delay = System.currentTimeMillis();
    traceCount++;
    //if((traceCount % 10) == 0) {
    //  update_msgString("Starting: "+traceCount+" traces read");
    //  repaint();
    //}
    old_capacity = events.capacity();
    events.addElement(new Anim_event(msg, this));
    // Be careful with our memory
    if(old_capacity < events.capacity()) System.gc();

    delay = System.currentTimeMillis() - delay;
    //System.out.println("... done ("+delay+")");

    /* Forward the trace line to any listeners */
    forwardTrace( new TraceEventObject(this,msg) );
  }


  // Called by the simulation when it is done
  /** Do not use this method directly */
  public void close() {
  }

  // Search for an entity called ent_name
  Anim_entity find_entity(String ent_name) {
    Anim_entity ent;
    for (Enumeration e = entities.elements(); e.hasMoreElements() ;) {
       ent = (Anim_entity)e.nextElement();
       if(ent.get_name().equals(ent_name)) return ent;
    }
    //  System.out.println("Error: find_entity() could not find: "+ent_name);
    return null;
  }

  /** Do not use this method directly */
  public void paint(Graphics g) {
    // Check if we've been resized or just brand new
    if((staticImage == null) || (width != this.getSize().width)
                          || (height != this.getSize().height)) {
      setup_static();
      draw_all_static();
    }

    // just slap the static buffer down
    g.drawImage(staticImage,0,0,this);
    draw_messages(g);
    // redraw the messages

		//    for (Enumeration e = entities.elements(); e.hasMoreElements() ;) {
		//       ((Anim_entity)e.nextElement()).draw_messages(g);
		//    }
  }

  void draw_messages(Graphics g) {
    Enumeration e;
    for (e = sends.elements(); e.hasMoreElements();) {
      //System.out.println("drawing a message!\n");
      Anim_port port = (Anim_port)e.nextElement();
      port.draw_messages(g);
    }
  }

  /** Do not use this method directly */
  public void update(Graphics g) {
    paint(g);
  }


  // To force the painting of an update
  // Call repaint() then give painting thread a chance to do it
  void show_update(Thread simThread) {
    repaint();
    if(simThread != null) {
      do {
        try { simThread.sleep(applet.get_speed()); }
        catch(InterruptedException except) {}
      } while(applet.get_paused());
    }
  }

  // Draw all of the initial static ent stuff
  /** Do not use this method directly */
  public void draw_all_static() {
    staticGraphics.setColor(Color.lightGray);
    staticGraphics.fillRect(0,0,width,height);
    staticGraphics.setColor(Color.black);
    staticGraphics.drawString(msgString, 5, height-5);
    for (Enumeration e = entities.elements(); e.hasMoreElements() ;) {
       ((Anim_entity)e.nextElement()).draw(staticGraphics);
    }
  }

  // redraw the msgString
  void update_msgString(String new_msg) {
    staticGraphics.setColor(Color.lightGray);
    //staticGraphics.fillRect(0,height-16, width, 16);
    staticGraphics.drawString(msgString, 5, height-5);
    staticGraphics.setColor(Color.black);
    staticGraphics.drawString(new_msg, 5, height-5);
    msgString = new_msg;
  }


  // The main animation loop
  // Called by the applet after the simulation has finished
  // fwh - Updated to run the simulation too.
  /** Do not use this method directly */
  public void animate(Thread simThread) {
    int anim_steps = 10, i, ev_index;
    double timestamp = 0.0;
    Anim_event ev;
    Anim_port port;
    Enumeration e;
    draw_all_static();

    /* Generate trace info */
    genTraceHeader();

    /* Set simulation going */
    Sim_system.run_start();
    ev_index = 0;
    int upidx = 0; // Extra update for time if no animated events
    boolean running = true;

    do {
      // Run One Simulation Step
      long simdelay = System.currentTimeMillis();
      running = Sim_system.run_tick();
      simdelay = System.currentTimeMillis() - simdelay;
      //System.out.println("Tick at "+nf.format(timestamp)+" took "+simdelay+"ms");
      sends.removeAllElements();

      // Delay For Animation Time
      long anim_time = applet.get_speed() - simdelay;
      if (anim_time < 1) anim_time = 1;
      if (anim_time>1) {
	  try { simThread.sleep(anim_time); }
	  catch(InterruptedException except) {}
      }

      upidx++; if (upidx==100) {
	  upidx = 0;
	  update_msgString("Running: sim time = "+nf.format(Sim_system.clock()));
 	  show_update(simThread);
      }
      // Update the state
      for (; ev_index<events.size(); ) {
	// System.gc();
	ev = (Anim_event)events.elementAt(ev_index);
	timestamp = ev.timestamp;
	// Process all events with the same timestamp
	while(ev_index < events.size()) {
	  ev = (Anim_event)events.elementAt(ev_index);
	  if(ev.timestamp > timestamp) break;
	  switch(ev.type) {
          case(Anim_event.SEND):
            sends.addElement(ev.src_port);
            ev.src_port.set_data(ev.data.toString());
            break;
          case(Anim_event.PARAM):
            ev.src_ent.set_params(ev.data.toString());
            ev.src_ent.draw(staticGraphics);
            break;
	  }
	  ev_index++;
	} // end while

	// Ok repaint these updates
	update_msgString("Running: sim time = "+nf.format(timestamp));
	dispTrace();
	//update_static();
	show_update(simThread);
	// Only animate sends if speed > 1
	if (applet.get_speed() > 1) {
	    // Now animate all the sends we encountered
	    if(sends.size() > 0) {
		for(i=0; i<=anim_steps; i++) {
		    for (e = sends.elements(); e.hasMoreElements() ;) {
			port = (Anim_port)e.nextElement();
			if(i == anim_steps) {
			    port.move_msg(-1.0);
			} else
			    port.move_msg((1.0*i)/anim_steps);
		    }
		    show_update(simThread);
		}
	    }
	}
      }


    } while (running);

    // Our work here is done:
    Sim_system.run_stop();
    show_update(simThread);
    update_msgString("Sim completed: sim time = "+nf.format(timestamp));
    draw_all_static();
    genTraceTail();
    show_update(simThread);
  }

}
