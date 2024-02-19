package com.agrosoft.address.view;
import java.io.IOException;

import com.agrosoft.address.model.Person;
import com.agrosoft.address.service.impl.Service;
import com.agrosoft.address.util.DateUtil;
import com.agrosoft.address.util.Utilitaire;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    private PersonEditDialogController personEditDialogController;
    private Stage primaryStage;
    
    public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        
        addPersonObservableListToTheTable();
    }
    
    private void addPersonObservableListToTheTable() {
    	// Add observable list data to the table
    	
    	ObservableList<Person> persons = Service.getInstance().loadPersonObservableList();
    	personTable.setItems(persons);
    	
		if (persons.size() > 0)
        	personTable.getSelectionModel().select(0);						
	}

	/**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());

            // TODO: We need a way to convert the birthday into a String!
            birthdayLabel.setText(DateUtil.format(person.getBirthday())); // Done!
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
    	int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // personTable.getItems().remove(selectedIndex);
            Service.getInstance().deletePerson(personTable.getSelectionModel().getSelectedItem());
        } else {
            // Nothing selected.
        	Utilitaire.alert(AlertType.WARNING, primaryStage,
        			"No Selection", 
        			"No Person Selected", 
        			"Please select a person in the table.");
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonOverviewController.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            personEditDialogController = loader.getController();
            personEditDialogController.setDialogStage(dialogStage);
            personEditDialogController.setPerson(person);
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return personEditDialogController.isOkClicked();
        } catch (IOException e) {
        	// TODO
        }
        
        return false;
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = showPersonEditDialog(tempPerson);
        if (okClicked) {
        	Service.getInstance().createPerson(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();        
                
        if (selectedPerson != null) {
            boolean okClicked = showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
                Service.getInstance().updatePerson(selectedPerson);
            }
        } else {
            // Nothing selected.
        	Utilitaire.alert(AlertType.WARNING, primaryStage,
        			"No Selection", 
        			"No Person Selected", 
        			"Please select a person in the table.");
        }
    }
}