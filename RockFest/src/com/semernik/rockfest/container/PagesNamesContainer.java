package com.semernik.rockfest.container;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PagesNamesContainer {

	private static Logger logger = LogManager.getLogger();
	private static final String DEFAULT_RESOURCE_NAME = "pages";
	private static final String ERROR_PAGE = "/jsp/serviceError/pageNotFound.jsp";
	private static ResourceBundle bundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_NAME);

	public void loadPagesNames (){
		loadPagesNames(DEFAULT_RESOURCE_NAME);
	}

	public void loadPagesNames (String resourceName){
		try{
			bundle = ResourceBundle.getBundle(resourceName);
		} catch (MissingResourceException e) {
			if (DEFAULT_RESOURCE_NAME.equals(resourceName)){
				logger.error("There is no resources with name: " + resourceName, e);
			} else {
				logger.error("There is no resources with name: " + resourceName + ". Default resource will be loaded", e);
				bundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_NAME);
			}
		}
	}

	public static String findPage (String pageKey){
		String pageName;
		try {
			pageName = bundle.getString(pageKey);
		} catch (MissingResourceException e) {
			logger.error("There is no page for key " + pageKey, e);
			pageName = ERROR_PAGE;
		}
		return pageName;
	}

}
