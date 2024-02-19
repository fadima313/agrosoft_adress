package com.agrosoft.address.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.agrosoft.address.dao.impl.Dao;
import com.agrosoft.address.exceptions.DAOException;
import com.agrosoft.address.model.Person;
import com.agrosoft.address.service.IService;
import com.agrosoft.address.util.FilePersonManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// Singleton class
public class Service implements IService {
	private static IService instance = new Service();
	
	private Service() {}
	
	public static IService getInstance() {
		return instance;
	}

	@Override
	public void createEmptyAdressBook() {
		Service.getInstance().clearPersonList();
        FilePersonManager.setPersonFilePath(null);
	}

	@Override
	public void selectAnAddressBookToLoad(Stage primaryStage) {
		FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            FilePersonManager.loadPersonDataFromFile(file);
        }		
	}

	@Override
	public boolean saveCurrentOpenedPersonFile() {
		File personFile = FilePersonManager.getPersonFilePath();
        if (personFile != null) {
        	FilePersonManager.savePersonDataToFile(personFile);
        	return true;
        }
        
        return false;
	}

	@Override
	public void selectPersonFileToSaveAs(Stage primaryStage) {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(primaryStage);

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			FilePersonManager.savePersonDataToFile(file);
		}
	}

	@Override
	public List<Person> loadPersonList() {
		try {
			return Dao.getInstance().list();
		} catch (DAOException e) {
			// TODO
		}

		return new ArrayList<Person>();
	}

	@Override
	public void clearPersonList() {
		getCurrentPersonObservableList().clear();
	}

	@Override
	public void setPersonList(List<Person> persons) {		
		clearPersonList();
		getCurrentPersonObservableList().addAll(persons);	
	}

	@Override
	public void deletePerson(Person person) {
		try {
			Dao.getInstance().delete(person.getId());
		} catch (DAOException e) {
			// TODO 
		}
	}

	@Override
	public void createPerson(Person person) {
    	try {
			Dao.getInstance().create(person);
		} catch (DAOException e) {
			// TODO
		}
	}

	@Override
	public void updatePerson(Person person) {
		try {
			Dao.getInstance().update(person);
		} catch (DAOException e) {
			// TODO
		}
	}
	
	@Override
	public String getTheDaoImplementationClassname () {
		try {
			return Dao.getInstance().getClass().getSimpleName();
		} catch (Exception e) {}
				
		return null;
	}

	@Override
	public ObservableList<Person> loadPersonObservableList() {
		try {
			Dao.getInstance().list();
			return Dao.getInstance().listObservable();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
		}
		
		return FXCollections.observableArrayList();
	}
	
	@Override
	public ObservableList<Person> getCurrentPersonObservableList() {
		try {
			return Dao.getInstance().listObservable();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
		}
		
		return FXCollections.observableArrayList();
	}
}
