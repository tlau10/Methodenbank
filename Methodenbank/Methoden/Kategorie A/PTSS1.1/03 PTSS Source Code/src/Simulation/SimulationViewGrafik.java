package Simulation;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import ptss.*;

class SimulationViewGrafik extends JPanel{

  SimulationManager sManager;
  ModellierManager mManager;
  private KnotenListe knotenListe;
  private KantenListe kantenListe;
  private int tAX,tAY,tBX,tBY;
  private int StringX,StringY,StringXt,StringYt;

  private int innerOval = 10;
  private int outerOval = 12;




  Panel inputs;
  Label msgLabel, sizeLabel, workLabel, showMsgLabel;
  TextField msgField;
  Choice sizeChoice;
  Choice workChoice;
  Checkbox showMsgBox;
  public Thread simThread;

  public SimulationViewGrafik(SimulationManager _sManager, ModellierManager _mManager) {

    sManager = _sManager;
    mManager = _mManager;

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setBackground(Color.white);
    this.setMinimumSize(new Dimension(1280, 1024));
    this.setPreferredSize(new Dimension(1280, 1024));
  }

  public void setSimuManager (SimulationManager s) {
      sManager = s;
  }
  public void setModellierManager (ModellierManager m) {
       mManager = m;
  }

  public void  paint (Graphics g2){
    super.paint(g2);
       for(int j=0; j<1000;j++){}

   Graphics2D g = (Graphics2D) g2;  // zum weichzeichnen
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);


    kantenListe = mManager.getKantenListe();
    knotenListe = mManager.getKnotenListe();

    // Kanten zeichnen
    Kante tmpKante;
       for (Iterator iter = kantenListe.iterator(); iter.hasNext(); ) {
         tmpKante = (Kante) iter.next();
         g.setColor(Color.blue);
 //        g.setStroke(new BasicStroke(2)); // Liniendicke
/*         if(tmpKante.isMarked() )
           g.setColor(Color.red);
         if(tmpKante.isPfad()){
           g.setColor(Color.pink);
           if(tmpKante.isMarked() )
             g.setColor(Color.red);
             g.setStroke(new BasicStroke(5));
  */
 //        }
         g.drawLine( (  knotenListe.getKnotenById(tmpKante.getKnotenA()).getX()  ),
                     (  knotenListe.getKnotenById(tmpKante.getKnotenA()).getY() ),
                     (  knotenListe.getKnotenById(tmpKante.getKnotenB()).getX() ),
                     (  knotenListe.getKnotenById(tmpKante.getKnotenB()).getY() ) );


      tAX =  knotenListe.getKnotenById(tmpKante.getKnotenA()).getX();
      tAY =  knotenListe.getKnotenById(tmpKante.getKnotenA()).getY();
      tBX =  knotenListe.getKnotenById(tmpKante.getKnotenB()).getX();
      tBY =  knotenListe.getKnotenById(tmpKante.getKnotenB()).getY();

      //berechne Mittelpunkt der Kante
      if  (tAX <= tBX ) {
        StringXt =tBX -tAX;
        StringX = StringXt;
        StringX /= 2; StringX += tAX;
      }
      else{
        StringXt = tAX-tBX;
        StringX = StringXt;
        StringX /= 2; StringX += tBX;
      }

      if ( tAY <= tBY ) {
        StringYt = tBY-tAY;
        StringY = StringYt;
        StringY /= 2; StringY += tAY;
      }
      else{
        StringYt = tAY-tBY;
        StringY = StringYt;
        StringY /= 2; StringY += tBY ;
      }
    g.setColor(Color.black);
    g.drawString(""+tmpKante.getaktKapa(), StringX,StringY); //rotierten string zeichnen
   }

  // Knoten zeichnen
  Knoten tmpKnoten;
 // g.setStroke(new BasicStroke(2)); // Liniendicke
  for (Iterator iter = knotenListe.iterator(); iter.hasNext(); ) {
    tmpKnoten = (Knoten) iter.next();


    g.setColor(Color.red);
    g.drawOval(tmpKnoten.getX()-outerOval/2,tmpKnoten.getY()
               -outerOval/2,outerOval,outerOval);
    g.setColor(Color.green);
    g.fillOval(tmpKnoten.getX()-innerOval/2,tmpKnoten.getY()
                 -innerOval/2,innerOval,innerOval);



    if (tmpKnoten.getStart() ) {
      g.setColor(Color.yellow);
      g.fillOval(tmpKnoten.getX()-(outerOval+2)/2,tmpKnoten.getY()
                 -(outerOval+2)/2,outerOval+2,outerOval+2);
      g.setColor(Color.black);
      g.drawString(""+tmpKnoten.scheduledEvents,tmpKnoten.getX()-15,tmpKnoten.getY()+15);
    }
    else if (tmpKnoten.getZiel() ) {
      g.setColor(Color.cyan);
      g.fillOval(tmpKnoten.getX()-(outerOval+2)/2,tmpKnoten.getY()
                 -(outerOval+2)/2,outerOval+2,outerOval+2);
      g.setColor(Color.black);
      g.drawString(""+tmpKnoten.scheduledEvents,tmpKnoten.getX()-15,tmpKnoten.getY()+15);
    }
/*
    else if (tmpKnoten.isMarked() ){
      g.setColor(Color.red);
      g.fillOval(tmpKnoten.getX()-(outerOval+2)/2,tmpKnoten.getY()
                 -(outerOval+2)/2,outerOval+2,outerOval+2);
    }
    else{
    */

//    }
/*
    g.setColor(Color.black);
      if(beschriftung == true)
      g.drawString(tmpKnoten.getName(),tmpKnoten.getX()-15,tmpKnoten.getY()+15);
*/
    }
  }



}