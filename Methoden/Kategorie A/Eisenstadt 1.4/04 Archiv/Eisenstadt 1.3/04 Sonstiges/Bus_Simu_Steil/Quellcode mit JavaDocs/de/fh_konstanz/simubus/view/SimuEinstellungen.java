package de.fh_konstanz.simubus.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fh_konstanz.simubus.model.SimuKonfiguration;

public class SimuEinstellungen extends JFrame {

	private SimuKonfiguration config;
	private final Font fLabel = new Font("Arial", Font.BOLD, 12);
	private final Font fLabelInfo = new Font("Arial", Font.PLAIN, 12);
	
	private JPanel main;

	private JLabel lAufloesung;
	private JComboBox cAufloesung;
	
	private JLabel lName;
	private JTextField tName;
	
	private JLabel lBusgeschw;
	private JTextField tBusgeschw;
	private JLabel lPassgeschw;
	private JTextField tPassgeschw;
	private JLabel lRealSizeField;
	private JTextField tRealSizeField;
	private JLabel lSimDauer;
	private JTextField tSimDauer;
	private JLabel lRealZeit;
	private JTextField tRealZeit;
	
	private JButton bSave;
	
	
	public SimuEinstellungen() {
		super ("Einstellungen");

		config = SimuKonfiguration.getInstance();
		
		getContentPane().setLayout(null);		
		
		Dimension mainDimension = config.getEinstellungenFrameDimension();
		main = new JPanel();
		main.setLayout(null);
		
		main.setMinimumSize(mainDimension);
		main.setPreferredSize(mainDimension);
		main.setBounds(0, 0, mainDimension.width, mainDimension.height);


		lAufloesung = new JLabel("Bildschirmauflösung");
		lAufloesung.setFont(fLabel);
		lAufloesung.setBounds(10, 20, 125, 20);
		main.add(lAufloesung);

		cAufloesung = new JComboBox();
		cAufloesung.addItem("1024x768");
		cAufloesung.addItem("1280x1024");
		cAufloesung.setSelectedIndex(config.getActiveResolutionForCombo());
		cAufloesung.setBounds(140, 18, 110, 24);
		cAufloesung.setEnabled(false);
		main.add(cAufloesung);

		lBusgeschw = new JLabel("Busgeschwindigkeit (km/h)");
		lBusgeschw.setFont(fLabel);
		lBusgeschw.setBounds(10, 80, 175, 20);
		main.add(lBusgeschw);

		tBusgeschw = new JTextField(String.valueOf(config.getBusGeschwindigkeitInKmH()));
		tBusgeschw.setBounds(190, 78, 50, 24);
		main.add(tBusgeschw);

		lPassgeschw = new JLabel("Gehgeschwindigkeit (km/h)");
		lPassgeschw.setFont(fLabel);
		lPassgeschw.setBounds(10, 120, 175, 20);
		main.add(lPassgeschw);

		tPassgeschw = new JTextField(String.valueOf(config.getGehGeschwindigkeitInKmH()));
		tPassgeschw.setBounds(190, 118, 50, 24);
		main.add(tPassgeschw);
		
		lRealSizeField = new JLabel("Planquadratgrösse (m)");
		lRealSizeField.setFont(fLabel);
		lRealSizeField.setBounds(10, 160, 175, 20);
		lRealSizeField.setToolTipText("Realgrösse eines Planquadrats in Meter");
		main.add(lRealSizeField);

		tRealSizeField = new JTextField(String.valueOf(config.getFeldElementGroesseInM()));
		tRealSizeField.setBounds(190, 158, 50, 24);
		tRealSizeField.setToolTipText("Realgrösse eines Planquadrats in Meter");
		main.add(tRealSizeField);
		
		lSimDauer = new JLabel("Simulationsdauer (min)");
		lSimDauer.setFont(fLabel);
		lSimDauer.setBounds(10, 200, 175, 20);
		lSimDauer.setToolTipText("Simulationsdauer");
		main.add(lSimDauer);

		tSimDauer = new JTextField(String.valueOf(config.getEndezeit()));
		tSimDauer.setBounds(190, 198, 50, 24);
		tSimDauer.setToolTipText("Simulationsdauer");
		main.add(tSimDauer);
		
		lRealZeit = new JLabel("<html>Faktor der Animationszeit im Vergleich zur Simulationszeit (Bsp: 60 - in 1sec wird 60sec dargestellt)");
		lRealZeit.setFont(fLabel);
		lRealZeit.setBounds(10, 250, 175, 60);
		lRealZeit.setToolTipText("Realgrösse eines Planquadrats in Meter");
		main.add(lRealZeit);

		tRealZeit = new JTextField(String.valueOf(config.getSimulationsgeschwindigkeit()));
		tRealZeit.setBounds(190, 268, 50, 24);
		tRealZeit.setToolTipText("Faktor der Animationszeit im Vergleich zur Simulationszeit (Bsp: 60 - in 1sec wird 60sec dargestellt)");
		main.add(tRealZeit);
		

		bSave = new JButton("Speichern");
		bSave.setActionCommand("saveEinstellungen");
		bSave.setBounds(main.getWidth()/2-50, main.getHeight()-80, 100, 24);
		bSave.addMouseListener(new BusButtonListener());
		main.add(bSave);		
		
		getContentPane().add(main);
	}
	
	public void save() {
		config.setActiveResolutionFromCombo(cAufloesung.getSelectedIndex());
		
		try {
			config.setBusGeschwindigkeitInKmH(Double.parseDouble(tBusgeschw.getText()));
			config.setGehGeschwindigkeitInKmH(Double.parseDouble(tPassgeschw.getText()));
			config.setFeldElementGroesseInM(Double.parseDouble(tRealSizeField.getText()));
			config.setEndezeit(Double.parseDouble(tSimDauer.getText()));
			config.setSimulationsgeschwindigkeit(Integer.parseInt(tRealZeit.getText()));
		}
		catch (NumberFormatException e) {		
			JOptionPane.showMessageDialog(this, "Fehler bei Zahleneingaben!", "Bus Simulation", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private class BusButtonListener extends MouseAdapter{
		public void mouseClicked(MouseEvent evt) {
			save();

			setVisible(false);
			dispose();
		}
	}
}
