package de.enough.glaze.style.definition.converter.utils;

import java.util.Enumeration;

import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;

public class ConverterUtils {


	/**
	 * Checks the definition for unknown property ids
	 * 
	 * @param definition
	 *            the definition
	 * @throws CssSyntaxError
	 *             if an unknown property id was found
	 */
	public static void validate(Definition definition, String[] ids) throws CssSyntaxError {
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
	 * @throws CssSyntaxError
	 *             if the property id is unknown
	 */
	private static void validate(Property property, String[] ids) throws CssSyntaxError {
		String propertyId = property.getId();
		for (int index = 0; index < ids.length; index++) {
			String id = ids[index];
			if (id.equals(propertyId)) {
				return;
			}
		}

		throw new CssSyntaxError("unknown property id", property.getId(),
				property.getLine());
	}
}
