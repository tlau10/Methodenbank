/*************************************************************************
 * 
 * CONFIDENTIAL
 * __________________
 * 
 *  [2016] Bastian Schoettle & Tim Schoettle
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Bastian Schoettle & Tim Schoettle and his suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Bastian Schoettle & Tim Schoettle
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Bastian Schoettle & Tim Schoettle.
 *
 */
package com.htwg.powerlp.view;

/**
 * @author schobast
 *
 */
import java.awt.*;
import javax.swing.*;

import com.htwg.powerlp.util.Configurator;

public class SplashScreen extends JWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4024998562846058610L;
	private int duration;
	private Configurator conf;

	public SplashScreen(Configurator conf, int d) {
		duration = d;
		this.conf = conf;
	}

	// A simple little method to show a title screen in the center
	// of the screen for the amount of time given in the constructor
	public void showSplash() {
		JPanel content = (JPanel) getContentPane();
		JDesktopPane desk = new JDesktopPane();
		content.add(desk, BorderLayout.CENTER);
		// Set the window's bounds, centering the window
		int width = 450;
		int height = 280;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

		// Build the splash screen
		JLabel label = new JLabel(conf.getApplicationIcon());
		JLabel text = new JLabel("PowerLP");
		text.setBounds(new Rectangle(new Point((width/2) + 50, height/2), text.getPreferredSize()));
		label.setBounds(new Rectangle(new Point(10, 10), label.getPreferredSize()));
		desk.add(label);
		desk.add(text);
		JLabel copyrt = new JLabel("Copyright 2017, HTWG Konstanz",
				JLabel.CENTER);
		//copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		//content.add(label, BorderLayout.CENTER);
		content.add(copyrt, BorderLayout.SOUTH);
		content.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

		// Display it
		setVisible(true);

		// Wait a little while, maybe while loading resources
		try {
			Thread.sleep(duration);
		} catch (Exception e) {
		}

		setVisible(false);
	}

	public void showSplashAndExit() {
		showSplash();
		System.exit(0);
	}

	
}