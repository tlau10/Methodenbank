package ptss;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import ptss.*;
import Simulation.*;

class Progress extends JPanel implements Runnable{
  private Thread myThread;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JProgressBar jProgressBar1;
  private ModellierView mView;
  private JLabel Fortschritt = new JLabel();

public void start(){
  myThread = new Thread(this);
  myThread.start();
}

public void stop(){
  myThread = null;
  jProgressBar1.setValue(0);
}

public void run(){

  int i = 0;
  int Anzahl = 0;
  Thread alive = Thread.currentThread();

  while(alive == myThread){
      Anzahl = mView.getCalculatedRouten();
      jProgressBar1.setValue(Anzahl);
      try { myThread.sleep(100); } // gibt anderen Prozessen eine Chance
      catch(InterruptedException except) {}
  }
}

  public Progress(int Anz, ModellierView m) {

    mView = m;
    jProgressBar1 = new JProgressBar(0,Anz);

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {

    this.setLayout(borderLayout1);
    jProgressBar1.setPreferredSize(new Dimension(130, 14));
    Fortschritt.setToolTipText("");
    Fortschritt.setText("Fortschritt");
    this.add(jProgressBar1, BorderLayout.CENTER);
    this.add(Fortschritt, BorderLayout.NORTH);
  }

  public void init(ModellierView mView,int Anzahl){
    this.mView = mView;
    jProgressBar1.setMaximum(Anzahl);
  }
  public void setValue(int val){
    jProgressBar1.setValue(val);
  }

}