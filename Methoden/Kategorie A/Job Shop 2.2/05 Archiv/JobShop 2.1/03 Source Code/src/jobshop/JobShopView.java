/*
 * Bernd Haertenstein
 * 
 * Copyright (c) 2011-2015 University of Applied Science Konstanz. All Rights Reserved.
 * 
 * Version 	1.0	WS10/11
 * Author	Bernd Haertenstein
 * 
 * Version	1.1	WS14/15
 * Author	Dennis Klein, Sebastian Stephan
 * 
 */
package jobshop;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.help.HelpSet;
import javax.help.JHelp;
import javax.help.JHelpIndexNavigator;
import javax.help.JHelpNavigator;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;

/**
 * Initialize user interface. Loads it's all components like buttons, matrix, menu bar ....
 */
public class JobShopView extends FrameView {
	public JobCalculator cal = new JobCalculator();
    private JButton jButton1;
    private JButton jButton6;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JPanel jPanel1;
    private java.util.List<List<JFormattedTextField>> jFTFieldList;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private SpinnerNumberModel spinMod1;
    private JSpinner jSpinner1;
    private SpinnerNumberModel spinMod2;
    private JSpinner jSpinner2;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JProgressBar progressBar;
    private JLabel statusAnimationLabel;
    private JLabel statusMessageLabel;
    private JPanel statusPanel;
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private JDialog resultBox;
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private final JFileChooser fileChooser1 = new JFileChooser();
    private final JobShopFileFilter fileFilter = new JobShopFileFilter();

    public JobShopView(SingleFrameApplication app) {
    	super(app);
    	initComponents();		// method which loads all necessary components. Definition is below.

	/* Intention was a status bar initialization - message timeout, idle icon and busy animation, etc 
	 * does not work at the moment
	 * Necessary attributes can be found at
	 * package: jobshop.resources
	 * file: JobShopView.properties
	 */
	ResourceMap resourceMap = getResourceMap();
	int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
	messageTimer = new Timer(messageTimeout, new ActionListener() {
					    public void actionPerformed(ActionEvent e) {
					    	statusMessageLabel.setText("");
					    }
					});	
	
	messageTimer.setRepeats(false);
	int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
	for (int i = 0; i < busyIcons.length; i++) {
	    busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
	}
	busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
					    public void actionPerformed(ActionEvent e) {
						busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
						statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
					    }
					});
	
	idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
	statusAnimationLabel.setIcon(idleIcon);
	progressBar.setVisible(true);

	/* connecting action tasks to status bar via TaskMonitor */
	TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
	taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
	    public void propertyChange(java.beans.PropertyChangeEvent evt) {
	    	String propertyName = evt.getPropertyName();
		
				if ("started".equals(propertyName)) {
				    if (!busyIconTimer.isRunning()) {
						statusAnimationLabel.setIcon(busyIcons[0]);
						busyIconIndex = 0;
						busyIconTimer.start();
				    }
				    progressBar.setVisible(true);
				    progressBar.setIndeterminate(true);
				} else if ("done".equals(propertyName)) {
				    busyIconTimer.stop();
				    statusAnimationLabel.setIcon(idleIcon);
				    progressBar.setVisible(false);
				    progressBar.setValue(0);
				} else if ("message".equals(propertyName)) {
				    String text = (String) (evt.getNewValue());
				    statusMessageLabel.setText((text == null) ? "" : text);
				    messageTimer.restart();
				} else if ("progress".equals(propertyName)) {
				    int value = (Integer) (evt.getNewValue());
				    progressBar.setVisible(true);
				    progressBar.setIndeterminate(false);
				    progressBar.setValue(value);
				}
		    }
		});
    }	//Constructor closed

    @Action
    public void showAboutBox() {
		if (aboutBox == null) {
		    JFrame mainFrame = JobShopApp.getApplication().getMainFrame();
		    aboutBox = new JobShopAboutBox(mainFrame);
		    aboutBox.setLocationRelativeTo(mainFrame);		// place new window in front of input user interface
		}
		
		JobShopApp.getApplication().show(aboutBox);
    }

    @Action
    public void showResultBox(ResultList result) {
		JFrame mainFrame = JobShopApp.getApplication().getMainFrame();
		resultBox = new JobShopResultBox(mainFrame, result);
		resultBox.setLocationRelativeTo(mainFrame);
		JobShopApp.getApplication().show(resultBox);
    }
    
    /** This method is called from within the constructor to
     * initialize the user interface components.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        mainPanel = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        spinMod1 = new SpinnerNumberModel(2, 1, 16, 1);
        jSpinner1 = new JSpinner(spinMod1);
        spinMod2 = new SpinnerNumberModel(2, 2, 16, 1);
        jSpinner2 = new JSpinner(spinMod2);
        jScrollPane1 = new JScrollPane();
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jButton1 = new JButton();
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        JMenuItem exitMenuItem = new JMenuItem();
        JMenu helpMenu = new JMenu();
        JMenuItem aboutMenuItem = new JMenuItem();
        JMenuItem helpMenuItem = new JMenuItem();
        statusPanel = new JPanel();
        JSeparator statusPanelSeparator = new JSeparator();
        statusMessageLabel = new JLabel();
        statusAnimationLabel = new JLabel();
        progressBar = new JProgressBar();
        jButton6 = new JButton();
        
        /*
         * Attributes can be found at
		 * package: jobshop.resources
		 * file: JobShopView.properties
         */
        mainPanel.setName("mainPanel"); 

        ResourceMap resourceMap = Application.getInstance(JobShopApp.class).getContext().getResourceMap(JobShopView.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); 
        jLabel1.setName("jLabel1"); 

        jLabel2.setText(resourceMap.getString("jLabel2.text")); 
        jLabel2.setName("jLabel2"); 

        jSpinner1.setName("jSpinner1"); 
        jSpinner1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jSpinner2.setName("jSpinner2"); 
        jSpinner2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                jSpinner2StateChanged(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); 

        jPanel1.setName("jPanel1"); 
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

        jPanel2.setName("jPanel2"); 
        jPanel2.setLayout(new GridBagLayout());
        jPanel1.add(jPanel2);
        this.createMatrix();

        jScrollPane1.setViewportView(jPanel1);

        jButton1.setText(resourceMap.getString("jButton1.text")); 
        jButton1.setName("jButton1");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(jSpinner2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinner2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); 

        fileMenu.setText(resourceMap.getString("fileMenu.text"));
        fileMenu.setName("fileMenu"); 

        jMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); 
        jMenuItem1.setName("jMenuItem1"); 
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        
        fileMenu.add(jMenuItem1);

        jMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); 
        jMenuItem2.setName("jMenuItem2");
        jMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        
        fileMenu.add(jMenuItem2);
        
        ActionMap actionMap = Application.getInstance(JobShopApp.class).getContext().getActionMap(JobShopView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); 
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); 
        exitMenuItem.setName("exitMenuItem"); 
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); 
        helpMenu.setName("helpMenu"); 

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); 
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); 
        aboutMenuItem.setName("aboutMenuItem"); 
        helpMenu.add(aboutMenuItem);
        
        helpMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                hilfeMenue_actionPerformed(evt);
            }
        });
        helpMenuItem.setText(resourceMap.getString("helpMenuItem.text"));
        helpMenuItem.setName("helpMenuItem");
        helpMenu.add(helpMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); 

        statusPanelSeparator.setName("statusPanelSeparator"); 

        statusMessageLabel.setName("statusMessageLabel"); 

        statusAnimationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); 

        progressBar.setName("progressBar"); 

        GroupLayout statusPanelLayout = new GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(Alignment.LEADING)
            .addComponent(statusPanelSeparator, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jButton6.setText(resourceMap.getString("jButton6.text")); 
        jButton6.setName("jButton6"); 

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }

    /* Save input in a text-file. */
    private void save(File file) {
    	String ending = "";
    	try {
    		String lineSeperator = System.getProperty("line.separator");
    		
    		if(!file.getName().endsWith(".job")) {
			ending = ".job";
		    }
    		
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file + ending));
		    writer.write(jFTFieldList.size() + ";" + jFTFieldList.get(0).size() + lineSeperator); 	// number of machines and products, seperated by a semicolon
		    for (List<JFormattedTextField> jFTFields : jFTFieldList) {								// each machine
		    	for (JFormattedTextField jFormattedTextField : jFTFields) {							// print all processing time for each machine among themselves 
				    writer.write(jFormattedTextField.getValue() + lineSeperator);
				    System.out.println(jFormattedTextField.getValue());
				}
		    }
		    
		    writer.close();
		} catch (IOException e) {
		    System.out.println("Eingabe konnte nicht gespeichert werden!");
		    e.printStackTrace();
		}
    }

    /* Load a saved input-file. */
    private void load(File file) {
		try {
		    BufferedReader reader = new BufferedReader(new FileReader(file));
		    String[] strings = reader.readLine().split(";");
		    int anzahlM = Integer.parseInt(strings[0]);
		    int anzahlP = Integer.parseInt(strings[1]);
		    String s = "";
		    spinMod1.setValue(anzahlM);
		    spinMod2.setValue(anzahlP);
	
		    this.createMatrix();
	
		    for(int i=1, j=1; (s = reader.readLine()) != null; j++) {
				System.out.println(s);
				jFTFieldList.get(i-1).get(j-1).setValue(new Integer(s));
				/* Focus have to change to accept the programm's changes. */
				jFTFieldList.get(i-1).get(j-1).transferFocus();
				
				if(j == anzahlP) {
				    j = 0;
				    i++;
				}
		    }
		    reader.close();
		} catch (IOException e) {
			System.out.println(".job-Datei konnte nicht geladen werden!");
		    e.printStackTrace();
		}
    }

    // Create the text fields to get for each machine as well as products the processing time. */
    private void createMatrix() {
		int anzahlM = (Integer) spinMod1.getValue();
		int anzahlP = (Integer) spinMod2.getValue();
		jPanel2.removeAll();
		jFTFieldList = new ArrayList<List<JFormattedTextField>>();
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMinimumIntegerDigits(1);
		format.setMaximumIntegerDigits(3);
		JobShopNumberFormatter formater = new JobShopNumberFormatter(format);
		/* line */
		for (int i = 0; i < anzahlM + 1; i++) {
		    List<JFormattedTextField> innerList = new ArrayList<JFormattedTextField>();
		    /* column */
		    for (int j = 0; j < anzahlP + 1; j++) {
				String name = "";
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = i;
				c.gridy = j;
				c.fill = GridBagConstraints.BOTH;
				
				if (i == 0 || j == 0) {
				    if (i > 0 && j == 0) {
				    	name = "Maschine" + i;
				    } else if (i == 0 && j > 0) {
				    	name = "Produkt" + j;
				    }
				    JButton jButton = new JButton(name);
				    jButton.setName(name);
				    jButton.setFocusable(false);
				    jButton.validate();
				    jButton.setVisible(true);
				    jPanel2.add(jButton, c);
				} else {
				    JFormattedTextField jFTField = null;
				    jFTField = new JFormattedTextField(formater);
				    jFTField.setValue(0);
				    name = "jFTFField_M" + i + "_P" + j;
				    jFTField.setName(name);
				    jFTField.validate();
				    jFTField.setVisible(true);
				    innerList.add(jFTField);
				    jPanel2.add(jFTField, c);
				}
		    }
		    
		    if(innerList.size() > 0) {
		    	jFTFieldList.add(innerList);
		    }
		}
		
		/* Set bounds setBounds(int x,
         *            			 int y,
         *             			 int width,
                      			 int height) 
         */
		jPanel1.setBounds(-1, -1, -1, -1);
		jPanel2.setBounds(-1, -1, -1, -1);
    }
    
    /* Refresh input matrix when setting new number of machines. */
    private void jSpinner1StateChanged(ChangeEvent evt) {
    	this.createMatrix();
    }
    
    /* Refresh input matrix when setting new number of products. */
    private void jSpinner2StateChanged(ChangeEvent evt) {
    	this.createMatrix();
    }

    /* Adding ActionEvent to Berechnen-Button. */
    private void jButton1ActionPerformed(ActionEvent evt) {
    	JobCalculator calc = new JobCalculator();
    	ResultList result = calc.calculate(jFTFieldList);
    	this.showResultBox(result);			// shows the result
    }
    
	public void hilfeMenue_actionPerformed(ActionEvent e) {

		JHelp helpViewer = null;
		try {
			// Hauptfenster in der nächsten Zeile ersetzen durch aktuellen
			// Klassennamen
			ClassLoader cl = JobShopView.class.getClassLoader();
			URL url = HelpSet.findHelpSet(cl, "helpset.hs");
			helpViewer = new JHelp(new HelpSet(cl, url));

			Enumeration<?> eNavigators = helpViewer.getHelpNavigators();
			while (eNavigators.hasMoreElements()) {
				JHelpNavigator nav = (JHelpNavigator) eNavigators.nextElement();
				if (nav instanceof JHelpIndexNavigator) {
					helpViewer.removeHelpNavigator(nav);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("API Help Set not found");
		}

		JFrame frame = new JFrame();
		frame.setTitle("Hilfe zu Job Shop 2.0");
		frame.setSize(800, 600);
		frame.getContentPane().add(helpViewer);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

    /* Adding ActionEvent to save button */
    private void jMenuItem1ActionPerformed(ActionEvent evt) {
    	/* Focus have to change to accept the programm's changes. */
		jFTFieldList.get(1).get(0).transferFocus();
		jFTFieldList.get(0).get(1).transferFocus();
		jFTFieldList.get(0).get(0).transferFocus();
		fileChooser1.setFileFilter(fileFilter);
		int returnVal = fileChooser1.showSaveDialog(JobShopApp.getApplication().getMainFrame());
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser1.getSelectedFile();
            
            this.save(file);
            System.out.println("Speichere: " + file.getName());
        } else {
            System.out.println("Speicher-Befehl von User zurueckgezogen.");
        }	
    }

    /* Adding ActionEvent to load button. */
    private void jMenuItem2ActionPerformed(ActionEvent evt) {
    	int returnVal = fileChooser1.showOpenDialog(JobShopApp.getApplication().getMainFrame());
    	fileChooser1.setFileFilter(fileFilter);
	
		if (returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = fileChooser1.getSelectedFile();
		    this.load(file);
            System.out.println("Opening: " + file.getName());
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }    
}