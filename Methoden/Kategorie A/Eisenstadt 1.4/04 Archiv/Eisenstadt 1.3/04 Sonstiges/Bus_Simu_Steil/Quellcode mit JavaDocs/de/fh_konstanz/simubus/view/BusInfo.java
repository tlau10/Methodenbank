package de.fh_konstanz.simubus.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fh_konstanz.simubus.model.Buslinie;

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
public class BusInfo extends JPanel{
	
	private final int size = 20;
	private final Font nameFont = new Font("Arial", Font.ITALIC, 10);  
	private final Font dataFont = new Font("Arial", Font.PLAIN, 10);  
	private Color color;
	private JLabel lName;
	private JLabel lData;

	private Buslinie buslinie;

	public BusInfo(Buslinie buslinie) {
		this.buslinie = buslinie;
				
		this.setLayout(null);
		this.setMinimumSize(new Dimension(70, 35));
		this.setPreferredSize(new Dimension(70, 35));
		this.setBackground(buslinie.getLinienFarbe());

		lName = new JLabel(buslinie.getName());
		lName.setBounds(5, 5, this.getPreferredSize().width-5, 12);
		lName.setFont(nameFont);
		this.add(lName);

		lData = new JLabel();
		lData.setBounds(5, 18, this.getPreferredSize().width-5, 12);
		lData.setFont(dataFont);
		this.add(lData);
	}
	
	public void setPos(int anzahl, int x, int y) {
		lName.setText(buslinie.getName());
		lData.setText(String.valueOf(anzahl) +" Passag.");
	}
}