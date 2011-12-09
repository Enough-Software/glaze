package de.enough.glaze.content.source.impl;

import java.io.IOException;
import java.io.InputStream;

import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.helper.StreamHelper;
import de.enough.glaze.content.source.ContentSource;
import de.enough.glaze.content.storage.StorageReference;

/**
 * A sample HttpContentSource that receives byte[]
 * 
 * @author Andre Schmidt
 * 
 */
public class ResourceContentSource extends ContentSource {

	public ResourceContentSource(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zyb.nowplus.business.content.ContentSource#destroy(com.zyb.nowplus
	 * .business.content.StorageReference)
	 */
	protected void destroy(StorageReference reference) throws IOException {
		// do nothing here, its just a source
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zyb.nowplus.business.content.ContentSource#load(com.zyb.nowplus.business
	 * .content.ContentDescriptor)
	 */
	protected Object load(ContentDescriptor descriptor) throws IOException {
		byte[] data = null;

		// load resource from file
		String url = descriptor.getUrl();
		try {
			InputStream stream = getClass().getResourceAsStream(url);
			// read all bytes into data
			data = StreamHelper.toBytes(stream);

			return data;
		} catch (Exception e) {
			throw new IOException("unable to load " + url);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zyb.nowplus.business.content.ContentSource#load(com.zyb.nowplus.business
	 * .content.StorageReference)
	 */
	protected Object load(StorageReference reference) throws IOException {
		// do nothing here, its just a source
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zyb.nowplus.business.content.ContentSource#store(com.zyb.nowplus.
	 * business.content.ContentDescriptor, java.lang.Object)
	 */
	protected Object store(ContentDescriptor descriptor, Object data)
			throws IOException {
		// do nothing here, its just a source
		return null;
	}

}
