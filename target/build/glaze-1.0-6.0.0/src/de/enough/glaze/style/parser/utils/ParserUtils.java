package de.enough.glaze.style.parser.utils;

import java.util.Vector;

import de.enough.glaze.style.parser.CssParser;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public class ParserUtils {

	/**
	 * the array separators
	 */
	private static final char[] ARRAY_DELIMITERS = new char[] { ' ', '\n' };

	/**
	 * Returns true if the given value has one of the given array separator
	 * characters
	 * 
	 * @param value
	 *            the value
	 * @return true if the given value has one of the given array separator
	 *         characters otherwise false
	 */
	public static boolean isArray(String value) {
		return hasDelimiters(value, ARRAY_DELIMITERS);
	}

	/**
	 * Returns true if the delimiter is in the given value
	 * 
	 * @param value
	 *            the value
	 * @param delimiter
	 *            the delimiter
	 * @return true if the delimiter is in the given value otherwise false
	 */
	public static boolean hasDelimiter(String value, char delimiter) {
		return value.indexOf(delimiter) != -1;
	}

	/**
	 * Returns true if the delimiter is in the given value
	 * 
	 * @param value
	 *            the value
	 * @param delimiter
	 *            the delimiter
	 * @return true if the delimiter is in the given value otherwise false
	 */
	public static boolean hasDelimiter(String value, String delimiter) {
		return value.indexOf(delimiter) != -1;
	}

	/**
	 * Return true if at least one of the delimiters is in the given value
	 * 
	 * @param value
	 *            the value
	 * @param delimiters
	 *            the delimiters
	 * @return true if at least one of the delimiters is in the given value
	 *         otherwise false
	 */
	public static boolean hasDelimiters(String value, char[] delimiters) {
		for (int index = 0; index < delimiters.length; index++) {
			char delimiter = delimiters[index];
			if (hasDelimiter(value, delimiter)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns true if the delimiter is in the given value
	 * 
	 * @param value
	 *            the value
	 * @param delimiter
	 *            the delimiter
	 * @return true if the delimiter is in the given value otherwise false
	 */
	public static boolean isDelimiter(char character, char delimiter) {
		return character == delimiter;
	}

	/**
	 * Returns true if the delimiter is in the given value
	 * 
	 * @param value
	 *            the value
	 * @param delimiter
	 *            the delimiter
	 * @return true if the delimiter is in the given value otherwise false
	 */
	public static boolean isDelimiter(char character, char[] delimiters) {
		for (int index = 0; index < delimiters.length; index++) {
			char delimiter = delimiters[index];
			if (isDelimiter(character, delimiter)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Splits the given value by white spaces and returns the resulting array of
	 * values
	 * 
	 * @param value
	 *            the value
	 * @return the resulting array
	 */
	public static String[] toArray(String value) {
		if (isArray(value)) {
			return toArray(value, ARRAY_DELIMITERS);
		} else {
			throw new IllegalArgumentException("the value '" + value
					+ "' is not an array");
		}
	}

	/**
	 * Converts the given value into an array by splitting with the given
	 * delimiter
	 * 
	 * @param value
	 *            the value
	 * @param delimiter
	 *            the delimiter
	 * @return the resulting array
	 */
	public static String[] toArray(String value, char delimiter) {
		return toArray(value, new char[] { delimiter });
	}

	/**
	 * Converts the given value into an array by splitting with the given
	 * delimiters
	 * 
	 * @param value
	 *            the value
	 * @param delimiters
	 *            the delimiters
	 * @return the resulting array
	 */
	public static String[] toArray(String value, char[] delimiters) {
		char[] valueChars = value.toCharArray();
		int lastIndex = 0;
		Vector entries = null;
		// for all characters in the value ...
		for (int i = 0; i < valueChars.length; i++) {
			char character = valueChars[i];
			// check if the character is a delimiter ...
			if (isDelimiter(character, delimiters)) {
				if (entries == null) {
					entries = new Vector();
				}
				int count = i - lastIndex;
				// if entry to build is not empty ...
				if (count > 0) {
					// build and add the entry
					String entry = new String(valueChars, lastIndex, count);
					entries.addElement(entry);
				}
				lastIndex = i + 1;
			}
		}
		if (entries == null) {
			return new String[] { value };
		}
		// add the remaining character as an entry
		int count = valueChars.length - lastIndex;
		if (count > 0) {
			String entry = new String(valueChars, lastIndex, count);
			entries.addElement(entry);
		}
		// copy the vector to a normal array and return it
		String[] resultArray = new String[entries.size()];
		entries.copyInto(resultArray);
		return resultArray;
	}

	/**
	 * Converts the given value into an array by splitting with the given
	 * delimiters
	 * 
	 * @param value
	 *            the value
	 * @param delimiters
	 *            the delimiters
	 * @return the resulting array
	 */
	public static String[] toArray(String value, String delimiter) {
		Vector entries = null;

		int delimiterPosition = value.indexOf(delimiter);
		while (delimiterPosition != -1) {
			if (entries == null) {
				entries = new Vector();
			}
			String entry = value.substring(0, delimiterPosition);
			entries.addElement(entry);
			value = value.substring(delimiterPosition + delimiter.length());
			delimiterPosition = value.indexOf(delimiter);
		}

		if (entries == null) {
			return new String[] { value };
		}

		if (value.length() > 0) {
			entries.addElement(value);
		}

		// copy the vector to a normal array and return it
		String[] resultArray = new String[entries.size()];
		entries.copyInto(resultArray);
		return resultArray;
	}

	/**
	 * Converts the given value in an array to build a property
	 * 
	 * @param value
	 *            the value
	 * @param delimiter
	 *            the delimiter
	 * @return the resulting array
	 * @throws CssSyntaxError
	 */
	public static String[] toPropertyArray(String value, char delimiter) {
		value = ParserUtils.normalize(value);
		int delimiterIndex = value.indexOf(delimiter);
		if (delimiterIndex != -1) {
			String[] resultArray = new String[2];
			resultArray[0] = value.substring(0, delimiterIndex);
			resultArray[1] = value.substring(delimiterIndex + 1);
			return resultArray;
		} else {
			return null;
		}
	}

	/**
	 * Normalizes the given value
	 * 
	 * @param value
	 *            the value
	 * @return the normalized value
	 */
	public static String normalize(String value) {
		return value.trim().toLowerCase();
	}

	/**
	 * Returns true if the given id is valid
	 * 
	 * @param id
	 *            the id
	 * @return true if the given id is valid otherwise false
	 */
	public static void validate(CssParser parser, String id)
			throws CssSyntaxError {
		for (int index = 0; index < id.length(); index++) {
			char character = id.charAt(index);
			boolean valid = (character >= 'a' && character <= 'z')
					|| (character >= 'A' && character <= 'Z')
					|| (character >= '0' && character <= '9')
					|| character == '-' || character == '_';
			if (!valid) {
				throw new CssSyntaxError(
						"an id must only contain the following characters : a-z A-Z 0-9 _ -",
						id, parser.getLineNumber());
			}
		}
	}
}
