package com.semernik.rockfest.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.RatingDao;
import com.semernik.rockfest.entity.EntityRating;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.CommandType;
import com.semernik.rockfest.type.EntityType;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.type.RatingsComparator;
import com.semernik.rockfest.util.RatingUtil;
import com.semernik.rockfest.util.RatingsDaoMethod;

// TODO: Auto-generated Javadoc
/**
 * The Class RatingService.
 */
public class RatingService {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static RatingService instance = new RatingService();

	/** The Constant ELEMENTS_COUNT. */
	private static final int ELEMENTS_COUNT = 5;

	/**
	 * Gets the single instance of RatingService.
	 *
	 * @return single instance of RatingService
	 */
	public static RatingService getInstance(){
		return instance;
	}

	/**
	 * Instantiates a new rating service.
	 */
	private RatingService(){}

	/**
	 * Save user rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveUserRating(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		Long userId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		int melodyRating = Integer.parseInt(parameters.get(ParameterName.MELODY_RATING.toString())[0]);
		int textRating = Integer.parseInt(parameters.get(ParameterName.TEXT_RATING.toString())[0]);
		int musicRating = Integer.parseInt(parameters.get(ParameterName.MUSIC_RATING.toString())[0]);
		int vocalRating = Integer.parseInt(parameters.get(ParameterName.VOCAL_RATING.toString())[0]);
		EntityRating rating = new EntityRating(userId, compositionId, melodyRating, textRating, musicRating, vocalRating);
		RatingDao dao = DaoFactory.getRatingDao();
		boolean saved = false;
		try {
			saved = dao.saveUserRating(rating);
		} catch (DaoException e) {
			logger.error("User rating was not saved ", e);
		}
		return saved;
	}

	/**
	 * Find composition user rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCompositionUserRating(SessionRequestContent content){
		return findUserRating(DaoFactory.getRatingDao()::findCompositionUserRating, content);
	}

	/**
	 * Find genre user rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenreUserRating(SessionRequestContent content) {
		return findUserRating(DaoFactory.getRatingDao()::findGenreUserRating, content);
	}

	/**
	 * Find singer user rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingerUserRating(SessionRequestContent content) {
		return findUserRating(DaoFactory.getRatingDao()::findSingerUserRating, content);
	}

	/**
	 * Find user rating.
	 *
	 * @param daoMethod the dao method
	 * @param content the content
	 * @return true, if successful
	 */
	private boolean findUserRating (RatingDaoMethod daoMethod, SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long entityId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		Long userId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		boolean found = false;
		Optional<EntityRating> optional = Optional.empty();
		try {
			optional = daoMethod.apply(entityId, userId);
			found = true;
			if (optional.isPresent()){
				EntityRating rating = optional.get();
				content.getRequestAttributes().put(AttributeName.USER_RATING.toString(), rating);
			}
		} catch (DaoException e) {
			logger.error("Ratings are not reachable ", e);
		}
		return found;
	}

	/**
	 * Find common ratings.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCommonRatings(SessionRequestContent content){
		return false;
	}

	/**
	 * The Interface RatingDaoMethod.
	 */
	private static interface RatingDaoMethod {

		/**
		 * Apply.
		 *
		 * @param entityId the entity id
		 * @param userId the user id
		 * @return the optional
		 * @throws DaoException the dao exception
		 */
		Optional<EntityRating> apply(long entityId, long userId) throws DaoException;
	}

	/**
	 * Find compositions rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCompositionsRating(SessionRequestContent content){
		return findRatings(content, EntityType.COMPOSITION.name().toLowerCase());
	}

	/**
	 * Find genres rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenresRating(SessionRequestContent content){
		return findRatings(content,  EntityType.GENRE.name().toLowerCase());
	}

	/**
	 * Find singers rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingersRating(SessionRequestContent content){
		return findRatings(content,  EntityType.SINGER.name().toLowerCase());
	}


	/**
	 * Find ratings.
	 *
	 * @param content the content
	 * @param comparingEntity the comparing entity
	 * @return true, if successful
	 */
	private boolean findRatings(SessionRequestContent content, String comparingEntity){
		Map<String, String[]> parameters = content.getRequestParameters();
		String comparatorName = parameters.get(ParameterName.RATING_TYPE.toString())[0];
		RatingUtil ratingUtil = RatingUtil.getInstance();
		RatingsDaoMethod daoMethod = ratingUtil.findRatingsDaoMethod(comparingEntity, comparatorName);
		List<EntityRating> ratings = new LinkedList<>();
		boolean found = false;
		try {
			ratings = daoMethod.apply(0, ELEMENTS_COUNT);
			found = true;
			ratings.forEach(RatingUtil.getInstance()::transformToAverageRatings);
			addRatingAttributes(content, ratings, comparingEntity, comparatorName);
		} catch (DaoException e) {
			logger.error("Ratings are not reachable ", e);
		}

		return found;
	}

	/**
	 * Adds the rating attributes.
	 *
	 * @param content the content
	 * @param ratings the ratings
	 * @param comparingEntity the comparing entity
	 * @param comparatorName the comparator name
	 */
	private void addRatingAttributes(SessionRequestContent content, List<EntityRating> ratings, String comparingEntity,
			String comparatorName) {
		Map<String, Object> requestAttributes = content.getRequestAttributes();
		requestAttributes.put(AttributeName.RATINGS.toString(), ratings);
		requestAttributes.put(AttributeName.SPECIFIC_RATING.toString(), true);
		requestAttributes.put(AttributeName.COMPARATOR.toString(), comparatorName);
		requestAttributes.put(AttributeName.COMPARING_ENTITY.toString(), comparingEntity);
		RatingUtil ratingUtil = RatingUtil.getInstance();
		String entityCommand = ratingUtil.findEntityCommand(comparingEntity);
		requestAttributes.put(AttributeName.ENTITY_COMMAND.toString(), entityCommand);
		String ajaxCommand = ratingUtil.findAjaxCommand(comparingEntity);
		requestAttributes.put(AttributeName.AJAX_COMMAND.toString(), ajaxCommand);
		String ratingCommand = ratingUtil.findRatingCommand(comparingEntity);
		requestAttributes.put(AttributeName.RATING_COMMAND.toString(), ratingCommand);
		boolean ratingEnd = ELEMENTS_COUNT > ratings.size();
		requestAttributes.put(AttributeName.RATING_END.toString(), ratingEnd);
	}



	/**
	 * Find user compositions rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findUserCompositionsRating(SessionRequestContent content){
		boolean found = findUserRatings(DaoFactory.getRatingDao()::findUserCompositionsRating , content,
				EntityType.COMPOSITION.name().toLowerCase());
		if (found){
			content.getRequestAttributes().put(AttributeName.ENTITY_COMMAND.toString(), CommandType.FIND_SINGER.name().toLowerCase());
			content.getRequestAttributes().put(AttributeName.RATING_COMMAND.toString(),
					CommandType.FIND_USER_COMPOSITIONS_RATINGS.name().toLowerCase());
		}
		return found;
	}

	/**
	 * Find user singers rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findUserSingersRating(SessionRequestContent content){
		boolean found = findUserRatings(DaoFactory.getRatingDao()::findUserSingersRating , content,
				EntityType.SINGER.name().toLowerCase());
		if (found){
			content.getRequestAttributes().put(AttributeName.ENTITY_COMMAND.toString(), CommandType.FIND_COMPOSITION.name().toLowerCase());
			content.getRequestAttributes().put(AttributeName.RATING_COMMAND.toString(),
					CommandType.FIND_USER_SINGERS_RATINGS.name().toLowerCase());
		}
		return found;
	}

	/**
	 * Find user genres rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findUserGenresRating(SessionRequestContent content){
		boolean found = findUserRatings(DaoFactory.getRatingDao()::findUserGenresRating , content,
				EntityType.GENRE.name().toLowerCase());
		if (found){
			content.getRequestAttributes().put(AttributeName.ENTITY_COMMAND.toString(), CommandType.FIND_GENRE.name().toLowerCase());
			content.getRequestAttributes().put(AttributeName.RATING_COMMAND.toString(),
					CommandType.FIND_USER_GENRES_RATINGS.name().toLowerCase());
		}
		return found;
	}

	/**
	 * Find user ratings.
	 *
	 * @param daoMethod the dao method
	 * @param content the content
	 * @param comparingEntity the comparing entity
	 * @return true, if successful
	 */
	private boolean findUserRatings(UserRatingsDaoMethod daoMethod, SessionRequestContent content, String comparingEntity){
		Map<String, String[]> parameters = content.getRequestParameters();
		String comparatorName = parameters.get(ParameterName.RATING_TYPE.toString())[0];
		long userId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		RatingsComparator comparator = RatingsComparator.getInstance(comparatorName);
		List<EntityRating> ratings = new LinkedList<>();
		boolean found = false;
		try {
			ratings = daoMethod.apply(userId);
			found = true;
			ratings.sort(comparator);
			content.getRequestAttributes().put(AttributeName.USER_RATING.toString(), ratings);
			content.getRequestAttributes().put(AttributeName.SPECIFIC_RATING.toString(), true);
			content.getRequestAttributes().put(AttributeName.COMPARATOR.toString(), comparator.name());
			content.getRequestAttributes().put(AttributeName.COMPARING_ENTITY.toString(), comparingEntity);
		} catch (DaoException e) {
			logger.error("Ratings are not reachable ", e);
		}
		return found;
	}


	/**
	 * The Interface UserRatingsDaoMethod.
	 */
	private static interface UserRatingsDaoMethod {

		/**
		 * Apply.
		 *
		 * @param userId the user id
		 * @return the list
		 * @throws DaoException the dao exception
		 */
		List<EntityRating> apply(long userId) throws DaoException;
	}


}