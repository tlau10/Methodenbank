package AnwBESF;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Image;
import java.util.*;
import javax.swing.ImageIcon;



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

public class matrixController
{
        private matrixView mV;
        private matrixViewDef defFenster;
        private matrixViewAnzeige mVA;
        private matrixPlanQuadratController mPQC;
        private AbstandsMatrixView myABMV;
        private Solver mySolve;
        private AbstandsMatrix myABM;
        private BusAbstandsMatrix myBusABM;
        private ergebnisAnzeige myErgebnisAnzeige;
        public String hinterGrundBildName = new String("nochKeinBildAusgewaehlt");
        private int[][] busAbstaende;
        private Vector[] besteBusWege;
        private int besterBusWeg = 0;
        private String besterBusWegString = new String();
        private int durchlauf=0; // Anzahl der Durchläufe, ein Durchlauf pro optimale Haltestellenkonstellation
        private int counter1=0; // Wird benötigt, um dem Vector, der die opt.Busrouten enthält, die genauen Linien und Linienführung zuzuordnen
     
        public matrixController()
        {
        	aLDateiSteuerung aLD = new aLDateiSteuerung();
        	mV = new matrixView(aLD);
            mV.setVisible(true);
       
            aL aLDefFenster = new aL();
            berechnenListener berechnenListener = new berechnenListener();
            
            graphikAuswahlListener graLi = new graphikAuswahlListener();
            defFenster = new matrixViewDef(aLDefFenster,berechnenListener,graLi);
            
            mV.getContentPane().add(defFenster);

            defFenster.setVisible(true);
        }

        private void erstelleMatrixViewAnzeige()
        {
        	//PlanQuadratController erstellen
            mPQC = new matrixPlanQuadratController(defFenster.holeX(),defFenster.holeY(),defFenster.getNormalAbstand(),defFenster.getDiagonalAbstand(),defFenster.getBusNormalAbstand(),defFenster.getBusDiagonalAbstand(),defFenster.getAnzahlLinien());

            //Anzeige erstellen
            aLDateiSteuerung aLD = new aLDateiSteuerung();
            mVA = new matrixViewAnzeige(aLD,defFenster.holeX(),defFenster.holeY());
            mVA.getContentPane().add(erstelleMatrixButtons(defFenster.holeX(),defFenster.holeY()));
            mV.getContentPane().add(mVA);
        }
        
        private void erstelleMatrixViewAnzeige(int x, int y)
        {
        	//PlanQuadratController erstellen
            mPQC = new matrixPlanQuadratController(x,y);

            //Anzeige erstellen
            aLDateiSteuerung aLD = new aLDateiSteuerung();
            mVA = new matrixViewAnzeige(aLD,x,y);
            mVA.getContentPane().add(erstelleMatrixButtons(x,y));
            mV.getContentPane().add(mVA);
        }
        
        
        private void erstelleAbstandsMatrixViewAnzeige()
        {
        	myABMV = new AbstandsMatrixView(defFenster.holeX(),mPQC.getHaltestellen().size());
        	myABMV.getContentPane().add(erstelleAbstandsMatrixButtons(defFenster.holeX(),defFenster.holeY()));
    		myABMV.setVisible(true);
    		mV.getContentPane().add(myABMV);  
        }
        
        private void erstelleErgebnisViewAnzeige(String[] loesung)
        {
           myErgebnisAnzeige = new ergebnisAnzeige();
           myErgebnisAnzeige.setBackground(Color.lightGray);
           String temp = new String();
          
           int counter=0;
           for(int x=0; x < loesung.length; x++)
           {
           		temp += loesung[x];
           		if(x != loesung.length-1){
           		for(int y=0; y < defFenster.getAnzahlLinien(); y++)
           		{
           			temp += "Linie " + (y+1) +":\n";
           			for(int z=0; z < besteBusWege[counter].size(); z++)
           			{
           				temp += " " + besteBusWege[counter].get(z) + "\n";
           			}
           			counter++;
           			temp += "\n";
           		}
           		}
           		temp += "\n___________________________________________________\n";
           }
           counter=0;
           JPanel jP = new JPanel();
 	       jP.setLayout(new GridLayout(400,700));
 	       JTextArea myJT = new JTextArea(temp);
 	       myJT.setBackground(Color.lightGray);
 	       myJT.setVisible(true);
 	       jP.add(myJT);
 	       jP.setVisible(true);
 	       JScrollPane jSP = new JScrollPane(jP);
           jSP.setVisible(true);
 	      
           myErgebnisAnzeige.getContentPane().add(jSP);
 	       myErgebnisAnzeige.setVisible(true);
 	       mV.getContentPane().add(myErgebnisAnzeige);
        }
        
        
        private JScrollPane erstelleAbstandsMatrixButtons(int x, int y)
        {
        	myABM = new AbstandsMatrix(mPQC.getHaltestellen().size(),mPQC.getHaltestellen(), x, y,
        			mPQC);
        	myBusABM = new BusAbstandsMatrix(mPQC.getHaltestellen().size(),mPQC.getHaltestellen(), x, y,
        			mPQC);
        	
        	
        	
        	aLButtonAbstand aLButton = new aLButtonAbstand();
        	Vector myHSVector = mPQC.getHaltestellen();
        	Iterator myHSIt = myHSVector.iterator();
        	String hsBezeichnung = "";
        	
        	AbstandsMatrixButton mB;
	       
	        JPanel jP = new JPanel();
	        jP.setLayout(new GridLayout(myHSVector.size()+1, (x*y)+1 ));
         
	        mB = new AbstandsMatrixButton("");
	        jP.add(mB);

	        //erste Zeile
	        for (int i=0;i < (x*y) ; i++)
	        {
	        	mB = new AbstandsMatrixButton(Integer.toString(i+1));
	        	jP.add(mB);
	        }
	       
	        //ab der zweiten Zeile
	        for(int i=0; i < myHSVector.size(); i++)
	        {
	        	
	        	hsBezeichnung = (String)myHSIt.next(); 
	        	
	        	mB = new AbstandsMatrixButton(hsBezeichnung);
	        	jP.add(mB);

	        	for(int j=0; j < (x*y); j++)
	        	{
	        		mB = new AbstandsMatrixButton((myABM.getAbstandsMatrix()[i][j]+""),i,j,myABM.getAbstandsMatrix()[i][j]);
	        		mB.setActionCommand(Integer.toString(i) + "/" + Integer.toString(j));
	        		mB.addActionListener(aLButton);
	        		jP.add(mB);
	        	}
	        }

	        jP.setVisible(true);
	        JScrollPane jSP = new JScrollPane(jP);
	        jSP.setVisible(true);
	        return(jSP);
        }
        
        private JScrollPane erstelleMatrixButtons(int x,int y)
        {
                matrixButton mB;
                aLButton aLB = new aLButton();
                int id = 0;
                Image gesamtBild = Toolkit.getDefaultToolkit().getImage(hinterGrundBildName);
                int breite=0, hoehe=0, breiteProFeld=0, hoeheProFeld=0;
                ImageIcon icon = new ImageIcon();
                
                if(!(hinterGrundBildName.equals("nochKeinBildAusgewaehlt")))
                {	
                	gesamtBild = Toolkit.getDefaultToolkit().getImage(hinterGrundBildName);
                	ImageIcon gesamtIcon = new ImageIcon(gesamtBild);
                	breite = gesamtIcon.getIconWidth();
                	hoehe = gesamtIcon.getIconHeight();
                	breiteProFeld = breite/x;
                	hoeheProFeld = hoehe/y;
                }           
                JPanel jP = new JPanel();
                jP.setLayout(new GridLayout(y+1,x+1));

                mB = new matrixButton("");
                jP.add(mB);


                //erste Zeile
                for (int i=1;i <= x;i++)
                {
                        mB = new matrixButton("x" + Integer.toString(i));
                        jP.add(mB);
                }

                //ab der zweiten Zeile
                for (int i=1;i <= y;i++)
                {
                        mB = new matrixButton("y" + Integer.toString(i));
                        jP.add(mB);

                        for (int xi=1;xi <= x;xi++)
                        {
                                id = (xi + (i - 1) * x);
                          
                                if(!(hinterGrundBildName.equals("nochKeinBildAusgewaehlt"))) //  || (hinterGrundBildName.length() != 0)
                                {
                                		Image teilbild = java.awt.Toolkit.getDefaultToolkit().createImage( 
                                        new FilteredImageSource( gesamtBild.getSource(), new CropImageFilter( (xi-1)*breiteProFeld, (i-1)*hoeheProFeld, breiteProFeld, hoeheProFeld ))); 
                                        icon = new ImageIcon(teilbild);
                                        mB = new matrixButton(Integer.toString(i) + "/" + Integer.toString(xi),i,xi,id,erstellePlanQuadrat(i,xi,id),icon, breiteProFeld,hoeheProFeld,defFenster.getAnzahlLinien());
                                }
                                else 
                                {
                                	mB = new matrixButton(Integer.toString(i) + "/" + Integer.toString(xi),i,xi,id,erstellePlanQuadrat(i,xi,id),defFenster.getAnzahlLinien());
                                }
                                mB.setActionCommand(Integer.toString(i) + "/" + Integer.toString(xi));
                                mB.addActionListener(aLB);
                                jP.add(mB);
                                
                                mVA.fuegeButtonZuArray(mB);
                        }
                }

                jP.setVisible(true);
                JScrollPane jSP = new JScrollPane(jP);
                jSP.setVisible(true);
                //hinterGrundBildName = "nochKeinBildAusgewaehlt";
                return(jSP);
        }

        
        
       private byte[] loadImageData(String fileName, int breite, int hoehe)
        {
        	String temp = new String();
        	String temp1 = new String();
        	try 
			{
        		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        		temp = in.readLine();
        		while(temp != null)
        		{
        			temp1 += temp;
        			temp = in.readLine();
        		}
        	}
        	catch(Exception e) { System.out.println(e+""); }
        	System.out.println(temp1.toString());
        	return temp1.getBytes();
        }
       
       
//-----------------------------------------------------------------------------------------------------------
              
        private void ermitteleOptimaleLinien(String[][] optimaleHalteStellen)
        {
        	busAbstaende = myBusABM.getBusAbstandsMatrix();
        	int[][] linien = new int[defFenster.getAnzahlLinien()*2][2];
        	
        	besteBusWege = new Vector[defFenster.getAnzahlLinien()*mPQC.getHaltestellen().size()+1];
        	for(int h=0; h < besteBusWege.length; h++)
        	{
        		besteBusWege[h] = new Vector();
        	}
        	
        	ArrayList felder = mPQC.holePlanQuadratListe();
        	matrixPlanQuadrat myMPQ;
        	int counter=0;
        	
        	for(int x=0; x < felder.size(); x++)
        	{
        		myMPQ = (matrixPlanQuadrat)felder.get(x);
 
        		if(myMPQ.holeLinie() != 0)
        		{
        			linien[counter][0] = myMPQ.holeID();
        			linien[counter][1] = myMPQ.holeLinie();
        			counter++;
        		}
        	}
//--------------------Sortieren der Linien nach Reihenfolge, also Linie1,Linie2,usw...---	
        	int[][] linienSortiert = new int[linien.length][2];
          	for(int x=0; x < linien.length; x++)
          	{
          		for(int y=0; y < linien.length; y++)
          		{
          			if(linien[y][1] == x+1)
          			{
          				linienSortiert[x][0] = linien[y][0];
          				linienSortiert[x][1] = linien[y][1];
          			    //System.err.println(linienSortiert[x][0]+"###" + linienSortiert[x][1]);
          			}
          		}
          	}
    
//-------------------------------------------------------------------------------------
       for(int o=0; o < optimaleHalteStellen.length-1; o++)
       {
          	int anzahlHs = 0;
          	int zaehler = 0;

          	for(int a=0; a < optimaleHalteStellen[durchlauf].length; a++)
          	{
          		if(!(optimaleHalteStellen[durchlauf][a].equals("nichtBelegt")))
          		{
          			anzahlHs++;
          		}
          	}
          
        	String[] optHs = new String[anzahlHs];
          	for(int a=0; a < optimaleHalteStellen[durchlauf].length; a++)
          	{
          		if(!(optimaleHalteStellen[durchlauf][a].equals("nichtBelegt")))
          		{
          			optHs[zaehler] = optimaleHalteStellen [durchlauf][a];
          			zaehler++;
          		}
          	}
//----------Eigentliche Berechnung-------------------------------------------------
          	int[] index = new int[1];

          	besterBusWeg = 999999999;
          	
          	for(int k=0; k < (linienSortiert.length)/2; k++)
          	{
          		this.ermittleIndexKombinationen(anzahlHs, index, linienSortiert, k, optHs);
          		besterBusWeg = 999999999;
          		counter1++;
          	}
          	durchlauf++;
       } 
       durchlauf=0;
       counter1=0;
      }
        
     
   //----------------------------------------------------------------------------------------     
        
   private void ermittleIndexKombinationen(int anzahlHs, int[] index, int[][] linienSortiert, int linienNr, String[] optHs)  // Ermittelt alle Kombinationen, Aufwand Fakultät der indexlänge
      {
      	int[] indexTemp = new int[index.length+1];
      	for(int g=0; g < index.length; g++){ indexTemp[g] = index[g]; }
      	int temp=0;
    
      	if(anzahlHs > index.length)
      	{
      		indexTemp[indexTemp.length-1] = indexTemp.length-1;
      		int i=1;
      		int hs;
          	if(indexTemp.length > 1){
      		for(int x=0; x < indexTemp.length; x++)
          	{
      			if(indexTemp.length == anzahlHs) {
      				
      				temp = berechneWeg(indexTemp,linienSortiert,linienNr,optHs);
      
      				if(temp == besterBusWeg)
      				{
      					besteBusWege[counter1].add(besterBusWegString + " mit Aufwand " + besterBusWeg);
      				}
      				else if(temp < besterBusWeg)
      				{
      					besterBusWeg = temp;
      					besteBusWege[counter1].clear();
      					besteBusWege[counter1].add(besterBusWegString + " mit Aufwand " + besterBusWeg);
      				}
      			}
      			ermittleIndexKombinationen(anzahlHs, indexTemp, linienSortiert ,linienNr, optHs);
      			
      			hs = indexTemp[indexTemp.length-i];
      			indexTemp[indexTemp.length-i] = indexTemp[indexTemp.length-i-1];
      			indexTemp[indexTemp.length-i-1] = hs;
      			i++;
      			if(indexTemp.length-i-1 < 0) i--;
          	}
          	} else ermittleIndexKombinationen(anzahlHs, indexTemp, linienSortiert,linienNr, optHs);
      	}
      }
        
      private int berechneWeg(int[] index, int[][] linienSortiert, int linienNr, String[] optHs)
      { 
      	int tempWert = 0;
      	int x=0;
      	besterBusWegString = "";
      	linienNr *= 2;
      	
      	//int temp = busAbstaende[index[0]][Integer.parseInt(optHs[index[0]])-1];
      	
      	if(durchlauf == 0) { tempWert += busAbstaende[index[0]][linienSortiert[linienNr][0]-1];besterBusWegString = linienSortiert[linienNr][0]+","; }
      	else {
      		for(int h=0; h < mPQC.getHaltestellen().size(); h++)  // Von Start der Linie zur ersten HS
      		{
      			int temp = Integer.parseInt(optHs[index[0]]);
      			if(busAbstaende[h][mPQC.getX()*mPQC.getY()] == temp) 
      			{
      				tempWert += busAbstaende[h][linienSortiert[linienNr][0]-1];
      				besterBusWegString = linienSortiert[linienNr][0]+",";
      			}
      		}
      	}
      
      		for(x=0; x < index.length-1; x++)  // Strecke innerhalb der HS
      		{
      			if(durchlauf == 0) { 
      				tempWert += busAbstaende[index[x]][(Integer.parseInt(optHs[index[x+1]]))-1];
      				besterBusWegString += optHs[index[x]]+",";
      			}
      			else {
      			int temp = Integer.parseInt(optHs[index[x]]);
      			for(int h=0; h < mPQC.getHaltestellen().size(); h++)
      			{
      				if(busAbstaende[h][mPQC.getX()*mPQC.getY()] == temp) 
      				{
      					tempWert += busAbstaende[h][(Integer.parseInt(optHs[index[x+1]]))-1];
      					besterBusWegString += optHs[index[x]]+",";
      				}
      			}
      			}
      		}
      	
      		besterBusWegString += optHs[index[x]]+",";
      	
    	//int temp = busAbstaende[index[x]][(Integer.parseInt(optHs[index[x]]))-1];  // Ab hier, von letzter HS zum Endpunkt der Linie
    	
    	if(durchlauf == 0){ tempWert += busAbstaende[index[x]][linienSortiert[linienNr+1][0]-1];} // War notwendig, da index sich mit der Anzahl der Haltestellen anpasst, busAbstaende allerdings nicht
    	else {
    		for(int h=0; h < mPQC.getHaltestellen().size(); h++)
    		{
    			int temp = busAbstaende[h][mPQC.getX()*mPQC.getY()];
    			if(temp == Integer.parseInt(optHs[index[x]])) 
    			{
    				tempWert += busAbstaende[h][linienSortiert[linienNr+1][0]-1];
    			}
    		}
    	}
    	besterBusWegString += linienSortiert[linienNr+1][0]+",";
      	return tempWert;
      }
        
        
//------------------------------------------------------------------------ 
        
      private void buttonDefAnzeigen(matrixButton b)
        {
                matrixPlanQuadrat mPQ = mPQC.holePlanQuadrat(b.holeID());
                matrixButtonDef mBD = new matrixButtonDef(b);
                
                
                //Werte setzen
                boolean auswahl0 = mPQ.holePassierbar();
                boolean auswahl1 = mPQ.holePotentHalteStelle();
                boolean auswahl2 = mPQ.holeFesteHalteStelle();

                //Start oder Endpunkt einer Linie setzen
                System.out.println("b.holeLinie() -> " + b.holeLinie());
                mBD.setzeLinie(b.holeLinie());
                
                if (auswahl2)
                {
                  mBD.setzeAuswahl(2);
                }
                else if (auswahl1)
                {
                   mBD.setzeAuswahl(1);
                }
                else if (auswahl0)
                {
                   mBD.setzeAuswahl(0);
                }
                else
                {
                    mBD.setzeAuswahl(3);
                }

                mBD.setzeGewichtung(mPQ.holeGewichtung());
                mBD.setzeAbstandNord(mPQ.holeAbstandNord());
                mBD.setzeAbstandOst(mPQ.holeAbstandOst());
                mBD.setzeAbstandSued(mPQ.holeAbstandSued());
                mBD.setzeAbstandWest(mPQ.holeAbstandWest());
                mBD.setzeAbstandNordOst(mPQ.holeAbstandNordOst());
                mBD.setzeAbstandNordWest(mPQ.holeAbstandNordWest());
                mBD.setzeAbstandSuedOst(mPQ.holeAbstandSuedOst());
                mBD.setzeAbstandSuedWest(mPQ.holeAbstandSuedWest());
                
                mBD.setzeBusAbstandNord(mPQ.holeBusAbstandNord());
                mBD.setzeBusAbstandOst(mPQ.holeBusAbstandOst());
                mBD.setzeBusAbstandSued(mPQ.holeBusAbstandSued());
                mBD.setzeBusAbstandWest(mPQ.holeBusAbstandWest());
                mBD.setzeBusAbstandNordOst(mPQ.holeBusAbstandNordOst());
                mBD.setzeBusAbstandNordWest(mPQ.holeBusAbstandNordWest());
                mBD.setzeBusAbstandSuedOst(mPQ.holeBusAbstandSuedOst());
                mBD.setzeBusAbstandSuedWest(mPQ.holeBusAbstandSuedWest());
                
                mBD.setzeHSKapazitaet(mPQ.holeHSKapazitaet());
               
                mBD.setVisible(true);
               
                mV.getContentPane().add(mBD);
        }
        
        private void abstandsButtonDefAnzeigen(AbstandsMatrixButton b)
        {
        	//System.out.println(b.holeX() + "/" + b.holeY());
        	//System.out.println(myABM.holeWeg(b.holeX(),b.holeY()));
        	Vector halteStellen = mPQC.getHaltestellen();
        	String halteStelle = new String();
        	
        	halteStelle = (String)halteStellen.get(Integer.parseInt(b.holeX()+""));
        	
        	
        	
        	JInternalFrame jF = new JInternalFrame("Von " + halteStelle + " nach " + (b.holeY()+1)+"" ,false,true,false,false);
        	
        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        	switch(screenSize.width) {
            case 640: // 640x480
                  break;

            case 800: // 800x600
                  break;

            case 1024: // 1024x768
            	jF.setLocation(0,600);  
            	break;

            case 1280:  // 1280x1024
            	jF.setLocation(0,813);
            	break;

            case 1600:  // 1600x1200
                  break;

            default:  // 1024x768
            	jF.setLocation(201,550);
                  break;
            }
      	
        	jF.setSize(200,138);
        	jF.setVisible(true);
        	JTextArea jTA = new JTextArea();
        	System.err.println(b.holeY()+"<->"+b.holeX());
        	
        	jTA.setText(myABM.getBesterWeg(Integer.parseInt(b.holeX()+""),Integer.parseInt(b.holeY()+""))+(b.holeY()+1)+"");
   
        	jTA.setVisible(true);
        	jF.getContentPane().add(jTA);
        	       	       	
        	mV.getContentPane().add(jF);
        	
        }

        private matrixPlanQuadrat holePlanQuadrat(int x, int y,int id)
        {
                return mPQC.holePlanQuadrat(id);
        }

        private matrixPlanQuadrat erstellePlanQuadrat(int x,int y,int id)
        {
                return(mPQC.erstellePQ(x,y,id));
        }

        private void setGraphikName(String graphikName)
        {
        	this.defFenster.setGraphikName(graphikName);
        }
        
        class aL implements ActionListener 
        {
        	    public void actionPerformed(ActionEvent arg0)
                { 
        	    	erstelleMatrixViewAnzeige();
                }
        }

        class graphikAuswahlListener implements ActionListener
		{
        
        	public void actionPerformed(ActionEvent arg0) 
        	{
        			FileDialog fd = new FileDialog(mV,"Öffnen",FileDialog.LOAD);      
        			fd.setVisible(true);
        			hinterGrundBildName = fd.getDirectory()+fd.getFile();      
        			setGraphikName(fd.getFile());
        	}
		}
        
        
        class berechnenListener implements ActionListener
        {
        	public void actionPerformed(ActionEvent arg0)
        	{
        		erstelleAbstandsMatrixViewAnzeige();
        		mySolve = new LPSolve();
        		mySolve.setProperties(myABM.getAbstandsMatrix(), mPQC.getGewichtungsMatrix(), mPQC, defFenster.getDetailliertesErgebnis());	        	
        		String[] loesung = new String[mPQC.getHaltestellen().size()];
				loesung = mySolve.start();
        		ermitteleOptimaleLinien(mySolve.getOptimaleHalteStellen());
        		
        		erstelleErgebnisViewAnzeige(loesung);
        	}
        }

        class aLButton implements ActionListener
        {
                public void actionPerformed(ActionEvent arg0)
                {
                        matrixButton quellButton = (matrixButton)arg0.getSource();
                        buttonDefAnzeigen(quellButton);
                }
        }
        
        class aLButtonAbstand implements ActionListener
        {
                public void actionPerformed(ActionEvent arg0)
                {
                        AbstandsMatrixButton quellButton = (AbstandsMatrixButton)arg0.getSource();
                        abstandsButtonDefAnzeigen(quellButton);
                }
        }
        
        class aLDateiSteuerung implements ActionListener
        {
        	public void actionPerformed(ActionEvent arg0) 
        	{
        		String s = (String)arg0.getActionCommand(); 
        		if (s == "O")
        		{
        			FileDialog fd = new FileDialog(mV,"Öffnen",FileDialog.LOAD);      
        			fd.setVisible(true);
        			String file = fd.getDirectory()+fd.getFile();      
        			if(fd.getFile()!=null)      
        			{         
        				BufferedReader in =null;         
        				
        				try         
						{           
        					String zeile = null;           
        					in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        					zeile = in.readLine();
        					
        					//erste Zeile mit Koordinaten
        					int semikolon = 0; 
        					
        					for (int i = 0;i <= zeile.length();i++)
        					{
        						if (zeile.charAt(i) == ';')
        						{
        							semikolon = i;
        							break;
        						}
        					}
        					String x = zeile.substring(0,semikolon);
        					String y = zeile.substring(semikolon+1);
        					
        					defFenster.setX(x);
        					defFenster.setY(y);
        					
        					hinterGrundBildName = in.readLine();
        					
        					//matrixViewAnzeige anlegen
        					erstelleMatrixViewAnzeige(Integer.parseInt(x), Integer.parseInt(y));
        					
        					int gesamtAnzahl = Integer.parseInt(x) * Integer.parseInt(y);
        					
        					for (int i = 1;i <= gesamtAnzahl; i++)
        					{
        						zeile = in.readLine();
        						ArrayList werte = new ArrayList();
        						int zaehler = 0;
        						int groesse = 0;
        						int vonStelle = -1;
        						for (int j = 0; j < zeile.length(); j++)
        						{
        							if (vonStelle == -1)
        							{
        								vonStelle = j;
        							}
        							
        							groesse++;
        							
        							if (zeile.charAt(j) == ';')
        							{
        								zaehler++;
        								String zTemp = zeile.substring(vonStelle,vonStelle+groesse-1);
        								       								
        								werte.add(zTemp);
        								groesse = 0;
        								vonStelle = -1;
        							}
        						}
        						mPQC.setztPlanQuadrate(werte);
        						mVA.aktualisiereButtons();

        					}
        				
        					//defFenster.setGraphikName(in.readLine());       					        					
        					in.close();         
    					}         
        				catch(FileNotFoundException err)         
						{           
        					System.out.println("Datei nicht gefunden"+err);         
    					}         
        				catch(IOException err)
						{           
        					System.out.println("Lesefehler"+err);         
    					}      
    				}   
	    		}
        		
        		if (s == "S")
                 {
                 	FileDialog save = new FileDialog(mV,"Matrix Speichern",FileDialog.SAVE);
                 	save.setVisible(true);
                 	               	     
                 	String file = save.getDirectory()+save.getFile();     
                 	if(save.getFile()!=null)     
                 	{       
                 		BufferedWriter out =null;
                 		try       
        				{         
                 			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));         
                 			
                 			//Grösse schreiben
                 			int xKoordinate = mVA.holeX();
                 			int yKoordinate = mVA.holeY();
                 			int gesamtAnzahl = xKoordinate * yKoordinate;
                 			out.write(xKoordinate + ";" + yKoordinate);
                			out.newLine();
                 			out.write(hinterGrundBildName);             			                			
                 			for (int i = 1; i <= gesamtAnzahl; i++)
                 			{
                 				out.newLine();
                 				matrixPlanQuadrat mPQ = mPQC.holePlanQuadrat(i);
                 				out.write(	mPQ.holeID() + ";" +
                 							mPQ.holeX() + ";" +
											mPQ.holeY() + ";" +
											mPQ.holeGewichtung() + ";" +
											mPQ.holeAbstandNord() + ";" +
											mPQ.holeAbstandOst() + ";" +
											mPQ.holeAbstandSued() + ";" +
											mPQ.holeAbstandWest() + ";" +
											mPQ.holeFesteHalteStelle() + ";" +
											mPQ.holePassierbar() + ";" +
											mPQ.holePotentHalteStelle() + ";" +
											mPQ.holeAbstandNordWest() + ";" +
											mPQ.holeAbstandNordOst() + ";" +
											mPQ.holeAbstandSuedWest() + ";" +
											mPQ.holeAbstandSuedOst() + ";" +
											mPQ.holeHSKapazitaet() + ";"
											);
                 			}
                 	
                 			out.close();       
                 		}       
                 		catch(FileNotFoundException err)       
        					{         
                 			System.out.println("Fehler beim Speichern"+err);       
                 		}       
                 		catch(IOException err)       
        					{         
                 			System.out.println("Schreibfehler..."+err);       
                 		}     
                 	}
                 }	
        	}
        }

}
