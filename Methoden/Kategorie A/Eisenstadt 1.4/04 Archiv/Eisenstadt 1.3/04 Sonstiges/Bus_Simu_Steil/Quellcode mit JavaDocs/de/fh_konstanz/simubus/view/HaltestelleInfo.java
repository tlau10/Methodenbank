package de.fh_konstanz.simubus.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fh_konstanz.simubus.model.Haltestelle;

/*
 * Created on 18.04.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Robert Audersetz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HaltestelleInfo extends JPanel{
	
	private final Color haltestelleColor = new Color(70, 120, 160, 255);
	private final int size = 20;
	private final Font nameFont = new Font("Arial", Font.ITALIC, 10);  
	private final Font dataFont = new Font("Arial", Font.PLAIN, 10);  
	
	private JLabel lName;
	private JLabel lData;
	private Haltestelle haltestelle;
	
	
	public HaltestelleInfo(Haltestelle haltestelle) {
	
		this.haltestelle = haltestelle;
		this.setLayout(null);
		this.setBackground(Color.GREEN);
		this.setMinimumSize(new Dimension(100, 50));
		this.setPreferredSize(new Dimension(100, 50));
		
		lName = new JLabel(haltestelle.getName());
		lName.setBounds(5, 5, this.getPreferredSize().width-5, 12);
		lName.setFont(nameFont);
		this.add(lName);

		lData = new JLabel(haltestelle.getKoordinaten().toString());
		lData.setBounds(5, 18, this.getPreferredSize().width-5, 12);
		lData.setFont(dataFont);
		this.add(lData);
	}
	
	public HaltestelleInfo(Haltestelle haltestelle, boolean animFlag) {
		
		this.haltestelle = haltestelle;
		this.setLayout(null);		
		this.setBackground(haltestelleColor);
		this.setMinimumSize(new Dimension(100, 35));
		this.setPreferredSize(new Dimension(100, 35));
		
		lName = new JLabel(haltestelle.getName());
		lName.setBounds(5, 5, this.getPreferredSize().width-5, 12);
		lName.setForeground(Color.WHITE);
		lName.setFont(nameFont);
		this.add(lName);

		lData = new JLabel(" 20 Passagiere");
		lData.setBounds(5, 18, this.getPreferredSize().width-5, 12);
		lData.setForeground(Color.WHITE);
		lData.setFont(dataFont);
		this.add(lData);
	}
	
	public void setPassagiere(int anz) {
		lData.setText(anz +" Passagiere");
	}
}