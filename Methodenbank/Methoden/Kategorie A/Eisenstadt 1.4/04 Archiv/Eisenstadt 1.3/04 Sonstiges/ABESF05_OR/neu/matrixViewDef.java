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
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;;


public class matrixViewDef extends JInternalFrame
{
        private JTextField anzahlX;
        private JTextField anzahlY;
        
        private JTextField abstandVertikalHorizontal;
        private JTextField abstandDiagonal;
        
        private JLabel labelAbstandVertikalHorizontal;
        private JLabel labelAnzahlDiagonal;
        
        private JTextField busAbstandVertikalHorizontal;
        private JTextField busAbstandDiagonal;
        
        private JLabel busLabelAbstandVertikalHorizontal;
        private JLabel busLabelAnzahlDiagonal;
        
        private JLabel labelAnzahlLinien;
        private JTextField textAnzahlLinien;
        
        private JTextField nameMatrix;
        private JLabel labelAnzahlX;
        private JLabel labelAnzahlY;
        private JLabel labelNameMatrix;
        private JButton neuButton;
        private JButton berechnenButton;
        private JButton graphikAuswahl;
        private JTextField graphikName;
        private JCheckBox jCB;
        private JLabel detErgebnis;

        public matrixViewDef(ActionListener neuListener, ActionListener berechnenListener, ActionListener graphikAuswahlListener)
        {
                super("Definition",false,false,false,false);
                setSize(200,220);
                setLocation(0,0);
                setBackground(Color.lightGray);

                labelAnzahlX = new JLabel("Breite");
                labelAnzahlY = new JLabel("Länge");
                //labelNameMatrix = new JLabel("Matrixname");
                graphikAuswahl = new JButton("Stadtkarte");
                graphikAuswahl.addActionListener(graphikAuswahlListener);
                anzahlX = new JTextField("8");
                anzahlY = new JTextField("8");
                //nameMatrix = new JTextField("Matrix");
                graphikName = new JTextField();
                graphikName.setEnabled(false);
                graphikName.setDisabledTextColor(Color.BLACK);
                
                labelAbstandVertikalHorizontal = new JLabel("zu Fuss: normal");
                labelAnzahlDiagonal = new JLabel("zu Fuss: diagonal");
                abstandVertikalHorizontal = new JTextField("10");
                abstandDiagonal = new JTextField("14");
                
                busLabelAbstandVertikalHorizontal = new JLabel("Bus: normal");
                busLabelAnzahlDiagonal = new JLabel("Bus: diagonal");
                busAbstandVertikalHorizontal = new JTextField("5");
                busAbstandDiagonal = new JTextField("7");
                
                labelAnzahlLinien = new JLabel("Anzahl Linien:");
                textAnzahlLinien = new JTextField("0");
                
                neuButton = new JButton("NEU");
                neuButton.addActionListener(neuListener);
                berechnenButton = new JButton("SOLVE");
                berechnenButton.addActionListener(berechnenListener);

                detErgebnis = new JLabel("det. Ergebnis");
                jCB = new JCheckBox("",false);
                
                
                getContentPane().setLayout(new GridLayout(10,3));

                //getContentPane().add(labelNameMatrix);
                //getContentPane().add(nameMatrix);
                getContentPane().add(labelAnzahlX);
                getContentPane().add(anzahlX);
                getContentPane().add(labelAnzahlY);
                getContentPane().add(anzahlY);
                getContentPane().add(labelAbstandVertikalHorizontal);
                getContentPane().add(abstandVertikalHorizontal);
                getContentPane().add(labelAnzahlDiagonal);
                getContentPane().add(abstandDiagonal);
                
                getContentPane().add(busLabelAbstandVertikalHorizontal);
                getContentPane().add(busAbstandVertikalHorizontal);
                getContentPane().add(busLabelAnzahlDiagonal);
                getContentPane().add(busAbstandDiagonal);
                
                
                getContentPane().add(detErgebnis);
                getContentPane().add(jCB);
                
                getContentPane().add(labelAnzahlLinien);
                getContentPane().add(textAnzahlLinien);
                
                getContentPane().add(graphikAuswahl);
                getContentPane().add(graphikName);
                getContentPane().add(neuButton);
                getContentPane().add(berechnenButton);
        }

        public void setX(String x) { anzahlX.setText(x); }
        public void setY(String y) { anzahlY.setText(y); }
        
        public int holeX()
        {
                int x = 0;
                x = Integer.parseInt(anzahlX.getText());
                return x;
        }

        public void setGraphikName(String graphikName_)
        {
        	this.graphikName.setText(graphikName_);
        }
        
        public String getGraphikName()
        {
        	return this.graphikName.getText();
        }
        
        public int holeY()
        {
                int y = 0;
                y = Integer.parseInt(anzahlY.getText());
                return y;
        }

        public String holeNameMatrix()
        {
                return nameMatrix.getText();
        }
        
        public boolean getDetailliertesErgebnis()
        {
        	return jCB.isSelected();
        }
        
        public int getNormalAbstand()  // Abstand zu Fuss
        {
        	return Integer.parseInt(abstandVertikalHorizontal.getText());
        }
        
        public int getDiagonalAbstand() // Abstand zu Fuss
        {
        	return Integer.parseInt(abstandDiagonal.getText());
        }
        
        public int getBusNormalAbstand()  // Abstand für den Bus
        {
        	return Integer.parseInt(busAbstandVertikalHorizontal.getText());
        }
        
        public int getBusDiagonalAbstand() // Abstand für den Bus
        {
        	return Integer.parseInt(busAbstandDiagonal.getText());
        }
        
        public int getAnzahlLinien()
        {
        	return Integer.parseInt(textAnzahlLinien.getText());
        }
}

