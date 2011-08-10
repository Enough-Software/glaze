package de.enough.glaze.style.parser.exception;

public class CssSyntaxException extends Exception {

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
	 * Constructs a new {@link CssSyntaxException} instance
	 * 
	 * @param value
	 *            the value
	 * @param error
	 *            the error
	 */
	public CssSyntaxException(String value, String error) {
		this(Integer.MIN_VALUE, value, error);
		this.value = value;
		this.error = error;
	}

	/**
	 * Constructs a new {@link CssSyntaxException} instance
	 * 
	 * @param line
	 *            the line
	 * @param value
	 *            the value
	 * @param error
	 *            the error
	 */
	public CssSyntaxException(int line, String value, String error) {
		super(toMessage(line, value, error));
		this.line = line;
		this.value = value;
		this.error = error;
	}

	/**
	 * Constructs a new {@link CssSyntaxException} instance
	 * 
	 * @param line
	 *            the line
	 * @param exception
	 *            the base exception
	 */
	public CssSyntaxException(int line, CssSyntaxException exception) {
		this(line, exception.getValue(), exception.getError());

	}

	/**
	 * Creates a text from the given line, value and error
	 * 
	 * @param line
	 *            the line
	 * @param value
	 *            the value
	 * @param error
	 *            the error
	 * @return the created text
	 */
	private static String toMessage(int line, String value, String error) {
		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer.append("syntax error");
		if (line != Integer.MIN_VALUE) {
			messageBuffer.append(" in line ");
			messageBuffer.append(line);
		}

		messageBuffer.append(" : ");
		messageBuffer.append("'" + value + "'");
		messageBuffer.append(" : ");
		messageBuffer.append(error);

		return messageBuffer.toString();
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
