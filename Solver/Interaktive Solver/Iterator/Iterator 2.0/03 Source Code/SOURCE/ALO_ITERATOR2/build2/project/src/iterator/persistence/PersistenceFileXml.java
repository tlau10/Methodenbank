package iterator.persistence;

import iterator.tableau.TableauDTO;
import iterator.tableau.TableauFunctions;
import iterator.tableau.TableauManager;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class PersistenceFileXml implements IPersistence{
	
	private static Logger logger = Logger.getLogger(PersistenceFileXml.class);
	
	@Override
	public void save(TableauDTO tableau, String path) {
	
		try {
			
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(PersistenceFileXmlRoot.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(new PersistenceFileXmlRoot(tableau), file);
			logger.info("  >> File writed!");
			
		} catch (Exception e) {
			logger.error("FILE WRITER (XML): "+ e.getClass().getSimpleName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		

	}

	@Override
	public TableauDTO load(String path) {
		PersistenceFileXmlRoot root = null;
		TableauDTO tableau = null;
		
		if(!PersistenceFunctions.checkExtension(path, "xml")){
			TableauDTO oldTableau = PersistenceFunctions.loadOldFile(path);
			
			if(oldTableau != null){
				oldTableau.setId(TableauManager.lastId + 1);
				TableauManager.lastId++;
				logger.info(oldTableau.toString());
				return oldTableau;
			}
			
			logger.error("  >> path is not a valid XML!");
			return null;
		}
		

		try {
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(PersistenceFileXmlRoot.class);
	 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			root = (PersistenceFileXmlRoot) jaxbUnmarshaller.unmarshal(file);
			
			if(root!= null){
						tableau = root.getTableau();
						tableau.setId(TableauManager.lastId + 1);
						TableauManager.lastId++;
			}
			
			logger.info("  >> File readed! "+root.getHeader().getCreatedVersion() + ", " + root.getHeader().getCreatedVersion() + ", " + root.getHeader().getCreatedUser());
			logger.info("\r\n" + TableauFunctions.tableauToString(tableau));
		} catch (Exception e) {
			logger.error("FILE READER (XML): " + e.getMessage());
		}

		return tableau;
	}

}
