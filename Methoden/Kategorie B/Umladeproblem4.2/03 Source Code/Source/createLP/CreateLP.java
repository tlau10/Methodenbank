package createLP;

// Klasse wurde von den Studierenden erstellt
public class CreateLP {

	public String createRestriktionen(double[][] kostenmatrix,	double[] anbieterArray, double[] nachfragerArray, double difference) {

		String zfString = "min: ";
		String anbieterString = "";
		String nachfragerString = "";
		String restriktionString = "";

		
		String anbieterGroesserNachfragerTemp = "";
		String anbieterGroesserNachfragerString = "R";
		String nachfragerGrosserAnbieterTemp = "";
		String nachfragerGrosserAnbieterString = "R";

		int anbieterValueTemp = 0;
		int nachfragerValueTemp = 0;
		
		//Boolesche set...Variablen -> prüfen ob bereits Werte gesetzt wurden. Sie dienen nur der Formatierung
		//beisielsweise um zu überprüfen ob ein "+" gesetzt werden muss oder nicht.
		boolean setNachfragerGroesserAnbieter = false;

		
		for (int row = 0; row < kostenmatrix.length; row++) {//for row

			boolean setColumnAnbieter = false;
			boolean setColumnNachfrager = false;
			boolean setAnbieterFirstColumn = false;
			boolean setNachfragerFirstRow = false;
			boolean setRowNacfrager = false;
			
			anbieterString += "R" + (row + 1) + ": ";
			
			
			if(difference >= 0.0){
				nachfragerString += "R" + (row + kostenmatrix.length + 1) + ": ";
			}else{//Anbieter groeßer Nachfrager
				nachfragerString += "R" + (row + kostenmatrix.length + 2) + ": ";
			}
			

			for (int column = 0; column < kostenmatrix.length; column++) {//for column
				
				

// -------------------------------------------------------------------------------------------------------------------
//					 Zielfunktion :
// -------------------------------------------------------------------------------------------------------------------

					if(row == column){ // row == column -> Knoten 
						if((kostenmatrix.length - 1) != row && (kostenmatrix.length - 1) != column){
							zfString += kostenmatrix[row][column] + "*" + "t" + (row + 1) + " + ";
						}else{
							zfString += kostenmatrix[row][column] + "*" + "t" + (row + 1) + ";\n";
						}
					}else{	
						zfString += kostenmatrix[row][column] + "*" + "x" + (row + 1) + (column + 1) + " + ";
					}

// -------------------------------------------------------------------------------------------------------------------
// 					AnbieterRestriktionen :
// -------------------------------------------------------------------------------------------------------------------

				if (kostenmatrix[row][column] > 0.0) {
					if (row == column) {
						anbieterValueTemp = -1;
					} else {
						anbieterValueTemp = 1;
					}
				} else {
					anbieterValueTemp = 0;
				}

				if (anbieterValueTemp != 0) {
					if (setAnbieterFirstColumn == false) {
						setAnbieterFirstColumn = true;
						if (column == row) {
							anbieterString += anbieterValueTemp + "*" + "t" + (row + 1);
							setColumnAnbieter = true;
						} else {
							anbieterString += anbieterValueTemp + "*" + "x" + (row + 1) + (column + 1);
							setColumnAnbieter = true;
						}

					} else {
						if (row == column) {
							anbieterString += " + " + anbieterValueTemp + "*" + "t"	+ (row + 1);
							setColumnAnbieter = true;
						} else {
							anbieterString += " + " + anbieterValueTemp + "*" + "x"	+ (row + 1) + (column + 1);
							setColumnAnbieter = true;
						}
					}
				}

				if ((kostenmatrix.length - 1) == column) {
					if (setColumnAnbieter == true) {
						
						// Restriktion Anbieter groesser Nachfrager
						if(difference > 0.0){
							anbieterString += " + 1*x" + (row + 1) + (kostenmatrix.length +1) ;
						}// Restriktion Anbieter groesser Nachfrager ende
						
						anbieterString += " = " + anbieterArray[row] + ";"	+ "\n";
					
						
					} else {
						anbieterString += "0.0 = 0.0 ;"+" \n";
					
					}
				}
				
				// Restriktion Anbieter groesser Nachfrager
				if(difference < 0.0){
					if (column == kostenmatrix.length - 1) {
						if (row != 0.0) {
							anbieterGroesserNachfragerTemp += " + ";
						}
						anbieterGroesserNachfragerTemp += "1*x" + (row + 1) + (column +2);
					}

				}// Restriktion Anbieter groesser Nachfrager ende

// -------------------------------------------------------------------------------------------------------------------
// 					NachfragerRestriktionen :
// -------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------
				if (kostenmatrix[column][row]>0.0){
					if(column==row){
						nachfragerValueTemp =-1;
						}else{
						nachfragerValueTemp =1;
						}
					}else {
					nachfragerValueTemp =0;
					}

					if(nachfragerValueTemp  !=0){
					if (setNachfragerFirstRow == false) {
						setNachfragerFirstRow = true;
						if( row == column){
						nachfragerString += nachfragerValueTemp + "*" + "t" + (column + 1);
						setRowNacfrager = true;
						} else {
						nachfragerString += nachfragerValueTemp + "*" + "x" + (column + 1) + (row + 1);
						setRowNacfrager = true;
						}

						} else {
						if (column== row) {
							nachfragerString += " + " + nachfragerValueTemp + "*" + "t"	+ (column + 1);
							setRowNacfrager = true;
						} else {
						nachfragerString += " + " + nachfragerValueTemp + "*" + "x"	+ (column + 1) + (row + 1);
						setRowNacfrager = true;
						}	
					}
					}
				if ((kostenmatrix.length)== row) {
					if (setRowNacfrager == true) {
						
						if(difference > 0.0){
							nachfragerString += " + 1*x" + (column + 1) + (kostenmatrix.length) ;
						}
						nachfragerString += " = " + nachfragerArray[column] + ";"	+ "\n";	
					} else {
						nachfragerString += "0.0 " + "= 0.0 ;"+" \n";
								
					}
				}	
					
//---------------------------------------------------------------------------------------------------------
				
			}// end for colum
			
			if( difference < 0.0 && row == (kostenmatrix.length -1)){
				//Restriktion für Nachfrage Groesser Angebot 
//				nachfragerString += " + 1*x" + (kostenmatrix.length + 1) + (row + 1) + " = " + nachfragerArray[row] + ";" + "\n";
				nachfragerString += "0.0 = 0.0;" + "\n";
			}else{
				// Restriktion Nachfrager
	
				nachfragerString +=    " = " + nachfragerArray[row] + ";" + "\n";
			}
			

			
			if(difference != 0.0 && row == (kostenmatrix.length-1)){
				// Restriktion für Angebot Groesser Nachfrage 
				if(difference < 0.0){
					anbieterGroesserNachfragerString += kostenmatrix.length + row + 2 + ": " + anbieterGroesserNachfragerTemp + " = " + difference + ";";
				}else{// Restriktion für Nachfrage Groesser Angebot
					nachfragerGrosserAnbieterString += row + 2 + ": " + nachfragerGrosserAnbieterTemp + " = " + (difference *-1) + "; \n";
				}
			}

		}// end for row

// -------------------------------------------------------------------------------------------------------------------
// 					Übergabestring erstellen
// -------------------------------------------------------------------------------------------------------------------

		
		if(difference == 0.0){
			restriktionString += zfString;
			restriktionString += anbieterString;
			restriktionString += nachfragerString;
		}else{
			if(difference > 0.0){
				restriktionString += zfString;
				restriktionString += anbieterString;
				restriktionString += nachfragerString;
				restriktionString += anbieterGroesserNachfragerString;
			}else{
				restriktionString += zfString;
				restriktionString += anbieterString;
				restriktionString += nachfragerGrosserAnbieterString;
				restriktionString += nachfragerString;
			}
		}

		System.out.println(restriktionString);

		return restriktionString;

	}
}
