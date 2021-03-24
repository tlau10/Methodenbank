package de.fh_konstanz.simubus.model;

import java.awt.Point;

public class GesperrteHaltestelle {

	   /**
	    * Koordinaten der gesperrten Haltestelle
	    */
	   private Point  koordinaten;

	   /**
	    * Name der gesperrten Haltestelle
	    */
	   private String name;

	   /**
	    * ID der Haltestelle
	    */
	   private int    id;

	public GesperrteHaltestelle(Point planquadratCoordinates, int id2) {
		this.koordinaten = planquadratCoordinates;
		this.id = id2;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the koordinaten
	 */
	public Point getKoordinaten() {
		return koordinaten;
	}

	/**
	 * @param koordinaten the koordinaten to set
	 */
	public void setKoordinaten(Point koordinaten) {
		this.koordinaten = koordinaten;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
