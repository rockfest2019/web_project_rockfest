package com.semernik.rockfest.container;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.ParameterName;

public class RequiredParametersContainer {

	private static final Set<ParameterName> GENRE_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.TITLE,
			ParameterName.DESCRIPTION));
	private static final Set<ParameterName> COMPOSITION_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.TITLE,
			ParameterName.YEAR, ParameterName.SINGER_ID));
	private static final Set<ParameterName> OPTIONAL_COMPOSITION_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.GENRES_IDS));
	private static final  Set<ParameterName> LOGIN_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.USER_LOGIN, ParameterName.PASSWORD));
	private static final Set<ParameterName> COMMENT_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.ID, ParameterName.COMMENT_CONTENT));
	private static final Set<ParameterName> USER_RATING_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.ID,
			ParameterName.MELODY_RATING, ParameterName.TEXT_RATING, ParameterName.MUSIC_RATING, ParameterName.VOCAL_RATING));
	private static final Set<ParameterName> REGISTRATION_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.USER_LOGIN,
			ParameterName.PASSWORD, ParameterName.USER_EMAIL));
	private static final Set<ParameterName> NEW_EMAIL_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.NEW_EMAIL,
			ParameterName.PASSWORD));
	private static final Set<ParameterName> NEW_LOGIN_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.NEW_LOGIN,
			ParameterName.PASSWORD));
	private static final Set<ParameterName> NEW_PASSWORD_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.NEW_PASSWORD,
			ParameterName.PASSWORD));
	private static final Set<ParameterName> COMPOSITION_LINK_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.ID,
			ParameterName.URL));
	private static final Set<ParameterName> AJAX_RATING_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.POSITION,
			ParameterName.RATING_TYPE, ParameterName.ELEMENTS_COUNT));
	private static final Set<ParameterName> DESCRIPTION_UPDATE_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.DESCRIPTION,
			ParameterName.ID));
	private static final Set<ParameterName> COMPOSITION_YEAR_UPDATE_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.YEAR,
			ParameterName.ID));
	private static final Set<ParameterName> USER_BAN_DATE_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.DATE,
			ParameterName.ID));
	private static final Set<ParameterName> TITLE_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.TITLE,
			ParameterName.ID));
	private static final Set<ParameterName> ENTITIES_SEARCH_SET = Collections.unmodifiableSet(EnumSet.of(ParameterName.POSITION,
			ParameterName.ELEMENTS_COUNT));

	private static final Set<AttributeName> SESSION_USER_SET = Collections.unmodifiableSet(EnumSet.of(AttributeName.USER_ID,
			AttributeName.USER_LOGIN, AttributeName.ROLE));


	public static Set<ParameterName> getGenreSet(){
		return GENRE_SET;
	}

	public static Set<ParameterName> getCompositionSet() {
		return COMPOSITION_SET;
	}


	public static Set<ParameterName> getOptionalCompositionSet() {
		return OPTIONAL_COMPOSITION_SET;
	}

	public static Set<ParameterName> getLoginSet() {
		return LOGIN_SET;
	}

	public static Set<ParameterName> getUserRatingSet() {
		return USER_RATING_SET;
	}

	public static Set<ParameterName> getCommentSet() {
		return COMMENT_SET;
	}

	public static Set<ParameterName> getRegistrationSet() {
		return REGISTRATION_SET;
	}

	public static Set<ParameterName> getNewEmailSet() {
		return NEW_EMAIL_SET;
	}

	public static Set<ParameterName> getNewLoginSet() {
		return NEW_LOGIN_SET;
	}

	public static Set<ParameterName> getNewPasswordSet() {
		return NEW_PASSWORD_SET;
	}

	public static Set<ParameterName> getCompositionLinkSet() {
		return COMPOSITION_LINK_SET;
	}

	public static Set<ParameterName> getAjaxRatingSet() {
		return AJAX_RATING_SET;
	}

	public static Set<ParameterName> getDescriptionUpdateSet() {
		return DESCRIPTION_UPDATE_SET;
	}

	public static Set<ParameterName> getCompositionYearUpdateSet() {
		return COMPOSITION_YEAR_UPDATE_SET;
	}

	public static Set<ParameterName> getUserBanDateSet() {
		return USER_BAN_DATE_SET;
	}

	public static Set<ParameterName> getTitleSet() {
		return TITLE_SET;
	}

	public static Set<ParameterName> getEntitiesSet() {
		return ENTITIES_SEARCH_SET;
	}

	public static Set<AttributeName> getSessionUserSet() {
		return SESSION_USER_SET;
	}



}
