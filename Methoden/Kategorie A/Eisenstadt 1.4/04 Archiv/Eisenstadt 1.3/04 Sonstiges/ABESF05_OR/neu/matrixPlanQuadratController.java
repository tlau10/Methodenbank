package AnwBESF;

import java.util.ArrayList;
import java.util.*;

/*
 * Created on 15.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Alex
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class matrixPlanQuadratController
{
        private ArrayList planQuadratList;
        final private int xDim;
        final private int yDim;
        private matrixPlanQuadrat temp;
        private int normalAbstand;
        private int diagonalAbstand;
        private int busNormalAbstand;
        private int busDiagonalAbstand;
        private int anzahlLinien;

        public matrixPlanQuadratController(int x,int y)
        {
                planQuadratList = new ArrayList();
                xDim = x;
                yDim = y;
                temp = new matrixPlanQuadrat(0,0,0);
        }
        
        public matrixPlanQuadratController(int x,int y, int normalAbstand_, int diagonalAbstand_, int busNormalAbstand_, int busDiagonalAbstand_, int anzahlLinien_)
        {
                planQuadratList = new ArrayList();
                xDim = x;
                yDim = y;
                temp = new matrixPlanQuadrat(0,0,0);
                normalAbstand = normalAbstand_;
                diagonalAbstand = diagonalAbstand_;
                busNormalAbstand = busNormalAbstand_;
                busDiagonalAbstand = busDiagonalAbstand_;
                anzahlLinien = anzahlLinien_;
        }

        public int getX()
        {
        	return xDim;
        }
        
		public int getY()
        {
        	return yDim;
        }
        
        public matrixPlanQuadrat erstellePQ(int x,int y,int id)
        {  
        	matrixPlanQuadrat pQ = new matrixPlanQuadrat(x,y,id);
        	pQ.setzeAbstandNord(normalAbstand);
        	pQ.setzeAbstandWest(normalAbstand);
        	pQ.setzeAbstandSued(normalAbstand);
        	pQ.setzeAbstandOst(normalAbstand);
        	pQ.setzeAbstandNordWest(diagonalAbstand);
        	pQ.setzeAbstandNordOst(diagonalAbstand);
        	pQ.setzeAbstandSuedWest(diagonalAbstand);
        	pQ.setzeAbstandSuedOst(diagonalAbstand);
        	
        	pQ.setzeBusAbstandNord(busNormalAbstand);
        	pQ.setzeBusAbstandWest(busNormalAbstand);
        	pQ.setzeBusAbstandSued(busNormalAbstand);
        	pQ.setzeBusAbstandOst(busNormalAbstand);
        	pQ.setzeBusAbstandNordWest(busDiagonalAbstand);
        	pQ.setzeBusAbstandNordOst(busDiagonalAbstand);
        	pQ.setzeBusAbstandSuedWest(busDiagonalAbstand);
        	pQ.setzeBusAbstandSuedOst(busDiagonalAbstand);
        	
        	pQ.setzeAbstandNormal(normalAbstand);
        	pQ.setzeAbstandDiagonal(diagonalAbstand);
        	pQ.setzeBusAbstandNormal(busNormalAbstand);
        	pQ.setzeBusAbstandDiagonal(busDiagonalAbstand);
        	pQ.setzeLinie(0);
            setzePlanQuadrat(pQ);
            return(pQ);
        }

        private void setzePlanQuadrat(matrixPlanQuadrat pQ)
        {
                planQuadratList.add(pQ);
        }
        
        public void setztPlanQuadrate(ArrayList arrayList)
        {
        	int id = Integer.parseInt(arrayList.get(0).toString());
        	matrixPlanQuadrat mPQ = holePlanQuadrat(id);
        	
        	mPQ.setzeGewichtung(Integer.parseInt(arrayList.get(3).toString()));
        	mPQ.setzeAbstandNord(Integer.parseInt(arrayList.get(4).toString()));
        	mPQ.setzeAbstandOst(Integer.parseInt(arrayList.get(5).toString()));
        	mPQ.setzeAbstandSued(Integer.parseInt(arrayList.get(6).toString()));
        	mPQ.setzeAbstandWest(Integer.parseInt(arrayList.get(7).toString()));
        	mPQ.setzeAbstandNordWest(Integer.parseInt(arrayList.get(11).toString()));
        	mPQ.setzeAbstandNordOst(Integer.parseInt(arrayList.get(12).toString()));
        	mPQ.setzeAbstandSuedWest(Integer.parseInt(arrayList.get(13).toString()));
        	mPQ.setzeAbstandSuedOst(Integer.parseInt(arrayList.get(14).toString()));
 
        	mPQ.setzeHSKapazitaet(Integer.parseInt(arrayList.get(15).toString()));
        	
        	mPQ.setzePassierbar(false);
        	
        	if ("true".equals(arrayList.get(9).toString()))
        	{
        		mPQ.setzePassierbar(true);
        	}
        	
        	if ("true".equals(arrayList.get(10).toString()))
        	{
        		mPQ.setzePotentHalteStelle(true);
        	}
        	if ("true".equals(arrayList.get(8).toString()))
        	{
        		mPQ.setzeFesteHalteStelle(true);
        	}
        	
        	//if (Boolean.parseBoolean(arrayList.get(8).toString()))
        	//{
        		//mPQ.setzeFesteHalteStelle(Boolean.parseBoolean(arrayList.get(8).toString()));
        	//}
        }

        public int getAnzahlFesteHalteStellen()
        {
        	matrixPlanQuadrat aMPQ = new matrixPlanQuadrat(0,0,0);
        	int anzahlFesteHalteStellen=0;
        	for(int x=0; x < planQuadratList.size(); x++)
        	{
        		aMPQ = (matrixPlanQuadrat)planQuadratList.get(x);
        		if(aMPQ.holeFesteHalteStelle()) anzahlFesteHalteStellen++;
        	}
        	return anzahlFesteHalteStellen;
        }
        
        public ArrayList getGewichtungsMatrix()
        {
          return planQuadratList;
        }

        public Vector getHaltestellen()
        {
        	Vector myHSVector = new Vector();
        	String hsName = new String();
        	matrixPlanQuadrat myMPQ;
        	for(int x=0; x < planQuadratList.size(); x++)
        	{
        		myMPQ = (matrixPlanQuadrat)planQuadratList.get(x);
        		if(myMPQ.holePotentHalteStelle() || myMPQ.holeFesteHalteStelle())
        		{
        			myHSVector.add(myMPQ.holeID()+"");
        		}
        	}
        	return myHSVector;
        }
        
        
        public matrixPlanQuadrat holePlanQuadrat(int id)
        {
             matrixPlanQuadrat pQ = (matrixPlanQuadrat)planQuadratList.get(id-1);
             return pQ;
        }

        
        
        public matrixPlanQuadrat holeNachbarnNord(int id)
        {
        	if ((id - xDim) > 0)
        	{
        		  return (matrixPlanQuadrat)planQuadratList.get(id-xDim-1);
            }
            else
            {
              return temp;
            }
        	
        	/*if((id-xDim-1) > 0 && (id-xDim-1) < (xDim*yDim))
          {
            return (matrixPlanQuadrat)planQuadratList.get(id-xDim-1);
          }
          else
          {
            return temp;
          }*/
        }

        public matrixPlanQuadrat holeNachbarnNordWest(int id)
        {
        	if (((id - xDim) > 0) && ((id - 1) % xDim) != 0)
        	{
        		  return (matrixPlanQuadrat)planQuadratList.get(id-xDim-2);
            }
            else
            {
              return temp;
            }
        }
        
        public matrixPlanQuadrat holeNachbarnNordOst(int id)
        {
        	if (((id - xDim) > 0) && (id % xDim != 0))
        	{
        		  return (matrixPlanQuadrat)planQuadratList.get(id-xDim);
            }
            else
            {
              return temp;
            }
        }
        
        public matrixPlanQuadrat holeNachbarnSuedWest(int id)
        {
        	if (((id + xDim) <= (xDim * yDim)) && ((id - 1) % xDim) != 0)
			{
        		 return (matrixPlanQuadrat)planQuadratList.get(id+xDim-2);
			}
        	else
        	{
        		return temp;
        	}
        }
        public matrixPlanQuadrat holeNachbarnSuedOst(int id)
        {
        	if (((id + xDim) <= (xDim * yDim)) && (id % xDim != 0))
			{
        		 return (matrixPlanQuadrat)planQuadratList.get(id+xDim);
			}
        	else
        	{
        		return temp;
        	}
        }
        
        
        
        public matrixPlanQuadrat holeNachbarnOst(int id)
        {
        	if (id % xDim != 0)
        	{
        		return(matrixPlanQuadrat)planQuadratList.get(id);
        	}
        	else
        	{
        		return temp;
        	}
        	
        /*if((id) > 0 && id < (xDim*yDim) && (id%xDim != 0))
          {
          return(matrixPlanQuadrat)planQuadratList.get(id);
          }
          else
          {
            return temp;
          }*/
        }

        public matrixPlanQuadrat holeNachbarnSued(int id)
        {
        	if ((id + xDim) <= (xDim * yDim))
			{
        		 return (matrixPlanQuadrat)planQuadratList.get(id+xDim-1);
			}
        	else
        	{
        		return temp;
        	}
         
        	/*if((id+xDim-1) > 0 && (id+xDim-1) < (xDim*yDim))
         {
          return (matrixPlanQuadrat)planQuadratList.get(id+xDim-1);
         }
         else
          {
             return temp;
          }*/
        }

        public matrixPlanQuadrat holeNachbarnWest(int id)
        {
        	if (((id - 1) % xDim) != 0)
			{
        		return (matrixPlanQuadrat) planQuadratList.get(id - 2);
			}
        	else
        	{
        		return temp;
        	}
        
        	/*if((id-2) > 0 && (id-2) < (xDim*yDim) && (id%xDim != 1) )
         {
           return (matrixPlanQuadrat) planQuadratList.get(id - 2);
         }
         else
         {
           return temp;
        }*/

        }

        public ArrayList holePlanQuadratListe()
        {
        	return planQuadratList;
        }
}
