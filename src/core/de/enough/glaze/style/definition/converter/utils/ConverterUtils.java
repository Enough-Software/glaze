/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style.definition.converter.utils;

import java.util.Enumeration;

import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;

/**
 * Provides helper methods to validate property ids of styles, background etc.
 * definitions
 * 
 * @author Andre
 * 
 */
public class ConverterUtils {

	/**
	 * Checks the definition for unknown property ids
	 * 
	 * @param definition
	 *            the definition
	 * @param idsthe
	 *            known ids
	 * @throws CssSyntaxError
	 *             if an unknown property id was found
	 */
	public static void validate(Definition definition, String[] ids)
			throws CssSyntaxError {
		Enumeration properties = definition.getProperties();
		while (properties.hasMoreElements()) {
			Property property = (Property) properties.nextElement();
			validate(property, ids);
		}
	}

	/**
	 * Checks the given property if it is an unknown property id
	 * 
	 * @param property
	 *            the property
	 * @param ids
	 *            the known ids
	 * @throws CssSyntaxError
	 *             if the property id is unknown
	 */
	private static void validate(Property property, String[] ids)
			throws CssSyntaxError {
		String propertyId = property.getId();
		// compare the ids with the property id
		for (int index = 0; index < ids.length; index++) {
			String id = ids[index];
			if (id.equals(propertyId)) {
				return;
			}
		}

		// throw an error if the id was not found in the ids
		throw new CssSyntaxError("unknown property id", property.getId(),
				property.getLine());
	}
}
