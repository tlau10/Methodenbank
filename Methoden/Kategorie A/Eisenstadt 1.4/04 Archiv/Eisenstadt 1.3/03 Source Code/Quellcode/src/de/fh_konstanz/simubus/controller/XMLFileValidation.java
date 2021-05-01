package de.fh_konstanz.simubus.controller;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import de.fh_konstanz.simubus.util.Logger;

/**
 * Die Klasse <code>XMLFileValidation</code> validiert ein XML File, anhand einer XSD
 * 
 * @author Johannes Frei, Dominik Heller, Daniel Merkle
 * @version 1.0
 * 
 */
public class XMLFileValidation {

	/**
	 * Constructor
	 */
	public XMLFileValidation(){
	}
	
	/**
	 * validateIt,
	 * validiert ein XML- File anhand seines Schemas
	 * 
	 * @param filename, XML- File
	 * @param xsd, Schema für das XML- File
	 * @return
	 */
	public boolean validateIt(String filename,String xsd){
		Logger.getInstance().log("XML- File "+filename+" wird validiert!");
		boolean returnValue = false;

		try {
			File file = new File(filename);

            // Create a SchemaFactory capable of understanding WXS schemas.
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Load a WXS schema, represented by a Schema instance.
            //Source schemaFile = new StreamSource(new File(xsd));
            Source schemaFile = new StreamSource(new File(xsd));
            Schema schema = factory.newSchema(schemaFile);

            // Create a Validator object, which can be used to validate
            // an instance document.
            Validator validator = schema.newValidator();
            
            Source source = new StreamSource(file);

            // Validate the DOM tree.
            validator.validate(source);
            returnValue = true;

        } catch (Exception e) {
        	returnValue = false;
        }       
		
		return returnValue;		
	}

}
