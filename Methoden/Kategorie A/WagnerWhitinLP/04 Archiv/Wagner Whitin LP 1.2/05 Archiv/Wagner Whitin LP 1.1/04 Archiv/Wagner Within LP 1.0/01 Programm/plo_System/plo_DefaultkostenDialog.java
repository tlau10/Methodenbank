//*******************************************************************
//*		plo_DefaultkostenDialog.java								*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Klasse f�r den User-Dialog, 			*
//*		in welchem die Standartwerte f�r Bestell- und Lager-		*
//*		kosten zur Laufzeit ver�ndert werden k�nnen					*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//*** Klasse plo_DefaultkostenDialog ***----------------------------------
public class plo_DefaultkostenDialog extends JDialog
{
	private plo_DefaultkostenDialog ref;								//Referenzobjekt f�r den Aufruf von dispose()
	private JInternalFrame dialogFrame;									//
	private plo_Hauptfenster root;										//Referenzobjekt auf das Hauptfenster

	private JPanel defaultkostenPanel;									//Darstellungspanel
	private JLabel label1;												//Beschriftungskomponenten
	private JLabel bestellkostenLabel;									//
	private JLabel lagerkostenLabel;

	private JTextField tf_Bestellkosten;								//Textfelder
	private JTextField tf_Lagerkosten;									//

	private JButton jb_Ok;												//Schalter
	private JButton jb_Abbrechen;										//
	private boolean jb_Ok_Focus;										//Anzeigevaribalen um festzustellen
	private boolean jb_Abbrechen_Focus;									//welcher Button den Fokus bekommen hat

	private GridBagLayout d_DefaultkostenGridBagLayout;					//Layout des Dialogs
	private GridBagConstraints d_DefaultkostenGridBagConstraints;		//

//*** Konstruktor ***------------------------------------------------------
	public plo_DefaultkostenDialog(plo_Hauptfenster base)
	{
		root = base;													//Initialisieren der Refernzobjekte
		ref = this;														//

		dialogFrame = new JInternalFrame();								//Frameinitialisierung
		dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//

		defaultkostenPanel = new JPanel();								//Initialisierung der Dialog-Komponenten
		label1 = new JLabel("Bitte geben sie die Standardwerte folgender Kosten ein");
		bestellkostenLabel = new JLabel("Bestellkosten:  ");			//
		lagerkostenLabel = new JLabel("Lagerkosten:  ");				//
		tf_Bestellkosten = new JTextField ("20", 10);					//
		tf_Lagerkosten = new JTextField ("0.1", 10);					//
		jb_Ok = new JButton ("Ok");										//
		jb_Ok.setSize(150,25);											//
		jb_Abbrechen = new JButton("Abbrechen");						//
		jb_Abbrechen.setSize(100,25);									//

		jb_Ok_Focus = false;											//
		jb_Abbrechen_Focus = false;										//

		d_DefaultkostenGridBagLayout = new GridBagLayout();				//Erstellen des Layouts
		d_DefaultkostenGridBagConstraints = new GridBagConstraints();	//

		this.buildConstraints(d_DefaultkostenGridBagConstraints, label1, 0, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));				//Erstellen der Constraints
		this.buildConstraints(d_DefaultkostenGridBagConstraints, bestellkostenLabel, 0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));	//f�r die Darstellungskomponenten
		this.buildConstraints(d_DefaultkostenGridBagConstraints, lagerkostenLabel, 0, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(d_DefaultkostenGridBagConstraints, tf_Bestellkosten, 2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(d_DefaultkostenGridBagConstraints, tf_Lagerkosten, 2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));		//
		this.buildConstraints(d_DefaultkostenGridBagConstraints, jb_Ok, 0, 3, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));			//
		this.buildConstraints(d_DefaultkostenGridBagConstraints, jb_Abbrechen, 2, 3, 1, 1, GridBagConstraints.NONE	, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));		//

		defaultkostenPanel.setLayout(d_DefaultkostenGridBagLayout);		//Hinzuf�gen der Komponente
		defaultkostenPanel.add(label1);									//zum Darstellungspanel
		defaultkostenPanel.add(bestellkostenLabel);						//
		defaultkostenPanel.add(lagerkostenLabel);						//
		defaultkostenPanel.add(tf_Bestellkosten);						//
		defaultkostenPanel.add(tf_Lagerkosten);							//
		defaultkostenPanel.add(jb_Ok);									//
		defaultkostenPanel.add(jb_Abbrechen);							//

		ActionListener plo_DefkostDiaListener = new ActionListener()	//Definition des Listeners f�r
		{																//Knopfdr�cke
			public void actionPerformed(ActionEvent aevt)				//
			{															//
				JButton compare = new JButton();						//Feststellen, welcher Button degr�ckt
				compare = (JButton)aevt.getSource();					//wurde
				if(compare.equals(jb_Ok))									//Ist es der Button "ok"
				{															//
					Double buffer = new Double(tf_Bestellkosten.getText());	//Gewinnen der Inhalte
					root.setDefaultBestellkosten(buffer.doubleValue());		//der Textfelder und setzen der Defaultwerte
					buffer = new Double(tf_Lagerkosten.getText());			//im Hauptfenster
					root.setDefaultLagerkosten(buffer.doubleValue());		//
					ref.dispose();											//Schlie�en des Dialogs
				}
				if(compare.equals(jb_Abbrechen))						//Falls "abbrechen" geklickt wurde
				{														//
					ref.dispose();										//Schlie�en des Dialogs
				}														//
			}
		};

		FocusListener plo_DefkostDiaFocListener = new FocusListener()	//Definition des Fokus-Listeners
		{																//
			public void focusGained(FocusEvent focevt)					//
			{															//
				JButton compare = new JButton();						//Feststellen, von welchem Knopf der Fokus
				compare = (JButton)focevt.getSource();					//ver�ndert wurde
				if(compare.equals(jb_Ok))								//Falls "ok" den Fokus bekommen hat
				{														//
					jb_Ok_Focus = true;									//Anpassen der Indikatorvariablen
					jb_Abbrechen_Focus = false;							//
				}														//
				if(compare.equals(jb_Abbrechen))						//Falls "abbrechen" den Fokus bekommen hat
				{														//
					jb_Abbrechen_Focus = true;							//Anpassen der Indikatorvariablen
					jb_Ok_Focus = false;								//
				}

			}

			public void focusLost(FocusEvent focevt)					//Falls ein Button den Fokus wieder verliert
			{															//
				JButton compare = new JButton();						//Feststellen, welcher Button es ist
				compare = (JButton)focevt.getSource();					//
				if(compare.equals(jb_Ok))								//Falls es "ok" ist
				{														//
					jb_Ok_Focus = false;								//Anpassen der Indikatorvariablen
				}														//
				if(compare.equals(jb_Abbrechen))						//Falls es "abbrechen" ist
				{														//
					jb_Abbrechen_Focus = false;							//Anpassen der Indikatorvariablen
				}														//
			}
		};

		KeyListener plo_DefkostDiaKeyListener = new KeyListener()		//Definition des Tastendrucklisteners
		{																//
			public void keyPressed(KeyEvent kevt)						//
			{															//
				int compare = kevt.getKeyCode();						//Falls es sich um die Taste "ENTER" handelt
				if(compare == KeyEvent.VK_ENTER)						//
				{
					if(jb_Ok_Focus == true)										//Sollte "Ok" den Fokus haben
					{															//
						Double buffer = new Double(tf_Bestellkosten.getText());	//Gewinnen der Textfeldinhalte und
						root.setDefaultBestellkosten(buffer.doubleValue());		//Speichern in den Defaultvariblen
						buffer = new Double(tf_Lagerkosten.getText());			//des Hauptfensters.
						root.setDefaultLagerkosten(buffer.doubleValue());		//
						ref.dispose();											//Schlie�en des Dialogs
					}
					if(jb_Abbrechen_Focus == true)						//Sollte "abbrechen" den Fokus haben
					{													//
						ref.dispose();									//Schlie�en des Dialogs
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

		jb_Ok.addActionListener(plo_DefkostDiaListener);				//Verbinden von Schaltfl�chen und
		jb_Ok.addFocusListener(plo_DefkostDiaFocListener);				//Listenern
		jb_Ok.addKeyListener(plo_DefkostDiaKeyListener);				//
		jb_Abbrechen.addActionListener(plo_DefkostDiaListener);			//
		jb_Abbrechen.addFocusListener(plo_DefkostDiaFocListener);		//
		jb_Abbrechen.addKeyListener(plo_DefkostDiaKeyListener);			//

		defaultkostenPanel.add(dialogFrame);							//Hinzuf�gen des Darstellungsframes zum Dialog

		this.setTitle("Standardwerte f�r Kosten");						//Setzen der Standardparameter
		this.getContentPane().add(defaultkostenPanel);					//des Dialogs
		this.setSize(400, 170);											//
		this.setLocation(200, 200);										//
		this.setModal(true);											//
		this.setVisible(true);											//
	}
//*** Ende Konstruktor ***-------------------------------------------------

	public void buildConstraints(GridBagConstraints c, Component com, int x, int y, int width, int height, int fill, int ank, int pdx, int pdy, Insets ins)
	{																	//Hilfsmethode zur besseren Erstellung von
		c.gridx = x;													//Komponenten in GridBagConstraints
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		c.fill = fill;
		c.anchor = ank;
		c.ipadx = pdx;
		c.ipady = pdy;
		c.insets = ins;
		d_DefaultkostenGridBagLayout.setConstraints(com, c);
	}

//*** SET-Methoden ***-----------------------------------------------------

//*** Ende SET-Methoden ***------------------------------------------------

//*** GET-Methoden ***-----------------------------------------------------
	public String getTf_BestellkostenText()								//Methode zur R�ckgabe des Inhalts des Textfelds Bestellkosten
	{
		return tf_Bestellkosten.getText();
	}

	public String getTf_LagerkostenText()								//Methode zur R�ckgabe des Inhalts des Textfelds Lagerkosten
	{
		return tf_Lagerkosten.getText();
	}
//*** Ende GET-Methoden ***------------------------------------------------
}
//*** Ende Klasse plo_DefaultkostenDialog ***------------------------------