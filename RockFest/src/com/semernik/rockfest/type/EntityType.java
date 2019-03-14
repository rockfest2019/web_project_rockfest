package com.semernik.rockfest.type;

public enum EntityType {

	COMPOSITION("find_composition"),
	SINGER("find_singer"),
	GENRE("find_genre"),
	;

	private String command;

	EntityType(String command){
		this.command = command;
	}

	public String getCommand(){
		return command;
	}


}
