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
	 * Find specific part of singers.
	 * @param position the position in singers from which selection begin
	 * @param elementsCount the elements count from the position
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Singer> findSingers(int position, int elementsCount) throws DaoException;

	/**
	 * Find singer by id.
	 *
	 * @param singerId the singer id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Singer> findSingerById(long singerId) throws DaoException;

	/**
	 * Change singer title.
	 *
	 * @param singerId the singer id
	 * @param newTitle the new singer title
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	boolean changeSingerTitle(long singerId, String newTitle) throws DaoException;
}
