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
          
        private int hsKapazitaet;
        private matrixPlanQuadrat mPQ;

        public matrixButton(String t)
        {
                super(t);
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

        public matrixButton(String t,int pX,int pY,int sID,matrixPlanQuadrat pQ, ImageIcon img, int breiteProFeld, int hoeheProFeld)
        {
                super(img);
                //this.setText(Integer.toString(sID));
                //this.setHorizontalTextPosition(10);
                this.setBorderPainted(false);
                this.setContentAreaFilled(true);
                Insets in = this.getMargin();
                in.bottom = 0;
                in.left = 0;
                in.right = 0;
                in.top = 0;
                this.setMargin(in);
       
        		//super(Integer.toString(sID));
                id = sID;

                mPQ = pQ;

                x = pX;
                y = pY;
                setBackground(Color.lightGray);
                //setForeground(Color.BLACK);
        }
        
        public matrixButton(String t,int pX,int pY,int sID,matrixPlanQuadrat pQ)
        {
                super(Integer.toString(sID));
                id = sID;
                
                mPQ = pQ;

                x = pX;
                y = pY;
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
                        mPQ.setzeAbstandNord(10);
                        mPQ.setzeAbstandOst(10);
                        mPQ.setzeAbstandSued(10);
                        mPQ.setzeAbstandWest(10);
                        mPQ.setzeAbstandNordOst(14);
                        mPQ.setzeAbstandNordWest(14);
                        mPQ.setzeAbstandSuedWest(14);
                        mPQ.setzeAbstandSuedOst(14);
                        break;
                }
                case 1:
                {
                        mPQ.setzePotentHalteStelle(true);
                        setForeground(Color.WHITE);
                        setBackground(Color.GREEN);
                        break;
                }
                case 2:
                {
                		mPQ.setzeFesteHalteStelle(true);
                        setBackground(Color.RED);
                        setForeground(Color.WHITE);
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

        public int holeAbstandNord()
        {
                return abstandNord;
        }

        public void setzeAbstandNord(int wert)
        {
                mPQ.setzeAbstandNord(wert);
        }

        public int holeAbstandOst() {  return abstandOst;   }

        public void setzeAbstandOst(int wert)
        {
                mPQ.setzeAbstandOst(wert);
        }

        public int holeAbstandSued()
        {
                return abstandSued;
        }

        public void setzeAbstandSued(int wert)
        {
                mPQ.setzeAbstandSued(wert);
        }

        public int holeAbstandWest()
        {
                return abstandWest;
        }

        public void setzeAbstandWest(int wert)
        {
                mPQ.setzeAbstandWest(wert);
        }
        
        public void setzeAbstandNordWest(int wert) { mPQ.setzeAbstandNordWest(wert);}
        public void setzeAbstandNordOst(int wert) { mPQ.setzeAbstandNordOst(wert);} 
        public void setzeAbstandSuedWest(int wert) { mPQ.setzeAbstandSuedWest(wert);} 
        public void setzeAbstandSuedOst(int wert) { mPQ.setzeAbstandSuedOst(wert);} 
        
        public int holeAbstandNordOst() {  return abstandNordOst;   }
        public int holeAbstandNordWest() {  return abstandNordWest;   }
        public int holeAbstandSuedOst() {  return abstandSuedOst;   }
        public int holeAbstandSuedWest() {  return abstandSuedWest;   }
        
        
        public void setzeHSKapazitaet(int wert)
        {
        	mPQ.setzeHSKapazitaet(wert);
        }
        
        public int holeHSKapazitaet()
        {
        	return hsKapazitaet;
        }
}
