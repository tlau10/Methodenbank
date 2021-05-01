package iterator.logic;



import java.math.BigDecimal;

import org.apache.log4j.Logger;

import iterator.tableau.*;


public class LogicSimplexBigDecimal implements ILogic {

	private static Logger logger = Logger.getLogger(TableauDouble.class);

	
	@Override
	public TableauCellPosition pivot(ITableau tableau) {
		int pivotColumn = -1;
		int pivotRow = -1;
		
		BigDecimal[][] aMatrix = (BigDecimal[][]) tableau.getaMatrix();
		BigDecimal[] bVektor = (BigDecimal[]) tableau.getbVektor();
		BigDecimal[] zielfunktion = (BigDecimal[]) tableau.getZielfunktion();
		
		//Pivotspaltenkriterium
		BigDecimal mostNegativeValue = new BigDecimal(0);
		for (int i = 0; i < zielfunktion.length; i++) {
			logger.debug("  >> PIVOT DEBUG: " + i + ", Value = " + zielfunktion[i]);
			if(zielfunktion[i].compareTo(mostNegativeValue) < 0){
				logger.debug("  >> PIVOT DEBUG: Current lowest value!" );
				mostNegativeValue = zielfunktion[i];
				pivotColumn = i;
			}
			
		}
		
		if(pivotColumn == -1){
			logger.info("  >> pivot column selection criterion injured!");
			return null;
		}
		
		
		//Pivotzeilenkriterium
		BigDecimal smallestValue = new BigDecimal(999999999);
		for (int i = 0; i < bVektor.length; i++) {
			if(bVektor[i].compareTo(BigDecimal.ZERO) < 0 && bVektor[i].divide(aMatrix[pivotColumn][i]).compareTo(smallestValue) < 0){
				smallestValue = bVektor[i].divide(aMatrix[pivotColumn][i]);
				pivotRow = i;
			}
		}
		
		if(pivotRow == -1){
			logger.info("  >> pivot row selection criterion injured!");
			return null;
		}
		
		//Pivotelement gefunden
		logger.info(" >> PIVOT FOUND! Column: " + (pivotColumn+1) + "   |   Row: " + (pivotRow+1));
		return new TableauCellPosition(pivotRow, pivotColumn);
	}

	@Override
	public ITableau iterate(ITableau tableau, int row, int column) {
			TableauCellPosition cell = new TableauCellPosition(row, column);
			
			BigDecimal[][] aMatrix = (BigDecimal[][]) tableau.getaMatrix();
			BigDecimal[] bVektor = (BigDecimal[]) tableau.getbVektor();
			
			BigDecimal[] zielfunktion = (BigDecimal[]) tableau.getZielfunktion();
			BigDecimal zielfunktionskoeffizient = (BigDecimal) tableau.getZielfunktionskoeffizient();
			
			
			logger.debug("  >> ITERATE TABLEAU: variables = " + tableau.getaMatrixVariable() + ", restrictions = " + tableau.getaMatrixRestriction() );
			
			//----------------------------------------------------------------------------------------------------
			//Eingabeelemente prüfen
			if(row >= tableau.getaMatrixRestriction()){
				throw new IllegalArgumentException("ROW " + row + " not available in A-Matrix!");
			} else if( column >= tableau.getaMatrixVariable()){
				throw new IllegalArgumentException("COLUMN " + column + " not available in A-Matrix!");
			}
			
			//----------------------------------------------------------------------------------------------------
			//Pivotzeilenumrechnung
			BigDecimal pivotElement = aMatrix[cell.getColumn()][cell.getRow()];
			
			if(pivotElement.compareTo(BigDecimal.ZERO) == 0){
				return tableau;
			}
			
			logger.debug("CHOSEN ELEMENT: " + pivotElement);
			
			for (int i = 0; i < tableau.getaMatrixVariable(); i++) {
				if(aMatrix[i][cell.getRow()].compareTo(BigDecimal.ZERO) != 0){
					tableau.setAValue(cell.getRow(),i, aMatrix[i][cell.getRow()].divide(pivotElement));
				}
			}
			tableau.setBValue(cell.getRow(), bVektor[cell.getRow()].divide(pivotElement));

			//----------------------------------------------------------------------------------------------------
			//Zeilentransformation restlicher Restriktionen
			for (int restriction = 0; restriction < tableau.getaMatrixRestriction(); restriction++) {
				//Pivot Zeile übergehen
				if(restriction != cell.getRow()){
					BigDecimal faktor = aMatrix[cell.getColumn()][restriction].divide(aMatrix[cell.getColumn()][cell.getRow()]);
					logger.debug("   >> RES: " + (restriction + 1) + ", calculated factor: " + faktor + " (" + aMatrix[cell.getColumn()][restriction] + "/" + aMatrix[cell.getColumn()][cell.getRow()] + ")");
					
					//A-Matrix
					for (int variable = 0; variable < tableau.getaMatrixVariable(); variable++) {
						String logAppender = (variable == cell.getColumn())? " <-- CURRENT PIVOT-COLUMN!": "";
						logger.debug("     >> A-Value: (" + variable + "/" + restriction + ") " + aMatrix[variable][restriction] + "-" + "( " + faktor +" * "+aMatrix[variable][cell.getRow()] + ") = " + (aMatrix[variable][restriction].subtract((faktor.multiply(aMatrix[variable][cell.getRow()])))) + logAppender );
					
						tableau.setAValue(restriction, variable, aMatrix[variable][restriction].subtract((faktor.multiply(aMatrix[variable][cell.getRow()]))));
					}
					
					//b-Vektor
					tableau.setBValue(restriction, bVektor[restriction].subtract((faktor.multiply(bVektor[cell.getRow()]))));	
				}
			}
			
			
			BigDecimal faktor = zielfunktion[cell.getColumn()].divide(aMatrix[cell.getColumn()][cell.getRow()]);
			//Zielfunktionskoeffizient berechnen
			tableau.setZielfunktionskoeffizient(zielfunktionskoeffizient.subtract(faktor.multiply(bVektor[cell.getRow()])));
			
			//Zielfunktion
			for (int i = 0; i < zielfunktion.length; i++) {
				zielfunktion[i] = zielfunktion[i].subtract((faktor.multiply(aMatrix[i][cell.getRow()])));
			}

			
			//Optimum des Tableaus prüfen
			tableau.setOptimized(true);
			for (int i = 0; i < zielfunktion.length; i++) {
				if(zielfunktion[i].compareTo(BigDecimal.ZERO) > 0){
					tableau.setOptimized(false);
				}
			}
			
			tableau.setNumberOfIterates(tableau.getNumberOfIterates() + 1);
			

			for (BigDecimal zf: zielfunktion) {
				if(zf.compareTo(BigDecimal.ZERO) > 0){
					tableau.setOptimized(false);
					break;
				}
			}
			
			tableau.setZielfunktion(zielfunktion);

		return tableau;
	}


}
