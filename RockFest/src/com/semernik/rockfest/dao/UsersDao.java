package com.semernik.rockfest.dao;


import java.util.Collection;
import java.util.Optional;

import com.semernik.rockfest.entity.User;
import com.semernik.rockfest.entity.UserProfile;


// TODO: Auto-generated Javadoc
/**
 * The Interface UsersDao.
 */
public interface UsersDao extends Dao {

	/**
	 * Find composition author by composition id.
	 *
	 * @param compositionId the composition id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<User> findCompositionAuthorByCompositionId (int compositionId) throws DaoException;

	/**
	 * Find composition year editor by composition id.
	 *
	 * @param compositionId the composition id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<User> findCompositionYearEditorByCompositionId (int compositionId) throws DaoException;

	/**
	 * Find user by login.
	 *
	 * @param login the login
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<User> findUserByLogin (String login) throws DaoException;

	/**
	 * Save user.
	 *
	 * @param user the user
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveUser(User user) throws DaoException;

	/**
	 * Find all users.
	 *
	 * @return the collection
	 * @throws DaoException the dao exception
	 */
	Collection<User> findAllUsers() throws DaoException;

	/**
	 * Find user by id.
	 *
	 * @param userId the user id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<User> findUserById(long userId) throws DaoException;

	/**
	 * Save new user info.
	 *
	 * @param user the user
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	boolean saveNewUserInfo (User user) throws DaoException;

	/**
	 * Find user profile by user id.
	 *
	 * @param userId the user id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<UserProfile> findUserProfileByUserId(long userId) throws DaoException;

}
