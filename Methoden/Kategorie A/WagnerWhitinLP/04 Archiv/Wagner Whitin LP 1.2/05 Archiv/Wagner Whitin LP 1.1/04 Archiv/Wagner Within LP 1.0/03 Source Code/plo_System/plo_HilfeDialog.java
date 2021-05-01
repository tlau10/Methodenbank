//*******************************************************************
//*		plo_HilfeDialog.java										*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Göltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse die Darstellung der Online-	*
//*		Hilfe des P.L.O.-Systems									*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//*** Klasse plo_HilfeDialog ***-------------------------------------
public class plo_HilfeDialog extends JDialog
{
	private JInternalFrame hilfeFrame;								//Komponeten des Dialogs
	private JPanel hilfePanel;
	private JLabel hilfeLabel1;
	private JLabel hilfeLabel2;
	private JLabel hilfeLabel3;
	private JLabel hilfeLabel4;
	private JLabel hilfeLabel5;
	private JLabel hilfeLabel6;
	private JLabel hilfeLabel7;
	private JLabel hilfeLabel8;
	private JLabel hilfeLabel9;
	private final JButton jb_Ok;
	private GridBagLayout hilfeGridBagLayout;
	private GridBagConstraints hilfeGridBagConstraints;


//*** Konstruktor ***-------------------------------------------------
	public plo_HilfeDialog()
	{
		final plo_HilfeDialog ref = this;							//Initialisieren des Referenzobjekts für dispose()-Aufrufe

		hilfeFrame = new JInternalFrame();
		this.setTitle("P.L.O.-Hilfe");
		hilfeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		hilfePanel = new JPanel();
		hilfeLabel1 = new JLabel("Periodenorientierte Lagerhaltungs Optimierung");
		hilfeLabel2 = new JLabel(" ");
		hilfeLabel3 = new JLabel("---------------------------------------------------------------------- ");
		hilfeLabel4 = new JLabel(" Die Online-Hilfe ist aus Traditionsgründen noch nicht verfügbar");
		hilfeLabel5 = new JLabel("---------------------------------------------------------------------- ");
		hilfeLabel6 = new JLabel(" ");
		hilfeLabel7 = new JLabel("Konsultieren sie bitte die Dokumentationsunterlagen");
		hilfeLabel8 = new JLabel("Vielen Dank!");
		jb_Ok = new JButton("     Ok     ");
		jb_Ok.setSize(150,25);
		hilfeGridBagLayout = new GridBagLayout();
		hilfeGridBagConstraints = new GridBagConstraints();
		hilfePanel.setLayout(hilfeGridBagLayout);

		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel1, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//Erstellen der GridBagConstraints
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel2, 0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel3, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel4, 0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel5, 0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel6, 0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel7, 0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(hilfeGridBagConstraints, hilfeLabel8, 0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));	//
		this.buildConstraints(hilfeGridBagConstraints, jb_Ok, 0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, new Insets(5,0,5,0));		//

		this.setModal(true);
		this.setSize(380, 350);
		this.setLocation(200, 100);
		hilfePanel.add(hilfeFrame);
		hilfePanel.add(hilfeLabel1);
		hilfePanel.add(hilfeLabel2);
		hilfePanel.add(hilfeLabel3);
		hilfePanel.add(hilfeLabel4);
		hilfePanel.add(hilfeLabel5);
		hilfePanel.add(hilfeLabel6);
		hilfePanel.add(hilfeLabel7);
		hilfePanel.add(hilfeLabel8);
		hilfePanel.add(jb_Ok);

		ActionListener plo_HilfeDiaButListener = new ActionListener()
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

		KeyListener plo_HilfeDiaKeyListener = new KeyListener()		//Listener zum Abfangen von Return-Tastendruck
		{
			public void keyPressed(KeyEvent kevt)						//
			{
				int compare = kevt.getKeyCode();						//Wenn die Taste Return/enter gedrückt wird...
				if(compare == KeyEvent.VK_ENTER)						//
				{
					ref.dispose();										//Schließen der Nachricht
				}
			}
			public void keyReleased(KeyEvent kevt)
			{

			}
			public void keyTyped(KeyEvent kevt)
			{

			}
		};

		jb_Ok.addActionListener(plo_HilfeDiaButListener);
		jb_Ok.addKeyListener(plo_HilfeDiaKeyListener);

		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(hilfePanel);
		this.setVisible(true);
	}
//*** Ende Konstruktor ***-----------------------------------------

//*** Hilfsmehtode ***---------------------------------------------
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
			hilfeGridBagLayout.setConstraints(com, c);
	}
//*** Ende der Hilfsmehtode ***--------------------------------------
}
//*** Ende der Klasse plo_HilfeDialog ***----------------------------