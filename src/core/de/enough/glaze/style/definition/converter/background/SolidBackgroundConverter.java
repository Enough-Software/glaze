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
import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.definition.converter.Converter;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.ColorPropertyParser;
import de.enough.glaze.style.parser.property.Property;
import de.enough.glaze.style.property.background.GzBackgroundFactory;

/**
 * A {@link Converter} implementation to convert a definition to a solid
 * background
 * 
 * @author Andre
 * 
 */
public class SolidBackgroundConverter implements Converter {

	/**
	 * the instance
	 */
	private static SolidBackgroundConverter INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static SolidBackgroundConverter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SolidBackgroundConverter();
		}

		return INSTANCE;
	}

	public String[] getIds() {
		return new String[] { "background-color" };
	}

	public Object convert(Definition definition) throws CssSyntaxError {
		if (!definition.hasProperties(this)) {
			return null;
		}

		Property backgroundColorProp = definition
				.getProperty("background-color");

		if (backgroundColorProp != null) {
			Color backgroundColor = (Color) ColorPropertyParser.getInstance()
					.parse(backgroundColorProp);
			if (backgroundColor != null) {
				return GzBackgroundFactory
						.createSolidBackground(backgroundColor);
			}
		}

		return null;
	}

}
