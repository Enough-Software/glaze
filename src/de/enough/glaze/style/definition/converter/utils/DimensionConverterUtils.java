package de.enough.glaze.style.definition.converter.utils;

import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;

public class DimensionConverterUtils {
	public static XYEdges toXYEdges(Dimension dimension) {
		return toXYEdges(dimension.getValue());
	}

	public static XYEdges toXYEdges(int width) {
		return new XYEdges(width, width, width, width);
	}

	public static XYEdges toXYEdges(Dimension[] dimensions,
			Property dimensionProp) throws CssSyntaxError {
		switch (dimensions.length) {
		case 2:
			return new XYEdges(dimensions[0].getValue(),
					dimensions[1].getValue(), dimensions[0].getValue(),
					dimensions[1].getValue());
		case 3:
			return new XYEdges(dimensions[0].getValue(),
					dimensions[1].getValue(), dimensions[2].getValue(),
					dimensions[1].getValue());
		case 4:
			return new XYEdges(dimensions[0].getValue(),
					dimensions[1].getValue(), dimensions[2].getValue(),
					dimensions[3].getValue());
		default:
			throw new CssSyntaxError("must be 1,2,3 or 4 dimensions",
					dimensionProp);
		}
	}

	public static Dimension[] toArray(Dimension dimension, int size) {
		Dimension[] dimensions = new Dimension[size];
		for (int index = 0; index < dimensions.length; index++) {
			dimensions[index] = dimension;
		}
		return dimensions;
	}

	public static Dimension toDimension(Property dimensionProp)
			throws CssSyntaxError {
		Object result = DimensionPropertyParser.getInstance().parse(
				dimensionProp);
		if (result instanceof Dimension) {
			return (Dimension) result;
		} else {
			throw new CssSyntaxError("must be a single dimension",
					dimensionProp);
		}
	}
}
