package com.semernik.rockfest.util;

import java.util.List;

import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.RatingDao;
import com.semernik.rockfest.entity.EntityRating;
import com.semernik.rockfest.type.CommandType;
import com.semernik.rockfest.type.EntityType;


// TODO: Auto-generated Javadoc
/**
 * The Class RatingUtil.
 */
public class RatingUtil {

	/** The instance. */
	private static RatingUtil instance = new RatingUtil();

	/** The rating dao. */
	private static RatingDao ratingDao = DaoFactory.getRatingDao();

	/** The Constant DELIMITER. */
	private final static String DELIMITER = "_";

	/**
	 * Instantiates a new rating util.
	 */
	private RatingUtil() {}

	/**
	 * Gets the single instance of RatingUtil.
	 *
	 * @return single instance of RatingUtil
	 */
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
		System.out.println("transformToAverageRatings");
		int votedUsersCount = rating.getVotedUsersCount();
		if (votedUsersCount == 0){
			return;
		}
		rating.setMelodyRating( roundToHundredth(rating.getMelodyRating() / votedUsersCount));
		rating.setTextRating( roundToHundredth(rating.getTextRating() / votedUsersCount));
		rating.setMusicRating( roundToHundredth(rating.getMusicRating() / votedUsersCount));
		rating.setVocalRating( roundToHundredth(rating.getVocalRating() / votedUsersCount));
		System.out.println(rating.getEntityTitle());
	}

	private double roundToHundredth(double d) {
		d *=100;
		d = Math.round(d);
		d /= 100;
		System.out.println(d);
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

		/** The composition general. */
		COMPOSITION_GENERAL(ratingDao::findCompositionsRatingPartOrderedByGeneralRating),

		/** The composition melody. */
		COMPOSITION_MELODY(ratingDao::findCompositionsRatingPartOrderedByMelodyRating),

		/** The composition text. */
		COMPOSITION_TEXT(ratingDao::findCompositionsRatingPartOrderedByTextRating),

		/** The composition music. */
		COMPOSITION_MUSIC(ratingDao::findCompositionsRatingPartOrderedByMusicRating),

		/** The composition vocal. */
		COMPOSITION_VOCAL(ratingDao::findCompositionsRatingPartOrderedByVocalRating),

		/** The singer general. */
		SINGER_GENERAL(ratingDao::findSingersRatingPartOrderedByGeneralRating),

		/** The singer melody. */
		SINGER_MELODY(ratingDao::findSingersRatingPartOrderedByMelodyRating),

		/** The singer text. */
		SINGER_TEXT(ratingDao::findSingersRatingPartOrderedByTextRating),

		/** The singer music. */
		SINGER_MUSIC(ratingDao::findSingersRatingPartOrderedByMusicRating),

		/** The singer vocal. */
		SINGER_VOCAL(ratingDao::findSingersRatingPartOrderedByVocalRating),

		/** The genre general. */
		GENRE_GENERAL(ratingDao::findGenresRatingPartOrderedByGeneralRating),

		/** The genre melody. */
		GENRE_MELODY(ratingDao::findGenresRatingPartOrderedByMelodyRating),

		/** The genre text. */
		GENRE_TEXT(ratingDao::findGenresRatingPartOrderedByTextRating),

		/** The genre music. */
		GENRE_MUSIC(ratingDao::findGenresRatingPartOrderedByMusicRating),

		/** The genre vocal. */
		GENRE_VOCAL(ratingDao::findGenresRatingPartOrderedByVocalRating),
		;

		/** The dao method. */
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

		/** The composition general. */
		COMPOSITION_GENERAL(ratingDao::findCompositionsUserRatingPartOrderedByGeneralRating),

		/** The composition melody. */
		COMPOSITION_MELODY(ratingDao::findCompositionsUserRatingPartOrderedByMelodyRating),

		/** The composition text. */
		COMPOSITION_TEXT(ratingDao::findCompositionsUserRatingPartOrderedByTextRating),

		/** The composition music. */
		COMPOSITION_MUSIC(ratingDao::findCompositionsUserRatingPartOrderedByMusicRating),

		/** The composition vocal. */
		COMPOSITION_VOCAL(ratingDao::findCompositionsUserRatingPartOrderedByVocalRating),
		;

		/** The dao method. */
		private UserRatingsDaoMethod daoMethod;


		/**
		 * Instantiates a new user rating dao mehtod.
		 *
		 * @param daoMethod the dao method
		 */
		UserRatingDaoMehtod (UserRatingsDaoMethod daoMethod){
			this.daoMethod = daoMethod;
		}

		/* (non-Javadoc)
		 * @see com.semernik.rockFest.util.UserRatingsDaoMethod#apply(long, int, int)
		 */
		@Override
		public List<EntityRating> apply(long userId, int positionFrom, int elementsCount) throws DaoException {
			return daoMethod.apply(userId, positionFrom, elementsCount);
		}
	}

}
