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

import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.Url;
import de.enough.glaze.style.parser.property.UrlPropertyParser;

/**
 * The default implementation of {@link UrlBuilder} used by
 * {@link UrlPropertyParser}.
 * 
 * @author Andre
 * 
 */
public class DefaultUrlBuilder implements UrlBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.property.url.UrlBuilder#getResourceUrl(java
	 * .lang.String)
	 */
	public Url getResourceUrl(String url) {
		// use the stylesheet url to use as a context for the given url
		Url stylesheetUrl = StyleSheet.getInstance().getUrl();
		return new Url(url.toString(), stylesheetUrl);
	}

}
