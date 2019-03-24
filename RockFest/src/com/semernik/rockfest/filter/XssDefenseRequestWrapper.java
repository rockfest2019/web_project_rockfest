package com.semernik.rockfest.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssDefenseRequestWrapper extends HttpServletRequestWrapper {

	private final static String OPEN_BRACKET = "<";
	private final static String CLOSE_BRACKET = ">";
	private final static String AMPERSAND = "&";
	private final static String LESS_THEN = "lt";
	private final static String GREATER_THEN = "gt";
	private final static String PLUS = "+";
	private Map<String, String[]> parameterMap;

	public XssDefenseRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name){
		String parameter = super.getParameter(name);
		if (parameter != null){
			parameter = checkAgainstXss(parameter);
		}
		return parameter;
	}

	@Override
	public String[] getParameterValues(String name){
		String[] parameters = super.getParameterValues(name);
		checkAgainstXss(parameters);
		return parameters;
	}

	@Override
	public Map<String, String[]> getParameterMap(){
		if (parameterMap == null){
			parameterMap = new HashMap<>();
			Map<String, String[]> parameters = super.getParameterMap();
			parameterMap.putAll(parameters);
			parameterMap.forEach((k,v)->checkAgainstXss(v));
		}

		return parameterMap;
	}

	private void checkAgainstXss(String[] parameters) {
		boolean xss = false;
		if (parameters != null){
			for (int i=0; i < parameters.length; i++){
				parameters[i] = checkAgainstXss(parameters[i]);
			}
		}
	}

	private String checkAgainstXss(String parameter) {
		parameter = parameter.replaceAll(AMPERSAND, PLUS);
		parameter = parameter.replaceAll(OPEN_BRACKET, LESS_THEN);
		parameter = parameter.replaceAll(CLOSE_BRACKET, GREATER_THEN);
		return parameter;
	}

}
