package com.semernik.rockfest.connection;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

 // TODO: Auto-generated Javadoc
/**
  * The Class InitPoolParams.
  */
 class InitPoolParams {

	 /** The logger. */
 	private static Logger logger = LogManager.getLogger();

 	/** The Constant DEFAULT_PARAMS_PATH. */
 	private final static String DEFAULT_PARAMS_PATH = "connectionPool";

	/** The url. */
	private String url;

	/** The user. */
	private String user;

	/** The password. */
	private String password;

	/** The min connections count. */
	private int minConnectionsCount;

	/** The initial connections count. */
	private int initialConnectionsCount;

	/** The max connections count. */
	private int maxConnectionsCount;

	/** The pool check interval. */
	private long poolCheckInterval;

	/** The connections work load treshold. */
	private int connectionsWorkLoadTreshold;

	/** The connection occupy time treshold. */
	private long connectionOccupyTimeTreshold;

	/** The poll idle connection timeout. */
	private long pollIdleConnectionTimeout;


	/**
	 * Gets the inits the params.
	 *
	 * @return the inits the params
	 */
	static InitPoolParams getInitParams() {
		return getInitParams(DEFAULT_PARAMS_PATH);
	}

	/**
	 * Gets the inits the params.
	 *
	 * @param path the path
	 * @return the inits the params
	 */
	static InitPoolParams getInitParams(String path) {
		InitPoolParams params;
		try {
			ResourceBundle config = ResourceBundle.getBundle(DEFAULT_PARAMS_PATH);
			params = new InitPoolParams();
			params.url = config.getString("url");
			System.out.println(params.url);
			params.user = config.getString("user");
			params.password = config.getString("password");
			params.minConnectionsCount = Integer.parseInt(config.getString("minConnectionsCount"));
			params.initialConnectionsCount = Integer.parseInt(config.getString("initialConnectionsCount"));
			params.maxConnectionsCount = Integer.parseInt(config.getString("maxConnectionsCount"));
			params.poolCheckInterval = Long.parseLong(config.getString("poolCheckInterval"));
			params.connectionsWorkLoadTreshold = Integer.parseInt(config.getString("connectionsWorkLoadTreshold"));
			params.connectionOccupyTimeTreshold = Long.parseLong(config.getString("connectionOccupyTimeTreshold"));
			params.pollIdleConnectionTimeout = Long.parseLong(config.getString("pollIdleConnectionTimeout"));
		} catch (MissingResourceException e) {
			logger.error("MissingResourceException in ConnectionPool. Pool will be configured by default settings", e);
			params = DefaultInitializer.getDefaultParams();
		}
		return params;
	}


	/**
	 * The Class DefaultInitializer.
	 */
	private static class DefaultInitializer {

		/** The url. */
		private static String url = "jdbc:mysql://localhost:3306/testFestival?useSSL=false";

		/** The user. */
		private static String user = "root";

		/** The password. */
		private static String password = "application";

		/** The min connections count. */
		private static int minConnectionsCount = 1;

		/** The initial connections count. */
		private static int initialConnectionsCount = 2;

		/** The max connections count. */
		private static int maxConnectionsCount = 5;

		/** The pool check interval. */
		private static long poolCheckInterval = 1000;

		/** The connections work load treshold. */
		private static int connectionsWorkLoadTreshold = 5;

		/** The connection occupy time treshold. */
		private static long connectionOccupyTimeTreshold = 2000;

		/** The poll idle connection timeout. */
		private static long pollIdleConnectionTimeout = 100;

		/**
		 * Gets the default params.
		 *
		 * @return the default params
		 */
		private static InitPoolParams getDefaultParams(){
			InitPoolParams params = new InitPoolParams();
			params.url = url;
			params.user = user;
			params.password = password;
			params.minConnectionsCount = minConnectionsCount;
			params.initialConnectionsCount = initialConnectionsCount;
			params.maxConnectionsCount = maxConnectionsCount;
			params.poolCheckInterval = poolCheckInterval;
			params.connectionsWorkLoadTreshold = connectionsWorkLoadTreshold;
			params.connectionOccupyTimeTreshold = connectionOccupyTimeTreshold;
			params.pollIdleConnectionTimeout = pollIdleConnectionTimeout;
			return params;
		}
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	String getUrl() {
		return url;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	String getUser() {
		return user;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	String getPassword() {
		return password;
	}

	/**
	 * Gets the min connections count.
	 *
	 * @return the min connections count
	 */
	int getMinConnectionsCount() {
		return minConnectionsCount;
	}

	/**
	 * Gets the initial connections count.
	 *
	 * @return the initial connections count
	 */
	int getInitialConnectionsCount() {
		return initialConnectionsCount;
	}

	/**
	 * Gets the max connections count.
	 *
	 * @return the max connections count
	 */
	int getMaxConnectionsCount() {
		return maxConnectionsCount;
	}

	/**
	 * Gets the pool check interval.
	 *
	 * @return the pool check interval
	 */
	long getPoolCheckInterval() {
		return poolCheckInterval;
	}

	/**
	 * Gets the connections work load treshold.
	 *
	 * @return the connections work load treshold
	 */
	int getConnectionsWorkLoadTreshold() {
		return connectionsWorkLoadTreshold;
	}

	/**
	 * Gets the connection occupy time treshold.
	 *
	 * @return the connection occupy time treshold
	 */
	long getConnectionOccupyTimeTreshold() {
		return connectionOccupyTimeTreshold;
	}

	/**
	 * Gets the poll idle connection timeout.
	 *
	 * @return the poll idle connection timeout
	 */
	long getPollIdleConnectionTimeout() {
		return pollIdleConnectionTimeout;
	}

}
