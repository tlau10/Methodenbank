package AnwBESF;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.ImageCapabilities;
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
        private Solve mySolve;
        private AbstandsMatrix myABM;
        private ergebnisAnzeige myErgebnisAnzeige;
        private String hinterGrundBildName = new String("nochKeinBildAusgewaehlt");

        public matrixController()
        {
        	aLDateiSteuerung aLD = new aLDateiSteuerung();
        	mV = new matrixView(aLD);
            mV.setVisible(true);
       

            aL aLDefFenster = new aL();
            berechnenListener berechnenListener = new berechnenListener();

            graphikAuswahlListener graLi = new graphikAuswahlListener(this);
            defFenster = new matrixViewDef(aLDefFenster,berechnenListener,graLi);
           
            mV.getContentPane().add(defFenster);

            defFenster.setVisible(true);
        }

        private void erstelleMatrixViewAnzeige()
        {
        	//PlanQuadratController erstellen
            mPQC = new matrixPlanQuadratController(defFenster.holeX(),defFenster.holeY());

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
        	myABMV.add(erstelleAbstandsMatrixButtons(defFenster.holeX(),defFenster.holeY()));
    		myABMV.setVisible(true);
    		mV.add(myABMV);  
        }
        
        private void erstelleErgebnisViewAnzeige(String loesung)
        {
           myErgebnisAnzeige = new ergebnisAnzeige();
           myErgebnisAnzeige.setBackground(Color.lightGray);
           
           JPanel jP = new JPanel();
 	       jP.setLayout(new GridLayout(400,700));
 	       JTextArea myJT = new JTextArea(loesung);
 	       myJT.setBackground(Color.lightGray);
 	       myJT.setVisible(true);
 	       jP.add(myJT);
 	       jP.setVisible(true);
 	       JScrollPane jSP = new JScrollPane(jP);
           jSP.setVisible(true);
 	      
           myErgebnisAnzeige.add(jSP);
 	       myErgebnisAnzeige.setVisible(true);
 	       mV.add(myErgebnisAnzeige);
        }
        
        
        private JScrollPane erstelleAbstandsMatrixButtons(int x, int y)
        {
        	myABM = new AbstandsMatrix(mPQC.getHaltestellen().size(),mPQC.getHaltestellen(), x, y,
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
                          
                                if(!(hinterGrundBildName.equals("nochKeinBildAusgewaehlt")))
                                {
                                		Image teilbild = java.awt.Toolkit.getDefaultToolkit().createImage( 
                                        new FilteredImageSource( gesamtBild.getSource(), new CropImageFilter( (xi-1)*breiteProFeld, (i-1)*hoeheProFeld, breiteProFeld, hoeheProFeld ))); 
                                        icon = new ImageIcon(teilbild);
                                        mB = new matrixButton(Integer.toString(i) + "/" + Integer.toString(xi),i,xi,id,erstellePlanQuadrat(i,xi,id),icon, breiteProFeld,hoeheProFeld);
                                }
                                else 
                                {
                                	mB = new matrixButton(Integer.toString(i) + "/" + Integer.toString(xi),i,xi,id,erstellePlanQuadrat(i,xi,id));
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
                hinterGrundBildName = "nochKeinBildAusgewaehlt";
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
        
        
        
       //------------------------------------------------------------------------ 
        
        
        
        private void buttonDefAnzeigen(matrixButton b)
        {
                matrixPlanQuadrat mPQ = mPQC.holePlanQuadrat(b.holeID());
                matrixButtonDef mBD = new matrixButtonDef(b);

                //Werte setzen
                boolean auswahl0 = mPQ.holePassierbar();
                boolean auswahl1 = mPQ.holePotentHalteStelle();
                boolean auswahl2 = mPQ.holeFesteHalteStelle();

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
                mBD.setzeHSKapazitaet(mPQ.holeHSKapazitaet());
               
                mBD.setVisible(true);
               
                mV.getContentPane().add(mBD);
        }
        
        private void abstandsButtonDefAnzeigen(AbstandsMatrixButton b)
        {
        	System.out.println(b.holeX() + "/" + b.holeY());
        	System.out.println(myABM.holeWeg(b.holeX(),b.holeY()));
        	
        	JInternalFrame jF = new JInternalFrame("bester Weg",false,true,false,false);
        	jF.setSize(200,100);
        	jF.setLocation(500,500);
        	jF.setVisible(true);
        	JTextArea jTA = new JTextArea();
        	jTA.setText(myABM.holeWeg(b.holeX(),b.holeY()));
        	jTA.setVisible(true);
        	jF.add(jTA);
        	       	       	
        	mV.add(jF);
        	
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
        	private matrixController mC;
        	
        	public graphikAuswahlListener(matrixController mC_)
        	{
        		mC = mC_;
        	}
        	
        	public void actionPerformed(ActionEvent arg0) 
        	{
        		
        			FileDialog fd = new FileDialog(mV,"Öffnen",FileDialog.LOAD);      
        			fd.setVisible(true);
        			hinterGrundBildName = fd.getDirectory()+fd.getFile();      
        			this.mC.setGraphikName(fd.getFile());
        	}
		}
        
        
        class berechnenListener implements ActionListener
        {
        	public void actionPerformed(ActionEvent arg0)
        	{
        		erstelleAbstandsMatrixViewAnzeige();
        		mySolve = new Solve();
        		mySolve.setProperties(myABM.getAbstandsMatrix(), mPQC.getGewichtungsMatrix(), mPQC, true);	        	
        		erstelleErgebnisViewAnzeige(mySolve.start());
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
