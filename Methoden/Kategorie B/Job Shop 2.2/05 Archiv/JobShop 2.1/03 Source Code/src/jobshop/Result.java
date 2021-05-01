/*
 * Bernd Haertenstein
 * 
 * Copyright (c) 2011-2015 University of Applied Science Konstanz. All Rights Reserved.
 * 
 * Version 	1.0	WS10/11
 * Author	Bernd Haertenstein
 * 
 */
package jobshop;

/**
 *  Save calculated result. 
 */
public class Result {
    public int zeitpunkt;
    public int maschine;
    public int produkt;

    public Result(int zeitpunkt, int maschine, int produkt) {
		this.zeitpunkt = zeitpunkt;
		this.maschine = maschine;
		this.produkt = produkt;
    }
}
