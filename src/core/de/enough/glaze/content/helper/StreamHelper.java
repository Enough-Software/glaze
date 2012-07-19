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
 
package de.enough.glaze.content.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamHelper {
	/**
	 * the default length for the temporary buffer is 8 kb
	 */
	public static final int DEFAULT_BUFFER = 8 * 1024;

	/**
	 * Reads the complete input stream into a byte array using a 8kb temporary
	 * buffer
	 * 
	 * @param in
	 *            the input stream
	 * @return the read data
	 * @throws IOException
	 *             when reading fails
	 * @throws NullPointerException
	 *             when the given input stream is null
	 */
	public static byte[] toBytes(InputStream in) throws IOException {
		return toBytes(in, DEFAULT_BUFFER);
	}

	/**
	 * Reads the complete input stream into a byte array using the specified
	 * buffer size
	 * 
	 * @param in
	 *            the input stream
	 * @param bufferLength
	 *            the length of the used temporary buffer
	 * @return the read data
	 * @throws IOException
	 *             when reading fails
	 * @throws NullPointerException
	 *             when the given input stream is null
	 */
	public static byte[] toBytes(InputStream in, int bufferLength)
			throws IOException {
		return toBytes(in, new byte[bufferLength]);
	}

	/**
	 * Reads the complete input stream into a byte array using the specified
	 * buffer
	 * 
	 * @param in
	 *            the input stream
	 * @param buffer
	 *            the used temporary buffer
	 * @return the read data
	 * @throws IOException
	 *             when reading fails
	 * @throws NullPointerException
	 *             when the given input stream is null
	 */
	public static byte[] toBytes(InputStream in, byte[] buffer)
			throws IOException {
		int bufferLength = buffer.length;
		int read;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((read = in.read(buffer, 0, bufferLength)) != -1) {
			out.write(buffer, 0, read);
		}
		out.flush();
		in.close();
		out.close();
		return out.toByteArray();
	}
}
