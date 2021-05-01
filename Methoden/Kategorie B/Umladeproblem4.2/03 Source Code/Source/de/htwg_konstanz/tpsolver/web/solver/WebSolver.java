package de.htwg_konstanz.tpsolver.web.solver;

import createLP.CreateLP;

//Klasse wurde von den Studierenden erstellt
public class WebSolver {

	public String getWebResult(String data){
		
				
		SolverService_Service service = new SolverService_Service();
		SolverService webService = service.getSolverServicePort();
		String resultXML = webService.processXml(data);
		
		return  resultXML;
	}
	
}
