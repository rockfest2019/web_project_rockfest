package com.semernik.rockfest.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter (urlPatterns = { "/*" })
public class XssFilter implements Filter {

	private final static String OPEN_BRACKET = "<";
	private final static String CLOSE_BRACKET = ">";
	private final static String AMPERSAND = "&";
	private final static String LESS_THEN = "lt";
	private final static String GREATER_THEN = "gt";
	private final static String PLUS = "+";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Enumeration<String> parametersNames = request.getParameterNames();
		String parameterName = null;
		String[] parameters = null;
		while(parametersNames.hasMoreElements()){
			parameterName = parametersNames.nextElement();
			parameters = request.getParameterValues(parameterName);
			checkAgainstXss(parameters);
		}
		chain.doFilter(request, response);
	}

	private void checkAgainstXss(String[] parameters) {
		String parameter = null;
		for (int i = 0; i < parameters.length; i++){
			parameter = parameters[i];
			parameter = parameter.replaceAll(OPEN_BRACKET, LESS_THEN);
			parameter = parameter.replaceAll(CLOSE_BRACKET, GREATER_THEN);
			parameter = parameter.replaceAll(AMPERSAND, PLUS);
			parameters[i] = parameter;
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
