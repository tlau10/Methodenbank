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
import java.awt.Event;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;



public class matrixView extends JFrame
{
        private JDesktopPane desk;

        public matrixView(ActionListener aL)
        {
        	    this.setTitle("Haltestellen - Optimierung");
        		setSize(1024,743);
                setLocation(0,0);
                setExtendedState(JFrame.MAXIMIZED_BOTH);

                addWindowListener(new WindowClosingAdapter(true));

                JMenuBar menubar = new JMenuBar();
                menubar.add(erstelleDateiMenu(aL));
                setJMenuBar(menubar);

                desk = new JDesktopPane();
                setContentPane(desk);
        }

        private JMenu erstelleDateiMenu(ActionListener aL)
        {
            JMenu ret = new JMenu("Datei");
            ret.setMnemonic('D');
            JMenuItem oeffnen;
            JMenuItem speichern;

            oeffnen = new JMenuItem("Öffnen", 'f');
            setCtrlAccelerator(oeffnen, 'O');
            oeffnen.addActionListener(aL);
            oeffnen.setActionCommand("O");
            
            speichern = new JMenuItem("Speichern", 'g');
            setCtrlAccelerator(speichern, 'S');
            speichern.addActionListener(aL);
            speichern.setActionCommand("S");
            
            ret.add(oeffnen);
            ret.add(speichern);
            
            return ret;
        }

          private void setCtrlAccelerator(JMenuItem mi, char acc)
          {
            KeyStroke ks = KeyStroke.getKeyStroke(
              acc, Event.CTRL_MASK
            );
            mi.setAccelerator(ks);
          }
}
