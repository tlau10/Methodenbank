/*
 * Created on 27.10.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package AnwBESF;

import java.util.ArrayList;

/**
 * @author jensk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Solver {
	public boolean calculate(String dataSet);
	public void exchange(int i, int j);
	public String LPAnsatzGenerieren();
	public String parseSolution(int durchlauf);
	public void quicksort(int x , int y);
	public void setProperties(int[][] abstandsMatrix_, ArrayList gewichtungsMatrix_,  matrixPlanQuadratController mPQC_, boolean kurzAusgabe_);
	public String[] start();
	public int vergleich(String eins, String zwei);
	public String[][] getOptimaleHalteStellen();
}
