package de.enough.glaze.style.parser;

import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.parser.exception.CssSyntaxException;

public class DimensionValueParser extends ValueParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.parser.ValueParser#parse(java.lang.String)
	 */
	public Object parseValue(String value) throws CssSyntaxException {
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
			throw new CssSyntaxException(value, "invalid dimension");
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
			throw new CssSyntaxException(value, "invalid dimension");
		}
	}
}
