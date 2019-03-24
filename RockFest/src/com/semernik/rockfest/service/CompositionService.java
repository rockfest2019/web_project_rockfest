package com.semernik.rockfest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.CommentsDao;
import com.semernik.rockfest.dao.CompositionLinksDao;
import com.semernik.rockfest.dao.CompositionsDao;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.GenresDao;
import com.semernik.rockfest.entity.Comment;
import com.semernik.rockfest.entity.Composition;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Link;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ErrorMessage;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.EntityUtil;
import com.semernik.rockfest.util.ErrorUtil;



// TODO: Auto-generated Javadoc
/**
 * The Class CompositionService.
 */
public class CompositionService {

	private static Logger logger = LogManager.getLogger();
	private static CompositionService instance= new CompositionService();

	public static CompositionService getInstance(){
		return instance;
	}

	private CompositionService () {}

	/**
	 * Save composition.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveComposition(SessionRequestContent content) {
		Composition composition = EntityUtil.getComposititonFromContent(content);
		boolean saved = false;
		try {
			saved = tryToSaveComposition(composition, content);
		} catch (DaoException e) {
			logger.error("Composition didn't save " + composition, e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_COMPOSITION_ERROR, content);
			content.setUsingCurrentPage(true);
		}
		return saved;
	}

	private boolean tryToSaveComposition(Composition composition, SessionRequestContent content) throws DaoException {
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean saved = dao.saveComposition(composition);
		if (saved){
			content.addRequestAttribute(AttributeName.COMPOSITION.toString(), composition);
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_COMPOSITION_ERROR, content);
			content.setUsingCurrentPage(true);
		}
		return saved;
	}

	/**
	 * Find compositions.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCompositions(SessionRequestContent content){
		int position = Integer.parseInt(content.getParameter(ParameterName.POSITION.toString()));
		int elementsCount = Integer.parseInt(content.getParameter(ParameterName.ELEMENTS_COUNT.toString()));
		boolean found = false;
		try {
			found = tryFindCompositions(position, elementsCount, content);
		} catch (DaoException e) {
			logger.error("Compositions are not reachable ", e);
		}
		return found;
	}

	private boolean tryFindCompositions(int position, int elementsCount, SessionRequestContent content) throws DaoException {
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		Collection<Composition> compositions = dao.findCompositions(position, elementsCount);
		content.getRequestAttributes().put(AttributeName.COMPOSITIONS.toString(), compositions);
		content.addRequestAttribute(AttributeName.POSITION.toString(), position);
		content.addRequestAttribute(AttributeName.ELEMENTS_COUNT.toString(), elementsCount);
		return true;
	}

	/**
	 * Find singer compositions.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingerCompositions(SessionRequestContent content){
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		long singerId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean found = false;
		try {
			Collection<Composition> compositions = dao.findSingerCompositions(singerId);
			content.addRequestAttribute(AttributeName.COMPOSITIONS.toString(), compositions);
			content.removeCurrentPageAttribute(ErrorMessage.COMPOSITIONS_ERROR.toString());
			found = true;
		} catch (DaoException e) {
			logger.error("Compositions are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMPOSITIONS_ERROR, content);
			content.setUsingCurrentPage(true);
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
		long genreId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean found = false;
		try {
			Collection<Composition> compositions = dao.findGenreCompositions(genreId);
			content.addRequestAttribute(AttributeName.COMPOSITIONS.toString(), compositions);
			content.removeCurrentPageAttribute(ErrorMessage.COMPOSITIONS_ERROR.toString());
			found = true;
		} catch (DaoException e) {
			logger.error("Compositions are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMPOSITIONS_ERROR, content);
			content.setUsingCurrentPage(true);
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
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		Optional<Composition> composition = Optional.empty();
		try {
			composition = dao.findCompositionById(compositionId);
			content.removeCurrentPageAttribute(ErrorMessage.COMPOSITION_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Composition are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMPOSITION_ERROR, content);
		}
		return tryAddCompositionToContent(composition, content);
	}

	private boolean tryAddCompositionToContent(Optional<Composition> optional, SessionRequestContent content) {
		boolean added = false;
		if (optional.isPresent()){
			Composition composition = optional.get();
			long compositionId = composition.getCompositionId();
			content.addRequestAttribute(AttributeName.COMPOSITION.toString(), composition);
			addCompositionLinks(compositionId, content);
			if (content.getSessionAttribute(AttributeName.USER_ID.toString()) != null){
				addUserCompositionRatings(content);
			}
			addCompositionComments(compositionId, content);
			added = true;
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMPOSITION_ERROR, content);
		}
		return added;

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
			content.addRequestAttribute(AttributeName.LINKS.toString(), links);
			content.removeCurrentPageAttribute(ErrorMessage.LINK_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Links are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.LINK_ERROR, content);
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
		if (ratingsFound){
			content.removeCurrentPageAttribute(ErrorMessage.RATING_FAILURE.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.RATING_FAILURE, content);
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
		try {
			Collection<Comment> comments = commentsDao.findCompositionCommentsByCompositionId(compositionId);
			content.addRequestAttribute(AttributeName.COMMENTS.toString(), comments);
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
		} catch (DaoException e) {
			logger.error("Comments are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMMENTS_FAILURE, content);
		}
	}

	/**
	 * Save composition comment.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveCompositionComment(SessionRequestContent content){
		Comment comment = EntityUtil.getEntityCommentFromContent(content);
		boolean saved = false;
		try {
			saved = trySaveCompositionComment(comment, content);
		} catch (DaoException e) {
			logger.error("Comment is not saved ", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.SAVE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		content.setUsingCurrentPage(true);
		return saved;
	}

	private boolean trySaveCompositionComment(Comment comment, SessionRequestContent content) throws DaoException {
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean saved = dao.saveCompositionComment(comment);
		if (saved){
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
			Collection<Comment> comments = (Collection<Comment>) content.getCurrentPageAttribute(AttributeName.COMMENTS.toString());
			comments.add(comment);
		} else {
			ErrorUtil.addErrorMessageToContent(ErrorMessage.SAVE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
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
		Link link = EntityUtil.getCompositionLinkFromContent(content);
		boolean saved = false;
		try {
			saved = trySaveCompositionLink(link, content);
		} catch (DaoException e) {
			logger.error("Composition link is not saved ", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.SAVE_LINK_ERROR, ErrorMessage.LINK_ERROR, content);
		}
		content.setUsingCurrentPage(true);

		return saved;
	}

	private boolean trySaveCompositionLink(Link link, SessionRequestContent content) throws DaoException {
		CompositionLinksDao dao = DaoFactory.getCompositionLinksDao();
		boolean saved = dao.saveCompositionLink(link);
		if (saved){
			content.removeCurrentPageAttribute(ErrorMessage.LINK_ERROR.toString());
			Collection<Link> links = (Collection<Link>) content.getCurrentPageAttribute(AttributeName.LINKS.toString());
			links.add(link);
		} else {
			ErrorUtil.addErrorMessageToContent(ErrorMessage.SAVE_LINK_ERROR, ErrorMessage.LINK_ERROR, content);
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
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		long authorId = (Long) content.getSessionAttribute(AttributeName.USER_ID.toString());
		Collection <Long> genresIds = EntityUtil.findGenresIds(content);
		boolean changed = false;
		try {
			changed = tryChangeCompositionGenres(compositionId, authorId, genresIds, content);
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		return changed;
	}

	private boolean tryChangeCompositionGenres(long compositionId, long authorId, Collection<Long> genresIds,
			SessionRequestContent content) throws DaoException {
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = dao.changeCompositionGenres(compositionId, genresIds, authorId);
		if (changed){
			addNewCompositionGenresToContent(compositionId, content);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR, content);
		}
		return changed;
	}

	private void addNewCompositionGenresToContent(long compositionId, SessionRequestContent content) {
		try {
			tryAddNewCompositionGenresToContent(compositionId, content);
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR, ErrorMessage.COMPOSITION_GENRES_ERROR,
					content);
		}
	}

	private void tryAddNewCompositionGenresToContent(long compositionId, SessionRequestContent content) throws DaoException{
		GenresDao genreDao = DaoFactory.getGenresDao();
		Collection<Genre> genres = genreDao.findGenresByCompositionId(compositionId);
		Composition composition = (Composition) content.getCurrentPageAttribute(AttributeName.COMPOSITION.toString());
		composition.setGenres(genres);
		content.getCurrentPageAttributes().remove(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR.toString());
	}

	/**
	 * Change composition year.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean changeCompositionYear(SessionRequestContent content){
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String newYear = content.getParameter(ParameterName.YEAR.toString());
		long authorId = (Long) content.getSessionAttribute(AttributeName.USER_ID.toString());
		boolean changed = false;
		try {
			changed = tryChangeCompositionYear(compositionId, newYear, authorId, content);
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_YEAR_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		return changed;
	}

	private boolean tryChangeCompositionYear(long compositionId, String newYear, long authorId,
			SessionRequestContent content) throws DaoException {
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = dao.changeCompositionYear(compositionId, newYear, authorId);
		if (changed){
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_YEAR_ERROR.toString());
			Composition composition = (Composition) content.getCurrentPageAttribute(AttributeName.COMPOSITION.toString());
			composition.setYear(newYear);
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_YEAR_ERROR, content);
		}
		return changed;
	}

	public boolean deleteCompositionComment(SessionRequestContent content){
		long commentId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean deleted = false;
		try {
			deleted = tryDeleteCompositionComment(commentId, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.DELETE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		content.setUsingCurrentPage(true);
		return deleted;
	}

	private boolean tryDeleteCompositionComment(long commentId, SessionRequestContent content) throws DaoException {
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean deleted = dao.deleteCompositionComment(commentId);
		if (deleted){
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
			Collection<Comment> comments = (Collection<Comment>) content.getCurrentPageAttribute(AttributeName.COMMENTS.toString());
			comments.removeIf(a -> a.getCommentId() == commentId);
		} else {
			ErrorUtil.addErrorMessageToContent(ErrorMessage.DELETE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		return deleted;
	}

	public boolean deleteCompositionLink(SessionRequestContent content){
		long linkId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean deleted = false;
		try {
			deleted = tryDeleteCompositionLink(linkId, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.DELETE_LINK_ERROR, ErrorMessage.LINK_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		return deleted;
	}

	private boolean tryDeleteCompositionLink(long linkId, SessionRequestContent content) throws DaoException {
		CompositionLinksDao dao = DaoFactory.getCompositionLinksDao();
		boolean deleted = dao.deleteCompositionLink(linkId);
		if (deleted){
			content.removeCurrentPageAttribute(ErrorMessage.LINK_ERROR.toString());
			Collection<Link> links = (Collection<Link>) content.getCurrentPageAttribute(AttributeName.LINKS.toString());
			links.removeIf(a -> a.getLinkId() == linkId);
		} else {
			ErrorUtil.addErrorMessageToContent(ErrorMessage.DELETE_LINK_ERROR, ErrorMessage.LINK_ERROR, content);
		}
		return false;
	}

	public boolean changeCompositionTitle(SessionRequestContent content){
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String newTitle = content.getParameter(ParameterName.TITLE.toString());
		boolean changed = false;
		try {
			changed = tryChangeCompositionTitle(compositionId, newTitle, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_TITLE_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		return changed;
	}

	private boolean tryChangeCompositionTitle(long compositionId, String newTitle, SessionRequestContent content) throws DaoException {
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = dao.changeCompositionTitle(compositionId, newTitle);
		if (changed){
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_TITLE_ERROR.toString());
			Composition composition = (Composition) content.getCurrentPageAttribute(AttributeName.COMPOSITION.toString());
			composition.setCompositionTitle(newTitle);
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_TITLE_ERROR, content);
		}
		return changed;
	}

}
