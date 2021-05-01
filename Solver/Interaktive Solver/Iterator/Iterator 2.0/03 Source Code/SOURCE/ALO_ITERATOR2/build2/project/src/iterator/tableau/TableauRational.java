package iterator.tableau;

import org.apache.log4j.Logger;
import org.jscience.mathematics.number.Rational;

public class TableauRational implements ITableau{

	private static Logger logger = Logger.getLogger(TableauRational.class);
	
	//Tableaubasic
	private int id;
	private Rational[][] aMatrix;
	private int aMatrixVariable;
	private int aMatrixRestriction;
	
	private Rational[] bVektor;
	private Rational[] zielfunktion;
	
	private Rational zielfunktionskoeffizient;
	
	
	//Simplex
	private boolean isOptimized = false;
	private int numberOfIterates = 0;
	
	
	
	public TableauRational(){}
	
	public TableauRational(int variables, int restrictions){
	
		this.aMatrixVariable 		= variables;
		this.aMatrixRestriction 	= restrictions;
		
		this.zielfunktion = new Rational[variables];
		this.bVektor = new Rational[restrictions];
		
		this.aMatrix = new Rational[variables][restrictions];
		
		for (int i = 0; i < this.aMatrixVariable; i++) {
			this.zielfunktion[i] = Rational.ZERO;
			for (int j = 0; j < this.aMatrixRestriction; j++) {
				this.aMatrix[i][j] = Rational.ZERO;
				if(this.bVektor[j] == null){
					this.bVektor[j] = Rational.ZERO;
				}
			}
		}
	}
	
	public TableauRational(TableauDTO dto){
		
		this.id = dto.getId();
		
		this.aMatrix = TableauFunctions.parseString2NdArrayToRational(dto.getaMatrix());
		this.aMatrixRestriction = dto.getAMatrixRestriction();
		this.aMatrixVariable = dto.getAMatrixVariable();
		
		this.bVektor = TableauFunctions.parseStringArrayToRational(dto.getBVektor());
		this.zielfunktion = TableauFunctions.parseStringArrayToRational(dto.getZielfunktion());
		this.zielfunktionskoeffizient = TableauFunctions.parseRational(dto.getZielfunktionskoeffizient());
		
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

		Rational[] zfBefore = this.zielfunktion;
		this.zielfunktion = new Rational[this.aMatrixVariable];
		for (int i = 0; i < zfBefore.length; i++) {
			this.zielfunktion[i] = zfBefore[i];
		}
		
		
	}
	
	public void addRestriction(){
		logger.info("TABLEAU (" + this.getId() + "): adding restriction");
		
		this.aMatrix = rebuildTableau(this.aMatrixVariable, this.aMatrixRestriction + 1);
		this.aMatrixRestriction++;
		
		Rational[] bVekorBefore = this.bVektor;
		this.bVektor = new Rational[this.aMatrixRestriction];
		
		for (int i = 0; i < bVekorBefore.length; i++) {
			this.bVektor[i] = bVekorBefore[i];
		}

	}
	
	public void removeVariable() {
		logger.info("TABLEAU (" + this.getId() + "): remove restriction");
		this.aMatrix = rebuildTableau(this.aMatrixVariable-1, this.aMatrixRestriction);
		this.aMatrixVariable--;

		Rational[] zfBefore = this.zielfunktion;
		this.zielfunktion = new Rational[this.aMatrixVariable];
		for (int i = 0; i < zielfunktion.length; i++) {
			this.zielfunktion[i] = zfBefore[i];
		}

	}

	public void removeRestriction() {
		logger.info("TABLEAU (" + this.getId() + "): remove restriction");
		
		this.aMatrix = rebuildTableau(this.aMatrixVariable, this.aMatrixRestriction -1);
		this.aMatrixRestriction--;
		
		Rational[] bVekorBefore = this.bVektor;
		this.bVektor = new Rational[this.aMatrixRestriction];
		
		for (int i = 0; i < bVektor.length; i++) {
			this.bVektor[i] = bVekorBefore[i];
		}
	}
	
	private Rational[][] rebuildTableau(int variables, int restrictions){
		logger.debug("rebuilding Tableau: old: " + this.aMatrixVariable +"/"+this.aMatrixRestriction + " --> new:"+variables+"/"+restrictions);
		Rational[][] newMatrixA = new Rational[variables][restrictions];
		
		int varTemp =	((this.aMatrixVariable <= variables)? this.aMatrixVariable: variables); 
		int resTemp =	((this.aMatrixRestriction <= restrictions)? this.aMatrixRestriction: restrictions);
		
		for (int i = 0; i < varTemp; i++) {
			for (int j = 0; j < resTemp; j++) {
				logger.debug("  > current: ("+ varTemp + "," + resTemp+") " + i + "/" + j);
				Rational test = this.aMatrix[i][j];
				newMatrixA[i][j] = test; 
			}
		}
		
		return newMatrixA;
	}

	public void setAValue(int row, int column, Object value){
		this.aMatrix[column][row] = (Rational) value;
	}
	
	public void setZValue(int row, Object value){
		this.zielfunktion[row] = (Rational) value;
	}
	
	public void setBValue(int row, Object value){
		this.bVektor[row] = (Rational) value;
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
		
		dto.setAMatrix(TableauFunctions.parseRational2NdArrayToString(this.aMatrix));
		dto.setAMatrixRestriction(this.aMatrixRestriction);
		dto.setAMatrixVariable(this.aMatrixVariable);
		
		dto.setBVektor(TableauFunctions.parseRationalArrayToString(this.bVektor));
			
		dto.setZielfunktion(TableauFunctions.parseRationalArrayToString(this.zielfunktion));
		dto.setZielfunktionskoeffizient(TableauFunctions.parseString(this.zielfunktionskoeffizient));
		
		dto.setNumberOfIterates(this.numberOfIterates);
		dto.setOptimized(this.isOptimized);
		
		return dto;
	}
	


	@Override
	public Object[][] getaMatrix() {
		return this.aMatrix;
	}

	@Override
	public void setaMatrix(Object[][] aMatrix) {
		this.aMatrix = (Rational[][]) aMatrix;
		
	}

	@Override
	public Object[] getbVektor() {
		return this.bVektor;
	}

	@Override
	public void setbVektor(Object[] bVektor) {
		this.bVektor = (Rational[]) bVektor;
		
	}

	@Override
	public Object[] getZielfunktion() {
		return this.zielfunktion;
	}

	@Override
	public void setZielfunktion(Object[] zielfunktion) {
		this.zielfunktion = (Rational[]) zielfunktion;
		
	}

	@Override
	public Rational getZielfunktionskoeffizient() {
		return zielfunktionskoeffizient;
	}

	@Override
	public void setZielfunktionskoeffizient(Object zielfunktionskoeffizient) {
		this.zielfunktionskoeffizient = (Rational) zielfunktionskoeffizient;
		
	}
	
	
	
	
	





	
	
	
}
