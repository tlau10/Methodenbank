package de.fh_konstanz.simubus.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import de.fh_konstanz.simubus.model.Buslinie;
import de.fh_konstanz.simubus.model.SimuKonfiguration;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.Ziel;
import de.fh_konstanz.simubus.model.optimierung.Adapter;

public class BuslinieDetail extends JFrame {
	private Editor editor;
	private ControlPanel panel;
	private Strassennetz netz;
	private Buslinie linie;
	
	private final Font labelFont = new Font("Arial", Font.PLAIN, 10);
	
	private Adapter optim;
	
	private JPanel main;
	
	private JLabel label;
	
	private JLabel ziele;
	private JList lZiele;
	private JLabel lGewichtung;
	private JTextField tGewichtung;
	private JLabel lStart;
	private JTextField tStart;
	private JLabel lFrequenz;
	private JTextField tFrequenz;
	private JLabel lAnzahlPass;
	private JTextField tAnzahlPass;
	private JLabel lName;
	private JTextField tName;
	private JLabel lFarbe;
	private JButton bFarbe;
	private JButton bSave;
	
	private int indexToSave = -1;
	
	
	public BuslinieDetail(Buslinie linie) {
		super ("Buslinie bearbeiten");
		netz = Strassennetz.getInstance();
		editor = Editor.getInstance();
		
		this.linie = linie;
		
		getContentPane().setLayout(null);		
		
		main = new JPanel();
		main.setLayout(null);
		main.setMinimumSize(new Dimension(600, 600));
		main.setPreferredSize(new Dimension(600, 600));
		main.setBounds(0, 0, 600, 600);

		ziele = new JLabel("Ziele");
		ziele.setBounds(30, 15, 150, 16);
		main.add(ziele);

		lZiele = new JList();
		lZiele.setName("ziele");		
		lZiele.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lZiele.addMouseListener(new BusButtonListener());
		
		JScrollPane spz = new JScrollPane(lZiele);
		spz.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		spz.setBounds(30, 35, 150, 450);
		main.add(spz);
		
		
		lGewichtung = new JLabel("Gewichtung der Passagiere/Ziel");
		lGewichtung.setVerticalAlignment(JLabel.TOP);
		lGewichtung.setBounds(200, 15, 350, 16);
		main.add(lGewichtung);
		
		tGewichtung = new JTextField();
		tGewichtung.setBounds(200, 35, 50, 22);
		main.add(tGewichtung);
		
		label = new JLabel("<html>(Legt fest welcher Anteil der Passagiere, die das System in dieser "
							+ "Buslinie betreten, ein bestimmtes Ziel hat. "
							+ "Die Gewichtung ist immer relativ zu den anderen Zielen zu sehen. Haben "
							+ "zwei Ziele die Gewichtung x, so kommt das auf das selbe raus als ob beide "
							+ "ein Vielfaches von x als Gewichtung haben. In beiden Fällen fährt jeweils "
							+ "die Hälfte der Passagiere zu den beiden Zielen.)");
		label.setBounds(260, 35, 325, 85);
		label.setFont(labelFont);
		main.add(label);
		

		lStart = new JLabel("Startzeit der Buslinie");
		lStart.setBounds(260, 170, 350, 16);
		main.add(lStart);
		
		tStart = new JTextField();
		tStart.setBounds(260, 190, 50, 22);
		main.add(tStart);

		label = new JLabel("(Zeit nach Simulationstart)");
		label.setBounds(320, 190, 290, 22);
		label.setFont(labelFont);
		main.add(label);

		
		lFrequenz = new JLabel("Frequenz der Buslinie");
		lFrequenz.setBounds(260, 230, 350, 16);
		main.add(lFrequenz);
		
		tFrequenz = new JTextField();
		tFrequenz.setBounds(260, 250, 50, 22);
		main.add(tFrequenz);

		label = new JLabel("(Angabe in HH:MM)");
		label.setBounds(320, 250, 250, 22);
		label.setFont(labelFont);
		main.add(label);

		
		lAnzahlPass = new JLabel("Anzahl der Passagiere pro Tag");
		lAnzahlPass.setBounds(260, 290, 350, 16);
		main.add(lAnzahlPass);
		
		tAnzahlPass = new JTextField();
		tAnzahlPass.setBounds(260, 310, 50, 22);
		main.add(tAnzahlPass);


		lName = new JLabel("Name der Busline");
		lName.setBounds(260, 380, 350, 16);
		main.add(lName);
		
		tName = new JTextField();
		tName.setBounds(260, 400, 120, 22);
		main.add(tName);

		lFarbe = new JLabel("Buslinenfarbe");
		lFarbe.setBounds(260, 440, 350, 16);
		main.add(lFarbe);
		
		bFarbe = new JButton("");
		bFarbe.setName("farbe");
		bFarbe.setBounds(260, 460, 20, 20);
		bFarbe.setBackground(linie.getLinienFarbe());
		bFarbe.addMouseListener(new BusButtonListener());
		main.add(bFarbe);

		
		bSave = new JButton("Speichern");
		bSave.setName("speichern");
		bSave.setBounds(50, 510, 100, 24);
		bSave.addMouseListener(new BusButtonListener());
		main.add(bSave);

		initialie();
		getContentPane().add(main);
	}
	
	public void initialie() {
		if (netz.getZiele().size() > 0) {
			lZiele.setListData(netz.getZiele().toArray());
			lZiele.setSelectedIndex(0);
			indexToSave = 0;
			int value = linie.getZielgewichtung((Ziel) lZiele.getModel().getElementAt(0));
			tGewichtung.setText(String.valueOf(value));

			Ziel z = ((Ziel) lZiele.getSelectedValue());
			editor.markZielField(z.getX(), z.getY());
		}
		
		tAnzahlPass.setText(String.valueOf(linie.getPassagiereProTag()));
		
		tStart.setText(getRealtime(SimuKonfiguration.getInstance().getStartzeit() + linie.getStartzeit()));
		tFrequenz.setText(getRealtime(linie.getFrequenz()));
		
		tName.setText(linie.getName());
	}
	
	
	private void saveZielgewichtung() {
		try {
			if (indexToSave > -1) {
				linie.setZielgewichtung((Ziel) lZiele.getModel().getElementAt(indexToSave), Integer.parseInt(tGewichtung.getText()));
				
				indexToSave = lZiele.getSelectedIndex();
				int value = linie.getZielgewichtung((Ziel) lZiele.getSelectedValue());
				tGewichtung.setText(String.valueOf(value));
				tGewichtung.setForeground(Color.BLACK);
			}
		} catch (NumberFormatException e) {
			lZiele.setSelectedIndex(indexToSave);
			tGewichtung.setForeground(Color.RED);
		}
	}
	
	private void saveLinienDaten() {

		try {
			if (lZiele.getModel().getSize() > 0) {
				linie.setZielgewichtung((Ziel) lZiele.getSelectedValue(), Integer.parseInt(tGewichtung.getText()));
			}
			linie.setPassagiereProTag(Integer.valueOf(tAnzahlPass.getText()));
			
			linie.setStartzeit(Double.valueOf(getSimtime(tStart.getText())));
			linie.setFrequenz(Double.valueOf(getSimtime(tFrequenz.getText())));
			
			if (!tName.getText().trim().equals(""))
				linie.setName(tName.getText().trim());
		} 
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Fehler bei Eingabe!", "Bus Simulation", JOptionPane.ERROR_MESSAGE);
		} 
		catch (Exception ex) {			
			JOptionPane.showMessageDialog(this, "Fehler bei Zeiteingabe!", "Bus Simulation", JOptionPane.ERROR_MESSAGE);
		}

	}

	private String getRealtime(double time) {
		DecimalFormat df = new DecimalFormat("#0");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		
		int minutes = Integer.parseInt(df.format(time));
		String h = String.valueOf(minutes/60);
		if (h.length() == 1)
			h = "0" + h;
		String m = String.valueOf(minutes % 60);
		if (m.length() == 1)
			m = "0" + m;
		return  h+":" + m;
	}
	
	private String getSimtime(String num) throws Exception{
		int delim = num.indexOf(":");
		if (delim == -1 || (num.length()-delim) != 3)
			throw new Exception();
		
		String tmp = num.substring(0, delim);
		int result = Integer.parseInt(tmp);
		if (result < 0 || result > 23)
			throw new Exception();
		result = result *60;
		
		tmp = num.substring(delim+1, num.length());
		int min = Integer.parseInt(tmp);
		if (min < 0 || min > 59)
			throw new Exception();
		result += min;

	  return String.valueOf(result);
	}
	
	
	private class BusButtonListener extends MouseAdapter{
		public void mouseClicked(MouseEvent evt) {
			if (evt.getSource() instanceof JList) {
				if (lZiele.getModel().getSize() > 0) {
					saveZielgewichtung();				
					Ziel z = ((Ziel) lZiele.getSelectedValue());
					editor.markZielField(z.getX(), z.getY());
				}
			}
			else if (evt.getSource() instanceof JButton) {
				JButton btn = (JButton) evt.getSource(); 
				if (btn.getName().equals("speichern")) {
					saveLinienDaten();
					
					setVisible(false);
					dispose();
				}
				else if (btn.getName().equals("farbe")) {
					Color color = JColorChooser.showDialog(bFarbe, "Buslinienfarbe", linie.getLinienFarbe());
					
					if (color != null) {
						bFarbe.setBackground(color);
						linie.setLinienFarbe(color);
					}						
				}
			}
		}
	}
}
