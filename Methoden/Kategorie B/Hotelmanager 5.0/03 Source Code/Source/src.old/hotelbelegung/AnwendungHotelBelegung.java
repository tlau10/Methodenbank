package hotelbelegung;

import javax.swing.UIManager;

/**
 * Überschrift: Hotelbelegung Beschreibung: Copyright: Copyleft (c) 2014
 * Organisation: HTWG
 *
 * @author Volker Wohlleber
 * @version 2.0 Oliver Bühler, Kilian Thiel Juni 2003
 * @Version 2.0.1 Christian Gruhler SS08
 * @Version 4.0 Vitaliy Davats, Dominique Lebert, Manuel Falkenstein WS2013/14
 */
public class AnwendungHotelBelegung {
    boolean packFrame = true;

    /** Die Anwendung konstruieren */
    public AnwendungHotelBelegung() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        mainFrame.setSize(1200, 800);
        // frame.redirectSystemsStreams();
    }

    /** Main-Methode */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        new AnwendungHotelBelegung();
    }
}