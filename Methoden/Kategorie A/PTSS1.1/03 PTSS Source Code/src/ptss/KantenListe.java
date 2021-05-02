package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:Verwaltet Kanten</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */


import java.util.*;

public class KantenListe {

   private Vector kList = new Vector();
   private KnotenListe knotenListe;
   private int kId = -1;
   private int marked = -1;
   private Kante lnkKante;
   private int iName = 0;

  public KantenListe( KnotenListe kl) {
    knotenListe = kl;
  }

  public void setKnotenListe(KnotenListe kl){
    knotenListe = kl;
  }

  public int addKante (int a, int b, int gew){
    if (kList == null)
       kList = new Vector();
    int i = kList.size();
    Kante tmp = new Kante(a,b,gew,gew, "Kante"+(iName),iName);
    kList.add(tmp);
    iName++;
    return iName-1;
  }

  public int  markKante(int x, int y) {
    Kante t_id = getKanteByPos(x,y);
    if ( t_id != null){
      if (marked > -1)
        unmarkKante(marked); // nur ein Kante kann markiert sein
      t_id.setMarked(true); // markiere Kante
      marked = t_id.getId();
      return t_id.getId();
    }
    return -1;
  }

  public void  markKante(int id) {
    unmarkKante(marked); // nur ein Kante kann markiert sein
    Kante t_id = getKanteById(id);
    if ( t_id != null){
      if (marked > -1)
        unmarkKante(marked); // nur ein Kante kann markiert sein
      t_id.setMarked(true); // markiere Kante
      marked = t_id.getId();
    }

  }
  public void unmarkKante( int id){
    Kante tmpKante = this.getKanteById(id);
    if (tmpKante != null)
      tmpKante.setMarked(false);
  }

  public synchronized Iterator iterator () {

    Iterator i = kList.listIterator();
    return i;

  }

  public int getKanteIdByKnotenId(int a, int b){
    Kante tmpKante;

    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKante = (Kante) iter.next();
      if ( (tmpKante.getKnotenA() == a ) && (tmpKante.getKnotenB() == b) )
        return tmpKante.getId();
      if ( (tmpKante.getKnotenA() == b ) && (tmpKante.getKnotenB() == a) )
        return tmpKante.getId();
    }
    return -1;
  }

  public boolean existsKnotenRef(int KnotenId){
    Kante tmpKante;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKante = (Kante) iter.next();
      if ( (tmpKante.getKnotenA() == KnotenId ) || (tmpKante.getKnotenB() == KnotenId) )
        return true;
    }
    return false;
  }

  public Kante getKanteByPos(int x1, int y1) {

    double c,m,y,diff;
    Knoten a = null, b=null;
    Kante tmpKante;
    for (Iterator iter = iterator(); iter.hasNext(); ) {
      tmpKante = (Kante) iter.next();
      a = knotenListe.getKnotenById(tmpKante.getKnotenA());
      b = knotenListe.getKnotenById(tmpKante.getKnotenB());

      while (true) {
        // Wertebereich prüfen
        if (a.getX() < b.getX()) {
          if ((x1<a.getX()) || (x1>b.getX()))
            break;
        }
        else {
          if ((x1>a.getX()) || (x1<b.getX()))
            break;
        }
        if (a.getY() < b.getY()) {
          if ((y1<a.getY()) || (y1>b.getY()))
            break;
        }
        else {
          if ((y1>a.getY()) || (y1<b.getY()))
            break;
        }
        // innerhalb des Wertebereiches prüfen ob der Klick auf der Kante lag
        m=((double)a.getY()-(double)b.getY())/((double)a.getX()-(double)b.getX());
        c=(-1.0)*((double)m*(double)a.getX())+(double)a.getY();
        y=((double)m*(double)x1)+(double)c;
        diff=y-y1;

        if (diff <= 0)
          diff=(double)diff*(double)(-1.0);
        if (diff <=10.0) {
          return tmpKante;
        }
        break;
      } // while true
    }//for
    return null; // Kante nicht erkannt
  }

  public Kante getKanteById (int id){
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      Kante tmpKante = (Kante) iter.next();
      if (tmpKante.getId() == id)
        return (tmpKante);
    }
    return null;
  }

  public void deleteKante(int kiD){

    try{
      for (Iterator iter = iterator(); iter.hasNext(); ) {
        Kante tmpKante = (Kante) iter.next();
        if (tmpKante.getId() == kiD){
          kList.remove(tmpKante);
          marked = -1;
        }
      }
      }catch (ConcurrentModificationException c){ }
  }

  public int getSize(){
    return kList.size();
  }

  public void clearPath(){
    Kante tmpKante;
    // alle Pfad Markierungen löschen
    // alle temporären Markierungen löschen
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKante = (Kante) iter.next();
      tmpKante.setPath(false);
      tmpKante.setMarked(false);
    }
  }

  public void  updateAktKapa(int id, int i) { // für simu
     Kante t_id = getKanteById(id);
     if ( t_id != null){
           t_id.updateaktKapa(i); // nur ein Kante kann markiert sein
    }
  }

  private void setKantenListe(Vector k){
    kList = k;
  }
  public void clearKapa(){
     Kante tmpKante;
     for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
       tmpKante = (Kante) iter.next();
       tmpKante.setaktKapa(0);
     }
  }

}