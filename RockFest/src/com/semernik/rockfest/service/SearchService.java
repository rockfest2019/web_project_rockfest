package com.semernik.rockfest.service;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.SearchDao;
import com.semernik.rockfest.entity.Composition;
import com.semernik.rockfest.entity.Entity;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Singer;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchService.
 */
public class SearchService {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static SearchService instance= new SearchService();

	/**
	 * Gets the single instance of SearchService.
	 *
	 * @return single instance of SearchService
	 */
	public static SearchService getInstance(){
		return instance;
	}

	/**
	 * Instantiates a new search service.
	 */
	private SearchService () {}

	/**
	 * Find compositions by pattern.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCompositionsByPattern(SessionRequestContent content){
		String pattern = content.getRequestParameters().get(ParameterName.SEARCH_PATTERN.toString())[0];
		SearchDao dao = DaoFactory.getSearchDao();
		Collection<Composition> foundCompositions = null;
		boolean found = false;
		try {
			foundCompositions = dao.compositionsSearch(pattern);
			content.getRequestAttributes().put(AttributeName.COMPOSITIONS.toString(), foundCompositions);
			found = true;
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		return found;
	}

	/**
	 * Find singers by pattern.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingersByPattern(SessionRequestContent content){
		String pattern = content.getRequestParameters().get(ParameterName.SEARCH_PATTERN.toString())[0];
		SearchDao dao = DaoFactory.getSearchDao();
		Collection<Singer> foundSingers = null;
		boolean found = false;
		try {
			foundSingers = dao.singersSearch(pattern);
			content.getRequestAttributes().put(AttributeName.SINGERS.toString(), foundSingers);
			found = true;
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		return found;
	}

	/**
	 * Find genres by pattern.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenresByPattern(SessionRequestContent content){
		String pattern = content.getRequestParameters().get(ParameterName.SEARCH_PATTERN.toString())[0];
		SearchDao dao = DaoFactory.getSearchDao();
		Collection<Genre> foundGenres = null;
		boolean found = false;
		try {
			foundGenres = dao.genresSearch(pattern);
			content.getRequestAttributes().put(AttributeName.GENRES.toString(), foundGenres);
			found = true;
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		return found;
	}

	/**
	 * Find entities by pattern.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findEntitiesByPattern(SessionRequestContent content){
		String pattern = content.getRequestParameters().get(ParameterName.SEARCH_PATTERN.toString())[0];
		SearchDao dao = DaoFactory.getSearchDao();
		Collection<Entity> foundEntities = null;
		boolean found = false;
		try {
			foundEntities = dao.entitiesSearch(pattern);
			content.getRequestAttributes().put(AttributeName.ENTITIES.toString(), foundEntities);
			found = true;
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		return found;
	}

}
