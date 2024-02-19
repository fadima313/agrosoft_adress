package com.agrosoft.address.util;

import java.io.File;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.agrosoft.address.model.PersonListWrapper;
import com.agrosoft.address.service.impl.Service;

import javafx.scene.control.Alert.AlertType;

public class FilePersonManager {

	/**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public static File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(FilePersonManager.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
	/**
     * Saves the current person data to the specified file.
     * 
     * @param file
	 * @param personData 
     */
    public static void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(Service.getInstance().getCurrentPersonObservableList());

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
        	Utilitaire.alert(AlertType.WARNING, null,
    			"Error",
    			"Could not save data", 
    			"Could not save data to file:\n" + file.getPath());
        }
    }
    
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     * 
     * @param file
     */
    public static void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
            Service.getInstance().setPersonList(wrapper.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
        	Utilitaire.alert(AlertType.WARNING, null,
    			"Error", 
    			"Could not load data", 
    			"Could not load data from file:\n" + file.getPath());
    	}
    }
    
    public static void loadLastOpenedPersonFile () {
    	// Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     * @param primaryStage 
     */
    public static boolean setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(FilePersonManager.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            return false;
        } else {
            prefs.remove("filePath");            
            return true;
        }
    }
}