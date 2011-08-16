package de.enough.glaze.style.parser.property;

import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

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
	public Object parse(String value) throws CssSyntaxError {
		StringBuffer valueBuffer = new StringBuffer();
		int index = 0;
		// parse the value
		for (; index < value.length(); index++) {
			char character = value.charAt(index);
			// while the characters in the value are digits ...
			if (Character.isDigit(character)) {
				// append the digit to the value buffer
				valueBuffer.append(character);
			} else {
				break;
			}
		}

		int dimensionValue;
		try {
			// parse the value buffer for the dimensional value
			dimensionValue = Integer.parseInt(valueBuffer.toString());
		} catch (NumberFormatException e) {
			throw new CssSyntaxError("invalid dimension", value);
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
				|| Dimension.UNIT_SP.equals(dimensionUnit)) {
			// return the dimension
			return new Dimension(dimensionValue, dimensionUnit);
		} else {
			throw new CssSyntaxError("invalid dimension", value);
		}
	}
}