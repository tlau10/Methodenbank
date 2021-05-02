package Dakin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

class ActionPfadSolverPfad implements ActionListener
{
	private EingabeTabelle eingabeFenster;

	//Konstruktor
	public ActionPfadSolverPfad(JInternalFrame d)
	{
		eingabeFenster=(EingabeTabelle)d;
	}

	public void actionPerformed(ActionEvent e) {
		if(eingabeFenster.solverPfad.equals("") ) {
			eingabeFenster.solverPfad = ".";
		}
		JFileChooser fileChooser=new JFileChooser(eingabeFenster.solverPfad);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("Solver Pfad setzen");

		if (fileChooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION)
		{
			try
			{
				//eingabeFenster.solverPfad=fileChooser.getSelectedFile().toString();
				eingabeFenster.solverPfad=fileChooser.getCurrentDirectory().toString() + "\\";
			}
			catch (Exception exception)
			{
				JOptionPane.showMessageDialog(null, "Ein Dateifehler ist aufgetaucht",
                                    "Fehler beim Pfad setzen", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}

class ActionDateiOeffnen implements ActionListener {
	public ActionDateiOeffnen(JInternalFrame ef)
	{
		eingabeTabelle=(EingabeTabelle)ef;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser=new JFileChooser("../daten");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("Datei Oeffnen");


		if (fileChooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION)
		{
			try {
				File datei = new File(fileChooser.getSelectedFile().toString());
				BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(datei)));

				int i,rows, cols;

				rows=Integer.valueOf(br.readLine().substring(5)).intValue();
				cols=Integer.valueOf(br.readLine().substring(5)).intValue();

				eingabeTabelle.anzRowsTextField.setText(String.valueOf(rows));
				eingabeTabelle.anzColsTextField.setText(String.valueOf(cols));

				eingabeTabelle.anzRowsTextField.postActionEvent();
				eingabeTabelle.anzColsTextField.postActionEvent();

				for (int r=0; r<eingabeTabelle.dm.datenMatrix.getNumRows(); r++)
					for (int c=0; c<eingabeTabelle.dm.datenMatrix.getNumCols(); c++)
						eingabeTabelle.dm.datenMatrix.setValueAt(r,c,br.readLine().substring(5));

				br.close();

				// setzen der Min/Max RadioButtons
				if (((String)eingabeTabelle.dm.datenMatrix.getValueAt
						(0,eingabeTabelle.dm.datenMatrix.getNumCols()-1)).equals((String)"max."))
					eingabeTabelle.rbMax.setSelected(true);
				else
					eingabeTabelle.rbMin.setSelected(true);

				eingabeTabelle.updateTable();
			}
			catch (Exception exception)
			{
				JOptionPane.showMessageDialog(null, "Ein Dateifehler ist aufgetaucht","Fehler beim Oeffnen", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private EingabeTabelle eingabeTabelle;
}

class ActionDateiSpeichern implements ActionListener {

	public ActionDateiSpeichern(JInternalFrame ef)
	{
		eingabeTabelle=(EingabeTabelle)ef;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("Datei Speichern");

		if (fileChooser.showSaveDialog(null) != JFileChooser.CANCEL_OPTION)
		{
			try {
				File datei = new File(fileChooser.getSelectedFile().toString());
				FileOutputStream fos=new FileOutputStream(datei);

				fos.write(((String)"Rows:").getBytes());
				fos.write(String.valueOf(eingabeTabelle.dm.datenMatrix.getNumRows()).getBytes());
				fos.write(((String)"\n").getBytes());

				fos.write(((String)"Cols:").getBytes());
				fos.write(String.valueOf(eingabeTabelle.dm.datenMatrix.getNumCols()).getBytes());
				fos.write(((String)"\n").getBytes());

				for (int r=0; r<eingabeTabelle.dm.datenMatrix.getNumRows(); r++)
					for (int c=0; c<eingabeTabelle.dm.datenMatrix.getNumCols(); c++)
					{
						fos.write(((String)"r").getBytes());
						fos.write(String.valueOf(r).getBytes());
						fos.write(((String)"c").getBytes());
						fos.write(String.valueOf(c).getBytes());
						fos.write(((String)":").getBytes());
						fos.write(eingabeTabelle.dm.datenMatrix.getValueAt(r,c).getBytes());
						fos.write(((String)"\n").getBytes());
					}

				fos.close();
			}
			catch (Exception exception)
			{
				JOptionPane.showMessageDialog(null, "Ein Dateifehler ist aufgetaucht","Fehler beim Schreiben", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private EingabeTabelle eingabeTabelle;
}

class ActionDateiBeenden implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}

class ActionInfoEntwickler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null,
			"FH Konstanz\nSommersemester 2000\n\nAndreas Gossmann\nRainer Faller\nHenrik Feidner\nHarald Woelfle",
			"Info",JOptionPane.PLAIN_MESSAGE,null );
	}
}

class ActionFensterAusgabefenster implements ActionListener {
	public ActionFensterAusgabefenster(JDesktopPane d)
	{
		Desktop=d;
	}

	public void actionPerformed(ActionEvent e) {
		JInternalFrame[] allFrames=Desktop.getAllFrames();

		try {
			int frameNum=0;

			if (allFrames[1].getTitle()=="Ausgabe")
				frameNum=1;
                        if(allFrames[2].getTitle()=="Ausgabe")
                                frameNum=2;

			allFrames[frameNum].setIcon(false);
			allFrames[frameNum].show();
		}
		catch (Exception exception)
		{
		}
	}
	private JDesktopPane Desktop;
}

class ActionFensterVisualisiereFenster implements ActionListener {
  public ActionFensterVisualisiereFenster(JDesktopPane d)
  {
    Desktop = d;
  }
  public void actionPerformed(ActionEvent e) {
    JInternalFrame[] allFrames=Desktop.getAllFrames();

    try {
      int frameNum=0;

      if(allFrames[1].getTitle()=="Loesungsraum")
        frameNum=1;
      if(allFrames[2].getTitle()=="Loesungsraum")
        frameNum=2;

      allFrames[frameNum].setIcon(false);
      allFrames[frameNum].show();
    }
    catch(Exception exception) {
    }
  }
  private JDesktopPane Desktop;
}

class ActionFensterEingabefenster implements ActionListener {
	public ActionFensterEingabefenster(JDesktopPane d)
	{
		Desktop=d;
	}

	public void actionPerformed(ActionEvent e) {
		JInternalFrame[] allFrames=Desktop.getAllFrames();

		try {
			int frameNum=0;

			if (allFrames[1].getTitle()=="Eingabe")
				frameNum=1;
                        if (allFrames[2].getTitle()=="Eingabe")
                                frameNum=2;

			allFrames[frameNum].setIcon(false);
			allFrames[frameNum].show();
		}
		catch (Exception exception)
		{
		}
	}
	private JDesktopPane Desktop;
}

public class dakin extends JFrame {

	private JDesktopPane Desktop;
	public JInternalFrame EingabeFenster;
	public JInternalFrame AusgabeFenster;
        public JInternalFrame VisualisiereFenster;

	public dakin() {
	    super( "Dakin Eingabetabelle" );

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation(0,0);
            setSize(screenSize.width, screenSize.height);
            //setLocation(40,40);
            //setSize( 900, 650 );

	    Desktop=new JDesktopPane();

            getContentPane().add(Desktop);

	    // Zwei Fenster ins Desktop einfuegen
	    EingabeFenster=new EingabeTabelle();
            EingabeFenster.setClosable(false);
	    EingabeFenster.setBounds(0,0,550,(screenSize.height/2)-30);
            //EingabeFenster.setBounds(10,10,600,400);
	    Desktop.add(EingabeFenster, new Integer(0));

	    AusgabeFenster=new DakinAusgabe();
	    AusgabeFenster.setClosable(false);
	    AusgabeFenster.setBounds(0,(screenSize.height/2)-30,screenSize.width/2,(screenSize.height/2)-50);
            //AusgabeFenster.setBounds(40,40,600,400);
	    Desktop.add(AusgabeFenster, new Integer(0));

            VisualisiereFenster=new Visualisiere();
            VisualisiereFenster.setClosable(false);
            //VisualisiereFenster.setBounds(screenSize.width/2,(screenSize.height/2)-30,screenSize.width/2,(screenSize.height/2)-50);
            VisualisiereFenster.setBounds(40,40,screenSize.width/2,(screenSize.height/2)-50);
            Desktop.add(VisualisiereFenster, new Integer(0));

	    // MenuBar einfuegen
	    JMenuBar jmb=new JMenuBar();
	    JMenuItem item;

	    // Datei Menu einfuegen
	    JMenu Datei=new JMenu("Datei");

	    Datei.add(item=new JMenuItem("Oeffnen"));
	    item.addActionListener(new ActionDateiOeffnen(EingabeFenster));
	    Datei.add(item=new JMenuItem("Speichern als..."));
	    item.addActionListener(new ActionDateiSpeichern(EingabeFenster));
	    Datei.addSeparator();
	    Datei.add(item=new JMenuItem("Beenden"));
	    item.addActionListener(new ActionDateiBeenden());

	    jmb.add(Datei);

	    // Fenster Menu einfuegen
	    JMenu Fenster=new JMenu("Fenster");

	    Fenster.add(item=new JMenuItem("Eingabefenster"));
	    item.addActionListener(new ActionFensterEingabefenster(Desktop));
	    Fenster.add(item=new JMenuItem("Ausgabefenster"));
	    item.addActionListener(new ActionFensterAusgabefenster(Desktop));
            Fenster.add(item=new JMenuItem("Visualisierungsfenster"));
            item.addActionListener(new ActionFensterVisualisiereFenster(Desktop));

	    jmb.add(Fenster);

	    // Pfad Mene einfuegen
	    JMenu Pfad=new JMenu("Pfad");

	    Pfad.add(item=new JMenuItem("Solverpfad setzen"));
	    item.addActionListener(new ActionPfadSolverPfad(EingabeFenster));

	    jmb.add(Pfad);

	    // Info Menu einfuegen
	    JMenu Info=new JMenu("Info");

	    Info.add(item=new JMenuItem("ueber die Entwickler"));
	    item.addActionListener(new ActionInfoEntwickler());

	    jmb.add(Info);

	    // Hintergrundbild einfuegen
	    ImageIcon icon=new ImageIcon("imperia.jpg");
	    JLabel l=new JLabel(icon);
	    l.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
	    Desktop.add(l, new Integer(Integer.MIN_VALUE));

	    getContentPane().add(jmb, BorderLayout.NORTH);


	}

	public static void main(String[] args) {
	    dakin frame = new dakin();
	    frame.addWindowListener( new WindowAdapter() {
	      public void windowClosing( WindowEvent e ) {
		System.exit(0);
	      }
	    });
	    frame.setVisible(true);
	    JOptionPane.showMessageDialog(null,
			"Nach der letzten Eingabe in die Matrix muss \"return\" gedrueckt werden!",
			"Bug-Info",JOptionPane.PLAIN_MESSAGE,null );
	 }

}