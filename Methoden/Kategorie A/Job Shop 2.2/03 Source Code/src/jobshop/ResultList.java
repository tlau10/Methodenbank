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

import java.util.List;

/**
 *  Collection of calculated results with number of machines/products and processing time. 
 */
public class ResultList {
    public int anzM, anzP, maxDauer;
    public List<Result> result;

    public ResultList(int anzM, int anzP, int maxDauer, List<Result> result) {
	this.anzM = anzM;
	this.anzP = anzP;
	this.maxDauer = maxDauer;
	this.result = result;
    }
}
