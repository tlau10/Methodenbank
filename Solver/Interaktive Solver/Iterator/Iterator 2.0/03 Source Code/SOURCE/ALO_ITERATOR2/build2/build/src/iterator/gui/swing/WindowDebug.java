package iterator.gui.swing;

import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class WindowDebug {

	private JFrame frameDebugConsole;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowDebug window = new WindowDebug();
					window.frameDebugConsole.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowDebug() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameDebugConsole = new JFrame();
		frameDebugConsole.setBounds(100, 100, 750, 400);
		frameDebugConsole.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameDebugConsole.getContentPane().setLayout(null);
		
		JTextArea txConsole = new JTextArea();
		txConsole.setEditable(false);
		txConsole.setBackground(SystemColor.window);
		txConsole.setLineWrap(true);
		
	
		txConsole.setBounds(10, 11, 714, 340);
		frameDebugConsole.getContentPane().add(txConsole);
	}
}
