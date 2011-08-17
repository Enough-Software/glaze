package de.enough.glaze.style.definition.converter.utils;

import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;

public class ColorConverterUtils {
	public static XYEdges toXYEdges(Color color) {
		return toXYEdges(color.getColor());
	}

	public static XYEdges toXYEdges(int color) {
		return new XYEdges(color, color, color, color);
	}

	public static XYEdges toXYEdges(Color[] colors, Property colorProp)
			throws CssSyntaxError {
		switch (colors.length) {
		case 2:
			return new XYEdges(colors[0].getColor(), colors[1].getColor(),
					colors[0].getColor(), colors[1].getColor());
		case 3:
			return new XYEdges(colors[0].getColor(), colors[1].getColor(),
					colors[2].getColor(), colors[1].getColor());
		case 4:
			return new XYEdges(colors[0].getColor(), colors[1].getColor(),
					colors[2].getColor(), colors[3].getColor());
		default:
			throw new CssSyntaxError("must be 1,2,3 or 4 colors", colorProp);
		}
	}
}
