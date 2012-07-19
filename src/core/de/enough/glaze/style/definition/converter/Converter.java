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

import de.enough.glaze.style.definition.Definition;
import de.enough.glaze.style.parser.exception.CssSyntaxError;

public interface Converter {

	/**
	 * Returns the ids handled by this converter
	 * 
	 * @return the ids
	 */
	public String[] getIds();

	/**
	 * Convert the given definition to a stylesheet object
	 * 
	 * @param definition
	 *            the definition
	 * @return the created stylesheet object
	 * @throws CssSyntaxError
	 */
	public Object convert(Definition definition) throws CssSyntaxError;
}
