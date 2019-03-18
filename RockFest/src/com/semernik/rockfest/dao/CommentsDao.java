package com.semernik.rockfest.dao;

import java.util.Collection;

import com.semernik.rockfest.entity.Comment;



// TODO: Auto-generated Javadoc
/**
 * The Interface CommentsDao.
 */
public interface CommentsDao extends Dao{

	/**
	 * Find composition comments by composition id.
	 *
	 * @param compositionId the composition id
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection <Comment> findCompositionCommentsByCompositionId(long compositionId) throws DaoException;

	/**
	 * Find singer comments by singer id.
	 *
	 * @param singerId the singer id
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection <Comment> findSingerCommentsBySingerId(long singerId) throws DaoException;

	/**
	 * Find genre comments by genre id.
	 *
	 * @param genreId the genre id
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection <Comment> findGenreCommentsByGenreId(long genreId) throws DaoException;

	/**
	 * Save composition comment.
	 *
	 * @param comment the comment
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveCompositionComment (Comment comment) throws DaoException;

	/**
	 * Save singer comment.
	 *
	 * @param comment the comment
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveSingerComment (Comment comment) throws DaoException;

	/**
	 * Save genre comment.
	 *
	 * @param comment the comment
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveGenreComment (Comment comment) throws DaoException;

	boolean deleteCompositionComment(long commentId) throws DaoException;

	boolean deleteSingerComment(long commentId) throws DaoException;

	boolean deleteGenreComment(long commentId) throws DaoException;

}
