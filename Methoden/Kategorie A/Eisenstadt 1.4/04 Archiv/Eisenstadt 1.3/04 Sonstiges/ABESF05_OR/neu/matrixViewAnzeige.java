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
import java.awt.*;

import javax.swing.*;

public class matrixViewAnzeige extends JInternalFrame 
{
	private int x;
	private int y;
	private ArrayList buttonList;
		
	public matrixViewAnzeige(ActionListener aL,int x_,int y_)
	{
       	super("Stadtplan",true,true,true,true);
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	setLocation(201,0);
    	switch(screenSize.width) {
        case 640: // 640x480
              break;

        case 800: // 800x600
              break;

        case 1024: 
        	  setSize(514,550);		// 1024x768
              break;

        case 1280:  // 1280x1024
        	setSize(773,814);
        	break;

        case 1600:  // 1600x1200
              break;

        default:  // 1024x768
        	setSize(520,550);		
              break;
        }

       	
        
        setVisible(true);
        
        buttonList = new ArrayList();
        
        /*JMenuBar menubar = new JMenuBar();  Ist jetzt in Hauptfenster enthalten
        menubar.add(erstelleDateiMenu(aL));
        setJMenuBar(menubar);*/
        
        x = x_;
        y = y_;
	}
       
	/*private JMenu erstelleDateiMenu(ActionListener aL) Ist jetzt in Hauptfenster enthalten
	{
		JMenu ret = new JMenu("Datei");
		ret.setMnemonic('D');
		JMenuItem mi;
	
		mi = new JMenuItem("Speichern", 'p');
		mi.addActionListener(aL);
		mi.setActionCommand("S");
		ret.add(mi);
		
		return ret;
	}*/
	
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
