package AnwBESF;
/*
 * Created on 13.05.2005
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

import java.awt.Color;
import javax.swing.*;
import java.awt.Insets;

public class matrixButton extends JButton
{
        private int id;
        private int x;
        private int y;

        private int auswahl;
        private int gewichtung;
        private int abstandNord;
        private int abstandOst;
        private int abstandSued;
        private int abstandWest;
        private int abstandNordWest;
        private int abstandNordOst;
        private int abstandSuedWest;
        private int abstandSuedOst;
          
        private int busAbstandNord;
        private int busAbstandOst;
        private int busAbstandSued;
        private int busAbstandWest;
        private int busAbstandNordWest;
        private int busAbstandNordOst;
        private int busAbstandSuedWest;
        private int busAbstandSuedOst;
        private int hsKapazitaet;
        private int linie; // 1 für Start LinieA; 2 für Ende LinieA; 3 für Start LinieB ...usw....
        private int anzahlLinien;
        
        private matrixPlanQuadrat mPQ;

        public matrixButton(String t)
        {
                super(t);
                this.setText(t);
                this.setContentAreaFilled(true);
                Insets in = this.getMargin();
                in.bottom = 0;
                in.left = 0;
                in.right = 0;
                in.top = 0;
                this.setMargin(in);        
                setBackground(Color.BLUE);
                setForeground(Color.WHITE);
                setEnabled(false);
        }

        public matrixButton(String t,int pX,int pY,int sID,matrixPlanQuadrat pQ, ImageIcon img, int breiteProFeld, int hoeheProFeld, int anzahlLinien_)
        {
                super(img);
                //this.setText(Integer.toString(sID));
                //this.setHorizontalTextPosition(10);
                this.setToolTipText("Feld " +sID);
                this.setBorderPainted(false);
                this.setContentAreaFilled(true);
                Insets in = this.getMargin();
                in.bottom = 0;
                in.left = 0;
                in.right = 0;
                in.top = 0;
                
                anzahlLinien = anzahlLinien_;
                
                this.setMargin(in);
       
        		//super(Integer.toString(sID));
                id = sID;

                mPQ = pQ;

                x = pX;
                y = pY;
                setBackground(Color.lightGray);
                //setForeground(Color.BLACK);
        }
        
        public matrixButton(String t,int pX,int pY,int sID,matrixPlanQuadrat pQ, int anzahlLinien_)
        {
                super(Integer.toString(sID));
                id = sID;
                
                mPQ = pQ;

                x = pX;
                y = pY;
                anzahlLinien = anzahlLinien_;
                setBackground(Color.lightGray);
                //setForeground(Color.BLACK);
        }
        
        
        public void aktualisiereButton()
        {
        	setForeground(Color.WHITE);
            setBackground(Color.WHITE);
            
        	if (mPQ.holePassierbar() == true)
			{
        		setForeground(Color.BLACK);
        		setBackground(Color.lightGray);
			}
        	
        	if (mPQ.holePotentHalteStelle() == true)
        	{
        		setForeground(Color.WHITE);
        		setBackground(Color.GREEN);
        	}
        	
        	if (mPQ.holeFesteHalteStelle() == true)
        	{
        		setBackground(Color.RED);
        		setForeground(Color.WHITE);
        	}
        }

        public int holeID()
        {
                return id;
        }

        public int holeX()
        {
                return x;
        }

        public int holeY()
        {
                return y;
        }

        public void setzeAuswahl(int a)
        {
                switch (a)
                {
                case 0:
                {
                        mPQ.setzePassierbar(true);
                        setForeground(Color.BLACK);
                        setBackground(Color.lightGray);
                       // this.setzeLinie(mPQ.holeLinie());
                        /*mPQ.setzeAbstandNord(10);
                        mPQ.setzeAbstandOst(10);
                        mPQ.setzeAbstandSued(10);
                        mPQ.setzeAbstandWest(10);
                        mPQ.setzeAbstandNordOst(14);
                        mPQ.setzeAbstandNordWest(14);
                        mPQ.setzeAbstandSuedWest(14);
                        mPQ.setzeAbstandSuedOst(14);*/
                        break;
                }
                case 1:
                {
                        if(!(mPQ.holePassierbar()))
                        {
                        	mPQ.setzeAbstandNord(mPQ.holeAbstandNormal());
                            mPQ.setzeAbstandOst(mPQ.holeAbstandNormal());
                            mPQ.setzeAbstandSued(mPQ.holeAbstandNormal());
                            mPQ.setzeAbstandWest(mPQ.holeAbstandNormal());
                            mPQ.setzeAbstandNordOst(mPQ.holeAbstandDiagonal());
                            mPQ.setzeAbstandNordWest(mPQ.holeAbstandDiagonal());
                            mPQ.setzeAbstandSuedWest(mPQ.holeAbstandDiagonal());
                            mPQ.setzeAbstandSuedOst(mPQ.holeAbstandDiagonal());
                            mPQ.setzeBusAbstandNord(mPQ.holeBusAbstandNormal());
                            mPQ.setzeBusAbstandOst(mPQ.holeBusAbstandNormal());
                            mPQ.setzeBusAbstandSued(mPQ.holeBusAbstandNormal());
                            mPQ.setzeBusAbstandWest(mPQ.holeBusAbstandNormal());
                            mPQ.setzeBusAbstandNordOst(mPQ.holeBusAbstandDiagonal());
                            mPQ.setzeBusAbstandNordWest(mPQ.holeBusAbstandDiagonal());
                            mPQ.setzeBusAbstandSuedWest(mPQ.holeBusAbstandDiagonal());
                            mPQ.setzeBusAbstandSuedOst(mPQ.holeBusAbstandDiagonal());
                        }
                	    mPQ.setzePotentHalteStelle(true);
                        setForeground(Color.WHITE);
                        setBackground(Color.GREEN);
                        this.setzeLinie(mPQ.holeLinie());
                        break;
                }
                case 2:
                {
                	if(!(mPQ.holePassierbar()))
                    {
                    	mPQ.setzeAbstandNord(mPQ.holeAbstandNormal());
                        mPQ.setzeAbstandOst(mPQ.holeAbstandNormal());
                        mPQ.setzeAbstandSued(mPQ.holeAbstandNormal());
                        mPQ.setzeAbstandWest(mPQ.holeAbstandNormal());
                        mPQ.setzeAbstandNordOst(mPQ.holeAbstandDiagonal());
                        mPQ.setzeAbstandNordWest(mPQ.holeAbstandDiagonal());
                        mPQ.setzeAbstandSuedWest(mPQ.holeAbstandDiagonal());
                        mPQ.setzeAbstandSuedOst(mPQ.holeAbstandDiagonal());
                        mPQ.setzeBusAbstandNord(mPQ.holeBusAbstandNormal());
                        mPQ.setzeBusAbstandOst(mPQ.holeBusAbstandNormal());
                        mPQ.setzeBusAbstandSued(mPQ.holeBusAbstandNormal());
                        mPQ.setzeBusAbstandWest(mPQ.holeBusAbstandNormal());
                        mPQ.setzeBusAbstandNordOst(mPQ.holeBusAbstandDiagonal());
                        mPQ.setzeBusAbstandNordWest(mPQ.holeBusAbstandDiagonal());
                        mPQ.setzeBusAbstandSuedWest(mPQ.holeBusAbstandDiagonal());
                        mPQ.setzeBusAbstandSuedOst(mPQ.holeBusAbstandDiagonal());
                    }
                	    mPQ.setzeFesteHalteStelle(true);
                        setBackground(Color.RED);
                        setForeground(Color.WHITE);
                        this.setzeLinie(mPQ.holeLinie());
                        break;
                }
                default:
                {
                	mPQ.setzePassierbar(false);
                	setForeground(Color.BLACK);
                    setBackground(Color.BLACK);
                    mPQ.setzeAbstandNord(10000);
                    mPQ.setzeAbstandOst(10000);
                    mPQ.setzeAbstandSued(10000);
                    mPQ.setzeAbstandWest(10000);
                    mPQ.setzeAbstandNordOst(10000);
                    mPQ.setzeAbstandNordWest(10000);
                    mPQ.setzeAbstandSuedWest(10000);
                    mPQ.setzeAbstandSuedOst(10000);
                    mPQ.setzeBusAbstandNord(10000);
                    mPQ.setzeBusAbstandOst(10000);
                    mPQ.setzeBusAbstandSued(10000);
                    mPQ.setzeBusAbstandWest(10000);
                    mPQ.setzeBusAbstandNordOst(10000);
                    mPQ.setzeBusAbstandNordWest(10000);
                    mPQ.setzeBusAbstandSuedWest(10000);
                    mPQ.setzeBusAbstandSuedOst(10000);
                    this.setzeLinie(mPQ.holeLinie());
                };
                }

        }

        public int holeAuswahl()
        {
                return auswahl;
        }

        public int holeGewichtung()
        {
                return gewichtung;
        }

        public void setzeGewichtung(int wert)
        {
                mPQ.setzeGewichtung(wert);
        }

        
 //------ABSTÄNDE ZU FUSS---------------------------------------------------------------------------
        public int holeAbstandNord() { return abstandNord; }
        public int holeAbstandOst() {  return abstandOst;   }
        public int holeAbstandSued(){ return abstandSued; }
        public int holeAbstandWest(){return abstandWest; }
               
        public int holeAbstandNordOst() {  return abstandNordOst;   }
        public int holeAbstandNordWest() {  return abstandNordWest;   }
        public int holeAbstandSuedOst() {  return abstandSuedOst;   }
        public int holeAbstandSuedWest() {  return abstandSuedWest;   }
        
        
        public void setzeAbstandNord(int wert)  { mPQ.setzeAbstandNord(wert); }
        public void setzeAbstandOst(int wert){  mPQ.setzeAbstandOst(wert); }
        public void setzeAbstandSued(int wert){ mPQ.setzeAbstandSued(wert); }
        public void setzeAbstandWest(int wert){ mPQ.setzeAbstandWest(wert); }
        
        
        public void setzeAbstandNordWest(int wert) { mPQ.setzeAbstandNordWest(wert);}
        public void setzeAbstandNordOst(int wert) { mPQ.setzeAbstandNordOst(wert);} 
        public void setzeAbstandSuedWest(int wert) { mPQ.setzeAbstandSuedWest(wert);} 
        public void setzeAbstandSuedOst(int wert) { mPQ.setzeAbstandSuedOst(wert);} 
 //-------------------------------------------------------------------------------------------------
        
 //------ABSTÄNDE FÜR DEN BUS-----------------------------------------------------------------------
        public int holeBusAbstandNord() { return busAbstandNord; }
        public int holeBusAbstandOst() {  return busAbstandOst;   }
        public int holeBusAbstandSued(){ return busAbstandSued; }
        public int holeBusAbstandWest(){return busAbstandWest; }
               
        public int holeBusAbstandNordOst() {  return busAbstandNordOst;   }
        public int holeBusAbstandNordWest() {  return busAbstandNordWest;   }
        public int holeBusAbstandSuedOst() {  return busAbstandSuedOst;   }
        public int holeBusAbstandSuedWest() {  return busAbstandSuedWest;   }
        
        
        public void setzeBusAbstandNord(int wert)  { mPQ.setzeBusAbstandNord(wert); }
        public void setzeBusAbstandOst(int wert){  mPQ.setzeBusAbstandOst(wert); }
        public void setzeBusAbstandSued(int wert){ mPQ.setzeBusAbstandSued(wert); }
        public void setzeBusAbstandWest(int wert){ mPQ.setzeBusAbstandWest(wert); }
        
        
        public void setzeBusAbstandNordWest(int wert) { mPQ.setzeBusAbstandNordWest(wert);}
        public void setzeBusAbstandNordOst(int wert) { mPQ.setzeBusAbstandNordOst(wert);} 
        public void setzeBusAbstandSuedWest(int wert) { mPQ.setzeBusAbstandSuedWest(wert);} 
        public void setzeBusAbstandSuedOst(int wert) { mPQ.setzeBusAbstandSuedOst(wert);} 
     
//-------------------------------------------------------------------------------------------------
        
        public void setzeHSKapazitaet(int wert) { mPQ.setzeHSKapazitaet(wert);  }
        
        public int holeHSKapazitaet()  {  	return hsKapazitaet;   }
        
        public void setzeLinie(int wert)
        { 
        	mPQ.setzeLinie(wert);
        }
        
        public int holeLinie() {  return linie;  }
        
        public int getAnzahlLinien()  {  return anzahlLinien;  }
}
