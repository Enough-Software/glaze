package de.enough.glaze.style.definition.converter.background;

import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.property.background.GzBackgroundFactory;

/**
 * A {@link Converter} implementation to convert a definition to a rounded
 * background
 * 
 * @author Andre
 * 
 */
public class RoundedBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static RoundedBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static RoundedBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RoundedBackgroundConverter();
		}

		return INSTANCE;
	}

	public String[] getIds() {
		return new String[] { "background-color", "background-width" };
	}

	public Object convert(Definition definition) throws CssSyntaxError {
		if (!definition.hasProperties(this)) {
			return null;
		}

		Property backgroundTypeProp = definition.getProperty("background-type");
		Property backgroundColorProp = definition
				.getProperty("background-color");
		Property backgroundWidthProp = definition
				.getProperty("background-width");

		Color color = null;
		XYEdges widths = null;

		if (backgroundColorProp != null) {
			Object result = ColorPropertyParser.getInstance().parse(
					backgroundColorProp);
			if (result instanceof Color) {
				color = (Color) result;
			} else if (result instanceof Color[]) {
				throw new CssSyntaxError("must be a single color",
						backgroundColorProp);
			}
		}

		if (backgroundWidthProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					backgroundWidthProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				widths = DimensionConverterUtils.toXYEdges(dimension);
			} else if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				widths = DimensionConverterUtils.toXYEdges(dimensions,
						backgroundWidthProp);
			}
		}

		if (color != null && widths != null) {
			return GzBackgroundFactory.createRoundedBackground(color, widths);
		} else {
			throw new CssSyntaxError(
					"unable to create rounded background, properties are missing",
					backgroundTypeProp);
		}
	}
}
