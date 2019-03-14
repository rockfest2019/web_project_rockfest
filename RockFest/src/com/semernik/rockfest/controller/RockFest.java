package com.semernik.rockfest.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.semernik.rockfest.command.Command;
import com.semernik.rockfest.command.CommandFactory;
import com.semernik.rockfest.type.SendingMethod;


// TODO: Auto-generated Javadoc
/**
 * The Class RockFest.
 */
@WebServlet("/RockFest")
public class RockFest extends HttpServlet {

	/** The logger. */
	private static Logger logger = LogManager.getLogger();

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant COMMAND. */
	private static final String COMMAND = "command";

	/** The Constant ERROR. */
	private static final String ERROR = "error";

	/** The Constant REDIRECT_PREFFIX. */
	private static final String REDIRECT_PREFFIX = "/RockFest";

	/** The Constant COMMAND_ABSENCE. */
	private static final String COMMAND_ABSENCE = "HTTP request has no command";


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Process request.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionName = request.getParameter(COMMAND);
		if (actionName == null){
			logger.error(COMMAND_ABSENCE);
			actionName = ERROR;
		}
		SessionRequestContent content = new SessionRequestContent();
		content.extractValuesFromRequest(request);
		Command command = CommandFactory.getCommand(actionName);
		String page = command.execute(content);
		System.out.println("page: " + page);
		sendResponse(page, content, request, response);
	}


	/**
	 * Send response.
	 *
	 * @param page the page
	 * @param content the content
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void sendResponse(String page, SessionRequestContent content, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		SendingMethod method = content.getSendingMethod();
		if (method == SendingMethod.AJAX){
			String ajaxResponse = content.getAjaxResponse();
			response.getWriter().write(ajaxResponse);
		} else {
			content.insertValuesToRequest(request);
			if (method == SendingMethod.REDIRECT){
				response.sendRedirect(REDIRECT_PREFFIX + page);
			} else {
				getServletContext().getRequestDispatcher(page).forward(request, response);
			}
		}
	}

}
