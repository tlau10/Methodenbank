/*
 * eingabe.java
 *
 * Created on 14. Mai 2004, 18:09
 */

package mischungsplaner.model.eingabe;

import java.util.*;
import mischungsplaner.model.*;
/**
 * Eine Eingabe enth�lt die Anforderungen sowie die Sorten
 * @author  hmaass
 */
public class Eingabe {
    
    private LinkedHashMap anforderungen;
    private LinkedHashMap sorten;
    private int anforderungenSize;
    /** Konstruktor  */
    public Eingabe() {
        anforderungen = new LinkedHashMap();
        sorten = new LinkedHashMap();
        anforderungenSize = 0;
    }
    /** f�gt eine Sorte hinzu 
     *  @param toAdd die neue Sorte */
    public void addSorte(Sorte toAdd) {
        Integer key = new Integer(toAdd.getId());
        sorten.put(key,toAdd);
   }
    /** gibt die Sorte mit der �bergebenen ID zur�ck
     *  @param id ID der Sorte
     */
   public Sorte getSorte(int id) {
       Integer key = new Integer(id);
       return (Sorte) sorten.get(key);
   }
   /** l�scht die Sorte mit der �bergebenen ID
    *  @param id ID der Sorte */
   public void removeSorte(int id) {
       Integer key = new Integer(id);
       sorten.remove(key);
   }
   /** gibt einen Iterator auf die Sorten zur�ck */
   public Iterator getSorten() {
       return sorten.values().iterator();
   }
   /** f�gt eine Anforderung hinzu.
    *  @param toAdd die neue Anforderung */ 
    public void addAnforderung(Anforderung toAdd) {
        Integer key = new Integer(toAdd.getId());
        anforderungen.put(key, toAdd);
        anforderungenSize++;
    }
    /** gibt die Anfoderung mit der �bergebenen ID zur�ck
     * @param id die ID der Anforderung */
    public Anforderung getAnforderung(int id) {
        Integer key = new Integer(id);
        return (Anforderung) anforderungen.get(key);
   }
   /** gibt einen Iterator auf die Anforderungen zur�ck
    */
    public Iterator getAnforderungen() {
        return anforderungen.values().iterator();
    }
    /** gibt die Anzahl der Anforderungen zur�ck */
    public int getAnforderungenSize() {
        return anforderungenSize;
    }
    
    /** gibt die Anzahl der Sorten zur�ck */
    public int getSortenSize() {
        return sorten.size();
    }
        
    }
