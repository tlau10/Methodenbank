package SimulationVisuell;

import javax.swing.*;

import Simulation.BusImSystem;
import Simulation.Planquadrat;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class SimuView extends JFrame
{
	private JComponent toolBox;
	private JPanel jP;
	private JComponent parameterBox;
	private ArrayList MatrixButtonListe;
	private ArrayList BusButtonListe;
	private JScrollPane jSP;
	private JPanel jPBusLeiste;
	private JPanel jPSimuPanel;
	private JTextField jTSimuZeit;
	private JLabel jLAktuelleZeit;
	private ArrayList BusListe;
	private SimuViewBus sBView;
	private SimuViewBusButton sB;
	private int y;
	
	public SimuView(String Name)
	{
		super(Name);
		addWindowListener(new WindowsClosingAdapter(true));
		this.jP = new JPanel();
	}
	
	public void anzeigenSimuView(boolean Anzeigen)
	{
		setVisible(Anzeigen);
	}
	
	public void definiereSimuView(int hoehe, int breite, int xPos, int yPos)
	{
		setSize(hoehe,breite);
		setLocation(xPos,yPos);
	}
	
	public void zeichneBusse(ArrayList busse)
	{
		System.out.println("Zeichne");
		
		if (BusButtonListe != null)
		{
			Iterator busIT = BusButtonListe.iterator();
			while(busIT.hasNext())
			{
				JButton jB = (JButton) busIT.next();
				jB.setVisible(false);
			}
			BusButtonListe = null;
		}
		
		BusButtonListe = new ArrayList();
		
		Iterator it = busse.iterator();
		
		while(it.hasNext())
		{
			BusImSystem b = (BusImSystem) it.next();
			
			if (b.isInBetrieb())
			{
				Planquadrat p = b.getAktuellerPlanquadrat();
				
				int id = p.holeID();
				
				SimuViewMatrixButton mB = (SimuViewMatrixButton) MatrixButtonListe.get(id-1);
				
				JButton jBBus = new JButton();
				
				ImageIcon i = null;
				
				if (b.isBewegung())
				{
					i = new ImageIcon("Bilder/bus.jpg");
				}
				else
				{
					i = new ImageIcon("Bilder/busStop.jpg");
				}
				
				jBBus.setIcon(i);
				jBBus.setSize(20,20);
				jBBus.setOpaque(false);
				
				BusButtonListe.add(jBBus);
				jBBus.setVisible(true);
				
				mB.add(jBBus);
				mB.repaint();
			}
		}
		
	}
	
	public void erzeugeMenu(ActionListener aL)
	{
		MenuBar mB = new MenuBar();
		Menu m = new Menu("Datei");
		MenuItem mI;
		
		
		mI = new MenuItem("Neu");
		mI.addActionListener(aL);
		mI.setActionCommand("Neu");
		m.add(mI);
		
		mI = new MenuItem("Speichern");
		mI.addActionListener(aL);
		mI.setActionCommand("Speichern");
		m.add(mI);
		
		mI = new MenuItem("Öffnen");
		mI.addActionListener(aL);
		mI.setActionCommand("Öffnen");
		m.add(mI);
		
		mB.add(m);
		
		m = new Menu("Simulation");
		
		mI = new MenuItem("Starten");
		mI.addActionListener(aL);
		mI.setActionCommand("Starten");
		m.add(mI);
		
		mI = new MenuItem("Statistik");
		mI.addActionListener(aL);
		mI.setActionCommand("Statistik");
		m.add(mI);
		
		mB.add(m);
		
		mB.add(m);
		setMenuBar(mB);
	}
	
	public void erzeugeMatrix(int x, int y, ActionListener aL, ActionListener aLBus,
			ActionListener aLSimu)
	{
		MatrixButtonListe = new ArrayList();
		BusListe = new ArrayList();
		this.y = y;
		
		//BusLeiste
		erzeugeButtonNeuerBus(y,aLBus);
		
		//SimuPanel
		erstelleSimuPanel(aLSimu);
				
		int abstandBusLeiste = 100;
		
		//Matrix
		jP.setLayout(null);
		
		Dimension size = new Dimension(x*50+abstandBusLeiste, y*50); 
		Dimension minSize = new Dimension(0, 0);
		jP.setSize(size); 
	    jP.setMinimumSize(minSize); 
	    jP.setPreferredSize(size); 
	    jP.setMaximumSize(size); 
		
		SimuViewMatrixButton jB;
		int zaehler = 0;
		int posX = 0;
		int posY = 0;
		
		for (int i = 0; i < x; i++)
		{
			posY = 50 * i;
			for (int j = 0; j < y; j++)
			{
				posX = 50 * j + abstandBusLeiste;
				zaehler++;
				jB = new SimuViewMatrixButton(zaehler, i+1, j+1);
				jB.addActionListener(aL);
				MatrixButtonListe.add(jB);
				
				jB.setLocation(posX,posY);
				jB.setVisible(true);
				
				jP.add(jB);
			}
		}
		
		jP.add(jPBusLeiste);
		
		jSP = new JScrollPane(jP);
		jSP.setVisible(true);
        
        add(jSP);
        pack();
	}
	
	public void erstelleSimuPanel(ActionListener aL)
	{
		jPSimuPanel = new JPanel();
		jPSimuPanel.setLayout(null);
		
		jPSimuPanel.setSize(100,100);
		jPSimuPanel.setLocation(0,0);
		
		JLabel jL= new JLabel("Simzeit");
		jL.setLocation(0,0);
		jL.setSize(45,20);
		jPSimuPanel.add(jL);
						
		jTSimuZeit = new JTextField("Simulationszeit");
		jTSimuZeit.setText("100");
		jTSimuZeit.setLocation(50,0);
		jTSimuZeit.setSize(45,20);
		jPSimuPanel.add(jTSimuZeit);
		
		jLAktuelleZeit = new JLabel("0");
		jLAktuelleZeit.setLocation(50,20);
		jLAktuelleZeit.setSize(45,20);
		jPSimuPanel.add(jLAktuelleZeit);
		
		
		JButton jB = new JButton();
		ImageIcon i = new ImageIcon("Bilder/play.jpg");
		jB.setIcon(i);
		jB.addActionListener(aL);
		jB.setActionCommand("Start");
		jB.setSize(20,20);
		jB.setLocation(10,80);
		jPSimuPanel.add(jB);
		
		jB = new JButton();
		i = new ImageIcon("Bilder/stop.jpg");
		jB.setIcon(i);
		jB.addActionListener(aL);
		jB.setActionCommand("Stop");
		jB.setSize(20,20);
		jB.setLocation(40,80);
		jPSimuPanel.add(jB);
		
		jB = new JButton();
		jB.addActionListener(aL);
		jB.setActionCommand("Pause");
		jB.setSize(20,20);
		jB.setLocation(70,80);
		jPSimuPanel.add(jB);
		
		jP.add(jPSimuPanel);
	}
	
	public void addBusButton(SimuViewBusButton b) 
	{
		BusListe.add(b);
		b.setName("Button");
		jPBusLeiste.add(b);
		jPBusLeiste.setVisible(false);
		jPBusLeiste.setVisible(true);
	}
		
	public ArrayList holeMatrixButtons()
	{
		return MatrixButtonListe;
	}
	
	public void removeButton(SimuViewBusButton b)
	{
		SimuViewBusButton bButton;
		BusListe.remove(b);
		Iterator it = BusListe.iterator();
		int j=0;
		while(it.hasNext()) 
		{
			bButton = (SimuViewBusButton)it.next();
			bButton.setId(j);
			j++;
		}
		this.setBusListe(BusListe);
		jPBusLeiste.remove(b);
		
		Component[] busLeiste = jPBusLeiste.getComponents();
		int z = 0;
		for (int i = 0; i < busLeiste.length; i++)
		{
			if(busLeiste[i].getName() == "Button") {
				bButton = (SimuViewBusButton)busLeiste[i];
				bButton.setId(z);
				z++;
			}
		}
		
		jPBusLeiste.setVisible(false);
		jPBusLeiste.setVisible(true);
		
	}
	
	public void erzeugeButtonNeuerBus(int y,ActionListener aLBus) 
	{
		jPBusLeiste = new JPanel();
		jPBusLeiste.setSize(100,(y * 50)-100);
		jPBusLeiste.setLocation(0,100);
		JButton jBNeuerBus = new JButton("Neuer Bus");
		jBNeuerBus.addActionListener(aLBus);
		jPBusLeiste.add(jBNeuerBus);
		
		jP.add(jPBusLeiste);
	}
	
	public ArrayList getBusListeButtons() 
	{
		return BusListe;
	}
	
	public void setBusListe(ArrayList a)
	{
		this.BusListe = a;
	}
	
	public void editBusButtonName(SimuViewBusButton b,int linienNr) 
	{
		SimuViewBusButton bButton;
		bButton = (SimuViewBusButton) BusListe.get(b.getId());
		bButton.setText("Linie "+linienNr);
	}
	  
	public void setzeAktuelleZeit(double z)
	{
		jLAktuelleZeit.setText(Double.toString(z));
	}
	
	public double holeSimZeit()
	{
		return Double.parseDouble(jTSimuZeit.getText());
	}
}
