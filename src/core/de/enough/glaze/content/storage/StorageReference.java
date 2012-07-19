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
 
package de.enough.glaze.content.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.helper.ToStringHelper;
import de.enough.glaze.content.io.Serializer;

/**
 * Represents a reference to stored content data.
 * 
 * @author Andre Schmidt
 * 
 */
public class StorageReference extends ContentDescriptor {
	/**
	 * the reference to the data
	 */
	Object reference;

	/**
	 * Default descriptor for instantiation for serialization DO NOT USE !
	 */
	public StorageReference() {
	}

	/**
	 * Creates a new StorageReference instance.
	 * 
	 * @param descriptor
	 *            the content descriptor
	 * @param size
	 *            the size of the content data
	 * @param reference
	 *            the reference to the content data
	 */
	public StorageReference(ContentDescriptor descriptor, int size,
			Object reference) {
		super(descriptor);
		this.reference = reference;
	}

	/**
	 * Returns the reference to the content data
	 * 
	 * @return the reference to the content data
	 */
	public Object getReference() {
		return this.reference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.polish.content.ContentDescriptor#read(java.io.DataInputStream)
	 */
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.reference = Serializer.deserialize(in);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.polish.content.ContentDescriptor#write(java.io.DataOutputStream
	 * )
	 */
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		Serializer.serialize(this.reference, out);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ToStringHelper.createInstance("StorageReference")
				.set("url", this.url).set("hash", this.hash)
				.set("reference", this.reference).toString();
	}
}
