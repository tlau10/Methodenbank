package de.fh_konstanz.simubus.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Info extends JFrame{
	
	private JPanel main;
	
	private JLabel text;
	private JLabel copyright;
	private JLabel help;
	
	public Info() {
		super ("Bussimulation 2005");

		main = new JPanel();
		main.setLayout(null);
		main.setBounds(0, 0, getWidth(), getHeight());
				
		help = new JLabel();
		help.setIcon(new ImageIcon("fhcampus.png"));
		help.setBounds(235, 20, 350, 245);
		main.add(help);
		
		text = new JLabel();
		text.setText("<html><b>Anschrift</b><br>" +
					"<br>Prof. Dr. Ulrich Hedtstück" +
					"<br>Prof. Dr. Michael Grütz" +
					"<br><br>Fachhochschule Konstanz" +
					"<br>Braunegger Str. 55" +
					"<br>78467 Konstanz" +
					"<br><br>hdstueck@fh-konstanz.de" +
					"<br>gruetz@fh-konstanz.de" +
					"<br><br><b>Programmierer</b><br>" +
					"<br>Tobias Lott" +
					"<br>Robert Audersetz" +
					"<br>Daniel Prinz" +
					"<br>Daniel Speicher" +
					"<br>Christian Steil" +
					"<br>Ruping Hua");
		text.setBounds(30, 4, 225, 330);
		main.add(text);
		
		copyright = new JLabel();
		copyright.setText("<html><div align=center>Version 1.0");
		copyright.setBounds(360, 270, 225, 30);
		main.add(copyright);
		
		getContentPane().add(main);
		pack();
	}
}
