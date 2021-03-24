package de.fh_konstanz.simubus.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import de.fh_konstanz.simubus.model.Gesamtfahrplan;
import de.fh_konstanz.simubus.model.SimuKonfiguration;

public class Main {
	
	public static void main(String[] args) {
		
		Gesamtfahrplan plan = Gesamtfahrplan.getInstance();
		SimuKonfiguration config = SimuKonfiguration.getInstance();
		
		Dimension screen_res = Toolkit.getDefaultToolkit().getScreenSize();
		if (screen_res.height < 1000)
			config.setActiveResolutionFromCombo(0);
		else
			config.setActiveResolutionFromCombo(1);
		
		Editor mainFrame = Editor.getInstance();
		mainFrame.setSize(config.getResWidth(), config.getResHeight());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
}
