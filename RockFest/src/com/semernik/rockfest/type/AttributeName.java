package com.semernik.rockfest.type;

public enum AttributeName {

	COMPOSITION("composition"),
	GENRE("genre"),
	SINGER("singer"),
	COMPOSITIONS("compositions"),
	GENRES("genres"),
	SINGERS("singers"),
	COMMENTS("comments"),
	USERS("users"),
	USER_LOGIN("user_login"),
	USER_ID("user_id"),
	ROLE("role"),
	LOCALE("locale"),
	LINKS("links"),
	RATINGS("ratings"),
	RATINGS_COMPARATOR("ratings_comparator"),
	ENTITY_TYPE("entity_type"),
	USER_RATING("user_rating"),
	USER_PROFILE("user_profile"),
	USER_PROFILE_CHANGE("user_profile_change"),
	REGISTRATION_SUCCESS("registration_success"),
	CURRENT_PAGE("current_page"),
	CURRENT_PAGE_ATTRIBUTES("current_page_attributes"),
	SPECIFIC_RATING("specific_rating"),
	COMPARING_ENTITY("comparing_entity"),
	COMPARATOR("comparator"),
	ENTITY_COMMAND("entity_command"),
	RATING_COMMAND("rating_command"),
	COMPOSITIONS_RATING("compositions_rating"),
	SINGERS_RATING("singers_rating"),
	GENRES_RATING("genres_rating"),
	USER_COMPOSITIONS_RATING("user_compositions_rating"),
	USER_SINGERS_RATING("user_singers_rating"),
	USER_GENRES_RATING("user_genres_rating"),
	POSITION("position"),
	RATING_END("rating_end"),
	AJAX_COMMAND("ajax_command"),
	ENTITIES("entities"),
	DATE("date"),
	ELEMENTS_COUNT("elements_count")
	;

	private String name;

	AttributeName(String name){
		this.name = name;
	}

	@Override
	public String toString(){
		return name;
	}

}
