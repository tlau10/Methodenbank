package opsa;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

/**
 * Class PathOption
 *
 * Gibt das Frame für die Pfad Einstellungen aus und komunniziert
 * zum lesen und speichern des Pfades mit der Class Ini.
 *
 * @author Stefan Brecht
 * @version 0.2
 */

public class PathOption extends JFrame {
	/**
	 * Serial ID für Serialisierung
	 */
	private static final long serialVersionUID = -3689730427433425263L;
	Ini classini = new Ini();
	Button buttonCancel = new Button();
	Button buttonOK = new Button();
	Button buttonFileChooser = new Button();
	Button buttonFileChooser2 = new Button();
	Label labelLPPath = new Label();
	Label labelLPPath2 = new Label();
	TextField textFieldLPPath = new TextField();
	TextField textFieldLPPath2 = new TextField();
	FmOpSa theFmOpSa;
	XYLayout xYLayout1 = new XYLayout();
	
	CheckboxGroup grpRadio = new CheckboxGroup();
    
    //if you create checkboxes and add to group,they become radio buttons
    Checkbox grp_1 = new Checkbox("", grpRadio, true);
    Checkbox grp_2 = new Checkbox("", grpRadio, true);

	public PathOption(FmOpSa aFmOpSa) {
		super.setTitle("Solver Pfad Einstellungen:");
		theFmOpSa = aFmOpSa;
		buttonFileChooser2.setEnabled(false);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void buttonCancel_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void buttonOK_actionPerformed(ActionEvent e) {
		String lpPath = textFieldLPPath.getText();
		File f = new File(lpPath);
		String lpPath2 = "";
		File f2 = new File(lpPath2);
		String checkedPath = "";
		
		if (!textFieldLPPath2.getText().equals("")) {
			lpPath2 = textFieldLPPath2.getText();
		}
		if (!textFieldLPPath.getText().equals("")) {
			lpPath = textFieldLPPath.getText();
		}
		
		if (grp_1.getState()) {
			checkedPath = lpPath;
		} else {
			checkedPath = lpPath2;
		}
		
//		if (lpPath == "" || !(f.exists() || !(f2.exists()))) {
//			JOptionPane.showMessageDialog(this,
//							"Eingabe falsch! Datei nicht gefunden.",
//							"Info", JOptionPane.INFORMATION_MESSAGE);
//		}else {
			classini.writeIni(lpPath, lpPath2, checkedPath);
			this.dispose();
//		}
		


	}

	private void jbInit() throws Exception {
		labelLPPath.setText("Pfad LP-Solve:");
		labelLPPath2.setEnabled(false);
		labelLPPath2.setText("Pfad 'Solver #2':");
		textFieldLPPath.setText(classini.getLPsolvePath());
		//textFieldLPPath2.setText(classini.getLPsolvePath2());
		if (classini.getCheckedPath().equals(textFieldLPPath.getText())) {
			grp_1.setState(true);
		}
//		} else {
//			grp_2.setState(true);
//		}
		grp_2.setEnabled(false);
		this.getContentPane().setLayout(xYLayout1);
		this.getContentPane().setBackground(new Color(153, 204, 255));
		this.setResizable(true);
		buttonCancel.setLabel("Abbrechen");
		buttonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonCancel_actionPerformed(e);
			}
		});
		buttonOK.setLabel("OK");
		buttonOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonOK_actionPerformed(e);
			}
		});
		buttonFileChooser.setLabel("...");
		buttonFileChooser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
		        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        int returnVal = fc.showOpenDialog(null);
		        File f;
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		            f = fc.getSelectedFile();
		            textFieldLPPath.setText(f.getPath());
		        }
			}
		});
		buttonFileChooser2.setLabel("...");
		buttonFileChooser2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
		        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        int returnVal = fc.showOpenDialog(null);
		        File f;
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		            f = fc.getSelectedFile();
		            textFieldLPPath2.setText(f.getPath());
		        }
			}
		});
		
		this.getContentPane().add(buttonCancel, new XYConstraints(50, 80, -1, -1));
		this.getContentPane().add(buttonOK, new XYConstraints(450, 80, 69, -1));
		
		this.getContentPane().add(labelLPPath, new XYConstraints(20, 5, -1, -1));
		this.getContentPane().add(labelLPPath2, new XYConstraints(20, 30, -1, -1));		
		this.getContentPane().add(textFieldLPPath, new XYConstraints(120, 5, 420, -1));
		this.getContentPane().add(textFieldLPPath2, new XYConstraints(120, 30, 420, -1));
		this.getContentPane().add(buttonFileChooser, new XYConstraints(550,5, 20, -1));
		this.getContentPane().add(buttonFileChooser2, new XYConstraints(550,30, 20, -1));
		this.getContentPane().add(grp_1, new XYConstraints(5,5, 20, -1));
		this.getContentPane().add(grp_2, new XYConstraints(5,30, 20, -1));
		
		
		
	}
}