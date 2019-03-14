package com.semernik.rockfest.entity;

import java.sql.Date;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Class Singer.
 */
public class Singer {

	/** The singer id. */
	private  long singerId;

	/** The title. */
	private String title;

	/** The description. */
	private String description;

	/** The adding date. */
	private Date addingDate;

	/** The author id. */
	private long authorId;

	/** The author title. */
	private String authorTitle;

	/** The description editor id. */
	private long descriptionEditorId;

	/** The description editor title. */
	private String descriptionEditorTitle;

	/** The voted users count. */
	private int votedUsersCount;

	/** The melody rating. */
	private double melodyRating;

	/** The text rating. */
	private double textRating;

	/** The music rating. */
	private double musicRating;

	/** The vocal rating. */
	private double vocalRating;

	/** The comments. */
	private Collection<Comment> comments;


	/**
	 * Instantiates a new singer.
	 *
	 * @param builder the builder
	 */
	private Singer(SingerBuilder builder) {
		singerId = builder.singerId;
		title = builder.title;
		description = builder.description;
		addingDate = builder.addingDate;
		authorId = builder.authorId;
		authorTitle = builder.authorTitle;
		descriptionEditorId = builder.descriptionEditorId;
		descriptionEditorTitle = builder.descriptionEditorTitle;
		votedUsersCount = builder.votedUsersCount;
		melodyRating = builder.melodyRating;
		textRating = builder.textRating;
		musicRating = builder.musicRating;
		vocalRating = builder.vocalRating;
		comments = builder.comments;
	}

	/**
	 * Gets the singer id.
	 *
	 * @return the singer id
	 */
	public long getSingerId() {
		return singerId;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the adding date.
	 *
	 * @return the adding date
	 */
	public Date getAddingDate() {
		return addingDate;
	}

	/**
	 * Gets the author id.
	 *
	 * @return the author id
	 */
	public long getAuthorId() {
		return authorId;
	}

	/**
	 * Gets the description editor id.
	 *
	 * @return the description editor id
	 */
	public long getDescriptionEditorId() {
		return descriptionEditorId;
	}

	/**
	 * Gets the author title.
	 *
	 * @return the author title
	 */
	public String getAuthorTitle() {
		return authorTitle;
	}

	/**
	 * Gets the description editor title.
	 *
	 * @return the description editor title
	 */
	public String getDescriptionEditorTitle() {
		return descriptionEditorTitle;
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
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public Collection<Comment> getComments() {
		return comments;
	}

	/**
	 * The Class SingerBuilder.
	 */
	public static class SingerBuilder {

		/** The singer id. */
		private long singerId;

		/** The title. */
		private String title;

		/** The description. */
		private String description;

		/** The adding date. */
		private Date addingDate;

		/** The author id. */
		private long authorId;

		/** The author title. */
		private String authorTitle;

		/** The description editor id. */
		private long descriptionEditorId;

		/** The description editor title. */
		private String descriptionEditorTitle;

		/** The voted users count. */
		private int votedUsersCount;

		/** The melody rating. */
		private int melodyRating;

		/** The text rating. */
		private int textRating;

		/** The music rating. */
		private int musicRating;

		/** The vocal rating. */
		private int vocalRating;

		/** The comments. */
		private Collection<Comment> comments;

		/**
		 * Singer id.
		 *
		 * @param singerId the singer id
		 * @return the singer builder
		 */
		public SingerBuilder singerId(long singerId){
			this.singerId = singerId;
			return this;
		}

		/**
		 * Title.
		 *
		 * @param title the title
		 * @return the singer builder
		 */
		public SingerBuilder title(String title){
			this.title = title;
			return this;
		}

		/**
		 * Description.
		 *
		 * @param description the description
		 * @return the singer builder
		 */
		public SingerBuilder description(String description){
			this.description = description;
			return this;
		}

		/**
		 * Adding date.
		 *
		 * @param addingDate the adding date
		 * @return the singer builder
		 */
		public SingerBuilder addingDate(Date addingDate){
			this.addingDate = addingDate;
			return this;
		}

		/**
		 * Author id.
		 *
		 * @param authorId the author id
		 * @return the singer builder
		 */
		public SingerBuilder authorId(long authorId){
			this.authorId = authorId;
			return this;
		}

		/**
		 * Author title.
		 *
		 * @param authorTitle the author title
		 * @return the singer builder
		 */
		public SingerBuilder authorTitle(String authorTitle){
			this.authorTitle = authorTitle;
			return this;
		}

		/**
		 * Description editor id.
		 *
		 * @param descriptionEditorId the description editor id
		 * @return the singer builder
		 */
		public SingerBuilder descriptionEditorId(long descriptionEditorId){
			this.descriptionEditorId = descriptionEditorId;
			return this;
		}

		/**
		 * Description editor title.
		 *
		 * @param descriptionEditorTitle the description editor title
		 * @return the singer builder
		 */
		public SingerBuilder descriptionEditorTitle(String descriptionEditorTitle){
			this.descriptionEditorTitle = descriptionEditorTitle;
			return this;
		}

		/**
		 * Voted users count.
		 *
		 * @param votedUsersCount the voted users count
		 * @return the singer builder
		 */
		public SingerBuilder votedUsersCount(int votedUsersCount){
			this.votedUsersCount = votedUsersCount;
			return this;
		}

		/**
		 * Melody rating.
		 *
		 * @param melodyRating the melody rating
		 * @return the singer builder
		 */
		public SingerBuilder melodyRating(int melodyRating){
			this.melodyRating = melodyRating;
			return this;
		}

		/**
		 * Text rating.
		 *
		 * @param textRating the text rating
		 * @return the singer builder
		 */
		public SingerBuilder textRating(int textRating){
			this.textRating = textRating;
			return this;
		}

		/**
		 * Music rating.
		 *
		 * @param musicRating the music rating
		 * @return the singer builder
		 */
		public SingerBuilder musicRating(int musicRating){
			this.musicRating = musicRating;
			return this;
		}

		/**
		 * Vocal rating.
		 *
		 * @param vocalRating the vocal rating
		 * @return the singer builder
		 */
		public SingerBuilder vocalRating(int vocalRating){
			this.vocalRating = vocalRating;
			return this;
		}

		/**
		 * Comments.
		 *
		 * @param comments the comments
		 * @return the singer builder
		 */
		public SingerBuilder comments(Collection<Comment> comments) {
			this.comments = comments;
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the singer
		 */
		public Singer build(){
			return new Singer(this);
		}

	}

}
