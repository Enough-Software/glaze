package de.enough.glaze.style.parser.property;

import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.utils.ParserUtils;

public abstract class PropertyParser {
	/**
	 * Parses the given value into an array or single value
	 * 
	 * @param value
	 *            the value
	 * @return the result
	 * @throws CssSyntaxError
	 *             if the syntax is wrong
	 */
	public Object parse(Property property) throws CssSyntaxError {
		String value = property.getValue();
		// normalize the value
		value = ParserUtils.normalize(value);
		// if the given value is an array ...
		if (ParserUtils.isArray(value)) {
			// parse all array values ...
			String[] arrayValues = ParserUtils.toArray(value);
			Object[] result = new Object[arrayValues.length];
			for (int index = 0; index < arrayValues.length; index++) {
				String arrayValue = arrayValues[index];
				result[index] = parse(arrayValue);
			}
			// and return the array
			return result;
			// otherwise ...
		} else {
			// parse and return the single value
			return parse(value);
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
	protected abstract Object parse(String value) throws CssSyntaxError;

}