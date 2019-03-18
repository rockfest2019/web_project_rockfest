package com.semernik.rockfest.entity;

import java.sql.Date;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Class Composition.
 */
public class Composition {

	/** The composition id. */
	private long compositionId;

	/** The composition title. */
	private String compositionTitle;

	/** The singer id. */
	private long singerId;

	/** The singer title. */
	private String singerTitle;

	/** The year. */
	private String year;

	/** The adding date. */
	private Date addingDate;

	/** The melody rating. */
	private int melodyRating;

	/** The text rating. */
	private int textRating;

	/** The music rating. */
	private int musicRating;

	/** The vocal rating. */
	private int vocalRating;

	/** The voted users count. */
	private int votedUsersCount;

	/** The author id. */
	private long authorId;

	/** The year editor id. */
	private long yearEditorId;

	/** The genre editor id. */
	private long genreEditorId;

	/** The author. */
	private String author;

	/** The year editor. */
	private String yearEditor;

	/** The genre editor. */
	private String genreEditor;

	/** The genres ids. */
	private Collection<Long> genresIds;

	/** The genres. */
	private Collection<Genre> genres;


	/**
	 * Instantiates a new composition.
	 *
	 * @param compositionId the composition id
	 */
	public Composition(long compositionId) {
		this.compositionId = compositionId;
	}

	/**
	 * Instantiates a new composition.
	 *
	 * @param builder the builder
	 */
	private Composition(CompositionBuilder builder) {
		compositionId = builder.compositionId;
		compositionTitle = builder.compositionTitle;
		singerId = builder.singerId;
		singerTitle = builder.singerTitle;
		year = builder.year;
		addingDate = builder.addingDate;
		melodyRating = builder.melodyRating;
		textRating = builder.textRating;
		musicRating = builder.musicRating;
		vocalRating = builder.vocalRating;
		votedUsersCount = builder.votedUsersCount;
		authorId = builder.authorId;
		yearEditorId = builder.yearEditorId;
		genreEditorId = builder.genreEditorId;
		author = builder.author;
		yearEditor = builder.yearEditor;
		genreEditor = builder.genreEditor;
		genresIds = builder.genresIds;
		genres = builder.genres;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return compositionTitle;
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
	 * Gets the year.
	 *
	 * @return the year
	 */
	public String getYear() {
		return year;
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
	 * Gets the voted users count.
	 *
	 * @return the voted users count
	 */
	public int getVotedUsersCount() {
		return votedUsersCount;
	}

	/**
	 * Gets the composition id.
	 *
	 * @return the composition id
	 */
	public long getCompositionId() {
		return compositionId;
	}

	/**
	 * Gets the singer title.
	 *
	 * @return the singer title
	 */
	public String getSingerTitle() {
		return singerTitle;
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
	 * Gets the year editor id.
	 *
	 * @return the year editor id
	 */
	public long getYearEditorId() {
		return yearEditorId;
	}

	/**
	 * Gets the genres ids.
	 *
	 * @return the genres ids
	 */
	public Collection<Long> getGenresIds() {
		return genresIds;
	}

	/**
	 * Gets the genre editor id.
	 *
	 * @return the genre editor id
	 */
	public long getGenreEditorId() {
		return genreEditorId;
	}

	/**
	 * Gets the year editor.
	 *
	 * @return the year editor
	 */
	public String getYearEditor() {
		return yearEditor;
	}

	/**
	 * Gets the genre editor.
	 *
	 * @return the genre editor
	 */
	public String getGenreEditor() {
		return genreEditor;
	}

	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Gets the genres.
	 *
	 * @return the genres
	 */
	public Collection<Genre> getGenres() {
		return genres;
	}
	
	


	public void setCompositionTitle(String compositionTitle) {
		this.compositionTitle = compositionTitle;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setGenreEditorId(long genreEditorId) {
		this.genreEditorId = genreEditorId;
	}

	public void setGenres(Collection<Genre> genres) {
		this.genres = genres;
	}




	/**
	 * The Class CompositionBuilder.
	 */
	public static class CompositionBuilder {

		/** The composition id. */
		private long compositionId;

		/** The composition title. */
		private String compositionTitle;

		/** The singer id. */
		private long singerId;

		/** The singer title. */
		private String singerTitle;

		/** The year. */
		private String year;

		/** The adding date. */
		private Date addingDate;

		/** The melody rating. */
		private int melodyRating;

		/** The text rating. */
		private int textRating;

		/** The music rating. */
		private int musicRating;

		/** The vocal rating. */
		private int vocalRating;

		/** The voted users count. */
		private int votedUsersCount;

		/** The author id. */
		private long authorId;

		/** The year editor id. */
		private long yearEditorId;

		/** The genre editor id. */
		private long genreEditorId;

		/** The author. */
		private String author;

		/** The year editor. */
		private String yearEditor;

		/** The genre editor. */
		private String genreEditor;

		/** The genres ids. */
		private Collection<Long> genresIds;

		/** The genres. */
		private Collection<Genre> genres;


		/**
		 * Composition id.
		 *
		 * @param compositionId the composition id
		 * @return the composition builder
		 */
		public CompositionBuilder compositionId(long compositionId) {
			this.compositionId = compositionId;
			return this;
		}

		/**
		 * Composition title.
		 *
		 * @param compositionTitle the composition title
		 * @return the composition builder
		 */
		public CompositionBuilder compositionTitle(String compositionTitle) {
			this.compositionTitle = compositionTitle;
			return this;
		}

		/**
		 * Singer id.
		 *
		 * @param singerId the singer id
		 * @return the composition builder
		 */
		public CompositionBuilder singerId(long singerId) {
			this.singerId = singerId;
			return this;
		}

		/**
		 * Singer title.
		 *
		 * @param singerTitle the singer title
		 * @return the composition builder
		 */
		public CompositionBuilder singerTitle(String singerTitle) {
			this.singerTitle = singerTitle;
			return this;
		}

		/**
		 * Year.
		 *
		 * @param year the year
		 * @return the composition builder
		 */
		public CompositionBuilder year(String year) {
			this.year = year;
			return this;
		}

		/**
		 * Adding date.
		 *
		 * @param addingDate the adding date
		 * @return the composition builder
		 */
		public CompositionBuilder addingDate(Date addingDate) {
			this.addingDate = addingDate;
			return this;
		}

		/**
		 * Melody rating.
		 *
		 * @param melodyRating the melody rating
		 * @return the composition builder
		 */
		public CompositionBuilder melodyRating(int melodyRating) {
			this.melodyRating = melodyRating;
			return this;
		}

		/**
		 * Text rating.
		 *
		 * @param textRating the text rating
		 * @return the composition builder
		 */
		public CompositionBuilder textRating(int textRating) {
			this.textRating = textRating;
			return this;
		}

		/**
		 * Music rating.
		 *
		 * @param musicRating the music rating
		 * @return the composition builder
		 */
		public CompositionBuilder musicRating(int musicRating) {
			this.musicRating = musicRating;
			return this;
		}

		/**
		 * Vocal rating.
		 *
		 * @param vocalRating the vocal rating
		 * @return the composition builder
		 */
		public CompositionBuilder vocalRating(int vocalRating) {
			this.vocalRating = vocalRating;
			return this;
		}

		/**
		 * Voted users count.
		 *
		 * @param votedUsersCount the voted users count
		 * @return the composition builder
		 */
		public CompositionBuilder votedUsersCount(int votedUsersCount) {
			this.votedUsersCount = votedUsersCount;
			return this;
		}

		/**
		 * Author id.
		 *
		 * @param authorId the author id
		 * @return the composition builder
		 */
		public CompositionBuilder authorId(long authorId) {
			this.authorId = authorId;
			return this;
		}

		/**
		 * Year editor id.
		 *
		 * @param yearEditorId the year editor id
		 * @return the composition builder
		 */
		public CompositionBuilder yearEditorId(long yearEditorId) {
			this.yearEditorId = yearEditorId;
			return this;
		}

		/**
		 * Genre editor id.
		 *
		 * @param genreEditorId the genre editor id
		 * @return the composition builder
		 */
		public CompositionBuilder genreEditorId(long genreEditorId) {
			this.genreEditorId = genreEditorId;
			return this;
		}

		/**
		 * Author.
		 *
		 * @param author the author
		 * @return the composition builder
		 */
		public CompositionBuilder author(String author) {
			this.author = author;
			return this;
		}

		/**
		 * Year editor.
		 *
		 * @param yearEditor the year editor
		 * @return the composition builder
		 */
		public CompositionBuilder yearEditor(String yearEditor) {
			this.yearEditor = yearEditor;
			return this;
		}

		/**
		 * Genre editor.
		 *
		 * @param genreEditor the genre editor
		 * @return the composition builder
		 */
		public CompositionBuilder genreEditor(String genreEditor) {
			this.genreEditor = genreEditor;
			return this;
		}

		/**
		 * Genres ids.
		 *
		 * @param genresIds the genres ids
		 * @return the composition builder
		 */
		public CompositionBuilder genresIds(Collection<Long> genresIds) {
			this.genresIds = genresIds;
			return this;
		}

		/**
		 * Genres.
		 *
		 * @param genres the genres
		 * @return the composition builder
		 */
		public CompositionBuilder genres(Collection<Genre> genres) {
			this.genres = genres;
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the composition
		 */
		public Composition build(){
			return new Composition(this);
		}


	}

}
