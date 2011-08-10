package de.enough.glaze.style.parser;

import de.enough.glaze.style.parser.exception.CssSyntaxException;
import de.enough.glaze.style.parser.utils.ParserUtils;

public abstract class ValueParser {

	public String[] getNames() {
		return null;
	}

	/**
	 * Parses the given value into an array or single value
	 * 
	 * @param value
	 *            the value
	 * @return the result
	 * @throws CssSyntaxException
	 *             if the syntax is wrong
	 */
	public Object parse(String value) throws CssSyntaxException {
		// normalize the value
		value = ParserUtils.normalize(value);
		// if the given value is an array ...
		if (ParserUtils.isArray(value)) {
			// parse all array values ...
			String[] arrayValues = ParserUtils.toArray(value);
			Object[] result = new Object[arrayValues.length];
			for (int index = 0; index < arrayValues.length; index++) {
				String arrayValue = arrayValues[index];
				result[index] = parseValue(arrayValue);
			}
			// and return the array
			return result;
			// otherwise ...
		} else {
			// parse and return the single value
			return parseValue(value);
		}
	}

	/**
	 * Parses a single value and returns it
	 * 
	 * @param value
	 *            the value
	 * @return the result
	 * @throws CssSyntaxException
	 *             if the syntax is wrong
	 */
	public abstract Object parseValue(String value) throws CssSyntaxException;

}
