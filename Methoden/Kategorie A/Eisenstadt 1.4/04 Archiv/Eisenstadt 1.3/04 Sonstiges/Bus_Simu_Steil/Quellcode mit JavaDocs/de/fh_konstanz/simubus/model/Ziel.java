package de.fh_konstanz.simubus.model;

import java.awt.Point;
import java.io.Serializable;

public class Ziel implements Serializable{

	private Point point;
	private String zielName = null;
	
	public Ziel(Point point) {
		this.point = point;
	}
	
	public Ziel(Point point, String zielName) {
		this.point = point;
		this.zielName = zielName;
	}

	public Ziel(int x, int y) {
		point = new Point(x, y);
	}

	public Ziel(int x, int y, String zielName) {
		this.point = new Point(x, y);
		this.zielName = zielName;
	}
	
	public void setZielName(String zielName) {
		this.zielName = zielName;
	}
	
	public Point getZiel() {
		return point;
	}
	
	public int getX() {
		return point.x;
	}
	
	public int getY() {
		return point.y;
	}
	
	public String toString() {
		if (zielName == null)
			return "Ziel [" +point.x +", " +point.y +"]";
		
		return zielName;
	}
}
