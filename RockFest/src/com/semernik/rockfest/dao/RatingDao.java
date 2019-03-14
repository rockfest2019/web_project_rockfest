package com.semernik.rockfest.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.semernik.rockfest.entity.EntityRating;


// TODO: Auto-generated Javadoc
/**
 * The Interface RatingDao.
 */
public interface RatingDao extends Dao {

	/**
	 * Save user rating.
	 *
	 * @param rating the rating
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveUserRating(EntityRating rating) throws DaoException;

	/**
	 * Find user ratings by user id.
	 *
	 * @param userId the user id
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<EntityRating> findUserRatingsByUserId(long userId) throws DaoException;

	/**
	 * Find composition user rating.
	 *
	 * @param compositionId the composition id
	 * @param userId the user id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<EntityRating> findCompositionUserRating(long compositionId, long userId) throws DaoException;

	/**
	 * Find genre user rating.
	 *
	 * @param genreId the genre id
	 * @param userId the user id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<EntityRating> findGenreUserRating(long genreId, long userId) throws DaoException;

	/**
	 * Find singer user rating.
	 *
	 * @param singerId the singer id
	 * @param userId the user id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<EntityRating> findSingerUserRating(long singerId, long userId) throws DaoException;

	/**
	 * Find user compositions rating.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findUserCompositionsRating(long userId) throws DaoException;

	/**
	 * Find compositions user rating part ordered by general rating.
	 *
	 * @param userId the user id
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsUserRatingPartOrderedByGeneralRating(long userId, int positionFrom, int elementsCount)
			throws DaoException;

	/**
	 * Find compositions user rating part ordered by melody rating.
	 *
	 * @param userId the user id
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsUserRatingPartOrderedByMelodyRating(long userId, int positionFrom, int elementsCount)
			throws DaoException;

	/**
	 * Find compositions user rating part ordered by text rating.
	 *
	 * @param userId the user id
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsUserRatingPartOrderedByTextRating(long userId, int positionFrom, int elementsCount)
			throws DaoException;

	/**
	 * Find compositions user rating part ordered by music rating.
	 *
	 * @param userId the user id
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsUserRatingPartOrderedByMusicRating(long userId, int positionFrom, int elementsCount)
			throws DaoException;

	/**
	 * Find compositions user rating part ordered by vocal rating.
	 *
	 * @param userId the user id
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsUserRatingPartOrderedByVocalRating(long userId, int positionFrom, int elementsCount)
			throws DaoException;

	/**
	 * Find user singers rating.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findUserSingersRating(long userId) throws DaoException;

	/**
	 * Find user genres rating.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findUserGenresRating(long userId) throws DaoException;

	/**
	 * Find compositions rating part ordered by general rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsRatingPartOrderedByGeneralRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find compositions rating part ordered by melody rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsRatingPartOrderedByMelodyRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find compositions rating part ordered by text rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsRatingPartOrderedByTextRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find compositions rating part ordered by music rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsRatingPartOrderedByMusicRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find compositions rating part ordered by vocal rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findCompositionsRatingPartOrderedByVocalRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find singers rating part ordered by general rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findSingersRatingPartOrderedByGeneralRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find singers rating part ordered by melody rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findSingersRatingPartOrderedByMelodyRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find singers rating part ordered by text rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findSingersRatingPartOrderedByTextRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find singers rating part ordered by music rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findSingersRatingPartOrderedByMusicRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find singers rating part ordered by vocal rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findSingersRatingPartOrderedByVocalRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find genres rating part ordered by general rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findGenresRatingPartOrderedByGeneralRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find genres rating part ordered by melody rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findGenresRatingPartOrderedByMelodyRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find genres rating part ordered by text rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findGenresRatingPartOrderedByTextRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find genres rating part ordered by music rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findGenresRatingPartOrderedByMusicRating(int positionFrom, int elementsCount) throws DaoException;

	/**
	 * Find genres rating part ordered by vocal rating.
	 *
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> findGenresRatingPartOrderedByVocalRating(int positionFrom, int elementsCount) throws DaoException;

}
