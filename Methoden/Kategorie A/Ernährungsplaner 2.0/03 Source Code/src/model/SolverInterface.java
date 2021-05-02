package model;

import controller.Matrix;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: SolverInterface
 * </p>
 * <p>
 * Description: Interface für alle Solver, die implementiert werden
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 (Refactoring)
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */
public interface SolverInterface {
	public double[] calcModel(Matrix lpModell, Matrix borders);

	public void createInputFile(Matrix lpModell, Matrix borders);

	public void createBatchFile();
}
