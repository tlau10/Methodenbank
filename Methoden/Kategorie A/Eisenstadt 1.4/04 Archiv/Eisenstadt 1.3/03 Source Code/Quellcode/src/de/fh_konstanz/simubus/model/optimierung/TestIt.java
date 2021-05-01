package de.fh_konstanz.simubus.model.optimierung;

import javax.swing.JProgressBar;

import de.fh_konstanz.simubus.util.OrOptiIterator;

public class TestIt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		double[][]myMatrix = new double[1][16];
		
		for (int i = 0; i < myMatrix.length; i++) {
			for (int j = 0; j < myMatrix[i].length; j++) {
//				myMatrix[i][j] = (Math.random()<0.5?0.0:1.0);
				myMatrix[i][j] = 0.0;
			}
		}
		
		myMatrix[0][0] = 1;
		myMatrix[0][3] = 1;
		myMatrix[0][6] = 1;
		myMatrix[0][11] = 1;
		
		OrOptiIterator myOR = OrOptiIterator.getInstance(myMatrix);
		myOR.transform();
//		JProgressBar j = new JProgressBar();
//		myOR.solve(j);
		
		
		
		

	}

}
