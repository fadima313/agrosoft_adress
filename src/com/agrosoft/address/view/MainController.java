package com.agrosoft.address.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController {
	private Stage primaryStage;
    private BorderPane rootLayout;

	private PersonOverviewController personOverviewController;	
	private RootLayoutController rootLayoutController;	

	/**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.    
        	FXMLLoader loader = new FXMLLoader();
        	loader.setLocation(MainController.class.getResource("PersonOverview.fxml"));
        	AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        
            // Give the controller access to the main app.
            personOverviewController = loader.getController();
            personOverviewController.setPrimaryStage(primaryStage);
        } catch (IOException e) {
        	// TODO
        }
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout(Stage primaryStage) {
        try {
        	this.primaryStage = primaryStage;
        
        	// Load person overview.
        	FXMLLoader loader = new FXMLLoader ();
        	loader.setLocation(MainController.class.getResource("RootLayout.fxml"));
        	rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            this.primaryStage.setScene(scene);

            // Give the controller access to the main app.
            rootLayoutController = loader.getController();       
            rootLayoutController.setPrimaryStage(primaryStage);

            this.primaryStage.show();
            
        } catch (IOException e) {
        	// TODO
        }
    }
}