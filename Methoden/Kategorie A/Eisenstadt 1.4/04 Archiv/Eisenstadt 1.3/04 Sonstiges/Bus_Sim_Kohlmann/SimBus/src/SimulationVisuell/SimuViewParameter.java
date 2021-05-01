package SimulationVisuell;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Simulation.Planquadrat;

public class SimuViewParameter extends JFrame
{
	private Planquadrat aktPQ;
	private SimuViewMatrixButton aktButton;
	
	private JPanel ParameterPanel;
	
	private JLabel lAbstandNord;
	private JLabel lAbstandOst;
	private JLabel lAbstandSued;
	private JLabel lAbstandWest;
	private JLabel lAbstandNordOst;
	private JLabel lAbstandSuedOst;
	private JLabel lAbstandSuedWest;
	private JLabel lAbstandNordWest;
	private JLabel lEWPersonenKommen;
	private JLabel lSAPersonenKommen;
	private JLabel lEWPersonenZiel;
	private JLabel lSAPersonenZiel;
		
	private JTextField AbstandNord;
	private JTextField AbstandOst;
	private JTextField AbstandSued;
	private JTextField AbstandWest;
	private JTextField AbstandNordOst;
	private JTextField AbstandSuedOst;
	private JTextField AbstandSuedWest;
	private JTextField AbstandNordWest;
	private JTextField EWPersonenKommen;
	private JTextField SAPersonenKommen;
	private JTextField EWPersonenZiel;
	private JTextField SAPersonenZiel;
	
	private JCheckBox checkHaltestelle;
	
	
	public SimuViewParameter(String Name)
	{
		super(Name);
	}
	
	public void anzeigenSimuViewParameter(boolean Anzeigen)
	{
		setVisible(Anzeigen);
	}
	
	public void definiereSimuViewParameter(int hoehe, int breite, int xPos, int yPos, 
			boolean stretch)
	{
		setSize(hoehe,breite);
		setLocation(xPos,yPos);
		setResizable(stretch);
	}
	
	
	public void erzeugeParameterPanel(Planquadrat pQ, SimuViewMatrixButton b)
	{
		
		this.aktPQ = pQ;
		this.aktButton = b;
		
		ParameterPanel = new JPanel();
		
		ParameterPanel.setLayout(null);
		
		Dimension size = new Dimension(getSize()); 
		Dimension minSize = new Dimension(0, 0);
		ParameterPanel.setSize(size); 
		ParameterPanel.setMinimumSize(minSize); 
		ParameterPanel.setPreferredSize(size); 
		ParameterPanel.setMaximumSize(size); 
		
		
		
		JLabel lName = new JLabel("Planquadrat " + aktPQ.holeID());
		lName.setLocation(1,0);
		lName.setSize(300,20);
		
		lAbstandNord = new JLabel("Abstand Nord");
		lAbstandNord.setLocation(10,40);
		lAbstandNord.setSize(150,20);
		
		lAbstandOst = new JLabel("Abstand Ost");
		lAbstandOst.setLocation(10,65);
		lAbstandOst.setSize(150,20);
		
		lAbstandSued = new JLabel("Abstand Sued");
		lAbstandSued.setLocation(10,90);
		lAbstandSued.setSize(150,20);
		
		lAbstandWest = new JLabel("Abstand West");
		lAbstandWest.setLocation(10,115);
		lAbstandWest.setSize(150,20);
		
		lAbstandNordOst = new JLabel("Abstand Nordst");
		lAbstandNordOst.setLocation(10,140);
		lAbstandNordOst.setSize(150,20);
		
		lAbstandSuedOst = new JLabel("Abstand Südost");
		lAbstandSuedOst.setLocation(10,165);
		lAbstandSuedOst.setSize(150,20);
		
		lAbstandSuedWest = new JLabel("Abstand Südwest");
		lAbstandSuedWest.setLocation(10,190);
		lAbstandSuedWest.setSize(150,20);
		
		lAbstandNordWest = new JLabel("Abstand Nordwest");
		lAbstandNordWest.setLocation(10,215);
		lAbstandNordWest.setSize(150,20);
		
		lEWPersonenKommen = new JLabel("EW Personen");
		lEWPersonenKommen.setLocation(10,245);
		lEWPersonenKommen.setSize(150,20);
		
		lSAPersonenKommen = new JLabel("SA Personen");
		lSAPersonenKommen.setLocation(10,270);
		lSAPersonenKommen.setSize(150,20);
		
		
		lEWPersonenZiel = new JLabel("EW Personenziel");
		lEWPersonenZiel.setLocation(10,295);
		lEWPersonenZiel.setSize(150,20);
		
		lSAPersonenZiel = new JLabel("SA Personenziel");
		lSAPersonenZiel.setLocation(10,320);
		lSAPersonenZiel.setSize(150,20);
		
		AbstandNord = new JTextField();
		AbstandNord.setLocation(160,40);
		AbstandNord.setSize(50,20);
		
		AbstandOst = new JTextField();
		AbstandOst.setLocation(160,65);
		AbstandOst.setSize(50,20);
		
		AbstandSued = new JTextField();
		AbstandSued.setLocation(160,90);
		AbstandSued.setSize(50,20);
		
		AbstandWest = new JTextField();
		AbstandWest.setLocation(160,115);
		AbstandWest.setSize(50,20);
		
		AbstandNordOst = new JTextField();
		AbstandNordOst.setLocation(160,140);
		AbstandNordOst.setSize(50,20);
		
		AbstandSuedOst = new JTextField();
		AbstandSuedOst.setLocation(160,165);
		AbstandSuedOst.setSize(50,20);
		
		AbstandSuedWest = new JTextField();
		AbstandSuedWest.setLocation(160,190);
		AbstandSuedWest.setSize(50,20);
		
		AbstandNordWest = new JTextField();
		AbstandNordWest.setLocation(160,215);
		AbstandNordWest.setSize(50,20);
		
		EWPersonenKommen = new JTextField();
		EWPersonenKommen.setLocation(160,245);
		EWPersonenKommen.setSize(50,20);
		
		SAPersonenKommen = new JTextField();
		SAPersonenKommen.setLocation(160,270);
		SAPersonenKommen.setSize(50,20);
		
		EWPersonenZiel = new JTextField();
		EWPersonenZiel.setLocation(160,295);
		EWPersonenZiel.setSize(50,20);
		
		SAPersonenZiel = new JTextField();
		SAPersonenZiel.setLocation(160,320);
		SAPersonenZiel.setSize(50,20);
		
		checkHaltestelle = new JCheckBox("Haltestelle",pQ.holeHaltestelle());
		checkHaltestelle.setLocation(250,40);
		checkHaltestelle.setSize(150,20);
		
				
		AbstandNord.setText(Double.toString(aktPQ.holeAbstandNord()));
		AbstandOst.setText(Double.toString(aktPQ.holeAbstandOst()));
		AbstandSued.setText(Double.toString(aktPQ.holeAbstandSued()));
		AbstandWest.setText(Double.toString(aktPQ.holeAbstandWest()));
		AbstandNordOst.setText(Double.toString(aktPQ.holeAbstandNordOst()));
		AbstandSuedOst.setText(Double.toString(aktPQ.holeAbstandSuedOst()));
		AbstandSuedWest.setText(Double.toString(aktPQ.holeAbstandSuedWest()));
		AbstandNordWest.setText(Double.toString(aktPQ.holeAbstandNordWest()));
		EWPersonenKommen.setText(Double.toString(aktPQ.holeEWPersonenKommen()));
		SAPersonenKommen.setText(Double.toString(aktPQ.holeSAPersonenKommen()));
		EWPersonenZiel.setText(Double.toString(aktPQ.holeEWPersonenZiel()));
		SAPersonenZiel.setText(Double.toString(aktPQ.holeSAPersonenZiel()));
		
		ParameterPanel.add(lName);
		ParameterPanel.add(lAbstandNord);
		ParameterPanel.add(AbstandNord);
		ParameterPanel.add(lAbstandOst);
		ParameterPanel.add(AbstandOst);
		ParameterPanel.add(lAbstandSued);
		ParameterPanel.add(AbstandSued);
		ParameterPanel.add(lAbstandWest);
		ParameterPanel.add(AbstandWest);
		ParameterPanel.add(lAbstandNordOst);
		ParameterPanel.add(AbstandNordOst);
		ParameterPanel.add(lAbstandSuedOst);
		ParameterPanel.add(AbstandSuedOst);
		ParameterPanel.add(lAbstandSuedWest);
		ParameterPanel.add(AbstandSuedWest);
		ParameterPanel.add(lAbstandNordWest);
		ParameterPanel.add(AbstandNordWest);
		ParameterPanel.add(lEWPersonenKommen);
		ParameterPanel.add(lSAPersonenKommen);
		ParameterPanel.add(EWPersonenKommen);
		ParameterPanel.add(SAPersonenKommen);
		ParameterPanel.add(lEWPersonenZiel);
		ParameterPanel.add(lSAPersonenZiel);
		ParameterPanel.add(EWPersonenZiel);
		ParameterPanel.add(SAPersonenZiel);
		
		ParameterPanel.add(checkHaltestelle);
		
		
		aLSpeichern aL = new aLSpeichern();
		JButton jB = new JButton("Speichern");
		jB.addActionListener(aL);
		jB.setActionCommand("Speichern");
		jB.setSize(100,25);
		jB.setLocation(10,370);
		ParameterPanel.add(jB);
		
		jB = new JButton("Beenden");
		jB.addActionListener(aL);
		jB.setActionCommand("Beenden");
		jB.setSize(100,25);
		jB.setLocation(120,370);
		ParameterPanel.add(jB);
		add(ParameterPanel);
	}
	
	public void loescheParameterPanel()
	{
		if (ParameterPanel != null)
		{
			remove(ParameterPanel);
		}
		ParameterPanel = null;
		aktButton = null;
		
		anzeigenSimuViewParameter(false);
	}
	
	public void sendeAenderungen()
	{
		aktPQ.setzeAbstandNord(Double.parseDouble(AbstandNord.getText()));
		aktPQ.setzeAbstandOst(Double.parseDouble(AbstandOst.getText()));
		aktPQ.setzeAbstandSued(Double.parseDouble(AbstandSued.getText()));
		aktPQ.setzeAbstandWest(Double.parseDouble(AbstandWest.getText()));
		aktPQ.setzeAbstandNordOst(Double.parseDouble(AbstandNordOst.getText()));
		aktPQ.setzeAbstandSuedOst(Double.parseDouble(AbstandSuedOst.getText()));
		aktPQ.setzeAbstandSuedWest(Double.parseDouble(AbstandSuedWest.getText()));
		aktPQ.setzeAbstandNordWest(Double.parseDouble(AbstandNordWest.getText()));
		aktPQ.setzeEWPersonenKommen(Double.parseDouble(EWPersonenKommen.getText()));
		aktPQ.setzeSAPersonenKommen(Double.parseDouble(SAPersonenKommen.getText()));
		aktPQ.setzeEWPersonenZiel(Double.parseDouble(EWPersonenZiel.getText()));
		aktPQ.setzeSAPersonenZiel(Double.parseDouble(SAPersonenZiel.getText()));
		aktPQ.setzeHaltestelle(checkHaltestelle.isSelected());
		aktButton.zeichneKomponente(aktPQ);
	}
	
	class aLSpeichern implements ActionListener
    {
            public void actionPerformed(ActionEvent arg0)
            {
            		if (arg0.getActionCommand() == "Speichern")
            		{
            			sendeAenderungen();
                		loescheParameterPanel();	
            		}
            		
            		if (arg0.getActionCommand() == "Beenden")
            		{
            			loescheParameterPanel();	
            		}
            }
    }
}
