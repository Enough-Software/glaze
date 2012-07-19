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

public interface GzExtent {

	/**
	 * Must call FieldDelegate.getPreferredWidth(Field) in an implementing field
	 */
	public int getPreferredWidth();

	/**
	 * Must call FieldDelegate.getPreferredHeight(Field) in an implementing
	 * field
	 */
	public int getPreferredHeight();

	/**
	 * Must call super.setExtent(width,height) in an implementing field
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public void gz_setExtent(int width, int height);

	/**
	 * Must call super.getPreferredWidth() in an implementing field
	 * 
	 * @return the preferred width
	 */
	public int gz_getPreferredWidth();

	/**
	 * Must call super.getPreferredHeight() in an implementing field
	 * 
	 * @return the preferred height
	 */
	public int gz_getPreferredHeight();
}
