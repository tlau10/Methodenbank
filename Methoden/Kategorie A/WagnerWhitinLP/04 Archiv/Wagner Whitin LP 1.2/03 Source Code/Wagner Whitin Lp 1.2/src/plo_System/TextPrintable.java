//*******************************************************************
//*																	*
//*		Teil des P.L.O. Systems (c) 2001/2002						*
//* 	von Francis Goeltner, Helmut Lindinger, Bernd Saile			*
//*																	*
//*		Synopsis: Enthält die Klasse für den Datentyp, welcher	    *
//*		die Nachfrqagen eines Modells verwaltet		  				*
//*																	*
//*		Version: 1.0												*
//*																	*
//*     Version 1.2 												*
//*     von Marco Weiß und Jenne Justin								*
//*																	*
//*******************************************************************

package plo_System;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.JobAttributes;
import java.awt.PageAttributes;
import java.awt.PrintJob;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class TextPrintable implements Printable{

	private String[] data;
    private static ArrayList ar = null;
    private Font myFont = new Font("SansSerif", Font.PLAIN, 12);
 
    
//*** String **************************************************************

    // Fügt einen String hinzu und geht danach in die nächste Zeile
    public String addString(String Text) {
        if (ar == null) {
            ar = new ArrayList();
            ar.add(Text);
        } else {
            ar.add(Text);
        }
		return null;
     }
//**************************************************************
 
 
//*** Leerzeile ***********************************************
    // Fügt eine leerzeile hinzu
    public String addLeerzeile() {
        if (ar != null) {
            ar.add("\n");
        }
		return null;
    }
//**************************************************************
 
    
//*** Tab *******************************************************
    // Fügt einen Tab hinzu und somit kann man in der gleichen Zeile weiterschreiben
    public static String addTab() {
        if (ar != null) {
            ar.add("\t");
        }
		return null;
    }
 
    void druckeSeite(JFrame f, String title, boolean bRand) {
        druckeSeite(f, title, bRand, false);
    }
//**************************************************************
    
    
//*** Drucken ***************************************************
    // Druckt schließlich die Seite und ob ein Rand dabei ist oder nicht
     void druckeSeite(JFrame f, String title, boolean bRand, boolean bLandscape) {
 
        //falls der Frame null ist
        if (f == null) {
            f = new JFrame();
        }
 
        PageAttributes page = new PageAttributes();

        PrintJob prjob = f.getToolkit().getPrintJob(f, title, new JobAttributes(), page);
 
        if (null != prjob) {
            final int iScreenResol = f.getToolkit().getScreenResolution();
            final int iPageResol = prjob.getPageResolution();
            final Dimension dimScreenSize = f.getToolkit().getScreenSize();
            final Dimension dimPageDimension = prjob.getPageDimension();
            Graphics pg = prjob.getGraphics();
 
            if (null != pg && 0 < iPageResol) {
                //merkt sich die Größe
                int iAddY = pg.getFontMetrics(this.myFont).getHeight();
                int iRand = (int) Math.round(iPageResol * 2. / 2.54); // 2 cm Rand
                int iPosX = iRand + iRand / 4; // Textposition
                int iPosY = iPosX - iAddY / 2;
                int iWdth = dimPageDimension.width - iRand * 2; // innere Breite
                int iMidY = dimPageDimension.height / 2;
 
                //setzt die Schrift und die Schriftgröße
                pg.setFont(myFont);
                if (ar != null) {
                    data = (String[]) ar.toArray(new String[ar.size()]);
                    for (int x = 0; x != data.length; x++) {
                        //fügt ein Leerzeichen hinzu
                        if (data[x].equals("\n")) {
                            iPosY += (iAddY * 0.65); //nicht die komplette Höhe nehmen
                        } else if (data[x].equals("\t")) {
                            //der 10 steht für die Breite des Tabs, variable vereinbar
                            iPosX += (iAddY * 2 / 3) * 10;
                            //wenn die Tab position schon weiter ist, als die Weite, dann wird auf
                            //Anfang gesetzt und neue Zeile
                            if (iPosX > iWdth) {
                                iPosX = iRand + iRand / 4;
                                iPosY += iAddY;
                            } else {
                                iPosY -= iAddY;
                            }
                        } //wenn nichts erfüllt wird, wird einfach dazugehängt
                        else {
                            String str[] = seperateString(data[x], pg.getFontMetrics(myFont), true, iWdth);
                            for (int y = 0; str != null && y != str.length; y++) {
                                if (y > 0) {
                                    iPosY += (iAddY * 0.65); //nicht die komplette Höhe nehmen
                                }
                                pg.drawString(str[y], iPosX, iPosY += iAddY);
                            }
                        }
                    }
 
                    //Buffer wird geleert
                    ar = null;
 
                } //wenn man noch gar nix hineingeschrieben hat, wird der Fehlertext gedruckt,
                //variable abendbar, ob überhaupt gedruckt werden soll
                else {
                    pg.drawString("Error, not initialized", iPosX, iPosY += iAddY);
                }
 
                //wenn rand true ist, wird ein Rand gezeichnet
                if (bRand) {
                    pg.drawRect(iRand, iRand, iWdth, dimPageDimension.height - iRand * 2);
                }
                pg.dispose();
            }
            prjob.end();
        }
    }
 //**************************************************************
    
    
//*** Bildschirm ausgabe ************************************************************
     // Funktion zerlegt die Strings für die Anpassung am Bildschirm
     
   /*   strText = der Text
        fontMet FontMetrics = welche Schriftart
        wrapword boolean =  ob Wörter abgeteilt werden sollen oder nicht
        iWidth int = die Breite des Blatts
   */
    private String[] seperateString(String strText, FontMetrics fontMet, boolean wrapword, int iWidth) {
 
        ArrayList myTmp = new ArrayList();
        int z = 0; //merkt sich den Index
 
        //geht die Wörter durch und sollte sie abteilen
        for (int x = 0, y = 0; x != strText.length(); x++) {
            y += fontMet.charWidth(strText.charAt(x));
            if (y > iWidth) {
                y = 0;
                x--;
                //wenn wrapword ist, bei einem Leerzeichen abtrennen
                if (wrapword) {
                    x = strText.lastIndexOf(" ", x) + 1; //+1 damit er das Leerzeichen mitausdruckt und nicht in der nächsten Zeile steht
                }
 
                myTmp.add(strText.substring(z, x));
                z = x;
            }
        }
 
        //damit er auch den letzten Teil hinzufügt
        myTmp.add(strText.substring(z, strText.length()));
 
        //Gibt die ArrayListe als String Objekt zurück
        return (String[]) myTmp.toArray(new String[myTmp.size()]);
    }
 //**************************************************************
    
    
 //*** Schriftart ***********************************************
    
    // Funktion setzt eine neue Schriftart
      public void setFont(Font font) {
        this.myFont = font;
    }

	@Override
	public int print(Graphics arg0, PageFormat arg1, int arg2) throws PrinterException {
		return 0;
	}
//**************************************************************
	
}

//****************************************************************************************************************************
