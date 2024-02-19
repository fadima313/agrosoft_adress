package com.agrosoft.address.view;
import java.io.IOException;

import com.agrosoft.address.service.impl.Service;
import com.agrosoft.address.util.Utilitaire;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {
	private BirthdayStatisticsController birthdayStatisticsController;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
    
	/**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
    	Service.getInstance().createEmptyAdressBook();   	
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        Service.getInstance().selectAnAddressBookToLoad(primaryStage);
    }

    /**
     * Saves the file to the person file that is currently open. 
     * If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        if (false == Service.getInstance().saveCurrentOpenedPersonFile())
        	 handleSaveAs();
    }

    /**
     * Opens a FileChooser to let the user select a file 
     * to save to.
     */
    @FXML
    private void handleSaveAs() {
		Service.getInstance().selectPersonFileToSaveAs(primaryStage);
	}

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	Utilitaire.alert(AlertType.INFORMATION, 
    			null, 
    			"AddressApp", 
    			"About", 
    			"Author: Marco Jakob\n"
    			+ "Website: http://code.makery.ch\n"
    			+ "Reviewed architecture : Ghislain AKINOCHO");
    }

    /**
     * Opens the birthday statistics.
     */
    @FXML
    private void handleShowBirthdayStatistics() {
    	showBirthdayStatistics();
    }
    
    /**
     * Opens a dialog to show birthday statistics.
     */
    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootLayoutController.class.getResource("BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            birthdayStatisticsController = loader.getController();                        
            birthdayStatisticsController.setPersonData(Service.getInstance().loadPersonList());

            dialogStage.show();

        } catch (IOException e) {
        	// TODO            
        }
    }
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}