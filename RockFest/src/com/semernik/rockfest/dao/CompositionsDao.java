package com.semernik.rockfest.dao;

import java.util.Collection;
import java.util.Optional;

import com.semernik.rockfest.entity.Composition;



// TODO: Auto-generated Javadoc
/**
 * The Interface CompositionsDao.
 */
public interface CompositionsDao extends Dao {

	/**
	 * Find composition by id.
	 *
	 * @param compositionId the composition id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Composition> findCompositionById(long compositionId) throws DaoException;

	/**
	 * Find all compositions.
	 *
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Composition> findAllCompositions() throws DaoException;

	/**
	 * Save composition.
	 *
	 * @param composition the composition
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveComposition (Composition composition) throws DaoException;

	/**
	 * Change composition genres.
	 *
	 * @param compositionId the composition id
	 * @param genreIds the genre ids
	 * @param authorId the author id
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean changeCompositionGenres(long compositionId, Collection<Long> genreIds, long authorId) throws DaoException;

	/**
	 * Change composition year.
	 *
	 * @param compositionId the composition id
	 * @param year the year
	 * @param authorId the author id
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean changeCompositionYear(long compositionId, String year, long authorId) throws DaoException;

	/**
	 * Find singer compositions.
	 *
	 * @param singerId the singer id
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Composition> findSingerCompositions(long singerId) throws DaoException;

	/**
	 * Find genre compositions.
	 *
	 * @param singerId the singer id
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Composition> findGenreCompositions(long singerId) throws DaoException;

	boolean changeCompositionTitle(long compositionId, String newTitle) throws DaoException;

}
