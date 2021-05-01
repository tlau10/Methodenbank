package iterator.gui.javafx;

import iterator.Fascade;
import iterator.MessageHandler;
import iterator.tableau.TableauCellPosition;
import iterator.tableau.TableauDTO;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.log4j.Logger;

public class TableauController implements Initializable {

	private static Logger logger = Logger.getLogger(TableauController.class);
	
	private int id;

	FractionConverter fractionConv;
	
	protected Fascade fascade;
	private int anzColumns = 2;
	private int anzRows = 2;
	
	private TableauDTO currentTableau = null;
	
	@SuppressWarnings("rawtypes")
	private static EditingCell currentEditingCell = null;
	@SuppressWarnings("unused")
	private static TextField inputTextField = null;
	private boolean isEditing;
	
	public TableauController() {
		this.fascade = Fascade.getInstance();
		fractionConv = new FractionConverter();
	}

	// Instances in Controller-Class for every ID-Item
	@FXML
	private AnchorPane footer;
	
	@SuppressWarnings("rawtypes")
	@FXML
	protected TableView tabView;
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView zielFunktionTable;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableView bVektorTable;
	@FXML
	private TextField zielFunktionCoefficient;
	
	@FXML
	private Button btnRowColum;
	@FXML
	private TextField rows;
	@FXML
	private TextField columns;

	@FXML
	private Text labelRow;
	@FXML
	private Text labelColumn;
	
	@FXML
	private ScrollPane scrollpane;

	@SuppressWarnings("rawtypes")
	private ObservableList<ObservableList> dataMatrix;


	@FXML
	private CheckBox checkNewWindow;
	private boolean stateCheckWindow;

	@FXML
	private Label infolabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ButtonStyle
		Image iconButtonRefresh = new Image(getClass().getResourceAsStream("img_refresh.png"));
		
		btnRowColum.setGraphic(new ImageView(iconButtonRefresh));
		btnRowColum.setMaxHeight(15);
		btnRowColum.setMaxWidth(15);
		
		// Language selection
		labelRow.setText(MessageHandler.getInstance().getMessage("gui.table.tableRows"));
		labelColumn.setText(MessageHandler.getInstance().getMessage("gui.table.tableColumns"));
		checkNewWindow.setText(MessageHandler.getInstance().getMessage("gui.table.newWindow"));
		labelRow.setTextAlignment(TextAlignment.RIGHT);
		labelColumn.setTextAlignment(TextAlignment.RIGHT);
		
		tabView.setOnMouseMoved(new EventHandler<Event>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void handle(Event event) {
				int selectedColumn = 0;
				int selectedRow = 0;

				ObservableList<TablePosition> cells = tabView.getSelectionModel().getSelectedCells();
				for (TablePosition<?, ?> cell : cells) {
					selectedColumn = cell.getColumn();
					selectedRow = cell.getRow();
				}
				
				if(selectedColumn == (currentTableau.getAMatrixVariable()+1) && selectedRow == currentTableau.getAMatrixRestriction()) {
					infolabel.setText(MessageHandler.getInstance().getMessage("gui.table.zielfunktionskoeffizient"));
				}
				else if(selectedColumn <= (currentTableau.getAMatrixVariable()+1) && selectedRow <= currentTableau.getAMatrixRestriction()) {
					infolabel.setText("a-Matrix | "+MessageHandler.getInstance().getMessage("gui.table.column") + ": "  + selectedColumn + "  " +MessageHandler.getInstance().getMessage("gui.table.row") + ": " + (selectedRow+1));
				
					if(selectedColumn == (currentTableau.getAMatrixVariable()+1)) {
						infolabel.setText( MessageHandler.getInstance().getMessage("gui.table.bvektor")+ " | " + MessageHandler.getInstance().getMessage("gui.table.row") + ": " + (selectedRow+1));
					}
					else if(selectedRow == (currentTableau.getAMatrixRestriction())) {
						infolabel.setText(MessageHandler.getInstance().getMessage("gui.table.zielfunktion") + " | "+ MessageHandler.getInstance().getMessage("gui.table.column")+": " + selectedColumn);
					}
					else if (selectedColumn == 0) {
						infolabel.setText("a-Matrix " + MessageHandler.getInstance().getMessage("gui.table.description") + " | "+MessageHandler.getInstance().getMessage("gui.table.row") + ": " + (selectedRow+1));
					}
				}
				else {
					infolabel.setText("no selection");
				}
			}
		});
		

		// ----------------------------------------
		// EVENT -> Button New Rows Columns
		btnRowColum.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if (rows.getText() == null) {
					anzRows = 2;
				} else {
					anzRows = Integer.parseInt(rows.getText());
				}

				if (columns.getText() == null) {
					anzColumns = 2;
				} else {
					anzColumns = Integer.parseInt(columns.getText());
				}
				

				TableauDTO tableau = getCurrentTableau();
				
				// add Columns
				if(tableau.getAMatrixVariable() <= anzColumns) {
					
					int difference = anzColumns - tableau.getAMatrixVariable();
					
					for(int i = 0; i < difference; i++) {
						fascade.addVariableToId(tableau.getId());
					}
				}
				// delete Columns
				else {
					int difference = tableau.getAMatrixVariable() - anzColumns;
					
					for(int i = 0; i < difference; i++) {
						fascade.removeVariableFromId(tableau.getId());
					}
				}
				
				// add Rows
				if(tableau.getAMatrixRestriction() <= anzRows) {
					int difference = anzRows - tableau.getAMatrixRestriction();
					
					for(int i = 0; i < difference; i++) {
						fascade.addRestrictionToId(tableau.getId());
					}
				}
				// delete Row
				else {
					int difference = tableau.getAMatrixRestriction() - anzRows;
					
					for(int i = 0; i < difference; i++) {
						fascade.removeRestrictionFromId(tableau.getId());
					}
				}

				tableau = fascade.getTableauById(tableau.getId());
						
				buildingNewTableau(tableau);

			}

		});

		// ----------------------------------------
		// EVENT -> CHECKBOX
		checkNewWindow.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// get Status in boolean
				stateCheckWindow = checkNewWindow.selectedProperty().get();
			}
		});
		
		rows.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  btnRowColum.fire();
		      }
		    }
		});
		
		columns.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  btnRowColum.fire();
		      }
		    }
		});
	}

	// *********************************************************************
	// Method creates new Tableau with all Tables
	// *********************************************************************
	public void buildingNewTableau(TableauDTO tableauDTO) {

		// Clear Columns
		tabView.getColumns().clear();

		// clear Data-Lists
		if (dataMatrix != null) {
			dataMatrix.clear();
		}
	
		setCurrentTableau(fascade.getTableauById(tableauDTO.getId()));

		buildWholeTableau(tableauDTO.getAMatrixVariable(),tableauDTO.getAMatrixRestriction());

		fascade.updateTableau(currentTableau);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void buildWholeTableau(int anzColumns, int anzRows) {

		// Passt die Größe der Spalten an die Tabelle an
//		tabView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Select that Table is in Cell-SelectionMode and not in Row-Selection mode
		tabView.getSelectionModel().setCellSelectionEnabled(true);
		
		// Editierbarkeit implementieren
		tabView.setEditable(true);
		Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
			public TableCell call(TableColumn p) {
				currentEditingCell = new EditingCell(tabView);
				return currentEditingCell;
			}
		};
		
		//EditCell on NumberKeyPressed
		tabView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {

            	if(	!isEditing && (
            		t.getText().matches("\\d") | 
            		t.getCode() == KeyCode.DELETE | 
            		t.getCode() == KeyCode.MINUS | 
            		t.getCode() == KeyCode.SUBTRACT)){
            		isEditing = true;
            		
           			TablePosition tablePosition = tabView.getFocusModel().getFocusedCell();
            		EditingCell.setKeyLastInput(t.getText(), t);
            		
            		tabView.edit(tablePosition.getRow(), tablePosition.getTableColumn());
            		
            		
            		isEditing = false;
            	} 
            }
        });
		

		
		
		try {
		dataMatrix = FXCollections.observableArrayList();

		/**********************************
		 * TABLE COLUMN ADDED DYNAMICALLY *
		 **********************************/
		for (int i = 0; i <= (anzColumns + 1); i++) {
			// +1, da erste Spalte Beschreibung sein soll, und diese nicht mitgezählt wird
			
			final int index = i;
			
			// ------------------------------
			// Create Column Description 
			// ------------------------------
			if (i == 0) {
				TableColumn columnDescription = new TableColumn(MessageHandler.getInstance().getMessage("gui.table.description"));
				columnDescription.setPrefWidth(120);
				columnDescription.setSortable(false);

				columnDescription.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

							@Override
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								ObservableValue<String> myObservableValueDescription = new SimpleStringProperty(param.getValue().get(index).toString());

								return myObservableValueDescription;
							}

						});

				// Implementing Editing Cell
				columnDescription.setCellFactory(TextFieldTableCell.forTableColumn());
				columnDescription.setOnEditCommit(new EventHandler<CellEditEvent>() {

							public void handle(CellEditEvent t) {
								t.getTableView().getItems().get(t.getTablePosition().getRow());
							}
						});

				tabView.getColumns().addAll(columnDescription);
				logger.info("Column [Description]");
				continue;
			}
			
			// =========================
			// CREATE Column B-VEKTOR
			// =========================
			if(i == (anzColumns+1)) {
				TableColumn columnBVektor = new TableColumn(MessageHandler.getInstance().getMessage("gui.table.bvektor"));
				columnBVektor.setPrefWidth(65);
				columnBVektor.setSortable(false);

				
				columnBVektor.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
						ObservableValue<String> myObservableValue = new SimpleStringProperty((String) param.getValue().get(index));
						return myObservableValue;
					}

				});
					// --- Add for Editable Cell of Value field, in Double
					columnBVektor.setCellFactory(cellFactory);

					columnBVektor.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {

								@Override
								public void handle(CellEditEvent t) {
									
									t.getTableView().getItems().get(t.getTablePosition().getRow());

									// update Back-End
									int updateRow = t.getTablePosition()
											.getRow();

									// last Row belongs to Zielfunktionskoeffizient
									if (updateRow == currentTableau.getAMatrixRestriction()) {
										String tempZFKoeff = currentTableau.getZielfunktionskoeffizient();
										tempZFKoeff = (String) t.getNewValue();

										currentTableau.setZielfunktionskoeffizient(tempZFKoeff);
										fascade.updateTableau(currentTableau);

										logger.info("===== ZF-Koeffizient ======");
										logger.info("Feld in Spalte ["
												+ t.getTablePosition()
														.getColumn()
												+ "] und Zeile ["
												+ t.getTablePosition().getRow()
												+ "] wurde von Wert '"
												+ t.getOldValue()
												+ "' auf neuen Wert '"
												+ t.getNewValue() + "' gesetzt");

									} else {

										// update Value in Arrays[][]
										String[] tempBVektor = currentTableau
												.getBVektor();
										tempBVektor[updateRow] = (String) t.getNewValue();

										currentTableau.setBVektor(tempBVektor);
										fascade.updateTableau(currentTableau);

										logger.info("===== B-Vektor ======");
										logger.info("Feld in Spalte ["
												+ t.getTablePosition()
														.getColumn()
												+ "] und Zeile ["
												+ t.getTablePosition().getRow()
												+ "] wurde von Wert '"
												+ t.getOldValue()
												+ "' auf neuen Wert '"
												+ t.getNewValue() + "' gesetzt");
									}
								}
							});

				
				tabView.getColumns().addAll(columnBVektor);
				logger.info("Column [b-Vektor]");
				
				continue;
				}
			
			
			/* +++++++++++++++++++++++++++++++++++++++++++++++
			 * CREATE DYNAMIC COLUMNS FOR PARAMETERS
			 * +++++++++++++++++++++++++++++++++++++++++++++++
			*/
			// We are using non property style for making dynamic table
			TableColumn col = new TableColumn("X" + (i));

			col.setSortable(false);
			col.setPrefWidth(65);			
		
			col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

				@Override
				public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
					ObservableValue<String> myObservableValueDescription = new SimpleStringProperty(param.getValue().get(index).toString());

					return myObservableValueDescription;
				}

			});

				// Implementing Editing Cell
			col.setCellFactory(TextFieldTableCell.forTableColumn());
			col.setOnEditCommit(new EventHandler<CellEditEvent>() {

				public void handle(CellEditEvent t) {
					t.getTableView().getItems().get(t.getTablePosition().getRow());
				}
			});
			

			// --- Add for Editable Cell of Value field, in Double
			col.setCellFactory(cellFactory);

			col.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {

				@Override
				public void handle(CellEditEvent t) {
					t.getTableView().getItems().get(t.getTablePosition().getRow());
					
					// update Back-End
					int updateColumn = t.getTablePosition().getColumn();
					int updateRow = t.getTablePosition().getRow();

					// If last row is edited the row belongs to Zielfunktion
					if(updateRow == currentTableau.getAMatrixRestriction()) {
						String[] tempZielfunkt = currentTableau.getZielfunktion();
						tempZielfunkt[updateColumn -1] = (String) t.getNewValue();
												
						currentTableau.setZielfunktion(tempZielfunkt);
						fascade.updateTableau(currentTableau);
						
						logger.info("=====  Zielfunktion  ======");
						logger.info("Feld in Spalte ["
								+ t.getTablePosition().getColumn()
								+ "] und Zeile ["
								+ t.getTablePosition().getRow()
								+ "] wurde von Wert '" + t.getOldValue()
								+ "' auf neuen Wert '" + t.getNewValue()
								+ "' gesetzt");				
					} else {
						// update Value in Arrays[][]
						String[][] tempAMatrix = currentTableau.getaMatrix();
						tempAMatrix[updateColumn - 1][updateRow] = (String) t.getNewValue();
	
						currentTableau.setAMatrix(tempAMatrix);
						fascade.updateTableau(currentTableau);
	
						logger.info("===== a-Matrix =======");
						logger.info("Feld in Spalte ["
								+ t.getTablePosition().getColumn()
								+ "] und Zeile ["
								+ t.getTablePosition().getRow()
								+ "] wurde von Wert '" + t.getOldValue()
								+ "' auf neuen Wert '" + t.getNewValue()
								+ "' gesetzt");
					}
				}
			});
			
			tabView.getColumns().addAll(col);
			logger.info("Column [" + i + "]");

		}
		
		/********************************
		 * Data added to ObservableList *
		 ********************************/
		for (int i = 0; i <= anzRows; i++) {
			// Iterate Row
			ObservableList row = FXCollections.observableArrayList();
			
			// RESTRIKTIONEN
			if(i < anzRows) {
				String restrictionName = MessageHandler.getInstance().getMessage("gui.table.restriction") + (i + 1);
				row.add(restrictionName);
				
				// Iterate over Columns
				for (int j = 1; j <= anzColumns+1; j++) {
					
					// Build a-Matrix
					if(j <= anzColumns) {	
						row.add(currentTableau.getaMatrix()[j - 1][i]);
					}
					else {
						row.add(currentTableau.getBVektor()[i]);
					}
				}
			
			}
			
			// ZIELFUNKTION
			else {
				String restrictionName = MessageHandler.getInstance().getMessage("gui.table.zielfunktion");
				row.add(restrictionName);

				// Iterate Column
				for (int j = 1; j <= anzColumns+1; j++) {
					
					// Build ZF
					if(j <= anzColumns) {	
						row.add(currentTableau.getZielfunktion()[j - 1]);
					}
					else {
						row.add(currentTableau.getZielfunktionskoeffizient());
					}
				}
			}

			logger.info("Row [" + (i + 1) + "] added " + row);
					
			dataMatrix.add(row);				

			// FINALLY ADDED TO TableView
			tabView.setItems(dataMatrix);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error on Building Data");
		}
		
	}

	
	// -------------------------------------------------------
	// Methode converts a String Fraction out of a double Value
	// -------------------------------------------------------
	public String convertDoubleToStringFraction(double value) {
		
		// Rounding-Check
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		
		DecimalFormat dcFormat = new DecimalFormat("##########.00",dfs);
		
		String roundedValue = dcFormat.format(value);
		
		String fraction = fractionConv.convertToFraction(Double.parseDouble(roundedValue));
		
		return fraction;
	}
	
	// -------------------------------------------------------
	// Methode converts a String Fraction out of a double Value
	// -------------------------------------------------------
	public double convertStringFractionToDoubleValue(String fraction) throws Exception{

		Double d = null;
		if (fraction != null) {
			if (fraction.contains("/")) {
				String[] numbers = fraction.split("/");
				if (numbers.length == 2) {
					BigDecimal d1 = BigDecimal.valueOf(Double.valueOf(numbers[0]));
					BigDecimal d2 = BigDecimal.valueOf(Double.valueOf(numbers[1]));
					BigDecimal response = d1.divide(d2, MathContext.DECIMAL128);
					d = response.doubleValue();
				}
			} else {
				d = Double.valueOf(fraction);
			}
		}
		if (d == null) {
			throw new Exception(fraction);
		}
		return d;
	}
	
	
	
	// -------------------------------------------------------
	// Methode returns the selected Cell
	// -------------------------------------------------------
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int[] selectedCell() {

		int selectedColumn = 0;
		int selectedRow = 0;

		ObservableList<TablePosition> cells = tabView.getSelectionModel().getSelectedCells();
		for (TablePosition<?, ?> cell : cells) {
			selectedColumn = cell.getColumn();
			selectedRow = cell.getRow();
		}

		int[] returnArray = new int[2];
		returnArray[0] = selectedColumn;
		returnArray[1] = selectedRow;

		return returnArray;

	}

	// -------------------------------------------------------
	// Methode for iteration over a selected cell in Tableau
	// -------------------------------------------------------
	@SuppressWarnings({ })
	public void iterateOverSelectedCell(int selectedRow, int selectedColumn,  Stage owner) throws Exception {
		int nowSelectedColumn = selectedColumn;
		int nowSelectedRow = selectedRow;
		logger.debug("   >>> iterate over Column:" + selectedColumn + ", Row:" + selectedRow);
		
		int iterationSteps = currentTableau.getNumberOfIterates() + 1;
		currentTableau.setNumberOfIterates(iterationSteps);

			// Error-Handling -> Popup
			if(nowSelectedColumn == (currentTableau.getAMatrixVariable()+1) && nowSelectedRow == currentTableau.getAMatrixRestriction()) {
				Dialogs.showWarningDialog(owner, MessageHandler.getInstance().getMessage("gui.action.iterate.iterateTargetvalue"));
			}
			else if(nowSelectedColumn <= (currentTableau.getAMatrixVariable()+1) && nowSelectedRow <= currentTableau.getAMatrixRestriction()) {
				
				if(nowSelectedColumn == 0) {
					Dialogs.showWarningDialog(owner, MessageHandler.getInstance().getMessage("gui.action.iterate.iterateAMatrix"));
				}
				else if(nowSelectedColumn == (currentTableau.getAMatrixVariable()+1)) {
					Dialogs.showWarningDialog(owner, MessageHandler.getInstance().getMessage("gui.action.iterate.iterateBVektor"));
				}
				else if(nowSelectedRow == (currentTableau.getAMatrixRestriction())) {
					Dialogs.showWarningDialog(owner, MessageHandler.getInstance().getMessage("gui.action.iterate.iterateTarget"));
				}
				else{
					
					TableauDTO iteratedTableau;
					try {
						iteratedTableau = fascade.iterateById(currentTableau.getId(), nowSelectedRow,(nowSelectedColumn - 1));

						setCurrentTableau(iteratedTableau);
	
						// Ergebnis in Tablle eingeben
						fascade.updateTableau(iteratedTableau);
						buildingNewTableau(iteratedTableau);
					} catch (Exception e) {
						MainBasicWindow.showDialog("ERROR", e.getMessage(), "ERROR");
					}
				}
			}
			else {
				Dialogs.showWarningDialog(owner, MessageHandler.getInstance().getMessage("gui.action.iterate.iterateNoCell"));
			}			

	}

	// -------------------------------------------------------
	// Methode for iteration until optimum is reached
	// -------------------------------------------------------
	public void iterateUntilOptimum() {
		TableauDTO tableau = null;
		
		for (;;) {
			if (currentTableau.isOptimized() == false) {
				
				// Find Pivot-Element
				TableauCellPosition pivotElement;
				
				try {
					pivotElement = fascade.pivotById(currentTableau.getId());
	
	
					// Iteration
					tableau = fascade.iterateById(currentTableau.getId(),pivotElement.getRow(), pivotElement.getColumn());
	
					setCurrentTableau(tableau);
					logger.info("Optimization: Step: " + currentTableau.getNumberOfIterates());
				
				} catch (Exception e) {
					MainBasicWindow.showDialog("ERROR", e.getMessage(), "ERROR");
					break;
				}
						
				
			} else {
				logger.info("Tableau is optimized");

				fascade.updateTableau(tableau);
				buildingNewTableau(tableau);
				break;
			}
		}
	}

	
	
	// *********************************************************************
	// GETTER und SETTER
	// *********************************************************************
	public int getAnzRows() {
		return anzRows;
	}

	public void setAnzRows(int anzRows) {
		this.anzRows = anzRows;
		this.rows.setText(String.valueOf(anzRows));
	}

	public int getAnzColumns() {
		return anzColumns;
	}

	public void setAnzColumns(int anzColumns) {
		this.anzColumns = anzColumns;
		this.columns.setText(String.valueOf(anzColumns));
	}

	public boolean isStateCheckWindow() {
		return stateCheckWindow;
	}

	public void setStateCheckWindow(boolean stateCheckWindow) {
		this.stateCheckWindow = stateCheckWindow;
		this.checkNewWindow.setSelected(stateCheckWindow);
	}

	public TableauDTO getCurrentTableau() {
		return currentTableau;
	}

	public void setCurrentTableau(TableauDTO currentTableau) {
		this.currentTableau = currentTableau;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void selectCell(int row, int column){
		tabView.getSelectionModel().clearAndSelect(row, (TableColumn) tabView.getColumns().get(column));
		//((Cell) tabView.getSelectionModel().getSelectedCells().get(0)).setStyle("-fx-border-color: red;");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int[] getSelectedCell(){
		int nowSelectedColumn = 0;
		int nowSelectedRow = 0;

		ObservableList<TablePosition> cells = tabView.getSelectionModel().getSelectedCells();
		for (TablePosition<?, ?> cell : cells) {
			nowSelectedColumn = cell.getColumn();
			nowSelectedRow = cell.getRow();
		}
		
		logger.debug("   >> GET SELECTED ROW: " + nowSelectedRow + ", COLUMN:" + nowSelectedColumn);
		
		int[] cell = {nowSelectedRow,nowSelectedColumn};
		return cell;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
