package com.semernik.rockfest.util;

import java.util.List;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.RatingDao;
import com.semernik.rockfest.entity.EntityRating;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.CommandType;
import com.semernik.rockfest.type.EntityType;
import com.semernik.rockfest.type.ParameterName;


// TODO: Auto-generated Javadoc
/**
 * The Class RatingUtil.
 */
public class RatingUtil {

	private static RatingUtil instance = new RatingUtil();

	private static RatingDao ratingDao = DaoFactory.getRatingDao();

	private final static String DELIMITER = "_";

	private RatingUtil() {}

	public static RatingUtil getInstance() {
		return instance;
	}

	/**
	 * Find rating command.
	 *
	 * @param entityType the entity type
	 * @return the string
	 */
	public String findRatingCommand(String entityType) {
		CommandType commandType = CommandType.FIND_COMPOSITIONS_RATINGS;
		if (entityType.equals(EntityType.SINGER.name().toLowerCase())){
			commandType = CommandType.FIND_SINGERS_RATINGS;
		} else if (entityType.equals(EntityType.GENRE.name().toLowerCase())){
			commandType = CommandType.FIND_GENRES_RATINGS;
		}
		return commandType.name().toLowerCase();
	}

	/**
	 * Find entity command.
	 *
	 * @param entityType the entity type
	 * @return the string
	 */
	public String findEntityCommand(String entityType) {
		CommandType commandType = CommandType.FIND_COMPOSITION;
		if (entityType.equals(EntityType.SINGER.name().toLowerCase())){
			commandType = CommandType.FIND_SINGER;
		} else if (entityType.equals(EntityType.GENRE.name().toLowerCase())){
			commandType = CommandType.FIND_GENRE;
		}
		return commandType.name().toLowerCase();
	}

	/**
	 * Transform to average ratings.
	 *
	 * @param rating the rating
	 */
	public void transformToAverageRatings(EntityRating rating){
		int votedUsersCount = rating.getVotedUsersCount();
		if (votedUsersCount == 0){
			return;
		}
		rating.setMelodyRating( roundToHundredth(rating.getMelodyRating() / votedUsersCount));
		rating.setTextRating( roundToHundredth(rating.getTextRating() / votedUsersCount));
		rating.setMusicRating( roundToHundredth(rating.getMusicRating() / votedUsersCount));
		rating.setVocalRating( roundToHundredth(rating.getVocalRating() / votedUsersCount));
	}

	private double roundToHundredth(double d) {
		d *=100;
		d = Math.round(d);
		d /= 100;
		return d;
	}

	/**
	 * Find ajax command.
	 *
	 * @param entityType the entity type
	 * @return the string
	 */
	public String findAjaxCommand(String entityType) {
		CommandType commandType = CommandType.COMPOSITIONS_RATINGS_AJAX;
		if (entityType.equals(EntityType.SINGER.name().toLowerCase())){
			commandType = CommandType.SINGERS_RATINGS_AJAX;
		} else if (entityType.equals(EntityType.GENRE.name().toLowerCase())){
			commandType = CommandType.GENRES_RATINGS_AJAX;
		}
		return commandType.name().toLowerCase();
	}

	/**
	 * Find ratings dao method.
	 *
	 * @param entity the entity
	 * @param comparator the comparator
	 * @return the ratings dao method
	 */
	public RatingsDaoMethod findRatingsDaoMethod(String entity, String comparator){
		String methodKey = entity + DELIMITER + comparator;
		return RatingDaoMehtod.valueOf(methodKey.toUpperCase());
	}

	/**
	 * The Enum RatingDaoMehtod.
	 */
	private static enum RatingDaoMehtod implements RatingsDaoMethod {

		COMPOSITION_GENERAL(ratingDao::findCompositionsRatingPartOrderedByGeneralRating),
		COMPOSITION_MELODY(ratingDao::findCompositionsRatingPartOrderedByMelodyRating),
		COMPOSITION_TEXT(ratingDao::findCompositionsRatingPartOrderedByTextRating),
		COMPOSITION_MUSIC(ratingDao::findCompositionsRatingPartOrderedByMusicRating),
		COMPOSITION_VOCAL(ratingDao::findCompositionsRatingPartOrderedByVocalRating),
		SINGER_GENERAL(ratingDao::findSingersRatingPartOrderedByGeneralRating),
		SINGER_MELODY(ratingDao::findSingersRatingPartOrderedByMelodyRating),
		SINGER_TEXT(ratingDao::findSingersRatingPartOrderedByTextRating),
		SINGER_MUSIC(ratingDao::findSingersRatingPartOrderedByMusicRating),
		SINGER_VOCAL(ratingDao::findSingersRatingPartOrderedByVocalRating),
		GENRE_GENERAL(ratingDao::findGenresRatingPartOrderedByGeneralRating),
		GENRE_MELODY(ratingDao::findGenresRatingPartOrderedByMelodyRating),
		GENRE_TEXT(ratingDao::findGenresRatingPartOrderedByTextRating),
		GENRE_MUSIC(ratingDao::findGenresRatingPartOrderedByMusicRating),
		GENRE_VOCAL(ratingDao::findGenresRatingPartOrderedByVocalRating),
		;

		private RatingsDaoMethod daoMethod;


		/**
		 * Instantiates a new rating dao mehtod.
		 *
		 * @param daoMethod the dao method
		 */
		RatingDaoMehtod (RatingsDaoMethod daoMethod){
			this.daoMethod = daoMethod;
		}

		/* (non-Javadoc)
		 * @see com.semernik.rockFest.util.RatingsDaoMethod#apply(int, int)
		 */
		@Override
		public List<EntityRating> apply(int positionFrom, int elementsCount) throws DaoException {
			return daoMethod.apply(positionFrom, elementsCount);
		}
	}

	/**
	 * Find user ratings dao method.
	 *
	 * @param entity the entity
	 * @param comparator the comparator
	 * @return the user ratings dao method
	 */
	public UserRatingsDaoMethod findUserRatingsDaoMethod(String entity, String comparator){
		String methodKey = entity + DELIMITER + comparator;
		return UserRatingDaoMehtod.valueOf(methodKey.toUpperCase());
	}

	/**
	 * The Enum UserRatingDaoMehtod.
	 */
	private static enum UserRatingDaoMehtod implements UserRatingsDaoMethod {

		COMPOSITION_GENERAL(ratingDao::findCompositionsUserRatingPartOrderedByGeneralRating),
		COMPOSITION_MELODY(ratingDao::findCompositionsUserRatingPartOrderedByMelodyRating),
		COMPOSITION_TEXT(ratingDao::findCompositionsUserRatingPartOrderedByTextRating),
		COMPOSITION_MUSIC(ratingDao::findCompositionsUserRatingPartOrderedByMusicRating),
		COMPOSITION_VOCAL(ratingDao::findCompositionsUserRatingPartOrderedByVocalRating),
		;

		private UserRatingsDaoMethod daoMethod;


		/**
		 * Instantiates a new user rating dao mehtod.
		 *
		 * @param daoMethod the dao method
		 */
		UserRatingDaoMehtod (UserRatingsDaoMethod daoMethod){
			this.daoMethod = daoMethod;
		}


		@Override
		public List<EntityRating> apply(long userId, int positionFrom, int elementsCount) throws DaoException {
			return daoMethod.apply(userId, positionFrom, elementsCount);
		}
	}

	public EntityRating getUserRatingFromContent(SessionRequestContent content) {
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		Long userId = (Long) content.getSessionAttribute(AttributeName.USER_ID.toString());
		int melodyRating = Integer.parseInt(content.getParameter(ParameterName.MELODY_RATING.toString()));
		int textRating = Integer.parseInt(content.getParameter(ParameterName.TEXT_RATING.toString()));
		int musicRating = Integer.parseInt(content.getParameter(ParameterName.MUSIC_RATING.toString()));
		int vocalRating = Integer.parseInt(content.getParameter(ParameterName.VOCAL_RATING.toString()));
		EntityRating rating = new EntityRating(userId, compositionId, melodyRating, textRating, musicRating, vocalRating);
		return rating;
	}

}
