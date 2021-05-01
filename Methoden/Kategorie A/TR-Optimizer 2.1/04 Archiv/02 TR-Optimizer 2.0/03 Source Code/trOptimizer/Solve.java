package trOptimizer;

/**
 * <p>Title: TR-Optimizer</p>
 * <p>Description: TR-Optimizer ist eine Weiterentwicklung von TransOp</p>
 * <p>Copyright: Copyright (c) 2004, Stefanie Brauchle, Konstanze Czierpka</p>
 * <p>Company: FH-Konstanz</p>
 * @author Stefanie Brauchle, Konstanze Czierpka
 * @version 2.0
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import java.util.Vector;

public class Solve {
  private Vector empfaengerliste = new Vector();
  private Vector produzentenliste = new Vector();
  private Vector streckenliste = new Vector();
  private Vector transporterliste = new Vector();
  private Vector erg = new Vector();
  private Vector varVector = new Vector();
  private int solvertyp=1;

  private String _ERROR_MSG;
  private String _WARNING_MSG;
  private StringBuffer lpSolveOutput = new StringBuffer();
  private StringBuffer MopsOutput = new StringBuffer();
  private Vector myStringVector = new Vector();
  String dataSet = "";
  String MopsData = "";

// set- und get-Methoden
  public Vector getEmpfaengerliste() {
    return empfaengerliste;
  }

  public void setEmpfaengerliste( Vector empfaengerliste ) {
    this.empfaengerliste = empfaengerliste;
  }

  public Vector getProduzentenliste() {
    return produzentenliste;
  }

  public void setProduzentenliste( Vector produzentenliste ) {
    this.produzentenliste = produzentenliste;
  }

  public Vector getStreckenliste() {
    return streckenliste;
  }

  public void setStreckenliste( Vector streckenliste ) {
    this.streckenliste = streckenliste;
  }

  public Vector getTransporterliste() {
    return transporterliste;
  }

  public void setTransporterliste( Vector transporterliste ) {
    this.transporterliste = transporterliste;
  }

// diese Funktion überprüft ob die Liefermenge mit der empfangenen Menge übereinstimmt
// wenn dies nicht so ist wird ein virtueller Empfänger/ Produzent eingeführt
  private void korrigiereDaten() {
    int prodMenge = 0;
    int empfMenge = 0;

    for( int i = 0; i < produzentenliste.size(); i++ ) {
      prodMenge += ( ( Produzent )produzentenliste.get( i ) ).getLieferMenge();
    }
    for( int i = 0; i < empfaengerliste.size(); i++ ) {
      empfMenge += ( ( Empfaenger )empfaengerliste.get( i ) ).getBenoetigteMenge();
    }
    if( prodMenge > empfMenge ) {
      Empfaenger virtEmpf = new Empfaenger( -50, -50, 0 );
      virtEmpf.setName( "Virtueller Empfaenger" );
      virtEmpf.setBenoetigteMenge( prodMenge - empfMenge );
      empfaengerliste.add( virtEmpf );

      for( int i = 0; i < produzentenliste.size(); i++ ) {
        Strecke virtStrecke = new Strecke( -50, -50, -50, -50, 0 );
        virtStrecke.setEntfernung( 0 );
        virtStrecke.setQuelle( ( ( Produzent )produzentenliste.get( i ) ).getId() );
        virtStrecke.setZiel( virtEmpf.getId() );
        streckenliste.add( virtStrecke );
      }
    }
    if( prodMenge < empfMenge ) {
      Produzent virtProd = new Produzent( -50, -50, 0 );
      virtProd.setName( "Virtueller Produzent" );
      virtProd.setLieferMenge( empfMenge-prodMenge );
      produzentenliste.add( virtProd );

      for( int i = 0; i < empfaengerliste.size(); i++ ) {
        Strecke virtStrecke = new Strecke( -50, -50, -50, -50, 0 );
        virtStrecke.setEntfernung( 0 );
        virtStrecke.setQuelle( virtProd.getId() ); //Quelle immer Prod., Ziel immer Epmf.
        virtStrecke.setZiel( ( ( Empfaenger )empfaengerliste.get( i ) ).getId() );
        streckenliste.add( virtStrecke );
      }
    }
  }

// Im Konstruktor werden gleich alle benötigten Funktionen aufgerufen damit
// sofort eine Lösung vorliegt welche durch die "get-Methode" abgeholt werden kann
  public Solve( int solvertyp, Vector empfaengerliste, Vector produzentenliste,
      Vector streckenliste, Vector transporterliste ) {
    this.empfaengerliste = empfaengerliste;
    this.produzentenliste = produzentenliste;
    this.streckenliste = streckenliste;
    this.transporterliste = transporterliste;
    this.solvertyp=solvertyp;

    korrigiereDaten();
    String dataSet = null;
    String MopsData ="";


    if(solvertyp==1)
    {
      dataSet = this.createFile();
      calculateLPsolve( dataSet );
      parseData();
    }
    if(solvertyp==2)
    {
      this.createFileWeid();
      if( calculateWeidenauer() == true )
        parseDataWeid();
    }
    if(solvertyp==3)
    {
      this.createMopsFile();
      MopsData = createMopsFormat();
      if( calculateMops( MopsData ) == true )
        ;
      parseMopsData();
    }
    printVector();
  }

/////////////////////////////////////////////////////////////////////////////
// In dieser Funktion wird das komplette LP-Modell für Mops erstellt!
  private void createMopsFile()
  {
    String dataSet = "";

// hier wird die Zielfunktion erstellt!
 String zf = null;
 int koeffizient = 1;
 zf = "ZF ";
 for( int i = 0; i < streckenliste.size(); i++ ) {
   koeffizient++;
 }
 for( int i = 0; i < transporterliste.size(); i++ ) {
   for( int j = 0; j < streckenliste.size(); j++ ) {
     zf += " + " +
         ( ( ( Strecke )streckenliste.get( j ) ).getEntfernung() *
         ( ( Transporter )transporterliste.get( i ) ).getKosten_pro_km() ) +
         " x " + koeffizient;
     koeffizient++;
   }
 }
 zf += " ;";
 dataSet += zf + "\n";
 int anzVariablen = koeffizient;
 koeffizient = 1;

// Ab hier werden die Restriktionen zusammengebaut!
 int restNr = 1;
 String rest = "";

 // Restriktionen für die Produzenten
 for( int i = 0; i < produzentenliste.size(); i++ )
 {
   rest = "R " + restNr + " :  ";
   for( int j = 0; j < streckenliste.size(); j++ )
   {
     if( ( ( ( Strecke )streckenliste.get( j ) ).getQuelle() ) == ( ( ( Produzent )produzentenliste.get( i ) ).getId() ) ) {
       if( koeffizient == 1 )
       {
         rest += " 1 x " + koeffizient;
       }
       else {
         rest += " + 1 x " + koeffizient;
       }
     }
     koeffizient++;
   }
   for( int k = koeffizient; k < anzVariablen; k++ ) {
     koeffizient++;
   }
   rest += " = " + ( ( Produzent )produzentenliste.get( i ) ).getLieferMenge() + " ;";
   dataSet += rest + "\n";
   koeffizient = 1;
   restNr++;
 }

 // Restriktionen für die Empfänger
 for( int i = 0; i < empfaengerliste.size(); i++ )
 {
   rest = "R " + restNr + " :  ";
   for( int j = 0; j < streckenliste.size(); j++ )
   {
     if( ( ( Strecke )streckenliste.get( j ) ).getZiel() == ( ( Empfaenger )empfaengerliste.get( i ) ).getId() ) {
       if( koeffizient == 1 )
       {
         rest += " 1 x " + koeffizient;
       }
       else
       {
         rest += " + 1 x " + koeffizient;
       }
     }
     koeffizient++;
   }
   for( int k = koeffizient; k < anzVariablen; k++ ) {
     koeffizient++;
   }
   rest += " = " + ( ( Empfaenger )empfaengerliste.get( i ) ).getBenoetigteMenge() + " ;";
   dataSet += rest + "\n";
   koeffizient = 1;
   restNr++;
 }

 // Restliche Restriktionen
 int firstPosition = 0;
 for( int i = 0; i < streckenliste.size(); i++ )
 {
   rest = "R " + restNr + " :  ";
   for( int j = 0; j < ( anzVariablen / ( transporterliste.size() + 1 ) ); j++ ) {
     if( j == firstPosition )
     {
       if( j == 0 )
       {
         rest += " 1 x " + koeffizient;
       }
       else
       {
         rest += " + 1 x " + koeffizient;
       }
     }
     koeffizient++;
   }
   for( int k = 0; k < transporterliste.size(); k++ )
   {
     for( int l = 0; l < streckenliste.size(); l++ )
     {
       if( l == firstPosition )
       {
         rest += " - " + ( ( Transporter )transporterliste.get( k ) ).getKapazitaet() + " x " +
             koeffizient;
       }
       koeffizient++;
     }
   }
   rest += " < 0 ;";
   dataSet += rest + "\n";
   firstPosition++;
   restNr++;
   koeffizient = 1;
 }
 try
 {
   java.io.FileWriter fileWriter6 = new java.io.FileWriter( "C:\\temp\\MopsDatei.mps" );
   java.io.PrintWriter batchWriter6 = new java.io.PrintWriter( fileWriter6 );
   batchWriter6.println(dataSet);
   batchWriter6.flush();
   batchWriter6.close();
 }
 catch( IOException e )
 {
      _ERROR_MSG += "\nIO error: " + e;
      //myStringVector.add( 0, "error" );
      //myStringVector.add( 1, _ERROR_MSG );
 }
 return ;
 }

 private String createMopsFormat()
 {
    int Anzahl_Zeilen = 0;
    int savekey=0;
    int zaehler = 0;
    int int_token;
    String zeile = "";
    String Input = "";
    String token = "";
    String int_str = "";
    double tmp;
    String ZielFunktion="";
    String ColumnsFunktion="";
    String R="";
    String H="";
    String [][] columnsmatrix = new String [300][8];
    for(int i=0;i<300;i++)
    {
      for (int j=0; j<8; j++)
      {
        columnsmatrix [i][j]="q";
      }
    }
      String columnsFunktion="";
      String rhsFunktion="";
      int a=0;
      int i=0;
      int ber=0;

    // Daten einlesen
    try
    {
      java.io.FileReader mopsOutReader = new java.io.FileReader("C:\\temp\\MopsDatei.mps");
      java.io.BufferedReader mopsreader = new java.io.BufferedReader(mopsOutReader);
      java.io.FileWriter fileWriter3 = new java.io.FileWriter( "C:\\temp\\Mops.mps" );
      java.io.PrintWriter batchWriter3 = new java.io.PrintWriter( fileWriter3 );
      while ( (zeile=mopsreader.readLine()) != null )
      {
        Anzahl_Zeilen++;
        Input=Input.concat(zeile+" ");
      }
///////////////////////////////ROWS
        StringTokenizer stringtok = new StringTokenizer( Input," \t\n\r\f" );
        String wert="";

        while( stringtok.hasMoreTokens() )
        {
          token = stringtok.nextToken();
          if( token.equals( "ZF" ) )
          {
           ZielFunktion = ZielFunktion.concat( " N" + "  ZF\n" );
               while( !( token.equals( ";" ) ))
               {
                       if(token.equals("x"))
                       {
                         a=a+1;
                         columnsmatrix[a][1]="x";
                         token=stringtok.nextToken();
                         columnsmatrix[a][2]=token;
                         columnsmatrix[a][3]="ZF";
                         columnsmatrix[a][4]=wert;
                       }
                       wert =token;
                       token = stringtok.nextToken();
                     }

          }
          if( token.equals( "R" ) )
          {
            token = stringtok.nextToken();
            R = token;
            while( !( token.equals( ";" ) ) )
            {
              if( token.equals( "=" ) )
              {
                ZielFunktion = ZielFunktion.concat( " E" + "  R"+R + "\n" );
              }
              if( token.equals( "<" ) )
              {
                ZielFunktion = ZielFunktion.concat( " L" + "  R"+R + "\n" );
              }
              token = stringtok.nextToken();
            }
          }

///////////////////////////////Columnserstellung
         StringTokenizer stringtok2 = new StringTokenizer( Input," \t\n\r\f" );
          i=a;
         String minusString="";

         while( stringtok2.hasMoreTokens() )
         {
           int anzahl=0;
              token = stringtok2.nextToken();
                   if( token.equals( "R" ) )
                   {
                     token = stringtok2.nextToken();
                     R = token;

                     while( !( token.equals(";")))
                     {
                       if(token.equals("x"))
                       {
                         anzahl=anzahl+1;
                         i=i+1;
                         columnsmatrix[i][1]="x";
                         token=stringtok2.nextToken();
                         columnsmatrix[i][2]=token;
                         columnsmatrix[i][3]="R"+R;
                         if(minusString != "")
                         {
                          columnsmatrix[i][4]="-"+H;
                          minusString="";
                         }
                         else{
                         columnsmatrix[i][4]=H;
                         }
                        }
                       if(token.equals("="))
                       {
                         int v=i;
                         for(int k =1+(v-anzahl); k<(v+1); k++)
                         {
                           columnsmatrix[k][5]="E";
                         }
                         i=i+1;
                         columnsmatrix[i][1]="MYRHS";
                         columnsmatrix[i][2]="dummy ";
                         columnsmatrix[i][3]="R"+R;
                         token = stringtok2.nextToken();
                         columnsmatrix[i][4]=token;
                       }
                       if(token.equals("<"))
                       {
                         int w=i;
                         for(int k =1+(w-anzahl); k<(w+1); k++)
                         {
                         columnsmatrix[k][5]="L";
                         }
                       }
                       H =token;
                       if(token.equals("-"))
                           {
                             minusString="-";
                           }
                       token = stringtok2.nextToken();
                     }
                  }
                }
        }
///////////////////////////////Columnsausgabe
        int sicheri=i;
        double tmp2;
                for(int l=1;l<i;l++)
                {
                  final Integer m = new Integer( l );
                  String s = m.toString();
                  int p = 0;
                  p = 1;
                  while( !( columnsmatrix[p][2].equals( "q" ) ) )
                  {
                    if( columnsmatrix[p][2].equals( s ) )
                    {
                      tmp2 = Double.parseDouble( columnsmatrix[p][2] );
                      if(tmp2<10)
                        {
                        columnsFunktion = columnsFunktion.concat( "    x"+columnsmatrix[p][2]+"        "+columnsmatrix[p][3]+ "        "+columnsmatrix[p][4]+"\n" );
                        }
                        if(tmp2>=10){
                        columnsFunktion = columnsFunktion.concat( "    x"+columnsmatrix[p][2]+"       "+columnsmatrix[p][3]+ "        "+columnsmatrix[p][4]+"\n" );
                    }
                    }
                    p = p + 1;
                  }
                 }
                 for(int l=1;l<100;l++)
                 {
                   if( columnsmatrix[l][1].equals( "MYRHS" ) )
                   {
                   rhsFunktion = rhsFunktion.concat( "    MYRHS     "+ columnsmatrix[l][3]+ "        "+columnsmatrix[l][4]+"\n" );
                   }
                 }
      batchWriter3.println("NAME"+"          Beispiel\n"+"ROWS\n"+ZielFunktion+"COLUMNS");
      batchWriter3.println(columnsFunktion+"RHS");
      batchWriter3.println(rhsFunktion);
      //batchWriter3.println("BOUNDS");
      batchWriter3.println("ENDATA");

      MopsData +="NAME"+"\tBeispiel\n"+"ROWS\n"+ZielFunktion+"\n"+"COLUMNS"+"\n"+columnsFunktion+"\n"+"RHS"+"\n"+rhsFunktion+"\n"+"ENDATA";
      batchWriter3.flush();
      batchWriter3.close();
      fileWriter3.close();
    }
    catch( IOException e )
    {
      _ERROR_MSG += "\nIO error: " + e;
      return (" ");
    }
    try
    {
      Thread.sleep(500);
    }
    catch(Exception e) {}

    createVariablenVector( myStringVector.size() - 1 );
    return (MopsData);
  }

// diese Funktion berechnet die Lösung mit Mops
  private boolean calculateMops98( String MopsData) {

    if( MopsData.equals( "" ) )
    {
      return false;
    }
    BufferedReader p_inBuffer;
    String proc_input_string = "";
    try
    {
       java.io.FileWriter fileWritermopsbatch = new java.io.FileWriter("C:\\temp\\mopsbatch.cmd");
                        java.io.PrintWriter batchWriter55 = new java.io.PrintWriter(fileWritermopsbatch);
                        batchWriter55.println("@echo off");
                        batchWriter55.println("copy Mops C:\\temp");
                        batchWriter55.println("C:");
                        batchWriter55.println("chdir " + "C:\\temp");
                        batchWriter55.println("start /wait C:\\Temp\\MOPS_PC.EXE C:\\temp\\Mops.mps MIN");
                        batchWriter55.flush();
                        batchWriter55.close();
      try
      {
        Thread.sleep(5*100);
      }
      catch(InterruptedException e) {}
      try
      {
        Process mopsproc = Runtime.getRuntime().exec("C:\\temp\\mopsbatch.cmd");
        mopsproc.waitFor();
        mopsproc.destroy();
        return true;
      }
      catch(Exception e) {}
    }
    catch( IOException e )
    {
      _ERROR_MSG += "\nIO error: " + e;
      myStringVector.add( 0, "error" );
      myStringVector.add( 1, _ERROR_MSG );
      return false;
    }
    return true;
  }

  // diese Funktion berechnet die Lösung mit Mops
  private boolean calculateMops( String MopsData ) {

    if( MopsData.equals( "" ) )
    {
      return false;
    }
    //write xmops.pro
    try{
        java.io.FileWriter fileWriterMOPSProfile = new java.io.FileWriter("C:\\temp\\xmops.pro");
                            java.io.PrintWriter printWriter88 = new java.io.PrintWriter( fileWriterMOPSProfile );
                            printWriter88.println("xoutsl = 1");
                            printWriter88.println("xlptyp = 0");
                            printWriter88.println("xmxmin = 15.0");
                            printWriter88.println("xfnmps = 'c:\\temp\\mops.mps'");
                            printWriter88.flush();
                            printWriter88.close();
    }
    catch( IOException e )
    {
      _ERROR_MSG += "\nIO error: " + e;
      myStringVector.add( 0, "error" );
      myStringVector.add( 1, _ERROR_MSG );
      return false;
    }

    //copy and start mops.exe
    BufferedReader p_inBuffer;
    String proc_input_string = "";
    try
    {
       java.io.FileWriter fileWritermopsbatch = new java.io.FileWriter("C:\\temp\\mopsbatch.cmd");
                        java.io.PrintWriter batchWriter55 = new java.io.PrintWriter(fileWritermopsbatch);
                        batchWriter55.println("@echo off");
                        batchWriter55.println("copy  \"L:\\BESF\\Solver\\MOPS 7.06\\Exec\\mops.exe\" C:\\temp\\");
                        batchWriter55.println("C:");
                        batchWriter55.println("chdir " + "C:\\temp");
                        batchWriter55.println("start /wait C:\\Temp\\MOPS.EXE > C:\\Temp\\MOPS.LOG");
                        batchWriter55.flush();
                        batchWriter55.close();
      try
      {
        Thread.sleep(5*100);
      }
      catch(InterruptedException e) {}
      try
      {
        Process mopsproc = Runtime.getRuntime().exec("C:\\temp\\mopsbatch.cmd");
        mopsproc.waitFor();
        mopsproc.destroy();
        return true;
      }
      catch(Exception e) {}
    }
    catch( IOException e )
    {
      _ERROR_MSG += "\nIO error: " + e;
      myStringVector.add( 0, "error" );
      myStringVector.add( 1, _ERROR_MSG );
      return false;
    }
    return true;
  }
//////////////////////////////////////////////////////////////////////////////
// Einlesen der MOPS-Lösung damit sie weiterverarbeitet werden kann
  private boolean parseMopsData()
  {
    int Anzahl_Zeilen = 0;
    int savekey=0;
    int zaehler = 0;
    int int_token;
    String zeile = "";
    String MyInput = "";
    String token = "";
    String int_str = "";
    double tmp;
    // Daten einlesen
    try
    {
      java.io.FileReader MopsReader = new java.io.FileReader("C:\\temp\\mops.lps"); // "C:\\temp\\mops.out");
      java.io.BufferedReader mreader = new java.io.BufferedReader(MopsReader);
      myStringVector.add( zaehler, "0" );
      zaehler++;
      while ( (zeile= mreader.readLine()) != null )
      {
        Anzahl_Zeilen++;
        MyInput=MyInput.concat(zeile+" ");
      }
      int xi=1;
      int ri=2;
      double hilf=0;
      double minhilf=0;
      double starthilf=0;
      for(int i=0; i<=Anzahl_Zeilen; i++)
      {
        StringTokenizer mopsSt = new StringTokenizer( MyInput," \t\n\r\f" );
        while( mopsSt.hasMoreTokens() )
        {
          token = mopsSt.nextToken();
          if (token.equals("R"+1))
          {
            token=mopsSt.nextToken();
            token=mopsSt.nextToken();
            starthilf = Double.parseDouble( token );
            minhilf=starthilf;
          }
          if (token.equals("R"+ri))
          {
            token=mopsSt.nextToken();
            token=mopsSt.nextToken();
            hilf = Double.parseDouble( token );
            if(hilf>0 && hilf<minhilf)
            {
              minhilf=hilf;
            }
            ri=ri+1;
          }
          if( token.equals( "x"+xi ) )
          {
            token = mopsSt.nextToken();
            try
            {
              token = mopsSt.nextToken();
              tmp = Double.parseDouble( token );

              if( tmp > 0 && tmp<1)
              {
                int_str = "1";
              }
              else
              {
                int_token=(int)tmp;
                int_str=Integer.toString(int_token);
               }
               savekey = 1;
             }
             catch( NumberFormatException e ) {}
             if( savekey == 1 )
             {
               myStringVector.add( zaehler, int_str );
               savekey = 0;
               zaehler++;
             }
             xi=xi+1;
           }
         }
       }
       mreader.close();
       MopsReader.close();
     }
     catch( IOException e )
     {
       _ERROR_MSG += "\nIO error: " + e;
       myStringVector.add( 0, "error" );
       myStringVector.add( 1, _ERROR_MSG );
       return false;
     }
     createVariablenVector( myStringVector.size() - 1 );
     createErgebnis();
     return true;
   }
//////////////////////////////////////////////////////////////////////////
// In dieser Funktion wird das komplette LP-Modell für LP-Solve erstellt!
  private String createFile()
  {
   String dataSet = "";

// hier wird die Zielfunktion erstellt!
    String zf = null;
    int koeffizient = 1;
    zf = "min:";
    for( int i = 0; i < streckenliste.size(); i++ ) {
      koeffizient++;
    }
    for( int i = 0; i < transporterliste.size(); i++ ) {
      for( int j = 0; j < streckenliste.size(); j++ ) {

        zf += "+" +
            ( ( ( Strecke )streckenliste.get( j ) ).getEntfernung() *
            ( ( Transporter )transporterliste.get( i ) ).getKosten_pro_km() ) +
            "x" + koeffizient;
        koeffizient++;
      }
    }
    zf += ";";
    dataSet += zf + "\n";
    int anzVariablen = koeffizient;
    koeffizient = 1;

// Ab hier werden die Restriktionen zusammengebaut!
    int restNr = 1;
    String rest = "";

// Restriktionen für die Produzenten
    for( int i = 0; i < produzentenliste.size(); i++ ) {
      rest = "R" + restNr + ":  ";
      for( int j = 0; j < streckenliste.size(); j++ ) {
        if( ( ( ( Strecke )streckenliste.get( j ) ).getQuelle() ) == ( ( ( Produzent )produzentenliste.get( i ) ).getId() ) ) {
          if( koeffizient == 1 ) {
            rest += "1x" + koeffizient;
          }
          else {
            rest += "+1x" + koeffizient;
          }
        }
        koeffizient++;
      }
      for( int k = koeffizient; k < anzVariablen; k++ ) {
        koeffizient++;
      }
      rest += "=" + ( ( Produzent )produzentenliste.get( i ) ).getLieferMenge() + ";";
      dataSet += rest + "\n";
      koeffizient = 1;
      restNr++;
    }

// Restriktionen für die Empfänger
    for( int i = 0; i < empfaengerliste.size(); i++ ) {
      rest = "R" + restNr + ":  ";
      for( int j = 0; j < streckenliste.size(); j++ ) {
        if( ( ( Strecke )streckenliste.get( j ) ).getZiel() == ( ( Empfaenger )empfaengerliste.get( i ) ).getId() ) {
          if( koeffizient == 1 ) {
            rest += "1x" + koeffizient;
          }
          else {
            rest += "+1x" + koeffizient;
          }
        }
        koeffizient++;
      }
      for( int k = koeffizient; k < anzVariablen; k++ ) {
        koeffizient++;
      }
      rest += "=" + ( ( Empfaenger )empfaengerliste.get( i ) ).getBenoetigteMenge() + ";";
      dataSet += rest + "\n";
      koeffizient = 1;
      restNr++;
    }

// Restliche Restriktionen
    int firstPosition = 0;
    for( int i = 0; i < streckenliste.size(); i++ ) {
      rest = "R" + restNr + ":  ";
      for( int j = 0; j < ( anzVariablen / ( transporterliste.size() + 1 ) ); j++ ) {
        if( j == firstPosition ) {
          if( j == 0 ) {
            rest += "1x" + koeffizient;
          }
          else {
            rest += "+1x" + koeffizient;
          }
        }
        koeffizient++;
      }
      for( int k = 0; k < transporterliste.size(); k++ ) {
        for( int l = 0; l < streckenliste.size(); l++ ) {
          if( l == firstPosition ) {
            rest += "-" + ( ( Transporter )transporterliste.get( k ) ).getKapazitaet() + "x" +
                koeffizient;
          }
          koeffizient++;
        }
      }
      rest += "<=0;";
      dataSet += rest + "\n";
      firstPosition++;
      restNr++;
      koeffizient = 1;
    }

// Zusammenbau der Zeile, damit nur ganzzahlige Ergebnisse herauskommen
    String intZeile = "int ";
    for( int i = 1; i < anzVariablen; i++ ) {
      if( i == 1 ) {
        intZeile += "x" + ( i );
      }
      else {
        intZeile += ",x" + ( i );
      }
    }
    intZeile += ";";
    dataSet += "\n" + intZeile;

   return dataSet;
  }

//////////////////////////////////////////////////////////////////////////
// In dieser Funktion wird das komplette LP-Modell für Weidenauer erstellt!
  private void createFileWeid()
  {
    try
      {
        java.io.FileWriter fileWriter2 = new java.io.FileWriter("C:\\temp\\LP.LP");
        java.io.PrintWriter batchWriter2 = new java.io.PrintWriter(fileWriter2);
// ab hier wird die Zielfunktion erstellt!
        String zf = null;
        int koeffizient = 1;
        zf = "MIN: ";
        for( int i = 0; i < streckenliste.size(); i++ )
        {
          koeffizient++;
        }
        for( int i = 0; i < transporterliste.size(); i++ )
        {
          for( int j = 0; j < streckenliste.size(); j++ )
          {
            zf += "+" +
                ( ( ( Strecke )streckenliste.get( j ) ).getEntfernung() *
                ( ( Transporter )transporterliste.get( i ) ).getKosten_pro_km() ) +
                "*x" + koeffizient;
            koeffizient++;
          }
        }
        zf += ";";
        batchWriter2.println(zf);
        int anzVariablen = koeffizient;
        koeffizient = 1;
// ab hier werden die Restriktionen zusammengebaut!
        int restNr = 1;
        String rest = "";
// Restriktionen für die Produzenten
        for( int i = 0; i < produzentenliste.size(); i++ ) {
          rest = "R" + restNr + ":  ";
          for( int j = 0; j < streckenliste.size(); j++ ) {
            if( ( ( ( Strecke )streckenliste.get( j ) ).getQuelle() ) == ( ( ( Produzent )produzentenliste.get( i ) ).getId() ) ) {
              if( koeffizient == 1 ) {
                rest += "1*x" + koeffizient;
              }
              else {
                rest += "+1*x" + koeffizient;
              }
            }
            koeffizient++;
          }
          for( int k = koeffizient; k < anzVariablen; k++ ) {
            koeffizient++;
          }
          rest += "=" + ( ( Produzent )produzentenliste.get( i ) ).getLieferMenge() + ";";
          batchWriter2.println(rest);
          koeffizient = 1;
          restNr++;
        }
// Restriktionen für die Empfänger
        for( int i = 0; i < empfaengerliste.size(); i++ )
        {
          rest = "R" + restNr + ":  ";
          for( int j = 0; j < streckenliste.size(); j++ )
          {
            if( ( ( Strecke )streckenliste.get( j ) ).getZiel() == ( ( Empfaenger )empfaengerliste.get( i ) ).getId() ) {
              if( koeffizient == 1 )
              {
                rest += "1*x" + koeffizient;
              }
              else
              {
                rest += "+1*x" + koeffizient;
              }
            }
            koeffizient++;
          }
          for( int k = koeffizient; k < anzVariablen; k++ )
          {
            koeffizient++;
          }
          rest += "=" + ( ( Empfaenger )empfaengerliste.get( i ) ).getBenoetigteMenge() + ";";
          batchWriter2.println(rest);
          koeffizient = 1;
          restNr++;
        }
// Restliche Restriktionen
        int firstPosition = 0;
        for( int i = 0; i < streckenliste.size(); i++ )
        {
          rest = "R" + restNr + ":  ";
          for( int j = 0; j < ( anzVariablen / ( transporterliste.size() + 1 ) ); j++ )
          {
            if( j == firstPosition )
            {
              if( j == 0 )
              {
                rest += "1*x" + koeffizient;
              }
              else {
                rest += "+1*x" + koeffizient;
              }
            }
            koeffizient++;
          }
          for( int k = 0; k < transporterliste.size(); k++ )
          {
            for( int l = 0; l < streckenliste.size(); l++ )
            {
              if( l == firstPosition )
              {
                rest += "-" + ( ( Transporter )transporterliste.get( k ) ).getKapazitaet() + "*x" +
                    koeffizient;
              }
              koeffizient++;
            }
          }
          rest += "<0;";
          batchWriter2.println(rest);
          firstPosition++;
          restNr++;
          koeffizient = 1;
        }
// Zusammenbau der Bounds und Schlussteil
        batchWriter2.println(":ENDE");
        batchWriter2.println("BOUNDS");
        for(int i=1; i<anzVariablen; i++)
        {
          //VORSICHT: blanks nicht entfernen!
          //vor und nach UI muss je 1 blank stehen
          //nach BOUND müssen 5 blanks sthen
          //vor 1e6 müssen 15 blanks stehen
          //GRUND: mps-Konvention
          batchWriter2.println(" UI BOUND     x"+i+"               1e6");
        }
        batchWriter2.println("ENDDATA");
        batchWriter2.flush();
        batchWriter2.close();
      }
      catch( IOException e )
      {
          _ERROR_MSG += "\nIO error: " + e;
          myStringVector.add( 0, "error" );
          myStringVector.add( 1, _ERROR_MSG );
      }
}

//////////////////////////////////////////////////////////////////////////////
// diese Funktion berechnet die Lösung mit LP-Solver
  private boolean calculateLPsolve( String dataSet )
  {
    if( dataSet.equals( "" ) )
    {
      return false;
    }

    BufferedReader p_inBuffer;
    String proc_input_string = "";
    try {
      Process proc = Runtime.getRuntime().exec( "lp_solve.exe" );
      //get the streams
      InputStream proc_in = proc.getInputStream();
      InputStream prog_err = proc.getErrorStream();
      int exit = 0;
      boolean processEnded = false;
      //push the data to the lpsolve
      OutputStream proc_out = proc.getOutputStream();
      DataOutputStream out_s = new DataOutputStream( proc_out );
      try {
        out_s.writeBytes( dataSet );
      }
      catch( IOException ex ) {
        ex.printStackTrace();
      }
      out_s.close();
      proc_out.close();

      // fetch the output from the LPsolve
      lpSolveOutput = new StringBuffer();
      while( !processEnded ) {
        try {
          exit = proc.exitValue();
          processEnded = true;
        }
        catch( IllegalThreadStateException e ) {
        } // still running

        int n = proc_in.available();
        if( n > 0 ) {
          byte[] pbytes = new byte[n];
          proc_in.read( pbytes );
          lpSolveOutput.append( new String( pbytes ) );
        }
        n = prog_err.available();
        if( n > 0 ) {
          byte[] pbytes = new byte[n];
          prog_err.read( pbytes );
          _ERROR_MSG += new String( pbytes );
        }
        try {
          Thread.sleep( 10 );
        }
        catch( InterruptedException e ) {}
      } // ### END-while ###
      //close/destroy all handles
      prog_err.close();
      proc_in.close();
      proc.destroy();
    }
    catch( IOException e ) {
      _ERROR_MSG += "\nIO error: " + e;
      myStringVector.add( 0, "error" );
      myStringVector.add( 1, _ERROR_MSG );
      return false;
    }
    return true;
  }


//////////////////////////////////////////////////////////////////////////////
// diese Funktion berechnet die Lösung mit Weidenau Optimizer
  private boolean calculateWeidenauer()
  {
    try
     {
       java.io.FileWriter fileWriter = new java.io.FileWriter("C:\\temp\\weid_par.cmd");
                         java.io.PrintWriter batchWriter = new java.io.PrintWriter(fileWriter);
                         batchWriter.println("LOADLP C:\\temp\\LP.LP");
                         batchWriter.println("MINIMISE");
                         batchWriter.println("GLOBAL");
                         batchWriter.println("SAVEINTEGER");
                         batchWriter.println("QUIT");
                         batchWriter.flush();
                         batchWriter.close();

        java.io.FileWriter fileWriter2 = new java.io.FileWriter("C:\\temp\\weid_solve.cmd");
                         java.io.PrintWriter batchWriter2 = new java.io.PrintWriter(fileWriter2);
                         batchWriter2.println("@echo off");
                         batchWriter2.println("copy Weidenauer C:\\temp");
                         batchWriter2.println("C:");
                         batchWriter2.println("chdir " + "C:\\temp");
                         batchWriter2.println("start /wait C:\\temp\\Lp.exe " + "C:\\temp\\weid_par.cmd");
                         batchWriter2.flush();
                         batchWriter2.close();
       /************************************************************/
       try
       {
         Process proc = Runtime.getRuntime().exec("C:\\temp\\weid_solve.cmd");
         proc.waitFor();
       }
       catch(Exception e) {}
       /************************************************************/
     }
     catch( IOException e )
     {
       _ERROR_MSG += "\nIO error: " + e;
       myStringVector.add( 0, "error" );
       myStringVector.add( 1, _ERROR_MSG );
       return false;
     }
     return true;
  }

////////////////////////////////////////////////////////////////////////////////
// Einlesen der Weidenauer-Lösung damit sie weiterverarbeitet werden kann
  private boolean parseDataWeid()
  {
    String zeile      = "";
     String Input      = "";
     String token      = "";
     String int_str    = "";
     int Anzahl_Zeilen = 0;
     int savekey       = 0;
     int zaehler       = 0;
     int int_token;
     double tmp;

     // Daten einlesen
     try
     {
       java.io.FileReader solveOutReader = new java.io.FileReader("C:\\temp\\Lp.int");
       java.io.BufferedReader reader     = new java.io.BufferedReader(solveOutReader);
      //Lösungs-Datei in String lesen
       while ( (zeile=reader.readLine()) != null )
       {
         Anzahl_Zeilen++;
         Input=Input.concat(zeile+" ");
       }
       // Gesamtkosten suchen und als erstes in Vector schreiben
       StringTokenizer st1 = new StringTokenizer(Input);
       while(st1.hasMoreTokens() )
       {
         token = st1.nextToken();
         if(token.equals("MIN"))
         {
           try
           {
             token   = st1.nextToken();
             tmp     = Double.parseDouble(token);
             int_str = Double.toString(tmp);
             savekey = 1;
           }
           catch( NumberFormatException e ) {}
           if( savekey == 1 )
           {
             myStringVector.add( zaehler, int_str );
             savekey = 0;
             zaehler++;
          }
         }
       }
       // Alle Variablen-Lösungen suchen und in Vector schreiben
       for(int i=0; i<=Anzahl_Zeilen; i++)
       {
         StringTokenizer st2 = new StringTokenizer( Input );
         while( st2.hasMoreTokens() )
         {
           token = st2.nextToken();
           if( token.equals( "x"+i ) )
           {
             try
             {
               token     = st2.nextToken();
               tmp       = Double.parseDouble(token);
               int_token = (int)tmp;
               int_str   =Integer.toString(int_token);
               savekey   = 1;
             }
             catch( NumberFormatException e ) {}
             if( savekey == 1 )
             {
               myStringVector.add( zaehler, int_str );
               savekey = 0;
               zaehler++;
             }
           }
         }
       }
     }
     catch( IOException e )
     {
       _ERROR_MSG += "\nIO error: " + e;
       myStringVector.add( 0, "error" );
       myStringVector.add( 1, _ERROR_MSG );
       return false;
     }
     createVariablenVector( myStringVector.size() - 1 );
     createErgebnis();
     return true;


  }

/////////////////////////////////////////////////////////////////////////////
// Einlesen der LP-Solve-Lösung damit sie weiterverarbeitet werden kann
  private boolean parseData()
  {
    String token = "";
    int savekey = 0;
    int valuekey = 0; // means 'Value of objective function' comes and this means a solution is comming :-)
    int zaehler = 0;
    double tmp = 0;

    StringTokenizer st = new StringTokenizer( lpSolveOutput.toString() );
    while( st.hasMoreTokens() ) {
      token = st.nextToken();
      if( token.equals( "Value" ) )
        valuekey = 1;
      if( valuekey == 1 ) {
        try {
          tmp = Double.parseDouble( token );
          savekey = 1;
        }
        catch( NumberFormatException e ) {}
        if( savekey == 1 ) {
          myStringVector.add( zaehler, token );
          savekey = 0;
          zaehler++;
        }
      }
    }
    valuekey = 0;
    //no numbers means solution is infeasible
    if( zaehler == 0 ) {
      myStringVector.add( 0, "ERROR" );
      myStringVector.add( 1, lpSolveOutput.toString() );
      return false;
    }
    createVariablenVector( myStringVector.size() - 1 );
    sortiereMyStringVariableVector();
    createErgebnis();
    return true;
  }

// Hilfsfunktion 1
  private void createVariablenVector( int size )
  {
    String strZahl = "";
    int iZahl;
    varVector.add( String.valueOf( 0 ) );
    for( int i = 10; i < 100; i++ ) {
      if( ( i > 0 ) && ( i <= size ) ) {
        varVector.add( String.valueOf( i ) );
      }
    }
    for( int i = 1; i < 10; i++ ) {
      if( ( i > 0 ) && ( i <= size ) ) {
        varVector.add( String.valueOf( i ) );
      }
    }
    for( int i = 0; i < varVector.size(); i++ ) {

    }
  }

//Hilfsfuntion 2
  private void sortiereMyStringVariableVector()
  {
    Vector tmpVector = new Vector();
    for( int i = 0; i < ( varVector.size() ); i++ ) {
      tmpVector.add( myStringVector.get( holePosition( String.valueOf( i ) ) ) );
    }
    myStringVector = tmpVector;
  }

//Hilfsfuntion 3
  private int holePosition( String pos )
  {
    for( int i = 0; i < varVector.size(); i++ ) {
      if( varVector.get( i ).equals( pos ) ) {
        return i;
      }
    }
    return 0;
  }

// Erstellen des Ergebnisses welches durch die get-Methode geholt werden kann
  private void createErgebnis()
  {
    double kosten = 0;
    int anzFahrten = 0;
    Ergebnis tmpErgebnis = new Ergebnis();
    for( int i = 1; i <= streckenliste.size(); i++ ) {
      if( !myStringVector.get( i ).equals( "0" ) ) {
        tmpErgebnis.setLadung( ( String )myStringVector.get( i ) );
        tmpErgebnis.setQuelle( ( ( Strecke )streckenliste.get( i - 1 ) ).getQuelle() );
        tmpErgebnis.setZiel( ( ( Strecke )streckenliste.get( i - 1 ) ).getZiel() );
        for( int j = 0; j < transporterliste.size(); j++ ) {
          if( !myStringVector.get( i + ( streckenliste.size() * ( j + 1 ) ) ).equals( "0" ) ) {
            anzFahrten = Integer.parseInt( myStringVector.get( i + ( streckenliste.size() * ( j + 1 ) ) ).toString() );
            tmpErgebnis.setTransporter( ( ( Transporter )transporterliste.get( j ) ).getName() );
            kosten = ( ( ( Transporter )transporterliste.get( j ) ).getKosten_pro_km() *
                ( ( Strecke )streckenliste.get( i - 1 ) ).getEntfernung() * anzFahrten );
            tmpErgebnis.setKosten( kosten );
          }
        }
        erg.add( tmpErgebnis );
        tmpErgebnis = new Ergebnis();
      }
    }
  }

// get the error-messages
  public String getErrorMsg()
  {
    return _ERROR_MSG;
  }

// get the warning-messages
  public String getWarningMsg()
  {
    return _WARNING_MSG;
  }

  public Vector getMyStringVector()
  {
    return myStringVector;
  }

  public Vector getErgebnis()
  {
    return erg;
  }

  public void printVector()
  {

  }
}