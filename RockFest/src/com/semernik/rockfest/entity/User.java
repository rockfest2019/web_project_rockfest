package com.semernik.rockfest.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {

	/** The user id. */
	private long userId;

	/** The login. */
	private String login;

	/** The role. */
	private String role;

	/** The password. */
	private String password;

	/** The email. */
	private String email;

	private long banExpirationDate;


	/**
	 * Instantiates a new user.
	 *
	 * @param userId the user id
	 * @param login the login
	 * @param role the role
	 * @param password the password
	 * @param email the email
	 */
	public User (long userId, String login, String role, String password, String email, long banExpirationDate){
		this.userId = userId;
		this.login = login;
		this.role = role;
		this.password = password;
		this.email = email;
		this.banExpirationDate = banExpirationDate;
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param userId the user id
	 * @param login the login
	 */
	public User (long userId, String login){
		this(userId, login, 0);
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param userId the user id
	 * @param login the login
	 */
	public User (long userId, String login, long banExpirationDate){
		this(userId, login, null, null, banExpirationDate);
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param userId the user id
	 * @param login the login
	 * @param password the password
	 * @param email the email
	 */
	public User (long userId, String login, String password, String email){
		this(userId, login, password, email, 0);
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param userId the user id
	 * @param login the login
	 * @param password the password
	 * @param email the email
	 */
	public User (long userId, String login, String password, String email, long banExpirationDate){
		this(userId, login, null, password, email, banExpirationDate);
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}


	public long getBanExpirationDate() {
		return banExpirationDate;
	}



}
