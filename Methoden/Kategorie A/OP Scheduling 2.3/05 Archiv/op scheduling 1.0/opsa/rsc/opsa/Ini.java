package opsa;

import java.io.*;
/**
 * Title:        Operationssäleplanung
 * Description:  Das Prototyp wurde von Liwei Lu & Arne Bittermann ertellt.
 * Copyright:    Copyright (c) WI8 SS2002
 * Company:      FHKN
 * @author Arne Bittermann, Liwei Lu
 * @version 1.0
 */

public class Ini {

  public Ini() {
  readIni();
  }
  String LPsolvePath="";
  String s1,s2;
  int defaultPeriode=0;
  public boolean readIni()
  {
    try{
      String theLine=null;
      File  inFile=new File("ini.txt");
      FileReader theReader=new FileReader(inFile);
      BufferedReader in=new BufferedReader(theReader);
      if(in.ready()){
        s1= in.readLine();
        s2=in.readLine();
      }
      defaultPeriode=Integer.parseInt(s1.substring(s1.indexOf("=")+1));
      LPsolvePath=s2.substring(s2.indexOf("=")+1);
      in.close();
    }
    catch(Exception e3){
      System.out.println(e3.toString());
    return false;
    }
    return true;
  }
  public String getLPsolvePath()
  {
    return LPsolvePath;
  }
  public int getDefauldPeriode()
  {
    return defaultPeriode;
  }
}