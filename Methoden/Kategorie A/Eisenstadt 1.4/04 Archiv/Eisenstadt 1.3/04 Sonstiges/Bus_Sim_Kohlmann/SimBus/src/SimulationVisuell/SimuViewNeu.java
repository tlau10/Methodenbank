package SimulationVisuell;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimuViewNeu extends JFrame
{
	private JPanel ParameterPanel;
	
	private JLabel lX;
	private JLabel lY;
	private JLabel lName;
	
	private TextField tX;
	private TextField tY;
	private TextField tName;
	
	private SimuViewNeuBeobachter sB;
	
	public SimuViewNeu(SimuViewNeuBeobachter _sB)
	{
		super("Definition");
		sB = _sB;
	}
	
	public void anzeigenSimuViewNeu(boolean Anzeigen)
	{
		setVisible(Anzeigen);
	}
	
	public void definiereSimuViewNeu(int hoehe, int breite, int xPos, int yPos, 
			boolean stretch)
	{
		setSize(hoehe,breite);
		setLocation(xPos,yPos);
		setResizable(stretch);
	}
		
	public void erzeugeNeuPanel(ActionListener aL)
	{
		ParameterPanel = new JPanel();
		
		textListener tL = new textListener();
		
		ParameterPanel.setLayout(null);
		
		Dimension size = new Dimension(getSize()); 
		Dimension minSize = new Dimension(0, 0);
		ParameterPanel.setSize(size); 
		ParameterPanel.setMinimumSize(minSize); 
		ParameterPanel.setPreferredSize(size); 
		ParameterPanel.setMaximumSize(size); 
		
		lName = new JLabel("Name");
		lName.setLocation(10,10);
		lName.setSize(100,20);
		
		tName = new TextField();
		tName.setLocation(100,10);
		tName.setSize(100,20);
		
		lX = new JLabel("X Koordinate");
		lX.setLocation(10,40);
		lX.setSize(100,20);
		
		lY = new JLabel("YKoordinate");
		lY.setLocation(10,65);
		lY.setSize(100,20);
		
		tX = new TextField();
		tX.addTextListener(tL);
		tX.setLocation(100,40);
		tX.setSize(50,20);
		
		tY = new TextField();
		tY.addTextListener(tL);
		tY.setLocation(100,65);
		tY.setSize(50,20);
				
		tX.setText("30");
		tY.setText("30");
		
		ParameterPanel.add(lX);
		ParameterPanel.add(lY);
		ParameterPanel.add(tX);
		ParameterPanel.add(tY);
		
		ParameterPanel.add(lName);
		ParameterPanel.add(tName);
		
		JButton jB = new JButton("Neu");
		jB.addActionListener(aL);
		jB.setActionCommand("Neu");
		jB.setSize(100,25);
		jB.setLocation(10,100);
		
		ParameterPanel.add(jB);
		
		jB = new JButton("Abbrechen");
		jB.addActionListener(aL);
		jB.setActionCommand("Abbrechen");
		jB.setSize(100,25);
		jB.setLocation(120,100);
		
		ParameterPanel.add(jB);
		
		add(ParameterPanel);
	}
	
	public void schreibeWerte()
	{
		sB.setzeWerte(Integer.parseInt(tX.getText()),Integer.parseInt(tY.getText()));
	}
	
	class textListener implements TextListener
    {
		public void textValueChanged(TextEvent arg0) 
		{
			schreibeWerte();
		}
    }
}
