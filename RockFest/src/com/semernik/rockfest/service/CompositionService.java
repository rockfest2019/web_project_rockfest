package com.semernik.rockfest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
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
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean saved = false;
		try {
			saved = dao.saveComposition(composition);
			saved = true;
		} catch (DaoException e) {
			logger.error("Composition didn't save " + composition, e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_COMPOSITION_ERROR, content);
			content.setUsingCurrentPage(true);
		}
		if (saved){
			content.addRequestAttribute(AttributeName.COMPOSITION.toString(), composition);
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
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		Collection<Composition> compositions = null;
		boolean found = false;
		try {
			compositions = dao.findAllCompositions();
			found = true;
		} catch (DaoException e) {
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
		long singerId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean found = false;
		try {
			compositions = dao.findSingerCompositions(singerId);
			found = true;
			content.removeCurrentPageAttribute(ErrorMessage.COMPOSITIONS_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Compositions are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMPOSITIONS_ERROR, content);
			content.setUsingCurrentPage(true);
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
		long genreId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean found = false;
		try {
			compositions = dao.findGenreCompositions(genreId);
			found = true;
			content.getCurrentPageAttributes().remove(ErrorMessage.COMPOSITIONS_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Compositions are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMPOSITIONS_ERROR, content);
			content.setUsingCurrentPage(true);
		}
		if (found){
			content.addRequestAttribute(AttributeName.COMPOSITIONS.toString(), compositions);
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
		boolean found = false;
		if (composition.isPresent()){
			found = true;
			content.addRequestAttribute(AttributeName.COMPOSITION.toString(), composition.get());
			addCompositionLinks(compositionId, content);
			if (content.getSessionAttribute(AttributeName.USER_ID.toString()) != null){
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
		Collection<Comment> comments = null;
		try {
			comments = commentsDao.findCompositionCommentsByCompositionId(compositionId);
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
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean saved = false;
		try {
			saved = dao.saveCompositionComment(comment);
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
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

	/**
	 * Save composition link.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveCompositionLink(SessionRequestContent content){
		Link link = EntityUtil.getCompositionLinkFromContent(content);
		CompositionLinksDao dao = DaoFactory.getCompositionLinksDao();
		boolean saved = false;
		try {
			saved = dao.saveCompositionLink(link);
			content.removeCurrentPageAttribute(ErrorMessage.LINK_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Composition link is not saved ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_LINK_ERROR, ErrorMessage.LINK_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		if (saved){
			Collection<Link> links = (Collection<Link>) content.getCurrentPageAttribute(AttributeName.LINKS.toString());
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
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		long authorId = (Long) content.getSessionAttribute(AttributeName.USER_ID.toString());
		Collection <Long> genresIds = EntityUtil.findGenresIds(content);
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = false;
		try {
			changed = dao.changeCompositionGenres(compositionId, genresIds, authorId);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR, content);
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
			Composition composition = (Composition) content.getCurrentPageAttribute(AttributeName.COMPOSITION.toString());
			composition.setGenres(genres);
			content.getCurrentPageAttributes().remove(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_COMPOSITION_GENRES_ERROR, ErrorMessage.COMPOSITION_GENRES_ERROR,
					content);
		}
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
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = false;
		try {
			changed = dao.changeCompositionYear(compositionId, newYear, authorId);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_YEAR_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_YEAR_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		if (changed){
			Composition composition = (Composition) content.getCurrentPageAttribute(AttributeName.COMPOSITION.toString());
			composition.setYear(newYear);
		}
		return changed;
	}

	public boolean deleteCompositionComment(SessionRequestContent content){
		long commentId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean deleted = false;
		try {
			deleted = dao.deleteCompositionComment(commentId);
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

	public boolean deleteCompositionLink(SessionRequestContent content){
		long linkId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		CompositionLinksDao dao = DaoFactory.getCompositionLinksDao();
		boolean deleted = false;
		try {
			deleted = dao.deleteCompositionLink(linkId);
			content.removeCurrentPageAttribute(ErrorMessage.LINK_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.DELETE_LINK_ERROR, ErrorMessage.LINK_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		if (deleted){
			Collection<Link> links = (Collection<Link>) content.getCurrentPageAttribute(AttributeName.LINKS.toString());
			links.removeIf(a -> a.getLinkId() == linkId);
		}
		return deleted;
	}

	public boolean changeCompositionTitle(SessionRequestContent content){
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String newTitle = content.getParameter(ParameterName.TITLE.toString());
		CompositionsDao dao = DaoFactory.getCompositionsDao();
		boolean changed = false;
		try {
			changed = dao.changeCompositionTitle(compositionId, newTitle);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_TITLE_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_TITLE_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		if (changed){
			Composition composition = (Composition) content.getCurrentPageAttribute(AttributeName.COMPOSITION.toString());
			composition.setCompositionTitle(newTitle);
		}
		return changed;
	}

}
