package com.semernik.rockfest.validator;

import com.semernik.rockfest.controller.SessionRequestContent;

// TODO: Auto-generated Javadoc
/**
 * The Interface Validator.
 */
public interface Validator {

	/**
	 * Checks if is valid.
	 *
	 * @param content the content
	 * @return true, if is valid
	 */
	boolean isValid(SessionRequestContent content);

}
