/* Sim_negexp_obj.java
 */

package Simulation.eduni.simjava;

import java.util.Random;
import java.lang.Math;

/**
 * A negative exponential random number generator.
 * @version     1.0, 17 February 1998
 * @author      Fred Howell
 */

public class Sim_negexp_obj {
  private Random gen;
  private double avg;

  /**
   * The constructor.
   * @param name The name to be associated with this instance,
   *             (currently ignored)
   * @param avg  The *mean* of the exponential distribution
   * @param seed The initial seed for the generator, two instances with
   *             the same seed will generate the same sequence of numbers
   * @return A new instance of the class
   */
  public Sim_negexp_obj(String name, double avg, int seed) {
    gen = new Random((long)seed);
    this.avg = avg;
  }

  /**
   * Generate a new random number.
   * @return The next random number in the sequence
   */
  public double sample() {
    return -java.lang.Math.log(gen.nextDouble()) * avg;
  }
}
