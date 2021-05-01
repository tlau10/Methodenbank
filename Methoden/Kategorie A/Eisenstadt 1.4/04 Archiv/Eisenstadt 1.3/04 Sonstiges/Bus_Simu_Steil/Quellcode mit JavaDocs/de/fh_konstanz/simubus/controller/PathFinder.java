/*
 * Created on 10.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.fh_konstanz.simubus.controller;

import java.awt.Point;
import java.util.ArrayList;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.view.Field;

/**
 * @author Robert Audersetz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PathFinder {
	
	private Field [][] field;
	private Field current;
	private int anzahlFelder;
	
	private Point start;
	private Point end;
	
	private ArrayList<Field> aSuccessorList;
	private ArrayList<Field> aOpenList;
	private ArrayList<Field> aCloseList;	

	private int fTemp;
	private int gTemp;
	private int hTemp;
	
	
	public PathFinder(Field[][] field, int anzahlFelder) {
		this.field = field;
		this.anzahlFelder = anzahlFelder;
		
		aSuccessorList = new ArrayList<Field>();
		aOpenList = new ArrayList<Field>();
		aCloseList = new ArrayList<Field>();
	}
	
		
	public void setStartAndZiel(Point start, Point ziel) {
		this.start = start;
		this.end = ziel;
	}
	
	
	public int startSearch() throws Exception{
		
		reset();
		
		// Initialisieren
		current = field[start.x][start.y];
		gTemp = 0;
		hTemp = getH(start.x, start.y);
		fTemp = getF(gTemp, hTemp);
		current.setValues(fTemp, gTemp, hTemp);
		aOpenList.add(current);
		
		boolean found = false;
		Field successor;

		while (!aOpenList.isEmpty() && !found) {
			current = getNextF();
			aOpenList.remove(current);			
			
			generateSuccessorList();	
	
			for (int loop=0; loop<aSuccessorList.size(); loop++){			
				successor = (Field) aSuccessorList.get(loop);

				hTemp = getH(successor.x, successor.y);
				gTemp = getG(successor.x, successor.y);
				fTemp = getF(gTemp, hTemp);
				
				if (successor.x == end.x && successor.y == end.y) {
					found = true;
					successor.father = current;
					successor.setValues(fTemp, gTemp, hTemp);
					break;
				}
				
				if (successor.x == start.x && successor.y == start.y)
					continue;

				if (aOpenList.contains(successor)) {
					if (fTemp >= successor.f) {
						continue;
					}
				}

				if (aCloseList.contains(successor)) {
					if (fTemp >= successor.f) {
						continue;
					}							
				}
				
				successor.father = current;
				successor.setValues(fTemp, gTemp, hTemp);
				aOpenList.add(successor);					
			}						
			
			aCloseList.add(current);
		}

		return gTemp;
	}

	
	// Nächst kleineres F in der OpenListe suchen
	private Field getNextF() {		
		
		int min = ((Field) aOpenList.get(0)).f;
		int tmp;
		Field tmpField;
		Field next = (Field) aOpenList.get(0);
		
		if (aOpenList.size() > 1) {
			for (int i=1; i<aOpenList.size(); i++) {
				tmpField = (Field) aOpenList.get(i);
				tmp = tmpField.f;
				if (tmp < min) {
					min = tmp;
					next = tmpField;
				}
			}
		}		
		return next;
	}
	
	// Umliegende Punkte vom aktuellen Punkt suchen und in die entsprechende Liste einfügen
	private void generateSuccessorList() {		
		
		aSuccessorList.clear();
		if (current.x > 0 && current.x<anzahlFelder && current.y > 0 && current.y<anzahlFelder) {
			
			for (int y=current.y-1; y<=current.y+1; y++){
				for (int x=current.x-1; x<=current.x+1; x++){
					
					if (x == current.x && current.y == y)
						continue;
					else if (x < 0 || y < 0)
						continue;
					else if (!field[x][y].isStreet)
						continue;
					else if (!aCloseList.contains(field[x][y]))
						aSuccessorList.add(field[x][y]);
				}
			}
			
			if (aSuccessorList.contains(field[current.x][current.y-1])) {
//				field[current.x][current.y-1].rotation = 0;
				aSuccessorList.remove(field[current.x-1][current.y-1]);
				aSuccessorList.remove(field[current.x+1][current.y-1]);
			}
			if (aSuccessorList.contains(field[current.x][current.y+1])) {
//				field[current.x][current.y+1].rotation = 0;
				aSuccessorList.remove(field[current.x-1][current.y+1]);
				aSuccessorList.remove(field[current.x+1][current.y+1]);				
			}
			if (aSuccessorList.contains(field[current.x-1][current.y])) {
//				field[current.x-1][current.y].rotation = 0;
				aSuccessorList.remove(field[current.x-1][current.y-1]);
				aSuccessorList.remove(field[current.x-1][current.y+1]);
			}
			if (aSuccessorList.contains(field[current.x+1][current.y])) {
//				field[current.x+1][current.y].rotation = 0;
				aSuccessorList.remove(field[current.x+1][current.y-1]);
				aSuccessorList.remove(field[current.x+1][current.y+1]);
			}
			if (aSuccessorList.contains(field[current.x+1][current.y+1])) {
//				field[current.x+1][current.y+1].rotation = 135;
			}
			if (aSuccessorList.contains(field[current.x+1][current.y-1])) {
//				field[current.x+1][current.y-1].rotation = 45;
			}
			if (aSuccessorList.contains(field[current.x-1][current.y+1])) {
//				field[current.x-1][current.y+1].rotation = 135;
			}
			if (aSuccessorList.contains(field[current.x-1][current.y-1])) {
//				field[current.x-1][current.y-1].rotation = 45;
			}
		}
	}

	// Berechnungen von F, G und H
	private int getF(int g, int h) {
		return g + h;
	}
	
	private int getG(int x, int y) {
		if ((Math.abs(x-current.x) - Math.abs(y-current.y)) == 0)
			return 14 + current.g;
		else
			return 10 + current.g;
	}

	private int getH(int x, int y) {
		return ((Math.abs(x-end.x) + Math.abs(y-end.y)))*10;
	}
	
	// Speichert den optimalen Pfad der übergebenen Buslinie in einer ArrayList und übergibt diese dem Linien-Objekt
	public void buildPath(Buslinie linie) {
		Field father = field[end.x][end.y];
		ArrayList<Point> pfad = new ArrayList<Point>();
		
		while (true) {
			if (father.x == start.x && father.y == start.y)
				break;
			pfad.add(0, new Point(father.x, father.y));
			father = father.father;
		}

		linie.setPfad(pfad);
	}
	
	
	private void reset() {
		current = null;
		aOpenList.clear();
		aCloseList.clear();
		aSuccessorList.clear();
	}

}
