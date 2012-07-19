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
 
package de.enough.glaze.style.parser.property;

import java.util.Vector;

import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

/**
 * A {@link PropertyParser} implementation to parse dimensions
 * 
 * @author Andre
 * 
 */
public class DimensionPropertyParser extends PropertyParser {

	/**
	 * the instance
	 */
	private static DimensionPropertyParser INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static DimensionPropertyParser getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DimensionPropertyParser();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.parser.ValueParser#parse(java.lang.String)
	 */
	public Object parse(String value, Property property) throws CssSyntaxError {
		StringBuffer valueBuffer = new StringBuffer();
		int index = 0;
		// parse the value
		for (; index < value.length(); index++) {
			char character = value.charAt(index);
			// while the characters in the value are digits ...
			if (Character.isDigit(character) || character == ','
					|| character == '.') {
				// append the digit to the value buffer
				valueBuffer.append(character);
			} else {
				break;
			}
		}

		float dimensionValue;
		try {
			// parse the value buffer for the dimensional value
			dimensionValue = Float.parseFloat(valueBuffer.toString());
		} catch (NumberFormatException e) {
			throw new CssSyntaxError("invalid dimension", property);
		}

		String dimensionUnit = value.substring(index);
		// if the unit is not set ...
		if (dimensionUnit.length() == 0) {
			// set px as the unit
			dimensionUnit = Dimension.UNIT_PX;
		}

		// if the unit is valid ...
		if (Dimension.UNIT_CM.equals(dimensionUnit)
				|| Dimension.UNIT_INCH.equals(dimensionUnit)
				|| Dimension.UNIT_MM.equals(dimensionUnit)
				|| Dimension.UNIT_PERCENT.equals(dimensionUnit)
				|| Dimension.UNIT_PT.equals(dimensionUnit)
				|| Dimension.UNIT_PX.equals(dimensionUnit)
				|| Dimension.UNIT_WP.equals(dimensionUnit)
				|| Dimension.UNIT_HP.equals(dimensionUnit)) {
			// return the dimension
			return new Dimension(dimensionValue, dimensionUnit);
		} else {
			throw new CssSyntaxError("invalid dimension", property);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.property.PropertyParser#toArray(java.util
	 * .Vector)
	 */
	protected Object toArray(Vector vector) {
		Dimension[] dimension = new Dimension[vector.size()];
		vector.copyInto(dimension);
		return dimension;
	}
}
