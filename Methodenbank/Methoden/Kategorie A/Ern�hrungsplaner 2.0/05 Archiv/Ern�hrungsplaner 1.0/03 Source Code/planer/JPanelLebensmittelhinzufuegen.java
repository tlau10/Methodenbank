package planer;
import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.util.Vector;
import org.jdom.Element;
import java.awt.event.*;

public class JPanelLebensmittelhinzufuegen extends JPanel {
  JLabel jLabelEingabe = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel jLabelName = new JLabel();
  JTextField jTextFieldname = new JTextField();
  JLabel jLabelGruppe = new JLabel();
  JLabel jLabelLebensmittelgruppe = new JLabel();
  JComboBox jComboBoxGruppe;
  JLabel jLabelMenge = new JLabel();
  JTextField jTextFieldMenge = new JTextField();
  JLabel jLabelKalorien = new JLabel();
  JTextField jTextFieldKalorien = new JTextField();

 Vector allEatables_;
  mainFrame parentFrame_;
  ApplicationDiaetplaner ap_;
  JButton jButtonUebernehmen = new JButton();
  JButton jButtonVerwerfen = new JButton();
  JButton jButtonSchliessen = new JButton();



  public JPanelLebensmittelhinzufuegen(mainFrame parentFrame, ApplicationDiaetplaner ap,Vector allEatables) {
    try {
      parentFrame_=parentFrame;
      ap_=ap;
      allEatables_=allEatables;
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    jComboBoxGruppe= new JComboBox(ap_.getGruppenName());
    jComboBoxGruppe.setSelectedIndex(0);

    jLabelEingabe.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabelEingabe.setBorder(BorderFactory.createRaisedBevelBorder());
    jLabelEingabe.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelEingabe.setText("Biite geben Sie die Daten des neuen Elements ein");
    this.setLayout(xYLayout1);
    jLabelName.setBorder(BorderFactory.createEtchedBorder());
    jLabelName.setMaximumSize(new Dimension(34, 15));
    jLabelName.setToolTipText("");
    jLabelName.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelName.setText("Name");
    jTextFieldname.setText("");
    jLabelGruppe.setBorder(BorderFactory.createEtchedBorder());
    jLabelGruppe.setText("Lebensmittelgruppe");
    jLabelLebensmittelgruppe.setBorder(BorderFactory.createEtchedBorder());
    jLabelLebensmittelgruppe.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelLebensmittelgruppe.setText("Lebensmittelgruppe");
    jComboBoxGruppe.setEditable(false);
    jLabelMenge.setBorder(BorderFactory.createEtchedBorder());
    jLabelMenge.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelMenge.setText("Menge (gr/ml) )");
    jTextFieldMenge.setText("");
    jLabelKalorien.setBorder(BorderFactory.createEtchedBorder());
    jLabelKalorien.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelKalorien.setText("Kalorien");
    jTextFieldKalorien.setText("");
    jButtonUebernehmen.setText("Übernehmen");
    jButtonUebernehmen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonUebernehmen_actionPerformed(e);
      }
    });
    jButtonVerwerfen.setActionCommand("jButton1");
    jButtonVerwerfen.setText("Verwerfen");
    jButtonVerwerfen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonVerwerfen_actionPerformed(e);
      }
    });
    jButtonSchliessen.setText("Schliessen");
    jButtonSchliessen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonSchliessen_actionPerformed(e);
      }
    });
    this.add(jLabelEingabe,  new XYConstraints(60, 34, 433, 28));
    this.add(jTextFieldname,  new XYConstraints(199, 132, 159, -1));
    this.add(jLabelName, new XYConstraints(143, 134, 47, 21));
    this.add(jLabelLebensmittelgruppe,   new XYConstraints(84, 177, 105, 21));
    this.add(jComboBoxGruppe, new XYConstraints(202, 176, 157, -1));
    this.add(jLabelMenge,  new XYConstraints(85, 222, 101, -1));
    this.add(jTextFieldMenge, new XYConstraints(201, 222, 154, -1));
    this.add(jLabelKalorien, new XYConstraints(87, 270, 97, -1));
    this.add(jTextFieldKalorien,   new XYConstraints(202, 268, 152, -1));
    this.add(jButtonSchliessen, new XYConstraints(444, 354, -1, -1));
    this.add(jButtonUebernehmen, new XYConstraints(336, 355, -1, -1));
    this.add(jButtonVerwerfen, new XYConstraints(232, 355, -1, -1));




  }

  public Element Entries2Element()
  {
    int gruppenID;
    String name, amount, calories;
    name = jTextFieldname.getText();
    gruppenID = jComboBoxGruppe.getSelectedIndex();
    amount = jTextFieldMenge.getText();
    calories = jTextFieldKalorien.getText();
    return ap_.newEatable(name, gruppenID, amount, calories);
  }

  void clearentries()
  {
         jTextFieldname.setText("");
         jComboBoxGruppe.setSelectedIndex(0);
         jTextFieldMenge.setText("");
         jTextFieldKalorien.setText("");

  }

  boolean checkString4Int(String testString)
  {
    char cTmp;
     for (int i = 0; i < testString.length(); i++)
     {
        cTmp = testString.charAt(i);
        String c=String.valueOf(cTmp);

        if ( (c.equals("0")) || (c.equals("1")) || (c.equals("2")) || (c.equals("3")) ||
             (c.equals("4")) || (c.equals("5")) || (c.equals("6")) || (c.equals("7")) ||
             (c.equals("8")) || (c.equals("9")) )
        {
          // " guter Fall "
        }
        else
        {
          return false;
        }
      }
      return true;
  }


  void jButtonVerwerfen_actionPerformed(ActionEvent e)
  {
         this.clearentries();
  }

  boolean alreadyexists(Element elem)
  {
    for (int i = 0; i < ap_.getAllEatables().size(); i++)
    {
      Element myElement = (Element) ap_.getAllEatables().get(i);

      if(myElement.getChild("Name").getText().toLowerCase().equals(elem.getChild("Name").getText().toLowerCase()))
         {
           return true;
      }

    }
    return false;
  }

  void jButtonUebernehmen_actionPerformed(ActionEvent e)
  {
    if ( (!jTextFieldname.getText().equals("")) && (!jTextFieldMenge.getText().equals("")) && (!jTextFieldKalorien.getText().equals("")))
    {
      if( ( checkString4Int( jTextFieldMenge.getText() ) ) && ( checkString4Int( jTextFieldKalorien.getText() ) ) )
      {
        try
        {
          Element newElem=this.Entries2Element();
          if(!(this.alreadyexists(newElem)))
          {
             ap_.addEatable(newElem);

             JOptionPane.showMessageDialog(null,
                                           "Lebensmittel " +
                                           jTextFieldname.getText() +
                                           " erfasst!");
          }
          else
          {
          JOptionPane.showMessageDialog(null,"Lebensmittel existiert schon!");
          }
        }
        catch (DiaetplanerException e1) {
          e1.getMessage();
        }
      }
      else
      {
        JOptionPane.showMessageDialog(null,"Nur ganze Zahlen als Eingabe erlaubt!");
      }
    }
    else
    {
      JOptionPane.showMessageDialog(null,"Es müssen alle Felder ausgefüllt werden!");
    }

  }

  void jButtonSchliessen_actionPerformed(ActionEvent e)
  {
    this.clearentries();
    parentFrame_.closechild(4,this,0,null);
  }



}








