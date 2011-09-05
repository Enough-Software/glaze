package de.enough.glaze.style.parser.property;

import java.util.Vector;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

/**
 * A {@link PropertyParser} implementation to parse simple text values
 * 
 * @author Andre
 * 
 */
public class ValuePropertyParser extends PropertyParser {

	/**
	 * the instance
	 */
	private static ValuePropertyParser INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static ValuePropertyParser getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ValuePropertyParser();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.property.PropertyParser#parseValue(java.
	 * lang.String)
	 */
	public Object parse(String value, Property property) throws CssSyntaxError {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.property.PropertyParser#toArray(java.util
	 * .Vector)
	 */
	protected Object toArray(Vector vector) {
		String[] values = new String[vector.size()];
		vector.copyInto(values);
		return values;
	}
}