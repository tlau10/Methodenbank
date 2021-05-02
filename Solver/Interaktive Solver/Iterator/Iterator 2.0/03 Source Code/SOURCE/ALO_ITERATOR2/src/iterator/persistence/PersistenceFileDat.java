package iterator.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import iterator.tableau.*;

public class PersistenceFileDat implements IPersistence{

	private static Logger logger = Logger.getLogger(PersistenceFileXml.class);
	
	@Override
	public void save(TableauDTO tableau, String path) {

		FileOutputStream fos;
		
		try {
			
			fos = new FileOutputStream(PersistenceFunctions.parseExtension(path, "dat"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
				
			oos.writeObject(tableau);
			oos.close();
			logger.info("  >> File writed!");
			
		} catch (IOException e) {
			logger.error("FILE WRITER (DAT): " + e.getMessage());
		}
		

	}

	@Override
	public TableauDTO load(String path) {
		TableauDTO tableau = null;
		
		if(PersistenceFunctions.checkExtension(path, "dat")){
			logger.error("  >> path is not a valid DAT!");
			return null;
		}
		
		// Create input stream.
		  FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tableau = (TableauDTO) ois.readObject();
			ois.close();
			logger.info("  >> File readed!");
		} catch (ClassNotFoundException e) {
			logger.error("FILE READER (DAT): " + e.getMessage());
		} catch (IOException e) {
			logger.error("FILE READER (DAT): " + e.getMessage());
		}
	
		return tableau;
	}

}
