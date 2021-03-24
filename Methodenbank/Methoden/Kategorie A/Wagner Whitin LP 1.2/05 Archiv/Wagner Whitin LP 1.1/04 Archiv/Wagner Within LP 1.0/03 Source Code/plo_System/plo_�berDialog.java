//*******************************************************************
//*		plo_�berDialog.java											*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis G�ltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enth�lt die Klasse des Info-Dialogs				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//*** Klasse plo_�berDialog ***-------------------------------------
public class plo_�berDialog extends JDialog
{
	private JInternalFrame �berFrame;								//Komponenten des Dialogs
	private JPanel �berPanel;
	private JLabel �berLabel1;
	private JLabel �berLabel2;
	private JLabel �berLabel3;
	private JLabel �berLabel4;
	private JLabel �berLabel5;
	private JLabel �berLabel6;
	private JLabel �berLabel7;
	private JLabel �berLabel8;
	private JLabel �berLabel9;
	private final JButton jb_Ok;
	private GridBagLayout �berGridBagLayout;
	private GridBagConstraints �berGridBagConstraints;


//*** Konstruktor ***-------------------------------------------------
	public plo_�berDialog()
	{
		final plo_�berDialog ref = this;							//Erstellen eines Referenzobjekts f�r dispose()-Aufrufe

		�berFrame = new JInternalFrame();
		this.setTitle("�ber P.L.O.");
		�berFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		�berPanel = new JPanel();
		�berLabel1 = new JLabel("Periodenorientierte Lagerhaltungs Optimierung");
		�berLabel2 = new JLabel("(Version 1.0)");
		�berLabel3 = new JLabel("(c) 2001/2002 ");
		�berLabel4 = new JLabel("---------------------------------------------------------------------- ");
		�berLabel5 = new JLabel("Programm von:");
		�berLabel6 = new JLabel("Francis G�ltner");
		�berLabel7 = new JLabel("Helmut Lindinger");
		�berLabel8 = new JLabel("Bernd Saile");
		jb_Ok = new JButton("     Ok     ");
		jb_Ok.setSize(150,25);
		�berGridBagLayout = new GridBagLayout();
		�berGridBagConstraints = new GridBagConstraints();
		�berPanel.setLayout(�berGridBagLayout);

		this.buildConstraints(�berGridBagConstraints, �berLabel1, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(�berGridBagConstraints, �berLabel2, 0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(�berGridBagConstraints, �berLabel3, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(�berGridBagConstraints, �berLabel4, 0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(�berGridBagConstraints, �berLabel5, 0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(�berGridBagConstraints, �berLabel6, 0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(�berGridBagConstraints, �berLabel7, 0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(�berGridBagConstraints, �berLabel8, 0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(�berGridBagConstraints, jb_Ok, 0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, new Insets(5,0,5,0));

		this.setModal(true);
		this.setSize(380, 350);
		this.setLocation(200, 100);
		�berPanel.add(�berFrame);
		�berPanel.add(�berLabel1);
		�berPanel.add(�berLabel2);
		�berPanel.add(�berLabel3);
		�berPanel.add(�berLabel4);
		�berPanel.add(�berLabel5);
		�berPanel.add(�berLabel6);
		�berPanel.add(�berLabel7);
		�berPanel.add(�berLabel8);
		�berPanel.add(jb_Ok);

		ActionListener plo_�berDiaButListener = new ActionListener()
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

		KeyListener plo_�berDiaKeyListener = new KeyListener()		//Listener zum Abfangen von Return-Tastendruck
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

		jb_Ok.addActionListener(plo_�berDiaButListener);
		jb_Ok.addKeyListener(plo_�berDiaKeyListener);

		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(�berPanel);
		this.setVisible(true);
	}
//*** Konstruktor ***-------------------------------------------------

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
			�berGridBagLayout.setConstraints(com, c);
	}
//*** Hilfsmethode ***------------------------------------------------
}
//*** Ende der Klasse plo_�berDialog ***------------------------------