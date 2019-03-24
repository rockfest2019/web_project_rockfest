package com.semernik.rockfest.service;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.GenresDao;
import com.semernik.rockfest.entity.EntityRating;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.EntityType;
import com.semernik.rockfest.type.ErrorMessage;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.AjaxUtil;
import com.semernik.rockfest.util.RatingUtil;
import com.semernik.rockfest.util.RatingsDaoMethod;

// TODO: Auto-generated Javadoc
/**
 * The Class AjaxService.
 */
public class AjaxService {


	private static Logger logger = LogManager.getLogger();
	private static AjaxService instance = new AjaxService();
	private final static int MAX_ELEMENTS_COUNT = 50;


	private AjaxService(){}

	public static AjaxService getInstance(){
		return instance;
	}

	/**
	 * Test ajax.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean testAjax(SessionRequestContent content){
			content.setAjaxResponse("AJAX response");
			return true;
	}

	/**
	 * Find compositions ratings.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findCompositionsRatings(SessionRequestContent content){
		boolean found = findRatings(content,
				EntityType.COMPOSITION.name().toLowerCase());
		return found;
	}

	/**
	 * Find genres ratings.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenresRatings(SessionRequestContent content){
		boolean found =  findRatings(content, EntityType.GENRE.name().toLowerCase());
		return found;
	}

	/**
	 * Find singers ratings.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findSingersRatings(SessionRequestContent content){
		boolean found =  findRatings( content,  EntityType.SINGER.name().toLowerCase());
		return found;
	}


	/**
	 * Find ratings.
	 *
	 * @param content the content
	 * @param comparingEntity the comparing entity
	 * @return true, if successful
	 */
	private boolean findRatings(SessionRequestContent content, String comparingEntity){
		String comparatorName = content.getParameter(ParameterName.RATING_TYPE.toString());
		RatingUtil ratingUtil = RatingUtil.getInstance();
		RatingsDaoMethod daoMethod = ratingUtil.findRatingsDaoMethod(comparingEntity, comparatorName);
		int position = Integer.parseInt(content.getParameter(ParameterName.POSITION.toString()));
		int elementsCount = Integer.parseInt(content.getParameter(ParameterName.ELEMENTS_COUNT.toString()));
		elementsCount = (elementsCount > MAX_ELEMENTS_COUNT) ? MAX_ELEMENTS_COUNT : elementsCount;
		boolean found = false;
		try {
			found = tryFindRatings(content, daoMethod, position, elementsCount, comparingEntity, comparatorName);
		} catch (DaoException e) {
			logger.error("Ratings are not reachable ", e);
			String ratingFailure = ErrorMessage.RATING_FAILURE.findMessage();
			content.setAjaxResponse(ratingFailure);
		}
		return found;
	}

	private boolean tryFindRatings(SessionRequestContent content, RatingsDaoMethod daoMethod, int position,
			int elementsCount, String comparingEntity, String comparatorName)  throws DaoException{
		List<EntityRating> ratings = daoMethod.apply(position, elementsCount);
		ratings.forEach(RatingUtil.getInstance()::transformToAverageRatings);
		AjaxUtil utilAjax = AjaxUtil.getInstance();
		String locale = (String)content.getSessionAttribute(AttributeName.LOCALE.toString());
		String ajaxResponse = utilAjax.generateHTMLRatings(comparingEntity, comparatorName, ratings, position, elementsCount, locale);
		content.setAjaxResponse(ajaxResponse);
		return true;
	}

	public boolean findGenresForComposition(SessionRequestContent content){
		long compositionId = Long.parseLong(content.getParameter(ParameterName.ID.toString()));
		boolean found = false;
		try {
			found = tryFindGenresForComposition(content, compositionId);
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			String genresFailure = ErrorMessage.GENRE_ERROR.findMessage();
			content.setAjaxResponse(genresFailure);
		}
		return found;
	}

	private boolean tryFindGenresForComposition(SessionRequestContent content, long compositionId) throws DaoException{
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = dao.findAllGenres();
		AjaxUtil utilAjax = AjaxUtil.getInstance();
		String locale = (String)content.getSessionAttribute(AttributeName.LOCALE.toString());
		String ajaxResponse = utilAjax.generateHTMLGenresForComposition(compositionId, genres, locale);
		content.setAjaxResponse(ajaxResponse);
		return true;
	}


}
