package hotelbelegung.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuchungStore implements Serializable {

    private static final String FILE_STORE = "buchungen.dat";

    private static final long serialVersionUID = -6235122585605403159L;

    private List<Buchung> buchungen;

    public void neueBuchung(Buchung b) {
        buchungen.add(b);
    }

    public List<Buchung> getBuchungen() {
        return buchungen;
    }

    public BuchungStore() {
        buchungen = lesen();
    }

    private List<Buchung> lesen() {
        List<Buchung> bookings = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(FILE_STORE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            bookings = (List<Buchung>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Buchungen gelesen vom " + FILE_STORE);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return bookings;
    }

    public void speichern() {
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_STORE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.buchungen);
            out.close();
            fileOut.close();
            System.out.println("Buchungen gespeichert unter " + FILE_STORE);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
