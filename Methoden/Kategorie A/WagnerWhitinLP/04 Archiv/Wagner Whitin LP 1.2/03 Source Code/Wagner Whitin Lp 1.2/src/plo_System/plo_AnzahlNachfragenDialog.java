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


//***** Klasse plo_AnzahlNachfragenDialog ******************************************************************************************
public class plo_AnzahlNachfragenDialog extends JDialog{
	private static final long serialVersionUID = 1285740339048359323L;
	private JInternalFrame anzahlNachfragenFrame;						//Komponenten des Dialogs
	private JPanel anzahlNachfragenPanel;
	private JLabel anzahlNachfragenLabel;
	private JLabel fillLabel;
	private final JButton jb_Ok;
	private final JButton jb_Abbrechen;
	private boolean jb_Ok_Focus;
	private boolean jb_Abbrechen_Focus;
	private JTextField tf_AnzahlNachfragen;
	private int int_AnzahlNachfragen;
	private GridBagLayout anzahlNachfragenGridBagLayout;				//Layout des Dialogs
	private GridBagConstraints anzahlNachfragenGridBagConstraints;

//***** Konstruktor *********************************************************************************
	public plo_AnzahlNachfragenDialog(final plo_Hauptfenster root, final int mode, final String modellName)
	{
		final plo_AnzahlNachfragenDialog ref = this;					//Erzeugen eines Referenzobjets fuer dispose()-Aufrufe
		anzahlNachfragenFrame = new JInternalFrame();					//Initialisierung der Komponenten des Dialogs
		this.setTitle("Anzahl der Nachfragen");							
		anzahlNachfragenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		anzahlNachfragenPanel = new JPanel();						
		anzahlNachfragenLabel = new JLabel("Wieviele Nachfragen sollen im Modell enthalten sein?");
		fillLabel = new JLabel(" ");									
		jb_Ok = new JButton("     Ok     ");							
		jb_Ok.setSize(150,25);											
		jb_Abbrechen = new JButton("Abbrechen");						
		jb_Abbrechen.setSize(100,25);									
		jb_Ok_Focus = false;											
		jb_Abbrechen_Focus = false;										
		tf_AnzahlNachfragen = new JTextField("0", 5);					
		anzahlNachfragenGridBagLayout = new GridBagLayout();			
		anzahlNachfragenGridBagConstraints = new GridBagConstraints();	
		anzahlNachfragenPanel.setLayout(anzahlNachfragenGridBagLayout);	
		int_AnzahlNachfragen = -1;										//Defaultwert des Intpuffers

		this.buildConstraints(anzahlNachfragenGridBagConstraints, anzahlNachfragenLabel, 0, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));	//Aufbauen der Constraints
		this.buildConstraints(anzahlNachfragenGridBagConstraints, tf_AnzahlNachfragen, 0, 1, 3, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//fuer die einzelnen Komponenten
		this.buildConstraints(anzahlNachfragenGridBagConstraints, fillLabel, 0, 2, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));		//
		this.buildConstraints(anzahlNachfragenGridBagConstraints, jb_Ok, 1, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.EAST, 5, 5, new Insets(5,0,5,0));					//
		this.buildConstraints(anzahlNachfragenGridBagConstraints, jb_Abbrechen, 2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, new Insets(5,0,5,0));			//

		this.setModal(true);											//Dialog als modaler Dialog
		this.setSize(330, 150);											//Groessenanpassung
		this.setLocation(100, 100);										//Positionierung
		anzahlNachfragenPanel.add(anzahlNachfragenFrame);				//Einsetzen der Komponenten
		anzahlNachfragenPanel.add(anzahlNachfragenLabel);				
		anzahlNachfragenPanel.add(tf_AnzahlNachfragen);					
		anzahlNachfragenPanel.add(jb_Ok);								
		anzahlNachfragenPanel.add(jb_Abbrechen);						

		ActionListener plo_AnzNachDiaButListener = new ActionListener()	     //Listener zum Abfangen von ButtonKlicks
		{
			public void actionPerformed(ActionEvent aevt)				
			{
				JButton compare = new JButton();						
				compare = (JButton)aevt.getSource();					
				if(compare.equals(jb_Ok))								//Bei Klick auf "Ok"
				{
					Integer retVal = new Integer(getTf_AnzahlNachfragenText());	//Eingabewert wird ermittelt
					int_AnzahlNachfragen = retVal.intValue();					
					if((retVal.intValue() > 0) && (retVal.intValue() < 100))		//Abpruefen, ob ein Eingabefehler vorliegt
					{
						if(mode == 1)												//Wird der Dialog aus der Aktion "allesAendern" eines bestehenden Modells aufgerufen,
						{															//wird die Mehtode "neuesModell" im Hauptfenster mit der Konfiguration aufgerufen, die bewirkt, dass
							root.neuesModell(retVal.intValue(), 1, modellName);		//das neue Modell den Modellnamen und die Position beiebehaelt
						}
						else												//Wird der Dialog aus der Aktion "neues Modell" gestartet, wird die Methode "neuesModell" im
						{													//Hauptfenster angestossen, ein neues Fenster und Modell zu erstellen mit Defaultparametern
							root.neuesModell(retVal.intValue(), 0, "");		
						}
						ref.dispose();										//Hernach wird der Dialog geschlossen
					}
					if(retVal.intValue() == 0)								//Wurde 0 eingegeben
					{
						plo_FehlerMeldung fehler = new plo_FehlerMeldung(3);
					}
					if(retVal.intValue() >= 100)							//Wurde ein zu grosser Wert eingegeben
					{
						plo_FehlerMeldung fehler = new plo_FehlerMeldung(2);
					}
				}
				if(compare.equals(jb_Abbrechen))						//Bei Klick auf "Abbrechen" wird der
				{														//Dialog beendet
					ref.dispose();
				}
			}
		};

		FocusListener plo_AnzNachDiaFocListener = new FocusListener()	//Listener um Focusaenderungen fuer die Buttons des Dialogs zu behandeln
		{
			public void focusGained(FocusEvent focevt)					//Bekommt eine Komponente den Focus
			{
				JButton compare = new JButton();						
				compare = (JButton)focevt.getSource();					
				if(compare.equals(jb_Ok))								//Bekommt der Button "Ok" den Focus
				{														//wird die entsprechende Bool-Variable true gesetzt, und die
					jb_Ok_Focus = true;									//des "Abbrechen"-Buttons auf false
					jb_Abbrechen_Focus = false;
				}
				if(compare.equals(jb_Abbrechen))						//Bekommt der Button"Abbrechen" den Focus
				{														//wird entsprechend wie beim Button "OK" verfahren, nur anders
					jb_Abbrechen_Focus = true;							//herum
					jb_Ok_Focus = false;
				}

			}

			public void focusLost(FocusEvent focevt)					//Verliert ein Button den Focus wieder
			{
				JButton compare = new JButton();						//Die Bool-Variable fuer den entsprechenden Button wird wieder auf
				compare = (JButton)focevt.getSource();					//false gesetzt
				if(compare.equals(jb_Ok))								
				{
					jb_Ok_Focus = false;
				}
				if(compare.equals(jb_Abbrechen))
				{
					jb_Abbrechen_Focus = false;
				}
			}
		};

		KeyListener plo_AnzNachDiaKeyListener = new KeyListener()		//Listener zum Abfangen von Return-Tastendruck
		{
			public void keyPressed(KeyEvent kevt)						
			{
				int compare = kevt.getKeyCode();						//Wenn die Taste Return/enter gedrueckt wird...
				if(compare == KeyEvent.VK_ENTER)						
				{
					if(jb_Ok_Focus == true)								//Wenn der Button "OK" den Focus hat
					{													
						Integer retVal = new Integer(getTf_AnzahlNachfragenText());	//Eingabewert wird ermittelt
						int_AnzahlNachfragen = retVal.intValue();		
						if((retVal.intValue() > 0) && (retVal.intValue() < 100))		//Abpruefen, ob ein Eingabefehler vorliegt
						{
							if(mode == 1)												//Wird der Dialog aus der Aktion "allesAendern" eines bestehenden Modells aufgerufen,
							{															//wird die Mehtode "neuesModell" im Hauptfenster mit der Konfiguration aufgerufen, die bewirkt, dass
								root.neuesModell(retVal.intValue(), 1, modellName);		//das neue Modell den Modellnamen und die Position beiebehaelt
							}
							else												//Wird der Dialog aus der Aktion "neues Modell" gestartet, wird die Methode "neuesModell" im
							{													//Hauptfenster angestossen, ein neues Fenster und Modell zu erstellen mit Defaultparametern
								root.neuesModell(retVal.intValue(), 0, "");
							}
							ref.dispose();										//Hernach wird der Dialog geschlossen
						}
						if(retVal.intValue() == 0)								//Wurde 0 eingegeben
						{
							plo_FehlerMeldung fehler = new plo_FehlerMeldung(3);
						}
						if(retVal.intValue() >= 100)							//Wurde ein zu grosser Wert eingegeben
						{
							plo_FehlerMeldung fehler = new plo_FehlerMeldung(2);
						}
						ref.dispose();									//Der Dialog wird geschlossen
					}
					if(jb_Abbrechen_Focus == true)						//Hat der Button "Abbrechen" den Focus
					{													//wird der Dialog geschlossen
						ref.dispose();
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

		jb_Ok.addActionListener(plo_AnzNachDiaButListener);				//Die Buttons werden mit den Listenern versehen
		jb_Ok.addFocusListener(plo_AnzNachDiaFocListener);				
		jb_Ok.addKeyListener(plo_AnzNachDiaKeyListener);				
		jb_Abbrechen.addActionListener(plo_AnzNachDiaButListener);		
		jb_Abbrechen.addFocusListener(plo_AnzNachDiaFocListener);		
		jb_Abbrechen.addKeyListener(plo_AnzNachDiaKeyListener);			

		this.getContentPane().setLayout(new FlowLayout());				//Hinzufuegen des Layouts
		this.getContentPane().add(anzahlNachfragenPanel);				//Hinzufuegen der Komponenten
		this.setVisible(true);											//Sichtbarmachen des Fensters

	}
//***** Ende Konstruktor ************************************************************************************

  //Hilfsmethode zur besseren Erstellung von
	public void buildConstraints(GridBagConstraints c, Component com, int x, int y, int width, int height, int fill, int ank, int pdx, int pdy, Insets ins)
	{																	
		c.gridx = x;													//Komponenten in GridBagConstraints
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		c.fill = fill;
		c.anchor = ank;
		c.ipadx = pdx;
		c.ipady = pdy;
		c.insets = ins;
		anzahlNachfragenGridBagLayout.setConstraints(com, c);
	}

//***** SET-Methoden *************************************************************************************
  
  //Methode zum Setzen des EingabeFeld-Textes
	public void setTf_AnzahlNachfragenText(String elem)					
	{
		tf_AnzahlNachfragen.setText(elem);
	}
	
  //Methode zum Setzen der Nachfragenzahl
	public void setInt_AnzahlNachfragen(int elem)						
	{
		int_AnzahlNachfragen = elem;
	}
//***** Ende SET-Methoden *********************************************************************************

	
//***** GET-Methoden **************************************************************************************
  
  //Methode zur Rueckgabe des Eingabefeldinhaltes
	public String getTf_AnzahlNachfragenText()							
	{
		return tf_AnzahlNachfragen.getText();
	}
	
  //Methode zur Rueckgabe der Nachfragenanzahl
	public int getInt_AnzahlNachfragen()								
	{
		return int_AnzahlNachfragen;
	}
	
//***** Ende GET-Methoden *********************************************************************************

}

//***** Ende Klasse plo_AnzahlNachfragenDialog *****************************************************************************************