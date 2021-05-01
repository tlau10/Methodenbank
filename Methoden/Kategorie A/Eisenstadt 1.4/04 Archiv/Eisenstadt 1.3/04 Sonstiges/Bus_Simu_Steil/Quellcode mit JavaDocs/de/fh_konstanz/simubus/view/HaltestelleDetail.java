package de.fh_konstanz.simubus.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fh_konstanz.simubus.controller.HaltestelleButtonListener;
import de.fh_konstanz.simubus.model.Haltestelle;

public class HaltestelleDetail extends JFrame {

	private Haltestelle haltestelle;
	
	private JPanel main;
	private JLabel lName;
	private JButton bSave;
	private JTextField tName;
	
	public HaltestelleDetail(Haltestelle haltestelle) {
		super ("Haltestelle <" +haltestelle.getName() +"> bearbeiten");

		this.haltestelle = haltestelle;
		

		getContentPane().setLayout(null);		
		
		main = new JPanel();
		main.setLayout(null);
		main.setMinimumSize(new Dimension(400, 300));
		main.setPreferredSize(new Dimension(400, 300));
		main.setBounds(0, 0, 400, 300);

		lName = new JLabel("Name der Haltestelle");
		lName.setBounds(10, 20, 125, 16);
		main.add(lName);

		tName = new JTextField(haltestelle.getName());
		tName.setBounds(135, 18, 175, 22);
		main.add(tName);
		
		
		bSave = new JButton("Speichern");
		bSave.setActionCommand("saveHaltestelleEdit");
		bSave.setBounds(150, 235, 100, 24);
		bSave.addMouseListener(new HaltestelleButtonListener(this));
		main.add(bSave);		
		
		getContentPane().add(main);
	}
	
	public void save() {
		if (!tName.getText().trim().equals(""))
			haltestelle.setName(tName.getText().trim());
	}
	
	public Haltestelle getHaltestelle() {
		return haltestelle;
	}
}
