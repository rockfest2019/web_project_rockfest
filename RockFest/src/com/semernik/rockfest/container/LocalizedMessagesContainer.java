package com.semernik.rockfest.container;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocalizedMessagesContainer {

	private static Logger logger = LogManager.getLogger();
	private static ResourceBundle en = ResourceBundle.getBundle("language_en_US");
	private static ResourceBundle rus = ResourceBundle.getBundle("language_ru_RU");
	private static final String DEFAULT_MESSAGE_KEY = "error";
	private static final String RUS_LOCALE = "ru_RU";
	private static final String QUESTIONS = "???";

	public static String getLocalizedMessageByKey(String key, String locale){
		String message = null;
		if (locale != null && locale.equals(RUS_LOCALE)){
			message = getRusMessage(key);
		} else {
			message = getEnMessage(key);
		}
		return message;
	}

	private static String getRusMessage(String key) {
		String message;
		try {
			message = rus.getString(key);
		} catch (MissingResourceException e) {
			logger.error("There is no message for key " + key, e);
			message = QUESTIONS + key + QUESTIONS;
		}
		return message;
	}

	private static String getEnMessage(String key) {
		String message;
		try {
			message = en.getString(key);
		} catch (MissingResourceException e) {
			logger.error("There is no message for key " + key, e);
			message = QUESTIONS + key + QUESTIONS;
		}
		return message;
	}

}
