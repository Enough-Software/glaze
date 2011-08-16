package de.enough.glaze.style.parser.attribute;

import de.enough.glaze.style.Style;
import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public class EnumAttributeParser extends AttributeParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.value.ValueParser#parseValue(java.lang.String
	 * )
	 */
	public Object parseValue(String id, String value, Style style,
			StyleSheet stylesheet) throws CssSyntaxError {
		return value;
	}

}
