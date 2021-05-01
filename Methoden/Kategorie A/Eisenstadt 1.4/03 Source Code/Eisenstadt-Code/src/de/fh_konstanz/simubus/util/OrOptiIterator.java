package de.fh_konstanz.simubus.util;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JProgressBar;

import Jama.Matrix;

import de.fh_konstanz.simubus.model.optimierung.BasicSolution;
import de.fh_konstanz.simubus.model.optimierung.OrIterator;

/**
 * Class to encapsulate the old iterator.
 * This optimized iterator should be used instead of the old iterator.
 * The class reduces the source-matrix to a minimized matrix without rows and columns just filled with zeros.
 * This results in an enormous speed-boost, the iterator has less operations to do
 * 
 * @author sidler, smeyer, mkaiser
 * @version 1.0
 * @since ss2007
 *
 */
public class OrOptiIterator {

	/** 
	 * new optimizer :-)
	 * set it active or inactive
	 */
	//private final boolean OPTIMIZE = true;
	private final boolean OPTIMIZE = false;
	
	/** Object to be decorated, the old iterator  */
	private OrIterator orIt;
	
	/** Instance for singleton */
	private static OrOptiIterator instance = null;
	
	/** Rows not only consisting of zero-values */
	private Set<Integer> myForbiddenRows = null;
	
	/** Column not only consisting of zero-values */
	private Set<Integer> myForbiddenCols = null;
	
	/** Number of columns containing only zeros */
	private int myZeroColumnCounter = 0;
	
	/** Number of columns of the minimized matrix */
	private int myColumnsOfMinimalMatrix = 0;
	
	/** Number of columns of the source-matrix */
	private int myColumnsOfMatrix = 0;
	
	/** Number of cells of the matrix (e.g. a single row of 4096 cells...) */
	private int nrOfElementsInSourceMatrix = 0;
	
	/** return an instance of OrOptiIterator --> without parameter for orIterator */
	public static OrOptiIterator getInstance() {
		if(instance == null)
			instance = new OrOptiIterator();
		
		return instance;
	}
	
	/** returns an instance of OrOptiIterator */
	public static OrOptiIterator getInstance(double[][] aMatrix) {
		if(instance == null)
			instance = new OrOptiIterator(aMatrix);
		
		return instance;
	}
	
	/**
	 * resets the current singleton instance with a new instance of OrOptiIterator
	 * and returns the new instance
	 * @param aMatrix
	 * @return
	 */
	public static OrOptiIterator createNewSingletonInstance(double[][] aMatrix) {
		instance = new OrOptiIterator(aMatrix);
		return instance;
	}
	
	
	
	/** 
	 * constructor, being private for the sake of the singleton
	 */
	private OrOptiIterator() {
		
	}
	
	
	/**
	 * CONSTRUCTOR 2, expects a source-matrix and reduces the matrix
	 * @param aMatrix
	 */
	private OrOptiIterator( double[][] aMatrix )
	{
		
		Logger.getInstance().log("OrOpti: new");
		
		if(OPTIMIZE) {
			
			/** Initialize */
			myForbiddenRows = new HashSet<Integer>();
			myForbiddenCols = new HashSet<Integer>();
							
			//create a sumrow
			double[] tempRow = this.createSumVector(aMatrix);
			this.nrOfElementsInSourceMatrix = tempRow.length;
			
			//split up in a new matrix (row to matrix)
			double[][] newMatrix = this.createTwoDimMatrixFromRow(tempRow);	
			
			//create minimal matrix -> determin rows and cols to remove from the 
			//origin source-matrixes
			double[][] minimalMatrix =  this.minimizeMatrix(newMatrix);
			
			//reduce all submatrixes of aMatrix
			for(int i = 0; i < aMatrix.length; i++) {
				double[][] rowMatrix = this.createTwoDimMatrixFromRow(aMatrix[i]); //z.B. aus 4096 --> 64 x 64
				rowMatrix = this.cutMatrix(rowMatrix);//cut Matrix --> 20 x 20
				
				aMatrix[i] = createRowFromMatrix(rowMatrix); // aus 20 x 20 --> 400
			}
			
		}
		
		this.orIt = new OrIterator(aMatrix);
	}

	/**
	 * solves the OR-model
	 * 
	 * @param detailProgressBar
	 * 
	 */
	public Set<BasicSolution> solve( JProgressBar detailProgressBar ) {
		Logger.getInstance().log("OrOpti: solve");
		return this.orIt.solve(detailProgressBar);
		
	}


	/**
	 * Tranforms the matrix, inserts a m-variable and an excess-variable for each restriction (necessary
	 * for >= restrictions). In addition, a preiteration is done to make the matrix become solvable
	 * 
	 */
	public void transform() {
		Logger.getInstance().log("OrOpti: transform");
		this.orIt.transform();
	}

	/**
	 * returns a copy of the matrix
	 * 
	 * @return 
	 */
	public double[][] getMatrix() {
		Logger.getInstance().log("OrOpti: getMatrix");
		return this.orIt.getMatrix();
	}

	
	
	
	/**
	 * creates a sum-matrix containing all rows of the sourcematrix.
	 * the summatrix represents the summarized values of each source row
	 * @param sourceMatrix
	 * @return
	 */
	public double[] createSumVector(double[][] sourceMatrix) {
		
		Logger.getInstance().log("OrOpti: createSumMatrix, n: "+sourceMatrix.length);
		double[] newMatrix = new double[sourceMatrix[0].length];
		
		//init matrix
		for(int i = 0; i < newMatrix.length; i++)
			newMatrix[i] = 0;
		
		//create sums
		for(int i = 0; i < sourceMatrix.length; i++) {
			for(int j = 0; j < sourceMatrix[i].length; j++) {
				newMatrix[j] += sourceMatrix[i][j];
			}
		}
		
		return newMatrix;
	}
	
	
	/**
	 * Creates a n*n matrix out of a source-row
	 * @param sourceRow
	 * @return
	 */
	public double[][] createTwoDimMatrixFromRow(double[] sourceRow) {
		int nrOfRows = (int)Math.sqrt(sourceRow.length);
		
		Logger.getInstance().log("Matrix Dimensions: "+nrOfRows+"x"+nrOfRows);
		double[][] twoDimMatrix = new double[nrOfRows][nrOfRows];
		
		int rowCounter = 0;
		for(int i = 0; i < nrOfRows*nrOfRows; i++) {
			twoDimMatrix[rowCounter][(i % nrOfRows)] = sourceRow[i];
			
			if((i%nrOfRows) == (nrOfRows-1)) {
				rowCounter++;
			}
		}
		
		
		return twoDimMatrix;
	}
	
	/**
	 * Creates a one-dim row from a two-dim matrix
	 * @param sourceMatrix
	 * @return
	 */
	private double[] createRowFromMatrix(double[][] sourceMatrix) {
		double doubleRow[] = new double[(sourceMatrix.length * sourceMatrix[0].length)];
		int linearCounter = 0;
		for(int i = 0; i < sourceMatrix.length; i++) {
			for(int j = 0; j < sourceMatrix[i].length; j++) {
				doubleRow[linearCounter++] = sourceMatrix[i][j];
			}
		}
		return doubleRow;
	}
	
	/**********************************   MINIMIZE MATRIX  ***************************************/
	/** @param: matrix, to be reduced
	 *  @return: matrix (double[][]), without columns and rows consisting of ONLY zero values 
	 * */
	private double[][] minimizeMatrix(double[][] matrix){
		//Get values from matrix 
		myColumnsOfMatrix = matrix[0].length;
		
		//Iterate over columns
		for (int currentCol = 0; currentCol < myColumnsOfMatrix; currentCol++){	
			//check sum of col
			if (this.isColumnSumZero(matrix,currentCol)){
				//cut away by doing nothing :-)		
				//save information about column
				//myColumnReminder.add(currentCol);
				myZeroColumnCounter++;				
			}
		}
		
		//add b-vektor by default
		//myForbiddenRows.add(row);
		myForbiddenCols.add(myColumnsOfMatrix-1);
		
		myColumnsOfMinimalMatrix = myColumnsOfMatrix - myZeroColumnCounter;
		
		return this.cutMatrix(matrix);
		
	}
	
	/**********************************   IS COLUMN SUM ZERO  *************************************/
	/** @param:        matrix, matrix whose column sum should be checked
	 *  @param: columnToCheck, number of column to be checked
	 *  @return: true, if sum of column is zero, else false
	 *  
	 *  Additionally, it adds rows and columns with other values than zero to sets (forbidden rows + cols)
	 * 
	 * */
	private boolean isColumnSumZero(double[][] matrix, int columnToCheck){
		int rows = matrix.length;
		
		boolean bitReturn = true;
			
		for (int row = 0; row < rows; row++){
			if (matrix[row][columnToCheck] > 0.0){
				/*keep this row  in mind
				 * because it´s not necessary to walk through this 
				 * row later
				 */
				myForbiddenRows.add(row);
				myForbiddenCols.add(columnToCheck);
				bitReturn = false;
			}
			else{
				//do nothing, go on
			}			
		}
		return bitReturn;
	}
	
	/**********************************   COPY COLUMN  ***************************************/
	/**
	 * @param:    sourceCol: number of column to be copied (source)<br>
	 * @param: sourceMatrix: matrix whose column to be copied from <br>
	 * @param:    targetCol: number target column(target) <br>
	 * @param: targetMatrix: target matrix where the copy of the sourceCol will be inserted<br>
	 * @return: void
	 * 
	 */
	private void copyColumn(int sourceCol,double[][] sourceMatrix,int targetCol,double[][] targetMatrix){
				
		//Compare column length
		if (sourceMatrix.length == targetMatrix.length){
			/* If length are equal, copy it */
			for (int row = 0; row < sourceMatrix.length; row++ ){
				targetMatrix[row][targetCol] = sourceMatrix[row][sourceCol];
			}
		}
		else{
			/* Else, if not the same length, cut away by skipping row */
			int rowCounter = 0;
			for (int row = 0; row < sourceMatrix.length; row++ ){
				if (myForbiddenRows.contains(row)){ //can not zero
					targetMatrix[rowCounter++][targetCol] = sourceMatrix[row][sourceCol];
				}
				else{
					//do nothing, skip this row
				}
			}						
		}		
	}
	
	
	
	/**
	 * Reduces a given matrix with the values calculated by mimizeMatrix.
	 * 
	 * @param sourceMatrix
	 * @see minizeMatrix
	 * @return
	 */
	private double[][] cutMatrix(double[][] sourceMatrix) {
		double[][] minimalMatrix = null;
		int colPointer = 0;
		//Columns for minimal matrix
		//myColumnsOfMinimalMatrix = myColumnsOfMatrix - myZeroColumnCounter; 
		
		//Rows for minimal matrix
		/* Every row not contained in the set (myForbiddenRows) consist of zeros
		 * 
		 * because you walked through all columns and every column, in which there were
		 * ONE in it, you added it to the set
		 */
		int rowsOfMinimalMatrix = myForbiddenRows.size();
		
		minimalMatrix = new double[rowsOfMinimalMatrix][];
		
		//Create minimal matrix
		for (int r = 0; r < rowsOfMinimalMatrix; r++){
			minimalMatrix[r] = new double[myColumnsOfMinimalMatrix];
			
			for(int j = 0; j < myColumnsOfMinimalMatrix; j++) {
				minimalMatrix[r][j] = 0.0;
			}
		}
		
		Logger.getInstance().log("size of minimalMatrix: "+minimalMatrix.length+"x"+minimalMatrix[0].length);
		
	 
		//Fill matrix (col)
		for (int currentCol = 0; currentCol < myColumnsOfMatrix; currentCol++){
			if (myForbiddenCols.contains(currentCol)){
				//copy column in minimizeMatrix			
				//prototype of copyColumn >> (sourceColumn, sourceMatrix, targetColum, targetMatrix)
				Logger.getInstance().log("call copyColumn, currentCol: "+ currentCol + " colPointer: "+ colPointer);
				this.copyColumn(currentCol,sourceMatrix,colPointer, minimalMatrix);
				colPointer++;
			}
			else{
				//do nothing, column consists only of zeros
			}
		}
		
		//return value
		return minimalMatrix;
	}
	
	/**
	 * Fills the minized Matrix with zeros to create regular marix
	 * @param aMatrix = anzahlLösungen * 4096
	 * @return
	 */
	public Matrix fillMinimalMatrix(Matrix aMatrix) {
		int nDimension = (int) Math.sqrt(this.nrOfElementsInSourceMatrix);
		Logger.getInstance().log("nDimension: " + nDimension+" rows:"+aMatrix.getRowDimension());

		if (!OPTIMIZE)
			return aMatrix;

		// z.B. 3 x x, x<=4096
		double[][] minmizedMatrix = aMatrix.getArray();

		double[][] maximizedMatrix = new double[aMatrix.getRowDimension()][this.nrOfElementsInSourceMatrix];

		for (int r = 0; r < (aMatrix.getRowDimension()); r++) {
			maximizedMatrix[r] = new double[this.nrOfElementsInSourceMatrix];

			for (int j = 0; j < (this.nrOfElementsInSourceMatrix); j++) {
				maximizedMatrix[r][j] = 0.0;
			}
		}

		for (int k = 0; k < (minmizedMatrix.length); k++) {

			/**
			 * current values to use out of the minimized and optimized matrix (a single row)
			 */
			double[] minimizedMatrixAsRow = minmizedMatrix[k];

			/**
			 * current row of the maximized matrix gets transformed into a 2-d matrix.
			 * this allows the access by using x and y values
			 * 
			 */
			double[][] currentMaximizedRowAsMatrix = this.createTwoDimMatrixFromRow(maximizedMatrix[k]);

			int sourceRowCounter = 0;
			for (int i = 0; i < nDimension; i++) {

				if (this.myForbiddenRows.contains(i)) {

					for (int j = 0; j < nDimension; j++) {

						if (this.myForbiddenCols.contains(j)) {
							currentMaximizedRowAsMatrix[i][j] = minimizedMatrixAsRow[sourceRowCounter++];
						}
					}
				}
			}
			/**
			 * the 2-d matrix is retransformed into a 4096 row and copied into the return-array
			 */
			maximizedMatrix[k] = this.createRowFromMatrix(currentMaximizedRowAsMatrix);
			
			
		}

		Logger.getInstance().log("maximized: "+maximizedMatrix.length);
		return new Matrix(maximizedMatrix);
	}
	
	
	
	
	/**
	 * DEBUGGER
	 * @param matrix
	 */
	public void printMatrix(double[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j]+ " ");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * prints the matrix to the console
	 * 
	 */
	public void printMatrix() {
		Logger.getInstance().log("OrOpti: printMatrix");
		this.orIt.printMatrix();
	}
	

}
