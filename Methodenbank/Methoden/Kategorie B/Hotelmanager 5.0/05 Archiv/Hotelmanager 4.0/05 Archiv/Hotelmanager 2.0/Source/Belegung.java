/*
 * Belegung.java
 *
 * Created on 7. Januar 2003, 13:35
 */

package hotelbelegung;
import java.util.*;
import java.text.*;

/**
 *
 * @author  Florian Raiber
 */
public class Belegung {

    private DateFormat outFormat = new SimpleDateFormat("dd.MM.yyyy");
    private DateFormat compareFormat = new SimpleDateFormat("yyyyMMdd");

    private Hashtable belegung;
    private int[] belegungErfahrung;
    private IFZufallszahlen zufallsZahl;
    // Anfangsdatum
    private int year = 2001;
    private int month = 1;
    private int day = 1;

    /** Creates a new instance of Belegung */
    public Belegung() {
        zufallsZahl = new Normalverteilung(30,200);
        belegung = new Hashtable();
        belegungErfahrung = new int[2];
        this.setRandomBelegung();
    }

    public void setRandomBelegung() {
        Calendar calendar = new GregorianCalendar();
        Calendar endDatum = new GregorianCalendar();
        // bis zum 31.01. fünf jahre nach aktuellem datum

        endDatum.set(endDatum.DAY_OF_MONTH,31);
        endDatum.set(endDatum.MONTH,endDatum.JANUARY);
        endDatum.add(endDatum.YEAR,6);

        calendar.set(year,month,day);
        while(true) {
            belegungErfahrung[0] = new Double(zufallsZahl.nextDouble()).intValue(); //Akt. Belegung
            belegungErfahrung[1] = new Double(zufallsZahl.nextDouble()).intValue(); //Erfahrungswert
            belegung.put(outFormat.format(calendar.getTime()),belegungErfahrung.clone());
            if(outFormat.format(endDatum.getTime()).equals(outFormat.format(calendar.getTime()))) {
                System.out.println("Zufallsdaten generiert");
                break;
            }
            calendar.add(calendar.DATE,1);
        }
        calendar.set(year,month,day);

        while(true) {
            int tmp[]=(int[])belegung.get(outFormat.format(calendar.getTime()));
//            System.out.println("Datum: "+outFormat.format(calendar.getTime())+"  Belegung: "+tmp[0]+"  Erfahrungswert: "+tmp[1]);
            if(outFormat.format(endDatum.getTime()).equals(outFormat.format(calendar.getTime()))) {
                break;
            }
            calendar.add(calendar.DATE,1);
        }
        this.setBuchung(new GregorianCalendar());
    }

    public int getBelegung(Date date) {
        int tmp[]=(int[])belegung.get(outFormat.format(date));
        return tmp[0];
    }

    public int getErfahrungswert(Date date) {
       int tmp[]=(int[])belegung.get(outFormat.format(date));
        return tmp[1];
    }

    public void setBuchung(Calendar date) {
        int tmp[]=(int[])belegung.get(outFormat.format(date.getTime()));
        tmp[0]++;
        belegung.put(outFormat.format(date.getTime()),tmp);
    }
}
