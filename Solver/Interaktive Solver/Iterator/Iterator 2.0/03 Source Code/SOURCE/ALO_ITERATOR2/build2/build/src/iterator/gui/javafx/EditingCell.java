package iterator.gui.javafx;



import com.sun.media.jfxmedia.logging.Logger;

import iterator.Fascade;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;

import javafx.scene.control.Dialogs;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.beans.value.ChangeListener;

public class EditingCell<S> extends TableCell<S , String>{
	
	private TextField textField;
	private static TextField textFieldStatic = null;

	
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
        
        if(keyLastEvent != null && keyLastEvent.getCode() == KeyCode.DELETE){
        	
        	textField.setText("0");
        	commit("0");
        	
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
        });
        

       


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
