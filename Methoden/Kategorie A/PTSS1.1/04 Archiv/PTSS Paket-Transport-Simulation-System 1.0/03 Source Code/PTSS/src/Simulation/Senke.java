package Simulation;

//import eduni.*;
import Simulation.eduni.simjava.*;
import java.util.*;

import ptss.*;

class Senke extends Sim_entity {
//  private Sim_port in;
  private int index;
  private int state;
  private int nextKnoten;
  private ModellierManager mManager;
  public static final int SINK_BLOCKED = 0;
  public static final int SINK_OK      = 1;
  private KnotenListe knotenListe;
  private KantenListe kantenListe;

  public Senke(String name, int index, int state,ModellierManager _mManager) {
    super(name);
    this.index = index;
    this.state = state;
    mManager = _mManager;

    kantenListe  = mManager.getKantenListe();
    knotenListe  = mManager.getKnotenListe();

  }

  public void body() {
    Sim_event ev = new Sim_event();

    while(true){

      sim_get_next(ev);
      knotenListe.getKnotenById(index).scheduledEvents++;
      System.out.println("got event");

    }
}}