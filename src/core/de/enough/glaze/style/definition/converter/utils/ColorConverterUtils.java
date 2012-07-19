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
 
package de.enough.glaze.style.definition.converter.utils;

import net.rim.device.api.ui.XYEdges;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;

/**
 * Provides helper methods for converters using colors
 * 
 * @author Andre
 * 
 */
public class ColorConverterUtils {

	/**
	 * Creates a {@link XYEdges} instance from the given color
	 * 
	 * @param color
	 *            the color
	 * @return the created {@link XYEdges} instance
	 */
	public static XYEdges toXYEdges(Color color) {
		return toXYEdges(color.getColor());
	}

	/**
	 * Creates a {@link XYEdges} instance from the given color
	 * 
	 * @param color
	 *            the color
	 * @return the created {@link XYEdges} instance
	 */
	public static XYEdges toXYEdges(int color) {
		return new XYEdges(color, color, color, color);
	}

	/**
	 * Creates a {@link XYEdges} instance from the given colors
	 * 
	 * @param color
	 *            the color
	 * @return the created {@link XYEdges} instance
	 */
	public static XYEdges toXYEdges(Color[] colors, Property colorProp)
			throws CssSyntaxError {
		switch (colors.length) {
		case 4:
			return new XYEdges(colors[0].getColor(), colors[1].getColor(),
					colors[2].getColor(), colors[3].getColor());
		default:
			throw new CssSyntaxError("must be 1 or 4 colors", colorProp);
		}
	}
}
