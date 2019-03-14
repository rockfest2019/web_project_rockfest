package com.semernik.rockfest.dao;

import java.util.Collection;

import com.semernik.rockfest.entity.Link;



// TODO: Auto-generated Javadoc
/**
 * The Interface CompositionLinksDao.
 */
public interface CompositionLinksDao extends Dao {

	/**
	 * Find composition links by composition id.
	 *
	 * @param compositionId the composition id
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<Link> findCompositionLinksByCompositionId(long compositionId) throws DaoException;

	/**
	 * Save composition link.
	 *
	 * @param link the link
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveCompositionLink (Link link) throws DaoException;

	/**
	 * Delete composition link.
	 *
	 * @param linkId the link id
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean deleteCompositionLink (long linkId) throws DaoException;


}
