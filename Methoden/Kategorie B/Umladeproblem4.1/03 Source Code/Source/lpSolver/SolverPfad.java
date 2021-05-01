package lpSolver;

// Die Klasse wurde von studierenden entwickelt um Solver Pfad zu verbessern 

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jtoolbox.INIDatei;


public class SolverPfad extends JFrame {

	private static final long serialVersionUID = 1L;
	
	 private javax.swing.JLabel jLabel1;
	 private javax.swing.JPanel jPanel1;
	 private javax.swing.JTextField pfadEingabeTextField;
	 public javax.swing.JButton OK;
	 public javax.swing.JButton Abbrechen;

	
	
	private static final String iniPath = "paths.ini";
	private static final INIDatei paths = new INIDatei(iniPath);
	private String result = "c:\\methodenbank\\solver\\lp_solve\\exec\\lp_solve.exe";



	
	public SolverPfad() {
		super.setTitle("Solver Pfad Einstellung:");
		initComponents();
	}
	

	private void initComponents() {
		
	
		jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

	
        pfadEingabeTextField = new javax.swing.JTextField(result);
        OK = new javax.swing.JButton();
        Abbrechen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Solver Pfad:");
       
        pfadEingabeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfadEingabeTextFieldActionPerformed(evt);
            }

			private void pfadEingabeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
				
				
			}
        });
        pfadEingabeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pfadEingabeTextFieldKeyPressed(evt);
            }

			private void pfadEingabeTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
				
				
			}
        });
        
        OK.setText("OK");
        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	OKActionPerformed(evt);
            }

        	protected void setLpSolvePath(String newLpSolvePath) {
        		
        		if (newLpSolvePath != null) {
        			paths.setzeString("LPSolve", "Path", newLpSolvePath);
        			paths.schreibeINIDatei(iniPath, true);
        		}
        		dispose();
        	}
        	
			private void OKActionPerformed(java.awt.event.ActionEvent evt) {
			
				String txt = pfadEingabeTextField.getText();
				setLpSolvePath(txt);
				dispose();
			}
        });
        
        Abbrechen.setText("Abbrechen");
        Abbrechen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbbrechenActionPerformed(evt);
            }

			private void AbbrechenActionPerformed(java.awt.event.ActionEvent evt) {
				//neue Funktion die zum beenden führt
				 dispose();			}
        });
        
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pfadEingabeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(OK, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(Abbrechen, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pfadEingabeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OK, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Abbrechen, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
		
	}

	
}
