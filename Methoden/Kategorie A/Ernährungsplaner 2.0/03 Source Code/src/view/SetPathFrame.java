package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.IniPaths;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: SetPathFrame
 * </p>
 * <p>
 * Description: Zeigt das Fenster an, um die Solver-Pfade zu ändern
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */
public class SetPathFrame extends JPanel {

	private static final long serialVersionUID = -3969428104348764098L;
	private JLabel jLabelHeading = new JLabel();
	private JLabel jLabelLpSolvePath = new JLabel();
	private JLabel jLabelCplexPath = new JLabel();
	private JLabel jLabelTempPath = new JLabel();

	private JTextField jTextFieldLpSolvePath = new JTextField();
	private JTextField jTextFielCplexPath = new JTextField();;
	private JTextField jTextFieldTempPath = new JTextField();;

	private MainFrame parentFrame_;
	private JButton jButtonSave = new JButton();
	private JButton jButtonUndo = new JButton();
	private JButton jButtonClose = new JButton();

	public SetPathFrame(MainFrame parentFrame) {
		try {
			parentFrame_ = parentFrame;
			createFrame();
		} catch (Exception ex) {
			ErrorMessages.throwErrorMessage("Fehler beim Anzeigen des Fensters \n Sollte das Problem weiterhin auftreten, wenden Sie sich an den Systemadministrator");
		}
	}

	private void createFrame() throws Exception {

		jLabelHeading.setFont(new java.awt.Font("SansSerif", 1, 14));
		jLabelHeading.setBorder(BorderFactory.createRaisedBevelBorder());
		jLabelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelHeading.setText("Bitte geben Sie die gew\u00FCnschten Pfade ein");

		jLabelLpSolvePath.setBorder(BorderFactory.createEtchedBorder());
		jLabelLpSolvePath.setMaximumSize(new Dimension(34, 15));
		jLabelLpSolvePath.setToolTipText("");
		jLabelLpSolvePath.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelLpSolvePath.setText("LP_Solve Pfad");

		jLabelCplexPath.setToolTipText("");
		jLabelCplexPath.setText("Cplex Pfad");
		jLabelCplexPath.setMaximumSize(new Dimension(34, 15));
		jLabelCplexPath.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelCplexPath.setBorder(BorderFactory.createEtchedBorder());

		jLabelTempPath.setToolTipText("");
		jLabelTempPath.setText("Temp Pfad");
		jLabelTempPath.setMaximumSize(new Dimension(34, 15));
		jLabelTempPath.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTempPath.setBorder(BorderFactory.createEtchedBorder());

		jTextFieldLpSolvePath.setText(IniPaths.getLpSolvePath());
		jTextFielCplexPath.setText(IniPaths.getCplexPath());
		jTextFieldTempPath.setText(IniPaths.getTempPath());

		jButtonSave.setText("Übernehmen");
		jButtonSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonSave_actionPerformed(e);
			}
		});

		jButtonUndo.setText("Verwerfen");
		jButtonUndo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonUndo_actionPerformed(e);
			}
		});

		jButtonClose.setText("Schliessen");
		jButtonClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		createLayout();
	}

	private void createLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(jLabelCplexPath, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(jTextFielCplexPath))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(jLabelLpSolvePath, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(jTextFieldLpSolvePath))
						.addComponent(jLabelHeading, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(jButtonUndo)
								.addComponent(jLabelTempPath, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(jButtonSave)
									.addGap(18)
									.addComponent(jButtonClose))
								.addComponent(jTextFieldTempPath))))
					.addContainerGap(138, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jLabelHeading, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabelLpSolvePath, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldLpSolvePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabelCplexPath, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFielCplexPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabelTempPath, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextFieldTempPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jButtonUndo)
						.addComponent(jButtonSave)
						.addComponent(jButtonClose)))
		);
		setLayout(groupLayout);
	}

	private void clearEntries() {
		jTextFieldLpSolvePath.setText(IniPaths.getLpSolvePath());
		jTextFielCplexPath.setText(IniPaths.getCplexPath());
		jTextFieldTempPath.setText(IniPaths.getTempPath());
	}

	private void savePaths() {
		IniPaths.setCplexePath(jTextFielCplexPath.getText());
		IniPaths.setLpSolvePath(jTextFieldLpSolvePath.getText());
		IniPaths.setTempPath(jTextFieldTempPath.getText());
	}
	
	private void jButtonSave_actionPerformed(ActionEvent e) {
		savePaths();
	}

	private void jButtonUndo_actionPerformed(ActionEvent e) {
		clearEntries();
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		jTextFieldLpSolvePath.setText(IniPaths.getLpSolvePath());
		jTextFielCplexPath.setText(IniPaths.getCplexPath());
		jTextFieldTempPath.setText(IniPaths.getTempPath());
		parentFrame_.closechild(6, this, 0, null);
	}
}
