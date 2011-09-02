package de.enough.glaze.style.definition.converter.utils;

import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.property.border.GzBorder;

public class BorderConverterUtils {
	public static XYEdges toXYEdges(String borderStyle, Property borderStyleProp)
			throws CssSyntaxError {
		return toXYEdges(getBorderStyle(borderStyle, borderStyleProp));
	}

	public static XYEdges toXYEdges(int borderStyle) {
		return new XYEdges(borderStyle, borderStyle, borderStyle, borderStyle);
	}

	public static XYEdges toXYEdges(String[] borderStyles,
			Property borderStyleProp) throws CssSyntaxError {
		switch (borderStyles.length) {
		case 2:
			return new XYEdges(
					getBorderStyle(borderStyles[0], borderStyleProp),
					getBorderStyle(borderStyles[1], borderStyleProp),
					getBorderStyle(borderStyles[0], borderStyleProp),
					getBorderStyle(borderStyles[1], borderStyleProp));
		case 3:
			return new XYEdges(
					getBorderStyle(borderStyles[0], borderStyleProp),
					getBorderStyle(borderStyles[1], borderStyleProp),
					getBorderStyle(borderStyles[2], borderStyleProp),
					getBorderStyle(borderStyles[1], borderStyleProp));
		case 4:
			return new XYEdges(
					getBorderStyle(borderStyles[0], borderStyleProp),
					getBorderStyle(borderStyles[1], borderStyleProp),
					getBorderStyle(borderStyles[2], borderStyleProp),
					getBorderStyle(borderStyles[3], borderStyleProp));
		default:
			throw new CssSyntaxError("must be 1,2,3 or 4 ids", borderStyleProp);
		}
	}

	public static int getBorderStyle(String borderStyle,
			Property borderStyleProp) throws CssSyntaxError {
		if (GzBorder.STYLE_DASHED.equals(borderStyle)) {
			return Border.STYLE_DASHED;
		} else if (GzBorder.STYLE_DOTTED.equals(borderStyle)) {
			return Border.STYLE_DOTTED;
		} else if (GzBorder.STYLE_FILLED.equals(borderStyle)) {
			return Border.STYLE_FILLED;
		} else if (GzBorder.STYLE_SOLID.equals(borderStyle)) {
			return Border.STYLE_SOLID;
		} else {
			throw new CssSyntaxError("unknown border style", borderStyleProp);
		}
	}

}
