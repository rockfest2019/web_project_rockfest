package com.semernik.rockfest.util;

import java.util.List;

import com.semernik.rockfest.entity.EntityRating;

// TODO: Auto-generated Javadoc
/**
 * The Class RatingsAjaxUtil.
 */
public class RatingsAjaxUtil {

	/** The instance. */
	private static RatingsAjaxUtil instance = new RatingsAjaxUtil();

	/** The Constant POSITION_INPUT. */
	private static final String POSITION_INPUT = "<input type='number' id='position' value='";

	/** The Constant HIDDEN. */
	private static final String HIDDEN = "' hidden>";

	/** The Constant START_INPUT. */
	private static final String START_INPUT = "<input type='text' id='start' value='";

	/** The Constant END_INPUT. */
	private static final String END_INPUT = "<input type='text' id='end' value='";

	/** The Constant HTML_H_2. */
	private static final String HTML_H_2 = "<h2>";

	/** The Constant HTML_H_2_CLOSE. */
	private static final String HTML_H_2_CLOSE = "</h2>";

	/** The Constant NEW_LINE. */
	private static final String NEW_LINE = "\n";

	/** The Constant HTML_NEW_LINE. */
	private static final String HTML_NEW_LINE = "<br>";

	/** The Constant ID_INPUT. */
	private static final String ID_INPUT = "<input name='id' value ='";

	/** The Constant BUTTON_CLOSE. */
	private static final String BUTTON_CLOSE = "</button>";

	/** The Constant COMMAND_BUTTON. */
	private static final String COMMAND_BUTTON = "<button name='command' value='";

	/** The Constant BRACKET_CLOSE. */
	private static final String BRACKET_CLOSE = "'>";

	/** The Constant RATING_TYPE_INPUT. */
	private static final String RATING_TYPE_INPUT = "<input type='text' name='ratingType' value='";

	/** The Constant COMMAND_INPUT. */
	private static final String COMMAND_INPUT = "<input type='text' name='command' value='";

	/** The Constant HTML_H_3. */
	private static final String HTML_H_3 = "<h3>";

	/** The Constant HTML_H_3_CLOSE. */
	private static final String HTML_H_3_CLOSE = "</h3>";

	/** The Constant SUBMIT_INPUT. */
	private static final String SUBMIT_INPUT = "<input type='submit' name='command' value='";

	/** The Constant RATING. */
	private static final String RATING = "_rating'>";

	/** The Constant BG_RATING_SECTION. */
	private static final String BG_RATING_SECTION = "<section class='rating_bg'>";

	/** The Constant RATING_SECTION. */
	private static final String RATING_SECTION = "<section class='rating'>";

	/** The Constant SECTION. */
	private static final String SECTION = "<section>";

	/** The Constant SECTION_CLOSE. */
	private static final String SECTION_CLOSE = "</section>";

	/** The Constant FORM. */
	private static final String FORM = "<form action='/RockFest/RockFest'>";

	/** The Constant FORM_CLOSE. */
	private static final String FORM_CLOSE = "</form>";

	/** The Constant HTML_B. */
	private static final String HTML_B = "<b>";

	/** The Constant HTML_B_CLOSE. */
	private static final String HTML_B_CLOSE = "</b>";

	/** The Constant HTML_P. */
	private static final String HTML_P = "<p>";

	/** The Constant HTML_P_CLOSE. */
	private static final String HTML_P_CLOSE = "</p>";

	/**
	 * Instantiates a new ratings ajax util.
	 */
	private RatingsAjaxUtil(){}

	/**
	 * Gets the single instance of RatingsAjaxUtil.
	 *
	 * @return single instance of RatingsAjaxUtil
	 */
	public static RatingsAjaxUtil getInstance(){
		return instance;
	}

	/**
	 * Generate HTML ratings.
	 *
	 * @param entityType the entity type
	 * @param comparatorType the comparator type
	 * @param ratings the ratings
	 * @param position the position
	 * @param elementsCount the elements count
	 * @return the string
	 */
	public String generateHTMLRatings(String entityType, String comparatorType, List<EntityRating> ratings, int position,
			int elementsCount){
		StringBuilder builder = new StringBuilder();
		addPosition(builder, position, elementsCount, ratings.size());
		addHeader(entityType, comparatorType, builder);
		RatingUtil ratingUtil = RatingUtil.getInstance();
		String ratingCommand = ratingUtil.findRatingCommand(entityType);
		String entityCommand = ratingUtil.findEntityCommand(entityType);
		addRatings(ratings, builder, entityCommand, ratingCommand);
		return builder.toString();
	}

	/**
	 * Adds the position.
	 *
	 * @param builder the builder
	 * @param position the position
	 * @param elementsCount the elements count
	 * @param ratingSize the rating size
	 */
	private void addPosition(StringBuilder builder, int position, int elementsCount, int ratingSize) {
		int newPosition = position + elementsCount;
		boolean end = false;
		boolean start= false;
		builder.append(POSITION_INPUT);
		builder.append(newPosition);
		builder.append(HIDDEN);
		if (position <= 0){
			start = true;
		}
		if (ratingSize < elementsCount){
			end = true;
		}
		builder.append(START_INPUT);
		builder.append(start);
		builder.append(HIDDEN);
		builder.append(END_INPUT);
		builder.append(end);
		builder.append(HIDDEN);

	}

	/**
	 * Adds the header.
	 *
	 * @param entityType the entity type
	 * @param comparatorType the comparator type
	 * @param builder the builder
	 */
	private void addHeader(String entityType, String comparatorType, StringBuilder builder) {
		String comparatorMessage = RatingType.valueOf(comparatorType.toUpperCase()).toString();
		builder.append(HTML_H_2);
		builder.append(entityType);
		builder.append(HTML_H_2_CLOSE);
		builder.append(HTML_H_2);
		builder.append(comparatorMessage);
		builder.append(HTML_H_2_CLOSE);
		builder.append(NEW_LINE);
	}

	/**
	 * Adds the ratings.
	 *
	 * @param ratings the ratings
	 * @param builder the builder
	 * @param entityCommand the entity command
	 * @param ratingCommand the rating command
	 */
	private void addRatings(List<EntityRating> ratings, StringBuilder builder, String entityCommand, String ratingCommand) {
		for (EntityRating rating : ratings){
			addRating(rating, builder, entityCommand, ratingCommand);
		}
	}

	/**
	 * Adds the rating.
	 *
	 * @param rating the rating
	 * @param builder the builder
	 * @param entityCommand the entity command
	 * @param ratingCommand the rating command
	 */
	private void addRating(EntityRating rating, StringBuilder builder, String entityCommand, String ratingCommand) {
		addTitle(rating.getEntityTitle(), rating.getEntityId(), entityCommand, builder);
		addRatingBackgroundSectionStart(builder);
		addRatingSections(rating, ratingCommand, builder);
		addVotedUsersCount(rating.getVotedUsersCount(), builder);
		addSectionEnd(builder);
		builder.append(HTML_NEW_LINE);
	}

	/**
	 * Adds the title.
	 *
	 * @param entityTitle the entity title
	 * @param entityId the entity id
	 * @param entityCommand the entity command
	 * @param builder the builder
	 */
	private void addTitle(String entityTitle, long entityId, String entityCommand, StringBuilder builder) {
		addFormStart(builder);
		builder.append(ID_INPUT + entityId + HIDDEN);
		builder.append(COMMAND_BUTTON + entityCommand + BRACKET_CLOSE + entityTitle + BUTTON_CLOSE);
		addFormEnd(builder);
	}

	/**
	 * Adds the rating sections.
	 *
	 * @param rating the rating
	 * @param ratingCommand the rating command
	 * @param builder the builder
	 */
	private void addRatingSections(EntityRating rating, String ratingCommand, StringBuilder builder) {
		addRatingSection(RatingType.GENERAL.name().toLowerCase(), rating.getRating(), ratingCommand, builder);
		addRatingSection(RatingType.MELODY.name().toLowerCase(), rating.getMelodyRating(), ratingCommand, builder);
		addRatingSection(RatingType.TEXT.name().toLowerCase(), rating.getTextRating(), ratingCommand, builder);
		addRatingSection(RatingType.MUSIC.name().toLowerCase(), rating.getMusicRating(), ratingCommand, builder);
		addRatingSection(RatingType.VOCAL.name().toLowerCase(), rating.getVocalRating(), ratingCommand, builder);

	}

	/**
	 * Adds the rating section.
	 *
	 * @param ratingType the rating type
	 * @param rating the rating
	 * @param ratingCommand the rating command
	 * @param builder the builder
	 */
	private void addRatingSection(String ratingType, double rating, String ratingCommand, StringBuilder builder) {
		addRatingSectionStart(builder);
		addFormStart(builder);
		builder.append(RATING_TYPE_INPUT + ratingType + HIDDEN);
		builder.append(NEW_LINE);
		builder.append(COMMAND_INPUT + ratingCommand + HIDDEN);
		builder.append(NEW_LINE);
		builder.append(SUBMIT_INPUT);
		builder.append(ratingType);
		builder.append(RATING);
		builder.append(HTML_H_3);
		builder.append(rating);
		builder.append(HTML_H_3_CLOSE);
		addFormEnd(builder);
		addSectionEnd(builder);
		builder.append(NEW_LINE);

	}

	/**
	 * Adds the rating background section start.
	 *
	 * @param builder the builder
	 */
	private void addRatingBackgroundSectionStart(StringBuilder builder) {
		builder.append(BG_RATING_SECTION);
	}

	/**
	 * Adds the rating section start.
	 *
	 * @param builder the builder
	 */
	private void addRatingSectionStart(StringBuilder builder) {
		builder.append(RATING_SECTION);
	}

	/**
	 * Adds the section start.
	 *
	 * @param builder the builder
	 */
	private void addSectionStart(StringBuilder builder) {
		builder.append(SECTION);
	}

	/**
	 * Adds the section end.
	 *
	 * @param builder the builder
	 */
	private void addSectionEnd(StringBuilder builder) {
		builder.append(SECTION_CLOSE);
	}

	/**
	 * Adds the voted users count.
	 *
	 * @param votedUsersCount the voted users count
	 * @param builder the builder
	 */
	private void addVotedUsersCount(int votedUsersCount, StringBuilder builder) {
		addSectionStart(builder);
		builder.append(HTML_H_2);
		builder.append(HTML_B);
		builder.append("Voted users count");
		builder.append(HTML_B_CLOSE);
		builder.append(HTML_P);
		builder.append(votedUsersCount);
		builder.append(HTML_P_CLOSE);
		builder.append(HTML_H_2_CLOSE);
		addSectionEnd(builder);
		builder.append(NEW_LINE);
	}

	/**
	 * Adds the form start.
	 *
	 * @param builder the builder
	 */
	private void addFormStart(StringBuilder builder) {
		builder.append(FORM);

	}

	/**
	 * Adds the form end.
	 *
	 * @param builder the builder
	 */
	private void addFormEnd(StringBuilder builder) {
		builder.append(FORM_CLOSE);
	}

	/**
	 * The Enum RatingType.
	 */
	private static enum RatingType {

		/** The general. */
		GENERAL("General rating"),

		/** The melody. */
		MELODY("Melody rating"),

		/** The text. */
		TEXT("Text rating"),

		/** The music. */
		MUSIC("Music rating"),

		/** The vocal. */
		VOCAL("Vocal rating"),
		;

		/** The message. */
		private String message;

		/**
		 * Instantiates a new rating type.
		 *
		 * @param message the message
		 */
		RatingType(String message){
			this.message = message;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString(){
			return message;
		}
	}

	/**
	 * The Enum LocalizedMessageKey.
	 */
	private static enum LocalizedMessageKey {

		/** The rating. */
		RATING,

		/** The melody rating. */
		MELODY_RATING,

		/** The text rating. */
		TEXT_RATING,

		/** The music rating. */
		MUSIC_RATING,

		/** The vocal rating. */
		VOCAL_RATING,

		/** The voted users count. */
		VOTED_USERS_COUNT,

	}

}
