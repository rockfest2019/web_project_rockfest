package com.semernik.rockfest.validator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.semernik.rockfest.container.RequiredParametersContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;


// TODO: Auto-generated Javadoc
/**
 * The Class GeneralValidator.
 */
public class GeneralValidator {

	/** The instance. */
	private static GeneralValidator instance;

	/** The description length. */
	private final int DESCRIPTION_LENGTH = 65535;

	/** The title length. */
	private final int TITLE_LENGTH = 100;

	/** The comment content length. */
	private final int COMMENT_CONTENT_LENGTH = 200;

	/** The id pattern. */
	private final Pattern ID_PATTERN = Pattern.compile("\\d{1,18}");

	/** The position pattern. */
	private final Pattern POSITION_PATTERN = Pattern.compile("\\d{1,9}");

	/** The elements count pattern. */
	private final Pattern ELEMENTS_COUNT_PATTERN = Pattern.compile("\\d{1,9}");

	/** The year pattern. */
	private final Pattern YEAR_PATTERN = Pattern.compile("[12][90]\\d\\d");

	/** The rating type pattern. */
	private final Pattern RATING_TYPE_PATTERN = Pattern.compile("general|melody|text|music|vocal");

	/** The search pattern. */
	private final Pattern SEARCH_PATTERN = Pattern.compile("(\\p{Alnum}|\\p{Blank}|[а-яА-Я0-9]|[,.!?-]){1,100}");

	/** The invalid parameters. */
	private final String INVALID_PARAMETERS = "invalid parameters";

	/**
	 * Gets the single instance of GeneralValidator.
	 *
	 * @return single instance of GeneralValidator
	 */
	public static GeneralValidator getInstance() {
		if (instance == null){
			instance = new GeneralValidator();
		}
		return instance;
	}

	/**
	 * Valid singer.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validSinger (SessionRequestContent content){
		return validGenreOrSinger(content);
	}

	/**
	 * Valid genre.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validGenre (SessionRequestContent content){
		return validGenreOrSinger(content);
	}


	/**
	 * Valid genre or singer.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	private boolean validGenreOrSinger(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validUser(content) || requestedParametersNamesMiss(content, RequiredParametersContainer.getGenreSet())){
			return false;
		}
		Map <String, String[]> requestParameters = content.getRequestParameters();
		String description = requestParameters.get(ParameterName.DESCRIPTION.toString())[0];
		String title = requestParameters.get(ParameterName.TITLE.toString())[0];
		boolean valid = false;
		if ( description.length() <= DESCRIPTION_LENGTH && title.length() <= TITLE_LENGTH){
			valid = true;
		}
		return valid;
	}

	/**
	 * Valid id.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	private boolean validId(String id) {
		Matcher matcher = ID_PATTERN.matcher(id);
		return matcher.matches();
	}

	/**
	 * Requested parameters names miss.
	 *
	 * @param content the content
	 * @param requestedParametersSet the requested parameters set
	 * @return true, if successful
	 */
	private boolean requestedParametersNamesMiss(SessionRequestContent content, Set<ParameterName> requestedParametersSet) {
		Map <String, String[]> requestParameters = content.getRequestParameters();
		Set <String> names = requestParameters.keySet();
		Set <String> requestedNames = requestedParametersSet.stream()
				.map(a -> a.toString()).collect(Collectors.toSet());
		return (!names.containsAll(requestedNames));
	}

	/**
	 * Valid composition.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validComposition(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validUser(content) || requestedParametersNamesMiss(content, RequiredParametersContainer.getCompositionSet())
				|| !genresIdsAreValid(content)){
			return false;
		}
				Map <String, String[]> requestParameters = content.getRequestParameters();
		String title = requestParameters.get(ParameterName.TITLE.toString())[0];
		String year = requestParameters.get(ParameterName.YEAR.toString())[0];
		String singerId = requestParameters.get(ParameterName.SINGER_ID.toString())[0];
		boolean valid = false;
		if (title.length() <= TITLE_LENGTH && validYearDescription(year) && validId(singerId)){
			valid = true;
		}
		return valid;
	}

	/**
	 * Genres ids are valid.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	private boolean genresIdsAreValid(SessionRequestContent content){
		Map <String, String[]> requestParameters = content.getRequestParameters();
		boolean valid = true;
		String [] genresIds = requestParameters.get(ParameterName.GENRES_IDS.toString());
		if (genresIds != null){
			int i = 0;
			int len = genresIds.length;
			while (valid && i < len){
				valid = validId(genresIds[i++]);
			}
		}
		return valid;
	}

	/**
	 * Valid year description.
	 *
	 * @param yearDescription the year description
	 * @return true, if successful
	 */
	private boolean validYearDescription(String yearDescription) {
		Matcher matcher = YEAR_PATTERN.matcher(yearDescription);
		return matcher.matches();
	}

	/**
	 * Valid id.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validId (SessionRequestContent content){
		Map <String, String[]> parameters = content.getRequestParameters();
		String [] idsArray = parameters.get(ParameterName.ID.toString());
		boolean valid = false;
		if (idsArray != null && validId(idsArray[0])){
			valid = true;
		}
		return valid;
	}

	/**
	 * Valid comment.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validComment(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validUser(content) || requestedParametersNamesMiss(content, RequiredParametersContainer.getCommentSet())){
			return false;
		}
		Map <String, String[]> parameters = content.getRequestParameters();
		String commentContent = parameters.get(ParameterName.COMMENT_CONTENT.toString())[0];
		String id = parameters.get(ParameterName.ID.toString())[0];
		boolean valid = false;
		if (commentContent.length() <= COMMENT_CONTENT_LENGTH && validId(id)){
			valid = true;
		}
		return valid;
	}

	/**
	 * Valid composition link.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validCompositionLink(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validUser(content) || requestedParametersNamesMiss(content, RequiredParametersContainer.getCompositionLinkSet())){
			return false;
		}
		Map <String, String[]> parameters = content.getRequestParameters();
		String compositionId = parameters.get(ParameterName.ID.toString())[0];
		String linkUrl = parameters.get(ParameterName.URL.toString())[0];
		boolean valid = false;
		if (validId(compositionId) && validUrl(linkUrl)){
			valid = true;
		}
		return valid;
	}

	/**
	 * Valid url.
	 *
	 * @param urlDescription the url description
	 * @return true, if successful
	 */
	private boolean validUrl(String urlDescription) {
		boolean valid = false;
		try {
			URL url = new URL(urlDescription);
			URI uri = url.toURI();
			valid = true;
		} catch (MalformedURLException | URISyntaxException e) {}
		return valid;
	}

	/**
	 * Valid rating type.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validRatingType(SessionRequestContent content){
		Map <String, String[]> parameters = content.getRequestParameters();
		String [] ratingTypes = parameters.get(ParameterName.RATING_TYPE.toString());
		boolean valid = false;
		if (ratingTypes != null && validRatingType(ratingTypes[0])){
			valid = true;
		} else {
			content.getSessionAttributes().put(AttributeName.RATING_FAILURE.toString(), AttributeName.RATING_FAILURE.toString());
		}
		return valid;
	}

	/**
	 * Valid rating type.
	 *
	 * @param ratingType the rating type
	 * @return true, if successful
	 */
	private boolean validRatingType(String ratingType) {
		Matcher matcher = RATING_TYPE_PATTERN.matcher(ratingType);
		return matcher.matches();
	}

	/**
	 * Valid user rating type.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validUserRatingType(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validUser(content)){
			return false;
		}
		Map <String, String[]> parameters = content.getRequestParameters();
		String [] ratingTypes = parameters.get(ParameterName.RATING_TYPE.toString());
		boolean valid = false;
		if (ratingTypes != null && validRatingType(ratingTypes[0])){
			valid = true;
		}
		return valid;
	}

	/**
	 * Valid ajax rating type.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validAjaxRatingType(SessionRequestContent content){
		if (content == null || requestedParametersNamesMiss(content, RequiredParametersContainer.getAjaxRatingSet())){
			content.setAjaxResponse(INVALID_PARAMETERS);
			return false;
		}
		Map <String, String[]> parameters = content.getRequestParameters();
		String ratingType = parameters.get(ParameterName.RATING_TYPE.toString())[0];
		String position = parameters.get(ParameterName.POSITION.toString())[0];
		String elementsCount = parameters.get(ParameterName.ELEMENTS_COUNT.toString())[0];
		boolean valid = false;
		if (validRatingType(ratingType) && validPosition(position) && validElementsCountn(elementsCount)){
			valid = true;
		} else {
			content.setAjaxResponse(INVALID_PARAMETERS);
		}
		return valid;
	}

	/**
	 * Valid elements countn.
	 *
	 * @param elementsCount the elements count
	 * @return true, if successful
	 */
	private boolean validElementsCountn(String elementsCount) {
		Matcher matcher = ELEMENTS_COUNT_PATTERN.matcher(elementsCount);
		return matcher.matches();
	}

	/**
	 * Valid position.
	 *
	 * @param position the position
	 * @return true, if successful
	 */
	private boolean validPosition(String position) {
		Matcher matcher = POSITION_PATTERN.matcher(position);
		return matcher.matches();
	}

	/**
	 * Valid search pattern.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validSearchPattern(SessionRequestContent content){
		if (content == null){
			return false;
		}
		String [] searchPatterns = content.getRequestParameters().get(ParameterName.SEARCH_PATTERN.toString());
		boolean valid = false;
		if (searchPatterns != null && validSearchPattern(searchPatterns[0])){
			valid = true;
		} else {
			content.getRequestAttributes().put(AttributeName.ERROR_MESSAGE.toString(), INVALID_PARAMETERS);
		}
		return valid;
	}

	/**
	 * Valid search pattern.
	 *
	 * @param searchPattern the search pattern
	 * @return true, if successful
	 */
	private boolean validSearchPattern(String searchPattern) {
		Matcher matcher = SEARCH_PATTERN.matcher(searchPattern);
		return matcher.matches();
	}

	/**
	 * Valid description update.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validDescriptionUpdate(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validUser(content)
				|| requestedParametersNamesMiss(content, RequiredParametersContainer.getDescriptionUpdateSet())){
			return false;
		}
		String description = content.getRequestParameters().get(ParameterName.DESCRIPTION.toString())[0];
		String entityId = content.getRequestParameters().get(ParameterName.ID.toString())[0];
		boolean valid = false;
		if (description.length() <= DESCRIPTION_LENGTH && validId(entityId)){
			valid = true;
		}
		return valid;
	}

	/**
	 * Valid composition genres.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validCompositionGenres(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validUser(content)){
			return false;
		}
		String [] compositionIds = content.getRequestParameters().get(ParameterName.ID.toString());
		boolean valid = false;
		if (compositionIds != null && validId(compositionIds[0]) && genresIdsAreValid(content)){
			valid = true;
		}
		return valid;
	}

	/**
	 * Valid composition year.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validCompositionYear(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validUser(content)
				|| requestedParametersNamesMiss(content, RequiredParametersContainer.getCompositionYearUpdateSet())){
			return false;
		}
		String id = content.getRequestParameters().get(ParameterName.ID.toString())[0];
		String year = content.getRequestParameters().get(ParameterName.YEAR.toString())[0];
		boolean valid = false;
		if (validId(id) && validYearDescription(year)){
			valid = true;
		}
		return valid;
	}

	public boolean validNewTitle(SessionRequestContent content){
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validAdmin(content)
				|| requestedParametersNamesMiss(content, RequiredParametersContainer.getTitleSet())){
			return false;
		}
		String id = content.getRequestParameters().get(ParameterName.ID.toString())[0];
		String title = content.getRequestParameters().get(ParameterName.TITLE.toString())[0];
		boolean valid = false;
		if (title.length() <= TITLE_LENGTH && validId(id)){
			valid = true;
		}
		return valid;
	}

	public boolean validCommentDeletion(SessionRequestContent content){
		return validIdAndAdmin(content);
	}

	public boolean validCompositionLinkDeletion(SessionRequestContent content){
		return validIdAndAdmin(content);
	}

	private boolean validIdAndAdmin(SessionRequestContent content) {
		UserValidator userValidator = UserValidator.getInstance();
		if (!userValidator.validAdmin(content)){
			return false;
		}
		String [] Ids = content.getRequestParameters().get(ParameterName.ID.toString());
		boolean valid = false;
		if (Ids != null && validId(Ids[0])){
			valid = true;
		}
		return valid;
	}

}
