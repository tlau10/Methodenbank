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
import java.io.*;
import javax.swing.*;

//*** Klasse plo_SolverDatenDialog **************************************************************************************************
public class plo_SolverDatenDialog extends JDialog{
	private static final long serialVersionUID = -4534700905270038966L;
	private plo_SolverDatenDialog ref;									//Referenzobjekt zum Aufruf von dispose()
	private JInternalFrame dialogFrame;									
	private plo_Hauptfenster root;										//Referenzobjekt auf das Hauptfenster

	private JPanel solverDatenPanel;									//Darstellungskomonenten
	private JLabel label1;												
	private JLabel arbeitsverzeichnisLabel;								
	private JLabel solverNameLabel;
	private JLabel solverPfadLabel;

	private JTextField tf_Arbeitsverzeichnis;							//Textfelder
	private JTextField tf_SolverName;									
	private JTextField tf_SolverPfad;									

	private JButton jb_Ok;												//Schaltflaechen
	private JButton jb_Abbrechen;										
	private boolean jb_Ok_Focus;										//Indikatorvariablen fuer den Fokusstatus
	private boolean jb_Abbrechen_Focus;									

	private GridBagLayout d_SolverDatenGridBagLayout;					//Layout des Dialogs
	private GridBagConstraints d_SolverDatenGridBagConstraints;			
	

//*** Konstruktor *************************************************************************************
	public plo_SolverDatenDialog(plo_Hauptfenster base)
	{
		root = base;													//Initialisieren der Referenzobjekte
		ref = this;														

		dialogFrame = new JInternalFrame();								//Frameinitialisierung
		dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	

		solverDatenPanel = new JPanel();								//Initialisierung der Komponenten
		label1 = new JLabel("Bitte geben sie die Standardwerte folgender Kosten ein");
		solverNameLabel = new JLabel("Dateiname des Solvers:  ");		
		solverPfadLabel = new JLabel("Solverpfad:  ");					
		arbeitsverzeichnisLabel = new JLabel("Arbeitsverzeichnis:  ");	

		tf_Arbeitsverzeichnis = new JTextField (root.getArbeitsverzeichnis(), 20);//Initialisieren der Textfelder
		tf_SolverName = new JTextField (root.getSolverName(), 20);					
		tf_SolverPfad = new JTextField (root.getSolverPfad(), 20);					
		jb_Ok = new JButton ("Ok");										//Initialisieren der Buttons
		jb_Ok.setSize(150,25);											
		jb_Abbrechen = new JButton("Abbrechen");						
		jb_Abbrechen.setSize(100,25);									

		jb_Ok_Focus = false;											//Initialisieren der Indikatorvariablen
		jb_Abbrechen_Focus = false;										

		d_SolverDatenGridBagLayout = new GridBagLayout();				//Erstellen des Layouts
		d_SolverDatenGridBagConstraints = new GridBagConstraints();		

		this.buildConstraints(d_SolverDatenGridBagConstraints, label1, 0, 0, 3, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));					//Erstellen der Constraints
		this.buildConstraints(d_SolverDatenGridBagConstraints, arbeitsverzeichnisLabel, 0, 1, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));	//fuer die Darstellungskomponenten
		this.buildConstraints(d_SolverDatenGridBagConstraints, solverNameLabel, 0, 2, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));			//
		this.buildConstraints(d_SolverDatenGridBagConstraints, solverPfadLabel, 0, 3, 2, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));			//
		this.buildConstraints(d_SolverDatenGridBagConstraints, tf_Arbeitsverzeichnis, 2, 1, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));		//
		this.buildConstraints(d_SolverDatenGridBagConstraints, tf_SolverName, 2, 2, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));				//
		this.buildConstraints(d_SolverDatenGridBagConstraints, tf_SolverPfad, 2, 3, 1, 1, GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));				//
		this.buildConstraints(d_SolverDatenGridBagConstraints, jb_Ok, 0, 4, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));				//
		this.buildConstraints(d_SolverDatenGridBagConstraints, jb_Abbrechen, 2, 4, 1, 1, GridBagConstraints.NONE	, GridBagConstraints.WEST, 0, 0, new Insets(5,0,5,0));			//

		solverDatenPanel.setLayout(d_SolverDatenGridBagLayout);			//Hinzufuegen der Komponenten
		solverDatenPanel.add(label1);									//zum Darstellungspanel
		solverDatenPanel.add(arbeitsverzeichnisLabel);					
		solverDatenPanel.add(solverNameLabel);							
		solverDatenPanel.add(solverPfadLabel);							
		solverDatenPanel.add(tf_Arbeitsverzeichnis);					
		solverDatenPanel.add(tf_SolverName);							
		solverDatenPanel.add(tf_SolverPfad);							
		solverDatenPanel.add(jb_Ok);									
		solverDatenPanel.add(jb_Abbrechen);								

		ActionListener plo_SolverDatenDiaListener = new ActionListener()//Listener fuer
		{																//Klicks auf die Schaltflaeche
			public void actionPerformed(ActionEvent aevt)				
			{															
				JButton compare = new JButton();						//Feststellen, welche Schaltflaeche geklickt wurde
				compare = (JButton)aevt.getSource();					
				if(compare.equals(jb_Ok))											//Ist es "ok"
				{														
					root.setArbeitsverzeichnis(tf_Arbeitsverzeichnis.getText());	//Setzen der Pfade und Dateien im Hauptfenster mit
					root.setSolverName(tf_SolverName.getText());					//dem Inhalt der Textfelder
					root.setSolverPfad(tf_SolverPfad.getText());		
					ref.solveriniAktualisieren();
					ref.dispose();													//Schliessen des Dialogs
				}
				if(compare.equals(jb_Abbrechen))						//Ist "abbrechen" geklickt worden
				{														
					ref.dispose();										//Schliessen des Dialogs
				}														
			}
		};

		FocusListener plo_SolverDatenDiaFocListener = new FocusListener()	//Listenerdefinition fuer FokusAenderungen
		{																
			public void focusGained(FocusEvent focevt)					
			{															//Ermitteln, welcher Button den Fokus
				JButton compare = new JButton();						//bekommen hat
				compare = (JButton)focevt.getSource();					
				if(compare.equals(jb_Ok))								//hat "ok" den Fokus bekommen
				{														
					jb_Ok_Focus = true;									//Anpassen der Inidkatorvariablen
					jb_Abbrechen_Focus = false;							
				}														
				if(compare.equals(jb_Abbrechen))						//hat "abbrechen" den Fokus bekommen
				{														
					jb_Abbrechen_Focus = true;							//Anpassen der Inidkatorvariablen
					jb_Ok_Focus = false;								
				}

			}

			public void focusLost(FocusEvent focevt)					
			{															
				JButton compare = new JButton();						//Feststellen, welcher
				compare = (JButton)focevt.getSource();					//Button den Fokus verloren hat
				if(compare.equals(jb_Ok))								//Hat "ok" den Fokus verloren
				{														
					jb_Ok_Focus = false;								//Anpassen der Inidkatorvariablen
				}														
				if(compare.equals(jb_Abbrechen))						//Hat "abbrechen" den Fokus verloren
				{														
					jb_Abbrechen_Focus = false;							//Anpassen der Inidkatorvariablen
				}														
			}
		};

		KeyListener plo_SolverDatenDiaKeyListener = new KeyListener()	//Definition des TastedruckListeners
		{																
			public void keyPressed(KeyEvent kevt)						
			{															
				int compare = kevt.getKeyCode();						//Ist die Taste "ENTER" gedrueckt worden
				if(compare == KeyEvent.VK_ENTER)						
				{
					if(jb_Ok_Focus == true)								//Falls Schalter "ok" den Fokus hat
					{													
					root.setArbeitsverzeichnis(tf_Arbeitsverzeichnis.getText());	//Setzen der Pfade und Dateinamen im
					root.setSolverName(tf_SolverName.getText());					//Hauptfenster
					root.setSolverPfad(tf_SolverPfad.getText());		
					ref.solveriniAktualisieren();
					ref.dispose();													//Schliessen des Dialogs
					}
					if(jb_Abbrechen_Focus == true)						//Falls Schalter "abbrechen" den Fokus hat
					{													
						ref.dispose();									//Schliessen des Dialogs
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

		jb_Ok.addActionListener(plo_SolverDatenDiaListener);				//Hinzufuegen der Listener zu
		jb_Ok.addFocusListener(plo_SolverDatenDiaFocListener);				//den Schaltern
		jb_Ok.addKeyListener(plo_SolverDatenDiaKeyListener);			
		jb_Abbrechen.addActionListener(plo_SolverDatenDiaListener);		
		jb_Abbrechen.addFocusListener(plo_SolverDatenDiaFocListener);	
		jb_Abbrechen.addKeyListener(plo_SolverDatenDiaKeyListener);		

		solverDatenPanel.add(dialogFrame);								//Hinzufuegen des Frames

		this.setTitle("Solverstandards");								//Setzen der Dialogparameter
		this.getContentPane().add(solverDatenPanel);					
		this.setSize(450, 220);											
		this.setLocation(200, 200);										
		this.setModal(true);											
		this.setVisible(true);											
	}
//*** Ende Konstruktor *****************************************************************************
	

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
		d_SolverDatenGridBagLayout.setConstraints(com, c);
	}

//*** KEINE SET-Methoden ************************************************************************************


//*** GET-Methoden *******************************************************************************
	public String getTf_ArbeitsverzeichnisText()						//Methode zur Rueckgabe des Inhalts des Textfelds Bestellkosten
	{
		return tf_Arbeitsverzeichnis.getText();
	}

	public String getTf_SolverNameText()								//Methode zur Rueckgabe des Inhalts des Textfelds Lagerkosten
	{
		return tf_SolverName.getText();
	}

	public String getTf_SolverPfadText()								//Methode zur Rueckgabe des Inhalts des Textfelds Lagerkosten
	{
		return tf_SolverPfad.getText();
	}
//*** Ende GET-Methoden ******************************************************************************

	
//*** Methode zum Aktualisieren der Solverini ********************************************************
	public void solveriniAktualisieren()
	{
		String buffer = new String("");
		try															//Erstellen des Stromobjektes fuer die Dateiausgabe
		{															
			BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream((root.getArbeitsverzeichnis()+"solverini")));
		}
		catch (FileNotFoundException fnfe)
		{	}

		try
		{
			BufferedWriter w = new BufferedWriter(new FileWriter("solverini.txt"));	//oeffnen bzw. Erstellen der
			buffer = new String("Working Directory:");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String(root.getArbeitsverzeichnis());
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("Solver Directory:");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String(root.getSolverPfad());
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String("Solver Name:");
			w.write(buffer, 0, buffer.length());
			w.newLine();
			buffer = new String(root.getSolverName());
			w.write(buffer, 0, buffer.length());

			w.close();
		}
		catch (IOException ioe)
		{	}
	}
//*** Ende Methode zum Aktualisieren der Solverini **********************************************

}

//*** Ende Klasse plo_SolverDatenDialog *******************************************************************************