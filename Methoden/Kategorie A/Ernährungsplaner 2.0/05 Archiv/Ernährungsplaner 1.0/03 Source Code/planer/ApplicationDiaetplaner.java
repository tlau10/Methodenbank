package planer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.UIManager;

import org.jdom.Element;


/**
 * <p>Title: Application Diaetplaner </p>
 * <p>Description: Anwendung mit Main-Methode für Programm Diätplaner </p>
 * <p>Copyright: Matthias Siegert Copyright (c) 2003</p>
 * <p>Company:FH Konstanz </p>
 * @author Matthias Siegert
 * @version 1.0
 */

public class ApplicationDiaetplaner implements DiaetplanerInterface
{
  boolean packFrame = false;
  private boolean printMatrix_ = true;

  private Vector allEatables; //Enthaelt alle Lebensmittel, die verfuegbar sind
  private Vector menu; //Enthaelt nur die Lebensmittel, die fuer das Menue ausge
  //waehlt wurden
  private XmlListHandler myListHandler;
  private Matrix myMatrix; //Diese Matrix enthaelt die Restriktionen und die Zielfunktion
  private Matrix grenzenMatrix; //Matrix für Zuordnung der Lebensmittel zu den Gruppen
  //als Hilfsmittel für Restriktionenerzeugung
  private String[] gruppenName; //Array enthaelt Zusammenhang GruppenID und Gruppenname
  private double menuCalories = 0;

  private String xmlPath = "Lebensmittel.xml";

  public static void main(String[] args){
     try {
       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     }
     catch(Exception e) {
       e.printStackTrace();
     }
     try
    {
      try {
		ApplicationDiaetplaner applicationDiaetplaner1 = new
		      ApplicationDiaetplaner();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
      catch (DiaetplanerException e)
     {
       System.out.println("Fehler beim Initialisieren der Anwendung: " + e.getMessage());
     }

   }


   public Vector getAllEatables()
   {
     return allEatables;
   }

  public double getMenuCalories()
  {
    return menuCalories;
  }

  public ApplicationDiaetplaner() throws DiaetplanerException, IOException
  {
    //Read from XML File
    readFromXmlFile(xmlPath);  //Verfuegbare Lebensmittel werden eingelesen
    //Zuordnung GruppenNummer zu Gruppenname herstellen
    gruppenName = new String[11];
    gruppenName[0] = "Milchprodukte" ;
    gruppenName[1] = "Cerealien" ;
    gruppenName[2] = "Backware_fruehstueck" ;
    gruppenName[3] = "Getraenk_fruehstück" ;
    gruppenName[4] = "Hauptgericht" ;
    gruppenName[5] = "Beilage" ;
    gruppenName[6] = "Dessert" ;
    gruppenName[7] = "Getränk_mittagessen" ;
    gruppenName[8] = "Brotbelag" ;
    gruppenName[9] = "Backwaren_abendessen" ;
    gruppenName[10] = "Getraenk_abendessen" ;

    mainFrame frame = new mainFrame(allEatables,this);

   //Validate frames that have preset sizes
   //Pack frames that have useful preferred size info, e.g. from their layout
   if (packFrame) {
     frame.pack();
   }
   else {
     frame.validate();
   }
   //Center the window
   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   Dimension frameSize = frame.getSize();
   if (frameSize.height > screenSize.height) {
     frameSize.height = screenSize.height;
   }
   if (frameSize.width > screenSize.width) {
     frameSize.width = screenSize.width;
   }
   frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
   frame.setVisible(true);

  }


 /* public static void main(String[] args)
  {
    try
    {
      ApplicationDiaetplaner applicationDiaetplaner1 = new
          ApplicationDiaetplaner();
      //Testtreiber starten
      applicationDiaetplaner1.testTreiber(1);
      applicationDiaetplaner1.testTreiber(2);
      applicationDiaetplaner1.testTreiber(3);
      applicationDiaetplaner1.testTreiber(4);
      applicationDiaetplaner1.testTreiber(5);

      System.out.println("Jippie !!!! 23.12 22:50 Uhr, Testtreiber lauft ohne Fehler durch");

      //Armin hier Dein Einsatz :-)
      //Gui anlegen
      //DiaetplanerGui myDiaetplanerGui = new DiaetplanerGui(Vector allEatables, this);
    }
    catch (DiaetplanerException e)
    {
      System.out.println("Fehler beim Initialisieren der Anwendung: " + e.getMessage());
    }

  }*/


  public Vector executeCalculation(Vector choosenEatables, int calories) throws DiaetplanerException
  {
    //Matrix erzeugen
    int anzahlLebensmittelgruppen = 11;
    //int matrixAnzahlZeilen = allEatables.size() + anzahlLebensmittelgruppen + 2;
    int matrixAnzahlZeilen = anzahlLebensmittelgruppen + 2;
    int matrixAnzahlSpalten = allEatables.size() + 2;
    int startpunktAusgewaehlteLebensmittel = anzahlLebensmittelgruppen + 2;
    int ZaehleZeileAusgewaehlteLebensmittel = startpunktAusgewaehlteLebensmittel;
    //Diese Variable wird benötigt, um die Zeilenanzahl der Restriktionen der
    //ausgewaehlten Lebensmittel zu bestimmen

    myMatrix = new Matrix(matrixAnzahlZeilen, matrixAnzahlSpalten,1000,1000);
    grenzenMatrix = new Matrix(allEatables.size(),3,allEatables.size(),3);

    try
    {
      for (int i = 0; i < allEatables.size(); i++) //Alle Lebensmittel werden
      //durchlaufen
      {
        Element myElement = (Element) allEatables.get(i);
        //Zielfunktion in Matrix schreiben
        myMatrix.setElement(0, i, myElement.getChild("Kalorien").getText());
        //Tagesbedarf erstellen
        myMatrix.setElement(1, i, myElement.getChild("Kalorien").getText());
        //Zugehörigkeiten zu Lebensmittelgruppen erstellen
        //Zeile der Matrix ist Gruppennummer + 2 (Zielfunktion und Tagesbedarf)
        int zeileGruppenzugehoerigkeit = Integer.parseInt(myElement.getChild(
            "GruppenID").getText()) + 2;
        myMatrix.setElement(zeileGruppenzugehoerigkeit, i, "1");
        //Jedes ausgewaehlte Lebensmittel muss einmal vorkommen
        if (choosenEatables.get(i) != null) {
          myMatrix.setElement(ZaehleZeileAusgewaehlteLebensmittel, i, "1");
          ZaehleZeileAusgewaehlteLebensmittel++;
        }
        //Grenzenmatrix fuellen Alle Anzahlen muessen ganzzahlig sein und zwischen 0 und 1 liegen !
        grenzenMatrix.setElement(i, 0, 0);
        grenzenMatrix.setElement(i, 1, 1);
        grenzenMatrix.setElement(i, 2, "ja");
      }

      //Zeichen und rechte Seite der Matrix setzen
      //Zielfunktion
      myMatrix.setElement(0, (allEatables.size() + 1), "min");
      //Tagesbedarf
      myMatrix.setElement(1, allEatables.size(), ">=");
      myMatrix.setElement(1, (allEatables.size()) + 1, calories);
      //Zugehörigkeiten zu Lebensmittelgruppen
      for (int j = 2; j < (anzahlLebensmittelgruppen + 2); j++) {
        myMatrix.setElement(j, allEatables.size(), ">=");
        myMatrix.setElement(j, (allEatables.size() + 1), "1");
      }

      //Jedes ausgewaehlte Lebensmittel muss einmal vorkommen
      for (int m = startpunktAusgewaehlteLebensmittel;
           m < ZaehleZeileAusgewaehlteLebensmittel; m++)
      {
        myMatrix.setElement(m, allEatables.size(), "=");
        myMatrix.setElement(m, (allEatables.size() + 1), "1");
      }

      //Solver aufrufen
      Solver mySolver = new LP_Solve();
      double[] ergebnis = mySolver.calcModel(myMatrix, grenzenMatrix);
      //Neuen Vector für Armin erstellen, der nur MenueElemente enthält
      //Zuerst den Vector mit allen Elementen initialisieren
      menu = (Vector) allEatables.clone();

      for (int i = 1; i < ergebnis.length; i++)
        {
          System.out.println("x" + i + " = " + ergebnis[i]);
          if (ergebnis[i] == 0.0) { //Alle Nahrungsmittel, die nicht im Menue vorkommen,
            //werden rausgeschmissen
          menu.setElementAt(null,i-1);
        }
      }
      //Ausgerechnete Kalorien setzen
      menuCalories = ergebnis[0];
      //Aus Ergebnis Vector menu zurückgeben
      if (printMatrix_ == true)
        printmatrix(myMatrix);

      return menu;
    }
    catch(DiaetplanerException e)
    {
      throw e;
    }
  }

  public Element newEatable(String name, int gruppenID, String amount, String calories)
  {
    Element myElement = (Element) myListHandler.getList().get(0); //Erstes Element aus
    //Liste wird geholt und geklont
    Element myNewElement = (Element) myElement.clone();
    //Neue Parameter werden gesetzt
    myNewElement.getChild("Name").setText(name);
    myNewElement.getChild("GruppenID").setText(String.valueOf(gruppenID));
    myNewElement.getChild("Menge").setText(amount);
    myNewElement.getChild("Kalorien").setText(calories);
    myNewElement.getChild("Gruppe").setText(gruppenName[gruppenID]);
    //Damit habe ich ein neues Element :-)

    return myNewElement;
  }

  public void addEatable(Element newEatable) throws DiaetplanerException
  {
    //Neues Lebensmittel in die Liste einfuegen
    myListHandler.addElement(newEatable);

    //Neues Lebensmittel dem Vector hinzufügen
    updateAllEatablesVector();

  }



  public boolean deleteEatable(Element pEatable) throws DiaetplanerException
  {

    String gruppenNummer = String.valueOf(pEatable.getChild("GruppenID").getText());
    String name = String.valueOf(pEatable.getChild("Name").getText());
    boolean found = false;
    for (int i=0; i<allEatables.size();i++)
    {
      //Vector allEatables wird nach entsprechendem Element durchsucht
      Element temp = (Element) allEatables.get(i);
      //Wenn Name und GruppenID stimmen, darf Element geloescht werden
      if (temp.getChild("GruppenID").getText().equals(gruppenNummer) && !temp.getChild("Name").getText().equals(name))
      {
        found = true;
        break;
      }
    }
    if(found)
    {
      //Löscht ein Element aus der Lebensmittelliste
      myListHandler.deleteElement(pEatable);
      //Updaten des AllEatables Vector
      updateAllEatablesVector();

      return true;
    }
    else
    {
      return false;
    }

  }

  public void deleteEatable(String name, int gruppenNummer) throws DiaetplanerException
  {

    //Löscht ein Element aus der Lebensmittelliste
    Element Eatable = null;
    for (int i=0; i<allEatables.size();i++)
    {
      //Vector allEatables wird nach entsprechendem Element durchsucht
      Element temp = (Element) allEatables.get(i);
      //Wenn Name und GruppenID stimmen, darf Element geloescht werden
      if (temp.getChild("Name").getText().equals(name)
      && temp.getChild("GruppenID").getText().equals(String.valueOf(gruppenNummer)))
      {
        Eatable = temp;
      }
    }
    if (Eatable != null)
    myListHandler.deleteElement(Eatable);
    else
      throw new DiaetplanerException("Das Element " + name + "kann nicht geloescht werden," +
                                     "es konnte nicht gefunden werden !");
    //Updaten des AllEatables Vector
    updateAllEatablesVector();
  }

   public String [] getGruppenName()
   {
     return gruppenName;
   }

   public void setPrintMatrix(boolean print)
   {
     printMatrix_ = print;
   }


   public void saveToXmlFile(String filename)throws DiaetplanerException
   {
    //Geänderten Vector ins XML-File schreiben
    try
    {
      //myListHandler.saveXmlList("Lebensmittel.xml");
      myListHandler.saveXmlList(filename);
    }
    catch (DiaetplanerException e)
    {
      throw e;
    }
  }

  public void readFromXmlFile(String filename) throws DiaetplanerException, IOException
  {
	  System.out.println(" readFromXml");
    //Vector allEatables initialisieren
    try
    {
      myListHandler = new XmlListHandler(filename);
    }
    catch (DiaetplanerException e)
    {
      throw e;
    }
    //Der Vector muss so gross sein wie die Liste
    System.out.println("+++++++++++++");
    allEatables = new Vector(myListHandler.getList().size());
    //Elemente hinzufuegen
    updateAllEatablesVector();
  }

  private void updateAllEatablesVector()
  {
    //Erst wird der alte Stand komplett rausgeschmissen
    allEatables.removeAllElements();
    //Anschließend wird der Vector mit der neuen Liste gefuellt
    for (int i=0; i < myListHandler.getList().size(); i++)
    {

    Element myElement = (Element) myListHandler.getList().get(i);
      allEatables.add(myListHandler.getList().get(i));
    }
  }

  private void testTreiber(int testfall) throws DiaetplanerException
  {
    //Diese Methode dient nur zum testen der Applikation (ohne GUI) und der Solver-
    //schnittstelle
    //Testtreiber fuer Anwendung
    Vector myTestchoosenEatables;
    Element myElement;


    switch(testfall)
    {

    case 1:
    //1.Testfall Berechnung starten
    //Alle Lebensmittel ausgewaehlt
    //Alle Elemente sollten angezeigt werden
    System.out.println("\n\n\n");
    System.out.println("*********  TESTFALL 1  ***********");
    System.out.println("Alle Lebensmittel ausgewaehlt !");
    myTestchoosenEatables = (Vector) allEatables.clone();
    try
    {
      menu = executeCalculation(myTestchoosenEatables, 1000);
    }
    catch (DiaetplanerException e)
    {
      System.out.println(e.getMessage());
    }
    //Ausgabe
    System.out.println("Im folgenden Menü sollten alle Lebensmittel angezeigt werden:");
    for (int i=0; i<allEatables.size();i++)
      if(menu.get(i) != null)
      {
        myElement = (Element) menu.get(i);
        System.out.println(myElement.getChild("Name").getText());
      }
    break;

    case 2:
      //2.Testfall Berechnung starten
      //Kein Lebensmittel ausgewaehlt
      //Pro Gruppe muss mindestens ein Lebensmittel angezeigt werden
      System.out.println("\n\n\n");
      System.out.println("*********  TESTFALL 2  ***********");
      System.out.println("Kein Lebensmittel ausgewaehlt !");
      myTestchoosenEatables = (Vector) allEatables.clone();
      for (int i=0; i<allEatables.size();i++)
        {
          myTestchoosenEatables.remove(i);
          myTestchoosenEatables.add(i, null);
        }
      try
      {
        menu = executeCalculation(myTestchoosenEatables, 1000);
      }
      catch (DiaetplanerException e)
      {
        System.out.println(e.getMessage());
      }

    System.out.println("Im folgenden Menü sollte pro Gruppe nur das Lebensmittel" +
                       "mit den wenigsten Kalorien angezeigt werden:");
    //Ausgabe Menue
    for (int i=0; i<allEatables.size();i++)
      if(menu.get(i) != null)
      {
        myElement = (Element) menu.get(i);
        System.out.println(myElement.getChild("Name").getText() + " " + i);
      }
    System.out.println("All Eatables.size(): " + allEatables.size());
    System.out.println("myTestchoosenEatables.size(): " + myTestchoosenEatables.size());
    System.out.println("menu.size(): " + menu.size());


    break;
    case 3: //3.Testfall Berechnung starten
            //Die ersten drei Lebensmittel ausgewaehlt
            //Die ersten drei Lebensmittel muessen angezeigt werden + Ein
            //Lebensmittel pro Gruppe
            System.out.println("\n\n\n");
            System.out.println("*********  TESTFALL 3  ***********");
            System.out.println("Die ersten drei Lebensmittel ausgewaehlt !");
            myTestchoosenEatables = (Vector) allEatables.clone();
            for (int i = 3; i < allEatables.size(); i++)
            {
              myTestchoosenEatables.remove(i);
              myTestchoosenEatables.add(i, null);
            }
            try {
              menu = executeCalculation(myTestchoosenEatables, 1000);
            }
            catch (DiaetplanerException e) {
              System.out.println(e.getMessage());
            }

            System.out.println(
                "Im folgenden Menü sollten die ersten drei Lebensmittel plus" +
                " pro restliche Gruppe \n nur das Lebensmittel " +
                "mit den wenigsten Kalorien angezeigt werden:");
            //Ausgabe Menue
            for (int i = 0; i < allEatables.size(); i++)
              if (menu.get(i) != null) {
                myElement = (Element) menu.get(i);
                System.out.println(myElement.getChild("Name").getText() + " " + i);

              }
            System.out.println("All Eatables.size(): " + allEatables.size());
            System.out.println("myTestchoosenEatables.size(): " + myTestchoosenEatables.size());
            System.out.println("menu.size(): " + menu.size());
            break;

        case 4:  //4.Testfall Element hinzufuegen
                 //Ein Element wird der Liste hinzugefuegt, die Liste gespeichert
                 //und die Liste in das XML-File gespeichert
                 //Anschließend die neue Liste
                 //ausgegeben
                 System.out.println("\n\n\n");
                 System.out.println("*********  TESTFALL 4  ***********");
                 System.out.println("Element der Liste hinzugefuegt");

                 myElement = newEatable("Schinken", 8, "100", "150");
                 try
                 {
                   addEatable(myElement);
                 }
                 catch(DiaetplanerException e)
                 {
                   throw e;
                 }
                 System.out.println("In Lebensmittelliste sollte neues Lebensmittel" +
                                    " Schinken vorhanden sein");
                 for (int i = 0; i < allEatables.size(); i++)
                   if (allEatables.get(i) != null)
                   {
                     myElement = (Element) allEatables.get(i);
                     System.out.println(myElement.getChild("Name").getText() + " " + i);
                   }
                 break;

          case 5: //5.Testfall Element loeschen
                  //Ein Element wird aus der Liste geloescht
                  //und die Liste in das XML-File gespeichert
                  //Anschließend die neue Liste
                  //ausgegeben
                  System.out.println("\n\n\n");
                  System.out.println("*********  TESTFALL 5  ***********");
                  System.out.println("Element wird aus der Liste geloescht");
                  //Das Element Schinken soll wieder aus der Liste geloescht werden
                  myElement = (Element) allEatables.lastElement();
                  try
                  {
                    deleteEatable("Schinken",8);
                    //deleteEatable(myElement);
                  }
                 catch(DiaetplanerException e)
                  {
                     throw e;
                  }
                 System.out.println("In Lebensmittelliste sollte letztes Lebensmittel" +
                                    " Schinken geloescht sein");
                 for (int i = 0; i < allEatables.size(); i++)
                   if (allEatables.get(i) != null)
                   {
                     myElement = (Element) allEatables.get(i);
                     System.out.println(myElement.getChild("Name").getText() + " " + i);
                   }
                 break;

          default: System.out.println("Dieser Testfall existiert nicht !!!");
            break;
    }
  }

  private void printmatrix(Matrix myMatrix)
  {
    try
   {
     String enter = System.getProperty("line.separator");
     File matrixFile = new File("c:\\temp\\matrix.txt");
     FileWriter matrixFileOut = new FileWriter(matrixFile);
     //  matrixout.write("\"" + this.getLpSolvVerzeichnis()+"lp_solve.exe\" -p <"+this.getTempVerzeichnis()+"diaet.lp >"+this.getTempVerzeichnis()+"diaet.out");

     for (int i = 0; i < myMatrix.getAnzahlZeilen(); i++)
     {
       for (int j = 0; j < myMatrix.getAnzahlSpalten(); j++)
       {
         matrixFileOut.write(myMatrix.getElement(i, j) + " ");
       }
       if(i>1 && i<13) //2-12
       {
         matrixFileOut.write("  (" + gruppenName[i-2]+")");
       }
       matrixFileOut.write(enter);
     }
     matrixFileOut.write(enter + enter);

     Element myElement;
     for (int i=0; i<allEatables.size();i++)
    {
       myElement = (Element) allEatables.get(i);
       matrixFileOut.write("x"+(i+1)+" = " + myElement.getChild("Name").getText() + enter);
     }

     matrixFileOut.flush();
     matrixFileOut.close();
     Runtime rt = Runtime.getRuntime();
     Process p = rt.exec("notepad.exe c:\\temp\\matrix.txt");

   }
   catch(Exception e)
       {
         System.out.println(e.getMessage());
       }

   }

}