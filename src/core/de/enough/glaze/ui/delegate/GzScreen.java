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
 
package de.enough.glaze.ui.delegate;

/**
 * An interface to extend {@link Screen} instances for the use in Glaze
 * 
 * @author Andre
 * 
 */
public interface GzScreen extends GzManager {

	/**
	 * Return the field manager of an implementing screen.
	 * 
	 * @return the field manager
	 */
	public GzManager getFieldManager();
}
