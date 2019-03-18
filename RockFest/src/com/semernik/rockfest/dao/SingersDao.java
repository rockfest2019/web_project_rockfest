package com.semernik.rockfest.dao;

import java.util.Collection;
import java.util.Optional;

import com.semernik.rockfest.entity.Singer;


// TODO: Auto-generated Javadoc
/**
 * The Interface SingersDao.
 */
public interface SingersDao extends Dao {

	/**
	 * Save singer.
	 *
	 * @param singer the singer
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveSinger(Singer singer) throws DaoException;

	/**
	 * Update singer description.
	 *
	 * @param singerId the singer id
	 * @param description the description
	 * @param authorId the author id
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean updateSingerDescription(long singerId, String description, long authorId) throws DaoException;

	/**
	 * Find all singers.
	 *
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Singer> findAllSingers() throws DaoException;

	/**
	 * Find singer by id.
	 *
	 * @param singerId the singer id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Singer> findSingerById(long singerId) throws DaoException;

	boolean changeSingerTitle(long singerId, String newTitle) throws DaoException;
}
