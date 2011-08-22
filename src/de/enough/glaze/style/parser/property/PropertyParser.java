package de.enough.glaze.style.parser.property;

import java.util.Vector;

import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.utils.ParserUtils;

public abstract class PropertyParser {

	private static final Object lock;

	private static final Vector valueCollection;

	static {
		lock = new Object();
		valueCollection = new Vector();
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
	public Object parse(Property property) throws CssSyntaxError {
		synchronized (lock) {
			String value = property.getValue();
			// normalize the value
			value = ParserUtils.normalize(value);
			// if the given value is an array ...
			if (ParserUtils.isArray(value)) {
				// parse all array values ...
				String[] arrayValues = ParserUtils.toArray(value);
				valueCollection.removeAllElements();
				for (int index = 0; index < arrayValues.length; index++) {
					String arrayValue = arrayValues[index];
					valueCollection.addElement(parse(arrayValue));
				}
				// and return the array
				return toArray(valueCollection);
				// otherwise ...
			} else {
				// parse and return the single value
				return parse(value);
			}
		}
	}

	/**
	 * @param vector
	 * @return
	 */
	protected abstract Object toArray(Vector vector);

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