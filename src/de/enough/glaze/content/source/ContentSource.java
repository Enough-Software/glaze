package de.enough.glaze.content.source;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.content.filter.ContentFilter;
import de.enough.glaze.content.helper.ToStringHelper;
import de.enough.glaze.content.storage.StorageIndex;
import de.enough.glaze.content.storage.StorageReference;
import de.enough.glaze.content.transform.ContentTransform;
import de.enough.glaze.log.Log;

/**
 * <p>
 * ContentSource implementations are used to load, store and destruct contents
 * through a hierachy of hosted ContentSources of a parenting ContentSource,
 * e.g.:
 * </p>
 * <p>
 * HttpContentSource http = new HttpContentSource();<br/>
 * RMSStorage rms = new RMSStorage();<br/>
 * rms.attachSource(http);<br/>
 * ContentLoader loader = new ContentLoader();<br/>
 * loader.attachSource(rms);<br/>
 * </p>
 * <p>
 * The sample code above will create a hierachy to load content from http, store
 * / load content from the RMS and load/store content from the memory.
 * </p>
 * <p>
 * A ContentSource implementation can be implemented in two ways : as a source
 * or a storage. A source only loads content (e.g. a http source) while a
 * storage both loads and stores content (e.g. a file storage). A storage is
 * identified through the setting of a StorageIndex implementation.
 * </p>
 * <p>
 * In both cases transforms and a filter are applied to transform or filter
 * requested content. Transforms are used to transform loaded byte data into
 * another data type. A good example is the transformation from raw byte data to
 * an Image object.
 * </p>
 * <p>
 * The filter of a ContentSource is used to determine if a requested content
 * should be retrieved through this ContentSource. This is useful for storing
 * e.g. images to the file system while audio should be stored in the rms. It is
 * mandatory to use filters when multiple ContentSources are used to avoid
 * redundant storing and loading of content.
 * </p>
 * 
 * @author Andre Schmidt
 * 
 */
public abstract class ContentSource {

	final String id;

	/**
	 * the storage index
	 */
	final StorageIndex storageIndex;

	/**
	 * the listeners
	 */
	final Vector sources = new Vector();

	/**
	 * the transformers
	 */
	final Hashtable transformers = new Hashtable();

	/**
	 * the filter
	 */
	ContentFilter filter;

	/**
	 * Creates a new ContentSource instance with no index
	 */
	public ContentSource(String id) {
		this(id, null);
	}

	/**
	 * Creates a new ContentSource with the specified index
	 * 
	 * @param index
	 *            the StorageIndex instances
	 */
	public ContentSource(String id, StorageIndex index) {
		this.id = id;
		this.storageIndex = index;
	}

	/**
	 * Sets the source for this ContentSource and registers this ContentSource
	 * as a listener in the source
	 * 
	 * @param source
	 *            the source
	 */
	public void attachSource(ContentSource source) {
		if (this.sources.size() > 0 && !hasFiltersComplete()) {
			throw new IllegalArgumentException(
					"please add filters to all used source for " + this);
		}

		this.sources.addElement(source);

		Log.d("attached source", source);
	}

	/**
	 * Returns true if all sources of this source have filters
	 * 
	 * @return true if all sources of this source have filters otherwise false
	 */
	boolean hasFiltersComplete() {
		for (int index = 0; index < this.sources.size(); index++) {
			ContentSource source = (ContentSource) this.sources
					.elementAt(index);
			if (source.getFilter() == null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Removes this ContentSource from the listeners of the current source and
	 * sets the source to null
	 */
	public void detachSource(ContentSource source) {
		this.sources.removeElement(source);

		Log.d("detached source", source);
	}

	public void setContentFilter(ContentFilter filter) {
		this.filter = filter;

		Log.d("set filter", filter);
	}

	public ContentFilter getFilter() {
		return this.filter;
	}

	public void addContentTransform(ContentTransform transformer) {
		String id = transformer.getTransformId();
		if (!id.equals(ContentDescriptor.TRANSFORM_NONE)
				&& this.transformers.containsKey(id)) {
			Log.i("overwriting transformer with id", id);
		}

		this.transformers.put(id, transformer);

		Log.d("added transform", transformer);
	}

	public void removeContentTransform(ContentTransform transformer) {
		String id = transformer.getTransformId();
		this.transformers.remove(id);

		Log.d("removed transform", transformers);
	}

	public Object loadContent(ContentDescriptor descriptor)
			throws ContentException {
		Object data = loadContentData(descriptor);

		if (data == null) {
			if (this.sources.size() > 0) {
				for (int index = 0; index < this.sources.size(); index++) {
					ContentSource source = (ContentSource) this.sources
							.elementAt(index);

					ContentFilter filter = source.getFilter();
					if (filter != null && !filter.filter(descriptor)) {
						Log.d("filtered source", source);
						continue;
					}

					Log.d("load content", descriptor);
					Log.d("from source", source);

					data = source.loadContent(descriptor);
					if (data != null) {
						data = transformContent(descriptor, data);
						Log.d("transformed content", data);

						if (descriptor.getCachingPolicy() == ContentDescriptor.CACHING_READ_WRITE) {
							storeContent(descriptor, data);
						} else {
							Log.d("no storage due to caching policy");
						}
						return data;
					}
				}
			} else {
				String message = this.getClass().getName()
						+ " : no source found, please add a source";
				throw new ContentException(message);
			}

			String message = this.getClass().getName()
					+ " : no source is applicable due to their filtering";
			throw new ContentException(message);
		}

		return data;
	}

	protected Object transformContent(ContentDescriptor descriptor, Object data) {
		if (this.transformers.size() > 0
				&& descriptor.getTransformID() != ContentDescriptor.TRANSFORM_NONE
				&& !(data instanceof ContentException)) {
			ContentTransform transformer = (ContentTransform) this.transformers
					.get(descriptor.getTransformID());
			if (transformer != null) {
				try {
					Log.d("using transform", transformer);
					Log.d("on descriptor", descriptor);
					data = transformer.transformContent(data);
				} catch (IOException e) {
					Log.e("error transforming", e);
				}

				return data;
			}
		}

		return data;
	}

	protected Object loadContentData(ContentDescriptor descriptor)
			throws ContentException {
		// if this source has a storage ...
		if (hasStorage()) {
			Log.d("has storage");

			// prepare the storage if it isn't
			if (!this.storageIndex.isPrepared()) {
				this.storageIndex.prepare();

				Log.d("storage index prepared");
			}

			// get the StorageReference to a potentially stored content
			StorageReference reference = this.storageIndex
					.getReference(descriptor);

			// if the content is stored ...
			if (reference != null) {
				Log.d("found reference : " + reference);

				// update its activity
				reference.updateActivity();

				// load the content
				return loadContent(descriptor, reference);
			}
		}

		// just load the content
		return loadContent(descriptor, null);
	}

	/**
	 * Returns true, if this ContentSource has a StorageIndex, otherwise false
	 * 
	 * @return true, if this ContentSource has a StorageIndex, otherwise false
	 */
	boolean hasStorage() {
		return this.storageIndex != null;
	}

	/**
	 * Checks for update, checks the version and finally calls load() to load
	 * the content
	 * 
	 * @param descriptor
	 *            the content descriptor
	 * @param reference
	 *            the reference to the stored content
	 * @return the reference retrieved in load()
	 */
	protected Object loadContent(ContentDescriptor descriptor,
			StorageReference reference) throws ContentException {

		if (reference != null) {
			if (reference.getVersion() != descriptor.getVersion()) {
				// #debug info
				System.out
						.println(this.getClass().getName()
								+ " : version does not match stored content, destroying ...");

				// destroy the content if the version doesn't match
				destroyContent(descriptor);
				return null;
			}
		}

		try {
			if (reference != null) {
				Log.d("loading content from storage", reference);

				return load(reference);
			} else {
				Log.d("loading content from source", descriptor);
				return load(descriptor);
			}
		} catch (IOException e) {
			String message = this.getClass().getName() + " : " + e;
			throw new ContentException(message);
		}
	}

	protected void storeContent(ContentDescriptor descriptor, Object data)
			throws ContentException {
		// when content is available ...
		try {
			// if this ContentSource has a storage ...
			if (hasStorage()) {
				Log.d("storing content" + data);
				Log.d("for", descriptor);

				// get the size of the content
				int size = getSize(descriptor, data);

				Log.d("data size", Integer.toString(size));

				// store the content
				Object reference = store(descriptor, data);

				// add the reference to the StorageIndex
				this.storageIndex.addReference(new StorageReference(descriptor,
						size, reference));

				Log.d("content stored with reference", reference);
			}
		} catch (IOException e) {
			String message = "error storing content : " + e;
			throw new ContentException(message);
		}

		// clean the storage if needed
		clean();
	}

	/**
	 * Fetches the StorageReference from the StorageIndex, removes it from the
	 * storageIndex and calls destroy() on the content
	 * 
	 * @param descriptor
	 *            the content descriptor
	 */
	protected void destroyContent(ContentDescriptor descriptor)
			throws ContentException {
		// if this ContentSource has a storage ...
		if (hasStorage()) {
			Log.d("destroying content", descriptor);

			// get the reference to the content
			StorageReference reference = this.storageIndex
					.getReference(descriptor);

			if (reference != null) {
				// remove the reference from the StorageIndex
				this.storageIndex.removeReference(reference);

				try {
					// destroy the stored content
					destroy(reference);
				} catch (IOException e) {
					String message = "error destroying content : " + e;
					throw new ContentException(message);
				}
			}

			Log.d("content destroyed", descriptor);
		}
	}

	/**
	 * If a clean is needed on the storage, the clean order is applied to the
	 * StorageIndex and contents are deleted until the cache size is below the
	 * thresholds
	 */
	public void clean() throws ContentException {
		if (hasStorage()) {
			if (!this.storageIndex.isCleanNeeded()) {
				return;
			}

			Log.d("clean", this.storageIndex);

			// apply the clean order
			this.storageIndex.applyOrder();

			do {
				// get the index of the first disposable content
				int index = this.storageIndex.getDisposableIndex();
				// get the reference
				StorageReference reference = this.storageIndex
						.getReference(index);
				// destroy the content
				destroyContent(reference);
			} while ((this.storageIndex.isCleanNeeded()));
		}
	}

	/**
	 * Returns the StorageIndex
	 * 
	 * @return the StorageIndex
	 */
	public StorageIndex getStorageIndex() {
		return this.storageIndex;
	}

	/**
	 * Returns the size for a data object.
	 * 
	 * @param data
	 *            the data
	 * @return the size of the data
	 */
	protected int getSize(ContentDescriptor descriptor, Object data)
			throws ContentException {
		int dataSize = ContentTransform.DATASIZE_UNKNOWN;

		String transformId = descriptor.getTransformID();
		ContentTransform transform = (ContentTransform) this.transformers
				.get(transformId);

		if (transform != null) {
			dataSize = transform.calculateDataSize(data);
			return dataSize;
		} else {
			if (dataSize == ContentTransform.DATASIZE_UNKNOWN) {
				if (data instanceof byte[]) {
					return ((byte[]) data).length;
				}
			}
		}

		throw new ContentException("unable to determine size of " + data);
	}

	/**
	 * Load the specified content by the use of the content descriptor.
	 * 
	 * @param descriptor
	 *            the content descriptor
	 * @return the loaded data
	 * @throws IOException
	 *             if an error occurs (obviously)
	 */
	protected abstract Object load(ContentDescriptor descriptor)
			throws IOException;

	/**
	 * Load the specified content by the use of the storage reference.
	 * 
	 * @param reference
	 *            the storage reference
	 * @return the loaded data
	 * @throws IOException
	 *             if an error occurs (obviously)
	 */
	protected abstract Object load(StorageReference reference)
			throws IOException;

	/**
	 * Stores the specified content
	 * 
	 * @param descriptor
	 *            the content descriptor
	 * @param data
	 *            the data
	 * @return the reference to the stored data
	 * @throws IOException
	 *             if an error occurs (obviously)
	 */
	protected abstract Object store(ContentDescriptor descriptor, Object data)
			throws IOException;

	/**
	 * Destroys the content specified by the storage reference
	 * 
	 * @param reference
	 *            the storage reference
	 * @throws IOException
	 *             if an error occurs (obviously)
	 */
	protected abstract void destroy(StorageReference reference)
			throws IOException;

	/**
	 * Sweeps the storage
	 * 
	 * @throws ContentException
	 *             if an error occurs
	 */
	public void sweep() throws ContentException {
		if (hasStorage()) {
			for (int index = 0; index < this.storageIndex.size(); index++) {
				StorageReference reference = this.storageIndex
						.getReference(index);

				// destroy the content
				destroyContent(reference);
			}
		}

		for (int index = 0; index < this.sources.size(); index++) {
			ContentSource source = (ContentSource) this.sources
					.elementAt(index);
			source.sweep();
		}
	}

	/**
	 * shuts this ContentSource down. It is encouraged to overwrite this method.
	 */
	protected void shutdown() {
		if (hasStorage()) {
			this.storageIndex.shutdown();
		}

		for (int index = 0; index < this.sources.size(); index++) {
			ContentSource source = (ContentSource) this.sources
					.elementAt(index);
			source.shutdown();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ToStringHelper.createInstance("ContentSource")
				.set("id", this.id).set("sources", this.sources)
				.set("transformers", this.transformers)
				.set("filter", this.filter).toString();
	}

	/**
	 * Prints an info
	 * 
	 * @param info
	 */
	public void debug(String info) {
		System.out.println(this.id + " : " + info);
	}

	public void error(String error, Exception e) {
		Log.e(error, e);
	}
}
