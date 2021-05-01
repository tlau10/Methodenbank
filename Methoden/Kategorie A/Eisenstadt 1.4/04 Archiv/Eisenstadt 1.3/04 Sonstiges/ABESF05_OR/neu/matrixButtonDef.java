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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class matrixButtonDef extends JInternalFrame
{
        private JLabel lAuswahl;
        private static final String[] AUSWAHL =
        {
                        "passierbar", "pot. Haltestelle", "feste Haltestelle", "unpassierbar"
        };

        private JLabel lGewichtung;
        private JLabel lAbstandNord;
        private JLabel lAbstandOst;
        private JLabel lAbstandSued;
        private JLabel lAbstandWest;
        private JLabel lhsKapazitaet;
        private JLabel lAbstandNordOst; 
        private JLabel lAbstandNordWest; 
        private JLabel lAbstandSuedOst; 
        private JLabel lAbstandSuedWest;
        
        private JLabel buslAbstandNord;
        private JLabel buslAbstandOst;
        private JLabel buslAbstandSued;
        private JLabel buslAbstandWest;
        private JLabel buslhsKapazitaet;
        private JLabel buslAbstandNordOst; 
        private JLabel buslAbstandNordWest; 
        private JLabel buslAbstandSuedOst; 
        private JLabel buslAbstandSuedWest;
        

        private JComboBox jCBAuswahl;
        private JTextField tFGewichtung;
        private JTextField tFAbstandNord;
        private JTextField tFAbstandOst;
        private JTextField tFAbstandSued;
        private JTextField tFAbstandWest;
        private JTextField tFhsKapazitaet;
        private JTextField tFAbstandNordOst; 
        private JTextField tFAbstandNordWest; 
        private JTextField tFAbstandSuedOst; 
        private JTextField tFAbstandSuedWest;
        
        private JTextField bustFAbstandNord;
        private JTextField bustFAbstandOst;
        private JTextField bustFAbstandSued;
        private JTextField bustFAbstandWest;
        private JTextField bustFhsKapazitaet;
        private JTextField bustFAbstandNordOst; 
        private JTextField bustFAbstandNordWest; 
        private JTextField bustFAbstandSuedOst; 
        private JTextField bustFAbstandSuedWest;
        
        private JLabel anzahlLinien;
        private JComboBox linienAuswahl;
        private String[] bezeichnungLinien;
        
        private matrixButton zugehoerigerButton;

        private JButton okButton;
        private static final String ActionEvent = null;
      

        public matrixButtonDef(matrixButton b)
        {	
        		super("Feld " + b.holeID(),false,true,false,false);
        		setSize(200,450);
                setLocation(0,220);

                zugehoerigerButton = b;
                bezeichnungLinien = new String[(b.getAnzahlLinien()*2)];
                
                int linie=0;
                for(int x=0; x < b.getAnzahlLinien(); x++)
                {
                	bezeichnungLinien[linie] = "Start L. " + (x+1);
                	bezeichnungLinien[linie+1] = "Ende L. " + (x+1);
                	linie += 2;
                }
               
                
                anzahlLinien = new JLabel("Linie");
                lAuswahl = new JLabel("Auswahl");
                lGewichtung = new JLabel("Personen");
                lAbstandNord = new JLabel("zu Fuss: Nord");
                lAbstandOst = new JLabel("zu Fuss: Ost");
                lAbstandSued = new JLabel("zu Fuss: Süd");
                lAbstandWest = new JLabel("zu Fuss: West");
                lAbstandNordOst = new JLabel("zu Fuss: N/O");
                lAbstandNordWest = new JLabel("zu Fuss: N/W");
                lAbstandSuedOst = new JLabel("zu Fuss: S/O");
                lAbstandSuedWest = new JLabel("zu Fuss: S/W");
                buslAbstandNord = new JLabel("Bus: Nord");
                buslAbstandOst = new JLabel("Bus: Ost");
                buslAbstandSued = new JLabel("Bus: Süd");
                buslAbstandWest = new JLabel("Bus: West");
                buslAbstandNordOst = new JLabel("Bus: N/O");
                buslAbstandNordWest = new JLabel("Bus: N/W");
                buslAbstandSuedOst = new JLabel("Bus: S/O");
                buslAbstandSuedWest = new JLabel("Bus: S/W");
                
                
                lhsKapazitaet = new JLabel("HS-Kapazitaet");
                jCBAuswahl = new JComboBox(AUSWAHL);
                
                tFGewichtung = new JTextField();
                tFAbstandNord = new JTextField();
                tFAbstandOst = new JTextField();
                tFAbstandSued = new JTextField();
                tFAbstandWest = new JTextField();
                tFhsKapazitaet = new JTextField();
                tFAbstandNordOst = new JTextField();
                tFAbstandNordWest = new JTextField();
                tFAbstandSuedOst = new JTextField();
                tFAbstandSuedWest = new JTextField();
                
                bustFAbstandNord = new JTextField();
                bustFAbstandOst = new JTextField();
                bustFAbstandSued = new JTextField();
                bustFAbstandWest = new JTextField();
                bustFhsKapazitaet = new JTextField();
                bustFAbstandNordOst = new JTextField();
                bustFAbstandNordWest = new JTextField();
                bustFAbstandSuedOst = new JTextField();
                bustFAbstandSuedWest = new JTextField();
                
                linienAuswahl = new JComboBox(bezeichnungLinien);
            
                okButton = new JButton("Speichern");
                aLClose aLClose1 = new aLClose();
                okButton.addActionListener(aLClose1);

                getContentPane().setLayout(new GridLayout(21,2));

                getContentPane().add(lAuswahl);
                getContentPane().add(jCBAuswahl);
                getContentPane().add(anzahlLinien);
                getContentPane().add(linienAuswahl);
                getContentPane().add(lGewichtung);
                getContentPane().add(tFGewichtung);
                
                getContentPane().add(lAbstandNord);
                getContentPane().add(tFAbstandNord);
                getContentPane().add(lAbstandOst);
                getContentPane().add(tFAbstandOst);
                getContentPane().add(lAbstandSued);
                getContentPane().add(tFAbstandSued);
                getContentPane().add(lAbstandWest);
                getContentPane().add(tFAbstandWest);
                getContentPane().add(lAbstandNordOst);
                getContentPane().add(tFAbstandNordOst);
                getContentPane().add(lAbstandNordWest);
                getContentPane().add(tFAbstandNordWest);
                getContentPane().add(lAbstandSuedOst);
                getContentPane().add(tFAbstandSuedOst);
                getContentPane().add(lAbstandSuedWest);
                getContentPane().add(tFAbstandSuedWest);
                
                getContentPane().add(buslAbstandNord);
                getContentPane().add(bustFAbstandNord);
                getContentPane().add(buslAbstandOst);
                getContentPane().add(bustFAbstandOst);
                getContentPane().add(buslAbstandSued);
                getContentPane().add(bustFAbstandSued);
                getContentPane().add(buslAbstandWest);
                getContentPane().add(bustFAbstandWest);
                getContentPane().add(buslAbstandNordOst);
                getContentPane().add(bustFAbstandNordOst);
                getContentPane().add(buslAbstandNordWest);
                getContentPane().add(bustFAbstandNordWest);
                getContentPane().add(buslAbstandSuedOst);
                getContentPane().add(bustFAbstandSuedOst);
                getContentPane().add(buslAbstandSuedWest);
                getContentPane().add(bustFAbstandSuedWest);
                
                getContentPane().add(lhsKapazitaet);
                getContentPane().add(tFhsKapazitaet);
                getContentPane().add(okButton);
        }

        public void setzeAuswahl(int a)
        {
                jCBAuswahl.setSelectedIndex(a);
        }

        public void setzeGewichtung(int wert)
        {
                tFGewichtung.setText(Integer.toString(wert));
        }

        public void setzeAbstandNord(int wert)  {  tFAbstandNord.setText(Integer.toString(wert));}
        public void setzeAbstandOst(int wert)   {  tFAbstandOst.setText(Integer.toString(wert)); }
        public void setzeAbstandSued(int wert)  {  tFAbstandSued.setText(Integer.toString(wert));}
        public void setzeAbstandWest(int wert)  {  tFAbstandWest.setText(Integer.toString(wert));}
        public void setzeAbstandSuedOst(int wert){ tFAbstandSuedOst.setText(Integer.toString(wert));}
        public void setzeAbstandSuedWest(int wert){tFAbstandSuedWest.setText(Integer.toString(wert));}
        public void setzeAbstandNordOst(int wert) {tFAbstandNordOst.setText(Integer.toString(wert));}
        public void setzeAbstandNordWest(int wert){tFAbstandNordWest.setText(Integer.toString(wert));}
        
        
        public void setzeBusAbstandNord(int wert)   { bustFAbstandNord.setText(Integer.toString(wert));}
        public void setzeBusAbstandOst(int wert)    { bustFAbstandOst.setText(Integer.toString(wert)); }
        public void setzeBusAbstandSued(int wert)   { bustFAbstandSued.setText(Integer.toString(wert));}
        public void setzeBusAbstandWest(int wert)   { bustFAbstandWest.setText(Integer.toString(wert));}
        public void setzeBusAbstandSuedOst(int wert){ bustFAbstandSuedOst.setText(Integer.toString(wert));}
        public void setzeBusAbstandSuedWest(int wert){bustFAbstandSuedWest.setText(Integer.toString(wert));}
        public void setzeBusAbstandNordOst(int wert) {bustFAbstandNordOst.setText(Integer.toString(wert));}
        public void setzeBusAbstandNordWest(int wert){bustFAbstandNordWest.setText(Integer.toString(wert));}
        
        public void setzeHSKapazitaet(int wert)
        {
        	tFhsKapazitaet.setText(Integer.toString(wert));
        }

        public void setzeLinie(int wert)
        {
        	linienAuswahl.setSelectedIndex(wert-1);
        }
        
        
        class aLClose implements ActionListener
        {
                public void actionPerformed(ActionEvent arg0)
                {
                        zugehoerigerButton.setzeAbstandNord(Integer.parseInt(tFAbstandNord.getText()));
                        zugehoerigerButton.setzeAbstandSued(Integer.parseInt(tFAbstandSued.getText()));
                        zugehoerigerButton.setzeAbstandOst(Integer.parseInt(tFAbstandOst.getText()));
                        zugehoerigerButton.setzeAbstandWest(Integer.parseInt(tFAbstandWest.getText()));
                        zugehoerigerButton.setzeAbstandNordOst(Integer.parseInt(tFAbstandNordOst.getText()));
                        zugehoerigerButton.setzeAbstandSuedOst(Integer.parseInt(tFAbstandSuedOst.getText()));
                        zugehoerigerButton.setzeAbstandNordWest(Integer.parseInt(tFAbstandNordWest.getText()));
                        zugehoerigerButton.setzeAbstandSuedWest(Integer.parseInt(tFAbstandSuedWest.getText()));
                        
                        zugehoerigerButton.setzeBusAbstandNord(Integer.parseInt(bustFAbstandNord.getText()));
                        zugehoerigerButton.setzeBusAbstandSued(Integer.parseInt(bustFAbstandSued.getText()));
                        zugehoerigerButton.setzeBusAbstandOst(Integer.parseInt(bustFAbstandOst.getText()));
                        zugehoerigerButton.setzeBusAbstandWest(Integer.parseInt(bustFAbstandWest.getText()));
                        zugehoerigerButton.setzeBusAbstandNordOst(Integer.parseInt(bustFAbstandNordOst.getText()));
                        zugehoerigerButton.setzeBusAbstandSuedOst(Integer.parseInt(bustFAbstandSuedOst.getText()));
                        zugehoerigerButton.setzeBusAbstandNordWest(Integer.parseInt(bustFAbstandNordWest.getText()));
                        zugehoerigerButton.setzeBusAbstandSuedWest(Integer.parseInt(bustFAbstandSuedWest.getText()));
                                                
                        zugehoerigerButton.setzeGewichtung(Integer.parseInt(tFGewichtung.getText()));
                        zugehoerigerButton.setzeHSKapazitaet(Integer.parseInt(tFhsKapazitaet.getText()));
                        zugehoerigerButton.setzeLinie((linienAuswahl.getSelectedIndex())+1);
                        zugehoerigerButton.setzeAuswahl(jCBAuswahl.getSelectedIndex());
                        
                        
                        //System.out.println(zugehoerigerButton.holeID());
                        dispose();
                }
        }
}

