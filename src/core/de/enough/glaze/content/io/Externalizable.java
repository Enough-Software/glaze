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
 
package de.enough.glaze.content.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * <p>Defines methods for serializing and de-serializing classes.</p>
 * <p>Note that classes implementing the Externalizable interface are required
 *    to provide a default constructor without any parameters.
 *    This is turn means that no final instance fields are allowed.
 * </p>
 * <p>Copyright Enough Software 2006 - 2009</p>
 * <pre>
 * history
 *        13-Mar-2006 - rob creation
 * </pre>
 * @author Robert Virkus, j2mepolish@enough.de
 * @see de.enough.polish.io.Storage
 * @see de.enough.polish.io.RmsStorage
 */
public interface Externalizable {
	
	/**
	 * Stores the internal instance fields to the output stream.
	 * 
	 * @param out the output stream to which instance fields should be written
	 * @throws IOException when writing fails
	 */
	void write( DataOutputStream out )
	throws IOException;
	
	/**
	 * Restores the internal instance fields from the given input stream.
	 * 
	 * @param in the input stream from which the data is loaded
	 * @throws IOException when reading fails
	 */
	void read( DataInputStream in )
	throws IOException;

}
