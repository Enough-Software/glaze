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
 
package de.enough.glaze.content.transform;

import java.io.IOException;

/**
 * Used to transform content data before storing and passing it to
 * the parenting source.
 * @author Andre
 *
 */
public interface ContentTransform {
	/**
	 * returned by calculateDataSize() to indicate that the
	 * size of the data is unknown 
	 */
	public static int DATASIZE_UNKNOWN = Integer.MIN_VALUE;
	
	/**
	 * Returns the transformation id
	 * @return the transformation id
	 */
	public String getTransformId();
	
	/**
	 * Transforms content data from one data type to another
	 * @param rawData the raw data to transform
	 * @return the transformed data
	 * @throws IOException if an error occurs
	 */
	public Object transformContent(Object rawData) throws IOException;
	
	/**
	 * Calculates the data size of the transformed data in bytes
	 * @param transformedData the transformed data
	 * @return the calculated size
	 */
	public int calculateDataSize(Object transformedData);
}
