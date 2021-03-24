package SimulationVisuell;

import java.awt.event.*;

public class WindowsClosingAdapter extends WindowAdapter
{
    private boolean exitSystem;

    /**
     * Erzeugt einen WindowClosingAdapter zum Schliessen
     * des Fensters. Ist exitSystem true, wird das komplette
     * Programm beendet.
     */
    public WindowsClosingAdapter(boolean exitSystem)
    {
      this.exitSystem = exitSystem;
    }

    /**
     * Erzeugt einen WindowClosingAdapter zum Schliessen
     * des Fensters. Das Programm wird nicht beendet.
     */
    public WindowsClosingAdapter()
    {
      this(false);
    }

    public void windowClosing(WindowEvent event)
    {
      event.getWindow().setVisible(false);
      event.getWindow().dispose();
      if (exitSystem) {
        System.exit(0);
      }
    }
  }