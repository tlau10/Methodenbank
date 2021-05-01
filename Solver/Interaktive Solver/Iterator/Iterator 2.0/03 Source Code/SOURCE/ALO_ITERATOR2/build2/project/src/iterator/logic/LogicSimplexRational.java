package iterator.logic;


import org.jscience.mathematics.number.Rational;
import org.apache.log4j.Logger;

import iterator.MessageHandler;
import iterator.tableau.*;

public class LogicSimplexRational implements ILogic {

	private static Logger logger = Logger.getLogger(TableauDouble.class);

	
	@Override
	public TableauCellPosition pivot(ITableau tableau) throws Exception {
		int pivotColumn = -1;
		int pivotRow = -1;
		
		Rational[][] aMatrix = (Rational[][]) tableau.getaMatrix();
		Rational[] bVektor = (Rational[]) tableau.getbVektor();
		Rational[] zielfunktion = (Rational[]) tableau.getZielfunktion();
		
		//Pivotspaltenkriterium
		Rational mostNegativeValue = Rational.valueOf("0");
		for (int i = 0; i < zielfunktion.length; i++) {
			logger.debug("  >> PIVOT DEBUG: " + i + ", Value = " + zielfunktion[i]);
			if(zielfunktion[i].isLessThan(mostNegativeValue)){
				logger.debug("  >> PIVOT DEBUG: Current lowest value!" );
				mostNegativeValue = zielfunktion[i];
				pivotColumn = i;
			}
			
		}
		
		if(pivotColumn == -1){
			logger.info("  >> pivot column selection criterion injured!");
			throw new Exception(MessageHandler.getInstance().getMessage("logic.pivotColumn"));
		}
		
		
		//Pivotzeilenkriterium
		Rational smallestValue = Rational.valueOf("999999999999999999999999999");
		for (int i = 0; i < bVektor.length; i++) {
			if(bVektor[i].isPositive() && bVektor[i].divide(aMatrix[pivotColumn][i]).isLessThan(smallestValue)){
				smallestValue = bVektor[i].divide(aMatrix[pivotColumn][i]);
				pivotRow = i;
			}
		}
		
		if(pivotRow == -1){
			logger.info("  >> pivot row selection criterion injured!");
			throw new Exception(MessageHandler.getInstance().getMessage("logic.pivotRow"));
		}
		
		//Pivotelement gefunden
		logger.info(" >> PIVOT FOUND! Column: " + (pivotColumn+1) + "   |   Row: " + (pivotRow+1));
		return new TableauCellPosition(pivotRow, pivotColumn);
	}

	@Override
	public ITableau iterate(ITableau tableau, int row, int column) throws Exception {
			TableauCellPosition cell = new TableauCellPosition(row, column);
			
			Rational[][] aMatrix = (Rational[][]) tableau.getaMatrix();
			Rational[] bVektor = (Rational[]) tableau.getbVektor();
			
			Rational[] zielfunktion = (Rational[]) tableau.getZielfunktion();
			Rational zielfunktionskoeffizient = (Rational) tableau.getZielfunktionskoeffizient();
			
			
			logger.debug("  >> ITERATE TABLEAU: variables = " + tableau.getaMatrixVariable() + ", restrictions = " + tableau.getaMatrixRestriction() );

			//----------------------------------------------------------------------------------------------------
			//Eingabeelemente prüfen
			if(row >= tableau.getaMatrixRestriction()){
				throw new IllegalArgumentException(MessageHandler.getInstance().getMessage("logic.rowNa"));
			} else if( column >= tableau.getaMatrixVariable()){
				throw new IllegalArgumentException(MessageHandler.getInstance().getMessage("logic.columnNa"));
			}
			
			//----------------------------------------------------------------------------------------------------
			//Pivotzeilenumrechnung
			Rational pivotElement = aMatrix[cell.getColumn()][cell.getRow()];
			logger.debug("CHOSEN ELEMENT: " + pivotElement + " Res: " + cell.getRow() + ", Var: " + cell.getColumn());
			
			if(pivotElement.isZero()){
				
				//Prüfe ob nur eine Restriktion vorhanden
				if(tableau.getaMatrixRestriction() == 1){
					logger.error("Element kann nicht in Basis gelangen!");
					throw new Exception(MessageHandler.getInstance().getMessage("logic.elementToBase"));
				}
				
				logger.debug("  >> Iteration over Zero!");
				logger.debug("  >> Searching a change-restriction (to bottom)!");
				
				//Finde nächste Restriktion in der Variable != 0
				int changeRow = -1;
				for (int i = cell.getRow()+1; i < tableau.getaMatrixRestriction(); i++) {
					logger.debug("  >> RES:" + (i+1) + " Value: " + aMatrix[cell.getColumn()][i]);
					
					if(!aMatrix[cell.getColumn()][i].isZero()){
						changeRow = i;
						break;
					}
				}
				
				if(changeRow == -1){
					logger.debug("  >> Searching a change-restriction (to top)!");
					for (int i = cell.getRow()-1; i >= 0; i--) {
						logger.debug("  >> RES:" + (i+1) + " Value: " + aMatrix[cell.getColumn()][i]);
						if(!aMatrix[cell.getColumn()][i].isZero()){
							changeRow = i;
							break;
						}
					}
				}
				
				if(changeRow == -1){
					logger.error("Keine Wechselrestriktion gefunden!");
					throw new Exception(MessageHandler.getInstance().getMessage("logic.changeRow"));
				}
				
				Rational[] changeRestriction = new Rational[tableau.getaMatrixVariable()];
				Rational[] pivotRestriction = new Rational[tableau.getaMatrixVariable()];
				
				//Pivotzeile und Tauschzeile zwischenspeichern
				for (int i = 0; i < tableau.getaMatrixRestriction(); i++) {
					for (int j = 0; j < tableau.getaMatrixVariable(); j++) {
						if(i == changeRow){
							changeRestriction[j] = aMatrix[j][i].copy();
						} else if(i == cell.getRow()){
							pivotRestriction[j] = aMatrix[j][i].copy();
						}
					}
				}
				
				for (int i = 0; i < tableau.getaMatrixVariable(); i++) {
					aMatrix[i][cell.getRow()] = changeRestriction[i].plus(pivotRestriction[i]);
					aMatrix[i][changeRow] = pivotRestriction[i].times(-1);
				}
				Rational tempB = bVektor[cell.getRow()].copy();
				bVektor[cell.getRow()] = bVektor[changeRow].plus(tempB);
				bVektor[changeRow] =  tempB.times(-1);
			
				
				//neues Pivotelement setzen
				
				pivotElement = aMatrix[cell.getColumn()][cell.getRow()];
				logger.debug("  >> NEW PIVOT ELEMENT = " + aMatrix[cell.getColumn()][cell.getRow()]);
			}
			
			for (int i = 0; i < tableau.getaMatrixVariable(); i++) {
				if(!aMatrix[i][cell.getRow()].isZero()){
					tableau.setAValue(cell.getRow(),i, aMatrix[i][cell.getRow()].divide(pivotElement));
				}
			}
			tableau.setBValue(cell.getRow(), bVektor[cell.getRow()].divide(pivotElement));
			
			
			//----------------------------------------------------------------------------------------------------
			//Zeilentransformation restlicher Restriktionen
			for (int restriction = 0; restriction < tableau.getaMatrixRestriction(); restriction++) {
				//Pivot Zeile übergehen
				if(restriction != cell.getRow()){
					Rational faktor = aMatrix[cell.getColumn()][restriction].divide(aMatrix[cell.getColumn()][cell.getRow()]);
					logger.debug("   >> RES: " + (restriction + 1) + ", calculated factor: " + faktor + " (" + aMatrix[cell.getColumn()][restriction] + "/" + aMatrix[cell.getColumn()][cell.getRow()] + ")");
			
					//A-Matrix
					for (int variable = 0; variable < tableau.getaMatrixVariable(); variable++) {
						String logAppender = (variable == cell.getColumn())? " <-- CURRENT PIVOT-COLUMN!": "";
						logger.debug("     >> A-Value: (" + variable + "/" + restriction + ") " + aMatrix[variable][restriction] + "-" + "( " + faktor +" * "+aMatrix[variable][cell.getRow()] + ") = " + (aMatrix[variable][restriction].minus((faktor.times(aMatrix[variable][cell.getRow()])))) + logAppender );
					
						tableau.setAValue(restriction, variable, aMatrix[variable][restriction].minus((faktor.times(aMatrix[variable][cell.getRow()]))));
					}
					
					//b-Vektor
					tableau.setBValue(restriction, bVektor[restriction].minus((faktor.times(bVektor[cell.getRow()]))));	
				}
			}
			
			
			Rational faktor = zielfunktion[cell.getColumn()].divide(aMatrix[cell.getColumn()][cell.getRow()]);
			//Zielfunktionskoeffizient berechnen
			tableau.setZielfunktionskoeffizient(zielfunktionskoeffizient.minus(faktor.times(bVektor[cell.getRow()])));
			
			//Zielfunktion
			for (int i = 0; i < zielfunktion.length; i++) {
				zielfunktion[i] = zielfunktion[i].minus((faktor.times(aMatrix[i][cell.getRow()])));
			}

			
			//Optimum des Tableaus prüfen
			tableau.setOptimized(true);
			for (Rational zf: zielfunktion) {
				if(zf.isNegative()){
					tableau.setOptimized(false);
					break;
				}
			}
			
			tableau.setNumberOfIterates(tableau.getNumberOfIterates() + 1);
			tableau.setZielfunktion(zielfunktion);
						
			
		return tableau;
	}


}
