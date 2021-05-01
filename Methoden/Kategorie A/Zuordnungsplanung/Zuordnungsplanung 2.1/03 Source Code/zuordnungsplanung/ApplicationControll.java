//awt
package zuordnungsplanung;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 * <p>Title: Zuordnungsplanung V 1.0</p>
 * <p>Description: ApplicationControll is the interface to FileHandler (save and load), Solver and FrameMain (GUI).</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: </p>
 * <p>Geändert im Jahr 2013 Benedikt Wölfle</p>
 * @author Patrick Badent, Benedikt Wölfle
 * @version 1.1
 */


public class ApplicationControll
{
  boolean packFrame = false;
  public FrameMain frameMain;
  String filename = null;

  //Constructor
  public ApplicationControll()
  {
    frameMain = new FrameMain(this);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      frameMain.pack();
    else
      frameMain.validate();

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frameMain.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;

    frameMain.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frameMain.setVisible(true);
    frameMain.setResizable(false);
  }

  public DataModel dateiOeffnen(ActionEvent e) throws Exception
  {
    DataModel dataModel=null;
    JTextField jTextField = new JTextField();
    jTextField.setText("."+Variables.fileExtension);
    
    JFileChooser fc = new JFileChooser("Daten");
    FileFilterZuo myFilter = new FileFilterZuo(Variables.fileExtension);
    fc.addChoosableFileFilter(myFilter);

    int returnVal = fc.showOpenDialog(null); //ok=1, ko=0

    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      File file = fc.getSelectedFile();
      String path = file.getAbsolutePath();
      jTextField.setText(path);
    }
    if (returnVal != 1) //if(datei öffnen)
    {
      String filename = jTextField.getText();
      try
      {
        dataModel = new FileHandler().readZuoData(filename);
        this.filename = filename;
        return dataModel;
      }
      catch (Exception eOeffnen)
      {
        throw eOeffnen;
      }
    }

    else//else (abbrechen des Filechoosers)
      return null;
  }

  public boolean dateiSpeichern(ActionEvent e, DataModel dataModel)
  {
    //if(filename==null || filename.startsWith("."+Variables.fileExtension))// if(no filename choosen yet || filename is only .zuo)
    if(filename==null)
    {
      if(!this.dateiSpeichernUnter(e, dataModel))
      //if(! call dateiSpeichernUnter) {will store a filename in this.filename and call dateiSpeichern() again}
        return false;//quit
    }
    else//else(filename is valid)
    {
      try
      {
        new FileHandler().writeZuoData(dataModel,filename);
      }
      catch (Exception ex)
      {
        ExceptionDialog.showExceptionDialog("Fehler: Die Datei ist Feherhaft.",ex.getMessage(),ex);
      }
    }
    return true;
  }


  public boolean dateiSpeichernUnter(ActionEvent e,DataModel dataModel)
  {
    JTextField jTextField = new JTextField("."+Variables.fileExtension);

    JFileChooser fc = new JFileChooser();
    FileFilterZuo myFilter = new FileFilterZuo(Variables.fileExtension);
    fc.addChoosableFileFilter(myFilter);

    boolean fileElected=false;

    while(!fileElected)//while(no file choosen)
    {
      int returnVal = fc.showSaveDialog(null); //File chooser öffnen, 1==abbrechen, 0==speichern

      if (returnVal == JFileChooser.APPROVE_OPTION)
      {
        File file = fc.getSelectedFile();
        String path = file.getAbsolutePath();
        jTextField.setText(path);
      }

      if (returnVal==0)//if(do save)
      {
        String filename = jTextField.getText(); //get Filename
        if(!this.isFilename(filename))
          filename = filename + "."+Variables.fileExtension; //append a .csv

        if (new File(filename).exists())//if(file is existing)
        {
          if (Variables.jOptionPane_DateiUeberschreiben())//if(file overwrite) 0==ja, 1==no
            fileElected=true;//set fileElected to true, so can exit while
          this.filename=filename;
        }
        else//if file doesn't exist
        {
          this.filename=filename;
          fileElected = true; //set fileElected to true, so can exit while
        }
      }
      else//else (quit saving)
        return false; //cancel save funktion
    }

    this.dateiSpeichern(e, dataModel); //call dateiSpeichern (filename is stored in applicationControll.filename)

    return true;
  }


  public String starteSolve(DataModel dataModel, int solverTyp)
  {
    String result = "Verwendeter Solver: ";
    Solver solve=null;
    if(solverTyp==1)
    {
      solve = new SolveLP();
      result = result + "LP\n";
    }
    else if(solverTyp==2)
    {
    	//Geändert durch Benedikt Wölfle 2013(anstatt SolverXA wird Solver MOPS aufgerfufen
      solve = new SolverMOPS();
      result = result + "MOPS\n";
    }
    else//Solver must be (solverTyp==3)
    {
      solve = new SolveEnumeration();
      result = result + "Enumeration\n";
    }
    solve.starteSolver(dataModel);

    String[] ergebnis = solve.getErgebnis();
    //Geändert durch Benedikt Wölfle 2013 ( Zeitberechnung nicht sinnvoll aufgrund anderer Solveraufrufe
    //result = result + "\n" + "benötigte Zeit: ";
    //result = result + "\n\n"+ "Typ: ";
    if(dataModel.getMaximize())
      result=result+"Maximierung";
    else
      result=result+"Minimierung";

    result= result +"\n"+ "Zielfunktionswert: " +  solve.getZielfunktionswert() + "\n\n";

    for(int i=0; i<ergebnis.length;i++)
      result = result + ergebnis[i] + "\n";

    return result;
  }

  private boolean isFilename(String filename)
  {
    if(filename.substring(filename.length()-2,filename.length()-1).equals("."))
      return true;
    else if(filename.substring(filename.length()-3,filename.length()-2).equals("."))
      return true;
    else if(filename.substring(filename.length()-4,filename.length()-3).equals("."))
      return true;
    else if(filename.substring(filename.length()-5,filename.length()-4).equals("."))
      return true;
    else
      return false;
  }
}