package com.agrosoft.address.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.agrosoft.address.bd.JDBCConnection;
import com.agrosoft.address.dao.IDao;
import com.agrosoft.address.exceptions.DAOException;
import com.agrosoft.address.model.ObservableListPerson;
import com.agrosoft.address.model.Person;
import com.agrosoft.address.util.DateUtil;

import javafx.collections.ObservableList;

public class PersonDaoJdbc implements IDao<Person> {
	private ObservableListPerson personData;
	
	public PersonDaoJdbc() {
		personData = new ObservableListPerson();
	}
	
	@Override
	public void create(Person entity) throws DAOException {		
		if (entity == null) throw new DAOException ("Entity must not be null !");		
		String query = "Insert Into T_Person (firstname,lastname,street,postalcode,city,birthday) "
				+ "values (?,?,?,?,?,?)";

		try {
		    Connection connection = JDBCConnection.getInstance().open();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
	
			preparedStatement.setString(1, entity.getFirstName());
			preparedStatement.setString(2, entity.getLastName());
			preparedStatement.setString(3, entity.getStreet());
			preparedStatement.setInt(4, entity.getPostalCode());
			preparedStatement.setString(5, entity.getCity());
			preparedStatement.setString(6, DateUtil.format(entity.getBirthday()));			
	
			preparedStatement.execute();
			JDBCConnection.getInstance().close();

			personData.addPerson(entity);
		} catch (SQLException e) {
			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}		
	}

	@Override
	public Person read(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Person obj) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Person> list() throws DAOException {
		List<Person> persons = new ArrayList<>();
		try {
		  Connection connection = JDBCConnection.getInstance().open();
	        String query = "Select * From T_Person";
	        PreparedStatement prepareStatement = connection.prepareStatement(query);
	        ResultSet resultSet = prepareStatement.executeQuery();

	        while (resultSet.next()) {
				int id = resultSet.getInt("id"); 
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String street = resultSet.getString("street"); 
				int postalcode = resultSet.getInt("postalcode"); 
				String city = resultSet.getString("city"); 
				String birthday = resultSet.getString("birthday"); 
				
				Person person = new Person (id, firstname, lastname, 
						street, postalcode, city, birthday);
				persons.add(person);
				personData.addPerson(person);
			}

			JDBCConnection.getInstance().close();
			return persons;
		} catch (SQLException e) {
			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		try {
			Connection connection = JDBCConnection.getInstance().open();
			String query = "Delete From T_Person Where id = ?";
			PreparedStatement preparedStatement;
			       
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();

			JDBCConnection.getInstance().close();
			
			personData.deletePerson(id);
		} catch (SQLException e) {
			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
	}

	@Override
	public ObservableList<Person> listObservable() throws DAOException {		
		return personData.getPersonData();
	}
}