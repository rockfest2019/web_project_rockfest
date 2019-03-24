package com.semernik.rockfest.util;

import java.util.Collection;
import java.util.List;

import com.semernik.rockfest.container.LocalizedMessagesContainer;
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
	private static final String SUBMIT_INPUT = "<input type='submit' value='";
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
	private static final String GENRES_ID_INPUT = "<input type='checkbox' name='genresIds' value='";


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
			int elementsCount, String locale){
		StringBuilder builder = new StringBuilder();
		addPosition(builder, position, elementsCount, ratings.size());
		addHeader(entityType, comparatorType, builder, locale);
		RatingUtil ratingUtil = RatingUtil.getInstance();
		String ratingCommand = ratingUtil.findRatingCommand(entityType);
		String entityCommand = ratingUtil.findEntityCommand(entityType);
		addRatings(ratings, builder, entityCommand, ratingCommand, locale);
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

	private void addHeader(String entityType, String comparatorType, StringBuilder builder, String locale) {
		RatingType ratingType = RatingType.valueOf(comparatorType.toUpperCase());
		String comparatorMessageKey = ratingType.getMessageKey();
		String comparatorMessage = LocalizedMessagesContainer.getLocalizedMessageByKey(comparatorMessageKey, locale);
		String entityMessage = LocalizedMessagesContainer.getLocalizedMessageByKey(entityType, locale);
		builder.append(HTML_H_2);
		builder.append(entityMessage);
		builder.append(HTML_H_2_CLOSE);
		builder.append(HTML_H_2);
		builder.append(comparatorMessage);
		builder.append(HTML_H_2_CLOSE);
		builder.append(NEW_LINE);
	}

	private void addRatings(List<EntityRating> ratings, StringBuilder builder, String entityCommand, String ratingCommand,
			String locale) {
		for (EntityRating rating : ratings){
			addRating(rating, builder, entityCommand, ratingCommand, locale);
		}
	}

	private void addRating(EntityRating rating, StringBuilder builder, String entityCommand, String ratingCommand, String locale) {
		addTitle(rating.getEntityTitle(), rating.getEntityId(), entityCommand, builder);
		addRatingBackgroundSectionStart(builder);
		addRatingSections(rating, ratingCommand, builder, locale);
		addVotedUsersCount(rating.getVotedUsersCount(), builder, locale);
		addSectionEnd(builder);
		builder.append(HTML_NEW_LINE);
	}

	private void addTitle(String entityTitle, long entityId, String entityCommand, StringBuilder builder) {
		addFormStart(builder);
		builder.append(ID_INPUT + entityId + HIDDEN);
		builder.append(COMMAND_BUTTON + entityCommand + BRACKET_CLOSE + entityTitle + BUTTON_CLOSE);
		addFormEnd(builder);
	}

	private void addRatingSections(EntityRating rating, String ratingCommand, StringBuilder builder, String locale) {
		addRatingSection(RatingType.GENERAL, rating.getRating(), ratingCommand, builder, locale);
		addRatingSection(RatingType.MELODY, rating.getMelodyRating(), ratingCommand, builder, locale);
		addRatingSection(RatingType.TEXT, rating.getTextRating(), ratingCommand, builder, locale);
		addRatingSection(RatingType.MUSIC, rating.getMusicRating(), ratingCommand, builder, locale);
		addRatingSection(RatingType.VOCAL, rating.getVocalRating(), ratingCommand, builder, locale);

	}

	private void addRatingSection(RatingType ratingType, double rating, String ratingCommand, StringBuilder builder, String locale) {
		addRatingSectionStart(builder);
		addFormStart(builder);
		builder.append(RATING_TYPE_INPUT + ratingType.name().toLowerCase() + HIDDEN);
		builder.append(NEW_LINE);
		builder.append(COMMAND_INPUT + ratingCommand + HIDDEN);
		builder.append(NEW_LINE);
		builder.append(SUBMIT_INPUT);
		String ratingMessage = LocalizedMessagesContainer.getLocalizedMessageByKey(ratingType.getMessageKey(), locale);
		builder.append(ratingMessage);
		builder.append(BRACKET_CLOSE);
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

	private void addVotedUsersCount(int votedUsersCount, StringBuilder builder, String locale) {
		addSectionStart(builder);
		builder.append(HTML_H_2);
		builder.append(HTML_B);
		String messageKey = LocalizedMessageKey.VOTED_USERS_COUNT.toString().toLowerCase();
		String message = LocalizedMessagesContainer.getLocalizedMessageByKey(messageKey, locale);
		builder.append(message);
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

		GENERAL("rating"),
		MELODY("melody_rating"),
		TEXT("text_rating"),
		MUSIC("music_rating"),
		VOCAL("vocal_rating")
		;

		private String messageKey;


		RatingType(String message){
			this.messageKey = message;
		}


		public String getMessageKey(){
			return messageKey;
		}
	}


	private static enum LocalizedMessageKey {

		VOTED_USERS_COUNT,
		SAVE_GENRES,

	}

	public String generateHTMLGenresForComposition(long compositionId, Collection<Genre> genres, String locale) {
		StringBuilder builder = new StringBuilder();
		addSectionStart(builder);
		addPostFormStart(builder);
		builder.append(COMMAND_INPUT);
		builder.append(CommandType.UPDATE_COMPOSITION_GENRE.name().toLowerCase());
		builder.append(HIDDEN);
		builder.append(ID_INPUT);
		builder.append(compositionId);
		builder.append(HIDDEN);
		addGenresForComposition(genres, builder);
		String messageKey = LocalizedMessageKey.SAVE_GENRES.toString().toLowerCase();
		String saveGenres = LocalizedMessagesContainer.getLocalizedMessageByKey(messageKey, locale);
		addSubmit(saveGenres, builder);
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
		builder.append(GENRES_ID_INPUT);
		builder.append(genre.getGenreId());
		builder.append(BRACKET_CLOSE);
		builder.append(genre.getTitle());
		builder.append(HTML_NEW_LINE);
		builder.append(NEW_LINE);
	}

	private void addSubmit(String value, StringBuilder builder) {
		builder.append(SUBMIT_INPUT);
		builder.append(value);
		builder.append(BRACKET_CLOSE);
	}

}
