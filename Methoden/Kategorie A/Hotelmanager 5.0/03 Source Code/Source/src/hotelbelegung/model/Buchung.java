package hotelbelegung.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Speichert Daten über eine Buchung.
 */
public class Buchung implements Serializable {

    public Date startDatum;
    public int anzahlTage;
    public int gesamtPreis;

    public Buchung(Date startDatum, int anzahlTage, int gesamtPreis) {
        this.startDatum = startDatum;
        this.anzahlTage = anzahlTage;
        this.gesamtPreis = gesamtPreis;
    }
}
