package com.semernik.rockfest.type;

public enum Role {

	ADMIN,
	USER
	;

	@Override
	public String toString(){
		return this.name().toLowerCase();
	}
}
