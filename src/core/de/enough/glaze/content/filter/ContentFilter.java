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
 
package de.enough.glaze.content.filter;

import de.enough.glaze.content.ContentDescriptor;

/**
 * Used to filter content descriptors from a ContentSource. Given the case that
 * a ContentSource has multiple ContentSources attached to it a content filter
 * is needed to determine which ContentSource to use.
 * 
 * @author Andre
 * 
 */
public interface ContentFilter {
	/**
	 * Returns true if the descriptor is valid for the parenting ContentSource
	 * 
	 * @param descriptor
	 *            the ContentDescriptor
	 * @return true if the descriptor is valid for the parenting ContentSource
	 */
	public boolean filter(ContentDescriptor descriptor);
}
