package com.semernik.rockfest.service;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.container.ErrorMessagesContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.SingersDao;
import com.semernik.rockfest.entity.Singer;
import com.semernik.rockfest.entity.Singer.SingerBuilder;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.GeneratorId;


// TODO: Auto-generated Javadoc
/**
 * The Class SingerService.
 */
public class SingerService {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static SingerService instance;

	/**
	 * Gets the single instance of SingerService.
	 *
	 * @return single instance of SingerService
	 */
	public static SingerService getInstance (){
		if (instance == null){
			instance = new SingerService();
		}
		return instance;
	}

	/**
	 * Instantiates a new singer service.
	 */
	private SingerService () {}

	/**
	 * Save singer.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveSinger(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		String singerTitle = (parameters.get(ParameterName.TITLE.toString()))[0];
		String description = (parameters.get(ParameterName.DESCRIPTION.toString()))[0];
		long authorId = (Long)content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		long descriptionEditorId = authorId;
		long singerId = GeneratorId.getInstance().generateSingerId();
		Date addingDate = new Date(System.currentTimeMillis());
		SingerBuilder builder = new SingerBuilder();
		Singer singer = builder.singerId(singerId).title(singerTitle).description(description).addingDate(addingDate)
					.authorId(authorId).descriptionEditorId(descriptionEditorId).build();

		SingersDao dao = DaoFactory.getSingersDao();
		boolean saved = false;
		try {
			saved = dao.saveSinger(singer);
		} catch (DaoException e) {
			logger.error("Can't save singer", e);
		}
		if (saved){
			Map <String, Object> attrs = content.getRequestAttributes();
			attrs.put(AttributeName.SINGER.toString(), singer);
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
		SingersDao dao = DaoFactory.getSingersDao();
		Collection<Singer> singers = null;
		boolean found = true;
		try {
			singers = dao.findAllSingers();
		} catch (DaoException e) {
			found = true;
			logger.error("Singers are not reachable", e);
		}
		if (found){
			content.getRequestAttributes().put(AttributeName.SINGERS.toString(), singers);
		}
		return found;
	}

	/**
	 * Find singer by id.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingerById(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		Long singerId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		SingersDao dao = DaoFactory.getSingersDao();
		Optional<Singer> optional = Optional.empty();
		try {
			optional = dao.findSingerById(singerId);
		} catch (DaoException e) {
			logger.error("Singer is not reachable", e);
		}
		boolean found = false;
		if (optional.isPresent()){
			found = true;
			content.getRequestAttributes().put(AttributeName.SINGER.toString(), optional.get());
		}
		if (content.getSessionAttributes().get(AttributeName.USER_ID.toString()) != null){
			addUserSingerRatings(content);
		}
		return found;
	}

	/**
	 * Adds the user singer ratings.
	 *
	 * @param content the content
	 */
	private void addUserSingerRatings(SessionRequestContent content) {
		RatingService ratingService = RatingService.getInstance();
		if (!ratingService.findGenreUserRating(content)){
			String key = AttributeName.RATING_FAILURE.toString();
			String message = ErrorMessagesContainer.findMessage(key);
			content.getRequestAttributes().put(key, message);
		}
	}

	/**
	 * Update singer description.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean updateSingerDescription(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		Long singerId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String newDescription = parameters.get(ParameterName.DESCRIPTION.toString())[0];
		Long userId = (Long)content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		SingersDao dao = DaoFactory.getSingersDao();
		boolean updated = false;
		try {
			updated = dao.updateSingerDescription(singerId, newDescription, userId);
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		return updated;
	}

}
