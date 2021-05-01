/**
 * <p>Title: TR-Optimizer</p>
 * <p>Description: TR-Optimizer ist eine Weiterentwicklung von TransOp</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */

import java.io.*;
import java.lang.*;
import java.util.*;

public class Solve {
  private Vector empfaengerliste = new Vector();
  private Vector produzentenliste = new Vector();
  private Vector streckenliste = new Vector();
  private Vector transporterliste = new Vector();
  private Vector erg = new Vector();
  private Vector varVector = new Vector();

  private String _ERROR_MSG;
  private String _WARNING_MSG;
  private StringBuffer lpSolveOutput = new StringBuffer();
  private Vector myStringVector = new Vector();
  String dataSet = "";

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
// wenn dies nicht so ist wird ein virtueller Empfänger eingeführt
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
  }

// Im Konstruktor werden gleich alle benötigten Funktionen aufgerufen damit
// sofort eine Lösung vorliegt welche durch die "get-Methode" abgeholt werden kann
  public Solve( Vector empfaengerliste, Vector produzentenliste,
      Vector streckenliste, Vector transporterliste ) {
    this.empfaengerliste = empfaengerliste;
    this.produzentenliste = produzentenliste;
    this.streckenliste = streckenliste;
    this.transporterliste = transporterliste;

    korrigiereDaten();
    String dataSet = null;
    dataSet = this.createFile();
    calculateLPsolve( dataSet );
    parseData();
    //printVector();
  }

// In dieser Funktion wird das komplette LP-Modell erstellt!
  private String createFile() {
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
        System.err.println( "vergleiche: " + ( ( Strecke )streckenliste.get( j ) ).getZiel() + " mit " +
            ( ( Produzent )produzentenliste.get( i ) ).getId() );
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

// diese Funktion berechnet die Lösung
  private boolean calculateLPsolve( String dataSet ) {
    if( dataSet.equals( "" ) ) {
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
        System.err.println( "dataSet-Laenge: " + dataSet.length() );
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
      System.err.println( "loesung: " + lpSolveOutput );
      //close/destroy all handles
      prog_err.close();
      proc_in.close();
      proc.destroy();
    }
    catch( IOException e ) {
      // System.err.println("exeption-error:" + e);
      _ERROR_MSG += "\nIO error: " + e;
      myStringVector.add( 0, "error" );
      myStringVector.add( 1, _ERROR_MSG );
      return false;
    }
    return true;
  }

// Einlesen der Lösung damit sie weiterverarbeitet werden kann
  private boolean parseData() {
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
  private void createVariablenVector( int size ) {
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
      System.out.println( varVector.get( i ) );
    }
  }

//Hilfsfuntion 2
  private void sortiereMyStringVariableVector() {
    Vector tmpVector = new Vector();
    for( int i = 0; i < ( varVector.size() ); i++ ) {
      tmpVector.add( myStringVector.get( holePosition( String.valueOf( i ) ) ) );
    }
    myStringVector = tmpVector;
  }

//Hilfsfuntion 3
  private int holePosition( String pos ) {
    for( int i = 0; i < varVector.size(); i++ ) {
      if( varVector.get( i ).equals( pos ) ) {
        return i;
      }
    }
    return 0;
  }

// Erstellen des Ergebnisses welches durch die get-Methode geholt werden kann
  private void createErgebnis() {
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
  public String getErrorMsg() {
    return _ERROR_MSG;
  }

// get the warning-messages
  public String getWarningMsg() {
    return _WARNING_MSG;
  }

  public Vector getMyStringVector() {
    return myStringVector;
  }

  public Vector getErgebnis() {
    return erg;
  }

  public void printVector() {
    for( int i = 0; i < myStringVector.size(); i++ )
      System.out.println( "Vector[" + i + "]:" + myStringVector.get( i ) );
    System.out.println( "---------------------" );
  }
}