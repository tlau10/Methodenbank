package standortplanung;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * <p>
 * Überschrift: Standortplanung
 * </p>
 * <p>
 * Beschreibung:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Organisation:
 * </p>
 * 
 * @author Manuel Thoma, Markus Klemens
 * @version 3.0
 */

public class FrameInfo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public void createFrameInfo() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameInfo frame = new FrameInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameInfo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 299, 176);
		setTitle("Info");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
		});
		btnOk.setBounds(95, 100, 91, 23);
		contentPane.add(btnOk);
		
		JLabel lblStandortplanunV = new JLabel("Standortplanung v3.0");
		lblStandortplanunV.setBounds(90, 25, 160, 14);
		contentPane.add(lblStandortplanunV);
		
		JLabel lblNewLabel = new JLabel("\u00A9 Copyright 2013");
		lblNewLabel.setBounds(95, 50, 124, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("     by Markus Klemens, Manuel Thoma");
		lblNewLabel_1.setBounds(33, 75, 248, 14);
		setLocationRelativeTo(null);
		contentPane.add(lblNewLabel_1);
	}
}
