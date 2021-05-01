package facade;

import createLP.CreateLP;
import createLP.CreateXML;
import de.htwg_konstanz.tpsolver.web.solver.WebSolver;
import lpSolver.LPSolve;

//Klasse wurde von den Studierenden erstellt
public class Facade {

	double[][] kostenMatrix;
	double[] anbieterArray;
	double[] nachfragerArray;
	double difference ;

	public Facade(double[] anbieterArray, double[] nachfragerArray, double[][] kostenMatrix, double difference) {

		this.kostenMatrix = kostenMatrix;
		this.anbieterArray = anbieterArray;
		this.nachfragerArray = nachfragerArray;
		this.difference = difference;
	}
	
	public String createLP(){
		
		CreateLP lp = new CreateLP();

		return lp.createRestriktionen(kostenMatrix, anbieterArray, nachfragerArray, difference);
	}

	public String startLpSolve(String lp) {

		LPSolve solve = new LPSolve();

		return solve.lpSolve(lp);

	}

	public String startMightyLP() {

		WebSolver webSolver = new WebSolver();

		CreateXML createXMLR = new CreateXML();

		System.out.println("Solver wird aufgerufen...\n"
				+ webSolver.getWebResult(createXMLR.createXML(kostenMatrix,
						anbieterArray, nachfragerArray)));
		
		return webSolver.getWebResult("adf " + createXMLR.createXML(kostenMatrix, anbieterArray, nachfragerArray));
		
	}
	
}
