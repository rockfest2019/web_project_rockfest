package com.semernik.rockfest.command;

import com.semernik.rockfest.controller.SessionRequestContent;

// TODO: Auto-generated Javadoc
/**
 * The Interface LogicInvoker.
 */
public interface LogicInvoker {

	/**
	 * Invoke.
	 *
	 * @param content the content
	 * @return true, if successful
	 */
	boolean invoke(SessionRequestContent content);

}
