package com.semernik.rockfest.type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.command.Command;
import com.semernik.rockfest.service.AjaxService;
import com.semernik.rockfest.service.CompositionService;
import com.semernik.rockfest.service.GenreService;
import com.semernik.rockfest.service.RatingService;
import com.semernik.rockfest.service.SearchService;
import com.semernik.rockfest.service.SingerService;
import com.semernik.rockfest.service.UserService;
import com.semernik.rockfest.validator.GeneralValidator;
import com.semernik.rockfest.validator.UserValidator;

public enum CommandType {

	ADD_COMPOSITION (new Command(CompositionService.getInstance()::findSingersAndGenres, PageType.ADD_COMPOSITION)),
	SAVE_COMPOSITION (new Command(GeneralValidator.getInstance()::validComposition, CompositionService.getInstance()::saveComposition,
			PageType.SAVE_COMPOSITION, SendingMethod.REDIRECT)),
	SAVE_GENRE (new Command(GeneralValidator.getInstance()::validGenre, GenreService.getInstance()::saveGenre, PageType.SAVE_GENRE,
			SendingMethod.REDIRECT)),
	SAVE_SINGER (new Command(GeneralValidator.getInstance()::validSinger, SingerService.getInstance()::saveSinger, PageType.SAVE_SINGER,
			SendingMethod.REDIRECT)),
	SAVE_USER_RATING (new Command(UserValidator.getInstance()::validUserRating, RatingService.getInstance()::saveUserRating,
			PageType.USER_RATING, SendingMethod.REDIRECT)),
	FIND_ALL_COMPOSITIONS (new Command(GeneralValidator.getInstance()::validEntitiesSearch,
			CompositionService.getInstance()::findCompositions,PageType.COMPOSITIONS)),
	FIND_ALL_GENRES (new Command(GeneralValidator.getInstance()::validEntitiesSearch, GenreService.getInstance()::findGenres,
			PageType.GENRES)),
	FIND_ALL_SINGERS (new Command(GeneralValidator.getInstance()::validEntitiesSearch, SingerService.getInstance()::findSingers,
			PageType.SINGERS)),
	FIND_COMPOSITION (new Command(GeneralValidator.getInstance()::validId, CompositionService.getInstance()::findCompositionById,
			PageType.COMPOSITION)),
	FIND_GENRE (new Command(GeneralValidator.getInstance()::validId, GenreService.getInstance()::findGenreById,
			PageType.GENRE)),
	FIND_SINGER (new Command(GeneralValidator.getInstance()::validId, SingerService.getInstance()::findSingerById,PageType.SINGER)),
	ERROR (new Command(PageType.ERROR, SendingMethod.REDIRECT)),
	LOGIN(new Command(UserValidator.getInstance()::validLoginParameters, UserService.getInstance()::loginUser, PageType.LOGIN,
			SendingMethod.REDIRECT)),
	LOGOUT(new Command( UserService.getInstance()::logoutUser, PageType.LOGOUT)),
	CHANGE_LOCALE(new Command(UserValidator.getInstance()::validLocale, UserService.getInstance()::changeLocale, PageType.LOCALE)),
	FIND_SINGER_COMPOSITIONS (new Command(GeneralValidator.getInstance()::validId, 	CompositionService.getInstance()::findSingerCompositions,
			PageType.COMPOSITIONS)),
	FIND_GENRE_COMPOSITIONS (new Command(GeneralValidator.getInstance()::validId, 	CompositionService.getInstance()::findGenreCompositions,
			PageType.COMPOSITIONS)),
	REGISTRATE_USER (new Command(UserValidator.getInstance()::validRegistrationParameters, UserService.getInstance()::registerNewUser,
			PageType.REGISTRATION, SendingMethod.REDIRECT)),
	SEND_PASSWORD_TO_EMAIL(new Command(UserValidator.getInstance()::validLogin, UserService.getInstance()::sendUserPasswordToEmail,
			PageType.REGISTRATION, SendingMethod.REDIRECT)),
	USER_PROFILE(new Command(UserValidator.getInstance()::validUser, UserService.getInstance()::findUserProfile, PageType.USER)),
	ADMIN_PAGE(new Command(UserValidator.getInstance()::validAdmin, UserService.getInstance()::findAdminInfo, PageType.ADMIN)),
	CHANGE_EMAIL(new Command(UserValidator.getInstance()::validNewEmail, UserService.getInstance()::changeEmail, PageType.USER_INFO,
			SendingMethod.REDIRECT)),
	CHANGE_LOGIN(new Command(UserValidator.getInstance()::validNewLogin, UserService.getInstance()::changeLogin, PageType.USER_INFO,
			SendingMethod.REDIRECT)),
	CHANGE_PASSWORD(new Command(UserValidator.getInstance()::validNewPassword, UserService.getInstance()::changeUserPassword,
			PageType.USER_INFO, SendingMethod.REDIRECT)),
	SAVE_COMMENT(new Command(GeneralValidator.getInstance()::validComment, CompositionService.getInstance()::saveCompositionComment,
			PageType.COMPOSITION, SendingMethod.REDIRECT)),
	SAVE_SINGER_COMMENT(new Command(GeneralValidator.getInstance()::validComment, SingerService.getInstance()::saveSingerComment,
			PageType.SINGER, SendingMethod.REDIRECT)),
	SAVE_GENRE_COMMENT(new Command(GeneralValidator.getInstance()::validComment, GenreService.getInstance()::saveGenreComment,
			PageType.GENRE, SendingMethod.REDIRECT)),
	SAVE_COMPOSITION_LINK(new Command(GeneralValidator.getInstance()::validCompositionLink,
			CompositionService.getInstance()::saveCompositionLink, PageType.COMPOSITION, SendingMethod.REDIRECT)),
	FIND_RATINGS(new Command(RatingService.getInstance()::findCommonRatings,  PageType.RATINGS)),
	FIND_COMPOSITIONS_RATINGS(new Command(GeneralValidator.getInstance():: validRatingType,
			RatingService.getInstance()::findCompositionsRating, PageType.RATINGS)),
	FIND_GENRES_RATINGS(new Command(GeneralValidator.getInstance():: validRatingType,
			RatingService.getInstance()::findGenresRating, PageType.RATINGS)),
	FIND_SINGERS_RATINGS(new Command(GeneralValidator.getInstance():: validRatingType,
			RatingService.getInstance()::findSingersRating, PageType.RATINGS)),
	FIND_USER_COMPOSITIONS_RATINGS(new Command(GeneralValidator.getInstance():: validUserRatingType,
			RatingService.getInstance()::findUserCompositionsRating, PageType.RATINGS)),
	FIND_USER_SINGERS_RATINGS(new Command(GeneralValidator.getInstance():: validUserRatingType,
			RatingService.getInstance()::findUserSingersRating, PageType.RATINGS)),
	FIND_USER_GENRES_RATINGS(new Command(GeneralValidator.getInstance():: validUserRatingType,
			RatingService.getInstance()::findUserGenresRating, PageType.RATINGS)),
	COMPOSITIONS_RATINGS_AJAX(new Command(GeneralValidator.getInstance():: validAjaxRatingType,
			AjaxService.getInstance()::findCompositionsRatings, SendingMethod.AJAX)),
	SINGERS_RATINGS_AJAX(new Command(GeneralValidator.getInstance():: validAjaxRatingType,
			AjaxService.getInstance()::findSingersRatings, SendingMethod.AJAX)),
	GENRES_RATINGS_AJAX(new Command(GeneralValidator.getInstance():: validAjaxRatingType,
			AjaxService.getInstance()::findGenresRatings, SendingMethod.AJAX)),
	ENTITIES_SEARCH(new Command(GeneralValidator.getInstance():: validSearchPattern, SearchService.getInstance()::findEntitiesByPattern,
			PageType.SEARCH)),
	COMPOSITIONS_SEARCH(new Command(GeneralValidator.getInstance():: validSearchPattern, SearchService.getInstance()::findCompositionsByPattern,
			PageType.SEARCH)),
	GENRES_SEARCH(new Command(GeneralValidator.getInstance():: validSearchPattern, SearchService.getInstance()::findGenresByPattern,
			PageType.SEARCH)),
	SINGERS_SEARCH(new Command(GeneralValidator.getInstance():: validSearchPattern, SearchService.getInstance()::findSingersByPattern,
			PageType.SEARCH)),
	UPDATE_SINGER_DESCRIPTION(new Command(GeneralValidator.getInstance():: validDescriptionUpdate, SingerService.getInstance()::updateSingerDescription,
			PageType.SINGER, SendingMethod.REDIRECT)),
	UPDATE_GENRE_DESCRIPTION(new Command(GeneralValidator.getInstance()::validDescriptionUpdate, GenreService.getInstance()::updateGenreDescription,
			PageType.GENRE, SendingMethod.REDIRECT)),
	UPDATE_COMPOSITION_GENRE(new Command(GeneralValidator.getInstance():: validCompositionGenres,
			CompositionService.getInstance()::changeCompositionGenres, PageType.COMPOSITION, SendingMethod.REDIRECT)),
	UPDATE_COMPOSITION_YEAR(new Command(GeneralValidator.getInstance():: validCompositionYear,
			CompositionService.getInstance()::changeCompositionYear, PageType.COMPOSITION, SendingMethod.REDIRECT)),
	SAVE_USER_BAN_DATE(new Command(UserValidator.getInstance()::validUserBanDate, UserService.getInstance()::saveUserBanExpirationDate,
			PageType.ADMIN, SendingMethod.REDIRECT)),
	CHANGE_COMPOSITION_TITLE(new Command(GeneralValidator.getInstance()::validNewTitle,
			CompositionService.getInstance()::changeCompositionTitle, PageType.COMPOSITION, SendingMethod.REDIRECT)),
	CHANGE_SINGER_TITLE(new Command(GeneralValidator.getInstance()::validNewTitle,
			SingerService.getInstance()::changeSingerTitle, PageType.SINGER, SendingMethod.REDIRECT)),
	CHANGE_GENRE_TITLE(new Command(GeneralValidator.getInstance()::validNewTitle,
			GenreService.getInstance()::changeGenreTitle, PageType.GENRE, SendingMethod.REDIRECT)),
	DELETE_COMPOSITION_COMMENT(new Command(GeneralValidator.getInstance()::validCommentDeletion,
			CompositionService.getInstance()::deleteCompositionComment, PageType.COMPOSITION, SendingMethod.REDIRECT)),
	DELETE_SINGER_COMMENT(new Command(GeneralValidator.getInstance()::validCommentDeletion,
			SingerService.getInstance()::deleteSingerComment, PageType.SINGER, SendingMethod.REDIRECT)),
	DELETE_GENRE_COMMENT(new Command(GeneralValidator.getInstance()::validCommentDeletion,
			GenreService.getInstance()::deleteGenreComment, PageType.GENRE, SendingMethod.REDIRECT)),
	DELETE_COMPOSITION_LINK(new Command(GeneralValidator.getInstance()::validCompositionLinkDeletion,
			CompositionService.getInstance()::deleteCompositionLink, PageType.COMPOSITION, SendingMethod.REDIRECT)),
	FIND_GENRES_FOR_COMPOSITION_AJAX (new Command(GeneralValidator.getInstance()::validId,
			AjaxService.getInstance()::findGenresForComposition, PageType.COMPOSITION, SendingMethod.AJAX))
	;

	private static Logger logger = LogManager.getLogger();
	private Command command;

	CommandType (Command command){
		this.command = command;
	}

	public Command getCommand(){
		return command;
	}

	public static CommandType getCommandType(String action){
		action = action.toUpperCase();
		boolean contains = false;
		for (CommandType command : CommandType.values()){
			if (action.equals(command.name())){
				contains = true;
			}
		}
		CommandType command;
		if (contains){
			command = CommandType.valueOf(action);
		} else {
			logger.error("There is no command with name " + action);
			command = CommandType.ERROR;
		}
		return command;
	}

}
