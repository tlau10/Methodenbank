//*******************************************************************
//*																	*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse für den Datentyp, welcher	    *
//*		die Nachfrqagen eines Modells verwaltet		  				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*     Version 1.2 												*
//*     von Marco Weiß und Jenne Justin								*
//*																	*
//*******************************************************************

package plo_System;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//*** Klasse plo_NachfrageEntfernenDialog *******************************************************************************************************
public class plo_NachfrageEntfernenDialog extends JDialog{
	private static final long serialVersionUID = 6471389368910588126L;
	private JInternalFrame nachfrageEntfernenFrame;					//Darstellungskomponenten
	private JPanel nachfrageEntfernenPanel;							
	private JLabel nachfrageEntfernenLabel;							
	private JLabel fillLabel;										
	private final JButton jb_Ok;									//Buttons
	private final JButton jb_Abbrechen;								
	private boolean jb_Ok_Focus;									//Indikatorvariablen fuer den Fokussatus
	private boolean jb_Abbrechen_Focus;								
	private JTextField tf_NachfrageNummer;							//Textfeld
	private GridBagLayout nachfrageEntfernenGridBagLayout;			//Layout
	private GridBagConstraints nachfrageEntfernenGridBagConstraints;
	

//*** Konstruktor **********************************************************************
	public plo_NachfrageEntfernenDialog(final plo_Hauptfenster root)
	{
		final plo_NachfrageEntfernenDialog ref = this;				//Erstellen eines Referenzobjekts auf das Hauptfenster

		nachfrageEntfernenFrame = new JInternalFrame();				//Erstellen des Frames
		this.setTitle("Auswahl der Nachfrage");						
		nachfrageEntfernenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	

		nachfrageEntfernenPanel = new JPanel();						//Erstellen des Darstellungspanels
		nachfrageEntfernenLabel = new JLabel("Welche Nachfrage soll entfernt werden (Nummer)?");	
		fillLabel = new JLabel(" ");								
		jb_Ok = new JButton("     Ok     ");						//Erstellen der Buttons
		jb_Ok.setSize(150,25);										
		jb_Abbrechen = new JButton("Abbrechen");					
		jb_Abbrechen.setSize(100,25);								
		jb_Ok_Focus = false;										
		jb_Abbrechen_Focus = false;									
		tf_NachfrageNummer = new JTextField("0", 5);				//Erstellen des Textfeldes
		nachfrageEntfernenGridBagLayout = new GridBagLayout();		//Erstellen des Layouts
		nachfrageEntfernenGridBagConstraints = new GridBagConstraints();
		nachfrageEntfernenPanel.setLayout(nachfrageEntfernenGridBagLayout);		//Hinzufuegen das Layouts zum Darstellungspanel

		this.buildConstraints(nachfrageEntfernenGridBagConstraints, nachfrageEntfernenLabel, 0, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));	//Erstellen der COnstraints
		this.buildConstraints(nachfrageEntfernenGridBagConstraints, tf_NachfrageNummer, 0, 1, 3, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));		//fuer die Darstellungskomponenten
		this.buildConstraints(nachfrageEntfernenGridBagConstraints, fillLabel, 0, 2, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));		
		this.buildConstraints(nachfrageEntfernenGridBagConstraints, jb_Ok, 1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.EAST, 5, 5, new Insets(5,0,5,0));					
		this.buildConstraints(nachfrageEntfernenGridBagConstraints, jb_Abbrechen, 2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, new Insets(5,0,5,0));			

		this.setModal(true);										//Setzen der Dialogparameter
		this.setSize(330, 150);										
		this.setLocation(100, 100);									
		nachfrageEntfernenPanel.add(nachfrageEntfernenFrame);		
		nachfrageEntfernenPanel.add(nachfrageEntfernenLabel);		
		nachfrageEntfernenPanel.add(tf_NachfrageNummer);			
		nachfrageEntfernenPanel.add(jb_Ok);							
		nachfrageEntfernenPanel.add(jb_Abbrechen);					

		ActionListener plo_NachEntDiaButListener = new ActionListener()	//Listerner fuer Klicks auf Schaltflaechen
		{															
			public void actionPerformed(ActionEvent aevt)			
			{
				JButton compare = new JButton();					//Ermitteln welche Schaltflaeche
				compare = (JButton)aevt.getSource();				//geklickt wurde

				Integer retVal = new Integer(getTf_NachfrageNummerText());		//Gewinnen der Anzahl der NAchfragen aus dem Textfeld
				Integer intTemp = new Integer("0");
				int pos = 0;
				for(int i = 0; i < root.getInternalFocus().getAnzahlNachfragen(); i++)	//Abpruefen, ob eine gueltige Nachfragenummer eingegeben wurde
				{
					if(root.getInternalFocus().getEm_NachfragenListe(i).getTf_NummerText().equals(new String(retVal.toString())))
					{
						pos = (i+1);
					}
				}
				if(compare.equals(jb_Ok))							//Falls "ok" geklickt wurde
				{													//und eine gueltige Nachfragennummer einge-
					if(pos > 0)										//geben wurde
					{
						root.getInternalFocus().nachfrageEntfernen(retVal.intValue());	//und Start der Entfernen-Methode der Eingabemaske
						ref.dispose();													//Schliessen des Dialogs
					}
					else											//Falls keine gueltige Nachfrage ausgewaehlt wurde
					{												//Ausgabe der Fehlermeldung
						plo_FehlerMeldung fehler = new plo_FehlerMeldung(1);
					}
				}
				if(compare.equals(jb_Abbrechen))					//Falls "abbrechen" geklickt wurde
				{													
					ref.dispose();									//Schliessen des Dialogs
				}
			}
		};

		FocusListener plo_NachEntDiaFocListener = new FocusListener()	//Listener fuer Fokusaenderungen
		{															
			public void focusGained(FocusEvent focevt)				
			{														
				JButton compare = new JButton();					//Ermitteln welcher Button den Fokus bekommen hat
				compare = (JButton)focevt.getSource();				
				if(compare.equals(jb_Ok))							//Falls "ok"
				{
					jb_Ok_Focus = true;								//Anpassen der Indikatorvariablen
					jb_Abbrechen_Focus = false;						
				}
				if(compare.equals(jb_Abbrechen))					//Falls "abbrechen"
				{
					jb_Abbrechen_Focus = true;						//Anpassen der Indikatorvariablen
					jb_Ok_Focus = false;							
				}

			}

			public void focusLost(FocusEvent focevt)				
			{
				JButton compare = new JButton();					//Ermitteln welcher Button den Fokus verloren hat
				compare = (JButton)focevt.getSource();				
				if(compare.equals(jb_Ok))							//Falls "ok"
				{
					jb_Ok_Focus = false;							//Anpassen der Indikatorvariablen
				}
				if(compare.equals(jb_Abbrechen))					//Falls "abbrechen"
				{
					jb_Abbrechen_Focus = false;						//Anpassen der Indikatorvariablen
				}
			}
		};

		KeyListener plo_NachEntDiaKeyListener = new KeyListener()	//Listener um Tastendrucke zu verwalten
		{
			public void keyPressed(KeyEvent kevt)					
			{
				int compare = kevt.getKeyCode();					//Falls Taste "ENTER" gedrueckt wurde
				if(compare == KeyEvent.VK_ENTER)					
				{
					if(jb_Ok_Focus == true)							//Falls "ok" den Fokus hat
					{
						Integer retVal = new Integer(getTf_NachfrageNummerText());		//Gewinnen der Anzahl der NAchfragen aus dem Textfeld
						Integer intTemp = new Integer("0");
						int pos = 0;
						for(int i = 0; i < root.getInternalFocus().getAnzahlNachfragen(); i++)	//Abpruefen, ob eine gueltige Nachfragenummer eingegeben wurde
						{
							if(root.getInternalFocus().getEm_NachfragenListe(i).getTf_NummerText().equals(new String(retVal.toString())))
							{
								pos = (i+1);
							}
						}
						if(pos > 0)										//Falls eine gueltige Nachfrage eingegeben wurde
						{
							root.getInternalFocus().nachfrageEntfernen(retVal.intValue());	//und Start der Entfernen-Methode der Eingabemaske
							ref.dispose();													//Schliessen des Dialogs
						}
						else											//Falls keine gueltige Nachfrage ausgewaehlt wurde
						{												//Ausgabe der Fehlermeldung
							plo_FehlerMeldung fehler = new plo_FehlerMeldung(1);
						}

					}
					if(jb_Abbrechen_Focus == true)					//Falls "abbrechen" den Fokus hat
					{
						ref.dispose();								//Schliessen des Dialogs
					}
				}
			}
			public void keyReleased(KeyEvent kevt)
			{

			}
			public void keyTyped(KeyEvent kevt)
			{

			}
		};

		jb_Ok.addActionListener(plo_NachEntDiaButListener);			//Hinzufuegen der Listener
		jb_Ok.addFocusListener(plo_NachEntDiaFocListener);			//zu den Schaltflaechen
		jb_Ok.addKeyListener(plo_NachEntDiaKeyListener);			
		jb_Abbrechen.addActionListener(plo_NachEntDiaButListener);	
		jb_Abbrechen.addFocusListener(plo_NachEntDiaFocListener);
		jb_Abbrechen.addKeyListener(plo_NachEntDiaKeyListener);		

		this.getContentPane().setLayout(new FlowLayout());			//Hinzufuegen des Darstellungspanels und
		this.getContentPane().add(nachfrageEntfernenPanel);			//des Layouts
		this.setVisible(true);										
	}
//*** Ende Konstruktor **********************************************************************

	
	public void buildConstraints(GridBagConstraints c, Component com, int x, int y, int width, int height, int fill, int ank, int pdx, int pdy, Insets ins)
	{																//Hilfsmethode zur besseren Initialisierung von Constraints
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		c.fill = fill;
		c.anchor = ank;
		c.ipadx = pdx;
		c.ipady = pdy;
		c.insets = ins;
		nachfrageEntfernenGridBagLayout.setConstraints(com, c);
	}

	
//*** SET-Methoden **********************************************************************
	public void setTf_NachfrageNummerText(String elem)					//Methode zum Setzen des Textfeldinhaltes
	{
		tf_NachfrageNummer.setText(elem);
	}
//*** Ende SET-Methoden *********************************************************************

	
//*** GET-Methoden **********************************************************************
	public String getTf_NachfrageNummerText()							//Methode zur Rueckgewinnung des Inhaltes des Textfeldes
	{
		return tf_NachfrageNummer.getText();
	}
//*** Ende GET-Methoden **********************************************************************
	
}

//*** Ende Klasse plo_NachfrageEntfernenDialog **********************************************************************