package Simulation;

import ptss.*;
import Simulation.eduni.simjava.*;
import java.lang.*;
import java.util.*;
import java.util.Iterator;
import java.awt.Dimension;
import java.awt.Toolkit;

public class SimulationManager implements Runnable{

  private SimulationView sView;
  private ModellierManager mManager;
  private Thread simThread;
  private Sim_outfile trace_out = new Sim_outfile();
  private int speed = 1;
  private Sim_system sim_System ;
  private SimulationViewGrafik sGrafik;
  private double gesamtZeit =0;
  private boolean paused = false;
  private KnotenListe knotenListe;
  private KantenListe kantenListe;
  private Vector zielKnotenListe = new Vector();


  public SimulationManager(SimulationView _sView,ModellierManager _mManager,SimulationViewGrafik _sGrafik){
    sView = _sView;
    mManager = _mManager;
    sGrafik = _sGrafik;


  }
  public void setModellierManager (ModellierManager m) {
    mManager = m;
  }


  public void startSimu(){

    Knoten tmpStartKnoten;
    Knoten tmpZielKnoten;
    RoutenListe routenListe;
    Route route;

    knotenListe = mManager.getKnotenListe();
    kantenListe = mManager.getKantenListe();

    knotenListe.clearSimuId(); // vorige Simu löschen (falls vorhanden)
    kantenListe.clearKapa(); // Kantenkapa auf 0 setzen

    RouterManager rm = new RouterManager(knotenListe, kantenListe,null);
    routenListe = rm.getRoutenListe();

    trace_out.initialise();
    Sim_system.initialise(trace_out, simThread);



    int i = -1; // interne SimuId

    // alle ZielKnoten in ZielKnotenListe eintragen
    int z = 0;
    for (Iterator iterZiel = knotenListe.iterator(); iterZiel.hasNext(); ) {
      tmpZielKnoten = (Knoten) iterZiel.next();
      if(tmpZielKnoten.isZiel()){
        // dann alle Ziel Knoten eintragen
       ZielKnotenListe kL = new ZielKnotenListe(tmpZielKnoten.getId());
       zielKnotenListe.add(kL);
       z++;
      }
    }

    // Erst alle Start Knoten eintragen
    for (Iterator iterStart = knotenListe.iterator(); iterStart.hasNext(); ) {
      tmpStartKnoten = (Knoten) iterStart.next();

      if(tmpStartKnoten.isStart()){

///        for (Iterator iterZiel = knotenListe.iterator(); iterZiel.hasNext(); ) {
//          tmpZielKnoten = (Knoten) iterZiel.next();

//          if(tmpZielKnoten.isZiel()){
            // Start eintragen
            if(tmpStartKnoten.getSimuId() == -1){
              i++; // interne Simu ID
 //             tmpStartKnoten.setSimuId(i);
              Sim_system.add( new Quelle(tmpStartKnoten.getName()+"_START", tmpStartKnoten.getId(), Quelle.SRC_OK,i,tmpStartKnoten.getEventAnzahl(),tmpStartKnoten.getEventProEinheit(),mManager,zielKnotenListe) );
//              System.out.println("added start");
  //          }
  //        }
        }
      }
    }

    // jetzt alle Zwischenstationen
    for (Iterator iterStart = knotenListe.iterator(); iterStart.hasNext(); ) {
      tmpStartKnoten = (Knoten) iterStart.next();

      if(tmpStartKnoten.isStart()){
        for (Iterator iterZiel = knotenListe.iterator(); iterZiel.hasNext(); ) {
          tmpZielKnoten = (Knoten) iterZiel.next();

          if(tmpZielKnoten.isZiel()){

            route = routenListe.getRoute(tmpStartKnoten.getId(), tmpZielKnoten.getId());
            route.resetKnoten();

            while(route.hasNextKnoten()){

              int tmpKnoten = route.nextKnoten();

              if(knotenListe.getKnotenById(tmpKnoten).getSimuId() == -1){ // Switch schon vorhanden?
                i++; // interne Simu ID
                knotenListe.getKnotenById(tmpKnoten).setSimuId(i); // setze interne Simuid
                Sim_system.add(new Switch(knotenListe.getKnotenById(tmpKnoten).getName()
                    , knotenListe.getKnotenById(tmpKnoten).getId(), Switch.SINK_OK, mManager));
              }

            }
            i++;
            tmpZielKnoten.setSimuId(i); // setze Simuid
            Sim_system.add(new Switch(tmpZielKnoten.getName()+"_ZIEL", tmpZielKnoten.getId(), Senke.SINK_OK,mManager));
          }else continue;
        }
      }else continue;
    }
   simThread.start();
  }

  public void run() {

    System.out.println("Entering simThread run()");

    double starttime = System.currentTimeMillis(); // Schlafenszeit wird später addiert
    double progTime = 0;
    double simTime = 0;

    int snapshot = 0;

    Sim_system.run_start();

    // bearbeite einen Simulationsschritt (tick)
    while(Sim_system.run_tick()) {

      Thread alive = Thread.currentThread();
      if (alive == simThread){ // Thread gestoppt?

        while(paused){ // pause modus
          try { simThread.sleep(100); }
            catch(InterruptedException except) {}
             starttime+=100;
        }

        progTime = (System.currentTimeMillis())- starttime; // Laufzeit unsers Programms
        simTime = Sim_system.clock()-progTime; // globale Simulationszeit

//         System.out.println("*****  SimuZeit       ******: "+Sim_system.clock());
//         System.out.println("*****  Laufzeit       ******: "+progTime);
//         System.out.println("*****  Differenz      ******: " + simTime);
//         System.out.println("*****  Thread schlief ******: "+gesamtZeit);


        // Um eine chronologisch & proportional richtige Darstellung zu gewährleisten
        // sollte die Simulation nun die differenz zwischen progTime und simTime "schlafen"
        // speed ist ein ModifikationsFaktor dieser "AnimationsZeit"
        long anim_time = 1;
        if (speed > 0) // Geschwindigkeit erhöhen
          anim_time = (long)(simTime/(speed));
        else{ // Geschwindigkeit verzögern
          anim_time =(long)Math.abs(simTime*(speed));
        }

        if((simTime < 0) || (speed < 0)){
          anim_time = Math.abs(speed*10);
        }
//         System.out.println("aktuelle Sleep time  : " + anim_time);
//         System.out.println("Modifikator          : " + speed);
//         System.out.println("*************************");


        // hinkt die Progzeit hinter der Simuzeit nach,
        // wird Thread nur auf "user-wunsch" speed < 0 schlafen gelegt
        if ((simTime > 1) || (speed < 0)){
          gesamtZeit +=simTime; // solange schlief thread
//           System.out.println("*********************aktuelle Sleep time  : " + anim_time);
          if(speed <= 80){
            try { simThread.sleep(anim_time); }
            catch(InterruptedException except) {}
          }
          if(speed > 0)
            starttime-=anim_time; // Schlafenszeit muss in Programmzeit berücksichtigt werden
          else
            starttime+=anim_time-simTime;
        }

        // Das repaint verbraucht viele Systemressourcen daher nur
        // bei jedem 10. Simu -tick

        snapshot++;
        if (snapshot >=10){
          sView.repaint();
          snapshot =0;
        }
 //       try { simThread.sleep(5); } // gibt anderen Prozessen eine Chance
 //       catch(InterruptedException except) {}

      } // solange Simulation läuft nicht gestoppt
      else
        break; // Simulation wurde gestoppt
    }
    // Auswertung
    auswertungFenster();

    sView.repaint();
    trace_out.close();
    simThread = null;
    Sim_system.run_stop();
    System.gc();        // Tidy up memory usage
  }

  void startSim_Btn() {

    if (simThread == null) {
      Sim_system.set_auto_trace(true);
      simThread = new Thread(this);
      startSimu();
    }


  }

  void stopSim() {

    simThread = null;
  //  trace_out.close();
 //   Sim_system.stop();
 //   Sim_system.run_stop();

  }

  public void pauseSimu_Btn(){
    if(paused == false){
      paused = true;
      sView.setPauseBtnText("Weiter");
    }
    else{
      paused = false;
      sView.setPauseBtnText("Pause");
    }
  }

  public void sliderChanged(int val){
    speed = val;

    if(speed <= 100){ // < 100 verzögerung
      speed -=100;
      sView.setSpeedText(" Geschwindigkeit:"+"\n verzögert "
                         +"\n Faktor: "+speed*-1 );
    }
    if(speed >= 100){ // > 100 beschleunigung
      speed -=100;
      sView.setSpeedText(" Geschwindigkeit:"+"\n beschleunigt "
                         +"\n Faktor: "+speed);
    }
    if(speed == 0){
      speed = 1;
      sView.setSpeedText(" Geschwindigkeit:"+"\n Echzeit");
    }
    if(speed >= 80) // > 100 beschleunigung
      sView.setSpeedText(" Geschwindigkeit:"+"\n Beschleunigung "
                         +"\n Faktor: ungebremst");

  }

  public void stopSimu_Btn(){
    stopSim();
  }

public void auswertungFenster(){
  // Fester mit Ausgabe der RoutenInfo
 //  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   DialogRoutenInfo diaRoutenInfo = new DialogRoutenInfo();
   diaRoutenInfo.setSize(450,450);
   diaRoutenInfo.setTitle("Statistik");
   diaRoutenInfo.setModal(false);
 //  diaRoutenInfo.setLocation((screenSize.width - mView.getX()) / 2, (screenSize.height - mView.getX()) / 2);

      String text = "";
      // werte alle Kanten aus.
      Kante tmpKante;
      int gesamtEventsKanten =0;
      text+="Statstik für Kanten:\n\n";
      text+= "Name:\tMAX\tAnzahlEvents:\tD-Schnitt\n";
      text+="\tBelastung\n\n";

      for (Iterator iter= kantenListe.iterator(); iter.hasNext(); ) {
        tmpKante = (Kante) iter.next();

        if (tmpKante.greatestKapa > 0){ // kannte war an der simu beteiligt

          text +=tmpKante.getName()+"\t";
          text +=tmpKante.greatestKapa+"\t";
          text+=tmpKante.gesamtEvents+"\t\t";
          text+=(tmpKante.gesamt/tmpKante.gesamtEvents)+"\n";
        }
      }
      diaRoutenInfo.setText(text);
      diaRoutenInfo.show();
}



  public void setGrafik(SimulationViewGrafik g){
    sGrafik = g;
  }
public class ZielKnotenListe{
  private int Id;
  public ZielKnotenListe(int i){
  Id = i;
  }
  public int getID(){
    return Id;
  }
}




}