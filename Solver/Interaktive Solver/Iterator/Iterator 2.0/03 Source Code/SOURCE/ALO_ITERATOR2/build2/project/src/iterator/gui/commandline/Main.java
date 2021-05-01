package iterator.gui.commandline;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import iterator.Fascade;
import iterator.tableau.TableauCellPosition;
import iterator.tableau.TableauDTO;
import iterator.tableau.TableauFunctions;

public class Main {

	/**
	 * Simple Test Class ---> testing the basic iterator functions!
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		Logger.getRootLogger().removeAppender(Logger.getRootLogger().getAppender("javaFX"));
		Logger.getRootLogger().setLevel(Level.DEBUG);
		
		Fascade fascade = Fascade.getInstance();
		
		fascade.createTableau(3,4);
		
		fascade.getTableauById(0);
		
		
		TableauDTO tab = fascade.load("C:\\temp\\test.xml");

		//System.out.println(fascade.pivotById(tab.getId()));

		int z = 0;
		//optimize
		while(!tab.isOptimized()){
			System.err.println(++z);
			TableauCellPosition pos = fascade.pivotById(tab.getId());
			tab = fascade.iterateById(tab.getId(), pos.getRow(), pos.getColumn());
			fascade.updateTableau(tab);
			
			System.err.println(TableauFunctions.tableauToString(fascade.getTableauById(tab.getId())));
			
			if(tab.isOptimized()){
				System.err.println("optimized!");
			}
			
		}

	}

}
