package Simulation;

//import eduni.*;
import Simulation.eduni.simjava.*;
import java.util.*;

import ptss.*;


class Quelle extends Sim_entity {

  public static final int SRC_OK      = 0;
  public static final int SRC_BLOCKED = 1;

  private int index;
  private int state;
  private ModellierManager mManager;
  private double delay = 1000;
  private int zielKnoten;
  private KnotenListe knotenListe;
  private KantenListe kantenListe;
  private RoutenListe routenListe;
  private Route route;
  private RouterManager rm;
  private int myKnotenId;
  private int knotenToSchedule = 0;
  private Vector zielKnotenListe;
  private String knotenName;

  private int anzahl;
  private int proSimuEinheit;


  public Quelle(String name, int kId, int state,int nextK,int event_Anzahl,int event_Pro_SimuEinheit,ModellierManager _mManager,Vector zielKnotenL ) {
    super(name);
    this.state = state;
    knotenName = name;
    //   nextKnoten = nextK;
    mManager = _mManager;
    zielKnotenListe = zielKnotenL;
    myKnotenId = kId;
    anzahl = event_Anzahl;
    proSimuEinheit = event_Pro_SimuEinheit;

    kantenListe  = mManager.getKantenListe();
    knotenListe  = mManager.getKnotenListe();
    rm = new RouterManager(knotenListe, kantenListe,null);

  }

  public void body() {
    int j=0;
    for(j=0; j<anzahl;j++) {

      for (Iterator iterZiel = zielKnotenListe.iterator(); iterZiel.hasNext(); ) {

        Simulation.SimulationManager.ZielKnotenListe z = (Simulation.SimulationManager.ZielKnotenListe) iterZiel.next();
        zielKnoten = z.getID();

        routenListe = rm.getRoutenListe();
        route = routenListe.getRoute(myKnotenId,zielKnoten);
        route.resetKnoten();

        knotenToSchedule = route.nextKnoten();
        if(route.hasNextKnoten() ){
          knotenToSchedule = route.nextKnoten();
        } else // letzter Knoten vor Ziel, ergo nächster Knoten == Ziel
          knotenToSchedule = zielKnoten;

        route = routenListe.getRoute(myKnotenId, zielKnoten);
        route.resetKnoten();
        int kanteNach = route.getAktKante();
        kantenListe.updateAktKapa(kanteNach,+1); // nachfolger Kante

   //     for(int test = 0; test < proSimuEinheit; test++)
          sim_schedule(knotenListe.getSimuId(knotenToSchedule),((kantenListe.getKanteById(kanteNach)).getGewA())*10,knotenToSchedule,new data(zielKnoten,myKnotenId));

          float r = Random.expon(((kantenListe.getKanteById(kanteNach)).getGewA())*10,1);
          r /= proSimuEinheit;
       //   System.out.println("Quelle "+ knotenName +" hold for "+ r);
        sim_hold(r);

      }
        knotenListe.getKnotenById(myKnotenId).scheduledEvents++;
   //   System.out.println("Anzahl: "+j);
    }
  }

  public class data{
    int ziel=0;
    int VorgaengerKnoten=0;
    public data(int _zielid, int myId){
      ziel = _zielid;
      VorgaengerKnoten = myId;
    }
  }


}