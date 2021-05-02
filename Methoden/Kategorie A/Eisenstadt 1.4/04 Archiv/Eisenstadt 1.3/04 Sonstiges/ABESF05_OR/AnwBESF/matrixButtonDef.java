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
        
        
        
        
        private matrixButton zugehoerigerButton;

        private JButton okButton;
        private static final String ActionEvent = null;

        public matrixButtonDef(matrixButton b)
        {	
        		super("Button " + b.holeID(),false,true,false,false);
        		setSize(200,300);
                setLocation(0,151);

                zugehoerigerButton = b;

                lAuswahl = new JLabel("Auswahl");
                lGewichtung = new JLabel("Personen");
                lAbstandNord = new JLabel("Abstand Nord");
                lAbstandOst = new JLabel("Abstand Ost");
                lAbstandSued = new JLabel("Abstand Süd");
                lAbstandWest = new JLabel("Abstand West");
                lAbstandNordOst = new JLabel("Abstand N/O");
                lAbstandNordWest = new JLabel("Abstand N/W");
                lAbstandSuedOst = new JLabel("Abstand S/O");
                lAbstandSuedWest = new JLabel("Abstand S/W");
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
                
            
                okButton = new JButton("Speichern");
                aLClose aLClose1 = new aLClose();
                okButton.addActionListener(aLClose1);

                getContentPane().setLayout(new GridLayout(12,2));

                getContentPane().add(lAuswahl);
                getContentPane().add(jCBAuswahl);
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

        public void setzeAbstandNord(int wert)
        {
                tFAbstandNord.setText(Integer.toString(wert));
        }

        public void setzeAbstandOst(int wert)
        {
                tFAbstandOst.setText(Integer.toString(wert));
        }

        public void setzeAbstandSued(int wert)
        {
                tFAbstandSued.setText(Integer.toString(wert));
        }

        public void setzeAbstandWest(int wert)
        {
                tFAbstandWest.setText(Integer.toString(wert));
        }
        
        public void setzeAbstandSuedOst(int wert)
        {
                tFAbstandSuedOst.setText(Integer.toString(wert));
        }

        public void setzeAbstandSuedWest(int wert)
        {
                tFAbstandSuedWest.setText(Integer.toString(wert));
        }
        public void setzeAbstandNordOst(int wert)
        {
                tFAbstandNordOst.setText(Integer.toString(wert));
        }

        public void setzeAbstandNordWest(int wert)
        {
                tFAbstandNordWest.setText(Integer.toString(wert));
        }
                
        public void setzeHSKapazitaet(int wert)
        {
        	tFhsKapazitaet.setText(Integer.toString(wert));
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
                        
                        zugehoerigerButton.setzeGewichtung(Integer.parseInt(tFGewichtung.getText()));
                        zugehoerigerButton.setzeHSKapazitaet(Integer.parseInt(tFhsKapazitaet.getText()));
                        zugehoerigerButton.setzeAuswahl(jCBAuswahl.getSelectedIndex());
                        System.out.println(zugehoerigerButton.holeID());
                        dispose();
                }
        }
}
