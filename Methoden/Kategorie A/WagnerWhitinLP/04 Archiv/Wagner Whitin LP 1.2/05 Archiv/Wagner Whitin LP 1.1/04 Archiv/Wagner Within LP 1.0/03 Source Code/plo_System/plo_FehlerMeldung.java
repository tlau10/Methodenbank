//*******************************************************************
//*		plo_FehlerMeldung.java										*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Klasse f�r die Ausgabe verschiedner	*
//*		Fehlermeldungen								 				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//*** Klasse plo_FehlerMeldung ***------------------------------------
public class plo_FehlerMeldung extends JDialog
{
		private JInternalFrame FehlerFrame;								//Komponenten des Dialogs
		private JPanel FehlerPanel;
		private JLabel FehlerLabel1;
		private JLabel FehlerLabel2;
		private JLabel FehlerLabel3;
		private final JButton jb_Ok;
		private GridBagLayout FehlerGridBagLayout;
		private GridBagConstraints FehlerGridBagConstraints;


//*** Konstruktor ***-------------------------------------------------
	public plo_FehlerMeldung(int value)
	{
		final plo_FehlerMeldung ref = this;							//Erstellen eines Referenzobjekts f�r dispose()-Aufrufe

		FehlerFrame = new JInternalFrame();
		this.setTitle("P.L.O.-Fehlermeldung");
		FehlerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		FehlerPanel = new JPanel();
		switch(value)
		{
		case 1:														//Keine g�ltige Nachfrage ausgew�hlt
			FehlerLabel1 = new JLabel("Eingabefehler:");
			FehlerLabel2 = new JLabel("-----------------------------------------------");
			FehlerLabel3 = new JLabel("W�hlen sie eine g�ltige Nachfragennummer aus!");
			break;
		case 2:														//Zu gro�es System
			FehlerLabel1 = new JLabel("Eingabefehler:");
			FehlerLabel2 = new JLabel("-----------------------------------------------");
			FehlerLabel3 = new JLabel("Das eingegebene Modell ist zu gro�!");
			break;
		case 3:														//Keine Anzahl Nachfragen = 0
			FehlerLabel1 = new JLabel("Eingabefehler:");
			FehlerLabel2 = new JLabel("-----------------------------------------------");
			FehlerLabel3 = new JLabel("Geben sie eine Nachfragenzahl > o ein!");
			break;
		case 4:														//Infeasible Solution
			FehlerLabel1 = new JLabel("Fehler in Lagersystem");
			FehlerLabel2 = new JLabel("-----------------------------------------------");
			FehlerLabel3 = new JLabel("System nicht l�sbar");
			break;
		case 5:														//Unbounded
			FehlerLabel1 = new JLabel("Fehler in Lagersystem");
			FehlerLabel2 = new JLabel("-----------------------------------------------");
			FehlerLabel3 = new JLabel("System unbounded");
			break;
		}

		jb_Ok = new JButton("     Ok     ");
		jb_Ok.setSize(150,25);
		FehlerGridBagLayout = new GridBagLayout();
		FehlerGridBagConstraints = new GridBagConstraints();
		FehlerPanel.setLayout(FehlerGridBagLayout);

		this.buildConstraints(FehlerGridBagConstraints, FehlerLabel1, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(FehlerGridBagConstraints, FehlerLabel2, 0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(FehlerGridBagConstraints, FehlerLabel3, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(FehlerGridBagConstraints, jb_Ok, 0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, new Insets(5,0,5,0));

		this.setModal(true);
		this.setSize(400, 170);
		this.setLocation(200, 100);
		FehlerPanel.add(FehlerFrame);
		FehlerPanel.add(FehlerLabel1);
		FehlerPanel.add(FehlerLabel2);
		FehlerPanel.add(FehlerLabel3);
		FehlerPanel.add(jb_Ok);

		ActionListener plo_FehlerMeldungButListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent aevt)
			{
				JButton compare = new JButton();
				compare = (JButton)aevt.getSource();
				if(compare.equals(jb_Ok))
				{
					ref.dispose();
				}
			}
		};

		KeyListener plo_FehlerMeldungKeyListener = new KeyListener()		//Listener zum Abfangen von Return-Tastendruck
		{
			public void keyPressed(KeyEvent kevt)						//
			{
				int compare = kevt.getKeyCode();						//Wenn die Taste Return/enter gedr�ckt wird...
				if(compare == KeyEvent.VK_ENTER)						//
				{
					ref.dispose();										//Schlie�en der Nachricht
				}
			}
			public void keyReleased(KeyEvent kevt)
			{

			}
			public void keyTyped(KeyEvent kevt)
			{

			}
		};

		jb_Ok.addActionListener(plo_FehlerMeldungButListener);
		jb_Ok.addKeyListener(plo_FehlerMeldungKeyListener);

		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(FehlerPanel);
		this.setVisible(true);
	}
//*** Ende Konstruktor ***--------------------------------------------

//*** Hilfsmethode ***------------------------------------------------
	public void buildConstraints(GridBagConstraints c, Component com, int x, int y, int width, int height, int fill, int ank, int pdx, int pdy, Insets ins)
	{																//Hilfsmethode zur besseren Erstellung von
			c.gridx = x;											//Komponenten in GridBagConstraints
			c.gridy = y;
			c.gridwidth = width;
			c.gridheight = height;
			c.fill = fill;
			c.anchor = ank;
			c.ipadx = pdx;
			c.ipady = pdy;
			c.insets = ins;
			FehlerGridBagLayout.setConstraints(com, c);
	}
//*** Hilfsmethode ***------------------------------------------------
}
//*** Ende Klasse plo_FehlerMeldung ***-------------------------------