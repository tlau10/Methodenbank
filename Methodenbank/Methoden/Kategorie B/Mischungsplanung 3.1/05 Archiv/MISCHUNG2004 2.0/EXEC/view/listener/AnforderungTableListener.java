/*
 * AnforderungTableListener.java
 *
 * Created on 12. Juni 2004, 20:22
 */

package view.listener;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.*;
/**
 * Dieser Listener reagiert auf bestimmte Änderungen in der Anforderung Tabelle
 * Wenn eine neue Anforderung benannt wird, so wird der Name auch in die 
 * Sortentabelle kopiert.
 * @author  hmaass
 */
public class AnforderungTableListener implements TableModelListener {
    
    private JTable jTableSorten;
    /** Konstruktor
     *  @param Sorten Die Referenz auf die Sortentabelle wird benötigt, damit wir 
                      die Werte der ersten Spalte in der Sortentabelle verändern können.
     */
    public AnforderungTableListener(JTable Sorten) {
        jTableSorten = Sorten;
    }
    /**
     * Liest aus der ersten Spalte der Anforderungstabelle die Namen der
     * Anforderungen ein und schreibt sie in die erste Spalte der Sortentabelle
     */
    public void tableChanged(TableModelEvent e) {
        // wurden die Namen der Anforderungen geändert?
        if (e.getColumn() == 0) {
            // kopiere die Namen aus der Anforderung Tabelle in die Sorten Tabelle
            TableModel tmAnforderung = (TableModel) e.getSource();
            TableModel tmSorten      = jTableSorten.getModel();
            
            int row = tmAnforderung.getRowCount();
            for (int i=0; i<row;i++) {
                String name = (String) tmAnforderung.getValueAt(i, 0);
                tmSorten.setValueAt(name, i, 0);
            }
        }
    }
}
