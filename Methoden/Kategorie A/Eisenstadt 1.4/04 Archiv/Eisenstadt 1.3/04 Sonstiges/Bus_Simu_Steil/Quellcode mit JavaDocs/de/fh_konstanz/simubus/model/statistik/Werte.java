package de.fh_konstanz.simubus.model.statistik;

public class Werte {
	private double durchschnitt;
	private int maximum;
	private int minimum;
	
	public double getDurchschnitt() {
		return durchschnitt;
	}
	public void setDurchschnitt(double durchschnitt) {
		this.durchschnitt = durchschnitt;
	}
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public int getMinimum() {
		return minimum;
	}
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
}