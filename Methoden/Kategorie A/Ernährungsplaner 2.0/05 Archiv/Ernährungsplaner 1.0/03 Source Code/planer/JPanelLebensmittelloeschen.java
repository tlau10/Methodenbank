package planer;
import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.util.Vector;
import org.jdom.Element;
import java.awt.event.*;

public class JPanelLebensmittelloeschen extends JPanel {
 Vector allEatables_;
  mainFrame parentFrame_;
  ApplicationDiaetplaner ap_;
  JLabel jLabelAuswaehlenText = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JList jListZuLoeschen;
  JButton jButtonLoeschen = new JButton();
  JButton jButtonSchliessen = new JButton();
  JListModelFactory myJListModelFactory;
  public JPanelLebensmittelloeschen(mainFrame parentFrame, ApplicationDiaetplaner ap,Vector allEatables) {
    try {
      allEatables_=allEatables;
      parentFrame_=parentFrame;
      ap_=ap;
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    myJListModelFactory= new JListModelFactory(ap_.getAllEatables());
    jListZuLoeschen= new JList(myJListModelFactory.createAll());
    jListZuLoeschen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
    JScrollPane myJScrollPane1=new JScrollPane(jListZuLoeschen);

    jLabelAuswaehlenText.setBorder(BorderFactory.createRaisedBevelBorder());
    jLabelAuswaehlenText.setToolTipText("");
    jLabelAuswaehlenText.setHorizontalAlignment(SwingConstants.CENTER);
    jLabelAuswaehlenText.setText("Wählen sie das zu löschende Element aus");
    this.setLayout(xYLayout1);
    xYLayout1.setWidth(523);
    xYLayout1.setHeight(477);
    jButtonLoeschen.setText("Löschen");
    jButtonLoeschen.addActionListener(new JPanelLebensmittelloeschen_jButtonLoeschen_actionAdapter(this));
    jButtonSchliessen.setText("Schliessen");
    jButtonSchliessen.addActionListener(new JPanelLebensmittelloeschen_jButtonSchliessen_actionAdapter(this));
    this.add(jButtonLoeschen,      new XYConstraints(220, 400, 90, -1));
    this.add(myJScrollPane1,      new XYConstraints(140, 70, 260, 300));
    this.add(jLabelAuswaehlenText,    new XYConstraints(140, 30, 260, -1));
    this.add(jButtonSchliessen,           new XYConstraints(310, 400, 90, -1));
  }

  void jButtonLoeschen_actionPerformed(ActionEvent e)
  {

    int sel = jListZuLoeschen.getSelectedIndex();
    ListModel lm = jListZuLoeschen.getModel();
    Element elem;
    int ID;
    Integer temp;
    String value;
    String strID;

    value = (String) lm.getElementAt(sel);
    strID = value.substring(0, value.indexOf(")"));
    temp = new Integer(strID);
    ID = temp.intValue();

    elem = (Element) ap_.getAllEatables().get(ID);

    try {
      if(ap_.deleteEatable(elem) == true)
      {
        JOptionPane.showMessageDialog(null,
                                      "Das Lebensmittel " + value + " wurde erfolgreich geloescht!");
        myJListModelFactory = new JListModelFactory(ap_.getAllEatables());
        jListZuLoeschen.setListData(myJListModelFactory.createAll());
      }
      else
      {
        JOptionPane.showMessageDialog(null,
                                      "Das Lebensmittel " + value + " konnte nicht gelöscht werden - bitte beachten: In jeder Gruppe muss ein Lebensmittel vorhanden sein!");
      }
    }
    catch (DiaetplanerException ex) {
      ex.printStackTrace();
    }

  }






  void jButtonSchliessen_actionPerformed(ActionEvent e)
  {
    parentFrame_.closechild(5,this,0,null);

  }

}

class JPanelLebensmittelloeschen_jButtonLoeschen_actionAdapter implements java.awt.event.ActionListener {
  JPanelLebensmittelloeschen adaptee;

  JPanelLebensmittelloeschen_jButtonLoeschen_actionAdapter(JPanelLebensmittelloeschen adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonLoeschen_actionPerformed(e);
  }
}

class JPanelLebensmittelloeschen_jButtonSchliessen_actionAdapter implements java.awt.event.ActionListener {
  JPanelLebensmittelloeschen adaptee;

  JPanelLebensmittelloeschen_jButtonSchliessen_actionAdapter(JPanelLebensmittelloeschen adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonSchliessen_actionPerformed(e);
  }
}
