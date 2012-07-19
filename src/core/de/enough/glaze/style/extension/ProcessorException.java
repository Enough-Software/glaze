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

import de.enough.glaze.ui.component.Field;

/**
 * A processor exception to be thrown if the given data of {@link Processor} is
 * not applicable to a {@link Field}.
 * 
 * @author Andre
 * 
 */
public class ProcessorException extends Exception {

	/**
	 * Constructs a new {@link ProcessorException} instance
	 * 
	 * @param message
	 *            the message
	 */
	public ProcessorException(String message) {
		super(message);
	}
}
