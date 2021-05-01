package com.htwg.powerlp;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.htwg.powerlp.controller.Controller;
import com.htwg.powerlp.util.Configurator;
import com.htwg.powerlp.view.ContainerFrame;

/**
 * @author Bastian Schoettle
 * 
 * @version 1.0
 */
public class Application {


	//private static final String ENV_PARAM = "-env";

	/*
	private static String getEnv(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(ENV_PARAM)) {
				if (args.length > i && args[i + 1] != null) {
					return args[i + 1];
				}
			}
		}
		return null;
	}
*/
	/**
	 * Application entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Configurator conf = new Configurator(null);
		if (conf.isDarkThemeEnabled()) {
			UIManager.put("control", new Color(128, 128, 128));
			UIManager.put("info", new Color(128, 128, 128));
			UIManager.put("nimbusBase", new Color(18, 30, 49));
			UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
			UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
			UIManager.put("nimbusFocus", new Color(115, 164, 209));
			UIManager.put("nimbusGreen", new Color(176, 179, 50));
			UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
			UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
			UIManager.put("nimbusOrange", new Color(191, 98, 4));
			UIManager.put("nimbusRed", new Color(169, 46, 34));
			UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
			UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
			UIManager.put("text", new Color(230, 230, 230));
		}
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			try {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		final Controller controller = new Controller(conf);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					ContainerFrame mainWindow = new ContainerFrame(controller);
					mainWindow.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
