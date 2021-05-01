//*******************************************************************
//*		plo_ÜberDialog.java											*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Göltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse des Info-Dialogs				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*******************************************************************
package plo_System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//*** Klasse plo_ÜberDialog ***-------------------------------------
public class plo_ueberDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8386350292304410051L;
	private JInternalFrame überFrame;								//Komponenten des Dialogs
	private JPanel überPanel;
	private JLabel überLabel1;
	private JLabel überLabel2;
	private JLabel überLabel3;
	private JLabel überLabel4;
	private JLabel überLabel5;
	private JLabel überLabel6;
	private JLabel überLabel7;
	private JLabel überLabel8;
	private JLabel überLabel9;
	private JLabel überLabel10;
	private final JButton jb_Ok;
	private GridBagLayout überGridBagLayout;
	private GridBagConstraints überGridBagConstraints;


//*** Konstruktor ***-------------------------------------------------
	public plo_ueberDialog()
	{
		final plo_ueberDialog ref = this;							//Erstellen eines Referenzobjekts für dispose()-Aufrufe

		überFrame = new JInternalFrame();
		this.setTitle("Über P.L.O.");
		überFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		überPanel = new JPanel();
		überLabel1 = new JLabel("Periodenorientierte Lagerhaltungs Optimierung");
		überLabel2 = new JLabel("(Version 1.1)");
		überLabel3 = new JLabel("(c) 2015/2016 ");
		überLabel4 = new JLabel("---------------------------------------------------------------------- ");
		überLabel5 = new JLabel("Programmiert von:");
		überLabel6 = new JLabel("Eugen Gering (2015/16)");
		überLabel7 = new JLabel("Melisa Gündüz (2015/16)");
		überLabel8 = new JLabel("Francis Göltner (2001/02)");
		überLabel9 = new JLabel("Helmut Lindinger (2001/02)");
		überLabel10 = new JLabel("Bernd Saile (2001/02)");
		jb_Ok = new JButton("     Ok     ");
		jb_Ok.setSize(150,25);
		überGridBagLayout = new GridBagLayout();
		überGridBagConstraints = new GridBagConstraints();
		überPanel.setLayout(überGridBagLayout);

		this.buildConstraints(überGridBagConstraints, überLabel1, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel2, 0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel3, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel4, 0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel5, 0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel6, 0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel7, 0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel8, 0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel9, 0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, überLabel10, 0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(überGridBagConstraints, jb_Ok, 0, 12, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, new Insets(5,0,5,0));

		this.setModal(true);
		this.setSize(380, 350);
		this.setLocation(200, 100);
		überPanel.add(überFrame);
		überPanel.add(überLabel1);
		überPanel.add(überLabel2);
		überPanel.add(überLabel3);
		überPanel.add(überLabel4);
		überPanel.add(überLabel5);
		überPanel.add(überLabel6);
		überPanel.add(überLabel7);
		überPanel.add(überLabel8);
		überPanel.add(überLabel9);
		überPanel.add(überLabel10);
		
		
		überPanel.add(jb_Ok);

		ActionListener plo_ÜberDiaButListener = new ActionListener()
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

		KeyListener plo_ÜberDiaKeyListener = new KeyListener()		//Listener zum Abfangen von Return-Tastendruck
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

		jb_Ok.addActionListener(plo_ÜberDiaButListener);
		jb_Ok.addKeyListener(plo_ÜberDiaKeyListener);

		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(überPanel);
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
			überGridBagLayout.setConstraints(com, c);
	}
//*** Hilfsmethode ***------------------------------------------------
}
//*** Ende der Klasse plo_ÜberDialog ***------------------------------