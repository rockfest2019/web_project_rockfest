package com.semernik.rockfest.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.container.ErrorMessagesContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.CommentsDao;
import com.semernik.rockfest.dao.CompositionLinksDao;
import com.semernik.rockfest.dao.CompositionsDao;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.GenresDao;
import com.semernik.rockfest.entity.Comment;
import com.semernik.rockfest.entity.Composition;
import com.semernik.rockfest.entity.Composition.CompositionBuilder;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Link;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.GeneratorId;



// TODO: Auto-generated Javadoc
/**
 * The Class CompositionService.
 */
public class CompositionService {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static CompositionService instance= new CompositionService();

	private final static String UPDATED_GENRES_ERROR = "updated genres display failure";
	/**
	 * Gets the single instance of CompositionService.
	 *
	 * @return single instance of CompositionService
	 */
	public static CompositionService getInstance(){
		return instance;
	}

	/**
	 * Instantiates a new composition service.
	 */
	private CompositionService () {}

	/**
	 * Save composition.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveComposition(SessionRequestContent content) {
		Map<String, String[]> parameters = content.getRequestParameters();
		String title = (parameters.get(ParameterName.TITLE.toString()))[0];
		String year = (parameters.get(ParameterName.YEAR.toString()))[0];
		long singerId = Long.parseLong((parameters.get(ParameterName.SINGER_ID.toString()))[0]);
		long authorId = (Long)content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		long yearEditorId = authorId;
		long genreEditorId = authorId;
		long compositionId = GeneratorId.getInstance().generateCompositionId();
		Date addingDate = new Date(System.currentTimeMillis());
		Collection <Long> genresIds = findGenresIds(content);
		CompositionBuilder builder = new CompositionBuilder();
		Composition composition = builder.compositionId(compositionId).compositionTitle(title).year(year)
				.singerId(singerId).authorId(authorId).yearEditorId(yearEditorId).genreEditorId(genreEditorId)
				.addingDate(addingDate).genresIds(genresIds).build();
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean saved = false;
		try {
			saved = dao.saveComposition(composition);
		} catch (DaoException e) {
			logger.error("Composition didn't save " + composition, e);
		}
		if (saved){
			Map <String, Object> attrs = content.getRequestAttributes();
			attrs.put(AttributeName.COMPOSITION.toString(), composition);
		}
		return saved;
	}

	/**
	 * Find genres ids.
	 *
	 * @param content the content
	 * @return the collection
	 */
	private Collection<Long> findGenresIds(SessionRequestContent content) {
		Collection <Long> genresIds = new LinkedList<>();
		String [] strIds = content.getRequestParameters().get(ParameterName.GENRES_IDS.toString());
		if (strIds != null){
			for (String idStr : strIds){
				genresIds.add(Long.parseLong(idStr));
			}
		}
		return genresIds;
	}

	/**
	 * Find compositions.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCompositions(SessionRequestContent content){
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		Collection<Composition> compositions = null;
		boolean found = true;
		try {
			compositions = dao.findAllCompositions();
		} catch (DaoException e) {
			found = false;
			logger.error("Compositions are not reachable ", e);
		}
		if (found){
			content.getRequestAttributes().put(AttributeName.COMPOSITIONS.toString(), compositions);
		}
		return found;
	}

	/**
	 * Find singer compositions.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingerCompositions(SessionRequestContent content){
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		Collection<Composition> compositions = null;
		Map<String, String[]> parameters = content.getRequestParameters();
		long singerId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		boolean found = true;
		try {
			compositions = dao.findSingerCompositions(singerId);
		} catch (DaoException e) {
			found = false;
			logger.error("Compositions are not reachable ", e);
		}
		if (found){
			content.getRequestAttributes().put(AttributeName.COMPOSITIONS.toString(), compositions);
		}
		return found;
	}

	/**
	 * Find genre compositions.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenreCompositions(SessionRequestContent content){
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		Collection<Composition> compositions = null;
		Map<String, String[]> parameters = content.getRequestParameters();
		long genreId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		boolean found = true;
		try {
			compositions = dao.findGenreCompositions(genreId);
		} catch (DaoException e) {
			found = false;
			logger.error("Compositions are not reachable ", e);
		}
		if (found){
			content.getRequestAttributes().put(AttributeName.COMPOSITIONS.toString(), compositions);
		}
		return found;
	}

	/**
	 * Find singers and genres.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingersAndGenres(SessionRequestContent content){
		boolean found = (SingerService.getInstance().findSingers(content) && GenreService.getInstance().findGenres(content));
		return found;
	}

	/**
	 * Find composition by id.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCompositionById(SessionRequestContent content){
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		Map<String, String[]> parameters = content.getRequestParameters();
		long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		Optional<Composition> composition = Optional.empty();
		try {
			composition = dao.findCompositionById(compositionId);
		} catch (DaoException e) {
			logger.error("Composition are not reachable ", e);
		}
		boolean found = false;
		if (composition.isPresent()){
			found = true;
			content.getRequestAttributes().put(AttributeName.COMPOSITION.toString(), composition.get());
			addCompositionLinks(compositionId, content);
			if (content.getSessionAttributes().get(AttributeName.USER_ID.toString()) != null){
				addUserCompositionRatings(content);
			}
			addCompositionComments(compositionId, content);
		}
		return found;
	}

	/**
	 * Adds the composition links.
	 *
	 * @param compositionId the composition id
	 * @param content the content
	 */
	private void addCompositionLinks(long compositionId, SessionRequestContent content) {
		Collection<Link> links = new ArrayList<>();
		CompositionLinksDao dao = DaoFactory.getCompositionLinksDao();
		try {
			links = dao.findCompositionLinksByCompositionId(compositionId);
			content.getRequestAttributes().put(AttributeName.LINKS.toString(), links);
		} catch (DaoException e) {
			logger.error("Links are not reachable ", e);
		}
	}

	/**
	 * Adds the user composition ratings.
	 *
	 * @param content the content
	 */
	private void addUserCompositionRatings(SessionRequestContent content) {
		RatingService ratingService = RatingService.getInstance();
		boolean ratingsFound = ratingService.findCompositionUserRating(content);
		if (!ratingsFound){
			String message = ErrorMessagesContainer.findMessage(AttributeName.RATING_FAILURE.toString());
			content.getRequestAttributes().put(AttributeName.RATING_FAILURE.toString(), message);
		}
	}

	/**
	 * Adds the composition comments.
	 *
	 * @param compositionId the composition id
	 * @param content the content
	 */
	private void addCompositionComments(long compositionId, SessionRequestContent content) {
		CommentsDao commentsDao = DaoFactory.getCommentsDao();
		Collection<Comment> comments = null;
		try {
			comments = commentsDao.findCompositionCommentsByCompositionId(compositionId);
			content.getRequestAttributes().put(AttributeName.COMMENTS.toString(), comments);
		} catch (DaoException e) {
			String errorMessage = ErrorMessagesContainer.findMessage(AttributeName.COMMENTS_FAILURE.toString());
			content.getRequestAttributes().put(AttributeName.COMMENTS_FAILURE.toString(), errorMessage);
			logger.error("Comments are not reachable ", e);
		}
	}

	/**
	 * Save composition comment.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveCompositionComment(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long commentId = GeneratorId.getInstance().generateCompositionCommentId();
		long commentedEntityId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String commentContent = parameters.get(ParameterName.COMMENT_CONTENT.toString())[0];
		Date date = new Date(System.currentTimeMillis());
		long authorId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		Comment comment = new Comment(commentId, commentContent, date, authorId, commentedEntityId);
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean saved = false;
		try {
			saved = dao.saveCompositionComment(comment);
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

	/**
	 * Save composition link.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveCompositionLink(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String url = parameters.get(ParameterName.URL.toString())[0];
		long authorId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		Date addingDate = new Date(System.currentTimeMillis());
		long linkId = GeneratorId.getInstance().generateCompositionLinkId();
		Link link = new Link(compositionId, linkId, addingDate, url, authorId);
		CompositionLinksDao dao = DaoFactory.getCompositionLinksDao();
		boolean saved = false;
		try {
			saved = dao.saveCompositionLink(link);
		} catch (DaoException e) {
			logger.error("Composition link is not saved ", e);
		}
		content.setUsingCurrentPage(true);
		if (saved){
			Map<String, Object> currentPageAttributes = content.getCurrentPageAttributes();
			Collection<Link> links = (Collection<Link>) currentPageAttributes.get(AttributeName.LINKS.toString());
			links.add(link);
		}
		return saved;
	}

	/**
	 * Change composition genres.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean changeCompositionGenres(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		long authorId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		Collection <Long> genresIds = findGenresIds(content);
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = false;
		try {
			changed = dao.changeCompositionGenres(compositionId, genresIds, authorId);
		} catch (DaoException e) {
			logger.error("Data access error ", e);
		}
		content.setUsingCurrentPage(true);
		if (changed){
			addNewCompositionGenresToContent(compositionId, content);
		}
		return changed;
	}

	private void addNewCompositionGenresToContent(long compositionId, SessionRequestContent content) {
		GenresDao genreDao = DaoFactory.getGenresDao();
		Collection<Genre> genres = new LinkedList<>();
		try {
			genres = genreDao.findGenresByCompositionId(compositionId);
			Map<String, Object> currentPageAttributes = content.getCurrentPageAttributes();
			Composition composition = (Composition) currentPageAttributes.get(AttributeName.COMPOSITION.toString());
			composition.setGenres(genres);
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			content.getRequestAttributes().put(AttributeName.GENRE_ERROR.toString(), UPDATED_GENRES_ERROR);
		}
	}

	/**
	 * Change composition year.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean changeCompositionYear(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String newYear = parameters.get(ParameterName.YEAR.toString())[0];
		long authorId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = false;
		try {
			changed = dao.changeCompositionYear(compositionId, newYear, authorId);
		} catch (DaoException e) {
			logger.error("Data access error ", e);
		}
		content.setUsingCurrentPage(true);
		if (changed){
			Map<String, Object> currentPageAttributes = content.getCurrentPageAttributes();
			Composition composition = (Composition) currentPageAttributes.get(AttributeName.COMPOSITION.toString());
			composition.setYear(newYear);
		}
		return changed;
	}

	public boolean deleteCompositionComment(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long commentId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean deleted = false;
		try {
			deleted = dao.deleteCompositionComment(commentId);
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

	public boolean deleteCompositionLink(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long linkId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		CompositionLinksDao dao = DaoFactory.getCompositionLinksDao();
		boolean deleted = false;
		try {
			deleted = dao.deleteCompositionLink(linkId);
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		if (deleted){
			Map<String, Object> currentPageAttributes = content.getCurrentPageAttributes();
			Collection<Link> links = (Collection<Link>) currentPageAttributes.get(AttributeName.LINKS.toString());
			links.removeIf(a -> a.getLinkId() == linkId);
		}
		content.setUsingCurrentPage(true);
		return deleted;
	}

	public boolean changeCompositionTitle(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String newTitle = parameters.get(ParameterName.TITLE.toString())[0];
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = false;
		try {
			changed = dao.changeCompositionTitle(compositionId, newTitle);
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		content.setUsingCurrentPage(true);
		if (changed){
			Map<String, Object> currentPageAttributes = content.getCurrentPageAttributes();
			Composition composition = (Composition) currentPageAttributes.get(AttributeName.COMPOSITION.toString());
			composition.setCompositionTitle(newTitle);
		}
		return changed;
	}

}
