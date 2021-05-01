package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import view.ErrorMessages;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: XmlListHandler
 * </p>
 * <p>
 * Description: Auslesen und Schreiben in die XML-Files in denen die
 * Nahrungsmittel gespeichert sind
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 (Refactoring)
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */

public class XmlListHandler {
	private Document doc_;
	private Element root_;
	private String xmlFilename;

	// Öffnet die XML-Datei
	public XmlListHandler(String xmlFilename) {
		SAXBuilder saxBuilder = new SAXBuilder();
		this.xmlFilename = xmlFilename;
		try {
			File xmlFile = new File(xmlFilename);
			doc_ = saxBuilder.build(xmlFile);
			root_ = doc_.getRootElement();
		} catch (Exception e) {
			ErrorMessages
					.throwErrorMessage("XML-Datei konnte nicht eingelesen werden");
		}
	}

	// Gibt die Liste der Nahrungsmittel zurück
	public List<?> getList() {
		return root_.getChildren();
	}

	// Löscht ein Nahrungsmittel aus der XML
	public boolean deleteElement(Element element) {
		boolean result = root_.removeContent(element);
		try {
			saveXmlList(xmlFilename);
		} catch (Exception e) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Speichern der Nahrungsmittel XML-Datei");
			result = false;
		}
		return result;
	}

	// Fügt ein Nahrungsmittel zur XML-Datei hinzu
	public void addElement(Element element) {
		root_.addContent(element);
		root_.addContent("\n");
		try {
			saveXmlList(xmlFilename);
		} catch (Exception e) {
			ErrorMessages
					.throwErrorMessage("Fehler beim Speichern der Nahrungsmittel XML-Datei");
		}
	}

	// Speichert die XML-Datei
	public void saveXmlList(String filename) throws Exception {
		try {
			File outputFile = new File(filename);
			OutputStreamWriter wout = new OutputStreamWriter(
					new FileOutputStream(outputFile), "UTF-8");
			XMLOutputter outputter = new XMLOutputter();

			outputter.setTextTrim(true);
			outputter.setIndent(" ");
			outputter.setNewlines(true);
			outputter.setLineSeparator(System.getProperty("line.separator"));

			outputter.output(doc_, wout);
			wout.close();
		} catch (IOException e) {
			throw e;
		}
	}
}