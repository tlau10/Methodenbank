package hotelbelegung;

/*
 * Solver.java
 *
 * Created on 2. Januar 2003, 16:21
 */

/**
 *
 * @author  Florian Raiber
 */
public interface Solver {

    public double[] calcModel(Frame1 frame, String[][] lpModell, int zeilen, int spalten, String[][] restriktionen);

}

