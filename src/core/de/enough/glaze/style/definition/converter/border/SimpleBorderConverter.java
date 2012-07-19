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
 
package de.enough.glaze.style.definition.converter.border;

import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.decor.Border;
import de.enough.glaze.style.Color;
import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.definition.converter.utils.BorderConverterUtils;
import de.enough.glaze.style.definition.converter.utils.ColorConverterUtils;
import de.enough.glaze.style.definition.converter.utils.DimensionConverterUtils;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.parser.property.ValuePropertyParser;
import de.enough.glaze.style.property.border.GzBorderFactory;

/**
 * A {@link Converter} implementation to convert a definition to a simple border
 * 
 * @author Andre
 * 
 */
public class SimpleBorderConverter implements Converter {
	/**
	 * the instance
	 */
	private static SimpleBorderConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static SimpleBorderConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SimpleBorderConverter();
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

		XYEdges borderColors = ColorConverterUtils.toXYEdges(0x000000);
		XYEdges borderWidths = DimensionConverterUtils.toXYEdges(1);
		XYEdges borderStyles = new XYEdges(Border.STYLE_SOLID,
				Border.STYLE_SOLID, Border.STYLE_SOLID, Border.STYLE_SOLID);

		if (borderColorProp != null) {
			Object result = ColorPropertyParser.getInstance().parse(
					borderColorProp);
			if (result instanceof Color) {
				Color color = (Color) result;
				borderColors = ColorConverterUtils.toXYEdges(color);
			} else if (result instanceof Color[]) {
				Color[] colors = (Color[]) result;
				borderColors = ColorConverterUtils.toXYEdges(colors,
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
				String borderStyleValue = (String) result;
				borderStyles = BorderConverterUtils.toXYEdges(borderStyleValue,
						borderStyleProp);
			} else if(result instanceof String[]) {
				String[] borderStyleValues = (String[]) result;
				borderStyles = BorderConverterUtils.toXYEdges(borderStyleValues,
						borderStyleProp);
			}
		}

		return GzBorderFactory.createSimpleBorder(borderWidths, borderColors,
				borderStyles);
	}

}
