package com.semernik.rockfest.type;

public enum ParameterName {
	ID("id"),
	TITLE("title"),
	DESCRIPTION("description"),
	AUTHOR_ID("authorId"),
	DESCRIPTION_EDITOR_ID("descriptionEditorId"),
	YEAR("year"),
	SINGER_ID("singerId"),
	YEAR_EDITOR_ID("yearEditorId"),
	GENRES_IDS("genresIds"),
	USER_LOGIN("userLogin"),
	USER_EMAIL("userEmail"),
	PASSWORD("password"),
	NEW_PASSWORD("newPassword"),
	RATING_TYPE("ratingType"),
	NEW_LOGIN("newLogin"),
	NEW_EMAIL("newEmail"),
	MELODY_RATING("melodyRating"),
	TEXT_RATING("textRating"),
	MUSIC_RATING("musicRating"),
	VOCAL_RATING("vocalRating"),
	LOCALE("locale"),
	COMMENT_CONTENT("commentContent"),
	URL("url"),
	POSITION("position"),
	ELEMENTS_COUNT("elementsCount"),
	SEARCH_PATTERN("searchPattern")
	;

	private String name;

	ParameterName(String name){
		this.name = name;
	}

	@Override
	public String toString(){
		return name;
	}


}
