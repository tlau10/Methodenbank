package Dakin;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
import com.jrefinery.ui.ApplicationFrame;
import com.jrefinery.ui.RefineryUtilities;


class TreeDakin {

	private matrix eingabeMatrix; // Matrix mit evt. zugefuegten Restritktionen
	private Vector ergebnisVektor; // Ergebnisvektor vom Solver
	private TreeDakin right; // rechtes Kindblatt
	private TreeDakin left; // linkes Kindblatt
	private int depth; // Tiefe
	private String tmp_restriction; // zugefügte Restriktion
	private static float zielFunktionsWert; // optimaler Zielwert
	private LPsolve lpsolve;
	private boolean optimal; // Optimallösung setzen
        // TM:
        private TreeDakin father;



	TreeDakin()
	{
		eingabeMatrix = null;
		tmp_restriction = new String();
		depth=0;
		zielFunktionsWert=-1;
		lpsolve = new LPsolve();
		lpsolve.setPath(".");
	}
	TreeDakin(matrix m,String solverpath)
	{// constructor der beim ersten anlegen eines objektes auf diese
	 // klasse aufgerufen wird
		eingabeMatrix= new matrix (m);
		// TM
                father=null;
                right=null;
		left=null;
		depth=0;
		zielFunktionsWert=0;
		optimal=true;
		lpsolve = new LPsolve();
		lpsolve.setPath(solverpath);
		calculate();
	}
	TreeDakin(matrix m,String s, int muell, TreeDakin _father)
	{// konstruktor der von objekten, die in dieser klasse (blaetter
	 // des baumes), aufgerufen wird
		depth=muell+1;
		// TM
                father=_father;
                right=null;
		left=null;
		optimal=true;
		eingabeMatrix= m;
		tmp_restriction = s;
		lpsolve = new LPsolve();
		calculate();
	}
	public void calculate()
	{

                Float [] tmpArray;

		String bVektorString = new String();
		int x1,x2;
                int first=0;
                int x_index;
		float tmp_float;
		sendSolver();

		if (ergebnisVektor.get(0) == "ERROR")
		{
			optimal=false;
			return;  // abbrechen
		}


                /* formatieren der Werte von object zu float, mit benutzung der klasse Float */
                // anlegen eines float-Arrays mit passender Größe
		tmpArray = new Float[ergebnisVektor.size()];
                // das erste Element des Ergebnisvektors (der Zielfunktionswert)
                // als float-Wert in das tmpArray eintragen
		tmpArray[0]= new Float((String) ergebnisVektor.get(0));
                // in einer float Variablen den auf zwei Stellen gerundeten
                // Wert für die Ausgabe speichern
		tmp_float=(float) (Math.round(tmpArray[0].floatValue()*100))/100;
                //den auf zwei Stellen gerundeten Wert wieder in den
                //  ergebnisVektor eintragen
		ergebnisVektor.setElementAt((String) Float.toString(tmp_float),0);
                // Schleife über alle xi-Ergebnisse
		for (int i=1; i< ergebnisVektor.size();i++)
		{
                        // Ergebniswert des Elementes xi als float-Wert in
                        // das tmpArray eintragen
			tmpArray[i]= new Float((String) ergebnisVektor.get(i));
			// suchen nach der ersten nichtganzzahligen loesung
			if (first==0 && !(tmpArray[i].floatValue()%2== 1.0 || tmpArray[i].floatValue()%2== 0.0))
			{
                            // Dieses Ergebnis ist nicht ganzzahlig
                            optimal=false;
			    first=i;
			}
                        // in einer float Variablen den auf zwei Stellen gerundeten
                        // Wert für die Ausgabe speichern
			tmp_float=(float) (Math.round(tmpArray[i].floatValue()*100))/100;
                        // den gerundeten Wert wieder in den ergebnisVektor eintragen
			ergebnisVektor.setElementAt((String) Float.toString(tmp_float),i);
		}



                // wenn es nur ganzzahlige Lösungen gab, setze privates Feld
                // zielFunktionsWert
		if (first==0)
			setZiel(tmpArray[0].floatValue());

		// wenn noch ein nichtganzahliges element vorhanden ist
		// und der Zielfunktionwert neu gesetzt wurde
		if (first!=0 && checkZiel(tmpArray[0].floatValue()))
		{
		        // zuerst den baum nach rechts durchgehen
                        // das erste nichtganzzahlige Element holen und
                        // auf die nächste Ganzzahl x1 abrunden, x2 aufrunden
			x1 = (int) tmpArray[first].intValue();
                        x2 = x1+1;

                        // Zusätzliche Zeile für die neue Restriktion
                        // in der Matrix schaffen
			eingabeMatrix.addRow();

                        // hilfsvariable setzen
                        x_index=first;

                        // ergebnisvector hat im ersten element den wert der
                        // zielfunktion stehen, deshalb ist das erste
                        // nichtganzzahlige element eins
                        // zu weit in der linearen gleichung
			first--;

                        // erweitere String um neue Ganzzahlgrenze
			bVektorString += x1;

			// Kopiere eingabeMatrix in Hilfsmatrix
			matrix tempMatrix = new matrix(eingabeMatrix);

                        // Schleife über Spalten der Matrix, um zusätzliche
                        // Restriktion (beispielsweise x <= 2)
                        // in die neue Matrixzeile zu schreiben
			for (int i=0; i<tempMatrix.getNumCols();i++)
			{
                              // schreibe als Koeffizient für den betreffenden
                              // x-Wert (also der, der ein nichtganzzahliges
                              // Ergebnis geliefert hat) eine 1
                              if (i==first)
                                tempMatrix.setValueAt(tempMatrix.getNumRows()-1,i,"1");
                              // schreibe als Relation das <=-Zeichen
                              else if (i==(tempMatrix.getNumCols()-2))
				tempMatrix.setValueAt(tempMatrix.getNumRows()-1,i,"<=");
                              // schreibe in den b-Vektor den errechneten Wert
                              // x1 (als String)
                              else if ((tempMatrix.getNumCols()-1) ==i)
				tempMatrix.setValueAt(tempMatrix.getNumRows()-1,i,bVektorString);
                              // belege alle anderen x-Koeffizienten mit 0
                              else
				tempMatrix.setValueAt(tempMatrix.getNumRows()-1,i,"0");
			}
			bVektorString="";
                        // den String fuer die restriktion bauen, die im Tree
                        // ausgegeben werden soll
                        bVektorString += "   x" + x_index +" <= " + x1;
                        // ein neues linkes blatt hinzufuegen
			left = new TreeDakin(tempMatrix,bVektorString,depth, this);
			// baum nach rechts durchgehen
			bVektorString="";
                        bVektorString += x2;

			// analog zu oben!
                        matrix m2 = new matrix(eingabeMatrix);
			for (int i=0; i<m2.getNumCols();i++)
			{
				if (first ==i)
					m2.setValueAt(m2.getNumRows()-1,i,"1");
				else if ((m2.getNumCols()-2) ==i)
					m2.setValueAt(m2.getNumRows()-1,i,">=");
				else if ((m2.getNumCols()-1) ==i)
					m2.setValueAt(m2.getNumRows()-1,i,bVektorString);
				else
					m2.setValueAt(m2.getNumRows()-1,i,"0");
			}
			bVektorString="";
                        bVektorString += "   x" + x_index +" >= " + x2;
			right = new TreeDakin(m2,bVektorString,depth, this);

                        // TM: Zusätzliche Zeile in der EingabeMatrix wieder löschen
                        eingabeMatrix.removeRow();
		}
		else
		{
                  // alle loesungen sind ganzzahlig
                  System.out.println(zielFunktionsWert);
                  return;
		}
	}

        public void setMatrix(matrix m)
	{
		eingabeMatrix=m;
	}
        public matrix getMatrix() {
          return eingabeMatrix;
        }
	public void printSolution()
	{
		eingabeMatrix.printMatrix();
	}
	public void sendSolver()
	{
                ergebnisVektor = lpsolve.calculate(eingabeMatrix);
	}
	public String getRestriction()
	{
		return tmp_restriction;
	}
	public TreeDakin getRight()
	{
		return right;
	}
	public TreeDakin getLeft()
	{
		return left;
	}
	public void incrementDepth()
	{
		depth++;
	}
	public void decrementDepth()
	{
		depth--;
	}
	public int getDepth()
	{
		return depth;
	}
	public void setZiel(float tmp)
	{
		zielFunktionsWert=tmp;
	}
	public boolean checkZiel(float tmp)
	{
		if (tmp > zielFunktionsWert)
			return true;
		return false;
	}
	public Vector getData()
	{
		return ergebnisVektor;
	}
	public boolean getInfoOpt()
	{
		return optimal;
	}
	public void setOpt(boolean what)
	{
		optimal=what;
	}
        //TM
        public TreeDakin getFather()
        {
                return father;
	}
}

