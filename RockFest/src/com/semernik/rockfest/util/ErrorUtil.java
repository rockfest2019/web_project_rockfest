package com.semernik.rockfest.util;

import com.semernik.rockfest.controller.SessionRequestContent;
import com.semernik.rockfest.type.ErrorMessage;

public class ErrorUtil {


	public static void addErrorMessageToContent(ErrorMessage message, ErrorMessage name, SessionRequestContent content) {
		String errorMessage = message.findMessage();
		content.getCurrentPageAttributes().put(name.toString(), errorMessage);
	}

	public static void addErrorMessageTotContent(ErrorMessage message, SessionRequestContent content) {
		addErrorMessageToContent(message, message, content);
	}

}
