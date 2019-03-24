package com.semernik.rockfest.service;

import java.util.Collection;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.CommentsDao;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.SingersDao;
import com.semernik.rockfest.entity.Comment;
import com.semernik.rockfest.entity.Singer;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ErrorMessage;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.EntityUtil;
import com.semernik.rockfest.util.ErrorUtil;


// TODO: Auto-generated Javadoc
/**
 * The Class SingerService.
 */
public class SingerService {

	private static Logger logger = LogManager.getLogger();
	private static SingerService instance;


	public static SingerService getInstance (){
		if (instance == null){
			instance = new SingerService();
		}
		return instance;
	}

	private SingerService () {}

	/**
	 * Save singer.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveSinger(SessionRequestContent content){
		Singer singer = EntityUtil.getSingerFromContent(content);
		boolean saved =  saveSinger(singer, content);
		if (saved){
			content.addRequestAttribute(AttributeName.SINGER.toString(), singer);
		}
		return saved;
	}



	private boolean saveSinger(Singer singer, SessionRequestContent content) {
		SingersDao dao = DaoFactory.getSingersDao();
		boolean saved = false;
		try {
			saved = dao.saveSinger(singer);
			content.removeCurrentPageAttribute(ErrorMessage.SAVE_SINGER_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Can't save singer", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SAVE_SINGER_ERROR, content);
			content.setUsingCurrentPage(true);
		}
		return saved;
	}

	/**
	 * Find singers.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingers(SessionRequestContent content){
		int position = Integer.parseInt(content.getParameter(ParameterName.POSITION.toString()));
		int elementsCount = Integer.parseInt(content.getParameter(ParameterName.ELEMENTS_COUNT.toString()));
		boolean found = false;
		try {
			found = tryFindSingers(position, elementsCount, content);
		} catch (DaoException e) {
			logger.error("Singers are not reachable", e);
		}
		return found;
	}

	private boolean tryFindSingers(int position, int elementsCount, SessionRequestContent content) throws DaoException {
		SingersDao dao = DaoFactory.getSingersDao();
		Collection<Singer> singers = dao.findSingers(position, elementsCount);
		content.addRequestAttribute(AttributeName.SINGERS.toString(), singers);
		content.addRequestAttribute(AttributeName.POSITION.toString(), position);
		content.addRequestAttribute(AttributeName.ELEMENTS_COUNT.toString(), elementsCount);
		return true;
	}

	/**
	 * Find singer by id.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingerById(SessionRequestContent content){
		long singerId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		SingersDao dao = DaoFactory.getSingersDao();
		Optional<Singer> optional = Optional.empty();
		try {
			optional = dao.findSingerById(singerId);
			content.removeCurrentPageAttribute(ErrorMessage.SINGER_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Singer is not reachable", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SINGER_ERROR, content);
		}
		return tryAddSingerToContent(optional, content);
	}

	private boolean tryAddSingerToContent(Optional<Singer> optional, SessionRequestContent content) {
		boolean added = false;
		if (optional.isPresent()){
			Singer singer = optional.get();
			content.addRequestAttribute(AttributeName.SINGER.toString(), singer);
			addSingerComments(singer.getSingerId(), content);
			if (content.getSessionAttribute(AttributeName.USER_ID.toString()) != null){
				addUserSingerRatings(content);
			}
			added = true;
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SINGER_ERROR, content);
		}
		return added;
	}

	private void addSingerComments(long singerId, SessionRequestContent content) {
		CommentsDao commentsDao = DaoFactory.getCommentsDao();
		try {
			Collection<Comment> comments = commentsDao.findSingerCommentsBySingerId(singerId);
			content.addRequestAttribute(AttributeName.COMMENTS.toString(), comments);
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
		} catch (DaoException e) {
			logger.error("Comments are not reachable ", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.COMMENTS_FAILURE, content);
		}
	}

	/**
	 * Adds the user singer ratings.
	 *
	 * @param content the content
	 */
	private void addUserSingerRatings(SessionRequestContent content) {
		RatingService ratingService = RatingService.getInstance();
		if (!ratingService.findGenreUserRating(content)){
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.USER_RATING_ERROR, content);
		}
	}

	/**
	 * Update singer description.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean updateSingerDescription(SessionRequestContent content){
		Long singerId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String newDescription = content.getParameter(ParameterName.DESCRIPTION.toString());
		Long userId = (Long)content.getSessionAttribute(AttributeName.USER_ID.toString());
		boolean updated = false;
		try {
			updated = tryUpdateSingerDescription(singerId, newDescription, userId, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_DESCRIPTION_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		return updated;
	}

	private boolean tryUpdateSingerDescription(Long singerId, String newDescription, Long userId,
			SessionRequestContent content) throws DaoException{
		SingersDao dao = DaoFactory.getSingersDao();
		boolean updated = dao.updateSingerDescription(singerId, newDescription, userId);
		if (updated){
			Singer singer = (Singer) content.getCurrentPageAttribute(AttributeName.SINGER.toString());
			singer.setDescription(newDescription);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_DESCRIPTION_ERROR.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_DESCRIPTION_ERROR, content);
		}
		return updated;
	}

	public boolean deleteSingerComment(SessionRequestContent content){
		long commentId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean deleted = false;
		try {
			deleted = tryDeleteSingerComment(commentId, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.DELETE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		content.setUsingCurrentPage(true);
		return deleted;
	}

	private boolean tryDeleteSingerComment(long commentId, SessionRequestContent content) throws DaoException{
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean deleted = dao.deleteSingerComment(commentId);
		if (deleted){
			Collection<Comment> comments = (Collection<Comment>) content.getCurrentPageAttribute(AttributeName.COMMENTS.toString());
			comments.removeIf(a -> a.getCommentId() == commentId);
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
		} else {
			ErrorUtil.addErrorMessageToContent(ErrorMessage.DELETE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		return deleted;
	}

	public boolean changeSingerTitle(SessionRequestContent content){
		long singerId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		String newTitle = content.getParameter(ParameterName.TITLE.toString());
		boolean changed = false;
		try {
			changed = tryChangeSingerTitle(singerId, newTitle, content);
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_TITLE_ERROR, content);
		}
		content.setUsingCurrentPage(true);
		return changed;
	}

	private boolean tryChangeSingerTitle(long singerId, String newTitle, SessionRequestContent content) throws DaoException{
		SingersDao dao = DaoFactory.getSingersDao();
		boolean changed = dao.changeSingerTitle(singerId, newTitle);
		if (changed){
			Singer singer = (Singer) content.getCurrentPageAttribute(AttributeName.SINGER.toString());
			singer.setTitle(newTitle);
			content.removeCurrentPageAttribute(ErrorMessage.UPDATE_TITLE_ERROR.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.UPDATE_TITLE_ERROR, content);
		}
		return changed;
	}

	/**
	 * Save singer comment.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveSingerComment(SessionRequestContent content){
		Comment comment = EntityUtil.getEntityCommentFromContent(content);
		boolean saved = false;
		try {
			saved = trySaveSingerComment(comment, content);
		} catch (DaoException e) {
			logger.error("Comment is not saved ", e);
			ErrorUtil.addErrorMessageToContent(ErrorMessage.SAVE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		content.setUsingCurrentPage(true);
		return saved;
	}

	private boolean trySaveSingerComment(Comment comment, SessionRequestContent content) throws DaoException{
		CommentsDao dao = DaoFactory.getCommentsDao();
		boolean saved = dao.saveSingerComment(comment);
		if (saved){
			Collection<Comment> comments = (Collection<Comment>) content.getCurrentPageAttribute(AttributeName.COMMENTS.toString());
			comments.add(comment);
			content.removeCurrentPageAttribute(ErrorMessage.COMMENTS_FAILURE.toString());
		} else {
			ErrorUtil.addErrorMessageToContent(ErrorMessage.SAVE_COMMENT_ERROR, ErrorMessage.COMMENTS_FAILURE, content);
		}
		return saved;
	}

}
