package trOptimizer;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class AutoSave extends TimerTask {

	Timer timer;
	TimerTask autosave;
	MainFrame MainFrame;
	TransopController controller;
	Speichern sp;
	
	public AutoSave() {

	}

	
	public Timer getTimer() {
		return timer;
	}


	public void setTimer(Timer timer) {
		this.timer = timer;
	}


	public Speichern getSp() {
		return sp;
	}


	public void setSp(Speichern sp) {
		this.sp = sp;
	}


	public void run() {
		
		//wenn die Option Autosave ausgeschaltet wurde
		if (!GlobaleVariable.autosaveSTATUS) {
			timer.cancel();
			timer.purge();
		} 
		
		//wenn die Option Autosave eingeschaltet wurde
		else if (GlobaleVariable.autosaveSTATUS) {
			if (GlobaleVariable.speichernach == 1) {

				/*
				 * Die Datei, die gespeichert wird, ist die Datei, die gerade
				 * unter "speichern unter" gespeichert wurde.
				 */

				JFrame speicherndialog = new JFrame();

				String tmp = GlobaleVariable.selFile.getAbsolutePath();
				GlobaleVariable.selFile = new File(tmp);
				sp.speichernObjekt(GlobaleVariable.selFile);

				JOptionPane.showMessageDialog(speicherndialog,
						"erfolgreich gespeichert! Stamp: " + new Date());

			} else if (GlobaleVariable.speichernach == 2) {

				/*
				 * Die Datei, die gespeichert wird, ist die Datei, die gerade
				 * geoeffnet wurde.
				 */

				JFrame speicherndialog = new JFrame();

				String tmp = GlobaleVariable.oeffneFile.getAbsolutePath();
				GlobaleVariable.oeffneFile = new File(tmp);
				sp.speichernObjekt(GlobaleVariable.oeffneFile);

				JOptionPane.showMessageDialog(speicherndialog,
						"erfolgreich gespeichert! Timestamp: " + new Date());

			} else {

				/*
				 * Die Datei, die gespeichert wird, ist noch nicht festgelegt.
				 * Hier wird ein Meldungsfenster angezeigt
				 */

				JFrame speicherndialog = new JFrame();
				JOptionPane
						.showMessageDialog(speicherndialog,
								"Speichern Sie erst die Datei mit der 'Speichern unter' Funktion");
				GlobaleVariable.autosaveSTATUS = false;

			}
		}

	}
}