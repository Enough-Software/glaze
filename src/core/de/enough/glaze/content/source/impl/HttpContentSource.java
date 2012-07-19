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
 
package de.enough.glaze.content.source.impl;

import java.io.IOException;
import java.io.InputStream;

import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.helper.StreamHelper;
import de.enough.glaze.content.source.ContentSource;
import de.enough.glaze.content.storage.StorageReference;
import de.enough.glaze.style.Url;

/**
 * A sample HttpContentSource that receives byte[]
 * @author Andre Schmidt
 *
 */
public class HttpContentSource extends ContentSource{
	
	public final static String PREFIX = "http://";

	public HttpContentSource(String id) {
		super(id);
	}

	/* (non-Javadoc)
	 * @see com.zyb.nowplus.business.content.ContentSource#destroy(com.zyb.nowplus.business.content.StorageReference)
	 */
	protected void destroy(StorageReference reference) throws IOException {
		// do nothing here, its just a source
	}
	
	/* (non-Javadoc)
	 * @see com.zyb.nowplus.business.content.ContentSource#load(com.zyb.nowplus.business.content.ContentDescriptor)
	 */
	protected Object load(ContentDescriptor descriptor) throws IOException {
		byte[] data = null;
		
		try {
			// open the http connection
			Url url = new Url(descriptor.getUrl());
			InputStream stream = url.openStream();
	
			// read all bytes into data
			data = StreamHelper.toBytes(stream);
		} catch (Exception e) {
			throw new IOException("unable to load " + descriptor.getUrl());
		}
		
		return data;
	}

	/* (non-Javadoc)
	 * @see com.zyb.nowplus.business.content.ContentSource#load(com.zyb.nowplus.business.content.StorageReference)
	 */
	protected Object load(StorageReference reference) throws IOException {
		// do nothing here, its just a source
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zyb.nowplus.business.content.ContentSource#store(com.zyb.nowplus.business.content.ContentDescriptor, java.lang.Object)
	 */
	protected Object store(ContentDescriptor descriptor, Object data)
			throws IOException {
		// do nothing here, its just a source
		return null;
	}

}
