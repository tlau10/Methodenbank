/**
 * Title:        Programm zur linearen Portfoliooptimierung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FH Konstanz
 * @author Matthias Gommeringer
 * @version 1.0
 */
package portfolio.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;


import portfolio.view.*;
import portfolio.view.images.ImageLoader;
import portfolio.business.*;


public class Hauptfenster extends JFrame implements ActionListener {

  // the model objects
  private LPModell lpmodell;
  private Portfolio portfolio;

  // this panel contains all other GUI-panels
  private EinstellungenPanel einstellungenPanel = new EinstellungenPanel(this);

  private boolean datenbasis = false;
  private Solution ergebnis;
  private final String WINDOW_TITLE = "Portfolio Optimierer";


     /**
     * Construct the frame
     */
    public Hauptfenster() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Component initialization
     */
    private void jbInit() throws Exception  {

      ImageIcon icon = ImageLoader.createImageIcon("bullet2.gif", "Chart");
      if( icon != null )
        this.setIconImage( icon.getImage() );

      this.setSize(new Dimension(873, 600));
      this.setTitle(WINDOW_TITLE);
      this.setResizable(false);


      PortfolioMenuBar jMenuBar1 = new PortfolioMenuBar(this);
      this.setJMenuBar(jMenuBar1);
      // register to get notified by the menu bar if something happens
      jMenuBar1.register(this);


      JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new GridBagLayout());

      mainPanel.add(einstellungenPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
          ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

      JPanel contentPane = (JPanel) this.getContentPane();
      contentPane.setLayout(new BorderLayout() );
      contentPane.setBackground(UIManager.getColor("activeCaptionBorder"));
      contentPane.setPreferredSize(new Dimension(600, 440));
      contentPane.add(new JScrollPane(mainPanel), BorderLayout.CENTER);

      this.setResizable(false);
    }


    /**
     * Overridden so we can exit when window is closed
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
          System.exit(0);
        }
    }


    /**
     * starte die Berechnungen
     */
    void buttonStart_actionPerformed(ActionEvent e) {
      startSolving( false );
      einstellungenPanel.getResultPanel().activateTabbedPanePortfolio();
    }


    /**
     *
     */
    public void buttonPruefeQualitaet_actionPerformed(ActionEvent e) {
      startSolving( true );
      einstellungenPanel.getResultPanel().activateTabbedPaneQualitaetspruefung();
    }


    /**
     * solves the current problem
     */
    private void startSolving(boolean qualitaetspruefung)
    {
        if( portfolio != null )
          portfolio.resetAktienliste();

        if( !datenbasis )
            JOptionPane.showMessageDialog(this, "Sie müssen erst eine Datenbasis laden.");
        else
        {
          Integer zufallszahl = einstellungenPanel.getOptionsPanel().getZufallsAnzahlAktien();
          if( zufallszahl != null ) {
            portfolio.erzeugeAktienlisteZufall(zufallszahl.intValue());
          }
          resetHauptfenster();

          int schrittweite = einstellungenPanel.getSchrittweite();

          portfolio.setSchrittweite(schrittweite);

          lpmodell = einstellungenPanel.getSelectedTypeModel();

          double anteilsbegrenzung = einstellungenPanel.getAnteilAmPortfolio();
          /** pruefe ob anteilsbegrenzung <=1 ist und ob genug aktien in
            * der Datenbasis sind für diese anteilsbegrenzung */
          if( anteilsbegrenzung > 1.0 ) {
            JOptionPane.showMessageDialog(this, "Die Anteilsbegrenzung kann nicht größer als 1 sein. Sie wird automatisch auf 1 gesetzt!");
            einstellungenPanel.getOptionsPanel().setAnteilsbegrenzung(1.0);
            anteilsbegrenzung = 1.0;
          }
          else if( anteilsbegrenzung <= 0.0 ) {
            JOptionPane.showMessageDialog(this, "Die Anteilsbegrenzung kann nicht kleiner oder gleich 0 sein. Sie wird automatisch auf 0.1 gesetzt!");
            einstellungenPanel.getOptionsPanel().setAnteilsbegrenzung(1.0);
            anteilsbegrenzung = 1.0;
          }
          if( (anteilsbegrenzung * portfolio.getAktienListe().size()) < 1 ) {
            JOptionPane.showMessageDialog(this, "Die Anteilsbegrenzung ist zu niedrig für die Anzahl der Aktien in der Datenbasis!\n Bitte setzen Sie eine größere Anteilsbegrenzung und starten Sie erneut!");
            return;
          }
          lpmodell.setAnteilsBegrenzung(anteilsbegrenzung);


          Double muMin;
          if( (muMin = einstellungenPanel.getOptionsPanel().getMuMin()) != null)
          {
              lpmodell.setMuMin(muMin.doubleValue());
          }


          berechneModell( einstellungenPanel.getParamPanel().getAnalyseVon().getTime(),
                          einstellungenPanel.getParamPanel().getAnalyseBis().getTime());

          // now check for qualitaetspruefung
          if( qualitaetspruefung ) {
            pruefeQualitaet();
          }
        }
    }


    /**
     * berechnet das Modell für den angegebenen Zeitraum. Das Ergebnis wird dann
     * direkt an der Oberfläche gesetzt.
     * @param analysezeitVon
     * @param analysezeitBis
     */
    private void berechneModell(long analysezeitVon, long analysezeitBis)
    {
        einstellungenPanel.getResultPanel().reset();

        portfolio.setAnalyseDatumVon( analysezeitVon );
        portfolio.setAnalyseDatumBis( analysezeitBis );
        portfolio.setVergleichszeitraumTage( einstellungenPanel.getParamPanel().getVergleichszeitraum() );

        // rendite muss auf jeden fall neu berechnet werden, falls einstellungen
        // für zeitraum geändert
        portfolio.berechneRendite();
        einstellungenPanel.setAnzahlRenditen( ((Aktie)portfolio.getAktienListe().get(0)).getLaengeEinzelRendite() );

        Vector modell = lpmodell.erstelleModell(portfolio.getAktienListe());

        // Ausgabe des Ergebnisses und des LP Modells
        einstellungenPanel.getResultPanel().setLPModell(modell);

        SolverAufruf solver = new SolverAufruf();
        //Solution
        ergebnis = solver.starteSolver(modell);

        if( ergebnis.hasErrorHappened() ) {
          // System.out.println("Hauptfenster.java->berechneModell(): Error " + ergebnis.getErrorLinesFromSolver().toString() );
          einstellungenPanel.getResultPanel().getResultPortfolioPanel().showError(ergebnis.getErrorLinesFromSolver());
          return;
        }

        if (ergebnis.getRenditeVerfehlt() > 0 && einstellungenPanel.getOptionsPanel().getMuMin() != null)
        {
          einstellungenPanel.getResultPanel().getResultPortfolioPanel().setTextFieldGesamtRenditeBackground(Color.red);
          einstellungenPanel.getOptionsPanel().setColorMuMinTextfield(Color.red);
        }

        einstellungenPanel.getResultPanel().setPortfolio(null);
        einstellungenPanel.getResultPanel().setGesamtRendite(-1 * ergebnis.getRenditeNegative() + ergebnis.getRenditePositive());
        if ( ergebnis.getRenditeTyp4() + ergebnis.getRenditeNegativZumZeitpunktT() != 0.0 )
          einstellungenPanel.getResultPanel().getResultPortfolioPanel().setGesamtRenditeT(-1 * ergebnis.getRenditeNegativZumZeitpunktT() + ergebnis.getRenditeTyp4());

        Vector aktienliste = portfolio.getAktienListe();
        Iterator iteratorPortfolioModels = ergebnis.getAllPortfolioModels().iterator();
        while(iteratorPortfolioModels.hasNext())
        {
            portfolio.model.Portfolio model = (portfolio.model.Portfolio)iteratorPortfolioModels.next();

            // suche die passende rendite heraus
            for (int k =0; k<aktienliste.size(); k++)
            {
              Aktie currentShare = (Aktie)aktienliste.get(k);
              if( currentShare.getNameSolver().equalsIgnoreCase( model.getName() ) ) {
                double aktienrendite=0;
                model.setRendite( new Double(currentShare.getRendite()) );
                break;
              }
            }

            Hashtable hashtableVollerName = portfolio.getAktienHashVolleNamen();
            if (hashtableVollerName.containsKey(model.getName()))
                model.setVollerName(hashtableVollerName.get(model.getName()).toString());

            einstellungenPanel.getResultPanel().addPortfolio(model);
        }
        einstellungenPanel.getResultPanel().getResultPortfolioPanel().resetColumnWidth();

    }


    /**
     * führt die Berechnungen für die Qualitätsprüfung durch
     */
    private void pruefeQualitaet() {
      // 1. berechne Rendite * AnteilAmPortfolio vom Komplettdurchlauf
      Vector aktienlisteZeitpunktT = portfolio.getAktienListe();//Renditen zum Zeitpunkt T

      double portfolioWertA=0;
      double portfolioWertB=0;
      Vector ErgebnisvektorA=new Vector();
      ErgebnisvektorA=ergebnis.getAllPortfolioModels();
      Iterator iteratorPortfolioA = ErgebnisvektorA.iterator();
      while(iteratorPortfolioA.hasNext())
      {
        portfolio.model.Portfolio Einzelaktie= (portfolio.model.Portfolio)iteratorPortfolioA.next();

        // suche die passende aktie heraus
        if((Einzelaktie.getAnteil().compareTo(new Double(0)))>0)
        {
          /*// suche die passende kurse heraus
          double kurswert=0;
          Vector aktienliste = portfolio.getAktienListe();
          for (int k =0; k<aktienliste.size(); k++)
          {
            Aktie currentShare = (Aktie)aktienliste.get(k);
            if( currentShare.getNameSolver().equalsIgnoreCase( Einzelaktie.getName() ) ) {
              kurswert=currentShare.getWert(currentShare.getLaenge()-1);
              break;
            }
          }*/

          //suche die passende rendite heraus
          double dieRendite=0;
          for (int k =0; k<aktienlisteZeitpunktT.size(); k++)
          {
           Aktie currentShare = (Aktie)aktienlisteZeitpunktT.get(k);
           if( currentShare.getNameSolver().equalsIgnoreCase( Einzelaktie.getName() ) )
           {
             dieRendite=currentShare.getRendite();
             //System.out.println("Rendite: "+dieRendite);
             break;
           }
          }
          //Wert des Portfolios
          //System.out.println("Anteil: "+Einzelaktie.getAnteil()+" Name: "+Einzelaktie.getName());
          portfolioWertA=portfolioWertA+((Einzelaktie.getAnteil().doubleValue())*dieRendite);
          //System.out.println(portfolioWertA+" A");
        }
      }
      ErgebnisvektorA=null;


     // 2. starte neuen durchlauf mit 2/3 der Werte, die für den vorigen Durchlauf benutzt wurden
     long differenzTage =  einstellungenPanel.getParamPanel().getAnalyseBis().getTime() -
                           einstellungenPanel.getParamPanel().getAnalyseVon().getTime();
     //long dif =35424000000;
     differenzTage = differenzTage*2/3;

     berechneModell( einstellungenPanel.getParamPanel().getAnalyseVon().getTime(),
                     einstellungenPanel.getParamPanel().getAnalyseVon().getTime()+differenzTage);


     // 3. errechne zu den neuen AnteilAmPortfolio-Werten zum Zeitpunkt 2/3 die
     // EndRenditen und somit die Performance des Portfolios mit den Informationen zur Zeit 2/3T.
      Vector ErgebnisvektorB=new Vector();
      ErgebnisvektorB=ergebnis.getAllPortfolioModels();
      Iterator iteratorPortfolioB = ErgebnisvektorB.iterator();



      ////////////////////////Berechne modell zum zeitpunkt T
      berechneModell( einstellungenPanel.getParamPanel().getAnalyseVon().getTime(),
                    einstellungenPanel.getParamPanel().getAnalyseBis().getTime());


      while(iteratorPortfolioB.hasNext())
      {
        portfolio.model.Portfolio Einzelaktie= (portfolio.model.Portfolio)iteratorPortfolioB.next();

        // suche die passende aktie heraus
        if((Einzelaktie.getAnteil().compareTo(new Double(0)))>0)
        {

          //suche die passende rendite heraus
          double dieRendite=0;
          for (int k =0; k<aktienlisteZeitpunktT.size(); k++)
          {
           Aktie currentShare = (Aktie)aktienlisteZeitpunktT.get(k);
           if( currentShare.getNameSolver().equalsIgnoreCase( Einzelaktie.getName() ) )
           {
             dieRendite=currentShare.getRendite();
             //System.out.println("Rendite: "+dieRendite);
             break;
           }
          }
          //Wert des Portfolios
          //System.out.println("Anteil: "+Einzelaktie.getAnteil()+" Name: "+Einzelaktie.getName());
          portfolioWertB=portfolioWertB+((Einzelaktie.getAnteil().doubleValue())*dieRendite);
          //System.out.println(portfolioWertB+" B");
        }
      }
      ErgebnisvektorB=null;

      // 4. ermittle die Differenz zwischen 1. und 3. -> Spiegelt die Qualität des
      // zur Zeit 2/3 errechneten Portfolios wieder.
      double performance=portfolioWertB/portfolioWertA*100; //portfolioA=T, portfolioB=2/3T,
      //System.out.println("P: "+performance);//wieviel Prozent hat das Portfolio mit den 2/3 Werten, vom dem Portfolio mit den Endwerten erreicht??
      einstellungenPanel.getResultPanel().getQualitaetspruefungPanel().setPortfPerformance(performance);

      long Zeitraum = einstellungenPanel.getParamPanel().getAnalyseBis().getTime() - einstellungenPanel.getParamPanel().getAnalyseVon().getTime();
      Zeitraum=Zeitraum/86400000;//ms in t
      einstellungenPanel.getResultPanel().getQualitaetspruefungPanel().setVorhandenerGesamtzeitraum((int)Zeitraum);
      differenzTage=differenzTage/86400000;//Berechnung über x Tage
      einstellungenPanel.getResultPanel().getQualitaetspruefungPanel().setZeitBerechnungPortfAnteile((int)differenzTage);
      long PrognoseTage=Zeitraum-differenzTage;//Vorhersage über x Tage
      einstellungenPanel.getResultPanel().getQualitaetspruefungPanel().setZeitBerechnungPortfWert((int)PrognoseTage);
    }


    /**
     * implement ActionListener
     */
    public void actionPerformed(ActionEvent e) {

      // we are only interested in the open command
      if( e.getActionCommand().equalsIgnoreCase(PortfolioMenuBar.ACTION_COMMAND_FILE_MENU_OPEN) )
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
            this.setTitle(WINDOW_TITLE + " - [" + theFile.getName() + "]");
            Aktienparser a = new Aktienparser();
            portfolio = a.parseFile(theFile);
            datenbasis = true;
            resetHauptfenster();
            einstellungenPanel.setAnzahlAktien(portfolio.getAktienListe().size());
            einstellungenPanel.setAnzahlKurse( ((Aktie)portfolio.getAktienListe().get(0)).getLaenge() );
            einstellungenPanel.getParamPanel().setAnalyseVon( portfolio.getAnalyseDatumVon() );
            einstellungenPanel.getParamPanel().setAnalyseBis( portfolio.getAnalyseDatumBis() );

        }
        else if(retval == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(this, "Operation abgebrochen. Es wurde keine Datei ausgewählt.");
        } else if(retval == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(this, "Ein Fehler ist aufgetreten. Es wurde keine Datei ausgewählt.");
        } else {
            JOptionPane.showMessageDialog(this, "Unknown operation occured.");
        }

      }

      else if( e.getActionCommand().equalsIgnoreCase(ButtonPanel.ACTION_COMMAND_START) ) {
        buttonStart_actionPerformed(e);
      }

      else if( e.getActionCommand().equalsIgnoreCase(ButtonPanel.ACTION_COMMAND_PRUEFE_QUALITAET) ) {
        buttonPruefeQualitaet_actionPerformed(e);
      }
      else
        System.err.println("Hauptfenster.java->actionPerformed(): unbekannter Befehl " + e.getActionCommand());

    }


    /**
     * setzt alle daten des Hauptfensters zurück auf die Initialwerte
     */
    private void resetHauptfenster() {
      einstellungenPanel.berkLabel.setText("-");
      einstellungenPanel.getResultPanel().getResultPortfolioPanel().resetPortfolioPanel();
      einstellungenPanel.getResultPanel().getResultPortfolioPanel().setTextFieldGesamtRenditeBackground(Color.white);
      einstellungenPanel.getResultPanel().getResultPortfolioPanel().setGesamtRenditeT(0.0);
      einstellungenPanel.getResultPanel().getResultPortfolioPanel().setGesamtRendite(0.0);
      einstellungenPanel.getResultPanel().getResultPortfolioPanel().setGesamtRenditeTVisible(false);

      einstellungenPanel.getResultPanel().setLPModell(null);
      einstellungenPanel.getOptionsPanel().setColorMuMinTextfield(Color.white);
      einstellungenPanel.getResultPanel().getQualitaetspruefungPanel().setPortfPerformance(0.0);
      einstellungenPanel.getResultPanel().getQualitaetspruefungPanel().setVorhandenerGesamtzeitraum(0);
      einstellungenPanel.getResultPanel().getQualitaetspruefungPanel().setZeitBerechnungPortfAnteile(0);
      einstellungenPanel.getResultPanel().getQualitaetspruefungPanel().setZeitBerechnungPortfWert(0);
    }
}
