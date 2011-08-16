package de.enough.glaze.style.parser.attribute;

import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.utils.ParserUtils;

public abstract class AttributeParser {

	/**
	 * Returns the valid count of values. Override this to validate the parsed
	 * values for their count.
	 * 
	 * @return the valid count of values
	 */
	protected int[] getValidCounts() {
		return null;
	}

	/**
	 * Validates the size of the given array
	 * 
	 * @param value
	 *            the original value
	 * @param values
	 *            the array values
	 * @throws CssSyntaxError
	 */
	private void validateCount(String value, int count)
			throws CssSyntaxError {
		int[] validCounts = getValidCounts();
		// if valid counts are set ...
		if (validCounts != null) {
			// check if the parsed count is valid
			for (int index = 0; index < validCounts.length; index++) {
				int validSize = validCounts[index];
				if (validSize == count) {
					return;
				}
			}

			// build and throw the syntax error
			StringBuffer errorBuffer = new StringBuffer();
			errorBuffer.append("invalid value count, valid are ");
			for (int index = 0; index < validCounts.length; index++) {
				int validSize = validCounts[index];
				errorBuffer.append(validSize);
				if (index != validCounts.length - 1) {
					errorBuffer.append(',');
				}
			}

			throw new CssSyntaxError(value, errorBuffer.toString());
		}
	}

	/**
	 * Parses the given value into an array or single value
	 * 
	 * @param value
	 *            the value
	 * @return the result
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public Object parse(String id, String value, Style style, StyleSheet stylesheet)
			throws CssSyntaxError {
		// normalize the value
		value = ParserUtils.normalize(value);
		// if the given value is an array ...
		if (ParserUtils.isArray(value)) {
			// parse all array values ...
			String[] arrayValues = ParserUtils.toArray(value);
			validateCount(value, arrayValues.length);
			Object[] result = new Object[arrayValues.length];
			for (int index = 0; index < arrayValues.length; index++) {
				String arrayValue = arrayValues[index];
				result[index] = parseValue(id, arrayValue, style, stylesheet);
			}
			// and return the array
			return result;
			// otherwise ...
		} else {
			validateCount(value, 1);
			// parse and return the single value
			return parseValue(id, value, style, stylesheet);
		}
	}

	/**
	 * Parses a single value and returns it
	 * 
	 * @param value
	 *            the value
	 * @return the result
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public abstract Object parseValue(String id, String value, Style style,
			StyleSheet stylesheet) throws CssSyntaxError;

}
