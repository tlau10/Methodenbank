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

public class matrixViewDef extends JInternalFrame
{
        private JTextField anzahlX;
        private JTextField anzahlY;
        private JTextField nameMatrix;
        private JLabel labelAnzahlX;
        private JLabel labelAnzahlY;
        private JLabel labelNameMatrix;
        public JButton neuButton;
        public JButton berechnenButton;
        public JButton graphikAuswahl;
        public JTextField graphikName;

        public matrixViewDef(ActionListener neuListener, ActionListener berechnenListener, ActionListener graphikAuswahlListener)
        {
                super("Definition",false,false,false,false);
                setSize(200,150);
                setLocation(0,0);
                setBackground(Color.lightGray);

                labelAnzahlX = new JLabel("Anzahl X");
                labelAnzahlY = new JLabel("Anzahl Y");
                labelNameMatrix = new JLabel("Matrixname");
                graphikAuswahl = new JButton("Stadtkarte");
                graphikAuswahl.addActionListener(graphikAuswahlListener);
                anzahlX = new JTextField("5");
                anzahlY = new JTextField("5");
                nameMatrix = new JTextField("Matrix");
                graphikName = new JTextField();
                graphikName.setEnabled(false);
                graphikName.setDisabledTextColor(Color.BLACK);
                
                neuButton = new JButton("NEU");
                neuButton.addActionListener(neuListener);
                berechnenButton = new JButton("SOLVE");
                berechnenButton.addActionListener(berechnenListener);

                getContentPane().setLayout(new GridLayout(6,3));

                getContentPane().add(labelNameMatrix);
                getContentPane().add(nameMatrix);
                getContentPane().add(labelAnzahlX);
                getContentPane().add(anzahlX);
                getContentPane().add(labelAnzahlY);
                getContentPane().add(anzahlY);
                getContentPane().add(graphikAuswahl);
                getContentPane().add(graphikName);
                getContentPane().add(neuButton);
                getContentPane().add(berechnenButton);
        }

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
}
