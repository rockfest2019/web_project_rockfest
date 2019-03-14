package com.semernik.rockfest.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Interface Dao.
 */
public interface Dao {

	/** The logger. */
	static Logger logger = LogManager.getLogger();

	/**
	 * Close result set.
	 *
	 * @param set the set
	 */
	default void closeResultSet(ResultSet set){
		try {
			if (set != null){
				set.close();
			}
		} catch (SQLException e) {
			logger.error("Failed to close ResultSet " + set, e);
		}
	}

	/**
	 * Close statement.
	 *
	 * @param statement the statement
	 */
	default void closeStatement(Statement statement){
		try {
			if (statement != null){
				statement.close();
			}
		} catch (SQLException e) {
			logger.error("Failed to close Statement " + statement, e);
		}
	}

	/**
	 * Close connection.
	 *
	 * @param connection the connection
	 */
	default void closeConnection(Connection connection){
		try {
			if (connection != null){
				connection.setAutoCommit(true);
				connection.close();
			}
		} catch (SQLException e) {
			logger.error("Failed to close connection " + connection, e);
		}
	}

}
