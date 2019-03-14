package com.semernik.rockfest.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class UserProfile.
 */
public class UserProfile {

	/** The added compositions count. */
	private int addedCompositionsCount;

	/** The added singers count. */
	private int addedSingersCount;

	/** The added genres count. */
	private int addedGenresCount;

	/** The assessed compositions count. */
	private int assessedCompositionsCount;

	/** The email. */
	private String email;

	/**
	 * Instantiates a new user profile.
	 *
	 * @param addedCompositionsCount the added compositions count
	 * @param addedSingersCount the added singers count
	 * @param addedGenresCount the added genres count
	 * @param assessedCompositionsCount the assessed compositions count
	 * @param email the email
	 */
	public UserProfile(int addedCompositionsCount, int addedSingersCount, int addedGenresCount,
			int assessedCompositionsCount, String email) {
		super();
		this.addedCompositionsCount = addedCompositionsCount;
		this.addedSingersCount = addedSingersCount;
		this.addedGenresCount = addedGenresCount;
		this.assessedCompositionsCount = assessedCompositionsCount;
		this.email = email;
	}

	/**
	 * Gets the added compositions count.
	 *
	 * @return the added compositions count
	 */
	public int getAddedCompositionsCount() {
		return addedCompositionsCount;
	}

	/**
	 * Gets the added singers count.
	 *
	 * @return the added singers count
	 */
	public int getAddedSingersCount() {
		return addedSingersCount;
	}

	/**
	 * Gets the added genres count.
	 *
	 * @return the added genres count
	 */
	public int getAddedGenresCount() {
		return addedGenresCount;
	}

	/**
	 * Gets the assessed compositions count.
	 *
	 * @return the assessed compositions count
	 */
	public int getAssessedCompositionsCount() {
		return assessedCompositionsCount;
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

}
