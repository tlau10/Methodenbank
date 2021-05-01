package opsa;

import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

/**
 * Title: Operationssleplanung Description: Copyright: Copyright (c) 2002
 * Company: FHKN
 *
 * @author Arne Bittermann, Liwei Lu
 * @version 1.0
 *
 * erweitert Juni 2003
 * @author Nina Bruch, Katharina Dammeier
 * @version 2.0
 */

public class DlgConfig extends JFrame {
	/**
	 * Serial ID für Serialisierung
	 */
	private static final long serialVersionUID = -3689730427433425263L;
	Button buttonCancel = new Button();
	Button buttonOK = new Button();
	Label label1 = new Label();
	Label label2 = new Label();
	Label label3 = new Label();
	Label label4 = new Label();
	TextField textFieldAnzOp = new TextField();
	TextField textFieldAnzPer = new TextField();
	TextField textFieldAnzSaal = new TextField();
	FmOpSa theFmOpSa;
	XYLayout xYLayout1 = new XYLayout();

	public DlgConfig(FmOpSa aFmOpSa) {
		super.setTitle("Voreinstellungen:");
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
		try {
			int anzOp = Integer.parseInt(textFieldAnzOp.getText());
			int anzPer = Integer.parseInt(textFieldAnzPer.getText());
			int anzSaal = Integer.parseInt(textFieldAnzSaal.getText());

			if (anzSaal != 2) {
				JOptionPane
						.showMessageDialog(
								this,
								"Eingabe falsch! Im Feld Saalanzahl ist nur Ziffer 2 zulssig! ",
								"Info", JOptionPane.INFORMATION_MESSAGE);
			} else {
				theFmOpSa.setOperationsAnz(anzOp);
				theFmOpSa.setPeriodeAnz(anzPer);
				theFmOpSa.setSaalAnz(anzSaal);
				theFmOpSa.setDefaultTableOperation(anzOp);
				this.dispose();
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Eingabe falsch! Nur Ziffer zulssig! ", "Info",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void jbInit() throws Exception {
		label1.setText("Anzahl der OP's");
		this.getContentPane().setLayout(xYLayout1);
		label2.setText("Anzahl der Perioden");
		label3.setText("Saalanzahl (2 setzen)");
		label4.setText("(siehe Doku)");
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
		this.getContentPane().add(label3, new XYConstraints(41, 109, -1, -1));
		this.getContentPane().add(label4, new XYConstraints(252, 109, -1, -1));
		this.getContentPane().add(label2, new XYConstraints(41, 70, -1, -1));
		this.getContentPane().add(label1, new XYConstraints(41, 30, -1, -1));
		this.getContentPane().add(buttonCancel,
				new XYConstraints(99, 163, -1, -1));
		this.getContentPane()
				.add(buttonOK, new XYConstraints(204, 163, 69, -1));
		this.getContentPane().add(textFieldAnzSaal,
				new XYConstraints(190, 110, 56, -1));
		this.getContentPane().add(textFieldAnzOp,
				new XYConstraints(190, 29, 56, -1));
		this.getContentPane().add(textFieldAnzPer,
				new XYConstraints(190, 70, 56, -1));
	}
}