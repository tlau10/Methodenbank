package SimulationVisuell;

import javax.swing.JButton;

public class SimuViewBusButton extends JButton 
{
	private int id;
	
	
	public SimuViewBusButton()
	{
		
	}
	
	public SimuViewBusButton(int id)
	{
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
