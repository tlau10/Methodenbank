package SimulationVisuell;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Scrollable;

public class SimuStatistikView extends JFrame
{
		  JPanel contentPane;
		  BorderLayout borderLayout1 = new BorderLayout();
		  JScrollPane jScrollPane1 = new JScrollPane();
		  JTextArea jTextArea1 = new JTextArea();
		  
		  private int anzahlBusseimSystem=0;
		  private int anzahlPersonenimSystem=0;
		  private int anzahlPersonenGesamt=0;
		  private int anzahlBusseGesamt=0;
		  
		
		public SimuStatistikView(String statistikname)
		{
			contentPane = (JPanel) getContentPane();
		    contentPane.setLayout(borderLayout1);
		    setSize(new Dimension(671, 685));
		    
		    setLocation(690,100);
		    setTitle("LIVE-STATISTK");
		    jTextArea1.setText("");
		    contentPane.add(jScrollPane1, java.awt.BorderLayout.CENTER);
		    jScrollPane1.getViewport().add(jTextArea1);
		    jTextArea1.setWrapStyleWord(true);
			
			
		}
		
		public void gebeEventsAus(String meldung)
		{
			jTextArea1.append(meldung+"\n"+"\r");
			jTextArea1.setCaretPosition(jTextArea1.getRows());
			
			
		}

		public void statistikAnzeigen(boolean b) 
		{
			this.setVisible(true);
		}
		
		public SimuStatistikView getStatistikView()
		{
			return this;
		}

		
		
		
		public int getAnzahlBusseGesamt() {
			return anzahlBusseGesamt;
		}

		public void setAnzahlBusseGesamt() {
			this.anzahlBusseGesamt++;
		}

		public int getAnzahlBusseimSystem() {
			return anzahlBusseimSystem;
		}

		public void setAnzahlBusseimSystem() {
			this.anzahlBusseimSystem++;
		}

		public int getAnzahlPersonenGesamt() {
			return anzahlPersonenGesamt;
		}

		public void setAnzahlPersonenGesamt() {
			this.anzahlPersonenGesamt++;
		}

		public int getAnzahlPersonenimSystem() {
			return anzahlPersonenimSystem;
		}

		public void setAnzahlPersonenimSystem() 
		{
			this.anzahlPersonenimSystem ++;
		}
		
		public void removeAnzahlPersonimSystem()
		{
			this.anzahlPersonenimSystem--;
		}
		
		public void removeAnzahlBusimSystem()
		{
			this.anzahlBusseimSystem--;
		}
		
		
	
	
	
}
