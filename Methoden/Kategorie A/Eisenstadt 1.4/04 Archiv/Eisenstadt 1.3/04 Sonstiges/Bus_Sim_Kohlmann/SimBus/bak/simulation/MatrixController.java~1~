package Simulation;

import java.util.ArrayList;
import java.util.Iterator;

public class MatrixController 
{
	private PlanquadratFabrik planquadratFabrik;
	
	private ArrayList planquadratListe;
    private ArrayList quellenListe;
    private ArrayList zielListe;
    private ArrayList wegeMatrix;
    
    private int xKoordinate;
    private int yKoordinate;
    private int gesamtElemente;
    
    
	public MatrixController()
	{
		planquadratListe = new ArrayList();
		quellenListe = new ArrayList();
		zielListe = new ArrayList();
		wegeMatrix = new ArrayList();
	}
	
	public int holeXKoordinate()
	{
		return xKoordinate;
	}
	
	public int holeYKoordinate()
	{
		return yKoordinate;
	}
	
	public void initialisiereMatrix(int x, int y)
    {
    	xKoordinate = x;
		yKoordinate = y;
		gesamtElemente = 0;
    	
    	for (int i = 1; i <= x; i++)
    	{
    		for (int j = 1; j <= y; j++)
    		{
    			gesamtElemente++;
    			erzeugePlanquadrat(gesamtElemente,i,j);
    		}
    	}
    }
		
	public void setzePlanquadrate(ArrayList arrayList)
    {
    	int id = Integer.parseInt(arrayList.get(0).toString()) - 1;
    	Planquadrat mPQ = holePlanquadrat(id);
    	
       	mPQ.setzeAbstandNord(Double.parseDouble(arrayList.get(3).toString()));
    	mPQ.setzeAbstandOst(Double.parseDouble(arrayList.get(4).toString()));
    	mPQ.setzeAbstandSued(Double.parseDouble(arrayList.get(5).toString()));
    	mPQ.setzeAbstandWest(Double.parseDouble(arrayList.get(6).toString()));
    	mPQ.setzeAbstandNordWest(Double.parseDouble(arrayList.get(7).toString()));
    	mPQ.setzeAbstandNordOst(Double.parseDouble(arrayList.get(8).toString()));
    	mPQ.setzeAbstandSuedWest(Double.parseDouble(arrayList.get(9).toString()));
    	mPQ.setzeAbstandSuedOst(Double.parseDouble(arrayList.get(10).toString()));
    	
    	mPQ.setzeEWPersonenKommen(Double.parseDouble(arrayList.get(11).toString()));
    	mPQ.setzeEWPersonenZiel(Double.parseDouble(arrayList.get(12).toString()));
    	mPQ.setzeSAPersonenKommen(Double.parseDouble(arrayList.get(13).toString()));
    	mPQ.setzeSAPersonenZiel(Double.parseDouble(arrayList.get(14).toString()));
    	
    	
    	if (Boolean.parseBoolean(arrayList.get(15).toString()))
    	{
    		mPQ.setzeHaltestelle(Boolean.parseBoolean(arrayList.get(15).toString()));
    	}
    }
	
	
    private void erzeugePlanquadrat(int id, int x, int y)
    {
    	planquadratFabrik = new PlanquadratFabrik();
    	
    	planquadratListe.add(planquadratFabrik.erzeuge(id, x, y));
    }
    
    public void erzeugeQuellListe()
    {
    	Iterator it = planquadratListe.iterator();
    	
    	while (it.hasNext())
    	{
    		Planquadrat pQ = (Planquadrat) it.next();
    		
    		if (pQ.holeHaltestelle())
    		{
    			quellenListe.add(pQ);
    		}
    	}
    }
    
    public void erzeugeZielListe()
    {
    	Iterator it = planquadratListe.iterator();
    	
    	while (it.hasNext())
    	{
    		Planquadrat pQ = (Planquadrat) it.next();
    		
    		if (pQ.holeEWPersonenZiel() != 0)
    		{
    			zielListe.add(pQ);
    		}
    	}
    }
    
    public ArrayList holePlanquadratListe()
    {
    	return planquadratListe;
    }
    
    public Planquadrat holePlanquadrat(int index)
    {
    	return (Planquadrat)planquadratListe.get(index);
    }
    
    public ArrayList holeQuellenListe()
    {
    	return quellenListe;
    }
    
    public boolean istHaltestelle(int id)
    {
    	Iterator it = quellenListe.iterator();
		
    	while (it.hasNext())
    	{
    		Planquadrat q = (Planquadrat) it.next();
    		if (q.holeID() == id)
    		{
    			return true;
    		}
		}
    	return false;
    }
    
    public ArrayList holeZielListe()
    {
    	return zielListe;
    }
    
    public void aendereQuelle(int _id, double _ew, double _sa)
    {
    	Iterator it = quellenListe.iterator();
    	
    	while (it.hasNext())
    	{
    		Planquadrat q = (Planquadrat) it.next();
    		if (q.holeID() == _id)
    		{
    			q.setzeEWPersonenKommen(_ew);
    			q.setzeSAPersonenKommen(_sa);
    			break;
    		}
    	}
    }
    
    public void aendereZiel(int _id, double _ew, double _sa)
    {
    	Iterator it = zielListe.iterator();
    	
    	while (it.hasNext())
    	{
    		Planquadrat z = (Planquadrat) it.next();
    		if (z.holeID() == _id)
    		{
    			z.setzeEWPersonenZiel(_ew);
    			z.setzeSAPersonenZiel(_sa);
    			break;
    		}
    	}
    }
    
    public void erzeugeWegeMatrix()
    {
    	Iterator it = planquadratListe.iterator();
	   	double gesamtAufwand = 0;
    	int zaehler = 0;
    	Planquadrat Planquadrat;
    	
    	for(int k=0; k < gesamtElemente; k++)
    	{
    		Planquadrat = holePlanquadrat(k);
	   		gesamtAufwand += Planquadrat.holeAbstandNord();
	   		gesamtAufwand += Planquadrat.holeAbstandSued();
	   		gesamtAufwand += Planquadrat.holeAbstandWest();
	   		gesamtAufwand += Planquadrat.holeAbstandOst();
	   		gesamtAufwand += Planquadrat.holeAbstandNordWest();
	   		gesamtAufwand += Planquadrat.holeAbstandNordOst();
	   		gesamtAufwand += Planquadrat.holeAbstandSuedWest();
	   		gesamtAufwand += Planquadrat.holeAbstandSuedOst();
    	}
    	
    	while(it.hasNext())
    	{
    		zaehler++;
    		Planquadrat mE = (Planquadrat)it.next();
    		double[] besteWerte = new double[xKoordinate*yKoordinate];
    			
    	 	for (int i = 0; i < gesamtElemente; i++)
    	   	{
    	   		besteWerte[i] = gesamtAufwand+1;
    	   	}
    	   	
    	 	besteWerte[(mE.holeID()-1)] = 0;
    	            	   		
    	 	baueWegeMatrix(mE.holeID(),mE,0,besteWerte);
    	 	
    	 	wegeMatrix.add(besteWerte);
    	}
    }
    
    public void baueWegeMatrix(int id,Planquadrat mE, double aufwand,double[] besteWerte)
    {
    	//Knoten kTemp;
    	double aTemp = 0;
    	int pQTempID = 0;

    	double[][] gainArray = new double[8][2];
    	for(int t=0; t < 8; t++)
    	{
    		gainArray[t][0] = 0;
    		gainArray[t][1] = (int)(t+1);
    	}
    	
    	double neuerAufwand;
    	int index=0;
    	    	    	    	    	    	    	
    	Planquadrat pQTemp;
    			
    	pQTemp = holeNachbarnNord(id);
    	pQTempID = pQTemp.holeID();
    	if (pQTempID != 0 ) 
    	{
    		gainArray[0][0] =  besteWerte[pQTempID-1] - (aufwand + mE.holeAbstandNord());
    	}
    			
  		pQTemp = holeNachbarnOst(id);
    	pQTempID = pQTemp.holeID();
    	if (pQTempID != 0) 
    	{
    		gainArray[1][0] = besteWerte[pQTempID-1] - (aufwand + mE.holeAbstandOst());
    	}
    			
    	pQTemp = holeNachbarnSued(id);
    	pQTempID = pQTemp.holeID();
    	if (pQTempID != 0)
    	{
    		gainArray[2][0] = besteWerte[pQTempID-1] - (aufwand + mE.holeAbstandSued());
    	}
    			
    	pQTemp = holeNachbarnWest(id);
    	pQTempID = pQTemp.holeID();
    	if (pQTempID != 0)
    	{
    		gainArray[3][0] = besteWerte[pQTempID-1] - (aufwand + mE.holeAbstandWest());
    	}
    
    	pQTemp = holeNachbarnNordWest(id);
    	pQTempID = pQTemp.holeID();
    	if (pQTempID != 0)
    	{
    		gainArray[4][0] = besteWerte[pQTempID-1] - (aufwand + mE.holeAbstandNordWest());
    	}
    			
    	pQTemp = holeNachbarnNordOst(id);
    	pQTempID = pQTemp.holeID();
    	if (pQTempID != 0)
    	{
    		gainArray[5][0] = besteWerte[pQTempID-1] - (aufwand + mE.holeAbstandNordOst());
    	}
    
    	pQTemp = holeNachbarnSuedWest(id);
    	pQTempID = pQTemp.holeID();
    	if (pQTempID != 0)
    	{
    		gainArray[6][0] = besteWerte[pQTempID-1] - (aufwand + mE.holeAbstandSuedWest());
    	}
   
		pQTemp = holeNachbarnSuedOst(id);
		pQTempID = pQTemp.holeID();
    	if (pQTempID != 0)
    	{
    		gainArray[7][0] = besteWerte[pQTempID-1] - (aufwand + mE.holeAbstandSuedOst());
    	}
    		
    	gainArray = sort(gainArray);
    	
    	for(int h=0; h < 8 && gainArray[h][0] > 0 ;h++)
    	{
    		index = (int)gainArray[h][1];
    		if(index == 1)
    		{
    			pQTemp = holeNachbarnNord(id);
    			pQTempID = pQTemp.holeID();
    	
    			if (pQTempID != 0)
    			{
    				aTemp = mE.holeAbstandNord();
    				neuerAufwand = aufwand + aTemp;
				
    				double aufwandTemp = besteWerte[pQTempID-1];
    		
    				if (aufwandTemp > neuerAufwand || aufwandTemp == 0)
    				{
    					besteWerte[pQTempID-1] = neuerAufwand;
    					baueWegeMatrix(pQTempID,pQTemp,neuerAufwand,besteWerte);  
    				}
    			}
    		}
    		
    		if(index == 2)
    		{
    			//nach Osten laufen
    			pQTemp = holeNachbarnOst(id);
    			pQTempID = pQTemp.holeID();
 
    			if (pQTempID != 0)
    			{
    				aTemp = mE.holeAbstandOst();
    				neuerAufwand = aufwand + aTemp;
    				double aufwandTemp = besteWerte[pQTempID-1];
    			
    				if (aufwandTemp > neuerAufwand || aufwandTemp == 0)
    				{
    					besteWerte[pQTempID-1] = neuerAufwand;
    					baueWegeMatrix(pQTempID,pQTemp,neuerAufwand,besteWerte);
    				}
    			}
    		}
    	
    		if(index == 3)
    		{
    			//nach Süden laufen
    			pQTemp = holeNachbarnSued(id);
    			pQTempID = pQTemp.holeID();
    	
    			if (pQTempID != 0)
    			{
    				aTemp = mE.holeAbstandSued();
    				neuerAufwand = aufwand + aTemp;
    				double aufwandTemp = besteWerte[pQTempID-1];
    			
    				if (aufwandTemp > neuerAufwand || aufwandTemp == 0)
    				{
    					besteWerte[pQTempID-1] = neuerAufwand;
    					baueWegeMatrix(pQTempID,pQTemp,neuerAufwand,besteWerte);      		
    				}
    			}
    		}
    		
    		if(index == 4)
    		{
    			//nach Westen laufen
    			pQTemp = holeNachbarnWest(id);
    			pQTempID = pQTemp.holeID();
  
    			if (pQTempID != 0)
    			{
    				aTemp = mE.holeAbstandWest();
    				neuerAufwand = aufwand + aTemp;
    				double aufwandTemp = besteWerte[pQTempID-1];
    			
    				if (aufwandTemp > neuerAufwand || aufwandTemp == 0)
    				{
    					besteWerte[pQTempID-1] = neuerAufwand;
    					baueWegeMatrix(pQTempID,pQTemp,neuerAufwand,besteWerte);
    				}
    			}
    		}
    		
    		if(index == 5)
    		{
    			//nach NordWesten laufen
    			pQTemp = holeNachbarnNordWest(id);
    			pQTempID = pQTemp.holeID();
      
        		if (pQTempID != 0)
        		{
        			aTemp = mE.holeAbstandNordWest();
        			neuerAufwand = aufwand + aTemp;
        			double aufwandTemp = besteWerte[pQTempID-1];
        			
        			if (aufwandTemp > neuerAufwand || aufwandTemp == 0)
        			{
        				besteWerte[pQTempID-1] = neuerAufwand;
        				baueWegeMatrix(pQTempID,pQTemp,neuerAufwand,besteWerte);
        			}
        		}
    		}
    		
    		if(index == 6)
    		{
    			//nach NordOsten laufen
            	pQTemp = holeNachbarnNordOst(id);
            	pQTempID = pQTemp.holeID();
          
            	if (pQTempID != 0)
            	{
            		aTemp = mE.holeAbstandNordOst();
            		neuerAufwand = aufwand + aTemp;
            		double aufwandTemp = besteWerte[pQTempID-1];
            			
            		if (aufwandTemp > neuerAufwand || aufwandTemp == 0)
            		{
            			besteWerte[pQTempID-1] = neuerAufwand;
            			baueWegeMatrix(pQTempID,pQTemp,neuerAufwand,besteWerte);
            		}
            	}
    		}
    		
    		if(index == 7)
    		{
    			//nach SuedWesten laufen
                pQTemp = holeNachbarnSuedWest(id);
                pQTempID = pQTemp.holeID();
              
                if (pQTempID != 0)
                {
                	aTemp = mE.holeAbstandSuedWest();
                	neuerAufwand = aufwand + aTemp;
                	double aufwandTemp = besteWerte[pQTempID-1];
                			
                	if (aufwandTemp > neuerAufwand || aufwandTemp == 0)
                	{
                		besteWerte[pQTempID-1] = neuerAufwand;
                		baueWegeMatrix(pQTempID,pQTemp,neuerAufwand,besteWerte);
                	}
                }
    		}
    		
    		if(index == 8 )
    		{
    			//nach SuedOsten laufen
                pQTemp = holeNachbarnSuedOst(id);
                pQTempID = pQTemp.holeID();
                  
                if (pQTempID != 0)
                {
                	aTemp = mE.holeAbstandSuedOst();
                    neuerAufwand = aufwand + aTemp;
                    double aufwandTemp = besteWerte[pQTempID-1];
                    			
                    if (aufwandTemp > neuerAufwand || aufwandTemp == 0)
                    {
                    	besteWerte[pQTempID-1] = neuerAufwand;
                    	baueWegeMatrix(pQTempID,pQTemp,neuerAufwand,besteWerte);
                    }
                }
    		}
    	}
    }
    
    public double[][] sort(double[][]gainArray)
    {
    	double[][] schluessel = new double[1][2];
    	int lauf;
    	for (int index = 1; index < gainArray.length; index++) 
    	{
    		schluessel[0][0] = gainArray[index][0];
    		schluessel[0][1] = gainArray[index][1];
    		lauf = index - 1;
    		
    		while (lauf >= 0 && gainArray[lauf][0] < schluessel[0][0]) 
    		{
    			gainArray[lauf + 1][0] = gainArray[lauf][0];
    			gainArray[lauf + 1][1] = gainArray[lauf][1];
    			lauf--;
    		}
    		
    		gainArray[lauf + 1][0] = schluessel[0][0];
    		gainArray[lauf + 1][1] = schluessel[0][1];
    	}
    	return gainArray;
    }
    
    public void modifizierePlanquadratListe()
    {
    	Iterator it = planquadratListe.iterator();
    	
    	while(it.hasNext())
    	{
    		Planquadrat pQ = (Planquadrat) it.next();
    		
    		if (pQ.holeAbstandNord() == 0)
    		{
    			pQ.setzeAbstandNord(999999);
    		}
    		
    		if (pQ.holeAbstandOst() == 0)
    		{
    			pQ.setzeAbstandOst(999999);
    		}
    		
    		if (pQ.holeAbstandSued() == 0)
    		{
    			pQ.setzeAbstandSued(999999);
    		}
    		
    		if (pQ.holeAbstandWest() == 0)
    		{
    			pQ.setzeAbstandWest(999999);
    		}
    		
    		if (pQ.holeAbstandNordOst() == 0)
    		{
    			pQ.setzeAbstandNordOst(999999);
    		}
    		
    		if (pQ.holeAbstandSuedOst() == 0)
    		{
    			pQ.setzeAbstandSuedOst(999999);
    		}
    		
    		if (pQ.holeAbstandNordWest() == 0)
    		{
    			pQ.setzeAbstandNordWest(999999);
    		}
    		
    		if (pQ.holeAbstandSuedWest() == 0)
    		{
    			pQ.setzeAbstandSuedWest(999999);
    		}
    	}
    }
    
    public double[] ermittleBesteHaltestelle(int id)
    {
    	Iterator it = wegeMatrix.iterator();
    	int zaehler = -1;
    	double besterWert = 0;
    	int besteHaltestelle = 0;
    	id = id - 1;
   	
    	while(it.hasNext())
    	{
    		zaehler++;
    		
    		double[] werte = (double[]) it.next();
    		
    		if (istHaltestelle(zaehler+1))
			{
    			if (besteHaltestelle == 0)
    			{
    				besterWert = werte[id];
    				besteHaltestelle = zaehler + 1;
    			}
    			else
    			{
    				if (besterWert > werte[id])
    				{
    					besterWert = werte[id];
        				besteHaltestelle = zaehler + 1;
    				}
    			}
        		
    		}
    	}
    	
    	double[] rueckgabe = new double[2];
    	
    	rueckgabe[0] = besteHaltestelle;
    	rueckgabe[1] = besterWert;
    	
    	return rueckgabe;
    }
    
    public Planquadrat holeNachbarnNord(int id)
    {
    	Planquadrat temp = new Planquadrat(0, 0, 0);
    	
    	if ((id - xKoordinate) > 0)
    	{
    		  return (Planquadrat)planquadratListe.get(id-xKoordinate-1);
        }
        else
        {
          return temp;
        }
    }

    public Planquadrat holeNachbarnNordWest(int id)
    {
    	Planquadrat temp = new Planquadrat(0, 0, 0);
    	
    	if (((id - xKoordinate) > 0) && ((id - 1) % xKoordinate) != 0)
    	{
    		  return (Planquadrat)planquadratListe.get(id-xKoordinate-2);
        }
        else
        {
          return temp;
        }
    }
    
    public Planquadrat holeNachbarnNordOst(int id)
    {
    	Planquadrat temp = new Planquadrat(0, 0, 0);
    	
    	if (((id - xKoordinate) > 0) && (id % xKoordinate != 0))
    	{
    		  return (Planquadrat)planquadratListe.get(id-xKoordinate);
        }
        else
        {
          return temp;
        }
    }
    
    public Planquadrat holeNachbarnSuedWest(int id)
    {
    	Planquadrat temp = new Planquadrat(0, 0, 0);
    	
    	if (((id + xKoordinate) <= (xKoordinate * yKoordinate)) && ((id - 1) % xKoordinate) != 0)
		{
    		 return (Planquadrat)planquadratListe.get(id+xKoordinate-2);
		}
    	else
    	{
    		return temp;
    	}
    }
    public Planquadrat holeNachbarnSuedOst(int id)
    {
    	Planquadrat temp = new Planquadrat(0, 0, 0);
    	
    	if (((id + xKoordinate) <= (xKoordinate * yKoordinate)) && (id % xKoordinate != 0))
		{
    		 return (Planquadrat)planquadratListe.get(id+xKoordinate);
		}
    	else
    	{
    		return temp;
    	}
    }
    
    public Planquadrat holeNachbarnOst(int id)
    {
    	Planquadrat temp = new Planquadrat(0, 0, 0);
    	
    	if (id % xKoordinate != 0)
    	{
    		return(Planquadrat)planquadratListe.get(id);
    	}
    	else
    	{
    		return temp;
    	}
    }

    public Planquadrat holeNachbarnSued(int id)
    {
    	Planquadrat temp = new Planquadrat(0, 0, 0);
    	
    	if ((id + xKoordinate) <= (xKoordinate * yKoordinate))
		{
    		 return (Planquadrat)planquadratListe.get(id+xKoordinate-1);
		}
    	else
    	{
    		return temp;
    	}
    }

    public Planquadrat holeNachbarnWest(int id)
    {
    	Planquadrat temp = new Planquadrat(0, 0, 0);
    	
    	if (((id - 1) % xKoordinate) != 0)
		{
    		return (Planquadrat) planquadratListe.get(id - 2);
		}
    	else
    	{
    		return temp;
    	}
    }

    
    
    
    // N U R    Z U M    T E S T E N    
    public void druckeListe(ArrayList liste)
    {
    	Iterator it = liste.iterator();
    	
    	while (it.hasNext())
    	{
    		Planquadrat mE = (Planquadrat) it.next();
    	}
    }
}
