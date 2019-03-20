package com.semernik.rockfest.service;

import java.util.Collection;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.CommentsDao;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.GenresDao;
import com.semernik.rockfest.entity.Comment;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ErrorMessage;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.EntityUtil;
import com.semernik.rockfest.util.ErrorUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class GenreService.
 */
public class GenreService {

	private static Logger logger = LogManager.getLogger();
	private static GenreService instance = new GenreService();


	public static GenreService getInstance(){
		return instance;
	}

	private GenreService () {}

	/**
	 * Save genre.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveGenre(SessionRequestContent content) {
		Genre genre = EntityUtil.getGenreFromContent(content);
		GenresDao dao = DaoFactory.getGenresDao();
		boolean saved = false;
		try {
			saved = dao.saveGenre(genre);
			content.removeCurrentPageAttribute(ErrorMessage.SAVE_GENRE_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Genre is not saved", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_GENRE_ERROR, content);
			content.setUsingCurrentPage(true);
		}
		if (saved){
			content.addRequestAttribute(AttributeName.GENRE.toString(), genre);
		}
		return saved;
	}



	/**
	 * Find genres.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenres(SessionRequestContent content){
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = null;
		boolean found = false;
		try {
			genres = dao.findAllGenres();
			found = true;
		} catch (DaoException e) {
			logger.error("Genres are not reachable", e);
		}
		if(found){
			content.addRequestAttribute(AttributeName.GENRES.toString(), genres);
		}
		return true;
	}

	/**
	 * Find genres by composition id.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenresByCompositionId(SessionRequestContent content){
		Long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = null;
		boolean found = false;
		try {
			genres = dao.findGenresByCompositionId(compositionId);
			content.addRequestAttribute(AttributeName.GENRES.toString(), genres);
			found = true;
			content.removeCurrentPageAttribute(ErrorMessage.COMPOSITION_GENRES_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Genres are not reachable", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMPOSITION_GENRES_ERROR, content);
		}
		return found;
	}

	/**
	 * Find genre by id.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenreById(SessionRequestContent content){
		Long genreId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		GenresDao dao = DaoFactory.getGenresDao();
		Optional<Genre> genre = Optional.empty();
		boolean found = false;
		try {
			genre = dao.findGenreById(genreId);
			if (genre.isPresent()){
				found = true;
				content.addRequestAttribute(AttributeName.GENRE.toString(), genre.get());
				content.removeCurrentPageAttribute(ErrorMessage.GENRE_ERROR.toString());
				addGenreComments(genreId, content);
			}
			if (content.getSessionAttribute(AttributeName.USER_ID.toString()) != null){
				addUserGenreRatings(content);
			}
		} catch (DaoException e) {
			logger.error("Genre is not reachable", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.GENRE_ERROR, content);
		}
		return found;
	}

	private void addGenreComments(long genreId, SessionRequestContent content) {
		CommentsDao commentsDao = DaoFactory.getCommentsDao();
		Collection<Comment> comments = null;
		try {
			comments = commentsDao.findGenreCommentsByGenreId(genreId);
			content.addRequestAttribute(AttributeName.COMMENTS.toString(), comments);
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
		} catch (DaoException e) {
			logger.error("Comments are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMMENTS_FAILURE, ErrorMessage.COMMENTS_FAILURE, content);
		}
	}

	/**
	 * Adds the user genre ratings.
	 *
	 * @param content the content
	 */
	private void addUserGenreRatings(SessionRequestContent content) {
		RatingService ratingService = RatingService.getInstance();
		if (!ratingService.findGenreUserRating(content)){
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.RATING_FAILURE, content);
		}
	}

	/**
	 * Update genre description.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean updateGenreDescription(SessionRequestContent content){
		long genreId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String newDescription = content.getParameter(ParameterName.DESCRIPTION.toString());
		long userId = (Long)content.getSessionAttribute(AttributeName.USER_ID.toString());
		GenresDao dao = DaoFactory.getGenresDao();
		boolean updated = false;
		try {
			updated = dao.updateGenreDescription(genreId, newDescription, userId);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_DESCRIPTION_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_DESCRIPTION_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		if (updated){
			Genre genre = (Genre) content.getCurrentPageAttribute(AttributeName.GENRE.toString());
			genre.setDescription(newDescription);
		}
		return updated;
	}

	public boolean deleteGenreComment(SessionRequestContent content){
		long commentId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean deleted = false;
		try {
			deleted = dao.deleteGenreComment(commentId);
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.DELETE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		content.setUsingCurrentPage(true);
		if (deleted){
			Collection<Comment> comments = (Collection<Comment>) content.getCurrentPageAttribute(AttributeName.COMMENTS.toString());
			comments.removeIf(a -> a.getCommentId() == commentId);
		}
		return deleted;
	}

	public boolean changeGenreTitle(SessionRequestContent content){
		long genreId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String newTitle = content.getParameter(ParameterName.TITLE.toString());
		GenresDao dao = DaoFactory.getGenresDao();
		boolean changed = false;
		try {
			changed = dao.changeGenreTitle(genreId, newTitle);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_TITLE_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_TITLE_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		if (changed){
			Genre genre = (Genre) content.getCurrentPageAttribute(AttributeName.GENRE.toString());
			genre.setTitle(newTitle);
		}
		return changed;
	}

	/**
	 * Save genre comment.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveGenreComment(SessionRequestContent content){
		Comment comment = EntityUtil.getEntityCommentFromContent(content);
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean saved = false;
		try {
			saved = dao.saveGenreComment(comment);
			content.getCurrentPageAttributes().remove(ErrorMessage.COMMENTS_FAILURE.toString());
		} catch (DaoException e) {
			logger.error("Comment is not saved ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		content.setUsingCurrentPage(true);
		if (saved){
			Collection<Comment> comments = (Collection<Comment>) content.getCurrentPageAttribute(AttributeName.COMMENTS.toString());
			comments.add(comment);
		}
		return saved;
	}

}
