package com.semernik.rockfest.entity;

import com.semernik.rockfest.type.CommandType;
import com.semernik.rockfest.type.EntityType;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity.
 */
public class Entity {

	/** The title. */
	private String title;

	/** The id. */
	private long id;

	/** The command. */
	private String command;

	/**
	 * Instantiates a new entity.
	 *
	 * @param type the type
	 * @param title the title
	 * @param id the id
	 */
	public Entity(EntityType type, String title, long id) {
		this.title = title;
		this.id = id;
		command = findCommand(type);
	}

	/**
	 * Find command.
	 *
	 * @param entityType the entity type
	 * @return the string
	 */
	private String findCommand(EntityType entityType) {
		CommandType commandType = CommandType.FIND_COMPOSITION;
		if (entityType == EntityType.GENRE){
			commandType = CommandType.FIND_GENRE;
		} else if (entityType == EntityType.SINGER){
			commandType = CommandType.FIND_SINGER;
		}
		return commandType.name().toLowerCase();
	}


	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the command.
	 *
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}




}
