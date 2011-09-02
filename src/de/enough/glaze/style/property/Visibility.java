package de.enough.glaze.style.property;

import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;

public class Visibility {

	public final static int VISIBLE = 0x00;

	public final static int HIDDEN = 0x01;

	public final static int COLLAPSE = 0x02;

	public static int getVisibility(String value, Property visibilityProp)
			throws CssSyntaxError {
		if ("visible".equals(value)) {
			return VISIBLE;
		} else if ("hidden".equals(value)) {
			return HIDDEN;
		} else if ("collapse".equals(value)) {
			return COLLAPSE;
		} else {
			throw new CssSyntaxError("unknown visibility", visibilityProp);
		}
	}
}
