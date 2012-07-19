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

import de.enough.glaze.style.definition.converter.Converter;

/**
 * An extension holding a {@link Converter} and a {@link Processor}
 * 
 * @author Andre
 * 
 */
public class Extension {

	/**
	 * the converter
	 */
	private final Converter converter;

	/**
	 * the processor
	 */
	private final Processor processor;

	/**
	 * Constructs a new {@link Extension} instance
	 * 
	 * @param converter
	 *            the converter
	 * @param processor
	 *            the processor
	 */
	public Extension(Converter converter, Processor processor) {
		this.converter = converter;
		this.processor = processor;
	}

	/**
	 * Returns the converter
	 * 
	 * @return the converter
	 */
	public Converter getConverter() {
		return converter;
	}

	/**
	 * Returns the processor
	 * 
	 * @return the processor
	 */
	public Processor getProcessor() {
		return processor;
	}

}
