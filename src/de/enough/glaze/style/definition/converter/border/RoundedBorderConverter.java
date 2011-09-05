package de.enough.glaze.style.definition.converter.border;

import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.utils.BorderConverterUtils;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.border.GzBorderFactory;

/**
 * A {@link Converter} implementation to convert a definition to a rounded
 * border
 * 
 * @author Andre
 * 
 */
public class RoundedBorderConverter implements Converter {
	/**
	 * the instance
	 */
	private static RoundedBorderConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static RoundedBorderConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RoundedBorderConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "border-color", "border-width", "border-style" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.definition.converter.Converter#convert(de.enough
	 * .glaze.style.definition.Definition)
	 */
	public Object convert(Definition definition) throws CssSyntaxError {
		if (!definition.hasProperties(this)) {
			return null;
		}

		Property borderColorProp = definition.getProperty("border-color");
		Property borderWidthProp = definition.getProperty("border-width");
		Property borderStyleProp = definition.getProperty("border-style");

		XYEdges borderWidths = DimensionConverterUtils.toXYEdges(1);
		int borderColor = 0xFF000000;
		int borderStyle = Border.STYLE_SOLID;

		if (borderColorProp != null) {
			Object result = ColorPropertyParser.getInstance().parse(
					borderColorProp);
			if (result instanceof Color) {
				Color color = (Color) result;
				borderColor = color.getColor();
			} else {
				throw new CssSyntaxError("must be a single color",
						borderColorProp);
			}
		}

		if (borderWidthProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					borderWidthProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				borderWidths = DimensionConverterUtils.toXYEdges(dimension);
			} else if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				borderWidths = DimensionConverterUtils.toXYEdges(dimensions,
						borderWidthProp);
			}
		}

		if (borderStyleProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					borderStyleProp);
			if (result instanceof String) {
				String borderStyleString = (String) result;
				borderStyle = BorderConverterUtils.getBorderStyle(
						borderStyleString, borderStyleProp);
			} else {
				throw new CssSyntaxError("must be a single border type",
						borderStyleProp);
			}
		}

		return GzBorderFactory.createRoundedBorder(borderWidths, borderColor,
				borderStyle);
	}

}
