/*
 * main.java
 *
 * Created on 31. März 2004, 17:37
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import javax.swing.event.*;
import constants.*;
import model.eingabe.*;
import model.lpBuilder.*;
import model.solverCaller.*;
import view.listener.*;
import view.fileFilter.*;
import view.exception.*;



/**
 *
 * @author  hmaass, choefel
 */

public class Main extends javax.swing.JFrame {
    /** Creates new form main */
    public Main() {
        // set Windows UI
        try {
            String lookandfeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookandfeel);
        } catch (Exception e) {
        }
        // init WSYIWYG Components
        initComponents();
        jLabelBerechnung.setVisible(false);
        jPanel_Answer.setVisible(false);
        jPanel_Answer.setLayout(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("images/symbol.gif"));
        
        // create EingabeBuilder
        eb = new EingabeBuilder();
        
        // get Properties
        try {
            InputStream propertiesStream = new FileInputStream("solver.properties");
            solverProperties = new Properties();
            solverProperties.load(propertiesStream);
        } catch (Exception e) {
            System.err.println(e);
        }
        
        // create Tables
        createAnforderungTable();
        createSortenTable();
        
        // add Listener
        anforderungTableListener = new AnforderungTableListener(jTableSorten);
        TableModel tm = jTableAnforderung.getModel();
        tm.addTableModelListener(anforderungTableListener);
        
        // resize Frame
        this.setSize(Constants.SCRWIDTH,Constants.SCRHEIGHT);
        
    }
    
    private void startSolver(Eingabe eingabe) {
        int errorcode       = 0;
        String errorMessage = "";
        
        // Alte Textfields löschen.
        jPanel_Answer.removeAll();
        // LP-Ansatz!
        String [] lpModell = null;
        
        // Pfade setzen!
        String eingabeDatei = "lp_ein.lp";
        String ausgabeDatei = "lp_aus.txt";
        String workDir      = null;
        String solverPfad   = null;
        String solverName   = null;
        
        SolverDriver driver  = null;
        LPBuilder    builder = null;
        // Welcher Solver wurde ausgewählt?
        if (jRadioButtonLP.isSelected()) {
            solverPfad = solverProperties.getProperty("LP_PATH");
            solverName = solverProperties.getProperty("LP_FILENAME");
            workDir    = solverProperties.getProperty("LP_WORKING_PATH");
            boolean ganzzahlig = false;
            if (jCheckBoxGanzzahlig.getSelectedObjects() != null) {
                ganzzahlig = true;
            }
            builder = new LPBuilderLPSolve();
            lpModell = builder.createLPModell(eingabe,ganzzahlig);
            
            // Neues LP-Solver Objekt anlegen.
            LPSolver lpSolver = new LPSolver(eingabeDatei,
            ausgabeDatei,
            workDir,
            solverPfad,
            solverName,
            new LPSolveParser());
            // Das Treiberobjekt anlegen. Dadurch wird der Solver gestartet. Die
            // Ergebnisse können über get-Methoden ausgelesen werde.
            driver = new SolverDriver( lpSolver, lpModell.length, lpModell);
            
        } else if (jRadioButtonXA.isSelected()) {
            solverPfad = solverProperties.getProperty("XA_PATH");
            solverName = solverProperties.getProperty("XA_FILENAME");
            workDir    = solverProperties.getProperty("XA_PATH");
            builder    = new LPBuilderXA();
            lpModell   = builder.createLPModell(eingabe,false);
            // Neues LP-Solver Objekt anlegen.
            XASolver xaSolver = new XASolver(eingabeDatei,
            ausgabeDatei,
            workDir,
            solverPfad,
            solverName,
            new XAParser());
            
            // Das Treiberobjekt anlegen. Dadurch wird der Solver gestartet. Die
            // Ergebnisse können über get-Methoden ausgelesen werde.
            driver = new SolverDriver( xaSolver, lpModell.length, lpModell, eingabe.getSortenSize());
            System.out.println(eingabe.getSortenSize());
        }
        
        
        
        // Fehlercode auslesen.
        // falls errorcode = 0 -> kein Fehler.
        // falls errorcode < 0 -> Fehler! Fehlertext ist gesetzt.
        // falls errorcode > 0 -> 1 : infeasable
        //                        2 : unbounded
        
        errorcode = driver.getErrorCode();
        errorMessage = driver.getErrorMsg();
        //errorcode = 1;
        
        Font myFont = new java.awt.Font("Arial", Font.PLAIN, 14);
        Font myFont12 = new java.awt.Font("Arial", Font.PLAIN, 12);
        Font myError  = new java.awt.Font("Arial", Font.BOLD,12);
        Color color_textFieldBackground = Color.red;
        JTextArea hTextArea = new JTextArea();
        hTextArea.setBackground(color_textFieldBackground);
        hTextArea.setFont(myError);
        
        hTextArea.setBounds(40, 20, 600, 120);
        
        if (errorcode == 1) {
            hTextArea.setText(Constants.ERROR_LP_INFEASABLE);
            System.out.println(Constants.ERROR_LP_INFEASABLE);
            jPanel_Answer.add(hTextArea);
        } else if (errorcode == 2) {
            hTextArea.setText(Constants.ERROR_LP_UNBOUNDED);
            System.out.println(Constants.ERROR_LP_UNBOUNDED);
            jPanel_Answer.add(hTextArea);
        } else if (errorcode < 0) {
            hTextArea.setText("Fehler:" + errorMessage + ".");
            System.out.println("Fehler:" + errorMessage);
            jPanel_Answer.add(hTextArea);
        } else {
            // Bearbeiten des Ergebnisses:
            color_textFieldBackground = new Color(236,233,216);
            Vector[] primalResult = driver.getPrimalResult();
            double optimum = driver.getOptimum();
            JTextField hTextField = new JTextField();
            hTextField.setBorder(BorderFactory.createEmptyBorder());
            hTextField.setBackground(color_textFieldBackground);
            hTextField.setFocusable(false);
            hTextField.setFont(myFont);
            hTextField.setText("L\u00f6sung des LP-Ansatzes");
            hTextField.setBounds(300, 10, 300, 20);
            jPanel_Answer.add(hTextField);
            
            hTextField = new JTextField();
            hTextField.setBorder(BorderFactory.createEmptyBorder());
            hTextField.setBackground(color_textFieldBackground);
            hTextField.setFont(myFont12);
            System.out.println("---------------------------------");
            int i;
            Vector x = (Vector)primalResult[0];
            Vector werte = (Vector)primalResult[1];
            int xVal = 20;
            for (i=0; i < x.size(); i++) {
                if (i % 10 == 0) {
                    xVal += 40;
                }
                System.out.println(x.get(i) + ": " + werte.get(i));
                hTextField = new JTextField();
                hTextField.setBorder(BorderFactory.createEmptyBorder());
                hTextField.setBackground(color_textFieldBackground);
                hTextField.setText("Sorte "+(i+1)+ " : " + werte.get(i));
                hTextField.setBounds(xVal, (i) * 20, 200, 20);
                jPanel_Answer.add(hTextField);
            }
            
            String sOptimum = "minimale Kosten: " + optimum;
            hTextField = new JTextField();
            hTextField.setBorder(BorderFactory.createEmptyBorder());
            hTextField.setBackground(color_textFieldBackground);
            hTextField.setText(sOptimum);
            hTextField.setFont(myFont12);
            hTextField.setBounds(300, ((i+4)/2) * 20, 400, 20);
            jPanel_Answer.add(hTextField);
            jPanel_Answer.repaint();
            System.out.println(sOptimum);
            
            
        }
    }
    
    private void loadAnforderungTable(Eingabe eingabe) {
        Vector columnNames = new Vector();
        columnNames.add("Name");
        columnNames.add("Wert");
        
        Vector data = new Vector();
        Iterator it = eingabe.getAnforderungen();
        while (it.hasNext()) {
            Anforderung a = (Anforderung) it.next();
            Vector row = new Vector();
            row.add(a.getName());
            row.add(Double.toString(a.getWert()));
            data.add(row);
            
        }
        DefaultTableModel tm = new DefaultTableModel(data,columnNames);
        jTableAnforderung.setModel(tm);
        tm.addTableModelListener(anforderungTableListener);
        formatAnforderungTable();
    }
    
    /** setzt die Breite der ersten Spalte der Anforderungstabelle auf 150 Pixel. */
    private void formatAnforderungTable() {
        TableColumn column = jTableAnforderung.getColumnModel().getColumn(0);
        column.setPreferredWidth(150);
    }
    /** setzt die Breite der ersten Spalte der Sortentabelle auf 150 Pixel. */
    private void formatSortenTable() {
        TableColumn column = jTableSorten.getColumnModel().getColumn(0);
        column.setPreferredWidth(150);
        TableColumn column2 = jTableSorten.getColumnModel().getColumn(1);
        column2.setPreferredWidth(100);
    }
    
    /** füllt die Sortentabelle mit den Inhalten aus dem Eingabe Objekt
     * @param eingabe Die Eingabe, die dargestellt werden soll.
     */
    private void loadSortenTable(Eingabe eingabe) {
        Vector columnNames = new Vector();
        
        /** fülle die Anforderungen */
        columnNames.add("Eigenschaft");
        
        Vector data = new Vector();
        
        Iterator itAnforderungen = eingabe.getAnforderungen();
        while (itAnforderungen.hasNext()) {
            Anforderung a = (Anforderung) itAnforderungen.next();
            Vector row = new Vector();
            row.add(a.getName());
            data.add(row);
        }
        Vector preis = new Vector();
        preis.add("Preis");
        data.add(preis);
        
        DefaultTableModel tm = new DefaultTableModel(data, columnNames);
        Iterator it = eingabe.getSorten();
        int sorteCounter = 1;
        /** iteriere durch die Sorten */
        while (it.hasNext()) {
            Vector column = new Vector();
            Sorte s    = (Sorte) it.next();
            Iterator itErfuellung = s.getErfuellungAnforderungen();
            while (itErfuellung.hasNext()) {
                ErfuellungAnforderung ea = (ErfuellungAnforderung) itErfuellung.next();
                column.add(Double.toString(ea.getWert()));
                
            }
            column.add(Double.toString(s.getPreis()));
            // Vorsicht: SorteID != Angezeigte Nummer
            tm.addColumn("Anteile in Sorte " + sorteCounter,column);
            TableColumn columnx = jTableSorten.getColumnModel().getColumn(sorteCounter);
            columnx.setPreferredWidth(100);
            sorteCounter++;
        }
        jTableSorten.setModel(tm);
        formatSortenTable();
    }
    /* initialisiert initAnforderungColumnNames und initAnforderungData.
     * Diese Methode wird benötigt, wenn die JTable neu initialisiert werden soll.<br>
     * (z.B. bei Klick auf Datei > Neu)
     */
    private void initAnforderungVectors() {
        initAnforderungColumnNames = new Vector();
        initAnforderungColumnNames.add("Eigenschaften");
        initAnforderungColumnNames.add("mind. Anteil");
        
        // Beispieldaten für Testzwecke
       
        initAnforderungData = new Vector();
        Vector row1 = new Vector();
        row1.add("");
        row1.add(Double.toString(0.0));
       
        initAnforderungData.add(row1);
        /*
        Vector row2 = new Vector();
        row2.add("Kalzium (in mg)");
        row2.add(Double.toString(2.0));
        initAnforderungData.add(row2);*/
    }
  /* initialisiert initAnforderungColumnNames und initAnforderungData.
   * Diese Methode wird benötigt, wenn die JTable neu initialisiert werden soll.<br>
   * (z.B. bei Klick auf Datei > Neu)
   */
    private void initSortenVectors() {
        initSortenColumnNames = new Vector();
        initSortenColumnNames.add("Eigenschaft");
        initSortenColumnNames.add("Anteil in Sorte 1");
        
        initSortenData = new Vector();
        //Beispieldaten zu Testzwecken
        /*
        String sKey = (String)jTableAnforderung.getValueAt(0, 0);
         */
        Vector row1 = new Vector();
        row1.add("");
        row1.add(Double.toString(0.0));
        initSortenData.add(row1);
        
         /*
        sKey = (String)jTableAnforderung.getValueAt(1, 0);
        Vector row2 = new Vector();
        row2.add(sKey);
        row2.add(Double.toString(1.5));
        initSortenData.add(row2);*/
        Vector rowEnd = new Vector();
        rowEnd.add("Preis");
        rowEnd.add(Double.toString(0.0));
        //rowEnd.add(Double.toString(2.5));
        initSortenData.add(rowEnd);
    }
    
    
    /** erzeugt die Anforderung Tabelle.
     */
    private void createAnforderungTable() {
        initAnforderungVectors();
        jTableAnforderung = new JTable(initAnforderungData, initAnforderungColumnNames);
        jTableAnforderung.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableAnforderung.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                for (int i = 0; i < jTableAnforderung.getRowCount(); i++ ){
                    Object o = jTableAnforderung.getValueAt(i, 0);
                    jTableSorten.setValueAt(o, i, 0);
                }
            }
        });
        jTableAnforderung.getTableHeader().setReorderingAllowed(false);
        formatAnforderungTable();
        
        myJScrollPane1 = new JScrollPane(jTableAnforderung);
        myJScrollPane1.setViewportView(jTableAnforderung);
        
        getContentPane().add(myJScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80,264, 200));
    }
    
    /** erzeugt die Sorten Tabelle.
     */
    private void createSortenTable() {
        initSortenVectors();
        jTableSorten = new JTable(initSortenData, initSortenColumnNames);
        TableColumnModel tcm = jTableSorten.getColumnModel();
        TableModel tm        = jTableSorten.getModel();
        
        jTableSorten.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableSorten.getTableHeader().setReorderingAllowed(false);
        formatSortenTable();
        
        myJScrollPane2 = new JScrollPane(jTableSorten);
        myJScrollPane2.setViewportView(jTableSorten);
        
        getContentPane().add(myJScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80 ,460, 200));
    }
    
    /** extrahiert aus den Tabellen die Daten und erzeugt damit
     *  ein Eingabe Objekt */
    private Eingabe getEingabeFromTables() {
        Eingabe eingabe = new Eingabe();
        try {
            DefaultTableModel tmAnforderung = (DefaultTableModel) jTableAnforderung.getModel();
            DefaultTableModel tmSorten      = (DefaultTableModel) jTableSorten.getModel();
            int id = 1;
            
            /** hole die Anforderungen */
            for (int row =0;row<tmAnforderung.getRowCount();row++) {
                String name     =  (String) tmAnforderung.getValueAt(row, 0);
                String sValue   =  (String) tmAnforderung.getValueAt(row,1);
                double wert     =  0.0;
                if (sValue != null) {
                    wert     =   Double.parseDouble(sValue);
                }
                Anforderung a   = new Anforderung();
                a.setName(name);
                a.setWert(wert);
                a.setId(id);
                id++;
                eingabe.addAnforderung(a);
            }
            int sorteId = 1;
            
            /** hole die Sorten */
            for (int column = 1; column<tmSorten.getColumnCount();column++) {
                int anforderungId =1;
                Sorte sorte = new Sorte();
                sorte.setId(sorteId);
                // die erfuellung der Anforderung holen */
                int row = 0;
                
                for (row=0;row<tmSorten.getRowCount() -1 ;row++) {
                    ErfuellungAnforderung ea = new ErfuellungAnforderung();
                    Object o = tmSorten.getValueAt(row,column);
                    String wertString = (String) o;
                    double wert = 0.0;
                    if (wertString != null) {
                        wert = Double.parseDouble(wertString);
                    }
                    ea.setAnforderungId(anforderungId);
                    ea.setWert(wert);
                    anforderungId++;
                    sorte.addErfuellungAnforderung(ea);
                }
                String preisString = (String) tmSorten.getValueAt(row,column);
                //double preis = Double.NaN;
                double preis = 0.0;
                if (preisString != null && !"".equals(preisString)) {
                    preis = Double.parseDouble(preisString);
                }
                sorte.setPreis(preis);
                sorteId++;
                eingabe.addSorte(sorte);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return eingabe;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jButtonGroupSolver = new javax.swing.ButtonGroup();
        addSorteButton = new javax.swing.JButton();
        removeSorteButton = new javax.swing.JButton();
        removeRowButton = new javax.swing.JButton();
        addAnforderungButton = new javax.swing.JButton();
        jLabelAnforderung = new javax.swing.JLabel();
        jLabelSorten = new javax.swing.JLabel();
        calculateButton = new javax.swing.JButton();
        jLabelBerechnung = new javax.swing.JLabel();
        jPanel_Answer = new javax.swing.JPanel();
        jCheckBoxGanzzahlig = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuDatei = new javax.swing.JMenu();
        jMenuItem_Neu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItem_Oeffnen = new javax.swing.JMenuItem();
        jMenuItem_Speichern = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItem_Beenden = new javax.swing.JMenuItem();
        jMenuSolver = new javax.swing.JMenu();
        jRadioButtonLP = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonXA = new javax.swing.JRadioButtonMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItem_Pfadeinstellungen = new javax.swing.JMenuItem();
        jMenuHilfe = new javax.swing.JMenu();
        jMenuItem_Hilfe = new javax.swing.JMenuItem();
        jMenuItem_Ueber = new javax.swing.JMenuItem();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("Optimierung von Mischungsverh\u00e4ltnissen");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        addSorteButton.setText("+");
        addSorteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSorteButtonActionPerformed(evt);
            }
        });

        getContentPane().add(addSorteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        removeSorteButton.setText("-");
        removeSorteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSorteButtonActionPerformed(evt);
            }
        });

        getContentPane().add(removeSorteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 40, -1));

        removeRowButton.setText("-");
        removeRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowButtonActionPerformed(evt);
            }
        });

        getContentPane().add(removeRowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 40, -1));

        addAnforderungButton.setText("+");
        addAnforderungButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAnforderungButtonActionPerformed(evt);
            }
        });

        getContentPane().add(addAnforderungButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        jLabelAnforderung.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        jLabelAnforderung.setText("Anforderungen an das Endprodukt");
        getContentPane().add(jLabelAnforderung, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jLabelSorten.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        jLabelSorten.setText("Zur Auswahl stehende Sorten");
        getContentPane().add(jLabelSorten, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, -1, -1));

        calculateButton.setText("Berechnen");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        getContentPane().add(calculateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, -1, -1));

        jLabelBerechnung.setFont(new java.awt.Font("BankGothic Md BT", 3, 12));
        jLabelBerechnung.setText("Die L\u00f6sung wird berechnet. Bitte haben Sie einen Moment Geduld.");
        jLabelBerechnung.setBorder(new javax.swing.border.EtchedBorder(java.awt.Color.lightGray, null));
        getContentPane().add(jLabelBerechnung, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 570, 40));

        jPanel_Answer.setName("jPanel_Answer");
        getContentPane().add(jPanel_Answer, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 730, 180));

        jCheckBoxGanzzahlig.setText("Ganzzahlig");
        jCheckBoxGanzzahlig.setOpaque(false);
        getContentPane().add(jCheckBoxGanzzahlig, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, -1, -1));

        jMenuDatei.setText("Datei");
        jMenuItem_Neu.setMnemonic('N');
        jMenuItem_Neu.setText("Neu");
        jMenuItem_Neu.setName("jMenuItem_Neu");
        jMenuItem_Neu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_NeuActionPerformed(evt);
            }
        });

        jMenuDatei.add(jMenuItem_Neu);

        jMenuDatei.add(jSeparator1);

        jMenuItem_Oeffnen.setText("\u00d6ffnen");
        jMenuItem_Oeffnen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_OeffnenActionPerformed(evt);
            }
        });

        jMenuDatei.add(jMenuItem_Oeffnen);

        jMenuItem_Speichern.setText("Speichern");
        jMenuItem_Speichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_SpeichernActionPerformed(evt);
            }
        });

        jMenuDatei.add(jMenuItem_Speichern);

        jMenuDatei.add(jSeparator2);

        jMenuItem_Beenden.setText("Beenden");
        jMenuDatei.add(jMenuItem_Beenden);

        jMenuBar1.add(jMenuDatei);

        jMenuSolver.setText("Solver");
        jRadioButtonLP.setSelected(true);
        jRadioButtonLP.setText("LP");
        jButtonGroupSolver.add(jRadioButtonLP);
        jRadioButtonLP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonLPActionPerformed(evt);
            }
        });

        jMenuSolver.add(jRadioButtonLP);
//
        jRadioButtonXA.setText("XA");
        jButtonGroupSolver.add(jRadioButtonXA);
        jRadioButtonXA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonXAActionPerformed(evt);
            }
        });

        //jMenuSolver.add(jRadioButtonXA);

        jMenuSolver.add(jSeparator3);

        jMenuItem_Pfadeinstellungen.setText("Pfade...");
        jMenuItem_Pfadeinstellungen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_PfadeinstellungenActionPerformed(evt);
            }
        });

        jMenuSolver.add(jMenuItem_Pfadeinstellungen);

        jMenuBar1.add(jMenuSolver);

        jMenuHilfe.setText("?");
        jMenuItem_Hilfe.setText("Hilfe");
        jMenuItem_Hilfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_HilfeActionPerformed(evt);
            }
        });

        jMenuHilfe.add(jMenuItem_Hilfe);

        jMenuItem_Ueber.setText("Info");
        jMenuItem_Ueber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_UeberActionPerformed(evt);
            }
        });

        jMenuHilfe.add(jMenuItem_Ueber);

        jMenuBar1.add(jMenuHilfe);

        setJMenuBar(jMenuBar1);

        pack();
    }//GEN-END:initComponents
    
    private void jMenuItem_HilfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_HilfeActionPerformed
        jPanel_Answer.setVisible(false);
        try {
            Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler "+ new java.io.File("hilfe/hilfe.html").getAbsolutePath());
        } catch (Exception e)
        {}
    }//GEN-LAST:event_jMenuItem_HilfeActionPerformed
    
    private void jMenuItem_PfadeinstellungenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_PfadeinstellungenActionPerformed
        jPanel_Answer.setVisible(false);
        SolverPath sp = new SolverPath(solverProperties,this,true);
        int newX = this.getX() + Constants.SCRWIDTH / 2 - sp.getWidth() / 2;
        int newY = this.getY() + Constants.SCRHEIGHT / 2 - sp.getHeight() / 2;
        sp.setBounds(newX,newY, sp.getWidth(), sp.getHeight());
        sp.show();
    }//GEN-LAST:event_jMenuItem_PfadeinstellungenActionPerformed
    
    private void jRadioButtonLPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLPActionPerformed
        jPanel_Answer.setVisible(false);        
        jCheckBoxGanzzahlig.setEnabled(true);
    }//GEN-LAST:event_jRadioButtonLPActionPerformed
    
    private void jRadioButtonXAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonXAActionPerformed
        jPanel_Answer.setVisible(false);
        jCheckBoxGanzzahlig.setSelected(false);
        jCheckBoxGanzzahlig.setEnabled(false);
    }//GEN-LAST:event_jRadioButtonXAActionPerformed
    
    private void jMenuItem_UeberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_UeberActionPerformed
        jPanel_Answer.setVisible(false);
        JFrame info = new JFrame("Info");
        
        
        JTextArea infoTextField = new JTextArea();
        
        infoTextField.setText(" Idee und Implementierung:\n   Christoph Höfel\n   Heiko Maaß\n\n" +
        " Version\n   1.0 Sommersemester 2004 \n\n" +
        " Version\n   2.0 Sommersemester 2013 \n" +
        "\n überarbeitet und portiert \n\n auf Windows 7 \n" +
        "von \t D. Brunner,\n\t M. Deppe,\n\t M.Prügel \n"+
        " Externe Bibliotheken:\n  SolverCaller (Helmut Lindinger, FHK)\n  Xerces2 XML Parser \n") ;
        info.getContentPane().add(infoTextField);
        info.pack();
        int newX = this.getX() + Constants.SCRWIDTH / 2 - info.getWidth() / 2;
        int newY = this.getY() + Constants.SCRHEIGHT / 2 - info.getHeight() / 2;
        info.setBounds(newX,newY,info.getWidth(),info.getHeight());
        info.show();
    }//GEN-LAST:event_jMenuItem_UeberActionPerformed
    
    private void jMenuItem_SpeichernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_SpeichernActionPerformed
        jPanel_Answer.setVisible(false);
        // Die Editierung stoppen (sonst kann es passieren, dass eine neu eingegebene Zelle
        // keine Daten enthält)
        if (jTableAnforderung.isEditing()) {
            jTableAnforderung.getCellEditor().stopCellEditing();
        }
        if (jTableSorten.isEditing()) {
            jTableSorten.getCellEditor().stopCellEditing();
        }
        try {
            checkTables();
            JFileChooser chooser = null;
            if (savePath == null) {
                chooser = new JFileChooser();
            } else {
                chooser = new JFileChooser(savePath);
            }
            chooser.setFileFilter(new XMLFileFilter());
            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                savePath = chooser.getCurrentDirectory().getAbsolutePath();
                String name = chooser.getSelectedFile().getName();
                System.out.println("Save file: " + name);
                File file = chooser.getSelectedFile();
                File renamedFile = null;
                boolean isRenamed = false;
                if (!name.endsWith(".xml")) {
                    isRenamed = true;
                    renamedFile = new File(savePath + File.separatorChar + name + ".xml");
                    System.out.println("new File: " + savePath + File.separatorChar + name + ".xml");
                    file.renameTo(renamedFile);
                }
                try {
                    File newFile = null;
                    if (isRenamed) {
                        newFile = renamedFile;
                    } else {
                        newFile = file;
                    }
                    newFile.createNewFile();
                    eingabe = getEingabeFromTables();
                    eb.saveEingabe(eingabe, newFile);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,e.getMessage());
                }
            }
        } catch (TableException te) {
            jPanel_Answer.removeAll();
            JTextArea hTextArea = new JTextArea();
            hTextArea.setBackground(Color.getHSBColor(255,153,153));
            hTextArea.setFont(new Font("Arial",Font.BOLD,12));
            hTextArea.setBounds(40, 20, 600, 120);
            hTextArea.setText(Constants.ERROR_INPUT_TABLES + "\n" + te.getMessage());
            jPanel_Answer.add(hTextArea);
        }
        
        
    }//GEN-LAST:event_jMenuItem_SpeichernActionPerformed
    
    private void jMenuItem_OeffnenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_OeffnenActionPerformed
        jPanel_Answer.setVisible(false);
        JFileChooser chooser = null;
        if (savePath == null) {
            chooser = new JFileChooser();
        } else {
            chooser = new JFileChooser(savePath);
        }
        chooser.setFileFilter(new XMLFileFilter());
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            savePath = chooser.getCurrentDirectory().getAbsolutePath();
            
            try {
                Eingabe eingabe = eb.loadEingabe(chooser.getSelectedFile());
                loadAnforderungTable(eingabe);
                loadSortenTable(eingabe);
            } catch (EingabeBuilderException ee) {
                JOptionPane.showMessageDialog(this,ee.getMessage());
            }
        }
    }//GEN-LAST:event_jMenuItem_OeffnenActionPerformed
    
    private void jMenuItem_NeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_NeuActionPerformed
        jPanel_Answer.setVisible(false);
        // leere Tabellen erzeugen  BSP löschen
        initAnforderungVectors();
        TableModel tm = new DefaultTableModel(initAnforderungData, initAnforderungColumnNames);
        jTableAnforderung.setModel(tm);
        tm.addTableModelListener(anforderungTableListener);
        formatAnforderungTable();
        
        
        initSortenVectors();
        tm = new DefaultTableModel(initSortenData, initSortenColumnNames);
        jTableSorten.setModel(tm);
        formatSortenTable();
        
    }//GEN-LAST:event_jMenuItem_NeuActionPerformed
    
    private void removeSorteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSorteButtonActionPerformed
        jPanel_Answer.setVisible(false);
        jPanel_Answer.setVisible(false);
        // Add your handling code here:
        // delete selected column Sorte
        try {
            int selected = jTableSorten.getSelectedColumn();
            Eingabe eingabe = getEingabeFromTables();
            eingabe.removeSorte(selected);
            loadSortenTable(eingabe);
            TableModel tm = jTableSorten.getModel();
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            JFrame hFrame = new JFrame("Warnung!");
            JLabel hLabel = new JLabel(Constants.ERROR_NO_COLUMN_SELECTED);
            hFrame.getContentPane().add(hLabel);
            hFrame.pack();
            hFrame.show();
        }
    }//GEN-LAST:event_removeSorteButtonActionPerformed
    
    private void removeRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRowButtonActionPerformed
        jPanel_Answer.setVisible(false);
        // delete selected row Anforderung
        int nRowToDelete = jTableAnforderung.getSelectedRow();
        if (nRowToDelete != -1 && jTableAnforderung.getRowCount() > 1) {
            DefaultTableModel tm = (DefaultTableModel) jTableAnforderung.getModel();
            tm.removeRow(nRowToDelete);
            
            DefaultTableModel tmSorten = (DefaultTableModel) jTableSorten.getModel();
            tmSorten.removeRow(nRowToDelete);
        }
    }//GEN-LAST:event_removeRowButtonActionPerformed
    
    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        jPanel_Answer.setVisible(false);
        // Die Editierung stoppen (sonst kann es passieren, dass eine neu eingegebene Zelle
        // keine Daten enthält)
        if (jTableAnforderung.isEditing()) {
            jTableAnforderung.getCellEditor().stopCellEditing();
        }
        if (jTableSorten.isEditing()) {
            jTableSorten.getCellEditor().stopCellEditing();
        }
        
        
        jLabelBerechnung.setVisible(true);
        try {
            checkTables();
            Eingabe eingabe = getEingabeFromTables();
            
            startSolver(eingabe);
        } catch (TableException e) {
            jPanel_Answer.removeAll();
            JTextArea hTextArea = new JTextArea();
            hTextArea.setBackground(new Color(228,149,156));
            hTextArea.setFont(new Font("Arial",Font.BOLD,12));
            hTextArea.setBounds(40, 20, 600, 120);
            hTextArea.setText(Constants.ERROR_INPUT_TABLES + "\n" + e.getMessage());
            jPanel_Answer.add(hTextArea);
        }
        jLabelBerechnung.setVisible(false);
        jPanel_Answer.setVisible(true);
    }//GEN-LAST:event_calculateButtonActionPerformed
    
    private void addAnforderungButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAnforderungButtonActionPerformed
        jPanel_Answer.setVisible(false);
        // Add your handling code here:
        // add new Row Anforderung
        
        DefaultTableModel tm = (DefaultTableModel) jTableAnforderung.getModel();
        tm.addRow(new Vector());
        
        // add new Row Sorte
        DefaultTableModel tmSorten = (DefaultTableModel) jTableSorten.getModel();
        Vector sorteData = new Vector();
        sorteData.add(new String(""));
        for (int i=1;i<tmSorten.getColumnCount();i++) {
            sorteData.add(new String("0.0"));
        }
        tmSorten.insertRow(tmSorten.getRowCount()-1,sorteData);
    }//GEN-LAST:event_addAnforderungButtonActionPerformed
    
    private void addSorteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSorteButtonActionPerformed
        jPanel_Answer.setVisible(false);
        // Add your handling code here:
        
        DefaultTableModel tm = (DefaultTableModel) jTableSorten.getModel();
        int colcount = tm.getColumnCount();
        int rowcount = tm.getRowCount();
        Vector data = new Vector(rowcount);
        for (int i=0;i<rowcount;i++) {
            data.add(new String("0.0"));
        }
        tm.addColumn("Anteil in Sorte " + (colcount),data);
        TableColumn columnx = jTableSorten.getColumnModel().getColumn(colcount);
        columnx.setPreferredWidth(100);
        
        colcount++;
        formatSortenTable();
    }//GEN-LAST:event_addSorteButtonActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Main hMain = new Main();
        hMain.show();
    }
    /** Überprüft ob die Eingaben korrekt sind, d.h. ob in jedem Feld (bis auf
     *  die Namensfelder) auch Double Werte drin stehen.
     *  Bei fehlerhaften Eingaben wird eine TableException geworfen
     */
    private void checkTables() throws TableException {
        TableModel tm = jTableAnforderung.getModel();
        // Anforderung Tabelle überprüfen
        for (int rowIndex = 0; rowIndex<tm.getRowCount(); rowIndex++) {
            String value = (String) tm.getValueAt(rowIndex,1);
            try {
                double wert = Double.parseDouble(value);
            } catch (Exception e ) {
                throw new TableException(Constants.ERROR_INPUT_TABLES_CAST_EXCEPTION);
            }
        }
        
        tm = jTableSorten.getModel();
        // Wenn keine Sorte existiert, Fehlermeldung ausgeben
        if (tm.getColumnCount() < 2) {
            throw new TableException(Constants.ERROR_NO_SORTE_DEFINED);
            
        }
        // Sorten Tabelle überprüfen
        for (int columnIndex = 1; columnIndex<tm.getColumnCount();columnIndex++) {
            boolean otherValueThanNull = false;
            for (int rowIndex = 0; rowIndex<tm.getRowCount();rowIndex++) {
                try {
                    String value = (String) tm.getValueAt(rowIndex, columnIndex);
                    double wert = Double.parseDouble(value);
                    if (wert != 0.0) {
                        otherValueThanNull = true;
                    }
                } catch (Exception e) {
                    throw new TableException(Constants.ERROR_INPUT_TABLES_CAST_EXCEPTION);
                }
            }
            if (!otherValueThanNull) {
                // Eine Sorte hat für jede Anforderung den Nullwert (0.0)
                // Das macht keinen Sinn
                throw new TableException(Constants.ERROR_INPUT_TABLES_NULL_SORTE);
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAnforderungButton;
    private javax.swing.JButton addSorteButton;
    private javax.swing.JButton calculateButton;
    private javax.swing.ButtonGroup jButtonGroupSolver;
    private javax.swing.JCheckBox jCheckBoxGanzzahlig;
    private javax.swing.JLabel jLabelAnforderung;
    private javax.swing.JLabel jLabelBerechnung;
    private javax.swing.JLabel jLabelSorten;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuDatei;
    private javax.swing.JMenu jMenuHilfe;
    private javax.swing.JMenuItem jMenuItem_Beenden;
    private javax.swing.JMenuItem jMenuItem_Hilfe;
    private javax.swing.JMenuItem jMenuItem_Neu;
    private javax.swing.JMenuItem jMenuItem_Oeffnen;
    private javax.swing.JMenuItem jMenuItem_Pfadeinstellungen;
    private javax.swing.JMenuItem jMenuItem_Speichern;
    private javax.swing.JMenuItem jMenuItem_Ueber;
    private javax.swing.JMenu jMenuSolver;
    private javax.swing.JPanel jPanel_Answer;
    private javax.swing.JRadioButtonMenuItem jRadioButtonLP;
    private javax.swing.JRadioButtonMenuItem jRadioButtonXA;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton removeRowButton;
    private javax.swing.JButton removeSorteButton;
    // End of variables declaration//GEN-END:variables
    
    
    // Anforderung Table
    private JTable              jTableAnforderung;
    private JScrollPane         myJScrollPane1;
    private Vector              initAnforderungColumnNames;
    private Vector              initAnforderungData;
    private AnforderungTableListener anforderungTableListener;
    
    // Sorten Table
    private JTable              jTableSorten;
    private JScrollPane         myJScrollPane2;
    private Vector              initSortenColumnNames;
    private Vector              initSortenData;
    
    // EingabeBuilder
    private EingabeBuilder      eb;
    
    // Solver Properties
    private Properties          solverProperties;
    
    private Eingabe eingabe;
    private String savePath;
    
}
