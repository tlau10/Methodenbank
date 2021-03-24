package jobshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFormattedTextField;
import lpsolve.*;

/*
 * Hier wird das LP-Modell erstellt. Mit Hilfe des LP-Solve wird dann das Optimum
 * berechnet und als ResultList an die JobShopView zurückgegeben.
 */
public class JobCalculator {

    private int sumDauer, anzM, anzP;
    private String separator;

    public JobCalculator() {
	this.sumDauer = 0;
	this.anzM = 0;
	this.anzP = 0;
	this.separator = " ";
    }

    public ResultList calculate(List<List<JFormattedTextField>> jFTFieldList) {
	this.sumDauer = this.getMaxDuration(jFTFieldList);
	this.anzM = jFTFieldList.size();
	this.anzP = jFTFieldList.get(0).size();
	List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
	matrix.addAll(this.getDurationRestrictions(jFTFieldList));
	matrix.addAll(this.getMachineRestrictions());
	matrix.addAll(this.getProductRestrictions());
	matrix.addAll(this.getRewardRestrictions());
//	for (Map<String, String> map : matrix) {
//	    System.out.print(map.get("function"));
//	    System.out.print(map.get("operator") + this.separator);
//	    System.out.println(map.get("result"));
//	}
	return this.getResult(matrix);
    }

    private ResultList analyseResult(double[] tmpResArr) {
	int anzVariablen = this.anzP * this.anzM * this.sumDauer;
	List<Double> tmpRes = new ArrayList<Double>();
	for (double d : tmpResArr) {
	    tmpRes.add(d);
	}
	int maxDauer = 0;
	/* Die Variablen die den Wert 1 besitzen zeigen den Zeitpunkt (z), zu
	   welchen auf Maschine (m) das Produkt (p) produziert wird. */
	List<Result> sort = new ArrayList<Result>();
	for (int m = 1; m <= this.anzM; m++) {
	    List<Double> schnipp = tmpRes.subList((m - 1) * this.anzP * this.sumDauer, m * this.anzP * this.sumDauer);
	    for (int p = 1; p <= this.anzP; p++) {
		List<Double> schnapp = schnipp.subList((p - 1) * this.sumDauer, p * this.sumDauer);
		for (int z = 1; z <= this.sumDauer; z++) {
		    if (schnapp.get(z - 1) == 1) {
			Result zmp = new Result(z, m, p);
			sort.add(zmp);
			if(maxDauer < z) {
			    maxDauer = z;
			}
		    }
		}
	    }
	}
	ResultList result = new ResultList(anzM, anzP, maxDauer, sort);
//	System.out.println("aM: " + anzM + " aP: " + anzP + "maxD: " + maxDauer);
//	for (Result result1 : sort) {
//	    System.out.print("M" + result1.maschine);
//	    System.out.print(" P" + result1.produkt);
//	    System.out.println(" Z" + result1.zeitpunkt);
//	}
	return result;
    }

    // Produkte sollen möglichst am Stück bearbeitet werden.
    private List<Map<String, String>> getRewardRestrictions() {
	List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
	int anzRestriktionen = this.sumDauer * this.anzP * this.anzM;
	int anzVariablen = this.anzP * this.anzM * this.sumDauer;
	for (int i = 1; i <= anzRestriktionen; i++) {
	    Map<String, String> rest = new HashMap<String, String>();
	    String variables = "";
	    for (int j = 1; j <= anzVariablen * 2; j++) {
		if (j <= anzVariablen && (j == i || j == i + 1 && i % this.sumDauer > 0)) {
		    variables += "1" + this.separator;
		} else if (j == i + anzVariablen) {
		    variables += "-2" + this.separator;
		} else {
		    variables += "0" + this.separator;
		}
	    }
	    rest.put("function", variables);	// linke (x-)seite der restriktion "x1+x2+x3" = y
	    rest.put("operator", ">=");		// Mitte der restriktion x1+x2+x3 "=" y
	    rest.put("result", "0");		// rechte (y-)seite der restriktion x1+x2+x3 = "y"
	    matrix.add(rest);
	}
	return matrix;
    }

    // Ein Produkt kann pro Zeitintervall nur auf einer Maschine bearbeitet werden.
    private List<Map<String, String>> getProductRestrictions() {
	// Restriktionen erstellen
	List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
	int anzRestriktionen = this.sumDauer * this.anzP;
	int anzVariablen = this.anzP * this.anzM * this.sumDauer;
	int aktuelleZE = 1;
	int aktuellesP = 1;
	for (int i = 1; i <= anzRestriktionen; i++) {
	    Map<String, String> rest = new HashMap<String, String>();
	    String variables = "";
	    for (int j = 1; j <= anzVariablen * 2; j++) {
		if (j <= anzVariablen
			&& (j % (this.sumDauer * this.anzP) == aktuelleZE + (aktuellesP - 1) * this.sumDauer
			|| (this.sumDauer == aktuelleZE && anzRestriktionen == i && j % (this.sumDauer * this.anzP) == 0))) {
		    variables += "1" + this.separator;
		} else {
		    variables += "0" + this.separator;
		}
	    }
	    if (aktuelleZE == this.sumDauer) {
		aktuelleZE = 0;
		aktuellesP++;
	    }
	    aktuelleZE++;
	    rest.put("function", variables);	// linke (x-)seite der restriktion "x1+x2+x3" = y
	    rest.put("operator", "<=");		// Mitte der restriktion x1+x2+x3 "=" y
	    rest.put("result", "1");		// rechte (y-)seite der restriktion x1+x2+x3 = "y"
	    matrix.add(rest);
	}
	return matrix;
    }

    // Eine Maschine kann nur an einem Produkt pro Zeitintervall arbeiten.
    private List<Map<String, String>> getMachineRestrictions() {
	// Restriktionen erstellen
	List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
	int anzRestriktionen = this.sumDauer * this.anzM;
	int anzVariablen = this.anzP * this.anzM * this.sumDauer;
	int aktuelleZE = 1;
	int aktuelleM = 1;
	for (int i = 1; i <= anzRestriktionen; i++) {
	    Map<String, String> rest = new HashMap<String, String>();
	    String variables = "";
	    for (int j = 1; j <= anzVariablen * 2; j++) {
		if (j <= anzVariablen
			&& (j % this.sumDauer == aktuelleZE
			|| (this.sumDauer == aktuelleZE && j % this.sumDauer == 0))
			&& j <= aktuelleM * this.sumDauer * this.anzP
			&& j > (aktuelleM - 1) * this.sumDauer * this.anzP) {
		    variables += "1" + this.separator;
		} else {
		    variables += "0" + this.separator;
		}
	    }
	    if (aktuelleZE == this.sumDauer) {
		aktuelleZE = 0;
		aktuelleM++;
	    }
	    aktuelleZE++;
	    rest.put("function", variables);	// linke (x-)seite der restriktion "x1+x2+x3" = y
	    rest.put("operator", "<=");		// Mitte der restriktion x1+x2+x3 "=" y
	    rest.put("result", "1");		// rechte (y-)seite der restriktion x1+x2+x3 = "y"
	    matrix.add(rest);
	}
	return matrix;
    }

    private List<Map<String, String>> getDurationRestrictions(List<List<JFormattedTextField>> jFTFieldList) {
	// Werte der Textfelder auslesen
	List<String> values = new ArrayList();
	for (List<JFormattedTextField> jFormattedTextFields : jFTFieldList) {
	    for (JFormattedTextField jFormattedTextField : jFormattedTextFields) {
		values.add(jFormattedTextField.getText());
	    }
	}
	// Restriktionen erstellen
	int anzRestriktionen = this.anzP * this.anzM;
	int anzVariablen = this.anzP * this.anzM * this.sumDauer;
	List<Map<String, String>> matrix = new ArrayList<Map<String, String>>();
	for (int i = 1; i <= anzRestriktionen; i++) {
	    Map<String, String> rest = new HashMap<String, String>();
	    String variables = "";
	    for (int j = 1; j <= anzVariablen * 2; j++) {
		if (j <= anzVariablen
			&& j <= i * this.sumDauer && j > (i - 1) * this.sumDauer) {
		    variables += "1" + this.separator;
		} else {
		    variables += "0" + this.separator;
		}
	    }
	    rest.put("function", variables);		// linke (x-)seite der restriktion "x1+x2+x3" = y
	    rest.put("operator", ">=");			// Mitte der restriktion x1+x2+x3 "=" y
	    rest.put("result", values.get(i - 1));	// rechte (y-)seite der restriktion x1+x2+x3 = "y"
	    matrix.add(rest);
	}
	return matrix;
    }

    private int getMaxDuration(List<List<JFormattedTextField>> jFTFieldList) {
	int sum = 0;
	for (List<JFormattedTextField> jFormattedTextFields : jFTFieldList) {
	    for (JFormattedTextField jFormattedTextField : jFormattedTextFields) {
		Number no = (Number) jFormattedTextField.getValue();
		if (no == null) {
		    no = 0;
		}
		sum += no.intValue();
	    }
	}
	return sum;
    }

    private ResultList getResult(List<Map<String, String>> matrix) {
	try {
	    // Create a problem with 4 variables and 0 constraints
	    int anzRestriktionen = this.anzP * this.anzM * (1 + this.sumDauer) + (this.anzM + this.anzP) * this.sumDauer;
//	    int anzRestriktionen = this.anzP * this.anzM + (this.anzM + this.anzP) * this.sumDauer;
	    int anzVariablen = this.anzP * this.anzM * this.sumDauer * 2;
	    LpSolve solver = LpSolve.makeLp(anzRestriktionen, anzVariablen);

	    // add constraints
	    for (Map<String, String> restriktion : matrix) {
		if (restriktion.get("operator").equals(">=")) {
		    solver.strAddConstraint(restriktion.get("function"), LpSolve.GE, Integer.valueOf(restriktion.get("result")));
		} else if (restriktion.get("operator").equals("<=")) {
		    solver.strAddConstraint(restriktion.get("function"), LpSolve.LE, Integer.valueOf(restriktion.get("result")));
		}
	    }

	    // set objective function
	    String variables = "";
	    for (int j = 1; j <= this.anzP * this.anzM * 2; j++) {
		for (int k = 1; k <= this.sumDauer; k++) {
		    if(j <= this.anzP * this.anzM) {
			variables += (k * 5) + this.separator;
		    } else {
			variables += "-1" + this.separator;
		    }
		}
	    }
	    solver.strSetObjFn(variables);
	    // minimieren
	    solver.setMinim();

	    // alle Variablen auf ganzzahlig schalten
	    for (int i = 1; i <= anzVariablen; i++) {
		solver.setInt(i, true);
	    }

	    // solve the problem
	    solver.solve();

	    // print solution
//	    solver.printObjective();
//	    solver.printSolution(1);
//	    solver.printConstraints(1);
	    double[] var = solver.getPtrVariables();
	    // delete the problem and free memory
	    solver.deleteLp();
	    // analise result
	    ResultList result = this.analyseResult(var);
	    return result;
	} catch (LpSolveException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
