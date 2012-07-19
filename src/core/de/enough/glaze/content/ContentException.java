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
 
package de.enough.glaze.content;

/**
 * A exception if an error occurs during the retrieval of content
 * 
 * @author Andre
 * 
 */
public class ContentException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new ContentException instance
	 * @param message the message
	 */
	public ContentException(String message) {
		super(message);
	}
}
