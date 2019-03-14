package com.semernik.rockfest.service;

import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.container.ErrorMessagesContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.GenresDao;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.entity.Genre.GenreBuilder;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.GeneratorId;


// TODO: Auto-generated Javadoc
/**
 * The Class GenreService.
 */
public class GenreService {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static GenreService instance = new GenreService();

	/**
	 * Gets the single instance of GenreService.
	 *
	 * @return single instance of GenreService
	 */
	public static GenreService getInstance(){
		return instance;
	}

	/**
	 * Instantiates a new genre service.
	 */
	private GenreService () {}

	/**
	 * Save genre.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean saveGenre(SessionRequestContent content) {
		Map<String, String[]> parameters = content.getRequestParameters();
		String genreTitle = (parameters.get(ParameterName.TITLE.toString()))[0];
		String description = (parameters.get(ParameterName.DESCRIPTION.toString()))[0];
		long authorId = (Long)content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		long descriptionEditorId = authorId;
		long genreId = GeneratorId.getInstance().generateGenreId();
		Date addingDate = new Date(System.currentTimeMillis());
		GenreBuilder builder = new GenreBuilder();
		Genre genre = builder.genreId(genreId)
				.title(genreTitle)
				.description(description)
				.addingDate(addingDate)
				.authorId(authorId)
				.descriptionEditorId(descriptionEditorId)
				.build();
		GenresDao dao = DaoFactory.getGenresDao();
		boolean saved = false;
		try {
			saved = dao.saveGenre(genre);
		} catch (DaoException e) {
			logger.error("Genre is not saved", e);
		}
		if (saved){
			Map <String, Object> attrs = content.getRequestAttributes();
			attrs.put(AttributeName.GENRE.toString(), genre);
		}
		return saved;
	}

	/**
	 * Find genres.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenres(SessionRequestContent content){
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = null;
		boolean found = true;
		try {
			genres = dao.findAllGenres();
		} catch (DaoException e) {
			found = false;
			logger.error("Genres are not reachable", e);
		}
		if(found){
			content.getRequestAttributes().put(AttributeName.GENRES.toString(), genres);
		}
		return true;
	}

	/**
	 * Find genres by composition id.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenresByCompositionId(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		Long compositionId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		GenresDao dao = DaoFactory.getGenresDao();
		Collection<Genre> genres = null;
		boolean found = true;
		try {
			genres = dao.findGenresByCompositionId(compositionId);
		} catch (DaoException e) {
			logger.error("Genres are not reachable", e);
		}
		if (found){
			content.getRequestAttributes().put(AttributeName.GENRES.toString(), genres);
		}
		return found;
	}

	/**
	 * Find genre by id.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findGenreById(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		Long genreId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		GenresDao dao = DaoFactory.getGenresDao();
		Optional<Genre> genre = Optional.empty();
		try {
			genre = dao.findGenreById(genreId);
		} catch (DaoException e) {
			logger.error("Genre is not reachable", e);
		}
		boolean found = false;
		if (genre.isPresent()){
			found = true;
			content.getRequestAttributes().put(AttributeName.GENRE.toString(), genre.get());
		}
		if (content.getSessionAttributes().get(AttributeName.USER_ID.toString()) != null){
			addUserGenreRatings(content);
		}
		return found;
	}

	/**
	 * Adds the user genre ratings.
	 *
	 * @param content the content
	 */
	private void addUserGenreRatings(SessionRequestContent content) {
		RatingService ratingService = RatingService.getInstance();
		if (!ratingService.findGenreUserRating(content)){
			String key = AttributeName.RATING_FAILURE.toString();
			String message = ErrorMessagesContainer.findMessage(key);
			content.getRequestAttributes().put(key, message);
		}
	}

	/**
	 * Update genre description.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean updateGenreDescription(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		Long genreId = Long.parseLong(parameters.get(ParameterName.ID.toString())[0]);
		String newDescription = parameters.get(ParameterName.DESCRIPTION.toString())[0];
		Long userId = (Long)content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		GenresDao dao = DaoFactory.getGenresDao();
		boolean updated = false;
		try {
			updated = dao.updateGenreDescription(genreId, newDescription, userId);
		} catch (DaoException e) {
			logger.error("Data access error", e);
		}
		return updated;
	}

}
