package opsa;
import java.util.Vector;
import java.io.*;
import javax.swing.*;

/**
 * Title:        Operationssäleplanung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FHKN
 * @author Arne Bittermann, Liwei Lu
 * @version 1.0
 */

public class LPData {
  String data="";
  String ziel="min: x1; \n";
  String [] restriction=new String[30];
  int anzRestriction;
  String[][] xMatrix;
  String colon=";\n ";
  int xNr;
  Vector vectorOperation;
  Vector vectorPeriodeSaal;
  Vector vectorErgebnisse=new Vector();
  LPsolve theLPsolve;
  int anzOp=0;
  int anzPer=0;
  String ergebnisString="";
  String lpsolvePath="";
  int defaulfperiod=60;

  public LPData(String lpPath,int defaultperiod) {
    //ini Restriction
    this.lpsolvePath=lpPath;
    this.defaulfperiod=defaulfperiod;
        //readText();
  }// ende Konstruktor
  public boolean initialisierung(Vector in_vectorOperation, Vector in_vectorPeriodeSaal)
  {
    xNr=2;
    anzRestriction=1;
    data="";
    ergebnisString="";
    for(int i=0;i<30;i++){
      restriction[i]=new String();
    }
    vectorOperation=in_vectorOperation;
    vectorPeriodeSaal=in_vectorPeriodeSaal;
    int maxMatrix=0;
    if(vectorPeriodeSaal.size()>vectorOperation.size())
      maxMatrix=vectorPeriodeSaal.size()+1;
    else
      maxMatrix=vectorOperation.size()+1;
    //ini xMatrix
    xMatrix=new String [maxMatrix][maxMatrix];
    for(int r=0;r<maxMatrix;r++){
      for(int c=0;c<maxMatrix;c++){
        xMatrix[r][c]=new String("x");
      }
    }
    /*System.out.println("xMatrix");
      for(int i=0;i<xMatrix.length;i++)
      {
        for(int j=0;j<xMatrix[i].length;j++)
        {
          System.out.println(i+""+j+":"+xMatrix[i][j]);
        }
      }*/
      return true;
  }
  public void parseData(){
    try{
      boolean IsRestriction=false;
      boolean moreX=false;
      //set xMatrix----------------------------------------------------
      for(int i=0;i<vectorOperation.size();i++)
      {
        for(int j=0;j<(vectorPeriodeSaal.size()+1);j++)
        {
          if(((Operation) vectorOperation.elementAt(i)!=null)
            && (j>=((Operation) vectorOperation.elementAt(i)).getAnfang())
            && (j<=((Operation) vectorOperation.elementAt(i)).getEnde()))
          {
              xMatrix[i+1][j]+=xNr++;
          }
        }
      }
      System.out.println("xMatrix");
      for(int i=0;i<xMatrix.length;i++)
      {
        for(int j=0;j<xMatrix[i].length;j++)
        {
          System.out.println(i+" "+j+" :"+xMatrix[i][j]);
        }
      }
      //erstelle Restriktionen: Operationen---------------------------------------
      //zeile
      for(int i=0;i<xMatrix.length;i++)
      {
        //spalte
        for(int j=0;j<(int)xMatrix[i].length;j++)
        {
          if((xMatrix[i][j].length())>1)
          {
            if(moreX==false)
            {
              restriction[anzRestriction]+=xMatrix[i][j];
              moreX=true;
            }
            else
              restriction[anzRestriction]+="+"+xMatrix[i][j];
            IsRestriction=true;
          }
        }
        moreX=false;
        if(IsRestriction==true)
        {
            restriction[anzRestriction]+="="+((Operation) vectorOperation.elementAt(i-1)).getDauer();
            IsRestriction=false;
            anzRestriction++;
        }
      }
      //erstelle Restriktion: Periode---------------------------------------------------
      //spaltweiser
      for(int i=0;i<vectorPeriodeSaal.size();i++)
      {
        for(int j=1;j<xMatrix.length;j++)
        {
          if((xMatrix[j][i+1].length())>1)
          {
            if(moreX==false)
            {
              restriction[anzRestriction]+=xMatrix[j][i+1];
              moreX=true;
            }
            else
              restriction[anzRestriction]+="+"+xMatrix[j][i+1];
            IsRestriction=true;
          }
        }
        moreX=false;
        if(IsRestriction==true)
        {
          restriction[anzRestriction]="-"+((periodSaal) vectorPeriodeSaal.elementAt(i)).getAnzSaal()
                                      +"x1+"+restriction[anzRestriction];
          restriction[anzRestriction]+="<=0";
          anzRestriction++;
          IsRestriction=false;
        }
      }
      //erstelle Restriktionen: <=Periode
      for(int i=0;i<vectorPeriodeSaal.size();i++)
      {
        for(int j=1;j<xMatrix.length;j++)
        {
          if((xMatrix[j][i+1].length())>1)
          {
            if(moreX==false)
            {
              restriction[anzRestriction]+=xMatrix[j][i+1];
              moreX=true;
            }
            else
              restriction[anzRestriction]+="+"+xMatrix[j][i+1];
            IsRestriction=true;
          }
        }
        moreX=false;
        if(IsRestriction==true)
        {
          restriction[anzRestriction]+="<="+(((periodSaal) vectorPeriodeSaal.elementAt(i)).getAnzSaal())*defaulfperiod;
          anzRestriction++;
          IsRestriction=false;
        }
      }
      //erstelle data
      data=ziel;
      for(int i=1;i<anzRestriction;i++)
        data+=restriction[i]+colon;
      System.out.println(data);
      //this.setVectorErgebnisse();
    }
    catch(Exception e2){
      System.out.println(e2.toString()+"!!!!!!!!!!!!!");
    }
    //ende setData
    //****************************************************
    //erzeuge ein Objekt von LPsolve
    //hole die Ergebnisse
    theLPsolve = new LPsolve(data,lpsolvePath);
    this.setVectorErgebnisse();
  }
  public void setVectorErgebnisse()
  {
    if(vectorErgebnisse.size()!=0) vectorErgebnisse.removeAllElements();
    vectorErgebnisse=theLPsolve.getMyStringVector();
    this.setErgebnisseString();
  }
  public String getErgebnisse()
  {
    return ergebnisString;
  }
  public void setErgebnisseString()
  {
    int ergebnisNr=1;
    ergebnisString="Optimale Auslastung pro Saal ist: "+(String)vectorErgebnisse.elementAt(ergebnisNr++)
              +"\n\n"+"Übersicht von Periodenr. Operationsnr.und Zeitaufwand(min)\n";
    //reset xMatrix
    //set xMatrix----------------------------------------------------
      for(int i=0;i<vectorOperation.size();i++)
      {
        for(int j=0;j<(vectorPeriodeSaal.size()+1);j++)
        {
          if(((Operation) vectorOperation.elementAt(i)!=null)
            && (j>=((Operation) vectorOperation.elementAt(i)).getAnfang())
            && (j<=((Operation) vectorOperation.elementAt(i)).getEnde()))
          {
              xMatrix[i+1][j]=(String)vectorErgebnisse.elementAt(ergebnisNr++);
          }
        }
      }
    //for(int i=0;i<vectorPeriodeSaal.size();i++)
    for(int i=1;i<xMatrix.length;i++)//spalt
    {
      boolean moreX=false;
      for(int j=1;j<xMatrix.length;j++) // Zeile
      {
        if((xMatrix[j][i].length())>1)
        {
          if(moreX==false)
          {
            ergebnisString+="Periode "+(i)+": Op_Nr."+(j)+"-> "+xMatrix[j][i];
            moreX=true;
          }
          else
            ergebnisString+="  "+": Op_Nr."+j+"-> "+xMatrix[j][i];
        }
      }
      moreX=false;
      ergebnisString+="\n";
    }
  }
  public void readText(){
    try{
      String theLine=null;
      File  inFile=new File("lpsolve.out");
      FileReader theReader=new FileReader(inFile);
      BufferedReader in=new BufferedReader(theReader);
      System.out.println("teste, eine Datei einzulesen.................");
      while(in.ready()){
        theLine = in.readLine();
        System.out.println(theLine);
      }
      in.close();
    }
    catch(Exception e3){
      System.out.println(e3.toString());
    }
  }
}