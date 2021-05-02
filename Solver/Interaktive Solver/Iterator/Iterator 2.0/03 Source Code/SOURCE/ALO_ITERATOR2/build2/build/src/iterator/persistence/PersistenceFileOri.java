package iterator.persistence;

import iterator.tableau.TableauDouble;
import iterator.tableau.TableauDTO;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

public class PersistenceFileOri implements IPersistence{

	private static Logger logger = Logger.getLogger(PersistenceFileOri.class);
	
	@Override
	public void save(TableauDTO tableau, String path) {
		logger.error("SAVE OLD FILE: NOT IMPLEMENTED! - no action will be done!");
		
	}

	@Override
	public TableauDTO load(String path) {
		logger.info(" > LOAD FILE: ORI");
		
		TableauDouble tableau = null;
		int variables = 0;
		int restrictions = 0;
		
		try{
			  FileInputStream fstream = new FileInputStream(path);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;

			  int currentLine = 1;
			 
			  
			  int currentVariable = 0;
			  int currentRestriction = 0;
			  
			  while ((strLine = br.readLine()) != null)   {
				 
				  BigDecimal value =  new BigDecimal(strLine);
				  if(currentLine == 1){
					  restrictions = Integer.parseInt(strLine) - 1;
					  logger.debug("    - number of restrictions:" + restrictions);
				  } else if( currentLine == 2){
					  variables  = Integer.parseInt(strLine) - 1;
					  logger.debug("    - number of variables:" + variables);
				  } else {
					  if(tableau == null){
						  tableau = new TableauDouble(variables, restrictions);
						  tableau.setAValue(currentRestriction, currentVariable, value);
						  logger.debug("    >> A-Value: " + value);
						  currentVariable++;
					  } else {
						  if(currentRestriction < restrictions){
							  if(currentVariable < variables){
								  tableau.setAValue(currentRestriction,currentVariable, value);
								  logger.debug("    >> A-Value: " + value);
							  } else if(currentVariable == variables){
								  tableau.setBValue(currentRestriction, value);
								  logger.debug("    >> b-Value: " + value);
								  currentRestriction++;
							  }
						  } else if(currentRestriction == restrictions){
							  if(currentVariable < variables){
								  tableau.setZValue(currentVariable, value);
								  logger.debug("    >> z-Value: " + value);
							  } else {
								  tableau.setZielfunktionskoeffizient(value);
								  logger.debug("    >> ZF-Value: " + value);
							  }
						  }
						  
						  
						  currentVariable = (currentVariable < variables)? currentVariable + 1: 0;
					  }
					  
					  
				  }
				  
				  currentLine++;
			  }
			  //Close the input stream
			  in.close();
		}catch (Exception e){//Catch exception if any
			logger.error("   >>> file load failed! " + e.getClass().getName());
			e.printStackTrace();
		}
		
		
		
		return tableau.convertDTO();
	}

}
