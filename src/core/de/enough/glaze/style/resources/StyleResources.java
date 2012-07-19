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
 
package de.enough.glaze.style.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.microedition.m2g.SVGImage;

import net.rim.device.api.system.Bitmap;
import de.enough.glaze.content.ContentDescriptor;
import de.enough.glaze.content.ContentException;
import de.enough.glaze.content.ContentLoader;
import de.enough.glaze.content.filter.impl.HttpContentFilter;
import de.enough.glaze.content.source.impl.HttpContentSource;
import de.enough.glaze.content.source.impl.RMSContentStorage;
import de.enough.glaze.content.source.impl.RMSStorageIndex;
import de.enough.glaze.content.source.impl.ResourceContentSource;
import de.enough.glaze.style.Url;

/**
 * Provides loader methods to load resources used in backgrounds etc.. Web-based
 * resources are cached the resources to provide faster access.
 * 
 * @author Andre
 * 
 */
public class StyleResources {

	/**
	 * the instance
	 */
	private static StyleResources INSTANCE;

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static StyleResources getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new StyleResources();
		}

		return INSTANCE;
	}

	/**
	 * the content loader
	 */
	private final ContentLoader contentLoader;

	/**
	 * Creates a new {@link StyleResources} instance
	 */
	private StyleResources() {
		// create the content source and filter for web resources
		HttpContentSource httpSource = new HttpContentSource("http");

		// create the rms content storage
		RMSContentStorage rmsStorage = new RMSContentStorage("rms",
				new RMSStorageIndex());
		// set a filter to avoid loading resource via the rms
		rmsStorage.setContentFilter(new HttpContentFilter());
		// attach http to the rms storage
		rmsStorage.attachSource(httpSource);

		// create the content source for local resources
		ResourceContentSource resourceSource = new ResourceContentSource(
				"resource");

		// create the content loader with the rms storage and resource source
		this.contentLoader = new ContentLoader();
		this.contentLoader.attachSource(rmsStorage);
		this.contentLoader.attachSource(resourceSource);
	}

	/**
	 * Loads the bitmap from the given url and returns it
	 * 
	 * @param url
	 *            the url
	 * @return the loaded bitmap
	 * @throws ContentException
	 *             if an error occurs while loading the bitmap
	 */
	public Bitmap loadBitmap(Url url) throws ContentException {
		return loadBitmap(url.toString());
	}

	/**
	 * Loads the bitmap from the given url and returns it
	 * 
	 * @param url
	 *            the url
	 * @return the loaded bitmap
	 * @throws ContentException
	 *             if an error occurs while loading the bitmap
	 */
	public Bitmap loadBitmap(String url) throws ContentException {
		byte[] data = loadContent(url);
		return Bitmap.createBitmapFromBytes(data, 0, data.length, 1);
	}

	/**
	 * Loads the content data from the given url and returns it
	 * 
	 * @param url
	 *            the url
	 * @return the loaded content data
	 * @throws ContentException
	 *             if an error occurs while loading the data
	 */
	private byte[] loadContent(String url) throws ContentException {
		ContentDescriptor descriptor = new ContentDescriptor(url);
		return (byte[]) this.contentLoader.loadContent(descriptor);
	}

	/**
	 * Loades the SVG from the given url and returns it
	 * 
	 * @param url
	 *            the url
	 * @return the SVG image
	 * @throws ContentException
	 *             if an error occurs while loading the data
	 * @throws IOException
	 *             if an error occurs while loading the data
	 */
	public SVGImage loadSVG(Url url) throws ContentException, IOException {
		return loadSVG(url.toString());
	}

	/**
	 * Loads the SVG image from the given url and returns it
	 * 
	 * @param url
	 *            the url
	 * @return the SVG image
	 * @throws ContentException
	 *             if an error occurs while loading the data
	 * @throws IOException
	 *             if an error occurs while loading the data
	 */
	public SVGImage loadSVG(String url) throws ContentException, IOException {
		byte[] data = loadContent(url);
		ByteArrayInputStream stream = new ByteArrayInputStream(data);
		return (SVGImage) SVGImage.createImage(stream, null);
	}

	/**
	 * Clears the resource cache
	 */
	public void clear() throws ContentException {
		this.contentLoader.clear();
	}
}
