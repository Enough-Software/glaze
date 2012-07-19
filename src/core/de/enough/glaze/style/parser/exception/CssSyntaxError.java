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
 
package de.enough.glaze.style.parser.exception;

import de.enough.glaze.style.parser.property.Property;

/**
 * An exception implementation for CSS syntax errors
 * 
 * @author Andre
 * 
 */
public class CssSyntaxError extends Exception {

	/**
	 * the line
	 */
	private int line = Integer.MIN_VALUE;

	/**
	 * the value
	 */
	private String value;

	/**
	 * the error
	 */
	private String error;

	/**
	 * the message
	 */
	private String message;

	/**
	 * Constructs a new {@link CssSyntaxError} instance
	 * 
	 * @param error
	 *            the error
	 * @param line
	 *            the line
	 */
	public CssSyntaxError(String error, int line) {
		this(error, null, line);
	}

	/**
	 * Constructs a new {@link CssSyntaxError} instance
	 * 
	 * @param error
	 *            the error
	 * @param property
	 *            the attribute
	 */
	public CssSyntaxError(String error, Property property) {
		this(error, property.getValue(), property.getLine());
	}

	/**
	 * Constructs a new {@link CssSyntaxError} instance
	 * 
	 * @param error
	 *            the error
	 * @param value
	 *            the value
	 * @param line
	 *            the line
	 */
	public CssSyntaxError(String error, String value, int line) {
		super();
		this.error = error;
		this.value = value;
		this.line = line;
	}

	/**
	 * Returns the line
	 * 
	 * @return the line
	 */
	public int getLine() {
		return this.line;
	}

	/**
	 * Returns the value
	 * 
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Returns the error
	 * 
	 * @return the error
	 */
	public String getError() {
		return this.error;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		if (this.message == null) {
			StringBuffer messageBuffer = new StringBuffer();
			if(this.line != Integer.MIN_VALUE) {
				messageBuffer.append(this.line);
				messageBuffer.append(" : ");
			} 
			messageBuffer.append(this.error);
			messageBuffer.append(" : ");
			messageBuffer.append(this.value);
			this.message = messageBuffer.toString();
		}

		return this.message;
	}

}
