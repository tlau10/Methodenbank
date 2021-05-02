package Simulation;

//import eduni.*;
import Simulation.eduni.simjava.*;
import java.util.*;
import javax.swing.*;

import ptss.*;

class Switch extends Sim_entity {

  private int state;
  private int nextKnoten;
  private ModellierManager mManager;
 private     double delay = 1000;
  // expontial verteilte Zufallszahl
//  private Sim_negexp_obj expNum = new Sim_negexp_obj("test1",1000,56789);
  private String KnotenName;

  private int eventZiel;

  public static final int SINK_BLOCKED = 0;
  public static final int SINK_OK      = 1;

  private KnotenListe knotenListe;
  private KantenListe kantenListe;
  private RoutenListe routenListe;
  private Route route;
  private Route vorRoute;
  private RouterManager rm;
  private Knoten tmpKnoten;

  private int myKnotenId;
  private int knotenToSchedule = 0;
  private int eventVorgaengerKnoten = 0;

  public Switch(String name, int kId, int state ,ModellierManager _mManager) {
    super(name);
    KnotenName = name;
    myKnotenId = kId;
    state = state;

    mManager = _mManager;

    kantenListe  = mManager.getKantenListe();
    knotenListe  = mManager.getKnotenListe();
    Knoten tmpKnoten;

    rm = new RouterManager(knotenListe, kantenListe,null);
    routenListe = rm.getRoutenListe();
  }

  public void body() {
    Sim_event ev = new Sim_event(); //

    while(true){ // solange "further events" ....

      sim_get_next(ev);
 //     delay = expNum.sample();
      eventZiel = ((Simulation.Quelle.data)ev.get_data()).ziel; // EventZiel
      eventVorgaengerKnoten = ((Simulation.Quelle.data)ev.get_data()).VorgaengerKnoten; // Vorgänger Knoten

      // setzte aktuelle ID als Vorgänger für nachfolgenden Switch
      ((Simulation.Quelle.data)ev.get_data()).VorgaengerKnoten = myKnotenId;

        // erhöhe Anzahl der vermittelten events
        knotenListe.getKnotenById(myKnotenId).scheduledEvents++;


      if(myKnotenId != eventZiel){ // switch ist auch ziel
        route = routenListe.getRoute(myKnotenId, eventZiel);
        route.resetKnoten();

        int kanteNach = route.getAktKante();
        knotenToSchedule = route.nextKnoten();

        if(route.hasNextKnoten() ){
          knotenToSchedule = route.nextKnoten();
        } else // letzter Knoten vor Ziel, ergo nächster Knoten == Ziel
          knotenToSchedule = eventZiel;

//        System.out.println(KnotenName +" Ziel "+ eventZiel +" nextKnoten "+knotenToSchedule + " VorgaengerKnoten "+ +eventVorgaengerKnoten+ " MyId "+myKnotenId);
        kantenListe.updateAktKapa(kanteNach,+1); // nachfolger Kante

        // berechne delay abhängig der KantenDistanz
        delay = (kantenListe.getKanteById(kanteNach).getGewA()*10);

        // Event zum nächsten Knoten(Switch) weiterleiten
        sim_schedule(knotenListe.getSimuId(knotenToSchedule),(delay),1,ev.get_data());
      }
        vorRoute = routenListe.getRoute(eventVorgaengerKnoten, eventZiel);
        vorRoute.resetKnoten();
        int kanteVor =  vorRoute.getAktKante();

 //       if(myKnotenId == eventZiel)
 //        kantenListe.updateAktKapa(kanteVor,0); // nachfolger Kante
        // vorgaenger Kante
        kantenListe.updateAktKapa(kanteVor,-1);

    }
  }
}