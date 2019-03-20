package com.semernik.rockfest.validator;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.semernik.rockfest.container.RequiredParametersContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ErrorMessage;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.type.Role;
import com.semernik.rockfest.util.ErrorUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class UserValidator.
 */
public class UserValidator {


	private static UserValidator instance = new UserValidator();

	private final int LOGIN_LENGTH = 45;
	private final int PASSWORD_LENGTH = 20;
	private final Pattern RATING_PATTERN = Pattern.compile("\\d|10");
	private final Pattern ID_PATTERN = Pattern.compile("\\d{1,18}");
	private final Pattern BAN_DATE_PATTERN = Pattern.compile("\\d{1,18}");
	private final Pattern LOCALE_PATTERN = Pattern.compile("en_US|ru_RU");

	/**
	 * Gets the single instance of UserValidator.
	 *
	 * @return single instance of UserValidator
	 */
	public static UserValidator getInstance(){
		return instance;
	}

	/**
	 * Instantiates a new user validator.
	 */
	private UserValidator(){}

	/**
	 * Valid login parameters.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validLoginParameters(SessionRequestContent content){
		if (content == null || requestedParametersNamesMiss(content, RequiredParametersContainer.getLoginSet())
				|| !requestedSessionNamesMiss(content, RequiredParametersContainer.getSessionUserSet())){
			return false;
		}
		boolean valid = true;
		Map <String, String[]> requestParameters = content.getRequestParameters();
		String login = requestParameters.get(ParameterName.USER_LOGIN.toString())[0];
		String password = requestParameters.get(ParameterName.PASSWORD.toString())[0];
		if ( login.length() > LOGIN_LENGTH || password.length() > PASSWORD_LENGTH){
			valid = false;
			content.getCurrentPageAttributes().remove(ErrorMessage.LOGIN_FAILURE.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_LOGIN_PARAMETERS, ErrorMessage.LOGIN_FAILURE, content);
		}
		return valid;
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
	 * Requested session names miss.
	 *
	 * @param content the content
	 * @param requestedAttributesSet the requested attributes set
	 * @return true, if successful
	 */
	private boolean requestedSessionNamesMiss(SessionRequestContent content, Set<AttributeName> requestedAttributesSet) {
		Map <String, Object> requestParameters = content.getSessionAttributes();
		Set <String> names = requestParameters.keySet();
		Set <String> requestedNames = requestedAttributesSet.stream()
				.map(a -> a.toString()).collect(Collectors.toSet());
		return (!names.containsAll(requestedNames));
	}

	/**
	 * Valid user rating.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validUserRating(SessionRequestContent content){
		if (content == null || requestedParametersNamesMiss(content, RequiredParametersContainer.getUserRatingSet())
				|| requestedSessionNamesMiss(content, RequiredParametersContainer.getSessionUserSet())){
			return false;
		}
		boolean valid = false;
		Map <String, String[]> requestParameters = content.getRequestParameters();
		String melody = requestParameters.get(ParameterName.MELODY_RATING.toString())[0];
		String text = requestParameters.get(ParameterName.TEXT_RATING.toString())[0];
		String music = requestParameters.get(ParameterName.MUSIC_RATING.toString())[0];
		String vocal = requestParameters.get(ParameterName.VOCAL_RATING.toString())[0];
		String compositionId = requestParameters.get(ParameterName.ID.toString())[0];
		Long userId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		if ( userId != null && validId(compositionId) && validRating(melody)
				&& validRating(text) && validRating(music) && validRating(vocal)){
			valid = true;
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_USER_RATING.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_USER_RATING, content);
		}
		return valid;
	}

	/**
	 * Valid rating.
	 *
	 * @param rating the rating
	 * @return true, if successful
	 */
	private boolean validRating(String rating) {
		Matcher matcher = RATING_PATTERN.matcher(rating);
		return matcher.matches();
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
	 * Valid locale.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validLocale(SessionRequestContent content){
		if (content == null){
			return false;
		}
		boolean valid = false;
		String [] locales = content.getRequestParameters().get(ParameterName.LOCALE.toString());
		if (locales != null && locales.length > 0 && validLocale(locales[0])){
			valid = true;
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_LOCALE.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_LOCALE, content);
		}
		return valid;
	}

	/**
	 * Valid locale.
	 *
	 * @param locale the locale
	 * @return true, if successful
	 */
	private boolean validLocale(String locale) {
		Matcher matcher = LOCALE_PATTERN.matcher(locale);
		return matcher.matches();
	}

	/**
	 * Valid registration parameters.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validRegistrationParameters(SessionRequestContent content){
		if (content == null || requestedParametersNamesMiss(content, RequiredParametersContainer.getRegistrationSet())){
			return false;
		}
		boolean valid = false;
		Map <String, String[]> requestParameters = content.getRequestParameters();
		String login = requestParameters.get(ParameterName.USER_LOGIN.toString())[0];
		String password = requestParameters.get(ParameterName.PASSWORD.toString())[0];
		String email = requestParameters.get(ParameterName.USER_EMAIL.toString())[0];
		boolean validEmail = validEmail(email);
		if ( login.length() <= LOGIN_LENGTH && password.length() <= PASSWORD_LENGTH && validEmail){
			valid = true;
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_EMAIL.toString());
			content.getCurrentPageAttributes().remove(ErrorMessage.REGISTRATION_FAILURE.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_REGISTRATION_PARAMETERS, ErrorMessage.REGISTRATION_FAILURE,
					content);
			if (!validEmail){
				ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_EMAIL, content);
			}
		}
		return valid;
	}

	/**
	 * Invalid email.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	private boolean validEmail(String email) {
		boolean valid = false;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			valid = true;
		} catch (AddressException e) {

		}
		return valid;
	}

	/**
	 * Valid new email.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validNewEmail(SessionRequestContent content){
		if (!validUser(content) || requestedParametersNamesMiss(content, RequiredParametersContainer.getNewEmailSet())){
			return false;
		}
		Map <String, String[]> requestParameters = content.getRequestParameters();
		String newEmail = requestParameters.get(ParameterName.NEW_EMAIL.toString())[0];
		boolean valid = validEmail(newEmail);
		if (valid){
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_EMAIL.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_EMAIL, content);
		}
		return valid;
	}

	/**
	 * Valid new login.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validNewLogin(SessionRequestContent content){
		if (!validUser(content) || requestedParametersNamesMiss(content, RequiredParametersContainer.getNewLoginSet())){
			return false;
		}
		Map <String, String[]> requestParameters = content.getRequestParameters();
		String newLogin = requestParameters.get(ParameterName.NEW_LOGIN.toString())[0];
		boolean valid = newLogin.length() <= LOGIN_LENGTH;
		if (valid){
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_NEW_LOGIN.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_NEW_LOGIN, content);
		}
		return valid;
	}

	/**
	 * Valid new password.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validNewPassword(SessionRequestContent content){
		if (!validUser(content) || requestedParametersNamesMiss(content, RequiredParametersContainer.getNewPasswordSet())){
			return false;
		}
		Map <String, String[]> requestParameters = content.getRequestParameters();
		String newPassword = requestParameters.get(ParameterName.NEW_PASSWORD.toString())[0];
		boolean valid = newPassword.length() <= PASSWORD_LENGTH;
		if (valid){
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_NEW_PASSWORD.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_NEW_PASSWORD, content);
		}
		return valid;
	}

	/**
	 * Valid user.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validUser (SessionRequestContent content){
		boolean valid =  (content != null && !requestedSessionNamesMiss(content, RequiredParametersContainer.getSessionUserSet()));
		if (valid){
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_USER.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_USER, content);
		}
		return valid;
	}

	/**
	 * Valid login.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean validLogin (SessionRequestContent content){
		if (content == null){
			return false;
		}
		String[] loginArray = content.getRequestParameters().get(ParameterName.USER_LOGIN.toString());
		boolean valid = false;
		if (loginArray != null && loginArray[0].length() <= LOGIN_LENGTH){
			valid = true;
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_LOGIN.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_LOGIN, content);
		}
		return valid;
	}

	public boolean validAdmin (SessionRequestContent content){
		if (!validUser(content)){
			return false;
		}
		String role = (String)content.getSessionAttributes().get(AttributeName.ROLE.toString());
		boolean valid =  role.equals(Role.ADMIN.toString());
		if (valid){
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_ADMIN.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_ADMIN, content);
		}
		return valid;
	}

	public boolean validUserBanDate (SessionRequestContent content){
		if (!validAdmin(content) || requestedParametersNamesMiss(content, RequiredParametersContainer.getUserBanDateSet())){
			return false;
		}
		Map <String, String[]> requestParameters = content.getRequestParameters();
		String banDate = requestParameters.get(ParameterName.DATE.toString())[0];
		String userId = requestParameters.get(ParameterName.ID.toString())[0];
		boolean valid = false;
		if (validBanDate(banDate) && validId(userId)){
			valid = true;
			content.getCurrentPageAttributes().remove(ErrorMessage.INVALID_DATE.toString());
		} else {
			ErrorUtil.addErrorMessageTotContent(ErrorMessage.INVALID_DATE, content);
		}
		return valid;
	}

	private boolean validBanDate(String banDate) {
		Matcher matcher = BAN_DATE_PATTERN.matcher(banDate);
		return matcher.matches();
	}

}
