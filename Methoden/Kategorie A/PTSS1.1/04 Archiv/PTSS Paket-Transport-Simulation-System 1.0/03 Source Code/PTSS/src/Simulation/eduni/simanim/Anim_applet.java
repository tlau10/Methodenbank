/** The Super class for simulation animation applets
 */

package Simulation.eduni.simanim;

import java.applet.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Simulation.eduni.simjava.*;
import Simulation.eduni.simdiag.*;

/**
 * The superclass for all simulation animations. New animations
 * should <tt>extend</tt> this and provide bodies for the methods
 * <tt>anim_layout()</tt> and <tt>anim_init()</tt>.
 * <p>
 * See the <a href="../simanim_guide/index.html"> simanim guide</a>
 * for more information on how to use this class.
 * @version     1.0, 4 September
 * @version     1.1, 24 March - updates for JDK 1.1
 * @author      Ross McNab
 */

public abstract class Anim_applet extends JPanel implements Runnable,
ActionListener, AdjustmentListener, Traceable {
  Thread simThread;
  // The AWT bits
  //protected GridBagLayout gbl;
  //protected GridBagConstraints gbc;
  protected Panel controls;
  protected Sim_anim trace_out;
  Button runBut, stopBut, layoutBut, pauseBut;
  Label speedLabel;
  Scrollbar speedScroll;
  // The options
  int speed = 10;
  boolean paused = false;

  // Two functions that should be over-ridden in the sub-class
  // MUST be used toset up all the entities
  /** This method <b>must</b> be provided in the subclass, it should setup
   * all the simulation entities, and link their ports. The method should
   * never be called by the subclass itself, call <tt>anim_relayout()</tt>
   * instead.
   */
  public abstract void anim_layout();

  /** This method can be overidden in the subclass, and used to setup
   * any GUI objects to be used for input to the animation.
   * The method is called once when the applet starts up.
   */
  public void anim_init() {}

  /** This method can be overidden in the subclass, and used to
   * display results.
   * It is called once when each simulation run finishes.
   */
  public void anim_completed() {}

  /** This method should not be overidden in the subclass, use
   * <tt>anim_init()</tt> instead. */
  public final void init() {
      //     System.out.println("Anim_applet.init()");
    controls = new Panel();
    GridBagLayout gb = new GridBagLayout();
    controls.setLayout(gb);
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;
    layoutBut = new Button("Layout");
    layoutBut.addActionListener(this);
    gb.setConstraints(layoutBut, c);
    controls.add(layoutBut);
    runBut = new Button("Run");
    runBut.addActionListener(this);
    gb.setConstraints(runBut, c);
    controls.add(runBut);
    pauseBut = new Button("Pause");
    pauseBut.addActionListener(this);
    gb.setConstraints(pauseBut, c);
    pauseBut.setEnabled(false); // disable();
    controls.add(pauseBut);
    c.gridwidth = GridBagConstraints.REMAINDER; //end row
    stopBut = new Button("Stop");
    stopBut.addActionListener(this);
    gb.setConstraints(stopBut, c);
    stopBut.setEnabled(false); // disable();
    controls.add(stopBut);

    c.gridwidth = 1;
    speedLabel = new Label("Speed: "+speed);
    gb.setConstraints(speedLabel, c);
    controls.add(speedLabel);
    c.gridwidth = GridBagConstraints.REMAINDER; //end row
    speedScroll = new Scrollbar(Scrollbar.HORIZONTAL, speed,
                                1, 1, 1000);
    speedScroll.setUnitIncrement(10);
    speedScroll.addAdjustmentListener(this);
    gb.setConstraints(speedScroll, c);
    controls.add(speedScroll);

    trace_out = new Sim_anim(this);


    /* Top level layout */
    /* Top level gridbag don't work
    gbl = new GridBagLayout();
    gbc = new GridBagConstraints();
    this.setLayout(gbl);
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty=1.0;
    // this.add(trace_out); layout.setConstraints(trace_out, gbc);
    this.add(controls); gbl.setConstraints(controls, gbc);
    //anim_init();
    */

    this.setLayout(new BorderLayout(5,5));
    this.add("Center", trace_out);
    this.add("South",  controls);
    anim_init();

    /*
    this.setLayout(new BorderLayout(5,5));
    this.add("Center", trace_out);
    this.add("South", controls);
    anim_init();
    */
  }


  /** This method should not be overidden in the subclass */
  public final void start() {
    anim_relayout();
  }

  /** This method should not be overidden in the subclass */
  public final void stop() {
    simThread = null;
  }

  /** Reinitialises all the animation entities, by calling
   * <tt>anim_layout()</tt>, then redraws them. This method
   * can be called by the subclass if a change to the entities
   * needs to be shown before the simulation starts. It should be
   * used instead of calling <tt>anim_layout()</tt> directly.
   */
  public void anim_relayout() {
    Sim_system.initialise(trace_out, simThread);
    Sim_system.set_auto_trace(false);
    anim_layout();

    /* Pass on the Layout event to all listeners */
    /* Messy method. */
    trace_out.forwardTrace( new TraceEventObject(trace_out,LAYOUT) );
    // Dump out all bars etc.


    trace_out.draw_all_static();
    trace_out.repaint();
  }

  int get_speed() { return speed; }
  boolean get_paused() { return paused; }

  void startSim() {
    stopBut.setEnabled(true);
    pauseBut.setEnabled(true); paused = false;
    runBut.setEnabled(false); // disable();
    layoutBut.setEnabled(false); // disable();
    if (simThread == null) {
      simThread = new Thread(this);
    }
    simThread.start();
  }


  void stopSim() {
    stopBut.setEnabled(false);
    pauseBut.setEnabled(false); paused = false;
    runBut.setEnabled(true);// enable();
    layoutBut.setEnabled(true);// enable();
    simThread = null;
    trace_out.close();
    System.gc();        // Tidy up memory usage
  }

  /** Updated action handler for 1.1 event model
   */
  public void actionPerformed(ActionEvent e) {
  // public boolean handleEvent(Event e) {
    Object source = e.getSource();
    // target = e.target;

    // if (e.id == Event.ACTION_EVENT) {
    if (source == runBut) {
      startSim();
    } else if (source == layoutBut) {
      anim_relayout();
    } else if (source == stopBut) {
  //    simThread.stop();
      stopSim();
    } else if (source == pauseBut) {
      if(paused) {
	paused = false;
	pauseBut.setLabel("Pause");
      } else {
	paused = true;
	pauseBut.setLabel("Restart");
      }
    }
    //    return super.processEvent(e);
  }

  /** Scroll bar event handler
   */
  public void adjustmentValueChanged(AdjustmentEvent e) {
    Object source = e.getSource();
    if (source == speedScroll){
      speed = speedScroll.getValue();
      speedLabel.setText("Speed: "+speed);
    }
  }


  /** This method should not be overidden in the subclass */
  public final void run() {
    System.out.println("Entering simThread run()");
    // layout the simulation
    anim_relayout();
    // Run -- moved to Sim_anim.animate()
    // Sim_system.run();
    // System.out.println("Exiting simThread run()");
    System.gc();                   // Tidy up memory usage
    trace_out.animate(simThread);  // now run anim :-)
    stopSim();
    anim_completed();
  }


}







