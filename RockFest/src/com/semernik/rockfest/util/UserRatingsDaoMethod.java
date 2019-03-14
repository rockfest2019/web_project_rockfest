package com.semernik.rockfest.util;

import java.util.List;

import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.entity.EntityRating;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserRatingsDaoMethod.
 */
public interface UserRatingsDaoMethod {

	/**
	 * Apply.
	 *
	 * @param userId the user id
	 * @param positionFrom the position from
	 * @param elementsCount the elements count
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<EntityRating> apply(long userId, int positionFrom, int elementsCount) throws DaoException;

}
