package iterator.gui.javafx;


import java.util.ArrayList;
import java.util.List;

import com.sun.media.jfxmedia.logging.Logger;
 
import iterator.Fascade;
import iterator.logic.LogicSimplexRational;
import iterator.tableau.TableauCellPosition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;

import javafx.scene.control.Dialogs;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.beans.value.ChangeListener;

public class EditingCell<S> extends TableCell<S , String>{

	//private TableauController tab;
	//private BasicWindowController basic;
 	
 	private TextField textField;
 	private static TextField textFieldStatic = null;
 	
	private int anzColumns;
	private int anzRows;
	private static TableauController tc = null;
 	
	private int currentRow;
 	private EventTarget target;
 	private static String keyLastInput = null;
 	private static KeyEvent keyLastEvent = null;
 	   
     public EditingCell(EventTarget target) {
     	this.target = target;
 
     }
    
     @Override
     public void startEdit() {
         super.startEdit();
 
       
         if (textField == null) {
             createTextField();
         }
         
         setGraphic(textField);
         setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
 
         textField.requestFocus();
         
         if(keyLastEvent != null && keyLastEvent.getCode() == KeyCode.DELETE){ //ausloesen bei betaetigen von Entfernen-Taste
         	
         	textField.setText("0"); //Wert wird auf 0 gesetzt
         	commit("0");			//Wert wird uebernommen und Textfeld verlassen
         	
         } else if(keyLastInput != null){
         	textField.setText(keyLastInput);
             keyLastInput = null;
         	keyLastEvent = null;
         	
         	textField.selectEndOfNextWord();     	
 
         } else {
         	
         	textField.selectAll();
         	
         }
     }
    
     @Override
     public void cancelEdit() {
         super.cancelEdit();
        
         setText(String.valueOf(getItem()));
         setContentDisplay(ContentDisplay.TEXT_ONLY);
     }
 
     @Override
     public void updateItem(String item, boolean empty) {
         super.updateItem(item, empty);
 
         if (empty) {
             setText(null);
             setGraphic(null);
         } else {
         	
             if (isEditing()) {
                 if (textField != null) {
                     textField.setText(getString());
                 }
                 setGraphic(textField);
                 setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
             } else {
                 setText(getString());
                 setContentDisplay(ContentDisplay.TEXT_ONLY);
             }
         }
         	
        
     }
 
    
    
    
     private TextField createTextField() {
         textField = new TextField(getString());
         textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
         
         textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
             public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                 if(!newValue.booleanValue()){
                     commit(textField.getText());
                 }
             }
         });
         
         
         textField.inputMethodRequestsProperty().addListener(new ChangeListener<Object>() {
 			@Override
 			public void changed(ObservableValue<? extends Object> arg0,
 					Object arg1, Object arg2) {
 				textField.selectEndOfNextWord();   
 			}
         });
         
         //sobald Maus Textfeld verlaesst, wird Wert uebernommen
 
 		/*tab.tabView.setOnMouseClicked(new EventHandler<MouseEvent>() {
 			
 			@Override
 			public void handle(MouseEvent mouse) {
 				
 					//commit(textField.getText());
 				
 				
 				
 					
 			}
         	
         });*/
         
 		/*Double Click
 		textField.setOnMouseClicked(new EventHandler<MouseEvent>(){
 			
 			public void handle(MouseEvent mouseEvent){
 				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
 					if(mouseEvent.getClickCount() == 2){
 										
 						
 						Alert alert = new Alert(AlertType.INFORMATION);
 				        alert.setTitle("Help");
 				        alert.setHeaderText("Help");				     
 				        alert.showAndWait();
 						
 						}
 					
 					}
 				}
 		});*/
         
 		
 		
 		//Uebernahme von Wert in Textfeld bei Tastendruck
 		
       
        
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if ((t.getCode() == KeyCode.ENTER && textField.getText() != null)
                		|| t.getCode().isNavigationKey() 
                		|| t.getCode().isArrowKey() ) {

                	commit(textField.getText()); 
                	
                	//Fire KeyEvent to TabView (Arrows, etc.)
                	if(t.getCode() != KeyCode.ENTER ){
                		KeyEvent.fireEvent(target, t);
                	}

                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                } else if(keyLastInput != null && (t.getCode().isDigitKey() ||
                		t.getCode() == KeyCode.SLASH ||
                		t.getCode() == KeyCode.PERIOD ||
                		t.getCode() == KeyCode.SUBTRACT)){
                } else if(t.getCode() == KeyCode.TAB){
                	
                	anzColumns = tc.getAnzColumns();
                	anzRows = tc.getAnzRows();
                	System.out.println(anzColumns + " Anzahl");
                	TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                	
                	commit(textField.getText());
                	if (nextColumn != null) {
                		//tc.selectCell(1, 1);
                        getTableView().edit(currentRow, nextColumn);
                    }
                	
                }
                
                           
            }
        });
        
        /*
         textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
             @Override
             public void handle(KeyEvent t) {
                 if ((t.getCode() == KeyCode.ENTER && textField.getText() != null)
                 		|| t.getCode().isNavigationKey() 
                 		|| t.getCode().isArrowKey() 
                 		|| t.getCode() == KeyCode.TAB) {
                 	
                 	
                 	commit(textField.getText());       	
                 	
                 	//Fire KeyEvent to TabView (Arrows, etc.)
                 	if(t.getCode() != KeyCode.ENTER ){
                 		KeyEvent.fireEvent(target, t);
                 	}
                     
                     
                 } else if (t.getCode() == KeyCode.ESCAPE) {
                     cancelEdit();
                 } else if(keyLastInput != null && (t.getCode().isDigitKey() ||
                 		t.getCode() == KeyCode.SLASH ||
                 		t.getCode() == KeyCode.PERIOD ||
                 		t.getCode() == KeyCode.SUBTRACT)){
                 }
                 
                            
             }
-        });
+        });*/
         
 
        
 
 
         return this.textField;
         
     }
     
    
     private String getString() {
         return getItem() == null ? "" : getItem().toString();
     }
 
 	public  TextField getTextField() {
 		return createTextField();
 	}
 	 	 	
 	public void setTextField(TextField textField) {
 		this.textField = textField;
 	}
 	
 	public static TextField getStaticTextField(){
 		return textFieldStatic;
 	}
 	
	private TableColumn<S, ?> getNextColumn(boolean forward) {
		List<TableColumn<S, ?>> columns = new ArrayList<>();
		currentRow = getTableRow().getIndex();
		for (TableColumn<S, ?> column : getTableView().getColumns()) {
			columns.addAll(getLeaves(column));
		}
		// There is no other column that supports editing.
		if (columns.size() < 2) {
			return null;
		}
		int currentIndex = columns.indexOf(getTableColumn());
		int nextIndex = currentIndex;
		if (forward) {
			nextIndex++;
			if (nextIndex > columns.size() - 1) {
				nextIndex = 0;
			}
		} else {
			nextIndex--;
			if (nextIndex < 0) {
				nextIndex = columns.size() - 1;
			}
		}
		
		if (currentIndex == anzColumns+1) {
			if(currentRow < anzRows){
				currentRow = currentRow+1;
				nextIndex = nextIndex+1;
			}
			//System.out.println(currentRow);
			
		}
		return columns.get(nextIndex);
	}
	
	private List<TableColumn<S, ?>> getLeaves(
			TableColumn<S, ?> root) {
		List<TableColumn<S, ?>> columns = new ArrayList<>();
		if (root.getColumns().isEmpty()) {
			// We only want the leaves that are editable.
			if (root.isEditable()) {
				columns.add(root);
			}
			return columns;
		} else {
			for (TableColumn<S, ?> column : root.getColumns()) {
				columns.addAll(getLeaves(column));
			}
			return columns;
		}
	}
 
 	public void commit(String arg0){
 		if(arg0 != null && !arg0.isEmpty() && InputCheck.isValidTableauInput(arg0)){
     		super.commitEdit(arg0);
     		
     	} else {
     		//MainBasicWindow.showDialog("Input Error", Fascade.getInstance().getMessage("gui.inputNumberError"), "Error");
     		super.commitEdit("0");
     	}  
 	}
 
 	public static void setKeyLastInput(String keyLastInputIn, KeyEvent keyLastEventIn) {
 		keyLastInput = keyLastInputIn;
 		keyLastEvent = keyLastEventIn;
 	}
 	
 	
 
 	
 	
 
     
 
 }
