package hotelbelegung;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;


/**
 * Überschrift:   Hotelbelegung
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author Volker Wohlleber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003 
 */

public class FrameAlternative extends JFrame {
  private DateFormat outFormat = new SimpleDateFormat("dd.MM.yyyy");
  Date tmpDatum;
  int anzTage;
  int gesPreis;
  String resDatum;
  Manager manager;
  Frame1 frame;
  Belegung belegung;

  JPanel contentPane;
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField4 = new JTextField();
  JTextField jTextField5 = new JTextField();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  Border border1;
  TitledBorder titledBorder1;

  public FrameAlternative(Frame1 frame, Manager manager, Belegung belegung, Date wunschDatum, Date anfang, int buchung, int tagesPreis, int kategorie) {
    try {
      this.frame = frame;
      this.manager = manager;
      this.belegung = belegung;
      jbInit(wunschDatum, anfang, buchung, tagesPreis, kategorie);
      resDatum = ""+outFormat.format(anfang);
      tmpDatum = anfang;
      anzTage = buchung;
      gesPreis = tagesPreis*anzTage;
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit(Date wunschDatum, Date anfang, int buchung, int tagesPreis, int kategorie) throws Exception {
    contentPane = (JPanel) this.getContentPane();
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder1 = new TitledBorder(border1,"Buchungsvorschlag");
    this.setSize(new Dimension(440, 380));
    this.setTitle(" Buchungsvorschlag");
    jPanel1.setLayout(null);
    jPanel2.setBorder(titledBorder1);
    jPanel2.setBounds(new Rectangle(16, 16, 395, 260));
    jPanel2.setLayout(null);
    jLabel1.setText("Gewünschtes Anreisedatum:");
    jLabel1.setBounds(new Rectangle(20, 35, 174, 22));
    jLabel2.setText("Beginn Alternative:");
    jLabel2.setBounds(new Rectangle(83, 75, 156, 23));
    jLabel3.setText("Buchungsdauer:");
    jLabel3.setBounds(new Rectangle(92, 118, 106, 20));
    jLabel4.setText("Zimmerpreis pro Tag:");
    jLabel4.setBounds(new Rectangle(65, 160, 137, 23));
	jLabel5.setText("Zimmerkategorie:");
	jLabel5.setBounds(new Rectangle(75, 201, 137, 23));
    jTextField1.setText(" " + outFormat.format(wunschDatum));
    jTextField1.setEditable(false);
    jTextField1.setBounds(new Rectangle(210, 33, 148, 29));
    jTextField2.setText(" " + outFormat.format(anfang));
    jTextField2.setEditable(false);
    jTextField2.setBounds(new Rectangle(210, 72, 148, 30));
    jTextField3.setText(" " + buchung);
    jTextField3.setEditable(false);
    jTextField3.setBounds(new Rectangle(210, 115, 149, 30));
    jTextField4.setText(" " + tagesPreis);
    jTextField4.setEditable(false);
    jTextField4.setBounds(new Rectangle(210, 158, 150, 29));
	jTextField5.setText(" " + kategorie);
	jTextField5.setEditable(false);
	jTextField5.setBounds(new Rectangle(210, 201, 150, 29));
    jButton1.setFont(new java.awt.Font("Dialog", 1, 14));
    jButton1.setForeground(new Color(0, 189, 0));
    jButton1.setText("Annehmen");
    jButton1.setBounds(new Rectangle(19, 290, 165, 35));
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 1, 14));
    jButton2.setForeground(Color.red);
    jButton2.setText("Ablehnen");
    jButton2.setBounds(new Rectangle(234, 290, 174, 36));
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton2_actionPerformed(e);
      }
    });
    contentPane.add(jPanel1, BorderLayout.CENTER);
    jPanel2.add(jLabel1, null);
    jPanel2.add(jLabel2, null);
	jPanel2.add(jLabel3, null);
    jPanel2.add(jLabel4, null);
	jPanel2.add(jLabel5, null);
    jPanel2.add(jTextField1, null);
    jPanel2.add(jTextField2, null);
	jPanel2.add(jTextField3, null);
    jPanel2.add(jTextField4, null);
	jPanel2.add(jTextField5, null);
	jPanel1.add(jPanel2, null);
    jPanel1.add(jButton1, null);
    jPanel1.add(jButton2, null);
  }
    /**Alternative annehmen*/
    void jButton1_actionPerformed(ActionEvent e) {
      this.dispose();
      JOptionPane.showMessageDialog(null, "Check-in am " + resDatum + "  -  Aufenthaltsdauer " + anzTage + " Tag(e)  -  Gesamtpreis € " + gesPreis, "Alternative Reservierung vorgenommen" ,1);
      frame.getRegisterJTabbedPane().setSelectedComponent(frame.getBelegungJPanel());
      
      //frame.RegisterJTabbedPane.addNotify();

      // Zimmerbelegung wird bei der Buchung für jeden Tag hochgezählt
      for(int i=0; i < anzTage; i++) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(tmpDatum);
        belegung.setBuchung(calendar,1);
        tmpDatum = manager.setDatum(tmpDatum, 1);
      }
    }

    /**Alternative ablehnen*/
    void jButton2_actionPerformed(ActionEvent e) {
      this.dispose();
      manager.changeModel();
    }
}