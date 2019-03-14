package com.semernik.rockfest.container;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import com.semernik.rockfest.type.AttributeName;

public class RequestedAttributesNames {

	private static EnumSet<AttributeName> optionalSessionSet = EnumSet.of(AttributeName.USER_ID);

	public static Set<AttributeName> getOptionalSessionSet() {
		return Collections.unmodifiableSet(optionalSessionSet);
	}




}
