package com.semernik.rockfest.dao;

import java.util.Collection;
import java.util.Optional;

import com.semernik.rockfest.entity.Genre;



// TODO: Auto-generated Javadoc
/**
 * The Interface GenresDao.
 */
public interface GenresDao extends Dao {

	/**
	 * Find genres by composition id.
	 *
	 * @param compositionId the composition id
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Genre> findGenresByCompositionId(long compositionId) throws DaoException;

	/**
	 * Save genre.
	 *
	 * @param genre the genre
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveGenre(Genre genre) throws DaoException;

	/**
	 * Update genre description.
	 *
	 * @param genreId the genre id
	 * @param description the description
	 * @param authorId the author id
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean updateGenreDescription(long genreId, String description, long authorId) throws DaoException;

	/**
	 * Find all genres.
	 *
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Genre> findAllGenres() throws DaoException;

	/**
	 * Find specific part of genres.
	 * @param position the position in genres from which selection begin
	 * @param elementsCount the elements count from the position
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Genre> findGenres(int position, int elementsCount) throws DaoException;

	/**
	 * Find genre by id.
	 *
	 * @param genreId the genre id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Genre> findGenreById(long genreId) throws DaoException;

	/**
	 * Change genre title.
	 *
	 * @param genreId the genre id
	 * @param newTitle the new genre title
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	boolean changeGenreTitle(long genreId, String newTitle) throws DaoException;

}
