package view;


/**
 * <p>
 * Application: Ernaehrungsplaner 2.0
 * </p>
 * <p>
 * Class: ErrorMessages
 * </p>
 * <p>
 * Description: Zeigt alle Fehlermeldungen als Pop-up Fenster an
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Organisation: HTWG-Konstanz
 * </p>
 * 
 * @author Julien Hespeler, Dusan Spasic
 * @version 2.0
 */
public class ErrorMessages {
	private static MainFrame mainFrame = null;

	public static void setMainFrame(MainFrame mainFrame) {
		ErrorMessages.mainFrame = mainFrame;
	}

	public static void throwErrorMessage(String errorMessage) {
		ErrorMessages.mainFrame.showErrorMessage(errorMessage);
	}
}
