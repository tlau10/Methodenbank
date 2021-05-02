/* Sim_port.java
 */

package Simulation.eduni.simjava;

import Simulation.eduni.simanim.*;

/**
 * This class represents ports which are used to connect entities for
 * event passing.
 * @see         Sim_system
 * @version     1.0, 4 September 1996
 * @author      Ross McNab
 */

public class Sim_port {
  // Private data members
  private String pname;      // The port's name
  private String dest_ename; // The destination entity's name
  private int srce;          // Index of the source entity
  private int deste;         // Index of the destination entity
  private Anim_port aport;

  //
  // Public library interface
  //

  // Constructors
  //public Sim_port(String d) {
  //  dest_ename = d;
  //  deste = -1;
  //}
  //public Sim_port(int d) {
  //  dest_ename = null;
  //  deste = d;
  //}
  /** Constructor, for stand-alone simulations.
   * @param port_name The name to identify this port
   * @return A new instance of the class Sim_port
   */
  public Sim_port(String port_name) {
    pname = port_name;
    dest_ename = null;
    srce = -1;
    deste = -1;
    aport = null;
  }
  // Anim constructor
  /** Constructor for use with the eduni.simanim package for animations.
   * @param port_name The name to identify this port
   * @param image_name The name of the gif graphics file to use for this port's
   *                   icon, (without the ".gif" extension)
   * @param side Which side of the parent entity the port should be drawn on,
   *             one of Anim_port.LEFT, Anim_port.RIGHT, Anim_port.TOP, or
   *             Anim_port.BOTTOM.
   * @param pos How many pixels along that side the port should be drawn.
   * @return A new instance of the class Sim_port
   */
  public Sim_port(String port_name, String image_name, int side, int pos) {
    pname = port_name;
    dest_ename = null;
    srce = -1;
    deste = -1;
    // Now anim stuff
    aport = new Anim_port(port_name, image_name);
    aport.set_position(side, pos);
  }

  // Public access methods
  /** Get the name of the destination entity of this port.
   * @return The name of the entity
   */
  public String get_dest_ename() { return dest_ename; }
  /** Get the name of this port.
   * @return The name
   */
  public String get_pname() { return pname; }
  /** Get the unique id number of the destination entity of this port.
   * @return The id number
   */
  public int get_dest() { return deste; }
  /** Get the unique id number of the source entity of this port.
   * @return The id number
   */
  public int get_src() { return srce; }

  //
  // Package level interface
  //

  Anim_port get_aport() { return aport; }
  void set_src(int s) { srce = s; }
  void connect(Sim_entity dest) {
    dest_ename = dest.get_name();
    deste = dest.get_id();
  }

  // Set and return the destination entity id for this port
  //int connect(int s) {
  //  /* Initialise deste from destename */
  //  srce = s;
  //  if(deste==-1) {
  //    if(dest_ename != null)
  //      deste = Sim_system.get_entity_id(dest_ename);
  //  }
  //  return deste;
  //}

  //void set_pname(String pn) { pname = pn; }
}
