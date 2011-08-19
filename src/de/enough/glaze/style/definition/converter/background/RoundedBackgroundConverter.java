package de.enough.glaze.style.definition.converter.background;

import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.background.GzBackgroundFactory;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;

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
		return new String[] { "background-color", "background-arcs" };
	}

	public Object convert(Definition definition) throws CssSyntaxError {
		if (!definition.hasProperties(this)) {
			return null;
		}

		Property backgroundColorProp = definition
				.getProperty("background-color");
		Property backgroundArcsProp = definition.getProperty("background-arcs");

		Color color = null;
		Dimension[] arcs = null;

		if (backgroundColorProp != null) {
			Object result = ColorPropertyParser.getInstance().parse(
					backgroundColorProp);
			if (result instanceof Color) {
				color = (Color) result;
			} else if (result instanceof Color[]) {
				throw new CssSyntaxError("must be a single color",
						backgroundColorProp);
			}
		} else {
			color = new Color(0);
		}

		if (backgroundArcsProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					backgroundArcsProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				arcs = DimensionConverterUtils.toArray(dimension, 1);
			} else if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				if (dimensions.length <= 4) {
					arcs = dimensions;
				} else {
					throw new CssSyntaxError("must be 1,2,3 or 4 dimensions",
							backgroundArcsProp);
				}
			}
		} else {
			arcs = DimensionConverterUtils.toArray(new Dimension(5,
					Dimension.UNIT_PERCENT), 1);
		}

		return GzBackgroundFactory.createRoundrectBackground(color, arcs);
	}
}
