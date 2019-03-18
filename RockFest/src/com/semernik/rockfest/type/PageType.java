package com.semernik.rockfest.type;

public enum PageType {

	COMPOSITION,
	GENRE,
	SINGER,
	COMPOSITIONS,
	GENRES,
	SINGERS,
	RATINGS,
	ERROR,
	ADD_COMPOSITION,
	ADD_GENRE,
	ADD_SINGER,
	SAVE_COMPOSITION,
	SAVE_GENRE,
	SAVE_SINGER,
	USER,
	USER_INFO,
	LOGIN,
	LOGOUT,
	REGISTRATION,
	USER_RATING,
	LOCALE,
	SINGER_COMPOSITIONS,
	GENRE_COMPOSITIONS,
	SEARCH,
	ADMIN
	;

	@Override
	public String toString(){
		return this.name().toLowerCase();
	}

}
