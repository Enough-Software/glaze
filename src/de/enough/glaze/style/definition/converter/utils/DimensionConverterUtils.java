package de.enough.glaze.style.definition.converter.utils;

import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;

/**
 * Provides helper methods for converters using dimensions
 * 
 * @author Andre
 * 
 */
public class DimensionConverterUtils {

	/**
	 * Converts the given dimension and creates a {@link XYEdges} instance from
	 * the result
	 * 
	 * @param dimension
	 *            the dimension
	 * @return the created {@link XYEdges} instance
	 */
	public static XYEdges toXYEdges(Dimension dimension) {
		return toXYEdges(dimension.getValue());
	}

	/**
	 * Creates a {@link XYEdges} instance from the given width
	 * 
	 * @param width
	 *            the width
	 * @return the created {@link XYEdges} instance
	 */
	public static XYEdges toXYEdges(int width) {
		return new XYEdges(width, width, width, width);
	}

	/**
	 * @param dimensions
	 * @param dimensionProp
	 * @return
	 * @throws CssSyntaxError
	 */
	public static XYEdges toXYEdges(Dimension[] dimensions,
			Property dimensionProp) throws CssSyntaxError {
		switch (dimensions.length) {
		case 4:
			return new XYEdges(dimensions[0].getValue(),
					dimensions[1].getValue(), dimensions[2].getValue(),
					dimensions[3].getValue());
		default:
			throw new CssSyntaxError("must be 1 or 4 dimensions", dimensionProp);
		}
	}

	public static Dimension[] toArray(Dimension dimension, int size) {
		Dimension[] dimensions = new Dimension[size];
		for (int index = 0; index < dimensions.length; index++) {
			dimensions[index] = dimension;
		}
		return dimensions;
	}

	/**
	 * Parses a {@link Dimension} from the given property
	 * 
	 * @param dimensionProp
	 *            the dimension property
	 * @return the parsed {@link Dimension}
	 * @throws CssSyntaxError
	 *             if the CSS syntax is wrong
	 */
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
