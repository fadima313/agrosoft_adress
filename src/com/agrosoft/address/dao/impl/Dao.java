package com.agrosoft.address.dao.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.agrosoft.address.dao.IDao;
import com.agrosoft.address.model.Person;
import com.agrosoft.address.util.Utilitaire;

import javafx.scene.control.Alert.AlertType;

// Singleton class
public class Dao {
	private static IDao<Person> dao = null;
	
	private Dao() {
		
		Properties properties = new Properties();
		try {			
			properties.load(new FileInputStream("application.properties"));
			String daoClassname = (String) properties.get("dao.classname");

			@SuppressWarnings("unchecked")
			Class<? extends IDao<Person>> daoClass = (Class<? extends IDao<Person>>) Class.forName(daoClassname);
			dao = daoClass.newInstance();
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ioe) {
			Utilitaire.alert(AlertType.ERROR, null, 
				"Error", ioe.getClass() + 
				"Error while loading Configuration file", 
				ioe.getMessage());
		}
	}

	public static IDao<Person> getInstance() {
		if (dao == null) new Dao();
		
		return dao;
	}
}