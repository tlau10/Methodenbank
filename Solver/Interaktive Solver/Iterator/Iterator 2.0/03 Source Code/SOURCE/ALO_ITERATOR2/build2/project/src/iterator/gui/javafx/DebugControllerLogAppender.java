package iterator.gui.javafx;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class DebugControllerLogAppender extends WriterAppender{
	
	static private TextArea textArea = new TextArea();
	static private String lastLoggs;
	
	/** Set the target JTextArea for the logging information to appear. */
	static public void setTextArea(TextArea textArea) {
		DebugControllerLogAppender.textArea = textArea;
		DebugControllerLogAppender.textArea.clear();
		DebugControllerLogAppender.textArea.appendText(lastLoggs);
	}
	
	public void append(LoggingEvent loggingEvent) {
		if(loggingEvent != null){
		
			final String message = this.layout.format(loggingEvent);
	
			Platform.runLater(new Runnable() {
				public void run() {
					textArea.appendText(message);
					lastLoggs += message;
				}
			});
		}
	}
}
