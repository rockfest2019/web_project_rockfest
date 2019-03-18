package com.semernik.rockfest.service;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.container.ErrorMessagesContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.CommentsDao;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.GenresDao;
import com.semernik.rockfest.entity.Comment;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Genre.GenreBuilder;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.GeneratorId;


// TODO: Auto-generated Javadoc
/**
 * The Class GenreService.
 */
public class GenreService {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static GenreService instance = new GenreService();

	/**
	 * Gets the single instance of GenreService.
	 *
	 * @return single instance of GenreService
	 */
	public static GenreService getInstance(){
		return instance;
	}

	/**
	 * Instantiates a new genre service.
	 */
	private GenreService () {}

	/**
	 * Save genre.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveGenre(SessionRequestContent content) {
		Map<String, String[]> parameters = content.getRequestParameters();
		String genreTitle = (parameters.get(ParameterName.TITLE.toString()))[0];
		String description = (parameters.get(ParameterName.DESCRIPTION.toString()))[0];
		long authorId = (Long)content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		long descriptionEditorId = authorId;
		long genreId = GeneratorId.getInstance().generateGenreId();
		Date addingDate = new Date(System.currentTimeMillis());
		GenreBuilder builder = new GenreBuilder();
		Genre genre = builder.genreId(genreId)
				.title(genreTitle)
				.description(description)
				.addingDate(addingDate)
				.authorId(authorId)
				.descriptionEditorId(descriptionEditorId)
				.build();
		GenresDao dao = DaoFactory.getGenresDao();
		boolean saved = false;
		try {
			saved = dao.saveGenre(genre);
		} catch (DaoException e) {
			logger.error("Genre is not saved", e);
		}
		if (saved){
			Map <String, Object> attrs = content.getRequestAttributes();
			attrs.put(AttributeName.GENRE.toString(), genre);
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
		boolean found = true;
		try {
			genres = dao.findAllGenres();
		} catch (DaoException e) {
			found = false;
			logger.error("Genres are not reachable", e);
		}
		if(found){
			content.getRequestAttributes().put(AttributeName.GENRES.toString(), genres);
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
		Map<String, String[]> parameters = content.getRequestParameters();
		Long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = null;
		boolean found = false;
		try {
			genres = dao.findGenresByCompositionId(compositionId);
			content.getRequestAttributes().put(AttributeName.GENRES.toString(), genres);
			found = true;
		} catch (DaoException e) {
			logger.error("Genres are not reachable", e);
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
		Map<String, String[]> parameters = content.getRequestParameters();
		Long genreId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		GenresDao dao = DaoFactory.getGenresDao();
		Optional<Genre> genre = Optional.empty();
		boolean found = false;
		try {
			genre = dao.findGenreById(genreId);
			if (genre.isPresent()){
				found = true;
				content.getRequestAttributes().put(AttributeName.GENRE.toString(), genre.get());
				addGenreComments(genreId, content);
			}
			if (content.getSessionAttributes().get(AttributeName.USER_ID.toString()) != null){
				addUserGenreRatings(content);
			}
		} catch (DaoException e) {
			logger.error("Genre is not reachable", e);
		}
		return found;
	}

	private void addGenreComments(long genreId, SessionRequestContent content) {
		CommentsDao commentsDao = DaoFactory.getCommentsDao();
		Collection<Comment> comments = null;
		try {
			comments = commentsDao.findGenreCommentsByGenreId(genreId);
			content.getRequestAttributes().put(AttributeName.COMMENTS.toString(), comments);
		} catch (DaoException e) {
			String errorMessage = ErrorMessagesContainer.findMessage(AttributeName.COMMENTS_FAILURE.toString());
			content.getRequestAttributes().put(AttributeName.COMMENTS_FAILURE.toString(), errorMessage);
			logger.error("Comments are not reachable ", e);
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
			String key = AttributeName.RATING_FAILURE.toString();
			String message = ErrorMessagesContainer.findMessage(key);
			content.getRequestAttributes().put(key, message);
		}
	}

	/**
	 * Update genre description.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean updateGenreDescription(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long genreId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String newDescription = parameters.get(ParameterName.DESCRIPTION.toString())[0];
		Long userId = (Long)content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		GenresDao dao = DaoFactory.getGenresDao();
		boolean updated = false;
		try {
			updated = dao.updateGenreDescription(genreId, newDescription, userId);
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		content.setUsingCurrentPage(true);
		if (updated){
			Map<String, Object> currentPageAttributes = content.getCurrentPageAttributes();
			Genre genre = (Genre) currentPageAttributes.get(AttributeName.GENRE.toString());
			genre.setDescription(newDescription);
		}
		return updated;
	}

	public boolean deleteGenreComment(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long commentId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean deleted = false;
		try {
			deleted = dao.deleteGenreComment(commentId);
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		content.setUsingCurrentPage(true);
		if (deleted){
			Map<String, Object> currentPageAttributes = content.getCurrentPageAttributes();
			Collection<Comment> comments = (Collection<Comment>) currentPageAttributes.get(AttributeName.COMMENTS.toString());
			comments.removeIf(a -> a.getCommentId() == commentId);
		}
		return deleted;
	}

	public boolean changeGenreTitle(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long genreId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String newTitle = parameters.get(ParameterName.TITLE.toString())[0];
		GenresDao dao = DaoFactory.getGenresDao();
		boolean changed = false;
		try {
			changed = dao.changeGenreTitle(genreId, newTitle);
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		content.setUsingCurrentPage(true);
		if (changed){
			Map<String, Object> currentPageAttributes = content.getCurrentPageAttributes();
			Genre genre = (Genre) currentPageAttributes.get(AttributeName.GENRE.toString());
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
		Map<String, String[]> parameters = content.getRequestParameters();
		long commentId = GeneratorId.getInstance().generateGenreCommentId();
		long commentedEntityId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String commentContent = parameters.get(ParameterName.COMMENT_CONTENT.toString())[0];
		Date date = new Date(System.currentTimeMillis());
		long authorId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		Comment comment = new Comment(commentId, commentContent, date, authorId, commentedEntityId);
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean saved = false;
		try {
			saved = dao.saveGenreComment(comment);
		} catch (DaoException e) {
			logger.error("Comment is not saved ", e);
		}
		content.setUsingCurrentPage(true);
		if (saved){
			Map<String, Object> currrentPageAttributes = content.getCurrentPageAttributes();
			Collection<Comment> comments = (Collection<Comment>) currrentPageAttributes.get(AttributeName.COMMENTS.toString());
			comments.add(comment);
		}
		return saved;
	}

}
