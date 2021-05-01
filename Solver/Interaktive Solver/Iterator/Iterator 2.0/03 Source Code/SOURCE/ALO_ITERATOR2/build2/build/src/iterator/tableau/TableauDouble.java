package iterator.tableau;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

public class TableauDouble implements ITableau{

	private static Logger logger = Logger.getLogger(TableauDouble.class);
	
	//Tableaubasic
	private int id;
	private double[][] aMatrix;
	private int aMatrixVariable;
	private int aMatrixRestriction;
	
	private double[] bVektor;
	private double[] zielfunktion;
	
	private double zielfunktionskoeffizient;
	
	
	//Simplex
	private boolean isOptimized = false;
	private int numberOfIterates = 0;
	
	
	
	public TableauDouble(){}
	
	public TableauDouble(int variables, int restrictions){
	
		this.aMatrixVariable 		= variables;
		this.aMatrixRestriction 	= restrictions;
		
		this.zielfunktion = new double[variables];
		this.bVektor = new double[restrictions];
		
		aMatrix = new double[variables][restrictions];
	}
	
	public TableauDouble(TableauDTO dto){
		
		this.id = dto.getId();
		
		this.aMatrix = TableauFunctions.parseBd2NdArray(TableauFunctions.parseString2NdArray(dto.getaMatrix()));
		this.aMatrixRestriction = dto.getAMatrixRestriction();
		this.aMatrixVariable = dto.getAMatrixVariable();
		
		this.bVektor = TableauFunctions.parseBdArray(TableauFunctions.parseStringArray(dto.getBVektor()));
		this.zielfunktion = TableauFunctions.parseBdArray(TableauFunctions.parseStringArray(dto.getZielfunktion()));
		this.zielfunktionskoeffizient = Double.parseDouble(dto.getZielfunktionskoeffizient());
		
		this.isOptimized = dto.isOptimized();
		this.numberOfIterates = dto.getNumberOfIterates();
		
	}
	
		
		
	public Object[][] getaMatrix() {
		return TableauFunctions.parseDouble2NdArray(aMatrix);
	}



	public void setaMatrix(Object[][] aMatrix) {
		this.aMatrix = TableauFunctions.parseBd2NdArray((BigDecimal[][]) aMatrix);
	}



	public int getaMatrixVariable() {
		return aMatrixVariable;
	}



	public void setaMatrixVariable(int aMatrixVariable) {
		this.aMatrixVariable = aMatrixVariable;
	}



	public int getaMatrixRestriction() {
		return aMatrixRestriction;
	}



	public void setaMatrixRestriction(int aMatrixRestriction) {
		this.aMatrixRestriction = aMatrixRestriction;
	}



	public Object[] getbVektor() {
		return TableauFunctions.parseDoubleArray(bVektor);
	}



	public void setbVektor(Object[] bVektor) {
		this.bVektor = TableauFunctions.parseBdArray((BigDecimal[]) bVektor);
	}



	public Object[] getZielfunktion() {
		return TableauFunctions.parseDoubleArray(zielfunktion);
	}



	public void setZielfunktion(Object[] zielfunktion) {
		this.zielfunktion = TableauFunctions.parseBdArray((BigDecimal[]) zielfunktion);
	}



	public Object getZielfunktionskoeffizient() {
		return new BigDecimal(this.zielfunktionskoeffizient);
	}



	public void setZielfunktionskoeffizient(Object zielfunktionskoeffizient) {
		this.zielfunktionskoeffizient = ((BigDecimal) zielfunktionskoeffizient).doubleValue();
	}



	public boolean isOptimized() {
		return isOptimized;
	}



	public void setOptimized(boolean isOptimized) {
		this.isOptimized = isOptimized;
	}



	public int getNumberOfIterates() {
		return numberOfIterates;
	}



	public void setNumberOfIterates(int numberOfIterates) {
		this.numberOfIterates = numberOfIterates;
	}



	public void addVariable(){
		logger.info("TABLEAU (" + this.getId() + "): adding variable");
		
		this.aMatrix = rebuildTableau(this.aMatrixVariable+1, this.aMatrixRestriction);
		this.aMatrixVariable++;

		double[] zfBefore = this.zielfunktion;
		this.zielfunktion = new double[this.aMatrixVariable];
		for (int i = 0; i < zfBefore.length; i++) {
			this.zielfunktion[i] = zfBefore[i];
		}
		
		
	}
	
	public void addRestriction(){
		logger.info("TABLEAU (" + this.getId() + "): adding restriction");
		
		this.aMatrix = rebuildTableau(this.aMatrixVariable, this.aMatrixRestriction + 1);
		this.aMatrixRestriction++;
		
		double[] bVekorBefore = this.bVektor;
		this.bVektor = new double[this.aMatrixRestriction];
		
		for (int i = 0; i < bVekorBefore.length; i++) {
			this.bVektor[i] = bVekorBefore[i];
		}

	}
	
	public void removeVariable() {
		logger.info("TABLEAU (" + this.getId() + "): remove restriction");
		this.aMatrix = rebuildTableau(this.aMatrixVariable-1, this.aMatrixRestriction);
		this.aMatrixVariable--;

		double[] zfBefore = this.zielfunktion;
		this.zielfunktion = new double[this.aMatrixVariable];
		for (int i = 0; i < zielfunktion.length; i++) {
			this.zielfunktion[i] = zfBefore[i];
		}

	}

	public void removeRestriction() {
		logger.info("TABLEAU (" + this.getId() + "): remove restriction");
		
		this.aMatrix = rebuildTableau(this.aMatrixVariable, this.aMatrixRestriction -1);
		this.aMatrixRestriction--;
		
		double[] bVekorBefore = this.bVektor;
		this.bVektor = new double[this.aMatrixRestriction];
		
		for (int i = 0; i < bVektor.length; i++) {
			this.bVektor[i] = bVekorBefore[i];
		}
	}
	
	private double[][] rebuildTableau(int variables, int restrictions){
		logger.debug("rebuilding Tableau: old: " + this.aMatrixVariable +"/"+this.aMatrixRestriction + " --> new:"+variables+"/"+restrictions);
		double[][] newMatrixA = new double[variables][restrictions];
		
		int varTemp =	((this.aMatrixVariable <= variables)? this.aMatrixVariable: variables); 
		int resTemp =	((this.aMatrixRestriction <= restrictions)? this.aMatrixRestriction: restrictions);
		
		for (int i = 0; i < varTemp; i++) {
			for (int j = 0; j < resTemp; j++) {
				logger.debug("  > current: ("+ varTemp + "," + resTemp+") " + i + "/" + j);
				double test = this.aMatrix[i][j];
				newMatrixA[i][j] = test; 
			}
		}
		
		return newMatrixA;
	}

	public void setAValue(int row, int column, Object value){
		this.aMatrix[column][row] = ((BigDecimal) value).doubleValue();
	}
	
	public void setZValue(int row, Object value){
		this.zielfunktion[row] = ((BigDecimal) value).doubleValue();
	}
	
	public void setBValue(int row, Object value){
		this.bVektor[row] = ((BigDecimal) value).doubleValue();
	}
	
	

	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public TableauDTO convertDTO(){
		TableauDTO dto = new TableauDTO();
		
		dto.setId(this.id);
		
		dto.setAMatrix(TableauFunctions.parseBd2NdArrayToString(TableauFunctions.parseDouble2NdArray(this.aMatrix)));
		dto.setAMatrixRestriction(this.aMatrixRestriction);
		dto.setAMatrixVariable(this.aMatrixVariable);
		
		dto.setBVektor(TableauFunctions.parseBdArrayToString(TableauFunctions.parseDoubleArray(this.bVektor)));
			
		dto.setZielfunktion(TableauFunctions.parseBdArrayToString(TableauFunctions.parseDoubleArray(this.zielfunktion)));
		dto.setZielfunktionskoeffizient(TableauFunctions.parseString(new BigDecimal(this.zielfunktionskoeffizient)));
		
		dto.setNumberOfIterates(this.numberOfIterates);
		dto.setOptimized(this.isOptimized);
		
		return dto;
	}

	
	
	
	
	





	
	
	
}
