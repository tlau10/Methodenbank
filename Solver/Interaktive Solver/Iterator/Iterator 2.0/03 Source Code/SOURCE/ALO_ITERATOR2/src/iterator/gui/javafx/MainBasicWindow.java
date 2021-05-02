package iterator.gui.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainBasicWindow extends Application {
	
	private String nameOfTableau = "Default";
	private int countIterations = 0;
	private static Stage mainStage = null;
	static Stage window;
	@Override
	public void start(Stage primaryStage) {

		window = primaryStage;
		
		primaryStage.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		
		Parent root;
		BasicWindowController basicWindowController;
		try {
			// Connection to fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("BasicWindow.fxml"));
			
			root = (Parent) loader.load();
			// Connection to Controller
			basicWindowController = (BasicWindowController)loader.getController();
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
	
                        
		// Stage = TopLevel-Frame
		// Scene = Content of FXML-File
		Scene scene = new Scene(root);
				
		// Maintain Fxml to Frame (primaryStage)
		primaryStage.setScene(scene);
		primaryStage.setTitle("Operations Research - Iterator 2.0");
		primaryStage.setWidth(1000);
		primaryStage.setHeight(650);
		mainStage = primaryStage;
		
		//Set Icon
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("img_calculator.png")));
		
		
		primaryStage.show();

		basicWindowController.setPrimaryStage(primaryStage);
		
		
		// create Start-Tableau 
		basicWindowController.createTableau();

		
	}
	
	
	public static void closeProgram() {
		 Boolean answer = BasicWindowController.display("WARNUNG!!", "Wollen Sie das Programm wirklich beenden?");
		 
		if(answer)
			 window.close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	

	public String getNameOfTableau() {
		return nameOfTableau;
	}
	public void setNameOfTableau(String nameOfTableau) {
		this.nameOfTableau = nameOfTableau;
	}

	public int getCountIterations() {
		return countIterations;
	}

	public void setCountIterations(int countIterations) {
		this.countIterations = countIterations;
	}
	
	public static void showDialog(String title, String message, String info){
		Dialogs.showInformationDialog(mainStage ,message,title,info);
	}

	
}
