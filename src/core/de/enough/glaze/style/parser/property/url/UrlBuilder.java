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
 
package de.enough.glaze.style.parser.property.url;

import de.enough.glaze.style.Url;
import de.enough.glaze.style.parser.property.UrlPropertyParser;

/**
 * An interface to handle relative urls in stylesheets. Used by
 * {@link UrlPropertyParser} to resolve urls.
 * 
 * @author Andre
 * 
 */
public interface UrlBuilder {

	/**
	 * Returns an {@link Url} instance with the given base url.
	 * 
	 * @param url
	 *            the url
	 * @return the {@link Url} instance
	 */
	public Url getResourceUrl(String url);
}
