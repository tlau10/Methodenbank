package iterator.tableau;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tableau")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableauDTO {
	
	//Tableaubasic
	private int id;
	private String[][] aMatrix;
	private int aMatrixVariable;
	private int aMatrixRestriction;
	
	private String[] bVektor;
	private String[] zielfunktion;
	
	private String zielfunktionskoeffizient;
	
	
	//Simplex
	private boolean isOptimized = false;
	private int numberOfIterates = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String[][] getaMatrix() {
		return aMatrix;
	}
	public void setAMatrix(String[][] aMatrix) {
		this.aMatrix = aMatrix;
	}
	public int getAMatrixVariable() {
		return aMatrixVariable;
	}
	public void setAMatrixVariable(int aMatrixVariable) {
		this.aMatrixVariable = aMatrixVariable;
	}
	public int getAMatrixRestriction() {
		return aMatrixRestriction;
	}
	public void setAMatrixRestriction(int aMatrixRestriction) {
		this.aMatrixRestriction = aMatrixRestriction;
	}
	public String[] getBVektor() {
		return bVektor;
	}
	public void setBVektor(String[] bVektor) {
		this.bVektor = bVektor;
	}
	public String[] getZielfunktion() {
		return zielfunktion;
	}
	public void setZielfunktion(String[] zielfunktion) {
		this.zielfunktion = zielfunktion;
	}
	public String getZielfunktionskoeffizient() {
		return zielfunktionskoeffizient;
	}
	public void setZielfunktionskoeffizient(String zielfunktionskoeffizient) {
		this.zielfunktionskoeffizient = zielfunktionskoeffizient;
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
	
	
	
	
}
