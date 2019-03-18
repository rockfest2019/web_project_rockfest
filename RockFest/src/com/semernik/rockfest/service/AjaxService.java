package com.semernik.rockfest.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.container.ErrorMessagesContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.GenresDao;
import com.semernik.rockfest.entity.EntityRating;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.EntityType;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.RatingUtil;
import com.semernik.rockfest.util.AjaxUtil;
import com.semernik.rockfest.util.RatingsDaoMethod;

// TODO: Auto-generated Javadoc
/**
 * The Class AjaxService.
 */
public class AjaxService {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static AjaxService instance = new AjaxService();

	/** The Constant MAX_ELEMENTS_COUNT. */
	private final static int MAX_ELEMENTS_COUNT = 5;

	/**
	 * Instantiates a new ajax service.
	 */
	private AjaxService(){}

	/**
	 * Gets the single instance of AjaxService.
	 *
	 * @return single instance of AjaxService
	 */
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
		Map<String, String[]> parameters = content.getRequestParameters();
		String comparatorName = parameters.get(ParameterName.RATING_TYPE.toString())[0];
		RatingUtil ratingUtil = RatingUtil.getInstance();
		RatingsDaoMethod daoMethod = ratingUtil.findRatingsDaoMethod(comparingEntity, comparatorName);
		int position = Integer.parseInt(parameters.get(ParameterName.POSITION.toString())[0]);
		int elementsCount = Integer.parseInt(parameters.get(ParameterName.ELEMENTS_COUNT.toString())[0]);
		elementsCount = (elementsCount > MAX_ELEMENTS_COUNT) ? MAX_ELEMENTS_COUNT : elementsCount;
		List<EntityRating> ratings = new LinkedList<>();
		boolean found = false;
		try {
			ratings = daoMethod.apply(position, elementsCount);
			found = true;
			ratings.forEach(RatingUtil.getInstance()::transformToAverageRatings);
			AjaxUtil utilAjax = AjaxUtil.getInstance();
			String ajaxResponse = utilAjax.generateHTMLRatings(comparingEntity, comparatorName, ratings, position, elementsCount);
			content.setAjaxResponse(ajaxResponse);
		} catch (DaoException e) {
			logger.error("Ratings are not reachable ", e);
			String ratingFailure = ErrorMessagesContainer.findMessage(AttributeName.RATING_FAILURE.toString());
			content.setAjaxResponse(ratingFailure);
		}
		return found;
	}

	public boolean findGenresForComposition(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = new LinkedList<>();
		boolean found = false;
		try {
			genres = dao.findAllGenres();
			found = true;
			AjaxUtil utilAjax = AjaxUtil.getInstance();
			String ajaxResponse = utilAjax.generateHTMLGenresForComposition(compositionId, genres);
			content.setAjaxResponse(ajaxResponse);
		} catch (DaoException e) {
			logger.error("Data access error ", e);
			String genresFailure = ErrorMessagesContainer.findMessage(AttributeName.GENRE_ERROR.toString());
			content.setAjaxResponse(genresFailure);
		}
		return found;
	}


}
