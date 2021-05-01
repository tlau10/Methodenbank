package planer;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;
import javax.swing.JPanel;
import java.beans.*;

public class JPanelWerte extends JPanel {

  JLabel jLabel1EingabeText = new JLabel("Bitte machen Sie die folgenden Angaben zu Ihrer Person");
  mainFrame parentFrame_;

  JTextField jTextFieldGroesse = new JTextField();

  JTextField jTextFieldGewicht = new JTextField();
  JTextField jTextFieldAlter  = new JTextField();
  JLabel jLabelGroesse = new JLabel("Grösse") ;
  JLabel jLabelGewicht = new JLabel("Gewicht") ;
  JLabel jLabelAlter =   new JLabel("Alter") ;

  BorderLayout myBorderLayout;

  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  int kalorien=0;

  XYLayout xYLayout1 = new XYLayout();
  JButton jButtonWeiter = new JButton();
  JLabel jLabelTaetigkeit = new JLabel();
  String[] sTaetig = { "Leicht", "Mittelschwer", "Schwer"};
  JComboBox jComboBoxTaetig = new JComboBox(sTaetig);
  JLabel jLabelGeschl = new JLabel();
  String[] sGeschl = { "männlich", "weiblich"};
  JComboBox jComboBoxGeschl = new JComboBox(sGeschl);
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabelBMIWert = new JLabel();
  JLabel jLabelUnterUeber = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabelEnergie = new JLabel();

  public JPanelWerte() {
    try {


      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public JPanelWerte(mainFrame diaetParent) {
    try {

      parentFrame_=diaetParent;
      jbInit();

    }
    catch(Exception ex) {
      ex.printStackTrace();
    }



  }

  void setCalories(int cal)
  {

    kalorien=cal;
  }


  void jbInit() throws Exception {

    this.setLayout(xYLayout1);
    this.setFont(new java.awt.Font("Dialog", 0, 20));
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    this.setDebugGraphicsOptions(0);
    this.setMaximumSize(new Dimension(300, 150));
    this.setMinimumSize(new Dimension(300, 150));
    this.setNextFocusableComponent(jTextFieldGewicht);
    this.setPreferredSize(new Dimension(300, 150));
    jLabelGroesse.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelGroesse.setText("Grösse ( in Zentimetern )");
    jLabelGewicht.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelGewicht.setText("Gewicht ( in Kilogramm)");
    jLabelAlter.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelAlter.setText("Alter ( in Jahren )");
    jTextFieldGewicht.setMaximumSize(new Dimension(10, 30));
    jTextFieldGewicht.setSize(10,10);
    jTextFieldGewicht.addKeyListener(new JPanelWerte_jTextFieldGewicht_keyAdapter(this));

    jTextFieldGroesse.setText("170");
    jTextFieldGewicht.setText("66");
    jTextFieldAlter.setText("33");




    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setBorder(BorderFactory.createRaisedBevelBorder());
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setText("Bitte machen sie hier Angaben zu Ihrer Person");
    jLabel2.setText("");
    jLabel3.setText(" ");
    jLabel4.setText(" ");
    jTextFieldGroesse.setMaximumSize(new Dimension(10, 30));
    jTextFieldGroesse.addKeyListener(new JPanelWerte_jTextFieldGroesse_keyAdapter(this));
    jTextFieldAlter.setMaximumSize(new Dimension(10, 30));
    jTextFieldAlter.addKeyListener(new JPanelWerte_jTextFieldAlter_keyAdapter(this));
    //jButton1.setText("Weiter");
    jButtonWeiter.setHorizontalTextPosition(SwingConstants.CENTER);
    jButtonWeiter.setText("Weiter");
    jButtonWeiter.addActionListener(new JPanelWerte_jButtonWeiter_actionAdapter(this));
    xYLayout1.setWidth(614);
    xYLayout1.setHeight(395);
    jLabelTaetigkeit.setText("Taetigkeit");
    jLabelGeschl.setText("Geschlecht");
    jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabel5.setText("Ihr Body-Mass-Index: ");
    jLabel6.setText("<HTML> &lt;19 Untergewicht<br>19 - 25 Normalgewicht <br> &gt;25  " +
    "   Übergewicht </HMTL>");
    jLabelBMIWert.setFont(new java.awt.Font("SansSerif", 3, 16));
    jLabelBMIWert.setText("");
    jLabelEnergie.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabelEnergie.setText("Energiebedarf: ");
    jComboBoxTaetig.addItemListener(new JPanelWerte_jComboBoxTaetig_itemAdapter(this));
    jComboBoxGeschl.addItemListener(new JPanelWerte_jComboBoxGeschl_itemAdapter(this));
    this.add(jLabel2,  new XYConstraints(0, 0, -1, -1));
    this.add(jLabel3,  new XYConstraints(0, 280, -1, -1));
    this.add(jLabel4,  new XYConstraints(0, 163, -1, -1));
    this.add(jLabel1,   new XYConstraints(56, 39, 306, 39));
    this.add(jLabelGroesse, new XYConstraints(37, 101, 152, 34));
    this.add(jLabelGewicht, new XYConstraints(30, 141, 162, 34));
    this.add(jTextFieldAlter,  new XYConstraints(202, 191, 46, -1));
    this.add(jTextFieldGroesse, new XYConstraints(202, 109, 45, 22));
    this.add(jTextFieldGewicht,    new XYConstraints(202, 150, 45, 22));
    this.add(jLabelAlter,     new XYConstraints(30, 182, 132, 34));
    this.add(jComboBoxTaetig, new XYConstraints(130, 233, 122, 22));
    this.add(jComboBoxGeschl,     new XYConstraints(129, 279, 122, -1));
    this.add(jLabelGeschl, new XYConstraints(51, 277, 64, 23));
    this.add(jLabelTaetigkeit, new XYConstraints(53, 229, 64, 31));
    this.add(jLabel5,         new XYConstraints(311, 99, 157, 33));
    this.add(jLabel6,  new XYConstraints(356, 133, 147, 63));
    this.add(jLabelBMIWert,   new XYConstraints(468, 96, 120, 35));
    this.add(jLabelUnterUeber,   new XYConstraints(313, 215, 261, 52));
    this.add(jLabelEnergie,  new XYConstraints(54, 340, 207, 37));
    this.add(jButtonWeiter,     new XYConstraints(416, 341, -1, -1));
      updateScreen();
  }

  void jButtonWeiter_actionPerformed(ActionEvent e)
  {



    if( kalorien > 0 )
    {
      parentFrame_.closechild(1,this,kalorien,null);
    }
    else
    {
      JOptionPane.showMessageDialog( null, "Es müssen alle Felder ausgefüllt werden!" );
    }


  }


  private void updateScreen()
  {
    setCalories(0);
    int groesse = 0;
    int gewicht = 0;
    int alter = 0;
    int eb = 0; // energiebedarf
    double bmi = 0;
    jLabelUnterUeber.setText("");
    jLabelEnergie.setText("Energiebedarf:");
    jLabelBMIWert.setText("");

    try
    {
      if (jTextFieldGroesse.getText().length() >0)
        groesse = new Integer(jTextFieldGroesse.getText()).intValue();
      if (jTextFieldGewicht.getText().length() >0)
        gewicht = new Integer(jTextFieldGewicht.getText()).intValue();
      if (jTextFieldAlter.getText().length() >0)
        alter = new Integer(jTextFieldAlter.getText()).intValue();
    }
    catch(Exception e)
    {
      jLabelBMIWert.setText("");
      jLabelEnergie.setText("Energiebedarf:");
      groesse = 0;
      gewicht = 0;
      alter = 0;
    }

    if (groesse > 0 && gewicht >0)
    {
      bmi = Energiebedarf.bmi(gewicht,groesse);
      String str = new String(String.valueOf(bmi));
      int i;
      if (str.length()<5)
        jLabelBMIWert.setText(String.valueOf(bmi).substring(0,str.length()));
      else
        jLabelBMIWert.setText(String.valueOf(bmi).substring(0,5));
    }

    if (bmi<19 && bmi >1)
    {
      jLabelUnterUeber.setText("<html>Sie haben Untergewicht, für die Berechnung des Energiebedarfs wird deshalb das Normalgewicht (Körpergröße - 100) als Grundlage verwendet.</html>");
      gewicht = groesse -100;
    }

    if (bmi>25)
    {
      jLabelUnterUeber.setText("<html>Sie haben Übergewicht, für die Berechnung des Energiebedarfs wird deshalb das Normalgewicht (Körpergröße - 100) als Grundlage verwendet.</html>");
      gewicht = groesse -100;
    }

    if (groesse > 0 && gewicht >0 && alter > 0)
   {
     double taetig = 1;
     switch (jComboBoxTaetig.getSelectedIndex())
     {
       case 0: taetig = 0.5;
         break;
       case 1: taetig = 0.75;
         break;
       case 2: taetig = 1;
         break;
     }
     String geschl;
     if (jComboBoxGeschl.getSelectedIndex() == 0)
       geschl = "m";
     else
       geschl = "w";

     eb =  Energiebedarf.energiebedarf(gewicht, groesse,alter,taetig,geschl);
     setCalories(eb);
     jLabelEnergie.setText("Energiebedarf: " + eb + " Kcal");
   }

   // System.out.println(groesse + " " + gewicht);

  }
  void jTextFieldGroesse_keyReleased(KeyEvent e)
  {
  updateScreen();
  }

  void jTextFieldGewicht_keyReleased(KeyEvent e)
  {
    updateScreen();

  }

  void jTextFieldAlter_keyReleased(KeyEvent e)
  {
    updateScreen();

  }

  void jComboBoxTaetig_itemStateChanged(ItemEvent e)
  {
    updateScreen();
  }

  void jComboBoxGeschl_itemStateChanged(ItemEvent e)
  {
    updateScreen();

  }



}

class JPanelWerte_jButtonWeiter_actionAdapter implements java.awt.event.ActionListener {
  JPanelWerte adaptee;

  JPanelWerte_jButtonWeiter_actionAdapter(JPanelWerte adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonWeiter_actionPerformed(e);
  }
}

class JPanelWerte_jTextFieldGroesse_keyAdapter extends java.awt.event.KeyAdapter {
  JPanelWerte adaptee;

  JPanelWerte_jTextFieldGroesse_keyAdapter(JPanelWerte adaptee) {
    this.adaptee = adaptee;
  }
  public void keyReleased(KeyEvent e) {
    adaptee.jTextFieldGroesse_keyReleased(e);
  }
}

class JPanelWerte_jTextFieldGewicht_keyAdapter extends java.awt.event.KeyAdapter {
  JPanelWerte adaptee;

  JPanelWerte_jTextFieldGewicht_keyAdapter(JPanelWerte adaptee) {
    this.adaptee = adaptee;
  }
  public void keyReleased(KeyEvent e) {
    adaptee.jTextFieldGewicht_keyReleased(e);
  }
}

class JPanelWerte_jTextFieldAlter_keyAdapter extends java.awt.event.KeyAdapter {
  JPanelWerte adaptee;

  JPanelWerte_jTextFieldAlter_keyAdapter(JPanelWerte adaptee) {
    this.adaptee = adaptee;
  }
  public void keyReleased(KeyEvent e) {
    adaptee.jTextFieldAlter_keyReleased(e);
  }
}

class JPanelWerte_jComboBoxTaetig_itemAdapter implements java.awt.event.ItemListener {
  JPanelWerte adaptee;

  JPanelWerte_jComboBoxTaetig_itemAdapter(JPanelWerte adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.jComboBoxTaetig_itemStateChanged(e);
  }
}

class JPanelWerte_jComboBoxGeschl_itemAdapter implements java.awt.event.ItemListener {
  JPanelWerte adaptee;

  JPanelWerte_jComboBoxGeschl_itemAdapter(JPanelWerte adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    adaptee.jComboBoxGeschl_itemStateChanged(e);
  }
}
