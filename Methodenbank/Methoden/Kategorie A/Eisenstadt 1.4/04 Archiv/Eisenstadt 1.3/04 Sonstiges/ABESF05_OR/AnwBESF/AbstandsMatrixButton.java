package AnwBESF;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Jens
 * @version 1.0
 */


import java.awt.Color;

import javax.swing.JButton;


public class AbstandsMatrixButton extends JButton 
{
	private int x;
	private int y;
	private double wert;
	 
	public AbstandsMatrixButton(String t)
	{
		super(t);
		setBackground(Color.BLUE);
		setForeground(Color.WHITE);
	}

	public AbstandsMatrixButton(String bezeichnung, int x_, int y_, double wert_)
	{
		super(bezeichnung);
		setBackground(Color.lightGray);
		setForeground(Color.BLACK);
		wert = wert_;
		x = x_;
		y = y_;
	}
	
	public int holeX()
    {
		return x;
    }

    public int holeY()
    {
    	return y;
    }
}