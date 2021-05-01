package de.fh_konstanz.simubus.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import de.fh_konstanz.simubus.model.*;

public final class DateiManager {
	public static void speichern(File datei) throws IOException {
		Serialisierungsklasse ser = new Serialisierungsklasse();
		ser.setStrassennetz(Strassennetz.getInstance());
		ser.setGesamtfahrplan(Gesamtfahrplan.getInstance());
		ser.setSimuKonfiguration(SimuKonfiguration.getInstance());
		
		FileOutputStream fos = new FileOutputStream(datei);
		GZIPOutputStream gos = new GZIPOutputStream(fos);
		ObjectOutputStream oos = new ObjectOutputStream(gos);
		oos.writeObject(ser);
		oos.close();
	}
	
	public static void laden(File datei) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(datei);
		GZIPInputStream gis = new GZIPInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(gis);
		Serialisierungsklasse ser = (Serialisierungsklasse) ois.readObject();
		ois.close();
		
		Strassennetz.setInstance(ser.getStrassennetz());
		Gesamtfahrplan.setInstance(ser.getGesamtfahrplan());
		SimuKonfiguration.setInstance(ser.getSimuKonfiguration());
	}
}
