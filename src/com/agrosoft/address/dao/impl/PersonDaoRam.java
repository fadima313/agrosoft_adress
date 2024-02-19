package com.agrosoft.address.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.agrosoft.address.dao.IDao;
import com.agrosoft.address.exceptions.DAOException;
import com.agrosoft.address.model.ObservableListPerson;
import com.agrosoft.address.model.Person;

import javafx.collections.ObservableList;

public class PersonDaoRam implements IDao <Person> {
	private ObservableListPerson personData;
	
	/**
	 * The data as an observable list of Persons.
	 */
		
	public PersonDaoRam() {
		personData = new ObservableListPerson();
		personData.init();
	}
	
	@Override
	public void create(Person obj) throws DAOException {
		if (obj == null) throw new DAOException ("Entity must not be null !");
		
		personData.addPerson(obj);
	}

	@Override
	public Person read(int id) throws DAOException {
		return null;
	}

	@Override
	public void update(Person obj) throws DAOException {
		personData.updatePerson(obj);
	}

	@Override
	public List<Person> list() throws DAOException {
		List<Person> persons = new ArrayList<>();
		persons.addAll(personData.getPersonData());
		
		return persons;
	}

	@Override
	public void delete(int id) throws DAOException {
		personData.deletePerson(id);
	}

	@Override
	public ObservableList<Person> listObservable() {
		return personData.getPersonData();
	}
}
