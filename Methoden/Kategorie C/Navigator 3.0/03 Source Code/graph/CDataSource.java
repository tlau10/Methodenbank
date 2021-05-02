package graph;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CDataSource {

  boolean dirty = false;  // True means modified text.

  Frame1 framehlp;
  ElementController ec1;

  Route myRoute;

  char impzeichen[] = {':', 'K', 'E', ';', ',', '\n', 'x', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '_', ' '};
  boolean attention = false;
  boolean att = true;

  Vector tmplist = new Vector();
  Vector sortlist = new Vector();


  String tmpKnotenDaten = ""; //für das auslesen aus dem element controller zum zwischenspeichern
  String tmpKantenDaten = ""; //für das auslesen aus dem element controller zum zwischenspeichern
  String saveFileString = ""; //der string, der in die datei geschrieben wird
  String DateiKnoten = "#Die Orte und deren Bezeichnungen \n"; //der anfang der Knoten-Datei für den solver
  String DateiKnotenEnde = "#Ende der Verbindungen. Achtung dieser Zeile nicht loeschen "; //das ende der knoten-datei für den solver
  String DateiKanten = "#Die Orte und deren Bezeichnungen sind in der Datei DBorte.\n#Verbindungen:	\n"; //der anfang der kanten-datei für den solver
  String DateiKantenEnde = "#Ende der Verbindungen. Achtung dieser Zeile nicht loeschen"; //das ende der kanten-datei für den solver
  String ergebnisString = ""; //beinhaltet den String aus der navigat.out datei

  String KnotenNeuZeile;
  String DateiStringAlt;
  String DateiStringNeu;

  String DateiInhaltSaveFile;
  String TmpDateiKnoten = "";



  public CDataSource(){
  }

  public CDataSource(Frame1 tmpDataStruct) {
      framehlp = tmpDataStruct;
      ec1 = tmpDataStruct.jPanel1;

  }



  public CDataSource(Route tmpRoute, Frame1 tmpDataStruct) {
      myRoute = tmpRoute;
      framehlp = tmpDataStruct;
      ec1 = tmpDataStruct.jPanel1;
  }



  /*******************************************************************
 * LOAD - Methode
 */

    void openFile(String tmpfileName)
    {
      framehlp.jPanel1.removeAllElments();
      framehlp.repaint();
      try
      {
        String tmpAKnoten = "";
        String tmpCharAKnoten = "";
        String tmpBKnoten = "";
        String tmpCharBKnoten = "";

        int tmpAB = 0;
        String tmpCharAB = "";
        String tmpStringAB = "";
        int tmpBA = 0;
        String tmpCharBA = "";
        String tmpStringBA = "";

        char e[] = new char[1];
        attention = false;
        boolean att = true;
        int tmpXPos = 0;
        String tmpCharXPos = "";
        String tmpStringXPos = "";
        int tmpYPos = 0;
        String tmpCharYPos = "";
        String tmpStringYPos = "";
        String tmpName = "";
        String tmpCharName = "";
        int tmpKuerzel = 0;
        String tmpStringKuerzel = "";
        String tmpCharKuerzel = "";
        String tmpString = " ";
        char c = tmpString.charAt(0);

        File file = new File(tmpfileName);
        // FileReader zum Lesen aus Datei
        FileReader fr = new FileReader(file);
        int size = (int)file.length();
                  
        // Der String, der am Ende ausgegeben wird
        String gelesen;
                   
        // char-Array als Puffer fuer das Lesen. Die
        // Laenge ergibt sich aus der Groesse der Datei
        char[] data = new char[(int) file.length()];
                   
        // Lesevorgang
        fr.read(data);
                   
        // Umwandlung des char-Arrays in einen String
        DateiStringAlt = new String(data);
                   
        //Ausgabe des Strings
        System.out.println(DateiStringAlt);
                   
        // Ressourcen freigeben
        fr.close();

//        int size = (int)file.length();
//        int chars_read = 0;
        int i = 0;
//        FileReader in = new FileReader(file);
//        char[] data = new char[size];
//
//        while(in.ready())
//        {
//          chars_read += in.read(data, chars_read, size - chars_read);
//        }
//
//        in.close();

//        DateiStringAlt = new String(data, 0, chars_read);

        if (data[i] != impzeichen[0])
        {
          System.out.println("keine gültige Datei ausgewählt!");
        }

        else
        {
        for (i=0;i<size;i++)
        {
          if (data[i] == impzeichen[0])
          {
            attention = true;
          }
          if ((data[i] == impzeichen[1])&&(attention==true)) //Knoten-Zweig
          {
            i++;
            i++;
            while (att==true) //Schleife fuer die Knotenzeile
            {
              if (data[i] == impzeichen[6]) // "x"
              {
                i++;
                do
                {
                  if (data[i]==impzeichen[7]||data[i]==impzeichen[8]||data[i]==impzeichen[9]||data[i]==impzeichen[10]||data[i]==impzeichen[11]||data[i]==impzeichen[12]||data[i]==impzeichen[13]||data[i]==impzeichen[14]||data[i]==impzeichen[15]||data[i]==impzeichen[16])
                  {
                    e[0]=data[i];
                    tmpCharKuerzel = new String (e);
                    tmpStringKuerzel = tmpStringKuerzel+tmpCharKuerzel;
                    i++;
                  }
                }
                while (data[i] != impzeichen[4]);

                tmpKuerzel = Integer.parseInt(tmpStringKuerzel.trim());
              }
              if (data[i]==impzeichen[4])
              {
                i++;
                do
                {
                  //i++;
                  e[0]=data[i];
                  tmpCharName = new String (e);
                  tmpName = tmpName+tmpCharName;
                  i++;
                }
                while (data[i] != impzeichen[3]);
              }
              i++;
              do
              {
                if (data[i]==impzeichen[7]||data[i]==impzeichen[8]||data[i]==impzeichen[9]||data[i]==impzeichen[10]||data[i]==impzeichen[11]||data[i]==impzeichen[12]||data[i]==impzeichen[13]||data[i]==impzeichen[14]||data[i]==impzeichen[15]||data[i]==impzeichen[16])
                {
                  e[0]=data[i];
                  tmpCharXPos = new String (e);
                  tmpStringXPos = tmpStringXPos+tmpCharXPos;
                  i++;
                }
              }
              while(data[i] != impzeichen[3]);
              tmpXPos = Integer.parseInt(tmpStringXPos.trim());

              i++;
              do
              {
                if (data[i]==impzeichen[7]||data[i]==impzeichen[8]||data[i]==impzeichen[9]||data[i]==impzeichen[10]||data[i]==impzeichen[11]||data[i]==impzeichen[12]||data[i]==impzeichen[13]||data[i]==impzeichen[14]||data[i]==impzeichen[15]||data[i]==impzeichen[16])
                {
                  e[0]=data[i];
                  tmpCharYPos = new String (e);
                  tmpStringYPos = tmpStringYPos+tmpCharYPos;
                  i++;
                }
              }
              while(data[i] != impzeichen[3]);
              tmpYPos = Integer.parseInt(tmpStringYPos.trim());

              framehlp.jPanel1.add(new Knoten(tmpXPos,tmpYPos,tmpName,tmpKuerzel,framehlp.jPanel1));
              i++;
              framehlp.repaint();
              att=false;

            } // ende while schleife

            att=true;
            attention = false;

            tmpXPos = 0;
            tmpCharXPos = "";
            tmpStringXPos = "";
            tmpYPos = 0;
            tmpCharYPos = "";
            tmpStringYPos = "";
            tmpName = "";
            tmpCharName = "";
            tmpKuerzel = 0;
            tmpStringKuerzel = "";
            tmpCharKuerzel = "";

          } //ende Knoten-Zweig

          if ((data[i] == impzeichen[2])&&(attention==true)) //Kanten-Zweig
          {
            i++;
            i++;
            while (att==true) //Schleife fuer die Kantenzeile
            {
              if (data[i] == impzeichen[6]) // erstes x: A-Knoten
              {
                do
                {
                  if (data[i]!=impzeichen[3])
                  {
                    e[0]=data[i];
                    tmpCharAKnoten = new String (e);
                    tmpAKnoten = tmpAKnoten+tmpCharAKnoten;
                    i++;
                  }
                }
                while (data[i] != impzeichen[3]);
              }
              i++;

              if (data[i] == impzeichen[6]) // zweites x: B-Knoten
              {
                do
                {
                  if (data[i]!=impzeichen[3])
                  {
                    e[0]=data[i];
                    tmpCharBKnoten = new String (e);
                    tmpBKnoten = tmpBKnoten+tmpCharBKnoten;
                    i++;
                  }
                }
                while (data[i] != impzeichen[3]);
              }

              if (data[i]==impzeichen[3]) //AB-gewichtung
              {
                i++;
                do
                {
                  if (data[i]==impzeichen[7]||data[i]==impzeichen[8]||data[i]==impzeichen[9]||data[i]==impzeichen[10]||data[i]==impzeichen[11]||data[i]==impzeichen[12]||data[i]==impzeichen[13]||data[i]==impzeichen[14]||data[i]==impzeichen[15]||data[i]==impzeichen[16])
                  {
                   e[0]=data[i];
                   tmpCharAB = new String (e);
                   tmpStringAB = tmpStringAB+tmpCharAB;
                   i++;
                  }
                }
                while(data[i] != impzeichen[3]);
                tmpAB = Integer.parseInt(tmpStringAB.trim());
              }

              if (data[i]==impzeichen[3]) //BA-gewichtung
              {
                i++;
                do
                {
                  if (data[i]==impzeichen[7]||data[i]==impzeichen[8]||data[i]==impzeichen[9]||data[i]==impzeichen[10]||data[i]==impzeichen[11]||data[i]==impzeichen[12]||data[i]==impzeichen[13]||data[i]==impzeichen[14]||data[i]==impzeichen[15]||data[i]==impzeichen[16])
                  {
                   e[0]=data[i];
                   tmpCharBA = new String (e);
                   tmpStringBA = tmpStringBA+tmpCharBA;
                   i++;
                  }
                }
                while(data[i] != impzeichen[3]);
                tmpBA = Integer.parseInt(tmpStringBA.trim());
              }

              framehlp.jPanel1.add(new Kante(framehlp.jPanel1.findById(tmpAKnoten),framehlp.jPanel1.findById(tmpBKnoten),tmpAB,tmpBA));

              i++;
              //i=i+2;
              framehlp.repaint();
              att=false;

              if (i>size)
              {
                i=i-2;
              }

            } //ende while schleife kantenzeile

            att=true;
            attention = false;

            tmpAKnoten = "";
            tmpCharAKnoten = "";
            tmpBKnoten = "";
            tmpCharBKnoten = "";

            tmpAB = 0;
            tmpCharAB = "";
            tmpStringAB = "";
            tmpBA = 0;
            tmpCharBA = "";
            tmpStringBA = "";


          } //ende Kanten-Zweig

        } //ende for-schleife
        } //ende der if-schleife für die Dateiprüfung
      }
      catch (IOException e)
      {
        System.out.println("Fehler beim Laden");
      }
    }

/*********************************************************************
 *  SAVE - Methode
 */

    boolean saveFile(String tmpcurrFileName)
    {
      File file = new File (tmpcurrFileName);
      saveFileString = "";

      try
      {
        FileWriter out = new FileWriter(file);
        out.write("");
        out.close();
      }
      catch (IOException ex1)
      {
      }

      int elemCount = framehlp.jPanel1.getElementCount();
      // auslesen aus element-controller
      for (int i=0; i<elemCount;i++)
      {
        if ( ((Element)framehlp.jPanel1.getElement(i)).getType() == 1) //knoten
        {
          if (i<(elemCount-1))
          {
          tmpKnotenDaten = ":K;x"+((Knoten)framehlp.jPanel1.getElement(i)).getId()+","+((Knoten)framehlp.jPanel1.getElement(i)).getName()+";"+((Knoten)framehlp.jPanel1.getElement(i)).getX()+";"+((Knoten)framehlp.jPanel1.getElement(i)).getY()+";\n";
          saveFileString = saveFileString+tmpKnotenDaten;
          }

          else
          {
            tmpKnotenDaten = ":K;x"+((Knoten)framehlp.jPanel1.getElement(i)).getId()+","+((Knoten)framehlp.jPanel1.getElement(i)).getName()+";"+((Knoten)framehlp.jPanel1.getElement(i)).getX()+";"+((Knoten)framehlp.jPanel1.getElement(i)).getY()+";";
            saveFileString = saveFileString+tmpKnotenDaten;
          }
        }

        else //kante
        {
          if (i<(elemCount-1))
          {
            tmpKantenDaten = ":E;x"+((Kante)framehlp.jPanel1.getElement(i)).getA().getId()+";x"+((Kante)framehlp.jPanel1.getElement(i)).getB().getId()+";"+((Kante)framehlp.jPanel1.getElement(i)).getG1()+";"+((Kante)framehlp.jPanel1.getElement(i)).getG2()+";\n";
            saveFileString = saveFileString+tmpKantenDaten;
          }

          else
          {
            tmpKantenDaten = ":E;x"+((Kante)framehlp.jPanel1.getElement(i)).getA().getId()+";x"+((Kante)framehlp.jPanel1.getElement(i)).getB().getId()+";"+((Kante)framehlp.jPanel1.getElement(i)).getG1()+";"+((Kante)framehlp.jPanel1.getElement(i)).getG2()+";";
            saveFileString = saveFileString+tmpKantenDaten;
          }
        }


      }

      // in file schreiben
      try
      {
        FileWriter out = new FileWriter(file);
        out.write(saveFileString);
        out.close();
        return true;
      }
      catch (IOException e)
      {
      }
      return false;
    }

/*********************************************************************
 * vorbBearbeitung - Methode
 */

    void vorbBearbeitung()
    {
      File tmpKnotenTab = new File ("c:/temp/DBorte.asc");
      File tmpKantenTab = new File ("c:/temp/DBverbindungen.asc");

      DateiKnoten = "#Die Orte und deren Bezeichnungen \n"; //der anfang der Knoten-Datei für den solver
      DateiKnotenEnde = "#Ende der Verbindungen. Achtung dieser Zeile nicht loeschen "; //das ende der knoten-datei für den solver
      DateiKanten = "#Die Orte und deren Bezeichnungen sind in der Datei DBorte.\n#Verbindungen:	\n"; //der anfang der kanten-datei für den solver
      DateiKantenEnde = "#Ende der Verbindungen. Achtung dieser Zeile nicht loeschen"; //das ende der kanten-datei für den solver

      int elemCounter = framehlp.jPanel1.getElementCount();

      for (int i=0; i<elemCounter; i++)
      {
        if ( ((Element)framehlp.jPanel1.getElement(i)).getType() == 1) // Knoten
        {
          DateiKnoten=DateiKnoten+"x"+((Knoten)framehlp.jPanel1.getElement(i)).getId()+" : "+((Knoten)framehlp.jPanel1.getElement(i)).getName()+"\n";
        }
        else //Kante
        {
          if (((Kante)framehlp.jPanel1.getElement(i)).getG1()!= 0)
          {
            DateiKanten=DateiKanten+"x"+((Kante)framehlp.jPanel1.getElement(i)).getA().getId()+"_x"+((Kante)framehlp.jPanel1.getElement(i)).getB().getId()+" : "+((Kante)framehlp.jPanel1.getElement(i)).getG1()+"\n";
          }
          if (((Kante)framehlp.jPanel1.getElement(i)).getG2()!= 0)
          {
            DateiKanten=DateiKanten+"x"+((Kante)framehlp.jPanel1.getElement(i)).getB().getId()+"_x"+((Kante)framehlp.jPanel1.getElement(i)).getA().getId()+" : "+((Kante)framehlp.jPanel1.getElement(i)).getG2()+"\n";
          }
        }
      }

      DateiKnoten=DateiKnoten+DateiKnotenEnde;

      DateiKanten=DateiKanten+DateiKantenEnde;

      try
      {
        FileWriter outKnoten = new FileWriter(tmpKnotenTab);
        outKnoten.write(DateiKnoten);
        outKnoten.close();

        FileWriter outKanten = new FileWriter(tmpKantenTab);
        outKanten.write(DateiKanten);
        outKanten.close();
      }
      catch (IOException exio)
      {
      }
    }


/**********************************************************************
 * Ausgabe Berechnung - Methode
 */

  boolean ausgabeBerechnung(String myTmpID)
  {
    char f[] = new char[1];
    String tmpAKnoten = "";
    String tmp2AKnoten = "";
    String tmpBKnoten = "";
    String tmp2BKnoten = "";

    int sackgasse = 0;

    tmplist.removeAllElements() ;

    try
    {
      File ergfile = new File("c:/temp/navigat.out");
      int ergsize = (int)ergfile.length();
      int ergchars_read = 0;
      int i = 0;
      FileReader in_erg = new FileReader(ergfile);
      char[] ergdata = new char[ergsize];

        while(in_erg.ready())
        {
          ergchars_read += in_erg.read(ergdata, ergchars_read, ergsize - ergchars_read);
        }
      ergebnisString = new String(ergdata, 0, ergchars_read);
      ec1.resetRoute();

      for (i=0; i<ergsize; i++) //for-schleife, die durch das ergebnisfile läuft
      {
        tmpAKnoten="";
        tmp2AKnoten="";
        tmpBKnoten="";
        tmp2BKnoten="";


        if (ergdata[i]==impzeichen[6])
        {
          do // erster knoten in der ergebnis datei
          {
            f[0]=ergdata[i];
            tmp2AKnoten = new String (f);
            tmpAKnoten=tmpAKnoten+tmp2AKnoten;
            i++;
          }
          while (ergdata[i]!=impzeichen[17]);
          i++;
          do // zweiter knoten in der ergebnis datei
          {
            f[0]=ergdata[i];
            tmp2BKnoten = new String (f);
            tmpBKnoten=tmpBKnoten+tmp2BKnoten;
            i++;
          }
          while (ergdata[i]!=impzeichen[18]);

          while (ergdata[i]==impzeichen[18])
          {

            i++;
          }
          if (ergdata[i]==impzeichen[8])
          {
            sackgasse++;
            ec1.setRouteKnoten(ec1.findById(tmpAKnoten));
            ec1.setRouteKnoten(ec1.findById(tmpBKnoten));

            ec1.setRouteKante(ec1.findKante(ec1.findById(tmpAKnoten),ec1.findById(tmpBKnoten)));
            this.addToList(ec1.findKante(ec1.findById(tmpAKnoten),ec1.findById(tmpBKnoten)));
            ec1.repaint();
          }

        } // ende der if-abfragen, auslesen einer zeile aus der ergebnis datei
      }// ende for schleife


if (sackgasse != 0)
{
 this.ausgabe(myTmpID);
 return true;
}


    }
    catch (IOException exi)
    {
    }
  return false;
  }

/**********************************************************************
 * class end
 */


 private void addToList(Kante mKante) {
  tmplist.add(mKante);

 }

 public void ausgabe(String myID) {

      String ausgabeString = "";
      String ausgabeStringStriche = "\n-----------------------------------\n";
      String ausgabeStringEnde = "die Wegstrecke beträgt: ";


      Kante mytempkante;
      int mySumme=0;
      Knoten current, old;
      Kante currentKante;

      current = this.ec1.findById(myID);
      currentKante = findFirstKante(current);

      sortlist.removeAllElements();

      //sortlist aufbauen
      sortlist.add(currentKante);
      for (int i=0; i<tmplist.size()-1; i++ ){
        currentKante = findNextKante(current, currentKante);
        current = getNextNode(current, currentKante);
        sortlist.add(currentKante);

      }
      //sortlist sortieren
      current = this.ec1.findById(myID);
      for (int i=0; i<sortlist.size(); i++ ){

        if ( ((Kante)sortlist.get(i)).a == current )  {
          current = ((Kante)sortlist.get(i)).b;
          ausgabeString=ausgabeString+"Weg: " + ((Kante)sortlist.get(i)).a.getName() + " -> " + ((Kante)sortlist.get(i)).b.getName() + " länge: " + ((Kante)sortlist.get(i)).g1 + "\n";
          mySumme=mySumme+((Kante)sortlist.get(i)).g1;
        }
        else {
          current = ((Kante)sortlist.get(i)).a;
          ausgabeString=ausgabeString+"Weg: " + ((Kante)sortlist.get(i)).b.getName() + " -> " + ((Kante)sortlist.get(i)).a.getName() + " länge: " + ((Kante)sortlist.get(i)).g2 + "\n";
          mySumme=mySumme+((Kante)sortlist.get(i)).g2;
        }

      }

    ausgabeString=ausgabeString+ausgabeStringStriche+ausgabeStringEnde+mySumme+" km.";

    myRoute.jTextArea1.setText(ausgabeString);


    System.out.println(sortlist.size() );

 }

  private Kante findNextKante(Knoten k1,Kante kante) {
    Knoten tmp;
    tmp = getNextNode(k1,kante);
    for (int i=0; i<tmplist.size(); i++){
      if (tmplist.get(i) != kante) {
        if ((tmp==((Kante)tmplist.get(i)).a) || (tmp==((Kante)tmplist.get(i)).b)) {
          return (Kante)tmplist.get(i);
        }
      }
    }
  return null;
  }

    private Kante findFirstKante(Knoten k1) {
    for (int i=0; i<tmplist.size(); i++){
        if ((k1==((Kante)tmplist.get(i)).a) || (k1==((Kante)tmplist.get(i)).b)) {
          return (Kante)tmplist.get(i);
        }
    }
  return null;
  }

  private Knoten getNextNode(Knoten k1, Kante kante) {
    if (kante.a == k1) {
      return kante.b;
    }
    else {
      return kante.a;
    }
  }




}