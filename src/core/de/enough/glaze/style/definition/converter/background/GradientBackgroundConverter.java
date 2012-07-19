/*
 *
 * Copyright: (c) 2012 Enough Software GmbH & Co. KG
 *
 * Licensed under:
 * 1. MIT: http://www.opensource.org/licenses/mit-license.php
 * 2. Apache 2.0: http://opensource.org/licenses/apache2.0
 * 3. GPL with classpath exception: http://www.gnu.org/software/classpath/license.html
 *
 * You may not use this file except in compliance with these licenses.
 *
 */
 
package de.enough.glaze.style.definition.converter.background;

import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.background.GzBackgroundFactory;

/**
 * A {@link Converter} implementation to convert a definition to a gradient
 * background
 * 
 * @author Andre
 * 
 */
public class GradientBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static GradientBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static GradientBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GradientBackgroundConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "background-orientation", "background-colors",
				"background-offsets" };
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

		Property backgroundTypeProp = definition.getProperty("background-type");
		Property backgroundOrientationProp = definition
				.getProperty("background-orientation");
		Property backgroundColorsProp = definition
				.getProperty("background-colors");
		Property backgroundOffsetsProp = definition
				.getProperty("background-offsets");

		String orientation = "vertical";
		Color[] colors = null;
		Dimension[] offsets = null;

		if (backgroundColorsProp != null) {
			Object result = ColorPropertyParser.getInstance().parse(
					backgroundColorsProp);
			if (result instanceof Color[]) {
				colors = (Color[]) result;
			} else if (result instanceof Color) {
				throw new CssSyntaxError("must be 2 colors",
						backgroundColorsProp);
			}
		}

		if (backgroundOrientationProp != null) {
			Object result = ValuePropertyParser.getInstance().parse(
					backgroundOrientationProp);
			if (result instanceof String) {
				String orientationValue = (String) result;
				if ("vertical".equals(orientationValue)
						|| "horizontal".equals(orientationValue)) {
					orientation = orientationValue;
				} else {
					throw new CssSyntaxError(
							"invalid background gradient orientation",
							backgroundOrientationProp);
				}
			} else {
				throw new CssSyntaxError("must be a single id",
						backgroundOrientationProp);
			}
		} else {
			orientation = "vertical";
		}

		if (backgroundOffsetsProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					backgroundOffsetsProp);
			if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				if (dimensions.length == 2) {
					offsets = (Dimension[]) result;
				} else {
					throw new CssSyntaxError("must be 2 dimensions",
							backgroundOffsetsProp);
				}
			} else {
				throw new CssSyntaxError("must be 2 dimensions",
						backgroundOffsetsProp);
			}
		} else {
			offsets = new Dimension[] {
					new Dimension(0, Dimension.UNIT_PERCENT),
					new Dimension(100, Dimension.UNIT_PERCENT) };
		}

		if (orientation != null && colors != null && offsets != null) {
			return GzBackgroundFactory.createGradientBackground(orientation,
					colors, offsets);
		} else {
			throw new CssSyntaxError(
					"unable to create gradient background, properties are missing",
					backgroundTypeProp);
		}
	}
}
