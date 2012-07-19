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
 
package de.enough.glaze.style.property;

import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.Property;

/**
 * Constants and methods for the visibility of a field
 * 
 * @author Andre
 * 
 */
public class Visibility {

	/**
	 * the visible constant
	 */
	public final static int VISIBLE = 0x00;

	/**
	 * the hidden constant
	 */
	public final static int HIDDEN = 0x01;

	/**
	 * the collapse constant
	 */
	public final static int COLLAPSE = 0x02;

	/**
	 * Returns the visibility constant for the given value
	 * 
	 * @param value
	 *            the value
	 * @param visibilityProp
	 *            the visibility property
	 * @return the visibility constant
	 * @throws CssSyntaxError
	 *             if the CSS syntax is wrong
	 */
	public static int getVisibility(String value, Property visibilityProp)
			throws CssSyntaxError {
		if ("visible".equals(value)) {
			return VISIBLE;
		} else if ("hidden".equals(value)) {
			return HIDDEN;
		} else if ("collapse".equals(value)) {
			return COLLAPSE;
		} else {
			throw new CssSyntaxError("unknown visibility", visibilityProp);
		}
	}
}
