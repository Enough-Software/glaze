package de.enough.glaze.style.parser.exception;

import de.enough.glaze.style.parser.property.Property;

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
	 * Constructs a new {@link CssSyntaxError} instance
	 * 
	 * @param value
	 *            the value
	 * @param error
	 *            the error
	 */
	public CssSyntaxError(String error, String value) {
		this(error, value, Integer.MIN_VALUE);
	}

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
	 * @param line
	 *            the line
	 * @param value
	 *            the value
	 * @param error
	 *            the error
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

}