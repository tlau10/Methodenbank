package ptss;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung:Manager Klasse für die Modellier Grafik</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Organisation: </p>
 * @author Mathias Jehle
 * @version 1.0
 */



import javax.swing.*;
import javax.swing.UIManager;
import java.awt.*;
import java.util.*;


public class ModellierManager {

  private KnotenListe knotenListe = new KnotenListe();
  private KantenListe kantenListe = new KantenListe(knotenListe);
  public int mouseX, mouseY;
  private int draggedKnoten = -1;
  private boolean mouseDown;
  public int tmpKanteX, tmpKanteY;
  private int markedKante =-1, markedKnoten= -1;
  private ModellierView mView;
  int buttonState = 1;
  private DialogKante diaKante = new DialogKante(knotenListe);
  private DialogKnoten diaKnoten = new DialogKnoten(knotenListe);
  private int zielKnoten, startKnoten = -1;
  private boolean demo = false;


  public ModellierManager( ModellierView view) {
    mView = view;
  }

  public ModellierManager( ) {
  }

  public void modellierViewMouseClicked(int x, int y) {

    boolean check = false; // marked knoten?
    check = knotenListe.markKnoten(x,y);

    if (check == true){ // markiere Knoten
      mouseX = x;
      mouseY = y;
      markedKnoten = knotenListe.getKnotenByPos(x,y).getId();
      kantenListe.unmarkKante(markedKante);
      markedKante = -1;
      updateText(knotenListe.getKnotenById(markedKnoten));
    }
    // Knoten  hinzufügen ...
    else if ( (knotenListe.getKnotenByPos( x, y) == null) && (buttonState == 1)) { //kein Knoten an dieser Stelle also neuer Knoten
      if (markedKante != -1){
        kantenListe.unmarkKante(markedKante);
        markedKante = -1;
      }
      markedKnoten = knotenListe.addKnoten(x,y );
      knotenListe.markKnoten(markedKnoten);
      updateText(knotenListe.getKnotenById(markedKnoten));
      mouseX = x; mouseY = y;
    }

    if (( buttonState == 3) || (buttonState == 4) ) { // MausAktion auf "markieren" gestellt
      check = knotenListe.markKnoten(x,y); // Suche Knoten
      if (check == true){ // markiere Knoten
        kantenListe.unmarkKante(markedKante);
        markedKante = -1;
        updateText((knotenListe.getKnotenByPos( x, y)));
        if (buttonState == 4)
          showKnotenDia(markedKnoten);
      }else{
        markedKante = kantenListe.markKante(x,y); // Suche Kante
        if (markedKante != -1){
          updateText(kantenListe.getKanteById(markedKante));
          if (buttonState == 4){
            showKantenDia(markedKante);
          }
        }
      }
    } // Ende markieren

    if (buttonState == 5)  // Element löschen
      deleteElement(x,y);
  }


  public void modellierViewMouseReleased(int x, int y) {

    Knoten tmpKnoten;
    mouseDown = false;
    if (tmpKanteX > -1) { // user gerade beim Kante zeichnen ...
      tmpKnoten = (knotenListe.getKnotenByPos(x, y));
      if ( tmpKnoten != null){ // überprüfen ob KantenEnde auf Knoten
        // ist diese Kante schon vorhanden ?
        if (( kantenListe.getKanteIdByKnotenId((knotenListe.getKnotenByPos(mouseX,mouseY).getId()),
               tmpKnoten.getId()) != -1) ){
        }
        else{ // neue Kante anmelden
          if ((knotenListe.getKnotenByPos(mouseX,mouseY) == null)) {
            JOptionPane.showMessageDialog(null, "Kein Ausgangs Knoten markiert");
          }
          else {
            Knoten tmpAktKnoten = knotenListe.getKnotenByPos(mouseX,mouseY);
            if (tmpAktKnoten.getId() != tmpKnoten.getId()){ // Start und Ziel Knoten dürfen nicht identisch sein

              int id = kantenListe.addKante(tmpAktKnoten.getId(),tmpKnoten.getId(),
                                            berechneDistanz(tmpAktKnoten,tmpKnoten));
              kantenListe.markKante(id);
              markedKante = id;
            }
          }
        }
      }
    }

    if (draggedKnoten != -1){ // user hat gerade einen knoten bewegt ... entfernung der
                              // betroffenen Kanten müssen neu errechnet werden.
      tmpKnoten = knotenListe.getKnotenById(draggedKnoten);
      Kante tmpKante;
      if (tmpKnoten != null){ // suche Kanten
        markedKnoten = -1;
        markedKante = -1;
 //       int i = 0;
 //       int j = 0;
        Iterator iter = kantenListe.iterator();
        while (iter.hasNext() ) {
          tmpKante = (Kante) iter.next();
          if( (tmpKante.getKnotenA() == tmpKnoten.getId()) || (tmpKante.getKnotenB() == tmpKnoten.getId()) ){
            // berechne Distanz
            int gew = berechneDistanz(knotenListe.getKnotenById(tmpKante.getKnotenA()),
                                      knotenListe.getKnotenById(tmpKante.getKnotenB()));
            // setzte Distanz
            tmpKante.setGewA(gew);
            tmpKante.setGewB(gew);
          }
        }
      }
    }
    draggedKnoten = -1;
    tmpKanteX = -1; tmpKanteY = -1; // tempKante löschen

  }

  public int berechneDistanz(Knoten k1, Knoten k2){

    // gewichtung der kanten nach pythagoras ausrechnen
    int tAX =  k1.getX();
    int tAY =  k1.getY();
    int tBX =  k2.getX();
    int tBY =  k2.getY();

    double a,b;

    if  (tAX <= tBX ) {
      a =tBX -tAX;
    }
    else{
      a = tAX-tBX;
    }

    if ( tAY <= tBY ) {
      b = tBY-tAY;
    }
    else{
      b = tAY-tBY;
    }

    // länge berechnen
    return( (int) Math.round(Math.sqrt(a*a+b*b)) );
  }

  void modellierViewMouseDragged(int x, int y) {

    if (mouseDown == false)
      if (knotenListe.getKnotenByPos(x, y) != null){
    draggedKnoten = knotenListe.getKnotenByPos(x, y).getId();
      }else{
      draggedKnoten = -1;}

      if (buttonState == 1){ // temporäre Kante zeichnen
        tmpKanteX = x;
        tmpKanteY = y;
      }
      if (buttonState == 2) { // Knoten bewegen
        if ( draggedKnoten != -1) { // Maus Fokus (x,y) ist auf Knoten
          mouseX =x;
          mouseY = y;
          knotenListe.updateCoord(draggedKnoten,mouseX,mouseY);
          updateText(knotenListe.getKnotenById(draggedKnoten));
        }
        mouseDown = true;
      }
  }

  public void updateText (Knoten k) {

    if (k != null)
      mView.updatePropertyText("Aktiver Knoten"+
                       '\n' + "ist " + k.getName()
                       +'\n' + "X Koordinate ist " +k.getX()
                       +'\n' + "Y Koordinate ist " +k.getY()
                       +"\n ID =: "+k.getId()
                       );
  }
  public void updateText (Kante k) {

    if (k != null)
      mView.updatePropertyText("Aktiver Kante "+
                       '\n' + "ist " + k.getName()
                       +'\n' + "Knoten A ist " +knotenListe.getKnotenById(k.getKnotenA()).getName() +" ID "+knotenListe.getKnotenById(k.getKnotenA()).getId()
                       +'\n' + "Knoten B ist " +knotenListe.getKnotenById(k.getKnotenB()).getName() +" ID "+knotenListe.getKnotenById(k.getKnotenB()).getId()
                       +'\n'+'\n' + "Gewichtung (A,B): " +k.getGewA()
                       +'\n' + "Gewichtung (B,A): " +k.getGewB()
                       +"\n ID =: "+k.getId());
  }
  public void updateButtonState(int i){
    buttonState = i;
  }

  public void deleteElement(int x, int y) {

    Knoten tmpKnoten;
    Kante tmpKante;

    tmpKnoten = knotenListe.getKnotenByPos(x,y);
    if (tmpKnoten != null){ // lösche Knoten
      markedKnoten = -1;
      markedKante = -1;
 //     int i = 0;
 //     int j = 0;
      Iterator iter = kantenListe.iterator();
      while (iter.hasNext() ) {
        tmpKante = (Kante) iter.next();
        if( (tmpKante.getKnotenA() == tmpKnoten.getId()) || (tmpKante.getKnotenB() == tmpKnoten.getId()) ){
          // zum knoten gehörende Kanten (egal ob hin- oder weggehened) müssen auch gelöscht werden
          kantenListe.deleteKante(tmpKante.getId());
          iter = kantenListe.iterator();
        }
      }
      knotenListe.deleteKnoten(tmpKnoten.getId()); // loesche knoten
    }
    else{ // lösche Kante
      tmpKante = kantenListe.getKanteByPos(x,y);
      if (tmpKante != null){
        kantenListe.deleteKante(tmpKante.getId());
        markedKante = -1;
      }
    }
  } // Ende deleteElement

  public void showKantenDia(int KantenId){

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    diaKante.setSize(350,350);
    diaKante.setLocation((screenSize.width - mView.getX()) / 2, (screenSize.height - mView.getX()) / 2);

    diaKante.setKnotenAName(knotenListe.getName(kantenListe.getKanteById(KantenId).getKnotenA()));
    diaKante.setKnotenBName(knotenListe.getName(kantenListe.getKanteById(KantenId).getKnotenB()));
    diaKante.setKanteNameText(kantenListe.getKanteById(markedKante).getName());
    diaKante.setGewA(kantenListe.getKanteById(KantenId).getGewA());
    diaKante.setGewB(kantenListe.getKanteById(KantenId).getGewB());

    diaKante.show();

    if (diaKante.getOk() ){ // OK button clicked
      kantenListe.getKanteById(markedKante).setName(diaKante.getName());
      kantenListe.getKanteById(markedKante).setGewA(diaKante.getGewA());
      kantenListe.getKanteById(markedKante).setGewB(diaKante.getGewB());
    }
  }

  public void showKnotenDia (int KnotenId){
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    diaKnoten = new DialogKnoten(knotenListe);
    diaKnoten.setSize(240,320);
    diaKnoten.setLocation((screenSize.width - mView.getX()) / 2,
                          (screenSize.height - mView.getX()) / 2);

    // setze werte mit werten aus Objekt
    diaKnoten.setKnotenName(knotenListe.getKnotenById(KnotenId).getName());
    diaKnoten.setEventAnzahl(knotenListe.getKnotenById(KnotenId).getEventAnzahl());
    diaKnoten.setEventProEinheit(knotenListe.getKnotenById(KnotenId).getEventProEinheit());

    diaKnoten.show();

    // setze werte mit werten aus Dialog
    knotenListe.getKnotenById(KnotenId).setEventAnzahl(diaKnoten.getEventAnzahl());
    knotenListe.getKnotenById(KnotenId).setEventProEinheit(diaKnoten.getEventProEinheit());

    if (diaKnoten.getOK())
      knotenListe.getKnotenById(KnotenId).setName(diaKnoten.getKnotenName());
    if (diaKnoten.getStart()) {
      knotenListe.getKnotenById(KnotenId).setStart();
    }
    if (diaKnoten.getZiel()) {
      knotenListe.getKnotenById(KnotenId).setZiel();
    }
  }

  public KnotenListe getKnotenListe (){
    return knotenListe;
  }

  public KantenListe getKantenListe (){
    return kantenListe;
  }

  public void setKantenListe( KantenListe k){
    kantenListe= k;
  }

  public void setKnotenListe(KnotenListe k){
    knotenListe = k;
  }

  public void setKantenKnotenListe(KnotenListe k){
    kantenListe.setKnotenListe(k);
  }

  public void setMarkedKante(int i){
    markedKante =i;
  }
  public void setMarkedKnoten(int i){
    markedKnoten = -1;
  }

  public void berechneAllRoutenBtnPerformed(){

  }

  public void berechneOneRouteBtnPerformed(){

  }

//  public RouterManager getRoutenManager(){

 // }

} // ENde ModellierManager