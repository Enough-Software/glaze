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
 
package de.enough.glaze.style.extension;

import net.rim.device.api.ui.Field;
import de.enough.glaze.style.definition.converter.Converter;

/**
 * A Processor interface. Processor instances are used to apply a data object
 * created through a {@link Converter} to a field. Used in stylesheet
 * extensions.
 * 
 * @author Andre
 * 
 */
public interface Processor {
	/**
	 * Applies the given data to the given field
	 * 
	 * @param field
	 *            the field
	 * @param data
	 *            the data
	 * @throws ProcessorException
	 *             if the given data is not applicable to the given field
	 */
	public void apply(Field field, Object data) throws ProcessorException;
}
