package de.fh_konstanz.simubus.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import sun.awt.image.ToolkitImage;
import sun.awt.image.URLImageSource;
import de.fh_konstanz.simubus.model.Linie;
import de.fh_konstanz.simubus.model.Strassennetz;
import de.fh_konstanz.simubus.model.simulation.BusSimulationModel;
import de.fh_konstanz.simubus.util.DateUtil;
import de.fh_konstanz.simubus.util.ImageUtil;
import de.fh_konstanz.simubus.view.BuslinieDetail;
import de.fh_konstanz.simubus.view.ReportFrame;
import de.fh_konstanz.simubus.view.SimuControlPanel;
import de.fh_konstanz.simubus.view.SimuPanel;
import de.fh_konstanz.simubus.view.View;
import desmoj.core.simulator.ExpProgressBar;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.SimTime;

/**
 * Die Klasse <code>SimuButtonListener</code> reagiert auf Ereignisse, wenn im
 * <code>SimuControlPanel</code> ein Button gedrueckt wird.
 * 
 * @author Robert Audersetz
 * @version 1.0 (05.05.2005)
 */
public class SimuButtonListener extends MouseAdapter {
	/** Einstellungen zur Simulation */
	private SimuControlPanel simu;
	private JButton stopButton = null;

	@Override
	public void mouseClicked(MouseEvent evt) {

		JButton actual = (JButton) evt.getSource();
		String actionCmd = actual.getActionCommand();

		if (actionCmd.equals("addBuslinie")) {
			if (Strassennetz.getInstance().getArrayBuslinie().size() + 1 ==20) {
				JOptionPane.showMessageDialog(View.getInstance(), "Sie haben nun 20 Buslinien hinzugefügt. Mehr als 20 Linien kann sich stark auf die Performance Ihres Computers auswirken.",
						"Performance Warnung", JOptionPane.WARNING_MESSAGE);
			}
			if (Strassennetz.getInstance().getArrayBuslinie().size() + 1 > 20) {
				JOptionPane.showMessageDialog(View.getInstance(), "Sie haben mehr als 20 Buslinien hinzugefügt. Dies kann sich stark auf die Performance Ihres Rechners auswirken. Es wird empfohlen, die Anzahl der Linien geringer zu halten!",
						"Performance Warnung", JOptionPane.WARNING_MESSAGE);
			}
			simu = SimuControlPanel.getInstance();

			Linie linie = new Linie(
					"Linie " + String.valueOf(Strassennetz.getInstance().getArrayBuslinie().size() + 1));

			Strassennetz netz = Strassennetz.getInstance();
			netz.addLinie(linie);

			Random rand = new Random();

			Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 255);
			linie.setLinienfarbe(color);

			simu.updateBuslinienList();
			simu.updateTeilstreckenList();
		} else if (actionCmd.equals("removeBuslinie")) {

			simu = SimuControlPanel.getInstance();
			Linie bus = simu.getSelectedBuslinie();

			if (bus != null) {

				SimuPanel.getInstance().removeLinien();
				Strassennetz.getInstance().removeLinie(bus);
				simu.updateBuslinienList();
				simu.updateTeilstrecke();
				simu.updateTeilstreckenList();
			} else {
				JOptionPane.showMessageDialog(View.getInstance(), "Bitte wählen Sie zuerst eine Linie aus!",
						"Keine Linie ausgewählt", JOptionPane.ERROR_MESSAGE);
			}
			simu.updateTeilstreckenList();
		} else if (actionCmd.equals("editBuslinie")) {

			Linie linie = SimuControlPanel.getInstance().getSelectedBuslinie();

			if (linie != null) {
				BuslinieDetail f = new BuslinieDetail(linie);
				f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
				f.setSize(new Dimension(600, 600));
				f.setBounds(300, 100, 600, 600);
				f.setTitle("Details von Buslinie: " + linie.getId());
				f.setResizable(false);
				f.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(View.getInstance(), "Bitte wählen Sie zuerst eine Linie aus!",
						"Keine Linie ausgewählt", JOptionPane.ERROR_MESSAGE);
			}

		} else if (actionCmd.equals("startSimu")) {

			Strassennetz strassennetz = Strassennetz.getInstance();

			// Startzeit Simulation
			DateUtil du = DateUtil.getInstance();
			du.setStartTime();

			if (!strassennetz.getArrayBuslinie().isEmpty() && strassennetz.getAlleHaltestellen().size() >= 2) {
				Thread t = new Thread(new Runnable() {

					public void run() {

						File aFile = new File("Reports");
						String p = aFile.getAbsolutePath();

						if (aFile.exists() == false) {
							aFile.mkdir();
						}

						SimuControlPanel panel = SimuControlPanel.getInstance();
						final Experiment exp = new Experiment("Bus Simulations Experiment", p,
								panel.getSelectedStartpunkt(), 2, "desmoj.core.report.HTMLReportOutput",
								"desmoj.core.report.HTMLTraceOutput", "desmoj.core.report.HTMLErrorOutput",
								"desmoj.core.report.HTMLDebugOutput");
						// 2.Parameter: Startzeit, 3.Parameter: Einheit der
						// SimTime
						// -> 2 = min
						BusSimulationModel model = new BusSimulationModel(null, "BusGeschichten", true, true, exp);
						// ReportFileOut rfo = new ReportFileOut( 1, "c:/temp"
						// );

						model.connectToExperiment(exp);

						exp.setShowProgressBar(false);

						exp.tracePeriod(new SimTime(0.0), new SimTime(360.0));
						exp.debugPeriod(new SimTime(0.0), new SimTime(360.0));

						// Wartezeit in ms zwischen EventScheduling -> 1s
						exp.setDelayInMillis(panel.getSimulationsgeschwindigkeit());

						// Ausgabeformat fuer Realtime in
						exp.setOutputTimeFormat("hh:mm:ss");

						exp.stop(new SimTime(panel.getTimeDifference()));

						final ExpProgressBar progress = new ExpProgressBar(exp);
						progress.setIconImage(
								new ToolkitImage(new URLImageSource(ImageUtil.getImageUrl("haltestelle.png"))));
						progress.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						progress.setVisible(true);

						// Stop Funktion einbauen
						stopButton = new JButton("Simulation stoppen");
						// stopButton.setSize(new Dimension(100, 20));
						stopButton.setToolTipText("Simulation stoppen");
						stopButton.setName("Simulation stoppen");
						progress.setLayout(new GridLayout(2, 0));
						progress.add(stopButton);
						progress.pack();
						progress.addWindowListener(new WindowAdapter() {

							@Override
							public void windowClosing(WindowEvent e) {
								if (JOptionPane.showOptionDialog(progress, "Wollen Sie Sie die Simulation abbrechen?",
										"Abbrechen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
										null, null) == JOptionPane.YES_OPTION) {
									exp.finish();
									super.windowClosing(e);
								}

							}
						});

						// stopButton ActionListener
						stopButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (JOptionPane.showOptionDialog(progress, "Wollen Sie Sie die Simulation abbrechen?",
										"Abbrechen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
										null, null) == JOptionPane.YES_OPTION) {
									exp.finish();
								}
							}
						});

						exp.start();
						exp.report();
						model.closeLog();
						exp.finish();
						progress.setVisible(false);
						progress.dispose();

						// Ende Dauer Simulation
						DateUtil du = DateUtil.getInstance();
						du.setEndTime();
						JOptionPane.showMessageDialog(View.getInstance(),
								"Benötigte Zeit für die Simulation: " + du.getTimeDiffAsString() + "\n",
								"Dauer der Simulation", JOptionPane.INFORMATION_MESSAGE);
					}
				});
				t.start();
			} else {
				JOptionPane.showMessageDialog(View.getInstance(),
						"Für die Simulation fehlt entweder eine Linie oder Haltestellen", "Bus Simulation",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (actionCmd.equals("reportsSimu")) {

			File file = new File("Reports");
			if (file.exists() == false) {
				JOptionPane.showMessageDialog(View.getInstance(), "Keine Reports vorhanden", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			} else {
				new ReportFrame();
			}

		} else if (actionCmd.equals("deleteTeilstrecke")) {
			SimuPanel.getInstance().deleteSelectedLinienEdge();
			SimuControlPanel.getInstance().updateTeilstreckenList();
		}

	}

}