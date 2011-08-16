package de.enough.glaze.style.parser.exception;

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
	 * @param error
	 *            the error
	 */
	public CssSyntaxError(String error) {
		this(Integer.MIN_VALUE, null, error);
	}

	/**
	 * Constructs a new {@link CssSyntaxError} instance
	 * 
	 * @param value
	 *            the value
	 * @param error
	 *            the error
	 */
	public CssSyntaxError(String value, String error) {
		this(Integer.MIN_VALUE, value, error);
	}

	/**
	 * Constructs a new {@link CssSyntaxError} instance
	 * 
	 * @param line
	 *            the line
	 * @param error
	 *            the error
	 */
	public CssSyntaxError(int line, String error) {
		this(line, null, error);
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
	public CssSyntaxError(int line, String value, String error) {
		super();
		this.line = line;
		this.value = value;
		this.error = error;
	}

	/**
	 * Constructs a new {@link CssSyntaxError} instance
	 * 
	 * @param line
	 *            the line
	 * @param exception
	 *            the base exception
	 */
	public CssSyntaxError(int line, CssSyntaxError exception) {
		this(line, exception.getValue(), exception.getError());

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
