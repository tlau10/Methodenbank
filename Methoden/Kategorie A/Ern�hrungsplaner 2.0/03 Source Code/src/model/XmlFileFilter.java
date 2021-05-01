package model;

import java.io.*;

/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: XmlFileFilter
 * </p>
 * <p>
 * Description: Überprüft, ob eine Datei auch wirklich eine XML-Datei ist
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

public class XmlFileFilter extends javax.swing.filechooser.FileFilter {

	//Hier wird sichergestellt, dass nur Verzeichnisse und .xml Dateien im FileChooser sichtbar sind.
	public boolean accept(File pathname) {
		if (pathname.getName().endsWith(".xml") || pathname.isDirectory())
			return true;
		return false;
	}


	//Liefert den Text für einen Eintrag in einer Combo-Box im FileChooser.
	public String getDescription() {
		return ".xml Dateien";
	}
}
