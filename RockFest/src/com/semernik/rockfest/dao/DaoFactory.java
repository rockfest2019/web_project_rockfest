package com.semernik.rockfest.dao;

import com.semernik.rockfest.dao.impl.CommentsDaoImpl;
import com.semernik.rockfest.dao.impl.CompositionLinksDaoImpl;
import com.semernik.rockfest.dao.impl.CompositionsDaoImpl;
import com.semernik.rockfest.dao.impl.GenresDaoImpl;
import com.semernik.rockfest.dao.impl.IdsDaoImpl;
import com.semernik.rockfest.dao.impl.RatingDaoImpl;
import com.semernik.rockfest.dao.impl.SearchDaoImpl;
import com.semernik.rockfest.dao.impl.SingersDaoImpl;
import com.semernik.rockfest.dao.impl.UsersDaoImpl;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Dao objects.
 */
public class DaoFactory {

	/**
	 * Gets the comments dao.
	 *
	 * @return the comments dao
	 */
	public static CommentsDao getCommentsDao(){
		return CommentsDaoImpl.getInstance();

	}

	/**
	 * Gets the composition links dao.
	 *
	 * @return the composition links dao
	 */
	public static CompositionLinksDao getCompositionLinksDao(){
		return CompositionLinksDaoImpl.getInstance();
	}

	/**
	 * Gets the compositions dao.
	 *
	 * @return the compositions dao
	 */
	public static CompositionsDao getCompositionsDao(){
		return CompositionsDaoImpl.getInstance();
	}

	/**
	 * Gets the genres dao.
	 *
	 * @return the genres dao
	 */
	public static GenresDao getGenresDao(){
		return GenresDaoImpl.getInstance();
	}

	/**
	 * Gets the singers dao.
	 *
	 * @return the singers dao
	 */
	public static SingersDao getSingersDao(){
		return SingersDaoImpl.getInstance();
	}

	/**
	 * Gets the users dao.
	 *
	 * @return the users dao
	 */
	public static UsersDao getUsersDao(){
		return UsersDaoImpl.getInstance();
	}

	/**
	 * Gets the rating dao.
	 *
	 * @return the rating dao
	 */
	public static RatingDao getRatingDao(){
		return RatingDaoImpl.getInstance();
	}

	/**
	 * Gets the ids dao.
	 *
	 * @return the ids dao
	 */
	public static IdsDao getIdsDao(){
		return IdsDaoImpl.getInstance();
	}

	/**
	 * Gets the search dao.
	 *
	 * @return the search dao
	 */
	public static SearchDao getSearchDao(){
		return SearchDaoImpl.getInstance();
	}

}
