package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:verwaltet Knoten</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */


import java.util.*;

public class KnotenListe {

  private Vector kList = new Vector();
  private int kId = -1;
  private int marked = -1;
  private boolean enum_var = false;
  private Enumeration e;
  private Knoten lnkKnoten;
  private int iName =0;

  public int addKnoten (int x, int y){

    int i = kList.size();
    Knoten tmp = new Knoten(x,y, ("Knoten"+(iName)), iName);
    kList.add(tmp);
    iName++;
    return iName-1;
  }

  public int getKnotenByName( String n){

    Knoten tmpK;
    int i = 0;
    while(i+1 <= kList.size()){
      tmpK =(Knoten) kList.get(i);
      if( n.compareTo(tmpK.getName()) == 0)
        return tmpK.getId();
      i++;
    }
    return -1;
  }

  public Knoten getKnotenByPos(int x, int y){

    for (int i=0;i<kList.size();i++) {
      if ((((Knoten)kList.get(i)).isInPos(x,y)==true))
        return (Knoten) kList.get(i);
    }
    return null;
  }

  public boolean  markKnoten(int x, int y) {
    Knoten t_id = getKnotenByPos(x,y);
    if ( t_id != null){
      if (marked > -1)
        unmarkKnoten(marked); // nur ein Knoten kann markiert sein
      t_id.setMarked(true); // markiere Knoten
      marked = t_id.getId();
      return true;
    }
    return false;
  }

  public void  markKnoten(int id) {
    Knoten tmp;
    if (marked > -1)
      unmarkKnoten(marked); // nur ein Knoten kann markiert sein
    tmp = getKnotenById(id);
    if (tmp != null)
      tmp.setMarked(true);
    marked = id;
  }

  public void unmarkKnoten( int id){
    Knoten tmpknoten = getKnotenById(id);
    if (tmpknoten != null)
      tmpknoten.setMarked(false);
  }

  public synchronized Iterator iterator () {
    Iterator i = kList.listIterator();
    return i;
  }

  public Knoten getKnotenById (int i){
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      Knoten tmpKnoten = (Knoten) iter.next();
      if (tmpKnoten.getId() == i)
        return (tmpKnoten);
    }
    return null;
  }

  public void updateCoord(int id, int x, int y) {
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      Knoten tmpKnoten = (Knoten) iter.next();
      if (tmpKnoten.getId() == id)
        tmpKnoten.setPos(x,y);
    }
  }

  public void deleteKnoten(int kId){

    try{
      for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
        Knoten tmpKnoten = (Knoten) iter.next();
        if (tmpKnoten.getId() == kId)
          kList.remove(tmpKnoten);
        marked = -1;
      }
    }catch (ConcurrentModificationException c){

    }

  }
  public String getName(int id){
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      Knoten tmpKnoten = (Knoten) iter.next();
        if (tmpKnoten.getId() == id)
          return tmpKnoten.getName();
    }
    return ("");
  }

  public int getSize(){
    return kList.size();
  }

  public void updateIds(){
    Knoten tmpK;
    int i = 0;
    while(i+1 <= kList.size()){
      tmpK =(Knoten) kList.get(i);
      tmpK.setId(i);
      i++;
    }
  }

  public synchronized void clearStart(){

    Knoten tmp;
    // alle Start knoten löschen
    Enumeration enum_var = kList.elements();
    while (enum_var.hasMoreElements()){
      tmp = (Knoten) enum_var.nextElement();
      tmp.unSetStart();
    }

  }
  public synchronized void clearZiel(){

    Knoten tmp;
    // alle Ziel knoten löschen
    Enumeration enum_var = kList.elements();
    while (enum_var.hasMoreElements()){
      tmp = (Knoten) enum_var.nextElement();
      tmp.unSetZiel();
    }
  }

  public boolean existsStart(){
    Knoten tmpKnoten;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      if ( tmpKnoten.isStart() )
        return true;
    }
    return false;
  }

  public int getStartKnotenId(){
    Knoten tmpKnoten;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      if ( tmpKnoten.isStart() )
        return tmpKnoten.getId();
    }
    return -1;
  }

  public synchronized void setStartKnoten(int KnotenId){
//    clearStart();
    Knoten tmpKnoten;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      if ( tmpKnoten.getId() ==  KnotenId ){
        tmpKnoten.setStart(  );
        return;
      }
    }
  }

  public boolean existsZiel(){
    Knoten tmpKnoten;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      if ( tmpKnoten.isZiel() )
        return true;
    }
    return false;
  }

  public int getZielKnotenId(){
    Knoten tmpKnoten;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      if ( tmpKnoten.isZiel() )
        return tmpKnoten.getId();
    }
    return -1;
  }

  public synchronized void setZielKnoten(int KnotenId){
 //   clearZiel();
    Knoten tmpKnoten;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      if ( tmpKnoten.getId() ==  KnotenId ){
        tmpKnoten.setZiel(  );
        return;
      }
    }
  }

  public void setSimuId(int KnotenId, int simuId){
//    clearStart();
    Knoten tmpKnoten;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      if ( tmpKnoten.getId() ==  KnotenId ){
        tmpKnoten.setSimuId(simuId);
        return;
      }
    }
  }
  public int getSimuId(int KnotenId){
//    clearStart();
    Knoten tmpKnoten;
    for (Iterator iter = kList.iterator(); iter.hasNext(); ) {
      tmpKnoten = (Knoten) iter.next();
      if ( tmpKnoten.getId() ==  KnotenId ){
        return tmpKnoten.getSimuId();
      }
    }
    return -1;
  }
  public synchronized void clearSimuId(){

     Knoten tmp;
     // alle Start knoten löschen
     Enumeration enum_var = kList.elements();
     while (enum_var.hasMoreElements()){
       tmp = (Knoten) enum_var.nextElement();
       tmp.setSimuId(-1);
       tmp.scheduledEvents =0;
     }

  }
}