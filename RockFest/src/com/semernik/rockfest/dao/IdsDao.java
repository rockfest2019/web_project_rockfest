package com.semernik.rockfest.dao;

import java.util.Optional;

import com.semernik.rockfest.entity.LastIdsContainer;


// TODO: Auto-generated Javadoc
/**
 * The Interface IdsDao.
 */
public interface IdsDao extends Dao {

	/**
	 * Find last ids.
	 *
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<LastIdsContainer> findLastIds() throws DaoException;

}
