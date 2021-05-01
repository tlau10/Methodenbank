/* Sim_normal_obj.java
 */

package Simulation.eduni.simjava;

import java.util.Random;
import java.lang.Math;

/**
 * A normally distrubuted random number generator
 * @version     1.0, 4 September 1996
 * @author      Ross McNab
 */

public class Sim_normal_obj {
  private Random gen;
  private double mean, stdev;

  /**
   * The constructor.
   * @param name The name to be associated with this instance,
   *             (currently ignaored)
   * @param mean The mean value about which numbers should be generated
   * @param var  The varience numbers should have about the mean
   * @param seed The initial seed for the generator, two instances with
   *             the same seed will generate the same sequence of numbers
   * @return A new instance of the class
   */
  public Sim_normal_obj(String name, double mean, double var, int seed) {
    gen = new Random((long)seed);
    this.mean = mean;
    this.stdev = Math.sqrt(var);
  }

  /**
   * Generate a new random number.
   * @return The next random number in the sequence
   */
  public double sample() {
    return stdev*gen.nextGaussian() + mean;
  }
}
