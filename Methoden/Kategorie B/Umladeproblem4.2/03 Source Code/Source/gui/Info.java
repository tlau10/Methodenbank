package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;

public class Info {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public  void Info_method() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Info window = new Info();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Info() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 342);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JTextPane txtpnauthor = new JTextPane();
		txtpnauthor.setContentType("text/html");
		txtpnauthor.setBackground(new Color(240, 240, 240));
		txtpnauthor.setText("<html>\r\n<body BGCOLOR=\"#f0f0f0\">\r\n\r\n<h1>Umladenproblem 4.2</h1><p>\r\n\r\n<b>Version 4.2</b><p>\r\n\r\n<b>Autoren</b><br>\r\nSchnopp, Joggerst<p>\r\n<b>Kompatibilit\u00E4t</b><br>\r\nWindows 7, Windows 8, Windows 10<p>\r\n<b>Datum 31.08.16</b><p>\r\n<b>Programmiersprache</b><br>\r\nJava\r\n</body>\r\n</html>");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(txtpnauthor, GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtpnauthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
		
		
	}
}
