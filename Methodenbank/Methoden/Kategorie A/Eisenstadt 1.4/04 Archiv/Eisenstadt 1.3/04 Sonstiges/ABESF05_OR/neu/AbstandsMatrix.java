package AnwBESF;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Alexander Lauermann / Jens Kohlmann
 * @version 1.0
 */

import java.util.*;

public class AbstandsMatrix {
//_________________________________________________________________________________
  private int abstandsMatrix[][];
  private String[][] wegeMatrix;
  private int anzahlFelder;
  private int laenge;
  private Vector myHSVector;
  private int breite;
  private int anzahlHS;
  private matrixPlanQuadratController mPQC;
  private int durchlaeufe=0;
  private int[] besteWerte;
  private String[][] besterWeg;
  private String gelaufenerWeg = new String();
  private int aktuelleHalteStelle;


//_________________________________________________________________________________

  public AbstandsMatrix(int anzahlHS_, Vector myHSVector_, int breite_, int laenge_,matrixPlanQuadratController mPQC_)
  {
   breite = breite_;
   laenge = laenge_;
   myHSVector = myHSVector_;
   Iterator it = myHSVector.iterator();
   anzahlFelder = breite*laenge;
   anzahlHS = anzahlHS_;
   int g=0;
   besteWerte = new int[laenge*breite];
   besterWeg = new String[anzahlHS][laenge*breite];
   aktuelleHalteStelle = 0;
   
   
   mPQC = mPQC_;
   
   abstandsMatrix = new int[anzahlHS][anzahlFelder];
   wegeMatrix = new String[anzahlHS][anzahlFelder];
   String hs;
   int halteStelle=0;
   int gesamtAufwand=0;
   matrixPlanQuadrat myMPQ = new matrixPlanQuadrat(0,0,0);
		
   for(int k=0; k < anzahlFelder; k++)
   {
   		myMPQ = mPQC.holePlanQuadrat(k+1);
   		gesamtAufwand += myMPQ.holeAbstandNord();
   		gesamtAufwand += myMPQ.holeAbstandSued();
   		gesamtAufwand += myMPQ.holeAbstandWest();
   		gesamtAufwand += myMPQ.holeAbstandOst();
   		gesamtAufwand += myMPQ.holeAbstandNordWest();
   		gesamtAufwand += myMPQ.holeAbstandNordOst();
   		gesamtAufwand += myMPQ.holeAbstandSuedWest();
   		gesamtAufwand += myMPQ.holeAbstandSuedOst();
   }
   
   it = myHSVector.iterator();
   for(int x=0; x < anzahlHS; x++)
   {
   		aktuelleHalteStelle = x;
   		String temp = (String)it.next();
   		int id = (new Integer (temp)).intValue();
   	
   		
   		ArrayList besteWege = new ArrayList();
   		//ArrayList besteWerte = new ArrayList();
   		ArrayList zaehler = new ArrayList();
   		
   		
   		hs = (String)myHSVector.get(x);
   		halteStelle = (new Integer(hs)).intValue();
 		matrixPlanQuadrat pQ = mPQC.holePlanQuadrat(halteStelle);
   		
 		for (int i = 0; i < anzahlFelder; i++)
   		{
   			//besteWege.add("");
   			besteWerte[i] = gesamtAufwand+1;
   		}
   		besteWerte[halteStelle-1] = 0;
     
 		//Vector bereitsDurchlaufen = new Vector();
  	 			
 		//bereitsDurchlaufen.add(Integer.toString(id));
 	    
   		Date start = new Date();
   		durchlaeufe=0;
 		baue(pQ.holeID(),pQ,0,besteWerte,gelaufenerWeg);
 
 		
 		/*for(int j=0; j < besterWeg.length; j++)
 		{
 			System.out.println("Kürzester Weg für -> " + (j+1));
 			System.out.println(besterWeg[j]);
 		}
 			
 		Date ende = new Date();
 	    long time1 = (ende.getTime() - start.getTime());

 		
 		System.out.println("Rekursionsdurchlaeufe : " + durchlaeufe + " in " + time1 + " Millisekunden");
 		*/
 		
 		int counter=0;
 		for(int y=0; y < anzahlFelder; y++)
 		{
 			abstandsMatrix[x][y] = besteWerte[counter++];
 			//System.out.println(besteWege.size());
 			//System.out.println(besteWege.get(y));
 			//wegeMatrix[x][y] = besteWege.get(y).toString();
 		}
   	}
 }
//_________________________________________________________________________________

    public void setElement(int wert, int x, int y)
    {
      abstandsMatrix[x][y] = wert;
    }
  //_________________________________________________________________________________

    public String getBesterWeg(int halteStelle, int feld)
    {
    	return besterWeg[halteStelle][feld];
    }
    
    
    public int[][] getAbstandsMatrix()
    {
     return abstandsMatrix;
    }
  //_________________________________________________________________________________
    
    public void baue(int id,matrixPlanQuadrat mPQ, int aufwand,int[] besteWerte, String gelaufenerWeg)
    {
    	//Knoten kTemp;
    	int pQTempID = 0;
    	int neuerAufwand;	    	    	    	    	
    	matrixPlanQuadrat pQTemp;
      	
    	gelaufenerWeg += id+",";
    	
 	//durchlaeufe++;
  	// 		nach Norden laufen
    	pQTemp = mPQC.holeNachbarnNord(id);
    	pQTempID = pQTemp.holeID();
    	
    		if (pQTempID != 0)
    		{
    			neuerAufwand = aufwand + mPQ.holeAbstandNord();
				
    			int aufwandTemp = besteWerte[pQTempID-1];
    		
    			if (aufwandTemp > neuerAufwand)
    			{
    				besterWeg[aktuelleHalteStelle][pQTempID-1] = "";
    				besterWeg[aktuelleHalteStelle][pQTempID-1] = gelaufenerWeg;
    				besteWerte[pQTempID-1] = neuerAufwand;
    				baue(pQTempID,pQTemp,neuerAufwand,besteWerte,gelaufenerWeg);  
    			}
    		}
   
    	//nach Osten laufen
   
    	pQTemp = mPQC.holeNachbarnOst(id);
    	pQTempID = pQTemp.holeID();
 
    		if (pQTempID != 0)
    		{  			
    			neuerAufwand = aufwand + mPQ.holeAbstandOst();
    			int aufwandTemp = besteWerte[pQTempID-1];
    			
    			if (aufwandTemp > neuerAufwand)
    			{
    				besterWeg[aktuelleHalteStelle][pQTempID-1] = "";
    				besterWeg[aktuelleHalteStelle][pQTempID-1] = gelaufenerWeg;
    				besteWerte[pQTempID-1] = neuerAufwand;
    				baue(pQTempID,pQTemp,neuerAufwand,besteWerte,gelaufenerWeg);
    			}
    		}

    //nach Süden laufen

    	pQTemp = mPQC.holeNachbarnSued(id);
    	pQTempID = pQTemp.holeID();
    	
    
    		if (pQTempID != 0)
    		{
     			neuerAufwand = aufwand + mPQ.holeAbstandSued();
    			int aufwandTemp = besteWerte[pQTempID-1];
    			
    			if (aufwandTemp > neuerAufwand)
    			{
    				besterWeg[aktuelleHalteStelle][pQTempID-1] = "";
    				besterWeg[aktuelleHalteStelle][pQTempID-1] = gelaufenerWeg;
    				besteWerte[pQTempID-1] = neuerAufwand;
    				baue(pQTempID,pQTemp,neuerAufwand,besteWerte,gelaufenerWeg);
    			}
       		}
    	
    
    	//nach Westen laufen

    	pQTemp = mPQC.holeNachbarnWest(id);
    	pQTempID = pQTemp.holeID();
  
    		if (pQTempID != 0)
    		{
    			neuerAufwand = aufwand + mPQ.holeAbstandWest();
    			int aufwandTemp = besteWerte[pQTempID-1];
    			
    			if (aufwandTemp > neuerAufwand)
    			{
    				besterWeg[aktuelleHalteStelle][pQTempID-1] = "";
    				besterWeg[aktuelleHalteStelle][pQTempID-1] = gelaufenerWeg;
    				besteWerte[pQTempID-1] = neuerAufwand;
    				baue(pQTempID,pQTemp,neuerAufwand,besteWerte,gelaufenerWeg);
    			}
    		  }
    		
  
//    		nach NordWesten laufen
    	pQTemp = mPQC.holeNachbarnNordWest(id);
        	pQTempID = pQTemp.holeID();
      
        		if (pQTempID != 0)
        		{
        			neuerAufwand = aufwand + mPQ.holeAbstandNordWest();
        			int aufwandTemp = besteWerte[pQTempID-1];
        			
        			if (aufwandTemp > neuerAufwand)
        			{
        				besterWeg[aktuelleHalteStelle][pQTempID-1] = "";
        				besterWeg[aktuelleHalteStelle][pQTempID-1] = gelaufenerWeg;
        				besteWerte[pQTempID-1] = neuerAufwand;
        				baue(pQTempID,pQTemp,neuerAufwand,besteWerte,gelaufenerWeg);
        			}
        		  }
        	
   
    //        		nach NordOsten laufen   	
    	pQTemp = mPQC.holeNachbarnNordOst(id);
        pQTempID = pQTemp.holeID();
          
           if (pQTempID != 0)
            {
            neuerAufwand = aufwand + mPQ.holeAbstandNordOst();
            int aufwandTemp = besteWerte[pQTempID-1];
            			
            if (aufwandTemp > neuerAufwand)
            {
            	besterWeg[aktuelleHalteStelle][pQTempID-1] = "";
				besterWeg[aktuelleHalteStelle][pQTempID-1] = gelaufenerWeg;
				besteWerte[pQTempID-1] = neuerAufwand;
				baue(pQTempID,pQTemp,neuerAufwand,besteWerte,gelaufenerWeg);
            }
           }
   
    //            		nach SuedWesten laufen
        	
    	pQTemp = mPQC.holeNachbarnSuedWest(id);
        pQTempID = pQTemp.holeID();
              
        if (pQTempID != 0)
        {
               neuerAufwand = aufwand + mPQ.holeAbstandSuedWest();
               int aufwandTemp = besteWerte[pQTempID-1];
                			
               if (aufwandTemp > neuerAufwand)
               {
               	besterWeg[aktuelleHalteStelle][pQTempID-1] = "";
				besterWeg[aktuelleHalteStelle][pQTempID-1] = gelaufenerWeg;
				besteWerte[pQTempID-1] = neuerAufwand;
				baue(pQTempID,pQTemp,neuerAufwand,besteWerte,gelaufenerWeg);
                }
         }
   
    //                		nach SuedOsten laufen           	
    	pQTemp = mPQC.holeNachbarnSuedOst(id);
        pQTempID = pQTemp.holeID();
                  
        if (pQTempID != 0)
        {
         	neuerAufwand = aufwand + mPQ.holeAbstandSuedOst();
            int aufwandTemp = besteWerte[pQTempID-1];
                    			
            if (aufwandTemp > neuerAufwand)
            {      				
            	besterWeg[aktuelleHalteStelle][pQTempID-1] = "";
				besterWeg[aktuelleHalteStelle][pQTempID-1] = gelaufenerWeg;
				besteWerte[pQTempID-1] = neuerAufwand;
				baue(pQTempID,pQTemp,neuerAufwand,besteWerte,gelaufenerWeg);
            }
         }
  }	

    		
	//bereitsDurchlaufen.remove((Integer.toString(id)));

     
  public int getLaenge()
  {
  	return laenge;
  }
  
  public int getBreite()
  {
  	return anzahlFelder;
  }
  
  public String holeWeg(int x,int y)
  {
  	return wegeMatrix[x][y];
  }
  

//_________________________________________________________________________________





}