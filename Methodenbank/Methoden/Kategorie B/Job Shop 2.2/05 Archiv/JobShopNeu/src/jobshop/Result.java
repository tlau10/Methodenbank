package jobshop;

/*
 * Stellt ein gefundenes Ergebnis dar.
 */
public class Result {
    public int zeitpunkt, maschine, produkt;

    public Result(int zeitpunkt, int maschine, int produkt) {
	this.zeitpunkt = zeitpunkt;
	this.maschine = maschine;
	this.produkt = produkt;
    }
}
