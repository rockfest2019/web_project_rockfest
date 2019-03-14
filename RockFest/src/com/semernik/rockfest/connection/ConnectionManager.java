package com.semernik.rockfest.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionManager.
 */
class ConnectionManager {

	/** The url. */
	private String url;

	/** The user. */
	private String user;

	/** The password. */
	private String password;

	/**
	 * Gets the single instance of ConnectionManager.
	 *
	 * @param url the url
	 * @param user the user
	 * @param password the password
	 * @return single instance of ConnectionManager
	 */
	static ConnectionManager getInstance(String url, String user, String password){
		return new ConnectionManager(url, user, password);
	}

	/**
	 * Instantiates a new connection manager.
	 *
	 * @param url the url
	 * @param user the user
	 * @param password the password
	 */
	private ConnectionManager(String url, String user, String password){
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.url = url;
		this.user = user;
		this.password = password;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

}
