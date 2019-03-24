package com.semernik.rockfest.entity;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityRating.
 */
public class EntityRating {

	/** The melody rating. */
	private double melodyRating;

	/** The text rating. */
	private double textRating;

	/** The music rating. */
	private double musicRating;

	/** The vocal rating. */
	private double vocalRating;

	/** The voted users count. */
	private int votedUsersCount;

	/** The entity id. */
	private long entityId;

	/** The entity title. */
	private String entityTitle;

	/** The user id. */
	private long userId;

	/**
	 * Instantiates a new entity rating.
	 *
	 * @param melodyRating the melody rating
	 * @param textRating the text rating
	 * @param musicRating the music rating
	 * @param vocalRating the vocal rating
	 */
	public EntityRating(double melodyRating, double textRating, double musicRating, double vocalRating) {
		this(0, null, 0, melodyRating, textRating, musicRating, vocalRating, 0);
	}

	/**
	 * Instantiates a new entity rating.
	 *
	 * @param userId the user id
	 * @param entityId the entity id
	 * @param melodyRating the melody rating
	 * @param textRating the text rating
	 * @param musicRating the music rating
	 * @param vocalRating the vocal rating
	 */
	public EntityRating(long userId, long entityId, double melodyRating, double textRating, double musicRating, double vocalRating) {
		this(userId, null, entityId, melodyRating, textRating, musicRating, vocalRating, 0);
	}

	/**
	 * Instantiates a new entity rating.
	 *
	 * @param entityTitle the entity title
	 * @param entityId the entity id
	 * @param melodyRating the melody rating
	 * @param textRating the text rating
	 * @param musicRating the music rating
	 * @param vocalRating the vocal rating
	 */
	public EntityRating(String entityTitle, long entityId, double melodyRating, double textRating,
			double musicRating, double vocalRating) {
		this(0, entityTitle, entityId, melodyRating, textRating, musicRating, vocalRating, 0);
	}

	/**
	 * Instantiates a new entity rating.
	 *
	 * @param title the title
	 * @param entityId the entity id
	 * @param melody the melody
	 * @param text the text
	 * @param music the music
	 * @param vocal the vocal
	 * @param votedUsersCount the voted users count
	 */
	public EntityRating(String title, long entityId, double melody, double text, double music, double vocal,
			int votedUsersCount) {
		this(0, title, entityId, melody, text, music, vocal, votedUsersCount);
	}

	/**
	 * Instantiates a new entity rating.
	 *
	 * @param userId the user id
	 * @param entityTitle the entity title
	 * @param entityId the entity id
	 * @param melodyRating the melody rating
	 * @param textRating the text rating
	 * @param musicRating the music rating
	 * @param vocalRating the vocal rating
	 * @param votedUsersCount the voted users count
	 */
	public EntityRating(long userId, String entityTitle, long entityId, double melodyRating, double textRating,
			double musicRating, double vocalRating, int votedUsersCount) {
		this.userId = userId;
		this.entityTitle = entityTitle;
		this.entityId = entityId;
		this.melodyRating = melodyRating;
		this.textRating = textRating;
		this.musicRating = musicRating;
		this.vocalRating = vocalRating;
		this.votedUsersCount = votedUsersCount;
	}




	/**
	 * Gets the melody rating.
	 *
	 * @return the melody rating
	 */
	public double getMelodyRating() {
		return melodyRating;
	}

	/**
	 * Gets the text rating.
	 *
	 * @return the text rating
	 */
	public double getTextRating() {
		return textRating;
	}

	/**
	 * Gets the music rating.
	 *
	 * @return the music rating
	 */
	public double getMusicRating() {
		return musicRating;
	}

	/**
	 * Gets the vocal rating.
	 *
	 * @return the vocal rating
	 */
	public double getVocalRating() {
		return vocalRating;
	}

	/**
	 * Sets the melody rating.
	 *
	 * @param melodyRating the new melody rating
	 */
	public void setMelodyRating(double melodyRating) {
		this.melodyRating = melodyRating;
	}

	/**
	 * Sets the text rating.
	 *
	 * @param textRating the new text rating
	 */
	public void setTextRating(double textRating) {
		this.textRating = textRating;
	}

	/**
	 * Sets the music rating.
	 *
	 * @param musicRating the new music rating
	 */
	public void setMusicRating(double musicRating) {
		this.musicRating = musicRating;
	}

	/**
	 * Sets the vocal rating.
	 *
	 * @param vocalRating the new vocal rating
	 */
	public void setVocalRating(double vocalRating) {
		this.vocalRating = vocalRating;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity title.
	 *
	 * @param entityTitle the new entity title
	 */
	public void setEntityTitle(String entityTitle) {
		this.entityTitle = entityTitle;
	}

	/**
	 * Gets the entity title.
	 *
	 * @return the entity title
	 */
	public String getEntityTitle() {
		return entityTitle;
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
	 * Gets the voted users count.
	 *
	 * @return the voted users count
	 */
	public int getVotedUsersCount() {
		return votedUsersCount;
	}

	/**
	 * Sets the voted users count.
	 *
	 * @param votedUsersCount the new voted users count
	 */
	public void setVotedUsersCount(int votedUsersCount) {
		this.votedUsersCount = votedUsersCount;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public double getRating(){
		double rating = (melodyRating + textRating + musicRating + vocalRating)/4;
		rating *=100;
		rating = Math.round(rating);
		rating /= 100;
		return rating;
	}



}
