package Dakin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.JComponent;



public class DakinAusgabe extends JInternalFrame
{
	private TreeDakin tD; //Wurzel eines Baumes vom Typ TreeDakin
	private TheTreeComponent tTC;  //Eigene Komponente fuer Baum

  	public DakinAusgabe()
  	{
 	   	super("Ausgabe",true,true,true,true);

    		tTC = new TheTreeComponent(null); //beim anlegen des Fensters

    		JComponent c = (JComponent) getContentPane();
    		c.setLayout(new BorderLayout());

    		c.add(new JScrollPane(tTC,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);

    		setContentPane(c);
    		pack();
    	}

	//wird nach "Berechnen gerufen"
	void setTree(TreeDakin treeDakin)
	{
  		try
  		{
  			tD=treeDakin;
  			tTC.update(treeDakin);

    		}
    		catch(Exception e)
    		{
    			System.out.println("ERROR: " + e);
    		}
	}
}

//Klasse von H.W.
//Abgeleitet von JPanel
class TheTreeComponent extends JPanel
{
	private CRectTree rechteckBaum; //Baum

	public TheTreeComponent(TreeDakin dT)
	{
		super();
		rechteckBaum=null;

		if(dT!=null)
		{
			rechteckBaum = new CRectTree(dT);
		        setSize(rechteckBaum.getHeight(),rechteckBaum.getWidth());
		}
		setBackground(Color.white);
	}

	public void update(TreeDakin dT)
	{
		if(dT!=null)
		{
			rechteckBaum = new CRectTree(dT);
	                setBorder(BorderFactory.createLineBorder(Color.black));
			repaint();
		}

	}

	public void paintComponent(Graphics g)
  	{
		super.paintComponent(g);

  		if(rechteckBaum!=null) {
			rechteckBaum.drawMe(g);
			setSize(rechteckBaum.getWidth(),rechteckBaum.getHeight());
		}
	}
}