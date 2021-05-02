package portfolio;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;


public class Hauptfenster extends JFrame {
    LPModell lpmodell;
    Portfolio portfolio;
    JPanel contentPane;
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JMenuItem jMenuFileOpen = new JMenuItem();
    JPanel auswahl = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    Dimension dimension1;
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JPanel einstellungsPanel = new JPanel();
    JTextField schrittTextField = new JTextField();
    JLabel jLabel1 = new JLabel();
    BorderLayout borderLayout2 = new BorderLayout();
    JButton jButton1 = new JButton();
    JRadioButton typ0RadioButton = new JRadioButton();
    JRadioButton typ4RadioButton = new JRadioButton();
    JRadioButton typ1RadioButton = new JRadioButton();

    boolean datenbasis = false;
    boolean rendite = false;
    int alteschrittweite;
    JLabel jLabel2 = new JLabel();
    JTextField anteilTextField = new JTextField();
    JPanel modellPanel = new JPanel();
    JScrollPane jScrollPane2 = new JScrollPane();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    BorderLayout borderLayout3 = new BorderLayout();
    JTextArea portfolioArea = new JTextArea();
    JTextArea lpmodellArea = new JTextArea();
    JTextField muMinTextField = new JTextField();
    JCheckBox muMinCheckBox = new JCheckBox();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel aktienLabel = new JLabel();
    JLabel kursLabel = new JLabel();
    JLabel berkLabel = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();



    /**Construct the frame*/
    public Hauptfenster() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**Component initialization*/
    private void jbInit() throws Exception  {
        //setIconImage(Toolkit.getDefaultToolkit().createImage(Hauptfenster.class.getResource("[Your Icon]")));
        contentPane = (JPanel) this.getContentPane();
        dimension1 = auswahl.getMinimumSize();
        contentPane.setLayout(borderLayout1);

        this.setSize(new Dimension(800, 600));
        this.setTitle("Portfolio Manager");
        jMenuFile.setText("Datei");
        jMenuFileExit.setText("Beenden");
        jMenuFileExit.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                jMenuFileExit_actionPerformed(e);
            }
        });
        jMenuHelp.setText("Hilfe");
        jMenuHelpAbout.setText("About");
        jMenuHelpAbout.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpAbout_actionPerformed(e);
            }
        });
        jMenuFileOpen.setText("Öffnen");
        jMenuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuFileOpen_actionPerformed(e);
            }
        });
        contentPane.setBackground(UIManager.getColor("activeCaptionBorder"));
        contentPane.setPreferredSize(new Dimension(600, 400));
        contentPane.setToolTipText("");
        auswahl.setBackground(Color.lightGray);
        auswahl.setLayout(borderLayout2);

        schrittTextField.setMaximumSize(new Dimension(4, 21));
        schrittTextField.setPreferredSize(new Dimension(40, 21));
        schrittTextField.setText("1");
        schrittTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("Schrittweite");
        einstellungsPanel.setLayout(gridBagLayout1);
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });
        typ0RadioButton.setText("Typ0");
        typ0RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                typ0RadioButton_actionPerformed(e);
            }
        });
        typ0RadioButton.setSelected(true);

        typ4RadioButton.setText("Typ4");
        typ4RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                typ4RadioButton_actionPerformed(e);
            }
        });
        typ1RadioButton.setText("Typ1");
        typ1RadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                typ1RadioButton_actionPerformed(e);
            }
        });
        jLabel2.setText("Anteilsbegrenzung");
        modellPanel.setLayout(borderLayout3);
        jScrollPane1.setBorder(null);
        jScrollPane1.setDoubleBuffered(true);
        jScrollPane2.setBorder(null);
        jScrollPane2.setDoubleBuffered(true);
        portfolioArea.setDoubleBuffered(true);
        lpmodellArea.setDoubleBuffered(true);
        muMinCheckBox.setEnabled(false);
        muMinCheckBox.setText("MuMin");
        muMinTextField.setEnabled(false);
        muMinTextField.setMaximumSize(new Dimension(4, 21));
        muMinTextField.setPreferredSize(new Dimension(40, 21));
        muMinTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("Aktien:");
        jLabel4.setText("Kurse:");
        jLabel5.setText("Renditen:");
        aktienLabel.setText("-");
        berkLabel.setText("-");
        kursLabel.setText("-");
        anteilTextField.setMaximumSize(new Dimension(4, 21));
        anteilTextField.setPreferredSize(new Dimension(40, 21));
        anteilTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        jMenuFile.add(jMenuFileOpen);
        jMenuFile.add(jMenuFileExit);
        jMenuHelp.add(jMenuHelpAbout);
        jMenuBar1.add(jMenuFile);
        jMenuBar1.add(jMenuHelp);
        contentPane.add(auswahl, BorderLayout.CENTER);
        auswahl.add(modellPanel, BorderLayout.CENTER);
        modellPanel.add(jTabbedPane1,  BorderLayout.CENTER);
        jTabbedPane1.add(jScrollPane1, "Portfolio");
        jScrollPane1.getViewport().add(portfolioArea, null);
        jTabbedPane1.add(jScrollPane2, "LP-Modell");
        auswahl.add(einstellungsPanel, BorderLayout.NORTH);
        jScrollPane2.getViewport().add(lpmodellArea, null);
        this.setJMenuBar(jMenuBar1);
        typ0RadioButton.setSelected(true);
        buttonGroup1.add(typ0RadioButton);
        buttonGroup1.add(typ1RadioButton);
        buttonGroup1.add(typ4RadioButton);
        einstellungsPanel.add(typ0RadioButton,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 70, 0, 9), 36, 4));
        einstellungsPanel.add(jLabel1,   new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(11, 44, 0, 0), 5, 8));
        einstellungsPanel.add(typ1RadioButton,  new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 70, 0, 0), 45, 3));
        einstellungsPanel.add(jLabel2,  new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 7, 0, 0), 7, -1));
        einstellungsPanel.add(anteilTextField,        new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 0, 0, 0), 45, 0));
        einstellungsPanel.add(muMinTextField,     new GridBagConstraints(4, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 44, 1));
        einstellungsPanel.add(muMinCheckBox,  new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(7, 49, 0, 0), 0, 0));
        einstellungsPanel.add(typ4RadioButton,  new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 70, 0, 34), 11, 1));
        einstellungsPanel.add(jLabel5,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 17, 12, 0), 5, 0));
        einstellungsPanel.add(aktienLabel,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 0, 7, 34), 66, 0));
        einstellungsPanel.add(kursLabel,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 0, 6, 0), 75, 0));
        einstellungsPanel.add(berkLabel,    new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 12, 14), 62, 0));
        einstellungsPanel.add(jLabel3,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 31, 7, 0), 5, 0));
        einstellungsPanel.add(jLabel4,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4, 21, 6, 4), 2, 0));
        einstellungsPanel.add(schrittTextField,    new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(13, 0, 0, 15), 45, 0));
        einstellungsPanel.add(jButton1, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(9, 98, 2, 27), 76, -4));
        anteilTextField.setText("0.40");
    }
    /**File | Exit action performed*/
    public void jMenuFileExit_actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    /**Help | About action performed*/
    public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
        Hauptfenster_AboutBox dlg = new Hauptfenster_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.show();
    }
    /**Overridden so we can exit when window is closed*/
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            jMenuFileExit_actionPerformed(null);
        }
    }

    void jMenuFileOpen_actionPerformed(ActionEvent e)
    {
        File f = new File(System.getProperty("user.dir"));
        JFileChooser chooser = new JFileChooser();

        chooser.setFileSelectionMode(chooser.FILES_ONLY);
        chooser.setFileFilter(new PortfolioFileFilter());
        chooser.setCurrentDirectory(f);


        int retval = chooser.showOpenDialog(this);
	    if(retval == JFileChooser.APPROVE_OPTION)
        {
	        File theFile = chooser.getSelectedFile();
            Aktienparser a = new Aktienparser();
            portfolio = a.parseFile(theFile);
            rendite = false;
            datenbasis = true;
            aktienLabel.setText(Integer.toString(portfolio.getAktienListe().size()));
            kursLabel.setText(Integer.toString(((Aktie)portfolio.getAktienListe().get(0)).getLaenge()));
        }
	    else if(retval == JFileChooser.CANCEL_OPTION) {
	        JOptionPane.showMessageDialog(this, "Operation abgebrochen. Es wurde keine Datei ausgewählt.");
	    } else if(retval == JFileChooser.ERROR_OPTION) {
	        JOptionPane.showMessageDialog(this, "Ein Fehler ist aufgetreten. Es wurde keine Datei ausgewählt.");
	    } else {
	        JOptionPane.showMessageDialog(this, "Unknown operation occured.");
	    }

    }


    void jButton1_actionPerformed(ActionEvent e) {

        if(datenbasis)
        {
            portfolioArea.setText("");
            lpmodellArea.setText("");
            int t = Integer.parseInt(schrittTextField.getText());
            if(alteschrittweite!= t)
            {
                rendite = false;
            }

            portfolio.setSchrittweite(t);
            alteschrittweite=t;

            if(typ0RadioButton.isSelected())
            {
                lpmodell = new Typ0Modell();
            }
            else if(typ1RadioButton.isSelected())
            {
                lpmodell = new Typ1Modell();
            }
            else if(typ4RadioButton.isSelected())
            {
                lpmodell = new Typ4Modell();
            }

            // Falls noch nicht geschehen Renditen berechnen
            if(!rendite)
            {
                portfolio.berechneRendite();
                rendite = true;
                berkLabel.setText(Integer.toString(((Aktie)portfolio.getAktienListe().get(0)).getLaengeEinzelRendite()));
            }


            double anteilsbegrenzung = Double.parseDouble(anteilTextField.getText());
            lpmodell.setAnteilsBegrenzung(anteilsbegrenzung);

            if(muMinCheckBox.isEnabled() && muMinCheckBox.isSelected())
            {
                double mumin = Double.parseDouble(muMinTextField.getText());
                lpmodell.setMuMin(mumin);
            }




            SolverAufruf solver = new SolverAufruf();

            Vector modell = lpmodell.erstelleModell(portfolio.getAktienListe());

            // Ausgabe des Ergebnisses und des LP Modells
            lpmodellArea.setText("");
            Iterator iterator = modell.iterator();
            while(iterator.hasNext())
            {
                String temp = (String)iterator.next()+"\n";
                lpmodellArea.append(temp);
            }

            Vector ergebnis = solver.starteSolver(modell);
            portfolioArea.setText("");
            Iterator iterator2 = ergebnis.iterator();
            while(iterator2.hasNext())
            {
                String temp = (String)iterator2.next()+"\n";
                portfolioArea.append(temp);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "Sie müssen erst eine Datenbasis laden.");

    }

    void typ0RadioButton_actionPerformed(ActionEvent e)
    {
        muMinCheckBox.setEnabled(false);
        muMinTextField.setEnabled(false);
    }

    void typ1RadioButton_actionPerformed(ActionEvent e)
    {
        muMinCheckBox.setEnabled(true);
        muMinTextField.setEnabled(true);
    }

    void typ4RadioButton_actionPerformed(ActionEvent e)
    {
        muMinCheckBox.setEnabled(true);
        muMinTextField.setEnabled(true);
    }


}