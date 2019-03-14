package com.semernik.rockfest.entity;

import java.sql.Date;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Class Genre.
 */
public class Genre {

	/** The genre id. */
	private long genreId;

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
	 * Instantiates a new genre.
	 *
	 * @param builder the builder
	 */
	private Genre(GenreBuilder builder) {
		genreId = builder.genreId;
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
	 * Gets the genre id.
	 *
	 * @return the genre id
	 */
	public long getGenreId(){
		return genreId;
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
	 * Gets the melody rating.
	 *
	 * @return the melody rating
	 */
	public int getMelodyRating() {
		return melodyRating;
	}

	/**
	 * Gets the text rating.
	 *
	 * @return the text rating
	 */
	public int getTextRating() {
		return textRating;
	}

	/**
	 * Gets the music rating.
	 *
	 * @return the music rating
	 */
	public int getMusicRating() {
		return musicRating;
	}

	/**
	 * Gets the vocal rating.
	 *
	 * @return the vocal rating
	 */
	public int getVocalRating() {
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
	 * The Class GenreBuilder.
	 */
	public static class GenreBuilder {

		/** The genre id. */
		private long genreId;

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
		 * Genre id.
		 *
		 * @param genreId the genre id
		 * @return the genre builder
		 */
		public GenreBuilder genreId(long genreId){
			this.genreId = genreId;
			return this;
		}

		/**
		 * Title.
		 *
		 * @param title the title
		 * @return the genre builder
		 */
		public GenreBuilder title(String title){
			this.title = title;
			return this;
		}

		/**
		 * Description.
		 *
		 * @param description the description
		 * @return the genre builder
		 */
		public GenreBuilder description(String description){
			this.description = description;
			return this;
		}

		/**
		 * Adding date.
		 *
		 * @param addingDate the adding date
		 * @return the genre builder
		 */
		public GenreBuilder addingDate(Date addingDate){
			this.addingDate = addingDate;
			return this;
		}

		/**
		 * Author id.
		 *
		 * @param authorId the author id
		 * @return the genre builder
		 */
		public GenreBuilder authorId(long authorId){
			this.authorId = authorId;
			return this;
		}

		/**
		 * Author title.
		 *
		 * @param authorTitle the author title
		 * @return the genre builder
		 */
		public GenreBuilder authorTitle(String authorTitle){
			this.authorTitle = authorTitle;
			return this;
		}

		/**
		 * Description editor id.
		 *
		 * @param descriptionEditorId the description editor id
		 * @return the genre builder
		 */
		public GenreBuilder descriptionEditorId(long descriptionEditorId){
			this.descriptionEditorId = descriptionEditorId;
			return this;
		}

		/**
		 * Description editor title.
		 *
		 * @param descriptionEditorTitle the description editor title
		 * @return the genre builder
		 */
		public GenreBuilder descriptionEditorTitle(String descriptionEditorTitle){
			this.descriptionEditorTitle = descriptionEditorTitle;
			return this;
		}

		/**
		 * Voted users count.
		 *
		 * @param votedUsersCount the voted users count
		 * @return the genre builder
		 */
		public GenreBuilder votedUsersCount(int votedUsersCount){
			this.votedUsersCount = votedUsersCount;
			return this;
		}

		/**
		 * Melody rating.
		 *
		 * @param melodyRating the melody rating
		 * @return the genre builder
		 */
		public GenreBuilder melodyRating(int melodyRating){
			this.melodyRating = melodyRating;
			return this;
		}

		/**
		 * Text rating.
		 *
		 * @param textRating the text rating
		 * @return the genre builder
		 */
		public GenreBuilder textRating(int textRating){
			this.textRating = textRating;
			return this;
		}

		/**
		 * Music rating.
		 *
		 * @param musicRating the music rating
		 * @return the genre builder
		 */
		public GenreBuilder musicRating(int musicRating){
			this.musicRating = musicRating;
			return this;
		}

		/**
		 * Vocal rating.
		 *
		 * @param vocalRating the vocal rating
		 * @return the genre builder
		 */
		public GenreBuilder vocalRating(int vocalRating){
			this.vocalRating = vocalRating;
			return this;
		}

		/**
		 * Comments.
		 *
		 * @param comments the comments
		 * @return the genre builder
		 */
		public GenreBuilder comments(Collection<Comment> comments) {
			this.comments = comments;
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the genre
		 */
		public Genre build(){
			return new Genre(this);
		}

	}



}
