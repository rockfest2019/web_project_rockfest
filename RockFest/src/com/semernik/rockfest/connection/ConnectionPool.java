package com.semernik.rockfest.connection;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionPool.
 */
public class ConnectionPool implements Closeable {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static ConnectionPool instance;

	/** The free connections. */
	private BlockingQueue<ProxyConnection> freeConnections;

	/** The occupied connections. */
	private ArrayDeque<ProxyConnection> occupiedConnections;

	/** The manager. */
	private ConnectionManager manager;

	/** The occupied connections lock. */
	private Lock occupiedConnectionsLock;

	/** The min connection count. */
	private int minConnectionCount;

	/** The current connection count. */
	private int currentConnectionCount;

	/** The max connection count. */
	private int maxConnectionCount;

	/** The pool checker. */
	private Timer poolChecker;

	/** The pool check interval. */
	private long poolCheckInterval;



	/**
	 * Gets the single instance of ConnectionPool.
	 *
	 * @return single instance of ConnectionPool
	 */
	public static ConnectionPool getInstance(){
		ConnectionPool localInstance = instance;
		if (localInstance == null){
			synchronized(ConnectionPool.class){
				localInstance = instance;
				if (localInstance == null){
					localInstance = new ConnectionPool();
					instance = localInstance;
				}
			}
		}
		return localInstance;
	}

	/**
	 * Instantiates a new connection pool.
	 */
	private ConnectionPool(){
		freeConnections = new LinkedBlockingQueue<>();
		occupiedConnections = new ArrayDeque<>();
		occupiedConnectionsLock = new ReentrantLock();
		InitPoolParams params = InitPoolParams.getInitParams();
		manager = ConnectionManager.getInstance(params.getUrl(), params.getUser(), params.getPassword());
		minConnectionCount = params.getMinConnectionsCount();
		maxConnectionCount = params.getMaxConnectionsCount();
		initConnections(params.getInitialConnectionsCount());
		initTimer(params);
	}

	/**
	 * Inits the timer.
	 *
	 * @param params the params
	 */
	private void initTimer(InitPoolParams params) {
		poolChecker = new Timer(true);
		poolCheckInterval = params.getPoolCheckInterval();
		MaintenanceTask task = new MaintenanceTask(params.getConnectionsWorkLoadTreshold(),
				params.getConnectionOccupyTimeTreshold(), params.getPollIdleConnectionTimeout());
		poolChecker.scheduleAtFixedRate(task, poolCheckInterval, poolCheckInterval);

	}

	/**
	 * Inits the connections.
	 *
	 * @param connectionsCount the connections count
	 */
	private void initConnections(int connectionsCount) {
		Connection connection;
		currentConnectionCount = connectionsCount;
		for (int i=0;i<currentConnectionCount;i++){
			try {
				connection = manager.getConnection();
				freeConnections.add(new ProxyConnection(connection));
			} catch (SQLException e) {
				logger.error("Error while initiating connections", e);
			}
		}

	}

	/**
	 * Take connection.
	 *
	 * @return the connection
	 */
	public Connection takeConnection(){
		ProxyConnection connection = null;
		try {
			connection =  freeConnections.take();
			occupiedConnectionsLock.lock();
			connection.setStartUseTime(System.currentTimeMillis());
			occupiedConnections.add(connection);
		} catch (InterruptedException e) {
			logger.error("Thread interrupting while retriving connection from pool", e);
		} finally{
			occupiedConnectionsLock.unlock();
		}
		return connection;
	}

	/**
	 * Connection release.
	 *
	 * @param connection the connection
	 * @throws SQLException the SQL exception
	 */
	public void connectionRelease(ProxyConnection connection) throws SQLException{
		try {
			occupiedConnectionsLock.lock();
			boolean ourConnection = occupiedConnections.remove(connection);
			if (ourConnection){
				try {
					connection.setAutoCommit(true);
					freeConnections.put(connection);
				} catch (InterruptedException e) {
					logger.error("Thread interrupting while releasing connection to pool", e);
				}
			} else {							// if connection is not from our pool...
				connection.closeConnection();
			}
		} finally {
			occupiedConnectionsLock.unlock();
		}
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() {
		BlockingQueue<ProxyConnection> oldPool = freeConnections;
		freeConnections = new ClosedConnectionPool();
		oldPool.forEach(c -> {
			try {
				c.closeConnection();
			} catch (SQLException e) {
				logger.error("Failed to close connection " + c, e);
			}
		});
	}

	/**
	 * The Class MaintenanceTask.
	 */
	private class MaintenanceTask extends TimerTask {

		/** The connections lack counter. */
		private int connectionsLackCounter;				//look for addition connection necessity

		/** The connections over supply counter. */
		private int connectionsOverSupplyCounter;		// look for idle connections in pool

		/** The connections work load treshold. */
		private int connectionsWorkLoadTreshold;		// service pool workload value

		/** The connection occupy time treshold. */
		private long connectionOccupyTimeTreshold ;

		/** The poll idle connection timeout. */
		private long pollIdleConnectionTimeout;

		/** The time unit. */
		private TimeUnit timeUnit;

		/**
		 * Instantiates a new maintenance task.
		 *
		 * @param workLoadTreshold the work load treshold
		 * @param occupyTimeTreshold the occupy time treshold
		 * @param pollIdleConnectionTimeout the poll idle connection timeout
		 */
		private MaintenanceTask (int workLoadTreshold, long occupyTimeTreshold, long pollIdleConnectionTimeout){
			this.connectionsWorkLoadTreshold = workLoadTreshold;
			this.connectionOccupyTimeTreshold = occupyTimeTreshold;
			this.pollIdleConnectionTimeout = pollIdleConnectionTimeout;
			timeUnit = TimeUnit.MILLISECONDS;
		}


		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			lookForLostConnections();
			checkPoolWorkload();
		}

		/**
		 * Look for lost connections.
		 */
		private void lookForLostConnections() {
			try{
				occupiedConnectionsLock.lock();
				for (ProxyConnection connection : occupiedConnections){
					if (System.currentTimeMillis() - connection.getStartUseTime() > connectionOccupyTimeTreshold){
						logger.error("Connection didn't return to pool after workload treshold");
						occupiedConnections.remove(connection);
						closeOverOccupiedConnection(connection);
						try {
							Connection substitute = manager.getConnection();
							freeConnections.put(new ProxyConnection(substitute));
						} catch (SQLException e) {
							logger.error("Database access failure while getting new connection", e);
						} catch (InterruptedException e) {
							logger.error("Thread interrupting while putting new connection to pool", e);
						}
					}
				}
			} finally{
				occupiedConnectionsLock.unlock();
			}
		}

		/**
		 * Close over occupied connection.
		 *
		 * @param connection the connection
		 */
		private void closeOverOccupiedConnection(ProxyConnection connection) {
			try {
				if (! connection.getAutoCommit()){
					connection.rollback();
				}
			} catch (SQLException e) {
				logger.error("Database access failure in connection rollback", e);
			} finally{
				try {
					connection.closeConnection();
				} catch (SQLException e) {
					logger.error("Failed to close connection " + connection, e);
				}
			}
		}

		/**
		 * Check pool workload.
		 */
		private void checkPoolWorkload() {
			if (lackOfConnections() && currentConnectionCount < maxConnectionCount){
				addConnectionToPool();
			} else if (tooManyConnectionsInPool() && currentConnectionCount > minConnectionCount){
				removeConnectionFromPool();
			}
		}

		/**
		 * Lack of connections.
		 *
		 * @return true, if successful
		 */
		private boolean lackOfConnections() {
			boolean lack = false;
			if(freeConnections.isEmpty()){
				++connectionsLackCounter;
			} else {
				connectionsLackCounter = (connectionsLackCounter == 0) ? 0 : connectionsLackCounter-1;
			}
			if (connectionsLackCounter >= connectionsWorkLoadTreshold){
				lack = true;
				connectionsLackCounter = 0;
			}
			return lack;
		}

		/**
		 * Adds the connection to pool.
		 */
		private void addConnectionToPool() {
			try {
				Connection connection = manager.getConnection();
				freeConnections.put(new ProxyConnection(connection));
				currentConnectionCount++;
			} catch (SQLException e) {
				logger.error("Database access failure while getting new connection", e);
			} catch (InterruptedException e) {
				logger.error("Thread interrupting while putting new connection to pool", e);
			}
		}

		/**
		 * Too many connections in pool.
		 *
		 * @return true, if successful
		 */
		private boolean tooManyConnectionsInPool() {
			boolean overSupply = false;
			if (! freeConnections.isEmpty()){
				++connectionsOverSupplyCounter;
			} else {
				connectionsOverSupplyCounter = (connectionsOverSupplyCounter == 0) ? 0 : connectionsOverSupplyCounter-1;
			}
			if (connectionsOverSupplyCounter >= connectionsWorkLoadTreshold){
				overSupply = true;
				connectionsOverSupplyCounter = 0;
			}
			return overSupply;
		}

		/**
		 * Removes the connection from pool.
		 */
		private void removeConnectionFromPool() {
			ProxyConnection connection = null;
			try {
				connection = freeConnections.poll(pollIdleConnectionTimeout, timeUnit); // fix against
				if (connection != null){						// situation when freeConnections is empty and previously
					connection.closeConnection();				// taken connections won't be returned back in proper way
					currentConnectionCount--;
				}
			} catch (InterruptedException e) {
				logger.error("Thread interrupting while polling connection from pool", e);
			} catch (SQLException e) {
				logger.error("Failed to close connection " + connection, e);
			}
		}
	}

	/**
	 * The Class ClosedConnectionPool.
	 */
	private class ClosedConnectionPool extends LinkedBlockingQueue<ProxyConnection>{

		/* (non-Javadoc)
		 * @see java.util.concurrent.LinkedBlockingQueue#put(java.lang.Object)
		 */
		@Override
		public void put(ProxyConnection connection) throws InterruptedException{
			try {
				connection.closeConnection();
				super.put(connection);
			} catch (SQLException e) {
				logger.error("Failed to close connection " + connection, e);
			}
		}
	}


}
