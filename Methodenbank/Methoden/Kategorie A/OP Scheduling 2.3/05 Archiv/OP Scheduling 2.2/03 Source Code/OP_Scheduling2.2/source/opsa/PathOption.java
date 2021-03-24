package opsa;

import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.File;

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
	IniPaths classini = new IniPaths();
	Button buttonCancel = new Button();
	Button buttonOK = new Button();
	Label labelLPPath = new Label();
	TextField textFieldLPPath = new TextField();
	FmOpSa theFmOpSa;
	XYLayout xYLayout1 = new XYLayout();

	public PathOption(FmOpSa aFmOpSa) {
		super.setTitle("Solver Pfad Einstellungen:");
		theFmOpSa = aFmOpSa;
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

		if (lpPath == "" || !(f.exists())) {
			JOptionPane.showMessageDialog(this,
							"Eingabe falsch! Datei nicht gefunden.",
							"Info", JOptionPane.INFORMATION_MESSAGE);
	/*	}else {
			classini.writeIni(lpPath);
			this.dispose();
		*/}
	}

	private void jbInit() throws Exception {
		labelLPPath.setText("Pfad LP-Solve");
		textFieldLPPath.setText(classini.getLpSolvePath());
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
		this.getContentPane().add(labelLPPath, new XYConstraints(41, 15, -1, -1));
		this.getContentPane().add(buttonCancel,
				new XYConstraints(75, 85, -1, -1));
		this.getContentPane()
				.add(buttonOK, new XYConstraints(175, 85, 69, -1));
		this.getContentPane().add(textFieldLPPath,
				new XYConstraints(41, 40, 250, -1));
	}
}