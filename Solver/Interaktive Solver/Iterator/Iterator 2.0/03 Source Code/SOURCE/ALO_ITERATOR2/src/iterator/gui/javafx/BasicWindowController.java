package iterator.gui.javafx;

import iterator.Fascade;
import iterator.MessageHandler;
import iterator.tableau.TableauCellPosition;
import iterator.tableau.TableauDTO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;

import org.apache.log4j.Logger;

public class BasicWindowController implements Initializable {

	private static Logger logger = Logger.getLogger(BasicWindowController.class);

	private static HashSet<MyStage> allNewStages = new HashSet<MyStage>();
	private static MyStage lastIterateStage;
	private static Stage focusedStage;
	
	private static HashSet<TableauController> allTableauControllers = new HashSet<TableauController>();
	private static TableauController focusedTableauController;
	private static TableauController tempNextTableauController;
	
	private Stage primaryStage;
	private int keyCounter = 0;
	private TableauDTO focusedTableau;
	private String newTableauName = null;
	static boolean answer;
	
	private DebugController debugController;

	@FXML
	private Menu menuFile;
	@FXML
	private Menu menuEdit;
	@FXML
	private Menu menuSetting;
	@FXML
	private Menu menuHelp;
	
	@FXML
	private MenuItem itemNew;
	@FXML
	private MenuItem itemSave;
	@FXML
	private MenuItem itemOpen;
	@FXML
	private MenuItem itemExit;

	@FXML
	private MenuItem itemIteration;
	@FXML
	private MenuItem itemPivot;
	@FXML
	private MenuItem itemOptimize;

	@FXML
	private MenuItem itemAbout;
	@FXML
	private MenuItem itemDocumentation;
	@FXML
	private MenuItem itemDebug;

	@FXML
	private Button btnNew;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnOpen;
	
	@FXML
	private Button btnPivot;
	@FXML
	private Button btnIterate;
	@FXML
	private Button btnOptimize;

	
	
	@FXML
	private MenuItem itemLanguage;
	@FXML
	private RadioMenuItem radioGerman;
	@FXML
	private RadioMenuItem radioEnglish;
	@FXML
	private ImageView imageView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Buttonstyle
		Image imageSave = new Image(getClass().getResourceAsStream("img_save.png"));
		Image imageOpen = new Image(getClass().getResourceAsStream("img_open.png"));
		Image imageNew = new Image(getClass().getResourceAsStream("img_new.png"));
		
		
		
		btnNew.setGraphic(new ImageView(imageNew));
		btnOpen.setGraphic(new ImageView(imageOpen));
		btnSave.setGraphic(new ImageView(imageSave));
		
	
		
		
		adaptLanguage();
		radioGerman.setSelected(true);
		
		
		btnNew.setTooltip(
				new Tooltip(MessageHandler.getInstance().getMessage("gui.item.new"))
				);

	btnOpen.setTooltip(
			new Tooltip(MessageHandler.getInstance().getMessage("gui.item.open"))
			);

	
	btnSave.setTooltip(
			
				new Tooltip(MessageHandler.getInstance().getMessage("gui.item.save"))
			
			
	);
		// *********************************************
		// 					FILE
		// *********************************************

		// ----------------------------------------
		// EVENT -> MenuItem NEW
		itemNew.setOnAction(new EventHandler<ActionEvent>() {
			// Neues Tableau anlegen
			@Override
			public void handle(ActionEvent event) {

				final MyStage newStage = new MyStage(StageStyle.DECORATED,keyCounter);

				allNewStages.add(newStage);

				Parent newTableau;
				TableauController tabControl;
				
				try {
					// Connection to fxml
					FXMLLoader loader = new FXMLLoader(getClass().getResource("TableauPage.fxml"));
					newTableau = (Parent) loader.load();
					tabControl = (TableauController) loader.getController();
					
					allTableauControllers.add(tabControl);
					
					setFocusHandler(newStage, tabControl);

				} catch (IOException e) {
					e.printStackTrace();
					logger.info("FXML-File TableauPage.fxml wurde nicht gefunden");
					return;
				}
				
				Scene newScene = new Scene(newTableau);

				newStage.setScene(newScene);
				
				setStageOwner(newStage);

				// ++++++++++++++++++++++++++++++++
				// 		generates Tables
				// ++++++++++++++++++++++++++++++++
				int anzRows = 2;
				int anzColumns = 2;

				TableauDTO tabDTO = tabControl.fascade.createTableau(anzColumns, anzRows);

				tabControl.buildingNewTableau(tabDTO);
				// ++++++++++++++++++++++++++++++++

				
				setNewTableauName("Tableau: " + newStage.getStageId());
				
				// Title of Tableau-Stage => dynamisch beziehen
				newStage.setTitle(getNewTableauName());
				newStage.setWidth(600);
				newStage.setHeight(350);

				// Specify Owner of this stage -> defines Parent
				newStage.initOwner(primaryStage);

				newStage.show();

				
				newStage.setSceneHandler();
				tabControl.setId(keyCounter);
				keyCounter++;
				
			}
		});

		// ----------------------------------------
		// EVENT -> MenuItem SAVE
		itemSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					FileChooser fileChooser = new FileChooser();
	
					// Set extension filter
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(".XML", "*");
					fileChooser.getExtensionFilters().add(extFilter);
					
					
					String currentPath = System.getProperty("user.dir");
					
					File initFile = null;
					do{
						if(!currentPath.contains("\\") || currentPath.length() < 3){
							break;
						}
						currentPath = pathBack(currentPath);
						initFile = new File(currentPath + "\\Iterator 2.0\\01 Programm\\DATEN");
					} while(!initFile.exists());
					
					
					logger.debug("  >> Default Directory: " + initFile.getPath() + ", Exists: " + initFile.exists());
					if(initFile.exists()){
						fileChooser.setInitialDirectory(initFile);
					}
					
					// Show save file dialog
					File file = fileChooser.showSaveDialog(primaryStage);
	
					if (!file.getName().contains(".")) {
						file = new File(file.getAbsolutePath() + ".XML");
					}
	
					String path = (file != null)? file.getPath(): "";
	
					focusedTableauController.fascade.save(focusedTableauController.getCurrentTableau().getId(), path);
				} catch(Exception e){
					logger.debug(e);
				}
			}
		});

		// ----------------------------------------
		// EVENT -> MenuItem OPEN
		itemOpen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try{
					FileChooser fileChooser = new FileChooser();
	
					// Set extension filter
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.XML", "*.txt", "*.ORI", "*");
					fileChooser.getExtensionFilters().add(extFilter);
					
					String currentPath = System.getProperty("user.dir");
					
					File initFile = null;
					do{
						if(!currentPath.contains("\\") || currentPath.length() < 3){
							break;
						}
						currentPath = pathBack(currentPath);
						initFile = new File(currentPath + "\\Iterator 2.0\\01 Programm\\DATEN");
					} while(!initFile.exists());
					
					
					logger.debug("  >> Default Directory: " + initFile.getPath() + ", Exists: " + initFile.exists());
					if(initFile.exists()){
						fileChooser.setInitialDirectory(initFile);
					}
	
					
					// Show save file dialog
					File file = fileChooser.showOpenDialog(primaryStage);
	
					String path = (file != null)? file.getPath(): "";
					
					TableauDTO loadedTableau = focusedTableauController.fascade.load(path);
					
					if(loadedTableau != null){
						logger.debug("  >> GUI recieved Tableau: " + loadedTableau);
						itemNew.fire();
						focusedTableauController.buildingNewTableau(loadedTableau);
					}
				} catch(Exception e){
					logger.debug(e);
				}
			}

		});

		// ----------------------------------------
		// EVENT -> MenuItem EXIT
		itemExit.setOnAction(new EventHandler<ActionEvent>() {
			// Shuts down Application
			@Override
			public void handle(ActionEvent event) {

				Platform.exit();
			}
		});

		// *********************************************
		// 						EDIT
		// *********************************************

		// ----------------------------------------
		// EVENT -> MenuItem Find PIVOT
		itemPivot.setOnAction(new EventHandler<ActionEvent>() {
			// Searches Pivot-Element in last created Pivot
			@Override
			public void handle(ActionEvent event) {
				btnPivot.fire();
			}

		});

		// ----------------------------------------
		// EVENT -> MenuItem ITERATION
		itemIteration.setOnAction(new EventHandler<ActionEvent>() {
			// do iteration
			@Override
			public void handle(ActionEvent event) {
				btnIterate.fire();
			}

		});
		
		
		
		// ----------------------------------------
		// EVENT -> MenuItem OPTIMIZE
		itemOptimize.setOnAction(new EventHandler<ActionEvent>() {
			// iterate until Optimum is reached
			@Override
			public void handle(ActionEvent event) {
				
				btnOptimize.fire();
				
			}
		});

		// *********************************************
		// 						Settings
		// *********************************************
		radioEnglish.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {

		    	if(!radioEnglish.isSelected()) {
		    		radioEnglish.setSelected(true);
		    	}
		    	else {
		    		MessageHandler.getInstance().changeLanguage("en");
		    		adaptLanguage();
		    		itemNew.fire();

			    	radioGerman.setSelected(false);
			    	
		    	}
		    	
		    }
		});
		radioGerman.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				if(!radioGerman.isSelected()) {
					radioGerman.setSelected(true);
				}
				MessageHandler.getInstance().changeLanguage("de");
				adaptLanguage();
				itemNew.fire();
					
				
				radioEnglish.setSelected(false);
				
			}
		});
		
		
		// *********************************************
		// 					HELP
		// *********************************************

		// ----------------------------------------
		// EVENT -> MenuItem ABOUT
		itemAbout.setOnAction(new EventHandler<ActionEvent>() {
			// Pop-up with short informations about the Application ITERATOR
			@Override
			public void handle(ActionEvent event) {
				
		
				String title = "Iterator 2.0";
				String head = "ITERATOR 2.0";
				String message = "\r\n"+
						"	HTWG Konstanz		\r\n" +
						"	Prof. Dr. Grütz | Anwendungen der linearen Optimierung\r\n\r\n" +
						"	Aktualisierte Version:	1.1 \r\n" +
						"	Erstellt: 	\tSommersemester 2013 \r\n" +
						"			    \tThomas Winter / Florian Kurz\r\n\r\n" +
						"	Optimiert: 	Sommersemester 2017 \r\n" +
						"			    \tTobias Stübler / Lucas Herre\r\n\r\n";
				ImageView image = new ImageView(this.getClass().getResource("img_about.png").toString());
				
				 Alert alert = new Alert(AlertType.INFORMATION);
			        alert.setTitle(title);
			        alert.setHeaderText(head);
			        alert.setGraphic(image);
			        image.setFitHeight(60);
			        image.setFitWidth(60);
			        alert.setContentText(message);
			        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image(this.getClass().getResource("img_about.png").toString()));        

			        alert.showAndWait();
			
			}
		});

		// ----------------------------------------
		// EVENT -> MenuItem DOCUMENTATION
		itemDocumentation.setOnAction(new EventHandler<ActionEvent>() {
			// shows Documentation
			@Override
			public void handle(ActionEvent event) {
				try {
					File file = new File("doc_html/iterator2017.html");
					java.awt.Desktop.getDesktop().open(file);
				} catch (Exception e) {	
					String msg = MessageHandler.getInstance().getMessage("gui.item.documentation.failed");
					msg += " \n\r\n\r" + e.getMessage(); 
					logger.error(msg);
					MainBasicWindow.showDialog("ERROR", msg, "ERROR");
				}
			}
		});

		// ----------------------------------------
		// EVENT -> MenuItem DEBUG-Modus
		itemDebug.setOnAction(new EventHandler<ActionEvent>() {
			// shows output in Debug modus
			@Override
			public void handle(ActionEvent event) {

				Stage debugStage = new Stage();
				
				//Set Icon
				debugStage.getIcons().add(new Image(getClass().getResourceAsStream("img_console.png")));

				Parent newTableau;

				try {
					// Connection to fxml
					FXMLLoader loader = new FXMLLoader(getClass().getResource("DebugStage.fxml"));
					newTableau = (Parent) loader.load();
					setDebugController((DebugController) loader.getController());

				} catch (IOException e) {
					e.printStackTrace();
					logger.info("FXML-File DebugStage.fxml wurde nicht gefunden");
					return;
				}

				Scene newScene = new Scene(newTableau);
				debugStage.setScene(newScene);

				// Title of Tableau-Stage => dynamisch beziehen
				debugStage.setTitle("Debugger");

				// Specify Owner of this stage -> defines Parent

				debugStage.initOwner(primaryStage);

				DebugControllerLogAppender.setTextArea(debugController.getTextArea());

				debugStage.show();

			}
		});

		// ----------------------------------------
		//Die nachfolgende Methode ist oben schon aufgeführt!!
		// EVENT -> MenuItem SAVE
		/*itemSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(".XML", "*");
				fileChooser.getExtensionFilters().add(extFilter);

				// Show save file dialog
				File file = fileChooser.showSaveDialog(primaryStage);

				if (!file.getName().contains(".")) {
					file = new File(file.getAbsolutePath() + ".XML");
				}

				String path = file.getPath();

				focusedTableauController.fascade.save(focusedTableauController.getCurrentTableau().getId(), path);
			}
		});*/

		// *********************************************
		// 						BUTTONS
		// *********************************************

		// ----------------------------------------
		// EVENT -> Button New
		btnNew.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				itemNew.fire();
			}
		});
		// ----------------------------------------
		// EVENT -> Button Save
		btnSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				itemSave.fire();
			}
		});
		// ----------------------------------------
		// EVENT -> Button Open
		btnOpen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				itemOpen.fire();
			}
		});
		

		// ----------------------------------------
		// EVENT -> Button PIVOT
		btnPivot.setOnAction(new EventHandler<ActionEvent>() {
			// shows Pivot element
			@Override
			public void handle(ActionEvent event) {

				TableauCellPosition pivotPosition;
				
				try {
					pivotPosition = focusedTableauController.fascade.pivotById(focusedTableauController.getCurrentTableau().getId());
		
					if (pivotPosition != null) {
						focusedTableauController.selectCell(pivotPosition.getRow(), pivotPosition.getColumn()+1);
					
	
					} else {
						logger.info("Keine Darstellung aufgrund fehldendem Element möglich");
						
						Dialogs.showWarningDialog(primaryStage, "No element found!", "Pivot Element", "Pivot");
					}
				} catch (Exception e) {
					
					MainBasicWindow.showDialog("ERROR", e.getMessage(), "ERROR");
				}	
				

			}
		});

		// ----------------------------------------
		// EVENT -> Button ITERATION
		btnIterate.setOnAction(new EventHandler<ActionEvent>() {
			// accomplish one iteration step
			@Override
			public void handle(ActionEvent event) {
				
				
				// create new Stage if Checkbox is activated in TableauController
				if (focusedTableauController.isStateCheckWindow() == true) {
										
					TableauDTO tempTableau = focusedTableauController.fascade.getTableauById(focusedTableauController.getCurrentTableau().getId());
				
					try {	
						createNewStageWithTableau(tempTableau);
						// determine selected Cell -> Iterate about this cell-Value

						tempNextTableauController.iterateOverSelectedCell(focusedTableauController.getSelectedCell()[0],focusedTableauController.getSelectedCell()[1],focusedStage);
						tempNextTableauController.setStateCheckWindow(true);
						lastIterateStage.show();
					} catch (Exception e) {
						lastIterateStage.close();
						
						MainBasicWindow.showDialog("ERROR", e.getMessage(), "ERROR");
					}
						
						
				} else {

					// determine selected Cell -> Iterate about this cell-Value
					try {
						focusedTableauController.iterateOverSelectedCell(focusedTableauController.getSelectedCell()[0], focusedTableauController.getSelectedCell()[1],focusedStage);
					} catch (Exception e) {
						MainBasicWindow.showDialog("ERROR", e.getMessage(), "ERROR");
					}

				}
			}
		});

		// ----------------------------------------
		// EVENT -> Button OPTIMIZE
		btnOptimize.setOnAction(new EventHandler<ActionEvent>() {
			// accomplish iteration steps until optimum is reached
			@Override
			public void handle(ActionEvent event) {
				if (focusedTableauController.isStateCheckWindow() == true) {
					
					TableauDTO tempTableau = focusedTableauController.fascade.getTableauById(focusedTableauController.getCurrentTableau().getId());
					
					try {	
						createNewStageWithTableau(tempTableau);
						tempNextTableauController.iterateUntilOptimum();
						tempNextTableauController.setStateCheckWindow(true);
						lastIterateStage.show();
					} catch (Exception e) {
						lastIterateStage.close();
						if(e.getMessage() == null){
							MainBasicWindow.showDialog("INFO", MessageHandler.getInstance().getMessage("gui.action.iterate.optimized"), "INFO");
						} else {
							MainBasicWindow.showDialog("ERROR", e.getMessage(), "ERROR");
						}
					}
					
				} else {
				
					
						focusedTableauController.iterateUntilOptimum();
					
					
					
				
				}
			}
		});
	}
	public static void startAlertOptimum(){
		
		
		 Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Iterator 2.0");
	        alert.setHeaderText("ITERATOR 2.0");
	        alert.setContentText(MessageHandler.getInstance().getMessage("gui.action.iterate.optimized"));
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        alert.showAndWait();
		
		
	}
	
	public static void startAlertUnboundedSolution(){
		
		 Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Iterator 2.0");
	        alert.setHeaderText("ITERATOR 2.0");
	        alert.setContentText(MessageHandler.getInstance().getMessage("gui.action.iterate.unboundedSolution"));
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        alert.showAndWait();
		
		
	}
	public static boolean display(String title, String message){
		
		Stage window = new Stage();
		window.setTitle(title);
		window.setMinWidth(300);
		window.setMinHeight(100);
		
		Label label = new Label();
		label.setText(message);
		
		Button yesButton = new Button("     Ja      ");
		Button noButton = new Button("     Nein    ");
		
		yesButton.setMaxWidth(500.0);
		noButton.setMaxWidth(80.0);
	
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();	});
		
		noButton.setOnAction(e -> {
			answer = false;
			window.close();	});
		
		HBox hb = new HBox(yesButton, noButton);
		hb.setMaxWidth(200);
		hb.setSpacing(10);
		hb.setPadding(new Insets(20));
		
		VBox vb = new VBox(label);
		vb.setSpacing(10);
		//vb.setPadding(new Insets(20));
		
		FlowPane root = new FlowPane();
		root.setVgap(10);
        root.setHgap(10);
        root.setPrefWrapLength(300);
		root.getChildren().add(label);
		root.getChildren().add(vb);
		root.getChildren().add(hb);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root,300,100);
		window.setScene(scene);
		window.showAndWait();
		return answer;
		/*HBox layout = new HBox();
		layout.setSpacing(10);
		
		layout.getChildren().addAll(label, yesButton, noButton);
		
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();*/
		
		
	}

	private void adaptLanguage() {
		// Language selection
		btnPivot.setText(MessageHandler.getInstance().getMessage("gui.button.pivot"));
		btnIterate.setText(MessageHandler.getInstance().getMessage("gui.button.iterate"));
		btnOptimize.setText(MessageHandler.getInstance().getMessage("gui.button.optimize"));
		
		itemAbout.setText(MessageHandler.getInstance().getMessage("gui.item.about"));
		itemDebug.setText(MessageHandler.getInstance().getMessage("gui.item.debug"));
		itemDocumentation.setText(MessageHandler.getInstance().getMessage("gui.item.documentation"));
		itemExit.setText(MessageHandler.getInstance().getMessage("gui.item.exit"));
		itemIteration.setText(MessageHandler.getInstance().getMessage("gui.item.iteration"));
		itemNew.setText(MessageHandler.getInstance().getMessage("gui.item.new"));
		itemOpen.setText(MessageHandler.getInstance().getMessage("gui.item.open"));
		itemOptimize.setText(MessageHandler.getInstance().getMessage("gui.item.optimize"));
		itemPivot.setText(MessageHandler.getInstance().getMessage("gui.item.pivot"));
		itemSave.setText(MessageHandler.getInstance().getMessage("gui.item.save"));
		itemLanguage.setText(MessageHandler.getInstance().getMessage("gui.item.language"));
		menuSetting.setText(MessageHandler.getInstance().getMessage("gui.menu.settings"));
		menuFile.setText(MessageHandler.getInstance().getMessage("gui.menu.file"));
		menuHelp.setText(MessageHandler.getInstance().getMessage("gui.menu.help"));
		menuEdit.setText(MessageHandler.getInstance().getMessage("gui.menu.edit"));
		
		
		
		
	}

	public void setStageOwner(MyStage stage) {
		// Specify Owner of this stage -> defines Parent => primaryStage
		stage.initOwner(primaryStage);
	}
	
	
	public void createNewStageWithTableau(TableauDTO tableau) {
		final MyStage newStage = new MyStage(StageStyle.DECORATED,keyCounter);
		
		
	
		allNewStages.add(newStage);

		Parent newTableau = null;
		TableauController tabControl = null;
		
		
		
	
		try {
			// Connection to fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TableauPage.fxml"));
			newTableau = (Parent) loader.load();
			tabControl = (TableauController) loader.getController();
			tabControl.setAnzColumns(focusedTableauController.getAnzColumns());
			tabControl.setAnzRows(focusedTableauController.getAnzRows());
			
			allTableauControllers.add(tabControl);
			
			setFocusHandler(newStage, tabControl);
			
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FXML-File TableauPage.fxml wurde nicht gefunden");
		}
		
		Scene newScene = new Scene(newTableau);

		newStage.setScene(newScene);

		setStageOwner(newStage);
		
		// ++++++++++++++++++++++++++++++++
		// 		generates Tables
		// ++++++++++++++++++++++++++++++++
		tabControl.buildingNewTableau(tableau);
		tabControl.setCurrentTableau(tableau);
		// ++++++++++++++++++++++++++++++++

		// Title of Tableau-Stage => dynamisch beziehen
		newStage.setTitle(getNewTableauName());
		newStage.setWidth(focusedStage.getWidth());
		newStage.setHeight(focusedStage.getHeight());

		// Specify Owner of this stage -> defines Parent
		newStage.initOwner(primaryStage);
		
		newStage.setSceneHandler();
		tabControl.setId(keyCounter);
		keyCounter++;
		
		//newStage.show();
		lastIterateStage = newStage;
		tempNextTableauController = newStage.getTabController();
	}

	// Creates initial Tableau
	public void createTableau() {
		itemNew.fire();
	}

	public String getNewTableauName() {
		return newTableauName;
	}

	public void setNewTableauName(String newTableauName) {
		this.newTableauName = newTableauName;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		BasicWindowController.logger = logger;
	}

	public TableauDTO getFocusedTableau() {
		return focusedTableau;
	}

	public void setFocusedTableau(TableauDTO focusedTableau) {
		this.focusedTableau = focusedTableau;
	}

	public HashSet<MyStage> getAllNewStages() {
		return allNewStages;
	}

	@SuppressWarnings("rawtypes")
	public Object getLastElement(final HashSet c) {
		final Iterator itr = c.iterator();
		Object lastElement = itr.next();
		while (itr.hasNext()) {
			lastElement = itr.next();
		}
		return lastElement;
	}

	public MyStage getStageByID(int id) {
		for (MyStage stage : allNewStages) {
			if (stage.getStageId() == id) {
				return stage;
			}
		}
		return null;
	}

	public void setAllNewStages(HashSet<MyStage> allNewStages) {
		this.allNewStages = allNewStages;
	}

	public DebugController getDebugController() {
		return debugController;
	}

	public void setDebugController(DebugController debugController) {
		this.debugController = debugController;
	}

	
	
	public static void focusedTableau(int eventFromStage) {
		
		for(TableauController tabcontroller : allTableauControllers) {
		
			if(tabcontroller.getId() == eventFromStage) {
				focusedTableauController = tabcontroller;
			}
		
		}
		
	}

	public static void setFocusedTableauController(TableauController focusedTableauController) {
		BasicWindowController.focusedTableauController = focusedTableauController;
	}
	
	
	
	
	public static Stage getFocusedStage() {
		return focusedStage;
	}

	public static void setFocusedStage(Stage focusedStage) {
		BasicWindowController.focusedStage = focusedStage;
	}

	public void setFocusHandler(final MyStage stage, TableauController controller){
		stage.setTabController(controller);
		
		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                	BasicWindowController.setFocusedTableauController(stage.getTabController());
                	BasicWindowController.setFocusedStage(stage);
                }	            	
            }
        });
		
	}
	
	public String pathBack(String path){
		for (int i = path.length()-1; i > 0 ; i--) {
			if(path.charAt(i) == '\\'){
				path = path.substring(0, i);
				break;
			}		
		}
		return path;
	}
	
	
	
	
	
	
}
