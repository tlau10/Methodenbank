package iterator.logic;

import iterator.tableau.*;

public interface ILogic {
	
	public TableauCellPosition pivot(ITableau tableau) throws Exception;
	public ITableau iterate(ITableau tableau, int row, int column) throws Exception;
	
}
