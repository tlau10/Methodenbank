package jobshop;

import java.util.List;

/*
 * Sammlung der gefundenen Ergebnisse mit der Anzahl Maschinen/Produkte und der
 * maximalen Dauer.
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
