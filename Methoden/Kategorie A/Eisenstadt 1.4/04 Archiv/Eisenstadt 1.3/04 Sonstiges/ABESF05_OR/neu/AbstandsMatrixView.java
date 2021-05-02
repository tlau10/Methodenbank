package AnwBESF;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Jens
 * @version 1.0
 */
import javax.swing.JInternalFrame;
import java.awt.*;

public class AbstandsMatrixView extends JInternalFrame 
{
	public AbstandsMatrixView(int x, int y) 
	{
		super("Abstände von Haltestellen zu allen anderen Feldern",true,true,true,true);
		setLocation(201,0);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	switch(screenSize.width) {
        case 640: // 640x480
              break;

        case 800: // 800x600
              break;

        case 1024: 
        	setLocation(201,550);  // 1024x768
        	setSize(514,140);
        	break;

        case 1280:  // 1280x1024
        	setLocation(201,811);
        	setSize(773,140);
        	break;

        case 1600:  // 1600x1200
              break;

        default:  // 1024x768
        	setLocation(201,550);
        	setSize(520,140);
              break;
        }
		
		setVisible(true);
	}
}