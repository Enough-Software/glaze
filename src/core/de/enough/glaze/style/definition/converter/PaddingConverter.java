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
 
package de.enough.glaze.style.definition.converter;

import de.enough.glaze.style.Dimension;
import de.enough.glaze.style.Padding;
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.DimensionPropertyParser;
import de.enough.glaze.style.parser.property.Property;

/**
 * Converts a given definition to a padding
 * 
 * @author Andre
 * 
 */
public class PaddingConverter implements Converter {

	/**
	 * the instance
	 */
	private static PaddingConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static PaddingConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PaddingConverter();
		}

		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.enough.glaze.style.definition.converter.Converter#getIds()
	 */
	public String[] getIds() {
		return new String[] { "padding", "padding-left", "padding-top",
				"padding-right", "padding-bottom" };
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
			return Padding.ZERO;
		}
		
		Padding padding = new Padding();

		Property paddingProp = definition.getProperty("padding");
		Property paddingLeftProp = definition.getProperty("padding-left");
		Property paddingTopProp = definition.getProperty("padding-top");
		Property paddingRightProp = definition.getProperty("padding-right");
		Property paddingBottomProp = definition.getProperty("padding-bottom");


		if (paddingProp != null) {
			Object result = DimensionPropertyParser.getInstance().parse(
					paddingProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				padding.set(dimension);
			} else if (result instanceof Dimension[]) {
				Dimension[] dimensions = (Dimension[]) result;
				switch (dimensions.length) {
				case 2:
					padding.set(dimensions[0], dimensions[1]);
					break;
				case 3:
					padding.set(dimensions[0], dimensions[1], dimensions[2]);
					break;
				case 4:
					padding.set(dimensions[0], dimensions[1], dimensions[2],
							dimensions[3]);
					break;
				default:
					throw new CssSyntaxError("must be 1,2,3 or 4 dimensions",
							paddingProp);
				}
			}
		}

		if (paddingLeftProp != null) {
			Object result = (Dimension) DimensionPropertyParser.getInstance()
					.parse(paddingLeftProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				padding.setLeft(dimension);
			} else {
				throw new CssSyntaxError("must be a single dimension",
						paddingLeftProp);
			}
		}

		if (paddingTopProp != null) {
			Object result = (Dimension) DimensionPropertyParser.getInstance()
					.parse(paddingTopProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				padding.setTop(dimension);
			} else {
				throw new CssSyntaxError("must be a single dimension",
						paddingTopProp);
			}
		}

		if (paddingRightProp != null) {
			Object result = (Dimension) DimensionPropertyParser.getInstance()
					.parse(paddingRightProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				padding.setRight(dimension);
			} else {
				throw new CssSyntaxError("must be a single dimension",
						paddingRightProp);
			}
		}

		if (paddingBottomProp != null) {
			Object result = (Dimension) DimensionPropertyParser.getInstance()
					.parse(paddingBottomProp);
			if (result instanceof Dimension) {
				Dimension dimension = (Dimension) result;
				padding.setBottom(dimension);
			} else {
				throw new CssSyntaxError("must be a single dimension",
						paddingBottomProp);
			}
		}

		return padding;
	}

}
