package com.semernik.rockfest.dao;

import java.util.Collection;

import com.semernik.rockfest.entity.Composition;
import com.semernik.rockfest.entity.Entity;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Singer;

// TODO: Auto-generated Javadoc
/**
 * The Interface SearchDao.
 */
public interface SearchDao  extends Dao{

	/**
	 * Entities search.
	 *
	 * @param pattern the pattern
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Entity> entitiesSearch(String pattern) throws DaoException;

	/**
	 * Compositions search.
	 *
	 * @param pattern the pattern
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Composition> compositionsSearch(String pattern) throws DaoException;

	/**
	 * Genres search.
	 *
	 * @param pattern the pattern
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Genre> genresSearch(String pattern) throws DaoException;

	/**
	 * Singers search.
	 *
	 * @param pattern the pattern
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Singer> singersSearch(String pattern) throws DaoException;

}
