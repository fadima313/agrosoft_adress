package com.agrosoft.address.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableListPerson {	
	private ObservableList<Person> personData = FXCollections.observableArrayList();

	/**
	 * Returns the data as an observable list of Persons. 
	 * @return
	 */
	public ObservableList<Person> getPersonData() {
		return personData;
	}

	public void addPerson(Person person) {
		personData.add(person);
	}
	
	public void init() {
		// Add some sample dataDao.getInstance().addPerson(new Person("Hans", "Muster"));
		addPerson(new Person("Ruth", "Mueller"));
		addPerson(new Person("Heinz", "Kurz"));
		addPerson(new Person("Cornelia", "Meier"));
		addPerson(new Person("Werner", "Meyer"));
		addPerson(new Person("Lydia", "Kunz"));
		addPerson(new Person("Anna", "Best"));
		addPerson(new Person("Stefan", "Meier"));
		addPerson(new Person("Martin", "Mueller"));
	}
	
	public void deletePerson(Person person) {
		personData.remove(person);
	}

	public void updatePerson(Person newPerson) {
		int index = personData.lastIndexOf(newPerson);
		if (index >= 0) {
			Person.copy(personData.get(index), newPerson);			
		}
	}

	public void deletePerson(int id) {
		Predicate<Person> predicate = person -> (person.getId() == id);
		Optional<Person> person = personData.stream().filter(predicate).findFirst();
		if (person != null) {
			personData.remove(person.get());
		}
	}
	
	public void setPersonData (List<Person> persons) {
		this.personData.addAll(persons);
	}
}