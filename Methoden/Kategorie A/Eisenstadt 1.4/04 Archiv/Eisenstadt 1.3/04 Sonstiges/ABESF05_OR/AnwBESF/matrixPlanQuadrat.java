package AnwBESF;
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
public class matrixPlanQuadrat
{
        final private int ID;

        final private int xKoordinate;
        final private int yKoordinate;

        private boolean passierbar;
        private boolean potentHalteStelle;
        private boolean festeHalteStelle;
        private int gewichtung;
        private int abstandNord;
        private int abstandOst;
        private int abstandWest;
        private int abstandSued;
        private int abstandNordWest;
        private int abstandNordOst;
        private int abstandSuedWest;
        private int abstandSuedOst;
        private int hsKapazitaet;

        public matrixPlanQuadrat(int x, int y,int id)
        {
                ID = id;
                gewichtung = id;
                xKoordinate = x;
                yKoordinate = y;

                if (id != 0)
                {
                	passierbar = true;
                	potentHalteStelle = false;
                	festeHalteStelle = false;
                	abstandNord = 10;
                	abstandOst = 10;
                	abstandWest = 10;
                	abstandSued = 10;
                	abstandNordWest = 14;
                	abstandSuedWest = 14;
                	abstandNordOst= 14;
                	abstandSuedOst = 14;
                	gewichtung = 1;
                	hsKapazitaet = 0;
                }
                else
                {
                	passierbar = false;
                	potentHalteStelle = false;
                	festeHalteStelle = false;
                	abstandNord = 0;
                	abstandOst = 0;
                	abstandWest = 0;
                	abstandSued = 0;
                	abstandNordWest = 0;
                	abstandNordOst = 0;
                	abstandSuedWest = 0;
                	abstandSuedOst = 0;
                	gewichtung = 0;
                }
        }

        public int holeID()
        {
                return ID;
        }

        public int holeX()
        {
                return xKoordinate;
        }

        public int holeY()
        {
                return yKoordinate;
        }

        public boolean holePassierbar()
        {
                return passierbar;
        }

        public void setzePassierbar(boolean wert)
        {
                passierbar = wert;
                potentHalteStelle = false;
                festeHalteStelle = false;
        }

        public boolean holePotentHalteStelle()
        {
                return potentHalteStelle;
        }

        public void setzePotentHalteStelle(boolean wert)
        {
                if (!wert)
                {
                    passierbar = true;
                }

                potentHalteStelle = wert;

                festeHalteStelle = false;
        }

        public boolean holeFesteHalteStelle()
        {
                return festeHalteStelle;
        }

        public void setzeFesteHalteStelle(boolean wert)
        {
                if (!wert)
                {
                        passierbar = true;
                        potentHalteStelle = wert;
                }

                festeHalteStelle = wert;
        }

        public int holeGewichtung()
        {
                return gewichtung;
        }

        public void setzeGewichtung(int wert)
        {
                gewichtung = wert;
        }

        public int holeAbstandNord()
        {
                return abstandNord;
        }

        public void setzeAbstandNord(int wert) { abstandNord = wert;  }
        public void setzeAbstandSued(int wert) { abstandSued = wert;  }
        public void setzeAbstandOst(int wert)  { abstandOst = wert;   }
        public void setzeAbstandWest(int wert) { abstandWest = wert;   }
        
        public int holeAbstandOst()
        {
                return abstandOst;
        }

       
        public int holeAbstandSued()
        {
                return abstandSued;
        }
        
        
        public int holeAbstandSuedWest() { return abstandSuedWest;   }  
        public int holeAbstandSuedOst()  { return abstandSuedOst;    }  
        public int holeAbstandNordWest() { return abstandNordWest;   } 
        public int holeAbstandNordOst()  { return abstandNordOst;    } 
        
        public void setzeAbstandSuedOst(int wert){ abstandSuedOst = wert;  }
        public void setzeAbstandSuedWest(int wert){ abstandSuedWest = wert;  }
        public void setzeAbstandNordWest(int wert){ abstandNordWest = wert;  }
        public void setzeAbstandNordOst(int wert){ abstandNordOst = wert;  }
        
        
       
        
        public void setzeHSKapazitaet(int wert)
        {
        	hsKapazitaet = wert;
        }
        
        public int holeHSKapazitaet()
        {
        	return hsKapazitaet;
        }

        public int holeAbstandWest()
        {
                return abstandWest;
        }

       

}
