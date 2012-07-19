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
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.property.border.GzBorder;

/**
 * Provides helper methods for border converters
 * 
 * @author Andre
 * 
 */
public class BorderConverterUtils {

	/**
	 * Converts the given border style value and creates a {@link XYEdges}
	 * instance from the result
	 * 
	 * @param borderStyle
	 *            the border style value
	 * @param borderStyleProp
	 *            the border style property
	 * @return the created {@link XYEdges} instance
	 * @throws CssSyntaxError
	 *             if the CSS syntax is wrong
	 */
	public static XYEdges toXYEdges(String borderStyle, Property borderStyleProp)
			throws CssSyntaxError {
		return toXYEdges(getBorderStyle(borderStyle, borderStyleProp));
	}

	/**
	 * Creates a {@link XYEdges} instance from the given border style
	 * 
	 * @param borderStyle
	 *            the border style
	 * @return the created {@link XYEdges} instance
	 */
	public static XYEdges toXYEdges(int borderStyle) {
		return new XYEdges(borderStyle, borderStyle, borderStyle, borderStyle);
	}

	/**
	 * Converts the given border style values and creates a {@link XYEdges}
	 * instance from the results
	 * 
	 * @param borderStyles
	 *            the border style values
	 * @param borderStyleProp
	 *            the border style property
	 * @return the created {@link XYEdges} instance
	 * @throws CssSyntaxError
	 *             if the CSS syntax is wrong
	 */
	public static XYEdges toXYEdges(String[] borderStyles,
			Property borderStyleProp) throws CssSyntaxError {
		switch (borderStyles.length) {
		case 4:
			return new XYEdges(
					getBorderStyle(borderStyles[0], borderStyleProp),
					getBorderStyle(borderStyles[1], borderStyleProp),
					getBorderStyle(borderStyles[2], borderStyleProp),
					getBorderStyle(borderStyles[3], borderStyleProp));
		default:
			throw new CssSyntaxError("must be 1 or 4 border styles", borderStyleProp);
		}
	}

	/**
	 * Returns the border style constant for the given border style value
	 * 
	 * @param borderStyle
	 *            the border style value
	 * @param borderStyleProp
	 *            the border style property
	 * @return the border style constatn
	 * @throws CssSyntaxError
	 *             if the CSS syntax is wrong
	 */
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
