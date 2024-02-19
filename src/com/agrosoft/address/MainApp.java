package com.agrosoft.address;

import com.agrosoft.address.service.impl.Service;
import com.agrosoft.address.view.MainController;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static final String APPLICATION_ICON_URL = "file:resources/images/address_book_32.png"; 
	private MainController mainController;	
    private Stage primaryStage;
	
	/**
	 * Constructor
	 */
	public MainApp() {
		mainController = new MainController();
	}

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp - working with a <" + Service.getInstance().getTheDaoImplementationClassname() + "> instance");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image(APPLICATION_ICON_URL));

        // Init main application controller
        mainController.initRootLayout(primaryStage);
        mainController.showPersonOverview();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
