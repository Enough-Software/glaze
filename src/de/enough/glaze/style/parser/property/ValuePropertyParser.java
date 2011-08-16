package de.enough.glaze.style.parser.property;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

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
	
	/* (non-Javadoc)
	 * @see de.enough.glaze.style.parser.property.PropertyParser#parseValue(java.lang.String)
	 */
	public Object parse(String value) throws CssSyntaxError {
		return value;
	}
}