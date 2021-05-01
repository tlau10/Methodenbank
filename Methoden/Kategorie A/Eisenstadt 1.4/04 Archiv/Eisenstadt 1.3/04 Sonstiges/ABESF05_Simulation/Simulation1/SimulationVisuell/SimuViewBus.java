package SimulationVisuell;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Simulation.Bus;
import Simulation.Planquadrat;

public class SimuViewBus extends JFrame
{
	private JPanel ParameterPanel;
	private JLabel lLinie;
	private JLabel lRoute;
	private JLabel lHaltestellen;
	private JLabel lIntervall;
	private JTextField tLinie;
	private JTextField tRoute;
	private JTextField tHaltestellen;
	private JTextField tIntervall;
	private JTextField busID;
	private SimuViewBusButton sB;
	private Planquadrat pQ;
	
	
	public SimuViewBus(String Name)
	{
		super(Name);
	}
	
	public SimuViewBus(SimuViewBusButton _sB)
	{
		super();
		sB = _sB;
	}
	
	public void anzeigenSimuViewBus(boolean Anzeigen)
	{
		setVisible(Anzeigen);
	}
	
	public void definiereSimuViewBus(int breite, int hoehe, int xPos, int yPos, 
			boolean stretch)
	{
		setSize(breite,hoehe);
		setLocation(xPos,yPos);
		setResizable(stretch);
	}
	
	public void loescheBusPanel()
	{
		if (ParameterPanel != null)
		{
			remove(ParameterPanel);
		}
		ParameterPanel = null;
		
		anzeigenSimuViewBus(false);
	}
		
	public void erzeugeBusPanel(Bus aktBus,ActionListener aL)
	{
		ParameterPanel = new JPanel();
		ParameterPanel.setLayout(null);
		String route = "";
		String haltestellen = "";
		ArrayList tmpListe = new ArrayList();
		tmpListe = aktBus.getRoute();
		Iterator it = tmpListe.iterator();
		
		while(it.hasNext())
		{
			pQ = (Planquadrat)it.next();
			
			route = route+pQ.holeID();
			if(it.hasNext()) {
				route = route+",";
			}
		}
		
		tmpListe = aktBus.getBusHaltestellenListe();
		it = tmpListe.iterator();
		
		while(it.hasNext())
		{
			pQ = (Planquadrat)it.next();
			haltestellen = haltestellen+pQ.holeID();
			if(it.hasNext()) {
				haltestellen = haltestellen+",";
			}
		}
		
		Dimension size = new Dimension(getSize()); 
		Dimension minSize = new Dimension(0, 0);
		ParameterPanel.setSize(size); 
		ParameterPanel.setMinimumSize(minSize); 
		ParameterPanel.setPreferredSize(size); 
		ParameterPanel.setMaximumSize(size); 
		
		lLinie = new JLabel("Linie(Nr)");
		lLinie.setLocation(10,10);
		lLinie.setSize(100,20);
		
		lRoute = new JLabel("Route");
		lRoute.setLocation(10,35);
		lRoute.setSize(100,20);
		
		lHaltestellen = new JLabel("Haltestellen");
		lHaltestellen.setLocation(10,60);
		lHaltestellen.setSize(100,20);
		
		lIntervall = new JLabel("Intervall");
		lIntervall.setLocation(10,85);
		lIntervall.setSize(100,20);
		
		
		tLinie = new JTextField(aktBus.getLinieID());
		tLinie.setText(Integer.toString(aktBus.getLinieID()));
		tLinie.setName("LinieTF");
		tLinie.setLocation(100,10);
		tLinie.setSize(100,20);
		
		tRoute = new JTextField(route);
		tRoute.setText(route);
		tRoute.setName("RouteTF");
		tRoute.setLocation(100,35);
		tRoute.setSize(100,20);
		
		
		tHaltestellen = new JTextField(haltestellen);
		tHaltestellen.setText(haltestellen);
		tHaltestellen.setName("HaltestellenTF");
		tHaltestellen.setLocation(100,60);
		tHaltestellen.setSize(100,20);
		
		tIntervall = new JTextField(aktBus.getInterval());
		tIntervall.setText(Integer.toString(aktBus.getInterval()));
		tIntervall.setName("IntervallTF");
		tIntervall.setLocation(100,85);
		tIntervall.setSize(100,20);
		
		this.busID = new JTextField(aktBus.getId());
		this.busID.setText(Integer.toString(aktBus.getId()));
		this.busID.setName("busID");
		this.busID.setVisible(false);
		
		ParameterPanel.add(lLinie);
		ParameterPanel.add(tLinie);
		
		ParameterPanel.add(lRoute);
		ParameterPanel.add(tRoute);
		
		ParameterPanel.add(lHaltestellen);
		ParameterPanel.add(tHaltestellen);
		ParameterPanel.add(lIntervall);
		ParameterPanel.add(tIntervall);
		ParameterPanel.add(this.busID);
		
		
		JButton jB = new JButton("Speichern");
		jB.addActionListener(aL);
		jB.setActionCommand("Speichern");
		jB.setSize(100,25);
		jB.setLocation(10,120);
		
		ParameterPanel.add(jB);
		
		jB = new JButton("Löschen");
		jB.addActionListener(aL);
		jB.setActionCommand("Löschen");
		jB.setSize(100,25);
		jB.setLocation(120,120);
		
		ParameterPanel.add(jB);
		
		add(ParameterPanel);
	}
	
	public Component[] holePanelBus()
	{
		return ParameterPanel.getComponents();
	}
}
