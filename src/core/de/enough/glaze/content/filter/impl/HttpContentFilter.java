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
 
package de.enough.glaze.content.filter.impl;

import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.filter.ContentFilter;

public class HttpContentFilter implements ContentFilter {

	public boolean filter(ContentDescriptor descriptor) {
		return descriptor.getUrl().startsWith("http://");
	}

}
