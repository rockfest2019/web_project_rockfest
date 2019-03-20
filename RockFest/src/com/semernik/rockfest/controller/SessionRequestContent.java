package com.semernik.rockfest.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.semernik.rockfest.type.AttributeName;
import com.semernik.rockfest.type.SendingMethod;

// TODO: Auto-generated Javadoc
/**
 * The Class SessionRequestContent.
 */
public class SessionRequestContent {


	private static final String EMPTY_PARAMETER = "";
	private Map <String, Object> requestAttributes;
	private Map <String, String[]> requestParameters;
	private Map <String, Object> sessionAttributes;
	private boolean useCurrentPage;
	private String ajaxResponse;
	private SendingMethod sendingMethod;

	public SessionRequestContent() {
		requestAttributes = new HashMap<>();
		requestParameters = new HashMap<>();
		sessionAttributes = new HashMap<>();
		sessionAttributes.put(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString(), new HashMap<String, Object>());
	}


	void extractValuesFromRequest(HttpServletRequest request){
		String name;
		Enumeration <String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()){
			name = attributeNames.nextElement();
			requestAttributes.put(name, request.getAttribute(name));
		}
		requestParameters = request.getParameterMap();
		attributeNames = request.getSession().getAttributeNames();
		while (attributeNames.hasMoreElements()){
			name = attributeNames.nextElement();
			sessionAttributes.put(name, request.getSession().getAttribute(name));
		}
	}

	/**
	 * Insert values to request.
	 *
	 * @param request the request
	 */
	void insertValuesToRequest(HttpServletRequest request){
		if (useCurrentPage){
			Map<String, Object> currentPageAttributes = (Map<String, Object>)sessionAttributes.get(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString());
			for (Map.Entry <String, Object>entry : requestAttributes.entrySet()){
				currentPageAttributes.put(entry.getKey(), entry.getValue());
				}
		} else {
			sessionAttributes.put(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString(), requestAttributes);
		}
		HttpSession session = request.getSession();
		for (Map.Entry <String, Object>entry : sessionAttributes.entrySet()){
			session.setAttribute(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Gets the request attributes.
	 *
	 * @return the request attributes
	 */
	public Map<String, Object> getRequestAttributes() {
		return requestAttributes;
	}

	/**
	 * Gets the request parameters.
	 *
	 * @return the request parameters
	 */
	public Map<String, String[]> getRequestParameters() {
		return requestParameters;
	}


	public Map<String, Object> getSessionAttributes() {
		return sessionAttributes;
	}

	/**
	 * Gets the current page attributes.
	 *
	 * @return the current page attributes
	 */
	public Map<String, Object> getCurrentPageAttributes() {
		return (Map<String, Object>)sessionAttributes.get(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString());
	}

	/**
	 * Checks if is using current page.
	 *
	 * @return true, if is using current page
	 */
	public boolean isUsingCurrentPage() {
		return useCurrentPage;
	}

	/**
	 * Sets the using current page.
	 *
	 * @param currentPage the new using current page
	 */
	public void setUsingCurrentPage(boolean currentPage) {
		if (currentPage == true && sessionAttributes.containsKey(AttributeName.CURRENT_PAGE.toString())){
			this.useCurrentPage = true;
		} else {
			this.useCurrentPage = false;
		}
	}

	/**
	 * Gets the current page.
	 *
	 * @return the current page
	 */
	public String getCurrentPage() {
		return (String) sessionAttributes.get(AttributeName.CURRENT_PAGE.toString());
	}

	/**
	 * Sets the current page.
	 *
	 * @param page the new current page
	 */
	public void setCurrentPage(String page) {
		sessionAttributes.put(AttributeName.CURRENT_PAGE.toString(), page);
	}

	/**
	 * Adds the request parameters to session.
	 */
	public void addRequestParametersToSession() {
		sessionAttributes.put(AttributeName.CURRENT_PAGE_ATTRIBUTES.toString(), requestAttributes);
	}

	/**
	 * Gets the ajax response.
	 *
	 * @return the ajax response
	 */
	public String getAjaxResponse() {
		return ajaxResponse;
	}

	/**
	 * Sets the ajax response.
	 *
	 * @param ajaxResponse the new ajax response
	 */
	public void setAjaxResponse(String ajaxResponse) {
		this.ajaxResponse = ajaxResponse;
	}

	/**
	 * Gets the sending method.
	 *
	 * @return the sending method
	 */
	public SendingMethod getSendingMethod() {
		return sendingMethod;
	}

	/**
	 * Sets the sending method.
	 *
	 * @param sendingMethod the new sending method
	 */
	public void setSendingMethod(SendingMethod sendingMethod) {
		this.sendingMethod = sendingMethod;
	}

	public void setRequestParameters(Map<String, String[]> requestParameters) {
		this.requestParameters = requestParameters;
	}

	public void setSessionAttributes(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	public void addRequestAttribute(String name, Object value){
		requestAttributes.put(name, value);
	}

	public void addCurrentPageAttribute(String name, Object value){
		Map<String, Object> currentPageAttributes = this.getCurrentPageAttributes();
		currentPageAttributes.put(name, value);
	}

	public Object getCurrentPageAttribute(String name){
		Map<String, Object> currentPageAttributes = this.getCurrentPageAttributes();
		return currentPageAttributes.get(name);
	}

	public void removeCurrentPageAttribute(String name){
		Map<String, Object> currentPageAttributes = this.getCurrentPageAttributes();
		currentPageAttributes.remove(name);
	}

	public String getParameter(String name){
		String [] parameters = requestParameters.get(name);
		String parameter = EMPTY_PARAMETER;
		if (parameters != null && parameters.length >0){
			parameter = parameters[0];
		}
		return parameter;
	}

	public String[] getParameters(String name){
		String [] parameters = requestParameters.get(name);
		if (parameters == null ){
			parameters = new String [1];
		}
		return parameters;
	}

	public Object getSessionAttribute(String name){
		return sessionAttributes.get(name);
	}

	public void addSessionAttribute(String name, Object value){
		sessionAttributes.put(name, value);
	}



}
