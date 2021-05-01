package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @author Mark Deppe, HTWG Konstanz, 2013
 *
 */

public class WagWhit {

    //vor der Berechnung wird von der GUI geliefert
    private static double ruestkosten;
    private static double lagerkosten[];
    private static int anzahlPerioden;
    //---------------------------------------------
    /**
     * Das Array ergebnis[Periodenanzahl][Periodenanzahl] ist die Berechnungsmatrix des Wagner-Whitin Algorithmus
     * Es wird in der Ausgabe nicht angezeigt und ist nur zur internen Berechnung da
     */
    private static double ergebnis[][];
    /**
     * Das Array minimal[Periodenanzahl][1] speichert für jede Periode den Minimalwert der Kosten
     * auf [n][0] und die Periode, in der zuletzt produziert wurde [n][1]
     */
    private static double minimal[][];
    private static double minGesamtKosten;
    /**
     * Aufbau des Arrays ausgabe[t][Wert]:
     * Wert 0 - Nachfragemenge zum Zeitpunkt t (wird per Setter von der GUI geliefert)
     * Wert 1 - Bestellmenge zum Zeitpunkt t
     * Wert 2 - Anzahl der Perioden, fÃ¼r die zum Zeitpunkt t bestellt wird
     * Wert 3 - Lagerbestand zum Zeitpunkt t
     */
    private static double ausgabe[][];
    private static Object[][] objectAusgabe;
    private static double periodenlauf[];


    /**
     * Diese Methode startet den kompletten Vorgang der Berechnung
     * Die Methode print() kann aktiviert werden, falls eine Konsolen-Ausgabe benÃ¶tigt wird
     */
    public static void start() {

        berechnen();
        ausgabeBerechnen();
        ausgabeDrehen();
//        print();
        
    }
    
    /**
     * Diese Methode berechnet die Lösungsmatrix mittels Wagner-Whitin Algorithmus
     */
    public static void berechnen() {
//    	Diese Variable speichert zur Laufzeit die Periode, in der zuletzt produziert wurde
        int produziertePeriode;
//      Falls die Nachfrage der ersten Periode 0 ist, wird dort auf keinen Fall bestellt/produziert und alle Werte werden auf 0 gesetzt
        if (ausgabe[0][0] != 0) {
            produziertePeriode = 0;
            ergebnis[0][0] = ruestkosten;
            minimal[0][0] = ruestkosten;
            minimal[0][1] = 0;
        } else {
            produziertePeriode = 1;
            ergebnis[0][0] = 0;
            minimal[0][0] = 0;
            minimal[0][1] = -1;
        }
//      Die Berechnungsmatrix wird von links nach rechts durchlaufen
        for (int i = 1; i < ergebnis.length; i++) {

//        	Die Berechnungsmatrix wird von oben nach unten durchlaufen
            for (int j = produziertePeriode; j <= i; j++) {

//            	Der Fall j-Koordinate = i-Koordinate steht für Produktion/Bestellung...
                if (j == i) {
                    ergebnis[i][j] = minimal[i - 1][0] + ruestkosten;
                } 
//              ...andersfalls wurde vorher bereits produziert und die Lagerkosten werden berechnet
                else {
                    ergebnis[i][j] = ergebnis[i - 1][j] + lagerkostenBerechnen(ausgabe[i][0], j, i);
                }
            }
//          Die Minimalkosten der aktuellen Periode werden berechnet
            double minimum = ergebnis[i][produziertePeriode];
//          Hierzu wird das Array von oben nach unten an der aktuellen Periode durchlaufen
            for (int j = produziertePeriode + 1; j <= i; j++) {

                if (ergebnis[i][j] < minimum) {

                    minimum = ergebnis[i][j];
                    produziertePeriode = j;
                }
            }
//          Die gefundenen Werte werden in das Array gespeichert
            minimal[i][0] = minimum;
            minimal[i][1] = produziertePeriode;

            minGesamtKosten = minimum;

        }
    }

    /**
     * Diese Methode berechnet die Lagerkosten seit der letzten Produktion für die neu dazugekommene Menge
     */
    private static double lagerkostenBerechnen(double nachfragemenge, int produziertePeriode, int aktuellePeriode) {

        double preis = 0;

        for (int i = produziertePeriode; i < aktuellePeriode; i++) {
            preis = preis + nachfragemenge * lagerkosten[i];
        }

        return preis;
    }
    /**
     * Diese Methode ermöglicht eine Konsolenausgabe aller berechneten Daten
     */
    public static void print() {
        for (int i = 0; i < ergebnis.length; i++) {
            for (int j = 0; j < ergebnis[i].length; j++) {
                if (j + 1 == ergebnis[i].length) {
                    System.out.println(ergebnis[i][j]);
                } else {
                    System.out.print(ergebnis[j][i] + "   ");
                }
            }

        }
        System.out.println("=================================");

        for (int i = 0; i < minimal.length; i++) {
            System.out.println("Minimum " + minimal[i][0] + " in der Periode " + minimal[i][1] + " gefunden!");
        }

        System.out.println("=================================");

        for (int i = 0; i < periodenlauf.length; i++) {
            System.out.print(periodenlauf[i] + " ");
        }

        System.out.println("=================================");

        for (int i = 0; i < ausgabe.length; i++) {
            System.out.println(ausgabe[i][0] + " --- " + ausgabe[i][1] + " --- " + (int) ausgabe[i][2] + " --- " + ausgabe[i][3]);
        }

        System.out.println("=================================");

        for (int i = 0; i < objectAusgabe.length; i++) {
            for (int j = 0; j < objectAusgabe[i].length; j++) {
                System.out.print(objectAusgabe[i][j] + " -- ");
            }
            System.out.println(" <<>>");
        }
    }
    /**
     * Diese Methode berechnet die Daten, die für die Ausgabe benötigt werden
     * Dies geschieht anhand der Daten aus der Berechnungsmatrix
     */
    public static void ausgabeBerechnen() {

        int produziertePeriode = (int) minimal[minimal.length - 1][1];
        double bestellmenge = 0;
        int anzPerioden = 0;
        double lagerbestand = 0;

       //      Die Berechnungsmatrix wird von rechts nach links durchlaufen
        for (int i = ergebnis.length - 1; i >= 0; i--) {
//          Falls in dieser Periode Produziert wurde
            if (i == produziertePeriode) {
                bestellmenge += ausgabe[i][0];
                ausgabe[i][1] = bestellmenge;
                anzPerioden += 1;
                ausgabe[i][2] = anzPerioden;
                periodenlauf[i] = ergebnis[i][i];
                ausgabe[i][3] = lagerbestand;
                ausgabe[i][4] =  lagerbestand * getLagerkosten(i); // berechnung lagerkosten... aufgrund des gegebenen Algorithmus kann die Lagerzeit vernachlässigt werden, da es für jeden schleifendurchlauf jew. eine ZE wäre (also ME*GE*1) +++
                bestellmenge = 0;
                lagerbestand = 0;
                anzPerioden = 0;
                
                if (i > 0) {
                    produziertePeriode = (int) minimal[i - 1][1];
                }
//          Falls in dieser Periode nicht Produziert wurde
            } else {
                periodenlauf[i] = ergebnis[i][produziertePeriode];
                bestellmenge += ausgabe[i][0];
                anzPerioden += 1;
                ausgabe[i][3] = lagerbestand;
                ausgabe[i][4] = (lagerbestand) * getLagerkosten(i); // berechnung lagerkosten...+++
                lagerbestand += ausgabe[i][0];

    
            }

        }

    }

    /**
     * Diese Methode dreht die Matrix um 90Â°, da dies fÃ¼r die JTable benÃ¶tigt wird
     */
    public static void ausgabeDrehen() {

        objectAusgabe = new Object[ausgabe.length][ausgabe[0].length + 1];

        for (int i = 0; i < ausgabe.length; i++) {

            objectAusgabe[i][0] = (i + 1);

            for (int j = 0; j < ausgabe[i].length; j++) {

                objectAusgabe[i][j + 1] = ausgabe[i][j];

            }

        }

    }

    public static void setRuestkosten(double ruestkosten) {
        WagWhit.ruestkosten = ruestkosten;
    }

    public static void setAnzahlPerioden(int anzahlPerioden) {
        WagWhit.anzahlPerioden = anzahlPerioden;
        lagerkosten = new double[anzahlPerioden];
        ergebnis = new double[anzahlPerioden][anzahlPerioden];
        minimal = new double[anzahlPerioden][2];
        ausgabe = new double[anzahlPerioden][5];
        periodenlauf = new double[anzahlPerioden];
    }

    public static double getRuestkosten() {
        return ruestkosten;
    }

    public static double[] getLagerkosten() {
        return lagerkosten;
    }

    public static double getLagerkosten(int Periode) {
        return lagerkosten[0];
    }

    public static int getAnzahlPerioden() {
        return anzahlPerioden;
    }

    public static void setPeriodenbedarf(int[] periodenbedarf) {

        ausgabe = new double[periodenbedarf.length][5];
        for (int i = 0; i < ausgabe.length; i++) {
            ausgabe[i][0] = periodenbedarf[i];
        }
    }

    public static void setLagerkosten(double[] lagerkosten) {
        WagWhit.lagerkosten = lagerkosten;
    }

    public static Object[][] getObjectAusgabe() {
        return objectAusgabe;
    }

    public static double getMinGesamtKosten() {
        return minGesamtKosten;
    }
}
