package com.agrosoft.address.dao;

import java.util.List;

import com.agrosoft.address.exceptions.DAOException;
import com.agrosoft.address.model.Person;

import javafx.collections.ObservableList;

public interface IDao<T> {
	
	/**
	 * @param obj
	 * @throws DAOException
	 */
	public void create (T obj) throws DAOException;
	
	/**
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public T read (int id) throws DAOException;
	
	/**
	 * @param obj
	 * @throws DAOException
	 */
	public void update (T obj) throws DAOException;
	
	/**
	 * @return
	 * @throws DAOException
	 */
	public List<T> list () throws DAOException;
	
	/**
	 * @param id
	 * @throws DAOException
	 */
	public void delete (int id) throws DAOException;

	/**
	 * @return 
	 * @throws DAOException 
	 * 
	 */
	public ObservableList<Person> listObservable() throws DAOException;
}
