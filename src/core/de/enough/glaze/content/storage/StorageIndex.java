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

import java.util.Vector;

import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.helper.ToStringHelper;

/**
 * Serves as an index for stored contents
 * 
 * @author Andre Schmidt
 * 
 */
public class StorageIndex {
	/**
	 * the index list
	 */
	Vector index;

	/**
	 * is the StorageIndex prepared ?
	 */
	boolean isPrepared;

	/**
	 * Creates a new StorageIndex instance
	 * 
	 * @param maxCacheSize
	 *            the maximum cache size for this instances
	 */
	public StorageIndex() {
		this.index = new Vector();
		this.isPrepared = false;
	}

	/**
	 * Prepares this index by calling load and adding the loaded references to
	 * the index
	 */
	public void prepare() {
		Vector loadedIndex = load();

		if (loadedIndex != null) {
			for (int i = 0; i < loadedIndex.size(); i++) {
				addToIndex((StorageReference) loadedIndex.elementAt(i));
			}
		}

		this.isPrepared = true;
	}

	/**
	 * Adds the specified reference to the index and increases the cache size by
	 * the size of the content
	 * 
	 * @param reference
	 *            the storage reference
	 */
	void addToIndex(StorageReference reference) {
		this.index.addElement(reference);
	}

	/**
	 * Removes the specified reference from the index and decreases the cache
	 * size by the size of the content
	 * 
	 * @param reference
	 *            the storage reference
	 */
	void removeFromIndex(StorageReference reference) {
		this.index.removeElement(reference);
	}

	/**
	 * Returns true, if the index is prepared, otherwise false
	 * 
	 * @return true, if the index is prepared, otherwise false
	 */
	public boolean isPrepared() {
		return this.isPrepared;
	}

	/**
	 * Adds a reference to the index and stores the index
	 * 
	 * @param reference
	 *            the reference
	 */
	public void addReference(StorageReference reference) {
		addToIndex(reference);
		store(this.index);
	}

	/**
	 * Removes a reference from the index and stores the index
	 * 
	 * @param reference
	 *            the reference
	 */
	public void removeReference(StorageReference reference) {
		removeFromIndex(reference);
		store(this.index);
	}

	/**
	 * Returns the index of the first disposable content.
	 * 
	 * @return the index of the first disposable content.
	 */
	public int getDisposableIndex() {
		return 0;
	}

	/**
	 * Returns the reference at position i
	 * 
	 * @param i
	 *            the position
	 * @return the reference at the specified position
	 */
	public StorageReference getReference(int i) {
		return (StorageReference) this.index.elementAt(i);
	}

	/**
	 * Returns the number of the references in this index
	 * 
	 * @return the number of the references in this index
	 */
	public int size() {
		return this.index.size();
	}

	/**
	 * Loads the ArrayList representing the index. It is encouraged to overwrite
	 * this method for persistence of the index.
	 * 
	 * @return the ArrayList representing the index
	 */
	protected Vector load() {
		// implement for persistence
		return null;
	}

	/**
	 * Stores the ArrayList representing the index. It is encouraged to
	 * overwrite this method for persistence of the index.
	 * 
	 * @param index
	 *            the ArrayList representing the index
	 */
	protected void store(Vector index) {
		// implement for persistence
	}

	/**
	 * Returns the reference for the specified descriptor if it is present in
	 * the index
	 * 
	 * @param descriptor
	 *            the content descriptor
	 * @return the found reference or null
	 */
	public StorageReference getReference(ContentDescriptor descriptor) {
		int index = this.index.indexOf(descriptor);

		if (index != -1) {
			return (StorageReference) this.index.elementAt(index);
		}

		return null;
	}
	
	/**
	 * Called when a content storage is shutdown. Overwrite this to implements
	 * shutdown behaviour.
	 */
	public void shutdown() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ToStringHelper.createInstance("StorageIndex")
				.set("isPrepared", this.isPrepared)
				.set("index", this.index)
				.toString();
	}
}
