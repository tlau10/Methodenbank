package de.fh_konstanz.simubus.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.Haltestelle;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.view.AnimationView;
import de.fh_konstanz.simubus.view.ControlPanel;
import de.fh_konstanz.simubus.view.Editor;
import de.fh_konstanz.simubus.view.ResultUebersicht;

/*
 * Created on 05.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Robert Audersetz
 */
public class ButtonListener extends MouseAdapter{
	
	private Editor main;
	private ControlPanel panel;
	private SimuKonfiguration config;
	private Strassennetz netz;
	private AnimationView anim;
	
	public void mouseClicked(MouseEvent evt) {
		main = Editor.getInstance();
		config  = SimuKonfiguration.getInstance();
		
		JButton actual = (JButton) evt.getSource(); 
		String actionCmd = actual.getActionCommand();
		
		if (actionCmd.equals("startOR")) {
			ResultUebersicht f = new ResultUebersicht(); 
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setSize(new Dimension(600, 600));
			f.setBounds(300, 100, 600, 600);
			f.setResizable(false);
			f.setVisible(true);
		}
		else if (actionCmd.equals("startSearch")) {
			if (main.startSearch()) {
				boolean successful;
				
				successful= main.startSimulation();
				if (successful) {
					anim = new AnimationView();
					
					JFrame frame = new JFrame();
					frame.setSize(config.getResWidth(), config.getResHeight()-30);
					//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent wEvt) {
							JFrame f = (JFrame) wEvt.getSource();
							
							anim.stopBusThreads();
							f.setVisible(false);
							f.dispose();
						}
					});
					frame.setResizable(false);
					frame.setVisible(true);
					frame.add(anim);
				}
			}			
		}
		else if (actionCmd.equals("addBushaltestelle")) {
			panel = ControlPanel.getInstance();
			Buslinie bus = panel.getSelectedBuslinie();
			List<Haltestelle> list = bus.getHaltestellen();
			Haltestelle h = panel.getSelectedHaltestelle(); 
			if (!list.contains(h))
				list.add(h);
			panel.updateBushaltestellenList();
		}
		else if (actionCmd.equals("removeBushaltestelle")) {
			panel = ControlPanel.getInstance();
			Buslinie bus = panel.getSelectedBuslinie();
			bus.getHaltestellen().remove(panel.getSelectedBusHaltestelle());
			panel.updateBushaltestellenList();
		}
		else if (actionCmd.equals("saveHaltestelleEdit")) {
		
		}
		else if (actionCmd.equals("addBuslinie")) {
			panel = ControlPanel.getInstance();						
			Gesamtfahrplan plan = Gesamtfahrplan.getInstance();
			Buslinie bus = new Buslinie("Linie " +String.valueOf(plan.getBuslinien().size()+1));
			Random rand = new Random();
			bus.setLinienFarbe(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 255));
			plan.addLinie(bus);
			panel.updateBuslinienList();
			panel.updateBushaltestellenList();
		}
		else if (actionCmd.equals("removeBuslinie")) {
			panel = ControlPanel.getInstance();
			Gesamtfahrplan plan = Gesamtfahrplan.getInstance();
			Buslinie bus = panel.getSelectedBuslinie();			
			plan.removeLinie(bus);
			panel.updateBuslinienList();
			panel.updateBushaltestellenList();
		}
	}

	public ButtonListener() {
	}

}
