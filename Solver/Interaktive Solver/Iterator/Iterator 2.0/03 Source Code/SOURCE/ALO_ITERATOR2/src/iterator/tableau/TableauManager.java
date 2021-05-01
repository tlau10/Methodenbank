package iterator.tableau;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class TableauManager {
	
	private static Logger logger = Logger.getLogger(TableauManager.class);
	
	private static TableauManager tableauManager = null;
	private HashMap<Integer, ITableau> tableaus = new HashMap<Integer, ITableau>();
	public static int lastId = 0;
	
	private TableauManager(){
		
	}
	
	public static TableauManager getInstance(){
		if(tableauManager == null){
			tableauManager = new TableauManager();
		}
		return tableauManager;
	}


	
	
	public void createTableau(ITableau tableau){
		tableau.setId(lastId++);
		this.addTableau(tableau);
	}
	
	public void addTableau(ITableau tableau){
		if(tableau != null){
			tableaus.put(tableau.getId(), tableau);
		} else {
			logger.error("ADD TABLEAU FAILED: tableau is null!");
		}
	}
	
	public void deleteTableau(int id){
		tableaus.remove(id);
	}
	
	public ITableau getTableauById(int id){
		return tableaus.get(id);
	}
	
	public Collection<ITableau> getAllTableaus(){
		return this.tableaus.values();
		
	}
	
	
	
}
