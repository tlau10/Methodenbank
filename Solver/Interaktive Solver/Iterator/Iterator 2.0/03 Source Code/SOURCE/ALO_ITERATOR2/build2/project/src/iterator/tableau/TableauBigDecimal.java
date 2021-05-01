package iterator.tableau;

import java.math.BigDecimal;


import org.apache.log4j.Logger;

public class TableauBigDecimal implements ITableau{

	private static Logger logger = Logger.getLogger(TableauBigDecimal.class);
	
	//Tableaubasic
	private int id;
	private BigDecimal[][] aMatrix;
	private int aMatrixVariable;
	private int aMatrixRestriction;
	
	private BigDecimal[] bVektor;
	private BigDecimal[] zielfunktion;
	
	private BigDecimal zielfunktionskoeffizient;
	
	
	//Simplex
	private boolean isOptimized = false;
	private int numberOfIterates = 0;
	
	
	
	public TableauBigDecimal(){}
	
	public TableauBigDecimal(int variables, int restrictions){
	
		this.aMatrixVariable 		= variables;
		this.aMatrixRestriction 	= restrictions;
		
		this.zielfunktion = new BigDecimal[variables];
		this.bVektor = new BigDecimal[restrictions];
		
		this.aMatrix = new BigDecimal[variables][restrictions];
		
		for (int i = 0; i < this.aMatrixVariable; i++) {
			this.zielfunktion[i] = new BigDecimal(0);
			for (int j = 0; j < this.aMatrixRestriction; j++) {
				this.aMatrix[i][j] = new BigDecimal(0);
				if(this.bVektor[j] == null){
					this.bVektor[j] = new BigDecimal(0);
				}
			}
		}
	}
	
	public TableauBigDecimal(TableauDTO dto){
		
		this.id = dto.getId();
		
		this.aMatrix = TableauFunctions.parseString2NdArray(dto.getaMatrix());
		this.aMatrixRestriction = dto.getAMatrixRestriction();
		this.aMatrixVariable = dto.getAMatrixVariable();
		
		this.bVektor = TableauFunctions.parseStringArray(dto.getBVektor());
		this.zielfunktion = TableauFunctions.parseStringArray(dto.getZielfunktion());
		this.zielfunktionskoeffizient = TableauFunctions.parseBigDecimal(dto.getZielfunktionskoeffizient());
		
		this.isOptimized = dto.isOptimized();
		this.numberOfIterates = dto.getNumberOfIterates();
		
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

		BigDecimal[] zfBefore = this.zielfunktion;
		this.zielfunktion = new BigDecimal[this.aMatrixVariable];
		for (int i = 0; i < zfBefore.length; i++) {
			this.zielfunktion[i] = zfBefore[i];
		}
		
		
	}
	
	public void addRestriction(){
		logger.info("TABLEAU (" + this.getId() + "): adding restriction");
		
		this.aMatrix = rebuildTableau(this.aMatrixVariable, this.aMatrixRestriction + 1);
		this.aMatrixRestriction++;
		
		BigDecimal[] bVekorBefore = this.bVektor;
		this.bVektor = new BigDecimal[this.aMatrixRestriction];
		
		for (int i = 0; i < bVekorBefore.length; i++) {
			this.bVektor[i] = bVekorBefore[i];
		}

	}
	
	public void removeVariable() {
		logger.info("TABLEAU (" + this.getId() + "): remove restriction");
		this.aMatrix = rebuildTableau(this.aMatrixVariable-1, this.aMatrixRestriction);
		this.aMatrixVariable--;

		BigDecimal[] zfBefore = this.zielfunktion;
		this.zielfunktion = new BigDecimal[this.aMatrixVariable];
		for (int i = 0; i < zielfunktion.length; i++) {
			this.zielfunktion[i] = zfBefore[i];
		}

	}

	public void removeRestriction() {
		logger.info("TABLEAU (" + this.getId() + "): remove restriction");
		
		this.aMatrix = rebuildTableau(this.aMatrixVariable, this.aMatrixRestriction -1);
		this.aMatrixRestriction--;
		
		BigDecimal[] bVekorBefore = this.bVektor;
		this.bVektor = new BigDecimal[this.aMatrixRestriction];
		
		for (int i = 0; i < bVektor.length; i++) {
			this.bVektor[i] = bVekorBefore[i];
		}
	}
	
	private BigDecimal[][] rebuildTableau(int variables, int restrictions){
		logger.debug("rebuilding Tableau: old: " + this.aMatrixVariable +"/"+this.aMatrixRestriction + " --> new:"+variables+"/"+restrictions);
		BigDecimal[][] newMatrixA = new BigDecimal[variables][restrictions];
		
		int varTemp =	((this.aMatrixVariable <= variables)? this.aMatrixVariable: variables); 
		int resTemp =	((this.aMatrixRestriction <= restrictions)? this.aMatrixRestriction: restrictions);
		
		for (int i = 0; i < varTemp; i++) {
			for (int j = 0; j < resTemp; j++) {
				logger.debug("  > current: ("+ varTemp + "," + resTemp+") " + i + "/" + j);
				BigDecimal test = this.aMatrix[i][j];
				newMatrixA[i][j] = test; 
			}
		}
		
		return newMatrixA;
	}

	public void setAValue(int row, int column, Object value){
		this.aMatrix[column][row] = (BigDecimal) value;
	}
	
	public void setZValue(int row, Object value){
		this.zielfunktion[row] = (BigDecimal) value;
	}
	
	public void setBValue(int row, Object value){
		this.bVektor[row] = (BigDecimal) value;
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
		
		dto.setAMatrix(TableauFunctions.parseBd2NdArrayToString(this.aMatrix));
		dto.setAMatrixRestriction(this.aMatrixRestriction);
		dto.setAMatrixVariable(this.aMatrixVariable);
		
		dto.setBVektor(TableauFunctions.parseBdArrayToString(this.bVektor));
			
		dto.setZielfunktion(TableauFunctions.parseBdArrayToString(this.zielfunktion));
		dto.setZielfunktionskoeffizient(TableauFunctions.parseString(this.zielfunktionskoeffizient));
		
		dto.setNumberOfIterates(this.numberOfIterates);
		dto.setOptimized(this.isOptimized);
		
		return dto;
	}
	


	@Override
	public BigDecimal[][] getaMatrix() {
		return this.aMatrix;
	}

	@Override
	public void setaMatrix(Object[][] aMatrix) {
		this.aMatrix = (BigDecimal[][]) aMatrix;
		
	}

	@Override
	public BigDecimal[] getbVektor() {
		return this.bVektor;
	}

	@Override
	public void setbVektor(Object[] bVektor) {
		this.bVektor = (BigDecimal[]) bVektor;
		
	}

	@Override
	public Object[] getZielfunktion() {
		return this.zielfunktion;
	}

	@Override
	public void setZielfunktion(Object[] zielfunktion) {
		this.zielfunktion = (BigDecimal[]) zielfunktion;
		
	}

	@Override
	public BigDecimal getZielfunktionskoeffizient() {
		return zielfunktionskoeffizient;
	}

	@Override
	public void setZielfunktionskoeffizient(Object zielfunktionskoeffizient) {
		this.zielfunktionskoeffizient = (BigDecimal) zielfunktionskoeffizient;
		
	}
	
	
	
	
	





	
	
	
}
