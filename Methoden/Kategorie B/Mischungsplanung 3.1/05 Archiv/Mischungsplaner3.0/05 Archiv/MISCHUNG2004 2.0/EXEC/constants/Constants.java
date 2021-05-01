package constants;

/*
 * constants.java
 *
 * Created on 31. März 2004, 22:14
 */

/**
 * Klasse für Konstante Werte
 * @author  hmaass
 */
public class Constants {
    
    public final static String ERROR_LOAD_EINGABE                 = "Aus dieser Datei konnte keine Eingabe ausgelesen werden.";
    public final static String ERROR_INPUT_TABLES                 = "Fehler bei der Eingabe:";
    public final static String ERROR_INPUT_TABLES_NULL_SORTE      = "Eine oder mehrere Sorten haben nur Nullwerte";
    public final static String ERROR_INPUT_TABLES_CAST_EXCEPTION  = "Bitte geben Sie nummerische Werte ein.";
    public final static String ERROR_NO_SORTE_DEFINED             = "Sie haben keine Sorte definiert.";
    
    public final static String ERROR_LP_INFEASABLE          = "Das Problem ist nicht lösbar.";
    public final static String ERROR_LP_UNBOUNDED           = "Das Problem hat unendlich viele Lösungen.";
    public final static String ERROR_NO_COLUMN_SELECTED     = "Sie haben keine Spalte ausgewählt";
    
    public final static int ROWHEIGHT = 24;
    public final static int SCRWIDTH  = 800;
    public final static int SCRHEIGHT = 600;
    public final static int PFADDIALOG_WIDTH  = 400;
    public final static int PFADDIALOG_HEIGHT = 350;
}
