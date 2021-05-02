package iterator.gui.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DebugController implements Initializable {

	@FXML
	private TextArea textarea;
	
	@FXML
	private RadioMenuItem debug;
	
	@FXML
	private RadioMenuItem info;
	
	@FXML
	private RadioMenuItem warn;
	
	@FXML
	private RadioMenuItem error;
	
	
	private static Logger logger = Logger.getLogger(DebugController.class);



	@Override
	public void initialize(URL loctaion, ResourceBundle resource) {

		DebugControllerLogAppender.setTextArea(this.textarea);
		
		
		
		debug.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Logger.getRootLogger().setLevel(Level.DEBUG);
			
				logger.info("Logging Level: DEBUG");
				
				error.setSelected(false);
				warn.setSelected(false);
				info.setSelected(false);
				
			}
		});
		
		error.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Logger.getRootLogger().setLevel(Level.ERROR);
				
				logger.info("Logging Level: ERROR");
				
				debug.setSelected(false);
				warn.setSelected(false);
				info.setSelected(false);
			}
		});
		
		info.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Logger.getRootLogger().setLevel(Level.INFO);
				
				logger.info("Logging Level: INFO");
				
				error.setSelected(false);
				warn.setSelected(false);
				debug.setSelected(false);
			}
			
		});
	
		warn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Logger.getRootLogger().setLevel(Level.WARN);
				
				logger.info("Logging Level: WARN");
				
				error.setSelected(false);
				debug.setSelected(false);
				info.setSelected(false);
			}
		});

	}
	

	public void setText(String text) {
		textarea.setText(text);
		
	}
	
	public TextArea getTextArea(){
		return this.textarea;
	}


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DebugController.logger = logger;
	}

}
