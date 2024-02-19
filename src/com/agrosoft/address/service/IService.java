package com.agrosoft.address.service;

import java.util.List;

import com.agrosoft.address.exceptions.DAOException;
import com.agrosoft.address.model.Person;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

public interface IService {

	/**
	 * 
	 */
	public void createEmptyAdressBook();

	/**
	 * @param primaryStage
	 */
	public void selectAnAddressBookToLoad(Stage primaryStage);

	/**
	 * @return
	 */
	public boolean saveCurrentOpenedPersonFile();

	/**
	 * @param primaryStage
	 */
	public void selectPersonFileToSaveAs(Stage primaryStage);

	/**
	 * @return
	 */
	public List<Person> loadPersonList();

	/**
	 * 
	 */
	public void clearPersonList();

	/**
	 * @param persons
	 */
	public void setPersonList(List<Person> persons);

	/**
	 * @param person
	 */
	public void deletePerson(Person person);

	/**
	 * @param person
	 */
	public void updatePerson(Person person);

	/**
	 * @param person
	 */
	public void createPerson(Person person);

	/**
	 * @return
	 */
	public String getTheDaoImplementationClassname();

	/**
	 * @return
	 * @throws DAOException 
	 */
	public ObservableList<Person> loadPersonObservableList();

	/**
	 * @return
	 */
	public ObservableList<Person> getCurrentPersonObservableList();
}
