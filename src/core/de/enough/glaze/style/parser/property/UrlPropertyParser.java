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
 
package de.enough.glaze.style.parser.property;

import java.io.UTFDataFormatException;
import java.util.Vector;

import de.enough.glaze.style.StyleSheet;
import de.enough.glaze.style.Url;
import de.enough.glaze.style.parser.exception.CssSyntaxError;
import de.enough.glaze.style.parser.property.url.DefaultUrlBuilder;
import de.enough.glaze.style.parser.property.url.UrlBuilder;

/**
 * A {@link PropertyParser} implementation to parse a URL
 * 
 * @author Andre
 * 
 */
public class UrlPropertyParser extends PropertyParser {

	/**
	 * the instance
	 */
	private static UrlPropertyParser INSTANCE;

	/**
	 * The url builder.
	 */
	private static UrlBuilder urlBuilder = new DefaultUrlBuilder();

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static UrlPropertyParser getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UrlPropertyParser();
		}

		return INSTANCE;
	}

	/**
	 * Sets the {@link UrlBuilder} to use
	 * 
	 * @param urlBuilder
	 *            the {@link UrlBuilder} instance
	 */
	public static void setUrlBuilder(UrlBuilder urlBuilder) {
		UrlPropertyParser.urlBuilder = urlBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.ValueParser#parseValue(java.lang.String)
	 */
	protected Object parse(String value, Property property)
			throws CssSyntaxError {
		String url = parseUrl(value, property);
		return UrlPropertyParser.urlBuilder.getResourceUrl(url);
	}

	/**
	 * Parses the given value to a url
	 * 
	 * @param value
	 *            the value
	 * @param property
	 *            the property
	 * @return the url
	 * @throws CssSyntaxError
	 *             if the CSS syntax is wrong
	 */
	private String parseUrl(String value, Property property)
			throws CssSyntaxError {
		String url = null;
		int startPos = value.indexOf('"');
		if (startPos != -1) {
			int endPos = value.indexOf('"', startPos + 1);
			if (endPos != -1) {
				url = value.substring(startPos + 1, endPos);
			}
		}
		if (url == null) {
			if (value.startsWith("url")) {
				startPos = value.indexOf('(');
				int endPos = value.indexOf(')');
				if (startPos != -1 && endPos > startPos) {
					url = value.substring(startPos + 1, endPos).trim();
				}
			}
		}
		if (url == null) {
			throw new CssSyntaxError("invalid url", property);
		}

		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.enough.glaze.style.parser.property.PropertyParser#toArray(java.util
	 * .Vector)
	 */
	protected Object toArray(Vector vector) {
		Url[] urls = new Url[vector.size()];
		vector.copyInto(urls);
		return urls;
	}
}
