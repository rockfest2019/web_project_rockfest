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
		boolean saved = false;
		try {
			saved = trySaveGenre(genre, content);
		} catch (DaoException e) {
			logger.error("Genre is not saved", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_GENRE_ERROR, content);
			content.setUsingCurrentPage(true);
		}

		return saved;
	}

	private boolean trySaveGenre(Genre genre, SessionRequestContent content) throws DaoException{
		GenresDao dao = DaoFactory.getGenresDao();
		boolean saved = dao.saveGenre(genre);
		if (saved){
			content.addRequestAttribute(AttributeName.GENRE.toString(), genre);
			content.removeCurrentPageAttribute(ErrorMessage.SAVE_GENRE_ERROR.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_GENRE_ERROR, content);
			content.setUsingCurrentPage(true);
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
		int position = Integer.parseInt(content.getParameter(ParameterName.POSITION.toString()));
		int elementsCount = Integer.parseInt(content.getParameter(ParameterName.ELEMENTS_COUNT.toString()));
		boolean found = false;
		try {
			found = tryFindGenres(position, elementsCount, content);
		} catch (DaoException e) {
			logger.error("Genres are not reachable", e);
		}
		return found;
	}

	private boolean tryFindGenres(int position, int elementsCount, SessionRequestContent content) throws DaoException {
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = dao.findGenres(position, elementsCount);
		content.addRequestAttribute(AttributeName.GENRES.toString(), genres);
		content.addRequestAttribute(AttributeName.POSITION.toString(), position);
		content.addRequestAttribute(AttributeName.ELEMENTS_COUNT.toString(), elementsCount);
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
		boolean found = false;
		try {
			found = tryFindGenresByCompositionId(compositionId, content);
		} catch (DaoException e) {
			logger.error("Genres are not reachable", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMPOSITION_GENRES_ERROR, content);
		}
		return found;
	}

	private boolean tryFindGenresByCompositionId(Long compositionId, SessionRequestContent content) throws DaoException{
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = dao.findGenresByCompositionId(compositionId);
		content.addRequestAttribute(AttributeName.GENRES.toString(), genres);
		content.removeCurrentPageAttribute(ErrorMessage.COMPOSITION_GENRES_ERROR.toString());
		return true;
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
		boolean result = false;
		try {
			genre = dao.findGenreById(genreId);
			result = tryAddGenreToContent(genre, content);
		} catch (DaoException e) {
			logger.error("Genre is not reachable", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.GENRE_ERROR, content);
		}
		return result;
	}

	private boolean tryAddGenreToContent(Optional<Genre> optional, SessionRequestContent content) {
		boolean added = false;
		if (optional.isPresent()){
			Genre genre = optional.get();
			content.addRequestAttribute(AttributeName.GENRE.toString(), genre);
			content.removeCurrentPageAttribute(ErrorMessage.GENRE_ERROR.toString());
			addGenreComments(genre.getGenreId(), content);
			if (content.getSessionAttribute(AttributeName.USER_ID.toString()) != null){
				addUserGenreRatings(content);
			}
			added = true;
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.GENRE_ERROR, content);
		}
		return added;
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
			ErrorUtil.addErrorMessageToContent(ErrorMessage.COMMENTS_FAILURE, ErrorMessage.COMMENTS_FAILURE, content);
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
		boolean updated = false;
		try {
			updated = tryUpdateGenreDescription(genreId, newDescription, userId, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_DESCRIPTION_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		return updated;
	}

	private boolean tryUpdateGenreDescription(long genreId, String newDescription, long userId,
			SessionRequestContent content) throws DaoException{
		GenresDao dao = DaoFactory.getGenresDao();
		boolean updated = dao.updateGenreDescription(genreId, newDescription, userId);
		if (updated){
			Genre genre = (Genre) content.getCurrentPageAttribute(AttributeName.GENRE.toString());
			genre.setDescription(newDescription);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_DESCRIPTION_ERROR.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_DESCRIPTION_ERROR, content);
		}

		return false;
	}

	public boolean deleteGenreComment(SessionRequestContent content){
		long commentId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean deleted = false;
		try {
			deleted = tryDeleteGenreComment(commentId, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.DELETE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		content.setUsingCurrentPage(true);
		return deleted;
	}

	private boolean tryDeleteGenreComment(long commentId, SessionRequestContent content) throws DaoException{
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean deleted = dao.deleteGenreComment(commentId);
		if (deleted){
			Collection<Comment> comments = (Collection<Comment>) content.getCurrentPageAttribute(AttributeName.COMMENTS.toString());
			comments.removeIf(a -> a.getCommentId() == commentId);
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
		} else {
			ErrorUtil.addErrorMessageToContent(ErrorMessage.DELETE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		return deleted;
	}

	public boolean changeGenreTitle(SessionRequestContent content){
		long genreId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String newTitle = content.getParameter(ParameterName.TITLE.toString());
		boolean changed = false;
		try {
			changed = tryChangeGenreTitle(genreId, newTitle, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_TITLE_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		return changed;
	}

	private boolean tryChangeGenreTitle(long genreId, String newTitle, SessionRequestContent content) throws DaoException{
		GenresDao dao = DaoFactory.getGenresDao();
		boolean changed = dao.changeGenreTitle(genreId, newTitle);
		if (changed){
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_TITLE_ERROR.toString());
			Genre genre = (Genre) content.getCurrentPageAttribute(AttributeName.GENRE.toString());
			genre.setTitle(newTitle);
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_TITLE_ERROR, content);
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
		boolean saved = false;
		try {
			saved = trySaveGenreComment(comment, content);
		} catch (DaoException e) {
			logger.error("Comment is not saved ", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.SAVE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		content.setUsingCurrentPage(true);
		return saved;
	}

	private boolean trySaveGenreComment(Comment comment, SessionRequestContent content) throws DaoException {
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean saved = dao.saveGenreComment(comment);
		if (saved){
			content.getCurrentPageAttributes().remove(ErrorMessage.COMMENTS_FAILURE.toString());
			Collection<Comment> comments = (Collection<Comment>) content.getCurrentPageAttribute(AttributeName.COMMENTS.toString());
			comments.add(comment);
		} else {
			ErrorUtil.addErrorMessageToContent(ErrorMessage.SAVE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		return saved;
	}

}
