package iterator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import iterator.logic.ILogic;
import iterator.persistence.IPersistence;

import iterator.tableau.*;


public class Fascade {
	
	private static Logger logger = Logger.getLogger(Fascade.class);
	
	private static Fascade fascade = null;
	
	private TableauManager tableauManager = TableauManager.getInstance();
	private ILogic logic;
	private IPersistence persistence;
	
	private static Properties iteratorProps = new Properties();
	
	private Fascade(){
		
	}
	
	@SuppressWarnings("resource")
	public static Fascade getInstance(){
		if(fascade == null){
			logger.info("\r\n"+
						"*****************************************************************************\r\n\r\n"+
						//"	_ ___ ____ ____ ____ ___ ____ ____								\r\n" +
						//"	|  |  |___ |__/ |__|  |  |  | |__/ 								\r\n" +
						//"	|  |  |___ |  \\|  |  |  |__| |  \\ 2013						\r\n\r\n" +
						"        I T E R A T O R  2013\r\n\r\n" +
						"	Hochschule für Technik, Wirtschaft und Gestaltung Konstanz		\r\n" +
						"	Prof. Dr. Grütz | Anwendungen der linearen Optimierung 			\r\n\r\n" +
						"	Version:	1.0 \r\n" +
						"	Erstellt: 	Sommersemester 2013 \r\n" +
						"			Thomas Winter / Florian Kurz\r\n\r\n" +
						"****************************************************************************\r\n");
			
			
			logger.info(">> ITERATOR STARTED AT: " + new Date());
			logger.info("Generating Fascade using Spring-Framework 'META-INF/bean.xml'...");
			
			BeanFactory factory = new ClassPathXmlApplicationContext("META-INF/bean.xml");
			fascade = (Fascade) factory.getBean("fascade");
		
			
			logger.info("USED LOGIC: " + fascade.logic.getClass().getName());
			logger.info("USED PERSISTENCE: " + fascade.persistence.getClass().getName());
			logger.info("Fascade gererated!");
			logger.info("----------------------------------------------------------------");
			try {
				iteratorProps.load(new FileInputStream("config/iterator.properties"));
			} catch (Exception e) {
				logger.error(e);
			}
		} 
		return fascade;
	}
	
	//-------------------------------------------------------------------------
	// Spring action
	
	public void setLogic(ILogic logic) {
		this.logic = logic;
	}

	public void setPersistence(IPersistence persistence) {
		this.persistence = persistence;
	}
	
	//--------------------------------------------------------------------------
	// Tableau Management
	
	public TableauDTO createTableau(int variables, int restrictions){
		TableauRational newTableau = new TableauRational(variables, restrictions);
		tableauManager.addTableau(newTableau);
		
		logger.info("CREATING TABLEAU ("+ newTableau.getId() +"): Variables("+variables+"), Restrictions(" + restrictions +")");
		return newTableau.convertDTO();
	}
	
	public void deleteTableauById(int tableauId){
		logger.info("DELETEING TABLEAU (" + tableauId + ")");
		tableauManager.deleteTableau(tableauId);
	}
	
	public TableauDTO getTableauById(int id){
		logger.info("GET TABLEAU (" + id +")");
		return tableauManager.getTableauById(id).convertDTO();
	}
	
	public void updateTableau(TableauDTO dto){
		tableauManager.deleteTableau(dto.getId());
		tableauManager.addTableau(new TableauRational(dto));
	}
	
	public void addRestrictionToId(int id){
		tableauManager.getTableauById(id).addRestriction();
	}
	
	public void addVariableToId(int id){
		tableauManager.getTableauById(id).addVariable();
	}
	
	public void removeRestrictionFromId(int id){
		tableauManager.getTableauById(id).removeRestriction();
	}
	
	public void removeVariableFromId(int id){
		tableauManager.getTableauById(id).removeVariable();
	}
	
	
	//----------------------------------------------------------------
	// Logical operations
	
	public TableauCellPosition pivotById(int id) throws Exception{
		logger.info("PIVOT AU (" + id +")");
		return logic.pivot(tableauManager.getTableauById(id));
	}
	
	public TableauDTO iterateById(int id, int row, int column) throws Exception{
		logger.info("ITERATE TABLEAU (" + id +")");
		return logic.iterate(tableauManager.getTableauById(id), row, column).convertDTO();
	}
	
	

	//------------------------------------------------------------------
	// Persistence operations
		
	public void save(int tableauId, String path){
		logger.info("SAVE TABLEAU (" + tableauId +"): Path:" + path);
		persistence.save(tableauManager.getTableauById(tableauId).convertDTO(), path);
	}
	
	public TableauDTO load(String path){
		logger.info("LOADING TABLEAU: Path:" + path);
		TableauDTO loadedTableau = null;
		try{
			
			loadedTableau = (TableauDTO) persistence.load(path);
			
			if(loadedTableau != null){
				tableauManager.addTableau(new TableauDouble(loadedTableau));
				logger.debug("  >> loaded Tableau: " + loadedTableau);
			}
			
		} catch(Exception e){
			logger.error(e);
		}
		
		return loadedTableau;
	}
	
	//------------------------------------------------------------------
	// Messagehandling
	public String getMessage(String name){
		return MessageHandler.getInstance().getMessage(name);
	}
	
	//------------------------------------------------------------------
	// Config
	public String getConfigProperty(String property){
		return iteratorProps.getProperty(property);
	}

	

	
}
