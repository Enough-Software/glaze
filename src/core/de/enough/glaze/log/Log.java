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
 
package de.enough.glaze.log;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

/**
 * A simple log class to output informations about the CSS parsing process etc.
 * 
 * @author Andre
 * 
 */
public class Log {

	/**
	 * the tag
	 */
	public static final String TAG = "GLAZE";

	private static final String HEADER = "\n//" + TAG
			+ "/////////////////////////////////";

	private static final String SYNTAX_ERROR = "\nA CSS syntax error was found : \n\n";

	private static final String FOOTER = "\n////////////////////////////////////////\n";

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
	public static void debug(String message) {
		if (isLevelAllowed(DEBUG)) {
			message = toLog(message);
			System.out.println(message);
		}
	}

	/**
	 * Prints a debug message with the given message and object. Use this method
	 * to avoid intensive toString() operations if the given log level is not
	 * allowed.
	 * 
	 * @param message
	 *            the message
	 * @param object
	 *            the object
	 */
	public static void debug(String message, Object object) {
		if (isLevelAllowed(DEBUG)) {
			message = toLog(message + " : " + object);
			System.out.println(message);
		}
	}

	/**
	 * Prints an info with the given message
	 * 
	 * @param message
	 *            the message
	 */
	public static void info(String message) {
		if (isLevelAllowed(INFO)) {
			message = toLog(message);
			System.out.println(message);
		}
	}

	/**
	 * Prints an info message with the given message and object. Use this method
	 * to avoid intensive toString() operations if the given log level is not
	 * allowed.
	 * 
	 * @param message
	 *            the message
	 * @param object
	 *            the object
	 */
	public static void info(String message, Object object) {
		if (isLevelAllowed(INFO)) {
			message = toLog(message + " : " + object);
			System.out.println(message);
		}
	}

	/**
	 * Prints a warning with the given message
	 * 
	 * @param message
	 *            the message
	 */
	public static void warn(String message) {
		if (isLevelAllowed(WARN)) {
			message = toLog(message);
			System.err.println(message);
		}
	}

	/**
	 * Prints an info message with the given message and object. Use this method
	 * to avoid intensive toString() operations if the given log level is not
	 * allowed.
	 * 
	 * @param message
	 *            the message
	 * @param object
	 *            the object
	 */
	public static void warn(String message, Object object) {
		if (isLevelAllowed(WARN)) {
			message = toLog(message + " : " + object);
			System.out.println(message);
		}
	}

	/**
	 * Prints an error with the given message
	 * 
	 * @param message
	 *            the message
	 */
	public static void error(String message) {
		message = toLog(message);
		System.err.println(message);
	}

	/**
	 * Prints an info message with the given message and object. Use this method
	 * to avoid intensive toString() operations if the given log level is not
	 * allowed.
	 * 
	 * @param message
	 *            the message
	 * @param object
	 *            the object
	 */
	public static void error(String message, Object object) {
		error(message + " : " + object);
	}

	/**
	 * Prints an error with the given message and {@link Throwable} instance
	 * 
	 * @param message
	 *            the message
	 * @param t
	 *            the {@link Throwable}
	 */
	public static void error(String message, Throwable t) {
		message = toLog(t.getMessage());
		System.err.println(message);
		t.printStackTrace();
	}

	/**
	 * Prints a syntax error with the given {@link CssSyntaxError} instance
	 * 
	 * @param error
	 *            the {@link CssSyntaxError}
	 */
	public static void syntaxError(CssSyntaxError syntaxError) {
		System.err.println(HEADER);
		System.err.print(SYNTAX_ERROR);
		System.err.println(syntaxError.toString());
		System.err.println(FOOTER);
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
}
