package de.htwg_konstanz.tpsolver.web.metadata;

public class MetaDataSolver {

	String xml = "";
	
	MetaDataService_Service metaService = new MetaDataService_Service(); 
    MetaDataService webService = metaService.getMetaDataServicePort(); 
    String solversXml = webService.getAdequateSolvers(xml);
}
