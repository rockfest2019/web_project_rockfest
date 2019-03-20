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
import com.semernik.rockfest.type.ErrorMessage;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.ErrorUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchService.
 */
public class SearchService {


	private static Logger logger = LogManager.getLogger();
	private static SearchService instance= new SearchService();

	public static SearchService getInstance(){
		return instance;
	}

	private SearchService () {}

	/**
	 * Find compositions by pattern.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCompositionsByPattern(SessionRequestContent content){
		String pattern = content.getParameter(ParameterName.SEARCH_PATTERN.toString());
		SearchDao dao = DaoFactory.getSearchDao();
		Collection<Composition> foundCompositions = null;
		boolean found = false;
		try {
			foundCompositions = dao.compositionsSearch(pattern);
			content.addRequestAttribute(AttributeName.COMPOSITIONS.toString(), foundCompositions);
			found = true;
			content.removeCurrentPageAttribute(ErrorMessage.SEARCH_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SEARCH_ERROR, content);
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
		String pattern = content.getParameter(ParameterName.SEARCH_PATTERN.toString());
		SearchDao dao = DaoFactory.getSearchDao();
		Collection<Singer> foundSingers = null;
		boolean found = false;
		try {
			foundSingers = dao.singersSearch(pattern);
			content.addRequestAttribute(AttributeName.SINGERS.toString(), foundSingers);
			found = true;
			content.removeCurrentPageAttribute(ErrorMessage.SEARCH_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SEARCH_ERROR, content);
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
		String pattern = content.getParameter(ParameterName.SEARCH_PATTERN.toString());
		SearchDao dao = DaoFactory.getSearchDao();
		Collection<Genre> foundGenres = null;
		boolean found = false;
		try {
			foundGenres = dao.genresSearch(pattern);
			content.getRequestAttributes().put(AttributeName.GENRES.toString(), foundGenres);
			found = true;
			content.removeCurrentPageAttribute(ErrorMessage.SEARCH_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SEARCH_ERROR, content);
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
		String pattern = content.getParameter(ParameterName.SEARCH_PATTERN.toString());
		SearchDao dao = DaoFactory.getSearchDao();
		Collection<Entity> foundEntities = null;
		boolean found = false;
		try {
			foundEntities = dao.entitiesSearch(pattern);
			content.addRequestAttribute(AttributeName.ENTITIES.toString(), foundEntities);
			found = true;
			content.removeCurrentPageAttribute(ErrorMessage.SEARCH_ERROR.toString());
		} catch (DaoException e) {
			logger.error("Data access error", e);
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.SEARCH_ERROR, content);
		}
		return found;
	}

}
