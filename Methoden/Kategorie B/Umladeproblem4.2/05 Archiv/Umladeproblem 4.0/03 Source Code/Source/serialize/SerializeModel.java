package serialize;

import gui.Modell;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


//Klasse wurde von den Studierenden erstellt
public class SerializeModel {
	
	
	public void marshallModell(DTOModell dto, String savePath) {

		try{
			
			JAXBContext context = JAXBContext.newInstance(dto.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			FileWriter writer = new FileWriter(savePath);
			marshaller.marshal(dto, writer);

		}catch(JAXBException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public DTOModell unmarshallModell(String path){
		DTOModell modell = null;
		
		try{
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(DTOModell.class);
	 
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			modell = (DTOModell) jaxbUnmarshaller.unmarshal(file);
			
		}catch(JAXBException e){
			e.printStackTrace();
		}catch (Exception e2) {
			e2.printStackTrace();
		}
			
		return modell;

	}

}
