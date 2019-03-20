package com.semernik.rockfest.util;

import com.semernik.rockfest.container.LocalizedMessagesContainer;
import com.semernik.rockfest.controller.SessionRequestContent;

public class MessageUtil {

	private final static String DEFAULT_LOCALE = "en_EN";
	private final static String LOCALE_KEY = "locale";

	public static void addMessageToContent(String messageKey, String attributeName, SessionRequestContent content){
		String locale = (String) content.getSessionAttribute(LOCALE_KEY);
		if (locale == null){
			locale = DEFAULT_LOCALE;
		}
		String message = LocalizedMessagesContainer.getLocalizedMessageByKey(messageKey, locale);
		content.addRequestAttribute(attributeName, message);
	}

	public static void addMessageToContent(String messageKey, SessionRequestContent content){
		addMessageToContent(messageKey, messageKey, content);
	}

	public static String findMessage(String messageKey, String locale){
		return LocalizedMessagesContainer.getLocalizedMessageByKey(messageKey, locale);
	}

	public static String findMessage(String messageKey){
		return LocalizedMessagesContainer.getLocalizedMessageByKey(messageKey, DEFAULT_LOCALE);
	}
}
