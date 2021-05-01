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
        
        private int busAbstandNord;
        private int busAbstandOst;
        private int busAbstandWest;
        private int busAbstandSued;
        private int busAbstandNordWest;
        private int busAbstandNordOst;
        private int busAbstandSuedWest;
        private int busAbstandSuedOst;
        
        private int hsKapazitaet;
        
        
        private int abstandNormal;
        private int abstandDiagonal;
        private int busAbstandNormal;
        private int busAbstandDiagonal;

        private int linie;
        
        public matrixPlanQuadrat(int x, int y,int id)
        {
                ID = id;
                gewichtung = id;
                xKoordinate = x;
                yKoordinate = y;

                linie = 0;
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
                	
                	busAbstandNord = 5;
                    busAbstandOst = 5;
                    busAbstandWest = 5;
                    busAbstandSued = 5;
                    busAbstandNordWest = 7;
                    busAbstandNordOst = 7;
                    busAbstandSuedWest = 7;
                    busAbstandSuedOst = 7;
                	
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
                	
                	busAbstandNord = 0;
                    busAbstandOst = 0;
                    busAbstandWest = 0;
                    busAbstandSued = 0;
                    busAbstandNordWest = 0;
                    busAbstandNordOst = 0;
                    busAbstandSuedWest = 0;
                    busAbstandSuedOst = 0;
                	
                	
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

    
//-------ABSTÄNDE ZU FUSS!!!---------------------------------------------------------------
        public void setzeAbstandNord(int wert) { abstandNord = wert;  }
        public void setzeAbstandSued(int wert) { abstandSued = wert;  }
        public void setzeAbstandOst(int wert)  { abstandOst = wert;   }
        public void setzeAbstandWest(int wert) { abstandWest = wert;   }
        
        public void setzeAbstandSuedOst(int wert){ abstandSuedOst = wert;  }
        public void setzeAbstandSuedWest(int wert){ abstandSuedWest = wert;  }
        public void setzeAbstandNordWest(int wert){ abstandNordWest = wert;  }
        public void setzeAbstandNordOst(int wert){ abstandNordOst = wert;  }
        
        public int holeAbstandWest() {  return abstandWest;  }
        public int holeAbstandNord() { return abstandNord;  }
        public int holeAbstandOst() { return abstandOst; }
        public int holeAbstandSued(){ return abstandSued; }
        
        
        public int holeAbstandSuedWest() { return abstandSuedWest;   }  
        public int holeAbstandSuedOst()  { return abstandSuedOst;    }  
        public int holeAbstandNordWest() { return abstandNordWest;   } 
        public int holeAbstandNordOst()  { return abstandNordOst;    } 
        
//------------------------------------------------------------------------------------------     
        
//-------ABSTÄNDE FÜR DEN BUS !!!-----------------------------------------------------------
        public void setzeBusAbstandNord(int wert) { busAbstandNord = wert;  }
        public void setzeBusAbstandSued(int wert) { busAbstandSued = wert;  }
        public void setzeBusAbstandOst(int wert)  { busAbstandOst = wert;   }
        public void setzeBusAbstandWest(int wert) { busAbstandWest = wert;  }
        
        public void setzeBusAbstandSuedOst(int wert) { busAbstandSuedOst = wert;  }
        public void setzeBusAbstandSuedWest(int wert){ busAbstandSuedWest = wert; }
        public void setzeBusAbstandNordWest(int wert){ busAbstandNordWest = wert; }
        public void setzeBusAbstandNordOst(int wert) { busAbstandNordOst = wert;  }
        
        public int holeBusAbstandWest() {  return busAbstandWest;  }
        public int holeBusAbstandNord() { return busAbstandNord;  }
        public int holeBusAbstandOst() { return busAbstandOst; }
        public int holeBusAbstandSued(){ return busAbstandSued; }
        
        public int holeBusAbstandSuedWest() { return busAbstandSuedWest;   } 
        public int holeBusAbstandSuedOst()  { return busAbstandSuedOst;    }  
        public int holeBusAbstandNordWest() { return busAbstandNordWest;   } 
        public int holeBusAbstandNordOst()  { return busAbstandNordOst;    } 
        
//--------------------------------------------------------------------------------------------
        
        public void setzeHSKapazitaet(int wert)
        {
        	hsKapazitaet = wert;
        }
        
        public int holeHSKapazitaet()
        {
        	return hsKapazitaet;
        }


        
//------Get&Set-Methoden um den vom Benutzer definierten Standardabstand beim Wechsel von "Unpassierbar" zu "pot.HS" oder "feste HS" wieder herzustellen
        public void setzeAbstandNormal(int wert) { abstandNormal = wert; }
        public void setzeBusAbstandNormal(int wert) { busAbstandNormal = wert; }
        public void setzeAbstandDiagonal(int wert) { abstandDiagonal = wert; }
        public void setzeBusAbstandDiagonal(int wert) { busAbstandDiagonal = wert; }
        public int holeAbstandDiagonal() { return abstandDiagonal; }
        public int holeBusAbstandDiagonal() { return busAbstandDiagonal; }
        public int holeBusAbstandNormal() { return busAbstandNormal; }
        public int holeAbstandNormal() { return abstandNormal; }
        
        public void setzeLinie(int wert) { linie = wert; }
        public int holeLinie() { return linie; }
        
}
