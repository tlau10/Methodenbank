package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:DatenKlasse Kante</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */

import javax.swing.*;
import java.util.*;


public class Kante {

  private int knotenA ,knotenB, iD,GewA,GewB;
  private boolean isMarked=false;
  private String Name;
  private boolean isPath = false;
  private int aktKapa = 0; // für Simu
  private int maxKapa = 0; // für simu
  public int greatestKapa = 0;
  public int gesamtEvents = 0;
//  public Vector gesamt = new Vector();
  public int gesamt;

  public Kante(int a, int b, int gew_A, int gew_B,String s, int i) {
    knotenA = a;
    knotenB = b;
    GewA = gew_A; GewB =gew_B;
    Name = s;
    iD = i;
  }

  public int getKnotenA() {
    return knotenA;
  }

  public int getKnotenB() {
    return knotenB;
  }

  public void setMarked(boolean m) {
    isMarked = m;
  }

  public boolean isMarked(){
    return isMarked;
  }

  public String getName() {
    return Name;
  }

  public int abs(int temp) {
    if (temp < 0) temp = temp * (-1);
    return temp;
  }

  public int getId (){
    return iD;
  }

  public void setId(int i) {
    iD = i;
  }
  public void setKnotenA (int i){
    knotenA = i;
  }
  public void setKnotenB (int i){
    knotenB = i;
  }
  public void updateKnotenA (int i){
    knotenA -= i;
    if (knotenA < 0)
      knotenA =0;
   }
   public void updateKnotenB (int i){
     knotenB -= i;
     if (knotenB < 0)
      knotenB = 0;
  }
  public int getGewA(){
    return GewA;
  }
  public void setGewB(int i){
    GewB = i;
  }
  public void setGewA(int i){
    GewA = i;
  }
  public int getGewB(){
    return GewB;
  }
  public void setName(String s){
    Name = s;
  }
  public void setPath(boolean m) {
    isPath = m;
//    System.err.println("Kante ID:" + iD + " bin getroffen");
  }
  public boolean isPfad(){
    return isPath;
  }
  public int getaktKapa(){ // für simu
    return aktKapa;
  }
  public void setaktKapa(int i){
    aktKapa = i;
  }

  public void updateaktKapa(int i){ // für simu
    aktKapa += i;
    if(i >= 0)
      gesamtEvents++;
    gesamt += aktKapa;
    if(aktKapa > greatestKapa)
      greatestKapa = aktKapa;
    //   System.out.println("update Kante"+iD+ "Kapa: "+aktKapa);
  }
}

