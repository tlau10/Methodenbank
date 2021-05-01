/**
 * <p>Title: TransopController</p>
 * <p>Description: Diese Klasse ist die Controller-Klasse des TR-Optimizer und steuert die Abläufe der Anwendung.</p>
 * <p>Copyright: Copyright (c) 2003, Oliver Baumann, Gunther Koch, Ekkehard Will</p>
 * <p>Company: FH-Konstanz</p>
 * @author Oliver Baumann, Gunther Koch, Ekkehard Will
 * @version 1.0
 */


import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class TransopController {

  //Klassenattribute
  private ZeichenPane zeichenPane;
  private TransporterPane transporterPane;
  private int empfaengerId = 1; // empfänger ids zwischen 1 und 999
  private int produzentId = 1000; // produzenten ids zwischen 1000 und 1999
  private int transporterId = 2000; // transporter ids zwischen 2000 und 2999
  private int streckenId = 3000; // strecken ids zwischen 3000 und 3999
  private Vector empfaenger;
  private Vector produzenten;
  private Vector strecken;
  private Vector transporter;
  private double transportPos = 520;
  private int gewaehlteId = 0;
  private MyPopUp popUp;
  private MyTransporterPopUp transporterPopUp;
  private MainFrame mainFrame;
  private int popUpX, popUpY; // damit gespeichert wird, wo ein popUp aufgerufen wurde --> um linien zu zeichnen

  Produzent tmpProduzent;
  Empfaenger tmpEmpfaenger;
  Transporter tmpTransporter;

  /**
   * Der Konstruktor erwartet ein MainFrame-Objekt und initialisiert die Klassenattribute.
   *
   * @param m aktuelles mainFrame-Objekt
   */
  public TransopController( MainFrame m ) {
    mainFrame = m;
    empfaenger = new Vector();
    produzenten = new Vector();
    strecken = new Vector();
    transporter = new Vector();

  }

  /**
   * Diese Methode legt ein neues ProduzentenObject an und speichert es in einem vector.
   */
  public void addProduzent() {
    tmpProduzent = new Produzent( ( ( Math.random() * 450 ) + 10 ), ( ( Math.random() * 350 ) + 10 ), produzentId );
    tmpProduzent.setName( "" );
    tmpProduzent.setLieferMenge( 0 );
    produzenten.add( tmpProduzent );
    zeichenPane.setProduzenten( produzenten );

    produzentId++;
  }

  /**
   * Diese Methode legt ein neues empfaengerObject an und speichert es in einem vector
   */
  public void addEmpfaenger() {
    tmpEmpfaenger = new Empfaenger( ( int )( ( Math.random() * 450 ) + 10 ), ( int )( ( Math.random() * 350 ) + 10 ),
        empfaengerId );
    //zeichenPane.addEmpfaenger( tmpEmpfaenger);
    tmpEmpfaenger.setName( "" );
    tmpEmpfaenger.setBenoetigteMenge( 0 );
    empfaenger.add( tmpEmpfaenger );
    zeichenPane.setEmpfaenger( empfaenger );
    empfaengerId++;
  }

  /**
   * Diese Methode legt ein neues transporterObjekt an und speichert es in einem vector
   */
  public void addTransporter() {
    transportPos -= 60;

    tmpTransporter = new Transporter( transportPos, transporterPane.getHeight() - 35, transporterId );
    tmpTransporter.setName( "" );
    tmpTransporter.setKapazitaet( 0 );
    tmpTransporter.setKosten_pro_km( 0 );
    transporter.add( tmpTransporter );
    transporterPane.setTransporter( transporter );
    transporterId++;

  }

  /**
   * Diese Methode legt ein neues streckenObject an und speichert es in einem vector
   *
   * @param x xKoordinate der Strecke auf dem zeichenFeld
   * @param y yKoordinate der Strecke auf dem zeichenFeld
   * @param objektId id der Strecke
   *
   */
  public void addStrecke( int x, int y, int objektId ) {

    Empfaenger e;
    Produzent p;
    Strecke tmpStrecke = new Strecke( streckenId );

    // wurde die strecke von einem empfaenger aus gezeichnet
    if( objektId <= 999 ) {
      for( int i = 0; i < empfaenger.size(); i++ ) {
        e = ( Empfaenger )empfaenger.get( i );
        if( e.getId() == objektId ) {
          tmpStrecke.setX1Koordinate( ( int )e.getX() + 10 );
          tmpStrecke.setY1Koordinate( ( int )e.getY() + 10 );
          tmpStrecke.setX2Koordinate( x + 10 );
          tmpStrecke.setY2Koordinate( y + 10 );
          tmpStrecke.setQuelle( gewaehlteId );
          tmpStrecke.setZiel( objektId );
        }
      }
    }
    else { // wurde die strecke von einem produzenten aus gezeichnet
      for( int i = 0; i < produzenten.size(); i++ ) {
        p = ( Produzent )produzenten.get( i );
        if( p.getId() == objektId ) {
          tmpStrecke.setX1Koordinate( x + 10 );
          tmpStrecke.setY1Koordinate( y + 10 );
          tmpStrecke.setX2Koordinate( ( int )p.getX() + 10 );
          tmpStrecke.setY2Koordinate( ( int )p.getY() + 10 );
          tmpStrecke.setQuelle( objektId );
          tmpStrecke.setZiel( gewaehlteId );

        }

      }
    }
    tmpStrecke.setEntfernung( 0 );
    strecken.add( tmpStrecke );
    streckenId++;

  }

  /**
   * Diese Methode ueberprueft ob sich an der Stelle auf die im transporterPane geklickt wurde ein transporterObjekt befindet
   *
   * @param x xKoordinate des klicks
   * @param y yKoordinate des klicks
   * @return boolean true falls der Transporter in der richtigen Position ist, false sonst
   */
  public boolean isTransporterInPos( double x, double y ) {
    Transporter t;

    for( int i = 0; i < transporter.size(); i++ ) {
      t = ( Transporter )transporter.get( i );
      if( x >= t.getX() && x <= ( t.getX() + t.getBreite() ) && y >= ( t.getY() - 10 ) &&
          y <= ( ( t.getY() - 10 ) + t.getHoehe() ) ) {
        this.setGewaehlteId( t.getId() );
        return true;
      }
    }
    return false;
  }

  /**
   *
   * Diese Methode ueberprueft ob sich an der Stelle auf die geklickt wurde ein Objekt befindet.
   *
   * @param x = X-Koordinate des Maus-Klicks
   * @param y = Y-Koordinate der Maus-Klicks
   * @return 0, 1, 2, 3     1 = Empfaenger, 2 = Produzent, 0 = sonst
   */
  public boolean isInPos( double x, double y ) {

    Empfaenger e;
    Produzent p;
    Strecke s;

    for( int i = 0; i < empfaenger.size(); i++ ) {
      e = ( Empfaenger )empfaenger.get( i );
      if( x >= e.getX() && x <= ( e.getX() + e.getBreite() ) && y >= e.getY() && y <= ( e.getY() + e.getHoehe() ) ) {
        this.setGewaehlteId( e.getId() );
        return true;
      }
    }

    for( int i = 0; i < produzenten.size(); i++ ) {
      p = ( Produzent )produzenten.get( i );
      if( x >= p.getX() && x <= ( p.getX() + p.getBreite() ) && y >= p.getY() && y <= ( p.getY() + p.getHoehe() ) ) {
        this.setGewaehlteId( p.getId() );
        return true;
      }
    }

    for( int i = 0; i < strecken.size(); i++ ) {
      s = ( Strecke )strecken.get( i );
      double steigung;
      double konstante;
      double fVonX;
      double deltaY;
      boolean istWerteBereich = true;

      // Wertebereich prüfen

      if( s.getX1Koordinate() < s.getX2Koordinate() ) {
        if( x < s.getX1Koordinate() || x > s.getX2Koordinate() )
          istWerteBereich = false;
      }
      else {
        if( x > s.getX1Koordinate() || x < s.getX2Koordinate() )
          istWerteBereich = false;
      }

      if( s.getY1Koordinate() < s.getY2Koordinate() ) {
        if( y < s.getY1Koordinate() || y > s.getY2Koordinate() )
          istWerteBereich = false;
      }
      else {
        if( y > s.getY1Koordinate() || y < s.getY2Koordinate() )
          istWerteBereich = false;
      }

      // innerhalb des Wertebereiches prüfen ob der Klick auf der Kante lag

      steigung = ( ( double )s.getY1Koordinate() - ( double )s.getY2Koordinate() ) / ( ( double )s.getX1Koordinate() -
          ( double )s.getX2Koordinate() );
      konstante = ( -1.0 ) * ( steigung * ( double )s.getX1Koordinate() ) + ( double )s.getY1Koordinate();
      fVonX = ( steigung * x ) + konstante;
      deltaY = fVonX - y;

      if( deltaY < 0 ) {
        deltaY *= -1.0;
      }

      if( deltaY <= 8.0 && istWerteBereich ) {
        this.setGewaehlteId( s.getId() );
        return true;
      }
    }
    return false;
  }

  /**
   * Diese Methode fuehrt das Verschieben eines objektes an eine andere Stelle in der Zeichenfläche aus
   *
   * @param x - Koordinate
   * @param y - Koordinate
   */

  public void move( int x, int y ) {

    Empfaenger e;
    Produzent p;
    Strecke s;

    if( gewaehlteId <= 999 ) { // empfänger gewählt

      for( int i = 0; i < empfaenger.size(); i++ ) {
        e = ( Empfaenger )empfaenger.get( i );

        if( e.getId() == gewaehlteId ) {

          if( ( ( Math.abs( x - e.getX() ) ) > e.getBreite() ) || ( ( Math.abs( y - e.getY() ) ) > e.getHoehe() ) ) {
            if( ( x <= ( zeichenPane.getWidth() - 15 ) && x >= ( zeichenPane.getWidth() - zeichenPane.getWidth() ) ) &&
                ( y <= ( zeichenPane.getHeight() - 20 ) && y >= ( zeichenPane.getHeight() - zeichenPane.getHeight() ) ) ) { // (Größe Zeichenfläche beachten!) damit objekte nicht aus der Zeichenfläche gezogen werden können
              ( ( Empfaenger )empfaenger.get( i ) ).setX( x );
              ( ( Empfaenger )empfaenger.get( i ) ).setY( y );
              // zugehörige Strecke bewegen
              for( int j = 0; j < strecken.size(); j++ ) {
                if( ( ( Strecke )strecken.get( j ) ).getZiel() == gewaehlteId ) {
                  ( ( Strecke )strecken.get( j ) ).setX1Koordinate( x + 10 );
                  ( ( Strecke )strecken.get( j ) ).setY1Koordinate( y + 10 );
                }
              }
            }
          }
        }
      }
      zeichenPane.setStrecken( strecken );
      zeichenPane.setEmpfaenger( empfaenger );

    }
    else if( gewaehlteId >= 1000 && gewaehlteId <= 1999 ) { // produzent gewählt

      for( int i = 0; i < produzenten.size(); i++ ) {
        p = ( Produzent )produzenten.get( i );
        if( p.getId() == gewaehlteId ) {
          if( ( ( Math.abs( x - p.getX() ) ) > p.getBreite() ) || ( ( Math.abs( y - p.getY() ) ) > p.getHoehe() ) ) {
            if( ( x <= ( zeichenPane.getWidth() - 15 ) && x >= ( zeichenPane.getWidth() - zeichenPane.getWidth() ) ) &&
                ( y <= ( zeichenPane.getHeight() - 20 ) && y >= ( zeichenPane.getHeight() - zeichenPane.getHeight() ) ) ) { // (Größe Zeichenfläche beachten!) damit objekte nicht aus der Zeichenfläche gezogen werden können
              ( ( Produzent )produzenten.get( i ) ).setX( x );
              ( ( Produzent )produzenten.get( i ) ).setY( y );
              // zugehörige Strecke bewegen
              for( int j = 0; j < strecken.size(); j++ ) {
                if( ( ( Strecke )strecken.get( j ) ).getQuelle() == gewaehlteId ) {
                  ( ( Strecke )strecken.get( j ) ).setX2Koordinate( x + 10 );
                  ( ( Strecke )strecken.get( j ) ).setY2Koordinate( y + 10 );
                }
              }
            }
          }
        }
      }
      zeichenPane.setStrecken( strecken );
      zeichenPane.setProduzenten( produzenten );

    }
  }

  /**
   * Diese Methode ruft ein popUp fuer empfaenger und produzenten auf.
   *
   * @param x xKoordinate des klicks
   * @param y yKoordinate des klicks
   */
  public void popMeUp( int x, int y ) {
    if( gewaehlteId < 3000 ) {
      popUp = new MyPopUp( this );
      popUp.setObjektId( gewaehlteId );

      popUpX = x + 10;
      popUpY = y + 10;

      popUp.show( zeichenPane, popUpX, popUpY );
    }
    else {
      MyStreckenPopUp streckenPopUp = new MyStreckenPopUp( this );
      streckenPopUp.setObjektId( gewaehlteId );
      popUpX = x + 10;
      popUpY = y + 10;
      streckenPopUp.show( zeichenPane, popUpX, popUpY );
    }
  }

  /**
   * Diese Methode ruft ein popUp fuer transporter auf
   *
   * @param x xKoordinate des klicks
   * @param y yKoordinate des klicks
   */
  public void popUpTransporter( int x, int y ) {
    transporterPopUp = new MyTransporterPopUp( this );
    transporterPopUp.setObjektId( gewaehlteId );
    transporterPopUp.show( zeichenPane, ( x + 10 ), ( y + 10 ) );
  }

  /**
   * Diese Methode dient dazu, Objekte von der Zeichenfläche zu löschen
   *
   * @param id des zu löschenden Objekts
   */
  public void removeObjekt( int id ) {

    if( id <= 999 ) { // empfänger gewählt
      for( int i = 0; i < empfaenger.size(); i++ ) {
        if( ( ( Empfaenger )empfaenger.get( i ) ).getId() == id ) {
          empfaenger.remove( i ); // empfaenger loeschen
        }
      }
      // zugehörige Strecke löschen
      for( int i = 0; i < strecken.size(); i++ ) {
        if( ( ( Strecke )strecken.get( i ) ).getZiel() == id ) {
          strecken.remove( i );
          i = -1;
        }
      }

      zeichenPane.setStrecken( strecken );
      zeichenPane.setEmpfaenger( empfaenger );
    }
    else if( id >= 1000 && id <= 1999 ) { // produzent gewählt
      for( int i = 0; i < produzenten.size(); i++ ) {
        if( ( ( Produzent )produzenten.get( i ) ).getId() == id ) {
          produzenten.remove( i ); // produzent löschen
        }
      }
      // zugehörige Strecke löschen

      for( int i = 0; i < strecken.size(); i++ ) {
        if( ( ( Strecke )strecken.get( i ) ).getQuelle() == id ) {
          strecken.remove( i );
          i = -1;
        }
      }

      zeichenPane.setStrecken( strecken );
      zeichenPane.setProduzenten( produzenten );
    }
    else if( id >= 2000 && id <= 2999 ) { // transport gewählt
      for( int i = 0; i < transporter.size(); i++ ) {
        if( ( ( Transporter )transporter.get( i ) ).getId() == id ) {
          transporter.remove( i );
        }
      }
      // positionen der verbleibenden transporter neu setzen, damit sich alle nach rechts verschieben
      int tmp_transportPos = 460;
      for( int i = 0; i < transporter.size(); i++ ) {
        ( ( Transporter )transporter.get( i ) ).setX( tmp_transportPos );
        tmp_transportPos -= 60;
      }
      transportPos += 60;
      transporterPane.setTransporter( transporter );
    }
    else if( id >= 3000 && id <= 3999 ) { // strecke gewählt
      for( int i = 0; i < strecken.size(); i++ ) {
        if( ( ( Strecke )strecken.get( i ) ).getId() == id ) {
          strecken.remove( i );
        }
      }
    }
    mainFrame.repaint();
  }

  /**
   * Diese Methode dient dazu, eine temporaere Linie zu zeichnen, so lange der Benutzer streckeZeichnen ausgewählt hat
   *
   * @param x2 xKoordinate der bewegung
   * @param y2 yKoordinate der bewegung
   * @param objektId
   */
  public void printTempLine( int x2, int y2, int objektId ) {
    zeichenPane.setPrintLine(); // damit die zeichenPane weiss, dass sie jetzt eine linie zeichnen muss
    zeichenPane.setLineKoordinaten( popUpX - 10, popUpY - 10, x2, y2 );
    mainFrame.repaint();
  }

  /**
   * Diese Methode entscheidet ob die temporäre Linie einen richtigen Endpunkt hat und dauerhaft angelegt werden soll
   *
   * @param x2 xKoordinate des klicks
   * @param y2 yKoordinate des klicks
   * @param objektId des objektes, von wo aus die linie gezogen wurde
   */
  public void printLine( int x2, int y2, int objektId ) {

    boolean sollGezeichnetWerden = true;
    // der endpunkt der linie liegt auf einem objekt gewaehlteId ist die Id dieses Objektes
    if( this.isInPos( x2, y2 ) ) {
      Strecke s;
      // gibt es diese Strecke bereits? dann nicht zeichnen
      for( int i = 0; i < strecken.size(); i++ ) {
        s = ( Strecke )strecken.get( i );
        if( s.getQuelle() == objektId && s.getZiel() == gewaehlteId ) {
          sollGezeichnetWerden = false;
        }
        else if( s.getQuelle() == gewaehlteId && s.getZiel() == objektId ) {
          sollGezeichnetWerden = false;
        }

      }

      if( sollGezeichnetWerden ) {
        // anfangspunkt der Linie liegt auf einem empfänger
        if( objektId <= 999 ) {

          // ist der endpunkt ein produzent? dann zeichnen
          if( gewaehlteId >= 1000 && gewaehlteId <= 1999 ) {
            Produzent p;
            for( int i = 0; i < produzenten.size(); i++ ) {
              p = ( Produzent )produzenten.get( i );
              if( p.getId() == gewaehlteId ) {
                this.addStrecke( ( int )( p.getX() ), ( int )( p.getY() ), objektId );
              }
            }
          }

        }
        else if( objektId >= 1000 && objektId <= 1999 ) { // anfangspunkt der linie liegt auf einem produzenten
          if( gewaehlteId > 999 ) {
            mainFrame.repaint();
          }
          else { // ist der endpunkt ein empfänger? dann zeichnen
            Empfaenger e;

            for( int i = 0; i < empfaenger.size(); i++ ) {
              e = ( Empfaenger )empfaenger.get( i );

              if( e.getId() == gewaehlteId ) {
                this.addStrecke( ( int )( e.getX() ), ( int )( e.getY() ), objektId );
              }
            }
          }

        }
      }
      zeichenPane.setStrecken( strecken ); // neuen Vector zum zeichnen übergeben
      mainFrame.repaint();
    }
    else { // der endpunkt der linie liegt auf keinem objekt !
      mainFrame.repaint();
    }

  }

  /**
       * Diese Methode dient dazu, um bei der Menuauswahl "neu", alle Objekte zu loeschen und ein leeres ZeichenFeld aufzubauen
   */
  public void leereAlleVectoren() {

    // alle vectoren loeschen
    empfaenger.removeAllElements();
    transporter.removeAllElements();
    produzenten.removeAllElements();
    strecken.removeAllElements();

    // alle ids wieder auf anfang zurück-setzen
    this.setEmpfaengerId( 1 );
    this.setProduzentId( 1000 );
    this.setTransporterId( 2000 );
    this.setStreckenId( 3000 );

  }

  /**
   * Diese Methode dient dazu, um das Ergebnis bzw. die Einzelergebnisse einer Berechnung zu parsen und aufzubereiten.
   * Das Ergebnis wird später in dem Textarea ausgeben.
   *
   * @param erg ein Vector, der die Ergebnisse der Berechnung enthält
   * @return den Ergebnis-String
   */
  public String parseErg( Vector erg ) {

    String ergebnis = "";

    //Testausgabe
    System.out.println( "Ergebnis-Anfang: Groesse " + erg.size() );
    for( int i = 0; i < erg.size(); i++ ) {
      System.out.println( "Transporter: " + ( ( Ergebnis )erg.get( i ) ).getTransporter() );
      System.out.println( "Ladung: " + ( ( Ergebnis )erg.get( i ) ).getLadung() );
      System.out.println( "Quelle: " + ( ( Ergebnis )erg.get( i ) ).getQuelle() );
      System.out.println( "Ziel: " + ( ( Ergebnis )erg.get( i ) ).getZiel() );
      System.out.println( "Kosten: " + ( ( Ergebnis )erg.get( i ) ).getKosten() );

    }
    System.out.println( "\nErgebnis-Ende" );
    String tmpQuellenName = null;
    String tmpZielName = null;
    Ergebnis tmpErgebnis;

    if( erg.size() == 0 ) {
      ergebnis = "Aufgabe unloesbar oder Eingabe fehlerhaft!";
    }
    else {
      for( int i = 0; i < erg.size(); i++ ) {
        tmpErgebnis = ( Ergebnis )erg.get( i );
        if( tmpErgebnis.getKosten() != 0 ) {
          // empfaenger mit ergebnis-ziel vergleichen
          for( int j = 0; j < empfaenger.size(); j++ ) {
            if( ( ( Empfaenger )empfaenger.get( j ) ).getId() == ( ( Ergebnis )erg.get( i ) ).getZiel() ) {
              tmpZielName = ( ( Empfaenger )empfaenger.get( j ) ).getName();
            }
          }
          // produzenten mit ergebnis-quelle vergleichen
          for( int j = 0; j < produzenten.size(); j++ ) {
            if( ( ( Produzent )produzenten.get( j ) ).getId() == ( ( Ergebnis )erg.get( i ) ).getQuelle() ) {
              tmpQuellenName = ( ( Produzent )produzenten.get( j ) ).getName();
            }
          }

          ergebnis += "    " + ( ( Ergebnis )erg.get( i ) ).getTransporter() + " faehrt mit Ladung ";
          ergebnis += ( ( Ergebnis )erg.get( i ) ).getLadung() + " von ";
          ergebnis += tmpQuellenName + " nach " + tmpZielName;
          ergebnis += " mit Kosten: " + ( ( Ergebnis )erg.get( i ) ).getKosten() + "\n";

        }
      }
    }

    return ergebnis;

  }

  /**
   * Diese Methode dient dazu, um das Gesamt-Ergebnis einer Berechnung zu parsen und aufzubereiten.
   * Das Ergebnis wird später in dem Textfeld ausgeben.
   *
   * @param erg ein Vector, der die Ergebnisse der Berechnung enthält
   * @return den Ergebnis-String
   */

  public String berechneGesamt( Vector erg ) {

    double gesamtErgebnis = 0;

    for( int i = 0; i < erg.size(); i++ ) {
      gesamtErgebnis += ( ( Ergebnis )erg.get( i ) ).getKosten();
    }

    return String.valueOf( gesamtErgebnis );

  }

  /***************************************************
   * setter - Methoden
   ***************************************************/

  /**
   * Diese Methode setzt das die Zeichenfläche der Produzenten bzw. Empfänger der Anwendung.
   *
   * @param z aktuelles zeichenPane
   */
  public void setZeichenPane( ZeichenPane z ) {
    zeichenPane = z;
  }

  /**
   * Diese Methode setzt die Zeichenfläche für die Transporter der Anwendung.
   *
   * @param t aktuelles transporterPane
   */
  public void setTransporterPane( TransporterPane t ) {
    transporterPane = t;
  }

  /**
   * Diese Methode setzt die Id des gerade gewählten Objekts.
   *
   * @param id des ausgewählten objektes
   */
  public void setGewaehlteId( int id ) {
    gewaehlteId = id;
  }

  /**
   * Diese Methode setzt die Empfänger, die aktuell modelliert sind und sich auf der Zeichenfläche befinden.
   *
   * @param _empfaenger angelegte empfaenger werden an das zeichenPane uebergeben (Datei oeffnen)
   */
  public void setEmpfaenger( Vector _empfaenger ) {
    empfaenger = _empfaenger;
    zeichenPane.setEmpfaenger( empfaenger );
  }

  /**
   * Diese Methode setzt die Produzenten, die aktuell modelliert sind und sich auf der Zeichenfläche befinden.
   *
   * @param _produzenten angelegte produzenten werden an das zeichenPane uebergeben (Datei oeffnen)
   */
  public void setProduzenten( Vector _produzenten ) {
    produzenten = _produzenten;
    zeichenPane.setProduzenten( produzenten );
  }

  /**
   * Diese Methode setzt die Strecken, die aktuell modelliert sind und sich auf der Zeichenfläche befinden.
   *
   * @param _strecken angelegte strecken werden an das zeichenPane uebergeben (Datei oeffnen)
   */
  public void setStrecken( Vector _strecken ) {
    strecken = _strecken;
    zeichenPane.setStrecken( strecken );
  }

  /**
   * Diese Methode setzt die Transporter, die aktuell modelliert sind und sich auf der Zeichenfläche befinden.
   *
   * @param _transporter angelegte transporter werden an das transporterPane uebergeben (Datei oeffnen)
   */
  public void setTransporter( Vector _transporter ) {
    transporter = _transporter;
    transporterPane.setTransporter( transporter );
  }

  /**
   * Diese Methode setzt die Postition des Transporters.
   *
   * @param pos
   */
  public void setTransportPos( double pos ) {
    transportPos = pos;
  }

  /**
   * Diese Methode setzt die Id des Empfängers.
   *
   * @param id des Empfängers
   */
  public void setEmpfaengerId( int id ) {
    empfaengerId = id;
  }

  /**
   * Diese Methode setzt die Id des Produzenten.
   *
   * @param id des Produzenten
   */

  public void setProduzentId( int id ) {
    produzentId = id;
  }

  /**
   * Diese Methode setzt die Id einer Strecke.
   *
   * @param id der Strecke
   */

  public void setStreckenId( int id ) {
    streckenId = id;
  }

  /**
   * Diese Methode setzt die Id eines Transporters.
   *
   * @param id des Transporters
   */

  public void setTransporterId( int id ) {
    transporterId = id;
  }

  /***************************************************
   * getter - Methoden
   ***************************************************/

  /**
   * Diese Methode liefert die Id des gewählten Objekts zurück.
   *
   * @return id des gewählten Objekts
   */
  public int getGewaehlteId() {
    return gewaehlteId;
  }

  /**
   * Diese Methode liefert den aktuellen MainFrame zurück
   * @return den aktuellen MainFrame
   */

  public MainFrame getMainFrame() {
    return mainFrame;
  }

  /**
   * Diese Methode liefert das aktuelle ZeichenPane zurück.
   *
   * @return das aktuelle ZeichenPane
   */
  public ZeichenPane getZeichenPane() {
    return zeichenPane;
  }

  /**
   * Diese Methode liefert die aktuelle Transporter-Zeichenfläche zurück.
   *
   * @return die aktuelle Transporter-Zeichenfläche
   */
  public TransporterPane getTransporterPane() {
    return transporterPane;
  }

  /**
   * Diese Methode liefert die aktuellen Empfänger zurück.
   * @return die aktuellen Empfänger
   */
  public Vector getEmpfaenger() {
    return empfaenger;
  }

  /**
   * Diese Methode liefert die aktuellen Produzenten zurück.
   * @return die aktuellen Produzenten
   */
  public Vector getProduzenten() {
    return produzenten;
  }

  /**
   * Diese Methode liefert die aktuellen Transporter zurück.
   * @return die aktuellen Transporter
   */
  public Vector getTransporter() {
    return transporter;
  }

  /**
   * Diese Methode liefert die aktuellen Strecken zurück.
   * @return die aktuellen Strecken.
   */
  public Vector getStrecken() {
    return strecken;
  }

}