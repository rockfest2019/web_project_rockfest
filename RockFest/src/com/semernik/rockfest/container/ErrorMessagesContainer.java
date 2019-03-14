package com.semernik.rockfest.container;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErrorMessagesContainer {

	private static Logger logger = LogManager.getLogger();
	private static final String DEFAULT_RESOURCE_NAME = "error_message";
	private static final String DEFAULT_MESSAGE_KEY = "error";
	private static ResourceBundle bundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_NAME);

	public void loadErrorMessages (){
		loadErrorMessages(DEFAULT_RESOURCE_NAME);
	}

	public void loadErrorMessages (String resourceName){
		bundle = ResourceBundle.getBundle(resourceName);
	}

	public static String findMessage (String messageKey){
		String pageName;
		try {
			pageName = bundle.getString(messageKey);
		} catch (MissingResourceException e) {
			logger.error("There is no error message for key " + messageKey, e);
			pageName = bundle.getString(DEFAULT_MESSAGE_KEY);
		}
		return pageName;
	}

}
