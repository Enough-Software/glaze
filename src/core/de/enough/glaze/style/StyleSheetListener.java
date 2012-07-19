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
 
package de.enough.glaze.style;

import de.enough.glaze.style.parser.exception.CssSyntaxError;

/**
 * An interface to listen to the progress of a loading process in a
 * {@link StyleSheet}
 * 
 * @author Andre
 * 
 */
public interface StyleSheetListener {
	/**
	 * Called when the CSS stylesheet at the given url has been loaded and
	 * parsed successfully
	 * 
	 * @param url
	 *            the url
	 */
	public void onLoaded(String url);

	/**
	 * Called when a CSS syntax error was found in the CSS stylesheet
	 * 
	 * @param syntaxError
	 *            the CSS syntax error
	 */
	public void onSyntaxError(CssSyntaxError syntaxError);

	/**
	 * Called when an error occurs while loading a CSS stylesheet
	 * 
	 * @param e
	 */
	public void onError(Exception e);
}
