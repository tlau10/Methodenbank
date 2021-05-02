//*******************************************************************
//*																	*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthuelt die Klasse fuer den Datentyp, welcher	    *
//*		die Nachfrqagen eines Modells verwaltet		  				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*     Version 1.2 												*
//*     von Marco Weiue und Jenne Justin								*
//*																	*
//*******************************************************************

package plo_System;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//*** Klasse plo_ueberDialog ***********************************************************************************************
public class plo_ueberDialog extends JDialog
{
	private static final long serialVersionUID = -8386350292304410051L;
	private JInternalFrame ueberFrame;								//Komponenten des Dialogs
	private JPanel ueberPanel;
	private JLabel ueberLabel1;
	private JLabel ueberLabel2;
	private JLabel ueberLabel3;
	private JLabel ueberLabel4;
	private JLabel ueberLabel5;
	private JLabel ueberLabel6;
	private JLabel ueberLabel7;
	private JLabel ueberLabel8;
	private JLabel ueberLabel9;
	private JLabel ueberLabel10;
	private JLabel ueberLabel11;
	private JLabel ueberLabel12;
	private final JButton jb_Ok;
	private GridBagLayout ueberGridBagLayout;
	private GridBagConstraints ueberGridBagConstraints;


//*** Konstruktor ***************************************************************
	public plo_ueberDialog()
	{
		final plo_ueberDialog ref = this;				//Erstellen eines Referenzobjekts fuer dispose()-Aufrufe

		ueberFrame = new JInternalFrame();
		this.setTitle("ueber P.L.O.");
		ueberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		ueberPanel = new JPanel();
		ueberLabel1 = new JLabel("Periodenorientierte Lagerhaltungs Optimierung");
		ueberLabel2 = new JLabel("(Version 1.2)");
		ueberLabel3 = new JLabel("(c) 2016 ");
		ueberLabel4 = new JLabel("---------------------------------------------------------------------- ");
		ueberLabel5 = new JLabel("Programmiert von:");
		ueberLabel6 = new JLabel("Marco Weiue (2016)");
		ueberLabel7 = new JLabel("Jenne Justin (2016)");
		ueberLabel8 = new JLabel("Eugen Gering (2015/16)");
		ueberLabel9 = new JLabel("Melisa Guenduez (2015/16)");
		ueberLabel10 = new JLabel("Francis Gueltner (2001/02)");
		ueberLabel11 = new JLabel("Helmut Lindinger (2001/02)");
		ueberLabel12 = new JLabel("Bernd Saile (2001/02)");
		jb_Ok = new JButton("     Ok     ");
		jb_Ok.setSize(150,25);
		ueberGridBagLayout = new GridBagLayout();
		ueberGridBagConstraints = new GridBagConstraints();
		ueberPanel.setLayout(ueberGridBagLayout);

		this.buildConstraints(ueberGridBagConstraints, ueberLabel1, 0, 0, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel2, 0, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel3, 0, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel4, 0, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel5, 0, 4, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel6, 0, 5, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel7, 0, 6, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel8, 0, 7, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel9, 0, 8, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel10, 0, 9, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel11, 0, 10, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, ueberLabel12, 0, 11, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0, 0, new Insets(5,0,5,0));
		this.buildConstraints(ueberGridBagConstraints, jb_Ok, 0, 12, 1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, 5, 5, new Insets(5,0,5,0));

		this.setModal(true);
		this.setSize(480, 450);
		this.setLocation(200, 100);
		ueberPanel.add(ueberFrame);
		ueberPanel.add(ueberLabel1);
		ueberPanel.add(ueberLabel2);
		ueberPanel.add(ueberLabel3);
		ueberPanel.add(ueberLabel4);
		ueberPanel.add(ueberLabel5);
		ueberPanel.add(ueberLabel6);
		ueberPanel.add(ueberLabel7);
		ueberPanel.add(ueberLabel8);
		ueberPanel.add(ueberLabel9);
		ueberPanel.add(ueberLabel10);
		ueberPanel.add(ueberLabel11);
		ueberPanel.add(ueberLabel12);
		
		ueberPanel.add(jb_Ok);

		ActionListener plo_ueberDiaButListener = new ActionListener()
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

		KeyListener plo_ueberDiaKeyListener = new KeyListener()		//Listener zum Abfangen von Return-Tastendruck
		{
			public void keyPressed(KeyEvent kevt)						
			{
				int compare = kevt.getKeyCode();						//Wenn die Taste Return/enter gedrueckt wird...
				if(compare == KeyEvent.VK_ENTER)						
				{
					ref.dispose();										//Schlieueen der Nachricht
				}
			}
			public void keyReleased(KeyEvent kevt)
			{

			}
			public void keyTyped(KeyEvent kevt)
			{

			}
		};

		jb_Ok.addActionListener(plo_ueberDiaButListener);
		jb_Ok.addKeyListener(plo_ueberDiaKeyListener);

		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(ueberPanel);
		this.setVisible(true);
	}
//*** Ende Konstruktor *********************************************************

	
//*** Hilfsmethode ************************************************************
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
			ueberGridBagLayout.setConstraints(com, c);
	}
//*** Ende Hilfsmethode ***********************************************************
}
//*** Ende der Klasse plo_ueberDialog ********************************************************************************************************************