package com.semernik.rockfest.command;

import com.semernik.rockfest.type.CommandType;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Command objects.
 */
public class CommandFactory {

	/**
	 * Gets the command.
	 *
	 * @param action the action
	 * @return the command
	 */
	public static Command getCommand(String action) {
		CommandType type = CommandType.getCommandType(action);
		return type.getCommand();
	}

}
