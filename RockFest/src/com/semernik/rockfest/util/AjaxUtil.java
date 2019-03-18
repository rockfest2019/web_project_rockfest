package com.semernik.rockfest.util;

import java.util.Collection;
import java.util.List;

import com.semernik.rockfest.entity.EntityRating;
import com.semernik.rockfest.entity.Genre;
import com.semernik.rockfest.type.CommandType;

// TODO: Auto-generated Javadoc
/**
 * The Class RatingsAjaxUtil.
 */
public class AjaxUtil {

	private static AjaxUtil instance = new AjaxUtil();

	private static final String POSITION_INPUT = "<input type='number' id='position' value='";
	private static final String HIDDEN = "' hidden>";
	private static final String START_INPUT = "<input type='text' id='start' value='";
	private static final String END_INPUT = "<input type='text' id='end' value='";
	private static final String HTML_H_2 = "<h2>";
	private static final String HTML_H_2_CLOSE = "</h2>";
	private static final String NEW_LINE = "\n";
	private static final String HTML_NEW_LINE = "<br>";
	private static final String ID_INPUT = "<input type='text' name='id' value ='";
	private static final String BUTTON_CLOSE = "</button>";
	private static final String COMMAND_BUTTON = "<button name='command' value='";
	private static final String BRACKET_CLOSE = "'>";
	private static final String RATING_TYPE_INPUT = "<input type='text' name='ratingType' value='";
	private static final String COMMAND_INPUT = "<input type='text' name='command' value='";
	private static final String HTML_H_3 = "<h3>";
	private static final String HTML_H_3_CLOSE = "</h3>";
	private static final String SUBMIT_INPUT = "<input type='submit' name='command' value='";
	private static final String RATING = "_rating'>";
	private static final String BG_RATING_SECTION = "<section class='rating_bg'>";
	private static final String RATING_SECTION = "<section class='rating'>";
	private static final String SECTION = "<section>";
	private static final String SECTION_CLOSE = "</section>";
	private static final String FORM = "<form action='/RockFest/RockFest'>";
	private static final String POST_FORM = "<form action='/RockFest/RockFest' method='post'>";
	private static final String FORM_CLOSE = "</form>";
	private static final String HTML_B = "<b>";
	private static final String HTML_B_CLOSE = "</b>";
	private static final String HTML_P = "<p>";
	private static final String HTML_P_CLOSE = "</p>";


	private AjaxUtil(){}


	public static AjaxUtil getInstance(){
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

	private void addRatings(List<EntityRating> ratings, StringBuilder builder, String entityCommand, String ratingCommand) {
		for (EntityRating rating : ratings){
			addRating(rating, builder, entityCommand, ratingCommand);
		}
	}

	private void addRating(EntityRating rating, StringBuilder builder, String entityCommand, String ratingCommand) {
		addTitle(rating.getEntityTitle(), rating.getEntityId(), entityCommand, builder);
		addRatingBackgroundSectionStart(builder);
		addRatingSections(rating, ratingCommand, builder);
		addVotedUsersCount(rating.getVotedUsersCount(), builder);
		addSectionEnd(builder);
		builder.append(HTML_NEW_LINE);
	}

	private void addTitle(String entityTitle, long entityId, String entityCommand, StringBuilder builder) {
		addFormStart(builder);
		builder.append(ID_INPUT + entityId + HIDDEN);
		builder.append(COMMAND_BUTTON + entityCommand + BRACKET_CLOSE + entityTitle + BUTTON_CLOSE);
		addFormEnd(builder);
	}

	private void addRatingSections(EntityRating rating, String ratingCommand, StringBuilder builder) {
		addRatingSection(RatingType.GENERAL.name().toLowerCase(), rating.getRating(), ratingCommand, builder);
		addRatingSection(RatingType.MELODY.name().toLowerCase(), rating.getMelodyRating(), ratingCommand, builder);
		addRatingSection(RatingType.TEXT.name().toLowerCase(), rating.getTextRating(), ratingCommand, builder);
		addRatingSection(RatingType.MUSIC.name().toLowerCase(), rating.getMusicRating(), ratingCommand, builder);
		addRatingSection(RatingType.VOCAL.name().toLowerCase(), rating.getVocalRating(), ratingCommand, builder);

	}

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

	private void addRatingBackgroundSectionStart(StringBuilder builder) {
		builder.append(BG_RATING_SECTION);
	}

	private void addRatingSectionStart(StringBuilder builder) {
		builder.append(RATING_SECTION);
	}

	private void addSectionStart(StringBuilder builder) {
		builder.append(SECTION);
	}

	private void addSectionEnd(StringBuilder builder) {
		builder.append(SECTION_CLOSE);
	}

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

	private void addFormStart(StringBuilder builder) {
		builder.append(FORM);

	}

	private void addPostFormStart(StringBuilder builder) {
		builder.append(POST_FORM);

	}

	private void addFormEnd(StringBuilder builder) {
		builder.append(FORM_CLOSE);
	}


	private static enum RatingType {

		GENERAL("General rating"),
		MELODY("Melody rating"),
		TEXT("Text rating"),
		MUSIC("Music rating"),
		VOCAL("Vocal rating")
		;

		private String message;


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


	private static enum LocalizedMessageKey {

		RATING,
		MELODY_RATING,
		TEXT_RATING,
		MUSIC_RATING,
		VOCAL_RATING,
		VOTED_USERS_COUNT,

	}

	public String generateHTMLGenresForComposition(long compositionId, Collection<Genre> genres) {
		StringBuilder builder = new StringBuilder();
		addSectionStart(builder);
		addPostFormStart(builder);
		builder.append(COMMAND_INPUT);
		builder.append(CommandType.UPDATE_COMPOSITION_GENRE.name().toLowerCase());
		builder.append(HIDDEN);
		builder.append(ID_INPUT);
		builder.append(compositionId);
		builder.append(HIDDEN);
		builder.append("<input type='text' name='test' value='testValue' hidden>\n");
		addGenresForComposition(genres, builder);
		addSubmit("save genres", builder);
		builder.append(NEW_LINE);
		addFormEnd(builder);
		addSectionEnd(builder);
		return builder.toString();
	}


	private void addGenresForComposition(Collection<Genre> genres, StringBuilder builder) {
		for (Genre genre : genres){
			addGenreForComposition(genre, builder);
		}

	}

	private void addGenreForComposition(Genre genre, StringBuilder builder) {
		builder.append("<input type='checkbox' name='genresIds' value='");
		builder.append(genre.getGenreId());
		builder.append(BRACKET_CLOSE);
		builder.append(genre.getTitle());
		builder.append(HTML_NEW_LINE);
		builder.append(NEW_LINE);
	}

	private void addSubmit(String value, StringBuilder builder) {
		builder.append("<input type='submit' value='");
		builder.append(value);
		builder.append("'>");
	}

}
