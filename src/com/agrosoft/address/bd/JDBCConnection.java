package com.agrosoft.address.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.agrosoft.address.exceptions.DAOException;

/**
 * @author A459079
 *
 */
public class JDBCConnection {
	private static JDBCConnection instance = new JDBCConnection();
	private Connection connection;

	private JDBCConnection() {}

	public Connection open () throws DAOException {
		if (connection != null) return connection;
	
		try {
			Class.forName ("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbaddressapp?useLegacyDatetimeCode=false;serverTimezone=UTC;useSSL=false", "root", "");
			
			return connection;
		} catch (ClassNotFoundException e) {
			throw new DAOException("ERROR:" + e.getClass() + ":Le driver JDBC est introuvable !");
		} catch (SQLException sqle) {
			throw new DAOException("ERROR:" + sqle.getClass() + ":" + sqle.getMessage());
		}
	}
	
	public void close () throws DAOException {
		try {
			if (connection != null) {
				connection.close();
				// - ibompo
				connection = null;
			}
		} catch (SQLException e) {
			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
	}

	public static JDBCConnection getInstance() {
		return instance;
	} 
}
