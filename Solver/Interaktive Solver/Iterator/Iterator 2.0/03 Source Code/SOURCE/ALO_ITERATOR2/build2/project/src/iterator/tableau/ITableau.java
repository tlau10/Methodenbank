package iterator.tableau;

public interface ITableau {
	public int getId();
	public void setId(int id);
	
	public Object[][] getaMatrix();
	public void setaMatrix(Object[][] aMatrix);
	
	public int getaMatrixVariable() ;
	public void setaMatrixVariable(int aMatrixVariable);
	public void addVariable();
	public void removeVariable();

	public int getaMatrixRestriction();
	public void setaMatrixRestriction(int aMatrixRestriction);
	public void addRestriction();
	public void removeRestriction();
	
	public Object[] getbVektor();
	public void setbVektor(Object[] bVektor);
	
	public void setAValue(int row, int column, Object value);
	public void setZValue(int column, Object value);
	public void setBValue(int row, Object value);
	
	public Object[] getZielfunktion();
	public void setZielfunktion(Object[] zielfunktion);
	public Object getZielfunktionskoeffizient();
	public void setZielfunktionskoeffizient(Object zielfunktionskoeffizient);
	
	public boolean isOptimized();
	public void setOptimized(boolean isOptimized);
	
	public int getNumberOfIterates();
	public void setNumberOfIterates(int numberOfIterates);
	
	public TableauDTO convertDTO();
	

}
