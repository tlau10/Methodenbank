/* Sim_normal_obj.java
 */

package Simulation.eduni.simjava;

import java.util.Random;
import java.lang.Math;

/**
 * A uniformally distrubuted random number generator.
 * @version     1.0, 4 September 1996
 * @author      Ross McNab
 */

public class Sim_uniform_obj {
  private Random gen;
  private double mag, min;

  /**
   * The constructor.
   * @param name The name to be associated with this instance,
   *             (currently ignaored)
   * @param min  The minimum value this instance should generate
   * @param max  The maximum value this instance should generate
   * @param seed The initial seed for the generator, two instances with
   *             the same seed will generate the same sequence of numbers
   * @return A new instance of the class
   */
  public Sim_uniform_obj(String name, double min, double max,int seed) {
    gen = new Random((long)seed);
    this.mag = max-min;
    this.min = min;
  }

  /**
   * Generate a new random number.
   * @return The next random number in the sequence
   */
  public double sample() {
    return mag*gen.nextDouble() + min;
  }
}
