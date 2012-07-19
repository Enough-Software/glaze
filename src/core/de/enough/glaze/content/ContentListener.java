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
 * An interface to notify a listening instance that
 * a content is loaded or an error has occured
 * while fetching the content 
 * @author Andre Schmidt
 *
 */
public interface ContentListener {
	/**
	 * Notifies a ContentListener that content data is
	 * available
	 * @param descriptor the content descriptor
	 * @param data the content data
	 */
	public void onContentLoaded(ContentDescriptor descriptor, Object data);
	
	/**
	 * Notifies a ContentListener that an error
	 * occured while requesting content
	 * @param descriptor the content descriptor
	 * @param exception the error
	 */
	public void onContentError(ContentDescriptor descriptor,Exception exception);
	
}
