package AnwBESF;

/*
 * Created on 13.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Alex
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.*;

public class matrixViewAnzeige extends JInternalFrame 
{
	private int x;
	private int y;
	private ArrayList buttonList;
		
	public matrixViewAnzeige(ActionListener aL,int x_,int y_)
	{
       	super("Gewichtungsmatrix",true,true,true,true);
        setLocation(201,0);
        setSize(520,550);
        setVisible(true);
        
        buttonList = new ArrayList();
        
        JMenuBar menubar = new JMenuBar();
        menubar.add(erstelleDateiMenu(aL));
        setJMenuBar(menubar);
        
        x = x_;
        y = y_;
	}
       
	private JMenu erstelleDateiMenu(ActionListener aL)
	{
		JMenu ret = new JMenu("Datei");
		ret.setMnemonic('D');
		JMenuItem mi;
	
		mi = new JMenuItem("Speichern", 'p');
		mi.addActionListener(aL);
		mi.setActionCommand("S");
		ret.add(mi);
		
		return ret;
	}
	
	public int holeX()
	{
		return x;
	}
	
	public int holeY()
	{
		return y;
	}
	
	public void fuegeButtonZuArray(matrixButton mB)
	{
		buttonList.add(mB);
	}
	
	public void aktualisiereButtons()
	{
		int groesse = buttonList.size();
		for (int i = 0; i < groesse; i++)
		{
			matrixButton mB = (matrixButton) buttonList.get(i);
			mB.aktualisiereButton();
		}
	}
}
