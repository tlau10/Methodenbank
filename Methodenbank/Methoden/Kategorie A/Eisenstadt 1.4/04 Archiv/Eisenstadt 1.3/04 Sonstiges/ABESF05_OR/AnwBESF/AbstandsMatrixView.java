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

public class AbstandsMatrixView extends JInternalFrame 
{
	public AbstandsMatrixView(int x, int y) 
	{
		super("Abstandsmatrix",true,true,true,true);
		setLocation(200,550);
		setSize(520,140);
		setVisible(true);
	}
}