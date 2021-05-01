package AnwBESF;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Jens Kohlmann
 * @version 1.0
 */
import java.util.*;
import java.io.*;


public class MPSSolve implements Solver {

  private ArrayList gewichtungsMatrix;

  private int[][] abstandsMatrix;
  private matrixPlanQuadratController mPQC;
  private Vector myStringVector;
  private Vector myHSVector;
  private int laenge;
  private int breite;
  private String[] loesung;
  private loesungElement[] loesungsMenge;
  private boolean kurzAusgabe;
  private int anzahlHS;
  private boolean[] festeHaltestellen; 
  private String[] schalterVariablen;
  private String[][] optimaleHalteStellen;



  public void setProperties(int[][] abstandsMatrix_, ArrayList gewichtungsMatrix_,  matrixPlanQuadratController mPQC_, boolean kurzAusgabe_)
  {
  	kurzAusgabe = kurzAusgabe_;
  	mPQC = mPQC_;
  	abstandsMatrix = abstandsMatrix_;
    gewichtungsMatrix = gewichtungsMatrix_;
    breite = mPQC.getX();
    laenge = mPQC.getY();
    
	myStringVector  = new Vector();

	myHSVector = mPQC.getHaltestellen();
	loesung = new String[(myHSVector.size()*breite*laenge)+myHSVector.size()+2];  
	loesungsMenge = new loesungElement[(myHSVector.size()*breite*laenge)+myHSVector.size()+2];  
	festeHaltestellen = new boolean[myHSVector.size()];
    schalterVariablen = new String[myHSVector.size()];
    optimaleHalteStellen = new String[myHSVector.size()][myHSVector.size()];
  }
  
  public String[] start()
  {
  	String[] loesung = new String[mPQC.getHaltestellen().size()];
  	for(int x=0; x < mPQC.getHaltestellen().size(); x++)
	{
	  	String lpAnsatz = this.LPAnsatzGenerieren();

//---------SCHALTER-RESTRIKTION------------------------------------------------
  	    anzahlHS = x;
  		String temp = new String();
  		String schalter = new String();

  		for(int g=0; g < myHSVector.size(); g++)
  	    {
  	    	if(g < (myHSVector.size()-1))
  	    	{
  	    		schalter += (schalterVariablen[g] + "+");
  	    		if(festeHaltestellen[g] == false) 
  	    			{
  	    			 temp += (schalterVariablen[g] + "<=1;");
  	    			}
  	    		else temp += (schalterVariablen[g] + "=0;");
  	    	}
  	    	else 
  	    	{
  	    		schalter += (schalterVariablen[g] + "=" + x + ";");
  	    		if(festeHaltestellen[g] == false) temp += (schalterVariablen[g] + "<=1;"); // Schaltervariablen <= 1
  	    		else temp +=  (schalterVariablen[g] + "=0;");
  	        }
  	    }
  	  lpAnsatz += schalter+temp;
  	    for(int h=0; h < myHSVector.size(); h++)
  	    {
  	    	if(h < (myHSVector.size()-1))
  	    	{
  	    		lpAnsatz += ("int " + schalterVariablen[h] + ";");
  	    	}
  	    	else lpAnsatz += ("int " + schalterVariablen[h] + ";");
  	    }
  	 	//System.out.println(lpAnsatz);
  		if(!this.calculate(lpAnsatz))
  		{
  			loesung[x] =  "\n\nProblem mit " + (mPQC.getHaltestellen().size()-x) +" Haltestelle(n) nicht lösbar.";
  		}
  		else 
  		{
  			loesung[x] += this.parseSolution(x);
  		}
	}

  	return loesung;
  }
  
  public String LPAnsatzGenerieren()
  {
  	
    String zielFunktion = new String("min: ");
    String restriktionen = new String("");
    String x = new String("x");
    int gesamtAnzahl = gewichtungsMatrix.size();
    int anzahlMoeglicheHaltestellen = myHSVector.size();
    int gesamtGewichtung=0;
    matrixPlanQuadrat myPlanQuadrat;    
    

    
//---------EIGENSCHAFTEN ------------------------------------------------------
    
   for(int a=0; a < gewichtungsMatrix.size(); a++) // Potentielle Haltestellen ermitteln und in Vector speichern
    {
    	myPlanQuadrat = (matrixPlanQuadrat)gewichtungsMatrix.get(a);
    	gesamtGewichtung += myPlanQuadrat.holeGewichtung();
    }


//---------ZIELFUNKTION--------------------------------------------------------
   
    int counter=0;
    for(int a=0; a < myHSVector.size(); a++)
    {
    	for(int y=0; y < gewichtungsMatrix.size(); y++)
    	{
    		counter++;
    		if(abstandsMatrix[a][y] != 0.0) // 0.0 Werte nicht in ZF aufnehmen, sonst erschweren "Warnings" das Auslesen des Ergebnisses später
			{
				zielFunktion += (abstandsMatrix[a][y] + x + counter + "+");
			}
    	}
    }
    
    if(zielFunktion.endsWith("+"))
    {
    	zielFunktion = (zielFunktion.substring(0, (zielFunktion.length()-1)))+ ";"; 
    }
   
//---------GEWICHTUNGS-RESTRIKTIONEN-------------------------------------------
    int index=1;
    int gewichtungsIndex = 1;
    int festHalteStellenIndex = (anzahlMoeglicheHaltestellen*laenge*breite)+anzahlMoeglicheHaltestellen+1;
   
    int zaehler=0;
    for(int c=0; c < gesamtAnzahl; c++)
    {
    	myPlanQuadrat = (matrixPlanQuadrat)gewichtungsMatrix.get(c);
    	gewichtungsIndex = index;
    	
    		if(myPlanQuadrat.holeFesteHalteStelle())
    		{
    			festeHaltestellen[zaehler] = true;
    			zaehler++;
    		}
    		else if (myPlanQuadrat.holePotentHalteStelle()) 
    		{
    			festeHaltestellen[zaehler] = false;
    			zaehler++;
    		}
    		
    	for(int b=1; b <= anzahlMoeglicheHaltestellen; b++)
    	{
    		if(b < anzahlMoeglicheHaltestellen) restriktionen += (x + gewichtungsIndex + "+");
    		else restriktionen += (x + gewichtungsIndex + "=" + myPlanQuadrat.holeGewichtung()+ ";");
    		
    		gewichtungsIndex += gesamtAnzahl;
    	}
    	index++;
    }

//---------HALTESTELLEN-RESTRIKTIONEN------------------------------------------
    index=1;
    counter = 0;
  
    int hsKapazitaet=0;
    int schalterVariableIndex = anzahlMoeglicheHaltestellen*gesamtAnzahl+1;
  
    for(int d=0; d < anzahlMoeglicheHaltestellen; d++)
    {
    	String halteStellenID = (String)myHSVector.get(d);
    	myPlanQuadrat = (matrixPlanQuadrat)gewichtungsMatrix.get((new Integer(halteStellenID)).intValue()-1);
    	hsKapazitaet = myPlanQuadrat.holeHSKapazitaet();

    	if(hsKapazitaet == 0) hsKapazitaet = gesamtGewichtung;
    	for(int f=1; f <= gesamtAnzahl; f++)
    	{
    		if(f < gesamtAnzahl) restriktionen += (x + index++ + "+");
    		else {
    			schalterVariablen[counter++] = (x + schalterVariableIndex);
    			restriktionen += (x + index++ + "+" + hsKapazitaet + x + schalterVariableIndex + "<=" + hsKapazitaet + ";");
    			schalterVariableIndex++;
    		}	
    	}
    }
    System.out.println(zielFunktion+"\n"+restriktionen);
    return (zielFunktion+restriktionen);
  }

  
//_____________________________________________________________________________________ 
 
  public boolean calculate(String dataSet)
	{
    StringBuffer lpSolveOutput =  new StringBuffer();
  	String _ERROR_MSG 	= "ERROR: ";
	String _WARNING_MSG = "WARNING: ";
  	 // if there are not data --> return
	  	if(dataSet.equals(""))
	  		return false;
	  	
	  
	  BufferedReader p_inBuffer;
	  String proc_input_string = "";
		try
		{
			  //create the process
		  	Process proc = Runtime.getRuntime().exec("LP_SOLVE.EXE");

		  	//get the streams
			  InputStream  proc_in  = proc.getInputStream();
			  InputStream  prog_err = proc.getErrorStream();
		  	int exit              = 0;
		    boolean processEnded  = false;

	      //push the data to the lpsolve
	      OutputStream proc_out  = proc.getOutputStream();
	      DataOutputStream out_s = new DataOutputStream(proc_out);
	      out_s.writeBytes(dataSet);
	      out_s.close();
	      proc_out.close();

	      // fetch the output from the LPsolve
	      while(!processEnded)
	      {
	      	try {
	      		exit = proc.exitValue();
	      		processEnded = true;
	      	}
	      	catch(IllegalThreadStateException e) {
	      	} // still running

	      	int n = proc_in.available();
	      	if(n > 0) {
	                byte[] pbytes = new byte[n];
	                proc_in.read(pbytes);
	                lpSolveOutput.append(new String(pbytes));
	        }
	        n = prog_err.available();
	        if(n > 0) {
	                byte[] pbytes = new byte[n];
	                prog_err.read(pbytes);
	                _ERROR_MSG += new String(pbytes);
	        }
	        try {
	        	Thread.sleep(10);
	        }
	        catch(InterruptedException e) {
	        }

		    } // ### END-while ###

		//close/destroy all handles
		prog_err.close();
	    proc_in.close();
		proc.destroy();
		} catch (IOException e) {
	    	// System.err.println("exeption-error:" + e);
	    	_ERROR_MSG += "\nIO error: " + e;
	    	myStringVector.add(0,"error");
	    	myStringVector.add(1,_ERROR_MSG);
	    	return false;
		}

		//loesung = new String[];
		int zaehler=0;
		String temp = new String(); 
		
		for(int x=0; x < lpSolveOutput.length(); x++)
		{
			if(temp.equals("problem")) return false;  // eigentlich "contains"
			if(lpSolveOutput.charAt(x) == 'x') 
			{				
				loesung[zaehler] = temp; 
				temp = "";
				zaehler++; 
			}
			temp += lpSolveOutput.charAt(x);
		}
		loesung[zaehler] = temp; 
		return true;
	} // ### END-calculateLPsolve

  
//_______________________________PARSE-SOLUTION____________________________________ 
 
  public String parseSolution(int durchlauf)
  {
	String solution = new String();
  	try {
  	int counter=0;
  	int index=0;
  	for(int x=0; x < myHSVector.size(); x++)
  	{
  		for( int y=0; y < (breite*laenge); y++)
  		{
  			loesungsMenge[counter] = new loesungElement(loesung[counter].substring(0,6).trim(),loesung[counter].substring(6,loesung[counter].length()).trim());
  			counter++;
  		}
  	}

  	for(int z=0; z < myHSVector.size()+1; z++)
  	{
  		loesungsMenge[counter] = new loesungElement(loesung[counter].substring(0,6).trim(), loesung[counter].substring(6,loesung[counter].length()).trim());
  		counter++;
  	}
  	
  	this.quicksort(0, (loesungsMenge.length-2));
  	
  
  	//##########################AUSGABE#########################################
  	//System.out.println("Minimaler Aufwand: " + loesungsMenge[loesungsMenge.length-2].getAnzahlPersonen().substring(24,loesungsMenge[loesungsMenge.length-2].getAnzahlPersonen().length()).trim()+ " bei " + (myHSVector.size()-anzahlHS) + " gebauten Haltestellen.");
 
  	solution = "Min. Aufwand: " + loesungsMenge[loesungsMenge.length-2].getAnzahlPersonen().substring(24,loesungsMenge[loesungsMenge.length-2].getAnzahlPersonen().length()).trim()+ " bei " + (myHSVector.size()-anzahlHS) + " gebauten Haltestellen.\n\n";
  	int gesamtAufkommen=0;
  	for(int b=0; b < myHSVector.size(); b++)
  	{
  	
  		String temp = loesungsMenge[(breite*laenge*myHSVector.size())+(b)].getAnzahlPersonen();
  		if(temp.equals("0"))
  		{ 
  			gesamtAufkommen=0;
  			
  			solution += "Haltestelle in Feld " + myHSVector.get(b) + " wird gebaut.\n";
  				for(int c=(b*laenge*breite); c < (b+1)*(laenge*breite); c++ )
  				{
  					
  					temp = loesungsMenge[c].getAnzahlPersonen();
  					if(!(temp.equals("0")) && !(temp.equals("notFound")))
  					{
  						//solution += "     "+temp+" Personen -> Feld " + ((c+1)-(b*laenge*breite))+"\n";
  						gesamtAufkommen += (new Integer(temp)).intValue();
  					}
  				}
  				solution += gesamtAufkommen +" Personen steigen bei HS in Feld " +myHSVector.get(b)+  " aus.\n\n";
  			
  		}
  	}
  	solution += "____________________________________________________________________\n";
	return solution;
  	}
  	catch(Exception e) { e.printStackTrace(); }
  	return solution;
  	}

  
  
  public void quicksort (int lo, int hi)
  {
      int i=lo, j=hi;
      loesungElement x = loesungsMenge[(lo+hi)/2];

      //  Aufteilung
      while (i<=j)
      {    
          while (vergleich(loesungsMenge[i].getHalteStelle() ,x.getHalteStelle()) < 0)
          {
          	i++; 
          }
          while (vergleich(loesungsMenge[j].getHalteStelle() ,x.getHalteStelle()) > 0)
          { 
          	j--;
          }
          
          if (i<=j)
          {
              exchange(i, j);
              i++; j--;
          }
      }

      // Rekursion
      if (lo<j) quicksort(lo, j);
      if (i<hi) quicksort(i, hi);
  }


  public void exchange(int i, int j)
  {
      loesungElement t=loesungsMenge[i];
      loesungsMenge[i]=loesungsMenge[j];
      loesungsMenge[j]=t;
  }

  public int vergleich(String eins, String zwei)
  {
  	if(eins.length() > zwei.length()) return 1;
  	if(zwei.length() > eins.length()) return -1;
  	
  	return eins.compareTo(zwei);
   }
  
  public String[][] getOptimaleHalteStellen()
  {
  	return this.optimaleHalteStellen;
  }
  
}