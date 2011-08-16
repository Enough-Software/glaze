package de.enough.glaze.log;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

public class Log {

	/**
	 * the tag
	 */
	private static final String TAG = "GLAZE";

	/**
	 * the debug log level
	 */
	public static final byte DEBUG = 0x00;

	/**
	 * the info log level
	 */
	public static final byte INFO = 0x01;

	/**
	 * the warn log level
	 */
	public static final byte WARN = 0x02;

	/**
	 * the error log level
	 */
	public static final byte ERROR = 0x03;

	/**
	 * the log level
	 */
	private static byte logLevel = INFO;

	/**
	 * Sets the log level
	 * 
	 * @param level
	 *            the log level
	 */
	public static void setLevel(byte level) {
		logLevel = level;
	}

	/**
	 * Returns true if the given level is allowed
	 * 
	 * @param level
	 *            the level
	 * @return true if the given level is allowed otherwise false
	 */
	private static boolean isLevelAllowed(byte level) {
		return logLevel <= level;
	}

	/**
	 * Prints a debug message with the given message
	 * 
	 * @param message
	 *            the message
	 */
	public static void d(String message) {
		if (isLevelAllowed(DEBUG)) {
			message = toLog(message);
			System.out.println(message);
		}
	}

	/**
	 * Prints an info with the given message
	 * 
	 * @param message
	 *            the message
	 */
	public static void i(String message) {
		if (isLevelAllowed(INFO)) {
			message = toLog(message);
			System.out.println(message);
		}
	}

	/**
	 * Prints a warning with the given message
	 * 
	 * @param message
	 *            the message
	 */
	public static void w(String message) {
		if (isLevelAllowed(WARN)) {
			message = toLog(message);
			System.err.println(message);
		}
	}

	/**
	 * Prints an error with the given message
	 * 
	 * @param message
	 *            the message
	 */
	public static void e(String message) {
		if (isLevelAllowed(ERROR)) {
			message = toLog(message);
			System.err.println(message);
		}
	}

	/**
	 * Prints an error with the given message and {@link Throwable} instance
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the {@link Throwable}
	 */
	public static void e(String message, Throwable t) {
		if (isLevelAllowed(ERROR)) {
			message = toLog(t.getMessage());
			System.err.println(message);
			t.printStackTrace();
		}
	}

	/**
	 * Prints a syntax error with the given {@link CssSyntaxError} instance
	 * 
	 * @param error
	 *            the {@link CssSyntaxError}
	 */
	public static void s(CssSyntaxError error) {
		String message = toLog(error);
		message = toLog(message);
		System.err.println(message);
	}

	/**
	 * Adds the tag to the given message and returns it
	 * 
	 * @param message
	 *            the message
	 * @return the message with the tag
	 */
	private static String toLog(String message) {
		return TAG + ": " + message;
	}

	/**
	 * Creates a syntax error from the given {@link CssSyntaxError} instance
	 * 
	 * @param exception
	 *            the {@link CssSyntaxError}
	 * @return the created syntax error
	 */
	private static String toLog(CssSyntaxError exception) {
		StringBuffer messageBuffer = new StringBuffer();
		messageBuffer.append("syntax error");
		int line = exception.getLine();
		String value = exception.getValue();
		String error = exception.getError();
		if (line != Integer.MIN_VALUE) {
			messageBuffer.append(" in line ");
			messageBuffer.append(line);
		}

		messageBuffer.append(" : ");
		messageBuffer.append(error);
		if(value != null) {
			messageBuffer.append(" : ");
			messageBuffer.append("'" + value + "'");
		}

		return messageBuffer.toString();
	}
}
