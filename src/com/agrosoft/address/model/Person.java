package com.agrosoft.address.model;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.agrosoft.address.util.DateUtil;
import com.agrosoft.address.util.LocalDateAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Person {

	private int id;
	private static int counter;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty street;
	private IntegerProperty postalCode;
	private StringProperty city;
	private ObjectProperty<LocalDate> birthday;

	/**
	 * Default constructor.
	 */
	public Person() {
		this(null, null);		
	}
	
	/**
	 * Constructor with some initial data.
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		
		// Some initial dummy data, just for convenient testing.
		this.street = new SimpleStringProperty("some street");
		this.postalCode = new SimpleIntegerProperty(1234);
		this.city = new SimpleStringProperty("some city");
		
		this.birthday = new SimpleObjectProperty<>();
		this.setBirthday(DateUtil.parse("21.02.1999"));

		this.setId(++counter);
	}
		
	public Person(int id, String firstName, String lastName, String street,
			int postalCode, String city, String birthday) {
		this();
		this.setId (id);
		this.firstName.set(firstName);
		this.lastName.set(lastName);
		this.street.set(street);
		this.setPostalCode (postalCode);
		this.city.set(city);		
		this.setBirthday(DateUtil.parse(birthday));
	}

	public Person(int id) {
		this.setId(id);
	}

	public void setId(int id) {
		if (id > 0)
			this.id = id;
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	
	public StringProperty lastNameProperty() {
		return lastName;
	}

	public String getStreet() {
		return street.get();
	}

	public void setStreet(String street) {
		this.street.set(street);
	}
	
	public StringProperty streetProperty() {
		return street;
	}

	public int getPostalCode() {
		return postalCode.get();
	}

	public void setPostalCode(int postalCode) {
		this.postalCode.set(postalCode);
	}
	
	public IntegerProperty postalCodeProperty() {
		return postalCode;
	}

	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {
		this.city.set(city);
	}
	
	public StringProperty cityProperty() {
		return city;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getBirthday() {
		return birthday.get();
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}
	
	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}
	
	@Override
	public boolean equals(Object obj) {		
		if (obj instanceof Person) {
			if (((Person)obj).id == this.id) return true;
			else return false;
		}
		
		return false;
	}

	public int getId() {
		return id;
	}
	
	public static void copy(Person oldPerson, Person newPerson) {
		oldPerson.setFirstName(newPerson.getFirstName());
		oldPerson.setLastName(newPerson.getLastName());
		oldPerson.setStreet(newPerson.getStreet());
		oldPerson.setCity(newPerson.getCity());
		oldPerson.setPostalCode(newPerson.getPostalCode());
		oldPerson.setBirthday(newPerson.getBirthday());
	}
}