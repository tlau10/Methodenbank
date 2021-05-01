package planer;
import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.util.Vector;
import org.jdom.Element;
import java.awt.event.*;


public class JPanelAusgabe extends JPanel {
  JListModelFactory myJListModelFactory ;
  Vector resultVector_;
  double resultCalories_;
  mainFrame parentFrame_;
  int kalorienBedarf_;
  JLabel jLabelAusgabeHeading = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabelAusgabeFruehstueck = new JLabel();
  JLabel jLabelMittagsmenue = new JLabel();
  JLabel jLabelAbendessen = new JLabel();
  JLabel jLabelgesamtKalorien = new JLabel();
  JButton jButtonWeiter3 = new JButton();
  JList jListFruehstueck = new JList();
  JList jListMittagessen = new JList();
  JList jListAbendessen = new JList();
  JLabel jLabelFruehstueckKalorien = new JLabel();
  JLabel jLabelMittagessenKalorien = new JLabel();
  JLabel jLabelAbendessenKalorien = new JLabel();
  JLabel jLabelTagesBedarf = new JLabel();

  public JPanelAusgabe(Vector resultVector,double resultCalories,int kalorienBedarf, mainFrame parentFrame) {
    try {
      kalorienBedarf_=kalorienBedarf;
      resultVector_=resultVector;
      resultCalories_=resultCalories;
      parentFrame_=parentFrame;
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
     myJListModelFactory = new JListModelFactory(resultVector_);
    String strKaloriesnErgebnis =Double.toString(resultCalories_);
    this.setMenuCalories();
    jLabelTagesBedarf.setText("Tagesbedarf: "+kalorienBedarf_+" Kalorien");
    int ID;
    Integer tempInteger;
    String tmpstr="";


    jListFruehstueck = new JList(myJListModelFactory.createMenue("Fruehstueck"));
    jListMittagessen = new JList(myJListModelFactory.createMenue("Mittagessen"));
    jListAbendessen = new JList(myJListModelFactory.createMenue("Abendessen"));
    JScrollPane jScrollPaneFruehstueck=new JScrollPane(jListFruehstueck);
    JScrollPane jScrollPaneMittagessen=new JScrollPane(jListMittagessen);
    JScrollPane jScrollPaneAbendessen=new JScrollPane(jListAbendessen);

    jLabelAusgabeHeading.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabelAusgabeHeading.setForeground(Color.black);
    jLabelAusgabeHeading.setBorder(BorderFactory.createRaisedBevelBorder());
    jLabelAusgabeHeading.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelAusgabeHeading.setText("Ihr mittels LP-Solve berechnetes Tagesmenü");
    this.setLayout(xYLayout1);
    jLabelAusgabeFruehstueck.setForeground(Color.black);
    jLabelAusgabeFruehstueck.setBorder(BorderFactory.createEtchedBorder());
    jLabelAusgabeFruehstueck.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelAusgabeFruehstueck.setText("Fruehstücksmenü");

    jLabelMittagsmenue.setBorder(BorderFactory.createEtchedBorder());
    jLabelMittagsmenue.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelMittagsmenue.setText("Mittagsmenü");

    jLabelAbendessen.setBorder(BorderFactory.createEtchedBorder());
    jLabelAbendessen.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelAbendessen.setText("Abendessen");

    xYLayout1.setWidth(690);
    xYLayout1.setHeight(513);
    jLabelgesamtKalorien.setFont(new java.awt.Font("SansSerif", 1, 15));
    jLabelgesamtKalorien.setForeground(Color.black);
    jLabelgesamtKalorien.setBorder(BorderFactory.createEtchedBorder());
    jLabelgesamtKalorien.setHorizontalAlignment(SwingConstants.LEFT);
    jLabelgesamtKalorien.setHorizontalTextPosition(SwingConstants.TRAILING);
    jButtonWeiter3.setText("Ende");
    jButtonWeiter3.addActionListener(new JPanelAusgabe_jButtonWeiter3_actionAdapter(this));
    jLabelMittagessenKalorien.setHorizontalAlignment(SwingConstants.LEADING);
    jLabelTagesBedarf.setFont(new java.awt.Font("SansSerif", 1, 15));
    jLabelTagesBedarf.setBorder(BorderFactory.createEtchedBorder());
    this.add(jLabelAusgabeHeading, new XYConstraints(137, 24, 395, -1));
    this.add(jScrollPaneFruehstueck,     new XYConstraints(280, 80, 240, 80));
    this.add(jScrollPaneMittagessen,     new XYConstraints(280, 180, 240, 80));
    this.add(jScrollPaneAbendessen,      new XYConstraints(280, 280, 240, 80));
    this.add(jLabelAusgabeFruehstueck,    new XYConstraints(130, 110, 120, 25));
    this.add(jLabelMittagsmenue,    new XYConstraints(130, 210, 120, 25));
    this.add(jLabelAbendessen,     new XYConstraints(130, 310, 120, 25));
    this.add(jLabelFruehstueckKalorien,                new XYConstraints(540, 110, 140, 25));
    this.add(jLabelMittagessenKalorien,      new XYConstraints(540, 210, 140, 25));
    this.add(jLabelAbendessenKalorien,        new XYConstraints(540, 310, 140, 25));
    this.add(jButtonWeiter3, new XYConstraints(614, 485, -1, -1));
    this.add(jLabelgesamtKalorien,            new XYConstraints(530, 410, 200, 25));
    this.add(jLabelTagesBedarf,           new XYConstraints(280, 410, 200, 25));


  }



  void setMenuCalories()
  {
   int menueFCalories=0;
   int menueMCalories=0;
   int menueACalories=0;
   int elemGruppe,c;
   Integer temp;
   String tmpstr;

   for (int i = 0; i < resultVector_.size(); i++)
    {
      if(resultVector_.get(i)!=null)
      {
        Element myElement = (Element) resultVector_.get(i);
        temp = new Integer(myElement.getChild("GruppenID").getText());
        elemGruppe = temp.intValue();

          if ( elemGruppe < 4 )
          {
            temp = new Integer(myElement.getChild("Kalorien").getText());
            c = temp.intValue();
            menueFCalories+=c;
          }
          if ( ( elemGruppe > 3) && ( elemGruppe <8) )
          {
            temp = new Integer(myElement.getChild("Kalorien").getText());
            c = temp.intValue();
            menueMCalories+=c;
          }
          if ( elemGruppe > 7)
          {
            temp = new Integer(myElement.getChild("Kalorien").getText());
            c = temp.intValue();
            menueACalories+=c;
          }
      }
    }
    c=menueFCalories+menueMCalories+menueACalories;
    jLabelFruehstueckKalorien = new JLabel("Frühstück:   "+menueFCalories+" Kalorien");
    jLabelMittagessenKalorien = new JLabel("Mittagessen: "+menueMCalories+" Kalorien");
    jLabelAbendessenKalorien =  new JLabel("Abendessen:  "+menueACalories+" Kalorien");
    jLabelgesamtKalorien     = new JLabel(" Gesamt:      "+c+" Kalorien");
}



  void jButtonWeiter3_actionPerformed(ActionEvent e)
  {
    parentFrame_.closechild(3,this,0,null);
  }
}

class JPanelAusgabe_jButtonWeiter3_actionAdapter implements java.awt.event.ActionListener {
  JPanelAusgabe adaptee;

  JPanelAusgabe_jButtonWeiter3_actionAdapter(JPanelAusgabe adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonWeiter3_actionPerformed(e);
  }
}
