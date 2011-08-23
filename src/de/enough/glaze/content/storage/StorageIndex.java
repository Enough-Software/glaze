package de.enough.glaze.content.storage;

import java.util.Vector;

import com.google.zxing.common.Collections;
import com.google.zxing.common.Comparator;

import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.helper.ToStringHelper;

/**
 * Serves as an index for stored contents
 * 
 * @author Andre Schmidt
 * 
 */
public class StorageIndex implements Comparator {
	/**
	 * the index list
	 */
	Vector index;

	/**
	 * the current cache size
	 */
	int cacheSize = 0;

	/**
	 * the maximum cache size
	 */
	final int maxCacheSize;

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
	public StorageIndex(final int maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
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
		this.cacheSize += reference.size();
	}

	/**
	 * Removes the specified reference from the index and decreases the cache
	 * size by the size of the content
	 * 
	 * @param reference
	 *            the storage reference
	 */
	void removeFromIndex(StorageReference reference) {
		this.cacheSize -= reference.size();
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
	 * Returns the current cache size
	 * 
	 * @return the current cache size
	 */
	public int getCacheSize() {
		return this.cacheSize;
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
	 * Returns true, if the current cache size is greater than the threshold,
	 * otherwise false
	 * 
	 * @return true, if the current cache size is greater than the threshold,
	 *         otherwise false
	 */
	public boolean isCleanNeeded() {
		return getCacheSize() > this.maxCacheSize;
	}

	/* (non-Javadoc)
	 * @see net.rim.device.api.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object first, Object second) {
		StorageReference reference = (StorageReference) first;
		StorageReference master = (StorageReference) second;

		// if the reference is disposable to the master ...
		if (isDisposableTo(reference, master)) {
			// return smaller
			return -1;
		} else {
			// return greater
			return 1;
		}
	}

	/**
	 * Returns true, if the reference is disposable to the specified master,
	 * otherwise false
	 * 
	 * @param reference
	 *            the reference
	 * @param master
	 *            the master
	 * @return true, if the reference is disposable to the specified master,
	 *         otherwise false
	 */
	public boolean isDisposableTo(StorageReference reference,
			StorageReference master) {
		return reference.getCreationTime() < master.getCreationTime();
	}

	/**
	 * Applies the clean order to this index by the use of the internal index
	 * array and the compareTo() method
	 */
	public void applyOrder() {
		this.index.trimToSize();
		Collections.insertionSort(this.index, this);
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
				.set("cacheSize",this.cacheSize)
				.set("maxCacheSize", this.maxCacheSize)
				.set("isPrepared", this.isPrepared)
				.set("index", this.index)
				.toString();
	}
}
