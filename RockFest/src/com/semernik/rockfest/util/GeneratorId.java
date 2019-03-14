package com.semernik.rockfest.util;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.IdsDao;
import com.semernik.rockfest.entity.LastIdsContainer;


// TODO: Auto-generated Javadoc
/**
 * The Class GeneratorId.
 */
public class GeneratorId {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static GeneratorId instance;

	/** The lock. */
	private static Lock lock = new ReentrantLock();

	/** The user id. */
	private AtomicLong userId;

	/** The composition id. */
	private AtomicLong compositionId;

	/** The singer id. */
	private AtomicLong singerId;

	/** The genre id. */
	private AtomicLong genreId;

	/** The composition comment id. */
	private AtomicLong compositionCommentId;

	/** The singer comment id. */
	private AtomicLong singerCommentId;

	/** The genre comment id. */
	private AtomicLong genreCommentId;

	/** The composition link id. */
	private AtomicLong compositionLinkId;

	/**
	 * Gets the single instance of GeneratorId.
	 *
	 * @return single instance of GeneratorId
	 */
	public static GeneratorId getInstance(){
		GeneratorId localInstance = instance;
		if (localInstance == null){
			try {
				lock.lock();
				localInstance = instance;
				if (localInstance == null) {
					localInstance = new GeneratorId();
					instance = localInstance;
				}
			} finally {
				lock.unlock();
			}
		}
		return localInstance;
	}

	/**
	 * Instantiates a new generator id.
	 */
	private GeneratorId(){
		loadLastIds();
	}

	/**
	 * Load last ids.
	 *
	 * @return true, if successful
	 */
	public boolean loadLastIds() {
		boolean isLoaded = false;
		IdsDao dao = DaoFactory.getIdsDao();
		Optional<LastIdsContainer> optional = Optional.empty();
		try {
			optional = dao.findLastIds();
		} catch (DaoException e) {
			logger.error("Failed to load ids",e);
		}
		if (optional.isPresent()){
			LastIdsContainer container = optional.get();
			userId = new AtomicLong(container.getUserId());
			compositionId = new AtomicLong(container.getCompositionId());
			singerId = new AtomicLong(container.getSingerId());
			genreId = new AtomicLong(container.getGenreId());
			compositionCommentId = new AtomicLong(container.getCompositionCommentId());
			singerCommentId = new AtomicLong(container.getSingerCommentId());
			genreCommentId = new AtomicLong(container.getGenreCommentId());
			compositionLinkId = new AtomicLong(container.getCompositionLinkId());
			isLoaded = true;
		}
		return isLoaded;
	}

	/**
	 * Generate user id.
	 *
	 * @return the long
	 */
	public long generateUserId() {
		return userId.incrementAndGet();
	}

	/**
	 * Generate composition id.
	 *
	 * @return the long
	 */
	public long generateCompositionId() {
		return compositionId.incrementAndGet();
	}

	/**
	 * Generate singer id.
	 *
	 * @return the long
	 */
	public long generateSingerId() {
		return singerId.incrementAndGet();
	}

	/**
	 * Generate genre id.
	 *
	 * @return the long
	 */
	public long generateGenreId() {
		return genreId.incrementAndGet();
	}

	/**
	 * Generate composition comment id.
	 *
	 * @return the long
	 */
	public long generateCompositionCommentId() {
		return compositionCommentId.incrementAndGet();
	}

	/**
	 * Generate singer comment id.
	 *
	 * @return the long
	 */
	public long generateSingerCommentId() {
		return singerCommentId.incrementAndGet();
	}

	/**
	 * Generate genre comment id.
	 *
	 * @return the long
	 */
	public long generateGenreCommentId() {
		return genreCommentId.incrementAndGet();
	}

	/**
	 * Generate composition link id.
	 *
	 * @return the long
	 */
	public long generateCompositionLinkId() {
		return compositionLinkId.incrementAndGet();
	}


}
