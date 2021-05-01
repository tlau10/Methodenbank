/*
 * Created on 14.06.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package AnwBESF;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;

/**
 * @author jensk
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ergebnisAnzeige extends JInternalFrame {
	public ergebnisAnzeige() 
	{
		super("Ergebnis",true,true,true,true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	switch(screenSize.width) {
        case 640: // 640x480
              break;

        case 800: // 800x600
              break;

        case 1024: // 1024x768
        	setLocation(714,0);
        	setSize(310,690);
        	break;

        case 1280:  // 1280x1024
        	setLocation(969,0);
        	setSize(310,952);
        	break;

        case 1600:  // 1600x1200
              break;

        default:  // 1024x768
        	setLocation(720,0);
        	setSize(310,690);
              break;
        }
		
		setVisible(true);
	}
}

