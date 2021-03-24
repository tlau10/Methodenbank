package jobshop;

import java.util.ArrayList;
import java.util.List;


/*
 * Ermittelt alle möglichen Kombinationen in welcher Reihenfolge die Produkte
 * bearbeitet werden können. Soll dazu dienen die Bearbeitungsreihenfolge wie im
 * alten JobShop zu ermitteln, da die Lösung mit dem Solver zu rechenlastig ist.
 * Für genauere Informationen bitte die Doku des alten JobShops lesen.
 *
 * PS: Dashier ist natürlich noch nicht fertig. ;-)
 */
public class Permutator {
    
    public int maxIndex;
    public List<Integer> positionen = new ArrayList<Integer>();
    private List<List<Integer>> permutationen = new ArrayList<List<Integer>>();

    public Permutator(int anzahlProdukte) {
	for (int i = 1; i <= anzahlProdukte; i++) {
	    this.positionen.add(i);
	}
	this.maxIndex = this.positionen.size() - 1;
    }

    public static void main(String[] args) {
	Permutator jc = new Permutator(3);
        jc.permute(jc.positionen, jc.maxIndex);
	jc.print();
    }

    public void swap(List<Integer> a, int i, int j) {
        int temp = a.get(i);
        a.set(i, a.get(j));
	a.set(j, temp);
    }

    public void permute(List<Integer> a, int endIndex) {
        if (endIndex == 0){
	    List<Integer> copy = new ArrayList<Integer>();
	    copy.addAll(a);
            this.permutationen.add(copy);
        } else {
            permute(a, endIndex - 1);
            for (int i = 0; i <= endIndex - 1; i++){
                swap(a, i, endIndex);
                permute(a, endIndex - 1);
                swap(a, i, endIndex);
            }
        }
    }
    
    public void print() {
        for (List<Integer> l : this.permutationen) {
	    System.out.println(l.toString());
	}
    }
}
