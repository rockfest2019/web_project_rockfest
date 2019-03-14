package com.semernik.rockfest.service;

import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.container.ErrorMessagesContainer;
import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.dao.DaoException;
import com.semernik.rockfest.dao.DaoFactory;
import com.semernik.rockfest.dao.UsersDao;
import com.semernik.rockfest.entity.User;
import com.semernik.rockfest.entity.UserProfile;
import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;
import com.semernik.rockfest.util.Cryptor;
import com.semernik.rockfest.util.EmailUtil;
import com.semernik.rockfest.util.GeneratorId;

// TODO: Auto-generated Javadoc
/**
 * The Class UserService.
 */
public class UserService {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The instance. */
	private static UserService instance = new UserService();

	/** The Constant INVALID_PASSWORD. */
	private static final String INVALID_PASSWORD = "invalid_password";

	/** The Constant INVALID_LOGIN. */
	private static final String INVALID_LOGIN = "invalid_login";

	/** The Constant OCCUPIED_LOGIN. */
	private static final String OCCUPIED_LOGIN = "occupied_login";

	/** The Constant LOGIN_CHANGED. */
	private static final String LOGIN_CHANGED = "login was changed";

	/** The Constant EMAIL_CHANGED. */
	private static final String EMAIL_CHANGED = "email was changed";

	/** The Constant PASSWORD_CHANGED. */
	private static final String PASSWORD_CHANGED = "password was changed";


	/**
	 * Gets the single instance of UserService.
	 *
	 * @return single instance of UserService
	 */
	public static UserService getInstance(){
		return instance;
	}

	/**
	 * Instantiates a new user service.
	 */
	private UserService(){}

	/**
	 * Login user.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean loginUser(SessionRequestContent content){
		boolean logged = true;
		Map<String, String[]> parameters = content.getRequestParameters();
		String login = parameters.get(ParameterName.USER_LOGIN.toString())[0];
		UsersDao dao = DaoFactory.getUsersDao();
		Optional<User> optional = Optional.empty();
		try {
			optional = dao.findUserByLogin(login);
		} catch (DaoException e) {
			logged = false;
			logger.error("Can't reach users data", e);
		}
		if (logged && confirmUser(content, optional)){
			addUserToSessionContent(optional.get(), content);
		} else {
			logged = false;
		}
		content.setUsingCurrentPage(true);
		return logged;
	}

	/**
	 * Logout user.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean logoutUser(SessionRequestContent content){
		Map <String, Object> sessionAttrs = content.getSessionAttributes();
		sessionAttrs.put(AttributeName.USER_ID.toString(), null);
		sessionAttrs.put(AttributeName.USER_LOGIN.toString(), null);
		sessionAttrs.put(AttributeName.ROLE.toString(), null);
		content.setUsingCurrentPage(true);
		return true;
	}

	/**
	 * Confirm user.
	 *
	 * @param content the content
	 * @param optional the optional
	 * @return true, if successful
	 */
	private boolean confirmUser(SessionRequestContent content, Optional<User> optional) {
		boolean confirmed = false;
		Map <String,String[]> requestParameters = content.getRequestParameters();
		String password = requestParameters.get(ParameterName.PASSWORD.toString())[0];
		String loginFailureDescription;
		if (optional.isPresent() && validPassword(password, optional.get())){
			confirmed = true;
		} else if (optional.isPresent()){
			loginFailureDescription = ErrorMessagesContainer.findMessage(INVALID_PASSWORD);
			content.getRequestAttributes().put(AttributeName.LOGIN_FAILURE.toString(), loginFailureDescription);
		} else {
			loginFailureDescription = ErrorMessagesContainer.findMessage(INVALID_LOGIN);
			content.getRequestAttributes().put(AttributeName.LOGIN_FAILURE.toString(), loginFailureDescription);
		}
		return confirmed;
	}

	/**
	 * Valid password.
	 *
	 * @param password the password
	 * @param user the user
	 * @return true, if successful
	 */
	private boolean validPassword(String password, User user) {
		String encryptedPassword = user.getPassword();
		Cryptor cryptor = Cryptor.getInstance();
		String key = cryptor.findKey(user.getUserId(), user.getLogin());
		String decryptedPassword = cryptor.decrypt(encryptedPassword, key);
		return password.equals(decryptedPassword);
	}

	/**
	 * Adds the user to session content.
	 *
	 * @param user the user
	 * @param content the content
	 */
	private void addUserToSessionContent(User user, SessionRequestContent content) {
		Map<String,Object> sessionAttributes = content.getSessionAttributes();
		sessionAttributes.put(AttributeName.USER_ID.toString(), user.getUserId());
		sessionAttributes.put(AttributeName.USER_LOGIN.toString(), user.getLogin());
		sessionAttributes.put(AttributeName.ROLE.toString(), user.getRole());
	}

	/**
	 * Change locale.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean changeLocale(SessionRequestContent content){
		Map <String,String[]> requestParameters = content.getRequestParameters();
		String locale = requestParameters.get(ParameterName.LOCALE.toString())[0];
		Map<String,Object> sessionAttributes = content.getSessionAttributes();
		sessionAttributes.put(AttributeName.LOCALE.toString(), locale);
		content.setUsingCurrentPage(true);
		return true;
	}

	/**
	 * Change user password.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean changeUserPassword(SessionRequestContent content){
		boolean changed = false;
		Map<String, String[]> parameters = content.getRequestParameters();
		long userId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		String oldPassword = parameters.get(ParameterName.PASSWORD.toString())[0];
		String newPassword = parameters.get(ParameterName.NEW_PASSWORD.toString())[0];
		UsersDao dao = DaoFactory.getUsersDao();
		Optional<User> optional = Optional.empty();
		try {
			optional = dao.findUserById(userId);
			if (optional.isPresent()){
				changed = changePassword(optional.get(), oldPassword, newPassword);
			}
		} catch (DaoException e) {
			logger.error("Can't reach users data", e);
		}
		if(changed){
			content.getRequestAttributes().put(AttributeName.USER_PROFILE_CHANGE.toString(), PASSWORD_CHANGED);
		}
		return changed;
	}

	/**
	 * Change password.
	 *
	 * @param user the user
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	private boolean changePassword(User user, String oldPassword, String newPassword) throws DaoException{
		boolean changed = false;
		Cryptor cryptor = Cryptor.getInstance();
		String key = cryptor.findKey(user.getUserId(), user.getLogin());
		String decryptedPassword = cryptor.decrypt(user.getPassword(), key);
		if (decryptedPassword.equals(oldPassword)){
			String encryptedPassword = cryptor.encrypt(newPassword, key);
			user.setPassword(encryptedPassword);
			changed = saveUserInfo(user);
		}
		if (changed){
			String email = cryptor.decrypt(user.getEmail(), key);
			EmailUtil emailService = EmailUtil.getInstance();
			emailService.sendNewPasswordMessage(email, user.getLogin(), newPassword);
		}
		return changed;
	}

	/**
	 * Save user info.
	 *
	 * @param user the user
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	private boolean saveUserInfo(User user) throws DaoException{
		UsersDao dao = DaoFactory.getUsersDao();
		return dao.saveNewUserInfo(user);

	}

	/**
	 * Send user password to email.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean sendUserPasswordToEmail(SessionRequestContent content){
		boolean send = false;
		Map<String, String[]> parameters = content.getRequestParameters();
		String login = parameters.get(ParameterName.USER_LOGIN.toString())[0];
		UsersDao dao = DaoFactory.getUsersDao();
		Optional<User> optional = Optional.empty();
		try {
			optional = dao.findUserByLogin(login);
		} catch (DaoException e) {
			logger.error("Can't reach users data", e);
		}
		if (optional.isPresent()){
			send = sendPasswordToEmail(optional.get());
			send = true;
		}
		return send;
	}


	/**
	 * Send password to email.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	private boolean sendPasswordToEmail(User user) {
		boolean send = true;
		Cryptor cryptor = Cryptor.getInstance();
		String key = cryptor.findKey(user.getUserId(), user.getLogin());
		String email = cryptor.decrypt(user.getEmail(), key);
		String password = cryptor.decrypt(user.getPassword(), key);
		EmailUtil emailService = EmailUtil.getInstance();
		emailService.sendPasswordMessage(email, user.getLogin(), password);
		return send;
	}

	/**
	 * Change login.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean changeLogin(SessionRequestContent content){
		boolean changed = false;
		Map<String, String[]> parameters = content.getRequestParameters();
		long userId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		String password = parameters.get(ParameterName.PASSWORD.toString())[0];
		String newLogin = parameters.get(ParameterName.NEW_LOGIN.toString())[0];
		UsersDao dao = DaoFactory.getUsersDao();
		Optional<User> optional = Optional.empty();
		try {
			optional = dao.findUserById(userId);
			if(optional.isPresent()){
				changed = changeLogin(optional.get(), password, newLogin, content);
			}
		} catch (DaoException e) {
			logger.error("Can't reach users data", e);
		}
		if (changed){
			content.getSessionAttributes().put(AttributeName.USER_LOGIN.toString(), newLogin);
			content.getRequestAttributes().put(AttributeName.USER_PROFILE_CHANGE.toString(), LOGIN_CHANGED);
		}
		return changed;
	}

	/**
	 * Change login.
	 *
	 * @param user the user
	 * @param password the password
	 * @param newLogin the new login
	 * @param content the content
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	private boolean changeLogin(User user, String password, String newLogin, SessionRequestContent content) throws DaoException {
		boolean changed = false;
		Cryptor cryptor = Cryptor.getInstance();
		String key = cryptor.findKey(user.getUserId(), user.getLogin());
		String decryptedPassword = cryptor.decrypt(user.getPassword(), key);
		boolean passwordConfirmed = decryptedPassword.equals(password);
		if (passwordConfirmed && loginIsFree(newLogin)){
			reencryptUserInfo(user, newLogin);
			changed = saveUserInfo(user);
		} else if (passwordConfirmed){
			String loginFailureDescription = ErrorMessagesContainer.findMessage(OCCUPIED_LOGIN);
			content.getRequestAttributes().put(AttributeName.USER_PROFILE_CHANGE.toString(), loginFailureDescription);
		} else {
			String errorMessage = ErrorMessagesContainer.findMessage(INVALID_PASSWORD);
			content.getRequestAttributes().put(AttributeName.USER_PROFILE_CHANGE.toString(), errorMessage);
		}
		return changed;
	}

	/**
	 * Login is free.
	 *
	 * @param login the login
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	private boolean loginIsFree(String login) throws DaoException {
		UsersDao dao = DaoFactory.getUsersDao();
		Optional<User> userWithSameLogin = dao.findUserByLogin(login);
		return !userWithSameLogin.isPresent();
	}

	/**
	 * Reencrypt user info.
	 *
	 * @param user the user
	 * @param newLogin the new login
	 */
	private void reencryptUserInfo(User user, String newLogin) {
		Cryptor cryptor = Cryptor.getInstance();
		long userId = user.getUserId();
		String oldKey = cryptor.findKey(userId, user.getLogin());
		String decryptedPassword = cryptor.decrypt(user.getPassword(), oldKey);
		String decryptedEmail = cryptor.decrypt(user.getEmail(), oldKey);
		String newKey = cryptor.findKey(userId, newLogin);
		String encryptedPassword = cryptor.encrypt(decryptedPassword, newKey);
		String encryptedEmail = cryptor.encrypt(decryptedEmail, newKey);
		user.setLogin(newLogin);
		user.setPassword(encryptedPassword);
		user.setEmail(encryptedEmail);
	}

	/**
	 * Change email.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean changeEmail(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		long userId = (Long) content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		String password = parameters.get(ParameterName.PASSWORD.toString())[0];
		String newEmail = parameters.get(ParameterName.NEW_EMAIL.toString())[0];
		UsersDao dao = DaoFactory.getUsersDao();
		boolean changed = false;
		try {
			Optional<User> optional = dao.findUserById(userId);
			if (optional.isPresent()){
				changed = changeEmail(optional.get(), password, newEmail, content);
			}
		} catch (DaoException e) {
			logger.error("Can't reach users data", e);
		}
		if (changed){
			content.getRequestAttributes().put(AttributeName.USER_PROFILE_CHANGE.toString(), EMAIL_CHANGED);
		}
		return changed;
	}

	/**
	 * Change email.
	 *
	 * @param user the user
	 * @param password the password
	 * @param newEmail the new email
	 * @param content the content
	 * @return true, if successful
	 * @throws DaoException the dao exception
	 */
	private boolean changeEmail(User user, String password, String newEmail, SessionRequestContent content) throws DaoException{
		boolean changed = false;
		Cryptor cryptor = Cryptor.getInstance();
		String key = cryptor.findKey(user.getUserId(), user.getLogin());
		String decryptedPassword = cryptor.decrypt(user.getPassword(), key);
		if (decryptedPassword.equals(password)){
			String encryptedEmail = cryptor.encrypt(newEmail, key);
			user.setEmail(encryptedEmail);
			changed = saveUserInfo(user);
		} else {
			String errorMessage = ErrorMessagesContainer.findMessage(INVALID_PASSWORD);
			content.getRequestAttributes().put(AttributeName.USER_PROFILE_CHANGE.toString(), errorMessage);
		}
		return changed;
	}

	/**
	 * Register new user.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean registerNewUser(SessionRequestContent content){
		Map<String, String[]> parameters = content.getRequestParameters();
		String password = parameters.get(ParameterName.PASSWORD.toString())[0];
		String login = parameters.get(ParameterName.USER_LOGIN.toString())[0];
		String email = parameters.get(ParameterName.USER_EMAIL.toString())[0];
		UsersDao dao = DaoFactory.getUsersDao();
		boolean registered = false;
		try {
			Optional<User> userWithSameLogin = dao.findUserByLogin(login);
			if (userWithSameLogin.isPresent()){
				String loginFailureDescription = ErrorMessagesContainer.findMessage(OCCUPIED_LOGIN);
				content.getRequestAttributes().put(AttributeName.REGISTRATION_FAILURE.toString(), loginFailureDescription);
			} else {
				registered = registerNewUser(login, password, email);
			}
		} catch (DaoException e) {
			logger.error("Failed to get users data", e);
		}
		if (registered) {
			content.getRequestAttributes().put(AttributeName.REGISTRATION_SUCCESS.toString(), AttributeName.REGISTRATION_SUCCESS);
			EmailUtil emailService = EmailUtil.getInstance();
			emailService.sendRegistrationSuccessMessage(email, login);
		}
		return registered;
	}

	/**
	 * Register new user.
	 *
	 * @param login the login
	 * @param password the password
	 * @param email the email
	 * @return true, if successful
	 */
	private boolean registerNewUser(String login, String password, String email) {
		boolean registered = false;
		long userId = GeneratorId.getInstance().generateUserId();
		Cryptor cryptor = Cryptor.getInstance();
		String key = cryptor.findKey(userId, login);
		String encryptedPassword = cryptor.encrypt(password, key);
		String encryptedEmail = cryptor.encrypt(email, key);
		User newUser = new User(userId, login, encryptedPassword, encryptedEmail);
		UsersDao dao = DaoFactory.getUsersDao();
		try {
			registered = dao.saveUser(newUser);
		} catch (DaoException e) {
			logger.error("Failed to save new user", e);
		}
		return registered;
	}

	/**
	 * Find user profile.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	public boolean findUserProfile(SessionRequestContent content){
		long userId = (Long)content.getSessionAttributes().get(AttributeName.USER_ID.toString());
		String login = (String) content.getSessionAttributes().get(AttributeName.USER_LOGIN.toString());
		Optional<UserProfile> optional = Optional.empty();
		UsersDao dao = DaoFactory.getUsersDao();
		boolean found = false;
		try {
			optional = dao.findUserProfileByUserId(userId);
			if (optional.isPresent()){
				UserProfile profile = optional.get();
				Cryptor cryptor = Cryptor.getInstance();
				String key = cryptor.findKey(userId, login);
				String decryptedEmail = cryptor.decrypt(profile.getEmail(), key);
				profile.setEmail(decryptedEmail);
				content.getRequestAttributes().put(AttributeName.USER_PROFILE.toString(), profile);
				found = true;
			}
		} catch (DaoException e) {
			logger.error("Can't reach users data", e);
		}
		return found;
	}

}
